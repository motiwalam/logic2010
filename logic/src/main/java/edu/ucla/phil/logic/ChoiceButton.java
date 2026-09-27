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

class ChoiceButton extends JButton implements ActionListener, MenuKeyListener {
   Vector listeners;
   ChoiceList choiceList;
   String[] choices;
   String placeholder;
   int selectedIndex;
   Object userData;
   Color[] colors;
   Color savedForeground;
   Color savedBackground;
   JPopupMenu popup;
   boolean showPlaceholder;
   boolean sizeToWidest;
   boolean locked;
   static boolean useListPopup = false;

   ChoiceButton() {
   }

   ChoiceButton(String[] astring) {
      this(astring, null, 0, true);
   }

   ChoiceButton(String[] astring, int i) {
      this(astring, null, i, true);
   }

   ChoiceButton(String[] astring, String s, int i) {
      this(astring, s, i, true);
   }

   ChoiceButton(String[] astring, String s, int i, boolean flag) {
      this.choices = copyChoices(astring);
      this.placeholder = s;
      this.selectedIndex = i;
      this.showPlaceholder = false;
      this.sizeToWidest = false;
      if (flag) {
         this.setMargin(new Insets(0, 0, 0, 0));
      }

      this.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
      this.userData = null;
      this.colors = null;
      this.locked = false;
      this.setText(this.getDisplayText());
      this.setBackground(null);
      this.setForeground(null);
      if (useListPopup) {
         this.choiceList = new ChoiceList(astring, this);
      }

      this.addActionListener(this);
      this.listeners = new Vector();
   }

   ChoiceButton(String[] astring, String s) {
      this(astring, s, -1, true);
   }

   void setColors(Color color, Color color1) {
      this.setForeground(color);
      this.setBackground(color1);
   }

   void setUserData(Object object) {
      this.userData = object;
   }

   Object getUserData() {
      return this.userData;
   }

   void setLocked(boolean flag) {
      this.locked = flag;
   }

   boolean isLocked() {
      return this.locked;
   }

   void setSelectedIndex(int i) {
      this.setSelectedIndex(i, true);
   }

   void setSelectedIndex(int i, boolean flag) {
      int j = this.selectedIndex;
      this.selectedIndex = i;
      this.setText(this.getDisplayText());
      this.invalidateParent();
      if (flag) {
         int k = this.listeners.size();

         for (int l = 0; l < k; l++) {
            ((ChoiceListener)this.listeners.elementAt(l)).choiceChanged(this, j, i);
         }
      }
   }

   int getSelectedIndex() {
      return this.selectedIndex;
   }

   void setChoices(String[] astring, int i) {
      this.setChoices(astring, null, i);
   }

   void setChoices(String[] astring, String s, int i) {
      this.choices = copyChoices(astring);
      this.placeholder = s;
      this.selectedIndex = i;
      this.setText(this.getDisplayText());
      if (useListPopup) {
         this.choiceList = new ChoiceList(astring, this);
      }

      this.invalidateParent();
   }

   String getChoice(int i) {
      if (i < 0) {
         return this.placeholder;
      } else {
         return this.choices != null && i < this.choices.length ? this.choices[i] : null;
      }
   }

   void setSizeToWidest(boolean flag) {
      this.sizeToWidest = flag;
      this.invalidateParent();
   }

   void setShowPlaceholder(boolean flag) {
      this.showPlaceholder = flag;
      this.setText(this.getDisplayText());
   }

   String getDisplayText() {
      if (this.showPlaceholder) {
         return this.placeholder == null ? "none" : this.placeholder;
      } else if (this.choices == null || this.choices.length == 0) {
         return "none";
      } else if (this.selectedIndex < 0) {
         return this.placeholder == null ? "bad index" : this.placeholder;
      } else {
         return this.selectedIndex < this.choices.length ? this.choices[this.selectedIndex] : "bad index";
      }
   }

   static String[] copyChoices(String[] astring) {
      int i = astring.length;
      String[] astring1 = new String[i];
      System.arraycopy(astring, 0, astring1, 0, i);
      return astring1;
   }

   void invalidateParent() {
      JComponent jcomponent = (JComponent)this.getParent();
      if (jcomponent == null) {
         this.invalidate();
      } else {
         jcomponent.invalidate();
      }
   }

   void addChoiceListener(ChoiceListener choicelistener) {
      if (!this.listeners.contains(choicelistener)) {
         this.listeners.addElement(choicelistener);
      }
   }

   void removeChoiceListener(ChoiceListener choicelistener) {
      if (this.listeners.contains(choicelistener)) {
         this.listeners.removeElement(choicelistener);
      }
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      Graphics graphics;
      if (this.sizeToWidest && (graphics = this.getGraphics()) != null) {
         FontMetrics fontmetrics = graphics.getFontMetrics();
         int i = fontmetrics.stringWidth(this.getDisplayText());
         int j = this.placeholder == null ? 0 : fontmetrics.stringWidth(this.placeholder);

         for (int k = 0; k < this.choices.length; k++) {
            int l = fontmetrics.stringWidth(this.choices[k]);
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
         this.popup.setVisible(false);
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
         if (!this.locked) {
            this.showPopup();
         }
      } else if (object instanceof JMenuItem) {
         int i = this.popup.getComponentIndex((JMenuItem)object);
         if (i != -1) {
            this.setSelectedIndex(i);
         }

         this.popup.setVisible(false);
      }
   }

   void showPopup() {
      if (useListPopup) {
         this.popup = new JPopupMenu();
         this.popup.add(this.choiceList);
         this.popup.pack();
         this.popup.show(this, this.getWidth(), this.selectedIndex == -1 ? 0 : -this.choiceList.indexToLocation(this.selectedIndex).y);
      } else {
         this.popup = new JPopupMenu();
         int i = this.choices.length;

         for (int j = 0; j < i; j++) {
            JMenuItem jmenuitem = this.popup.add(new SmallMenuItem(this.choices[j]));
            jmenuitem.addActionListener(this);
            jmenuitem.addMenuKeyListener(this);
         }

         int l = 0;
         if (this.selectedIndex != -1) {
            this.popup.doLayout();
            Insets insets = ((JMenuItem)this.popup.getComponent(this.selectedIndex)).getMargin();
            int k = this.getGraphics().getFontMetrics().getHeight();
            l = (k + insets.top + insets.bottom) * this.selectedIndex + insets.top;
         }

         this.popup.show(this, this.getWidth(), -l);
      }
   }

   boolean isPopupVisible() {
      return this.popup == null ? false : this.popup.isVisible();
   }

   static {
      UIManager.put("MenuItem.selectionBackground", new Color(184, 207, 229));
   }
}
