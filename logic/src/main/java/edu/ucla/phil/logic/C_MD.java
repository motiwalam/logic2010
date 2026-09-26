package edu.ucla.phil.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

class C_MD extends ProblemSet {
   Hashtable f618 = null;

   synchronized void m1097(TaggedRecord taggedrecord) {
      String s = taggedrecord.getName();
      String s1 = LPDerivation.getProblemRuleProven(taggedrecord);
      if (s != null && s1 != null) {
         Vector vector = new Vector();
         C_n_F c_n_f = new C_n_F();
         LPDerivation.listRules(s1, vector, c_n_f, false);
         if (this.f618 == null) {
            this.f618 = new Hashtable();
         }

         Enumeration enumeration = vector.elements();

         while (enumeration.hasMoreElements()) {
            Rule rule = LogicProgram.f534.m2203((String)enumeration.nextElement());
            if (rule != null) {
               SchematicRule[] aschematicrule = rule.m1374();
               int i = aschematicrule.length;

               for (int j = 0; j < i; j++) {
                  String s2 = aschematicrule[j].f820;
                  Vector vector1 = (Vector)this.f618.get(s2);
                  if (vector1 == null) {
                     vector1 = new Vector();
                     vector1.addElement(s);
                     this.f618.put(s2, vector1);
                  } else if (!vector1.contains(s)) {
                     vector1.addElement(s);
                  }
               }
            }
         }

         enumeration = c_n_f.m1985();

         while (enumeration.hasMoreElements()) {
            Theorem theorem = LogicProgram.m1025((Integer)enumeration.nextElement());
            if (theorem != null) {
               Integer integer = theorem.f700;
               Vector vector2 = (Vector)this.f618.get(integer);
               if (vector2 == null) {
                  vector2 = new Vector();
                  vector2.addElement(s);
                  this.f618.put(integer, vector2);
               }

               if (!vector2.contains(s)) {
                  vector2.addElement(s);
               }
            }
         }
      }
   }

   @Override
   synchronized int m1098(String s, Vector vector, boolean flag) {
      TaggedRecord taggedrecord = new TaggedRecord(s);
      int i = this.m1775(taggedrecord, vector, flag);
      if (i != -1 && this.f618 != null) {
         this.m1097(taggedrecord);
      }

      return i;
   }

   @Override
   void m1099(LogicModule.C__A logicmodule$c__a) {
      LPDerivation.restating = true;
      ProblemSet.C__A problemset$c__a = new ProblemSet.C__A(this.size(), new TaggedRecord(), new LPDerivation(false), logicmodule$c__a);
      SwingUtilities.invokeLater(problemset$c__a);
   }

   synchronized void m1100() {
      int i = this.size();
      LPDerivation.userRules = new RuleTable(null);

      for (int j = 0; j < i; j++) {
         TaggedRecord taggedrecord = new TaggedRecord(this.m1778(j));
         String s = taggedrecord.getName();
         if (s != null && s.toUpperCase().startsWith("UR")) {
            C_DB c_db = new C_DB(s);
            if (c_db.f823 == null) {
               LPDerivation.userRules.m2202(c_db);
            }
         }
      }
   }

   @Override
   synchronized void m1101(int i) {
      TaggedRecord taggedrecord = new TaggedRecord(this.m1778(i));
      String s = taggedrecord.getName();
      if (s != null && s.toUpperCase().equals("UR")) {
         C_DB c_db = (C_DB)LPDerivation.userRules.m2203(s);
         if (c_db != null) {
            LPDerivation.userRules.m2206(c_db);
         }
      }

      super.m1101(i);
   }

   @Override
   ProblemEntry m1102(String s, boolean flag) {
      return new C_EE(s, flag);
   }

   @Override
   boolean m1103(TaggedRecord taggedrecord) {
      return LPDerivation.hasWork(taggedrecord);
   }

   @Override
   String m1104(TaggedRecord taggedrecord) {
      return LPDerivation.getWork(taggedrecord);
   }

   @Override
   String m1105(TaggedRecord taggedrecord) {
      return LPDerivation.removeWork(taggedrecord);
   }

   @Override
   String m1106(TaggedRecord taggedrecord) {
      return LPDerivation.getProblemStatement(taggedrecord);
   }

   @Override
   int m1107() {
      return 0;
   }
}
