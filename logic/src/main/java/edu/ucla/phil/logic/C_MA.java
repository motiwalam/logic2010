package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

class C_MA extends JPopupMenu implements ActionListener, C_v_D {
   C_x_C f609;
   static final String[] f610 = new String[]{"Hint"};
   static final String[] f611 = new String[]{"Ctrl+Shift+?"};
   static final Integer[] f612 = new Integer[]{new Integer(1)};
   static final String[] f613 = new String[]{"Inequality"};
   static final Integer[] f614 = new Integer[]{new Integer(5)};
   static final String[] f615 = new String[]{"Rest Univ", "Rest Exist", "Unrest"};
   static final String[] f616 = new String[]{
      "Restricted Universal Generalization", "Restricted Existential Generalization", "Remove Restricted Generalization"
   };
   static final Integer[] f617 = new Integer[]{new Integer(3), new Integer(3), new Integer(3)};

   C_MA(C_x_C c_x_c) {
      this.f609 = c_x_c;
   }

   void m1088() {
      C_d_C c_d_c = (C_d_C)this.f609.getParent();
      C_d_C c_d_c1 = c_d_c.m1688();
      Integer integer = c_d_c.f1044.chapter;
      int j = c_d_c1 == null ? -1 : c_d_c1.m1687(c_d_c);
      int k = c_d_c1 == null ? 2 : c_d_c1.f1050[j];

      while (this.getComponentCount() != 0) {
         this.remove(0);
      }

      int i = connOutTypes.length;

      for (int l = 0; l < i; l++) {
         int i1 = connOutTypes[l];
         if ((i1 == 2 || k == 2 || i1 == k) && this.m1089(integer, connChaps[l])) {
            C_OB c_ob;
            this.add(c_ob = new C_OB(connMenu[l]));
            c_ob.addActionListener(this);
            if (connHover[l] != null) {
               c_ob.m1141(connHover[l]);
            }
         }
      }

      if ((k == 0 || k == 2) && this.m1089(integer, f614[0])) {
         C_OB c_ob1;
         this.add(c_ob1 = new C_OB(f613[0]));
         c_ob1.addActionListener(this);
      }

      if (!c_d_c.f1044.hintsDisabled && c_d_c.f1044.problem.m1714() != 0 && this.m1089(integer, f612[0])) {
         C_OB c_ob2;
         this.add(c_ob2 = new C_OB(f610[0]));
         c_ob2.m1141(f611[0]);
         c_ob2.addActionListener(this);
      }

      if (k == 0 || k == 2) {
         if (this.m1089(integer, f617[0])) {
            C_OB c_ob3;
            this.add(c_ob3 = new C_OB(f615[0]));
            c_ob3.m1141(f616[0]);
            c_ob3.addActionListener(this);
         }

         if (this.m1089(integer, f617[1])) {
            C_OB c_ob4;
            this.add(c_ob4 = new C_OB(f615[1]));
            c_ob4.m1141(f616[1]);
            c_ob4.addActionListener(this);
         }
      }

      if ((c_d_c.f1048 == 6 && c_d_c.m1686(0).f1048 == 2 || c_d_c.f1048 == 7 && c_d_c.m1686(0).f1048 == 3 || c_d_c.f1048 == 1 && c_d_c.m1686(0).f1048 == 9)
         && this.m1089(integer, f617[2])) {
         C_OB c_ob5;
         this.add(c_ob5 = new C_OB(f615[2]));
         c_ob5.m1141(f616[2]);
         c_ob5.addActionListener(this);
      }

      this.addSeparator();
      i = editMenu.length;

      for (int j1 = 0; j1 < i; j1++) {
         C_OB c_ob6;
         this.add(c_ob6 = new C_OB(editMenu[j1]));
         c_ob6.m1141(editHover[j1]);
         c_ob6.addActionListener(this);
      }
   }

   boolean m1089(Integer integer, Integer integer1) {
      return integer == null || integer1 == null || integer >= integer1;
   }

