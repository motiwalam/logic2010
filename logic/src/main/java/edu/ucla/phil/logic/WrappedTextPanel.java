package edu.ucla.phil.logic;

import java.awt.BorderLayout;

class WrappedTextPanel extends SizedPanel {
   MessageTextArea textArea;

   WrappedTextPanel(String s) {
      this.setLayout(new BorderLayout());
      this.textArea = new MessageTextArea(s);
      this.textArea.setLineWrap(true);
      this.textArea.setWrapStyleWord(true);
      this.textArea.setBackground(LogicProgram.printColors[1]);
      this.setBackground(LogicProgram.printColors[1]);
      this.add(this.textArea, "North");
   }
}
