package edu.ucla.phil.logic;

import java.util.Vector;

class C_DD implements C_n_A {
   C_DD f277 = null;
   C_RF f278;
   boolean f279 = false;
   int f280 = 0;
   Vector f281 = null;
   int f282 = 0;
   int f283 = 0;
   String f284 = null;
   Vector f285 = null;
   int[] f286 = null;
   int[] f287 = null;
   static final String[] f288 = new String[]{"(", ")", " "};
   static final String[] f289 = new String[]{"", "", ""};

   C_DD(C_RF c_rf, boolean flag, int i) {
      this.f278 = c_rf;
      this.f279 = flag;
      this.f280 = i;
      if (c_rf != null) {
         c_rf.m1211(this);
      }
   }

   C_DD(C_RF c_rf, boolean flag) {
      this(c_rf, flag, 0);
   }

   C_DD(C_RF c_rf) {
      this(c_rf, false);
   }

   C_DD(String s) {
      this(m454(s));
      this.f284 = s;
   }

   static C_RF m454(String s) {
      if (s == null) {
         return null;
      } else {
         try {
            return LogicProgram.m1008(s, true, false);
         } catch (C_k_B c_k_b) {
            return null;
         }
      }
   }

   C_DD m455() {
      return this.f277;
   }

   C_RF m456() {
      return this.f278;
   }

   int m457() {
      return this.f281 == null ? 0 : this.f281.size();
   }

   C_DD m458(int i) {
      return i >= 0 && this.f281 != null && i < this.f281.size() ? (C_DD)this.f281.elementAt(i) : null;
   }

   int[] m459() {
      C_DD c_dd1 = this.m471(true);
      return this.f278 == null
         ? new int[]{0, this.f284.length()}
         : m486(c_dd1.f286, c_dd1.f287, this.m466(), this.f283, this.f278 instanceof C_q_F && this.f278.m1216() > 1);
   }

   void m460(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      if (i > 0 && this.f285 == null) {
         this.f285 = new Vector();
      }

      for (int j = 0; j < i; j++) {
         this.f285.addElement(m462(this.m474((C_i_A)vector.elementAt(j))));
      }
   }

   void m461(C__B c__b) {
      int i = c__b == null ? 0 : c__b.f926;
      if (i > 0 && this.f285 == null) {
         this.f285 = new Vector();
      }

      for (int j = 0; j < i; j++) {
         this.f285.addElement(m462(this.m476(C_i_A.m1853(j))));
      }
   }

   static C_n_F m462(Vector vector) {
      C_n_F c_n_f = new C_n_F();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         C_DD c_dd = (C_DD)vector.elementAt(j);
         int[] aint = c_dd.m459();
         aint[1] = aint[0] + c_dd.f278.f739.length();
         c_n_f.m1987(aint);
      }

