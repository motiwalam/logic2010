package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

class SizedPanel extends JPanel implements FocusPreference, ModuleComponentMarker {
   private Dimension sizeLimit;
   private boolean maximumOnly = false;
   boolean focusableFlag;

   SizedPanel() {
      this(null);
   }

   SizedPanel(int i) {
      this(new Dimension(i, -1));
   }

   SizedPanel(int i, int j) {
      this(new Dimension(i, j));
   }

   SizedPanel(Dimension dimension) {
      this.sizeLimit = dimension;
      this.focusableFlag = true;
      this.setLayout(new BorderLayout());
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
   }

   void setLimitWidth(int i) {
      if (this.sizeLimit == null) {
         this.sizeLimit = new Dimension(i, -1);
      } else {
         this.sizeLimit.width = i;
      }
   }

   void setLimitHeight(int i) {
      if (this.sizeLimit == null) {
         this.sizeLimit = new Dimension(-1, i);
      } else {
         this.sizeLimit.height = i;
      }
   }

   void setSizeLimit(Dimension dimension) {
      this.sizeLimit = dimension;
   }

   void setMaximumOnly(boolean flag) {
      this.maximumOnly = flag;
   }

   boolean isMaximumOnly() {
      return this.maximumOnly;
   }

   Dimension getSizeLimit() {
      return this.sizeLimit;
   }

   Dimension constrain(Dimension dimension) {
      if (this.sizeLimit != null && dimension != null) {
         if (this.sizeLimit.width >= 0 && (!this.maximumOnly || dimension.width > this.sizeLimit.width)) {
            dimension.width = this.sizeLimit.width;
         }

         if (this.sizeLimit.height >= 0 && (!this.maximumOnly || dimension.height > this.sizeLimit.height)) {
            dimension.height = this.sizeLimit.height;
         }
      }

      return dimension;
   }

   Rectangle constrain(Rectangle rectangle) {
      if (this.sizeLimit != null && rectangle != null) {
         if (this.sizeLimit.width >= 0 && (!this.maximumOnly || rectangle.width > this.sizeLimit.width)) {
            rectangle.width = this.sizeLimit.width;
         }

         if (this.sizeLimit.height >= 0 && (!this.maximumOnly || rectangle.height > this.sizeLimit.height)) {
            rectangle.height = this.sizeLimit.height;
         }
      }

      return rectangle;
   }

   @Override
   public Dimension getPreferredSize() {
      return this.constrain(super.getPreferredSize());
   }

   public Dimension getConstrainedPreferredSize(Dimension dimension) {
      return this.constrain(super.getPreferredSize());
   }

   @Override
   public void setSize(int i, int j) {
      Dimension dimension = this.constrain(new Dimension(i, j));
      super.setSize(dimension.width, dimension.height);
   }

   @Override
   public void setSize(Dimension dimension) {
      super.setSize(this.constrain(dimension));
   }

   @Override
   public void setBounds(Rectangle rectangle) {
      super.setBounds(this.constrain(rectangle));
   }

   @Override
   public void setBounds(int i, int j, int k, int l) {
      Rectangle rectangle = this.constrain(new Rectangle(i, j, k, l));
      super.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
   }

   @Override
   public void setFocusable(boolean flag) {
      this.focusableFlag = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean wantsFocus() {
      return this.focusableFlag;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
