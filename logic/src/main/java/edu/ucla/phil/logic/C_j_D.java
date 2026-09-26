package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_j_D extends Hashtable implements C_a_D {
   Vector f1189;
   String f1190 = null;
   Hashtable f1191 = null;

   C_j_D() {
      this.f1189 = new Vector();
   }

   C_j_D(C_j_D c_j_d1) {
      this();
      this.m1877(c_j_d1);
   }

   @Override
   public Object clone() {
      C_j_D c_j_d1 = (C_j_D)super.clone();
      c_j_d1.f1189 = (Vector)this.f1189.clone();
      c_j_d1.f1190 = null;
      c_j_d1.f1191 = null;
      return c_j_d1;
   }

   boolean m1877(C_j_D c_j_d1) {
      String s = null;
      Hashtable hashtable = null;
      if (c_j_d1 != null) {
         Enumeration enumeration = c_j_d1.keys();

         while (enumeration.hasMoreElements()) {
            C_i_A c_i_a = (C_i_A)enumeration.nextElement();
            C_GF c_gf = c_j_d1.m1878(c_i_a);
            if (!this.m1880(c_i_a, c_gf) && s == null) {
               s = this.f1190;
               hashtable = this.f1191;
            }
         }

         enumeration = c_j_d1.f1189.elements();

         while (enumeration.hasMoreElements()) {
            C_i_A c_i_a1 = (C_i_A)enumeration.nextElement();
            if (!this.m1879(c_i_a1.m1176(false)) && s == null) {
               s = this.f1190;
               hashtable = this.f1191;
            }

            this.m1886(c_i_a1);
         }
      }

      this.f1190 = s;
      this.f1191 = hashtable;
      return this.f1190 == null;
   }

   @Override
   public void clear() {
      super.clear();
      this.f1189.setSize(0);
      this.f1190 = null;
      this.f1191 = null;
   }

   C_GF m1878(C_i_A c_i_a) {
      return (C_GF)this.get(c_i_a);
   }

   boolean m1879(Vector vector) {
      if (vector == null) {
         return true;
      } else {
         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            C_m_B c_m_b = (C_m_B)enumeration.nextElement();
            if (!c_m_b.f1276.m1268(null, c_m_b.f1277, this, c_m_b.f1278, C_o_D.m2008(c_m_b.f1279))) {
               this.f1190 = "dererr062";
               this.f1191 = C_H.m667("pattern", "\\l" + c_m_b.f1276 + "\\l", "instance", "\\l" + c_m_b.f1277 + "\\l");
               return false;
            }
         }

         return true;
      }
   }

   boolean m1880(C_i_A c_i_a, C_GF c_gf) {
      C_GF c_gf1 = this.m1878(c_i_a);
      if (c_gf1 == null) {
         Vector vector = null;
         int i;
         if ((i = this.f1189.indexOf(c_i_a)) != -1) {
            vector = ((C_i_A)this.f1189.elementAt(i)).m1176(false);
            this.f1189.removeElementAt(i);
         }

         this.put(c_i_a, c_gf);
         if (!this.m1879(vector)) {
            return false;
         }
      } else if (!c_gf1.m656(c_gf)) {
         this.f1190 = "dererr073";
         this.f1191 = C_H.m668(
            "pattern", "\\l" + c_gf.f367 + "\\l", "new replacement", "\\l" + c_gf.f368 + "\\l", "old replacement", "\\l" + c_gf1.f368 + "\\l"
         );
         return false;
      }

      return true;
   }

   boolean m1881(C_RF c_rf, C_RF c_rf1) {
      C_GF c_gf = new C_GF(c_rf, c_rf1);
      if (c_gf.f369 != null) {
         this.f1190 = c_gf.f369.f427;
         this.f1191 = c_gf.f369.f428;
         return false;
      } else {
         return this.m1880(c_rf.m1272(), c_gf);
      }
   }

   boolean m1882(String s, String s1) {
      String s2 = null;

      C_RF c_rf;
      C_RF c_rf1;
      try {
         s2 = s;
         c_rf = LogicProgram.m1008(s, true, true);
         s2 = s1;
         c_rf1 = LogicProgram.m1008(s1, true, true);
      } catch (C_k_B c_k_b) {
         this.f1190 = "dererr059";
         this.f1191 = C_H.m666("parser error", s2);
         return false;
      }

      return this.m1881(c_rf, c_rf1);
   }

   boolean m1883(String s) {
      int i = s.indexOf(":");
      if (i == -1) {
         this.f1190 = "dererr059";
         this.f1191 = C_H.m666("parser error", "scheme map needs pattern:replacement");
         return false;
      } else {
         return this.m1882(s.substring(0, i), s.substring(i + 1));
      }
   }

   static C_c_B m1884(C_RF c_rf, C_RF c_rf1) {
      Hashtable hashtable = C_H.m667("pattern", "\\l" + c_rf + "\\l", "replacement", "\\l" + c_rf1 + "\\l");
      if (c_rf.f738 != 0 && c_rf.f738 != 4) {
         if (c_rf instanceof C_i_ && ((C_i_)c_rf).m1262()) {
            return new C_c_B("dererr067", hashtable);
         }
      } else {
         for (int i = 0; i < c_rf.f741; i++) {
            C_RF c_rf2 = c_rf.m1217(i);
            if (!(c_rf2 instanceof C_i_) || ((C_i_)c_rf2).m1262()) {
               return new C_c_B("dererr094", C_H.m664(hashtable, "n", i + 1 + ""));
            }
         }
      }

      switch (c_rf.f738) {
         case 0:
            return c_rf1 instanceof C_y_A ? null : new C_c_B("dererr068", hashtable);
         case 1:
         case 2:
         default:
            return new C_c_B("dererr071", hashtable);
         case 3:
         case 4:
            if (c_rf1 instanceof C_i_ && ((C_i_)c_rf1).m1262()) {
               return new C_c_B("dererr069", hashtable);
            } else {
               return c_rf1 instanceof C_X ? null : new C_c_B("dererr070", hashtable);
            }
      }
   }

   boolean m1885(C_RF c_rf) {
      C_i_A c_i_a = c_rf.m1272();
      if (c_i_a != null) {
         this.m1886(c_i_a);
         return true;
      } else {
         if (c_rf instanceof C_i_ && ((C_i_)c_rf).m1262()) {
            this.f1190 = "dererr067";
            this.f1191 = C_H.m666("pattern", "\\l" + c_rf + "\\l");
         } else {
            this.f1190 = "dererr071";
            this.f1191 = C_H.m666("pattern", "\\l" + c_rf + "\\l");
         }

         return false;
      }
   }

   void m1886(C_i_A c_i_a) {
      if (c_i_a != null && !this.containsKey(c_i_a)) {
         int i = this.f1189.indexOf(c_i_a);
         if (i == -1) {
            this.f1189.addElement(c_i_a);
         } else {
            Vector vector;
            if ((vector = c_i_a.m1176(false)) != null) {
               C_i_A c_i_a1 = (C_i_A)this.f1189.elementAt(i);
               Vector vector1 = c_i_a1.m1176(true);
               Enumeration enumeration = vector.elements();

               while (enumeration.hasMoreElements()) {
                  C_m_B c_m_b = (C_m_B)enumeration.nextElement();
                  if (!vector1.contains(c_m_b)) {
                     vector1.addElement(c_m_b);
                  }
               }
            }
         }
      }
   }

   boolean m1887(C_RF c_rf, C_RF c_rf1, C_MB c_mb, Vector vector) {
      return this.m1888(new C_m_B(c_rf, c_rf1, c_mb, vector));
   }

   boolean m1888(C_m_B c_m_b) {
      C_i_A c_i_a = c_m_b.f1276.m1272();
      if (c_i_a == null) {
         return false;
      } else {
         int i = this.f1189.indexOf(c_i_a);
         if (i != -1) {
            Vector vector = ((C_i_A)this.f1189.elementAt(i)).m1176(true);
            if (!vector.contains(c_m_b)) {
               vector.addElement(c_m_b);
            }
         }

         return true;
      }
   }

   Vector m1889() {
      return this.f1189;
   }

   boolean m1890() {
      Enumeration enumeration = this.f1189.elements();

      while (enumeration.hasMoreElements()) {
         if (((C_i_A)enumeration.nextElement()).m1176(false) != null) {
            return false;
         }
      }

      return true;
   }

   String m1891() {
      if (this.f1189 == null) {
         return "null";
      } else {
         Object object = "";
         Enumeration enumeration = this.f1189.elements();

         while (enumeration.hasMoreElements()) {
            C_i_A c_i_a = (C_i_A)enumeration.nextElement();
            object = object + (object.equals("") ? "" : ".") + c_i_a;
            Vector vector = c_i_a.m1176(false);
            if (vector != null && !vector.isEmpty()) {
               object = object + vector;
            }
         }

         return (String)object;
      }
   }

   Vector m1892(C_RF c_rf) {
      C_j_D c_j_d1 = new C_j_D();
      if (c_rf != null) {
         c_rf.m1273(c_j_d1);
      }

      Enumeration enumeration = this.elements();

      while (enumeration.hasMoreElements()) {
         C_GF c_gf = (C_GF)enumeration.nextElement();
         c_gf.f368.m1273(c_j_d1);
      }

      return c_j_d1.f1189;
   }

   C_j_D m1893(C_RF c_rf) {
      Vector vector = this.m1892(c_rf);
      int i = this.f1189.size();
      C_j_D c_j_d1 = new C_j_D();

      for (int j = 0; j < i; j++) {
         C_i_A c_i_a = (C_i_A)this.f1189.elementAt(0);
         if (c_i_a.m1176(false) != null) {
            C_i_A c_i_a1 = c_i_a.m1177(vector);
            c_j_d1.m1886(c_i_a1);
            this.m1881(c_i_a.m1175(), c_i_a1.m1175());
         }
      }

      return c_j_d1;
   }

   String m1894() {
      return this.f1190;
   }

   Hashtable m1895() {
      return this.f1191;
   }

   String m1896() {
      String s = "";
      boolean flag = false;

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         C_i_A c_i_a = (C_i_A)enumeration.nextElement();
         s = s + (flag ? "." : "") + this.m1878(c_i_a);
      }

      return s;
   }

   static C_j_D m1897(String s) {
      C_j_D c_j_d = new C_j_D();

      while (!s.equals("")) {
         String s1;
         int i;
         if ((i = s.indexOf(".")) == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         if (!c_j_d.m1883(s1)) {
            return null;
         }
      }

      return c_j_d;
   }
}
