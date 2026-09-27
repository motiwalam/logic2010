package edu.ucla.phil.logic;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.text.StyledDocument;

class SchemeSubstitutionPanel extends LinePanel implements LogicConstants {
   JScrollPane scrollPane;
   String[] patternLabels;
   FormulaEntryField[] replacementFields;
   FormulaEntryField initialFocusField;
   String errorId = null;
   Hashtable errorParams = null;
   int assignedCount;
   int pendingCount;
   static String[] displaySymbols = LogicProgram.symbols;

   SchemeSubstitutionPanel(SchemeInstantiation schemeinstantiation, int i, Frame frame) {
      this(schemeinstantiation, i, frame, false);
   }

   SchemeSubstitutionPanel(SchemeInstantiation schemeinstantiation, int i, Frame frame, boolean flag) {
      this.assignedCount = schemeinstantiation.size();
      this.pendingCount = schemeinstantiation.pendingLetters.size();
      SizedPanel sizedpanel = new SizedPanel();
      FlexGridLayout flexgridlayout = new FlexGridLayout(2, this.assignedCount + this.pendingCount, 0, 0, true, true);
      flexgridlayout.setVgap(2);
      flexgridlayout.setHgap(2);
      sizedpanel.setLayout(flexgridlayout);
      this.patternLabels = new String[this.assignedCount + this.pendingCount];
      this.replacementFields = new FormulaEntryField[this.assignedCount + this.pendingCount];
      Enumeration enumeration = schemeinstantiation.keys();

      for (int j = 0; enumeration.hasMoreElements(); j++) {
         LetterReplacement letterreplacement = schemeinstantiation.getReplacement((SchematicLetter)enumeration.nextElement());
         this.patternLabels[j] = LogicProgram.translateSymbols(letterreplacement.pattern.toString(), maggie, displaySymbols);
         this.replacementFields[j] = new FormulaEntryField(
            LogicProgram.translateSymbols(letterreplacement.replacement.toString(), maggie, displaySymbols), i, frame
         );
         this.replacementFields[j].setName("Substitution for " + this.patternLabels[j]);
         LogicLabel logiclabel;
         sizedpanel.add(logiclabel = new LogicLabel(this.patternLabels[j]), new Point(0, j));
         logiclabel.setFocusable(false);
         sizedpanel.add(this.replacementFields[j], new Point(1, j));
         this.replacementFields[j].setEditable(false);
      }

      for (int l = this.assignedCount; l < this.assignedCount + this.pendingCount; l++) {
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.pendingLetters.elementAt(l - this.assignedCount);
         TextHighlighter texthighlighter = null;
         IntervalSet intervalset = null;
         if (flag) {
            intervalset = IntervalSet.range(0, schematicletter.getLetter().length());
            Vector vector = new Vector();

            for (int k = this.assignedCount; k < l; k++) {
               vector.addElement(null);
            }

            vector.addElement(intervalset);
            texthighlighter = TextHighlighter.fromRanges(vector);
         }

         String s = this.patternLabels[l] = LogicProgram.translateSymbols(schematicletter.toString(), maggie, displaySymbols, intervalset);
         if (flag) {
            StyledDocument styleddocument = texthighlighter.createDocument(s);
            EditableTextPane editabletextpane;
            sizedpanel.add(editabletextpane = new EditableTextPane(styleddocument, -1, -1, false), new Point(0, l));
            editabletextpane.setEditable(false);
            editabletextpane.setFocusable(false);
         } else {
            LogicLabel logiclabel1;
            sizedpanel.add(logiclabel1 = new LogicLabel(s), new Point(0, l));
            logiclabel1.setFocusable(false);
         }

         this.replacementFields[l] = new FormulaEntryField("", i, frame);
         this.replacementFields[l].setName("Substitution for " + schematicletter);
         sizedpanel.add(this.replacementFields[l], new Point(1, l));
      }

      this.initialFocusField = this.pendingCount == 0 ? null : this.replacementFields[this.assignedCount];
      this.add(this.scrollPane = new JScrollPane(sizedpanel, 22, 31));
   }

   void setMessageDialog(MessageDialog messagedialog) {
      int i = this.replacementFields.length;

      for (int j = 0; j < i; j++) {
         if (this.replacementFields[j] != null) {
            this.replacementFields[j].ownerDialog = messagedialog;
         }
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.validate();
      this.getParent().invalidate();
      this.setSizeLimit(this.getPreferredSize());
      this.scrollPane.getVerticalScrollBar().setUnitIncrement(this.getGraphics().getFontMetrics().getHeight());
   }

   @Override
   public boolean gotFocus(Event event, Object object) {
      boolean flag = super.gotFocus(event, object);
      if (this.initialFocusField != null) {
         this.initialFocusField.requestFocus();
         this.initialFocusField = null;
      }

      return flag;
   }

   SchemeInstantiation readInstantiation() {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      String s = null;

      for (int i = 0; i < this.patternLabels.length; i++) {
         Expression expression;
         Expression expression1;
         try {
            s = this.patternLabels[i];
            expression = LogicProgram.parseFormula(LogicProgram.translateSymbols(s, displaySymbols, maggie), true, true);
            s = this.replacementFields[i].getText();
            expression1 = LogicProgram.parseFormula(LogicProgram.translateSymbols(s, displaySymbols, maggie), true, true);
         } catch (FormulaParseException formulaparseexception) {
            this.errorId = "dererr059";
            this.errorParams = Message.params("parser error", s);
            return null;
         }

         if (expression1 == null) {
            schemeinstantiation.addPendingLetter(expression);
         } else {
            if (expression1.hasUndeclaredPlaceholder(expression)) {
               this.errorId = "dererr072";
               this.errorParams = Message.params("pattern", "\\l" + expression + "\\l", "replacement", "\\l" + expression1 + "\\l");
               return null;
            }

            if (!schemeinstantiation.addReplacement(expression, expression1)) {
               this.errorId = schemeinstantiation.errorId;
               this.errorParams = schemeinstantiation.errorParams;
               return null;
            }
         }
      }

      return schemeinstantiation;
   }

   EditableTextPane[] getPendingFields() {
      if (this.pendingCount == 0) {
         return null;
      } else {
         EditableTextPane[] aeditabletextpane = new EditableTextPane[this.pendingCount];

         for (int i = 0; i < this.pendingCount; i++) {
            aeditabletextpane[i] = this.replacementFields[this.assignedCount + i];
         }

         return aeditabletextpane;
      }
   }
}
