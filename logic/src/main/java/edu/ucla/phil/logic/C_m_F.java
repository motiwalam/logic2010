package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;

class C_m_F extends CellPanel implements LogicConstants, C_F, C_LC {
   static String[] f1285 = LogicProgram.symbols;
   C_k_E f1286;
   C_HA f1287;
   int f1288;
   int f1289;
   boolean f1290;
   CellPanel f1291;
   C_ZE f1292;
   CellPanel[] f1293;
   CellPanel f1294;
   SizedPanel f1295;
   C_a_B f1296;
   C_a_B f1297;
   C_u_ f1298;

   C_m_F(C_k_E c_k_e) {
      this.f1286 = c_k_e;
      this.f1287 = c_k_e.f1214;
      this.f1290 = false;
      this.f1288 = this.f1287 == null ? 0 : this.f1287.f379.size();
      this.f1289 = this.f1288 == 0 ? 0 : 1 << this.f1288;
      this.f1293 = new CellPanel[this.f1289];
      this.setLayout(new BorderLayout());
      this.add(this.f1291 = new CellPanel(), "North");
      this.f1291.setBackground(LogicConstants.bruinBlue);
      this.m1950();
      this.add(this.f1294 = new CellPanel(), "Center");
      this.f1294.setLayout(new C_m_A());
      this.f1294.setBorder(BorderFactory.createEtchedBorder());

      for (int i = 0; i < this.f1289; i++) {
         CellPanel cellpanel = new CellPanel();
         String s = C_XF.m1523(i, this.f1288);

         for (int j = 0; j < this.f1288; j++) {
            C_b_A c_b_a;
            cellpanel.add(c_b_a = new C_b_A(new String[]{"T", "F"}, "?"));
            c_b_a.setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black));
            c_b_a.m1645(new Integer(i >> this.f1288 - j - 1 & 1));
            c_b_a.m1659(this);
         }

