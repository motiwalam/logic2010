package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_H {
   String f370;
   String f371;
   String f372;
   String f373;
   boolean f374;
   static Hashtable f375 = null;
   static String f376 = "messages";

   C_H(String s) {
      this.f370 = s;
      this.f371 = s;
      this.f372 = "no further explanation available";
      this.f373 = null;
      this.f374 = false;
   }

   static boolean m410() {
      C_XB c_xb = LogicProgram.m1062(f376, false);
      if (c_xb == null) {
         return false;
      } else {
         f375 = m658(new C_XD(c_xb, true));
         return f375 != null;
      }
   }

   static boolean m657(int i) {
      Class oclass = (Class)C_U.getStaticField(i, "messageClass");
      String s = (String)C_U.getStaticField(oclass, "linkName");
      C_XB c_xb = LogicProgram.m1062(s, false);
      if (c_xb == null) {
         return false;
      } else {
         Hashtable hashtable = m658(new C_XD(c_xb, true));
         C_U.setStaticField(oclass, "messages", hashtable);
         return hashtable != null;
      }
   }

   static Hashtable m658(C_XD c_xd) {
      if (c_xd == null) {
         return null;
      } else {
         Hashtable hashtable = new Hashtable();

         while (c_xd.m1469()) {
            int i;
            if ((i = c_xd.m1475('n')) != -1) {
               C_H c_h = new C_H(c_xd.m1483(i).trim());
               if ((i = c_xd.m1478("ie")) != -1) {
                  c_h.f371 = c_xd.m1483(i).trim();
                  c_h.f374 = c_xd.m1474(i) == 'e';
               }

               if ((i = c_xd.m1475('x')) != -1) {
                  c_h.f372 = c_xd.m1483(i).trim();
               }

               if ((i = c_xd.m1475('b')) != -1) {
                  c_h.f373 = c_xd.m1483(i);
               }

               hashtable.put(c_h.f370.toLowerCase(), c_h);
            }
         }

         return hashtable;
      }
   }

   static C_H m411(String s) {
      C_H c_h = f375 == null ? null : (C_H)f375.get(s.toLowerCase());
      if (c_h == null) {
         c_h = new C_H(s);
         c_h.f371 = "bad error id";
         c_h.f372 = "The program has encountered an unknown error id.  Please report this: " + c_h.f370;
         c_h.f373 = "OK";
         c_h.f374 = true;
      }

      return c_h;
   }

   static String m412(String s) {
      return m659(m411(s));
   }

   static String m659(C_H c_h) {
      return c_h == null ? null : c_h.f372;
   }

   static String m660(String s, int i) {
      String s1 = "";
      String s2 = " ";

      while (s2.length() < i) {
         s2 = s2 + s2;
      }

      s2 = s2.substring(0, i);
      String s3 = "\\n";
      int j = -1;

      do {
         if ((j = s.indexOf(s3)) == -1) {
            s1 = s1 + s2 + s;
         } else {
            s1 = s1 + s2 + s.substring(0, j + s3.length());
            s = s.substring(j + s3.length());
         }
      } while (j != -1);

      return s1;
   }

   static String m661(String s, Hashtable hashtable) {
      return m662(s, hashtable, null);
   }

   static String m662(String s, Hashtable hashtable, C_k_A[] ac_k_a) {
      if (s == null) {
         return null;
      } else {
         String s1 = "";
         int i = 0;
         C_OA c_oa = new C_OA("\\<");
         C_OA c_oa1 = new C_OA("\\>");
         c_oa.m1132(s);
         m671(hashtable);

         while (true) {
            s1 = s1 + c_oa.m1136(true);
            if (c_oa.m1134() != '<') {
               return s1;
            }

            c_oa1.m1132(c_oa.m1133());
            String s3 = c_oa1.m1136(true);
            if (c_oa1.m1134() != '>') {
               return s1 + "<" + s3;
            }

            c_oa.m1132(c_oa1.m1133());
            s3 = s3.toLowerCase();
            if (s3.length() >= 6 && s3.substring(0, 6).equals("indent")) {
               try {
                  i = Integer.parseInt(s3.substring(6).trim());
               } catch (NumberFormatException numberformatexception) {
                  i = 0;
               }
            } else {
               String s2;
               if (hashtable != null && (s2 = (String)hashtable.get(s3)) != null) {
                  s1 = s1 + m660(s2, i);
               } else if ((s2 = m663(ac_k_a, s3)) != null) {
                  s1 = s1 + m660(s2, i);
               } else {
                  s1 = s1 + m660("<" + s3 + ">", i);
               }

               i = 0;
            }
         }
      }
   }

   static String m663(C_k_A[] ac_k_a, String s) {
      if (ac_k_a != null) {
         int i = ac_k_a.length;

         for (int j = 0; j < i; j++) {
            String s1 = ac_k_a[j] == null ? null : ac_k_a[j].m547(s);
            if (s1 != null) {
               return s1;
            }
         }
      }

      return null;
   }

   static Hashtable m664(Hashtable hashtable, String s, String s1) {
      if (hashtable == null) {
         hashtable = new Hashtable();
      }

      if (s1 == null) {
         s1 = "";
      }

      hashtable.put(s.toLowerCase(), s1);
      return hashtable;
   }

   static Hashtable m665(Hashtable hashtable, Hashtable hashtable1) {
      return LogicProgram.m1050(hashtable, hashtable1, true);
   }

   static Hashtable m666(String s, String s1) {
      return m664(null, s.toLowerCase(), s1);
   }

   static Hashtable m667(String s, String s1, String s2, String s3) {
      return m664(m666(s, s1), s2, s3);
   }

   static Hashtable m668(String s, String s1, String s2, String s3, String s4, String s5) {
      return m664(m667(s, s1, s2, s3), s4, s5);
   }

   static Hashtable m669(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
      return m664(m668(s, s1, s2, s3, s4, s5), s6, s7);
   }

   static Hashtable m670(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
      return m664(m669(s, s1, s2, s3, s4, s5, s6, s7), s8, s9);
   }

   static void m671(Hashtable hashtable) {
      if (hashtable != null) {
         String s = (String)hashtable.get("n");
         if (s != null) {
            s = s.trim().toLowerCase();
            if (!s.equals("1") && !s.equals("one") && !s.equals("a") && !s.equals("an") && !s.equals("the")) {
               hashtable.put("s", "s");
            } else {
               hashtable.put("s", "");
            }
         }
      }
   }
}
