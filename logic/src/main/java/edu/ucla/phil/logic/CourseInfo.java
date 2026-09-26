package edu.ucla.phil.logic;

import java.util.Vector;

class CourseInfo {
   String f620;
   String f621;
   String f622;
   Integer f623;
   String f624;
   String[] f625;
   String[] f626;
   Integer f627;
   static CourseInfo[] f628 = null;

   CourseInfo(String s, String s1, String s2, Integer integer, String s3, Integer integer1, String s4) {
      this.f620 = s == null ? "" : s;
      this.f621 = s1 == null ? "" : s1;
      this.f622 = s2 == null ? "" : s2;
      this.f623 = integer;
      this.f624 = s3;
      this.f627 = integer1;
      this.f625 = m1114(s4, 0);
      this.f626 = m1114(s4, 1);
   }

   static String m1109(String s, Institution institution) {
      return s != null && institution != null ? institution.m1298(institution.m1296(s)) : s;
   }

   static String[] m1110(String[] astring, Institution institution) {
      if (astring == null) {
         return null;
      } else {
         int i = astring.length;
         String[] astring1 = new String[i];

         for (int j = 0; j < i; j++) {
            astring1[j] = m1109(astring[j], institution);
         }

         return astring1;
      }
   }

   static String[] m1111(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].f620;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1112(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].f621;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1113(CourseInfo[] acourseinfo) {
      if (acourseinfo == null) {
         return null;
      } else {
         int i = acourseinfo.length;
         Vector vector = new Vector();

         for (int j = 0; j < i; j++) {
            String s = acourseinfo[j].f622;
            if (!m1115(vector, s)) {
               vector.addElement(s);
            }
         }

         String[] astring = new String[vector.size()];
         vector.copyInto(astring);
         return astring;
      }
   }

   static String[] m1114(String s, int i) {
      if (s == null) {
         return null;
      } else {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         delimitedtokenizer.m1132(s);

         while (--i >= 0 && delimitedtokenizer.m1135() != null) {
         }

         String s2 = delimitedtokenizer.m1135();
         if (s2 == null) {
            return null;
         } else {
            Vector vector = new Vector();
            delimitedtokenizer = new DelimitedTokenizer("\\;");
            delimitedtokenizer.m1132(s2);

            String s1;
            while ((s1 = delimitedtokenizer.m1135()) != null && (s1 = s1.trim()).length() != 0) {
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

   static boolean m1115(Vector vector, String s) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof String && ((String)object).equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   static CourseInfo[] m1116(UserInfo userinfo) {
      String s = userinfo.getInstitution();
      String s1 = userinfo.getTerm();
      String s2 = userinfo.getClassName();
      CourseInfo[] acourseinfo = m1120(s, s1, s2);
      if (acourseinfo == null) {
         return null;
      } else {
         if (acourseinfo.length == 0) {
            acourseinfo = m1119(s, s1);
         }

         if (acourseinfo == null) {
            return null;
         } else {
            if (acourseinfo.length == 0) {
               acourseinfo = m1118(s);
            }

            if (acourseinfo == null) {
               return null;
            } else {
               if (userinfo instanceof NewUserInfo && acourseinfo.length == 0) {
                  acourseinfo = m1117();
               }

               return acourseinfo;
            }
         }
      }
   }

   static CourseInfo[] m1117() {
      if (f628 == null) {
         ServerConnection.m917();
      }

      return f628;
   }

   static CourseInfo[] m1118(String s) {
      CourseInfo[] acourseinfo = m1117();
      if (acourseinfo != null && s != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s.equalsIgnoreCase(acourseinfo[j].f620)) {
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

   static CourseInfo[] m1119(String s, String s1) {
      CourseInfo[] acourseinfo = m1118(s);
      if (acourseinfo != null && s1 != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s1.equalsIgnoreCase(acourseinfo[j].f621)) {
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

   static CourseInfo[] m1120(String s, String s1, String s2) {
      CourseInfo[] acourseinfo = m1119(s, s1);
      if (acourseinfo != null && s2 != null) {
         Vector vector = new Vector();
         int i = acourseinfo.length;

         for (int j = 0; j < i; j++) {
            if (s2.equalsIgnoreCase(acourseinfo[j].f622)) {
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
