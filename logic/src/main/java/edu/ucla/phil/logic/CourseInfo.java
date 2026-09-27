package edu.ucla.phil.logic;

import java.util.Vector;

class CourseInfo {
   String institution;
   String term;
   String course;
   Integer courseUid;
   String comment;
   String[] instructors;
   String[] assistants;
   Integer extraNumber;
   static CourseInfo[] allCourses = null;

   CourseInfo(String s, String s1, String s2, Integer integer, String s3, Integer integer1, String s4) {
      this.institution = s == null ? "" : s;
      this.term = s1 == null ? "" : s1;
      this.course = s2 == null ? "" : s2;
      this.courseUid = integer;
      this.comment = s3;
      this.extraNumber = integer1;
      this.instructors = parseStaffList(s4, 0);
      this.assistants = parseStaffList(s4, 1);
   }

   static String formatTermName(String s, Institution institutionx) {
      return s != null && institutionx != null ? institutionx.formatTermName(institutionx.parseTerm(s)) : s;
   }

   static String[] formatTermNames(String[] astring, Institution institutionx) {
      if (astring == null) {
         return null;
      } else {
         int i = astring.length;
         String[] astring1 = new String[i];

         for (int j = 0; j < i; j++) {
            astring1[j] = formatTermName(astring[j], institutionx);
         }

         return astring1;
      }
   }

   static String[] listInstitutions(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].institution;
            if (!containsIgnoreCase(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] listTerms(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].term;
            if (!containsIgnoreCase(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] listCourses(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].course;
            if (!containsIgnoreCase(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] parseStaffList(String s, int i) {
      if (s == null) {
         return null;
      } else {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         delimitedtokenizer.setInput(s);

         while (--i >= 0 && delimitedtokenizer.nextToken() != null) {
         }

         String s2 = delimitedtokenizer.nextToken();
         if (s2 == null) {
            return null;
         } else {
            Vector vector = new Vector();
            delimitedtokenizer = new DelimitedTokenizer("\\;");
            delimitedtokenizer.setInput(s2);

            String s1;
            while ((s1 = delimitedtokenizer.nextToken()) != null && (s1 = s1.trim()).length() != 0) {
               vector.add(s1);
            }

            if (vector.size() == 0) {
               return null;
            } else {
               String[] astring = new String[vector.size()];
               vector.copyInto(astring);
               return astring;
            }
         }
      }
   }

   static boolean containsIgnoreCase(Vector vector, String s) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof String && ((String)object).equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   static CourseInfo[] findCoursesFor(UserInfo userinfo) {
      String s = userinfo.getInstitution();
      String s1 = userinfo.getTerm();
      String s2 = userinfo.getClassName();
      CourseInfo[] acourseinfo = findCourses(s, s1, s2);
      if (acourseinfo == null) {
         return null;
      } else {
         if (acourseinfo.length == 0) {
            acourseinfo = findCourses(s, s1);
         }

         if (acourseinfo == null) {
            return null;
         } else {
            if (acourseinfo.length == 0) {
               acourseinfo = findCourses(s);
            }

            if (acourseinfo == null) {
               return null;
            } else {
               if (userinfo instanceof NewUserInfo && acourseinfo.length == 0) {
                  acourseinfo = getAllCourses();
               }

               return acourseinfo;
            }
         }
      }
   }

   static CourseInfo[] getAllCourses() {
      if (allCourses == null && !LogicProgram.localMode) {
         ServerConnection.fetchCourseList();
      }

      return allCourses;
   }

   static CourseInfo[] findCourses(String s) {
      CourseInfo[] acourseinfo = getAllCourses();
      if (acourseinfo != null && s != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s.equalsIgnoreCase(acourseinfo[j].institution)) {
               vector.addElement(acourseinfo[j]);
            }
         }

         CourseInfo[] acourseinfo1 = new CourseInfo[vector.size()];
         vector.copyInto(acourseinfo1);
         return acourseinfo1;
      } else {
         return acourseinfo;
      }
   }

   static CourseInfo[] findCourses(String s, String s1) {
      CourseInfo[] acourseinfo = findCourses(s);
      if (acourseinfo != null && s1 != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s1.equalsIgnoreCase(acourseinfo[j].term)) {
               vector.addElement(acourseinfo[j]);
            }
         }

         CourseInfo[] acourseinfo1 = new CourseInfo[vector.size()];
         vector.copyInto(acourseinfo1);
         return acourseinfo1;
      } else {
         return acourseinfo;
      }
   }

   static CourseInfo[] findCourses(String s, String s1, String s2) {
      CourseInfo[] acourseinfo = findCourses(s, s1);
      if (acourseinfo != null && s2 != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s2.equalsIgnoreCase(acourseinfo[j].course)) {
               vector.addElement(acourseinfo[j]);
            }
         }

         CourseInfo[] acourseinfo1 = new CourseInfo[vector.size()];
         vector.copyInto(acourseinfo1);
         return acourseinfo1;
      } else {
         return acourseinfo;
      }
   }
}
