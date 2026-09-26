package edu.ucla.phil.logic;

import java.util.Vector;

class C_w_C extends SchematicLetter {
   String f1426;
   int f1427;
   Vector f1428;

   C_w_C(AtomicFormula atomicformula) {
      this(atomicformula.symbol, atomicformula.childCount);
   }

   C_w_C(String s, int i) {
      this.f1426 = s;
      this.f1427 = i;
      this.f1428 = null;
   }

   @Override
   public int hashCode() {
      return this.f1426.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_w_C)) {
         return false;
      } else {
         C_w_C c_w_c1 = (C_w_C)object;
         return this.f1426.equals(c_w_c1.f1426) && this.f1427 == c_w_c1.f1427;
      }
   }

   @Override
   public String toString() {
      if (this.f1427 == 0) {
         return this.f1426;
      } else {
         return this.f1427 == 1 && AtomicFormula.m2030(this.f1426) ? this.f1426 + m1853(0) : this.f1426 + "(" + m1854(0, this.f1427) + ")";
      }
   }

   @Override
   public String m1173() {
      return this.f1426;
   }

   @Override
   public int m1174() {
      return this.f1427;
   }

   @Override
   public Expression m1175() {
      AtomicFormula atomicformula = new AtomicFormula(this.f1426);

      for (int i = 0; i < this.f1427; i++) {
         atomicformula.addChild(new SimpleTerm(m1853(i)));
      }

      return atomicformula;
   }

   @Override
   Vector m1176(boolean flag) {
      if (flag && this.f1428 == null) {
         this.f1428 = new Vector();
      }

      return this.f1428;
   }

   @Override
   public SchematicLetter m1177(Vector vector) {
      return m2158(!AtomicFormula.m2030(this.f1426), this.f1427, vector);
   }

   static SchematicLetter m2157(int i, Vector vector) {
      return m2158(false, i, vector);
   }

   static SchematicLetter m2158(boolean flag, int i, Vector vector) {
      C_CF c_cf = new C_CF(LogicProgram.m1021(i != 0 && !flag ? LogicProgram.f600 : LogicProgram.f599));

      while (c_cf.hasMoreElements()) {
         C_w_C c_w_c = new C_w_C((String)c_cf.nextElement(), i);
         if (vector.indexOf(c_w_c) == -1) {
            vector.addElement(c_w_c);
            return c_w_c;
         }
      }

      return null;
   }
}
