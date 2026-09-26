package edu.ucla.phil.logic;

import java.awt.event.KeyEvent;

class C_JC extends C_s_D {
   C_y_B f434;

   C_JC(String s, C_y_B c_y_b) {
      super(s);
      this.f434 = c_y_b;
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         if (c0 == '\t') {
            int j = this.f434.m2186(this);
            int k = this.f434.m2187(this);
            if (j != -1) {
               ((C_JC)this.f434.f1449.elementAt(j)).requestFocus();
               keyevent.consume();
               return;
            }

            if (k != -1) {
               ((C_JC)this.f434.f1448.elementAt(k)).requestFocus();
               keyevent.consume();
               return;
            }
         }

         super.keyTyped(keyevent);
      }
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         int j = keyevent.getKeyCode();
         if (j == 224 || j == 38) {
            int i1;
            if ((i1 = this.f434.m2186(this)) != -1) {
               if (i1 > 0) {
                  ((C_JC)this.f434.f1448.elementAt(i1 - 1)).requestFocus();
               }
            } else if ((i1 = this.f434.m2187(this)) != -1 && i1 > 0) {
               ((C_JC)this.f434.f1449.elementAt(i1 - 1)).requestFocus();
            }
         } else if (j != 225 && j != 40) {
            if (c0 == '\n') {
               this.f434.m2185("", "");
               ((C_JC)this.f434.f1448.elementAt(this.f434.m2183() - 1)).requestFocus();
               keyevent.consume();
               return;
            }
         } else {
            int l = this.f434.m2183();
            int k;
            if ((k = this.f434.m2186(this)) != -1) {
               if (k < l - 1) {
                  ((C_JC)this.f434.f1448.elementAt(k + 1)).requestFocus();
               }
            } else if ((k = this.f434.m2187(this)) != -1 && k < l - 1) {
               ((C_JC)this.f434.f1449.elementAt(k + 1)).requestFocus();
            }
         }

         super.keyPressed(keyevent);
      }
   }
}
