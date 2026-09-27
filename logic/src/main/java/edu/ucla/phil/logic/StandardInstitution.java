package edu.ucla.phil.logic;

class StandardInstitution extends Institution {
   String code;
   String[] seasonCodes;
   String[] seasonFormats;

   StandardInstitution(String s) {
      this.code = s;
      this.seasonCodes = new String[]{"W", "S", "SP", "SS", "SSA", "SSB", "SSC", "F"};
      this.seasonFormats = new String[]{"Winter %y", "Spring %y", "Spring %y", "Summer %y", "Summer %y A", "Summer %y B", "Summer %y C", "Fall %y"};
   }

   @Override
   String getCode() {
      return this.code;
   }

   @Override
   String normalizeStudentId(String s) {
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
   TermCode parseTerm(String s) {
      if (s == null) {
         return null;
      } else {
         String s1 = s.trim();
         int i = 0;
         int j = s1.length();

         while (i < j && "0123456789".indexOf(s1.charAt(i)) != -1) {
            i++;
         }

         Integer integer = LogicProgram.parseInteger(s1.substring(0, i));
         int k = LogicProgram.indexOf(this.seasonCodes, s1.substring(i).toUpperCase());
         return integer != null && k != -1 ? new TermCode(integer, k) : null;
      }
   }

   @Override
   String formatTermCode(TermCode termcode) {
      return termcode == null ? "" : termcode.year + this.seasonCodes[termcode.season];
   }

   @Override
   String formatTermName(TermCode termcode) {
      if (termcode == null) {
         return "";
      } else {
         String[] astring = new String[]{"%%", "%y"};
         String[] astring1 = new String[]{"%", termcode.year + ""};
         return LogicProgram.translateSymbols(this.seasonFormats[termcode.season], astring, astring1);
      }
   }
}
