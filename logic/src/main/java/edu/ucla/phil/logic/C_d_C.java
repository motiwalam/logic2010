package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Box;

class C_d_C extends C_LB implements C_v_D {
   public static final int f1041 = 0;
   public static final int f1042 = 1;
   public static final int f1043 = 2;
   protected LPSymbolizer f1044;
   protected C_x_C f1045;
   protected Vector f1046;
   protected int[] f1047 = new int[]{24, 19, 23};
   protected int f1048;
   int f1049;
   int[] f1050;
   String f1051;
   String f1052;
   String f1053;
   String f1054;
   String f1055;
   Vector f1056;
   Vector f1057;
   boolean f1058;
   static String[] f1059 = LogicProgram.f596;

   C_d_C(LPSymbolizer lpsymbolizer, int i) {
      this.f1044 = lpsymbolizer;
      this.f1048 = 0;
      this.f1049 = connOutTypes[this.f1048];
      this.f1050 = (int[])connArgTypes[this.f1048].clone();
      this.f1051 = null;
      this.f1052 = null;
      this.f1055 = null;
      this.f1046 = new Vector(3);
      this.f1056 = null;
      this.f1057 = null;
      this.f1058 = true;
      this.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.gridwidth = 3;
      gridbagconstraints.anchor = 19;
      gridbagconstraints.weighty = 0.0;
      gridbagconstraints.insets = new Insets(1, 0, 0, 0);
      this.add(this.f1045 = new C_x_C(this, null, i), gridbagconstraints);
      if (this.m1688() == null) {
         GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
         gridbagconstraints1.gridx = 0;
         gridbagconstraints1.gridy = 2;
         gridbagconstraints1.gridwidth = 3;
         gridbagconstraints1.anchor = 10;
         gridbagconstraints1.weighty = 0.05;
         this.add(Box.createGlue(), gridbagconstraints1);
      }
   }

   C_d_C(LPSymbolizer lpsymbolizer) {
      this(lpsymbolizer, 0);
   }

   void m1680(String s) {
      String s1 = "";
      if (s != null) {
         while (true) {
            int i = s.indexOf(92);
            if (i == -1) {
               s1 = s1 + s;
               break;
            }

            s1 = s1 + s.substring(0, i);
            if (i + 1 < s.length()) {
               char c0 = s.charAt(i + 1);
               if (c0 == 'n') {
                  s1 = s1 + '\n';
               } else {
                  s1 = s1 + c0;
               }

               s = s.substring(i + 2);
            } else {
               s = "";
            }
         }
      }

      this.f1045.f1438.setText(C_x_C.m2166(s1));
   }

   boolean m1681() {
      return this.f1048 != 0 ? true : !this.m1682().equals(C_x_C.m2166(this.f1055));
   }

   String m1682() {
      String s = "";
      if (this.f1045 != null && this.f1045.f1438 != null) {
         String s1 = C_x_C.m2166(this.f1045.f1438.getText());

         while (true) {
            int i = s1.indexOf(92);
            int j = s1.indexOf(10);
            if (i == -1 && j == -1) {
               return s + s1;
            }

            if (i == -1 || j != -1 && i >= j) {
               s = s + s1.substring(0, j) + "\\n";
               s1 = s1.substring(j + 1);
            } else {
               s = s + s1.substring(0, i) + "\\\\";
               s1 = s1.substring(i + 1);
            }
         }
      } else {
         return "";
      }
   }

   String m1683() {
      String s = connSymbol[this.f1048];
      int i = "*@!%".indexOf(s);
      if (i != -1) {
         s = s + this.m1685().f836;
      }

      if (i == 0) {
         s = C_OA.m1139(s, "\\{") + C_e_.m1753(this.f1050) + this.f1049;
      }

      return s;
   }

   String m1684() {
      C_VE c_ve = this.m1685();
      return c_ve == null ? null : c_ve.f836;
   }

