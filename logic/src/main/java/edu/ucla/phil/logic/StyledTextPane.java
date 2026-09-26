package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.InputMap;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

class StyledTextPane extends JTextPane implements C_LC {
   static StyleContext f1092 = null;
   boolean f1093 = false;
   private boolean f1094 = false;

   StyledTextPane() {
      this.setMargin(new Insets(0, 1, 0, 1));
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.enableEvents(8L);
   }

   StyledTextPane(String s) {
      this();
      this.setMargin(new Insets(0, 1, 0, 1));
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.setText(s);
   }

   void m1787(boolean flag) {
      this.f1093 = flag;
   }

   boolean m1788() {
      return this.f1093;
   }

   void m1789(boolean flag) {
   }

   boolean m1790() {
      return true;
   }

   @Override
   public void setEditable(boolean flag) {
      super.setEditable(flag);
      if (!flag) {
         this.setCursor(new Cursor(2));
      }
   }

   void m1791(Color color) {
      this.m1793(StyleConstants.Foreground, color);
   }

   void m1792(Color color) {
      this.m1793(StyleConstants.Background, color);
   }

   void m1793(Object object, Color color) {
      if (color != null) {
         if (f1092 == null) {
            f1092 = new StyleContext();
         }

         AttributeSet attributeset = null;
         StyledDocument styleddocument = this.getStyledDocument();
         if (styleddocument != null) {
            Caret caret = this.getCaret();
            Element element = styleddocument.getCharacterElement(caret == null ? 0 : caret.getDot());
            if (element != null) {
               attributeset = element.getAttributes();
            }
         }

         if (attributeset == null) {
            attributeset = SimpleAttributeSet.EMPTY;
         }

         AttributeSet attributeset1 = f1092.addAttribute(attributeset, object, color);
         StyledDocument styleddocument1 = this.getStyledDocument();
         if (styleddocument1 != null) {
            styleddocument1.setCharacterAttributes(0, 2147483647, attributeset1, false);
         }

         MutableAttributeSet mutableattributeset = this.getInputAttributes();
         if (mutableattributeset != null) {
            mutableattributeset.addAttributes(attributeset1);
         }
      }
   }

   void m1794(boolean flag) {
      if (f1092 == null) {
         f1092 = new StyleContext();
      }

      AttributeSet attributeset;
      try {
         attributeset = this.getCharacterAttributes();
      } catch (NullPointerException nullpointerexception) {
         attributeset = null;
      }

      if (attributeset == null) {
         attributeset = SimpleAttributeSet.EMPTY;
      }

      AttributeSet attributeset1 = f1092.addAttribute(attributeset, StyleConstants.Underline, flag);
      StyledDocument styleddocument = this.getStyledDocument();
      if (styleddocument != null) {
         styleddocument.setCharacterAttributes(0, 2147483647, attributeset1, false);
      }

      MutableAttributeSet mutableattributeset = this.getInputAttributes();
      if (mutableattributeset != null) {
         mutableattributeset.addAttributes(attributeset1);
      }

      this.repaint();
   }

   public void m1795(String s, int i, int j) {
      if (j < i) {
         throw new IllegalArgumentException("end before start");
      } else {
         Document document = this.getDocument();
         if (document != null) {
            try {
               if (document instanceof AbstractDocument) {
                  ((AbstractDocument)document).replace(i, j - i, s, null);
               } else {
                  document.remove(i, j - i);
                  document.insertString(i, s, null);
               }
            } catch (BadLocationException badlocationexception) {
               throw new IllegalArgumentException(badlocationexception.getMessage());
            }
         }
      }
   }

   public void m1796(String s, int i) {
      Document document = this.getDocument();
      if (document != null) {
         try {
            document.insertString(i, s, null);
         } catch (BadLocationException badlocationexception) {
            throw new IllegalArgumentException(badlocationexception.getMessage());
         }
      }
   }

   @Override
   public synchronized boolean getScrollableTracksViewportWidth() {
      if (this.f1094) {
         return false;
      } else {
         Container container = this.getParent();
         if (!this.f1093 && container instanceof JViewport) {
            boolean flag = false;
            this.f1094 = true;

            try {
               flag = container.getWidth() > this.getPreferredSize().width;
            } finally {
               this.f1094 = false;
            }

            return flag;
         } else {
            return super.getScrollableTracksViewportWidth();
         }
      }
   }

   Point m1797(int i) {
      try {
         Rectangle rectangle = this.modelToView(i);
         if (rectangle == null) {
            return new Point(0, 0);
         } else {
            rectangle.x += 0;
            return new Point(rectangle.x, rectangle.y);
         }
      } catch (BadLocationException badlocationexception) {
         return new Point(0, 0);
      }
   }

   void m1798(int i) {
      try {
         this.scrollRectToVisible(this.modelToView(i));
      } catch (BadLocationException badlocationexception) {
      } catch (NullPointerException nullpointerexception) {
      }
   }

   int m1799() {
      StyledDocument styleddocument = this.getStyledDocument();
      return styleddocument == null ? 0 : styleddocument.getLength();
   }

   static {
      for (InputMap inputmap = SwingUtilities.getUIInputMap(new JTextPane(), 0); inputmap != null; inputmap = inputmap.getParent()) {
         inputmap.remove(KeyStroke.getKeyStroke("control shift O"));
      }
   }
}
