package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleContext;

class C_NC extends JTextArea implements C_LC {
   static StyleContext f644 = new StyleContext();
   boolean f645 = false;
   private boolean f646 = false;
   boolean f647 = false;

   C_NC() {
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.enableEvents(8L);
   }

   C_NC(String s) {
      this();
      this.setText(s);
   }

   @Override
   public synchronized boolean getScrollableTracksViewportWidth() {
      if (this.f646) {
         return false;
      } else {
         Container container = this.getParent();
         if (!this.f645 && container instanceof JViewport) {
            boolean flag = false;
            this.f646 = true;

            try {
               flag = container.getWidth() > this.getPreferredSize().width;
            } finally {
               this.f646 = false;
            }

            return flag;
         } else {
            return super.getScrollableTracksViewportWidth();
         }
      }
   }

   Point m1130(int i) {
      try {
         Rectangle rectangle = this.modelToView(i);
         if (rectangle == null) {
            return null;
         } else {
            rectangle.x += 0;
            return new Point(rectangle.x, rectangle.y);
         }
      } catch (BadLocationException badlocationexception) {
         return null;
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.f647 = true;
   }

   @Override
   public void removeNotify() {
      super.removeNotify();
      this.f647 = false;
   }

   int m1131() {
      Document document = this.getDocument();
      return document == null ? 0 : document.getLength();
   }
}
