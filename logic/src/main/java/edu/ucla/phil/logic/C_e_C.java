package edu.ucla.phil.logic;

class C_e_C extends Institution {
   String f1071;
   String[] f1072;
   String[] f1073;

   C_e_C(String s) {
      this.f1071 = s;
      this.f1072 = new String[]{"W", "S", "SP", "SS", "SSA", "SSB", "SSC", "F"};
      this.f1073 = new String[]{"Winter %y", "Spring %y", "Spring %y", "Summer %y", "Summer %y A", "Summer %y B", "Summer %y C", "Fall %y"};
   }

   @Override
   String m1294() {
      return this.f1071;
   }

   @Override
   String m1295(String s) {
      if (s == null) {
         return null;
      } else {
         String s1 = "";
         boolean flag = false;
         int i = s.length();

         for (int j = 0; j < i; j++) {
            char c0 = s.charAt(j);
            if ("\t -".indexOf(c0) == -1) {
               s1 = s1 + c0;
            }
         }

         return s1;
      }
   }

   @Override
   C_P m1296(String s) {
      if (s == null) {
         return null;
      } else {
         s = s.trim();
         int i = 0;
         int j = s.length();

         while (i < j && "0123456789".indexOf(s.charAt(i)) != -1) {
            i++;
         }

         Integer integer = LogicProgram.parseInteger(s.substring(0, i));
         int k = LogicProgram.m1051(this.f1072, s.substring(i).toUpperCase());
         return integer != null && k != -1 ? new C_P(integer, k) : null;
      }
   }

   @Override
   String m1297(C_P c_p) {
      return c_p == null ? "" : c_p.f668 + this.f1072[c_p.f669];
   }

   @Override
   String m1298(C_P c_p) {
      if (c_p == null) {
         return "";
      } else {
         String[] astring = new String[]{"%%", "%y"};
         String[] astring1 = new String[]{"%", c_p.f668 + ""};
         return LogicProgram.m995(this.f1073[c_p.f669], astring, astring1);
      }
   }
}
