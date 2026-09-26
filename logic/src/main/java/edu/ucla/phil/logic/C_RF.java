package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

public abstract class C_RF implements C_a_D, C_n_A {
   protected int f738;
   protected String f739;
   protected Vector f740;
   protected int f741;
   protected boolean f742;

   C_RF(String s) {
      this.f739 = s;
      this.f740 = new Vector();
      this.f741 = 0;
      this.f742 = false;
      this.m1212();
   }

   @Override
   public String toString() {
      return this.m1205(true, 0);
   }

   String m1205(boolean flag, int i) {
      return flag ? this.m1207(i) : this.m1209(i);
   }

   String m1206() {
      return this.m1207(-1);
   }

   abstract String m1207(int i);

   String m1208() {
      return this.m1209(1);
   }

   abstract String m1209(int i);

   boolean m1210() {
      return false;
   }

   void m1211(C_DD c_dd) {
      c_dd.f281 = this.f741 == 0 ? null : new Vector();

      for (int i = 0; i < this.f741; i++) {
         C_DD c_dd1 = new C_DD(this.m1217(i));
         c_dd1.f277 = c_dd;
         c_dd.f281.addElement(c_dd1);
      }
   }

   abstract void m1212();

   int m1213() {
      return this.f738;
   }

   public String m1214() {
      return this.f739;
   }

   void m1215(C_RF c_rf1) {
      this.f740.addElement(c_rf1);
      this.f741++;
   }

   int m1216() {
      return this.f741;
   }

   C_RF m1217(int i) {
      return i >= 0 && i < this.f741 ? (C_RF)this.f740.elementAt(i) : null;
   }

   int m1218(String s, int i) {
      int j = i;

      while (j < this.f741 && !s.equals(this.m1217(j).f739)) {
         j++;
      }

      return j == this.f741 ? -1 : j;
   }

   int m1219(String s) {
      return this.m1218(s, 0);
   }

   C_RF m1220(C_e_ c_e_) {
      return this.m1221(c_e_, null);
   }

   C_RF m1221(C_e_ c_e_, Vector vector) {
      return c_e_ == null ? null : this.m1222(c_e_.f1063, 0, c_e_.f1062, vector);
   }

   C_RF m1222(int[] aint, int i, int j, Vector vector) {
      if (i == j) {
         return this;
      } else {
         C_RF c_rf1 = this.m1217(aint[i]);
         return c_rf1 == null ? null : c_rf1.m1222(aint, i + 1, j, vector);
      }
   }

   Vector m1223(C_RF c_rf1) {
      Vector vector = new Vector();
      this.m1224(c_rf1, new C_e_(), vector);
      return vector;
   }

   void m1224(C_RF c_rf1, C_e_ c_e_, Vector vector) {
      if (this.m1235(c_rf1)) {
         vector.addElement(c_e_.clone());
      } else {
         for (int i = 0; i < this.f741; i++) {
            c_e_.m1749(i);
            this.m1217(i).m1224(c_rf1, c_e_, vector);
            c_e_.f1062--;
         }
      }
   }

   Vector m1225(C_i_A c_i_a) {
      Vector vector = new Vector();
      this.m1226(c_i_a, new C_e_(), vector);
      return vector;
   }

   void m1226(C_i_A c_i_a, C_e_ c_e_, Vector vector) {
      if (c_i_a != null) {
         if (c_i_a.equals(this.m1272())) {
            vector.addElement(c_e_.clone());
         }

         for (int i = 0; i < this.f741; i++) {
            c_e_.m1749(i);
            this.m1217(i).m1226(c_i_a, c_e_, vector);
            c_e_.f1062--;
         }
      }
   }

   Vector m1227(String s) {
      Vector vector = new Vector();
      this.m1228(s, new C_e_(), vector);
      return vector;
   }

   void m1228(String s, C_e_ c_e_, Vector vector) {
      if (s.equals(this.f739)) {
         vector.addElement(c_e_.clone());
      }

      for (int i = 0; i < this.f741; i++) {
         c_e_.m1749(i);
         this.m1217(i).m1228(s, c_e_, vector);
         c_e_.f1062--;
      }
   }

   Vector m1229(String s) {
      Vector vector = new Vector();
      this.m1230(s, new C_e_(), vector);
      return vector;
   }

   void m1230(String s, C_e_ c_e_, Vector vector) {
      if (s != null) {
         for (int i = 0; i < this.f741; i++) {
            c_e_.m1749(i);
            this.m1217(i).m1230(s, c_e_, vector);
            c_e_.f1062--;
         }
      }
   }

   C_e_ m1231(C_RF c_rf1) {
      return this.m1232(this.m1233(c_rf1));
   }

   C_e_ m1232(Vector vector) {
      int i = vector == null ? 0 : vector.size();
      if (i == 0) {
         return null;
      } else {
         C_e_ c_e_ = (C_e_)vector.elementAt(0);

         for (int j = 1; j < i; j++) {
            c_e_.f1062 = c_e_.m1751((C_e_)vector.elementAt(j));
         }

         return c_e_;
      }
   }

