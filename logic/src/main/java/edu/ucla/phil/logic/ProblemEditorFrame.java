package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.JScrollPane;

class ProblemEditorFrame extends LogicModule {
   int f1036;
   ProblemSet f1037;
   ProblemSet f1038;
   ProblemListView f1039;
   ProblemListView f1040;

   ProblemEditorFrame(int i, boolean flag) {
      super(flag);
      this.f1036 = i;
      this.titlePanel = null;
      this.setLayout(new BorderLayout());
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new C_u_(3));
      SizedPanel sizedpanel1 = new SizedPanel(300, 200);
      SizedPanel sizedpanel2 = new SizedPanel(150, 200);
      SizedPanel sizedpanel3 = new SizedPanel(300, 200);
      JScrollPane jscrollpane = new JScrollPane();
      JScrollPane jscrollpane1 = new JScrollPane();
      ProblemSet problemset = getExercises(i, false, false, false);
      this.f1037 = getWork(i);
      this.f1037.f1081 = true;
      this.f1039 = this.f1037.m1781(null, problemset, true, true, null, null);
      this.f1038 = getExercises(i, true, false, true);
      this.f1038.f1081 = true;
      this.f1040 = this.f1038.m1781(null, null, true, true, null, null);
      jscrollpane.setViewportView(this.f1039);
      jscrollpane1.setViewportView(this.f1040);
      sizedpanel1.add(jscrollpane, "Center");
      sizedpanel3.add(jscrollpane1, "Center");
      sizedpanel.add(sizedpanel1, "West");
      sizedpanel.add(sizedpanel2, "Center");
      sizedpanel.add(sizedpanel3, "East");
      this.add(sizedpanel, "Center");
   }

   @Override
   int getModuleIndex() {
      return -1;
   }

   @Override
   int getProblemState(TaggedRecord taggedrecord) {
      System.out.println("LPProblemEdit.getProblemState(LPTagReader reader) should never be called!");
      return 4;
   }

   static ProblemEditorFrame m1674(int i, Rectangle rectangle) {
      ProblemEditorFrame problemeditorframe;
      if ((problemeditorframe = problemEditors[i]) != null) {
         problemeditorframe.requestFocus();
         return problemeditorframe;
      } else {
         problemeditorframe = new ProblemEditorFrame(i, false);
         problemeditorframe.m1675(LPInfo.programName + ": Problem Editor");
         problemeditorframe.m1676();
         problemeditorframe.frame.setBounds(rectangle);
         problemeditorframe.frame.setVisible(true);
         Vector vector = (Vector)getStaticField(i, "instances");
         if (vector != null) {
            vector.addElement(problemeditorframe);
         }

         problemEditors[i] = problemeditorframe;
         return problemeditorframe;
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
      this.frame = new ModuleFrame(s);
      this.m1677(this.colors);
      this.frame.add(this, "Center");
      this.frame.module = this;
   }

   void m1676() {
      this.frame.pack();
      this.f1039.m1529(this);
      this.f1040.m1529(this);
   }

   void m1677(Color[] acolor) {
      this.colors = acolor;
   }

   void m1678(ProblemSet problemset, ProblemSet problemset1) {
   }
}
