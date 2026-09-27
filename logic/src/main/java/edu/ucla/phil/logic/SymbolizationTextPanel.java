package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;

class SymbolizationTextPanel extends SizedPanel implements SymbolizationConstants {
   SymbolizationNode node;
   SymbolizationTextPane textPane;
   SymbolizationMenuButton connectiveMenu;
   SymbolizationPopupMenu popupMenu;
   boolean popupWasOpen;
   int parenSide;
   static final boolean UNUSED_FLAG = true;

   SymbolizationTextPanel(SymbolizationNode symbolizationnode, String s, int i) {
      this.node = symbolizationnode;
      this.parenSide = i;
      this.popupWasOpen = false;
      this.setLayout(new FlowLayout(1, 0, 0));
      if (i == 1) {
         LogicLabel logiclabel = new LogicLabel("(");
         logiclabel.setFocusable(false);
         this.add(logiclabel);
      }

      this.add(this.textPane = new SymbolizationTextPane(collapseWhitespace(s == null ? "" : s)));
      if (symbolizationnode.symbolizer != null) {
         Color[] acolor = symbolizationnode.symbolizer.colors;
         this.textPane.setForeground(acolor[0]);
         this.textPane.setBackground(acolor[2]);
      }

      this.textPane.textPanel = this;
      if (i == -1) {
         LogicLabel logiclabel1 = new LogicLabel(")");
         logiclabel1.setFocusable(false);
         this.add(logiclabel1);
      }

      this.connectiveMenu = null;
      this.popupMenu = new SymbolizationPopupMenu(this);
   }

   void normalizeText() {
      this.normalizeText(false);
   }

   void normalizeText(boolean flag) {
      if (flag) {
         int[] aint = new int[]{this.textPane.getSelectionStart(), this.textPane.getSelectionEnd()};
         this.textPane.setText(collapseWhitespace(this.textPane.getText(), aint));
         this.textPane.select(aint[0], aint[1]);
      } else {
         this.textPane.setText(collapseWhitespace(this.textPane.getText()));
      }

      this.node.invalidate();
      if (this.node.symbolizer != null) {
         this.node.symbolizer.updateSymbolization();
      }
   }

   static String collapseWhitespace(String s) {
      return collapseWhitespace(s, null);
   }

   static String collapseWhitespace(String s, int[] aint) {
      if (s == null) {
         s = "";
      }

      char[] achar = s.toCharArray();
      int i = 0;
      int j = 0;
      int k = achar.length;

      while (i < k) {
         while (i < k && isCollapsibleSpace(achar[i])) {
            i++;
         }

         while (i < k && !isCollapsibleSpace(achar[i])) {
            adjustIndexes(aint, j, i);
            achar[j++] = achar[i++];
         }

         if (i < k) {
            adjustIndexes(aint, j, i);
            achar[j++] = achar[i++];
         }
      }

      if (j > 0 && isCollapsibleSpace(achar[j - 1])) {
         j--;
      }

      adjustIndexes(aint, j, k);
      return j == 0 ? "    " : new String(achar, 0, j);
   }

   private static boolean isCollapsibleSpace(char c0) {
      return c0 <= ' ' && c0 != '\n';
   }

   private static void adjustIndexes(int[] aint, int i, int j) {
      if (aint != null) {
         int k = aint.length;

         while (--k >= 0) {
            if (aint[k] > i && aint[k] <= j) {
               aint[k] = i;
            }
         }
      }
   }

   public void onTextFocusGained(FocusEvent focusevent) {
      if (this.popupMenu.isVisible()) {
         this.popupWasOpen = true;
      }

      this.setActive(true);
   }

   public void onTextFocusLost(FocusEvent focusevent) {
      Object object = focusevent.getSource();
      if (!this.isMenuVisible() && !LogicProgram.isDescendant(this, (Component)object)) {
         this.setActive(false);
      }
   }

   void setActive(boolean flag) {
      if (this.node.symbolizer != null) {
         Color[] acolor = this.node.symbolizer.colors;
         if (flag) {
            if (this.node.symbolizer.focus == this) {
               return;
            }

            if (this.node.symbolizer.focus != null) {
               this.node.symbolizer.focus.setActive(false);
            }

            this.node.symbolizer.focus = this;
            this.node.symbolizer.lastFocus = this;
            this.textPane.setForeground(acolor[1]);
            this.textPane.setBackground(acolor[0]);
            if (this.textPane.resetCaretOnFocus) {
               this.textPane.setCaretPosition(0);
               this.textPane.resetCaretOnFocus = false;
            }

            SymbolizationNode symbolizationnode = this.node.getParentNode();
            if (symbolizationnode == null) {
               byte b0 = -1;
            } else {
               symbolizationnode.indexOfChildNode(this.node);
            }

            this.node.invalidate();
         } else {
            if (this.node.symbolizer.focus != this) {
               return;
            }

            this.textPane.setForeground(acolor[0]);
            this.textPane.setBackground(acolor[2]);
            this.node.symbolizer.focus = null;
            this.validate();
            this.normalizeText();
         }
      }
   }

   void showConnectiveMenu(boolean flag) {
      this.showConnectiveMenu(flag, 0);
   }

   void showConnectiveMenu(boolean flag, int i) {
      if (flag) {
         EditableTextPane.copyToClipboard(this.textPane.getText());
      }

      if (!this.textPane.hasFocus()) {
         this.textPane.resetCaretOnFocus = true;
         this.textPane.requestFocus();
      }

      Rectangle rectangle = this.textPane.getBounds(null);
      this.popupMenu.rebuildItems();
      this.popupMenu.showAt(i + rectangle.x, rectangle.y + rectangle.height, this);
   }

   boolean isMenuVisible() {
      return this.popupMenu.isVisible();
   }
}
