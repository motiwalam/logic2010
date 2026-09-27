package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

class NodeMessageHandler extends DialogHandler implements SymbolizationConstants {
   static String[] displaySymbols = LogicProgram.symbols;

   NodeMessageHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      if (s == null) {
         return true;
      } else if (!s.equalsIgnoreCase("up")) {
         if (s.equalsIgnoreCase("ok")) {
            SymbolizationNode symbolizationnode5 = (SymbolizationNode)this.getProperty("target");
            symbolizationnode5.textPanel.textPane.requestFocus();
            return true;
         } else if (s.equalsIgnoreCase("text")) {
            SymbolizationNode symbolizationnode4 = (SymbolizationNode)this.getProperty("target");
            SymbolizationNode symbolizationnode8 = (SymbolizationNode)this.getProperty("answer");
            Vector vector3 = (Vector)this.getProperty("targetBinders");
            Vector vector5 = (Vector)this.getProperty("answerBinders");
            if (!symbolizationnode4.textPanel.textPane.isEditable()) {
               Toolkit.getDefaultToolkit().beep();
            } else {
               symbolizationnode4.textPanel
                  .textPane
                  .setText(SymbolizationErrorButton.substituteVariables(symbolizationnode8.textPanel.textPane.getText(), vector5, vector3));
            }

            return false;
         } else if (s.equalsIgnoreCase("symb")) {
            SymbolizationNode symbolizationnode3 = (SymbolizationNode)this.getProperty("target");
            SymbolizationNode symbolizationnode7 = (SymbolizationNode)this.getProperty("answer");
            Vector vector2 = (Vector)this.getProperty("targetBinders");
            Vector vector4 = (Vector)this.getProperty("answerBinders");
            String s2 = symbolizationnode7.connective == 11 ? symbolizationnode7.getConnectivePanel().labelComponent.getText() : null;
            s2 = DelimitedTokenizer.escape(
               SymbolizationNode.translateExpression(LogicProgram.translateSymbols(s2, displaySymbols, maggie), vector4, vector2), "\\{"
            );
            symbolizationnode3.setConnective(symbolizationnode7.connective, s2, true);
            return false;
         } else {
            return true;
         }
      } else {
         SymbolizationNode symbolizationnode = (SymbolizationNode)this.getProperty("target");
         SymbolizationNode symbolizationnode1 = (SymbolizationNode)this.getProperty("answer");
         Vector vector = (Vector)this.getProperty("targetBinders");
         Vector vector1 = (Vector)this.getProperty("answerBinders");
         SymbolizationNode symbolizationnode2 = symbolizationnode.getParentNode();
         SymbolizationNode symbolizationnode6 = symbolizationnode1.getParentNode();
         if (symbolizationnode2 != null && symbolizationnode6 != null) {
            this.setProperty("target", symbolizationnode2);
            this.setProperty("answer", symbolizationnode6);
            if (symbolizationnode2.isBinder()) {
               vector.setSize(vector.size() - 1);
            }

            if (symbolizationnode6.isBinder()) {
               vector1.setSize(vector1.size() - 1);
            }

            Message message = SymbolizationMessages.get("symnot001");
            Hashtable hashtable = SymbolizationHint.hintParams(symbolizationnode6, vector, vector1);
            String s1 = LogicProgram.expandEscapes(Message.substitute(message.text, hashtable));
            if (messagedialog.content instanceof JScrollPane) {
               JViewport jviewport = ((JScrollPane)messagedialog.content).getViewport();
               if (jviewport != null) {
                  Component component = jviewport.getView();
                  if (component instanceof FormulaTextPane) {
                     ((FormulaTextPane)component).setText(s1);
                  }
               }
            }

            Rectangle rectangle = LogicProgram.boundsRelativeTo(symbolizationnode2.textPanel.textPane, null);
            Dimension dimension = messagedialog.getSize();
            Point point = new Point(rectangle.x + rectangle.width / 2 - dimension.width / 2, rectangle.y + rectangle.height);
            messagedialog.setLocation(point);
            messagedialog.setResizable(true);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }

         return false;
      }
   }
}
