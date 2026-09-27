package edu.ucla.phil.logic;

import java.awt.Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

abstract class TermOccurrenceSelector extends FormulaEntryField {
   int placeholderCount;
   Vector undoStack;
   Term[] selectedTerms;
   int[] selectionCounts;
   Vector listeners;
   Hashtable messageParams;
   static String[] SYMBOLS = LogicProgram.symbols;

   TermOccurrenceSelector(Expression expression, int i, Frame frame, int j) {
      this(expression, i, frame, j, new Hashtable());
   }

   TermOccurrenceSelector(Expression expression, int i, Frame frame, int j, Hashtable hashtable) {
      super(LogicProgram.translateSymbols(expression.format(true, 1), maggie, SYMBOLS), i, frame);
      this.placeholderCount = j;
      this.undoStack = new Vector();
      this.selectedTerms = new Term[j];
      this.selectionCounts = new int[j];
      this.listeners = new Vector();
      this.messageParams = hashtable;
      this.addKeyListener(this);
      this.setEditable(false);
      this.getCaret().setVisible(true);
      this.setFocusable(true);
      this.addFocusListener(new FocusAdapter() {
         public void onFocusGained(FocusEvent focusevent) {
            TermOccurrenceSelector.this.getCaret().setVisible(true);
         }
      });
   }

   @Override
   void insertAtCaret(String s) {
      if (!this.isEditable()) {
         int i = 0;

         while (i < this.placeholderCount && !SchematicLetter.placeholder(i).equals(s)) {
            i++;
         }

         if (i == this.placeholderCount) {
            return;
         }

         String s1 = this.getText();
         Hashtable hashtable = Message.mergeParams(this.messageParams, null);
         Message.putParam(hashtable, "full text", LogicProgram.escapeBackslashes(s1));
         String s2 = this.getSelectedText();
         if (s2.length() == 0) {
            this.reportNothingSelected(hashtable);
            this.requestFocus();
            return;
         }

         Message.putParam(hashtable, "selection", LogicProgram.escapeBackslashes(s2));
         int[] aint = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
         String s3 = LogicProgram.translateSymbols(s1, SYMBOLS, maggie, aint);
         String s4 = s3.substring(0, aint[0]) + s + s3.substring(aint[1]);
         Message.putParam(hashtable, "subbed text", "\\l" + s4 + "\\l");

         Expression expression;
         try {
            expression = LogicProgram.parseFormula(LogicProgram.translateSymbols(s2, SYMBOLS, maggie), true, false);
         } catch (FormulaParseException formulaparseexception2) {
            this.reportNotWellFormed(hashtable);
            this.requestFocus();
            return;
         }

         if (!(expression instanceof Term)) {
            this.reportNotATerm(hashtable);
            this.requestFocus();
            return;
         }

         try {
            LogicProgram.parseFormula(s4, true, true);
         } catch (FormulaParseException formulaparseexception1) {
            this.reportNotStandaloneTerm(hashtable);
            this.requestFocus();
            return;
         }

         Expression expression1;
         try {
            expression1 = LogicProgram.parseFormula(s3, true, true);
         } catch (FormulaParseException formulaparseexception) {
            expression1 = null;
         }

         FormulaParseNode formulaparsenode = new FormulaParseNode(expression1);
         formulaparsenode.text = s3;
         Expression expression2 = formulaparsenode.findNodeContaining(aint[0], aint[1]).getExpression();
         Vector vector = expression2.findMislinkedVariables();
         if (vector != null) {
            this.reportBoundOccurrence(vector, hashtable);
            this.requestFocus();
            return;
         }

         if (this.selectionCounts[i] != 0) {
            if (!expression.isIdentical(this.selectedTerms[i])) {
               this.reportDifferentTerm(Message.putParam(hashtable, "old term", "\\l" + this.selectedTerms[i] + "\\l"));
               this.requestFocus();
               return;
            }
         } else {
            Enumeration enumeration = this.listeners.elements();

            while (enumeration.hasMoreElements()) {
               SubstitutionListener substitutionlistener = (SubstitutionListener)enumeration.nextElement();
               ErrorRef errorref = substitutionlistener.validatePlaceholderValue(i, expression);
               if (errorref != null) {
                  DerivationDialogs.showMessage(errorref.id, Message.mergeParams(hashtable, errorref.params));
                  this.requestFocus();
                  return;
               }
            }

            enumeration = this.listeners.elements();

            while (enumeration.hasMoreElements()) {
               SubstitutionListener substitutionlistener1 = (SubstitutionListener)enumeration.nextElement();
               substitutionlistener1.setPlaceholderValue(i, expression.toString());
            }

            this.selectedTerms[i] = (Term)expression;
         }

         this.selectionCounts[i]++;
         this.undoStack.addElement(new PlaceholderEdit(i, this.getSelectionStart(), s2));
      }

      this.insertAtCaret(s, true);
   }

   void reportNothingSelected(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot013", hashtable);
   }

   abstract void reportNotWellFormed(Hashtable hashtable);

   abstract void reportNotATerm(Hashtable hashtable);

   abstract void reportNotStandaloneTerm(Hashtable hashtable);

   abstract void reportBoundOccurrence(Vector vector, Hashtable hashtable);

   abstract void reportDifferentTerm(Hashtable hashtable);

   @Override
   public boolean undo() {
      if (this.isEditable()) {
         super.undo();
      }

      int i = this.undoStack.size();
      if (i == 0) {
         return false;
      } else {
         PlaceholderEdit placeholderedit = (PlaceholderEdit)this.undoStack.elementAt(i - 1);
         this.undoStack.setSize(i - 1);
         int j = placeholderedit.placeholderIndex;
         int k = placeholderedit.position;
         String s = placeholderedit.replacedText;
         if (--this.selectionCounts[j] == 0) {
            Enumeration enumeration = this.listeners.elements();

            while (enumeration.hasMoreElements()) {
               SubstitutionListener substitutionlistener = (SubstitutionListener)enumeration.nextElement();
               substitutionlistener.setPlaceholderValue(j, null);
            }

            this.selectedTerms[j] = null;
         }

         this.replaceRange(null, k, k + SchematicLetter.placeholder(j).length());
         this.insertText(s, k);
         this.select(k, k + s.length());
         return true;
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if ((keyevent.getModifiers() & 1) == 0 && keyevent.getKeyChar() == 26) {
         keyevent.consume();
         if (!this.undo()) {
            DerivationDialogs.showMessage("dernot012");
            this.requestFocus();
         }
      }

      super.keyTyped(keyevent);
   }

   @Override
   void showKeypad() {
   }

   void addSubstitutionListener(SubstitutionListener substitutionlistener) {
      this.listeners.addElement(substitutionlistener);
   }

   void removeSubstitutionListener(SubstitutionListener substitutionlistener) {
      this.listeners.removeElement(substitutionlistener);
   }
}
