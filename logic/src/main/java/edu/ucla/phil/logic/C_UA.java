package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

class C_UA extends C_u_B implements C_n_A, ActionListener {
   static JFrame f785 = null;
   boolean f786;
   boolean f787;
   boolean f788;
   boolean f789 = false;
   int f790;
   C__D[] f791;
   Component f792;
   Vector f793;
   static Hashtable f794 = new Hashtable();
   String f795;

   C_UA(Frame frame, String s, Component component, String[] astring) {
      super(m1311(frame, s), s, true);
      this.f788 = false;
      this.f786 = frame == null && this.f1395 != f785;
      this.f787 = true;
      this.f790 = -1;
      this.setFont(LogicProgram.m1029(LogicProgram.f539));
      this.add(this.f792 = component, "Center");
      JRootPane jrootpane = this.getRootPane();
      jrootpane.getInputMap(1).put(KeyStroke.getKeyStroke(27, 0), "CloseOnEsc");
      jrootpane.getActionMap().put("CloseOnEsc", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            C_UA.this.f790 = -1;
            C_UA.this.m1325();
         }
      });
      if (astring == null) {
         this.f791 = null;
      } else {
         this.f791 = new C__D[astring.length];
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new C_b_C(1, 0, 5, 5));

         for (int i = 0; i < this.f791.length; i++) {
            jpanel.add(this.f791[i] = new C__D(astring[i]));
            this.f791[i].addActionListener(this);
         }

         this.add(jpanel, "South");
         if (this.f791.length > 0) {
            this.f791[0].requestFocus();
         }
      }

      this.f793 = null;
      this.f795 = null;
      this.pack();
   }

   static synchronized Frame m1311(Frame frame, String s) {
      if (frame != null) {
         return frame;
      } else if (LogicProgram.f586 == null) {
         if (f785 == null) {
            f785 = new JFrame();
            f785.setIconImages(LogicProgram.f594);
            f785.setUndecorated(true);
            f785.setVisible(true);
            f785.setLocationRelativeTo(null);
         }

         if (s != null) {
            f785.setTitle(s);
         }

         return f785;
      } else {
         return new C_0E();
      }
   }

   static synchronized void m1312() {
      if (f785 != null) {
         f785.dispose();
         f785 = null;
      }
   }

   @Override
   public void setSize(Dimension dimension) {
      super.setSize(dimension);
      this.f788 = true;
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getID() == 1001) {
         if (actionevent.getSource() instanceof JButton && LogicProgram.m1033(this, (Component)actionevent.getSource())) {
            this.f790 = 0;

            while (this.f790 < this.f791.length && !actionevent.getActionCommand().equals(this.f791[this.f790].getActionCommand())) {
               this.f790++;
            }

            if (this.f790 == this.f791.length || this.m1326()) {
               this.m1325();
            }
         }
      } else if (actionevent.getID() == 201 && actionevent.getSource() == this) {
         this.f790 = -1;
         this.m1325();
      }
   }

   void m1313(C__D[] ac__d) {
      this.f791 = ac__d;
   }

   void m1314(int i) {
      if (i < 0) {
         this.getRootPane().setDefaultButton(null);
      } else {
         this.f791[i].setDefaultCapable(true);
         this.getRootPane().setDefaultButton(this.f791[i]);
      }
   }

   void m1315(C_D c_d) {
      if (c_d != null) {
         if (this.f793 == null) {
            this.f793 = new Vector();
         }

         this.f793.addElement(c_d);
      }
   }

   void m1316(String s, String s1) {
      for (int i = 0; i < this.f791.length; i++) {
         C__D c__d;
         if ((c__d = this.f791[i]) != null && s.equalsIgnoreCase(c__d.getText())) {
            c__d.m1583(s1);
            break;
         }
      }
   }

   void m1317(String s) {
      this.f788 = false;
      this.f795 = s;
   }

   String m1318() {
      return this.f795;
   }

   void m1319() {
      f794.remove(this.f795);
   }

   Rectangle m1320() {
      return this.f795 == null ? null : (Rectangle)f794.get(this.f795);
   }

   static Point m1321(Dimension dimension) {
      return new Point((LogicProgram.f541.width - dimension.width) / 2, (LogicProgram.f541.height - dimension.height) / 2);
   }

   void m1322(Point point) {
      this.m1323(point, false);
   }

   void m1323(Point point, boolean flag) {
      Rectangle rectangle;
      if (!this.f788 && (rectangle = this.m1320()) != null) {
         this.setSize(rectangle.getSize());
         if (this.f787) {
            point = rectangle.getLocation();
         }
      }

      this.setLocation(point == null ? m1321(this.getSize()) : point);
      this.setResizable(flag);
      if (this.f789) {
         this.pack();
      }

      this.setVisible(true);
      if (this.f786 && this.isModal()) {
         this.f1395.dispose();
      }
   }

   void m1324(Point point, boolean flag) {
      this.setModal(false);
      this.f786 = flag;
      this.m1323(point, true);
   }

   void m1325() {
      this.dispose();
      if (this.f786 && !this.isModal()) {
         this.f1395.dispose();
      }
   }

   @Override
   public void dispose() {
      if (this.f795 != null) {
         Rectangle rectangle = this.getBounds(null);
         f794.put(this.f795, rectangle);
      }

      super.dispose();
   }

   boolean m1326() {
      int i = this.f793 == null ? 0 : this.f793.size();
      boolean flag = true;

      for (int j = 0; j < i; j++) {
         flag &= ((C_D)this.f793.elementAt(j)).m451(this);
      }

      return flag;
   }

   boolean m1327(Vector vector, C_p_A[] ac_p_a, int i) {
      int j = vector == null ? 0 : vector.size();
      int k = ac_p_a == null ? 0 : ac_p_a.length;
      if (k < j) {
         j = k;
      }

      k -= j;

      for (int l = 0; l < j; l++) {
         ac_p_a[l].setText((String)vector.elementAt(0));
         vector.removeElementAt(0);
         ac_p_a[l].select(0, 2147483647);
      }

      if (k == 0 && i != -1) {
         this.f790 = i;
         return this.m1326();
      } else {
         return false;
      }
   }

   static void m1328(String s, String s1, Point point, C_D c_d) {
      C_LB c_lb = new C_LB();
      c_lb.m934(LogicProgram.f541.width * 3 / 4);
      c_lb.m937(true);
      c_lb.setLayout(new BorderLayout());
      C_p_A c_p_a = new C_p_A(LogicProgram.m1004(s1));
      c_p_a.m1787(true);
      c_p_a.m1789(true);
      c_p_a.setEnabled(false);
      c_p_a.setFont(LogicProgram.m1029(LogicProgram.f539));
      c_p_a.setDisabledTextColor(C_n_A.bruinBlack);
      c_p_a.setBackground(C_n_A.bruinAsh);
      c_lb.add(c_p_a, "North");
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (c_d != null) {
         astring = c_d.m445();
         i = c_d.m446();
      }

      C_UA c_ua = new C_UA(null, s, c_lb, astring);
      c_ua.pack();
      c_ua.m1315(c_d);
      c_ua.m1314(i);
      c_ua.m1322(point);
   }

   static void m1329(C_H c_h, Hashtable hashtable, Point point, C_D c_d) {
      String s = c_h.f372;
      if (hashtable != null) {
         s = C_H.m661(s, hashtable);
      }

      m1328(c_h.f370, s, point, c_d);
   }

   static C_u_B m1330(C_H c_h, Hashtable hashtable) {
      String s = c_h.f372;
      if (hashtable != null) {
         s = C_H.m661(s, hashtable);
      }

      C_LB c_lb = new C_LB();
      c_lb.m934(LogicProgram.f541.width * 3 / 4);
      c_lb.m937(true);
      C_p_A c_p_a = new C_p_A(LogicProgram.m1004(s));
      c_p_a.m1787(true);
      c_p_a.m1789(true);
      c_lb.add(c_p_a, "Center");
      C_u_B c_u_b = new C_u_B(new C_0E(), c_h.f370, false);
      c_u_b.add(c_lb);
      c_u_b.pack();
      c_u_b.setLocation(m1321(c_u_b.getSize()));
      c_u_b.setResizable(false);
      c_u_b.show();
      return c_u_b;
   }

   static void m1331(C_u_B c_u_b) {
      c_u_b.dispose();
      c_u_b.f1395.dispose();
   }

   static void m1332(String s, String s1, Rectangle rectangle, C_D c_d) {
      JScrollPane jscrollpane = new JScrollPane();
      C_s_D c_s_d = new C_s_D(LogicProgram.m1004(s1));
      c_s_d.setEditable(false);
      c_s_d.setBackground(dialogWhite);
      c_s_d.setCaretPosition(0);
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (c_d != null) {
         astring = c_d.m445();
         i = c_d.m446();
      }

      jscrollpane.setViewportView(c_s_d);
      C_UA c_ua = new C_UA(null, s, jscrollpane, astring);
      c_ua.m1315(c_d);
      c_ua.m1314(i);
      c_ua.m1317("showMessage");
      Dimension dimension = rectangle == null ? new Dimension(300, 200) : rectangle.getSize();
      Point point = rectangle == null ? m1321(dimension) : rectangle.getLocation();
      c_ua.setSize(dimension);
      c_ua.m1323(point, true);
   }
}
