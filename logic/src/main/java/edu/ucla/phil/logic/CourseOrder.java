package edu.ucla.phil.logic;

class CourseOrder implements OrderPredicate {
   @Override
   public boolean inOrder(Object object, Object object1) {
      if (object instanceof CourseInfo && object1 instanceof CourseInfo) {
         int i = this.compareIgnoreCase(((CourseInfo)object1).institution, ((CourseInfo)object).institution);
         if (i != 0) {
            return i > 0;
         } else {
            Institution institution = Institution.forName(((CourseInfo)object1).institution);
            TermCode termcode = institution.parseTerm(((CourseInfo)object).term);
            TermCode termcode1 = institution.parseTerm(((CourseInfo)object1).term);
            if (termcode1 != null && termcode != null) {
               i = termcode1.compareTerm(termcode);
               if (i != 0) {
                  return i > 0;
               }
            }

            i = this.compareIgnoreCase(((CourseInfo)object1).course, ((CourseInfo)object).course);
            return i >= 0;
         }
      } else {
         throw new IllegalArgumentException("LPLogicCourseOrder expected two LPLogicCourses.");
      }
   }

   private int compareIgnoreCase(String s, String s1) {
      return s.toUpperCase().compareTo(s1.toUpperCase());
   }
}
