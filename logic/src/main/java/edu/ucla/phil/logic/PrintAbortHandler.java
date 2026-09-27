package edu.ucla.phil.logic;

class PrintAbortHandler extends DialogHandler {
   PrintAbortHandler(String s) {
      super(s);
   }

   @Override
   boolean handleChoice(MessageDialog messagedialog) {
      String s = this.getSelectedAction(messagedialog);
      if (s != null && s.equalsIgnoreCase("abort")) {
         PrintTask.abortQueue((PrintQueue)this.getProperty("queue"));
      }

      return false;
   }
}
