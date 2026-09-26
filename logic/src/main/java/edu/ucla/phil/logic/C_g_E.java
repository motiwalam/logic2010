package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_g_E extends C_IE {
   Hashtable f1134;
   Integer f1135;

   C_g_E(String s, int i) {
      super(s, i);
      this.m705();
   }

   C_g_E() {
      this.m705();
   }

   @Override
   void m705() {
      this.f1134 = null;
      this.f1135 = null;
   }

   boolean m1830(String s) {
      C_OA c_oa = new C_OA("\\{;");
      c_oa.m1132(s);

      while (c_oa.m1133() != null) {
         String s1 = c_oa.m1135();
         Integer integer = LogicProgram.m1010(s1.trim());
         if (integer == null) {
            return false;
         }

         if (c_oa.m1134() == '{' || this.f1135 != null) {
            while (c_oa.m1134() == '{') {
               C_e_ c_e_ = C_e_.m1746(C_e_.m1755("{" + c_oa.m1135()));
               if (c_e_ == null || c_e_.f1062 != this.f423) {
                  return false;
               }

               if (this.f1134 == null) {
                  this.f1134 = new Hashtable();
               }

               this.f1134.put(c_e_, integer);
            }
         } else {
            this.f1135 = integer;
         }
      }

      return true;
   }

   @Override
   void m707(int i) {
      if (this.f1135 != null && this.f1135 >= i) {
         this.f1135 = null;
      }

      if (this.f1134 != null) {
         Hashtable hashtable = new Hashtable();
         Enumeration enumeration = this.f1134.keys();

         while (enumeration.hasMoreElements()) {
            C_e_ c_e_ = (C_e_)enumeration.nextElement();
            Integer integer = (Integer)this.f1134.get(c_e_);
            if (integer < i) {
               int[] aint = c_e_.m1752();
               int k = aint.length;
               int j = 0;

               while (j < k && aint[j] < i) {
                  j++;
               }

               if (j >= k) {
                  hashtable.put(c_e_, integer);
               }
            }
         }

         this.f1134 = hashtable;
      }
   }

   @Override
   String m709() {
      return super.m709() + this.m1831();
   }

   String m1831() {
      Object object = "";
      boolean flag = false;
      if (this.f1134 != null) {
         Hashtable hashtable = m1832(this.f1134);
         Enumeration enumeration = hashtable.keys();

         while (enumeration.hasMoreElements()) {
            Object object1 = enumeration.nextElement();
            Vector vector = (Vector)hashtable.get(object1);
            object = object + (flag ? ";" : "") + object1;
            flag = true;
            int i = vector == null ? 0 : vector.size();

            for (int j = 0; j < i; j++) {
               object = object + vector.elementAt(j);
            }
         }
      }

      if (this.f1135 != null) {
         object = object + (flag ? ";" : "") + this.f1135;
      }

      return (String)object;
   }

   static Hashtable m1832(Hashtable hashtable) {
      Hashtable hashtable1 = new Hashtable();
      Enumeration enumeration = hashtable.keys();

      while (enumeration.hasMoreElements()) {
         Object object = enumeration.nextElement();
         Object object1 = hashtable.get(object);
         Vector vector = (Vector)hashtable1.get(object1);
         if (vector == null) {
            hashtable1.put(object1, vector = new Vector());
         }

         vector.addElement(object);
      }

      return hashtable1;
   }

   @Override
   String m711(int i) {
      if (i == 0) {
         return "";
      } else if (this.f423 == 0) {
         return this.f1135 == null ? "0" : this.f1135.toString();
      } else {
         boolean flag = false;
         String s = "";
         int[] aint = new int[this.f423];

         for (int j = 0; j < this.f423; j++) {
            aint[j] = 0;
         }

         int k;
         do {
            s = s + (flag ? "; " : "") + this.f422 + "(";
            flag = true;

            for (int l = 0; l < this.f423; l++) {
               s = s + (l == 0 ? "" : ",") + aint[l];
            }

            s = s + ")=" + this.m708(aint);

            for (k = 0; k < this.f423 && ++aint[k] == i; k++) {
               aint[k] = 0;
            }
         } while (k != this.f423);

         return s;
      }
   }

   @Override
   Object m708(int[] aint) {
      if (aint == null ? this.f423 == 0 : aint.length == this.f423) {
         if (this.f423 != 0 && this.f1134 != null) {
            Integer integer = (Integer)this.f1134.get(C_e_.m1746(aint));
            return integer == null ? (this.f1135 == null ? new Integer(0) : this.f1135) : integer;
         } else {
            return this.f1135 == null ? new Integer(0) : this.f1135;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }
}
