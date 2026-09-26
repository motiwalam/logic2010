package edu.ucla.phil.logic;

import java.util.Hashtable;
import java.util.Vector;

class C_y_ extends C_D implements C_DE {
   C_G f1446;
   static String[] f1447 = LogicProgram.f596;

   C_y_(C_G c_g, String s) {
      super(s);
      this.f1446 = c_g;
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      if (s == null) {
         return true;
      } else if (s.equalsIgnoreCase("query")) {
         LPDerivation lpderivation = this.f1446.f317.f915;
         C_l_E c_l_e = lpderivation.focus;
         if (c_l_e != null && c_l_e == c_l_e.f1258.f324) {
            c_l_e.f1258.m585();
         }

         lpderivation.abort(false);
         lpderivation.resetVarNames();
         this.f1446.f336 = null;
         this.f1446.m595(false);
         return true;
      } else if (s.equalsIgnoreCase("replace")) {
         C_RF c_rf2 = (C_RF)this.m449("sum");
         Vector vector2 = (Vector)this.m449("caches");
         this.f1446.m557(3);
         this.f1446.m6(c_rf2.toString());
         this.f1446.f332 = c_rf2;
         this.f1446.f333 = true;
         this.f1446.f336 = vector2;
         return true;
      } else if (s.equalsIgnoreCase("err033")) {
         C_a_ c_a_10 = (C_a_)this.m449("just");
         if (c_a_10 != null && c_a_10.f955 != null) {
            String s2 = "";
            int l = c_a_10.f955.size();

            for (int j1 = 0; j1 < l; j1++) {
               s2 = s2 + (j1 == 0 ? "" : "\\n") + c_a_10.f955.elementAt(j1);
            }

            Hashtable hashtable4 = C_H.m667("rule form premises", "\\l" + s2 + "\\l", "rule form conclusion", "\\l" + c_a_10.f942 + "\\l");
            this.f1446.m554("dererr033", c_a_10, 3, hashtable4);
         }

         C_y_ c_y_1 = this.f1446.f326.f759;
         if (c_y_1 != null) {
            c_y_1.m447("sum", c_a_10.f942);
            c_y_1.m447("caches", this.f1446.f336);
         }

         return true;
      } else if (s.equalsIgnoreCase("err103")) {
         C_a_ c_a_9 = (C_a_)this.m449("just");
         if (c_a_9 != null && c_a_9.f955 != null) {
            String s1 = "";
            int k = c_a_9.f955.size();

            for (int i1 = 0; i1 < k; i1++) {
               s1 = s1 + (i1 == 0 ? "" : "\\n") + c_a_9.f955.elementAt(i1);
            }

            Hashtable hashtable3 = C_H.m666("rule form premises", "\\l" + s1 + "\\l");
            this.f1446.m554("dererr103", c_a_9, 3, hashtable3);
         }

         return true;
      } else if (s.equalsIgnoreCase("undo")) {
         C_p_A c_p_a2 = (C_p_A)this.m449("undo");
         if (c_p_a2 != null) {
            if (!c_p_a2.m1844()) {
               C_KB.m759("dernot012");
            }

            c_p_a2.requestFocus();
         }

         return false;
      } else if (s.equalsIgnoreCase("dummyVarQueryOK")) {
         C_a_ c_a_8 = (C_a_)this.m449("just");
         C_HF c_hf5 = (C_HF)this.m449("inst");
         C_p_A c_p_a7 = (C_p_A)this.m449("edit");
         C__B c__b = new C__B();
         String s7 = LogicProgram.m995(c_p_a7.getText(), f1447, maggie);
         C_RF c_rf3 = c_hf5.m688().f527;
         if (!c__b.m1571(C__B.m1579(c_rf3.m1243(), 0), s7)) {
            C_KB.m761("dernot017", C_H.m666("gen var", "\\l" + s7 + "\\l"));
            return false;
         } else {
            C_RF c_rf7 = c_hf5.m688().f526[0].m1217(0).m1238(c_hf5.m690());
            C_RF c_rf9 = c_a_8.m1628(-1).m1237();
            C_MB c_mb = new C_MB();
            C_RF c_rf16 = c_rf3.m1239(c_hf5.m690(), c_mb);
            if (!c__b.m1574(c_rf3, c_rf16, c_mb, null)) {
               Vector vector6 = c__b.f925;
               Hashtable hashtable8 = C_H.m670(
                  "inst wff",
                  "\\l" + c_rf9 + "\\l",
                  "inst term",
                  "\\l" + c_rf7 + "\\l",
                  "gen var",
                  "\\l" + c_rf16.m1217(0) + "\\l",
                  "gen wff",
                  "\\l" + c_rf16.m1217(1) + "\\l",
                  "gen all",
                  "\\l" + c_rf16 + "\\l"
               );
               Object object1 = null;
               C_i_ c_i_5 = null;
               C_RF c_rf22 = null;
               if (vector6 != null && vector6.size() != 0) {
                  C_RF[] ac_rf5 = (C_RF[])vector6.elementAt(0);
                  object1 = ac_rf5[0];
                  c_i_5 = (C_i_)ac_rf5[1];
                  c_rf22 = c_i_5.m1850();
                  C_H.m664(hashtable8, "op phrase", "\\l" + ((C_RF)object1).f739 + ((C_RF)object1).m1217(0).f739 + "\\l");
                  C_H.m664(hashtable8, "op wfe", "\\l" + object1 + "\\l");
               }

               if (c_rf22 != null) {
                  C_KB.m761("dernot018", hashtable8);
               } else {
                  C_KB.m761("dernot029", hashtable8);
               }

               new C_GC(c_p_a7).start();
               return false;
            } else {
               return true;
            }
         }
      } else if (s.equalsIgnoreCase("existentialVarQueryOK")) {
         C_a_ c_a_7 = (C_a_)this.m449("just");
         C_HF c_hf4 = (C_HF)this.m449("inst");
         C_p_A c_p_a6 = (C_p_A)this.m449("edit");
         C_j_D c_j_d2 = new C_j_D(c_hf4.m690());
         String s6 = LogicProgram.m995(c_p_a6.getText(), f1447, maggie);
         Hashtable hashtable6 = C_H.m669(
            "inst var",
            "\\l" + s6 + "\\l",
            "gen var",
            "\\l" + c_a_7.m1628(-1).m1217(0) + "\\l",
            "gen wff",
            "\\l" + c_a_7.m1628(-1).m1217(1) + "\\l",
            "gen all",
            "\\l" + c_a_7.m1628(-1) + "\\l"
         );

         C_RF c_rf6;
         try {
            c_rf6 = LogicProgram.m1008(s6, true, false);
         } catch (C_k_B c_k_b) {
            C_KB.m761("dernot022", hashtable6);
            new C_GC(c_p_a6).start();
            return false;
         }

         if (c_rf6 == null) {
            return false;
         } else if (!(c_rf6 instanceof C_i_)) {
            C_KB.m761("dernot023", hashtable6);
            new C_GC(c_p_a6).start();
            return false;
         } else if (!c_j_d2.m1882(c_j_d2.f1189.elementAt(0).toString(), s6)) {
            C_KB.m761(c_j_d2.f1190, c_j_d2.f1191);
            new C_GC(c_p_a6).start();
            return false;
         } else if (!c_a_7.m1618(c_hf4, c_j_d2)) {
            Vector vector5 = c_a_7.f962;
            c_a_7.f962 = null;
            hashtable6 = C_H.m664(hashtable6, "inst wff", "\\l" + c_hf4.m688().f527.m1238(c_j_d2) + "\\l");
            if (vector5 != null && vector5.size() != 0) {
               C_RF[] ac_rf4 = (C_RF[])vector5.elementAt(0);
               C_RF c_rf15 = ac_rf4[0];
               C_i_ c_i_4 = (C_i_)ac_rf4[1];
               C_H.m664(hashtable6, "op phrase", "\\l" + c_rf15.f739 + c_rf15.m1217(0).f739 + "\\l");
               C_H.m664(hashtable6, "op wfe", "\\l" + c_rf15 + "\\l");
            }

            C_KB.m763("dernot024", hashtable6, c_a_7);
            new C_GC(c_p_a6).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("universalTermQueryOK")) {
         C_a_ c_a_6 = (C_a_)this.m449("just");
         C_HF c_hf3 = (C_HF)this.m449("inst");
         C_p_A c_p_a5 = (C_p_A)this.m449("edit");
         C_j_D c_j_d1 = new C_j_D(c_hf3.m690());
         String s5 = LogicProgram.m995(c_p_a5.getText(), f1447, maggie);
         Hashtable hashtable5 = C_H.m669(
            "inst term",
            "\\l" + s5 + "\\l",
            "gen var",
            "\\l" + c_a_6.m1628(-1).m1217(0) + "\\l",
            "gen wff",
            "\\l" + c_a_6.m1628(-1).m1217(1) + "\\l",
            "gen all",
            "\\l" + c_a_6.m1628(-1) + "\\l"
         );

         C_RF c_rf5;
         try {
            c_rf5 = LogicProgram.m1008(s5, true, false);
         } catch (C_k_B c_k_b1) {
            C_KB.m761("dernot019", hashtable5);
            new C_GC(c_p_a5).start();
            return false;
         }

         if (c_rf5 == null) {
            return false;
         } else if (!(c_rf5 instanceof C_X)) {
            C_KB.m761("dernot020", hashtable5);
            new C_GC(c_p_a5).start();
            return false;
         } else if (!c_j_d1.m1882(c_j_d1.f1189.elementAt(0).toString(), s5)) {
            C_KB.m761(c_j_d1.f1190, c_j_d1.f1191);
            new C_GC(c_p_a5).start();
            return false;
         } else if (!c_a_6.m1618(c_hf3, c_j_d1)) {
            Vector vector4 = c_a_6.f962;
            c_a_6.f962 = null;
            hashtable5 = C_H.m664(hashtable5, "inst wff", "\\l" + c_hf3.m688().f527.m1238(c_j_d1) + "\\l");
            if (vector4 != null && vector4.size() != 0) {
               C_RF[] ac_rf3 = (C_RF[])vector4.elementAt(0);
               C_RF c_rf14 = ac_rf3[0];
               C_i_ c_i_3 = (C_i_)ac_rf3[1];
               C_H.m664(hashtable5, "op phrase", "\\l" + c_rf14.f739 + c_rf14.m1217(0).f739 + "\\l");
               C_H.m664(hashtable5, "op wfe", "\\l" + c_rf14 + "\\l");
            }

            C_KB.m763("dernot021", hashtable5, c_a_6);
            new C_GC(c_p_a5).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz12TermQueryOK")) {
         C_a_ c_a_5 = (C_a_)this.m449("just");
         C_HF c_hf2 = (C_HF)this.m449("inst");
         C_j_E c_j_e = (C_j_E)this.m449("edit");
         Hashtable hashtable2 = C_H.m665(c_j_e.f1192, null);
         C_j_D c_j_d3 = new C_j_D(c_hf2.m690());
         String s10 = LogicProgram.m995(c_j_e.getText(), f1447, maggie);
         c_j_d3.m1882(c_j_d3.f1189.elementAt(0).toString(), s10);
         C_RF c_rf4 = c_hf2.f391.f527.m1238(c_j_d3);
         C_H.m664(hashtable2, "sub wff", "\\l" + c_rf4 + "\\l");
         Vector vector3 = c_rf4.m1259();
         if (vector3 != null) {
            C_RF[] ac_rf2 = (C_RF[])vector3.elementAt(0);
            C_RF c_rf13 = ac_rf2[0];
            C_i_ c_i_2 = (C_i_)ac_rf2[1];
            C_H.m664(hashtable2, "op phrase", "\\l" + c_rf13.f739 + c_rf13.m1217(0).f739 + "\\l");
            C_H.m664(hashtable2, "op wfe", "\\l" + c_rf13 + "\\l");
            C_H.m664(hashtable2, "op var", "\\l" + c_i_2.f739 + "\\l");
            C_KB.m763("dernot034", hashtable2, c_a_5);
            new C_GC(c_j_e).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("leibniz34TermQueryOK")) {
         C_a_ c_a_4 = (C_a_)this.m449("just");
         C_HF c_hf1 = (C_HF)this.m449("inst");
         C_KE c_ke = (C_KE)this.m449("edit");
         Hashtable hashtable1 = C_H.m665(c_ke.f498, null);
         C_i_A c_i_a1 = c_ke.f499;
         C_j_D c_j_d4 = new C_j_D(c_hf1.m690());
         String s11 = LogicProgram.m995(c_ke.getText(), f1447, maggie);
         c_j_d4.m1882(c_i_a1.toString(), s11);
         int k1 = c_hf1.f391.f526[c_hf1.f392[0]].f739.equals("~") ? 0 : 1;
         C_RF c_rf11 = c_hf1.f391.f526[c_hf1.f392[k1]];
         C_RF c_rf12 = c_a_4.m1628(k1 - 2);
         C_RF c_rf17 = c_rf11.m1238(c_j_d4);
         C_RF c_rf18 = c_rf11.m1217(0).m1217(0).m1238(c_j_d4);
         C_RF c_rf19 = c_hf1.f391.f526[c_hf1.f392[1 - k1]];
         C_RF c_rf20 = c_a_4.m1628(-k1 - 1);
         C_RF c_rf21 = c_rf19.m1238(c_j_d4);
         C_RF c_rf23 = c_rf19.m1217(0).m1238(c_j_d4);
         C_RF c_rf24 = c_hf1.f391.f527.m1238(c_j_d4);
         C_H.m664(hashtable1, "term A", "\\l" + c_rf23 + "\\l");
         C_H.m664(hashtable1, "wff A", "\\l" + c_rf21 + "\\l");
         C_H.m664(hashtable1, "term B", "\\l" + c_rf18 + "\\l");
         C_H.m664(hashtable1, "wff B", "\\l" + c_rf12 + "\\l");
         C_H.m664(hashtable1, "sub wff", "\\l" + c_rf17 + "\\l");
         C_H.m664(hashtable1, "rule premise A", "\\l" + c_rf19 + "\\l");
         C_H.m664(hashtable1, "rule premise B", "\\l" + c_rf11 + "\\l");
         Vector vector7 = c_rf17.m1259();
         if (vector7 != null) {
            C_RF[] ac_rf6 = (C_RF[])vector7.elementAt(0);
            C_RF c_rf1 = ac_rf6[0];
            C_i_ c_i_1 = (C_i_)ac_rf6[1];
            C_H.m664(hashtable1, "op phrase", "\\l" + c_rf1.f739 + c_rf1.m1217(0).f739 + "\\l");
            C_H.m664(hashtable1, "op wfe", "\\l" + c_rf1 + "\\l");
            C_H.m664(hashtable1, "op var", "\\l" + c_i_1.f739 + "\\l");
            C_KB.m763("dernot040", hashtable1, c_a_4);
            new C_GC(c_ke).start();
            return false;
         } else if (!c_rf12.m1235(c_rf17)) {
            C_KB.m763("dernot041", hashtable1, c_a_4);
            new C_GC(c_ke).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("eulerTermQueryOK")) {
         C_a_ c_a_3 = (C_a_)this.m449("just");
         C_HF c_hf = (C_HF)this.m449("inst");
         C_Y c_y = (C_Y)this.m449("edit");
         C_j_D c_j_d = new C_j_D(c_hf.m690());
         C_i_A c_i_a = (C_i_A)c_j_d.f1189.elementAt(0);
         String s9 = LogicProgram.m995(c_y.getText(), f1447, maggie);
         c_j_d.m1882(c_i_a.toString(), s9);
         C_X c_x4 = (C_X)c_hf.f391.f527.m1217(0);
         C_X c_x5 = (C_X)c_hf.f391.f527.m1217(1);
         C_RF c_rf10 = c_a_3.m1628(-1);
         C_X c_x6 = (C_X)c_rf10.m1217(0);
         C_X c_x = (C_X)c_rf10.m1217(1);
         C_X c_x1 = (C_X)c_a_3.f941.m1217(0);
         C_X c_x2 = (C_X)c_a_3.f941.m1217(1);
         C_X c_x3 = (C_X)c_x5.m1238(c_j_d);
         Hashtable hashtable = C_H.m669(
            "left premise term",
            "\\l" + c_x6 + "\\l",
            "left conclusion term",
            "\\l" + c_x1 + "\\l",
            "right premise term",
            "\\l" + c_x + "\\l",
            "right conclusion term",
            "\\l" + c_x2 + "\\l"
         );
         C_H.m664(hashtable, "scheme conclusion term", "\\l" + c_x3 + "\\l");
         Vector vector1 = c_x3.m1259();
         if (vector1 != null) {
            C_RF[] ac_rf = (C_RF[])vector1.elementAt(0);
            C_RF c_rf = ac_rf[0];
            C_i_ c_i_ = (C_i_)ac_rf[1];
            C_H.m664(hashtable, "op phrase", "\\l" + c_rf.f739 + c_rf.m1217(0).f739 + "\\l");
            C_H.m664(hashtable, "op wfe", "\\l" + c_rf + "\\l");
            C_H.m664(hashtable, "op var", "\\l" + c_i_.f739 + "\\l");
            C_KB.m763("dernot046", hashtable, c_a_3);
            new C_GC(c_y).start();
            return false;
         } else {
            return true;
         }
      } else if (s.equalsIgnoreCase("interchangeFormulaQueryOK")) {
         C_a_ c_a_2 = (C_a_)this.m449("just");
         C_p_A c_p_a4 = (C_p_A)this.m449("edit");
         String s3 = c_p_a4.getText();
         int[] aint = new int[]{c_p_a4.getSelectionStart(), c_p_a4.getSelectionEnd()};
         String s4 = LogicProgram.m996(s3, f1447, maggie, aint);
         String s8 = c_p_a4.getSelectedText();
         Hashtable hashtable7 = new Hashtable();
         C_H.m664(hashtable7, "full text", s3);
         C_H.m664(hashtable7, "selection", s8);
         if (s8 != null && s8.length() != 0) {
            C_RF c_rf8;
            try {
               c_rf8 = LogicProgram.m1008(LogicProgram.m995(s8, f1447, maggie), true, false);
            } catch (C_k_B c_k_b2) {
               C_KB.m761("dernot054", hashtable7);
               c_p_a4.requestFocus();
               return false;
            }

            if (!(c_rf8 instanceof C_y_A)) {
               C_KB.m761("dernot055", hashtable7);
               c_p_a4.requestFocus();
               return false;
            } else {
               C_DD c_dd = new C_DD(s4);
               if (!c_rf8.m1235(c_dd.m480(aint[0], aint[1]).f278)) {
                  C_H.m664(hashtable7, "full expression", LogicProgram.m995(c_dd.f278.m1207(1), maggie, f1447));
                  C_KB.m761("dernot056", hashtable7);
                  c_p_a4.requestFocus();
                  return false;
               } else {
                  return true;
               }
            }
         } else {
            C_KB.m759("dernot013");
            c_p_a4.requestFocus();
            return false;
         }
      } else if (s.equalsIgnoreCase("interchangeRuleQueryOK")) {
         C_a_ c_a_1 = (C_a_)this.m449("just");
         C_p_A c_p_a3 = (C_p_A)this.m449("edit");
         C_VB c_vb1 = this.m2179(c_a_1, c_p_a3, new C_c_B("dernot057"));
         if (c_vb1 == null) {
            return false;
         } else {
            this.m447("rule", c_vb1);
            return true;
         }
      } else if (!s.equalsIgnoreCase("cieRuleQueryOK")) {
         return true;
      } else {
         C_a_ c_a_ = (C_a_)this.m449("just");
         C_p_A c_p_a = (C_p_A)this.m449("ruleEdit");
         C_p_A c_p_a1 = (C_p_A)this.m449("condEdit");
         C_VB c_vb = this.m2179(c_a_, c_p_a, new C_c_B("dernot057"));
         if (c_vb == null) {
            return false;
         } else {
            Object object = this.m2179(c_a_, c_p_a1, new C_c_B("dernot059"));
            if (object == null) {
               return false;
            } else {
               if (!(object instanceof C_l_F) && !(object instanceof C_OF)) {
                  if (!(object instanceof C_LF)) {
                     Vector vector = new Vector();
                     C_LF[] ac_lf = ((C_VB)object).m1374();
                     int i = 0;

                     for (int j = 0; j < ac_lf.length; j++) {
                        C_LF c_lf = ac_lf[j];
                        if (c_lf.f526.length == 0 && C_GA.m631(c_a_, c_lf, false)) {
                           vector.addElement(c_lf.f527);
                           ac_lf[i++] = c_lf;
                        }
                     }

                     if (i == 0) {
                        C_KB.m761("dernot060", C_H.m666("condition", ((C_VB)object).f820));
                        return false;
                     }

                     C_RF[] ac_rf1 = new C_RF[i];
                     vector.copyInto(ac_rf1);
                     i = C_KB.m778(c_a_.f935, ac_rf1, "Please choose a form of " + ((C_VB)object).f820);
                     if (i == -1) {
                        c_p_a1.requestFocus();
                        return false;
                     }

                     object = ac_lf[i];
                  }

                  C_HF c_hf6 = new C_HF((C_LF)object, new int[0], new C_j_D(), new C__B());
                  c_hf6.f391.f527.m1266(null, c_hf6.f393);
                  if (!C_KB.m782(c_a_, c_hf6)) {
                     c_p_a1.requestFocus();
                     return false;
                  }

                  object = new C_PF(c_hf6.f391, c_hf6.f393);
               }

               this.m447("rule", c_vb);
               this.m447("condition", object);
               return true;
            }
         }
      }
   }

   C_VB m2179(C_a_ c_a_, C_p_A c_p_a, C_c_B c_c_b) {
      String s = c_p_a.getText().trim();
      if (s.length() == 0) {
         C_KB.m761(c_c_b.f427, c_c_b.f428);
         c_p_a.requestFocus();
         return null;
      } else {
         Object object;
         Integer integer;
         if ((integer = LogicProgram.m1010(s)) != null) {
            try {
               object = new C_l_F(c_a_.f935.f317.f915, integer);
            } catch (IllegalArgumentException illegalargumentexception) {
               C_KB.m761("dererr003", C_H.m666("remote line number", s));
               c_p_a.requestFocus();
               return null;
            }
         } else {
            int i;
            if ((i = C_a_.m1633(s.toUpperCase())) != -1) {
               try {
                  LPDerivation lpderivation = c_a_.f935.f317.f915;
                  C_RF[] ac_rf = lpderivation.premises;
                  if (ac_rf == null || ac_rf.length == 0) {
                     C_KB.m759("dererr029");
                     c_p_a.requestFocus();
                     return null;
                  }

                  if (i == 0) {
                     Vector vector = new Vector();

                     for (i = 1; i <= ac_rf.length; i++) {
                        vector.addElement(new C_OF(lpderivation, i));
                     }

                     object = new C_VB("all premises", vector);
                  } else {
                     object = new C_OF(lpderivation, i);
                  }
               } catch (IllegalArgumentException illegalargumentexception1) {
                  C_KB.m761("dererr030", C_H.m666("premise index", i + ""));
                  c_p_a.requestFocus();
                  return null;
               }
            } else if ((object = LPDerivation.getRule(s)) == null) {
               C_KB.m761("dernot058", C_H.m666("rule name", s));
               c_p_a.requestFocus();
               return null;
            }
         }

         return (C_VB)object;
      }
   }
}
