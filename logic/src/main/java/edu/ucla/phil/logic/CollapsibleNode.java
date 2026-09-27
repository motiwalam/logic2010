package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;

class CollapsibleNode extends JComponent implements ActionListener {
   private Component toggle = null;
   private boolean expanded = true;
   private boolean hasHeader = false;

   void setToggle(Component component) {
      if (this.toggle != component) {
         if (this.toggle != null) {
            Container container = this.toggle.getParent();
            if (container != null) {
               container.remove(this.toggle);
            }

            if (this.toggle instanceof JButton) {
               ((JButton)this.toggle).removeActionListener(this);
            } else if (this.toggle instanceof AbstractButton) {
               ((AbstractButton)this.toggle).removeActionListener(this);
            }

            this.toggle = null;
         }

         if (component instanceof ExpandToggleButton) {
            ((ExpandToggleButton)component).setExpanded(this.expanded);
         }

         Container container1 = this.getParent();
         if (component != null && container1 instanceof CollapsibleNode) {
            if (component instanceof JButton) {
               ((JButton)component).addActionListener(this);
            } else if (component instanceof AbstractButton) {
               ((AbstractButton)component).addActionListener(this);
            }

            container1.add(component);
            this.toggle = component;
         }

         this.invalidate();
      }
   }

   Component getToggle() {
      return this.toggle;
   }

   synchronized void setExpanded(boolean flag) {
      if (this.expanded != flag) {
         int i = this.getBodyCount();

         for (int j = 0; j < i; j++) {
            this.getBodyComponent(j).setVisible(flag);
         }

         this.expanded = flag;
         this.invalidate();
         if (this.toggle instanceof ExpandToggleButton) {
            ((ExpandToggleButton)this.toggle).setExpanded(flag);
         }

         if (this instanceof DerivationBox) {
            LinePanel linepanel = ((DerivationBox)this).module.scrollPanel;
            linepanel.revalidate();
            linepanel.repaint();
         }
      }
   }

   boolean isExpanded() {
      return this.expanded;
   }

   void setHeader(Component component) {
      if (this.hasHeader) {
         this.hasHeader = false;
         if (this.getComponentCount() > 0) {
            this.remove(0);
         }
      }

      if (component != null) {
         this.hasHeader = true;
         this.add(component, 0);
      }
   }

   Component getHeader() {
      return this.hasHeader && this.getComponentCount() > 0 ? this.getComponent(0) : null;
   }

   int getBodyCount() {
      int i = this.getComponentCount();
      return this.hasHeader && i > 0 ? i - 1 : i;
   }

   Component getBodyComponent(int i) {
      return this.getComponent(this.hasHeader ? i + 1 : i);
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
      component.setVisible(this.expanded);
      super.add(component, object, i);
      if (component == this.getHeader()) {
         component.setVisible(true);
      }

      if (this.getBodyCount() == 1 && this.getParent() instanceof CollapsibleNode) {
         this.setToggle(new ExpandToggleButton(this));
      }

      CollapsibleNode collapsiblenode1;
      if (component instanceof CollapsibleNode && (collapsiblenode1 = (CollapsibleNode)component).getBodyCount() > 0) {
         collapsiblenode1.setToggle(new ExpandToggleButton(collapsiblenode1));
      }
   }

   boolean detachChild(Component component) {
      if (component.getParent() != this) {
         return false;
      } else {
         if (component instanceof CollapsibleNode) {
            ((CollapsibleNode)component).setToggle(null);
         }

         return true;
      }
   }

   @Override
   public void remove(Component component) {
      if (this.detachChild(component)) {
         super.remove(component);
         if (this.getBodyCount() == 0) {
            this.setToggle(null);
         }
      }
   }

   @Override
   public void remove(int i) {
      if (this.detachChild(this.getComponent(i))) {
         super.remove(i);
         if (this.getBodyCount() == 0) {
            this.setToggle(null);
         }
      }
   }

   @Override
   public void removeAll() {
      super.removeAll();
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getSource() == this.toggle) {
         Object object = null;
         this.setExpanded(!this.expanded);
         if (this instanceof DerivationBox) {
            ((DerivationBox)this).module.setWidths(true);
         }
      }
   }

   int toComponentIndex(int i) {
      int j = 0;
      int k = this.getComponentCount();

      while (true) {
         while (j >= k || !(this.getComponent(j) instanceof ExpandToggleButton)) {
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

   int toContentIndex(int i) {
      int j = 0;
      int k = this.getComponentCount();
      if (k > i) {
         k = i;
      }

      for (int l = 0; l < k; l++) {
         if (!(this.getComponent(l) instanceof ExpandToggleButton)) {
            j++;
         }
      }

      return j;
   }

   int getContentCount() {
      int i = this.getComponentCount();
      int j = i;

      while (--j >= 0) {
         if (this.getComponent(j) instanceof ExpandToggleButton) {
            i--;
         }
      }

      return i;
   }
}
