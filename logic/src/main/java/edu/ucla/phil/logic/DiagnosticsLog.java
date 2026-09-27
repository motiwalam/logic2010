package edu.ucla.phil.logic;

import java.io.PrintWriter;

public class DiagnosticsLog {
   static PrintWriter out = null;

   static void log(String s) {
      if (out != null) {
         out.println(s);
      }
   }

   static void logThrowable(Throwable throwable) {
      if (out != null) {
         while (throwable != null) {
            out.println(throwable.toString());
            throwable = throwable.getCause();
         }
      }
   }
}
