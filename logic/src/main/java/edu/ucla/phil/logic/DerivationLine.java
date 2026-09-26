package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;

class DerivationLine extends JPanel implements DerivationNode, C_k_A, DerivationConstants, C_LC {
   DerivationBox f317;
   C_M f318;
   C_M f319;
   C_M f320;
   C_M f321;
   C_t_E f322;
   C_l_E f323;
   C_l_E f324;
   StyledTextPane f325;
   C_SB f326;
   C_t_E f327;
   Vector f328;
   Vector f329;
   private int f330;
   int f331;
   Expression f332;
   boolean f333;
   boolean f334;
   boolean f335;
   Vector f336;
   Message f337;
   boolean f338;
   String f339;
   FormulaEntryField f340;
   static String[] f341 = LogicProgram.symbols;

   DerivationLine(DerivationBox derivationbox, boolean flag) {
      this.f317 = derivationbox;
      this.f319 = new C_M(this);
      this.f320 = new C_M(this);
      ModuleFrame moduleframe = derivationbox.f915.frame;
      this.enableEvents(8L);
      if (flag) {
         this.f322 = new C_t_E(derivationbox.f916 == null ? "Problem: " : "Show ");
         this.f319.add(this.f322, "West");
         this.f320.add(this.f340 = new FormulaEntryField(moduleframe), "Center");
         if (!derivationbox.f915.doShowLog) {
            this.f340.setVisible(false);
         }

         this.f324 = null;
         this.f328 = null;
      } else {
         this.f322 = null;
         this.f340 = null;
         this.f320.add(this.f324 = new C_l_E(this), "Center");
         this.f328 = new Vector();
      }

      this.f319.add(this.f323 = new C_l_E(this), "Center");
      this.f319.add(new C_M(this, derivationbox.f915.hSpacer), "East");
      this.f320.add(new C_M(this, derivationbox.f915.hSpacer), "East");
      this.f321 = new C_M(this);
      this.f325 = new StyledTextPane("");
      this.f325.m1787(true);
      this.f325.m1789(true);
      this.f325.setEditable(false);
      this.f325.setFocusable(false);
      this.f321.add(this.f325, "Center");
      this.f326 = new C_SB(this);
      this.f326.setVisible(false);
      this.f318 = new C_M(this);
      this.f318.setFont(derivationbox.f915.font);
      if (derivationbox.f915.problem == null) {
         this.f318.setLayout(new C_u_A(derivationbox, derivationbox.f915.lineColumns - 1));
      } else {
         this.f318.setLayout(new C_u_A(derivationbox.f915.problem, derivationbox.f915.lineColumns - 1));
      }

      this.f318.add(this.f319);
      this.f318.add(this.f320);
      this.f318.add(this.f321);
      this.f318.add(this.f326);
      this.setLayout(new BorderLayout());
      this.add(this.f318, "Center");
      this.add(new C_M(this, derivationbox.f915.vSpacer), "South");
      derivationbox.f915.numbers.add(this.f327 = new C_t_E(this));
      this.f329 = new Vector();
      this.f331 = 0;
      this.f335 = false;
      this.m579();
      this.f332 = null;
      this.f333 = true;
      this.f336 = null;
      this.f337 = null;
      this.f339 = null;
      this.f338 = false;
   }

   public boolean m545() {
      return true;
   }

   @Override
   public DerivationBox m17() {
      return this.f317.f917 == this ? this.f317.f916 : this.f317;
   }

   @Override
   public void m6(String s) {
      if (this.f323 != null) {
         this.f323.setText(LogicProgram.m995(s, maggie, f341));
      }
   }

   @Override
   public String m7(boolean flag) {
      if (this.f323 == null) {
         return null;
      } else {
         String s = this.f323.getText();
         if (flag) {
            s = this.m546(s);
         }

         return LogicProgram.m995(s, f341, maggie);
      }
   }

   @Override
   public void m8(String s) {
      if (this.f324 != null) {
         this.f324.setText(s);
      }
   }

   @Override
   public String m9(boolean flag) {
      if (this.f324 == null) {
         return null;
      } else {
         String s = this.f324.getText();
         if (flag) {
            s = this.m546(s);
         }

         return s;
      }
   }

