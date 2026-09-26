package edu.ucla.phil.logic;

class C_d_B implements C_EA {
   @Override
   public boolean m493(Object object, Object object1) {
      if (object instanceof CourseInfo && object1 instanceof CourseInfo) {
         int i = this.m1679(((CourseInfo)object1).f620, ((CourseInfo)object).f620);
         if (i != 0) {
            return i > 0;
         } else {
            Institution institution = Institution.m1293(((CourseInfo)object1).f620);
            C_P c_p = institution.m1296(((CourseInfo)object).f621);
            C_P c_p1 = institution.m1296(((CourseInfo)object1).f621);
            if (c_p1 != null && c_p != null) {
               i = c_p1.m1172(c_p);
               if (i != 0) {
                  return i > 0;
               }
            }

            i = this.m1679(((CourseInfo)object1).f622, ((CourseInfo)object).f622);
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
