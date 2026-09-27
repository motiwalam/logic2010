package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class AdvicePanel extends JPanel implements ComponentListener, ModuleComponentMarker {
   JScrollPane scrollPane;
   OutlineNode page;

   AdvicePanel(JScrollPane jscrollpane, OutlineNode outlinenode) {
      this.setLayout(new FlowLayout(0, 0, 0));
      this.setFont(LogicProgram.getFont(outlinenode.indent * 7 / 10));
      this.scrollPane = jscrollpane;
      this.page = outlinenode;
   }

   @Override
   public void componentHidden(ComponentEvent componentevent) {
   }

   @Override
   public void componentMoved(ComponentEvent componentevent) {
   }

   @Override
   public void componentResized(ComponentEvent componentevent) {
      this.fitToWidth();
   }

   @Override
   public void componentShown(ComponentEvent componentevent) {
      this.fitToWidth();
   }

   void fitToWidth() {
      if (this.page != null && this.scrollPane != null) {
         this.page.setWidth(LogicProgram.getInteriorBounds(this.scrollPane).width - 16 - this.page.indent / 4);
         this.invalidate();
      }
   }
}
