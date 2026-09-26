package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class C_KE extends C_h_F {
   Hashtable f498;
   C_i_A f499;

   C_KE(C_RF c_rf, int i, Frame frame, Hashtable hashtable, C_i_A c_i_a) {
      super(c_rf, i, frame, 1);
      this.f498 = hashtable;
      this.f499 = c_i_a;
   }

   @Override
   void m927(Hashtable hashtable) {
      C_KB.m761("dernot036", C_H.m665(this.f498, hashtable));
   }

   @Override
   void m928(Hashtable hashtable) {
      C_KB.m761("dernot037", C_H.m665(this.f498, hashtable));
   }

   @Override
   void m929(Hashtable hashtable) {
      C_KB.m761("dernot042", C_H.m665(this.f498, hashtable));
   }

   @Override
   void m930(Vector vector, Hashtable hashtable) {
      C_KB.m761("dernot039", C_H.m665(this.f498, hashtable));
   }

   @Override
   void m931(Hashtable hashtable) {
      C_KB.m761("dernot038", C_H.m665(this.f498, hashtable));
   }
}
