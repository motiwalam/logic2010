package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class C_BC extends SizedPanel implements PropertyChangeListener, LogicConstants, C_j_F {
   static String[] f237 = LogicProgram.symbols;
   C_VF f238;
   C_DD f239;
   C_NC f240;
   C_b_A f241;

   C_BC(C_VF c_vf, C_DD c_dd) {
      this.f238 = c_vf;
      this.f239 = c_dd;
      this.setLayout(new C_t_C(c_vf.f849 ? 0 : 4));
      this.add(this.f240 = new C_NC(LogicProgram.m995(this.m389(), maggie, f237)));
      this.f240.setSize(this.f240.getPreferredSize());
      this.add(this.f241 = new C_b_A(new String[]{"T", "F"}, "?"));
      this.f241.m1655(true);
      this.f241.setBorder(new C_BB(0, Color.gray, Color.black));
      this.f241.setMargin(new Insets(2, 2, 2, 2));
      this.f241.addPropertyChangeListener(this);
   }

   C_BC(C_VF c_vf, String s, boolean flag) {
      this.f238 = c_vf;
      this.setLayout(new C_t_C(c_vf.f849 ? 0 : 4));
      this.f239 = flag ? new C_DD(s) : null;
      if (this.f239 != null && this.f239.f278 == null) {
         this.f239 = null;
      }

      this.add(this.f240 = new C_NC(LogicProgram.m995(s, maggie, f237)));
      this.add(this.f241 = new C_b_A(new String[]{"T", "F"}, "?"));
      this.f241.m1655(true);
      this.f241.setBorder(new C_BB(0, Color.gray, Color.black));
      this.f241.setMargin(new Insets(2, 2, 2, 2));
      this.f241.addPropertyChangeListener(this);
   }

   C_BC(C_VF c_vf, String s) {
      this(c_vf, s, false);
   }

   C_n_F m382() {
      if (this.f239 == null) {
         return new C_n_F();
      } else {
         C_DD c_dd = this.f239.m471(true);
         if (this.f238.f849) {
            C_n_F c_n_f2 = this.m387();
            C_n_F c_n_f1 = this.m388(c_n_f2.m1974(true));
            return c_n_f2.m1981(-c_n_f1.f1316[0]);
         } else {
            C_n_F c_n_f = this.f239.f278 instanceof ConnectiveFormula ? this.f239.m469() : this.f239.m468();
            C_DD.m487(c_dd.f286, c_dd.f287, c_n_f, false);
            return c_n_f.m1981(-this.f239.m459()[0]);
         }
      }
   }

   Point m383(int i) {
      Point point = this.f240.m1130(i);
      if (point == null) {
         return new Point(0, 0);
      } else {
         return point == null ? new Point(0, 0) : point;
      }
   }

   C_n_F m384() {
      C_n_F c_n_f = this.m382();
      int i = c_n_f.f1314;
      LogicProgram.m996(this.m389(), maggie, f237, c_n_f.f1316);

      for (int j = 0; j < i; j++) {
         c_n_f.f1316[j] = this.m383(c_n_f.f1316[j]).x;
      }

      return c_n_f;
   }

   int m385() {
      if (this.f240 != null && this.f240.m1131() != 0) {
         C_n_F c_n_f = this.m384();
         return c_n_f.f1314 < 2 ? (this.m383(0).x + this.m383(this.f240.m1131()).x) / 2 : (c_n_f.f1316[0] + c_n_f.f1316[1]) / 2;
      } else {
         return 0;
      }
   }

   @Override
   public int m386() {
      int i = this.f241.getPreferredSize().width / 2;
      int j = this.m385();
      return i > j ? i : j;
   }

   C_n_F m387() {
      if (this.f239 == null) {
         return null;
      } else {
         C_DD c_dd = this.f239.m471(true);
         C_n_F c_n_f = this.f239.f278 instanceof ConnectiveFormula ? this.f239.m469() : this.f239.m468();
         C_DD.m487(c_dd.f286, c_dd.f287, c_n_f, false);
         return c_n_f;
      }
   }

   C_n_F m388(C_n_F c_n_f) {
      if (this.f239 == null) {
         return c_n_f;
      } else {
         C_DD c_dd = this.f239.m471(true);
         int[] aint = c_dd.f286;
         int[] aint1 = c_dd.f287;
         int i = aint.length - 1;

         for (byte b0 = 0; b0 < c_n_f.f1314 - 1; b0 += 2) {
            int j = c_n_f.f1316[b0];
            int k = c_n_f.f1316[b0 + 1];
            int l = aint[j];
            int i1 = aint[k];

            while (j > 0 && aint[j - 1] == l && aint1[j - 1] <= aint1[j]) {
               j--;
            }

            while (k < i && aint[k + 1] == i1 && aint1[k + 1] <= aint1[k]) {
               k++;
            }

            c_n_f.f1316[b0] = j;
            c_n_f.f1316[b0 + 1] = k;
         }

         return c_n_f;
      }
   }

   String m389() {
      if (this.f239 == null) {
         return "";
      } else {
         return !this.f238.f849 ? this.f239.toString() : this.m388(this.m387()).m1984(this.f239.m470().f284);
      }
   }

   @Override
   public void propertyChange(PropertyChangeEvent propertychangeevent) {
      if (propertychangeevent.getPropertyName().equals("text")) {
         this.f238.m1413();
         this.f238.m1422().m1411(true);
         this.f238.f846.f358.f1202.invalidate();
         if (this.f238.f847 && !this.f238.f846.f358.f1220) {
            LPTruthAnalysis lptruthanalysis = this.f238.f846.f358.f1200;
            if (!lptruthanalysis.treeErrorsDisabled && !lptruthanalysis.noBeep) {
               Toolkit.getDefaultToolkit().beep();
            }

            lptruthanalysis.errorCount++;
         }
      }
   }

   @Override
   protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      int i = ((C_t_C)this.getLayout()).getVgap();
      if (i != 0) {
         int j = this.getComponentCount();
         if (j >= 2) {
            graphics.setColor(this.getForeground());
            Rectangle rectangle = this.getComponent(1).getBounds();
            int k = rectangle.x + rectangle.width / 2;
            int l = rectangle.y;
            graphics.drawLine(k, l - i, k, l);
         }
      }
   }
}
