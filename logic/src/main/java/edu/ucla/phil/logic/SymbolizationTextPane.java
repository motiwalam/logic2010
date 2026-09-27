package edu.ucla.phil.logic;

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import javax.swing.SwingUtilities;

class SymbolizationTextPane extends StyledTextPane implements SymbolizationConstants, KeyListener, FocusListener, MouseListener {
   SymbolizationTextPanel textPanel = null;
   boolean resetCaretOnFocus = true;

   SymbolizationTextPane(String s) {
      super(s);
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addFocusListener(this);
      this.setFocusTraversalKeys(0, Collections.EMPTY_SET);
      this.setFocusTraversalKeys(1, Collections.EMPTY_SET);
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   @Override
   public void keyPressed(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if ((i == 61 || i == 187) && (j & 3) == 3) {
         SymbolizationNode symbolizationnode3 = this.applyConnective(9, (j & 8) != 0);
         if (symbolizationnode3 != null) {
            symbolizationnode3.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if ((i == 47 || i == 191) && (j & 3) == 3) {
         if (this.textPanel.node.symbolizer.hintsDisabled) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            this.textPanel.node.showHint();
         }

         keyevent.consume();
      } else if (i == 50 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode = this.applyConnective(11, (j & 8) != 0);
         if (symbolizationnode != null) {
            symbolizationnode.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (i == 9) {
         keyevent.consume();
      } else if (i == 10) {
         keyevent.consume();
      } else if (i == 39 && (j & 8) != 0) {
         SymbolizationNode symbolizationnode6 = this.textPanel.node;
         SymbolizationNode symbolizationnode8 = symbolizationnode6.getParentNode();
         int l = symbolizationnode8 == null ? -1 : symbolizationnode8.indexOfChildNode(symbolizationnode6);
         if (l != -1 && l + 1 < symbolizationnode8.argTypes.length) {
            symbolizationnode8.getChildNode(l + 1).textPanel.textPane.requestFocus();
         } else {
            Toolkit.getDefaultToolkit().beep();
         }

         keyevent.consume();
      } else if (i == 37 && (j & 8) != 0) {
         SymbolizationNode symbolizationnode5 = this.textPanel.node;
         SymbolizationNode symbolizationnode7 = symbolizationnode5.getParentNode();
         System.out.println(symbolizationnode7.indexOfChildNode(symbolizationnode5));
         int k = symbolizationnode7 == null ? -1 : symbolizationnode7.indexOfChildNode(symbolizationnode5);
         if (k - 1 < 0) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            symbolizationnode7.getChildNode(k - 1).textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (i == 38 && (j & 8) != 0) {
         SymbolizationNode symbolizationnode4 = this.textPanel.node;
         SymbolizationNode symbolizationnode2 = symbolizationnode4.getParentNode();
         if (symbolizationnode2 == null) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            symbolizationnode2.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (i == 40 && (j & 8) != 0) {
         SymbolizationNode symbolizationnode1 = this.textPanel.node;
         if (symbolizationnode1.argTypes.length == 0) {
            Toolkit.getDefaultToolkit().beep();
         } else {
            symbolizationnode1.getChildNode(0).textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (c0 == 1 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode9 = this.applyConnective(3, (j & 8) != 0);
         if (symbolizationnode9 != null) {
            symbolizationnode9.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 2 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode8 = this.applyConnective(5, (j & 8) != 0);
         if (symbolizationnode8 != null) {
            symbolizationnode8.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 3 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode7 = this.applyConnective(2, (j & 8) != 0);
         if (symbolizationnode7 != null) {
            symbolizationnode7.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 4 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode6 = this.applyConnective(8, (j & 8) != 0);
         if (symbolizationnode6 != null) {
            symbolizationnode6.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 5 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode5 = this.applyConnective(7, (j & 8) != 0);
         if (symbolizationnode5 != null) {
            symbolizationnode5.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if ((c0 == '\n' || c0 == '\r') && (j & 3) == 3) {
         SymbolizationNode symbolizationnode4 = this.applyConnective(10, (j & 8) != 0);
         if (symbolizationnode4 != null) {
            symbolizationnode4.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 14 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode3 = this.applyConnective(1, (j & 8) != 0);
         if (symbolizationnode3 != null) {
            symbolizationnode3.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 15 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode2 = this.applyConnective(4, (j & 8) != 0);
         if (symbolizationnode2 != null) {
            symbolizationnode2.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 20 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode1 = this.applyConnective(0, (j & 8) != 0);
         if (symbolizationnode1 != null) {
            symbolizationnode1.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if (c0 == 21 && (j & 3) == 3) {
         SymbolizationNode symbolizationnode = this.applyConnective(6, (j & 8) != 0);
         if (symbolizationnode != null) {
            symbolizationnode.textPanel.textPane.requestFocus();
         }

         keyevent.consume();
      } else if ((c0 == '\n' || c0 == '\r') && (j & 3) == 2) {
         if (this.isEditable()) {
            int k = this.getSelectionStart();
            int l = this.getSelectionEnd();
            if (k == l) {
               k = this.getCaretPosition();
            } else {
               this.replaceRange("", k, l);
            }

            this.insertText("\n", k);
         }

         keyevent.consume();
      } else if (c0 == '\n') {
         this.textPanel.showConnectiveMenu((j & 8) != 0);
         keyevent.consume();
      } else if (c0 == 1) {
         this.selectAll();
         keyevent.consume();
      }

      this.scrollToPosition(this.getCaretPosition());
      this.textPanel.validate();
   }

   SymbolizationNode applyConnective(int i, boolean flag) {
      SymbolizationNode symbolizationnode = this.textPanel.node.getParentNode();
      int j = symbolizationnode == null ? -1 : symbolizationnode.indexOfChildNode(this.textPanel.node);
      int k = symbolizationnode == null ? 2 : symbolizationnode.argTypes[j];
      int l = connOutTypes[i];
      if (k != 2 && l != 2 && l != k) {
         Toolkit.getDefaultToolkit().beep();
         return null;
      } else {
         if (flag) {
            EditableTextPane.copyToClipboard(this.getText());
         }

         return this.textPanel.node.setConnective(i, true);
      }
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      this.resetCaretOnFocus = false;
      if (SwingUtilities.isRightMouseButton(mouseevent)) {
         this.textPanel.showConnectiveMenu((mouseevent.getModifiers() & 2) != 0, mouseevent.getX());
         mouseevent.consume();
      }
   }

   @Override
   public void focusGained(FocusEvent focusevent) {
      this.textPanel.onTextFocusGained(focusevent);
   }

   @Override
   public void focusLost(FocusEvent focusevent) {
      this.resetCaretOnFocus = true;
      this.textPanel.onTextFocusLost(focusevent);
   }
}
