package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class C_a_ implements C_k_A, C_DE {
   C_G f935;
   Vector f936;
   Vector f937;
   int f938;
   String f939;
   String f940;
   C_RF f941;
   C_RF f942;
   int f943;
   boolean f944;
   boolean f945;
   boolean f946;
   boolean f947;
   C_c_B f948;
   final boolean f949 = true;
   final boolean f950 = false;
   boolean f951;
   boolean f952;
   Vector f953;
   Vector f954;
   Vector f955;
   Vector f956;
   C_LF[] f957;
   C_LF[] f958;
   C_LF[] f959;
   int f960;
   int f961;
   Vector f962;

   C_a_(C_G c_g, boolean flag) {
      this.f935 = c_g;
      this.f946 = flag;
      this.m1589();
   }

   void m1589() {
      this.f936 = new Vector();
      this.f937 = new Vector();
      this.f938 = 0;
      this.f939 = null;
      this.f940 = this.f935.m9(true);
      this.f941 = this.f935.m44();
      this.f942 = null;
      this.f943 = -1;
      this.f944 = false;
      this.f945 = false;
      this.f947 = false;
      this.f948 = null;
      this.f953 = null;
      this.f951 = false;
      this.f954 = null;
      this.f952 = false;
      this.f955 = null;
      this.f956 = null;
      this.f957 = null;
      this.f958 = null;
      this.f959 = null;
      this.f962 = null;
      this.m1590();
   }

   void m1590() {
      this.f960 = -1;
      this.f961 = -1;
      if (this.f959 != null) {
         int i = this.f959.length;

         for (int j = 0; j < i; j++) {
            int k = this.f959[j].f526.length;
            if (k <= this.f938 && (!this.f944 && !this.f945 || k == this.f938)) {
               if (k > this.f961) {
                  this.f961 = k;
               }

               if (this.f960 == -1 || k < this.f960) {
                  this.f960 = k;
               }
            }
         }
      }
   }

   boolean m1591() {
      this.f939 = null;
      if (this.f940 == null) {
         return true;
      } else {
         boolean flag = false;
         char c0 = '\u0000';
         boolean flag1 = true;
         boolean flag2 = true;
         boolean flag3 = false;
         boolean flag4 = false;
         if (this.f942 != null) {
            this.f936.addElement(null);
            this.f937.addElement(this.f942);
         }

         C__ c__ = this.f935.f317.f915.problem;

         while (flag2 && flag1) {
            int k = 0;

            int l;
            for (l = this.f940.length(); k < l; k++) {
               c0 = this.f940.charAt(k);
               if (Character.isDigit(c0)) {
                  break;
               }

               if (m1596(c0)) {
                  flag1 = false;
                  break;
               }
            }

            if (k >= l) {
               if (flag3) {
                  this.m1621("dererr001");
                  return false;
               }

               if (flag4) {
                  this.m1621("dererr018");
                  return false;
               }

               if (this.f937.size() != 0) {
                  this.m1621("dererr002");
                  return false;
               }

               return true;
            }

            int i;
            for (i = k++; k < l; k++) {
               c0 = this.f940.charAt(k);
               if (m1596(c0)) {
                  if (flag1) {
                     flag2 = false;
                  }
               } else if (!Character.isDigit(c0)) {
                  break;
               }
            }

            int j = k++;
            if (flag2 && flag1) {
               if (flag3) {
                  this.m1621("dererr001");
                  return false;
               }

               if (flag4) {
                  this.m1621("dererr018");
                  return false;
               }

               C_0B c_0b;
               try {
                  c_0b = c__.m32(Integer.parseInt(this.f940.substring(i, j)));
               } catch (NumberFormatException numberformatexception) {
                  c_0b = null;
               }

               if (c_0b == null) {
                  this.m1622("dererr003", C_H.m666("remote line number", this.f940.substring(i, j)));
                  return false;
               }

               if (!this.f935.m565(c_0b)) {
                  return false;
               }

               C_RF c_rf = c_0b.m44();
               if (c_rf == null) {
                  String s = c_0b.m7(true).trim().equals("") ? "dererr004" : "dererr005";
                  this.m1622(s, C_H.m666("remote line number", this.f940.substring(i, j)));
                  return false;
               }

               this.f936.addElement(c_0b);
               this.f937.addElement(c_rf);
               this.f940 = this.f940.substring(j);
            } else {
               this.f939 = this.f940.substring(i, j);
               this.f940 = this.f940.substring(j);
               if (flag2) {
                  this.f939 = this.m1597(this.f939);
                  if (flag3) {
                     this.f939 = "ASS " + this.f939;
                  } else if (flag4) {
                     this.f939 = "SHOW " + this.f939;
                  } else {
                     if (this.f939.equals("ASS")) {
                        flag3 = true;
                        flag1 = true;
                        continue;
                     }

                     if (this.f939.equals("SHOW")) {
                        flag4 = true;
                        flag1 = true;
                        continue;
                     }
                  }
               } else {
                  this.m1621("dererr006");
               }

               return flag2;
            }
         }

         return false;
      }
   }

   boolean m1592() {
      int i = this.f940.length();

      for (int j = 0; j < i; j++) {
         char c0 = this.f940.charAt(j);
         if (Character.isDigit(c0) || m1596(c0)) {
            this.f940 = this.f940.substring(j);
            return true;
         }
      }

      return false;
   }

   Object m1593() {
      Integer integer = this.m1594();
      if (integer != null) {
         C_0B c_0b = this.f935.f317.f915.problem.m32(integer);
         if (c_0b == null) {
            return new C_c_B("dererr003", C_H.m666("remote line number", integer.toString()));
         } else if (!this.f935.m565(c_0b)) {
            return new C_c_B(null);
         } else {
            C_RF c_rf = c_0b.m44();
            if (c_rf == null) {
               String s = c_0b.m7(true).trim().equals("") ? "dererr004" : "dererr005";
               return new C_c_B(s, C_H.m666("remote line number", integer.toString()));
            } else {
               return c_rf;
            }
         }
      } else {
         int i = this.m1595();
         if (i != -1) {
            C_RF[] ac_rf = this.f935.f317.f915.premises;
            if (ac_rf == null || ac_rf.length == 0) {
               return new C_c_B("dererr029");
            } else if (i > ac_rf.length) {
               return new C_c_B("dererr030", C_H.m666("premise index", i + ""));
            } else {
               if (i == 0) {
                  if (ac_rf.length > 1) {
                     i = C_KB.m778(this.f935, ac_rf, C_n_.m1960(C_n_.m412("derdlg002"), null, this)) + 1;
                     if (i == 0) {
                        return new C_c_B(null);
                     }
                  } else {
                     i = 1;
                  }
               }

               return ac_rf[i - 1];
            }
         } else {
            return null;
         }
      }
   }

   Integer m1594() {
      int k = 0;
      int l = this.f940.length();
      boolean flag = false;

      char c0;
      for (c0 = 0; k < l; k++) {
         c0 = this.f940.charAt(k);
         if (Character.isDigit(c0)) {
            break;
         }

         if (m1596(c0)) {
            return null;
         }
      }

      if (k >= l) {
         return null;
      } else {
         int i;
         for (i = k++; k < l; k++) {
            c0 = this.f940.charAt(k);
            if (!Character.isDigit(c0)) {
               break;
            }
         }

         if (m1596(c0)) {
            return null;
         } else {
            int j = k++;
            Integer integer = LogicProgram.m1010(this.f940.substring(i, j));
            if (integer != null) {
               this.f940 = this.f940.substring(j);
            }

            return integer;
         }
      }
   }

   int m1595() {
      int k = 0;
      int l = this.f940.length();

      for (char c0 = '\u0000'; k < l; k++) {
         c0 = this.f940.charAt(k);
         if (Character.isDigit(c0)) {
            return -1;
         }

         if (m1596(c0)) {
            break;
         }
      }

      if (k >= l) {
         return -1;
      } else {
         int i;
         for (i = k++; k < l; k++) {
            char c1 = this.f940.charAt(k);
            if (!Character.isDigit(c1) && !m1596(c1)) {
               break;
            }
         }

         int j = k++;
         int i1 = m1633(this.f940.substring(i, j).toUpperCase());
         if (i1 != -1) {
            this.f940 = this.f940.substring(j);
         }

         return i1;
      }
   }

   static boolean m1596(char c0) {
      return !Character.isLetter(c0) && c0 < 256 ? "~!@#$%^&*(){}_+-=<>|/".indexOf(c0) != -1 : true;
   }

   String m1597(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf("/");
         if (i != -1) {
            if (this.f956 == null) {
               this.f956 = new Vector();
            }

            String s1 = s.substring(i + 1);
            s = s.substring(0, i);

            while ((i = s1.indexOf("/")) != -1) {
               this.f956.addElement(s1.substring(0, i));
               s1 = s1.substring(i + 1);
            }

            this.f956.addElement(s1);
         }

         return s.toUpperCase();
      }
   }

   private void m1598(C_HD c_hd) {
      this.f935.f336.setElementAt(c_hd, this.f943);
   }

   private boolean m1599() {
      this.f935.f336 = null;
      this.f935.f317.f922 = false;
      return false;
   }

   static boolean m1600(C_RF c_rf) {
      if (c_rf.f739.startsWith("?")) {
         return true;
      } else {
         int i = c_rf.m1216();

         for (int j = 0; j < i; j++) {
            if (m1600(c_rf.m1217(j))) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean m1601(C_RF c_rf, C_RF c_rf1) {
      if (!c_rf1.f739.startsWith("?")) {
         if (!c_rf.f739.equals(c_rf1.f739)) {
            return false;
         } else {
            int i = c_rf.m1216();
            if (c_rf1.m1216() != i) {
               return false;
            } else {
               for (int j = 0; j < i; j++) {
                  if (!m1601(c_rf.m1217(j), c_rf1.m1217(j))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else if (!(c_rf instanceof C_y_A)) {
         return false;
      } else if (c_rf.m1259() != null) {
         return false;
      } else if (c_rf1.f739.equals("?PNX")) {
         return m1602(c_rf);
      } else if (c_rf1.f739.equals("?NOV")) {
         return m1603(c_rf);
      } else if (c_rf1.f739.equals("?DNF")) {
         return m1605(c_rf, true);
      } else {
         return c_rf1.f739.equals("?CNF") ? m1605(c_rf, false) : c_rf1.f739.equals("?");
      }
   }

   static boolean m1602(C_RF c_rf) {
      return c_rf instanceof C_o_A ? m1602(c_rf.m1217(1)) : m1604(c_rf);
   }

   static boolean m1603(C_RF c_rf) {
      if (c_rf instanceof C_o_A) {
         return m1604(c_rf.m1217(1));
      } else {
         int i = c_rf.m1216();

         for (int j = 0; j < i; j++) {
            if (!m1603(c_rf.m1217(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean m1604(C_RF c_rf) {
      if (c_rf instanceof C_o_A) {
         return false;
      } else {
         int i = c_rf.m1216();

         for (int j = 0; j < i; j++) {
            if (!m1604(c_rf.m1217(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean m1605(C_RF c_rf, boolean flag) {
      if (c_rf instanceof C_o_A) {
         return m1605(c_rf.m1217(1), flag);
      } else {
         String s = c_rf.f739;
         if (!s.equals("->") && !s.equals("<->")) {
            int i = c_rf.m1216();

            for (int j = 0; j < i; j++) {
               C_RF c_rf1 = c_rf.m1217(j);
               String s1 = c_rf1.f739;
               if (s.equals("~")) {
                  if (s1.equals("~") || s1.equals("&") || s1.equals("|")) {
                     return false;
                  }
               } else if (flag) {
                  if (s.equals("&") && s1.equals("|")) {
                     return false;
                  }
               } else if (s.equals("|") && s1.equals("&")) {
                  return false;
               }

               if (!m1605(c_rf1, flag)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   boolean m1606(boolean flag) {
      return this.m1607(flag, false);
   }

   boolean m1607(boolean flag, boolean flag1) {
      this.f938 = this.m1626();
      this.f944 = flag;
      this.f945 = flag1;
      this.f953 = null;
      this.f951 = false;
      this.f954 = null;
      this.f952 = false;
      this.f957 = null;
      this.f958 = null;
      this.f959 = null;
      this.m1590();
      this.f943++;
      if (this.f935.f336 == null) {
         this.f935.f336 = new Vector();
      }

      int j = this.f943 + 1 - this.f935.f336.size();
      if (j > 0 || (flag || flag1) && j < 0) {
         this.f935.f336.setSize(this.f943 + 1);
      }

      C_c_B c_c_b;
      if ((c_c_b = this.f935.f317.f915.checkDerivationRule(this.f939, this.f946)) != null) {
         this.m1621(c_c_b.m716());
         return this.m1599();
      } else {
         if (!this.f939.equals("CD") && !this.f939.equals("ID") && !this.f939.equals("DD") && !this.f939.equals("UD") && !this.f939.equals("BD")) {
            if ((flag || flag1) && this.f935.f317.f918 == this.f935) {
               this.m1621("dererr009");
               return this.m1599();
            }
         } else {
            if (!flag && !flag1) {
               this.m1621("dererr007");
               return this.m1599();
            }

            boolean flag2 = this.f935.m576();
            this.f935.f334 = flag2 && this.f935.f317.f918 != this.f935;
            if (!flag2) {
               this.m1621("dererr008");
               return this.m1599();
            }
         }

         this.f935.f317.f922 = this.f935.f317.f922
            & (this.f939.equals("IE") || this.f939.equals("CIE") || this.f939.equals("BD") || this.f939.startsWith("ASS "));
         C_HD c_hd = (C_HD)this.f935.f336.elementAt(this.f943);
         if (c_hd != null) {
            if (c_hd.m600(this)) {
               this.f947 = true;
               return true;
            }

            if (this.f948 != null) {
               this.m1622(this.f948.f427, this.f948.f428);
               return this.m1599();
            }

            this.m1598(null);
         }

         if (this.f939.equals("CD")) {
            if (this.f938 != 1) {
               this.m1622("dererr010", C_H.m666("n", "1"));
               return this.m1599();
            } else {
               C_RF c_rf6 = this.f935.f317.m44();
               C_RF c_rf17 = this.m1628(-1);
               if (c_rf6 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!c_rf6.m1214().equals("->")) {
                  this.m1621("dererr013");
                  return this.m1599();
               } else if (!m1601(c_rf17, c_rf6.m1217(1))) {
                  this.m1621("dererr014");
                  return this.m1599();
               } else {
                  C_0B c_0b4;
                  if ((c_0b4 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", C_H.m666("remote line number", c_0b4.m30() + ""));
                     return this.m1599();
                  } else {
                     if (this.f935.f317.f918 == this.f935 && this.f935.f317.f915.serialMode) {
                        int i2 = this.f935.f317.f920;
                        if (this.f935.f317.f915.mixedModeDisabled && i2 != 2) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if (i2 != 0 && i2 != 1 && i2 != 2) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if ((c_c_b = this.f935.f317.f915.checkDerivationRule("CD/" + ASS_STR[i2], this.f946)) != null) {
                           this.m1621(c_c_b.f427);
                           return this.m1599();
                        }

                        this.f935.f317.f917.m549(i2 == 2 ? "derinf001" : "derinf002", 4);
                     }

                     this.m1631(1);
                     return true;
                  }
               }
            }
         } else if (this.f939.equals("ID")) {
            if (this.f938 != 2) {
               this.m1622("dererr010", C_H.m666("n", "2"));
               return this.m1599();
            } else {
               C_RF c_rf5 = this.m1628(-2);
               C_RF c_rf16 = this.m1628(-1);
               if (!c_rf5.m1255(c_rf16) && !c_rf16.m1255(c_rf5)) {
                  this.m1621("dererr017");
                  return this.m1599();
               } else {
                  C_0B c_0b3;
                  if ((c_0b3 = this.m1615(-2)) == null && (c_0b3 = this.m1615(-1)) == null) {
                     if (this.f935.f317.f918 == this.f935 && this.f935.f317.f915.serialMode) {
                        int l1 = this.f935.f317.f920;
                        if (this.f935.f317.f915.mixedModeDisabled && l1 != 1) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if (l1 != 0 && l1 != 1 && l1 != 2) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if ((c_c_b = this.f935.f317.f915.checkDerivationRule("ID/" + ASS_STR[l1], this.f946)) != null) {
                           this.m1621(c_c_b.f427);
                           return this.m1599();
                        }

                        this.f935.f317.f917.m11(l1 == 1 ? "derinf001" : "derinf002");
                     }

                     this.m1631(2);
                     return true;
                  } else {
                     this.m1622("dererr015", C_H.m666("remote line number", c_0b3.m30() + ""));
                     return this.m1599();
                  }
               }
            }
         } else if (this.f939.equals("DD")) {
            if (this.f938 != 1) {
               this.m1622("dererr010", C_H.m666("n", "1"));
               return this.m1599();
            } else {
               C_RF c_rf4 = this.f935.f317.m44();
               C_RF c_rf15 = this.m1628(-1);
               if (c_rf4 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!m1601(c_rf15, c_rf4)) {
                  this.m1621("dererr019");
                  return this.m1599();
               } else {
                  C_0B c_0b2;
                  if ((c_0b2 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", C_H.m666("remote line number", c_0b2.m30() + ""));
                     return this.m1599();
                  } else {
                     if (this.f935.f317.f918 == this.f935 && this.f935.f317.f915.serialMode) {
                        int k1 = this.f935.f317.f920;
                        if (this.f935.f317.f915.mixedModeDisabled && k1 != 0) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if (k1 != 0 && k1 != 1 && k1 != 2) {
                           this.m1621("dererr101");
                           return this.m1599();
                        }

                        if ((c_c_b = this.f935.f317.f915.checkDerivationRule("DD/" + ASS_STR[k1], this.f946)) != null) {
                           this.m1621(c_c_b.f427);
                           return this.m1599();
                        }

                        this.f935.f317.f917.m11(k1 == 0 ? "derinf001" : "derinf002");
                     }

                     this.m1631(1);
                     return true;
                  }
               }
            }
         } else if (this.f939.equals("UD")) {
            if (this.f938 != 1) {
               this.m1622("dererr010", C_H.m666("n", "1"));
               return this.m1599();
            } else {
               C_RF c_rf3 = this.f935.f317.m44();
               C_RF c_rf14 = this.m1628(-1);
               if (c_rf3 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!c_rf3.m1214().equals("@")) {
                  this.m1621("dererr021");
                  return this.m1599();
               } else if (!m1601(c_rf14, c_rf3.m1217(1))) {
                  this.m1621("dererr022");
                  return this.m1599();
               } else {
                  C_0B c_0b1;
                  if ((c_0b1 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", C_H.m666("remote line number", c_0b1.m30() + ""));
                     return this.m1599();
                  } else {
                     String s = ((C_i_)c_rf3.m1217(0)).f739;
                     if (this.m1614(s)) {
                        this.m1622("dererr023", C_H.m666("variable name", "\\l" + s + "\\l"));
                        return this.m1599();
                     } else {
                        this.m1631(1);
                        return true;
                     }
                  }
               }
            }
         } else if (this.f939.equals("BD")) {
            if (this.f938 != 1) {
               this.m1622("dererr010", C_H.m666("n", "1"));
               return this.m1599();
            } else {
               C_RF c_rf2 = this.f935.f317.m44();
               if (c_rf2 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!c_rf2.m1214().equals("<->")) {
                  this.m1621("dererr081");
                  return this.m1599();
               } else {
                  C_RF c_rf13 = this.m1628(-1);
                  int j1 = this.f935.f317.f923;
                  if (j1 == -1) {
                     if (!m1601(c_rf13, c_rf2.m1217(0)) && !m1601(c_rf13, c_rf2.m1217(1))) {
                        this.m1621("dererr102");
                        return this.m1599();
                     }
                  } else {
                     C_RF c_rf22 = j1 == 2 ? this.f935.f317.m1560(1).m44() : c_rf2.m1217(j1);
                     if ((!m1601(c_rf13, c_rf2.m1217(0)) || !m1601(c_rf22, c_rf2.m1217(1)))
                        && (!m1601(c_rf22, c_rf2.m1217(0)) || !m1601(c_rf13, c_rf2.m1217(1)))) {
                        this.m1621("dererr090");
                        return this.m1599();
                     }
                  }

                  if (this.f935.f317.f915.serialMode && !this.f935.f317.f922) {
                     this.m1621("dererr091");
                     return this.m1599();
                  } else {
                     C_0B c_0b;
                     if ((c_0b = this.m1615(-1)) != null) {
                        this.m1622("dererr015", C_H.m666("remote line number", c_0b.m30() + ""));
                        return this.m1599();
                     } else {
                        if (this.f935.f317.f918 == this.f935 && this.f935.f317.f915.serialMode) {
                           int i3 = this.f935.f317.f920;
                           if (i3 != 3) {
                              this.m1621("dererr101");
                              return this.m1599();
                           }

                           if ((c_c_b = this.f935.f317.f915.checkDerivationRule("BD/" + ASS_STR[i3], this.f946)) != null) {
                              this.m1621(c_c_b.f427);
                              return this.m1599();
                           }

                           this.f935.f317.f917.m549(i3 == 3 ? "derinf001" : "derinf002", 4);
                        }

                        this.m1631(1);
                        return true;
                     }
                  }
               }
            }
         } else if (this.f939.equals("ASS CD")) {
            if (this.f938 != 0) {
               this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
               return this.m1599();
            } else if (this.f935.m16() != 1) {
               this.m1621("dererr025");
               return this.m1599();
            } else if (this.f942 != null) {
               this.m1621("dererr026");
               return this.m1599();
            } else {
               C_RF c_rf1 = this.f935.f317.m44();
               if (c_rf1 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!c_rf1.m1214().equals("->")) {
                  this.m1621("dererr013");
                  return this.m1599();
               } else {
                  this.f935.f317.f920 = 2;
                  this.f942 = c_rf1.m1217(0);
                  if (flag && !this.m1608()) {
                     return this.m1599();
                  } else {
                     this.m1631(0);
                     return true;
                  }
               }
            }
         } else if (this.f939.equals("ASS ID")) {
            if (this.f938 != 0) {
               this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
               return this.m1599();
            } else if (this.f935.m16() != 1) {
               this.m1621("dererr025");
               return this.m1599();
            } else if (this.f942 != null) {
               this.m1621("dererr026");
               return this.m1599();
            } else {
               C_RF c_rf = this.f935.f317.m44();
               if (c_rf == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else {
                  if (flag && this.f941 != null) {
                     if (!c_rf.m1255(this.f941) && !this.f941.m1255(c_rf)) {
                        this.m1621("dererr027");
                        return this.m1599();
                     }

                     this.f942 = this.f941;
                  } else if (c_rf.m1214().equals("~")) {
                     C_RF[] ac_rf2 = new C_RF[]{c_rf.m1217(0), c_rf.m1256()};
                     int i1 = C_KB.m778(this.f935, ac_rf2, C_n_.m1960(C_n_.m412("derdlg001"), null, this));
                     if (i1 == -1) {
                        if (this.f935.f317.f915.serialMode) {
                           this.f935.f317.f915.complete = false;
                           this.m1621("dererr064");
                        } else {
                           this.m1621("dererr028");
                           this.f935.f317.f915.abort(true);
                        }

                        return this.m1599();
                     }

                     this.f942 = ac_rf2[i1];
                     this.m1598(new C_p_E(i1 == 1));
                  } else {
                     this.f942 = c_rf.m1256();
                  }

                  this.f935.f317.f920 = 1;
                  if (flag && !this.m1608()) {
                     return this.m1599();
                  } else {
                     this.m1631(0);
                     return true;
                  }
               }
            }
         } else {
            j = LogicProgram.m1051(BD_ASS, this.f939);
            if (j != -1) {
               if (this.f938 != 0) {
                  this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
                  return this.m1599();
               } else if (this.f935.m16() != 1) {
                  this.m1621("dererr025");
                  return this.m1599();
               } else if (this.f942 != null) {
                  this.m1621("dererr026");
                  return this.m1599();
               } else {
                  C_RF c_rf12 = this.f935.f317.m44();
                  if (c_rf12 == null) {
                     this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                     return this.m1599();
                  } else if (!c_rf12.m1214().equals("<->")) {
                     this.m1621("dererr081");
                     return this.m1599();
                  } else {
                     if (flag && this.f941 != null) {
                        if ((j == 1 || !c_rf12.m1217(0).m1235(this.f941)) && (j == 0 || !c_rf12.m1217(1).m1235(this.f941))) {
                           String[] astring = new String[]{"the left", "the right", "either"};
                           this.m1622("dererr092", C_H.m666("side", astring[j]));
                           return this.m1599();
                        }

                        this.f942 = this.f941;
                        this.f935.f317.f923 = j;
                     } else if (j < 2) {
                        if (m1600(this.f942 = c_rf12.m1217(j))) {
                           this.m1621("dererr093");
                           return this.m1599();
                        }

                        this.f935.f317.f923 = j;
                     } else {
                        C_RF[] ac_rf5 = new C_RF[]{c_rf12.m1217(0), c_rf12.m1217(1)};
                        int l2;
                        if (ac_rf5[0].m1235(ac_rf5[1])) {
                           l2 = 0;
                        } else if (m1600(ac_rf5[1]) && !m1600(ac_rf5[0])) {
                           l2 = 0;
                        } else if (m1600(ac_rf5[0]) && !m1600(ac_rf5[1])) {
                           l2 = 1;
                        } else {
                           if (m1600(ac_rf5[0]) && m1600(ac_rf5[1])) {
                              this.m1621("dererr093");
                              return this.m1599();
                           }

                           l2 = C_KB.m778(this.f935, ac_rf5, C_n_.m1960(C_n_.m412("derdlg001"), null, this));
                        }

                        if (l2 == -1) {
                           if (this.f935.f317.f915.serialMode) {
                              this.f935.f317.f915.complete = false;
                              this.m1621("dererr064");
                           } else {
                              this.m1621("dererr028");
                              this.f935.f317.f915.abort(true);
                           }

                           return this.m1599();
                        }

                        this.f942 = ac_rf5[l2];
                        this.m1598(new C_c_E(l2 == 1));
                        this.f935.f317.f923 = l2;
                     }

                     this.f935.f317.f920 = 3;
                     if (flag && !this.m1608()) {
                        return this.m1599();
                     } else {
                        this.m1631(0);
                        return true;
                     }
                  }
               }
            } else if (this.f939.startsWith("SHOW CONC") && "SHOW CONCLUSION".startsWith(this.f939)) {
               if (!this.m1610(true)) {
                  return this.m1599();
               } else if (this.f935.f317.f915.conclusion == null) {
                  this.m1621("dererr053");
                  return this.m1599();
               } else {
                  this.f942 = this.f935.f317.f915.conclusion.m1237();
                  return this.m1611();
               }
            } else if (this.f939.startsWith("SHOW CONS") && "SHOW CONSEQUENT".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  C_RF c_rf11 = this.f935.f317.m44();
                  if (c_rf11 != null && c_rf11.f739.equals("->")) {
                     this.f942 = c_rf11.m1217(1).m1237();
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", C_H.m667("an", "a", "expected form", "conditional"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW CORR") && "SHOW CORRCOND".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  C_RF c_rf10 = this.f935.f317.m44();
                  if (c_rf10 != null && c_rf10.f739.equals("|")) {
                     this.f942 = new C_q_F("->");
                     this.f942.m1215(c_rf10.m1217(0).m1256());
                     this.f942.m1215(c_rf10.m1217(1).m1237());
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", C_H.m667("an", "a", "expected form", "disjunction"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW CONJ") && "SHOW CONJUNCT".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  C_RF c_rf9 = this.f935.f317.m44();
                  if (c_rf9 != null && c_rf9.f739.equals("&")) {
                     C_RF[] ac_rf4 = new C_RF[]{c_rf9.m1217(0).m1237(), c_rf9.m1217(1).m1237()};
                     int k2 = C_KB.m778(this.f935, ac_rf4, "Please choose a conjunct:");
                     if (k2 == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = ac_rf4[k2];
                        return this.m1611();
                     }
                  } else {
                     this.m1622("dererr077", C_H.m667("an", "a", "expected form", "conjunction"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW COND") && "SHOW CONDITIONAL".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  C_RF c_rf8 = this.f935.f317.m44();
                  if (c_rf8 != null && c_rf8.f739.equals("<->")) {
                     C_RF[] ac_rf3 = new C_RF[]{new C_q_F("->"), null};
                     ac_rf3[0].m1215(c_rf8.m1217(0).m1237());
                     ac_rf3[0].m1215(c_rf8.m1217(1).m1237());
                     ac_rf3[1] = new C_q_F("->");
                     ac_rf3[1].m1215(c_rf8.m1217(1).m1237());
                     ac_rf3[1].m1215(c_rf8.m1217(0).m1237());
                     int j2 = C_KB.m778(this.f935, ac_rf3, "Please choose a conditional:");
                     if (j2 == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = ac_rf3[j2];
                        return this.m1611();
                     }
                  } else {
                     this.m1622("dererr077", C_H.m667("an", "a", "expected form", "biconditional"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW INST") && "SHOW INSTANCE".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  C_RF c_rf7 = this.f935.f317.m44();
                  if (c_rf7 != null && c_rf7.f739.equals("@")) {
                     this.f942 = c_rf7.m1217(1).m1237();
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", C_H.m667("an", "a", "expected form", "universal generalization"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW UNNEG") && "SHOW UNNEGATION".startsWith(this.f939)) {
               Object object3 = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object3 == null) {
                  this.m1622("dererr079", C_H.m667("an", "a", "expected form", "negation"));
                  return this.m1599();
               } else if (object3 instanceof C_c_B) {
                  c_c_b = (C_c_B)object3;
                  if (c_c_b.f427 != null) {
                     this.m1622(c_c_b.f427, c_c_b.f428);
                  }

                  return this.m1599();
               } else {
                  C_RF c_rf21 = (C_RF)object3;
                  if (!c_rf21.f739.equals("~")) {
                     String s4 = "\\l" + c_rf21 + "\\l";
                     this.m1622("dererr080", C_H.m668("remote line", s4, "an", "a", "expected form", "negation"));
                     return this.m1599();
                  } else {
                     this.f942 = c_rf21.m1217(0).m1237();
                     return this.m1611();
                  }
               }
            } else if (this.f939.startsWith("SHOW ANT") && "SHOW ANTECEDENT".startsWith(this.f939)) {
               Object object2 = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object2 == null) {
                  this.m1622("dererr079", C_H.m667("an", "a", "expected form", "(bi)conditional"));
                  return this.m1599();
               } else if (object2 instanceof C_c_B) {
                  c_c_b = (C_c_B)object2;
                  if (c_c_b.f427 != null) {
                     this.m1622(c_c_b.f427, c_c_b.f428);
                  }

                  return this.m1599();
               } else {
                  C_RF c_rf20 = (C_RF)object2;
                  if (c_rf20.f739.equals("->")) {
                     this.f942 = c_rf20.m1217(0).m1237();
                  } else {
                     if (!c_rf20.f739.equals("<->")) {
                        String s3 = "\\l" + c_rf20 + "\\l";
                        this.m1622("dererr080", C_H.m668("remote line", s3, "an", "a", "expected form", "(bi)conditional"));
                        return this.m1599();
                     }

                     if ((this.f942 = C_KB.m781(this, c_rf20, false)) == null) {
                        return this.m1599();
                     }
                  }

                  return this.m1611();
               }
            } else if (this.f939.startsWith("SHOW NEGCONS") && "SHOW NEGCONSEQUENT".startsWith(this.f939)) {
               Object object1 = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object1 == null) {
                  this.m1622("dererr079", C_H.m667("an", "a", "expected form", "(bi)conditional"));
                  return this.m1599();
               } else if (object1 instanceof C_c_B) {
                  c_c_b = (C_c_B)object1;
                  if (c_c_b.f427 != null) {
                     this.m1622(c_c_b.f427, c_c_b.f428);
                  }

                  return this.m1599();
               } else {
                  C_RF c_rf19 = (C_RF)object1;
                  if (c_rf19.f739.equals("->")) {
                     this.f942 = c_rf19.m1217(1).m1237().m1256();
                  } else {
                     if (!c_rf19.f739.equals("<->")) {
                        String s2 = "\\l" + c_rf19 + "\\l";
                        this.m1622("dererr080", C_H.m668("remote line", s2, "an", "a", "expected form", "(bi)conditional"));
                        return this.m1599();
                     }

                     if ((this.f942 = C_KB.m781(this, c_rf19, true)) == null) {
                        return this.m1599();
                     }
                  }

                  return this.m1611();
               }
            } else if (this.f939.startsWith("SHOW NEGDISJ") && "SHOW NEGDISJUNCT".startsWith(this.f939)) {
               Object object = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object == null) {
                  this.m1622("dererr079", C_H.m667("an", "a", "expected form", "disjunction"));
                  return this.m1599();
               } else if (object instanceof C_c_B) {
                  c_c_b = (C_c_B)object;
                  if (c_c_b.f427 != null) {
                     this.m1622(c_c_b.f427, c_c_b.f428);
                  }

                  return this.m1599();
               } else {
                  C_RF c_rf18 = (C_RF)object;
                  if (!c_rf18.f739.equals("|")) {
                     String s1 = "\\l" + c_rf18 + "\\l";
                     this.m1622("dererr080", C_H.m668("remote line", s1, "an", "a", "expected form", "disjunction"));
                     return this.m1599();
                  } else {
                     C_RF[] ac_rf = new C_RF[]{c_rf18.m1217(0).m1237().m1256(), c_rf18.m1217(1).m1237().m1256()};
                     int k = C_KB.m778(this.f935, ac_rf, "Please choose the negation of a disjunct:");
                     if (k == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = ac_rf[k];
                        return this.m1611();
                     }
                  }
               }
            } else {
               int i;
               if ((i = m1633(this.f939)) != -1) {
                  C_RF[] ac_rf1 = this.f935.f317.f915.premises;
                  int l = ac_rf1 == null ? 0 : ac_rf1.length;
                  if (l == 0 || !this.f935.f317.f915.problem.f917.f333) {
                     this.m1621("dererr029");
                     return this.m1599();
                  } else if (i > l) {
                     this.m1622("dererr030", C_H.m666("premise index", i + ""));
                     return this.m1599();
                  } else if ((flag || flag1) && this.f938 != 0) {
                     this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
                     return this.m1599();
                  } else if (flag && this.f941 != null) {
                     if (i == 0) {
                        if (!this.f935.f317.f915.isPremise(this.f941)) {
                           this.m1621("dererr031");
                           return this.m1599();
                        }
                     } else if (!this.f941.m1235(ac_rf1[i - 1])) {
                        this.m1622("dererr032", C_H.m667("premise index", i + "", "indexed premise", "\\l" + ac_rf1[i - 1] + "\\l"));
                        return this.m1599();
                     }

                     this.m1631(0);
                     return true;
                  } else {
                     if (i == 0) {
                        if (l > 1) {
                           i = C_KB.m778(this.f935, ac_rf1, C_n_.m1960(C_n_.m412("derdlg002"), null, this)) + 1;
                           if (i == 0) {
                              if (this.f935.f317.f915.serialMode) {
                                 this.f935.f317.f915.complete = false;
                                 this.m1621("dererr064");
                              } else {
                                 this.m1621("dererr028");
                                 this.f935.f317.f915.abort(true);
                              }

                              return this.m1599();
                           }

                           this.m1598(new C_l_(i - 1));
                        } else {
                           i = 1;
                        }
                     }

                     this.f942 = ac_rf1[i - 1];
                     if (flag && !this.m1608()) {
                        return this.m1599();
                     } else {
                        this.m1631(0);
                        return true;
                     }
                  }
               } else if (this.f939.equals("IE")) {
                  if (!flag && !flag1 ? this.f938 >= 1 : this.f938 == 1) {
                     C_GA c_ga1 = new C_GA();
                     if (!C_KB.m791(this, c_ga1)) {
                        return this.m1599();
                     } else if (!C_KB.m792(this, c_ga1)) {
                        return this.m1599();
                     } else if (!c_ga1.m600(this)) {
                        if (LogicProgram.f572) {
                           System.out.println("unexpected IE error during applyRule");
                        }

                        Hashtable hashtable1 = new Hashtable();
                        C_H.m664(hashtable1, "inner rule", c_ga1.m624(this).f820);
                        C_H.m664(hashtable1, "inner exp", "\\l" + this.m1628(-1).m1220(c_ga1.f343) + "\\l");
                        this.m1622("dererr085", hashtable1);
                        return this.m1599();
                     } else {
                        this.m1598(c_ga1);
                        return true;
                     }
                  } else {
                     this.m1622("dererr084", C_H.m666("n", "1"));
                     return this.m1599();
                  }
               } else if (!this.f939.equals("CIE")) {
                  C_j_D c_j_d = this.m1616();
                  if (c_j_d == null || !this.m1612(c_j_d)) {
                     return this.m1599();
                  } else {
                     return flag && !this.m1608() ? this.m1599() : true;
                  }
               } else if (!flag && !flag1 ? this.f938 >= 1 : this.f938 == 1) {
                  C_GA c_ga = new C_GA();
                  if (!C_KB.m791(this, c_ga)) {
                     return this.m1599();
                  } else if (!C_KB.m795(this, c_ga)) {
                     return this.m1599();
                  } else if (!c_ga.m600(this)) {
                     if (LogicProgram.f572) {
                        System.out.println("unexpected CIE error during applyRule");
                     }

                     Hashtable hashtable = new Hashtable();
                     C_H.m664(hashtable, "inner rule", c_ga.m624(this).f820);
                     C_H.m664(hashtable, "inner exp", "\\l" + this.m1628(-1).m1220(c_ga.f343) + "\\l");
                     this.m1622("dererr095", hashtable);
                     return this.m1599();
                  } else {
                     this.m1598(c_ga);
                     return true;
                  }
               } else {
                  this.m1622("dererr084", C_H.m666("n", "1"));
                  return this.m1599();
               }
            }
         }
      }
   }

   boolean m1608() {
      return this.m1609(false);
   }

   boolean m1609(boolean flag) {
      String s = this.f935.m7(false);
      if (s == null) {
         return true;
      } else {
         if (s.equals("") && this.f935.f317.f915.commandMode && this.f946) {
            this.f935.m557(1);
            this.f935.m6(this.f942.toString());
            this.f935.m594();
         } else if (this.f941 == null || !this.f941.m1235(this.f942)) {
            if (!flag) {
               boolean flag1 = this.f935.f317.f915.commandMode;
               this.m1621(this.f947 ? "dererr064" : (this.f952 ? (flag1 ? "dererr033" : "dererr103") : "dererr100"));
               this.m1623("sum", this.f942);
            }

            return false;
         }

         return true;
      }
   }

   boolean m1610(boolean flag) {
      if (this.f942 != null) {
         this.m1621("dererr020");
         return false;
      } else if (this.f938 != 0) {
         this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
         return false;
      } else if (!this.f944 && !this.f945) {
         this.m1621("dererr020");
         return false;
      } else if (!this.f946) {
         this.m1621("dererr074");
         return false;
      } else if (this.f935.f317.f916 == null != flag) {
         this.m1621(flag ? "dererr075" : "dererr076");
         return false;
      } else {
         return true;
      }
   }

   boolean m1611() {
      String s = this.f935.m9(true);
      this.f935.m571();
      if (this.f935.f340 != null) {
         this.f935.f340.setText("\"" + s + "\"");
      }

      if (this.f946 && !this.m1609(true)) {
         return this.m1599();
      } else {
         this.m1631(0);
         return true;
      }
   }

   boolean m1612(C_j_D c_j_d) {
      return this.m1613(c_j_d, false);
   }

   boolean m1613(C_j_D c_j_d, boolean flag) {
      if (this.f939.equals("EI")) {
         C_LF c_lf = (C_LF)LogicProgram.m1026("EI");
         C_RF c_rf = c_lf.m950().m1217(0).m1238(c_j_d);
         Vector vector = this.f935.f317.f915.varNames;
         if (!(c_rf instanceof C_i_)) {
            if (!flag) {
               this.m1621("dererr100");
            }

            return false;
         }

         if (vector != null && vector.contains(c_rf.f739)) {
            if (!flag) {
               this.m1622("dererr034", C_H.m666("variable name", "\\l" + c_rf + "\\l"));
            }

            this.f948 = new C_c_B("dererr034", C_H.m666("variable name", "\\l" + c_rf + "\\l"));
            return false;
         }
      }

      return true;
   }

   boolean m1614(String s) {
      C__ c__ = this.f935.f317;
      if (c__.f917 == this.f935) {
         c__ = c__.f916;
      }

      if (c__ != null) {
         c__ = c__.f916;
      }

      while (c__ != null) {
         if (c__.f921 != null && c__.f921.contains(s)) {
            return true;
         }

         c__ = c__.f916;
      }

      return false;
   }

   C_0B m1615(int i) {
      C_0B c_0b = this.m1627(i);
      if (c_0b == null) {
         return null;
      } else {
         return c_0b.m17() == this.f935.f317 ? null : c_0b;
      }
   }

   C_j_D m1616() {
      Integer integer = C_QE.m1366(this.f939);
      if (integer == null) {
         C_VB c_vb = LPDerivation.getRule(this.f939);
         if (c_vb == null) {
            this.m1621("dererr035");
            return null;
         } else {
            return this.m1617(c_vb);
         }
      } else {
         C_QE c_qe = LogicProgram.m1025(integer);
         if (c_qe == null) {
            this.m1622("dererr036", C_H.m666("theorem number", integer + ""));
            return null;
         } else if ((this.f944 || this.f945) && this.f938 != 0) {
            this.m1622("dererr024", C_H.m666("n", this.f938 + ""));
            return null;
         } else {
            return this.m1617(c_qe);
         }
      }
   }

   C_j_D m1617(C_VB c_vb) {
      if (c_vb == null) {
         return null;
      } else {
         boolean flag = false;
         boolean flag1 = false;
         boolean flag2 = false;
         boolean flag3 = false;
         Vector vector = new Vector();
         this.f953 = new Vector();
         this.f951 = false;
         this.f954 = new Vector();
         this.f952 = false;
         LPDerivation lpderivation = this.f935.f317.f915;
         this.f957 = c_vb.m1374();
         this.f958 = c_vb.m1373(lpderivation, "disabled");
         this.f959 = this.f946 ? c_vb.m1373(lpderivation, "manualOrDisabled") : this.f958;
         this.m1590();

         for (int i = 0; i < this.f957.length; i++) {
            C_LF c_lf = this.f957[i];
            int j = c_lf.f526.length;
            if (!this.f944 && !this.f945 ? j <= this.f938 : j == this.f938) {
               flag = true;
               C_j_D c_j_d = new C_j_D();
               C_j_D c_j_d1 = new C_j_D();
               boolean flag4 = false;
               boolean flag5 = false;
               boolean[] aboolean = new boolean[j];
               c_lf.f527.m1266(null, c_j_d1);
               if (this.f944 && this.f941 != null) {
                  flag4 = c_lf.f527.m1266(this.f941, c_j_d);
               } else {
                  flag4 = c_j_d.m1877(c_j_d1);
               }

               C_XE c_xe = new C_XE(j);

               while (true) {
                  int[] aint = c_xe.m1512();
                  C_j_D[] ac_j_d = new C_j_D[j];
                  int k = 0;

                  for (int l = 0; l < j; l++) {
                     ac_j_d[l] = new C_j_D();
                     if (c_lf.f526[aint[l]].m1266(this.m1628(l - j), ac_j_d[l])) {
                        k++;
                        aboolean[aint[l]] = true;
                     }
                  }

                  label330:
                  if (k == j) {
                     boolean flag8 = false;
                     C_j_D c_j_d2 = new C_j_D();

                     for (int i1 = 0; i1 < j; i1++) {
                        if (!c_j_d2.m1877(ac_j_d[i1])) {
                           break label330;
                        }
                     }

                     C__B c__b2 = new C__B();
                     if (c_j_d2.m1890()) {
                        for (int j1 = 0; j1 < j; j1++) {
                           if (!c__b2.m1576(c_lf.f526[aint[j1]], this.m1628(j1 - j), c_j_d2)) {
                              break label330;
                           }
                        }
                     } else {
                        flag8 = true;
                     }

                     C_HF c_hf2 = new C_HF(c_lf, aint, c_j_d2, c__b2);
                     this.f953.addElement(c_hf2);
                     this.f951 = true;
                     if (c_j_d2.m1877(c_j_d1) && c_j_d2.m1890() && c__b2.m1578(c_lf.f527)) {
                        this.f954.addElement(c_hf2);
                        this.f952 = true;
                     }

                     if (!flag4) {
                        if (!flag8) {
                           c_hf2.f395 = 1;
                        }
                     } else if (!c_j_d2.m1877(c_j_d)) {
                        if (!flag8) {
                           c_hf2.f395 = 2;
                        }
                     } else {
                        label394: {
                           if (c_j_d2.m1890()) {
                              if (flag8) {
                                 for (int k1 = 0; k1 < j; k1++) {
                                    if (!c__b2.m1576(c_lf.f526[aint[k1]], this.m1628(k1 - j), c_j_d2)) {
                                       break label394;
                                    }
                                 }
                              }

                              C_MB c_mb2 = new C_MB();
                              C_RF c_rf = c_lf.f527.m1239(c_j_d2, c_mb2);
                              int[][] aint1 = c_mb2.m1095(c_lf.f527, c_rf);
                              if (!c__b2.m1573(c_lf.f527, this.f944 ? this.f941 : null, aint1)) {
                                 c_hf2.f395 = 3;
                                 break label394;
                              }

                              if (!c__b2.m1575(c_lf.f527, c_rf, aint1, null)) {
                                 c_hf2.f395 = 4;
                                 c_hf2.f396 = c_rf.m1259();
                                 break label394;
                              }

                              if (this.f944 && this.f941 != null && !c_rf.m1235(this.f941)) {
                                 c_hf2.f395 = 5;
                                 break label394;
                              }
                           }

                           if (c_lf.m956(this.f958) == -1) {
                              flag1 = true;
                           } else if (this.f946 && c_lf.m956(this.f959) == -1) {
                              flag2 = true;
                           } else if (!c_lf.m952(this.f935.f317.f915)) {
                              flag3 = true;
                           } else {
                              vector.addElement(c_hf2);
                           }
                        }
                     }
                  }

                  if (!c_xe.m1513()) {
                     break;
                  }
               }
            }
         }

         if (!this.m1619(vector)) {
            return null;
         } else if (!flag) {
            if (!this.f944 && !this.f945 && this.f938 != 0) {
               this.m1622("dererr038", C_H.m666("n", this.f938 + ""));
            } else {
               this.m1622("dererr039", C_H.m666("n", this.f938 + ""));
            }

            return null;
         } else if (vector.isEmpty()) {
            if (flag3) {
               this.f935.f317.f915.proofMissing = true;
            }

            if (flag1) {
               this.m1621("dererr041");
            } else if (flag2) {
               this.m1621("dererr040");
            } else if (flag3) {
               this.m1621("dererr016");
            } else if (this.f947) {
               this.m1621("dererr064");
            } else if (this.f952 && this.f954.size() == 1) {
               if (this.f944 && this.f941 != null) {
                  C_RF c_rf1 = ((C_HF)this.f954.elementAt(0)).m695();
                  if (this.f935.f317.f915.commandMode) {
                     this.m1622("dererr033", C_H.m666("rule form conclusion", "\\l" + c_rf1 + "\\l"));
                     this.m1623("sum", c_rf1);
                  } else {
                     this.m1621("dererr103");
                  }
               } else {
                  C_HF c_hf = (C_HF)this.f954.elementAt(0);
                  if (c_hf.f395 == 4) {
                     C_RF[] ac_rf = (C_RF[])((Vector)c_hf.f396).elementAt(0);
                     this.m1622(
                        "dererr074",
                        C_H.m668(
                           "rule form conclusion",
                           "\\l" + c_hf.m695() + "\\l",
                           "misbinder",
                           "\\l" + ac_rf[0].f739 + "\\l",
                           "misbound",
                           "\\l" + ac_rf[1] + "\\l"
                        )
                     );
                  } else {
                     this.m1621("dererr100");
                  }
               }
            } else if (this.f951) {
               this.m1621("dererr100");
            } else if (this.f960 == this.f961) {
               this.m1621("dererr042");
            } else {
               this.m1621("dererr063");
            }

            return null;
         } else {
            int l1 = C_KB.m780(this, vector);
            if (l1 == -1) {
               if (this.f935.f317.f915.serialMode) {
                  this.f935.f317.f915.complete = false;
                  this.m1621("dererr064");
               } else {
                  this.m1621("dererr028");
                  this.f935.f317.f915.abort(true);
               }

               return null;
            } else {
               C_HF c_hf1 = (C_HF)vector.elementAt(l1);
               this.f953 = new Vector();
               this.f953.addElement(c_hf1);
               this.f954 = new Vector();
               this.f954.addElement(c_hf1);
               C_LF c_lf1 = c_hf1.m688();
               int i2 = c_lf1.f526.length;
               C_j_D c_j_d3 = c_hf1.m690();
               if (c_j_d3.m1890()) {
                  C_MB c_mb = new C_MB();
                  this.f942 = c_lf1.f527.m1239(c_j_d3, c_mb);
                  C__B c__b = c_hf1.m691();
                  boolean flag6 = vector.size() > 1 || !c__b.m1578(c_lf1.f527);
                  if (!c__b.m1574(c_lf1.f527, this.f942, c_mb, this)) {
                     return null;
                  } else {
                     if (flag6) {
                        this.m1598(c_hf1);
                     }

                     this.m1631(i2);
                     return c_j_d3;
                  }
               } else if (this.f935.f317.f915.frame == null) {
                  return null;
               } else {
                  if (this.f939.equals("EG")) {
                     if (!C_KB.m783(this, c_hf1)) {
                        return null;
                     }
                  } else if (this.f939.equals("EI")) {
                     if (!C_KB.m784(this, c_hf1)) {
                        return null;
                     }
                  } else if (this.f939.equals("UI")) {
                     if (!C_KB.m785(this, c_hf1)) {
                        return null;
                     }
                  } else if (!c_hf1.f391.f820.equalsIgnoreCase("LL1") && !c_hf1.f391.f820.equalsIgnoreCase("LL2")) {
                     if (!c_hf1.f391.f820.equalsIgnoreCase("LL3") && !c_hf1.f391.f820.equalsIgnoreCase("LL4")) {
                        if (this.f945 && this.f941 != null && this.f939.equals("EL")) {
                           if (!C_KB.m790(this, c_hf1)) {
                              return null;
                           }
                        } else if (!C_KB.m782(this, c_hf1)) {
                           return null;
                        }
                     } else if (!C_KB.m789(this, c_hf1)) {
                        return null;
                     }
                  } else if (!C_KB.m788(this, c_hf1)) {
                     return null;
                  }

                  C_MB c_mb1 = new C_MB();
                  C_RF c_rf2 = c_lf1.f527.m1239(c_j_d3, c_mb1);
                  int[][] aint2 = c_mb1.m1095(c_lf1.f527, c_rf2);
                  int[] aint3 = c_hf1.m689();
                  C__B c__b1 = c_hf1.m691();
                  boolean flag7 = true;

                  for (int j2 = 0; j2 < i2; j2++) {
                     if (!c__b1.m1576(c_lf1.f526[aint3[j2]], this.m1628(j2 - i2), c_j_d3)) {
                        flag7 = false;
                        break;
                     }
                  }

                  if (flag7) {
                     flag7 = false;
                     if ((!this.f944 || this.f941 == null || c__b1.m1573(c_lf1.f527, this.f941, aint2))
                        && c__b1.m1575(c_lf1.f527, c_rf2, aint2, null)
                        && (!this.f944 || this.f941 == null || c_rf2.m1235(this.f941))) {
                        flag7 = true;
                     }
                  }

                  if (!flag7) {
                     C_i_ c_i_ = null;
                     if (c__b1.f925 != null) {
                        c_i_ = (C_i_)((C_RF[])c__b1.f925.elementAt(0))[1];
                     }

                     this.m1622("dererr043", C_H.m666("inst term", "\\l" + c_i_ + "\\l"));
                     return null;
                  } else if (this.f939.equals("EG") && !C_KB.m786(this, c_hf1, c__b1)) {
                     return null;
                  } else if (!c__b1.m1575(c_lf1.f527, c_rf2, aint2, this)) {
                     return null;
                  } else {
                     this.f942 = c_rf2;
                     this.m1598(new C_HF(c_lf1, aint3, c_j_d3, c__b1));
                     this.m1631(i2);
                     return c_j_d3;
                  }
               }
            }
         }
      }
   }

   boolean m1618(C_HF c_hf, C_j_D c_j_d) {
      C_LF c_lf = c_hf.m688();
      int i = c_lf.f526.length;
      C_RF[] ac_rf = new C_RF[i];
      int[][][] aint = new int[i][][];

      for (int j = 0; j < i; j++) {
         C_MB c_mb = new C_MB();
         ac_rf[j] = c_lf.f526[j].m1239(c_j_d, c_mb);
         aint[j] = c_mb.m1095(c_lf.f526[j], ac_rf[j]);
      }

      C_MB c_mb1 = new C_MB();
      C_RF c_rf = c_lf.f527.m1239(c_j_d, c_mb1);
      int[][] aint1 = c_mb1.m1095(c_lf.f527, c_rf);
      int[] aint2 = c_hf.m689();
      C__B c__b = c_hf.m691();
      boolean flag = true;

      for (int k = 0; k < i; k++) {
         flag = false;
         if (!c__b.m1573(c_lf.f526[aint2[k]], this.m1628(k - i), aint[aint2[k]])
            || !c__b.m1575(c_lf.f526[aint2[k]], ac_rf[aint2[k]], aint[aint2[k]], null)
            || !ac_rf[aint2[k]].m1235(this.m1628(k - i))) {
            break;
         }

         flag = true;
      }

      if (flag) {
         flag = false;
         if ((!this.f944 || this.f941 == null || c__b.m1573(c_lf.f527, this.f941, aint1))
            && c__b.m1575(c_lf.f527, c_rf, aint1, null)
            && (!this.f944 || this.f941 == null || c_rf.m1235(this.f941))) {
            flag = true;
         }
      }

      this.f962 = c__b.f925;
      return flag;
   }

   boolean m1619(Vector vector) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         C_HF c_hf = (C_HF)vector.elementAt(j);
         if (!c_hf.f391.f820.equalsIgnoreCase("LL1") && !c_hf.f391.f820.equalsIgnoreCase("LL2")) {
            if (!c_hf.f391.f820.equalsIgnoreCase("LL3") && !c_hf.f391.f820.equalsIgnoreCase("LL4")) {
               if (c_hf.f391.f820.equalsIgnoreCase("AV3")) {
                  Vector vector5 = c_hf.f393.f1189;
                  if (!vector5.isEmpty()) {
                     C_i_A c_i_a2 = (C_i_A)c_hf.f393.f1189.elementAt(0);
                     Vector vector7 = c_i_a2.m1176(false);
                     if (vector7 != null) {
                        int i1 = vector7.size();

                        for (int j1 = 0; j1 < i1; j1++) {
                           C_m_B c_m_b3 = (C_m_B)vector7.elementAt(j1);
                           if (c_m_b3.f1277 != null && c_m_b3.f1277.m1227("%").size() == 0) {
                              this.m1620(vector, c_hf);
                              i--;
                              j--;
                              break;
                           }
                        }
                     }
                  }
               }
            } else {
               Vector vector4 = c_hf.f393.f1189;
               if (!vector4.isEmpty()) {
                  C_i_A c_i_a1 = (C_i_A)c_hf.f393.f1189.elementAt(0);
                  Vector vector6 = c_i_a1.m1176(false);
                  if (vector6 != null) {
                     if (vector6.size() == 1) {
                        this.m1620(vector, c_hf);
                        i--;
                        j--;
                     } else {
                        C_m_B c_m_b1 = (C_m_B)vector6.elementAt(0);
                        C_m_B c_m_b2 = (C_m_B)vector6.elementAt(1);
                        Vector vector8 = c_m_b1.f1277.m1233(c_m_b2.f1277);
                        int k1 = vector8.size();
                        C_e_ c_e_ = k1 == 0 ? null : (C_e_)vector8.elementAt(0);
                        int l1 = c_e_ == null ? 0 : c_e_.f1062;
                        if (l1 == 0) {
                           this.m1620(vector, c_hf);
                           i--;
                           j--;
                        } else {
                           C_RF c_rf6 = c_m_b1.f1277.m1220(c_e_);
                           C_RF c_rf3 = c_m_b2.f1277.m1220(c_e_);
                           boolean flag = true;

                           for (int l = 1; l < k1; l++) {
                              c_e_ = (C_e_)vector8.elementAt(l);
                              if (!c_rf6.m1235(c_m_b1.f1277.m1220(c_e_))) {
                                 flag = false;
                              } else if (!c_rf3.m1235(c_m_b2.f1277.m1220(c_e_))) {
                                 flag = false;
                              }

                              if (!flag) {
                                 break;
                              }

                              if (c_e_.f1062 < l1) {
                                 l1 = c_e_.f1062;
                              }
                           }

                           if (!flag) {
                              this.m1620(vector, c_hf);
                              i--;
                              j--;
                           } else if (l1 == 1) {
                              C_i_ c_i_1 = new C_i_(C_i_A.m1853(0));
                              C_RF c_rf4 = c_m_b1.f1277.m1237();
                              C_RF c_rf5 = c_m_b1.f1276.m1237();
                              c_rf5.f740.setElementAt(c_i_1, 0);

                              for (int i2 = 0; i2 < k1; i2++) {
                                 c_rf4 = C_GA.m617(c_rf4, c_i_1, (C_e_)vector8.elementAt(i2));
                              }

                              if (c_rf4.m1259() != null || !c_hf.f393.m1881(c_rf5, c_rf4)) {
                                 this.m1620(vector, c_hf);
                                 i--;
                                 j--;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            Vector vector1 = c_hf.f393.f1189;
            if (!vector1.isEmpty()) {
               C_i_A c_i_a = (C_i_A)c_hf.f393.f1189.elementAt(0);
               Vector vector2 = c_i_a.m1176(false);
               if (vector2 != null) {
                  C_m_B c_m_b = (C_m_B)vector2.elementAt(0);
                  C_RF c_rf = c_m_b.f1276.m1217(0);
                  Vector vector3 = c_m_b.f1277.m1223(c_rf.m1238(c_hf.f393));
                  int k = vector3.size();
                  if (k == 0) {
                     this.m1620(vector, c_hf);
                     i--;
                     j--;
                  } else if (k == 1) {
                     C_i_ c_i_ = new C_i_(C_i_A.m1853(0));
                     C_RF c_rf1 = C_GA.m617(c_m_b.f1277, c_i_, (C_e_)vector3.elementAt(0));
                     C_RF c_rf2 = c_m_b.f1276.m1237();
                     c_rf2.f740.setElementAt(c_i_, 0);
                     if (c_rf1.m1259() != null || !c_hf.f393.m1881(c_rf2, c_rf1)) {
                        this.m1620(vector, c_hf);
                        i--;
                        j--;
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   private void m1620(Vector vector, C_HF c_hf) {
      vector.removeElement(c_hf);
      this.f954.removeElement(c_hf);
      if (this.f954.isEmpty()) {
         this.f952 = false;
      }

      this.f953.removeElement(c_hf);
      if (this.f953.isEmpty()) {
         this.f951 = false;
      }
   }

   @Override
   public String m547(String s) {
      if (s.equals("rule name")) {
         return this.f939;
      } else if (s.equals("stack")) {
         Object object = "\\l";

         for (int j1 = 0; j1 < this.f938; j1++) {
            if (j1 != 0) {
               object = object + "\\n";
            }

            object = object + this.m1628(-j1);
         }

         return object + "\\l";
      } else {
         if (s.length() >= 5 && s.substring(0, 5).equals("stack")) {
            int i;
            try {
               i = Integer.parseInt(s.substring(5).trim());
            } catch (NumberFormatException numberformatexception) {
               i = 0;
            }

            if (i > 0 && i <= this.f938) {
               return "\\l" + this.m1628(-i) + "\\l";
            }
         }

         if (s.equals("assumption")) {
            int l = this.f935.f317.f923;
            if (l == -1) {
               return null;
            } else {
               return l == 2 ? "\\l" + this.f935.f317.m1560(1).m7(true) + "\\l" : "\\l" + this.f935.f317.m44().m1217(l) + "\\l";
            }
         } else if (s.equals("rule forms") && this.f959 != null) {
            String s4 = "\\l";
            int i1 = this.f959.length;
            boolean flag3 = true;

            for (int i2 = 0; i2 < i1; i2++) {
               C_LF c_lf1 = this.f959[i2];
               if (c_lf1.f526.length >= this.f960 && c_lf1.f526.length <= this.f961) {
                  if (!flag3) {
                     s4 = s4 + "\\n";
                  }

                  s4 = s4 + this.f959[i2].m958(" . ", " .: ");
                  flag3 = false;
               }
            }

            return s4 + "\\l";
         } else if (s.equals("rule forms premises") && this.f959 != null) {
            int k = this.f961;
            Object object1 = "\\l";

            for (int l1 = 0; l1 < k; l1++) {
               if (l1 != 0) {
                  object1 = object1 + "\\n";
               }

               object1 = object1 + this.m1628(-l1);
            }

            return object1 + "\\l";
         } else {
            if (s.equals("rule form")) {
               Vector vector = this.f952 ? this.f954 : (this.f951 ? this.f953 : null);
               if (vector != null && vector.size() > 0) {
                  C_LF c_lf = ((C_HF)vector.elementAt(0)).m688();
                  return "\\l" + c_lf.m958(" . ", " .: ") + "\\l";
               }
            }

            if (s.equals("rule form premises")) {
               Vector vector1 = this.f952 ? this.f954 : (this.f951 ? this.f953 : null);
               if (vector1 != null && vector1.size() > 0) {
                  C_HF c_hf = (C_HF)vector1.elementAt(0);
                  int k1 = c_hf.m692();
                  String s3 = "\\l";

                  for (int j = 0; j < k1; j++) {
                     s3 = s3 + (j == 0 ? "" : "\\n") + this.m1628(j - k1);
                  }

                  return s3 + "\\l";
               }
            }

            if (s.equals("rule form conclusion")) {
               return "\\l" + this.f942 + "\\l";
            } else {
               if (s.length() >= 4 && s.substring(0, 4).equals("rfsp")) {
                  boolean flag = this.f961 == 1;
                  String s1 = s.substring(4).trim();
                  if (s1.equals("s")) {
                     return flag ? "" : "s";
                  }

                  if (s1.equals("es")) {
                     return flag ? "es" : "";
                  }

                  if (s1.equals("those")) {
                     return flag ? "that" : "those";
                  }
               }

               if (s.length() >= 3 && s.substring(0, 3).equals("rfp")) {
                  boolean flag1 = false;
                  Vector vector2 = this.f952 ? this.f954 : (this.f951 ? this.f953 : null);
                  if (vector2 != null && vector2.size() > 0) {
                     flag1 = ((C_HF)vector2.elementAt(0)).m692() == 1;
                  }

                  String s2 = s.substring(3).trim();
                  if (s2.equals("s")) {
                     return flag1 ? "" : "s";
                  }

                  if (s2.equals("es")) {
                     return flag1 ? "es" : "";
                  }

                  if (s2.equals("those")) {
                     return flag1 ? "that" : "those";
                  }

                  if (s2.equals("are")) {
                     return flag1 ? "is" : "are";
                  }
               }

               if (s.length() >= 2 && s.substring(0, 2).equals("rf")) {
                  boolean flag2 = this.f959 != null && this.f959.length == 1;
                  String s5 = s.substring(2).trim();
                  if (s5.equals("s")) {
                     return flag2 ? "" : "s";
                  }
               }

               return null;
            }
         }
      }
   }

   void m1621(String s) {
      this.f935.m550(s, this);
   }

   void m1622(String s, Hashtable hashtable) {
      this.f935.m553(s, this, hashtable);
   }

   void m1623(String s, Object object) {
      this.f935.m556(s, object);
   }

   void m1624() {
      this.f935.m13();
   }

   void m1625(int i) {
      this.f935.m557(i);
   }

   int m1626() {
      return this.f937.size();
   }

   C_0B m1627(int i) {
      int j = this.f936.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (C_0B)this.f936.elementAt(i) : null;
   }

   C_RF m1628(int i) {
      int j = this.f937.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (C_RF)this.f937.elementAt(i) : null;
   }

   C_L m1629(int i) {
      C_RF c_rf = this.m1628(i);
      return c_rf == null ? null : c_rf.m1254();
   }

   C_L m1630() {
      return this.f941 == null ? null : this.f941.m1254();
   }

   void m1631(int i) {
      int j = this.f937.size();
      int k = j - i;
      if (k < 0) {
         k = 0;
      }

      if (this.f945) {
         this.f955 = new Vector();

         for (int l = 0; l < j - k; l++) {
            this.f955.addElement(this.f937.elementAt(k + l));
         }
      }

      this.f936.setSize(k);
      this.f937.setSize(k);
   }

   String m1632() {
      return this.f939;
   }

   static int m1633(String s) {
      if (s.equals("PR")) {
         return 0;
      } else if (s.length() < 2 || !s.substring(0, 2).equals("PR")) {
         return -1;
      } else if (s.length() > 2 && "-0".indexOf(s.substring(2, 3)) != -1) {
         return -1;
      } else {
         try {
            return Integer.parseInt(s.substring(2));
         } catch (NumberFormatException numberformatexception) {
            return -1;
         }
      }
   }
}
