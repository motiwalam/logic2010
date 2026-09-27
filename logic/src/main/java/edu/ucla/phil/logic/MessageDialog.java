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

class MessageDialog extends BaseDialog implements LogicConstants, ActionListener {
   static JFrame hiddenOwner = null;
   boolean disposeOwner;
   boolean restoreLocation;
   boolean sizeSet;
   boolean packOnShow = false;
   int selectedButton;
   ActionButton[] buttons;
   Component content;
   Vector handlers;
   static Hashtable savedBounds = new Hashtable();
   String boundsKey;

   MessageDialog(Frame frame, String s, Component component, String[] astring) {
      super(resolveOwner(frame, s), s, true);
      this.sizeSet = false;
      this.disposeOwner = frame == null && this.ownerFrame != hiddenOwner;
      this.restoreLocation = true;
      this.selectedButton = -1;
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.add(this.content = component, "Center");
      JRootPane jrootpane = this.getRootPane();
      jrootpane.getInputMap(1).put(KeyStroke.getKeyStroke(27, 0), "CloseOnEsc");
      jrootpane.getActionMap().put("CloseOnEsc", new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            MessageDialog.this.selectedButton = -1;
            MessageDialog.this.close();
         }
      });
      if (astring == null) {
         this.buttons = null;
      } else {
         this.buttons = new ActionButton[astring.length];
         JPanel jpanel = new JPanel();
         jpanel.setLayout(new AlignedFlowLayout(1, 0, 5, 5));

         for (int i = 0; i < this.buttons.length; i++) {
            jpanel.add(this.buttons[i] = new ActionButton(astring[i]));
            this.buttons[i].addActionListener(this);
         }

         this.add(jpanel, "South");
         if (this.buttons.length > 0) {
            this.buttons[0].requestFocus();
         }
      }

      this.handlers = null;
      this.boundsKey = null;
      this.pack();
   }

   static synchronized Frame resolveOwner(Frame frame, String s) {
      if (frame != null) {
         return frame;
      } else if (LogicProgram.mainMenu == null) {
         if (hiddenOwner == null) {
            hiddenOwner = new JFrame();
            hiddenOwner.setIconImages(LogicProgram.iconImages);
            hiddenOwner.setUndecorated(true);
            hiddenOwner.setVisible(true);
            hiddenOwner.setLocationRelativeTo(null);
         }

         if (s != null) {
            hiddenOwner.setTitle(s);
         }

         return hiddenOwner;
      } else {
         return new ModuleFrame();
      }
   }

   static synchronized void disposeHiddenOwner() {
      if (hiddenOwner != null) {
         hiddenOwner.dispose();
         hiddenOwner = null;
      }
   }

   @Override
   public void setSize(Dimension dimension) {
      super.setSize(dimension);
      this.sizeSet = true;
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (actionevent.getID() == 1001) {
         if (actionevent.getSource() instanceof JButton && LogicProgram.isDescendant(this, (Component)actionevent.getSource())) {
            this.selectedButton = 0;

            while (this.selectedButton < this.buttons.length && !actionevent.getActionCommand().equals(this.buttons[this.selectedButton].getActionCommand())) {
               this.selectedButton++;
            }

            if (this.selectedButton == this.buttons.length || this.runHandlers()) {
               this.close();
            }
         }
      } else if (actionevent.getID() == 201 && actionevent.getSource() == this) {
         this.selectedButton = -1;
         this.close();
      }
   }

   void setButtons(ActionButton[] aactionbutton) {
      this.buttons = aactionbutton;
   }

   void setDefaultButtonIndex(int i) {
      if (i < 0) {
         this.getRootPane().setDefaultButton(null);
      } else {
         this.buttons[i].setDefaultCapable(true);
         this.getRootPane().setDefaultButton(this.buttons[i]);
      }
   }

   void addHandler(DialogHandler dialoghandler) {
      if (dialoghandler != null) {
         if (this.handlers == null) {
            this.handlers = new Vector();
         }

         this.handlers.addElement(dialoghandler);
      }
   }

   void setButtonTip(String s, String s1) {
      for (int i = 0; i < this.buttons.length; i++) {
         ActionButton actionbutton;
         if ((actionbutton = this.buttons[i]) != null && s.equalsIgnoreCase(actionbutton.getText())) {
            actionbutton.setHelpText(s1);
            break;
         }
      }
   }

   void setBoundsKey(String s) {
      this.sizeSet = false;
      this.boundsKey = s;
   }

   String getBoundsKey() {
      return this.boundsKey;
   }

   void forgetSavedBounds() {
      savedBounds.remove(this.boundsKey);
   }

   Rectangle getSavedBounds() {
      return this.boundsKey == null ? null : (Rectangle)savedBounds.get(this.boundsKey);
   }

   static Point centeredLocation(Dimension dimension) {
      return new Point((LogicProgram.screenSize.width - dimension.width) / 2, (LogicProgram.screenSize.height - dimension.height) / 2);
   }

   void showAt(Point point) {
      this.showAt(point, false);
   }

   void showAt(Point point, boolean flag) {
      Rectangle rectangle;
      if (!this.sizeSet && (rectangle = this.getSavedBounds()) != null) {
         this.setSize(rectangle.getSize());
         if (this.restoreLocation) {
            point = rectangle.getLocation();
         }
      }

      this.setLocation(point == null ? centeredLocation(this.getSize()) : point);
      this.setResizable(flag);
      if (this.packOnShow) {
         this.pack();
      }

      this.setVisible(true);
      if (this.disposeOwner && this.isModal()) {
         this.ownerFrame.dispose();
      }
   }

   void showModeless(Point point, boolean flag) {
      this.setModal(false);
      this.disposeOwner = flag;
      this.showAt(point, true);
   }

   void close() {
      this.dispose();
      if (this.disposeOwner && !this.isModal()) {
         this.ownerFrame.dispose();
      }
   }

   @Override
   public void dispose() {
      if (this.boundsKey != null) {
         Rectangle rectangle = this.getBounds(null);
         savedBounds.put(this.boundsKey, rectangle);
      }

      super.dispose();
   }

   boolean runHandlers() {
      int i = this.handlers == null ? 0 : this.handlers.size();
      boolean flag = true;

      for (int j = 0; j < i; j++) {
         flag &= ((DialogHandler)this.handlers.elementAt(j)).handleChoice(this);
      }

      return flag;
   }

   boolean fillFieldsAndChoose(Vector vector, EditableTextPane[] aeditabletextpane, int i) {
      int j = vector == null ? 0 : vector.size();
      int k = aeditabletextpane == null ? 0 : aeditabletextpane.length;
      if (k < j) {
         j = k;
      }

      k -= j;

      for (int l = 0; l < j; l++) {
         aeditabletextpane[l].setText((String)vector.elementAt(0));
         vector.removeElementAt(0);
         aeditabletextpane[l].select(0, 2147483647);
      }

      if (k == 0 && i != -1) {
         this.selectedButton = i;
         return this.runHandlers();
      } else {
         return false;
      }
   }

   static void showMessage(String s, String s1, Point point, DialogHandler dialoghandler) {
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLimitWidth(LogicProgram.screenSize.width * 3 / 4);
      sizedpanel.setMaximumOnly(true);
      sizedpanel.setLayout(new BorderLayout());
      EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.expandEscapes(s1));
      editabletextpane.setWrapLines(true);
      editabletextpane.setWrapWords(true);
      editabletextpane.setEnabled(false);
      editabletextpane.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      editabletextpane.setDisabledTextColor(LogicConstants.bruinBlack);
      editabletextpane.setBackground(LogicConstants.bruinAsh);
      sizedpanel.add(editabletextpane, "North");
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (dialoghandler != null) {
         astring = dialoghandler.getLabels();
         i = dialoghandler.getDefaultIndex();
      }

      MessageDialog messagedialog = new MessageDialog(null, s, sizedpanel, astring);
      messagedialog.pack();
      messagedialog.addHandler(dialoghandler);
      messagedialog.setDefaultButtonIndex(i);
      messagedialog.showAt(point);
   }

   static void showMessage(Message message, Hashtable hashtable, Point point, DialogHandler dialoghandler) {
      String s = message.text;
      if (hashtable != null) {
         s = Message.substitute(s, hashtable);
      }

      showMessage(message.id, s, point, dialoghandler);
   }

   static BaseDialog showNotice(Message message, Hashtable hashtable) {
      String s = message.text;
      if (hashtable != null) {
         s = Message.substitute(s, hashtable);
      }

      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLimitWidth(LogicProgram.screenSize.width * 3 / 4);
      sizedpanel.setMaximumOnly(true);
      EditableTextPane editabletextpane = new EditableTextPane(LogicProgram.expandEscapes(s));
      editabletextpane.setWrapLines(true);
      editabletextpane.setWrapWords(true);
      sizedpanel.add(editabletextpane, "Center");
      BaseDialog basedialog = new BaseDialog(new ModuleFrame(), message.id, false);
      basedialog.add(sizedpanel);
      basedialog.pack();
      basedialog.setLocation(centeredLocation(basedialog.getSize()));
      basedialog.setResizable(false);
      basedialog.show();
      return basedialog;
   }

   static void closeNotice(BaseDialog basedialog) {
      basedialog.dispose();
      basedialog.ownerFrame.dispose();
   }

   static void showScrollingMessage(String s, String s1, Rectangle rectangle, DialogHandler dialoghandler) {
      JScrollPane jscrollpane = new JScrollPane();
      FormulaTextPane formulatextpane = new FormulaTextPane(LogicProgram.expandEscapes(s1));
      formulatextpane.setEditable(false);
      formulatextpane.setBackground(dialogWhite);
      formulatextpane.setCaretPosition(0);
      String[] astring = new String[]{"OK"};
      int i = 0;
      if (dialoghandler != null) {
         astring = dialoghandler.getLabels();
         i = dialoghandler.getDefaultIndex();
      }

      jscrollpane.setViewportView(formulatextpane);
      MessageDialog messagedialog = new MessageDialog(null, s, jscrollpane, astring);
      messagedialog.addHandler(dialoghandler);
      messagedialog.setDefaultButtonIndex(i);
      messagedialog.setBoundsKey("showMessage");
      Dimension dimension = rectangle == null ? new Dimension(300, 200) : rectangle.getSize();
      Point point = rectangle == null ? centeredLocation(dimension) : rectangle.getLocation();
      messagedialog.setSize(dimension);
      messagedialog.showAt(point, true);
   }
}
