package edu.ucla.phil.logic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.util.Vector;

class ProblemListView extends JList implements Runnable, MouseListener, KeyListener {
   ProblemEditorFrame editorFrame = null;
   MessageDialog dialog = null;
   int acceptButton = 0;
   int cancelButton = 1;
   int[] rowToProblem;
   boolean scrolledToSelection = false;
   DefaultListModel listModel;
   Object[] allItems = null;
   int[] allRowToProblem = null;
   String[] searchTexts = null;

   ProblemListView(boolean flag) {
      super(new DefaultListModel());
      this.setBackground(null);
      this.setForeground(null);
      this.setCellRenderer(new ProblemListCellRenderer());
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.listModel = (DefaultListModel)this.getModel();
      if (flag) {
         this.setSelectionMode(2);
      } else {
         this.setSelectionMode(0);
      }

      this.rowToProblem = null;
   }

   void addItem(Object object) {
      this.listModel.addElement(object);
   }

   int getItemCount() {
      return this.listModel.getSize();
   }

   void rememberRows(String[] astring) {
      this.allItems = this.listModel.toArray();
      this.allRowToProblem = this.rowToProblem;
      this.searchTexts = astring;
   }

   void setFilter(String s) {
      if (this.allItems != null) {
         String s1 = s.trim().toLowerCase();
         String[] astring = s1.equals("") ? new String[0] : s1.split("\\s+");
         int i = this.getSelectedProblem(this.rowToProblem);
         Vector vector = new Vector();
         Vector vector1 = new Vector();

         for (int j = 0; j < this.allItems.length; j++) {
            int k = this.allRowToProblem[j];
            if (astring.length == 0) {
               vector.addElement(this.allItems[j]);
               vector1.addElement(k);
            } else if (k >= 0 && this.searchTexts != null && k < this.searchTexts.length && matches(this.searchTexts[k], astring)) {
               int l = j;

               while (l > 0 && this.allRowToProblem[l - 1] < 0) {
                  l--;
               }

               for (; l < j; l++) {
                  if (!(this.allItems[l] instanceof LogicLabel) || !((LogicLabel)this.allItems[l]).getText().trim().equals("")) {
                     vector.addElement(this.allItems[l]);
                     vector1.addElement(-1);
                  }
               }

               vector.addElement(this.allItems[j]);
               vector1.addElement(k);
            }
         }

         this.listModel.removeAllElements();
         this.rowToProblem = new int[vector.size()];
         int m = -1;
         int q = -1;

         for (int n = 0; n < vector.size(); n++) {
            this.listModel.addElement(vector.elementAt(n));
            int p = (Integer)vector1.elementAt(n);
            this.rowToProblem[n] = p;
            if (p >= 0 && q == -1) {
               q = n;
            }

            if (p >= 0 && p == i) {
               m = n;
            }
         }

         if (m == -1) {
            m = q;
         }

         if (m == -1) {
            this.clearSelection();
         } else {
            this.setSelectedIndex(m);
            this.ensureIndexIsVisible(m);
         }
      }
   }

