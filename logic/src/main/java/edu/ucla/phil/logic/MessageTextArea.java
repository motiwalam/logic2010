package edu.ucla.phil.logic;

class MessageTextArea extends LogicTextArea implements FocusPreference {
   boolean focusRequested = false;

   MessageTextArea() {
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setEditable(false);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   MessageTextArea(String s) {
      super(s);
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setEditable(false);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
   }

   @Override
   public void setFocusable(boolean flag) {
      this.focusRequested = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean wantsFocus() {
      return this.focusRequested;
   }
}