      return c_n_f;
   }

   @Override
   public String toString() {
      int[] aint = this.m459();
      return this.m470().f284.substring(aint[0], aint[1]);
   }

   C_e_B m463() {
      C_DD c_dd1 = this.m471(true);
      int[] aint = this.m459();
      return new C_e_B(c_dd1.f284, c_dd1.f285).m1762(aint[0], aint[1]);
   }

   String m464() {
      String s = LogicProgram.m995(this.toString(), new String[]{" "}, new String[]{""});
      if (s.equals("")) {
         return "O";
      } else if (this.f278 == null) {
         return "N";
      } else {
         return s.equals(this.f278.m1208()) ? "O" : "I";
      }
   }

   String m465() {
      if (this.f278 == null) {
         return "0";
      } else {
         int i = this.m457();
         String s = "" + i;

         for (int j = 0; j < i; j++) {
            s = s + "," + this.m458(j).m465();
         }

         return s;
      }
   }

   int m466() {
      return this.f282 + (this.f277 == null ? 0 : this.f277.m466());
   }

   int m467() {
      return this.f283;
   }

   C_n_F m468() {
      int i = this.m466();
      return C_n_F.m1971(i, this.f283);
   }

   C_n_F m469() {
      C_n_F c_n_f = this.m468();
      int i = this.f281 == null ? 0 : this.f281.size();

      for (int j = 0; j < i; j++) {
         c_n_f.m1976(this.m458(j).m468());
      }

      return c_n_f;
   }

   C_DD m470() {
      return this.m471(false);
   }

   C_DD m471(boolean flag) {
      C_DD c_dd2 = this;

      while (true) {
         C_DD c_dd1 = c_dd2.f277;
         if (c_dd2.f277 == null) {
            if (flag) {
               c_dd2.m482();
            }

            return c_dd2;
         }

         c_dd2 = c_dd1;
      }
   }

   C_DD m472(C_RF c_rf) {
      if (this.f278 == c_rf) {
         return this;
      } else {
         int i = this.m457();

         for (int j = 0; j < i; j++) {
            C_DD c_dd1;
            if ((c_dd1 = this.m458(j).m472(c_rf)) != null) {
               return c_dd1;
            }
         }

         return null;
      }
   }

   C_e_ m473() {
      if (this.f277 == null) {
         return new C_e_();
      } else {
         C_e_ c_e_ = this.f277.m473();
         c_e_.m1749(this.f277.f281.indexOf(this));
         return c_e_;
      }
   }

   Vector m474(C_i_A c_i_a) {
      Vector vector = new Vector();
      this.m475(c_i_a, vector);
      return vector;
   }

   void m475(C_i_A c_i_a, Vector vector) {
      if (c_i_a.equals(this.f278.m1272())) {
         vector.addElement(this);
      }

      int i = this.m457();

      for (int j = 0; j < i; j++) {
         this.m458(j).m475(c_i_a, vector);
      }
   }

   Vector m476(String s) {
      Vector vector = new Vector();
      this.m477(s, vector);
      return vector;
   }

   void m477(String s, Vector vector) {
      if (this.f278 instanceof C_i_ && ((C_i_)this.f278).m1262() && s.equals(this.f278.f739)) {
         vector.addElement(this);
      }

      int i = this.m457();

      for (int j = 0; j < i; j++) {
         this.m458(j).m477(s, vector);
      }
   }

   C_DD m478(String s, int i, int j) {
      int[] aint = new int[]{i, j};
      LogicProgram.m996(s, f288, f289, aint);
      return this.m479(aint[0], aint[1]);
   }

   C_DD m479(int i, int j) {
      int k = this.m457();

      for (int l = 0; l < k; l++) {
         C_DD c_dd1 = this.m458(l);
         if (i >= c_dd1.f282 && j <= c_dd1.f282 + c_dd1.f283) {
            return c_dd1.m479(i - c_dd1.f282, j - c_dd1.f282);
         }
      }

      return this;
   }

   C_DD m480(int i, int j) {
      return this.m481(i, j, false);
   }

   C_DD m481(int i, int j, boolean flag) {
      int k = this.m457();

      for (int l = 0; l < k; l++) {
         C_DD c_dd1 = this.m458(l);
         int[] aint = c_dd1.m459();
         if (aint[0] <= i && j <= aint[1] && (!flag || aint[0] != i || j != aint[1])) {
            return c_dd1.m481(i, j, flag);
         }
      }

      return this;
   }

   void m482() {
      if (this.f284 == null) {
         this.f284 = this.f278 == null ? "" : this.f278.m1205(this.f279, this.f280);
      }

      if (this.f286 == null) {
         this.f286 = m483(this.f284);
      }

      if (this.f287 == null) {
         this.f287 = m484(this.f284);
      }
   }

   static int[] m483(String s) {
      int i = s.length() + 1;
      int[] aint = new int[i];
      int j = 0;

      while (j < i) {
         aint[j] = j++;
      }

      LogicProgram.m996(s, f288, f289, aint);
      return aint;
   }

   static int[] m484(String s) {
      int i = s.length() + 1;
      int[] aint = new int[i];
      aint[0] = 0;

      for (int j = 1; j < i; j++) {
         int k = "()".indexOf(s.charAt(j - 1));
         if (k == 0) {
            aint[j] = aint[j - 1] + 1;
         } else if (k == 1) {
            aint[j] = aint[j - 1] - 1;
         } else {
            aint[j] = aint[j - 1];
         }
      }

      return aint;
   }

   static int[] m485(String s, int i, int j, boolean flag) {
      return m486(m483(s), m484(s), i, j, flag);
   }

   static int[] m486(int[] aint, int[] aint1, int i, int j, boolean flag) {
      int k = aint.length;
      int l = i;
      int i1 = i + j;
      int j1 = 1;

      while (j1 < k && aint[j1] <= l) {
         j1++;
      }

      j1--;
      k--;
      int k1 = j1;

      while (k1 < k && aint[k1] < i1) {
         k1++;
      }

      int l1 = aint1[j1];

      for (int i2 = j1 + 1; i2 <= k1; i2++) {
         if (aint1[i2] < l1) {
            l1 = aint1[i2];
         }
      }

      while (j1 > 0 && aint1[j1] > l1) {
         j1--;
      }

      while (k1 < k && aint1[k1] > l1) {
         k1++;
      }

      if (aint1[j1] == l1 && aint1[k1] == l1 && aint[j1] == l && aint[k1] == i1) {
         int[] aint2 = new int[]{j1, k1};
         if (flag) {
            m488(aint, aint1, aint2);
         }

         return aint2;
      } else {
         return null;
      }
   }

   static void m487(int[] aint, int[] aint1, C_n_F c_n_f, boolean flag) {
      if (!c_n_f.f1313) {
         for (byte b0 = 0; b0 < c_n_f.f1314 - 1; b0 += 2) {
            int[] aint2 = m486(aint, aint1, c_n_f.f1316[b0], c_n_f.f1316[b0 + 1] - c_n_f.f1316[b0], flag);
            if (aint2 != null) {
               c_n_f.f1316[b0] = aint2[0];
               c_n_f.f1316[b0 + 1] = aint2[1];
            }
         }
      }
   }

   static int m488(int[] aint, int[] aint1, int[] aint2) {
      int i = aint2[0];
      int j = aint2[1];
      int k = aint.length - 1;
      int l = aint[i];
      int i1 = aint[j];
      int j1 = aint1[i];
      int k1 = j1;

      while (true) {
         while (i > 0 && aint1[i - 1] == k1 && aint[i - 1] == l) {
            i--;
         }

         while (j < k && aint1[j + 1] == k1 && aint[j + 1] == i1) {
            j++;
         }

         if (k1 <= 0 || i <= 0 || aint1[i - 1] != k1 - 1 || j >= k || aint1[j + 1] != k1 - 1) {
            while (i < j && aint1[i + 1] == k1 && aint[i + 1] == l) {
               i++;
            }

            while (j > i && aint1[j - 1] == k1 && aint[j - 1] == i1) {
               j--;
            }

            aint2[0] = i;
            aint2[1] = j;
            return j1 - k1;
         }

         i--;
         j++;
         k1--;
      }
   }

   static void m489(int[] aint, int[] aint1) {
      int i = aint1[0];
      int j = aint1[1];
      int k = aint.length - 1;
      int l = aint[i];
      int i1 = aint[j];
      if (l > i1) {
         while (i > 0 && aint[i - 1] > i1) {
            i--;
         }
      } else if (i1 > l) {
         while (j < k && aint[j + 1] > l) {
            k++;
         }
      }

      aint1[0] = i;
      aint1[1] = j;
   }

   boolean m490() {
      if (this.f284 != null && !this.f284.trim().equals("")) {
         this.m482();
         return this.m491(this.f286, this.f287);
      } else {
         return true;
      }
   }

   boolean m491(int[] aint, int[] aint1) {
      if (this.f278 == null) {
         return this.f284 == null || this.f284.trim().equals("");
      } else {
         int i = m488(aint, aint1, m486(aint, aint1, this.m466(), this.f283, false));
         if (this.f277 != null && this.f277.f278.m1210() && this.f277.m457() == 1) {
            i--;
         }

         if (i < 0) {
            return false;
         } else {
            if (!this.f278.f739.equals("->") && !this.f278.f739.equals("<->")) {
               if (!this.f278.f739.equals("&") && !this.f278.f739.equals("|")) {
                  if (i != 0) {
                     return false;
                  }
               } else if (this.f277 == null) {
                  if (i > 1) {
                     return false;
                  }
               } else if (!this.f277.f278.f739.equals("->") && !this.f277.f278.f739.equals("<->")) {
                  if (this.f277.f278.f739.equals(this.f278.f739) && this.f277.m458(0) == this) {
                     if (i > 1) {
                        return false;
                     }
                  } else if (i != 1) {
                     return false;
                  }
               } else if (i > 1) {
                  return false;
               }
            } else if (this.f277 == null) {
               if (i > 1) {
                  return false;
               }
            } else if (i != 1) {
               return false;
            }

            int j = this.m457();

            for (int k = 0; k < j; k++) {
               if (!this.m458(k).m491(aint, aint1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