   // Every term must occur in the text. A term like T2 or MC1 (letters, then a number)
   // must match a whole name: T2 does not match T25 or ST2.
   static boolean matches(String s, String[] astring) {
      if (s == null) {
         return false;
      } else {
         for (int i = 0; i < astring.length; i++) {
            if (!matchesTerm(s, astring[i])) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean matchesTerm(String s, String s1) {
      boolean flag = s1.matches(".*[a-z][0-9]+");
      int i = 0;

      while ((i = s.indexOf(s1, i)) != -1) {
         int j = i + s1.length();
         if (!flag || (i == 0 || !Character.isLetter(s.charAt(i - 1)) || !Character.isLetter(s1.charAt(0)))
               && (j >= s.length() || !Character.isDigit(s.charAt(j)))) {
            return true;
         }

         i++;
      }

      return false;
   }

   void moveSelection(int i) {
      int j = this.getSelectedIndex();
      int k = this.getItemCount();
      int l = j;

      do {
         l += i;
      } while (l >= 0 && l < k && this.rowToProblem != null && this.rowToProblem[l] < 0);

      if (l >= 0 && l < k) {
         this.setSelectedIndex(l);
         this.ensureIndexIsVisible(l);
      }
   }

   void setDialog(MessageDialog messagedialog, int i) {
      this.dialog = messagedialog;
      this.acceptButton = i;
   }

   void setEditorFrame(ProblemEditorFrame problemeditorframe) {
      this.editorFrame = problemeditorframe;
   }

   void selectAllProblems() {
      if (this.rowToProblem != null && this.getSelectionMode() != 0) {
         int i = this.rowToProblem.length;
         int k = 0;

         for (int j = 0; j < i; j++) {
            if (this.rowToProblem[j] != -1) {
               k++;
            }
         }

         int l = k;
         int[] aint = new int[k];

         for (int i1 = 0; i1 < i; i1++) {
            if (this.rowToProblem[i1] != -1) {
               aint[--l] = i1;
            }
         }

         this.setSelectedIndices(aint);
      }
   }

   void selectProblem(int i, int[] aint) {
      if (aint == null) {
         this.setSelectedIndex(i);
      } else {
         for (int j = 0; j < aint.length; j++) {
            if (aint[j] == i) {
               this.addSelectionInterval(j, j);
               break;
            }
         }
      }
   }

   int getSelectedProblem(int[] aint) {
      int i = this.getSelectedIndex();
      if (aint == null) {
         return i;
      } else {
         return i >= 0 && i < aint.length ? aint[i] : -1;
      }
   }

   int[] getSelectedProblems(int[] aint) {
      int[] aint1 = this.getSelectedIndices();
      if (aint != null && aint1 != null) {
         int i = 0;
         int j = aint1.length;

         for (int k = 0; k < j; k++) {
            if (aint1[k] >= 0 && aint1[k] < aint.length && aint[aint1[k]] >= 0) {
               i++;
            }
         }

         if (i == 0) {
            return null;
         } else {
            int[] aint2 = new int[i];
            int i1 = 0;

            for (int l = 0; l < j; l++) {
               if (aint1[l] >= 0 && aint1[l] < aint.length && aint[aint1[l]] >= 0) {
                  aint2[i1++] = aint[aint1[l]];
               }
            }

            return aint2;
         }
      } else {
         return aint1;
      }
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      if (this.rowToProblem != null) {
         int i = this.locationToIndex(mouseevent.getPoint());
         if (i < 0 || i >= this.rowToProblem.length || this.rowToProblem[i] < 0) {
            return;
         }
      }

      if (mouseevent.getClickCount() == 2 && this.accept()) {
         mouseevent.consume();
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      if (c0 == '\n' && this.accept()) {
         keyevent.consume();
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
   public void keyPressed(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      if (c0 == 27 && this.dialog != null) {
         this.dialog.selectedButton = this.cancelButton;
         this.dialog.close();
         keyevent.consume();
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   boolean accept() {
      if (this.dialog != null) {
         this.dialog.selectedButton = this.acceptButton;
         this.dialog.close();
         return true;
      } else {
         return this.editorFrame != null;
      }
   }

   @Override
   public void run() {
      try {
         Thread.sleep(250L);
      } catch (InterruptedException interruptedexception) {
      }

      this.scrollToSelection();
   }

   public void scrollToSelection() {
      int i = this.getSelectedIndex();
      if (i != -1) {
         this.ensureIndexIsVisible(i);
      }
   }

   @Override
   public void paintChildren(Graphics graphics) {
      super.paintChildren(graphics);
      if ((this.dialog != null || this.editorFrame != null) && !this.scrolledToSelection) {
         this.scrollToSelection();
         this.scrolledToSelection = true;
      }
   }
}
