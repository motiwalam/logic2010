package edu.ucla.phil.logic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Vector;

class C_VE extends SizedPanel implements C_v_D {
   int f835;
   String f836;
   C_ZE f837;
   C_f_A f838;
   C_d_C parent;
   Container f839;
   static String[] f840 = LogicProgram.symbols;

   C_VE(C_d_C c_d_c, int i) {
      this(c_d_c, i, null, null);
   }

   C_VE(C_d_C c_d_c, int i, String s) {
      this(c_d_c, i, s, null);
   }

   C_VE(C_d_C c_d_c, int i, String s, Container container) {
      this.parent = c_d_c;
      this.setLayout(new FlowLayout(1, 0, 0));
      if (i != 11) {
         C_ZE c_ze = new C_ZE(LogicProgram.m995(connSymbol[this.f835 = i], maggie, f840));
         if (c_d_c.f1044 != null) {
            c_ze.setForeground(c_d_c.f1044.colors[0]);
         }

         c_ze.setFocusable(false);
         this.add(c_ze);
      }

      this.f838 = null;
      this.f837 = null;
      this.m1392(s);
      this.f839 = (Container)(container == null ? this : container);
      this.f839.setEnabled(false);
   }

   void m1392(String s) {
      if (this.f837 != null) {
         this.remove(this.f837);
         this.f837 = null;
         this.invalidate();
      }

      if ((this.f836 = s) != null) {
         this.f837 = new C_ZE(LogicProgram.m995(s, maggie, f840));
         if (this.parent.f1044 != null) {
            this.f837.setForeground(this.parent.f1044.colors[0]);
         }

         this.f837.setFocusable(false);
         this.add(this.f837);
      }
   }

   void m1393(C_d_C c_d_c, C_d_C c_d_c1, Vector vector, Vector vector1) {
      if (!c_d_c.f1044.errorMessagesDisabled) {
         this.add(this.f838 = new C_f_A(c_d_c, c_d_c1, vector, vector1));
         this.f839.setEnabled(true);
         this.revalidate();
         if (c_d_c1.f1048 == 11) {
            ExpressionPath expressionpath = new ExpressionPath();
            if (C_d_C.m1733(c_d_c1.m1684(), vector1, vector, expressionpath) == null) {
               for (int i = 0; i < expressionpath.depth; i++) {
                  C_d_C c_d_c2 = c_d_c;
                  C_d_C c_d_c3 = c_d_c1;
                  int j = vector.size();
                  int k = expressionpath.indexes[i];

                  while (j > k && (c_d_c2 = c_d_c2.m1688()) != null) {
                     c_d_c3 = c_d_c3.m1688();
                     if (c_d_c2.m1726()) {
                        j--;
                     }
                  }

                  if (c_d_c2 != null) {
                     C_VE c_ve1 = c_d_c2.m1685();
                     if (c_ve1.f838 == null) {
                        Vector vector2 = (Vector)vector.clone();
                        Vector vector3 = (Vector)vector1.clone();
                        vector2.setSize(j);
                        vector3.setSize(j);
                        c_ve1.add(c_ve1.f838 = new C_f_A(c_d_c2, c_d_c3, vector2, vector3));
                        c_ve1.f839.setEnabled(true);
                     }
                  }
               }
            }
         }
      }

      c_d_c.f1044.errorCount++;
   }

   void m1394() {
      if (this.f838 != null) {
         this.f839.setEnabled(false);
         this.remove(this.f838);
         this.f838 = null;
         this.invalidate();
      }
   }
}
