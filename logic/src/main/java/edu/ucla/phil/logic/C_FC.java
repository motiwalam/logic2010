package edu.ucla.phil.logic;

import java.util.Hashtable;

public class C_FC implements ResponseHandler {
   ServerSession f305;
   String f306;
   String f307;
   String f308;
   String f309;
   Integer f310;

   String m538() {
      return Scrambler.md5Base64(this.f307.trim());
   }

   String m539() {
      return LogicProgram.m995(this.f307, LogicConstants.maggie, LogicProgram.f598);
   }

   String m540() {
      return this.f309 != null && this.f309.length() > 255 ? this.f309.substring(0, 252) + "..." : this.f309;
   }

   @Override
   public void m4(String s, Hashtable hashtable) {
      this.f305.m4(s, hashtable);
   }

   @Override
   public ErrorRef m5() {
      return this.f305.m5();
   }
}
