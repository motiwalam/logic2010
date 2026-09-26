package edu.ucla.phil.logic;

import java.io.PrintWriter;

public class C_k_C {
   static PrintWriter f1199 = null;

   static void m1905(String s) {
      if (f1199 != null) {
         f1199.println(s);
      }
   }

   static void m1906(Throwable throwable) {
      if (f1199 != null) {
         while (throwable != null) {
            f1199.println(throwable.toString());
            throwable = throwable.getCause();
         }
      }
   }
}
