package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.JScrollPane;

class C_d_A extends C_U {
   int f1036;
   C_e_D f1037;
   C_e_D f1038;
   C_YA f1039;
   C_YA f1040;

   C_d_A(int i, boolean flag) {
      super(flag);
      this.f1036 = i;
      this.titlePanel = null;
      this.setLayout(new BorderLayout());
      C_LB c_lb = new C_LB();
      c_lb.setLayout(new C_u_(3));
      C_LB c_lb1 = new C_LB(300, 200);
      C_LB c_lb2 = new C_LB(150, 200);
      C_LB c_lb3 = new C_LB(300, 200);
      JScrollPane jscrollpane = new JScrollPane();
      JScrollPane jscrollpane1 = new JScrollPane();
      C_e_D c_e_d = getExercises(i, false, false, false);
      this.f1037 = getWork(i);
      this.f1037.f1081 = true;
      this.f1039 = this.f1037.m1781(null, c_e_d, true, true, null, null);
      this.f1038 = getExercises(i, true, false, true);
      this.f1038.f1081 = true;
      this.f1040 = this.f1038.m1781(null, null, true, true, null, null);
      jscrollpane.setViewportView(this.f1039);
      jscrollpane1.setViewportView(this.f1040);
      c_lb1.add(jscrollpane, "Center");
      c_lb3.add(jscrollpane1, "Center");
      c_lb.add(c_lb1, "West");
      c_lb.add(c_lb2, "Center");
      c_lb.add(c_lb3, "East");
      this.add(c_lb, "Center");
   }

   @Override
   int getModuleIndex() {
      return -1;
   }

   @Override
   int getProblemState(C_XD c_xd) {
      System.out.println("LPProblemEdit.getProblemState(LPTagReader reader) should never be called!");
      return 4;
   }

   static C_d_A m1674(int i, Rectangle rectangle) {
      C_d_A c_d_a;
      if ((c_d_a = problemEditors[i]) != null) {
         c_d_a.requestFocus();
         return c_d_a;
      } else {
         c_d_a = new C_d_A(i, false);
         c_d_a.m1675(LPInfo.programName + ": Problem Editor");
         c_d_a.m1676();
         c_d_a.frame.setBounds(rectangle);
         c_d_a.frame.setVisible(true);
         Vector vector = (Vector)getStaticField(i, "instances");
         if (vector != null) {
            vector.addElement(c_d_a);
         }

         problemEditors[i] = c_d_a;
         return c_d_a;
      }
   }

   @Override
   public boolean shutdown(boolean flag) {
      if (!flag && !edu.ucla.phil.logic.C__A.m1568(this, null)) {
         return false;
      } else {
         Vector vector = (Vector)getStaticField(this.f1036, "instances");
         if (vector != null) {
            vector.removeElement(this);
         }

         problemEditors[this.f1036] = null;
         return true;
      }
   }

   @Override
   public boolean save() {
      return true;
   }

   @Override
   public void resize() {
   }

   @Override
   public Frame getFrame() {
      return this.frame;
   }

   void m1675(String s) {
      this.frame = new C_0E(s);
      this.m1677(this.colors);
      this.frame.add(this, "Center");
      this.frame.f27 = this;
   }

   void m1676() {
      this.frame.pack();
      this.f1039.m1529(this);
      this.f1040.m1529(this);
   }

   void m1677(Color[] acolor) {
      this.colors = acolor;
   }

   void m1678(C_e_D c_e_d, C_e_D c_e_d1) {
   }
}
