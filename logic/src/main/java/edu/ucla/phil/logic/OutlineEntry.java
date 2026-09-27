package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class OutlineEntry extends JPanel implements ModuleComponentMarker {
   LogicLabel titleLabel;
   JTextArea textArea;
   OutlineNode node;

   OutlineEntry(OutlineNode outlinenode) {
      this.node = outlinenode;
      this.titleLabel = new LogicLabel();
      this.textArea = new LogicTextArea();
      this.textArea.setEditable(false);
      this.textArea.setLineWrap(true);
      this.textArea.setWrapStyleWord(true);
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      jpanel.add(this.titleLabel, "North");
      JPanel jpanel1 = new JPanel();
      jpanel1.setLayout(outlinenode.columnLayout);
      jpanel1.add(this.textArea);
      this.add(jpanel);
      this.add(jpanel1);
      int i = outlinenode.indent / 4;
      int j = outlinenode.indent / 10;
      this.setLayout(new FlowLayout(0, i, j));
   }
}
