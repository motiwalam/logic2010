package edu.ucla.phil.logic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;

class ProblemListView extends JList implements Runnable, MouseListener, KeyListener {
   ProblemEditorFrame f895 = null;
   MessageDialog f896 = null;
   int f897 = 0;
   int f898 = 1;
   int[] f899;
   boolean f900 = false;
   DefaultListModel f901;

   ProblemListView(boolean flag) {
      super(new DefaultListModel());
      this.setBackground(null);
      this.setForeground(null);
      this.setCellRenderer(new C_DA());
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.f901 = (DefaultListModel)this.getModel();
      if (flag) {
         this.setSelectionMode(2);
      } else {
         this.setSelectionMode(0);
      }

      this.f899 = null;
   }

   void m1526(Object object) {
      this.f901.addElement(object);
   }

   int m1527() {
      return this.f901.getSize();
   }

   void m1528(MessageDialog messagedialog, int i) {
      this.f896 = messagedialog;
      this.f897 = i;
   }

   void m1529(ProblemEditorFrame problemeditorframe) {
      this.f895 = problemeditorframe;
   }

   void m1530() {
      if (this.f899 != null && this.getSelectionMode() != 0) {
         int i = this.f899.length;
         int k = 0;

         for (int j = 0; j < i; j++) {
            if (this.f899[j] != -1) {
               k++;
            }
         }

         int l = k;
         int[] aint = new int[k];

         for (int i1 = 0; i1 < i; i1++) {
            if (this.f899[i1] != -1) {
               aint[--l] = i1;
            }
         }

         this.setSelectedIndices(aint);
      }
   }

   void m1531(int i, int[] aint) {
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

   int m1532(int[] aint) {
      int i = this.getSelectedIndex();
      if (aint == null) {
         return i;
      } else {
         return i >= 0 && i < aint.length ? aint[i] : -1;
      }
   }

   int[] m1533(int[] aint) {
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
            i = 0;

            for (int l = 0; l < j; l++) {
               if (aint1[l] >= 0 && aint1[l] < aint.length && aint[aint1[l]] >= 0) {
                  aint2[i++] = aint[aint1[l]];
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
      if (this.f899 != null) {
         int i = this.locationToIndex(mouseevent.getPoint());
         if (i < 0 || i >= this.f899.length || this.f899[i] < 0) {
            return;
         }
      }

      if (mouseevent.getClickCount() == 2 && this.m1534()) {
         mouseevent.consume();
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      char c0 = keyevent.getKeyChar();
      if (c0 == '\n' && this.m1534()) {
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
      if (c0 == 27 && this.f896 != null) {
         this.f896.f790 = this.f898;
         this.f896.m1325();
         keyevent.consume();
      }
   }

   @Override
   public void keyReleased(KeyEvent keyevent) {
   }

   boolean m1534() {
      if (this.f896 != null) {
         this.f896.f790 = this.f897;
         this.f896.m1325();
         return true;
      } else {
         return this.f895 != null;
      }
   }

   @Override
   public void run() {
      try {
         Thread.sleep(250L);
      } catch (InterruptedException interruptedexception) {
      }

      this.m1535();
   }

   public void m1535() {
      int i = this.getSelectedIndex();
      if (i != -1) {
         this.ensureIndexIsVisible(i);
      }
   }

   @Override
   public void paintChildren(Graphics graphics) {
      super.paintChildren(graphics);
      if ((this.f896 != null || this.f895 != null) && !this.f900) {
         this.m1535();
         this.f900 = true;
      }
   }
}
