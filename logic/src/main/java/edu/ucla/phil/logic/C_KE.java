package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class C_KE extends C_h_F {
   Hashtable f498;
   SchematicLetter f499;

   C_KE(Expression expression, int i, Frame frame, Hashtable hashtable, SchematicLetter schematicletter) {
      super(expression, i, frame, 1);
      this.f498 = hashtable;
      this.f499 = schematicletter;
   }

   @Override
   void m927(Hashtable hashtable) {
      C_KB.m761("dernot036", Message.m665(this.f498, hashtable));
   }

   @Override
   void m928(Hashtable hashtable) {
      C_KB.m761("dernot037", Message.m665(this.f498, hashtable));
   }

   @Override
   void m929(Hashtable hashtable) {
      C_KB.m761("dernot042", Message.m665(this.f498, hashtable));
   }

   @Override
   void m930(Vector vector, Hashtable hashtable) {
      C_KB.m761("dernot039", Message.m665(this.f498, hashtable));
   }

   @Override
   void m931(Hashtable hashtable) {
      C_KB.m761("dernot038", Message.m665(this.f498, hashtable));
   }
}
