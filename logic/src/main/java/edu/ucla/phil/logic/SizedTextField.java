package edu.ucla.phil.logic;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class SizedTextField extends JTextField {
   int minWidth;
   int maxWidth;
   boolean flag;
   UndoManager undoManager;

   SizedTextField(String s, int i, int j, boolean flagx) {
      super(s);
      this.minWidth = i;
      this.maxWidth = j;
      this.flag = flagx;
      this.setBackground(null);
      this.setForeground(null);
      this.setBorder(BorderFactory.createEmptyBorder());
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.getDocument().addUndoableEditListener(this.undoManager = new UndoManager());
      this.enableEvents(8L);
   }

   SizedTextField(String s, int i, int j) {
      this(s, i, j, false);
   }

   SizedTextField(String s, int i, boolean flagx) {
      this(s, i, i, flagx);
   }

   SizedTextField(String s, int i) {
      this(s, i, i, false);
   }

   SizedTextField(int i, boolean flagx) {
      this("", i, i, flagx);
   }

   SizedTextField(int i) {
      this("", i, i, false);
   }

   SizedTextField(String s, boolean flagx) {
      this(s, 0, 2147483647, flagx);
   }

   SizedTextField(String s) {
      this(s, 0, 2147483647, false);
   }

   SizedTextField(boolean flagx) {
      this("", 0, 2147483647, flagx);
   }

   SizedTextField() {
      this("", 0, 2147483647, false);
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      if (dimension.width < this.minWidth) {
         dimension.width = this.minWidth;
      } else if (dimension.width > this.maxWidth) {
         dimension.width = this.maxWidth;
      }

      return dimension;
   }

   public Dimension getClampedSize(Dimension dimension) {
      Dimension dimension1 = super.getSize(dimension);
      if (dimension1.width < dimension.width) {
         dimension1.width = dimension.width;
      }

      if (dimension1.width > this.maxWidth) {
         dimension1.width = this.maxWidth;
      }

      return dimension1;
   }

   void setFixedWidth(int i) {
      this.minWidth = i;
      this.maxWidth = i;
   }

   public boolean undo() {
      try {
         this.undoManager.undo();
         return true;
      } catch (CannotUndoException cannotundoexception) {
         return false;
      }
   }

   public boolean redo() {
      try {
         this.undoManager.redo();
         return true;
      } catch (CannotRedoException cannotredoexception) {
         return false;
      }
   }
}
