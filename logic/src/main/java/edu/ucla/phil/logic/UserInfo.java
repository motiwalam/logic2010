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

class UserInfo extends Hashtable {
   static final int f659 = 2;
   static final String[] FIELD_KEYS = new String[]{"firstName", "midName", "lastName", "studentID", "email", "institution", "term", "className"};
   Vector f661;
   boolean dirty;
   boolean demo;
   Integer f664 = null;
   Integer f665 = null;
   String[] f666 = null;

   UserInfo() {
      this.f661 = new Vector();
      int i = FIELD_KEYS.length;

      for (int j = 0; j < i; j++) {
         this.put(FIELD_KEYS[j], "");
      }

      if (ServerConnection.institution != null) {
         this.put("institution", ServerConnection.institution);
      }

      if (ServerConnection.term != null) {
         this.put("term", ServerConnection.term);
      }

      if (ServerConnection.course != null) {
         this.put("className", ServerConnection.course);
      }

      if (ServerConnection.ident != null) {
         this.put("ident", ServerConnection.ident);
      }

      this.dirty = true;
      this.demo = false;
   }

   UserInfo(String s) {
      this();
      if (s != null) {
         this.put("studentID", s);
      }
   }

   static UserInfo load(boolean flag) {
      FileReader filereader = null;

      UserInfo userinfo;
      try {
         userinfo = read(filereader = new FileReader(LogicProgram.userFile));
      } catch (IOException ioexception1) {
         userinfo = null;
      } finally {
         if (filereader != null) {
            try {
               filereader.close();
            } catch (IOException ioexception) {
            }
         }
      }

      if (userinfo == null) {
         if (!flag) {
            Message message = Message.get(LogicProgram.userFile.exists() ? "not072" : "not073");
            MessageDialog.showMessage(message, null, null, null);
            return null;
         }

         userinfo = new UserInfo();
      }

      while (!userinfo.m683()) {
         if (!userinfo.m1170()) {
            return null;
         }
      }

      return userinfo.dirty && !userinfo.save() ? null : userinfo;
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
         messagedigest.update((this.getFullName() + " (" + this.getStudentId() + ")\n").getBytes());
      } else if (s.equals("1")) {
         messagedigest.update((this.getFullName() + " (" + this.getStudentId() + "@" + this.getInstitution() + ")\n").getBytes());
      }

      if (enumeration != null) {
         while (enumeration.hasMoreElements()) {
            messagedigest.update((enumeration.nextElement() + "\n").getBytes());
         }
      }

      Credentials credentials = LogicProgram.getCredentials("digest");
      if (credentials != null && credentials.password != null) {
         messagedigest.update(credentials.password.getBytes());
      }

