package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_OD extends Hashtable {
   String f657 = null;
   String f658 = null;

   public C_OD(String s, boolean flag) {
      if (s != null) {
         this.f657 = s.trim();
         this.m1144(flag);
      }
   }

   String m1142() {
      return this.f658;
   }

   String m1143(String s) {
      return (String)this.get(s.toLowerCase());
   }

   void m1144(boolean flag) {
      String s;
      if (flag) {
         this.f658 = null;
         s = this.f657;
      } else {
         int i = this.f657.indexOf(" ");
         if (i == -1) {
            this.f658 = this.f657;
            return;
         }

         this.f658 = this.f657.substring(0, i);
         s = this.f657.substring(i + 1).trim();
      }

      int j;
      while (s != null && (j = s.indexOf("=")) != -1) {
         String s2 = s.substring(0, j).trim();
         C_OA c_oa = new C_OA("\\\",");
         c_oa.m1132(s.substring(j + 1).trim());
         String s1 = c_oa.m1135().trim();
         if (c_oa.m1134() == '"') {
            C_OA c_oa1 = new C_OA("\\\"");
            c_oa1.m1132(c_oa.m1133());
            s1 = c_oa1.m1135();
            c_oa.m1132(c_oa1.m1133());

            do {
               c_oa.m1135();
            } while (c_oa.m1134() == '"');
         }

         s = c_oa.m1133();
         this.put(s2.toLowerCase(), s1);
      }
   }
}
