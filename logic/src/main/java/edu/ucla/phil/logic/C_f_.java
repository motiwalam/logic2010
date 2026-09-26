package edu.ucla.phil.logic;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.text.StyledDocument;

class C_f_ extends C_M implements LogicConstants {
   JScrollPane f1095;
   String[] f1096;
   FormulaEntryField[] f1097;
   FormulaEntryField f1098;
   String f1099 = null;
   Hashtable f1100 = null;
   int f1101;
   int f1102;
   static String[] f1103 = LogicProgram.symbols;

   C_f_(SchemeInstantiation schemeinstantiation, int i, Frame frame) {
      this(schemeinstantiation, i, frame, false);
   }

   C_f_(SchemeInstantiation schemeinstantiation, int i, Frame frame, boolean flag) {
      this.f1101 = schemeinstantiation.size();
      this.f1102 = schemeinstantiation.f1189.size();
      SizedPanel sizedpanel = new SizedPanel();
      C_QF c_qf = new C_QF(2, this.f1101 + this.f1102, 0, 0, true, true);
      c_qf.setVgap(2);
      c_qf.setHgap(2);
      sizedpanel.setLayout(c_qf);
      this.f1096 = new String[this.f1101 + this.f1102];
      this.f1097 = new FormulaEntryField[this.f1101 + this.f1102];
      Enumeration enumeration = schemeinstantiation.keys();

      for (int j = 0; enumeration.hasMoreElements(); j++) {
         LetterReplacement letterreplacement = schemeinstantiation.m1878((SchematicLetter)enumeration.nextElement());
         this.f1096[j] = LogicProgram.m995(letterreplacement.f367.toString(), maggie, f1103);
         this.f1097[j] = new FormulaEntryField(LogicProgram.m995(letterreplacement.f368.toString(), maggie, f1103), i, frame);
         this.f1097[j].setName("Substitution for " + this.f1096[j]);
         C_ZE c_ze;
         sizedpanel.add(c_ze = new C_ZE(this.f1096[j]), new Point(0, j));
         c_ze.setFocusable(false);
         sizedpanel.add(this.f1097[j], new Point(1, j));
         this.f1097[j].setEditable(false);
      }

      for (int l = this.f1101; l < this.f1101 + this.f1102; l++) {
         SchematicLetter schematicletter = (SchematicLetter)schemeinstantiation.f1189.elementAt(l - this.f1101);
         C_CB c_cb = null;
         C_n_F c_n_f = null;
         if (flag) {
            c_n_f = C_n_F.m1971(0, schematicletter.m1173().length());
            Vector vector = new Vector();

            for (int k = this.f1101; k < l; k++) {
               vector.addElement(null);
            }

            vector.addElement(c_n_f);
            c_cb = C_CB.m420(vector);
         }

         String s = this.f1096[l] = LogicProgram.m997(schematicletter.toString(), maggie, f1103, c_n_f);
         if (flag) {
            StyledDocument styleddocument = c_cb.m418(s);
            EditableTextPane editabletextpane;
            sizedpanel.add(editabletextpane = new EditableTextPane(styleddocument, -1, -1, false), new Point(0, l));
            editabletextpane.setEditable(false);
            editabletextpane.setFocusable(false);
         } else {
            C_ZE c_ze1;
            sizedpanel.add(c_ze1 = new C_ZE(s), new Point(0, l));
            c_ze1.setFocusable(false);
         }

         this.f1097[l] = new FormulaEntryField("", i, frame);
         this.f1097[l].setName("Substitution for " + schematicletter);
         sizedpanel.add(this.f1097[l], new Point(1, l));
      }

      this.f1098 = this.f1102 == 0 ? null : this.f1097[this.f1101];
      this.add(this.f1095 = new JScrollPane(sizedpanel, 22, 31));
   }

   void m1801(MessageDialog messagedialog) {
      int i = this.f1097.length;

      for (int j = 0; j < i; j++) {
         if (this.f1097[j] != null) {
            this.f1097[j].f425 = messagedialog;
         }
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.validate();
      this.getParent().invalidate();
      this.m936(this.getPreferredSize());
      this.f1095.getVerticalScrollBar().setUnitIncrement(this.getGraphics().getFontMetrics().getHeight());
   }

   @Override
   public boolean gotFocus(Event event, Object object) {
      boolean flag = super.gotFocus(event, object);
      if (this.f1098 != null) {
         this.f1098.requestFocus();
         this.f1098 = null;
      }

      return flag;
   }

   SchemeInstantiation m1802() {
      SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
      String s = null;

      for (int i = 0; i < this.f1096.length; i++) {
         Expression expression;
         Expression expression1;
         try {
            s = this.f1096[i];
            expression = LogicProgram.m1008(LogicProgram.m995(s, f1103, maggie), true, true);
            s = this.f1097[i].getText();
            expression1 = LogicProgram.m1008(LogicProgram.m995(s, f1103, maggie), true, true);
         } catch (FormulaParseException formulaparseexception) {
            this.f1099 = "dererr059";
            this.f1100 = Message.params("parser error", s);
            return null;
         }

         if (expression1 == null) {
            schemeinstantiation.m1885(expression);
         } else {
            if (expression1.m1265(expression)) {
               this.f1099 = "dererr072";
               this.f1100 = Message.params("pattern", "\\l" + expression + "\\l", "replacement", "\\l" + expression1 + "\\l");
               return null;
            }

            if (!schemeinstantiation.m1881(expression, expression1)) {
               this.f1099 = schemeinstantiation.f1190;
               this.f1100 = schemeinstantiation.f1191;
               return null;
            }
         }
      }

      return schemeinstantiation;
   }

   EditableTextPane[] m1803() {
      if (this.f1102 == 0) {
         return null;
      } else {
         EditableTextPane[] aeditabletextpane = new EditableTextPane[this.f1102];

         for (int i = 0; i < this.f1102; i++) {
            aeditabletextpane[i] = this.f1097[this.f1101 + i];
         }

         return aeditabletextpane;
      }
   }
}
