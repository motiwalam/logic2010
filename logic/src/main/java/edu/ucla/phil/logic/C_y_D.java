package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

class C_y_D extends SizedPanel implements LogicConstants {
   LPParsing f1455;
   C_f_D f1456;
   C_EB f1457;
   String f1458;
   String f1459;
   static String[] f1460 = LogicProgram.symbols;

   C_y_D(LPParsing lpparsing) {
      this.f1455 = lpparsing;
      this.setLayout(new C_m_A());
      SizedPanel sizedpanel;
      this.add(sizedpanel = new SizedPanel());
      sizedpanel.setLayout(new FlowLayout());
      this.f1458 = null;
      this.f1459 = null;
      SizedPanel sizedpanel1;
      this.add(sizedpanel1 = new SizedPanel());
      sizedpanel1.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.anchor = 23;
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.weighty = 1.0;
      sizedpanel1.add(this.f1456 = new C_f_D(this), gridbagconstraints);
      gridbagconstraints.gridx = 1;
      gridbagconstraints.weightx = 1.0;
      sizedpanel1.add(this.f1457 = new C_EB(lpparsing), gridbagconstraints);
      this.f1457.setVisible(false);
   }

   void m2188() {
      this.f1458 = null;
      this.f1455.titlePanel.m1821("Problem: ");
      this.f1459 = null;
      this.f1455.titlePanel.m1823("");
      this.f1455.lastUserProblem = null;
      this.f1455.loadTime = 0L;
      this.f1457.f292.m227("");
      this.m2189();
   }

   void m2189() {
      this.m2190(true);
   }

   void m2190(boolean flag) {
      this.f1455.titlePanel.m1825("");
      if (flag) {
         int i = this.f1456.m1809();
         if (i != -1) {
            this.f1456.f1114[i].setSelected(false);
         }

         this.f1456.m1808(" ");
      }

      this.f1457.setVisible(!this.f1455.checkNow);
      this.f1457.f292.m229(false);
      this.f1457.m494();
      this.f1457.f292.f142.m1902();
      this.validate();
   }

   boolean m2191() {
      return this.m2192().f427 == null;
   }

   ErrorRef m2192() {
      String s = null;
      String s1 = "Correct";
      int i = this.f1456.m1809();
      C_DD c_dd = new C_DD(this.f1459);
      String s2 = c_dd.m464();
      if (i == -1) {
         s = "parerr001";
         s1 = "Incomplete";
      } else if (C_f_D.m1812(s2) != i) {
         s = "parerr002";
         s1 = "Incorrect";
      }

      if (!this.f1455.checkDisabled) {
         this.f1456.m1808(s1);
      }

      if (i != 2) {
         if (!this.f1457.m495()) {
            if (!this.f1455.noDescent) {
               if (s == null) {
                  s = "parerr003";
                  s1 = "Incomplete";
               }

               if (!this.f1455.checkDisabled) {
                  this.f1457.f291.setText("Incomplete");
               }
            } else {
               int[] aint = this.f1457.f292.f142.f1195;
               if (aint != null && aint.length != 0) {
                  if (!"parerr002".equalsIgnoreCase(s)) {
                     s = "parerr004";
                     s1 = "Incorrect";
                  }

                  if (!this.f1455.checkDisabled) {
                     this.f1457.f291.setText("Incorrect");
                  }
               } else {
                  if (s == null) {
                     s = "parerr003";
                     s1 = "Incomplete";
                  }

                  if (!this.f1455.checkDisabled) {
                     this.f1457.f291.setText("Incomplete");
                  }
               }
            }
         } else if (!this.f1455.checkDisabled) {
            this.f1457.f291.setText(this.f1455.noDescent ? "Correct" : "Complete");
         }
      }

      return new ErrorRef(s, Message.params("summary", s1));
   }

   void m2193(TaggedRecord taggedrecord) {
      this.m2188();
      this.f1458 = taggedrecord.getName();
      if (this.f1458 != null && !this.f1458.trim().equals("")) {
         this.f1455.titlePanel.m1821(LPParsing.trimTitle(this.f1458));
      } else {
         this.f1455.titlePanel.m1821(null);
      }

      this.f1459 = LPParsing.getProblemStatement(taggedrecord);
      if (this.f1459 == null) {
         this.f1455.titlePanel.m1823("");
      } else {
         this.f1455.titlePanel.m1823(LogicProgram.m995(this.f1459, maggie, f1460));
      }

      String s = taggedrecord.valueAt(taggedrecord.indexOfTag(']'));
      this.f1457.f292.m227(this.f1459);
      this.f1457.f292.m230(s);
      s = taggedrecord.valueAt(taggedrecord.indexOfTag('['));
      int i = C_f_D.m1812(s);
      if (i >= 0 && i < this.f1456.f1114.length) {
         this.f1456.f1114[i].setSelected(true);
      }

      if ((s = taggedrecord.valueAt(taggedrecord.indexOfTag('*'))) != null) {
         this.f1455.noDescent = true;
         this.f1457.f292.f142.f1198 = s.charAt(0) == 'T';
         Vector vector = new Vector();
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\,");
         delimitedtokenizer.m1132(s.substring(1));

         Integer integer;
         while ((integer = LogicProgram.parseInteger(delimitedtokenizer.m1135())) != null) {
            vector.add(integer);
         }

         int j = vector.size();
         if (j == 0) {
            this.f1457.f292.f142.f1195 = null;
         } else {
            int[] aint = new int[j];

            for (int k = 0; k < j; k++) {
               aint[k] = (Integer)vector.elementAt(k);
            }

            this.f1457.f292.f142.m1901(this.f1457.f292.f142.f1195 = aint);
            this.repaint();
         }
      }
   }

   String m2194() {
      String s = "";
      s = s + TaggedRecord.m1508(this.f1458, '$');
      s = s + TaggedRecord.m1508(this.f1459, '=');
      int i = this.f1456.m1809();
      if (i != -1) {
         s = s + TaggedRecord.m1508(C_f_D.m1810(i), '[');
      }

      String s1 = this.f1457.f292.m233(false);
      if (!s1.equals("0")) {
         s = s + TaggedRecord.m1508(s1, ']');
      }

      if (this.f1455.noDescent) {
         String s2 = this.f1457.f292.f142.f1198 ? "T" : "F";
         int[] aint = this.f1457.f292.f142.f1195;
         if (aint != null && aint.length != 0) {
            int j = aint.length;

            for (int k = 0; k < j; k++) {
               s2 = s2 + (k == 0 ? "" : ",") + aint[k];
            }

            s = s + TaggedRecord.m1508(s2, '*');
         }
      }

      return s;
   }
}