      return new Base64Codec(messagedigest.digest()).toString();
   }

   String getField(String s, String s1) {
      String s2 = (String)this.get(s);
      return s2 == null ? s1 : s2;
   }

   @Override
   public Object put(Object object, Object object1) {
      if (!this.f661.contains(object)) {
         this.f661.addElement(object);
      }

      this.dirty = true;
      return super.put(object, object1);
   }

   @Override
   public Object remove(Object object) {
      this.f661.removeElement(object);
      this.dirty = true;
      return super.remove(object);
   }

   String getFirstName() {
      return this.getField("firstName", "");
   }

   String getMiddleName() {
      return this.getField("midName", "");
   }

   String getLastName() {
      return this.getField("lastName", "");
   }

   String getStudentId() {
      return this.m1154(false);
   }

   String m1154(boolean flag) {
      String s = this.getField("studentID", "");
      if (flag) {
         s = Institution.m1293(this.getInstitution()).m1295(s);
      }

      return s;
   }

   String getEmail() {
      return this.getField("email", "");
   }

   String getInstitution() {
      return this.getField("institution", "");
   }

   String getTerm() {
      return this.getField("term", "");
   }

   String getIdent() {
      return this.getField("ident", "");
   }

   String getClassName() {
      return this.getField("className", "");
   }

   boolean m1160() {
      return "work".equalsIgnoreCase(this.m1161());
   }

   String m1161() {
      return this.getField("backupKey", "work");
   }

   void m1162(String s) {
      if (s == null) {
         this.put("backupKey", "work");
      } else {
         this.put("backupKey", s);
      }

      this.save();
   }

   CourseInfo m1163() {
      CourseInfo[] acourseinfo = CourseInfo.m1120(this.getInstitution(), this.getTerm(), this.getClassName());
      return acourseinfo != null && acourseinfo.length != 0 ? acourseinfo[0] : null;
   }

   Integer m1164() {
      CourseInfo courseinfo = this.m1163();
      return courseinfo == null ? null : courseinfo.f623;
   }

   Hashtable m1165() {
      Institution institution = Institution.m1293(this.getInstitution());
      Hashtable hashtable = Message.params(
         "site",
         this.getInstitution(),
         "term",
         institution.m1299(this.getTerm()),
         "course",
         this.getClassName(),
         "name",
         this.getFullName(),
         "sid",
         this.getStudentId()
      );
      CourseInfo courseinfo = this.m1163();
      String s = courseinfo == null ? null : courseinfo.f624;
      return Message.putParam(hashtable, "comment", s == null ? "" : s);
   }

   boolean m683() {
      if (this.demo) {
         if (!this.m1154(true).equals("demo")) {
            this.put("studentID", "demo");
         }

         if (!this.getFirstName().equalsIgnoreCase("Logic")) {
            this.put("firstName", "Logic");
         }

         if (!this.getMiddleName().equals("")) {
            this.put("midName", "");
         }

         if (!this.getLastName().equalsIgnoreCase("User")) {
            this.put("lastName", "User");
         }

         return true;
      } else if (this.m1154(true).equals("")) {
         return false;
      } else {
         return this.getLastName().equals("") ? false : !this.getFirstName().equals("") || !this.getMiddleName().equals("");
      }
   }

   static UserInfo createDemoUser() {
      UserInfo userinfo = new UserInfo();
      userinfo.put("firstName", "Logic");
      userinfo.put("lastName", "User");
      userinfo.put("studentID", "demo");
      userinfo.demo = true;
      return userinfo;
   }

   static UserInfo read(Reader reader) {
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

      Integer integer = vector.size() == 0 ? null : LogicProgram.parseInteger((String)vector.elementAt(0));
      int i = integer == null ? 0 : integer;
      UserInfo userinfo = null;
      if (i == 0) {
         userinfo = new UserInfo();

         try {
            userinfo.put("firstName", vector.elementAt(0));
            userinfo.put("midName", vector.elementAt(1));
            userinfo.put("lastName", vector.elementAt(2));
            userinfo.put("studentID", vector.elementAt(3));
            userinfo.put("className", vector.elementAt(4));
            userinfo.put("instructor", vector.elementAt(5));
            userinfo.put("assistant", vector.elementAt(6));
            userinfo.put("email", vector.elementAt(7));
            userinfo.dirty = false;
         } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
            userinfo.put("className", "");
            userinfo.put("instructor", "");
            userinfo.put("assistant", "");
         }
      } else if (i == 1) {
         userinfo = new UserInfo();

         try {
            userinfo.put("firstName", vector.elementAt(1));
            userinfo.put("midName", vector.elementAt(2));
            userinfo.put("lastName", vector.elementAt(3));
            userinfo.put("studentID", vector.elementAt(4));
            userinfo.put("email", vector.elementAt(5));
            userinfo.put("institution", vector.elementAt(6));
            userinfo.put("term", vector.elementAt(7));
            userinfo.put("className", vector.elementAt(8));
            userinfo.put("instructor", vector.elementAt(9));
            userinfo.put("assistant", vector.elementAt(10));
            userinfo.dirty = false;
         } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            userinfo.put("institution", "");
            userinfo.put("term", "");
            userinfo.put("className", "");
            userinfo.put("instructor", "");
            userinfo.put("assistant", "");
         }
      } else if (i == 2) {
         Vector vector1 = new Vector();
         userinfo = new UserInfo();
         int k = vector.size();

         for (int j = 1; j < k; j++) {
            String s1 = (String)vector.elementAt(j);
            int l = s1.indexOf(":");
            if (l != -1) {
               String s2 = s1.substring(0, l);
               userinfo.put(s2, s1.substring(l + 1));
               vector1.addElement(s2);
            }
         }

         userinfo.dirty = false;
         k = FIELD_KEYS.length;

         for (int i1 = 0; i1 < k; i1++) {
            if (!vector1.contains(FIELD_KEYS[i1])) {
               userinfo.dirty = true;
               break;
            }
         }
      }

      if (userinfo == null) {
         MessageDialog.showMessage("Bad User Info", "Could not read the user information file.", null, null);
      } else {
         userinfo.demo = LogicProgram.m970(userinfo.getInstitution());
      }

      return userinfo;
   }

   boolean save() {
      try {
         this.write(new FileWriter(LogicProgram.userFile));
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   synchronized void write(Writer writer) throws IOException {
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
            s = s1 + ":" + this.getField(s1, "");
            bufferedwriter.write(s, 0, s.length());
            bufferedwriter.newLine();
         }
      } finally {
         bufferedwriter.close();
      }

      this.dirty = false;
   }

   String getFullName() {
      String s = this.getFirstName();
      s = s.trim() + " " + this.getMiddleName().trim();
      s = s.trim() + " " + this.getLastName().trim();
      return s.trim();
   }

   boolean m1170() {
      return this.m682(Message.getText("not054"));
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
         Institution institution = Institution.m1293(this.getInstitution());
         C_ZE c_ze = new C_ZE(this.getInstitution());
         C_ZE c_ze1 = new C_ZE(institution.m1299(this.getTerm()));
         C_ZE c_ze2 = new C_ZE(this.getClassName());
         C_a_B c_a_b = new C_a_B(this.getFirstName(), 250);
         C_a_B c_a_b1 = new C_a_B(this.getMiddleName(), 250);
         C_a_B c_a_b2 = new C_a_B(this.getLastName(), 250);
         C_a_B c_a_b3 = new C_a_B(this.getStudentId(), 250);
         C_a_B c_a_b4 = new C_a_B(this.getEmail(), 250);
         C_ZE[] ac_ze = new C_ZE[]{c_ze, c_ze1, c_ze2};
         C_a_B[] ac_a_b = new C_a_B[]{c_a_b3, c_a_b, c_a_b1, c_a_b2, c_a_b4};
         SizedPanel sizedpanel = new SizedPanel();
         GridBagLayout gridbaglayout = new GridBagLayout();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         sizedpanel.setLayout(gridbaglayout);

         for (int i = 0; i < ac_ze.length; i++) {
            C_d_D c_d_d = new C_d_D(ac_ze[i].getText());
            C_d_D c_d_d1 = new C_d_D(astring1[i]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(c_d_d1, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 1.0;
            sizedpanel.add(c_d_d, gridbagconstraints);
         }

         for (int j = 0; j < ac_a_b.length; j++) {
            C_d_D c_d_d2 = new C_d_D(astring2[j]);
            gridbagconstraints.gridwidth = -1;
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0;
            sizedpanel.add(c_d_d2, gridbagconstraints);
            gridbagconstraints.gridwidth = 0;
            gridbagconstraints.fill = 2;
            gridbagconstraints.weightx = 2.0;
            sizedpanel.add(ac_a_b[j], gridbagconstraints);
         }

         if (flag) {
            c_a_b3.setEditable(false);
         }

         if (flag1) {
            c_a_b.setEditable(false);
            c_a_b1.setEditable(false);
            c_a_b2.setEditable(false);
            if (this.demo) {
               c_a_b4.setEditable(false);
            }
         }

         MessageDialog messagedialog = new MessageDialog(null, s, sizedpanel, astring);
         messagedialog.m1314(0);
         messagedialog.m1322(null);
         if (!this.demo && messagedialog.f790 == 0) {
            if (!flag) {
               String s5 = (String)this.get("studentID");
               String s8 = c_a_b3.getText();
               if (!s8.equals(s5)) {
                  this.put("studentID", s8);
                  this.dirty = true;
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
                  this.dirty = true;
               }
            }

            String s7 = (String)this.get("email");
            String s10 = c_a_b4.getText();
            if (!s10.equals(s7)) {
               this.put("email", s10);
               this.dirty = true;
            }

            return this.dirty;
         } else {
            return false;
         }
      }
   }

   boolean m1171(String s) {
      if (this.f666 == null) {
         this.f666 = ServerConnection.m882(this, (NetworkTask)null);
      }

      s = s.toLowerCase();
      if (LogicProgram.m1051(this.f666, s) != -1) {
         return true;
      } else {
         return s.equals("instructor") && this.m1171("developer") ? true : s.equals("student") && this.m1171("instructor");
      }
   }
}
