package edu.ucla.phil.logic;

import java.io.Reader;

public class C_FB {
   static int f303 = 2;
   static int f304 = 2;

   static void m536(int i) {
      if (i >= 1 && i <= f303) {
         f304 = i;
      }
   }

   static int m537() {
      return f304;
   }

   static C_RF m114() throws C_k_B {
      switch (f304) {
         case 1:
            return edu.ucla.phil.logic.pkgB.C_E.m114();
         case 2:
            return edu.ucla.phil.logic.pkgA.C_E.m114();
         default:
            return null;
      }
   }

   static void m195(Reader reader) {
      switch (f304) {
         case 1:
            edu.ucla.phil.logic.pkgB.C_E.m195(reader);
            break;
         case 2:
            edu.ucla.phil.logic.pkgA.C_E.m195(reader);
      }
   }

   static void m205() {
      edu.ucla.phil.logic.pkgB.C_E.m205();
      edu.ucla.phil.logic.pkgA.C_E.m205();
   }
}
