package edu.ucla.phil.logic;

import java.util.Vector;

class C_PB extends C_i_A {
   String f672;
   Vector f673;

   C_PB(C_i_ c_i_) {
      this(c_i_.f739);
   }

   C_PB(String s) {
      this.f672 = s;
      this.f673 = null;
   }

   @Override
   public int hashCode() {
      return this.f672.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_PB)) {
         return false;
      } else {
         C_PB c_pb1 = (C_PB)object;
         return this.f672.equals(c_pb1.f672);
      }
   }

   @Override
   public String toString() {
      return this.f672;
   }

   @Override
   public String m1173() {
      return this.f672;
   }

   @Override
   public int m1174() {
      return 0;
   }

   @Override
   public C_RF m1175() {
      return new C_i_(this.f672);
   }

   @Override
   Vector m1176(boolean flag) {
      if (flag && this.f673 == null) {
         this.f673 = new Vector();
      }

      return this.f673;
   }

   @Override
   public C_i_A m1177(Vector vector) {
      return m1178(vector);
   }

   static C_i_A m1178(Vector vector) {
      C_CF c_cf = new C_CF(LogicProgram.f602);

      while (c_cf.hasMoreElements()) {
         C_PB c_pb = new C_PB((String)c_cf.nextElement());
         if (vector.indexOf(c_pb) == -1) {
            vector.addElement(c_pb);
            return c_pb;
         }
      }

      return null;
   }
}
