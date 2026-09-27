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

public class ProgressDialog extends JDialog implements ActionListener {
   Frame ownerFrame;
   JTextArea textArea;
   JButton[] buttons;
   public int selectedButton = -1;
   int defaultButton;
   int marginX = 0;
   int marginY = 0;
   static Font font = createFont(0);
   boolean disposed = false;

   private ProgressDialog(Frame frame, boolean flag) {
      super(frame, flag);
      this.ownerFrame = frame;
      this.setLayout(new BorderLayout());
      this.setTitle(frame.getTitle());
   }

   public ProgressDialog(String s, String s1, boolean flag) {
      this(s, s1, flag ? new String[]{"OK"} : null, 0);
   }

   public ProgressDialog(String s, String s1, String[] astring, int i) {
      this(new Frame(s), astring != null);
      this.setFont(new Font("Dialog", 1, 14));
      this.textArea = new JTextArea(unescape(s1));
      this.textArea.setAlignmentY(0.0F);
      this.textArea.setAlignmentX(0.0F);
      this.textArea.setEditable(false);
      this.add(this.textArea, "Center");
      if (astring == null) {
         this.buttons = null;
         this.requestFocus();
      } else {
         int j = astring.length;
         this.buttons = new JButton[j];
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new FlowLayout(1));

         for (int k = 0; k < j; k++) {
            jpanel.add(this.buttons[k] = new JButton(astring[k]));
            this.buttons[k].addActionListener(this);
            this.buttons[k].setFont(font);
         }

         this.add(jpanel, "South");
         this.defaultButton = i >= 0 && i < j ? i : 0;
         if (j > 0) {
            this.buttons[this.defaultButton].requestFocusInWindow();
         }
      }
   }

   public void showWithMargins(int i, int j) {
      this.marginX = i;
      this.marginY = j;
      this.show();
   }

   public static void setFontSize(int i) {
      if (font == null || i != font.getSize()) {
         font = createFont(i);
      }
   }

   static Font createFont(int i) {
      return createFont(i, 1);
   }

   static Font createFont(int i, int j) {
      return new Font("SanSerif", j, i == 0 ? getDefaultFontSize() : i);
   }

   public static int getDefaultFontSize() {
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      int i = dimension.height / 64;
      int j = dimension.width / 100;
      return i < j ? i : j;
   }

   @Override
   public void show() {
      if (!this.disposed) {
         this.pack();
         this.setLocation(centeredLocation(this.getSize()));
         this.setResizable(false);
         this.selectedButton = -1;
         super.show();
      }
   }

   @Override
   public void dispose() {
      if (!this.disposed) {
         if (!SwingUtilities.isEventDispatchThread()) {
            super.hide();
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  ProgressDialog.this.dispose();
               }
            });
         } else {
            this.disposed = true;
            super.dispose();
            this.ownerFrame.dispose();
         }
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getID() == 1001) {
         if (actionevent.getSource() instanceof JButton) {
            String s = actionevent.getActionCommand();
            this.selectedButton = 0;

            while (this.selectedButton < this.buttons.length && !s.equals(this.buttons[this.selectedButton].getActionCommand())) {
               this.selectedButton++;
            }

            if (this.selectedButton == this.buttons.length) {
               this.selectedButton = -1;
            }
         }

         this.dispose();
      } else if (actionevent.getID() == 201) {
         if (actionevent.getSource() == this) {
            this.selectedButton = -1;
            this.dispose();
         }
      } else if (actionevent.getID() == 401) {
      }
   }

   public void setMessage(String s) {
      this.textArea.setText(unescape(s));
      this.invalidate();
      this.setSize(this.getPreferredSize());
      this.validate();
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      dimension.width = dimension.width + this.marginX * 2;
      dimension.height = dimension.height + this.marginY * 2;
      return dimension;
   }

   public Dimension adjustSize(Dimension dimension) {
      return dimension;
   }

   @Override
   public boolean handleEvent(Event event) {
      if (event.id == 1001) {
         int i = this.buttons == null ? 0 : this.buttons.length;

         for (int j = 0; j < i; j++) {
            if (event.target == this.buttons[j]) {
               this.selectedButton = j;
               this.dispose();
               return true;
            }
         }
      }

      return super.handleEvent(event);
   }

   static Point centeredLocation(Dimension dimension) {
      Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
      return new Point((dimension1.width - dimension.width) / 2, (dimension1.height - dimension.height) / 2);
   }

   static String unescape(String s) {
      String s1 = "";
      String s2 = System.getProperty("line.separator", "\n");

      int i;
      while ((i = s.indexOf("\\")) != -1) {
         String s3 = s1 + s.substring(0, i);
         if (s.length() < i + 2) {
            return s3;
         }

         char c0 = s.charAt(i + 1);
         s = s.substring(i + 2);
         if (c0 == 'n') {
            s1 = s3 + '\n';
         } else {
            s1 = s3 + c0;
         }
      }

      return s1 + s;
   }
}
