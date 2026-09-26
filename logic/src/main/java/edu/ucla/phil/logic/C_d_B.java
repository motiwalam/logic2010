package edu.ucla.phil.logic;

class C_d_B implements C_EA {
   @Override
   public boolean m493(Object object, Object object1) {
      if (object instanceof C_MF && object1 instanceof C_MF) {
         int i = this.m1679(((C_MF)object1).f620, ((C_MF)object).f620);
         if (i != 0) {
            return i > 0;
         } else {
            C_SF c_sf = C_SF.m1293(((C_MF)object1).f620);
            C_P c_p = c_sf.m1296(((C_MF)object).f621);
            C_P c_p1 = c_sf.m1296(((C_MF)object1).f621);
            if (c_p1 != null && c_p != null) {
               i = c_p1.m1172(c_p);
               if (i != 0) {
                  return i > 0;
               }
            }

            i = this.m1679(((C_MF)object1).f622, ((C_MF)object).f622);
            return i >= 0;
         }
      } else {
         throw new IllegalArgumentException("LPLogicCourseOrder expected two LPLogicCourses.");
      }
   }

   private int m1679(String s, String s1) {
      return s.toUpperCase().compareTo(s1.toUpperCase());
   }
}
