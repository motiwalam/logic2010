package edu.ucla.phil.logic;

import java.awt.event.KeyEvent;

class SchemeCellPane extends FormulaTextPane {
   SchemeEditor editor;

   SchemeCellPane(String s, SchemeEditor schemeeditor) {
      super(s);
      this.editor = schemeeditor;
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if (!keyevent.isConsumed()) {
         char c0 = keyevent.getKeyChar();
         int i = keyevent.getModifiers();
         if (c0 == '\t') {
            int j = this.editor.indexOfSymbolField(this);
            int k = this.editor.indexOfEnglishField(this);
            if (j != -1) {
               ((SchemeCellPane)this.editor.englishFields.elementAt(j)).requestFocus();
               keyevent.consume();
               return;
            }

            if (k != -1) {
               ((SchemeCellPane)this.editor.symbolFields.elementAt(k)).requestFocus();
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
            if ((i1 = this.editor.indexOfSymbolField(this)) != -1) {
               if (i1 > 0) {
                  ((SchemeCellPane)this.editor.symbolFields.elementAt(i1 - 1)).requestFocus();
               }
            } else if ((i1 = this.editor.indexOfEnglishField(this)) != -1 && i1 > 0) {
               ((SchemeCellPane)this.editor.englishFields.elementAt(i1 - 1)).requestFocus();
            }
         } else if (j != 225 && j != 40) {
            if (c0 == '\n') {
               this.editor.addRow("", "");
               ((SchemeCellPane)this.editor.symbolFields.elementAt(this.editor.getRowCount() - 1)).requestFocus();
               keyevent.consume();
               return;
            }
         } else {
            int l = this.editor.getRowCount();
            int k;
            if ((k = this.editor.indexOfSymbolField(this)) != -1) {
               if (k < l - 1) {
                  ((SchemeCellPane)this.editor.symbolFields.elementAt(k + 1)).requestFocus();
               }
            } else if ((k = this.editor.indexOfEnglishField(this)) != -1 && k < l - 1) {
               ((SchemeCellPane)this.editor.englishFields.elementAt(k + 1)).requestFocus();
            }
         }

         super.keyPressed(keyevent);
      }
   }
}
