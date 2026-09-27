package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.Timer;
import javax.swing.ToolTipManager;

/**
 * The rules view of the derivation module (the Applicable button): the rules that apply to
 * the stack of the justification at the cursor, and what each would produce. It runs the
 * steps before the cursor as the stack view does, then tries every rule on the stack there
 * as the next step, without dialogs; nothing in the derivation changes.
 *
 * A result the program would ask the user to complete shows the missing parts as unknowns:
 * ?P, ?Q for formulas, ?F(..) for formulas with the arguments shown, ?t, ?u for terms,
 * ?f(..) for terms built around the arguments shown, and ?x, ?y for variables. Rules the
 * problem does not allow are listed too, grayed, with the reason.
 */
class DerivationRulesView extends JPanel implements ActionListener, DerivationConstants {
   static final String[] BOX_RULES = new String[]{"CD", "ID", "DD", "UD", "BD"};
   static final Color UNKNOWN_COLOR = new Color(0, 90, 200);
   static final Color LOCKED_COLOR = new Color(150, 150, 150);
   LPDerivation module;
   Timer timer;
   JPanel content;
   String lastKey;
   String lastShown;
   int ticks;
   int savedDismissDelay = -1;

   DerivationRulesView(LPDerivation lpderivation) {
      this.module = lpderivation;
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
      JLabel jlabel = new JLabel("Rules at the cursor");
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
      jlabel.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
      this.add(jlabel, "North");
      this.content = new JPanel(new GridBagLayout());
      DerivationStackView.WidthTrackingPanel widthtrackingpanel = new DerivationStackView.WidthTrackingPanel();
      widthtrackingpanel.add(this.content, "North");
      JScrollPane jscrollpane = new JScrollPane(widthtrackingpanel, 20, 31);
      jscrollpane.setBorder(null);
      jscrollpane.getVerticalScrollBar().setUnitIncrement(16);
      this.add(jscrollpane, "Center");
      this.setPreferredSize(new Dimension(LogicProgram.fontSize * 24, 0));
      this.timer = new Timer(200, this);
   }

