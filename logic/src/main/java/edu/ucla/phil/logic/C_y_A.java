package edu.ucla.phil.logic;

import java.util.Vector;

public abstract class C_y_A extends C_RF {
   C_y_A(String s) {
      super(s);
   }

   @Override
   C_RF m1275() {
      Object object = this.m1237();
      Vector vector = this.m1276();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         C_o_A c_o_a = new C_o_A("@");
         c_o_a.m1215(new C_i_((String)vector.elementAt(j)));
         c_o_a.m1215((C_RF)object);
         object = c_o_a;
      }

      ((C_RF)object).m1257();
      return (C_RF)object;
   }
}
