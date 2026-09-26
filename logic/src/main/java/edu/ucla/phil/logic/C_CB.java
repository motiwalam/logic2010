package edu.ucla.phil.logic;

import java.awt.Color;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class C_CB implements LogicConstants {
   static final char f252 = '̀';
   static final Color[] f253 = new Color[]{null, dialogRed, dialogGreen, dialogBlue, dialogOrange};
   int[] f254;
   Vector f255;

   C_CB(int[] aint, Vector vector) {
      this.f254 = aint;
      this.f255 = vector;
   }

   C_CB m415() {
      int i = this.m416();
      C_CB c_cb1 = new C_CB(new int[i], new Vector());
      if (i != 0) {
         System.arraycopy(this.f254, 0, c_cb1.f254, 0, i);
      }

      for (int j = 0; j < i; j++) {
         C_n_F c_n_f = (C_n_F)this.f255.elementAt(j);
         c_cb1.f255.addElement(c_n_f == null ? null : c_n_f.m1974(true));
      }

      return c_cb1;
   }

   int m416() {
      return Math.min(this.f254 == null ? 0 : this.f254.length, this.f255 == null ? 0 : this.f255.size());
   }

   static void m417(StyledDocument styleddocument, int i, C_n_F c_n_f) {
      SimpleAttributeSet simpleattributeset = new SimpleAttributeSet();
      simpleattributeset.addAttribute(StyleConstants.Foreground, f253[i]);
      int j = styleddocument == null ? 0 : styleddocument.getLength();
      if (j != 0 && c_n_f != null && !c_n_f.m1982()) {
         for (int k = 0; k < j; k++) {
            if (c_n_f.m1983(k)) {
               styleddocument.setCharacterAttributes(k, 1, simpleattributeset, false);
            }
         }
      }
   }

   StyledDocument m418(String s) {
      DefaultStyledDocument defaultstyleddocument = new DefaultStyledDocument();

      try {
         defaultstyleddocument.insertString(0, s, SimpleAttributeSet.EMPTY);
      } catch (BadLocationException badlocationexception) {
      }

      int i = this.m416();

      for (int j = 0; j < i; j++) {
         m417(defaultstyleddocument, this.f254[j], (C_n_F)this.f255.elementAt(j));
      }

      return defaultstyleddocument;
   }

   static StyledDocument m419(C_e_B c_e_b) {
      C_CB c_cb = m420(c_e_b.f1070);
      if (c_cb == null) {
         DefaultStyledDocument defaultstyleddocument = new DefaultStyledDocument();

         try {
            defaultstyleddocument.insertString(0, c_e_b.f1069, SimpleAttributeSet.EMPTY);
         } catch (BadLocationException badlocationexception) {
         }

         return defaultstyleddocument;
      } else {
         return c_cb.m418(c_e_b.f1069);
      }
   }

   static C_CB m420(Vector vector) {
      if (vector == null) {
         return null;
      } else {
         int i = vector.size();
         int[] aint = new int[i];

         for (int j = 0; j < i; j++) {
            aint[j] = 1;
         }

         return new C_CB(aint, vector);
      }
   }
}
