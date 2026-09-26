package edu.ucla.phil.logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class C_OE extends Hashtable {
   static final int f659 = 2;
   static final String[] f660 = new String[]{"firstName", "midName", "lastName", "studentID", "email", "institution", "term", "className"};
   Vector f661;
   boolean f662;
   boolean f663;
   Integer f664 = null;
   Integer f665 = null;
   String[] f666 = null;

   C_OE() {
      this.f661 = new Vector();
      int i = f660.length;

      for (int j = 0; j < i; j++) {
         this.put(f660[j], "");
      }

      if (C_KC.f471 != null) {
         this.put("institution", C_KC.f471);
      }

      if (C_KC.f472 != null) {
         this.put("term", C_KC.f472);
      }

      if (C_KC.f473 != null) {
         this.put("className", C_KC.f473);
      }

      if (C_KC.f474 != null) {
         this.put("ident", C_KC.f474);
      }

      this.f662 = true;
      this.f663 = false;
   }

   C_OE(String s) {
      this();
      if (s != null) {
         this.put("studentID", s);
      }
   }

   static C_OE m1145(boolean flag) {
      FileReader filereader = null;

      C_OE c_oe;
      try {
         c_oe = m1167(filereader = new FileReader(LogicProgram.f559));
      } catch (IOException ioexception1) {
         c_oe = null;
      } finally {
         if (filereader != null) {
            try {
               filereader.close();
            } catch (IOException ioexception) {
            }
         }
      }

      if (c_oe == null) {
         if (!flag) {
            C_H c_h = C_H.m411(LogicProgram.f559.exists() ? "not072" : "not073");
            C_UA.m1329(c_h, null, null, null);
            return null;
         }

         c_oe = new C_OE();
      }

      while (!c_oe.m683()) {
         if (!c_oe.m1170()) {
            return null;
         }
      }

      return c_oe.f662 && !c_oe.m684() ? null : c_oe;
   }

   String m1146() {
      return this.m1148(null, "1");
   }

   String m1147(Enumeration enumeration) {
      return this.m1148(enumeration, "1");
   }

   synchronized String m1148(Enumeration enumeration, String s) {
      MessageDigest messagedigest;
      try {
         messagedigest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         return null;
      }

      if (s == null) {
         messagedigest.update((this.m1169() + " (" + this.m1153() + ")\n").getBytes());
      } else if (s.equals("1")) {
         messagedigest.update((this.m1169() + " (" + this.m1153() + "@" + this.m1156() + ")\n").getBytes());
      }

      if (enumeration != null) {
         while (enumeration.hasMoreElements()) {
            messagedigest.update((enumeration.nextElement() + "\n").getBytes());
         }
      }

      C_p_D c_p_d = LogicProgram.m1040("digest");
      if (c_p_d != null && c_p_d.f1335 != null) {
         messagedigest.update(c_p_d.f1335.getBytes());
      }

      return new C_o_B(messagedigest.digest()).toString();
   }

   String m1149(String s, String s1) {
      String s2 = (String)this.get(s);
      return s2 == null ? s1 : s2;
   }

   @Override
   public Object put(Object object, Object object1) {
      if (!this.f661.contains(object)) {
         this.f661.addElement(object);
      }

      this.f662 = true;
      return super.put(object, object1);
   }

   @Override
   public Object remove(Object object) {
      this.f661.removeElement(object);
      this.f662 = true;
      return super.remove(object);
   }

   String m1150() {
      return this.m1149("firstName", "");
   }

   String m1151() {
      return this.m1149("midName", "");
   }

   String m1152() {
      return this.m1149("lastName", "");
   }

   String m1153() {
      return this.m1154(false);
   }

   String m1154(boolean flag) {
      String s = this.m1149("studentID", "");
      if (flag) {
         s = C_SF.m1293(this.m1156()).m1295(s);
      }

      return s;
   }

   String m1155() {
      return this.m1149("email", "");
   }

   String m1156() {
      return this.m1149("institution", "");
   }

   String m1157() {
      return this.m1149("term", "");
   }

   String m1158() {
      return this.m1149("ident", "");
   }

   String m1159() {
      return this.m1149("className", "");
   }

   boolean m1160() {
      return "work".equalsIgnoreCase(this.m1161());
   }

   String m1161() {
      return this.m1149("backupKey", "work");
   }

   void m1162(String s) {
      if (s == null) {
         this.put("backupKey", "work");
      } else {
         this.put("backupKey", s);
      }

      this.m684();
   }

   C_MF m1163() {
      C_MF[] ac_mf = C_MF.m1120(this.m1156(), this.m1157(), this.m1159());
      return ac_mf != null && ac_mf.length != 0 ? ac_mf[0] : null;
   }

   Integer m1164() {
      C_MF c_mf = this.m1163();
      return c_mf == null ? null : c_mf.f623;
   }

   Hashtable m1165() {
      C_SF c_sf = C_SF.m1293(this.m1156());
      Hashtable hashtable = C_H.m670("site", this.m1156(), "term", c_sf.m1299(this.m1157()), "course", this.m1159(), "name", this.m1169(), "sid", this.m1153());
      C_MF c_mf = this.m1163();
      String s = c_mf == null ? null : c_mf.f624;
      return C_H.m664(hashtable, "comment", s == null ? "" : s);
   }

   boolean m683() {
      if (this.f663) {
         if (!this.m1154(true).equals("demo")) {
            this.put("studentID", "demo");
         }

         if (!this.m1150().equalsIgnoreCase("Logic")) {
            this.put("firstName", "Logic");
         }

         if (!this.m1151().equals("")) {
            this.put("midName", "");
         }

         if (!this.m1152().equalsIgnoreCase("User")) {
            this.put("lastName", "User");
         }

         return true;
      } else if (this.m1154(true).equals("")) {
         return false;
      } else {
         return this.m1152().equals("") ? false : !this.m1150().equals("") || !this.m1151().equals("");
      }
   }

   static C_OE m1166() {
      C_OE c_oe = new C_OE();
      c_oe.put("firstName", "Logic");
      c_oe.put("lastName", "User");
      c_oe.put("studentID", "demo");
      c_oe.f663 = true;
      return c_oe;
   }

   static C_OE m1167(Reader reader) {
      Vector vector = new Vector();
      BufferedReader bufferedreader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);

      while (true) {
         try {
            String s = bufferedreader.readLine();
            if (s == null) {
               break;
            }

            vector.addElement(s);
         } catch (IOException ioexception1) {
            break;
         }
      }

      try {
         bufferedreader.close();
      } catch (IOException ioexception) {
      }

      Integer integer = vector.size() == 0 ? null : LogicProgram.m1010((String)vector.elementAt(0));
      int i = integer == null ? 0 : integer;
      C_OE c_oe = null;
      if (i == 0) {
         c_oe = new C_OE();

         try {
            c_oe.put("firstName", vector.elementAt(0));
            c_oe.put("midName", vector.elementAt(1));
            c_oe.put("lastName", vector.elementAt(2));
            c_oe.put("studentID", vector.elementAt(3));
            c_oe.put("className", vector.elementAt(4));
            c_oe.put("instructor", vector.elementAt(5));
            c_oe.put("assistant", vector.elementAt(6));
            c_oe.put("email", vector.elementAt(7));
            c_oe.f662 = false;
         } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
            c_oe.put("className", "");
            c_oe.put("instructor", "");
            c_oe.put("assistant", "");
         }
      } else if (i == 1) {
         c_oe = new C_OE();

         try {
            c_oe.put("firstName", vector.elementAt(1));
            c_oe.put("midName", vector.elementAt(2));
            c_oe.put("lastName", vector.elementAt(3));
            c_oe.put("studentID", vector.elementAt(4));
            c_oe.put("email", vector.elementAt(5));
            c_oe.put("institution", vector.elementAt(6));
            c_oe.put("term", vector.elementAt(7));
            c_oe.put("className", vector.elementAt(8));
            c_oe.put("instructor", vector.elementAt(9));
            c_oe.put("assistant", vector.elementAt(10));
            c_oe.f662 = false;
         } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            c_oe.put("institution", "");
            c_oe.put("term", "");
            c_oe.put("className", "");
            c_oe.put("instructor", "");
            c_oe.put("assistant", "");
         }
      } else if (i == 2) {
         Vector vector1 = new Vector();
         c_oe = new C_OE();
         int k = vector.size();

         for (int j = 1; j < k; j++) {
            String s1 = (String)vector.elementAt(j);
            int l = s1.indexOf(":");
            if (l != -1) {
               String s2 = s1.substring(0, l);
               c_oe.put(s2, s1.substring(l + 1));
               vector1.addElement(s2);
            }
         }

         c_oe.f662 = false;
         k = f660.length;

         for (int i1 = 0; i1 < k; i1++) {
            if (!vector1.contains(f660[i1])) {
               c_oe.f662 = true;
               break;
            }
         }
      }

      if (c_oe == null) {
         C_UA.m1328("Bad User Info", "Could not read the user information file.", null, null);
      } else {
         c_oe.f663 = LogicProgram.m970(c_oe.m1156());
      }

      return c_oe;
   }

   boolean m684() {
      try {
         this.m1168(new FileWriter(LogicProgram.f559));
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   synchronized void m1168(Writer writer) throws IOException {
      BufferedWriter bufferedwriter;
      if (writer instanceof BufferedWriter) {
         bufferedwriter = (BufferedWriter)writer;
      } else {
         bufferedwriter = new BufferedWriter(writer);
      }

      try {
         String s = "2";
         bufferedwriter.write(s, 0, s.length());
         bufferedwriter.newLine();
         int i = this.f661.size();

         for (int j = 0; j < i; j++) {
            String s1 = (String)this.f661.elementAt(j);
            s = s1 + ":" + this.m1149(s1, "");
            bufferedwriter.write(s, 0, s.length());
            bufferedwriter.newLine();
         }
      } finally {
         bufferedwriter.close();
      }

      this.f662 = false;
   }

   String m1169() {
      String s = this.m1150();
      s = s.trim() + " " + this.m1151().trim();
      s = s.trim() + " " + this.m1152().trim();
      return s.trim();
   }

   boolean m1170() {
      return this.m682(C_H.m412("not054"));
   }

   boolean m682(String s) {
      if (s == null) {
         return this.m1170();
      } else {
         String[] astring = new String[]{"Save", "Cancel"};
         String[] astring1 = new String[]{"Institution: ", "Term: ", "Course: "};
         String[] astring2 = new String[]{"Student ID: ", "First Name: ", "Middle Name: ", "Last Name: ", "E-Mail Address: "};
         short short1 = 350;
         boolean flag = !this.m1154(true).equals("");
         boolean flag1 = this.m683();
         C_SF c_sf = C_SF.m1293(this.m1156());
         C_ZE c_ze = new C_ZE(this.m1156());
         C_ZE c_ze1 = new C_ZE(c_sf.m1299(this.m1157()));
         C_ZE c_ze2 = new C_ZE(this.m1159());
         C_a_B c_a_b = new C_a_B(this.m1150(), 250);
         C_a_B c_a_b1 = new C_a_B(this.m1151(), 250);
         C_a_B c_a_b2 = new C_a_B(this.m1152(), 250);
         C_a_B c_a_b3 = new C_a_B(this.m1153(), 250);
         C_a_B c_a_b4 = new C_a_B(this.m1155(), 250);
         C_ZE[] ac_ze = new C_ZE[]{c_ze, c_ze1, c_ze2};
         C_a_B[] ac_a_b = new C_a_B[]{c_a_b3, c_a_b, c_a_b1, c_a_b2, c_a_b4};
         C_LB c_lb = new C_LB();
         GridBagLayout gridbaglayout = new GridBagLayout();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         c_lb.setLayout(gridbaglayout);

         for (int i = 0; i < ac_ze.length; i++) {
            C_d_D c_d_d = new C_d_D(ac_ze[i].getText());
            C_d_D c_d_d1 = new C_d_D(astring1[i]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            c_lb.add(c_d_d1, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            c_lb.add(c_d_d, gridbagconstraints);
         }

         for (int j = 0; j < ac_a_b.length; j++) {
            C_d_D c_d_d2 = new C_d_D(astring2[j]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            c_lb.add(c_d_d2, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 2.0;
            c_lb.add(ac_a_b[j], gridbagconstraints);
         }

         if (flag) {
            c_a_b3.setEditable(false);
         }

         if (flag1) {
            c_a_b.setEditable(false);
            c_a_b1.setEditable(false);
            c_a_b2.setEditable(false);
            if (this.f663) {
               c_a_b4.setEditable(false);
            }
         }

         C_UA c_ua = new C_UA(null, s, c_lb, astring);
         c_ua.m1314(0);
         c_ua.m1322(null);
         if (!this.f663 && c_ua.f790 == 0) {
            if (!flag) {
               String s5 = (String)this.get("studentID");
               String s8 = c_a_b3.getText();
               if (!s8.equals(s5)) {
                  this.put("studentID", s8);
                  this.f662 = true;
               }
            }

            if (!flag1) {
               String s6 = (String)this.get("firstName");
               String s9 = (String)this.get("midName");
               String s1 = (String)this.get("lastName");
               String s2 = c_a_b.getText();
               String s3 = c_a_b1.getText();
               String s4 = c_a_b2.getText();
               if (!s2.equals(s6) || !s3.equals(s9) || !s4.equals(s1)) {
                  this.put("firstName", s2);
                  this.put("midName", s3);
                  this.put("lastName", s4);
                  this.f662 = true;
               }
            }

            String s7 = (String)this.get("email");
            String s10 = c_a_b4.getText();
            if (!s10.equals(s7)) {
               this.put("email", s10);
               this.f662 = true;
            }

            return this.f662;
         } else {
            return false;
         }
      }
   }

   boolean m1171(String s) {
      if (this.f666 == null) {
         this.f666 = C_KC.m882(this, (C_r_A)null);
      }

      s = s.toLowerCase();
      if (LogicProgram.m1051(this.f666, s) != -1) {
         return true;
      } else {
         return s.equals("instructor") && this.m1171("developer") ? true : s.equals("student") && this.m1171("instructor");
      }
   }
}