   void setOn(boolean flag) {
      this.setVisible(flag);
      this.lastKey = null;
      if (flag) {
         this.timer.start();
         this.refresh();
      } else {
         this.timer.stop();
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (!this.isDisplayable()) {
         this.timer.stop();
      } else if (this.isShowing() && this.module.phase == 0 && !this.module.serialMode) {
         this.refresh();
      }
   }

   /** Recomputes when the edited text, the cursor or the line changes, and every two seconds or so. */
   void refresh() {
      DerivationLineEditor derivationlineeditor = this.module.lastFocus;
      if (derivationlineeditor == null || derivationlineeditor.line == null || derivationlineeditor.line.annotationEditor == null) {
         this.show(null, "Put the cursor in a justification to see the rules that apply there.", null);
      } else {
         DerivationLine derivationline = derivationlineeditor.line;
         String s = derivationline.annotationEditor.getText();
         int i = derivationlineeditor == derivationline.annotationEditor ? derivationlineeditor.getCaretPosition() : s.length();
         String s1 = System.identityHashCode(derivationline) + "\u0000" + i + "\u0000" + s;
         if (!s1.equals(this.lastKey) || ++this.ticks >= 10) {
            this.lastKey = s1;
            this.ticks = 0;
            String s2 = DerivationStackView.textBeforeCursor(s, i);
            this.show(derivationline, s2, compute(derivationline, s2));
         }
      }
   }

   void show(DerivationLine derivationline, String s, DerivationRulesView.Result result) {
      StringBuffer stringbuffer = new StringBuffer();
      Vector vector = new Vector();
      if (derivationline == null) {
         vector.addElement(DerivationStackView.note(s, Color.gray));
         stringbuffer.append(s);
      } else {
         String s1 = s.trim().equals("") ? "(no steps yet)" : s.trim();
         String s2 = "Line " + derivationline.getLineNumber() + ", after: " + s1;
         vector.addElement(DerivationStackView.note(s2, Color.darkGray));
         stringbuffer.append(s2);
         if (result.error != null) {
            String s3 = "The steps before the cursor do not apply: " + result.error;
            vector.addElement(DerivationStackView.note(s3, new Color(170, 0, 0)));
            stringbuffer.append('\u0000').append(s3);
         } else if (result.closed != null) {
            String s4 = result.closed + " closes the box; no rule can follow it.";
            vector.addElement(DerivationStackView.note(s4, Color.gray));
            stringbuffer.append('\u0000').append(s4);
         } else {
            Vector vector1 = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();

            for (int i = 0; i < result.rules.size(); i++) {
               DerivationRulesView.Applicable applicable = (DerivationRulesView.Applicable)result.rules.elementAt(i);
               (applicable.group == 3 ? vector3 : (applicable.lock == null ? vector1 : vector2)).addElement(applicable);
               stringbuffer.append('\u0000').append(applicable.signature());
            }

            if (vector1.isEmpty()) {
               vector.addElement(DerivationStackView.note(vector2.isEmpty() ? "No rule applies to the stack here." : "No rule this problem allows applies here.", Color.gray));
            }

            for (int j = 0; j < vector1.size(); j++) {
               vector.addElement(this.row((DerivationRulesView.Applicable)vector1.elementAt(j)));
            }

            if (!vector2.isEmpty()) {
               vector.addElement(heading("Not allowed here"));

               for (int k = 0; k < vector2.size(); k++) {
                  vector.addElement(this.row((DerivationRulesView.Applicable)vector2.elementAt(k)));
               }
            }

            if (!vector3.isEmpty()) {
               vector.addElement(heading("Stack operations"));

               for (int l = 0; l < vector3.size(); l++) {
                  vector.addElement(this.row((DerivationRulesView.Applicable)vector3.elementAt(l)));
               }
            }

            vector.addElement(
               DerivationStackView.note(
                  "Unknowns are parts the program would ask for: ?P a formula, ?t a term, ?x a variable. Hover over a rule for details. Theorems (Tn) are not listed.",
                  Color.gray
               )
            );
         }
      }

      String s5 = stringbuffer.toString();
      if (!s5.equals(this.lastShown)) {
         this.lastShown = s5;
         this.content.removeAll();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         gridbagconstraints.gridx = 0;
         gridbagconstraints.weightx = 1.0;
         gridbagconstraints.fill = 2;
         gridbagconstraints.anchor = 18;
         gridbagconstraints.insets = new Insets(2, 6, 2, 6);

         for (int i1 = 0; i1 < vector.size(); i1++) {
            this.content.add((Component)vector.elementAt(i1), gridbagconstraints);
         }

         this.content.revalidate();
         this.content.repaint();
      }
   }

   static JLabel heading(String s) {
      JLabel jlabel = new JLabel(s);
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize * 5 / 6, 1));
      jlabel.setForeground(Color.darkGray);
      jlabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
      return jlabel;
   }

   /** One rule: its name, what it produces, and (when it is not allowed) why; details on hover. */
   JPanel row(DerivationRulesView.Applicable applicable) {
      boolean flag = applicable.lock != null;
      JPanel jpanel = new JPanel(new BorderLayout(8, 0)) {
         @Override
         public JToolTip createToolTip() {
            JToolTip jtooltip = super.createToolTip();
            jtooltip.setFont(LogicProgram.getFont(LogicProgram.fontSize, 0));
            return jtooltip;
         }
      };
      jpanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.lightGray), BorderFactory.createEmptyBorder(3, 5, 3, 5)));
      JLabel jlabel = new JLabel(applicable.rule);
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize * 5 / 6, 1));
      jlabel.setForeground(flag ? LOCKED_COLOR : Color.black);
      jpanel.add(jlabel, "West");
      JLabel jlabel1 = new JLabel(applicable.formula ? "<html>" + formulaHtml(applicable.result, flag) + "</html>" : applicable.result);
      jlabel1.setFont(LogicProgram.getFont(applicable.formula ? LogicProgram.fontSize : LogicProgram.fontSize * 5 / 6, 0));
      jlabel1.setForeground(flag ? LOCKED_COLOR : Color.black);
      jpanel.add(jlabel1, "Center");
      if (applicable.matchesLine) {
         JLabel jlabel2 = new JLabel("= line");
         jlabel2.setFont(LogicProgram.getFont(LogicProgram.fontSize * 2 / 3, 0));
         jlabel2.setForeground(new Color(0, 120, 0));
         jpanel.add(jlabel2, "East");
      }

      if (flag) {
         JLabel jlabel3 = new JLabel(applicable.lock);
         jlabel3.setFont(LogicProgram.getFont(LogicProgram.fontSize * 2 / 3, 0));
         jlabel3.setForeground(LOCKED_COLOR);
         jpanel.add(jlabel3, "South");
      }

      this.setDetails(jpanel, "<html>" + applicable.details() + "</html>");
      return jpanel;
   }

   /** The hover text of the row; it stays up while the mouse is on the row. (The labels in it take no mouse events.) */
   void setDetails(JComponent jcomponent, String s) {
      jcomponent.setToolTipText(s);
      jcomponent.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent mouseevent) {
            ToolTipManager tooltipmanager = ToolTipManager.sharedInstance();
            if (DerivationRulesView.this.savedDismissDelay == -1) {
               DerivationRulesView.this.savedDismissDelay = tooltipmanager.getDismissDelay();
            }

            tooltipmanager.setDismissDelay(60000);
         }

         @Override
         public void mouseExited(MouseEvent mouseevent) {
            if (DerivationRulesView.this.savedDismissDelay != -1) {
               ToolTipManager.sharedInstance().setDismissDelay(DerivationRulesView.this.savedDismissDelay);
               DerivationRulesView.this.savedDismissDelay = -1;
            }
         }
      });
   }

   /** A formula in the display symbols as HTML, with its unknowns (?P, ?t, ...) colored. */
   static String formulaHtml(String s, boolean flag) {
      String s1 = escape(LogicProgram.translateSymbols(s));
      StringBuffer stringbuffer = new StringBuffer();
      int i = 0;

      while (i < s1.length()) {
         int j = markLength(s1, i);
         if (j == 0) {
            stringbuffer.append(s1.charAt(i));
            i++;
         } else {
            stringbuffer.append(flag ? "<i>" : "<font color=\"#005ac8\"><i>").append(s1, i, i + j).append(flag ? "</i>" : "</i></font>");
            i += j;
         }
      }

      return stringbuffer.toString();
   }

   static String escape(String s) {
      return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
   }

   /** The length of the unknown (?P, ?t2, ...) at i in s, or 0. */
   static int markLength(String s, int i) {
      if (i + 1 < s.length() && s.charAt(i) == '?' && Character.isLetter(s.charAt(i + 1))) {
         int j = i + 2;

         while (j < s.length() && Character.isDigit(s.charAt(j))) {
            j++;
         }

         return j - i;
      } else {
         return 0;
      }
   }

   /**
    * Formula text with unknowns (blank after each, as spaceMarks leaves it) as typed: with
    * quantifier words, "@?x Fx" being "forall ?x Fx".
    */
   static String typedText(String s) {
      StringBuffer stringbuffer = new StringBuffer();

      for (int i = 0; i < s.length(); i++) {
         int j = QuantifierWords.SYMBOLS.indexOf(s.charAt(i));
         if (j != -1 && markLength(s, i + 1) != 0) {
            stringbuffer.append(QuantifierWords.WORDS[j]).append(' ');
         } else {
            stringbuffer.append(s.charAt(i));
         }
      }

      return QuantifierWords.toWords(stringbuffer.toString());
   }

   /** Separates an unknown from a letter after it ("G?xb" is shown "G?x b"). */
   static String spaceMarks(String s) {
      StringBuffer stringbuffer = new StringBuffer();
      int i = 0;

      while (i < s.length()) {
         int j = markLength(s, i);
         if (j == 0) {
            stringbuffer.append(s.charAt(i));
            i++;
         } else {
            stringbuffer.append(s, i, i + j);
            i += j;
            if (i < s.length() && (Character.isLetterOrDigit(s.charAt(i)) || "?@!~".indexOf(s.charAt(i)) != -1)) {
               stringbuffer.append(' ');
            }
         }
      }

      return stringbuffer.toString();
   }

   /** The rules that apply after the steps s of derivationline's justification. */
   static DerivationRulesView.Result compute(final DerivationLine derivationline, final String s) {
      final DerivationRulesView.Result result = new DerivationRulesView.Result();
      DerivationStackView.Snapshot snapshot = DerivationStackView.compute(derivationline, s, new DerivationStackView.Visitor() {
         @Override
         public void visit(DerivationLineChecker derivationlinechecker, DerivationStackView.Snapshot snapshot) {
            DerivationRulesView.collect(derivationlinechecker, snapshot, s, result.rules);
         }
      });
      result.error = snapshot.error;
      result.closed = snapshot.closed;
      sort(result.rules);
      return result;
   }

   /** Available rules before the others, and those with no unknowns first; otherwise in the order found. */
   static void sort(Vector vector) {
      for (int i = 1; i < vector.size(); i++) {
         DerivationRulesView.Applicable applicable = (DerivationRulesView.Applicable)vector.elementAt(i);
         int j = i;

         while (j > 0 && ((DerivationRulesView.Applicable)vector.elementAt(j - 1)).rank() > applicable.rank()) {
            vector.setElementAt(vector.elementAt(j - 1), j);
            j--;
         }

         vector.setElementAt(applicable, j);
      }
   }

   static void collect(DerivationLineChecker derivationlinechecker, DerivationStackView.Snapshot snapshot, String s, Vector vector) {
      DerivationLine derivationline = derivationlinechecker.line;
      LPDerivation lpderivation = derivationline.box.module;
      int i = derivationlinechecker.getStackSize();
      derivationlinechecker.argumentCount = i;
      derivationlinechecker.matchLine = false;
      derivationlinechecker.finalStep = false;
      derivationlinechecker.assertion = null;
      derivationlinechecker.target = null;
      derivationlinechecker.premiseMatches = new Vector();
      derivationlinechecker.fullMatches = new Vector();
      Vector vector1 = new Vector();

      for (int j = 0; j < i; j++) {
         String s1 = (String)snapshot.origins.elementAt(j);
         vector1.addElement(LogicProgram.translateSymbols(snapshot.formulas.elementAt(j).toString()) + (s1.equals("") ? "" : " (" + s1 + ")"));
      }

      addBoxRules(derivationlinechecker, s, i, vector1, vector);
      addSchematicRules(derivationlinechecker, i, vector1, vector);
      if (i > 0) {
         String[] astring = new String[]{"IE", "CIE"};

         for (int k = 0; k < astring.length; k++) {
            DerivationRulesView.Applicable applicable = new DerivationRulesView.Applicable(astring[k], "?P", 1);
            applicable.lock = ruleLock(lpderivation, astring[k]);
            applicable.form = k == 0
               ? "replaces a part of the top formula by an equivalent (a biconditional theorem, rule or line)"
               : "replaces a part of the top formula by an equivalent, under a condition";
            applicable.uses.addElement(vector1.lastElement());
            applicable.notes.addElement("The program asks for the part to replace and the equivalence to use.");
            applicable.command = astring[k];
            vector.addElement(applicable);
         }
      }

      addPremises(derivationlinechecker, vector);
      if (s.trim().equals("")) {
         addAssumptions(derivationlinechecker, vector);
      }

      if (i >= 1) {
         Expression expression = derivationlinechecker.getStackFormula(-1);
         DerivationRulesView.Applicable applicable1 = new DerivationRulesView.Applicable("DUP", expression.toString(), 0);
         applicable1.group = 3;
         applicable1.form = "pushes another copy of the top formula";
         applicable1.uses.addElement(vector1.lastElement());
         vector.addElement(applicable1);
         DerivationRulesView.Applicable applicable2 = new DerivationRulesView.Applicable("DROP", i == 1 ? "(empty stack)" : "", 0);
         applicable2.group = 3;
         applicable2.formula = false;
         applicable2.result = "removes " + LogicProgram.translateSymbols(expression.toString());
         applicable2.form = "removes the top formula";
         applicable2.uses.addElement(vector1.lastElement());
         vector.addElement(applicable2);
      }

      if (i >= 2) {
         DerivationRulesView.Applicable applicable3 = new DerivationRulesView.Applicable("SWAP", derivationlinechecker.getStackFormula(-2).toString(), 0);
         applicable3.group = 3;
         applicable3.form = "exchanges the top two formulas; the result shown is the new top";
         applicable3.uses.addElement(vector1.elementAt(i - 2));
         applicable3.uses.addElement(vector1.lastElement());
         vector.addElement(applicable3);
      }

      Expression expression1 = derivationline.getFormula();

      for (int l = 0; l < vector.size(); l++) {
         DerivationRulesView.Applicable applicable4 = (DerivationRulesView.Applicable)vector.elementAt(l);
         applicable4.order = l;
         if (applicable4.group == 1 || applicable4.group == 2) {
            applicable4.matchesLine = expression1 != null && applicable4.unknowns == 0 && applicable4.value != null && applicable4.value.isIdentical(expression1);
            if (applicable4.uses.size() < i && applicable4.group == 1) {
               int i1 = i - applicable4.uses.size();
               applicable4.notes.addElement(
                  "Leaves " + i1 + " formula" + (i1 == 1 ? "" : "s") + " under the result; as the last step of the line, a rule must use the whole stack."
               );
            }
         }
      }
   }

   /** CD, ID, DD, UD and BD, where one of them can close the box with this line. */
   static void addBoxRules(DerivationLineChecker derivationlinechecker, String s, int i, Vector vector, Vector vector1) {
      DerivationLine derivationline = derivationlinechecker.line;
      if (i != 0 && derivationline.isLastInBox()) {
         DerivationBox derivationbox = derivationline.box;

         for (int j = 0; j < BOX_RULES.length; j++) {
            DerivationStackView.Snapshot snapshot = DerivationStackView.compute(derivationline, s + " " + BOX_RULES[j]);
            if (snapshot.error == null && BOX_RULES[j].equals(snapshot.closed)) {
               DerivationRulesView.Applicable applicable = new DerivationRulesView.Applicable(BOX_RULES[j], "", 0);
               applicable.group = 0;
               applicable.formula = false;
               Expression expression = derivationbox.getFormula();
               applicable.result = "closes the box: Show " + (expression == null ? "" : LogicProgram.translateSymbols(expression.toString()));
               applicable.value = expression;
               applicable.form = boxRuleForm(BOX_RULES[j]);
               int k = BOX_RULES[j].equals("ID") ? 2 : 1;

               for (int l = i - k; l < i; l++) {
                  applicable.uses.addElement(vector.elementAt(l));
               }

               applicable.lock = ruleLock(derivationline.box.module, BOX_RULES[j]);
               int i1 = derivationbox.assumptionType;
               if (applicable.lock == null && !BOX_RULES[j].equals("UD") && i1 >= 0 && i1 < ASS_STR.length) {
                  String s1 = BOX_RULES[j] + "/" + ASS_STR[i1];
                  if (derivationline.box.module.checkDerivationRule(s1, false) != null) {
                     applicable.lock = "may not close a box opened " + assumptionName(i1) + " in this problem";
                  }
               }

               if (i > k) {
                  applicable.notes.addElement("The stack must hold exactly " + k + " formula" + (k == 1 ? "" : "s") + " when the box is closed.");
               }

               applicable.command = BOX_RULES[j];
               vector1.addElement(applicable);
            }
         }
      }
   }

   static String boxRuleForm(String s) {
      if (s.equals("CD")) {
         return "the consequent of the Show line closes a conditional derivation";
      } else if (s.equals("ID")) {
         return "a formula and its negation close an indirect derivation";
      } else if (s.equals("DD")) {
         return "the Show line's formula closes a direct derivation";
      } else {
         return s.equals("UD") ? "the instance closes a universal derivation" : "the other side closes a biconditional derivation";
      }
   }

   static String assumptionName(int i) {
      String[] astring = new String[]{"without an assumption", "by ASS ID", "by ASS CD", "by ASS BD"};
      return astring[i];
   }

   /** Every rule form of the rules list and the user's rules whose premises match the top of the stack. */
   static void addSchematicRules(DerivationLineChecker derivationlinechecker, int i, Vector vector, Vector vector1) {
      LPDerivation lpderivation = derivationlinechecker.line.box.module;
      Hashtable hashtable = new Hashtable();
      LinkedHashMap linkedhashmap = allForms(hashtable);
      SchematicRule[] aschematicrule = (SchematicRule[])linkedhashmap.values().toArray(new SchematicRule[0]);

      for (int j = 0; j < aschematicrule.length; j++) {
         SchematicRule schematicrule = aschematicrule[j];
         int k = schematicrule.premises.length;
         if (k <= i && schematicrule.conclusion != null) {
            Vector vector2 = matchForm(derivationlinechecker, schematicrule);
            derivationlinechecker.pruneDegenerateMatches(vector2);
            Vector vector3 = new Vector();
            Vector vector4 = new Vector();

            for (int l = 0; l < vector2.size(); l++) {
               RuleApplication ruleapplication = (RuleApplication)vector2.elementAt(l);
               Vector vector5 = derivationlinechecker.occurrenceChoices(ruleapplication);

               for (int i1 = 0; i1 < vector5.size(); i1++) {
                  DerivationRulesView.Applicable applicable = describe(derivationlinechecker, ruleapplication, (SchemeInstantiation)vector5.elementAt(i1));
                  if (applicable != null && !vector4.contains(applicable.result)) {
                     vector4.addElement(applicable.result);
                     for (int j1 = 0; j1 < k; j1++) {
                        applicable.uses.addElement(vector.elementAt(i - k + j1));
                     }

                     vector3.addElement(applicable);
                  }
               }
            }

            String s = ruleLock(lpderivation, schematicrule);
            Vector vector6 = (Vector)hashtable.get(schematicrule.name);

            for (int k1 = 0; k1 < vector3.size(); k1++) {
               DerivationRulesView.Applicable applicable1 = (DerivationRulesView.Applicable)vector3.elementAt(k1);
               applicable1.group = k == 0 ? 2 : 1;
               applicable1.lock = s;
               applicable1.families = vector6;
               String s1 = vector3.size() == 1 && applicable1.unknowns == 0 ? schematicrule.name : schematicrule.name + "[" + typedText(applicable1.result) + "]";
               applicable1.command = s1;
               vector1.addElement(applicable1);
            }
         }
      }
   }

   /**
    * The rule forms in the order of the rules list (then the user's rules), each once; families
    * gets, for each form, the names of the compound rules that include it.
    */
   static LinkedHashMap allForms(Hashtable hashtable) {
      LinkedHashMap linkedhashmap = new LinkedHashMap();
      RuleTable[] aruletable = new RuleTable[]{LogicProgram.ruleTable, LPDerivation.userRules};

      for (int i = 0; i < aruletable.length; i++) {
         RuleTable ruletable = aruletable[i];
         int j = ruletable == null ? 0 : ruletable.ruleNames.size();

         for (int k = 0; k < j; k++) {
            Rule rule = ruletable.getRule((String)ruletable.ruleNames.elementAt(k));
            if (rule != null) {
               SchematicRule[] aschematicrule = rule.getAllForms();

               for (int l = 0; l < aschematicrule.length; l++) {
                  SchematicRule schematicrule = aschematicrule[l];
                  if (!linkedhashmap.containsKey(schematicrule.name)) {
                     linkedhashmap.put(schematicrule.name, schematicrule);
                  }

                  if (!(rule instanceof SchematicRule)) {
                     Vector vector = (Vector)hashtable.get(schematicrule.name);
                     if (vector == null) {
                        hashtable.put(schematicrule.name, vector = new Vector());
                     }

                     if (!vector.contains(rule.name)) {
                        vector.addElement(rule.name);
                     }
                  }
               }
            }
         }
      }

      return linkedhashmap;
   }

   /**
    * The ways the premises of schematicrule match the top of the stack, in any order, as the
    * next step (not the last one): what DerivationLineChecker.matchRule considers when no
    * result is asserted, before it asks which one to use.
    */
   static Vector matchForm(DerivationLineChecker derivationlinechecker, SchematicRule schematicrule) {
      Vector vector = new Vector();
      int i = schematicrule.premises.length;
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      schematicrule.conclusion.match(null, schemeinstantiation);
      PermutationIterator permutationiterator = new PermutationIterator(i);

      do {
         int[] aint = permutationiterator.current();
         SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
         boolean flag = true;

         for (int j = 0; j < i && flag; j++) {
            SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation();
            flag = schematicrule.premises[aint[j]].match(derivationlinechecker.getStackFormula(j - i), schemeinstantiation2)
               && schemeinstantiation1.mergeFrom(schemeinstantiation2);
         }

         BoundVariableMap boundvariablemap = new BoundVariableMap();
         if (flag && schemeinstantiation1.hasNoDeferredMatches()) {
            flag = derivationlinechecker.premisesMatch(schematicrule, aint, schemeinstantiation1, boundvariablemap);
         }

         if (flag && schemeinstantiation1.mergeFrom(schemeinstantiation)) {
            vector.addElement(new RuleApplication(schematicrule, aint, schemeinstantiation1, boundvariablemap));
         }
      } while (permutationiterator.next());

      return vector;
   }

   /**
    * What the rule form of ruleapplication gives with schemeinstantiation: the conclusion, with
    * the letters the premises leave open marked as unknowns. Null if it cannot be formed (its
    * variables would be captured).
    */
   static DerivationRulesView.Applicable describe(DerivationLineChecker derivationlinechecker, RuleApplication ruleapplication, SchemeInstantiation schemeinstantiation) {
      SchematicRule schematicrule = ruleapplication.form;
      BoundVariableMap boundvariablemap = new BoundVariableMap();
      if (!schemeinstantiation.hasNoDeferredMatches()
         || !derivationlinechecker.premisesMatch(schematicrule, ruleapplication.premiseOrder, schemeinstantiation, boundvariablemap)) {
         boundvariablemap = (BoundVariableMap)ruleapplication.boundVariables.clone();
      }

      Expression expression = schematicrule.conclusion.copy();
      Hashtable hashtable = new Hashtable();
      int[] aint = new int[5];
      Vector vector = new Vector();
      mark(expression, schemeinstantiation, hashtable, aint, vector);
      BinderMap bindermap = new BinderMap();
      Expression expression1 = expression.instantiate((SchemeInstantiation)schemeinstantiation.clone(), bindermap);
      if (!boundvariablemap.renameBinders(expression, expression1, bindermap, null)) {
         return null;
      } else {
         String s = expression1.toString();
         int i = 0;

         while (s.indexOf(SchematicLetter.placeholder(i)) != -1) {
            String s1 = "?" + markName(4, aint[4]++);
            s = s.replace(SchematicLetter.placeholder(i), s1);
            vector.addElement(s1 + ": a variable you choose (the program asks for it)");
            i++;
         }

         s = spaceMarks(s);
         DerivationRulesView.Applicable applicable = new DerivationRulesView.Applicable(schematicrule.name, s, hashtable.size() + i);
         applicable.value = applicable.unknowns == 0 ? expression1 : null;
         applicable.form = LogicProgram.translateSymbols(schematicrule.format(", ", " ∴ "));
         applicable.notes.addAll(vector);
         if (schematicrule.name.equals("EI") && applicable.unknowns > 0) {
            applicable.notes.addElement("EI: the variable must not occur earlier in the derivation.");
         }

         return applicable;
      }
   }

   /**
    * Renames the letters of expression (a copy of a conclusion) that schemeinstantiation leaves
    * open to unknowns, the same letter to the same unknown; vector gets their descriptions.
    */
   static void mark(Expression expression, SchemeInstantiation schemeinstantiation, Hashtable hashtable, int[] aint, Vector vector) {
      SchematicLetter schematicletter = null;
      if (expression instanceof AtomicFormula || expression instanceof OperationTerm || expression instanceof SimpleTerm && !((SimpleTerm)expression).hasBinder()) {
         schematicletter = expression.getSchematicLetter();
      }

      if (schematicletter != null && schemeinstantiation.getReplacement(schematicletter) == null) {
         String s = (String)hashtable.get(schematicletter);
         if (s == null) {
            int i = schematicletter instanceof PredicateLetter
               ? (schematicletter.getArity() == 0 ? 0 : 1)
               : (schematicletter instanceof OperationLetter ? (schematicletter.getArity() == 0 ? 2 : 3) : 4);
            s = "?" + markName(i, aint[i]++);
            hashtable.put(schematicletter, s);
            String[] astring = new String[]{
               ": a formula you choose",
               "(..): a formula you choose, with the arguments shown in it",
               ": a term you choose",
               "(..): a term you choose, with the arguments shown in it",
               ": a variable you choose"
            };
            vector.addElement(s + astring[i] + " (the program asks for it)");
         }

         expression.symbol = s;
      }

      for (int j = 0; j < expression.childCount; j++) {
         mark(expression.getChild(j), schemeinstantiation, hashtable, aint, vector);
      }
   }

   /** The n-th name for an unknown of the given kind: formula, formula with arguments, term, term with arguments, variable. */
   static String markName(int i, int j) {
      String[] astring = new String[]{"PQRSTUVW", "FGHJK", "tuvw", "fgh", "xyzw"};
      String s = astring[i];
      return j < s.length() ? s.substring(j, j + 1) : s.substring(0, 1) + (j - s.length() + 2);
   }

   /** Why the problem does not allow schematicrule here, or null. */
   static String ruleLock(LPDerivation lpderivation, Rule rule) {
      if (lpderivation.hasProperty(rule, "disabled")) {
         return lpderivation.isWeaklyDisabled(rule) ? "disabled: this problem is its own proof" : "disabled for this problem";
      } else if (lpderivation.commandMode && lpderivation.hasProperty(rule, "manual")) {
         return "not in command mode: type the line's formula yourself";
      } else if (!rule.isProven(lpderivation)) {
         Vector vector = rule instanceof SchematicRule ? ((SchematicRule)rule).getProofProblems(lpderivation) : null;
         String s = "";

         for (int i = 0; vector != null && i < vector.size(); i++) {
            s = s + (i == 0 ? "" : " or ") + vector.elementAt(i);
         }

         return s.equals("") ? "derived rule, not yet proved" : "derived rule: prove " + s + " first";
      } else {
         return null;
      }
   }

   static String ruleLock(LPDerivation lpderivation, String s) {
      Rule rule = LPDerivation.getRule(s);
      return rule == null ? null : ruleLock(lpderivation, rule);
   }

   /** PR1, PR2, ...: each premise of the problem can be pushed. */
   static void addPremises(DerivationLineChecker derivationlinechecker, Vector vector) {
      LPDerivation lpderivation = derivationlinechecker.line.box.module;
      Expression[] aexpression = lpderivation.premises;
      int i = aexpression == null ? 0 : aexpression.length;
      if (i != 0 && lpderivation.problem.showLine.syntaxOk) {
         for (int j = 0; j < i; j++) {
            DerivationRulesView.Applicable applicable = new DerivationRulesView.Applicable(i == 1 ? "PR" : "PR" + (j + 1), aexpression[j].toString(), 0);
            applicable.group = 2;
            applicable.value = aexpression[j];
            applicable.form = "pushes premise " + (j + 1) + " of the argument";
            applicable.command = applicable.rule;
            vector.addElement(applicable);
         }
      }
   }

   /** ASS CD, ASS ID, ASS BDL and ASS BDR, on the first line of a box before any step. */
   static void addAssumptions(DerivationLineChecker derivationlinechecker, Vector vector) {
      DerivationLine derivationline = derivationlinechecker.line;
      Expression expression = derivationline.box.getFormula();
      if (derivationline.getIndexInBox() == 1 && derivationline.box.showLine != derivationline && expression != null) {
         LPDerivation lpderivation = derivationline.box.module;
         String s = expression.getSymbol();
         if (s.equals("->")) {
            vector.addElement(assumption(lpderivation, "ASS CD", expression.getChild(0), 2, "assumes the antecedent of the Show line", false));
         }

         if (s.equals("~")) {
            vector.addElement(assumption(lpderivation, "ASS ID", expression.getChild(0), 1, "assumes the unnegated Show line", true));
            vector.addElement(assumption(lpderivation, "ASS ID", expression.negate(), 1, "assumes the negation of the Show line", true));
         } else {
            vector.addElement(assumption(lpderivation, "ASS ID", expression.negate(), 1, "assumes the negation of the Show line", false));
         }

         if (s.equals("<->")) {
            vector.addElement(assumption(lpderivation, "ASS BDL", expression.getChild(0), 3, "assumes the left side of the Show line", false));
            vector.addElement(assumption(lpderivation, "ASS BDR", expression.getChild(1), 3, "assumes the right side of the Show line", false));
         }
      }
   }

   static DerivationRulesView.Applicable assumption(LPDerivation lpderivation, String s, Expression expression, int i, String s1, boolean flag) {
      DerivationRulesView.Applicable applicable = new DerivationRulesView.Applicable(s, expression.toString(), 0);
      applicable.group = 2;
      applicable.value = expression;
      applicable.form = s1;
      applicable.command = flag ? s + "[" + typedText(expression.toString()) + "]" : s;
      String[] astring = i == 3 ? new String[]{"BD"} : new String[]{"CD", "ID", "DD"};
      boolean flag1 = false;

      for (int j = 0; j < astring.length && !flag1; j++) {
         flag1 = lpderivation.checkDerivationRule(astring[j] + "/" + ASS_STR[i], false) == null;
      }

      if (!flag1) {
         applicable.lock = "no rule may close a box opened " + assumptionName(i) + " in this problem";
      }

      return applicable;
   }

   static class Result {
      Vector rules = new Vector();
      String error;
      String closed;
   }

   /** A rule that applies at the cursor, with one of its results. */
   static class Applicable {
      String rule;
      /** The result as a formula in the program's notation, with unknowns; or a description. */
      String result;
      boolean formula = true;
      /** The result, when it is a formula with no unknowns. */
      Expression value;
      int unknowns;
      /** Why the problem does not allow it, or null. */
      String lock;
      /** 0 closes the box, 1 uses the stack, 2 pushes a formula, 3 rearranges the stack. */
      int group = 1;
      int order;
      String form;
      Vector families;
      Vector uses = new Vector();
      String command;
      Vector notes = new Vector();
      boolean matchesLine;

      Applicable(String s, String s1, int i) {
         this.rule = s;
         this.result = s1;
         this.unknowns = i;
      }

      int rank() {
         return (this.lock == null ? 0 : 8) + (this.unknowns == 0 ? 0 : 4) + (this.group == 3 ? 3 : this.group);
      }

      String signature() {
         return this.rule + "\u0001" + this.result + "\u0001" + this.lock + "\u0001" + this.matchesLine + "\u0001" + this.details();
      }

      String details() {
         StringBuffer stringbuffer = new StringBuffer();
         stringbuffer.append("<b>").append(escape(this.rule)).append("</b>");
         if (this.families != null && !this.families.isEmpty()) {
            stringbuffer.append(" &nbsp;<i>(part of ");

            for (int i = 0; i < this.families.size(); i++) {
               stringbuffer.append(i == 0 ? "" : ", ").append(escape((String)this.families.elementAt(i)));
            }

            stringbuffer.append(")</i>");
         }

         if (this.form != null) {
            stringbuffer.append("<br>Rule: ").append(escape(this.form));
         }

         for (int j = 0; j < this.uses.size(); j++) {
            stringbuffer.append(j == 0 ? "<br>Uses: " : "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(escape((String)this.uses.elementAt(j)));
         }

         if (this.formula) {
            stringbuffer.append("<br>Gives: ").append(formulaHtml(this.result, false));
         } else if (!this.result.equals("")) {
            stringbuffer.append("<br>").append(escape(this.result.substring(0, 1).toUpperCase() + this.result.substring(1)));
         }

         if (this.matchesLine) {
            stringbuffer.append("<br><font color=\"#007800\">This is the line's formula.</font>");
         }

         if (this.command != null) {
            stringbuffer.append("<br>Type: <b>").append(escape(this.command)).append("</b>");
            if (this.unknowns > 0 && this.command.indexOf('[') != -1) {
               stringbuffer.append(" (with the unknowns filled in), or ").append(escape(this.rule)).append(" to be asked");
            }
         }

         for (int k = 0; k < this.notes.size(); k++) {
            stringbuffer.append("<br><font color=\"#606060\">").append(escape((String)this.notes.elementAt(k))).append("</font>");
         }

         if (this.lock != null) {
            stringbuffer.append("<br><font color=\"#a00000\">Not allowed here: ").append(escape(this.lock)).append("</font>");
         }

         return stringbuffer.toString();
      }
   }
}
