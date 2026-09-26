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

public class Notifier extends JDialog implements ActionListener {
   Frame frame;
   JTextArea text;
   JButton[] buttons;
   public int buttonIndex = -1;
   int defaultButton;
   int hMargin = 0;
   int vMargin = 0;
   boolean disposed = false;

   private Notifier(Frame frame, boolean modal) {
      super(frame, modal);
      this.frame = frame;
      this.setLayout(new BorderLayout());
      this.setTitle(frame.getTitle());
   }

   public Notifier(String title, String message, boolean modal) {
      this(title, message, modal ? new String[]{"OK"} : null, 0);
   }

   public Notifier(String title, String message, String[] buttonTitles, int defaultButton) {
      this(new Frame(title), buttonTitles != null);
      this.setFont(new Font("Dialog", 1, 14));
      this.text = new JTextArea(display(message));
      this.text.setAlignmentY(0.0F);
      this.text.setAlignmentX(0.0F);
      this.text.setEditable(false);
      this.add(this.text, "Center");
      if (buttonTitles == null) {
         this.buttons = null;
         this.requestFocus();
      } else {
         int count = buttonTitles.length;
         this.buttons = new JButton[count];
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new FlowLayout(1));

         for (int i = 0; i < count; i++) {
            buttonPanel.add(this.buttons[i] = new JButton(buttonTitles[i]));
            this.buttons[i].addActionListener(this);
         }

         this.add(buttonPanel, "South");
         this.defaultButton = defaultButton >= 0 && defaultButton < count ? defaultButton : 0;
         if (count > 0) {
            this.buttons[this.defaultButton].requestFocusInWindow();
         }
      }
   }

   public void show(int hMargin, int vMargin) {
      this.hMargin = hMargin;
      this.vMargin = vMargin;
      this.show();
   }

   @Override
   public void show() {
      if (!this.disposed) {
         this.pack();
         this.setLocation(centerRect(this.getSize()));
         this.setResizable(false);
         this.buttonIndex = -1;
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
                  Notifier.this.dispose();
               }
            });
         } else {
            this.disposed = true;
            super.dispose();
            this.frame.dispose();
         }
      }
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
      if (evt.getID() == 1001) {
         if (evt.getSource() instanceof JButton) {
            String actionCommand = evt.getActionCommand();
            this.buttonIndex = 0;

            while (this.buttonIndex < this.buttons.length && !actionCommand.equals(this.buttons[this.buttonIndex].getActionCommand())) {
               this.buttonIndex++;
            }

            if (this.buttonIndex == this.buttons.length) {
               this.buttonIndex = -1;
            }
         }

         this.dispose();
      } else if (evt.getID() == 201) {
         if (evt.getSource() == this) {
            this.buttonIndex = -1;
            this.dispose();
         }
      } else if (evt.getID() == 401) {
      }
   }

   public void setMessage(String message) {
      this.text.setText(display(message));
      this.invalidate();
      this.setSize(this.getPreferredSize());
      this.validate();
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dim = super.getPreferredSize();
      dim.width = dim.width + this.hMargin * 2;
      dim.height = dim.height + this.vMargin * 2;
      return dim;
   }

   public Dimension getPreferredSize(Dimension dim) {
      return dim;
   }

   @Override
   public boolean handleEvent(Event evt) {
      if (evt.id == 1001) {
         int count = this.buttons == null ? 0 : this.buttons.length;

         for (int i = 0; i < count; i++) {
            if (evt.target == this.buttons[i]) {
               this.buttonIndex = i;
               this.dispose();
               return true;
            }
         }
      }

      return super.handleEvent(evt);
   }

   static Point centerRect(Dimension dim) {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      return new Point((screenSize.width - dim.width) / 2, (screenSize.height - dim.height) / 2);
   }

   static String display(String source) {
      String displayed = "";
      String lineSep = System.getProperty("line.separator", "\n");

      int index;
      while ((index = source.indexOf("\\")) != -1) {
         displayed = displayed + source.substring(0, index);
         if (source.length() < index + 2) {
            return displayed;
         }

         char escape = source.charAt(index + 1);
         source = source.substring(index + 2);
         if (escape == 'n') {
            displayed = displayed + '\n';
         } else {
            displayed = displayed + escape;
         }
      }

      return displayed + source;
   }
}
