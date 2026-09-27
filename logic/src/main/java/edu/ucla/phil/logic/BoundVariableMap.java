package edu.ucla.phil.logic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;

class BoundVariableMap extends Hashtable implements LogicConstants {
   Vector clashes = null;
   int freshCount = 0;

   @Override
   public Object clone() {
      BoundVariableMap boundvariablemap1 = (BoundVariableMap)super.clone();
      boundvariablemap1.clashes = null;
      boundvariablemap1.freshCount = 0;
      return boundvariablemap1;
   }

   String lookup(String s) {
      return (String)this.get(s);
   }

   boolean assign(String s, String s1, boolean flag) {
      if (!SimpleTerm.isSimpleTerm(s1, flag)) {
         return false;
      } else {
         this.put(s, s1);
         return true;
      }
   }

   boolean assign(String s, String s1) {
      return this.assign(s, s1, false);
   }

   boolean matchBinders(Expression expression, Expression expression1, BinderMap bindermap) {
      return expression1 == null ? true : this.matchBinders(expression, expression1, bindermap.getBinderCorrespondence(expression, expression1));
   }

   boolean matchBinders(Expression expression, Expression expression1, int[][] aint) {
      if (expression1 == null) {
         return true;
      } else {
         Vector vector = expression.getBinders();
         Vector vector1 = expression1.getBinders();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            int[] aint1 = aint[j];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               String s = binderVariable(vector1, aint1[l]);
               if (s != null) {
                  String s1 = binderVariable(vector, j);
                  String s2 = this.lookup(s1);
                  if (s2 == null) {
                     this.assign(s1, s);
                  } else if (!s2.equals(s)) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   boolean renameBinders(Expression expression, Expression expression1, BinderMap bindermap, DerivationLineChecker derivationlinechecker) {
      return this.renameBinders(expression, expression1, bindermap.getBinderCorrespondence(expression, expression1), derivationlinechecker);
   }

   boolean renameBinders(Expression expression, Expression expression1, int[][] aint, DerivationLineChecker derivationlinechecker) {
      DerivationLine derivationline = derivationlinechecker == null ? null : derivationlinechecker.line;
      Vector vector = expression.getBinders();
      Vector vector1 = expression1.getBinders();
      BoundVariableNames boundvariablenames = new BoundVariableNames();
      boundvariablenames.setSize(vector1.size());
      int i = vector.size();
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = binderVariable(vector, j);
      }

      label120:
      while (true) {
         this.freshCount = 0;
         BoundVariableMap boundvariablemap1 = (BoundVariableMap)this.clone();

         for (int i1 = 0; i1 < i; i1++) {
            String s = boundvariablemap1.lookup(astring[i1]);
            if (s == null) {
               s = SchematicLetter.placeholder(this.freshCount++);
               boundvariablemap1.assign(astring[i1], s, true);
            }

            int[] aint1 = aint[i1];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               boundvariablenames.setElementAt(s, aint1[l]);
            }
         }

         if (this.freshCount != 0 && derivationline != null) {
            if (derivationline.box.module.serialMode && derivationlinechecker.presetAnswers == null) {
               derivationlinechecker.reportError("dererr064");
               derivationline.box.module.complete = false;
               return false;
            }

            SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
            TermLetter[] atermletter = new TermLetter[this.freshCount];

            for (int j1 = 0; j1 < this.freshCount; j1++) {
               schemeinstantiation.addPendingLetter(atermletter[j1] = new TermLetter(SchematicLetter.placeholder(j1)));
            }

            SchemeSubstitutionPanel schemesubstitutionpanel = new SchemeSubstitutionPanel(schemeinstantiation, 50, derivationline.box.module.frame, true);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new VerticalStackLayout(0));
            String s1 = this.freshCount == 1 ? "" : "s";
            String s2 = this.freshCount == 1 ? "a " : "";
            jpanel.add(new LogicLabel("Given the expression"));
            Expression expression2 = expression1.copy();
            expression2.renameBoundVariables(boundvariablenames);
            String s3 = expression2.toString();
            TextHighlighter texthighlighter = highlightLetters(s3, 0, this.freshCount);
            jpanel.add(LogicProgram.createFormulaRow(s3, 0, 14, 250, texthighlighter));
            jpanel.add(new LogicLabel("please choose " + s2 + "symbol" + s1 + " for"));
            jpanel.add(new LogicLabel("the following bound variable" + s1));
            jpanel.add(schemesubstitutionpanel);
            String[] astring1 = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(derivationline.box.module.frame, "Line " + derivationline.getLineNumber(), jpanel, astring1);
            messagedialog.setDefaultButtonIndex(0);
            schemesubstitutionpanel.setMessageDialog(messagedialog);
            derivationline.focusEditor(true);
            if (!messagedialog.fillFieldsAndChoose(derivationlinechecker.presetAnswers, schemesubstitutionpanel.getPendingFields(), 0)) {
               if (derivationline.box.module.serialMode) {
                  derivationlinechecker.reportError("dererr064");
                  derivationline.box.module.complete = false;
                  messagedialog.dispose();
                  return false;
               }

               Rectangle rectangle = LogicProgram.boundsRelativeTo(derivationline.annotationEditor, null);
               messagedialog.pack();
               messagedialog.showAt(new Point(rectangle.x, rectangle.y + rectangle.height));
            } else {
               messagedialog.dispose();
            }

            if (messagedialog.selectedButton != 1 && messagedialog.selectedButton != -1) {
               if ((schemeinstantiation = schemesubstitutionpanel.readInstantiation()) == null) {
                  derivationline.showMessage(schemesubstitutionpanel.errorId, schemesubstitutionpanel.errorParams);
                  return false;
               }

               this.freshCount = 0;
               int k1 = 0;

               while (true) {
                  if (k1 >= i) {
                     continue label120;
                  }

                  if (aint[k1] != null && aint[k1].length != 0) {
                     String s4 = this.lookup(astring[k1]);
                     if (s4 == null) {
                        LetterReplacement letterreplacement = schemeinstantiation.getReplacement(atermletter[this.freshCount++]);
                        if (letterreplacement != null && !this.assign(astring[k1], letterreplacement.replacement.symbol)) {
                           derivationline.showMessage(
                              "dererr060", derivationlinechecker, Message.params("variable name", "\\l" + letterreplacement.replacement.symbol + "\\l")
                           );
                           return false;
                        }
                     }
                  }

                  k1++;
               }
            }

            derivationline.showMessage("dererr028");
            derivationline.box.module.abort(true);
            return false;
         }

         expression1.renameBoundVariables(boundvariablenames);
         this.clashes = expression1.findMislinkedVariables();
         if (this.clashes != null) {
            if (derivationline != null) {
               derivationline.showMessage("dererr061", derivationlinechecker);
            }

            return false;
         }

         return true;
      }
   }

