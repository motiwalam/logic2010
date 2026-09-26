package edu.ucla.phil.logic;

import java.util.Hashtable;

public class C_FC implements C_v_ {
   C_0C f305;
   String f306;
   String f307;
   String f308;
   String f309;
   Integer f310;

   String m538() {
      return C_z_D.m2225(this.f307.trim());
   }

   String m539() {
      return LogicProgram.m995(this.f307, C_n_A.maggie, LogicProgram.f598);
   }

   String m540() {
      return this.f309 != null && this.f309.length() > 255 ? this.f309.substring(0, 252) + "..." : this.f309;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f305.m4(s, hashtable);
   }

   @Override
   public C_c_B m5() {
      return this.f305.m5();
   }
}
