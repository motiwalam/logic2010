package edu.ucla.phil.logic;

import javax.swing.JPasswordField;

class PasswordInputField extends JPasswordField {
   PasswordInputField(int i) {
      super(i);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 0));
   }
}
