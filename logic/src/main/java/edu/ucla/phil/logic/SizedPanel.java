package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

class SizedPanel extends JPanel implements C_TE, C_LC {
   private Dimension f503;
   private boolean f504 = false;
   boolean f505;

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
      this.f503 = dimension;
      this.f505 = true;
      this.setLayout(new BorderLayout());
      this.setBackground(null);
      this.setForeground(null);
      this.enableEvents(8L);
   }

   void m934(int i) {
      if (this.f503 == null) {
         this.f503 = new Dimension(i, -1);
      } else {
         this.f503.width = i;
      }
   }

   void m935(int i) {
      if (this.f503 == null) {
         this.f503 = new Dimension(-1, i);
      } else {
         this.f503.height = i;
      }
   }

   void m936(Dimension dimension) {
      this.f503 = dimension;
   }

   void m937(boolean flag) {
      this.f504 = flag;
   }

   boolean m938() {
      return this.f504;
   }

   Dimension m939() {
      return this.f503;
   }

   Dimension m940(Dimension dimension) {
      if (this.f503 != null && dimension != null) {
         if (this.f503.width >= 0 && (!this.f504 || dimension.width > this.f503.width)) {
            dimension.width = this.f503.width;
         }

         if (this.f503.height >= 0 && (!this.f504 || dimension.height > this.f503.height)) {
            dimension.height = this.f503.height;
         }
      }

      return dimension;
   }

   Rectangle m941(Rectangle rectangle) {
      if (this.f503 != null && rectangle != null) {
         if (this.f503.width >= 0 && (!this.f504 || rectangle.width > this.f503.width)) {
            rectangle.width = this.f503.width;
         }

         if (this.f503.height >= 0 && (!this.f504 || rectangle.height > this.f503.height)) {
            rectangle.height = this.f503.height;
         }
      }

      return rectangle;
   }

   @Override
   public Dimension getPreferredSize() {
      return this.m940(super.getPreferredSize());
   }

   public Dimension m942(Dimension dimension) {
      return this.m940(super.getPreferredSize());
   }

   @Override
   public void setSize(int i, int j) {
      Dimension dimension = this.m940(new Dimension(i, j));
      super.setSize(dimension.width, dimension.height);
   }

   @Override
   public void setSize(Dimension dimension) {
      super.setSize(this.m940(dimension));
   }

   @Override
   public void setBounds(Rectangle rectangle) {
      super.setBounds(this.m941(rectangle));
   }

   @Override
   public void setBounds(int i, int j, int k, int l) {
      Rectangle rectangle = this.m941(new Rectangle(i, j, k, l));
      super.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
   }

   @Override
   public void setFocusable(boolean flag) {
      this.f505 = flag;
      super.setFocusable(flag);
   }

   @Override
   public boolean m943() {
      return this.f505;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
