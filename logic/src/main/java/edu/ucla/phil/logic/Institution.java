package edu.ucla.phil.logic;

import java.util.Hashtable;

abstract class Institution {
   static Institution[] KNOWN_INSTITUTIONS = new Institution[]{
      new UclaInstitution(), new UcsbInstitution(), new UcsdInstitution(), new YaleInstitution(), new SunysbInstitution(), new BounInstitution()
   };
   static Hashtable byName = new Hashtable();

   static Institution forName(String s) {
      if (s == null) {
         return null;
      } else {
         Institution institution = (Institution)byName.get(s.toUpperCase());
         if (institution != null) {
            return institution;
         } else {
            StandardInstitution standardinstitution = new StandardInstitution(s);
            byName.put(standardinstitution.getCode().toUpperCase(), standardinstitution);
            return standardinstitution;
         }
      }
   }

   abstract String getCode();

   abstract String normalizeStudentId(String s);

   abstract TermCode parseTerm(String s);

   abstract String formatTermCode(TermCode termcode);

   abstract String formatTermName(TermCode termcode);

   String displayTerm(String s) {
      TermCode termcode = this.parseTerm(s);
      return termcode == null ? s : this.formatTermName(termcode);
   }

   static {
      for (int i = 0; i < KNOWN_INSTITUTIONS.length; i++) {
         byName.put(KNOWN_INSTITUTIONS[i].getCode().toUpperCase(), KNOWN_INSTITUTIONS[i]);
      }
   }
}
