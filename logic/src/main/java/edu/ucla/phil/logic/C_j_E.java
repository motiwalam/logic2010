package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class C_j_E extends C_h_F {
   Hashtable f1192;

   C_j_E(Expression expression, int i, Frame frame, Hashtable hashtable) {
      super(expression, i, frame, 1);
      this.f1192 = hashtable;
   }

   @Override
   void m927(Hashtable hashtable) {
      C_KB.m761("dernot030", Message.m665(this.f1192, hashtable));
   }

   @Override
   void m928(Hashtable hashtable) {
      C_KB.m761("dernot031", Message.m665(this.f1192, hashtable));
   }

   @Override
   void m929(Hashtable hashtable) {
      C_KB.m761("dernot035", Message.m665(this.f1192, hashtable));
   }

   @Override
   void m930(Vector vector, Hashtable hashtable) {
      C_KB.m761("dernot033", Message.m665(this.f1192, hashtable));
   }

   @Override
   void m931(Hashtable hashtable) {
      C_KB.m761("dernot032", Message.m665(this.f1192, hashtable));
   }
}
