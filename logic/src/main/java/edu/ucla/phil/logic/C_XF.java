package edu.ucla.phil.logic;

import java.awt.Point;
import java.util.Hashtable;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class C_XF extends SizedPanel implements LogicConstants {
   static String[] f882 = LogicProgram.symbols;
   C_k_E f883;
   JScrollPane f884;
   CellPanel f885;
   CellPanel f886;
   CellPanel[] f887;
   C_u_ f888;
   C_x_B[] f889;
   C_GD[][] f890;
   Point f891;
   int f892;
   int f893;
   int f894;

   C_XF(C_k_E c_k_e) {
      this.f883 = c_k_e;
      this.setLayout(new C_m_A());
      this.add(this.f885 = new CellPanel());
      this.add(this.f884 = new JScrollPane(this.f886 = new CellPanel()));
      this.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.f886.setLayout(new C_m_A());
      this.f886.setBackground(c_k_e.f1200.colors[1]);
      this.f886.setForeground(c_k_e.f1200.colors[0]);
      this.f892 = -1;
      this.m1521(null);
   }

   void m1520() {
      this.f885.removeAll();

      for (int i = 0; i < this.f883.f1217; i++) {
         Expression expression = (Expression)this.f883.f1214.f379.elementAt(i);
         this.f885.add(new C_ZE(LogicProgram.m995(expression.toString(), maggie, f882)));
      }

      for (int j = 0; j < this.f883.f1216; j++) {
         C_ZE c_ze;
         this.f885.add(c_ze = new C_ZE("Pr" + (j + 1)));
         c_ze.setHorizontalAlignment(0);
      }

      C_ZE c_ze1;
      if (this.f883.f1218 != null && !this.f883.f1218.f829) {
         this.f885.add(c_ze1 = new C_ZE("Conc"));
      } else {
         this.f885.add(c_ze1 = new C_ZE("Form"));
      }

      c_ze1.setHorizontalAlignment(0);
      this.f885.add(new C_ZE(""));
   }

   void m1521(Hashtable hashtable) {
      this.f885.removeAll();
      this.f885.invalidate();
      this.f886.removeAll();
      this.f886.invalidate();
      this.f893 = 2 + this.f883.f1217 + this.f883.f1216;
      this.f894 = this.f883.f1217 == 0 ? 0 : 1 << this.f883.f1217;
      this.f891 = null;
      this.f887 = new CellPanel[this.f894];
      this.f890 = new C_GD[this.f894][this.f883.f1216 + 1];
      this.f889 = new C_x_B[this.f894];
      this.m1520();

      for (int i = 0; i < this.f894; i++) {
         CellPanel cellpanel = new CellPanel();
         String s = m1523(i, this.f883.f1217);

         for (int j = 0; j < this.f883.f1217; j++) {
            C_ZE c_ze = new C_ZE(s.substring(j, j + 1));
            cellpanel.add(c_ze);
         }

         String[] astring = hashtable == null ? null : (String[])hashtable.get(s);

         for (int k = 0; k <= this.f883.f1216; k++) {
            String s1 = astring == null ? null : astring[k];
            if (s1 == null) {
               s1 = "+?";
            }

            cellpanel.add(this.f890[i][k] = new C_GD(s1, this.f883, new Point(k, i)));
         }

         if (!this.f883.f1200.assumeTautology) {
            cellpanel.add(this.f889[i] = new C_x_B(this, i));
         }

         this.f886.add(this.f887[i] = cellpanel);
      }

      if (!this.f883.f1200.assumeTautology) {
         if (this.f892 != -1 && this.f892 < this.f894) {
            this.f889[this.f892].setSelected(true);
         }

         this.f883.f1209.f1033.m1649(this.f883.f1215);
      }

      this.m1522();
      this.setVisible(this.f894 != 0);
   }

   void m1522() {
      int[] aint = new int[this.f893];

      for (int i = 0; i < this.f893 - 1; i++) {
         aint[i] = this.f885.getComponent(i).getPreferredSize().width + 10;
      }

      aint[this.f893 - 1] = this.f883.f1200.assumeTautology ? 0 : C_g_F.m1833() + 8;
      this.f888 = new C_u_(this.f893);
      this.f888.m2094(aint);
      this.f885.setLayout(this.f888);

      for (int j = 0; j < this.f894; j++) {
         this.f887[j].setLayout(this.f888);
      }
   }

   static String m1523(int i, int j) {
      if (i < 0) {
         return null;
      } else {
         String s = "";

         for (int k = 0; k < j; k++) {
            s = (i % 2 == 0 ? "T" : "F") + s;
            i /= 2;
         }

         return s;
      }
   }

   Hashtable m1524() {
      Hashtable hashtable = new Hashtable();

      for (int i = 0; i < this.f894; i++) {
         String[] astring = new String[this.f883.f1216 + 1];

         for (int j = 0; j <= this.f883.f1216; j++) {
            astring[j] = this.f890[i][j].m636();
         }

         hashtable.put(m1523(i, this.f883.f1217), astring);
      }

      return hashtable;
   }

   boolean m1525(int i) {
      int j = this.f883.f1216 + 1;
      C_GD[] ac_gd = this.f890[i];

      for (int k = 0; k < j; k++) {
         if (ac_gd[k].f360.m1406()) {
            return true;
         }
      }

      return false;
   }
}
