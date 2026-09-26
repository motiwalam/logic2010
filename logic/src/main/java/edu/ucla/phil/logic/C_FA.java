package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_FA extends Hashtable {
   String f302 = null;

   private C_FA(String s, int i) {
      int j = m523(s, i + 1);
      int k = m525(s, j);
      int l = s.indexOf(62, i + 1);
      if (k > l) {
         k = l;
      }

      this.f302 = s.substring(j, k);
      this.m521(s.substring(k, l));
   }

   static C_FA m515(String s, int i) {
      if (i == -1) {
         return null;
      } else {
         return m532(s, i) ? null : new C_FA(s, i);
      }
   }

   static String m516(String s, int i) {
      return m527(s, i, m534(s, i));
   }

   static String m517(String s, int i) {
      if (i == -1) {
         return null;
      } else {
         return !m532(s, i) ? null : s.substring(i + 4, m534(s, i) - 3).trim();
      }
   }

   String m518(String s) {
      return this.m519(s, null);
   }

   String m519(String s, String s1) {
      String s2 = (String)this.get(s.toUpperCase());
      return s2 == null ? s1 : s2;
   }

   int m520(String s, int i) {
      String s1 = this.m518(s);
      if (s1 == null) {
         return i;
      } else {
         try {
            return Integer.parseInt(s1);
         } catch (NumberFormatException numberformatexception) {
            return i;
         }
      }
   }

   void m521(String s) {
      int i = s.length();
      int l = 0;

      int k;
      while ((k = m523(s, l)) < i) {
         l = m525(s, k);
         int j = s.indexOf(61, k);
         if (j != -1 && j < l) {
            l = j;
         }

         String s1 = s.substring(k, l).toUpperCase();
         if ((k = m523(s, l)) < i && s.charAt(k) == '=') {
            k = m523(s, k + 1);
            int i1 = "\"'".indexOf(s.charAt(k));
            if (i1 == -1) {
               l = m525(s, k);
            } else {
               l = s.indexOf("\"'".charAt(i1), ++k);
               if (l == -1) {
                  return;
               }
            }

            this.put(s1, m522(s.substring(k, l)));
            if (i1 != -1) {
               l++;
            }
         } else {
            this.put(s1, "");
            l = k;
         }
      }
   }

   static String m522(String s) {
      String[] astring = new String[]{"quot", "amp", "lt", "gt", "nbsp"};
      String[] astring1 = new String[]{"\"", "&", "<", ">", " "};
      String s1 = "";
      int l = 0;

      int i;
      while ((i = s.indexOf("&", l)) != -1) {
         s1 = s1 + s.substring(l, i);
         l = s.indexOf(";", i);
         if (l == -1) {
            l = i;
            break;
         }

         boolean flag = false;
         String s2 = s.substring(i + 1, l);
         l++;
         if (s2.length() > 0 && s2.charAt(0) == '#') {
            try {
               s1 = s1 + (char)Integer.parseInt(s2.substring(1));
               flag = true;
            } catch (NumberFormatException numberformatexception) {
            }
         } else {
            int j = astring.length;

            for (int k = 0; k < j; k++) {
               if (s2.equalsIgnoreCase(astring[k])) {
                  s1 = s1 + astring1[k];
                  flag = true;
                  break;
               }
            }
         }

         if (!flag) {
            s1 = s1 + "&" + s2 + ";";
         }
      }

      return s1 + s.substring(l);
   }

   static int m523(String s, int i) {
      return m524(s, i, s.length());
   }

   static int m524(String s, int i, int j) {
      if (i > j) {
         return j;
      } else {
         int k = i;

         while (k < j && Character.isWhitespace(s.charAt(k))) {
            k++;
         }

         return k;
      }
   }

   static int m525(String s, int i) {
      return m526(s, i, s.length());
   }

   static int m526(String s, int i, int j) {
      if (i > j) {
         return j;
      } else {
         int k = i;

         while (k < j && !Character.isWhitespace(s.charAt(k))) {
            k++;
         }

         return k;
      }
   }

   static String m527(String s, int i, int j) {
      if (j != -1 && !m533(s, i, j)) {
         int k = m524(s, i + 1, j - 1);
         int l = m526(s, k, j - 1);
         return s.substring(k, l);
      } else {
         return null;
      }
   }

   static int m528(String s, String s1, int i) {
      return m529(s, s1, i, s1.length());
   }

   static int m529(String s, String s1, int i, int j) {
      while (i < j) {
         int k = s1.indexOf(60, i);
         if (k != -1 && k <= j - 1) {
            i = m535(s1, k, j);
            if (i == -1) {
               return -1;
            }

            if (s != null && !s.equalsIgnoreCase(m527(s1, k, i))) {
               continue;
            }

            return k;
         }

         return -1;
      }

      return -1;
   }

   static int m530(String s, int i) {
      return m531(s, i, s.length());
   }

   static int m531(String s, int i, int j) {
      while (i < j) {
         int k = s.indexOf(60, i);
         if (k != -1 && k <= j - 1) {
            i = m535(s, k, j);
            if (i == -1) {
               return -1;
            }

            if (!m533(s, k, j)) {
               continue;
            }

            return k;
         }

         return -1;
      }

      return -1;
   }

   static boolean m532(String s, int i) {
      return m533(s, i, s.length());
   }

   static boolean m533(String s, int i, int j) {
      return i > j - 4 ? false : s.startsWith("<!--", i);
   }

   static int m534(String s, int i) {
      return m535(s, i, s.length());
   }

   static int m535(String s, int i, int j) {
      if (i == -1 || i > j - 1 || s.charAt(i) != '<') {
         return -1;
      } else if (m533(s, i, j)) {
         int l = s.indexOf("-->", i + 4);
         return l == -1 ? -1 : (l + 3 > j ? -1 : l + 3);
      } else {
         int k = s.indexOf(">", i + 1);
         return k == -1 ? -1 : (k + 1 > j ? -1 : k + 1);
      }
   }
}
