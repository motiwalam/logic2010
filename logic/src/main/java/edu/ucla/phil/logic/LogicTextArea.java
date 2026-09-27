package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleContext;

class LogicTextArea extends JTextArea implements ModuleComponentMarker {
   static StyleContext styleContext = new StyleContext();
   boolean noWidthTracking = false;
   private boolean computingTracking = false;
   boolean added = false;

   LogicTextArea() {
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.enableEvents(8L);
   }

   LogicTextArea(String s) {
      this();
      this.setText(s);
   }

   @Override
   public synchronized boolean getScrollableTracksViewportWidth() {
      if (this.computingTracking) {
         return false;
      } else {
         Container container = this.getParent();
         if (!this.noWidthTracking && container instanceof JViewport) {
            boolean flag = false;
            this.computingTracking = true;

            try {
               flag = container.getWidth() > this.getPreferredSize().width;
            } finally {
               this.computingTracking = false;
            }

            return flag;
         } else {
            return super.getScrollableTracksViewportWidth();
         }
      }
   }

   Point getCharLocation(int i) {
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
      this.added = true;
   }

   @Override
   public void removeNotify() {
      super.removeNotify();
      this.added = false;
   }

   int getTextLength() {
      Document document = this.getDocument();
      return document == null ? 0 : document.getLength();
   }
}
