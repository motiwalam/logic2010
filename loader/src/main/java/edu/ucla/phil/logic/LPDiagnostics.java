package edu.ucla.phil.logic;

import java.io.PrintWriter;

public class LPDiagnostics {
   static PrintWriter diagnostics = null;

   static void printDiagnostic(String message) {
      if (diagnostics != null) {
         diagnostics.println(message);
      }
   }

   static void printThrowableDiagnostic(Throwable e) {
      if (diagnostics != null) {
         while (e != null) {
            diagnostics.println(e.toString());
            e = e.getCause();
         }
      }
   }
}
