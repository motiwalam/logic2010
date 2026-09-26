package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class C_j_E extends C_h_F {
   Hashtable f1192;

   C_j_E(C_RF c_rf, int i, Frame frame, Hashtable hashtable) {
      super(c_rf, i, frame, 1);
      this.f1192 = hashtable;
   }

   @Override
   void m927(Hashtable hashtable) {
      C_KB.m761("dernot030", C_H.m665(this.f1192, hashtable));
   }

   @Override
   void m928(Hashtable hashtable) {
      C_KB.m761("dernot031", C_H.m665(this.f1192, hashtable));
   }

   @Override
   void m929(Hashtable hashtable) {
      C_KB.m761("dernot035", C_H.m665(this.f1192, hashtable));
   }

   @Override
   void m930(Vector vector, Hashtable hashtable) {
      C_KB.m761("dernot033", C_H.m665(this.f1192, hashtable));
   }

   @Override
   void m931(Hashtable hashtable) {
      C_KB.m761("dernot032", C_H.m665(this.f1192, hashtable));
   }
}
