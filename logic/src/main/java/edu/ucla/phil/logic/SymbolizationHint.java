package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class SymbolizationHint {
   String messageId = null;
   String messageText = null;
   SymbolizationNode target;
   SymbolizationNode answer;
   Vector targetBinders;
   Vector answerBinders;
   NodeMessageHandler handler;

   SymbolizationHint(SymbolizationNode symbolizationnode, SymbolizationNode symbolizationnode1, Vector vector, Vector vector1) {
      this.target = symbolizationnode;
      this.answer = symbolizationnode1;
      this.targetBinders = (Vector)vector.clone();
      this.answerBinders = (Vector)vector1.clone();
   }

   void buildMessage() {
      Message message = SymbolizationMessages.get("symnot001");
      this.messageId = message.id;
      this.messageText = Message.substitute(message.text, hintParams(this.answer, this.targetBinders, this.answerBinders));
      this.handler = new NodeMessageHandler(message.buttons);
   }

   static Hashtable hintParams(SymbolizationNode symbolizationnode, Vector vector, Vector vector1) {
      String s = SymbolizationErrorButton.substituteVariables(symbolizationnode.getEnglishText(), vector1, vector);
      String s1 = symbolizationnode.describeType(vector1, vector);
      return Message.params("right statement", s, "right type", s1);
   }

   void show() {
      this.buildMessage();
      this.target.textPanel.textPane.requestFocus();
      this.handler.setProperty("target", this.target);
      this.handler.setProperty("answer", this.answer);
      this.handler.setProperty("targetBinders", (Vector)this.targetBinders.clone());
      this.handler.setProperty("answerBinders", (Vector)this.answerBinders.clone());
      SymbolizationDialogs.showNodeMessage(this.messageId, this.messageText, this.handler);
   }
}
