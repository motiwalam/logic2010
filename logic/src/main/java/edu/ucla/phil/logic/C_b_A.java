package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

class C_b_A extends JButton implements ActionListener, MenuKeyListener {
   Vector f981;
   C_S f982;
   String[] f983;
   String f984;
   int f985;
   Object f986;
   Color[] f987;
   Color f988;
   Color f989;
   JPopupMenu f990;
   boolean f991;
   boolean f992;
   boolean f993;
   static boolean f994 = false;

   C_b_A() {
   }

   C_b_A(String[] astring) {
      this(astring, null, 0, true);
   }

   C_b_A(String[] astring, int i) {
      this(astring, null, i, true);
   }

   C_b_A(String[] astring, String s, int i) {
      this(astring, s, i, true);
   }

   C_b_A(String[] astring, String s, int i, boolean flag) {
      this.f983 = m1658(astring);
      this.f984 = s;
      this.f985 = i;
      this.f991 = false;
      this.f992 = false;
      if (flag) {
         this.setMargin(new Insets(0, 0, 0, 0));
      }

      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
      this.f986 = null;
      this.f987 = null;
      this.f993 = false;
      this.setText(this.m1657());
      this.setBackground(null);
      this.setForeground(null);
      if (f994) {
         this.f982 = new C_S(astring, this);
      }

      this.addActionListener(this);
      this.f981 = new Vector();
   }

   C_b_A(String[] astring, String s) {
      this(astring, s, -1, true);
   }

   void m1644(Color color, Color color1) {
      this.setForeground(color);
      this.setBackground(color1);
   }

   void m1645(Object object) {
      this.f986 = object;
   }

   Object m1646() {
      return this.f986;
   }

   void m1647(boolean flag) {
      this.f993 = flag;
   }

   boolean m1648() {
      return this.f993;
   }

   void m1649(int i) {
      this.m1650(i, true);
   }

   void m1650(int i, boolean flag) {
      int j = this.f985;
      this.f985 = i;
      this.setText(this.m1657());
      this.invalidateParent();
      if (flag) {
         int k = this.f981.size();

         for (int l = 0; l < k; l++) {
            ((C_F)this.f981.elementAt(l)).m514(this, j, i);
         }
      }
   }

   int m1651() {
      return this.f985;
   }

   void m1652(String[] astring, int i) {
      this.m1653(astring, null, i);
   }

   void m1653(String[] astring, String s, int i) {
      this.f983 = m1658(astring);
      this.f984 = s;
      this.f985 = i;
      this.setText(this.m1657());
      if (f994) {
         this.f982 = new C_S(astring, this);
      }

      this.invalidateParent();
   }

   String m1654(int i) {
      if (i < 0) {
         return this.f984;
      } else {
         return this.f983 != null && i < this.f983.length ? this.f983[i] : null;
      }
   }

   void m1655(boolean flag) {
      this.f992 = flag;
      this.invalidateParent();
   }

   void m1656(boolean flag) {
      this.f991 = flag;
      this.setText(this.m1657());
   }

   String m1657() {
      if (this.f991) {
         return this.f984 == null ? "none" : this.f984;
      } else if (this.f983 == null || this.f983.length == 0) {
         return "none";
      } else if (this.f985 < 0) {
         return this.f984 == null ? "bad index" : this.f984;
      } else {
         return this.f985 < this.f983.length ? this.f983[this.f985] : "bad index";
      }
   }

   static String[] m1658(String[] astring) {
      int i = astring.length;
      String[] astring1 = new String[i];
      System.arraycopy(astring, 0, astring1, 0, i);
      return astring1;
   }

   @Override
   void invalidateParent() {
      JComponent jcomponent = (JComponent)this.getParent();
      if (jcomponent == null) {
         this.invalidate();
      } else {
         jcomponent.invalidate();
      }
   }

   void m1659(C_F c_f) {
      if (!this.f981.contains(c_f)) {
         this.f981.addElement(c_f);
      }
   }

   void m1660(C_F c_f) {
      if (this.f981.contains(c_f)) {
         this.f981.removeElement(c_f);
      }
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      Graphics graphics;
      if (this.f992 && (graphics = this.getGraphics()) != null) {
         FontMetrics fontmetrics = graphics.getFontMetrics();
         int i = fontmetrics.stringWidth(this.m1657());
         int j = this.f984 == null ? 0 : fontmetrics.stringWidth(this.f984);

         for (int k = 0; k < this.f983.length; k++) {
            int l = fontmetrics.stringWidth(this.f983[k]);
            if (j < l) {
               j = l;
            }
         }

         if (j > i) {
            dimension.width += j - i;
         }
      }

      return dimension;
   }

   @Override
   public void menuKeyTyped(MenuKeyEvent menukeyevent) {
   }

   @Override
   public void menuKeyPressed(MenuKeyEvent menukeyevent) {
      if (menukeyevent.getKeyCode() == 27) {
         this.f990.setVisible(false);
         menukeyevent.consume();
      }
   }

   @Override
   public void menuKeyReleased(MenuKeyEvent menukeyevent) {
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      Object object = actionevent.getSource();
      if (object == this) {
         if (!this.f993) {
            this.m1661();
         }
      } else if (object instanceof JMenuItem) {
         int i = this.f990.getComponentIndex((JMenuItem)object);
         if (i != -1) {
            this.m1649(i);
         }

         this.f990.setVisible(false);
      }
   }

   void m1661() {
      if (f994) {
         this.f990 = new JPopupMenu();
         this.f990.add(this.f982);
         this.f990.pack();
         this.f990.show(this, this.getWidth(), this.f985 == -1 ? 0 : -this.f982.indexToLocation(this.f985).y);
      } else {
         this.f990 = new JPopupMenu();
         int i = this.f983.length;

         for (int j = 0; j < i; j++) {
            JMenuItem jmenuitem = this.f990.add(new C_OB(this.f983[j]));
            jmenuitem.addActionListener(this);
            jmenuitem.addMenuKeyListener(this);
         }

         int l = 0;
         if (this.f985 != -1) {
            this.f990.doLayout();
            Insets insets = ((JMenuItem)this.f990.getComponent(this.f985)).getMargin();
            int k = this.getGraphics().getFontMetrics().getHeight();
            l = (k + insets.top + insets.bottom) * this.f985 + insets.top;
         }

         this.f990.show(this, this.getWidth(), -l);
      }
   }

   boolean m1662() {
      return this.f990 == null ? false : this.f990.isVisible();
   }

   static {
      UIManager.put("MenuItem.selectionBackground", new Color(184, 207, 229));
   }
}
