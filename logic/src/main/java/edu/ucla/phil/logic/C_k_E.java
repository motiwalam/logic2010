package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.BorderFactory;

class C_k_E extends SizedPanel implements LogicConstants {
   LPTruthAnalysis f1200;
   C_XF f1201;
   C_B f1202;
   C_m_F f1203;
   C_ZE f1204;
   CellPanel f1205;
   CellPanel f1206;
   CellPanel f1207;
   C_K f1208;
   C_d_ f1209;
   C_RC f1210;
   String f1211;
   String f1212;
   static String[] f1213 = LogicProgram.symbols;
   C_HA f1214;
   int f1215;
   int f1216;
   int f1217;
   ArgumentParser f1218;
   boolean f1219;
   boolean f1220 = false;

   C_k_E(LPTruthAnalysis lptruthanalysis) {
      this.f1200 = lptruthanalysis;
      this.f1214 = null;
      this.f1216 = 0;
      this.f1217 = 0;
      this.f1211 = null;
      this.f1212 = null;
      this.setLayout(new BorderLayout());
      this.f1204 = new C_ZE("", 2);
      this.f1205 = new CellPanel();
      this.f1210 = new C_RC(this);
      this.add(this.f1207 = new CellPanel(), "West");
      this.f1207.setLayout(new BorderLayout());
      this.f1209 = new C_d_(this);
      this.f1201 = new C_XF(this);
      this.f1206 = new CellPanel();
      this.f1206.setBorder(BorderFactory.createEtchedBorder());
      this.f1206.setLayout(new C_m_A(17));
      this.f1206.add(this.f1202 = new C_B(this));
      this.f1206.add(this.f1208 = new C_K(this.f1202));
      this.m1908();
   }

   void m1908() {
      this.f1211 = null;
      this.f1200.titlePanel.m1821("Problem: ");
      this.f1212 = null;
      this.f1200.lastUserProblem = null;
      this.f1200.titlePanel.m1823("");
      this.f1209.m1673("Is this formula a tautology?");
      this.f1209.f1033.setVisible(true);
      this.f1205.removeAll();
      this.f1203 = null;
      this.m1909();
   }

   void m1909() {
      this.f1200.titlePanel.m1825("");
      this.f1202.m235(null, null);
      this.f1206.setVisible(false);
      this.f1215 = -1;
      this.f1201.f892 = -1;
      this.f1207.removeAll();
      if (this.f1200.completeSetup) {
         this.f1207.add(this.f1204, "North");
         this.f1207.add(this.f1205, "West");
         this.f1207.add(this.f1210, "South");
      } else {
         this.f1207.add(this.f1209, "North");
         this.f1207.add(this.f1201, "West");
         this.f1207.add(this.f1206, "Center");
      }

      this.f1207.revalidate();
      this.f1219 = false;
      if (this.f1203 != null) {
         this.f1203.m1952(null);
      }

      this.f1201.m1521(null);
   }

   void m1910(TaggedRecord taggedrecord) {
      this.m1908();
      this.m1913(taggedrecord.getName());
      this.f1212 = LPTruthAnalysis.getProblemStatement(taggedrecord);
      if (this.f1212 == null) {
         this.f1200.titlePanel.m1823("");
      } else {
         this.f1200.titlePanel.m1823(LogicProgram.m995(this.f1212, maggie, f1213));
      }

      this.f1200.titlePanel.f1131.validate();
      this.f1218 = new ArgumentParser(this.f1212);
      this.f1214 = new C_HA(this.f1218);
      this.f1216 = this.f1218.f827.length;
      this.f1217 = this.f1214.f379.size();
      this.f1200.assumeTautology = LPTruthAnalysis.assumeTautology(taggedrecord);
      if (this.f1200.assumeTautology) {
         this.f1209.m1673("Please complete a truth table for this formula.");
         this.f1209.f1033.setVisible(false);
      } else if (!this.f1218.f829) {
         this.f1209.m1673("Is this argument tautologically valid?");
      }

      Hashtable hashtable = new Hashtable();
      int[] aint = taggedrecord.m1477('@');
      int i = aint.length;

      for (int j = 0; j < i; j++) {
         String s = taggedrecord.valueAt(aint[j]);
         int k = s.indexOf(58);
         if (k != -1) {
            String s1 = s.substring(0, k);
            s = s.substring(k + 1);
            String[] astring = new String[this.f1216 + 1];

            for (int l = 0; l <= this.f1216; l++) {
               k = s.indexOf(46);
               astring[l] = k == -1 ? s : s.substring(0, k);
               s = k == -1 ? "" : s.substring(k + 1);
            }

            hashtable.put(s1.toUpperCase(), astring);
         }
      }

      if (!this.f1200.assumeTautology) {
         Integer integer = taggedrecord.m1485(taggedrecord.indexOfTag('*'));
         this.f1215 = integer == null ? -1 : integer;
         integer = taggedrecord.m1485(taggedrecord.indexOfTag('#'));
         this.f1201.f892 = integer == null ? -1 : integer;
      }

      String s2 = taggedrecord.valueAt(taggedrecord.indexOfTag('&'));
      if (s2 != null || this.f1200.completeSetup) {
         this.f1205.add(this.f1203 = new C_m_F(this));
         this.f1203.m1952(s2);
         if (this.f1219) {
            this.m1911();
         }
      }

      this.f1220 = true;
      this.f1201.m1521(hashtable);
      this.f1202.m235(null, null);
      this.f1220 = false;
   }

   void m1911() {
      this.f1201.m1520();
      this.f1207.removeAll();
      this.f1207.add(this.f1209, "North");
      this.f1207.add(this.f1201, "West");
      this.f1207.add(this.f1206, "Center");
      this.f1207.validate();
   }

   String m1912() {
      String s = "";
      s = s + TaggedRecord.m1508(this.f1211, '$');
      s = s + TaggedRecord.m1508(this.f1212, '=');
      Hashtable hashtable = this.f1201.m1524();

      for (int i = 0; i < this.f1201.f894; i++) {
         if (this.f1201.m1525(i)) {
            String s1 = C_XF.m1523(i, this.f1217);
            String[] astring = (String[])hashtable.get(s1);
            int j = astring == null ? 0 : astring.length;
            if (j != 0) {
               for (int k = 0; k < j; k++) {
                  s1 = s1 + (k == 0 ? ":" : ".") + astring[k];
               }

               s = s + TaggedRecord.m1508(s1, '@');
            }
         }
      }

      if (this.f1201.f892 != -1) {
         s = s + this.f1201.f892 + "`#";
      }

      if (this.f1200.assumeTautology) {
         s = s + "taut`%";
      } else if (this.f1215 != -1) {
         s = s + this.f1215 + "`*";
      }

      return s + TaggedRecord.m1508(this.f1203 == null ? null : this.f1203.m1953(), '&');
   }

   void m1913(String s) {
      this.f1211 = s;
      if (s != null && !(s = s.trim()).equals("")) {
         this.f1200.titlePanel.m1821(LPTruthAnalysis.trimTitle(s));
      } else {
         this.f1200.titlePanel.m1821(null);
      }
   }
}
