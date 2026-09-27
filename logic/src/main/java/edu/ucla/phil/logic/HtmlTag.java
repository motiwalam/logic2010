package edu.ucla.phil.logic;

import java.util.Hashtable;

class HtmlTag extends Hashtable {
   String tagName = null;

   private HtmlTag(String s, int i) {
      int j = skipWhitespace(s, i + 1);
      int k = skipToWhitespace(s, j);
      int l = s.indexOf(62, i + 1);
      if (k > l) {
         k = l;
      }

      this.tagName = s.substring(j, k);
      this.parseAttributes(s.substring(k, l));
   }

   static HtmlTag parseTagAt(String s, int i) {
      if (i == -1) {
         return null;
      } else {
         return isCommentAt(s, i) ? null : new HtmlTag(s, i);
      }
   }

   static String getTagNameAt(String s, int i) {
      return getTagName(s, i, findTagEnd(s, i));
   }

   static String getCommentTextAt(String s, int i) {
      if (i == -1) {
         return null;
      } else {
         return !isCommentAt(s, i) ? null : s.substring(i + 4, findTagEnd(s, i) - 3).trim();
      }
   }

   String getAttribute(String s) {
      return this.getAttribute(s, null);
   }

   String getAttribute(String s, String s1) {
      String s2 = (String)this.get(s.toUpperCase());
      return s2 == null ? s1 : s2;
   }

   int getIntAttribute(String s, int i) {
      String s1 = this.getAttribute(s);
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

   void parseAttributes(String s) {
      int i = s.length();
      int l = 0;

      int k;
      while ((k = skipWhitespace(s, l)) < i) {
         int k1 = skipToWhitespace(s, k);
         int j = s.indexOf(61, k);
         if (j != -1 && j < k1) {
            k1 = j;
         }

         String s1 = s.substring(k, k1).toUpperCase();
         if ((k = skipWhitespace(s, k1)) < i && s.charAt(k) == '=') {
            int j1 = skipWhitespace(s, k + 1);
            int i1 = "\"'".indexOf(s.charAt(j1));
            if (i1 == -1) {
               l = skipToWhitespace(s, j1);
            } else {
               l = s.indexOf("\"'".charAt(i1), ++j1);
               if (l == -1) {
                  return;
               }
            }

            this.put(s1, decodeEntities(s.substring(j1, l)));
            if (i1 != -1) {
               l++;
            }
         } else {
            this.put(s1, "");
            l = k;
         }
      }
   }

   static String decodeEntities(String s) {
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

   static int skipWhitespace(String s, int i) {
      return skipWhitespace(s, i, s.length());
   }

   static int skipWhitespace(String s, int i, int j) {
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

   static int skipToWhitespace(String s, int i) {
      return skipToWhitespace(s, i, s.length());
   }

   static int skipToWhitespace(String s, int i, int j) {
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

   static String getTagName(String s, int i, int j) {
      if (j != -1 && !isCommentAt(s, i, j)) {
         int k = skipWhitespace(s, i + 1, j - 1);
         int l = skipToWhitespace(s, k, j - 1);
         return s.substring(k, l);
      } else {
         return null;
      }
   }

   static int findTag(String s, String s1, int i) {
      return findTag(s, s1, i, s1.length());
   }

   static int findTag(String s, String s1, int i, int j) {
      while (i < j) {
         int k = s1.indexOf(60, i);
         if (k != -1 && k <= j - 1) {
            i = findTagEnd(s1, k, j);
            if (i == -1) {
               return -1;
            }

            if (s != null && !s.equalsIgnoreCase(getTagName(s1, k, i))) {
               continue;
            }

            return k;
         }

         return -1;
      }

      return -1;
   }

   static int findComment(String s, int i) {
      return findComment(s, i, s.length());
   }

   static int findComment(String s, int i, int j) {
      while (i < j) {
         int k = s.indexOf(60, i);
         if (k != -1 && k <= j - 1) {
            i = findTagEnd(s, k, j);
            if (i == -1) {
               return -1;
            }

            if (!isCommentAt(s, k, j)) {
               continue;
            }

            return k;
         }

         return -1;
      }

      return -1;
   }

   static boolean isCommentAt(String s, int i) {
      return isCommentAt(s, i, s.length());
   }

   static boolean isCommentAt(String s, int i, int j) {
      return i > j - 4 ? false : s.startsWith("<!--", i);
   }

   static int findTagEnd(String s, int i) {
      return findTagEnd(s, i, s.length());
   }

   static int findTagEnd(String s, int i, int j) {
      if (i == -1 || i > j - 1 || s.charAt(i) != '<') {
         return -1;
      } else if (isCommentAt(s, i, j)) {
         int l = s.indexOf("-->", i + 4);
         return l == -1 ? -1 : (l + 3 > j ? -1 : l + 3);
      } else {
         int k = s.indexOf(">", i + 1);
         return k == -1 ? -1 : (k + 1 > j ? -1 : k + 1);
      }
   }
}
