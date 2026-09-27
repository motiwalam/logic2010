package edu.ucla.phil.logic;

import javax.swing.JButton;

class ExpressionButton extends JButton implements LogicConstants {
   Expression expression;
   static String[] SYMBOLS = LogicProgram.symbols;

   ExpressionButton(Expression expressionx) {
      super(LogicProgram.translateSymbols(expressionx.toString(), maggie, SYMBOLS));
      this.expression = expressionx;
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
   }
}
