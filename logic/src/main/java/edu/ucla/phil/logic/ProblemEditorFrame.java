package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.JScrollPane;

class ProblemEditorFrame extends LogicModule {
   int moduleIndex;
   ProblemSet workProblems;
   ProblemSet exerciseProblems;
   ProblemListView workList;
   ProblemListView exerciseList;

   ProblemEditorFrame(int i, boolean flag) {
      super(flag);
      this.moduleIndex = i;
      this.titlePanel = null;
      this.setLayout(new BorderLayout());
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new FixedColumnLayout(3));
      SizedPanel sizedpanel1 = new SizedPanel(300, 200);
      SizedPanel sizedpanel2 = new SizedPanel(150, 200);
      SizedPanel sizedpanel3 = new SizedPanel(300, 200);
      JScrollPane jscrollpane = new JScrollPane();
      JScrollPane jscrollpane1 = new JScrollPane();
      ProblemSet problemset = getExercises(i, false, false, false);
      this.workProblems = getWork(i);
      this.workProblems.plainColors = true;
      this.workList = this.workProblems.createListView(null, problemset, true, true, null, null);
      this.exerciseProblems = getExercises(i, true, false, true);
      this.exerciseProblems.plainColors = true;
      this.exerciseList = this.exerciseProblems.createListView(null, null, true, true, null, null);
      jscrollpane.setViewportView(this.workList);
      jscrollpane1.setViewportView(this.exerciseList);
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

   static ProblemEditorFrame openEditor(int i, Rectangle rectangle) {
      ProblemEditorFrame problemeditorframe;
      if ((problemeditorframe = problemEditors[i]) != null) {
         problemeditorframe.requestFocus();
         return problemeditorframe;
      } else {
         problemeditorframe = new ProblemEditorFrame(i, false);
         problemeditorframe.createEditorFrame(LPInfo.programName + ": Problem Editor");
         problemeditorframe.finishLayout();
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
      if (!flag && !ProblemEditorChecks.confirmClose(this, null)) {
         return false;
      } else {
         Vector vector = (Vector)getStaticField(this.moduleIndex, "instances");
         if (vector != null) {
            vector.removeElement(this);
         }

         problemEditors[this.moduleIndex] = null;
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

   void createEditorFrame(String s) {
      this.frame = new ModuleFrame(s);
      this.setModuleColors(this.colors);
      this.frame.add(this, "Center");
      this.frame.module = this;
   }

   void finishLayout() {
      this.frame.pack();
      this.workList.setEditorFrame(this);
      this.exerciseList.setEditorFrame(this);
   }

   void setModuleColors(Color[] acolor) {
      this.colors = acolor;
   }

   void transferProblems(ProblemSet problemset, ProblemSet problemset1) {
   }
}
