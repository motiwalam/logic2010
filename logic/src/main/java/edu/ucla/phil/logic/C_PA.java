package edu.ucla.phil.logic;

import javax.swing.JButton;

class C_PA extends JButton implements LogicConstants {
   Expression f670;
   static String[] f671 = LogicProgram.symbols;

   C_PA(Expression expression) {
      super(LogicProgram.m995(expression.toString(), maggie, f671));
      this.f670 = expression;
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
   }
}
