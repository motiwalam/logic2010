package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class DerivationLineChecker implements C_k_A, DerivationConstants {
   DerivationLine f935;
   Vector f936;
   Vector f937;
   int f938;
   String f939;
   String f940;
   Expression f941;
   Expression f942;
   int f943;
   boolean f944;
   boolean f945;
   boolean f946;
   boolean f947;
   ErrorRef f948;
   final boolean f949 = true;
   final boolean f950 = false;
   boolean f951;
   boolean f952;
   Vector f953;
   Vector f954;
   Vector f955;
   Vector f956;
   SchematicRule[] f957;
   SchematicRule[] f958;
   SchematicRule[] f959;
   int f960;
   int f961;
   Vector f962;

   DerivationLineChecker(DerivationLine derivationline, boolean flag) {
      this.f935 = derivationline;
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
            int k = this.f959[j].premises.length;
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

         DerivationBox derivationbox = this.f935.f317.f915.problem;

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

               DerivationNode derivationnode;
               try {
                  derivationnode = derivationbox.m32(Integer.parseInt(this.f940.substring(i, j)));
               } catch (NumberFormatException numberformatexception) {
                  derivationnode = null;
               }

               if (derivationnode == null) {
                  this.m1622("dererr003", Message.params("remote line number", this.f940.substring(i, j)));
                  return false;
               }

               if (!this.f935.m565(derivationnode)) {
                  return false;
               }

               Expression expression = derivationnode.m44();
               if (expression == null) {
                  String s = derivationnode.m7(true).trim().equals("") ? "dererr004" : "dererr005";
                  this.m1622(s, Message.params("remote line number", this.f940.substring(i, j)));
                  return false;
               }

               this.f936.addElement(derivationnode);
               this.f937.addElement(expression);
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
         DerivationNode derivationnode = this.f935.f317.f915.problem.m32(integer);
         if (derivationnode == null) {
            return new ErrorRef("dererr003", Message.params("remote line number", integer.toString()));
         } else if (!this.f935.m565(derivationnode)) {
            return new ErrorRef(null);
         } else {
            Expression expression = derivationnode.m44();
            if (expression == null) {
               String s = derivationnode.m7(true).trim().equals("") ? "dererr004" : "dererr005";
               return new ErrorRef(s, Message.params("remote line number", integer.toString()));
            } else {
               return expression;
            }
         }
      } else {
         int i = this.m1595();
         if (i != -1) {
            Expression[] aexpression = this.f935.f317.f915.premises;
            if (aexpression == null || aexpression.length == 0) {
               return new ErrorRef("dererr029");
            } else if (i > aexpression.length) {
               return new ErrorRef("dererr030", Message.params("premise index", i + ""));
            } else {
               if (i == 0) {
                  if (aexpression.length > 1) {
                     i = C_KB.m778(this.f935, aexpression, C_n_.m1960(C_n_.getText("derdlg002"), null, this)) + 1;
                     if (i == 0) {
                        return new ErrorRef(null);
                     }
                  } else {
                     i = 1;
                  }
               }

               return aexpression[i - 1];
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
            Integer integer = LogicProgram.parseInteger(this.f940.substring(i, j));
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

   private void m1598(Justification justification) {
      this.f935.f336.setElementAt(justification, this.f943);
   }

   private boolean m1599() {
      this.f935.f336 = null;
      this.f935.f317.f922 = false;
      return false;
   }

   static boolean m1600(Expression expression) {
      if (expression.symbol.startsWith("?")) {
         return true;
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (m1600(expression.getChild(j))) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean m1601(Expression expression, Expression expression1) {
      if (!expression1.symbol.startsWith("?")) {
         if (!expression.symbol.equals(expression1.symbol)) {
            return false;
         } else {
            int i = expression.getChildCount();
            if (expression1.getChildCount() != i) {
               return false;
            } else {
               for (int j = 0; j < i; j++) {
                  if (!m1601(expression.getChild(j), expression1.getChild(j))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else if (!(expression instanceof Formula)) {
         return false;
      } else if (expression.m1259() != null) {
         return false;
      } else if (expression1.symbol.equals("?PNX")) {
         return m1602(expression);
      } else if (expression1.symbol.equals("?NOV")) {
         return m1603(expression);
      } else if (expression1.symbol.equals("?DNF")) {
         return m1605(expression, true);
      } else {
         return expression1.symbol.equals("?CNF") ? m1605(expression, false) : expression1.symbol.equals("?");
      }
   }

   static boolean m1602(Expression expression) {
      return expression instanceof QuantifiedFormula ? m1602(expression.getChild(1)) : m1604(expression);
   }

   static boolean m1603(Expression expression) {
      if (expression instanceof QuantifiedFormula) {
         return m1604(expression.getChild(1));
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (!m1603(expression.getChild(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean m1604(Expression expression) {
      if (expression instanceof QuantifiedFormula) {
         return false;
      } else {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            if (!m1604(expression.getChild(j))) {
               return false;
            }
         }

         return true;
      }
   }

   static boolean m1605(Expression expression, boolean flag) {
      if (expression instanceof QuantifiedFormula) {
         return m1605(expression.getChild(1), flag);
      } else {
         String s = expression.symbol;
         if (!s.equals("->") && !s.equals("<->")) {
            int i = expression.getChildCount();

            for (int j = 0; j < i; j++) {
               Expression expression1 = expression.getChild(j);
               String s1 = expression1.symbol;
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

               if (!m1605(expression1, flag)) {
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

      ErrorRef errorref;
      if ((errorref = this.f935.f317.f915.checkDerivationRule(this.f939, this.f946)) != null) {
         this.m1621(errorref.m716());
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
         Justification justification = (Justification)this.f935.f336.elementAt(this.f943);
         if (justification != null) {
            if (justification.m600(this)) {
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
               this.m1622("dererr010", Message.params("n", "1"));
               return this.m1599();
            } else {
               Expression expression6 = this.f935.f317.m44();
               Expression expression17 = this.m1628(-1);
               if (expression6 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!expression6.getSymbol().equals("->")) {
                  this.m1621("dererr013");
                  return this.m1599();
               } else if (!m1601(expression17, expression6.getChild(1))) {
                  this.m1621("dererr014");
                  return this.m1599();
               } else {
                  DerivationNode derivationnode4;
                  if ((derivationnode4 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", Message.params("remote line number", derivationnode4.m30() + ""));
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

                        if ((errorref = this.f935.f317.f915.checkDerivationRule("CD/" + ASS_STR[i2], this.f946)) != null) {
                           this.m1621(errorref.f427);
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
               this.m1622("dererr010", Message.params("n", "2"));
               return this.m1599();
            } else {
               Expression expression5 = this.m1628(-2);
               Expression expression16 = this.m1628(-1);
               if (!expression5.m1255(expression16) && !expression16.m1255(expression5)) {
                  this.m1621("dererr017");
                  return this.m1599();
               } else {
                  DerivationNode derivationnode3;
                  if ((derivationnode3 = this.m1615(-2)) == null && (derivationnode3 = this.m1615(-1)) == null) {
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

                        if ((errorref = this.f935.f317.f915.checkDerivationRule("ID/" + ASS_STR[l1], this.f946)) != null) {
                           this.m1621(errorref.f427);
                           return this.m1599();
                        }

                        this.f935.f317.f917.m11(l1 == 1 ? "derinf001" : "derinf002");
                     }

                     this.m1631(2);
                     return true;
                  } else {
                     this.m1622("dererr015", Message.params("remote line number", derivationnode3.m30() + ""));
                     return this.m1599();
                  }
               }
            }
         } else if (this.f939.equals("DD")) {
            if (this.f938 != 1) {
               this.m1622("dererr010", Message.params("n", "1"));
               return this.m1599();
            } else {
               Expression expression4 = this.f935.f317.m44();
               Expression expression15 = this.m1628(-1);
               if (expression4 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!m1601(expression15, expression4)) {
                  this.m1621("dererr019");
                  return this.m1599();
               } else {
                  DerivationNode derivationnode2;
                  if ((derivationnode2 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", Message.params("remote line number", derivationnode2.m30() + ""));
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

                        if ((errorref = this.f935.f317.f915.checkDerivationRule("DD/" + ASS_STR[k1], this.f946)) != null) {
                           this.m1621(errorref.f427);
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
               this.m1622("dererr010", Message.params("n", "1"));
               return this.m1599();
            } else {
               Expression expression3 = this.f935.f317.m44();
               Expression expression14 = this.m1628(-1);
               if (expression3 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!expression3.getSymbol().equals("@")) {
                  this.m1621("dererr021");
                  return this.m1599();
               } else if (!m1601(expression14, expression3.getChild(1))) {
                  this.m1621("dererr022");
                  return this.m1599();
               } else {
                  DerivationNode derivationnode1;
                  if ((derivationnode1 = this.m1615(-1)) != null) {
                     this.m1622("dererr015", Message.params("remote line number", derivationnode1.m30() + ""));
                     return this.m1599();
                  } else {
                     String s = ((SimpleTerm)expression3.getChild(0)).symbol;
                     if (this.m1614(s)) {
                        this.m1622("dererr023", Message.params("variable name", "\\l" + s + "\\l"));
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
               this.m1622("dererr010", Message.params("n", "1"));
               return this.m1599();
            } else {
               Expression expression2 = this.f935.f317.m44();
               if (expression2 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!expression2.getSymbol().equals("<->")) {
                  this.m1621("dererr081");
                  return this.m1599();
               } else {
                  Expression expression13 = this.m1628(-1);
                  int j1 = this.f935.f317.f923;
                  if (j1 == -1) {
                     if (!m1601(expression13, expression2.getChild(0)) && !m1601(expression13, expression2.getChild(1))) {
                        this.m1621("dererr102");
                        return this.m1599();
                     }
                  } else {
                     Expression expression22 = j1 == 2 ? this.f935.f317.m1560(1).m44() : expression2.getChild(j1);
                     if ((!m1601(expression13, expression2.getChild(0)) || !m1601(expression22, expression2.getChild(1)))
                        && (!m1601(expression22, expression2.getChild(0)) || !m1601(expression13, expression2.getChild(1)))) {
                        this.m1621("dererr090");
                        return this.m1599();
                     }
                  }

                  if (this.f935.f317.f915.serialMode && !this.f935.f317.f922) {
                     this.m1621("dererr091");
                     return this.m1599();
                  } else {
                     DerivationNode derivationnode;
                     if ((derivationnode = this.m1615(-1)) != null) {
                        this.m1622("dererr015", Message.params("remote line number", derivationnode.m30() + ""));
                        return this.m1599();
                     } else {
                        if (this.f935.f317.f918 == this.f935 && this.f935.f317.f915.serialMode) {
                           int i3 = this.f935.f317.f920;
                           if (i3 != 3) {
                              this.m1621("dererr101");
                              return this.m1599();
                           }

                           if ((errorref = this.f935.f317.f915.checkDerivationRule("BD/" + ASS_STR[i3], this.f946)) != null) {
                              this.m1621(errorref.f427);
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
               this.m1622("dererr024", Message.params("n", this.f938 + ""));
               return this.m1599();
            } else if (this.f935.m16() != 1) {
               this.m1621("dererr025");
               return this.m1599();
            } else if (this.f942 != null) {
               this.m1621("dererr026");
               return this.m1599();
            } else {
               Expression expression1 = this.f935.f317.m44();
               if (expression1 == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else if (!expression1.getSymbol().equals("->")) {
                  this.m1621("dererr013");
                  return this.m1599();
               } else {
                  this.f935.f317.f920 = 2;
                  this.f942 = expression1.getChild(0);
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
               this.m1622("dererr024", Message.params("n", this.f938 + ""));
               return this.m1599();
            } else if (this.f935.m16() != 1) {
               this.m1621("dererr025");
               return this.m1599();
            } else if (this.f942 != null) {
               this.m1621("dererr026");
               return this.m1599();
            } else {
               Expression expression = this.f935.f317.m44();
               if (expression == null) {
                  this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                  return this.m1599();
               } else {
                  if (flag && this.f941 != null) {
                     if (!expression.m1255(this.f941) && !this.f941.m1255(expression)) {
                        this.m1621("dererr027");
                        return this.m1599();
                     }

                     this.f942 = this.f941;
                  } else if (expression.getSymbol().equals("~")) {
                     Expression[] aexpression2 = new Expression[]{expression.getChild(0), expression.negate()};
                     int i1 = C_KB.m778(this.f935, aexpression2, C_n_.m1960(C_n_.getText("derdlg001"), null, this));
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

                     this.f942 = aexpression2[i1];
                     this.m1598(new C_p_E(i1 == 1));
                  } else {
                     this.f942 = expression.negate();
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
                  this.m1622("dererr024", Message.params("n", this.f938 + ""));
                  return this.m1599();
               } else if (this.f935.m16() != 1) {
                  this.m1621("dererr025");
                  return this.m1599();
               } else if (this.f942 != null) {
                  this.m1621("dererr026");
                  return this.m1599();
               } else {
                  Expression expression12 = this.f935.f317.m44();
                  if (expression12 == null) {
                     this.m1621(this.f935.f317.m7(true).trim().equals("") ? "dererr011" : "dererr012");
                     return this.m1599();
                  } else if (!expression12.getSymbol().equals("<->")) {
                     this.m1621("dererr081");
                     return this.m1599();
                  } else {
                     if (flag && this.f941 != null) {
                        if ((j == 1 || !expression12.getChild(0).m1235(this.f941)) && (j == 0 || !expression12.getChild(1).m1235(this.f941))) {
                           String[] astring = new String[]{"the left", "the right", "either"};
                           this.m1622("dererr092", Message.params("side", astring[j]));
                           return this.m1599();
                        }

                        this.f942 = this.f941;
                        this.f935.f317.f923 = j;
                     } else if (j < 2) {
                        if (m1600(this.f942 = expression12.getChild(j))) {
                           this.m1621("dererr093");
                           return this.m1599();
                        }

                        this.f935.f317.f923 = j;
                     } else {
                        Expression[] aexpression5 = new Expression[]{expression12.getChild(0), expression12.getChild(1)};
                        int l2;
                        if (aexpression5[0].m1235(aexpression5[1])) {
                           l2 = 0;
                        } else if (m1600(aexpression5[1]) && !m1600(aexpression5[0])) {
                           l2 = 0;
                        } else if (m1600(aexpression5[0]) && !m1600(aexpression5[1])) {
                           l2 = 1;
                        } else {
                           if (m1600(aexpression5[0]) && m1600(aexpression5[1])) {
                              this.m1621("dererr093");
                              return this.m1599();
                           }

                           l2 = C_KB.m778(this.f935, aexpression5, C_n_.m1960(C_n_.getText("derdlg001"), null, this));
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

                        this.f942 = aexpression5[l2];
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
                  this.f942 = this.f935.f317.f915.conclusion.copy();
                  return this.m1611();
               }
            } else if (this.f939.startsWith("SHOW CONS") && "SHOW CONSEQUENT".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  Expression expression11 = this.f935.f317.m44();
                  if (expression11 != null && expression11.symbol.equals("->")) {
                     this.f942 = expression11.getChild(1).copy();
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", Message.params("an", "a", "expected form", "conditional"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW CORR") && "SHOW CORRCOND".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  Expression expression10 = this.f935.f317.m44();
                  if (expression10 != null && expression10.symbol.equals("|")) {
                     this.f942 = new ConnectiveFormula("->");
                     this.f942.addChild(expression10.getChild(0).negate());
                     this.f942.addChild(expression10.getChild(1).copy());
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", Message.params("an", "a", "expected form", "disjunction"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW CONJ") && "SHOW CONJUNCT".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  Expression expression9 = this.f935.f317.m44();
                  if (expression9 != null && expression9.symbol.equals("&")) {
                     Expression[] aexpression4 = new Expression[]{expression9.getChild(0).copy(), expression9.getChild(1).copy()};
                     int k2 = C_KB.m778(this.f935, aexpression4, "Please choose a conjunct:");
                     if (k2 == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = aexpression4[k2];
                        return this.m1611();
                     }
                  } else {
                     this.m1622("dererr077", Message.params("an", "a", "expected form", "conjunction"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW COND") && "SHOW CONDITIONAL".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  Expression expression8 = this.f935.f317.m44();
                  if (expression8 != null && expression8.symbol.equals("<->")) {
                     Expression[] aexpression3 = new Expression[]{new ConnectiveFormula("->"), null};
                     aexpression3[0].addChild(expression8.getChild(0).copy());
                     aexpression3[0].addChild(expression8.getChild(1).copy());
                     aexpression3[1] = new ConnectiveFormula("->");
                     aexpression3[1].addChild(expression8.getChild(1).copy());
                     aexpression3[1].addChild(expression8.getChild(0).copy());
                     int j2 = C_KB.m778(this.f935, aexpression3, "Please choose a conditional:");
                     if (j2 == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = aexpression3[j2];
                        return this.m1611();
                     }
                  } else {
                     this.m1622("dererr077", Message.params("an", "a", "expected form", "biconditional"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW INST") && "SHOW INSTANCE".startsWith(this.f939)) {
               if (!this.m1610(false)) {
                  return this.m1599();
               } else {
                  Expression expression7 = this.f935.f317.m44();
                  if (expression7 != null && expression7.symbol.equals("@")) {
                     this.f942 = expression7.getChild(1).copy();
                     return this.m1611();
                  } else {
                     this.m1622("dererr077", Message.params("an", "a", "expected form", "universal generalization"));
                     return this.m1599();
                  }
               }
            } else if (this.f939.startsWith("SHOW UNNEG") && "SHOW UNNEGATION".startsWith(this.f939)) {
               Object object3 = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object3 == null) {
                  this.m1622("dererr079", Message.params("an", "a", "expected form", "negation"));
                  return this.m1599();
               } else if (object3 instanceof ErrorRef) {
                  errorref = (ErrorRef)object3;
                  if (errorref.f427 != null) {
                     this.m1622(errorref.f427, errorref.f428);
                  }

                  return this.m1599();
               } else {
                  Expression expression21 = (Expression)object3;
                  if (!expression21.symbol.equals("~")) {
                     String s4 = "\\l" + expression21 + "\\l";
                     this.m1622("dererr080", Message.params("remote line", s4, "an", "a", "expected form", "negation"));
                     return this.m1599();
                  } else {
                     this.f942 = expression21.getChild(0).copy();
                     return this.m1611();
                  }
               }
            } else if (this.f939.startsWith("SHOW ANT") && "SHOW ANTECEDENT".startsWith(this.f939)) {
               Object object2 = this.m1593();
               this.f944 = !this.m1592();
               if (!this.m1610(false)) {
                  return this.m1599();
               } else if (object2 == null) {
                  this.m1622("dererr079", Message.params("an", "a", "expected form", "(bi)conditional"));
                  return this.m1599();
               } else if (object2 instanceof ErrorRef) {
                  errorref = (ErrorRef)object2;
                  if (errorref.f427 != null) {
                     this.m1622(errorref.f427, errorref.f428);
                  }

                  return this.m1599();
               } else {
                  Expression expression20 = (Expression)object2;
                  if (expression20.symbol.equals("->")) {
                     this.f942 = expression20.getChild(0).copy();
                  } else {
                     if (!expression20.symbol.equals("<->")) {
                        String s3 = "\\l" + expression20 + "\\l";
                        this.m1622("dererr080", Message.params("remote line", s3, "an", "a", "expected form", "(bi)conditional"));
                        return this.m1599();
                     }

                     if ((this.f942 = C_KB.m781(this, expression20, false)) == null) {
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
                  this.m1622("dererr079", Message.params("an", "a", "expected form", "(bi)conditional"));
                  return this.m1599();
               } else if (object1 instanceof ErrorRef) {
                  errorref = (ErrorRef)object1;
                  if (errorref.f427 != null) {
                     this.m1622(errorref.f427, errorref.f428);
                  }

                  return this.m1599();
               } else {
                  Expression expression19 = (Expression)object1;
                  if (expression19.symbol.equals("->")) {
                     this.f942 = expression19.getChild(1).copy().negate();
                  } else {
                     if (!expression19.symbol.equals("<->")) {
                        String s2 = "\\l" + expression19 + "\\l";
                        this.m1622("dererr080", Message.params("remote line", s2, "an", "a", "expected form", "(bi)conditional"));
                        return this.m1599();
                     }

                     if ((this.f942 = C_KB.m781(this, expression19, true)) == null) {
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
                  this.m1622("dererr079", Message.params("an", "a", "expected form", "disjunction"));
                  return this.m1599();
               } else if (object instanceof ErrorRef) {
                  errorref = (ErrorRef)object;
                  if (errorref.f427 != null) {
                     this.m1622(errorref.f427, errorref.f428);
                  }

                  return this.m1599();
               } else {
                  Expression expression18 = (Expression)object;
                  if (!expression18.symbol.equals("|")) {
                     String s1 = "\\l" + expression18 + "\\l";
                     this.m1622("dererr080", Message.params("remote line", s1, "an", "a", "expected form", "disjunction"));
                     return this.m1599();
                  } else {
                     Expression[] aexpression = new Expression[]{expression18.getChild(0).copy().negate(), expression18.getChild(1).copy().negate()};
                     int k = C_KB.m778(this.f935, aexpression, "Please choose the negation of a disjunct:");
                     if (k == -1) {
                        return this.m1599();
                     } else {
                        this.f942 = aexpression[k];
                        return this.m1611();
                     }
                  }
               }
            } else {
               int i;
               if ((i = m1633(this.f939)) != -1) {
                  Expression[] aexpression1 = this.f935.f317.f915.premises;
                  int l = aexpression1 == null ? 0 : aexpression1.length;
                  if (l == 0 || !this.f935.f317.f915.problem.f917.f333) {
                     this.m1621("dererr029");
                     return this.m1599();
                  } else if (i > l) {
                     this.m1622("dererr030", Message.params("premise index", i + ""));
                     return this.m1599();
                  } else if ((flag || flag1) && this.f938 != 0) {
                     this.m1622("dererr024", Message.params("n", this.f938 + ""));
                     return this.m1599();
                  } else if (flag && this.f941 != null) {
                     if (i == 0) {
                        if (!this.f935.f317.f915.isPremise(this.f941)) {
                           this.m1621("dererr031");
                           return this.m1599();
                        }
                     } else if (!this.f941.m1235(aexpression1[i - 1])) {
                        this.m1622("dererr032", Message.params("premise index", i + "", "indexed premise", "\\l" + aexpression1[i - 1] + "\\l"));
                        return this.m1599();
                     }

                     this.m1631(0);
                     return true;
                  } else {
                     if (i == 0) {
                        if (l > 1) {
                           i = C_KB.m778(this.f935, aexpression1, C_n_.m1960(C_n_.getText("derdlg002"), null, this)) + 1;
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

                     this.f942 = aexpression1[i - 1];
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
                        if (LogicProgram.debug) {
                           System.out.println("unexpected IE error during applyRule");
                        }

                        Hashtable hashtable1 = new Hashtable();
                        Message.putParam(hashtable1, "inner rule", c_ga1.m624(this).f820);
                        Message.putParam(hashtable1, "inner exp", "\\l" + this.m1628(-1).m1220(c_ga1.f343) + "\\l");
                        this.m1622("dererr085", hashtable1);
                        return this.m1599();
                     } else {
                        this.m1598(c_ga1);
                        return true;
                     }
                  } else {
                     this.m1622("dererr084", Message.params("n", "1"));
                     return this.m1599();
                  }
               } else if (!this.f939.equals("CIE")) {
                  SchemeInstantiation schemeinstantiation = this.m1616();
                  if (schemeinstantiation == null || !this.m1612(schemeinstantiation)) {
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
                     if (LogicProgram.debug) {
                        System.out.println("unexpected CIE error during applyRule");
                     }

                     Hashtable hashtable = new Hashtable();
                     Message.putParam(hashtable, "inner rule", c_ga.m624(this).f820);
                     Message.putParam(hashtable, "inner exp", "\\l" + this.m1628(-1).m1220(c_ga.f343) + "\\l");
                     this.m1622("dererr095", hashtable);
                     return this.m1599();
                  } else {
                     this.m1598(c_ga);
                     return true;
                  }
               } else {
                  this.m1622("dererr084", Message.params("n", "1"));
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
         this.m1622("dererr024", Message.params("n", this.f938 + ""));
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

   boolean m1612(SchemeInstantiation schemeinstantiation) {
      return this.m1613(schemeinstantiation, false);
   }

   boolean m1613(SchemeInstantiation schemeinstantiation, boolean flag) {
      if (this.f939.equals("EI")) {
         SchematicRule schematicrule = (SchematicRule)LogicProgram.m1026("EI");
         Expression expression = schematicrule.m950().getChild(0).instantiate(schemeinstantiation);
         Vector vector = this.f935.f317.f915.varNames;
         if (!(expression instanceof SimpleTerm)) {
            if (!flag) {
               this.m1621("dererr100");
            }

            return false;
         }

         if (vector != null && vector.contains(expression.symbol)) {
            if (!flag) {
               this.m1622("dererr034", Message.params("variable name", "\\l" + expression + "\\l"));
            }

            this.f948 = new ErrorRef("dererr034", Message.params("variable name", "\\l" + expression + "\\l"));
            return false;
         }
      }

      return true;
   }

   boolean m1614(String s) {
      DerivationBox derivationbox = this.f935.f317;
      if (derivationbox.f917 == this.f935) {
         derivationbox = derivationbox.f916;
      }

      if (derivationbox != null) {
         derivationbox = derivationbox.f916;
      }

      while (derivationbox != null) {
         if (derivationbox.f921 != null && derivationbox.f921.contains(s)) {
            return true;
         }

         derivationbox = derivationbox.f916;
      }

      return false;
   }

   DerivationNode m1615(int i) {
      DerivationNode derivationnode = this.m1627(i);
      if (derivationnode == null) {
         return null;
      } else {
         return derivationnode.m17() == this.f935.f317 ? null : derivationnode;
      }
   }

   SchemeInstantiation m1616() {
      Integer integer = Theorem.m1366(this.f939);
      if (integer == null) {
         Rule rule = LPDerivation.getRule(this.f939);
         if (rule == null) {
            this.m1621("dererr035");
            return null;
         } else {
            return this.m1617(rule);
         }
      } else {
         Theorem theorem = LogicProgram.m1025(integer);
         if (theorem == null) {
            this.m1622("dererr036", Message.params("theorem number", integer + ""));
            return null;
         } else if ((this.f944 || this.f945) && this.f938 != 0) {
            this.m1622("dererr024", Message.params("n", this.f938 + ""));
            return null;
         } else {
            return this.m1617(theorem);
         }
      }
   }

   SchemeInstantiation m1617(Rule rule) {
      if (rule == null) {
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
         this.f957 = rule.m1374();
         this.f958 = rule.m1373(lpderivation, "disabled");
         this.f959 = this.f946 ? rule.m1373(lpderivation, "manualOrDisabled") : this.f958;
         this.m1590();

         for (int i = 0; i < this.f957.length; i++) {
            SchematicRule schematicrule = this.f957[i];
            int j = schematicrule.premises.length;
            if (!this.f944 && !this.f945 ? j <= this.f938 : j == this.f938) {
               flag = true;
               SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
               SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
               boolean flag4 = false;
               boolean flag5 = false;
               boolean[] aboolean = new boolean[j];
               schematicrule.conclusion.m1266(null, schemeinstantiation1);
               if (this.f944 && this.f941 != null) {
                  flag4 = schematicrule.conclusion.m1266(this.f941, schemeinstantiation);
               } else {
                  flag4 = schemeinstantiation.m1877(schemeinstantiation1);
               }

               C_XE c_xe = new C_XE(j);

               while (true) {
                  int[] aint = c_xe.m1512();
                  SchemeInstantiation[] aschemeinstantiation = new SchemeInstantiation[j];
                  int k = 0;

                  for (int l = 0; l < j; l++) {
                     aschemeinstantiation[l] = new SchemeInstantiation();
                     if (schematicrule.premises[aint[l]].m1266(this.m1628(l - j), aschemeinstantiation[l])) {
                        k++;
                        aboolean[aint[l]] = true;
                     }
                  }

                  label330:
                  if (k == j) {
                     boolean flag8 = false;
                     SchemeInstantiation schemeinstantiation2 = new SchemeInstantiation();

                     for (int i1 = 0; i1 < j; i1++) {
                        if (!schemeinstantiation2.m1877(aschemeinstantiation[i1])) {
                           break label330;
                        }
                     }

                     C__B c__b2 = new C__B();
                     if (schemeinstantiation2.m1890()) {
                        for (int j1 = 0; j1 < j; j1++) {
                           if (!c__b2.m1576(schematicrule.premises[aint[j1]], this.m1628(j1 - j), schemeinstantiation2)) {
                              break label330;
                           }
                        }
                     } else {
                        flag8 = true;
                     }

                     C_HF c_hf2 = new C_HF(schematicrule, aint, schemeinstantiation2, c__b2);
                     this.f953.addElement(c_hf2);
                     this.f951 = true;
                     if (schemeinstantiation2.m1877(schemeinstantiation1) && schemeinstantiation2.m1890() && c__b2.m1578(schematicrule.conclusion)) {
                        this.f954.addElement(c_hf2);
                        this.f952 = true;
                     }

                     if (!flag4) {
                        if (!flag8) {
                           c_hf2.f395 = 1;
                        }
                     } else if (!schemeinstantiation2.m1877(schemeinstantiation)) {
                        if (!flag8) {
                           c_hf2.f395 = 2;
                        }
                     } else {
                        label394: {
                           if (schemeinstantiation2.m1890()) {
                              if (flag8) {
                                 for (int k1 = 0; k1 < j; k1++) {
                                    if (!c__b2.m1576(schematicrule.premises[aint[k1]], this.m1628(k1 - j), schemeinstantiation2)) {
                                       break label394;
                                    }
                                 }
                              }

                              C_MB c_mb2 = new C_MB();
                              Expression expression = schematicrule.conclusion.m1239(schemeinstantiation2, c_mb2);
                              int[][] aint1 = c_mb2.m1095(schematicrule.conclusion, expression);
                              if (!c__b2.m1573(schematicrule.conclusion, this.f944 ? this.f941 : null, aint1)) {
                                 c_hf2.f395 = 3;
                                 break label394;
                              }

                              if (!c__b2.m1575(schematicrule.conclusion, expression, aint1, null)) {
                                 c_hf2.f395 = 4;
                                 c_hf2.f396 = expression.m1259();
                                 break label394;
                              }

                              if (this.f944 && this.f941 != null && !expression.m1235(this.f941)) {
                                 c_hf2.f395 = 5;
                                 break label394;
                              }
                           }

                           if (schematicrule.m956(this.f958) == -1) {
                              flag1 = true;
                           } else if (this.f946 && schematicrule.m956(this.f959) == -1) {
                              flag2 = true;
                           } else if (!schematicrule.m952(this.f935.f317.f915)) {
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
               this.m1622("dererr038", Message.params("n", this.f938 + ""));
            } else {
               this.m1622("dererr039", Message.params("n", this.f938 + ""));
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
                  Expression expression1 = ((C_HF)this.f954.elementAt(0)).m695();
                  if (this.f935.f317.f915.commandMode) {
                     this.m1622("dererr033", Message.params("rule form conclusion", "\\l" + expression1 + "\\l"));
                     this.m1623("sum", expression1);
                  } else {
                     this.m1621("dererr103");
                  }
               } else {
                  C_HF c_hf = (C_HF)this.f954.elementAt(0);
                  if (c_hf.f395 == 4) {
                     Expression[] aexpression = (Expression[])((Vector)c_hf.f396).elementAt(0);
                     this.m1622(
                        "dererr074",
                        Message.params(
                           "rule form conclusion",
                           "\\l" + c_hf.m695() + "\\l",
                           "misbinder",
                           "\\l" + aexpression[0].symbol + "\\l",
                           "misbound",
                           "\\l" + aexpression[1] + "\\l"
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
               SchematicRule schematicrule1 = c_hf1.m688();
               int i2 = schematicrule1.premises.length;
               SchemeInstantiation schemeinstantiation3 = c_hf1.m690();
               if (schemeinstantiation3.m1890()) {
                  C_MB c_mb = new C_MB();
                  this.f942 = schematicrule1.conclusion.m1239(schemeinstantiation3, c_mb);
                  C__B c__b = c_hf1.m691();
                  boolean flag6 = vector.size() > 1 || !c__b.m1578(schematicrule1.conclusion);
                  if (!c__b.m1574(schematicrule1.conclusion, this.f942, c_mb, this)) {
                     return null;
                  } else {
                     if (flag6) {
                        this.m1598(c_hf1);
                     }

                     this.m1631(i2);
                     return schemeinstantiation3;
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
                  Expression expression2 = schematicrule1.conclusion.m1239(schemeinstantiation3, c_mb1);
                  int[][] aint2 = c_mb1.m1095(schematicrule1.conclusion, expression2);
                  int[] aint3 = c_hf1.m689();
                  C__B c__b1 = c_hf1.m691();
                  boolean flag7 = true;

                  for (int j2 = 0; j2 < i2; j2++) {
                     if (!c__b1.m1576(schematicrule1.premises[aint3[j2]], this.m1628(j2 - i2), schemeinstantiation3)) {
                        flag7 = false;
                        break;
                     }
                  }

                  if (flag7) {
                     flag7 = false;
                     if ((!this.f944 || this.f941 == null || c__b1.m1573(schematicrule1.conclusion, this.f941, aint2))
                        && c__b1.m1575(schematicrule1.conclusion, expression2, aint2, null)
                        && (!this.f944 || this.f941 == null || expression2.m1235(this.f941))) {
                        flag7 = true;
                     }
                  }

                  if (!flag7) {
                     SimpleTerm simpleterm = null;
                     if (c__b1.f925 != null) {
                        simpleterm = (SimpleTerm)((Expression[])c__b1.f925.elementAt(0))[1];
                     }

                     this.m1622("dererr043", Message.params("inst term", "\\l" + simpleterm + "\\l"));
                     return null;
                  } else if (this.f939.equals("EG") && !C_KB.m786(this, c_hf1, c__b1)) {
                     return null;
                  } else if (!c__b1.m1575(schematicrule1.conclusion, expression2, aint2, this)) {
                     return null;
                  } else {
                     this.f942 = expression2;
                     this.m1598(new C_HF(schematicrule1, aint3, schemeinstantiation3, c__b1));
                     this.m1631(i2);
                     return schemeinstantiation3;
                  }
               }
            }
         }
      }
   }

   boolean m1618(C_HF c_hf, SchemeInstantiation schemeinstantiation) {
      SchematicRule schematicrule = c_hf.m688();
      int i = schematicrule.premises.length;
      Expression[] aexpression = new Expression[i];
      int[][][] aint = new int[i][][];

      for (int j = 0; j < i; j++) {
         C_MB c_mb = new C_MB();
         aexpression[j] = schematicrule.premises[j].m1239(schemeinstantiation, c_mb);
         aint[j] = c_mb.m1095(schematicrule.premises[j], aexpression[j]);
      }

      C_MB c_mb1 = new C_MB();
      Expression expression = schematicrule.conclusion.m1239(schemeinstantiation, c_mb1);
      int[][] aint1 = c_mb1.m1095(schematicrule.conclusion, expression);
      int[] aint2 = c_hf.m689();
      C__B c__b = c_hf.m691();
      boolean flag = true;

      for (int k = 0; k < i; k++) {
         flag = false;
         if (!c__b.m1573(schematicrule.premises[aint2[k]], this.m1628(k - i), aint[aint2[k]])
            || !c__b.m1575(schematicrule.premises[aint2[k]], aexpression[aint2[k]], aint[aint2[k]], null)
            || !aexpression[aint2[k]].m1235(this.m1628(k - i))) {
            break;
         }

         flag = true;
      }

      if (flag) {
         flag = false;
         if ((!this.f944 || this.f941 == null || c__b.m1573(schematicrule.conclusion, this.f941, aint1))
            && c__b.m1575(schematicrule.conclusion, expression, aint1, null)
            && (!this.f944 || this.f941 == null || expression.m1235(this.f941))) {
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
                     SchematicLetter schematicletter2 = (SchematicLetter)c_hf.f393.f1189.elementAt(0);
                     Vector vector7 = schematicletter2.m1176(false);
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
                  SchematicLetter schematicletter1 = (SchematicLetter)c_hf.f393.f1189.elementAt(0);
                  Vector vector6 = schematicletter1.m1176(false);
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
                        ExpressionPath expressionpath = k1 == 0 ? null : (ExpressionPath)vector8.elementAt(0);
                        int l1 = expressionpath == null ? 0 : expressionpath.depth;
                        if (l1 == 0) {
                           this.m1620(vector, c_hf);
                           i--;
                           j--;
                        } else {
                           Expression expression6 = c_m_b1.f1277.m1220(expressionpath);
                           Expression expression3 = c_m_b2.f1277.m1220(expressionpath);
                           boolean flag = true;

                           for (int l = 1; l < k1; l++) {
                              expressionpath = (ExpressionPath)vector8.elementAt(l);
                              if (!expression6.m1235(c_m_b1.f1277.m1220(expressionpath))) {
                                 flag = false;
                              } else if (!expression3.m1235(c_m_b2.f1277.m1220(expressionpath))) {
                                 flag = false;
                              }

                              if (!flag) {
                                 break;
                              }

                              if (expressionpath.depth < l1) {
                                 l1 = expressionpath.depth;
                              }
                           }

                           if (!flag) {
                              this.m1620(vector, c_hf);
                              i--;
                              j--;
                           } else if (l1 == 1) {
                              SimpleTerm simpleterm1 = new SimpleTerm(SchematicLetter.m1853(0));
                              Expression expression4 = c_m_b1.f1277.copy();
                              Expression expression5 = c_m_b1.f1276.copy();
                              expression5.children.setElementAt(simpleterm1, 0);

                              for (int i2 = 0; i2 < k1; i2++) {
                                 expression4 = C_GA.m617(expression4, simpleterm1, (ExpressionPath)vector8.elementAt(i2));
                              }

                              if (expression4.m1259() != null || !c_hf.f393.m1881(expression5, expression4)) {
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
               SchematicLetter schematicletter = (SchematicLetter)c_hf.f393.f1189.elementAt(0);
               Vector vector2 = schematicletter.m1176(false);
               if (vector2 != null) {
                  C_m_B c_m_b = (C_m_B)vector2.elementAt(0);
                  Expression expression = c_m_b.f1276.getChild(0);
                  Vector vector3 = c_m_b.f1277.m1223(expression.instantiate(c_hf.f393));
                  int k = vector3.size();
                  if (k == 0) {
                     this.m1620(vector, c_hf);
                     i--;
                     j--;
                  } else if (k == 1) {
                     SimpleTerm simpleterm = new SimpleTerm(SchematicLetter.m1853(0));
                     Expression expression1 = C_GA.m617(c_m_b.f1277, simpleterm, (ExpressionPath)vector3.elementAt(0));
                     Expression expression2 = c_m_b.f1276.copy();
                     expression2.children.setElementAt(simpleterm, 0);
                     if (expression1.m1259() != null || !c_hf.f393.m1881(expression2, expression1)) {
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
               return l == 2 ? "\\l" + this.f935.f317.m1560(1).m7(true) + "\\l" : "\\l" + this.f935.f317.m44().getChild(l) + "\\l";
            }
         } else if (s.equals("rule forms") && this.f959 != null) {
            String s4 = "\\l";
            int i1 = this.f959.length;
            boolean flag3 = true;

            for (int i2 = 0; i2 < i1; i2++) {
               SchematicRule schematicrule1 = this.f959[i2];
               if (schematicrule1.premises.length >= this.f960 && schematicrule1.premises.length <= this.f961) {
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
                  SchematicRule schematicrule = ((C_HF)vector.elementAt(0)).m688();
                  return "\\l" + schematicrule.m958(" . ", " .: ") + "\\l";
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

   DerivationNode m1627(int i) {
      int j = this.f936.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (DerivationNode)this.f936.elementAt(i) : null;
   }

   Expression m1628(int i) {
      int j = this.f937.size();
      if (i < 0) {
         i += j;
      }

      return i >= 0 && i < j ? (Expression)this.f937.elementAt(i) : null;
   }

   C_L m1629(int i) {
      Expression expression = this.m1628(i);
      return expression == null ? null : expression.m1254();
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
