package edu.ucla.phil.logic;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingUtilities;

abstract class ProblemSet extends Vector implements ModuleConstants {
   Hashtable f1074 = new Hashtable();
   Hashtable f1075 = null;
   String f1076 = null;
   static String[] f1077 = LogicProgram.symbols;
   boolean f1078 = false;
   boolean f1079 = false;
   boolean f1080 = false;
   boolean f1081 = false;
   static boolean f1082 = false;

   abstract String m1106(TaggedRecord taggedrecord);

   abstract boolean m1103(TaggedRecord taggedrecord);

   abstract String m1104(TaggedRecord taggedrecord);

   abstract String m1105(TaggedRecord taggedrecord);

   abstract ProblemEntry m1102(String s, boolean flag);

   abstract int m1107();

   synchronized int m1766(String s) {
      if (s == null) {
         return -1;
      } else {
         s = LogicProgram.m1000(s).toUpperCase();
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = TaggedRecord.m1493(((ProblemEntry)this.elementAt(j)).name);
            s1 = LogicProgram.m1000(s1).toUpperCase();
            int k = s.compareTo(s1);
            if (k == 0) {
               return -1;
            }

            if (k < 0) {
               return j;
            }
         }

         return i;
      }
   }

   int m1767(String s) {
      if (s == null) {
         return -1;
      } else {
         s = LogicProgram.m1000(s);
         int i = this.size();

         for (int j = 0; j < i; j++) {
            String s1 = LogicProgram.m1000(TaggedRecord.m1493(((ProblemEntry)this.elementAt(j)).name));
            if (s.equalsIgnoreCase(s1)) {
               return j;
            }
         }

         return -1;
      }
   }

   String m1768(String s) {
      return this.m1106(new TaggedRecord(s));
   }

   boolean m1769(String s) {
      return this.m1103(new TaggedRecord(s));
   }

   static boolean m1770(TaggedRecord taggedrecord) {
      Hashtable hashtable = taggedrecord.m1506('%');
      return hashtable != null && hashtable.containsKey("eg");
   }

   synchronized int m1771(ProblemEntry problementry, boolean flag) {
      String s = TaggedRecord.m1493(problementry.name);
      if (s == null) {
         return -1;
      } else {
         ProblemEntry problementry1 = this.f1074.put(s.trim().toUpperCase(), problementry);
         int i = problementry1 == null ? -1 : this.indexOf(problementry1);
         if (i != -1) {
            int j = 1;

            while (this.m1772(s + "-" + j) != null) {
               j++;
            }

            s = s + "-" + j;
            problementry1 = this.m1102(TaggedRecord.m1495(problementry1.name, s), flag);
            this.f1074.put(s.trim().toUpperCase(), problementry1);
            this.setElementAt(problementry1, i);
         }

         return i;
      }
   }

   synchronized ProblemEntry m1772(String s) {
      return s == null ? null : (ProblemEntry)this.f1074.get(s.trim().toUpperCase());
   }

   synchronized boolean m1773() {
      ProblemSet problemset1 = this.m1784();
      int i = problemset1 == null ? 0 : problemset1.size();
      int j = 0;
      this.f1078 = false;

      for (int k = 0; k < i; k++) {
         String s = problemset1.m1778(k);
         int l = this.m1774(s, j);
         if (l != -1) {
            j = l + 1;
         }
      }

      return this.f1078;
   }

   synchronized int m1774(String s, int i) {
      TaggedRecord taggedrecord1 = new TaggedRecord(s);
      String s1 = taggedrecord1.getName();
      String s2 = this.m1106(taggedrecord1);
      if (s1 != null && s2 != null) {
         ProblemEntry problementry = this.m1772(s1);
         int j = problementry == null ? -1 : this.indexOf(problementry);
         if (j == -1) {
            j = i - 1;
         } else {
            TaggedRecord taggedrecord;
            if (s2.equals(this.m1106(taggedrecord = new TaggedRecord(problementry.name)))) {
               return j;
            }

            if (!this.m1103(taggedrecord) || m1770(taggedrecord)) {
               this.m1101(j--);
            }
         }

         this.f1078 = true;
         ProblemEntry problementry1 = this.m1102(s, true);
         this.m1771(problementry1, true);
         this.insertElementAt(problementry1, j + 1);
         return j + 1;
      } else {
         return -1;
      }
   }

   synchronized int m1098(String s, Vector vector, boolean flag) {
      return this.m1775(new TaggedRecord(s), vector, flag);
   }

   int m1775(TaggedRecord taggedrecord, Vector vector, boolean flag) {
      String s = taggedrecord.getName();
      String s1 = taggedrecord.m1487();
      if (s == null) {
         System.out.println("problem without title: " + s1);
         return -1;
      } else {
         int i = flag ? this.m1766(s) : this.size();
         if (i == -1) {
            System.out.println("local problem with duplicate name: " + s);
            return -1;
         } else {
            if (this.f1075 != null && vector != null) {
               this.f1075.put(s, vector);
            }

            ProblemEntry problementry = this.m1102(s1, true);
            problementry.f1121 = taggedrecord.m1505();
            this.m1771(problementry, true);
            if (flag) {
               this.insertElementAt(problementry, i);
            } else {
               this.addElement(problementry);
            }

            return i;
         }
      }
   }

   abstract void m1099(LogicModule.C__A c__a);

   synchronized int m1776(String s, int i) {
      if (TaggedRecord.m1493(s) == null) {
         return -1;
      } else {
         ProblemEntry problementry = (ProblemEntry)this.elementAt(i);
         if (problementry != null) {
            this.f1074.remove(TaggedRecord.m1493(problementry.name).trim().toUpperCase());
         }

         problementry = this.m1102(s, false);
         this.m1771(problementry, false);
         this.setElementAt(problementry, i);
         return i;
      }
   }

   synchronized void m1101(int i) {
      String s = TaggedRecord.m1493(this.m1778(i));
      if (s != null) {
         this.f1074.remove(s.trim().toUpperCase());
      }

      this.removeElementAt(i);
      Vector vector = this.m1785();
      int j = vector.size();

      for (int k = 0; k < j; k++) {
         LogicModule logicmodule = (LogicModule)vector.elementAt(k);
         if (logicmodule.problemIndex == i) {
            logicmodule.problemIndex = -1;
         } else if (logicmodule.problemIndex > i) {
            logicmodule.problemIndex--;
         }
      }
   }

   synchronized String m1777(UserInfo userinfo) {
      return userinfo.m1148(new C_a_A(this), (String)userinfo.get(this.m1786()));
   }

   synchronized String m1778(int i) {
      ProblemEntry problementry = this.m1779(i);
      return problementry == null ? null : problementry.name;
   }

   synchronized ProblemEntry m1779(int i) {
      return i >= 0 && i < this.size() ? (ProblemEntry)this.elementAt(i) : null;
   }

   synchronized String m1780(String s) {
      if (s == null) {
         return null;
      } else {
         ProblemEntry problementry = this.m1772(s);
         return problementry == null ? null : problementry.name;
      }
   }

   ProblemListView m1781(
      LogicModule logicmodule, ProblemSet problemset1, boolean flag, boolean flag1, ProblemSelector problemselector, ProblemSelector problemselector1
   ) {
      BusyIndicator busyindicator = new BusyIndicator(logicmodule, true);
      ProblemListView problemlistview = new ProblemListView(flag);
      Hashtable hashtable = problemset1 == null ? null : problemset1.f1075;
      int i = logicmodule == null ? -1 : logicmodule.problemIndex;
      Color[] acolor = new Color[]{dialogBlack, dialogRed, dialogGreen, dialogRed};
      Color[] acolor1 = new Color[]{dialogBlack, dialogOrange, dialogOrange, dialogOrange};
      int j = this.size();
      int[] aint = new int[j];

      for (int k = 0; k < j; k++) {
         ProblemEntry problementry = this.m1779(k);
         if ((!problementry.f1121 || flag1) && (!problementry.f1122 || !f1082)) {
            TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
            String s = taggedrecord.getName();
            Vector vector;
            if (hashtable != null && (vector = (Vector)hashtable.get(s)) != null) {
               int l = vector.size();

               for (int i1 = 0; i1 < l; i1++) {
                  String s1 = (String)vector.elementAt(i1);
                  if (s1 == null || s1.equals("")) {
                     s1 = " ";
                  }

                  String s2 = LogicProgram.m1004(s1);
                  C_ZE c_ze = new C_ZE(s2, 2);
                  c_ze.setForeground(dialogBlue);
                  c_ze.setEnabled(false);
                  c_ze.setOpaque(true);
                  problemlistview.m1526(c_ze);
               }
            }

            String s3 = m1783(problemset1, s);
            if (!LogicProgram.m1060(problemselector1, s3)) {
               String s4 = this.m1106(taggedrecord);
               if (s != null && s3 != null) {
                  s = UserSetup.m2118(s);
               }

               s = (s == null ? "" : s + ":  ") + (s4 == null ? "" : s4.trim());
               C_QC c_qc = new C_QC(LogicProgram.m995(s, maggie, f1077), 2);
               c_qc.m1187(LogicProgram.m995(s, maggie, f1077));
               c_qc.setOpaque(true);
               if (!this.f1081) {
                  boolean flag2 = LogicProgram.m1060(problemselector, s3);
                  c_qc.setForeground(flag2 ? acolor1[problementry.state] : acolor[problementry.state]);
               }

               aint[k] = problemlistview.m1527();
               problemlistview.m1526(c_qc);
            }
         }
      }

      j = problemlistview.m1527();
      problemlistview.f899 = new int[j];
      if (j > 0) {
         for (int j1 = 0; j1 < j; j1++) {
            problemlistview.f899[j1] = -1;
         }

         j = aint.length;
         int k1 = 0;

         while (k1 < j) {
            problemlistview.f899[aint[k1]] = k1++;
         }

         if (i != -1) {
            problemlistview.setSelectedIndex(aint[i]);
         } else if (!flag && j > 0) {
            problemlistview.setSelectedIndex(aint[0]);
         }
      }

      busyindicator.m2162(false);
      return problemlistview;
   }

   static String m1782(String s) {
      return f1082 ? "h " + s : s;
   }

   static String m1783(ProblemSet problemset, String s) {
      String s1;
      return problemset != null && (s1 = problemset.m1780(s)) != null ? TaggedRecord.m1493(s1) : null;
   }

   ProblemSet m1784() {
      return (ProblemSet)LogicModule.getStaticField(this.m1107(), "exercises");
   }

   Vector m1785() {
      return (Vector)LogicModule.getStaticField(this.m1107(), "instances");
   }

   String m1786() {
      return (String)LogicModule.getStaticField(this.m1107(), "digestVersKey");
   }

   class C__A implements Runnable {
      boolean f1083;
      int f1084;
      int f1085;
      TaggedRecord f1086;
      LogicModule f1087;
      LogicModule.C__A f1088;
      C_h_C f1089;
      C_d_C f1090;

      C__A(int i, TaggedRecord taggedrecord, LogicModule logicmodule, LogicModule.C__A logicmodule$c__a) {
         this.f1084 = i;
         this.f1086 = taggedrecord;
         this.f1087 = logicmodule;
         this.f1088 = logicmodule$c__a;
         this.f1083 = false;
         this.f1089 = null;
         this.f1090 = null;
         this.f1085 = 0;
      }

      C__A(int i, TaggedRecord taggedrecord, C_d_C c_d_c, C_h_C c_h_c, LogicModule.C__A logicmodule$c__a) {
         this.f1084 = i;
         this.f1086 = taggedrecord;
         this.f1087 = null;
         this.f1088 = logicmodule$c__a;
         this.f1083 = false;
         this.f1089 = c_h_c;
         this.f1090 = c_d_c;
         this.f1085 = 0;
      }

      @Override
      public void run() {
         if (this.f1085 == this.f1084) {
            if (!this.f1083) {
               this.f1088.m959();
               return;
            }

            this.f1085 = 0;
            this.f1083 = false;
         }

         ProblemEntry problementry = ProblemSet.this.m1779(this.f1085);
         if (problementry != null && (problementry.state == 3 || problementry.state == 4)) {
            int i = problementry.state;
            this.f1086.m1471();
            this.f1086.parse(problementry.name);
            if (this.f1089 != null && this.f1090 != null) {
               this.f1090.m1741(this.f1086, this.f1089, (C__C)problementry);
            } else {
               problementry.state = this.f1087.getProblemState(this.f1086);
            }

            if (problementry.state != i) {
               this.f1083 = true;
            }
         }

         this.f1085++;
         SwingUtilities.invokeLater(this);
      }
   }
}
