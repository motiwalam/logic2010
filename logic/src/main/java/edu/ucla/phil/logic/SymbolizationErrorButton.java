package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

class SymbolizationErrorButton extends WideMenuButton implements ActionListener, SymbolizationConstants {
   String messageId = null;
   String messageText = null;
   SymbolizationNode target;
   SymbolizationNode answer;
   Vector targetBinders;
   Vector answerBinders;
   NodeMessageHandler handler;

   SymbolizationErrorButton(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      super("Error");
      this.target = symbolizationnode;
      this.answer = symbolizationnode1;
      this.targetBinders = (Vector)vector.clone();
      this.answerBinders = (Vector)vector1.clone();
      Color[] acolor = symbolizationnode.symbolizer.colors;
      this.setMargin(new Insets(0, 1, 0, 1));
      this.setForeground(acolor[4]);
      this.setBackground(acolor[3]);
      this.addActionListener(this);
   }

   void buildMessage() {
      Message message = chooseErrorMessage(this.target, this.answer, this.targetBinders, this.answerBinders);
      this.messageId = message.id;
      this.messageText = Message.substitute(message.text, errorParams(this.target, this.answer, this.targetBinders, this.answerBinders));
      this.handler = new NodeMessageHandler(message.buttons);
   }

   static Hashtable errorParams(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      String s = symbolizationnode.getEnglishText();
      String s1 = symbolizationnode.describeType();
      String s2 = substituteVariables(symbolizationnode1.getEnglishText(), vector1, vector);
      String s3 = symbolizationnode1.describeType(vector1, vector);
      Hashtable hashtable = Message.params("wrong statement", s, "wrong type", s1, "right statement", s2, "right type", s3);
      if (symbolizationnode.isBinder()) {
         Message.putParam(hashtable, "bound var", symbolizationnode.getLabel());
      }

      return hashtable;
   }

   static Message chooseErrorMessage(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      Message message;
      if (symbolizationnode1.connective == 11 && symbolizationnode.connective == 11) {
         String s = symbolizationnode.getLabel();
         String s1 = symbolizationnode1.getLabel();
         if (symbolizationnode1.isLowercaseTerm()) {
            s1 = SymbolizationNode.translateVariable(s1, vector1, vector);
         } else {
            s1 = SymbolizationNode.translateExpression(s1, vector1, vector);
         }

         if (s1 == null) {
            message = SymbolizationMessages.get("symerr003");
         } else if (s1.equals(s)) {
            message = SymbolizationMessages.get("symerr002");
         } else {
            message = SymbolizationMessages.get("symerr001");
         }
      } else if (symbolizationnode1.connective == symbolizationnode.connective && symbolizationnode1.isBinder()) {
         message = SymbolizationMessages.get("symerr002");
      } else {
         message = SymbolizationMessages.get("symerr001");
      }

      return message;
   }

   static String substituteVariables(String s, Vector vector, Vector vector1) {
      Hashtable hashtable = new Hashtable();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         String s1 = (String)vector.elementAt(j);
         String s2 = (String)vector1.elementAt(j);
         if (vector.lastIndexOf(s1) == j && vector1.lastIndexOf(s2) == j) {
            hashtable.put(s1, s2);
         }
      }

      return Message.substitute(s, hashtable, null);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (this.target.symbolizer.hintsDisabled) {
         MessageDialog.showMessage("Feature Disabled", "Hints are disabled for this problem.", null, null);
      } else {
         this.buildMessage();
         this.target.textPanel.textPane.requestFocus();
         this.handler.setProperty("target", this.target);
         this.handler.setProperty("answer", this.answer);
         this.handler.setProperty("targetBinders", (Vector)this.targetBinders.clone());
         this.handler.setProperty("answerBinders", (Vector)this.answerBinders.clone());
         SymbolizationDialogs.showNodeMessage(this.messageId, this.messageText, this.handler);
      }
   }
}
