package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class C_SD extends JDialog implements ActionListener {
   Frame f763;
   JTextArea f764;
   JButton[] f765;
   public int f766 = -1;
   int f767;
   int f768 = 0;
   int f769 = 0;
   static Font font = m1286(0);
   boolean f770 = false;

   private C_SD(Frame frame, boolean flag) {
      super(frame, flag);
      this.f763 = frame;
      this.setLayout(new BorderLayout());
      this.setTitle(frame.getTitle());
   }

   public C_SD(String s, String s1, boolean flag) {
      this(s, s1, flag ? new String[]{"OK"} : null, 0);
   }

   public C_SD(String s, String s1, String[] astring, int i) {
      this(new Frame(s), astring != null);
      this.setFont(new Font("Dialog", 1, 14));
      this.f764 = new JTextArea(m1292(s1));
      this.f764.setAlignmentY(0.0F);
      this.f764.setAlignmentX(0.0F);
      this.f764.setEditable(false);
      this.add(this.f764, "Center");
      if (astring == null) {
         this.f765 = null;
         this.requestFocus();
      } else {
         int j = astring.length;
         this.f765 = new JButton[j];
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new FlowLayout(1));

         for (int k = 0; k < j; k++) {
            jpanel.add(this.f765[k] = new JButton(astring[k]));
            this.f765[k].addActionListener(this);
            this.f765[k].setFont(font);
         }

         this.add(jpanel, "South");
         this.f767 = i >= 0 && i < j ? i : 0;
         if (j > 0) {
            this.f765[this.f767].requestFocusInWindow();
         }
      }
   }

   public void m1284(int i, int j) {
      this.f768 = i;
      this.f769 = j;
      this.show();
   }

   public static void m1285(int i) {
      if (font == null || i != font.getSize()) {
         font = m1286(i);
      }
   }

   static Font m1286(int i) {
      return m1287(i, 1);
   }

   static Font m1287(int i, int j) {
      return new Font("SanSerif", j, i == 0 ? m1288() : i);
   }

   public static int m1288() {
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      int i = dimension.height / 64;
      int j = dimension.width / 100;
      return i < j ? i : j;
   }

   @Override
   public void show() {
      if (!this.f770) {
         this.pack();
         this.setLocation(m1291(this.getSize()));
         this.setResizable(false);
         this.f766 = -1;
         super.show();
      }
   }

   @Override
   public void dispose() {
      if (!this.f770) {
         if (!SwingUtilities.isEventDispatchThread()) {
            super.hide();
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  C_SD.this.dispose();
               }
            });
         } else {
            this.f770 = true;
            super.dispose();
            this.f763.dispose();
         }
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getID() == 1001) {
         if (actionevent.getSource() instanceof JButton) {
            String s = actionevent.getActionCommand();
            this.f766 = 0;

            while (this.f766 < this.f765.length && !s.equals(this.f765[this.f766].getActionCommand())) {
               this.f766++;
            }

            if (this.f766 == this.f765.length) {
               this.f766 = -1;
            }
         }

         this.dispose();
      } else if (actionevent.getID() == 201) {
         if (actionevent.getSource() == this) {
            this.f766 = -1;
            this.dispose();
         }
      } else if (actionevent.getID() == 401) {
      }
   }

   public void m1289(String s) {
      this.f764.setText(m1292(s));
      this.invalidate();
      this.setSize(this.getPreferredSize());
      this.validate();
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      dimension.width = dimension.width + this.f768 * 2;
      dimension.height = dimension.height + this.f769 * 2;
      return dimension;
   }

   public Dimension m1290(Dimension dimension) {
      return dimension;
   }

   @Override
   public boolean handleEvent(Event event) {
      if (event.id == 1001) {
         int i = this.f765 == null ? 0 : this.f765.length;

         for (int j = 0; j < i; j++) {
            if (event.target == this.f765[j]) {
               this.f766 = j;
               this.dispose();
               return true;
            }
         }
      }

      return super.handleEvent(event);
   }

   static Point m1291(Dimension dimension) {
      Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
      return new Point((dimension1.width - dimension.width) / 2, (dimension1.height - dimension.height) / 2);
   }

   static String m1292(String s) {
      String s1 = "";
      String s2 = System.getProperty("line.separator", "\n");

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         s1 = s1 + s.substring(0, i);
         if (s.length() < i + 2) {
            return s1;
         }

         char c0 = s.charAt(i + 1);
         s = s.substring(i + 2);
         if (c0 == 'n') {
            s1 = s1 + '\n';
         } else {
            s1 = s1 + c0;
         }
      }

      return s1 + s;
   }
}