   String m546(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf(35);
         return i == -1 ? s : s.substring(0, i).trim();
      }
   }

   @Override
   public String m547(String s) {
      if (s.equals("line number")) {
         return this.m30() + "";
      } else if (s.equals("wff")) {
         return "\\l" + this.m7(true) + "\\l";
      } else {
         if (s.equals("show")) {
            DerivationBox derivationbox = this.m17();
            if (derivationbox != null) {
               return "\\l" + derivationbox.m7(true) + "\\l";
            }
         }

         if (s.equals("show line number")) {
            DerivationBox derivationbox1 = this.m17();
            if (derivationbox1 != null) {
               return derivationbox1.f917.m30() + "";
            }
         }

         if (s.equals("premises")) {
            Expression[] aexpression1 = this.f317.f915.premises;
            int j1 = aexpression1 == null ? 0 : aexpression1.length;
            Object object = "\\l";

            for (int k = 0; k < j1; k++) {
               if (k != 0) {
                  object = object + "\\n";
               }

               object = object + aexpression1[k];
            }

            return object + "\\l";
         } else {
            if (s.length() >= 7 && s.substring(0, 7).equals("premise")) {
               Expression[] aexpression = this.f317.f915.premises;
               int j = aexpression == null ? 0 : aexpression.length;

               int i;
               try {
                  i = Integer.parseInt(s.substring(7).trim());
               } catch (NumberFormatException numberformatexception1) {
                  i = 0;
               }

               if (i > 0 && i <= j) {
                  return "\\l" + aexpression[i - 1] + "\\l";
               }
            }

            if (s.equals("branch lines left")) {
               int l = this.m548();
               return l + " line" + (l == 1 ? "" : "s");
            } else if (!s.equals("operator") && !s.equals("quantifier") && !s.equals("connective")) {
               if (s.equals("bound variable") || s.equals("antecedent")) {
                  s = "arg 1";
               } else if (s.equals("bound wff") || s.equals("consequent")) {
                  s = "arg 2";
               }

               if (s.length() >= 3 && s.substring(0, 3).equals("arg")) {
                  Expression expression1 = this.m44();
                  if (expression1 == null) {
                     return "";
                  }

                  int k1 = expression1.getChildCount();

                  int i1;
                  try {
                     i1 = Integer.parseInt(s.substring(3).trim());
                  } catch (NumberFormatException numberformatexception) {
                     i1 = 0;
                  }

                  if (i1 > 0 && i1 <= k1) {
                     return "\\l" + expression1.getChild(i1 - 1) + "\\l";
                  }
               }

               return null;
            } else {
               Expression expression = this.m44();
               return expression == null ? "" : "\\l" + expression.getSymbol() + "\\l";
            }
         }
      }
   }

   int m548() {
      Object object = this.f317.f917 == this ? this.f317 : this;
      DerivationBox derivationbox = ((DerivationNode)object).m17();
      int i = 0;
      int j = derivationbox.m1555();
      int k = ((DerivationNode)object).m16();

      for (int l = k + 1; l < j; l++) {
         i += derivationbox.m1560(l).m38(false);
      }

      return i;
   }

   @Override
   public void m10(String s, boolean flag) {
      this.f331 = 0;
      if (s == null) {
         this.f325.setBackground(this.f317.f915.colors[2]);
         this.f325.m1791(this.f317.f915.colors[0]);
         this.f325.setText("");
      } else {
         this.f325.setBackground(this.f317.f915.colors[flag ? 3 : 2]);
         this.f325.m1791(this.f317.f915.colors[flag ? 5 : 0]);
         this.f325.setText(s);
      }
   }

   @Override
   public void m11(String s) {
      this.m554(s, null, this.f317.f915.phase, null);
   }

   void m549(String s, int i) {
      this.m554(s, null, i, null);
   }

   void m550(String s, DerivationLineChecker derivationlinechecker) {
      this.m554(s, derivationlinechecker, this.f317.f915.phase, null);
   }

   void m551(String s, DerivationLineChecker derivationlinechecker, int i) {
      this.m554(s, derivationlinechecker, i, null);
   }

   @Override
   public void m12(String s, Hashtable hashtable) {
      this.m554(s, null, this.f317.f915.phase, hashtable);
   }

   void m552(String s, int i, Hashtable hashtable) {
      this.m554(s, null, i, hashtable);
   }

   void m553(String s, DerivationLineChecker derivationlinechecker, Hashtable hashtable) {
      this.m554(s, derivationlinechecker, this.f317.f915.phase, hashtable);
   }

   void m554(String s, DerivationLineChecker derivationlinechecker, int i, Hashtable hashtable) {
      if (!LPDerivation.restating) {
         if (this.f331 == 0 || i < this.f331) {
            this.f337 = C_n_.get(s);
            if (this.f337.isError) {
               this.f325.setBackground(this.f317.f915.colors[3]);
               this.f325.m1791(this.f317.f915.colors[4]);
               if (!this.f337.id.equalsIgnoreCase("dererr078") && !this.f337.id.equalsIgnoreCase("dererr057")) {
                  this.f317.f915.errorCount++;
               }
            } else {
               this.f325.setBackground(this.f317.f915.colors[2]);
               this.f325.m1791(this.f317.f915.colors[0]);
            }

            this.f331 = i;
            if (derivationlinechecker == null) {
               this.f339 = C_n_.m1961(this.f337.title, hashtable, this);
            } else {
               this.f339 = C_n_.m1960(this.f337.title, hashtable, derivationlinechecker);
            }

            if (this.f317.f915.errorMessagesDisabled && !this.f337.id.equalsIgnoreCase("dererr064")) {
               if (!this.f337.id.equalsIgnoreCase("dererr078") && (this.f317.f916 != null || this.f317.f917 != this)) {
                  if (this.f337.isError) {
                     this.f325.setText("Error");
                  }
               } else {
                  this.f325.setText(LogicProgram.m1004(this.f339));
               }
            } else {
               this.f325.setText(LogicProgram.m1004(this.f339));
               this.f326.setVisible(true);
               this.f326.m1281(hashtable, derivationlinechecker);
            }

            if (this.f337.isError) {
               this.m555();
            }
         }
      }
   }

   void m555() {
      if (this.f317.f915.serialMode && this.f317.f916 != null) {
         DerivationBox derivationbox = this.m17();
         if (derivationbox.f916 != null) {
            derivationbox.m1561("dererr078", 4);
         }
      }
   }

   void m556(String s, Object object) {
      this.f326.m1282(s, object);
   }

   @Override
   public void m13() {
      this.m557(this.f317.f915.phase);
   }

   void m557(int i) {
      if (!LPDerivation.restating) {
         if (this.f331 == 0 || i == this.f331) {
            this.f326.setVisible(false);
            this.f326.m1283();
            this.f337 = null;
            this.f335 = false;
            this.f325.setBackground(this.f317.f915.colors[2]);
            this.f325.setForeground(this.f317.f915.colors[0]);
            this.f331 = 0;
            this.f339 = null;
            this.f325.setText("");
         }
      }
   }

   @Override
   public C_l_E m14() {
      return this.f323;
   }

   int m558(boolean flag) {
      C_l_E c_l_e = flag ? this.f324 : this.f323;
      if (c_l_e == null) {
         return 0;
      } else {
         return c_l_e == this.f317.f915.focus ? c_l_e.getSelectionStart() : c_l_e.f1255;
      }
   }

   int m559(boolean flag) {
      C_l_E c_l_e = flag ? this.f324 : this.f323;
      if (c_l_e == null) {
         return 0;
      } else {
         return c_l_e == this.f317.f915.focus ? c_l_e.getSelectionEnd() : c_l_e.f1256;
      }
   }

   int m560(boolean flag) {
      C_l_E c_l_e = flag ? this.f324 : this.f323;
      if (c_l_e == null) {
         return 0;
      } else {
         return c_l_e == this.f317.f915.focus ? c_l_e.getCaretPosition() : c_l_e.f1257;
      }
   }

   void m561(int i, int j, boolean flag) {
      C_l_E c_l_e = flag ? this.f324 : this.f323;
      if (c_l_e != null) {
         c_l_e.select(c_l_e.f1255 = i, c_l_e.f1256 = j);
      }
   }

   void m562(int i, boolean flag) {
      C_l_E c_l_e = flag ? this.f324 : this.f323;
      if (c_l_e != null) {
         c_l_e.setCaretPosition(c_l_e.f1257 = i);
      }
   }

   @Override
   public C_l_E m15() {
      return this.f324;
   }

   @Override
   public int m16() {
      return this.f317 == null ? -1 : this.f317.m1554(this.f317.m1563(this));
   }

   @Override
   public void m22(boolean flag) {
      this.m29();
      if ((flag || this.f323 == null) && this.f324 != null) {
         this.f324.requestFocus();
      } else if (this.f323 != null) {
         this.f323.requestFocus();
      }
   }

   @Override
   public void requestFocus() {
   }

   void m563(boolean flag) {
      if (this.f324 != null && (this.f323 == null ? flag : this.f323.getText().equals(""))) {
         this.f317.f915.abort(false);
         this.f317.f915.resetVarNames();
         if (flag) {
            this.f336 = null;
         }

         if (this.f317.f915.focus == this.f324) {
            this.m586();
         } else if (this.f317.f915.focus == this.f323) {
            this.m594();
         }

         if (this.m595(true) && this.f334) {
            this.m573();
         }
      }
   }

   DerivationBox m564() {
      if (this.f323 != null && this.f324 != null) {
         String s = this.f323.getText().trim();
         if (s.length() < 4 || !s.substring(0, 4).equalsIgnoreCase("Show")) {
            return null;
         } else if (s.length() > 4 && !Character.isWhitespace(s.charAt(4))) {
            return null;
         } else {
            this.f323.setText(s.substring(4).trim());
            return this.m571();
         }
      } else {
         return null;
      }
   }

   @Override
   public DerivationLine m18() {
      DerivationBox derivationbox = this.f317;
      C_l_E c_l_e = this.f317.f915.focus;
      boolean flag = c_l_e != null && c_l_e == c_l_e.f1258.f324;
      int i = this.m16();
      if (this.f317.f918 == this || !this.f317.m2124()) {
         derivationbox = derivationbox.f916;
         if (derivationbox == null) {
            return null;
         }

         i = this.f317.m16();
      }

      if (flag) {
         c_l_e.f1258.m586();
      }

      DerivationLine derivationline1 = derivationbox.m1556(derivationbox.m1553(i + 1));
      this.f317.f915.problem.m1558();
      if (flag) {
         c_l_e.f1258.m45();
         c_l_e.f1258.m592();
      }

      return derivationline1;
   }

   @Override
   public void m19(boolean flag1) {
      if (this.f317.f917 == this) {
         if (this.f317.f916 != null) {
            this.m572().m19(false);
         }
      } else {
         if (this.f317.f918 == this) {
            this.m575();
         }

         C_l_E c_l_e = this.f317.f915.focus;
         boolean flag = c_l_e != null && c_l_e == c_l_e.f1258.f324 && c_l_e.f1258 != this;
         if (flag) {
            c_l_e.f1258.m586();
         }

         this.m592();
         this.m41();
         this.f317.f915.numbers.remove(this.f327);
         this.m568();
         this.f317.remove(this);
         this.f317.f915.setWidths(false);
         this.f317.f915.problem.m1558();
         if (flag) {
            c_l_e.f1258.m45();
            c_l_e.f1258.m592();
         }
      }
   }

   @Override
   public DerivationNode m23(boolean flag) {
      int i = this.m16() + 1;

      DerivationBox derivationbox;
      for (derivationbox = this.f317;
         derivationbox != null && i >= (flag && !derivationbox.m2124() ? 1 : derivationbox.m1555());
         derivationbox = derivationbox.f916
      ) {
         i = derivationbox.m16() + 1;
      }

      return derivationbox == null ? null : derivationbox.m1560(i);
   }

   @Override
   public DerivationNode m24(boolean flag) {
      int i = this.m16();
      DerivationBox derivationbox = this.f317;
      if (i == 0) {
         i = derivationbox.m16();
         derivationbox = derivationbox.f916;
      }

      if (derivationbox == null) {
         return null;
      } else {
         DerivationNode derivationnode = derivationbox.m1560(i - 1);

         while (derivationnode instanceof DerivationBox) {
            derivationbox = (DerivationBox)derivationnode;
            if (flag && !derivationbox.m2124() || derivationbox.m1555() == 1) {
               break;
            }

            derivationnode = derivationbox.m1560(derivationbox.m1555() - 1);
         }

         return derivationnode;
      }
   }

   @Override
   public DerivationNode m25() {
      return (DerivationNode)(this.f317.f917 == this ? this.f317 : this);
   }

   @Override
   public boolean m26() {
      return this.f317.f917 == this;
   }

   @Override
   public boolean m27() {
      return this.f317.f918 == this;
   }

   @Override
   public boolean m28() {
      for (DerivationBox derivationbox = this.f317.f917 == this ? this.f317.f916 : this.f317; derivationbox != null; derivationbox = derivationbox.f916) {
         if (!derivationbox.m2124()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void m29() {
      for (DerivationBox derivationbox = this.f317.f917 == this ? this.f317.f916 : this.f317; derivationbox != null; derivationbox = derivationbox.f916) {
         if (!derivationbox.m2124()) {
            derivationbox.m2123(true);
         }
      }
   }

   @Override
   public Rectangle m31() {
      return LogicProgram.m1035(this, this.f317.f915.problemPanel);
   }

   boolean m565(DerivationNode derivationnode) {
      DerivationBox derivationbox = this.m17();
      Object object = this.m25();
      DerivationBox derivationbox1 = derivationnode.m17();
      derivationnode = derivationnode.m25();
      if (derivationbox1 != null && this.f317.f917 != this) {
         while (derivationbox != derivationbox1 && derivationbox != null) {
            object = derivationbox;
            derivationbox = derivationbox.f916;
         }

         if (derivationbox == derivationbox1 && derivationnode.m16() < ((DerivationNode)object).m16()) {
            return true;
         } else {
            int i = derivationnode.m30();
            if (i >= this.m30()) {
               this.m12("dererr044", Message.params("remote line number", i + ""));
               return false;
            } else if (derivationbox == derivationbox1) {
               this.m12("dererr045", Message.params("remote line number", i + ""));
               return false;
            } else {
               this.m12("dererr046", Message.params("remote line number", i + ""));
               return false;
            }
         }
      } else {
         this.m12("dererrtxt", Message.params("text", "unexpected error; contact instructor: LPDerLine.canUse(ILPDerLine)"));
         return false;
      }
   }

   @Override
   public void m20() {
      if (this.f317.f918 == this) {
         this.m575();
      }

      if (this.f317.f917 == this) {
         this.f317.m20();
      } else {
         int i = this.m16();
         if (i > 0) {
            DerivationNode derivationnode = this.f317.m1560(i - 1);
            if (derivationnode instanceof DerivationBox) {
               C_l_E c_l_e = this.f317.f915.focus;
               if (c_l_e != null && c_l_e.f1258 == this) {
                  this.f317.m1551(false);
               }

               this.f317.remove(this);
               ((DerivationBox)derivationnode).add(this, -1);
               this.f317 = (DerivationBox)derivationnode;
               if (c_l_e != null && c_l_e.f1258 == this) {
                  this.f317.m1551(true);
               }

               c_l_e.requestFocus();
            }
         }
      }
   }

   void m566() {
      int i = this.m16();
      DerivationBox derivationbox = this.f317;
      if (i == 0) {
         i = derivationbox.m16();
         derivationbox = derivationbox.f916;
      }

      if (derivationbox != null) {
         DerivationNode derivationnode = null;
         int j = i;

         while (--j > 0) {
            derivationnode = derivationbox.m1560(j);
            if (derivationnode instanceof DerivationBox && ((DerivationBox)derivationnode).f918 == null && ((DerivationBox)derivationnode).m2124()) {
               break;
            }
         }

         if (j > 0) {
            while (--i >= j) {
               derivationbox.m1560(j + 1).m20();
            }

            derivationnode.m34();
            derivationbox.f915.setWidths(false);
         }
      }

      this.f317.revalidate();
   }

   @Override
   public void m21() {
      if (this.f317.f918 == this) {
         this.m575();
      }

      if (this.f317.f917 == this) {
         this.f317.m21();
      } else {
         int i = this.m16();
         if (i == this.f317.m1555() - 1) {
            DerivationBox derivationbox = this.f317.f916;
            if (derivationbox != null) {
               C_l_E c_l_e = this.f317.f915.focus;
               if (c_l_e != null && c_l_e.f1258 == this) {
                  this.f317.m1551(false);
               }

               this.f317.remove(this);
               derivationbox.add(this, derivationbox.m1553(this.f317.m16() + 1));
               this.f317 = derivationbox;
               if (c_l_e != null && c_l_e.f1258 == this) {
                  this.f317.m1551(true);
               }

               c_l_e.requestFocus();
            }
         }
      }
   }

   void m567() {
      int i = this.m16();
      DerivationBox derivationbox = this.f317;
      if (i == 0) {
         i = derivationbox.m16();
         derivationbox = derivationbox.f916;
      }

      if (derivationbox != null && derivationbox.f916 != null) {
         int j = derivationbox.m1555();

         while (--j >= i) {
            derivationbox.m1560(j).m21();
         }

         derivationbox.f916.m34();
         derivationbox.f915.setWidths(false);
      }

      this.f317.revalidate();
   }

   void m568() {
      if (this.f323 != null) {
         this.f323.f1259 = true;
      }

      if (this.f324 != null) {
         this.f324.f1259 = true;
      }
   }

   void m569() {
      if (this.f323 != null) {
         this.f323.f1259 = false;
      }

      if (this.f324 != null) {
         this.f324.f1259 = false;
      }
   }

   DerivationNode m570() {
      Object object;
      if (this.f317.f917 == this) {
         object = this.m572();
      } else {
         object = this.m571();
      }

      return (DerivationNode)object;
   }

   DerivationBox m571() {
      if (this.f317.f917 == this) {
         return this.f317;
      } else if (this.f317.f918 == this) {
         return null;
      } else {
         this.f319.add(this.f322 = new C_t_E("Show "), "West");
         this.f320.remove(this.f324);
         this.f320.add(this.f340 = new FormulaEntryField(this.f317.f915.frame), "Center");
         this.f340.setForeground(this.f317.f915.colors[0]);
         this.f340.setBackground(this.f317.f915.colors[1]);
         if (!this.f317.f915.doShowLog) {
            this.f340.setVisible(false);
         }

         this.f324 = null;
         this.m592();
         this.f328 = null;
         DerivationBox derivationbox = this.f317;
         int i = derivationbox.m1563(this);
         this.m568();
         derivationbox.remove(this);
         derivationbox.add(this.f317 = new DerivationBox(this), i);
         this.m569();
         this.f317.revalidate();
         return this.f317;
      }
   }

   DerivationLine m572() {
      DerivationBox derivationbox = this.f317.f916;
      if (this.f317.f917 != this) {
         return this;
      } else if (derivationbox == null) {
         return null;
      } else {
         int i = this.f317.m1555();

         while (--i > 0) {
            this.f317.m1560(i).m21();
         }

         this.f319.remove(this.f322);
         this.f322 = null;
         this.f320.remove(this.f340);
         this.f340 = null;
         this.f320.add(this.f324 = new C_l_E(this), "Center");
         this.f328 = new Vector();
         int j = derivationbox.m1563(this.f317);
         this.m568();
         derivationbox.remove(this.f317);
         derivationbox.add(this, j);
         this.f317 = derivationbox;
         this.m569();
         this.f317.revalidate();
         return this;
      }
   }

   void m573() {
      if (this.f317.f918 == this) {
         this.m575();
      } else {
         this.m574();
      }

      this.f317.f915.invalrepaint();
   }

   void m574() {
      if (this.f317.f916 == null) {
         C_KB.m762("dernot009", null, this);
      } else if (this.f317.f917 == this) {
         C_KB.m762("dernot010", null, this);
      } else {
         if (this.f317.f918 == null && this.m576()) {
            this.m577();
         }

         if (this.f317.m1555() - 1 != this.m16()) {
            C_KB.m762("dernot011", null, this);
         } else if (this.f317.f918 == null) {
            this.f323.f1259 = true;
            this.f319.remove(this.f323);
            this.f323 = null;
            this.f332 = null;
            this.f333 = true;
            this.m557(1);
            this.f317.f918 = this;
            this.f317.f917.f322.m2090(true);
            this.f317.revalidate();
         }
      }
   }

   void m575() {
      if (this.f317.f918 == this) {
         this.f319.add(this.f323 = new C_l_E(this), "Center");
         this.f332 = null;
         this.f333 = true;
         this.f317.f918 = null;
         this.f317.f917.f322.m2090(false);
         this.f317.revalidate();
      }
   }

   boolean m576() {
      int i = this.f317.m1555();

      for (int j = this.m16() + 1; j < i; j++) {
         DerivationNode derivationnode = this.f317.m1560(j);
         if (derivationnode instanceof DerivationBox) {
            return false;
         }

         String s = ((DerivationLine)derivationnode).m7(true);
         String s1 = ((DerivationLine)derivationnode).m9(true);
         if (s != null && !s.trim().equals("") || s1 != null && !s1.trim().equals("")) {
            return false;
         }
      }

      return true;
   }

   void m577() {
      int i = this.m16() + 1;

      while (this.f317.m1555() > i) {
         this.f317.m1560(i).m19(true);
      }
   }

   @Override
   public int m33(int i) {
      this.m581(i);
      return i + 1;
   }

   void m578() {
      ModuleFrame moduleframe = this.f317.f915.frame;
      if (moduleframe != null) {
         this.setFont(LogicProgram.getFont(this.f317.f915.fontSize));
      }
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      if (this.f322 != null) {
         this.f322.setFont(font);
      }

      if (this.f323 != null) {
         this.f323.setFont(font);
      }

      if (this.f324 != null) {
         this.f324.setFont(font);
      }

      if (this.f340 != null) {
         this.f340.setFont(font);
      }

      if (this.f325 != null) {
         this.f325.setFont(font);
      }

      if (this.f326 != null) {
         this.f326.setFont(font);
      }
   }

   void m579() {
      ModuleFrame moduleframe = this.f317.f915.frame;
      if (moduleframe != null) {
         this.m35(this.f317.f915.colors);
      }
   }

   @Override
   public void m35(Color[] acolor) {
      C_l_E c_l_e = this.f317.f915.focus;
      this.setForeground(acolor[0]);
      this.setBackground(acolor[1]);
      this.f327.setForeground(acolor[0]);
      this.f327.setBackground(acolor[1]);
      if (this.f322 != null) {
         if (this.m580()) {
            this.f322.setForeground(acolor[1]);
            this.f322.setBackground(acolor[0]);
         } else {
            this.f322.setForeground(acolor[0]);
            this.f322.setBackground(acolor[2]);
         }
      }

      if (this.f323 != null) {
         this.f323.setForeground(c_l_e == this.f323 ? acolor[1] : acolor[0]);
         this.f323.setBackground(c_l_e == this.f323 ? acolor[0] : acolor[2]);
      }

      if (this.f324 != null) {
         this.f324.setForeground(c_l_e == this.f324 ? acolor[1] : acolor[0]);
         this.f324.setBackground(c_l_e == this.f324 ? acolor[0] : acolor[2]);
      }

      if (this.f340 != null) {
         this.f340.setForeground(acolor[0]);
         this.f340.setBackground(acolor[1]);
      }

      this.f325.setForeground(this.f335 ? acolor[4] : acolor[0]);
      this.f321.setBackground(this.f335 ? acolor[3] : acolor[2]);
      this.f326.setForeground(acolor[0]);
      this.f326.setBackground(acolor[1]);
   }

   boolean m580() {
      if (this.f322 == null) {
         return false;
      } else {
         C_l_E c_l_e = this.f317.f915.focus;
         if (c_l_e == null) {
            return false;
         } else {
            DerivationBox derivationbox = c_l_e.f1258.m17();
            return derivationbox == null ? false : derivationbox.f917 == this;
         }
      }
   }

   @Override
   public void m34() {
      Point point = this.getLocation();
      point.x = this.f317.f917 == this ? 0 : this.f317.f915.indent;
      this.setLocation(point);
      int[] aint;
      if (this.f317.f916 == null && this.f317.f917 == this) {
         aint = this.f317.f915.problemWidths;
      } else {
         aint = this.f317.f915.proofWidths;
      }

      ((C_u_A)this.f318.getLayout()).m2098(aint);
      this.f318.doLayout();
      this.revalidate();
   }

   @Override
   public int m36(boolean flag) {
      return 0;
   }

   @Override
   public int m37() {
      return this.f317.f917 == this ? this.f317.m37() : this.f317.m37() + 1;
   }

   @Override
   public int m38(boolean flag) {
      return 1;
   }

   @Override
   public synchronized int m30() {
      return this.f330;
   }

   public synchronized void m581(int i) {
      if (this.f330 != i) {
         if (this.f327 != null) {
            this.f327.setText(i == 0 ? " " : Integer.toString(i));
         }

         this.f330 = i;
      }
   }

   @Override
   public DerivationNode m32(int i) {
      return null;
   }

   DerivationNode m582(Integer integer) {
      return integer == null ? null : this.m583(integer);
   }

   DerivationNode m583(int i) {
      if (i >= 0) {
         return null;
      } else {
         int j = this.m16();
         DerivationBox derivationbox = this.f317;
         if (j == 0) {
            j = derivationbox.m16();
            derivationbox = derivationbox.f916;
         }

         if (derivationbox == null) {
            return null;
         } else {
            j += i;
            return j > 0 ? derivationbox.m1560(j) : derivationbox.f917.m583(j - 1);
         }
      }
   }

   static boolean m584(char c0) {
      return !Character.isLetter(c0) && c0 < 256 ? "~!@#$%^&*()_+-=<>|".indexOf(c0) != -1 : true;
   }

   void m585() {
      String s = this.m9(true);
      int i = s.length();
      int j = 0;
      int i1 = 0;
      char c0 = 0;
      char c1 = 0;

      while (true) {
         if (j < i) {
            c0 = c1;
            c1 = s.charAt(j);
            if (c1 != '-') {
               j++;
               continue;
            }
         }

         if (j >= i) {
            this.m45();
            this.m592();
            return;
         }

         int k;
         for (k = j++; j < i; j++) {
            c1 = s.charAt(j);
            if (!Character.isDigit(c1)) {
               break;
            }
         }

         int l = j++;
         if ((k == 0 || !m584(c0)) && (l == i || !m584(c1)) && this.m588(k - i1, l - k, this.m582(LogicProgram.parseInteger(s.substring(k, l)))) != null) {
            i1 = l;
         }
      }
   }

   void m586() {
      this.m587(true);
   }

   void m587(boolean flag) {
      int i = this.f317.f915.setPhase(2);

      try {
         if (this.f324 != null) {
            this.m13();
            this.m592();
            if (flag) {
               this.m585();
            }

            String s = this.m9(true);
            int j = s.length();
            int k = 0;
            int j1 = 0;
            char c0 = 0;
            char c1 = 0;

            while (true) {
               while (k < j) {
                  c0 = c1;
                  c1 = s.charAt(k);
                  if (Character.isDigit(c1)) {
                     break;
                  }

                  k++;
               }

               if (k >= j) {
                  return;
               }

               int l;
               for (l = k++; k < j; k++) {
                  c1 = s.charAt(k);
                  if (!Character.isDigit(c1)) {
                     break;
                  }
               }

               int i1 = k++;
               if (l != 0 && m584(c0) || i1 != j && m584(c1)) {
                  if ((l == 0 || !m584(c0)) && i1 != j && m584(c1)) {
                     this.m11("dererr047");
                  }
               } else if (this.m589(l - j1, i1 - l, LogicProgram.parseInteger(s.substring(l, i1))) != null) {
                  j1 = i1;
               }
            }
         }
      } finally {
         this.f317.f915.setPhase(i);
      }
   }

   @Override
   public void m45() {
      String s = this.m9(false);
      if (s != null) {
         String s1 = "";
         int i = this.f328.size();
         int j = 0;
         if (this.f317.f915.focus == this.f324) {
            this.f324.m1930(false);
         }

         for (int k = 0; k < i; k++) {
            C_AA c_aa = (C_AA)this.f328.elementAt(k);
            String s2 = Integer.toString(c_aa.f118.m30());
            this.f324.m1932(s1.length() + c_aa.f116, c_aa.f117, s2.length());
            s1 = s1 + s.substring(j, j + c_aa.f116) + s2;
            j += c_aa.f116 + c_aa.f117;
            c_aa.f117 = s2.length();
         }

         s1 = s1 + s.substring(j);
         this.m8(s1);
         if (this.f317.f915.focus == this.f324) {
            this.f324.m1931();
         }
      }
   }

   C_AA m588(int i, int j, DerivationNode derivationnode) {
      if (derivationnode == null) {
         return null;
      } else {
         C_AA c_aa = new C_AA(i, j, derivationnode, this);
         this.f328.addElement(c_aa);
         derivationnode.m39(c_aa);
         return c_aa;
      }
   }

   C_AA m589(int i, int j, Integer integer) {
      return integer == null ? null : this.m590(i, j, integer);
   }

   C_AA m590(int i, int j, int k) {
      return this.m588(i, j, this.f317.f915.problem.m32(k));
   }

   void m591(C_AA c_aa) {
      int i = this.f328.size();
      int j = 0;
      c_aa.f118.m40(c_aa);
      if (this.f317.f915.focus == this.f324) {
         this.f324.m1930(false);
      }

      for (int k = 0; k < i; k++) {
         C_AA c_aa1 = (C_AA)this.f328.elementAt(k);
         if (c_aa1 == c_aa) {
            String s = this.m9(false);
            String s1 = "<deleted>";
            this.f324.m1932(j + c_aa1.f116, c_aa1.f117, s1.length());
            this.m8(s.substring(0, j + c_aa1.f116) + s1 + s.substring(j + c_aa1.f116 + c_aa1.f117));
            this.f328.removeElementAt(k);
            i--;
            j = c_aa1.f116 + s1.length();
            if (k < i) {
               c_aa1 = (C_AA)this.f328.elementAt(k);
               c_aa1.f116 += j;
            }
            break;
         }

         j += c_aa1.f116 + c_aa1.f117;
      }

      if (this.f317.f915.focus == this.f324) {
         this.f324.m1931();
      }
   }

   void m592() {
      int i = this.f328.size();

      while (--i >= 0) {
         C_AA c_aa = (C_AA)this.f328.elementAt(i);
         c_aa.f118.m40(c_aa);
         this.f328.removeElementAt(i);
      }
   }

   @Override
   public void m39(C_AA c_aa) {
      this.f329.addElement(c_aa);
   }

   @Override
   public void m40(C_AA c_aa) {
      this.f329.removeElement(c_aa);
   }

   @Override
   public void m41() {
      while (!this.f329.isEmpty()) {
         C_AA c_aa = (C_AA)this.f329.firstElement();
         c_aa.f119.m591(c_aa);
      }
   }

   @Override
   public void m42() {
      Object object = this.f317.f917 == this ? this.f317 : this;
      Enumeration enumeration = this.f329.elements();

      while (enumeration.hasMoreElements()) {
         C_AA c_aa = (C_AA)enumeration.nextElement();
         c_aa.f118 = (DerivationNode)object;
      }
   }

   void m593() {
      if (this.f333 && this.f332 != null && this == this.f317.f917 && this.f317.f916 != null) {
         int i = this.f317.m16();

         for (DerivationBox derivationbox = this.f317.f916; derivationbox != null; derivationbox = derivationbox.f916) {
            while (--i != 0) {
               DerivationNode derivationnode = derivationbox.m1560(i);
               if (this.f332.m1235(derivationnode.m44())) {
                  Hashtable hashtable = Message.params("previous", "line " + derivationnode.m30());
                  this.m12("dernot053", hashtable);
                  return;
               }
            }

            i = derivationbox.m16();
         }

         Expression[] aexpression = this.f317.f915.premises;
         int j = aexpression == null ? 0 : aexpression.length;

         for (int k = 0; k < j; k++) {
            if (this.f332.m1235(aexpression[k])) {
               Hashtable hashtable1 = Message.params("previous", "premise " + (k + 1));
               this.m12("dernot053", hashtable1);
               return;
            }
         }
      }
   }

   @Override
   public boolean m43() {
      if (this.f317.f915.focus == this.f323) {
         this.m594();
      }

      return this.f333;
   }

   void m594() {
      int i = this.f317.f915.setPhase(1);

      try {
         this.f333 = true;
         if (this.f323 == null) {
            return;
         }

         this.m13();
         if (this.f317.f917 == this && this.f317.f916 == null) {
            this.f317.f915.parseProblem();
            return;
         }

         this.m564();

         try {
            this.f332 = LogicProgram.m1009(this.m7(true), false, false, this.f317.f917 == this);
         } catch (FormulaParseException formulaparseexception) {
            this.f332 = null;
            String s = formulaparseexception.getMessage();
            if (s == null) {
               this.m12("dererr059", Message.params("parser error", this.m7(true)));
            } else {
               this.m12("dererrtxt", Message.params("text", s));
            }

            this.f333 = false;
         }
      } finally {
         this.f317.f915.setPhase(i);
      }
   }

   @Override
   public Expression m44() {
      return this.f332;
   }

   @Override
   public boolean m46() {
      return this.m595(false);
   }

   boolean m595(boolean flag) {
      int i = this.f317.f915.setPhase(3);

      try {
         if (!this.f333) {
            this.f317.f922 = false;
            return false;
         } else {
            this.f334 = false;
            if (this.f317.f917 != this) {
               this.m557(4);
            }

            this.m13();
            if (this.f324 != null) {
               DerivationLineChecker derivationlinechecker = new DerivationLineChecker(this, flag);
               if (this.f323 != null && this.f332 == null) {
                  if (this.m9(true).trim().equals("")) {
                     if (!flag) {
                        this.m11("derinf003");
                        return true;
                     }

                     return true;
                  }

                  if (!flag) {
                     derivationlinechecker.getClass();
                     this.m11("derinf004");
                     return true;
                  }
               }

               while (derivationlinechecker.m1591()) {
                  if (derivationlinechecker.m1632() == null) {
                     this.m11("dererr049");
                     this.f317.f922 = false;
                     return false;
                  }

                  if (!derivationlinechecker.m1592()) {
                     if (this.f317.f915.serialMode) {
                        return derivationlinechecker.m1606(true) && this.m597();
                     }

                     if (flag) {
                        return derivationlinechecker.m1606(true);
                     }

                     if (!derivationlinechecker.m1607(false, true)) {
                        return false;
                     }

                     if (derivationlinechecker.f941 == null || derivationlinechecker.f941.m1235(derivationlinechecker.f942)) {
                        return true;
                     }

                     Hashtable hashtable = null;
                     if (derivationlinechecker.f955 != null) {
                        String s = "";
                        int j = derivationlinechecker.f955.size();

                        for (int k = 0; k < j; k++) {
                           s = s + (k == 0 ? "" : "\\n") + derivationlinechecker.f955.elementAt(k);
                        }

                        hashtable = Message.putParam(hashtable, "rule form premises", "\\l" + s + "\\l");
                     }

                     Hashtable hashtable1 = this.f317.f915.commandMode ? new Hashtable() : null;
                     if (hashtable1 != null) {
                        if (this.f336 != null) {
                           hashtable1.put("caches", this.f336);
                        }

                        hashtable1.put("just", derivationlinechecker);
                        if (derivationlinechecker.f942 != null) {
                           hashtable1.put("sum", derivationlinechecker.f942);
                           hashtable = Message.putParam(hashtable, "rule form conclusion", "\\l" + derivationlinechecker.f942 + "\\l");
                        }
                     }

                     String s1 = this.f317.f915.commandMode ? "dernot100" : "dernot101";
                     C_KB.m764(s1, hashtable, hashtable1, derivationlinechecker);
                     return false;
                  }

                  if (!this.f317.f915.queuedMode) {
                     this.m11("dererr050");
                     this.f317.f922 = false;
                     return false;
                  }

                  if (!derivationlinechecker.m1606(false)) {
                     return false;
                  }

                  if (this.f317.f915.aborted()) {
                     return false;
                  }

                  if (derivationlinechecker.f944) {
                     return true;
                  }

                  this.m596(derivationlinechecker.f942);
                  this.f334 = false;
               }

               return false;
            } else if (this.f332 != null) {
               return true;
            } else {
               this.m11("dererr048");
               this.f317.f922 = false;
               return false;
            }
         }
      } finally {
         if (this.f317.f917 != this) {
            this.m596(this.f332);
         }

         this.f317.f915.setPhase(i);
      }
   }

   void m596(Expression expression) {
      if (this.f317.f921 == null) {
         this.f317.f921 = new Vector();
      }

      if (this.f317.f915.varNames == null) {
         this.f317.f915.varNames = new Vector();
      }

      if (expression != null) {
         expression.m1253(this.f317.f915.varNames, this.f317.f921);
      }
   }

   boolean m597() {
      if (this.f324 != null && this.f317.f916 == null) {
         this.m549("dererr051", 4);
         this.f317.f915.complete = false;
         return false;
      } else {
         return true;
      }
   }

   String m598() {
      String s = "";
      if (this.f336 != null) {
         int i = this.f336.size();
         boolean flag = true;

         for (int j = 0; j < i; j++) {
            Justification justification = (Justification)this.f336.elementAt(j);
            if (justification != null) {
               flag = false;
            }

            s = s + TaggedRecord.m1508(justification == null ? "" : justification.m609(), ':');
         }

         if (flag) {
            s = "";
         }
      }

      return s;
   }

   @Override
   public String m47() {
      if (this.f317.f917 == this) {
         String s2 = TaggedRecord.m1508(this.m7(false), (char)(this.f317.m2124() ? '-' : '+'));
         String s1 = this.f340 == null ? null : LogicProgram.m995(this.f340.getText(), f341, rob);
         if (s1 != null && s1.length() != 0) {
            s2 = s2 + TaggedRecord.m1508(s1, 's');
         }

         return s2 + this.m598();
      } else {
         String s = LogicProgram.m995(this.m9(false), f341, rob);
         return this.f317.f918 == this
            ? TaggedRecord.m1508(s, '#') + this.m598()
            : TaggedRecord.m1508(this.m7(false), '<') + TaggedRecord.m1508(s, '>') + this.m598();
      }
   }

   @Override
   public String m48() {
      return this.f337 != null && this.f337.isError ? TaggedRecord.m1508(this.m30() + ":" + this.f339, 'm') : "";
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         LogicProgram.m1086(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