   @Override
   public boolean keyDown(Event event, int i) {
      if (i == 27) {
         this.f609.f1441 = true;
      }

      return super.keyDown(event, i);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      JMenuItem jmenuitem = (JMenuItem)actionevent.getSource();
      String s = jmenuitem.getText();
      C_d_C c_d_c = (C_d_C)this.f609.getParent();

      for (int i = 0; i < connMenu.length; i++) {
         if (s.equals(connMenu[i])) {
            C_d_C c_d_c1 = c_d_c.m1690(i, true);
            if (c_d_c1 != null) {
               c_d_c1.f1045.f1438.requestFocus();
            }

            return;
         }
      }

      if (s.equals(f613[0])) {
         C_TF c_tf3 = new C_TF(c_d_c);
         c_d_c.m1690(0, false);
         c_d_c.m1690(1, true);
         C_d_C c_d_c6;
         (c_d_c6 = c_d_c.m1686(0)).m1690(9, false);
         c_tf3.m1309(c_d_c6);
         c_d_c6.f1045.f1438.requestFocus();
      } else if (s.equals(f610[0])) {
         C_d_C c_d_c5 = c_d_c.f1044.problem;
         C_d_C c_d_c7 = c_d_c5.m1715();
         if (c_d_c7 != null) {
            c_d_c5.m1739();
            C_JA c_ja = new C_JA(c_d_c);
            c_d_c5.m1723(c_d_c7, new Vector(), new Vector(), c_ja);
            C_CD c_cd = c_ja.m721();
            if (c_cd == null) {
               C_f_A c_f_a = null;
               Vector vector = c_ja.m720();
               int k = vector.size();
               C_d_C c_d_c2 = c_d_c;

               while (c_f_a == null && (c_d_c2 = c_d_c2.m1688()) != null) {
                  for (int l = 0; l < k; l++) {
                     C_f_A c_f_a1 = (C_f_A)vector.elementAt(l);
                     if (c_f_a1.f1106 == c_d_c2) {
                        c_f_a = c_f_a1;
                        break;
                     }
                  }
               }

               if (c_f_a != null) {
                  C_VE c_ve = c_f_a.f1106.m1685();
                  if (c_ve != null) {
                     c_ve.add(c_f_a);
                     c_d_c.f1044.errorCount++;
                     c_ve.f839.setEnabled(true);
                     c_ve.revalidate();
                  }
               }
            } else {
               c_cd.m426();
               c_d_c.f1044.hintCount++;
            }
         }
      } else if (s.equals(f615[0])) {
         C_TF c_tf2 = new C_TF(c_d_c);
         c_d_c.m1690(0, false);
         c_d_c.m1690(6, true);
         C_d_C c_d_c4;
         (c_d_c4 = c_d_c.m1686(0)).m1690(2, false);
         c_tf2.m1309(c_d_c4);
         c_d_c4.f1045.f1438.requestFocus();
      } else if (s.equals(f615[1])) {
         C_TF c_tf1 = new C_TF(c_d_c);
         c_d_c.m1690(0, false);
         c_d_c.m1690(7, true);
         C_d_C c_d_c3;
         (c_d_c3 = c_d_c.m1686(0)).m1690(3, false);
         c_tf1.m1309(c_d_c3);
         c_d_c3.f1045.f1438.requestFocus();
      } else if (s.equals(f615[2])) {
         C_TF c_tf = new C_TF(c_d_c.m1686(0));
         int j1 = c_d_c.m1686(0).f1048;
         c_d_c.m1690(0, false);
         c_d_c.m1690(j1, true);
         c_tf.m1309(c_d_c);
         c_d_c.f1045.f1438.requestFocus();
      } else if (s.equals(editMenu[0]) || s.equals(editMenu[4])) {
         C_x_E c_x_e1 = this.f609.f1438;
         int i1 = c_x_e1.getSelectionStart();
         int j = c_x_e1.getSelectionEnd();
         if (j > i1) {
            if (s.equals(editMenu[0])) {
               C_p_A.m2024(c_x_e1.getSelectedText());
            }

            String s1 = c_x_e1.getText();
            c_x_e1.setText(s1.substring(0, i1) + s1.substring(j));
            c_x_e1.select(i1, i1);
            this.f609.m2165(true);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (s.equals(editMenu[1])) {
         String s2 = this.f609.f1438.getSelectedText();
         if (s2 != null && s2.length() > 0) {
            C_p_A.m2024(s2);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (s.equals(editMenu[2])) {
         C_x_E c_x_e = this.f609.f1438;
         String s3 = C_p_A.m2025();
         if (s3 == null) {
            s3 = "";
         }

         c_x_e.replaceSelection(s3);
         this.f609.m2165(true);
      } else if (s.equals(editMenu[3])) {
         this.f609.f1438.select(0, 2147483647);
      }
   }

   JMenuItem[] m1090() {
      int i = this.getComponentCount();
      JMenuItem[] ajmenuitem = new JMenuItem[i];

      for (int j = 0; j < i; j++) {
         ajmenuitem[j] = (JMenuItem)this.getComponent(j);
      }

      return ajmenuitem;
   }

   static {
      UIManager.put("MenuItem.selectionBackground", new Color(184, 207, 229));
   }
}