   boolean matches(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation) {
      BinderMap bindermap = new BinderMap();
      Expression expression2 = expression.instantiate(schemeinstantiation, bindermap);
      int[][] aint = bindermap.getBinderCorrespondence(expression, expression2);
      if (!this.matchBinders(expression, expression1, aint)) {
         return false;
      } else {
         if (!this.renameBinders(expression, expression2, aint, null)) {
            return false;
         } else if (!expression2.isIdentical(expression1)) {
            return false;
         } else {
            return true;
         }
      }
   }

   static TextHighlighter highlightLetters(String s, int i, int j) {
      Vector vector = new Vector();

      for (int k = 0; k < j; k++) {
         IntervalSet intervalset = new IntervalSet();
         String s1 = SchematicLetter.placeholder(i + k);
         int l = -1;

         while ((l = s.indexOf(s1, l + 1)) != -1) {
            intervalset.toggleBoundary(l).toggleBoundary(l + s1.length());
         }

         vector.addElement(intervalset);
      }

      return TextHighlighter.fromRanges(vector);
   }

   boolean coversBinders(Expression expression) {
      Vector vector = expression.getBinders();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         if (this.lookup(binderVariable(vector, j)) == null) {
            return false;
         }
      }

      return true;
   }

   static String binderVariable(Vector vector, int i) {
      return ((Expression)vector.elementAt(i)).getChild(0).getSymbol();
   }

   String encode() {
      boolean flag = false;
      String s = "";

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         String s1 = (String)enumeration.nextElement();
         s = s + (flag ? "." : "") + s1 + ":" + this.lookup(s1);
      }

      return s;
   }

   static BoundVariableMap decode(String s) {
      BoundVariableMap boundvariablemap = new BoundVariableMap();

      while (!s.equals("")) {
         int i = s.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         int j = s1.indexOf(":");
         if (j != -1) {
            boundvariablemap.assign(s1.substring(0, j).trim(), s1.substring(j + 1).trim());
         }
      }

      return boundvariablemap;
   }
}
