package edu.ucla.phil.logic;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ArgumentParser {
   String f824;
   String[] f825 = new String[0];
   String f826 = null;
   Expression[] f827 = new Expression[0];
   Expression f828 = null;
   boolean f829;
   C_HF[] f830 = null;
   C_HF[] f831 = null;
   static final String[] f832 = new String[]{"no error", "no conclusion", "no premises or conclusion"};

   ArgumentParser(String s) {
      this(s, false);
   }

   ArgumentParser(String s, boolean flag) {
      if ((this.f824 = s) != null) {
         if (this.f829 = s.indexOf(".") == -1) {
            s = ".:" + s;
         }

         int i = s.indexOf(".:");
         if (i != -1) {
            this.f826 = s.substring(i + 2).trim();

            try {
               this.f828 = LogicProgram.m1009(this.f826, false, false, flag);
            } catch (FormulaParseException formulaparseexception1) {
            }

            s = s.substring(0, i);
         }

         Vector vector = new Vector();
         Vector vector1 = new Vector();

         while (s.length() > 0) {
            int j = s.indexOf(".");
            String s1 = (j == -1 ? s : s.substring(0, j)).trim();
            s = j == -1 ? "" : s.substring(j + 1);

            try {
               Expression expression = LogicProgram.m1006(s1);
               if (expression != null) {
                  vector.addElement(s1);
                  vector1.addElement(expression);
               }
            } catch (FormulaParseException formulaparseexception) {
               vector.addElement(s1);
               vector1.addElement(null);
            }
         }

         this.f825 = new String[vector.size()];
         vector.copyInto(this.f825);
         this.f827 = new Expression[vector1.size()];
         vector1.copyInto(this.f827);
      }
   }

   static ArgumentParser m1382(String s) {
      return s == null ? null : new ArgumentParser(s);
   }

   static String m1383(String s) {
      String s1 = s;
      if (s == null) {
         return null;
      } else {
         do {
            Pattern pattern = Pattern.compile("\\.( *\\.)");
            s = s1;
            Matcher matcher = pattern.matcher(s1);
            s1 = matcher.replaceAll("$1");
         } while (!s1.equals(s));

         Matcher matcher1 = Pattern.compile("^ *\\.(.*)\\. *$").matcher(s1);
         return matcher1.replaceAll("$1");
      }
   }

   String m1384() {
      int i = this.f827.length;

      for (int j = 0; j < i; j++) {
         if (this.f827[j] == null) {
            return this.f825[j];
         }
      }

      return this.f828 == null && this.f826 != null && !this.f826.equals("") ? this.f826 : null;
   }

   int m1385() {
      return this.m1386(true);
   }

   int m1386(boolean flag) {
      if (this.f824 == null || this.f826 != null && !this.f826.equals("")) {
         return !flag && this.f829 ? 1 : 0;
      } else {
         return this.f827.length == 0 ? 2 : 1;
      }
   }

   static String m1387(ArgumentParser argumentparser, boolean flag, boolean flag1) {
      if (argumentparser == null) {
         return f832[2];
      } else {
         String s = argumentparser.m1384();
         if (s != null) {
            return "could not parse \"" + s + "\"";
         } else {
            int i = argumentparser.m1386(flag1);
            return i == 0 && flag ? null : f832[i];
         }
      }
   }

   String m1388(boolean flag, boolean flag1) {
      return m1387(this, flag, flag1);
   }

   int m1389(Rule rule) {
      if (rule != null && this.m1388(true, true) == null) {
         int i = this.f827 == null ? 0 : this.f827.length;
         SchematicRule[] aschematicrule = rule.m1374();
         int j = aschematicrule.length;
         Vector vector = new Vector();
         Vector vector1 = new Vector();

         for (int k = 0; k < j; k++) {
            SchematicRule schematicrule = aschematicrule[k];
            if ((schematicrule.premises == null ? 0 : schematicrule.premises.length) == i) {
               SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
               boolean flag = schematicrule.conclusion.m1266(this.f828, schemeinstantiation);
               C_XE c_xe = new C_XE(i);

               while (true) {
                  label149: {
                     int[] aint = c_xe.m1512();
                     SchemeInstantiation schemeinstantiation1 = new SchemeInstantiation();
                     C__B c__b = new C__B();
                     boolean flag1 = false;
                     if (i > 0) {
                        SchemeInstantiation[] aschemeinstantiation = new SchemeInstantiation[i];

                        for (int l = 0; l < i; l++) {
                           aschemeinstantiation[l] = new SchemeInstantiation();
                           if (!schematicrule.premises[aint[l]].m1266(this.f827[l], aschemeinstantiation[l])) {
                              break label149;
                           }
                        }

                        for (int k1 = 0; k1 < i; k1++) {
                           if (!schemeinstantiation1.m1877(aschemeinstantiation[k1])) {
                              break label149;
                           }
                        }

                        if (schemeinstantiation1.m1890()) {
                           for (int l1 = 0; l1 < i; l1++) {
                              if (!c__b.m1576(schematicrule.premises[aint[l1]], this.f827[l1], schemeinstantiation1)) {
                                 break label149;
                              }
                           }
                        } else {
                           flag1 = true;
                        }
                     }

                     if (!flag1) {
                        vector1.addElement(new C_HF(schematicrule, aint, (SchemeInstantiation)schemeinstantiation1.clone(), (C__B)c__b.clone()));
                     }

                     label99:
                     if (flag && schemeinstantiation1.m1877(schemeinstantiation) && schemeinstantiation1.m1890()) {
                        if (flag1) {
                           for (int j1 = 0; j1 < i; j1++) {
                              if (!c__b.m1576(schematicrule.premises[aint[j1]], this.f827[j1], schemeinstantiation1)) {
                                 break label99;
                              }
                           }

                           vector1.addElement(new C_HF(schematicrule, aint, (SchemeInstantiation)schemeinstantiation1.clone(), (C__B)c__b.clone()));
                        }

                        label92:
                        if (c__b.m1576(schematicrule.conclusion, this.f828, schemeinstantiation1)) {
                           if (schematicrule.f820.equalsIgnoreCase("EI")) {
                              Expression expression = schematicrule.m950().getChild(0).instantiate(schemeinstantiation1);
                              if (!(expression instanceof SimpleTerm)) {
                                 break label92;
                              }
                           }

                           vector.addElement(new C_HF(schematicrule, aint, schemeinstantiation1, c__b));
                        }
                     }
                  }

                  if (!c_xe.m1513()) {
                     break;
                  }
               }
            }
         }

         int i1;
         if ((i1 = vector1.size()) == 0) {
            this.f830 = null;
         } else {
            this.f830 = new C_HF[i1];
            vector1.copyInto(this.f830);
         }

         if ((i1 = vector.size()) == 0) {
            this.f831 = null;
         } else {
            this.f831 = new C_HF[i1];
            vector.copyInto(this.f831);
         }

         return this.f830 == null ? 0 : (this.f831 == null ? 1 : 2);
      } else {
         this.f830 = null;
         this.f831 = null;
         return 0;
      }
   }

   Expression m1390() {
      if (this.m1384() == null && this.m1385() == 0) {
         if (this.f827.length == 0) {
            return this.f828.copy();
         } else {
            Object object = this.f827[0].copy();
            if (!(object instanceof Formula)) {
               return null;
            } else if (!(this.f828 instanceof Formula)) {
               return null;
            } else {
               int i = this.f827.length;

               for (int j = 1; j < i; j++) {
                  if (!(this.f827[j] instanceof Formula)) {
                     return null;
                  }

                  ConnectiveFormula connectiveformula = new ConnectiveFormula("&");
                  connectiveformula.m2040((Formula)object);
                  connectiveformula.m2041((Formula)this.f827[j].copy());
                  object = connectiveformula;
               }

               ConnectiveFormula connectiveformula1 = new ConnectiveFormula("->");
               connectiveformula1.m2040((Formula)object);
               connectiveformula1.m2041((Formula)this.f828.copy());
               return connectiveformula1;
            }
         }
      } else {
         return null;
      }
   }

   @Override
   public String toString() {
      return this.m1391(".", ".:");
   }

   String m1391(String s, String s1) {
      if (this.f829) {
         return this.f826;
      } else {
         String s2 = "";
         int i = this.f827.length;

         for (int j = 0; j < i; j++) {
            s2 = s2 + (j == 0 ? "" : s) + this.f825[j];
         }

         if (this.f826 != null) {
            s2 = s2 + s1 + this.f826;
         }

         return s2;
      }
   }
}
