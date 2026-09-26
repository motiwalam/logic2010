package edu.ucla.phil.logic;

class C_HF extends C_HD {
   static final int f390 = 1;
   C_LF f391;
   int[] f392;
   C_j_D f393;
   C__B f394;
   int f395;
   Object f396;

   C_HF(C_LF c_lf, int[] aint, C_j_D c_j_d, C__B c__b) {
      super(c_lf.f820);
      this.f391 = c_lf;
      this.f392 = aint;
      this.f393 = c_j_d;
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
         c_hf1.f393 = (C_j_D)this.f393.clone();
      }

      if (this.f394 != null) {
         c_hf1.f394 = (C__B)this.f394.clone();
      }

      c_hf1.f395 = 0;
      c_hf1.f396 = null;
      return c_hf1;
   }

   C_LF m688() {
      return this.f391;
   }

   int[] m689() {
      return this.f392;
   }

   C_j_D m690() {
      return this.f393;
   }

   C__B m691() {
      return this.f394;
   }

   int m692() {
      return this.f391.f526.length;
   }

   C_RF m693(int i) {
      return this.m694(i, null);
   }

   C_RF m694(int i, C_a_ c_a_) {
      C_MB c_mb = new C_MB();
      C_RF c_rf = this.f391.f526[this.f392[i]].m1239(this.f393, c_mb);
      this.f394.m1574(this.f391.f526[this.f392[i]], c_rf, c_mb, c_a_);
      return c_rf;
   }

   C_RF m695() {
      return this.m696(null);
   }

   C_RF m696(C_a_ c_a_) {
      C_MB c_mb = new C_MB();
      C_RF c_rf = this.f391.f527.m1239(this.f393, c_mb);
      this.f394.m1574(this.f391.f527, c_rf, c_mb, c_a_);
      return c_rf;
   }

   boolean m697(C_RF c_rf) {
      return c_rf.m1274(this.f393) && this.f394.m1578(c_rf);
   }

   @Override
   boolean m600(C_a_ c_a_) {
      C_VB c_vb = LPDerivation.getRule(c_a_.f939);
      if (c_vb != null && c_vb.m1375(this.f391)) {
         int i = this.f391.f526.length;
         c_a_.getClass();
         if (!c_a_.f944 && !c_a_.f945 ? i <= c_a_.f938 : i == c_a_.f938) {
            for (int j = 0; j < i; j++) {
               C_RF c_rf = this.m693(j);
               if (c_rf.m1259() != null || !c_rf.m1235(c_a_.m1628(j - i))) {
                  return false;
               }
            }

            if (this.f391.m956(c_vb.m1373(c_a_.f935.f317.f915, c_a_.f946 ? "manualOrDisabled" : "disabled")) == -1) {
               return false;
            } else if ((c_a_.f942 = this.m695()).m1259() != null) {
               return false;
            } else if (!c_a_.m1613(this.f393, true)) {
               return false;
            } else if (c_a_.f944 && !c_a_.m1609(true)) {
               return false;
            } else {
               c_a_.m1631(i);
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
      return this.f384 + C_e_.m1753(this.f392) + this.f393.m1896() + "," + this.f394.m1580();
   }

   static C_HF m699(String s) {
      int i;
      if ((i = s.indexOf("{")) == -1) {
         return null;
      } else {
         C_VB c_vb = LPDerivation.getRule(s.substring(0, i));
         if (c_vb != null && c_vb instanceof C_LF) {
            s = s.substring(i);
            if ((i = s.indexOf("}")) == -1) {
               return null;
            } else {
               int[] aint = C_e_.m1755(s.substring(0, i + 1));
               if (aint == null) {
                  return null;
               } else {
                  s = s.substring(i + 1);
                  if ((i = s.indexOf(",")) == -1) {
                     return null;
                  } else {
                     C_j_D c_j_d = C_j_D.m1897(s.substring(0, i));
                     if (c_j_d == null) {
                        return null;
                     } else {
                        s = s.substring(i + 1);
                        C__B c__b = C__B.m1581(s);
                        return new C_HF((C_LF)c_vb, aint, c_j_d, c__b);
                     }
                  }
               }
            }
         } else {
            return null;
         }
      }
   }

   C_FD m700(C_a_ c_a_) {
      return new C_FD(this, c_a_);
   }
}
