package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.border.EtchedBorder;

class C_AD extends C_TA {
   C_AD parent = null;
   C_YF f140;
   C_DD f141 = null;
   C_k_ f142;
   boolean f143 = false;
   C_EB f144;

   C_AD(C_DD c_dd) {
      this((C_EB)null);
      this.m228(c_dd);
   }

   C_AD(C_EB c_eb) {
      this.f144 = c_eb;
      this.setLayout(this.f140 = new C_YF());
      this.add(this.f142 = new C_k_(""));
      this.f140.m1543(this.f142);
   }

   C_AD m224() {
      if (this.parent != null) {
         if (this.f144 != null && !this.parent.f143) {
            this.f144.f294--;
         }

         this.m226(null);
         this.parent.remove(this);
         this.parent = null;
      }

      return this;
   }

   C_AD m225(C_AD c_ad1) {
      if (c_ad1 != null) {
         this.add(c_ad1.m224());
         c_ad1.parent = this;
         if (this.f144 != null && !this.f143) {
            this.f144.f294++;
         }

         c_ad1.m226(this.f144);
      }

      return c_ad1;
   }

   void m226(C_EB c_eb) {
      int j = this.m231();
      if (!this.f143) {
         if (this.f144 != null) {
            this.f144.f294 -= j;
         }

         if (c_eb != null) {
            c_eb.f294 += j;
         }
      }

      this.f144 = c_eb;

      for (int i = 0; i < j; i++) {
         this.m232(i).m226(c_eb);
      }
   }

   void m227(String s) {
      this.m228(new C_DD(s));
      if (this.f141 == null) {
         this.add(this.f142 = new C_k_(s));
         this.f140.m1543(this.f142);
      }

      this.m229(false);
      this.setBorder(new EtchedBorder(1));
      if (this.f144 != null) {
         this.f144.f292 = this;
         this.f144.m494();
      }
   }

   void m228(C_DD c_dd) {
      this.f140.m1543(null);
      if (this.f142 != null) {
         int j = this.m231();
         this.remove(this.f142);
         this.f142 = null;

         for (int i = 0; i < j; i++) {
            ((C_AD)this.getComponent(0)).m224();
         }
      }

      if (c_dd != null && c_dd.f278 != null) {
         this.f141 = c_dd;
         this.add(this.f142 = new C_k_(c_dd));
         this.f140.m1543(this.f142);
         int l = c_dd.m457();

         for (int k = 0; k < l; k++) {
            this.m225(new C_AD(c_dd.m458(k)));
         }

         this.validate();
      } else {
         this.f141 = null;
      }
   }

   void m229(boolean flag) {
      int i = this.m231();
      if (this.f144 != null) {
         if (this.f143 && !flag) {
            if (this.f144.f293.checkNow && this.f144.f294 == 0 && i != 0) {
               this.f144.f291.setText("Incomplete");
            }

            this.f144.f294 += i;
         } else if (!this.f143 && flag) {
            this.f144.f294 -= i;
            if (this.f144.f293.checkNow && this.f144.f294 == 0 && i != 0) {
               this.f144.f291.setText("Complete");
            }
         }

         this.f144.validate();
      }

      this.f143 = flag;

      for (int j = 0; j < i; j++) {
         C_AD c_ad1 = this.m232(j);
         c_ad1.setVisible(flag);
         if (!flag) {
            c_ad1.m229(false);
         }
      }
   }

   String m230(String s) {
      if (s == null) {
         s = "";
      }

      int i = s.indexOf(",");
      String s1;
      if (i == -1) {
         s1 = s;
         s = "";
      } else {
         s1 = s.substring(0, i);
         s = s.substring(i + 1);
      }

      int j;
      try {
         j = Integer.parseInt(s1.trim());
      } catch (NumberFormatException numberformatexception) {
         j = 0;
      }

      this.m229(j != 0);

      for (int k = 0; k < j; k++) {
         try {
            s = this.m232(k).m230(s);
         } catch (ClassCastException classcastexception) {
         }
      }

      return s;
   }

   int m231() {
      return this.f142 == null ? 0 : this.getComponentCount() - 1;
   }

   C_AD m232(int i) {
      Component component = this.getComponent(i + 1);
      return component instanceof C_AD ? (C_AD)component : null;
   }

   String m233(boolean flag) {
      if (!flag && !this.f143) {
         return "0";
      } else {
         int i = this.m231();
         String s = "" + i;

         for (int j = 0; j < i; j++) {
            s = s + "," + this.m232(j).m233(flag);
         }

         return s;
      }
   }

   @Override
   public void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      if (!this.f144.f293.noDescent) {
         int i = this.getComponentCount();
         int j = 0;
         if (i != 0 && this.f142 != null && !this.f142.getText().isEmpty()) {
            Rectangle rectangle = this.getComponent(0).getBounds();
            graphics.setColor(this.getForeground());
            int i1;
            int k1;
            int j1 = k1 = i1 = rectangle.x + this.f142.m386();
            int l = rectangle.y + rectangle.height;
            int k = ((C_YF)this.getLayout()).getVgap();

            for (int l1 = 1; l1 < i; l1++) {
               Component component = this.getComponent(l1);
               if (component.isVisible()) {
                  j++;
                  rectangle = component.getBounds();
                  int i2 = rectangle.x + rectangle.width / 2;
                  if (i2 < j1) {
                     j1 = i2;
                  }

                  if (i2 > k1) {
                     k1 = i2;
                  }

                  graphics.drawLine(i2, l + k / 2, i2, l + k);
               }
            }

            if (j != 0) {
               graphics.drawLine(i1, l, i1, l + k / 2);
               graphics.drawLine(j1, l + k / 2, k1, l + k / 2);
            }
         }
      }
   }
}
