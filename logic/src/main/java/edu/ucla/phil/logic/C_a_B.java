package edu.ucla.phil.logic;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class C_a_B extends JTextField {
   int f965;
   int f966;
   boolean f967;
   UndoManager f968;

   C_a_B(String s, int i, int j, boolean flag) {
      super(s);
      this.f965 = i;
      this.f966 = j;
      this.f967 = flag;
      this.setBackground(null);
      this.setForeground(null);
      this.setBorder(BorderFactory.createEmptyBorder());
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.getDocument().addUndoableEditListener(this.f968 = new UndoManager());
      this.enableEvents(8L);
   }

   C_a_B(String s, int i, int j) {
      this(s, i, j, false);
   }

   C_a_B(String s, int i, boolean flag) {
      this(s, i, i, flag);
   }

   C_a_B(String s, int i) {
      this(s, i, i, false);
   }

   C_a_B(int i, boolean flag) {
      this("", i, i, flag);
   }

   C_a_B(int i) {
      this("", i, i, false);
   }

   C_a_B(String s, boolean flag) {
      this(s, 0, 2147483647, flag);
   }

   C_a_B(String s) {
      this(s, 0, 2147483647, false);
   }

   C_a_B(boolean flag) {
      this("", 0, 2147483647, flag);
   }

   C_a_B() {
      this("", 0, 2147483647, false);
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      if (dimension.width < this.f965) {
         dimension.width = this.f965;
      } else if (dimension.width > this.f966) {
         dimension.width = this.f966;
      }

      return dimension;
   }

   public Dimension m1636(Dimension dimension) {
      Dimension dimension1 = super.getSize(dimension);
      if (dimension1.width < dimension.width) {
         dimension1.width = dimension.width;
      }

      if (dimension1.width > this.f966) {
         dimension1.width = this.f966;
      }

      return dimension1;
   }

   void m1637(int i) {
      this.f965 = i;
      this.f966 = i;
   }

   public boolean m1638() {
      try {
         this.f968.undo();
         return true;
      } catch (CannotUndoException cannotundoexception) {
         return false;
      }
   }

   public boolean m1639() {
      try {
         this.f968.redo();
         return true;
      } catch (CannotRedoException cannotredoexception) {
         return false;
      }
   }
}
