package edu.ucla.phil.logic;

class C_HF extends Justification {
   static final int f390 = 1;
   SchematicRule f391;
   int[] f392;
   SchemeInstantiation f393;
   C__B f394;
   int f395;
   Object f396;

   C_HF(SchematicRule schematicrule, int[] aint, SchemeInstantiation schemeinstantiation, C__B c__b) {
      super(schematicrule.f820);
      this.f391 = schematicrule;
      this.f392 = aint;
      this.f393 = schemeinstantiation;
      this.f394 = c__b;
      this.f395 = 0;
      this.f396 = null;
   }

   @Override
   public Object clone() {
      C_HF c_hf1 = (C_HF)super.clone();
      if (this.f392 != null) {
         c_hf1.f392 = new int[this.f392.length];
         System.arraycopy(this.f392, 0, c_hf1.f392, 0, this.f392.length);
      }

      if (this.f393 != null) {
         c_hf1.f393 = (SchemeInstantiation)this.f393.clone();
      }

      if (this.f394 != null) {
         c_hf1.f394 = (C__B)this.f394.clone();
      }

      c_hf1.f395 = 0;
      c_hf1.f396 = null;
      return c_hf1;
   }

   SchematicRule m688() {
      return this.f391;
   }

   int[] m689() {
      return this.f392;
   }

   SchemeInstantiation m690() {
      return this.f393;
   }

   C__B m691() {
      return this.f394;
   }

   int m692() {
      return this.f391.premises.length;
   }

   Expression m693(int i) {
      return this.m694(i, null);
   }

   Expression m694(int i, DerivationLineChecker derivationlinechecker) {
      C_MB c_mb = new C_MB();
      Expression expression = this.f391.premises[this.f392[i]].m1239(this.f393, c_mb);
      this.f394.m1574(this.f391.premises[this.f392[i]], expression, c_mb, derivationlinechecker);
      return expression;
   }

   Expression m695() {
      return this.m696(null);
   }

   Expression m696(DerivationLineChecker derivationlinechecker) {
      C_MB c_mb = new C_MB();
      Expression expression = this.f391.conclusion.m1239(this.f393, c_mb);
      this.f394.m1574(this.f391.conclusion, expression, c_mb, derivationlinechecker);
      return expression;
   }

   boolean m697(Expression expression) {
      return expression.m1274(this.f393) && this.f394.m1578(expression);
   }

   @Override
   boolean m600(DerivationLineChecker derivationlinechecker) {
      Rule rule = LPDerivation.getRule(derivationlinechecker.f939);
      if (rule != null && rule.m1375(this.f391)) {
         int i = this.f391.premises.length;
         derivationlinechecker.getClass();
         if (!derivationlinechecker.f944 && !derivationlinechecker.f945 ? i <= derivationlinechecker.f938 : i == derivationlinechecker.f938) {
            for (int j = 0; j < i; j++) {
               Expression expression = this.m693(j);
               if (expression.m1259() != null || !expression.m1235(derivationlinechecker.m1628(j - i))) {
                  return false;
               }
            }

            if (this.f391.m956(rule.m1373(derivationlinechecker.f935.f317.f915, derivationlinechecker.f946 ? "manualOrDisabled" : "disabled")) == -1) {
               return false;
            } else if ((derivationlinechecker.f942 = this.m695()).m1259() != null) {
               return false;
            } else if (!derivationlinechecker.m1613(this.f393, true)) {
               return false;
            } else if (derivationlinechecker.f944 && !derivationlinechecker.m1609(true)) {
               return false;
            } else {
               derivationlinechecker.m1631(i);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   String m609() {
      return "1:" + this;
   }

   static C_HF m698(String s) {
      int i;
      if ((i = s.indexOf(":")) == -1) {
         return null;
      } else {
         return !s.substring(0, i).equals(Integer.toString(1)) ? null : m699(s.substring(i + 1));
      }
   }

   @Override
   public String toString() {
      return this.f384 + ExpressionPath.m1753(this.f392) + this.f393.m1896() + "," + this.f394.m1580();
   }

   static C_HF m699(String s) {
      int i;
      if ((i = s.indexOf("{")) == -1) {
         return null;
      } else {
         Rule rule = LPDerivation.getRule(s.substring(0, i));
         if (rule != null && rule instanceof SchematicRule) {
            s = s.substring(i);
            if ((i = s.indexOf("}")) == -1) {
               return null;
            } else {
               int[] aint = ExpressionPath.m1755(s.substring(0, i + 1));
               if (aint == null) {
                  return null;
               } else {
                  s = s.substring(i + 1);
                  if ((i = s.indexOf(",")) == -1) {
                     return null;
                  } else {
                     SchemeInstantiation schemeinstantiation = SchemeInstantiation.m1897(s.substring(0, i));
                     if (schemeinstantiation == null) {
                        return null;
                     } else {
                        s = s.substring(i + 1);
                        C__B c__b = C__B.m1581(s);
                        return new C_HF((SchematicRule)rule, aint, schemeinstantiation, c__b);
                     }
                  }
               }
            }
         } else {
            return null;
         }
      }
   }

   C_FD m700(DerivationLineChecker derivationlinechecker) {
      return new C_FD(this, derivationlinechecker);
   }
}
