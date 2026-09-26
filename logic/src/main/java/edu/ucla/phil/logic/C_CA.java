package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.Vector;
import javax.swing.Box;

abstract class C_CA extends C_l_B implements Printable {
   int[] f249;
   C_l_B.C__A f250 = null;
   Component f251 = null;

   C_CA(C_c_C c_c_c, int[] aint) {
      super(c_c_c);
      this.f249 = aint;
      this.f251 = Box.createVerticalStrut(LogicProgram.f539);
   }

   @Override
   Printable m413() {
      return this;
   }

   @Override
   public int print(Graphics graphics, PageFormat pageformat, int i) {
      if (this.f250 == null) {
         Vector vector = this.m70(this.m1926(pageformat));
         if (vector == null) {
            return 1;
         }

         C_t_B c_t_b = null;
         if (LogicProgram.f533 != null) {
            c_t_b = C_t_B.m2084();
         }

         this.f250 = new C_l_B.C__A(pageformat, c_t_b, vector, this.f251);
      }

      return this.f250.m1927(graphics, pageformat, i);
   }

   abstract Vector m70(Dimension dimension);

   void m414(Component component) {
      this.f251 = component;
   }
}