         this.f1294.add(this.f1293[i] = cellpanel);
      }

      this.m1959();
      Color[] acolor = c_k_e.f1200.colors;
      this.f1295 = new SizedPanel();
      C_QF c_qf;
      this.f1295.setLayout(c_qf = new C_QF(2, 2, 0, 0, true, false));
      c_qf.setVgap(1);
      C_ZE c_ze;
      this.f1295.add(c_ze = new C_ZE("Sentence Letters: "));
      c_ze.setFocusable(false);
      this.f1295.add(this.f1296 = new C_a_B("", 100));
      this.f1296.setForeground(acolor[1]);
      this.f1296.setBackground(acolor[0]);
      this.f1295.add(c_ze = new C_ZE("Number of Rows: "));
      c_ze.setFocusable(false);
      this.f1295.add(this.f1297 = new C_a_B("", 50));
      this.f1297.setForeground(acolor[1]);
      this.f1297.setBackground(acolor[0]);
   }

   void m1950() {
      this.f1291.removeAll();

      for (int i = 0; i < this.f1288; i++) {
         Expression expression = (Expression)this.f1287.f379.elementAt(i);
         this.f1291.add(this.f1292 = new C_ZE(LogicProgram.m995(expression.toString(), maggie, f1285)));
         this.f1292.setHorizontalAlignment(0);
      }
   }

   void m1951() {
      this.f1286.f1210.m1203(1);
      this.f1286.f1201.m1521(null);
      this.m1950();
      this.removeAll();
      this.add(this.f1291, "North");
      this.add(this.f1294, "Center");
      this.validate();
   }

   void m1952(String s) {
      if (!(this.f1290 = s != null)) {
         this.f1286.f1219 = false;
         this.f1286.f1210.m1203(0);
         this.f1296.setText("");
         this.f1297.setText("");
         this.removeAll();
         this.add(this.f1295, "Center");
         this.validate();
      } else {
         int i = s.indexOf(58);
         String s1;
         if (this.f1286.f1219 = i == -1) {
            s1 = s;
            s = null;
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         this.f1287.m673(s1);
         if (this.f1286.f1219) {
            return;
         }
      }

      int l = 0;
      int i1 = s == null ? 0 : s.length();

      for (int j = 0; j < this.f1289; j++) {
         CellPanel cellpanel = this.f1293[j];

         for (int k = 0; k < this.f1288; k++) {
            C_b_A c_b_a = (C_b_A)cellpanel.getComponent(k);
            c_b_a.m1650(l < i1 ? "TF".indexOf(s.charAt(l++)) : -1, false);
            this.m1958(c_b_a, false);
         }
      }
   }

   String m1953() {
      if (!this.f1290) {
         return null;
      } else {
         String s = "";

         for (int i = 0; i < this.f1288; i++) {
            s = s + (i == 0 ? "" : ".") + this.f1287.f379.elementAt(i);
         }

         if (this.f1286.f1219) {
            return s;
         } else {
            s = s + ":";
            String s1 = "";
            boolean flag = false;

            for (int l = 0; l < this.f1289; l++) {
               CellPanel cellpanel = this.f1293[l];

               for (int j = 0; j < this.f1288; j++) {
                  int k = ((C_b_A)cellpanel.getComponent(j)).m1651();
                  s = s + "?TF".charAt(k + 1);
                  if (k != -1) {
                     flag = true;
                  }
               }
            }

            return flag ? s + s1 : s;
         }
      }
   }

   boolean m1954() {
      return this.m1953() != null;
   }

   boolean m1955() {
      return this.m1956().f427 == null;
   }

   ErrorRef m1956() {
      if (this.f1286.f1219) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else if (this.f1286.f1200.completeSetup && !this.f1290) {
         return new ErrorRef("truerr020", Message.params("summary", "Incomplete"));
      } else {
         boolean flag = false;
         boolean flag1 = false;

         for (int i = 0; i < this.f1289; i++) {
            CellPanel cellpanel = this.f1293[i];

            for (int j = 0; j < this.f1288; j++) {
               C_b_A c_b_a = (C_b_A)cellpanel.getComponent(j);
               int k = c_b_a.m1651();
               if (k == -1) {
                  flag = true;
               } else if (k != (Integer)c_b_a.m1646()) {
                  flag1 = true;
               }
            }
         }

         if (flag1) {
            return new ErrorRef("truerr011", Message.params("summary", "Incorrect"));
         } else {
            return flag ? new ErrorRef("truerr012", Message.params("summary", "Incomplete")) : new ErrorRef(null, Message.params("summary", "Correct"));
         }
      }
   }

   ErrorRef m1957() {
      if (this.f1290) {
         return new ErrorRef(null, Message.params("summary", "Correct"));
      } else {
         String s = LogicProgram.m995(this.f1296.getText(), f1285, maggie);
         ErrorRef errorref = this.f1287.m673(s);
         if (errorref.f427 != null) {
            return (ErrorRef)errorref.m718("summary", "Incorrect");
         } else {
            Integer integer = LogicProgram.parseInteger(this.f1297.getText());
            return integer != null && integer == this.f1289
               ? (ErrorRef)errorref.m718("summary", "Correct")
               : new ErrorRef("truerr019", Message.params("summary", "Incorrect"));
         }
      }
   }

   @Override
   public void m514(C_b_A c_b_a, int i, int j) {
      this.m1958(c_b_a, true);
   }

   void m1958(C_b_A c_b_a, boolean flag) {
      if (!this.f1286.f1200.setupErrorsDisabled) {
         int i = c_b_a.m1651();
         Color[] acolor = this.f1286.f1200.colors;
         if (i >= 0 && i != (Integer)c_b_a.m1646()) {
            if (this.f1286.f1200.forPrint) {
               c_b_a.setFont(this.f1286.f1200.errorFont);
            } else {
               c_b_a.m1644(acolor[4], acolor[3]);
               if (flag) {
                  Toolkit.getDefaultToolkit().beep();
               }
            }
         } else if (this.f1286.f1200.forPrint) {
            c_b_a.setFont(this.f1286.f1200.font);
         } else {
            c_b_a.m1644(acolor[0], acolor[1]);
         }
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.m1959();
   }

   void m1959() {
      if (this.f1288 != 0) {
         int[] aint = new int[this.f1288];

         for (int i = 0; i < this.f1288; i++) {
            aint[i] = this.f1291.getComponent(i).getPreferredSize().width + 10;
         }

         C_u_ c_u_ = new C_u_(this.f1288);
         c_u_.m2094(aint);
         this.f1291.setLayout(c_u_);

         for (int j = 0; j < this.f1289; j++) {
            this.f1293[j].setLayout(c_u_);
         }
      }
   }
}
