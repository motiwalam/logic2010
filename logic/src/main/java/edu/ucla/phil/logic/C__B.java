package edu.ucla.phil.logic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;

class C__B extends Hashtable implements LogicConstants {
   Vector f925 = null;
   int f926 = 0;

   @Override
   public Object clone() {
      C__B c__b1 = (C__B)super.clone();
      c__b1.f925 = null;
      c__b1.f926 = 0;
      return c__b1;
   }

   String m1569(String s) {
      return (String)this.get(s);
   }

   boolean m1570(String s, String s1, boolean flag) {
      if (!SimpleTerm.m1852(s1, flag)) {
         return false;
      } else {
         this.put(s, s1);
         return true;
      }
   }

   boolean m1571(String s, String s1) {
      return this.m1570(s, s1, false);
   }

   boolean m1572(Expression expression, Expression expression1, C_MB c_mb) {
      return expression1 == null ? true : this.m1573(expression, expression1, c_mb.m1095(expression, expression1));
   }

   boolean m1573(Expression expression, Expression expression1, int[][] aint) {
      if (expression1 == null) {
         return true;
      } else {
         Vector vector = expression.m1243();
         Vector vector1 = expression1.m1243();
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            int[] aint1 = aint[j];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               String s = m1579(vector1, aint1[l]);
               if (s != null) {
                  String s1 = m1579(vector, j);
                  String s2 = this.m1569(s1);
                  if (s2 == null) {
                     this.m1571(s1, s);
                  } else if (!s2.equals(s)) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   boolean m1574(Expression expression, Expression expression1, C_MB c_mb, DerivationLineChecker derivationlinechecker) {
      return this.m1575(expression, expression1, c_mb.m1095(expression, expression1), derivationlinechecker);
   }

   boolean m1575(Expression expression, Expression expression1, int[][] aint, DerivationLineChecker derivationlinechecker) {
      DerivationLine derivationline = derivationlinechecker == null ? null : derivationlinechecker.f935;
      Vector vector = expression.m1243();
      Vector vector1 = expression1.m1243();
      C_L c_l = new C_L();
      c_l.setSize(vector1.size());
      int i = vector.size();
      String[] astring = new String[i];

      for (int j = 0; j < i; j++) {
         astring[j] = m1579(vector, j);
      }

      label120:
      while (true) {
         this.f926 = 0;
         C__B c__b1 = (C__B)this.clone();

         for (int i1 = 0; i1 < i; i1++) {
            String s = c__b1.m1569(astring[i1]);
            if (s == null) {
               s = SchematicLetter.m1853(this.f926++);
               c__b1.m1570(astring[i1], s, true);
            }

            int[] aint1 = aint[i1];
            int k = aint1 == null ? 0 : aint1.length;

            for (int l = 0; l < k; l++) {
               c_l.setElementAt(s, aint1[l]);
            }
         }

         if (this.f926 != 0 && derivationline != null) {
            if (derivationline.f317.f915.serialMode && derivationlinechecker.f956 == null) {
               derivationlinechecker.m1621("dererr064");
               derivationline.f317.f915.complete = false;
               return false;
            }

            SchemeInstantiation schemeinstantiation = new SchemeInstantiation();
            C_PB[] ac_pb = new C_PB[this.f926];

            for (int j1 = 0; j1 < this.f926; j1++) {
               schemeinstantiation.m1886(ac_pb[j1] = new C_PB(SchematicLetter.m1853(j1)));
            }

            C_f_ c_f_ = new C_f_(schemeinstantiation, 50, derivationline.f317.f915.frame, true);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new C_m_A(0));
            String s1 = this.f926 == 1 ? "" : "s";
            String s2 = this.f926 == 1 ? "a " : "";
            jpanel.add(new C_ZE("Given the expression"));
            Expression expression2 = expression1.copy();
            expression2.m1241(c_l);
            String s3 = expression2.toString();
            C_CB c_cb = m1577(s3, 0, this.f926);
            jpanel.add(LogicProgram.m991(s3, 0, 14, 250, c_cb));
            jpanel.add(new C_ZE("please choose " + s2 + "symbol" + s1 + " for"));
            jpanel.add(new C_ZE("the following bound variable" + s1));
            jpanel.add(c_f_);
            String[] astring1 = new String[]{"OK", "Cancel"};
            MessageDialog messagedialog = new MessageDialog(derivationline.f317.f915.frame, "Line " + derivationline.m30(), jpanel, astring1);
            messagedialog.m1314(0);
            c_f_.m1801(messagedialog);
            derivationline.m22(true);
            if (!messagedialog.m1327(derivationlinechecker.f956, c_f_.m1803(), 0)) {
               if (derivationline.f317.f915.serialMode) {
                  derivationlinechecker.m1621("dererr064");
                  derivationline.f317.f915.complete = false;
                  messagedialog.dispose();
                  return false;
               }

               Rectangle rectangle = LogicProgram.m1035(derivationline.f324, null);
               messagedialog.pack();
               messagedialog.m1322(new Point(rectangle.x, rectangle.y + rectangle.height));
            } else {
               messagedialog.dispose();
            }

            if (messagedialog.f790 != 1 && messagedialog.f790 != -1) {
               if ((schemeinstantiation = c_f_.m1802()) == null) {
                  derivationline.m12(c_f_.f1099, c_f_.f1100);
                  return false;
               }

               this.f926 = 0;
               int k1 = 0;

               while (true) {
                  if (k1 >= i) {
                     continue label120;
                  }

                  if (aint[k1] != null && aint[k1].length != 0) {
                     String s4 = this.m1569(astring[k1]);
                     if (s4 == null) {
                        LetterReplacement letterreplacement = schemeinstantiation.m1878(ac_pb[this.f926++]);
                        if (letterreplacement != null && !this.m1571(astring[k1], letterreplacement.f368.symbol)) {
                           derivationline.m553(
                              "dererr060", derivationlinechecker, Message.params("variable name", "\\l" + letterreplacement.f368.symbol + "\\l")
                           );
                           return false;
                        }
                     }
                  }

                  k1++;
               }
            }

            derivationline.m11("dererr028");
            derivationline.f317.f915.abort(true);
            return false;
         }

         expression1.m1241(c_l);
         this.f925 = expression1.m1259();
         if (this.f925 != null) {
            if (derivationline != null) {
               derivationline.m550("dererr061", derivationlinechecker);
            }

            return false;
         }

         return true;
      }
   }

   boolean m1576(Expression expression, Expression expression1, SchemeInstantiation schemeinstantiation) {
      C_MB c_mb = new C_MB();
      Expression expression2 = expression.m1239(schemeinstantiation, c_mb);
      int[][] aint = c_mb.m1095(expression, expression2);
      if (!this.m1573(expression, expression1, aint)) {
         return false;
      } else {
         return !this.m1575(expression, expression2, aint, null) ? false : expression2.m1235(expression1);
      }
   }

   static C_CB m1577(String s, int i, int j) {
      Vector vector = new Vector();

      for (int k = 0; k < j; k++) {
         C_n_F c_n_f = new C_n_F();
         String s1 = SchematicLetter.m1853(i + k);
         int l = -1;

         while ((l = s.indexOf(s1, l + 1)) != -1) {
            c_n_f.m1986(l).m1986(l + s1.length());
         }

         vector.addElement(c_n_f);
      }

      return C_CB.m420(vector);
   }

   boolean m1578(Expression expression) {
      Vector vector = expression.m1243();
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         if (this.m1569(m1579(vector, j)) == null) {
            return false;
         }
      }

      return true;
   }

   static String m1579(Vector vector, int i) {
      return ((Expression)vector.elementAt(i)).getChild(0).getSymbol();
   }

   String m1580() {
      boolean flag = false;
      String s = "";

      for (Enumeration enumeration = this.keys(); enumeration.hasMoreElements(); flag = true) {
         String s1 = (String)enumeration.nextElement();
         s = s + (flag ? "." : "") + s1 + ":" + this.m1569(s1);
      }

      return s;
   }

   static C__B m1581(String s) {
      C__B c__b = new C__B();

      while (!s.equals("")) {
         int i = s.indexOf(".");
         String s1;
         if (i == -1) {
            s1 = s;
            s = "";
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         int j = s1.indexOf(":");
         if (j != -1) {
            c__b.m1571(s1.substring(0, j).trim(), s1.substring(j + 1).trim());
         }
      }

      return c__b;
   }
}