   C_VE m1685() {
      Enumeration enumeration = this.f1046.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof C_VE) {
            return (C_VE)component;
         }
      }

      return null;
   }

   C_d_C m1686(int i) {
      Enumeration enumeration = this.f1046.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof C_d_C) {
            if (i == 0) {
               return (C_d_C)component;
            }

            i--;
         }
      }

      return null;
   }

   int m1687(C_d_C c_d_c1) {
      int i = 0;
      Enumeration enumeration = this.f1046.elements();

      while (enumeration.hasMoreElements()) {
         Component component = (Component)enumeration.nextElement();
         if (component instanceof C_d_C) {
            if (component == c_d_c1) {
               return i;
            }

            i++;
         }
      }

      return -1;
   }

   C_d_C m1688() {
      Container container = this.getParent();
      return container instanceof C_d_C ? (C_d_C)container : null;
   }

   void m1689() {
      this.m1690(0, false);
      this.f1051 = null;
      this.f1052 = null;
      this.f1053 = null;
      this.f1054 = null;
      this.f1055 = null;
      this.f1056 = null;
      this.f1057 = null;
      this.f1058 = true;
      this.f1045.f1438.setText("");
      this.f1045.m2164();
   }

   synchronized C_d_C m1690(int i, boolean flag) {
      return this.m1691(i, null, flag);
   }

   synchronized C_d_C m1691(int i, String s, boolean flag) {
      int j;
      int[] aint;
      switch (i) {
         case 6:
         case 7:
         case 8:
            if (i != this.f1048 && s == null) {
               s = this.m1694();
            }

            if (s == null) {
               s = C_WB.m1452("", "Bound Variable:", this);
            }

            if (s == null || s.equals("") || this.f1048 == i && s.equals(this.m1684())) {
               return this;
            }

            s = s.trim();
            j = connOutTypes[i];
            aint = connArgTypes[i];
            break;
         case 9:
         case 10:
         default:
            if (this.f1048 == i) {
               return this;
            }

            j = connOutTypes[i];
            aint = connArgTypes[i];
            break;
         case 11:
            C_OA c_oa = new C_OA("\\{");
            if (s == null) {
               s = c_oa.m1137(C_WB.m1452("", "Atomic Expression:", this));
            }

            if (s != null) {
               s = s.trim();
            }

            if (s == null || s.equals("") || this.f1048 == i && s.equals(this.m1684())) {
               return this;
            }

            c_oa.m1132(s);
            s = c_oa.m1135().trim();
            j = 0;
            aint = null;
            boolean flag1 = false;
            if (c_oa.m1134() == '{') {
               String s1 = "{" + c_oa.m1133();
               int k = s1.indexOf(125);
               if (k != -1) {
                  try {
                     j = Integer.parseInt(s1.substring(k + 1));
                     aint = C_e_.m1755(s1.substring(0, k + 1));
                     if (aint != null) {
                        flag1 = true;
                     }
                  } catch (Exception exception) {
                  }
               }
            }

            if (!flag1) {
               try {
                  C_RF c_rf = LogicProgram.m1008(s, true, false);
                  j = c_rf instanceof C_X ? 1 : 0;
               } catch (C_k_B c_k_b) {
                  if (flag) {
                     C_WB.m1429("symnot010", C_H.m664(null, "source", s));
                  }

                  return null;
               }

               aint = connArgTypes[i];
            }
      }

      if (j != 2) {
         C_d_C c_d_c1 = this.m1688();
         if (c_d_c1 != null) {
            int[] aint1 = c_d_c1.f1050;
            int l = aint1.length;

            int j1;
            for (j1 = 0; j1 < l; j1++) {
               if (c_d_c1.m1686(j1) == this) {
                  if (aint1[j1] != j) {
                     String s2 = "Formula or term";
                     if (aint1[j1] < expTypes.length) {
                        s2 = expTypes[aint1[j1]];
                     }

                     if (flag) {
                        C_WB.m1429("symnot011", C_H.m664(null, "type", s2.toLowerCase()));
                     }

                     return null;
                  }
                  break;
               }
            }

            if (j1 == l) {
               C_WB.m1427("could not find child");
            }
         }
      }

      C_TF c_tf;
      if (this.f1050.length != 0 && aint.length != 0) {
         c_tf = new C_TF(this);
      } else {
         c_tf = null;
      }

      if (this.f1046 != null) {
         Enumeration enumeration = this.f1046.elements();

         while (enumeration.hasMoreElements()) {
            Component component = (Component)enumeration.nextElement();
            if (component != null) {
               this.remove(component);
            }
         }

         this.f1046.removeAllElements();
         this.f1046.setSize(3);
      }

      C_d_C c_d_c2 = this;
      this.f1049 = j;
      this.f1050 = aint;
      switch (this.f1048 = i) {
         case 0:
            this.f1045.m2164();
            c_d_c2 = this;
            break;
         case 1:
            this.m1692(new C_VE(this, this.f1048), 0);
            this.m1692(c_d_c2 = new C_d_C(this.f1044), 2);
            break;
         case 2:
         case 3:
         case 4:
         case 5:
            this.m1692(c_d_c2 = new C_d_C(this.f1044, 1), 0);
            this.m1692(new C_VE(this, this.f1048), 1);
            this.m1692(new C_d_C(this.f1044, -1), 2);
            break;
         case 6:
         case 7:
         case 8:
            this.m1692(new C_VE(this, this.f1048, s), 0);
            this.m1692(c_d_c2 = new C_d_C(this.f1044), 2);
            break;
         case 9:
         case 10:
            this.m1692(c_d_c2 = new C_d_C(this.f1044), 0);
            this.m1692(new C_VE(this, this.f1048), 1);
            this.m1692(new C_d_C(this.f1044), 2);
            break;
         case 11:
            this.m1692(Box.createGlue(), 0);
            this.m1692(new C_VE(this, this.f1048, s), 1);
            this.m1692(Box.createGlue(), 2);
            int i1 = this.f1050.length;
            if (i1 <= 1 && (!m1693(s) || i1 <= 0)) {
               boolean flag2 = false;
            } else {
               boolean flag3 = true;
            }

            c_d_c2 = i1 == 0 ? this : this.m1686(0);
            this.f1045.m2164();
      }

      this.revalidate();
      if (this.f1044 != null) {
         this.f1044.repaintTree();
      }

      if (c_tf != null) {
         c_tf.m1309(this);
      }

      if (c_tf != null) {
         c_d_c2 = null;
      }

      if (this.f1044 != null) {
         this.f1044.updateSymbolization();
      }

      return c_d_c2;
   }

   void m1692(Component component, int i) {
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = i;
      gridbagconstraints.gridy = 1;
      gridbagconstraints.anchor = this.f1047[i];
      gridbagconstraints.weightx = i == 1 ? 0.0 : 1.0;
      gridbagconstraints.weighty = 1.0;
      gridbagconstraints.insets = new Insets(1, 0, 0, 0);
      this.add(component, gridbagconstraints);
      this.f1046.setElementAt(component, i);
   }

   static boolean m1693(String s) {
      return s != null && s.length() != 0 ? "FGHIJKLMNO".indexOf(s.charAt(0)) == -1 : true;
   }

   String m1694() {
      int i = this.f1050.length;

      for (int j = 0; j < i; j++) {
         if (this.m1686(j).m1696()) {
            return null;
         }
      }

      String s1 = "xyzuvwlmnopqrst";
      C_d_C c_d_c1 = this;

      while ((c_d_c1 = c_d_c1.m1688()) != null) {
         if (c_d_c1.m1726()) {
            String s = c_d_c1.m1684();
            int k = s1.indexOf(s);
            if (k != -1) {
               s1 = s1.substring(0, k) + s1.substring(k + 1);
            }
         }
      }

      return s1.length() == 0 ? null : s1.substring(0, 1);
   }

   void m1695() {
      C_d_C c_d_c1 = this.f1044.problem;
      C_d_C c_d_c2 = c_d_c1.m1715();
      if (c_d_c2 != null) {
         c_d_c1.m1739();
         C_JA c_ja = new C_JA(this);
         c_d_c1.m1723(c_d_c2, new Vector(), new Vector(), c_ja);
         C_CD c_cd = c_ja.m721();
         if (c_cd == null) {
            C_f_A c_f_a = null;
            Vector vector = c_ja.m720();
            int i = vector.size();
            C_d_C c_d_c3 = this;

            while (c_f_a == null && (c_d_c3 = c_d_c3.m1688()) != null) {
               for (int j = 0; j < i; j++) {
                  C_f_A c_f_a1 = (C_f_A)vector.elementAt(j);
                  if (c_f_a1.f1106 == c_d_c3) {
                     c_f_a = c_f_a1;
                     break;
                  }
               }
            }

            if (c_f_a != null) {
               C_VE c_ve = c_f_a.f1106.m1685();
               if (c_ve != null) {
                  c_ve.add(c_f_a);
                  this.f1044.errorCount++;
                  c_ve.f839.setEnabled(true);
                  c_ve.revalidate();
               }
            }
         } else {
            c_cd.m426();
            this.f1044.hintCount++;
         }
      }
   }

   boolean m1696() {
      if (this.m1726()) {
         return true;
      } else {
         int i = this.f1050.length;

         for (int j = 0; j < i; j++) {
            if (this.m1686(j).m1696()) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void invalidate() {
      C_d_C c_d_c1 = this.m1688();
      if (c_d_c1 != null) {
         c_d_c1.invalidate();
      }

      super.invalidate();
   }

   int m1697() {
      return this.f1048;
   }

   C_RF m1698() {
      if (this.m1717()) {
         return null;
      } else {
         try {
            return LogicProgram.m1006(LogicProgram.m995(this.toString(), f1059, maggie));
         } catch (C_k_B c_k_b) {
            return null;
         }
      }
   }

   @Override
   public String toString() {
      Object object = LogicProgram.m995(connSymbol[this.f1048], maggie, f1059);
      String s = LogicProgram.m995(this.m1684(), maggie, f1059);
      switch (this.f1048) {
         case 0:
            return this.m1682();
         case 1:
            return object + this.m1686(0);
         case 2:
         case 3:
         case 4:
         case 5:
            return "(" + this.m1686(0) + " " + object + " " + this.m1686(1) + ")";
         case 6:
         case 7:
         case 8:
            return object + s + " " + this.m1686(0);
         case 9:
         case 10:
            return this.m1686(0) + " " + object + " " + this.m1686(1);
         case 11:
            Object object1 = s;
            int i = this.f1050.length;
            boolean flag = i > 1 || this.f1049 == 1 && i > 0;
            if (flag) {
               object1 = s + "(";
            }

            for (int j = 0; j < i; j++) {
               object1 = object1 + this.m1686(j);
            }

            if (flag) {
               object1 = object1 + ")";
            }

            return (String)object1;
         default:
            return "";
      }
   }

   String m1699(boolean flag) {
      String s = "";
      String s1 = this.m1682();
      if (flag) {
         s = s + C_XD.m1508(this.f1051, '$');
         s = s + C_XD.m1508(this.f1055, '-');
         s = s + C_XD.m1508(this.f1052, 'o');
         if (this.f1058 && this.f1052 == null) {
            s = s + C_XD.m1508(this.f1053, '=');
            s = s + C_XD.m1508(this.f1054, '@');
         }
      }

      if (!flag || this.f1048 != 0 || !s1.equals(C_x_C.m2166(this.f1055))) {
         s = s + C_XD.m1508(C_OA.m1139(this.m1683(), "\\:") + ":" + s1, '+');
      }

      int i = connArgTypes[this.f1048].length;

      for (int j = 0; j < i; j++) {
         s = s + this.m1686(j).m1699(false);
      }

      return s;
   }

   void m1700() {
      if (this.f1052 != null && this.f1056 != null) {
         String[] astring = new String[this.f1056.size()];
         this.f1056.copyInto(astring);
         this.f1056 = null;
         this.f1054 = null;
         int i = astring.length;

         for (int j = 0; j < i; j++) {
            this.m1701(astring[j]);
         }

         this.f1052 = null;
         this.f1058 = true;
         LPSymbolizer.writeUserKey();
         String s = this.f1044.getChangedProblem();
         if (s == null) {
            LPSymbolizer.saveProblems(this.f1044.problemIndex);
         } else {
            this.f1044.saveProblems(s);
         }
      }
   }

   void m1701(String s) {
      int i = 1;

      String s1;
      while (LPSymbolizer.userKey.get(s1 = this.f1051 + "-" + i) != null) {
         i++;
      }

      s = C_XD.m1495(s, s1);
      LPSymbolizer.userKey.put(s1, s);
      if (this.f1054 != null && this.f1054 != "") {
         this.f1054 = this.f1054 + "." + C_OA.m1139(s1, "\\.");
      } else {
         this.f1054 = C_OA.m1139(s1, "\\.");
      }

      if (this.f1056 == null) {
         this.f1056 = new Vector();
      }

      this.f1056.addElement(s);
   }

   void m1702() {
      if (this.f1054 != null && this.f1058 && this.f1052 == null) {
         C_OA c_oa = new C_OA("\\.");
         c_oa.m1132(this.f1054);

         while (true) {
            String s = c_oa.m1135();
            if (s == null) {
               this.f1054 = null;
               break;
            }

            if (!s.equals("")) {
               LPSymbolizer.userKey.remove(s);
            }
         }
      }
   }

   void m1703(C_XD c_xd) {
      this.m1704(c_xd, true);
   }

   void m1704(C_XD c_xd, boolean flag) {
      this.m1705(c_xd, flag, true);
   }

   void m1705(C_XD c_xd, boolean flag, boolean flag1) {
      if (flag1) {
         this.m1689();
      }

      this.f1055 = LPSymbolizer.getProblemStatement(c_xd);
      int[] aint = c_xd.m1477('+');
      if (flag) {
         if (aint.length == 0 && this.f1055 != null) {
            int i = c_xd.m1482();
            c_xd.m1491('+', C_OA.m1139(connSymbol[0], "\\:") + ":" + this.f1055, i);
            aint = new int[]{i};
         }

         this.f1051 = c_xd.m1494();
         this.f1052 = c_xd.m1483(c_xd.m1475('o'));
      }

      this.m1706(aint, 0, c_xd);
      if (flag && (LPSymbolizer.exercises != null || LPSymbolizer.problems != null) && (this.f1051 != null || this.f1052 != null)) {
         String s2 = null;
         Hashtable hashtable = null;
         if (LPSymbolizer.exercises != null) {
            s2 = LPSymbolizer.exercises.m1780(this.f1052 == null ? this.f1051 : this.f1052);
            hashtable = LPSymbolizer.exercises.f1158;
            if (s2 != null) {
               this.f1058 = false;
            }
         }

         if (s2 == null && LPSymbolizer.problems != null) {
            s2 = LPSymbolizer.problems.m1780(this.f1052 == null ? this.f1051 : this.f1052);
            hashtable = LPSymbolizer.problems.f1158;
            if (s2 == null) {
               s2 = c_xd.toString();
            }
         }

         if (s2 != null) {
            c_xd = new C_XD(s2);
            String s;
            if (this.f1052 == null || (s = LPSymbolizer.getProblemStatement(c_xd)) != null && s.equals(this.f1055)) {
               this.f1053 = c_xd.m1483(c_xd.m1475('='));
               this.f1054 = c_xd.m1483(c_xd.m1475('@'));
               this.f1056 = m1708(this.f1054, this.f1058);
               if (hashtable != null) {
                  String s1 = c_xd.m1483(c_xd.m1475('g'));
                  if (s1 != null) {
                     this.f1057 = (Vector)hashtable.get(s1);
                  }
               }
            } else {
               this.f1052 = null;
            }
         }
      }
   }

   int m1706(int[] aint, int i, C_XD c_xd) {
      if (aint != null && aint.length != 0) {
         String s1 = null;
         this.f1049 = 0;

         String s;
         try {
            s = c_xd.m1483(aint[i]);
            i++;
         } catch (IndexOutOfBoundsException indexoutofboundsexception) {
            System.out.println("invalid symbolization node index: " + i);
            return -1;
         }

         C_OA c_oa = new C_OA("\\:");
         c_oa.m1132(s);
         String s2 = c_oa.m1135().trim();
         if (s2 == null) {
            System.out.println("invalid symbolization node: " + s);
            return -1;
         } else if (s2.equals("")) {
            System.out.println("blank symbolization connective");
            return -1;
         } else {
            if ("*@!%".indexOf(s2.charAt(0)) != -1) {
               s1 = s2.substring(1).trim();
               s2 = s2.substring(0, 1);
            }

            int j = LogicProgram.m1051(connSymbol, s2);
            if (j == -1) {
               System.out.println("unknown symbolization connective: " + s2);
               return -1;
            } else {
               this.m1691(j, s1, false);
               this.m1680(c_oa.m1133());
               this.f1045.m2164();
               int k = connArgTypes[j].length;

               for (int l = 0; l < k; l++) {
                  C_d_C c_d_c1 = this.m1686(l);
                  if (c_d_c1 == null) {
                     return -1;
                  }

                  i = c_d_c1.m1706(aint, i, c_xd);
                  if (i == -1) {
                     break;
                  }
               }

               return i;
            }
         }
      } else {
         return -1;
      }
   }

   static Vector m1707(C_XD c_xd, boolean flag) {
      String s = c_xd.m1483(c_xd.m1475('@'));
      return m1708(s, flag);
   }

   static Vector m1708(String s, boolean flag) {
      if (s == null) {
         return null;
      } else {
         Hashtable hashtable = flag ? LPSymbolizer.userKey : LPSymbolizer.answers;
         if (hashtable == null) {
            return null;
         } else {
            C_OA c_oa = new C_OA("\\.");
            c_oa.m1132(s);
            Vector vector = new Vector();

            while (true) {
               String s1 = c_oa.m1135();
               if (s1 == null) {
                  return vector;
               }

               if (!s1.equals("")) {
                  String s2 = (String)hashtable.get(s1);
                  if (s2 != null) {
                     vector.addElement(s2);
                  }
               }
            }
         }
      }
   }

   void m1709(String s) {
      C_RF c_rf;
      try {
         c_rf = LogicProgram.m1006(s);
      } catch (C_k_B c_k_b) {
         String s1 = "\\l" + s + "\\l is not a well formed expression";
         C_UA.m1328("Badly Formed Expression", s1, null, null);
         return;
      }

      this.m1690(0, false);
      this.m1710(c_rf);
      this.f1045.requestFocus();
   }

   void m1710(C_RF c_rf) {
      if (c_rf != null) {
         String s = null;
         int i = c_rf.f741;
         int j = LogicProgram.m1051(connSymbol, c_rf.f739);
         if (m1727(j)) {
            this.m1691(j, c_rf.m1217(0).f739, true);
            this.m1686(0).m1710(c_rf.m1217(1));
         } else {
            if (j == -1) {
               j = 11;
               s = C_OA.m1139(c_rf.toString(), "\\{");
               i = 0;
            }

            this.m1691(j, s, true);

            for (int k = 0; k < i; k++) {
               this.m1686(k).m1710(c_rf.m1217(k));
            }
         }
      }
   }

   Vector m1711() {
      if (this.f1057 != null) {
         return this.f1057;
      } else {
         Vector vector = new Vector();
         vector.addElement(new C_WD(this.f1051, this.f1056));
         return vector;
      }
   }

   int m1712() {
      Vector vector = this.m1711();
      int i = vector.size();
      if (i != 0 && !this.m1717()) {
         for (int j = 0; j < i; j++) {
            C_WD c_wd = (C_WD)vector.elementAt(j);
            int k = c_wd.f861 == null ? 0 : c_wd.f861.size();

            for (int l = 0; l < k; l++) {
               C_d_C c_d_c1 = new C_d_C(null);
               c_d_c1.m1703(new C_XD((String)c_wd.f861.elementAt(l)));
               if (c_d_c1.m1717()) {
                  System.out.println(c_d_c1.f1051 + " is incomplete.");
               } else if (this.m1722(c_d_c1, null)) {
                  return j;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int m1713() {
      Vector vector = this.m1711();
      int i = vector.size();
      C_RF c_rf = this.m1698();
      if (i != 0 && c_rf != null) {
         for (int j = 0; j < i; j++) {
            C_WD c_wd = (C_WD)vector.elementAt(j);
            int k = c_wd.f861 == null ? 0 : c_wd.f861.size();

            for (int l = 0; l < k; l++) {
               C_d_C c_d_c1 = new C_d_C(null);
               c_d_c1.m1703(new C_XD((String)c_wd.f861.elementAt(l)));
               if (c_d_c1.m1717()) {
                  System.out.println(c_d_c1.f1051 + " is incomplete.");
               } else {
                  C_RF c_rf1 = c_d_c1.m1698();
                  if (c_rf1 == null) {
                     System.out.println(c_d_c1.f1051 + " could not be parsed.");
                  } else if (m1716(c_rf, c_rf1)) {
                     return j;
                  }
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int m1714() {
      Vector vector = this.m1711();
      int i = vector.size();
      int j = 0;

      for (int k = 0; k < i; k++) {
         if (this.m1720(k, LPSymbolizer.problems) == -1) {
            C_WD c_wd = (C_WD)vector.elementAt(k);
            j += c_wd.f861 == null ? 0 : c_wd.f861.size();
         }
      }

      return j;
   }

   C_d_C m1715() {
      Vector vector = this.m1711();
      int i = vector.size();
      C_d_C c_d_c1 = null;
      int j = 0;

      for (int k = 0; k < i; k++) {
         if (this.m1720(k, LPSymbolizer.problems) == -1) {
            C_WD c_wd = (C_WD)vector.elementAt(k);
            int l = c_wd.f861 == null ? 0 : c_wd.f861.size();

            for (int i1 = 0; i1 < l; i1++) {
               C_d_C c_d_c2 = new C_d_C(null);
               c_d_c2.m1703(new C_XD((String)c_wd.f861.elementAt(i1)));
               int j1 = this.m1721(c_d_c2);
               if (c_d_c1 == null || j1 > j) {
                  c_d_c1 = c_d_c2;
                  j = j1;
               }
            }
         }
      }

      return c_d_c1;
   }

   static boolean m1716(C_RF c_rf, C_RF c_rf1) {
      if (c_rf instanceof C_y_A && c_rf1 instanceof C_y_A) {
         C_q_F c_q_f = new C_q_F("<->");
         c_q_f.m1215(c_rf);
         c_q_f.m1215(c_rf1);
         return new C_HA(c_q_f.m1248()).m680();
      } else {
         return false;
      }
   }

   boolean m1717() {
      if (this.f1048 == 0) {
         return true;
      } else {
         int i = this.f1050.length;

         for (int j = 0; j < i; j++) {
            if (this.m1686(j).m1717()) {
               return true;
            }
         }

         return false;
      }
   }

   static String m1718(int i, Vector vector) {
      return vector != null && i >= 0 && i < vector.size() ? ((C_WD)vector.elementAt(i)).f860 : null;
   }

   static int m1719(String s, Vector vector) {
      if (s != null && vector != null) {
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            if (s.equals(((C_WD)vector.elementAt(j)).f860)) {
               return j;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int m1720(int i, C_h_C c_h_c) {
      int j = m1719(this.f1051, this.f1057);
      if (c_h_c != null && j != -1) {
         int k = this.f1057.size();

         for (int l = 0; l < k; l++) {
            if (l != j) {
               C__C c__c = (C__C)c_h_c.m1772(((C_WD)this.f1057.elementAt(l)).f860);
               if (c__c != null && c__c.f1120 == 2 && c__c.f927 == i) {
                  return l;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   int m1721(C_d_C c_d_c1) {
      C_0F c_0f = new C_0F();
      this.m1723(c_d_c1, new Vector(), new Vector(), c_0f);
      return c_0f.m67();
   }

   boolean m1722(C_d_C c_d_c1, C_i_D c_i_d) {
      return this.m1723(c_d_c1, new Vector(), new Vector(), c_i_d);
   }

   boolean m1723(C_d_C c_d_c1, Vector vector, Vector vector1, C_i_D c_i_d) {
      if ((this.f1048 == 0 || this.f1048 == c_d_c1.f1048) && (this.f1048 != 11 || this.m1724(c_d_c1, vector, vector1))) {
         if (c_i_d != null) {
            c_i_d.m68(this, c_d_c1, vector, vector1);
         }

         if (this.m1726()) {
            vector.addElement(this.m1685().f836);
            vector1.addElement(c_d_c1.m1685().f836);
         }

         int i = this.f1050.length;
         boolean flag = true;

         for (int j = 0; j < i; j++) {
            if (!this.m1686(j).m1723(c_d_c1.m1686(j), vector, vector1, c_i_d)) {
               if (c_i_d == null) {
                  return false;
               }

               flag = false;
            }
         }

         if (this.m1726()) {
            vector.setSize(vector.size() - 1);
            vector1.setSize(vector1.size() - 1);
         }

         return flag;
      } else {
         if (c_i_d != null) {
            c_i_d.m69(this, c_d_c1, vector, vector1);
         }

         return false;
      }
   }

   boolean m1724(C_d_C c_d_c1, Vector vector, Vector vector1) {
      if (this.f1049 != c_d_c1.f1049) {
         return false;
      } else {
         int i = this.f1050.length;
         if (c_d_c1.f1050.length != i) {
            return false;
         } else {
            for (int j = 0; j < i; j++) {
               if (this.f1050[j] != c_d_c1.f1050[j]) {
                  return false;
               }
            }

            String s1 = this.m1684();
            String s = c_d_c1.m1684();
            return this.m1737(s1, s, vector, vector1);
         }
      }
   }

   boolean m1725() {
      if (this.f1048 == 11 && this.f1049 == 1 && this.f1050.length == 0) {
         String s = this.m1684();
         return s != null && s.length() != 0 ? s.equals(s.toLowerCase()) : false;
      } else {
         return false;
      }
   }

   boolean m1726() {
      return m1727(this.f1048);
   }

   static boolean m1727(int i) {
      return i == 6 || i == 7 || i == 8;
   }

   String m1728() {
      return this.m1729(null, null);
   }

   String m1729(Vector vector, Vector vector1) {
      if (this.f1048 == 11) {
         String s = this.m1684();
         return "the atomic expression \\l" + m1732(s, vector, vector1) + "\\l";
      } else {
         return connWords[this.f1048];
      }
   }

   static String m1730(String s, Vector vector, Vector vector1) {
      return m1731(s, vector, vector1, null);
   }

   static String m1731(String s, Vector vector, Vector vector1, C_e_ c_e_) {
      String s1 = s;
      int i = vector == null ? -1 : vector.lastIndexOf(s);
      if (i != -1) {
         if (vector1 == null || vector.size() != vector1.size()) {
            return null;
         }

         s1 = (String)vector1.elementAt(i);
      }

      if (vector1 != null && vector1.lastIndexOf(s1) != i) {
         if (c_e_ != null) {
            c_e_.m1749(vector1.lastIndexOf(s1));
         }

         return null;
      } else {
         return s1;
      }
   }

   static String m1732(String s, Vector vector, Vector vector1) {
      return m1733(s, vector, vector1, null);
   }

   static String m1733(String s, Vector vector, Vector vector1, C_e_ c_e_) {
      C_RF c_rf;
      try {
         c_rf = LogicProgram.m1008(s, true, false);
      } catch (C_k_B c_k_b) {
         return s;
      }

      c_rf = m1734(c_rf, vector, vector1, c_e_);
      return c_rf == null ? null : c_rf.toString();
   }

   static C_RF m1734(C_RF object, Vector vector, Vector vector1, C_e_ c_e_) {
      if (object == null) {
         return null;
      } else {
         int j = vector == null ? 0 : vector.size();
         if (vector1 == null ? j == 0 : vector1.size() == j) {
            for (int i = j - 1; i >= 0; i--) {
               C_o_A c_o_a = new C_o_A("@");
               c_o_a.m1215(new C_i_((String)vector.elementAt(i)));
               c_o_a.m1215((C_RF)object);
               object = c_o_a;
            }

            ((C_RF)object).m1257();
            ((C_RF)object).m1241(vector1);
            if (m1735((C_RF)object, c_e_)) {
               return null;
            } else {
               for (int k = 0; k < j; k++) {
                  object = ((C_RF)object).m1217(1);
               }

               return ((C_RF)object).m1237();
            }
         } else {
            return null;
         }
      }
   }

   static boolean m1735(C_RF c_rf, C_e_ c_e_) {
      Vector vector = c_rf.m1259();
      if (vector == null) {
         return false;
      } else {
         if (c_e_ != null) {
            int i = vector.size();

            for (int j = 0; j < i; j++) {
               C_RF c_rf1 = c_rf;
               C_RF c_rf2 = ((C_RF[])vector.elementAt(j))[0];

               for (int k = 0; c_rf1 instanceof C_o_A; c_rf1 = c_rf1.m1217(1)) {
                  if (c_rf1 == c_rf2) {
                     c_e_.m1749(k);
                     break;
                  }

                  k++;
               }
            }
         }

         return true;
      }
   }

   static boolean m1736(String s, String s1, Vector vector, Vector vector1) {
      int i = vector1.lastIndexOf(s1);
      int j = vector.lastIndexOf(s);
      return i == j && (i != -1 || s1.equals(s));
   }

   boolean m1737(String s, String s1, Vector vector, Vector vector1) {
      C_RF c_rf;
      C_RF c_rf1;
      try {
         c_rf = LogicProgram.m1007(s, true);
         c_rf1 = LogicProgram.m1007(s1, true);
      } catch (C_k_B c_k_b) {
         c_rf = null;
         c_rf1 = null;
      }

      c_rf1 = m1734(c_rf1, vector1, vector, null);
      return c_rf1 == null ? s.equals(s1) : c_rf1.m1236(c_rf, new C_MB());
   }

   static boolean m1738(String s, Vector vector, Vector vector1) {
      return m1730(s, vector, vector1) != null;
   }

   void m1739() {
      C_VE c_ve = this.m1685();
      if (c_ve != null) {
         c_ve.m1394();
      }

      int i = this.f1050.length;

      for (int j = 0; j < i; j++) {
         this.m1686(j).m1739();
      }
   }

   static C__C m1740(String s, C_h_C c_h_c, C__C c__c) {
      if (c__c == null) {
         c__c = new C__C(s, c_h_c, true);
      }

      new C_d_C(null).m1741(new C_XD(s), c_h_c, c__c);
      return c__c;
   }

   void m1741(C_XD c_xd, C_h_C c_h_c, C__C c__c) {
      if (c__c != null) {
         if (!LPSymbolizer.hasWork(c_xd)) {
            c__c.m1582();
         } else {
            this.m1703(c_xd);
            if (this.m1717()) {
               c__c.f927 = -1;
            } else if ((c__c.f927 = this.m1712()) == -1 && LPSymbolizer.equivalentCounts(this.f1051)) {
               c__c.f927 = this.m1713();
            }

            if (c__c.f927 != -1 && this.m1720(c__c.f927, c_h_c) == -1) {
               c__c.f1120 = 2;
            } else {
               c__c.f1120 = 1;
            }
         }
      }
   }

   void m1742(C_d_C c_d_c1) {
      if (c_d_c1 != null) {
         this.m1743(c_d_c1, new Vector(), new Vector());
      }
   }

   void m1743(C_d_C c_d_c1, Vector vector, Vector vector1) {
      if (this.f1048 == c_d_c1.f1048) {
         if (this.m1726()) {
            vector.addElement(this.m1685().f836);
            vector1.addElement(c_d_c1.m1685().f836);
         }

         int i = this.f1050.length;

         for (int j = 0; j < i; j++) {
            C_d_C c_d_c2 = this.m1686(j);
            C_d_C c_d_c3 = c_d_c1.m1686(j);
            c_d_c2.f1045.f1438.setText(C_f_A.m1807(c_d_c3.f1045.f1438.getText(), vector1, vector));
            c_d_c2.validate();
            c_d_c2.m1743(c_d_c3, vector, vector1);
         }

         if (this.m1726()) {
            vector.setSize(vector.size() - 1);
            vector1.setSize(vector1.size() - 1);
         }
      }
   }
}
