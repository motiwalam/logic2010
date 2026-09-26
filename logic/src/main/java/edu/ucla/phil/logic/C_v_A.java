package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

class C_v_A extends JComponent implements ActionListener {
   private Component f1417 = null;
   private boolean f1418 = true;
   private boolean f1419 = false;

   void m1548(Component component) {
      if (this.f1417 != component) {
         if (this.f1417 != null) {
            Container container = this.f1417.getParent();
            if (container != null) {
               container.remove(this.f1417);
            }

            if (this.f1417 instanceof JButton) {
               ((JButton)this.f1417).removeActionListener(this);
            } else if (this.f1417 instanceof AbstractButton) {
               ((AbstractButton)this.f1417).removeActionListener(this);
            }

            this.f1417 = null;
         }

         if (component instanceof C_ZD) {
            ((C_ZD)component).m1547(this.f1418);
         }

         Container container1 = this.getParent();
         if (component != null && container1 instanceof C_v_A) {
            if (component instanceof JButton) {
               ((JButton)component).addActionListener(this);
            } else if (component instanceof AbstractButton) {
               ((AbstractButton)component).addActionListener(this);
            }

            container1.add(component);
            this.f1417 = component;
         }

         this.invalidate();
      }
   }

   Component m2122() {
      return this.f1417;
   }

   synchronized void m2123(boolean flag) {
      if (this.f1418 != flag) {
         int i = this.m2127();

         for (int j = 0; j < i; j++) {
            this.m2128(j).setVisible(flag);
         }

         this.f1418 = flag;
         this.invalidate();
         if (this.f1417 instanceof C_ZD) {
            ((C_ZD)this.f1417).m1547(flag);
         }

         if (this instanceof C__) {
            C_M c_m = ((C__)this).f915.scrollPanel;
            c_m.revalidate();
            c_m.repaint();
         }
      }
   }

   boolean m2124() {
      return this.f1418;
   }

   void m2125(Component component) {
      if (this.f1419) {
         this.f1419 = false;
         if (this.getComponentCount() > 0) {
            this.remove(0);
         }
      }

      if (component != null) {
         this.f1419 = true;
         this.add(component, 0);
      }
   }

   Component m2126() {
      return this.f1419 && this.getComponentCount() > 0 ? this.getComponent(0) : null;
   }

   int m2127() {
      int i = this.getComponentCount();
      return this.f1419 && i > 0 ? i - 1 : i;
   }

   Component m2128(int i) {
      return this.getComponent(this.f1419 ? i + 1 : i);
   }

   @Override
   public Component add(Component component) {
      return this.add(component, -1);
   }

   @Override
   public Component add(Component component, int i) {
      this.add(component, null, i);
      return component;
   }

   @Override
   public void add(Component component, Object object) {
      this.add(component, object, -1);
   }

   @Override
   public Component add(String s, Component component) {
      this.add(component, s, -1);
      return component;
   }

   @Override
   public void add(Component component, Object object, int i) {
      component.setVisible(this.f1418);
      super.add(component, object, i);
      if (component == this.m2126()) {
         component.setVisible(true);
      }

      if (this.m2127() == 1 && this.getParent() instanceof C_v_A) {
         this.m1548(new C_ZD(this));
      }

      C_v_A c_v_a1;
      if (component instanceof C_v_A && (c_v_a1 = (C_v_A)component).m2127() > 0) {
         c_v_a1.m1548(new C_ZD(c_v_a1));
      }
   }

   boolean m2129(Component component) {
      if (component.getParent() != this) {
         return false;
      } else {
         if (component instanceof C_v_A) {
            ((C_v_A)component).m1548(null);
         }

         return true;
      }
   }

   @Override
   public void remove(Component component) {
      if (this.m2129(component)) {
         super.remove(component);
         if (this.m2127() == 0) {
            this.m1548(null);
         }
      }
   }

   @Override
   public void remove(int i) {
      if (this.m2129(this.getComponent(i))) {
         super.remove(i);
         if (this.m2127() == 0) {
            this.m1548(null);
         }
      }
   }

   @Override
   public void removeAll() {
      super.removeAll();
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getSource() == this.f1417) {
         Object object = null;
         this.m2123(!this.f1418);
         if (this instanceof C__) {
            ((C__)this).f915.setWidths(true);
         }
      }
   }

   int m1553(int i) {
      int j = 0;
      int k = this.getComponentCount();

      while (true) {
         while (j >= k || !(this.getComponent(j) instanceof C_ZD)) {
            if (j >= k) {
               return k;
            }

            if (i == 0) {
               return j;
            }

            i--;
            j++;
         }

         j++;
      }
   }

   int m1554(int i) {
      int j = 0;
      int k = this.getComponentCount();
      if (k > i) {
         k = i;
      }

      for (int l = 0; l < k; l++) {
         if (!(this.getComponent(l) instanceof C_ZD)) {
            j++;
         }
      }

      return j;
   }

   int m1555() {
      int i = this.getComponentCount();
      int j = i;

      while (--j >= 0) {
         if (this.getComponent(j) instanceof C_ZD) {
            i--;
         }
      }

      return i;
   }
}
