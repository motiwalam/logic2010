package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.util.Hashtable;
import java.util.Vector;

class C_AB extends SizedPanel implements LogicConstants {
   static String[] f120 = LogicProgram.symbols;
   LPRecognition f121;
   ArgumentParser f122;
   String f123;
   String f124;
   Vector f125;
   Vector f126;
   String f127;
   String f128;
   SizedPanel f129;
   SizedPanel f130;
   SizedPanel f131;
   SizedPanel f132;
   SizedPanel f133;
   SizedPanel f134;
   SizedPanel f135;
   C_ZE f136;
   C_ZE f137;
   C_s_B f138;
   C_YC f139;

   C_AB(LPRecognition lprecognition) {
      this.f121 = lprecognition;
      this.f129 = new SizedPanel();
      this.f129.add(this.f130 = new SizedPanel(), "North");
      this.f130.setLayout(new C_m_A());
      this.f129.add(new C_CC(5, 1, false, lprecognition.colors[0]), "Center");
      this.f129.add(this.f131 = new SizedPanel(), "South");
      this.f131.setLayout(new C_m_A());
      this.f131.add(this.f136 = new C_ZE());
      this.f132 = new SizedPanel();
      this.f132.setLayout(new C_m_A(4));
      this.f132.add(new C_ZE(LogicProgram.m1004(this.m213())));
      this.f132.add(this.f139 = new C_YC(lprecognition));
      this.f139.setForeground(lprecognition.colors[1]);
      this.f139.setBackground(lprecognition.colors[0]);
      this.f139.m1787(false);
      this.f139.m1789(false);
      this.f139.f1329 = lprecognition.fontSize * 3;
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new FlowLayout(0, 0, 0));
      sizedpanel.add(this.f129);
      sizedpanel.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]));
      sizedpanel.add(this.f132);
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.add(new C_CC(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      sizedpanel1.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      sizedpanel1.add(sizedpanel, "Center");
      this.add(sizedpanel1, "North");
      this.f133 = new SizedPanel();
      this.f133.add(this.f137 = new C_ZE(), "West");
      this.f133.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "Center");
      SizedPanel sizedpanel2 = new SizedPanel();
      sizedpanel2.add(this.f133, "North");
      this.f134 = new SizedPanel();
      this.f134.add(this.f138 = new C_s_B());
      SizedPanel sizedpanel3 = new SizedPanel();
      sizedpanel3.add(sizedpanel2, "West");
      sizedpanel3.add(this.f134, "Center");
      SizedPanel sizedpanel4 = new SizedPanel();
      sizedpanel4.add(new C_CC(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      sizedpanel4.add(new C_CC(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      sizedpanel4.add(sizedpanel3, "Center");
      this.add(this.f135 = sizedpanel4, "Center");
      this.m214();
   }

   String m213() {
      Message message = C_BF.get("recnot005");
      String s = message == null ? null : message.text;
      if (s == null) {
         s = "Please enter the rule that applies, or enter \"None\".";
      }

      return s;
   }

   void m214() {
      this.f122 = null;
      this.f123 = null;
      this.f124 = null;
      this.f125 = null;
      this.f126 = null;
      this.f127 = null;
      this.f128 = null;
      this.f130.removeAll();
      this.f136.setText("");
      this.f139.setText("");
      this.f137.setText("");
      this.f138.setText("");
   }

   void m215(TaggedRecord taggedrecord) {
      this.m214();
      this.f121.setProblemTitle(taggedrecord.getName());
      this.f124 = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
      this.f121.titlePanel.m1823(LogicProgram.m995(this.f124, maggie, f120));
      this.f122 = ArgumentParser.m1382(this.f124);
      this.f128 = taggedrecord.valueAt(taggedrecord.indexOfTag('*'));
      String s = LPRecognition.exercises == null ? null : LPRecognition.exercises.m1780(this.f123);
      taggedrecord = new TaggedRecord(s);
      this.f125 = m216(taggedrecord.valueAt(taggedrecord.indexOfTag('@')));
      this.f126 = m216(taggedrecord.valueAt(taggedrecord.indexOfTag('~')));
      this.f127 = taggedrecord.valueAt(taggedrecord.indexOfTag('&'));
      if (this.f122 != null) {
         int i = this.f122.f827 == null ? 0 : this.f122.f827.length;

         for (int j = 0; j < i; j++) {
            C_ZE c_ze = new C_ZE(LogicProgram.m995(this.f122.f825[j], maggie, f120));
            this.f130.add(c_ze);
            c_ze.setForeground(this.f121.colors[0]);
         }

         this.f136.setText(LogicProgram.m995(this.f122.f826, maggie, f120));
         this.f136.setForeground(this.f121.colors[0]);
      }

      if (this.f128 != null) {
         this.f139.setText(this.f128);
      }
   }

   static Vector m216(String s) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
         delimitedtokenizer.m1132(s);

         String s1;
         while ((s1 = delimitedtokenizer.m1135()) != null) {
            if ((s1 = s1.trim()).length() != 0) {
               vector.addElement(s1.toUpperCase());
            }
         }

         if (vector.size() == 0) {
            return null;
         } else {
            vector.trimToSize();
            return vector;
         }
      }
   }

   void m217() {
      this.f128 = this.f139.getText().trim();
      if (this.f128.length() == 0) {
         this.f128 = null;
      }
   }

   String m218() {
      this.m217();
      String s = "";
      s = s + TaggedRecord.m1508(this.f123, '$');
      s = s + TaggedRecord.m1508(this.f124, '=');
      return s + TaggedRecord.m1508(this.f128, '*');
   }

   void m219() {
      this.f128 = null;
      this.f139.setText("");
      this.f137.setText("");
      this.f138.setText("");
   }

   boolean m220(boolean flag, String s) {
      if (flag) {
         this.f137.setText("Correct");
         this.f137.setForeground(dialogGreen);
         if (s == null) {
            this.f138.setText("Congratulations!");
         } else {
            this.f138.setText(LogicProgram.m1004(s.trim()));
         }
      } else {
         this.f137.setText("Incorrect");
         this.f137.setForeground(dialogRed);
         this.f138.setText("Try Again");
         if (s == null) {
            this.f138.setText("Try Again.");
         } else {
            this.f138.setText(LogicProgram.m1004(s.trim()));
         }
      }

      return flag;
   }

   boolean m221() {
      this.m217();
      if (this.f128 == null) {
         return this.m220(false, C_BF.getText("recnot001"));
      } else if (this.f125 != null && this.f125.indexOf(this.f128.toUpperCase()) != -1) {
         return this.m220(true, this.f127);
      } else if (this.f126 != null && this.f126.indexOf(this.f128.toUpperCase()) != -1) {
         String s2 = C_BF.getText("recnot006");
         if (s2 != null) {
            String s5 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
            Hashtable hashtable1 = Message.params("ruleName", this.f128, "thisPremise", s5);
            s2 = Message.substitute(s2, hashtable1);
         }

         return this.m220(false, s2);
      } else if (this.f128.toUpperCase().equals("NONE")) {
         return this.f125 == null && (this.f122 == null || this.f122.m1389(this.f121.activeRules()) != 2)
            ? this.m220(true, this.f127)
            : this.m220(false, C_BF.getText("recnot001"));
      } else {
         Rule rule = LogicProgram.m1026(this.f128);
         if (rule == null) {
            String s4 = C_BF.getText("recnot002");
            if (s4 != null) {
               s4 = Message.substitute(s4, Message.params("ruleName", this.f128));
            }

            return this.m220(false, s4);
         } else if (!this.f121.ruleActive(rule)) {
            String s3 = C_BF.getText("recnot007");
            if (s3 != null) {
               s3 = Message.substitute(s3, Message.params("ruleName", rule.f820));
            }

            return this.m220(false, s3);
         } else if (this.f122 == null) {
            return this.m220(false, C_BF.getText("recnot004"));
         } else {
            int i = this.f122.m1389(rule);
            if (i == 2) {
               return this.m220(true, this.f127);
            } else if (i != 1 && !this.m222(rule)) {
               String s6 = C_BF.getText("recnot003");
               if (s6 != null) {
                  String s7 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable2 = Message.params("ruleName", rule.f820, "thisPremise", s7);
                  s6 = Message.substitute(s6, hashtable2);
               }

               return this.m220(false, s6);
            } else {
               String s = C_BF.getText("recnot006");
               if (s != null) {
                  String s1 = this.f122.f827 != null && this.f122.f827.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable = Message.params("ruleName", rule.f820, "thisPremise", s1);
                  s = Message.substitute(s, hashtable);
               }

               return this.m220(false, s);
            }
         }
      }
   }

   boolean m222(Rule rule) {
      if (this.f122.f827 != null && this.f122.f827.length == 1) {
         if (rule.f820.equalsIgnoreCase("EG")) {
            return true;
         }

         if (rule.f820.equalsIgnoreCase("AV")) {
            return true;
         }

         if (rule.f820.equalsIgnoreCase("AV3")) {
            return true;
         }
      }

      return false;
   }
}