   Vector m1233(C_RF c_rf1) {
      Vector vector = new Vector();
      this.m1234(c_rf1, new C_e_(), vector);
      return vector;
   }

   private void m1234(C_RF c_rf1, C_e_ c_e_, Vector vector) {
      if (c_rf1 != null && this.f739.equals(c_rf1.f739) && this.f741 == c_rf1.f741) {
         for (int i = 0; i < this.f741; i++) {
            c_e_.m1749(i);
            this.m1217(i).m1234(c_rf1.m1217(i), c_e_, vector);
            c_e_.f1062--;
         }
      } else {
         vector.addElement(c_e_.clone());
      }
   }

   boolean m1235(C_RF c_rf1) {
      if (c_rf1 != null && this.f739.equals(c_rf1.f739) && this.f741 == c_rf1.f741) {
         for (int i = 0; i < this.f741; i++) {
            if (!this.m1217(i).m1235(c_rf1.m1217(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean m1236(C_RF c_rf1, C_MB c_mb) {
      if (c_rf1 != null && this.f739.equals(c_rf1.f739) && this.f741 == c_rf1.f741) {
         for (int i = 0; i < this.f741; i++) {
            if (!this.m1217(i).m1236(c_rf1.m1217(i), c_mb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   C_RF m1237() {
      return this.m1240(null, null, new C_MB(), new Vector());
   }

   C_RF m1238(C_j_D c_j_d) {
      return this.m1240(null, c_j_d, new C_MB(), new Vector());
   }

   C_RF m1239(C_j_D c_j_d, C_MB c_mb) {
      return this.m1240(null, c_j_d, c_mb, new Vector());
   }

   abstract C_RF m1240(C_RF c_rf, C_j_D c_j_d, C_MB c_mb, Vector vector);

   void m1241(Vector vector) {
      if (vector != null) {
         this.m1242(vector, 0);
      }
   }

   int m1242(Vector vector, int i) {
      for (int j = 0; j < this.f741; j++) {
         i = this.m1217(j).m1242(vector, i);
      }

      return i;
   }

   Vector m1243() {
      return this.m1244(new Vector());
   }

   Vector m1244(Vector vector) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1244(vector);
      }

      return vector;
   }

   Vector m1245() {
      Vector vector = new Vector();
      this.m1246(vector);
      return vector;
   }

   void m1246(Vector vector) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1246(vector);
      }
   }

   C_RF m1247(Vector vector, int i, C_j_D c_j_d) {
      for (int j = 0; j < this.f741; j++) {
         this.f740.setElementAt(this.m1217(j).m1247(vector, i, c_j_d), j);
      }

      return this;
   }

   C_RF m1248() {
      return this.m1237().m1247(this.m1245(), 0, new C_j_D());
   }

   C_RF m1249(int i, String s) {
      return this.m1237().m1251(i, s, false);
   }

   C_RF m1250(int i, String s) {
      return this.m1237().m1251(i, s, true);
   }

   C_RF m1251(int i, String s, boolean flag) {
      if (flag) {
         for (int j = 0; j < this.f741; j++) {
            this.f740.setElementAt(this.m1217(j).m1251(i, s, true), j);
         }
      }

      return this;
   }

   boolean m1252(C_RF c_rf1) {
      for (int i = 0; i < this.f741; i++) {
         if (this.m1217(i).m1252(c_rf1)) {
            return true;
         }
      }

      return false;
   }

   void m1253(Vector vector, Vector vector1) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1253(vector, vector1);
      }
   }

   C_L m1254() {
      Vector vector = this.m1243();
      int i = vector.size();
      C_L c_l = new C_L();

      for (int j = 0; j < i; j++) {
         c_l.addElement(((C_RF)vector.elementAt(j)).m1217(0).m1214());
      }

      return c_l;
   }

   boolean m1255(C_RF c_rf1) {
      return this.f738 == 2 && this.f739.equals("~") && this.m1217(0).m1235(c_rf1);
   }

   public C_q_F m1256() {
      C_q_F c_q_f = new C_q_F("~");
      c_q_f.m1215(this);
      return c_q_f;
   }

   C_RF m1257() {
      this.m1258(new C_JD());
      return this;
   }

   void m1258(C_JD c_jd) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1258(c_jd);
      }
   }

   Vector m1259() {
      Vector vector = new Vector();
      this.m1260(new C_JD(), vector);
      return vector.size() == 0 ? null : vector;
   }

   void m1260(C_JD c_jd, Vector vector) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1260(c_jd, vector);
      }
   }

   boolean m1261() {
      return false;
   }

   boolean m1262() {
      return false;
   }

   boolean m1263() {
      return false;
   }

   void m1264(C_RF c_rf1) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1264(c_rf1);
      }
   }

   boolean m1265(C_RF c_rf1) {
      for (int i = 0; i < this.f741; i++) {
         if (this.m1217(i).m1265(c_rf1)) {
            return true;
         }
      }

      return false;
   }

   boolean m1266(C_RF c_rf1, C_j_D c_j_d) {
      return this.m1268(null, c_rf1, c_j_d, new C_MB(), new Vector());
   }

   boolean m1267(C_RF c_rf1, C_j_D c_j_d, C_MB c_mb) {
      return this.m1268(null, c_rf1, c_j_d, c_mb, new Vector());
   }

   boolean m1268(C_RF c_rf1, C_RF c_rf2, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      if (c_rf2 == null || this.f738 == c_rf2.f738 && this.f739.equals(c_rf2.f739) && this.f741 == c_rf2.f741) {
         for (int i = 0; i < this.f741; i++) {
            if (!this.m1217(i).m1268(c_rf1, c_rf2 == null ? null : c_rf2.m1217(i), c_j_d, c_mb, vector)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean m1269(C_RF c_rf1, C_j_D c_j_d, C_MB c_mb, Vector vector) {
      C_i_A c_i_a = this.m1272();
      C_GF c_gf;
      if ((c_gf = c_j_d.m1878(c_i_a)) != null) {
         return c_gf.f368.m1268(this, c_rf1, c_j_d, c_mb, vector);
      } else {
         if (c_rf1 != null) {
            c_gf = this.m1271(c_rf1, c_mb, vector);
            if (c_gf.f369 == null) {
               return c_j_d.m1880(c_i_a, c_gf);
            }

            Hashtable hashtable = c_gf.f369.f428;
            if (hashtable == null || hashtable.get("addMissingKey") == null) {
               c_j_d.f1190 = c_gf.f369.f427;
               c_j_d.f1191 = hashtable;
               return false;
            }
         }

         C_c_B c_c_b = this.m1270(c_rf1);
         if (c_c_b != null) {
            c_j_d.f1190 = c_c_b.f427;
            c_j_d.f1191 = c_c_b.f428;
            return false;
         } else {
            return this.m1273(c_j_d) && c_j_d.m1887(this, c_rf1, c_mb, vector);
         }
      }
   }

   C_c_B m1270(C_RF c_rf) {
      return null;
   }

   C_GF m1271(C_RF c_rf1, C_MB c_mb, Vector vector) {
      C_j_D c_j_d = new C_j_D();
      C_j_D c_j_d1 = new C_j_D();
      Hashtable hashtable = C_H.m667("pattern", "\\l" + this + "\\l", "replacement", "\\l" + c_rf1 + "\\l");

      for (int i = 0; i < this.f741; i++) {
         C_RF c_rf2;
         if (!(c_rf2 = this.m1217(i)).m1262()) {
            C_H.m664(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new C_GF(new C_c_B("dererr065", hashtable));
         }

         C_RF c_rf3 = ((C_i_)c_rf2).m1850();
         C_RF c_rf4 = c_mb.m1092(null, c_rf3, vector).m1217(0);
         C_i_ c_i_ = new C_i_(C_i_A.m1853(i));
         if (!c_j_d.m1881(new C_i_(c_rf2.f739), c_i_)) {
            C_H.m664(hashtable, "n", i + 1 + "");
            hashtable.put("addMissingKey", "");
            return new C_GF(new C_c_B("dererr066", hashtable));
         }

         c_j_d1.m1881(new C_i_(c_rf4.f739), c_i_);
      }

      C_RF c_rf5 = this.m1238(c_j_d);
      C_RF c_rf6 = c_rf1.m1238(c_j_d1);
      C_GF c_gf = new C_GF(c_rf5, c_rf6);
      if (c_gf.f369 == null) {
         C_H.m664(hashtable, "dummy pattern", c_rf5 + "");
         C_H.m664(hashtable, "dummy replacement", c_rf6 + "");
         if (!c_j_d1.m1889().isEmpty()) {
            c_gf.f369 = new C_c_B("dererr061");
         } else if (c_rf6.m1259() != null) {
            c_gf.f369 = new C_c_B("dererr061");
         }
      }

      return c_gf;
   }

   C_i_A m1272() {
      return null;
   }

   boolean m1273(C_j_D c_j_d) {
      boolean flag = true;

      for (int i = 0; i < this.f741; i++) {
         flag &= this.m1217(i).m1273(c_j_d);
      }

      return flag;
   }

   boolean m1274(C_j_D c_j_d) {
      for (int i = 0; i < this.f741; i++) {
         if (!this.m1217(i).m1274(c_j_d)) {
            return false;
         }
      }

      return true;
   }

   C_RF m1275() {
      return null;
   }

   Vector m1276() {
      Vector vector = new Vector();
      this.m1277(vector);
      return vector;
   }

   void m1277(Vector vector) {
      for (int i = 0; i < this.f741; i++) {
         this.m1217(i).m1277(vector);
      }
   }
}
