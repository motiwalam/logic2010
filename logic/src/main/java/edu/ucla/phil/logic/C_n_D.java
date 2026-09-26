package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JPanel;

class C_n_D extends C_M implements C_s_, LogicConstants {
   static String[] f1302 = LogicProgram.symbols;
   int f1303;
   Vector f1304;

   C_n_D(int i) {
      this(i, 1);
   }

   C_n_D(int i, int j) {
      super(i);
      this.f1303 = j;
      JPanel jpanel = new JPanel();
      this.setLayout(new FlowLayout(0, 0, 0));
      jpanel.setLayout(new C_QF(2, j, 0, 0, true, false));
      this.add(jpanel);
      this.f1304 = new Vector();

      for (int k = 0; k < j; k++) {
         C_ZE c_ze = new C_ZE("");
         this.f1304.addElement(c_ze);
         jpanel.add(new C_ZE(SchematicLetter.m1853(k) + ": "), new Point(0, k));
         jpanel.add(c_ze, new Point(1, k));
      }
   }

   @Override
   public void m1964(int i, String s) {
      C_ZE c_ze = (C_ZE)this.f1304.elementAt(i);
      c_ze.setText(s == null ? "" : LogicProgram.m995(s, maggie, f1302));
   }

   @Override
   public ErrorRef m1965(int i, Expression expression) {
      return null;
   }
}
