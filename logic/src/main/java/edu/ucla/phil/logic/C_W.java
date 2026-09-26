package edu.ucla.phil.logic;

import java.util.Vector;

class C_W extends SchematicLetter {
   String f850;
   int f851;
   Vector f852;

   C_W(OperationTerm operationterm) {
      this(operationterm.symbol, operationterm.childCount);
   }

   C_W(String s, int i) {
      this.f850 = s;
      this.f851 = i;
      this.f852 = null;
   }

   @Override
   public int hashCode() {
      return this.f850.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_W)) {
         return false;
      } else {
         C_W c_w1 = (C_W)object;
         return this.f850.equals(c_w1.f850) && this.f851 == c_w1.f851;
      }
   }

   @Override
   public String toString() {
      return this.f851 == 0 ? this.f850 : this.f850 + "(" + m1854(0, this.f851) + ")";
   }

   @Override
   public String m1173() {
      return this.f850;
   }

   @Override
   public int m1174() {
      return this.f851;
   }

   @Override
   public Expression m1175() {
      OperationTerm operationterm = new OperationTerm(this.f850);

      for (int i = 0; i < this.f851; i++) {
         operationterm.addChild(new SimpleTerm(m1853(i)));
      }

      return operationterm;
   }

   @Override
   Vector m1176(boolean flag) {
      if (flag && this.f852 == null) {
         this.f852 = new Vector();
      }

      return this.f852;
   }

   @Override
   public SchematicLetter m1177(Vector vector) {
      return m1423(this.f851, vector);
   }

   static SchematicLetter m1423(int i, Vector vector) {
      C_CF c_cf = new C_CF(LogicProgram.m1021(LogicProgram.f601));

      while (c_cf.hasMoreElements()) {
         C_W c_w = new C_W((String)c_cf.nextElement(), i);
         if (vector.indexOf(c_w) == -1) {
            vector.addElement(c_w);
            return c_w;
         }
      }

      return null;
   }
}
