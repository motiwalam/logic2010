package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JScrollPane;

class C_KC implements C_n_A {
   static C_p_ f445 = null;
   static C_p_ f446 = null;
   static C_p_ f447 = null;
   static C_p_ f448 = null;
   static C_p_ f449 = null;
   static C_p_ f450 = null;
   static C_p_ f451 = null;
   static C_p_ f452 = null;
   static C_p_ f453 = null;
   static C_p_ f454 = null;
   static C_p_ f455 = null;
   static C_p_ f456 = null;
   static C_p_ f457 = null;
   static C_p_ f458 = null;
   static C_p_ f459 = null;
   static C_p_ f460 = null;
   static C_p_ f461 = null;
   static C_p_ f462 = null;
   static C_p_ f463 = null;
   static C_p_ f464 = null;
   static C_p_ f465 = null;
   static File f466 = null;
   static File f467 = null;
   static File f468 = null;
   static File f469 = null;
   static File f470 = null;
   static String f471 = null;
   static String f472 = null;
   static String f473 = null;
   static String f474 = null;
   static String f475 = null;
   static String f476 = null;
   static String f477 = null;
   static String f478 = null;
   static String f479 = null;
   static boolean f480 = false;
   static boolean f481 = false;
   static boolean f482 = false;
   static boolean f483 = false;
   static boolean f484 = false;
   static boolean f485 = false;
   static boolean f486 = false;
   static C_p_D f487 = null;
   static String f488 = null;
   static C_a_F f489 = null;
   static final String f490 = "work/";
   static final String[] f491 = new String[]{
      "user.txt", "prefs.txt", "derwork.txt", "invwork.txt", "parwork.txt", "recwork.txt", "symwork.txt", "truwork.txt", "keywork.txt"
   };
   static final String[] f492 = new String[]{"derdata.txt", "invdata.txt", "pardata.txt", "recdata.txt", "symdata.txt", "trudata.txt"};
   static final String f493 = "work.zip";
   static final String f494 = "loader.jar";

   static boolean m802() {
      File file1 = LogicProgram.f550;
      String s = LogicProgram.m1078("editDir");
      f466 = s == null ? null : m877(file1, s);
      s = LogicProgram.m1078("localDir");
      f467 = s == null ? null : m877(file1, s);
      s = LogicProgram.m1078("textDir");
      f470 = s == null ? null : m877(file1, s);
      s = LogicProgram.m1078("adminDir");
      f468 = s == null ? null : m877(file1, s);
      s = LogicProgram.m1078("nonetDir");
      f469 = s == null ? null : m877(file1, s);
      Hashtable hashtable;
      if ((hashtable = LogicProgram.m1072(f470, false)) != null) {
         f471 = LogicProgram.m1076(hashtable, "institution", "").trim();
         f472 = LogicProgram.m1076(hashtable, "term", "").trim();
         f473 = LogicProgram.m1076(hashtable, "course", "").trim();
         f474 = LogicProgram.m1076(hashtable, "ident", "").trim();
         f475 = LogicProgram.m1076(hashtable, "version", "").trim();
         f476 = LogicProgram.m1077("institution", "").trim();
         f477 = LogicProgram.m1077("term", "").trim();
         f478 = LogicProgram.m1077("course", "").trim();
         f479 = LogicProgram.m1077("version", "").trim();
         f480 = true;
         s = LogicProgram.m1076(hashtable, "nonetDir", null);
         f482 = s == null ? false : LogicProgram.m1074(s).equals(LogicProgram.f552);
      } else if ((hashtable = LogicProgram.m1072(f468, false)) != null && LogicProgram.m1076(hashtable, "textDir", null) != null) {
         f471 = LogicProgram.m1077("institution", "").trim();
         f472 = LogicProgram.m1077("term", "").trim();
         f473 = LogicProgram.m1077("course", "").trim();
         f474 = LogicProgram.m1077("ident", "").trim();
         f475 = LogicProgram.m1077("version", "").trim();
         f476 = LogicProgram.m1076(hashtable, "institution", "").trim();
         f477 = LogicProgram.m1076(hashtable, "term", "").trim();
         f478 = LogicProgram.m1076(hashtable, "course", "").trim();
         f479 = LogicProgram.m1076(hashtable, "version", "").trim();
         f480 = false;
      } else {
         f471 = LogicProgram.m1077("institution", "").trim();
         f472 = LogicProgram.m1077("term", "").trim();
         f473 = LogicProgram.m1077("course", "").trim();
         f474 = LogicProgram.m1077("ident", "").trim();
         f475 = LogicProgram.m1077("version", "").trim();
         f476 = null;
         f477 = null;
         f478 = null;
         f479 = null;
         f480 = false;
      }

      f483 = LogicProgram.m970(f471);
      if (f471.equals("")) {
         return false;
      } else {
         if (!f483) {
            if (f472.equals("")) {
               return false;
            }

            if (f473.equals("")) {
               return false;
            }
         }

         if (f475.equals("")) {
            return false;
         } else {
            f465 = m803("website");
            f456 = m803("logic_nonce");
            f445 = m803("logic_verify");
            f446 = m803("logic_user");
            f447 = m803("logic_user_info");
            f448 = m803("logic_password");
            f449 = m803("logic_site");
            f450 = m803("logic_submission");
            f451 = m803("logic_upload");
            f452 = m803("logic_backup");
            f453 = m803("logic_backup_info");
            f454 = m803("logic_restore");
            f455 = m803("logic_delete_backup");
            f457 = m803("logic_load_remote");
            f458 = m803("logic_course_remote");
            f459 = m803("logic_get_user_reln");
            f460 = m803("logic_add_user_reln");
            f461 = m803("logic_insert_problem");
            f462 = m803("logic_update_problem");
            f463 = m803("logic_delete_problem");
            f464 = m803("logic_sql_generator");
            f487 = LogicProgram.m1040("logic");
            if (f456 == null) {
               return false;
            } else if (f445 == null || f446 == null || f448 == null || f449 == null) {
               return false;
            } else if (f450 == null) {
               return false;
            } else {
               return f452 == null || f453 == null || f454 == null || f455 == null ? false : f458 != null && f457 != null && f459 != null && f460 != null;
            }
         }
      }
   }

   static C_p_ m803(String s) {
      String s1 = LogicProgram.m1078(s);
      return m875(s1, new C_c_B(null));
   }

   static String m804(URL url) {
      String s = m805(url, null);
      if (C_k_C.f1199 != null) {
         C_k_C.f1199.println(LogicProgram.m1079());
         C_k_C.f1199.println("GET " + url);
         C_k_C.f1199.println(s);
      }

      return s;
   }

   static String m805(URL url, C_N c_n) {
      URLConnection urlconnection = null;

      String s;
      try {
         urlconnection = url.openConnection();
         if (!(urlconnection instanceof HttpURLConnection)) {
            return null;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
         httpurlconnection.setRequestMethod("GET");
         httpurlconnection.getHeaderField(0);
         int i = httpurlconnection.getResponseCode();
         if (i < 200 || i >= 300) {
            return null;
         }

         String s1 = System.getProperty("line.separator");
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
         s = "";

         String s2;
         while ((s2 = bufferedreader.readLine()) != null) {
            s = s + s2 + s1;
         }
      } catch (IOException ioexception) {
         if (LogicProgram.f572) {
            ioexception.printStackTrace(System.err);
         }

         s = null;
      }

      if (urlconnection != null) {
         ((HttpURLConnection)urlconnection).disconnect();
      }

      return s;
   }

   static String m806(C_p_ c_p_, C_0C c_0c, C_r_A c_r_a) {
      return m807(c_p_, c_0c.m57(), c_r_a);
   }

   static String m807(C_p_ c_p_, String s, C_r_A c_r_a) {
      Vector vector = new Vector();
      vector.addElement(s.getBytes());
      String s1 = m810(c_p_, vector, "application/x-www-form-urlencoded", c_r_a, null, null);
      if (C_k_C.f1199 != null) {
         C_k_C.f1199.println(LogicProgram.m1079());
         C_k_C.f1199.println("POST " + c_p_ + ": " + s);
         C_k_C.f1199.println(s1);
      }

      return s1;
   }

   static String m808(C_p_ c_p_, C_0C c_0c, C_r_A c_r_a, String s) {
      return m809(c_p_, c_0c.m58(s), c_r_a, s);
   }

   static String m809(C_p_ c_p_, Vector vector, C_r_A c_r_a, String s) {
      String s1 = m810(c_p_, vector, "multipart/form-data; boundary=" + s, c_r_a, null, null);
      if (C_k_C.f1199 != null) {
         C_k_C.f1199.println(LogicProgram.m1079());
         C_k_C.f1199.println("POST " + c_p_ + ": ");
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            Object object = vector.elementAt(j);
            if (object instanceof String) {
               C_k_C.f1199.println((String)object);
            } else if (object instanceof File) {
               C_k_C.f1199.println("File: " + ((File)object).getAbsolutePath());
            } else {
               C_k_C.f1199.println("Unexpected type: " + object.getClass());
            }
         }

         C_k_C.f1199.println(s1);
      }

      return s1;
   }

   static String m810(C_p_ c_p_, Vector vector, String s, C_r_A c_r_a, File file1, C_N c_n) {
      if (c_r_a == null) {
         c_r_a = m828(10000L);
      }

      return m811(c_p_, vector, s, c_r_a, file1, c_n);
   }

   static String m811(C_p_ c_p_, Vector vector, String s, C_r_A c_r_a, File file1, C_N c_n) {
      URLConnection urlconnection = null;
      C_KD c_kd = new C_KD();
      m813(c_kd, vector);

      String s1;
      try {
         urlconnection = c_p_.m2016();
         if (!(urlconnection instanceof HttpURLConnection)) {
            return null;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("Content-type", s);
         httpurlconnection.setRequestProperty("Content-length", c_kd.m924() + "");
         httpurlconnection.setRequestProperty("Content-MD5", new C_o_B(c_kd.m923()).toString());
         httpurlconnection.setConnectTimeout(10000);
         httpurlconnection.setReadTimeout(10000);
         httpurlconnection.setDoOutput(true);
         OutputStream outputstream = httpurlconnection.getOutputStream();
         c_r_a.m2059(outputstream);
         m813(outputstream, vector);
         c_r_a.m2059(null);
         httpurlconnection.getHeaderField(0);
         int i = httpurlconnection.getResponseCode();
         if (i < 200 || i >= 300) {
            httpurlconnection.disconnect();
            return null;
         }

         InputStream inputstream = httpurlconnection.getInputStream();
         c_r_a.m2057(inputstream);
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
         String s3 = System.getProperty("line.separator");
         s1 = "";

         String s2;
         while ((s2 = bufferedreader.readLine()) != null) {
            s1 = s1 + s2 + s3;
            if (s2.indexOf("</html>") != -1) {
               break;
            }
         }

         if (file1 != null) {
            m812(bufferedreader, file1);
         }

         c_r_a.m2057(null);
      } catch (IOException ioexception) {
         if (LogicProgram.f572) {
            ioexception.printStackTrace(System.err);
         }

         s1 = null;
      }

      if (urlconnection != null) {
         ((HttpURLConnection)urlconnection).disconnect();
      }

      return s1;
   }

   static IOException m812(Reader reader, File file1) {
      char[] achar = new char[2048];
      C_o_B c_o_b = new C_o_B();

      BufferedOutputStream bufferedoutputstream;
      try {
         bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file1));
      } catch (IOException ioexception4) {
         return ioexception4;
      }

      while (true) {
         try {
            int i = reader.read(achar);
            if (i == -1) {
               break;
            }

            if (i > 0) {
               c_o_b.m2003(new String(achar, 0, i));
               bufferedoutputstream.write(c_o_b.m2001(true));
            }
         } catch (IOException ioexception5) {
            try {
               bufferedoutputstream.close();
            } catch (IOException ioexception1) {
            }

            return ioexception5;
         }
      }

      try {
         bufferedoutputstream.write(c_o_b.m2001(false));
      } catch (IOException ioexception3) {
         try {
            bufferedoutputstream.close();
         } catch (IOException ioexception) {
         }

         return ioexception3;
      }

      try {
         bufferedoutputstream.close();
      } catch (IOException ioexception2) {
      }

      return null;
   }

   static IOException m813(OutputStream outputstream, Vector vector) {
      int i = vector.size();

      for (int j = 0; j < i; j++) {
         Object object = vector.elementAt(j);
         if (object instanceof String) {
            try {
               outputstream.write(((String)object + "\r\n").getBytes());
            } catch (IOException ioexception2) {
               return ioexception2;
            }
         } else if (object instanceof byte[]) {
            try {
               outputstream.write((byte[])object);
            } catch (IOException ioexception6) {
               return ioexception6;
            }
         } else if (object instanceof File && ((File)object).isFile()) {
            byte[] abyte = new byte[4096];
            C_o_B c_o_b = new C_o_B();

            BufferedInputStream bufferedinputstream;
            try {
               bufferedinputstream = new BufferedInputStream(new FileInputStream((File)object));
            } catch (IOException ioexception5) {
               return ioexception5;
            }

            while (true) {
               try {
                  int k = bufferedinputstream.read(abyte);
                  if (k == -1) {
                     break;
                  }

                  if (k > 0) {
                     c_o_b.m1999(abyte, 0, k);
                     outputstream.write(c_o_b.m2005(true).getBytes());
                  }
               } catch (IOException ioexception7) {
                  try {
                     bufferedinputstream.close();
                  } catch (IOException ioexception1) {
                  }

                  return ioexception7;
               }
            }

            try {
               outputstream.write((c_o_b.m2005(false) + "\r\n").getBytes());
            } catch (IOException ioexception4) {
               try {
                  bufferedinputstream.close();
               } catch (IOException ioexception) {
               }

               return ioexception4;
            }

            try {
               bufferedinputstream.close();
            } catch (IOException ioexception3) {
            }
         }
      }

      return null;
   }

   static String m814(String s) {
      return null;
   }

   static String m815(String s) {
      if (s == null) {
         return "NULL";
      } else {
         String s1 = "'";

         int i;
         while ((i = s.indexOf("'")) != -1) {
            s1 = s1 + s.substring(0, i + 1) + "'";
            s = s.substring(i + 1);
         }

         return s1 + s + "'";
      }
   }

   static String m816(Integer integer) {
      return integer == null ? "NULL" : "'" + integer + "'";
   }

   static Object[][] m817(String s) {
      if (s == null) {
         return (Object[][])null;
      } else {
         C_r_ c_r_ = new C_r_();
         c_r_.m2047(s, 0);
         return c_r_.m2050();
      }
   }

   static int m818(Object[][] aobject, String s) {
      return m820(aobject, 0, 0, s);
   }

   static int m819(Object[][] aobject, int i, String s) {
      return m820(aobject, 0, i, s);
   }

   static int m820(Object[][] aobject, int i, int j, String s) {
      if (aobject == null) {
         return -1;
      } else {
         int k = aobject.length;

         for (int l = i; l < k; l++) {
            try {
               if (((String)aobject[l][j]).equalsIgnoreCase(s)) {
                  return l;
               }
            } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            }
         }

         return -1;
      }
   }

   static String m821(Object[][] aobject, String s, C_v_ c_v_) {
      return m822(aobject, s, c_v_, false);
   }

   static String m822(Object[][] aobject, String s, C_v_ c_v_, boolean flag) {
      if (aobject == null) {
         m921("no response from server", c_v_);
         return null;
      } else {
         int i = m818(aobject, s);
         if (i == -1) {
            if (!flag) {
               m921("Could not find \"" + s.toUpperCase() + "\".", c_v_);
            }

            return null;
         } else if (aobject[i].length < 2) {
            if (!flag) {
               m921("Could not parse \"" + s.toUpperCase() + "\".", c_v_);
            }

            return null;
         } else {
            String s1 = (String)aobject[i][1];
            if (s1 == null) {
               if (!flag) {
                  m921("Could not parse \"" + s.toUpperCase() + "\".", c_v_);
               }

               return null;
            } else {
               return s1;
            }
         }
      }
   }

   static Integer m823(Object[][] aobject, String s, C_v_ c_v_) {
      return m824(aobject, s, c_v_, false);
   }

   static Integer m824(Object[][] aobject, String s, C_v_ c_v_, boolean flag) {
      String s1 = m822(aobject, s, c_v_, flag);
      if (s1 == null) {
         return null;
      } else {
         Integer integer = LogicProgram.m1010(s1.trim());
         if (integer == null) {
            if (!flag) {
               m921("Could not parse \"" + s1 + "\" as a number.", c_v_);
            }

            return null;
         } else {
            return integer;
         }
      }
   }

   static C_s_C m825(C_p_ c_p_, C_0C c_0c, C_r_A c_r_a, String s, C_x_A c_x_a) {
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      m831(c_0c, c_r_a);
      Object[][] aobject = m817(m808(c_p_, c_0c, c_r_a, s));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      if (!m834(aobject, c_0c)) {
         return null;
      } else {
         Integer integer = m920(aobject, c_0c);
         return !m833(integer, c_0c) ? null : new C_s_C(aobject, integer);
      }
   }

   static String m826(String s) {
      s = m827(s, "+", " ");
      String s1 = "";
      int j = 0;
      int k = s.length();

      int i;
      while ((i = s.indexOf("%", j)) != -1 && i + 3 <= k) {
         s1 = s1 + s.substring(j, i);

         try {
            s1 = s1 + (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
            j = i + 3;
         } catch (NumberFormatException numberformatexception) {
            s1 = s1 + "%";
            j = i + 1;
         }
      }

      return s1 + s.substring(j);
   }

   static String m827(String s, String s1, String s2) {
      String s3 = "";
      int j = 0;
      int k = s1.length();

      int i;
      while ((i = s.indexOf(s1, j)) != -1) {
         s3 = s3 + s.substring(j, i) + s2;
         j = i + k;
      }

      return s3 + s.substring(j);
   }

   static C_r_A m828(long i) {
      String s = "Process Watcher";
      String s1 = "This process has taken over " + i / 1000L + " seconds.\nIf you think it is hung, press Abort.";
      return new C_r_A(s, s1, i);
   }

   static C_0C m829(C_x_A c_x_a) {
      C_v_ c_v_;
      do {
         c_v_ = m830(c_x_a, (C_r_A)null);
      } while (c_v_ instanceof C_c_B && m922(c_v_));

      return c_v_ instanceof C_0C ? (C_0C)c_v_ : null;
   }

   static C_v_ m830(C_x_A c_x_a, C_r_A c_r_a) {
      Object object = null;

      try {
         object = "user=" + URLEncoder.encode(f487.f1334, "UTF-8");
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      Object[][] aobject = m817(m807(f456, (String)object, c_r_a));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      C_c_B c_c_b = new C_c_B(null);
      Integer integer = m920(aobject, c_c_b);
      if (integer == null) {
         return c_c_b;
      } else if (integer != 0) {
         m921("status = " + integer, c_c_b);
         return c_c_b;
      } else {
         String s = m821(aobject, "nonce", c_c_b);
         return (C_v_)(s == null ? c_c_b : new C_0C(f487, s));
      }
   }

   static boolean m831(C_0C c_0c, C_r_A c_r_a) {
      Object object = null;

      try {
         object = "user=" + URLEncoder.encode(f487.f1334, "UTF-8");
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      Object[][] aobject = m817(m807(f456, (String)object, c_r_a));
      Integer integer = m920(aobject, c_0c);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         m921("status = " + integer, c_0c);
         return false;
      } else {
         String s = m821(aobject, "nonce", c_0c);
         if (s == null) {
            return false;
         } else {
            c_0c.m49(s);
            return true;
         }
      }
   }

   static boolean m832(C_0C c_0c, C_x_A c_x_a) {
      String s = "";

      try {
         s = s + "user=" + URLEncoder.encode(c_0c.f17.f1334, "UTF-8") + "&";
         s = s + "nonce=" + URLEncoder.encode(c_0c.f18, "UTF-8") + "&";
         s = s + "auth=" + C_z_D.m2230(c_0c.f17.f1334 + ":" + c_0c.f18 + ":" + c_0c.f17.f1335);
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      Object[][] aobject = m817(m807(f456, s, (C_r_A)null));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      Integer integer = m920(aobject, c_0c);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         m921("status: " + integer, c_0c);
         return false;
      } else {
         return true;
      }
   }

   static boolean m833(Integer integer, C_v_ c_v_) {
      if (integer == null) {
         return false;
      } else if (integer < 0) {
         if (integer == -1) {
            m921("repeated nonce failure", c_v_);
         } else if (integer == -2) {
            m921("internal authentication failure", c_v_);
         } else {
            m921("unknown authentication error: " + integer, c_v_);
         }

         return false;
      } else {
         return true;
      }
   }

   static boolean m834(Object[][] aobject, C_0C c_0c) {
      String s = m821(aobject, "dgst", c_0c);
      if (s == null) {
         return false;
      } else {
         String s1 = m821(aobject, "cnonce", c_0c);
         if (s1 == null) {
            return false;
         } else {
            String s2 = m821(aobject, "auth", c_0c);
            if (s2 == null) {
               return false;
            } else if (c_0c.f22 != null && c_0c.f22.equals(s1)) {
               String[] astring = C_0C.m54(s);
               String s3 = "";
               int i = astring == null ? 0 : astring.length;

               for (int j = 0; j < i; j++) {
                  int k = m818(aobject, astring[j]);
                  if (k == -1) {
                     m921("Could not find \"" + astring[j] + "\".", c_0c);
                     return false;
                  }

                  int l = aobject[k].length;

                  for (int i1 = 1; i1 < l; i1++) {
                     String s4 = (String)aobject[k][i1];
                     if (s4 != null) {
                        s3 = s3 + s4;
                     }
                  }
               }

               String s5 = C_z_D.m2230(c_0c.f22 + ":" + c_0c.f17.f1335 + ":" + C_z_D.m2230(s3));
               if (!s5.equals(s2)) {
                  m921("signature mismatch", c_0c);
                  return false;
               } else {
                  return true;
               }
            } else {
               m921("client nonce mismatch", c_0c);
               return false;
            }
         }
      }
   }

   static C_0A m835(C_x_A c_x_a) {
      if (!f483 && !LogicProgram.f576) {
         C_0C c_0c = m829(c_x_a);
         if (c_0c == null) {
            return null;
         } else {
            C_OE c_oe = LogicProgram.f533;
            C_a_F c_a_f = C_a_F.m1642(c_0c, c_oe, c_x_a, 1);
            if (c_a_f != null && C_j_C.m1863(c_oe, "not048", false, false) != null) {
               if (c_oe.f662) {
                  c_oe.m684();
               }

               int i = c_oe.f665 == null ? 0 : c_oe.f665;
               return new C_0A(i, c_a_f.f980, c_0c, c_a_f.f979);
            } else {
               m832(c_0c, c_x_a);
               return null;
            }
         }
      } else {
         C_UA.m1329(C_H.m411("not039"), null, null, null);
         return null;
      }
   }

   static boolean m836(C_0A c_0a, C_x_A c_x_a) {
      if (c_0a.m2() && !f483) {
         C_H c_h = C_H.m411("not070");
         C_r_A c_r_a = new C_r_A(c_h.f370, c_h.f372);

         boolean flag;
         do {
            flag = m837(c_0a, c_x_a, c_r_a);
         } while (!flag && m922(c_0a));

         return flag;
      } else {
         return false;
      }
   }

   static boolean m837(C_0A c_0a, C_x_A c_x_a, C_r_A c_r_a) {
      String s = "logic_user_uid.logic_course_uid.evaluation.tproblem_md5.twork.problem_name.module.help_count.duration";
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", c_0a.f3 + "");
      hashtable.put("logic_course_uid", c_0a.f2 + "");
      hashtable.put("evaluation", c_0a.f6);
      hashtable.put("tproblem_md5", c_0a.f7);
      hashtable.put("twork", c_0a.f8);
      hashtable.put("problem_name", c_0a.f9);
      hashtable.put("module", c_0a.f10);
      hashtable.put("help_count", c_0a.f11 + "");
      hashtable.put("duration", c_0a.f12 + "");
      if (f488 != null) {
         s = s + ".wave_token";
         hashtable.put("wave_token", f488);
      }

      if (c_0a.f1 != null) {
         s = s + ".ip";
         hashtable.put("ip", c_0a.f1);
      }

      c_0a.f5.m53(hashtable, s);
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      Object[][] aobject = m817(m808(f450, c_0a.f5, c_r_a, "submit"));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      if (!m834(aobject, c_0a.f5)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_0a);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f450, c_0a.f5, c_r_a, "submit", c_x_a);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_0a);
               return false;
            } else {
               c_0a.f13 = m822(aobject, "dtTimestamp", c_0a, true);
               c_0a.f14 = m822(aobject, "logic_submission_uid", c_0a, true);
               return true;
            }
         }
      }
   }

   static void m838(C_0A c_0a, C_x_A c_x_a) {
      m832(c_0a.f5, c_x_a);
      c_0a.f5 = null;
   }

   static boolean m839(File file1) {
      File file2 = new File(LogicProgram.f550, "work.zip");

      FileOutputStream fileoutputstream;
      try {
         fileoutputstream = new FileOutputStream(file2);
      } catch (IOException ioexception1) {
         return false;
      }

      boolean flag = m845(file1, fileoutputstream);

      try {
         fileoutputstream.close();
      } catch (IOException ioexception) {
      }

      if (!flag && file2.exists()) {
         file2.delete();
      }

      return flag;
   }

   static C_LD m840(C_x_A c_x_a) {
      if (!f483 && f480 && !LogicProgram.f576 && f451 != null) {
         C_0C c_0c = m829(c_x_a);
         if (c_0c == null) {
            return null;
         } else {
            C_OE c_oe = LogicProgram.f533;
            if (c_oe.f664 == null) {
               Boolean obool = m906(c_0c, c_oe, null, null);
               if (obool == null || !obool) {
                  m832(c_0c, null);
                  return null;
               }
            }

            C_c_B c_c_b = C_u_C.m2102(null, "instructor");
            if (c_c_b != null) {
               String s = c_c_b.m716();
               if (s != null) {
                  C_UA.m1329(C_H.m411(s), c_c_b.f428, null, null);
               }

               m832(c_0c, null);
               return null;
            } else {
               C_MF c_mf = m861();
               if (c_mf.f623 == null) {
                  m832(c_0c, null);
                  return null;
               } else {
                  return new C_LD(c_0c, c_oe.f664, c_mf.f623, C_FB.m537());
               }
            }
         }
      } else {
         C_UA.m1329(C_H.m411("not039"), null, null, null);
         return null;
      }
   }

   static boolean m841(C_LD c_ld, C_x_A c_x_a) {
      if (c_ld == null) {
         return false;
      } else {
         C_H c_h = C_H.m411("not070");
         C_r_A c_r_a = new C_r_A(c_h.f370, c_h.f372);

         boolean flag;
         do {
            flag = m842(c_ld, c_x_a, c_r_a);
         } while (!flag && m922(c_ld));

         return flag;
      }
   }

   static boolean m842(C_LD c_ld, C_x_A c_x_a, C_r_A c_r_a) {
      String s = "logic_user_uid.logic_course_uid.problem_name.text.webtext.syntax.type.aux.num_answers";
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", c_ld.f507 + "");
      hashtable.put("logic_course_uid", c_ld.f508 + "");
      hashtable.put("problem_name", c_ld.f510);
      hashtable.put("text", c_ld.f511);
      hashtable.put("webtext", c_ld.f512);
      hashtable.put("syntax", c_ld.f509 + "");
      hashtable.put("type", c_ld.f513);
      hashtable.put("aux", c_ld.f514 == null ? "" : c_ld.f514);
      if (c_ld.f515 == null) {
         hashtable.put("num_answers", "0");
      } else {
         hashtable.put("num_answers", c_ld.f515.length + "");
         int i = c_ld.f515.length;

         for (int j = 0; j < i; j++) {
            s = s + ".answer_" + (j + 1);
            hashtable.put("answer_" + (j + 1), c_ld.f515[j]);
         }
      }

      c_ld.f506.m53(hashtable, s);
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      Object[][] aobject = m817(m808(f451, c_ld.f506, c_r_a, "upload"));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      if (!m834(aobject, c_ld.f506)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_ld);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f451, c_ld.f506, c_r_a, "upload", c_x_a);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_ld);
               return false;
            } else {
               c_ld.f516 = m822(aobject, "dtTimestamp", c_ld, true);
               c_ld.f517 = m822(aobject, "logic_inst_problem_uid", c_ld, true);
               return true;
            }
         }
      }
   }

   static void m843(C_LD c_ld, C_x_A c_x_a) {
      m832(c_ld.f506, c_x_a);
      c_ld.f506 = null;
   }

   static String m844(File file1) {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
      C_WC c_wc = new C_WC(bytearrayoutputstream);
      boolean flag = m845(file1, c_wc);

      try {
         c_wc.close();
      } catch (IOException ioexception) {
      }

      return flag ? bytearrayoutputstream.toString() : null;
   }

   static boolean m845(File file1, OutputStream outputstream) {
      if (!file1.exists()) {
         return false;
      } else {
         ZipOutputStream zipoutputstream = null;
         FileInputStream fileinputstream = null;

         try {
            byte[] abyte = new byte[4096];
            zipoutputstream = new ZipOutputStream(outputstream);
            zipoutputstream.setMethod(8);
            zipoutputstream.setLevel(9);
            zipoutputstream.putNextEntry(new ZipEntry("work/"));
            int j = f491.length;

            for (int k = 0; k < j; k++) {
               File file2 = new File(file1, f491[k]);
               if (file2.exists() && file2.isFile()) {
                  zipoutputstream.putNextEntry(new ZipEntry("work/" + f491[k]));
                  fileinputstream = new FileInputStream(file2);

                  int i;
                  while ((i = fileinputstream.read(abyte)) != -1) {
                     zipoutputstream.write(abyte, 0, i);
                  }

                  fileinputstream.close();
                  fileinputstream = null;
               }
            }

            j = f492.length;

            for (int i1 = 0; i1 < j; i1++) {
               File file3 = new File(file1, f492[i1]);
               if (file3.exists() && file3.isFile()) {
                  zipoutputstream.putNextEntry(new ZipEntry("work/" + f492[i1]));
                  fileinputstream = new FileInputStream(file3);

                  int l;
                  while ((l = fileinputstream.read(abyte)) != -1) {
                     zipoutputstream.write(abyte, 0, l);
                  }

                  fileinputstream.close();
                  fileinputstream = null;
               }
            }

            zipoutputstream.close();
            zipoutputstream = null;
            return true;
         } catch (IOException ioexception2) {
            if (fileinputstream != null) {
               try {
                  fileinputstream.close();
               } catch (IOException ioexception1) {
               }
            }

            if (zipoutputstream != null) {
               try {
                  zipoutputstream.close();
               } catch (IOException ioexception) {
               }
            }

            return false;
         }
      }
   }

   static boolean m846(boolean flag, File file1) {
      File file2 = new File(LogicProgram.f550, "work.zip");

      try {
         return m848(new FileInputStream(file2), flag, file1);
      } catch (FileNotFoundException filenotfoundexception) {
         return false;
      }
   }

   static boolean m847(String s, boolean flag, File file1) {
      return m848(new C_LE(new ByteArrayInputStream(s.getBytes())), flag, file1);
   }

   static boolean m848(InputStream inputstream, boolean flag, File file1) {
      ZipInputStream zipinputstream = null;
      FileOutputStream fileoutputstream = null;

      try {
         byte[] abyte = new byte[4096];
         zipinputstream = new ZipInputStream(inputstream);
         File file2 = null;

         while (true) {
            ZipEntry zipentry = zipinputstream.getNextEntry();
            if (zipentry == null) {
               zipinputstream.close();
               zipinputstream = null;
               return true;
            }

            String s = zipentry.getName();
            s = m851(s);
            if (zipentry.isDirectory()) {
               if (s.equalsIgnoreCase("work/")) {
                  file2 = new File(file1, s);
                  if (!file2.exists()) {
                     file2.mkdirs();
                  }
               }
            } else if (file2 != null) {
               String s1 = s.toLowerCase();
               int j = LogicProgram.m1051(f491, s1.substring("work/".length()));
               if (s1.startsWith("work/") && (flag ? j == 0 || j == 1 : j != -1)) {
                  fileoutputstream = new FileOutputStream(new File(file1, s));

                  int i;
                  while ((i = zipinputstream.read(abyte)) != -1) {
                     fileoutputstream.write(abyte, 0, i);
                  }

                  fileoutputstream.close();
                  fileoutputstream = null;
               }
            }
         }
      } catch (IOException ioexception2) {
         if (fileoutputstream != null) {
            try {
               fileoutputstream.close();
            } catch (IOException ioexception1) {
            }
         }

         if (zipinputstream != null) {
            try {
               zipinputstream.close();
            } catch (IOException ioexception) {
            }
         }

         return false;
      }
   }

   static boolean m849() {
      for (int i = 2; i < f491.length; i++) {
         if (new File(LogicProgram.f555, f491[i]).exists()) {
            return true;
         }
      }

      return false;
   }

   static Hashtable m850() {
      C_MF c_mf = m861();
      C_SF c_sf = C_SF.m1293(c_mf.f620);
      return C_H.m668("dbsite", c_mf.f620, "dbterm", c_sf.m1299(c_mf.f621), "dbcourse", c_mf.f622);
   }

   static String m851(String s) {
      String s1 = "logic/";
      return s.toLowerCase().startsWith(s1) ? "work/" + s.substring(s1.length()) : s;
   }

   static C_XA m852(C_OE c_oe, C_x_A c_x_a) {
      C_0C c_0c = m829(c_x_a);
      if (c_0c == null) {
         return null;
      } else {
         C_a_F c_a_f = f489 != null ? f489 : C_a_F.m1642(c_0c, c_oe, c_x_a, 1);
         if (c_a_f == null) {
            m832(c_0c, c_x_a);
            return null;
         } else {
            return new C_XA(c_a_f.f980, c_0c, c_a_f.f979);
         }
      }
   }

   static boolean m853(C_XA c_xa, C_r_A c_r_a) {
      if (c_xa.m1462() && c_xa.f868 != null) {
         boolean flag;
         do {
            flag = m854(c_xa, c_r_a);
         } while (!flag && m922(c_xa));

         return flag;
      } else {
         return false;
      }
   }

   static boolean m854(C_XA c_xa, C_r_A c_r_a) {
      String s = "userid.sectionid.data.key";
      Hashtable hashtable = new Hashtable();
      hashtable.put("userid", c_xa.f865 + "");
      hashtable.put("sectionid", "0");
      hashtable.put("data", c_xa.f869);
      hashtable.put("key", c_xa.f868);
      if (c_xa.f864 != null) {
         s = s + ".ip";
         hashtable.put("ip", c_xa.f864);
      }

      c_xa.f867.m53(hashtable, s);
      Object[][] aobject = m817(m808(f452, c_xa.f867, c_r_a, "backup"));
      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f452, c_xa.f867, c_r_a, "backup", null);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_xa);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m855(C_FC c_fc, C_r_A c_r_a, boolean flag) {
      if (f461 != null && f462 != null) {
         c_fc.f305 = m829(null);
         if (c_fc.f305 == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = m856(c_fc, c_r_a, flag);
            } while (obool == null && m922(c_fc.f305));

            m832(c_fc.f305, null);
            return obool != null && obool;
         }
      } else {
         return false;
      }
   }

   static Boolean m856(C_FC c_fc, C_r_A c_r_a, boolean flag) {
      String s = "problem_name.tProblem.tProblem_md5.tWeb_form_problem.common_name.comment.version.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", m815(c_fc.f306));
      hashtable.put("tProblem", m815(c_fc.f307));
      hashtable.put("tProblem_md5", m815(c_fc.m538()));
      hashtable.put("tWeb_form_problem", m815(c_fc.m539()));
      hashtable.put("common_name", m815(c_fc.f308));
      hashtable.put("comment", m815(c_fc.m540()));
      hashtable.put("version", m816(c_fc.f310));
      hashtable.put("syntax", m815(C_FB.m537() + ""));
      c_fc.f305.m53(hashtable, s);
      Object[][] aobject = m817(m808(flag ? f462 : f461, c_fc.f305, c_r_a, flag ? "update_problem" : "insert_problem"));
      if (!m834(aobject, c_fc.f305)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_fc);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f461, c_fc.f305, c_r_a, "backup", null);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_fc);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m857(C_FC c_fc, C_r_A c_r_a) {
      if (f463 == null) {
         return false;
      } else {
         c_fc.f305 = m829(null);
         if (c_fc.f305 == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = m858(c_fc, c_r_a);
            } while (obool == null && m922(c_fc.f305));

            m832(c_fc.f305, null);
            return obool != null && obool;
         }
      }
   }

   static Boolean m858(C_FC c_fc, C_r_A c_r_a) {
      String s = "problem_name.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", c_fc.f306);
      hashtable.put("syntax", C_FB.m537());
      c_fc.f305.m53(hashtable, s);
      Object[][] aobject = m817(m808(f463, c_fc.f305, c_r_a, "delete_problem"));
      if (!m834(aobject, c_fc.f305)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_fc);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f461, c_fc.f305, c_r_a, "backup", null);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_fc);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean m859() {
      if (LogicProgram.m1040("exam") == null) {
         return false;
      } else {
         C_0C c_0c = m829(null);
         if (c_0c == null) {
            return true;
         } else {
            Boolean obool;
            do {
               obool = m860(c_0c);
            } while (obool == null && m922(c_0c));

            m832(c_0c, null);
            return obool == null || obool;
         }
      }
   }

   static Boolean m860(C_0C c_0c) {
      C_MF c_mf = m861();
      if (c_mf == null) {
         return null;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", c_mf.f623 + "");
         hashtable.put("arch", LogicProgram.f570);
         c_0c.m53(hashtable, "logic_course_uid");
         Object[][] aobject = m817(m808(f457, c_0c, (C_r_A)null, "load_remote"));
         if (!m834(aobject, c_0c)) {
            return null;
         } else {
            Integer integer = m920(aobject, c_0c);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  C_s_C c_s_c = m825(f457, c_0c, (C_r_A)null, "load_remote", null);
                  if (c_s_c == null) {
                     return null;
                  }

                  aobject = c_s_c.f1362;
                  integer = c_s_c.f1363;
               }

               if (integer != 0) {
                  m921("no remote data", c_0c);
                  return null;
               } else {
                  String s1 = f480 ? f479 : f475;
                  String s = m821(aobject, f480 ? "admin version" : "text version", c_0c);
                  if (s == null) {
                     return null;
                  } else {
                     return m867(s.trim(), s1, true) ? Boolean.TRUE : Boolean.FALSE;
                  }
               }
            }
         }
      }
   }

   static C_MF m861() {
      C_MF[] ac_mf = C_MF.m1120(f471, f472, f473);
      return ac_mf != null && ac_mf.length != 0 ? ac_mf[0] : null;
   }

   static int m862(C_OE c_oe) {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return 1;
      } else {
         int i = m863(c_0c, c_oe);
         m832(c_0c, null);
         return i;
      }
   }

   static int m863(C_0C c_0c, C_OE c_oe) {
      int i;
      do {
         i = m864(c_0c, c_oe);
      } while (i == 4 && m922(c_0c));

      return i;
   }

   static int m864(C_0C c_0c, C_OE c_oe) {
      C_r_A c_r_a = new C_r_A(LPInfo.programName, "Checking for updates...");
      Integer integer1 = c_oe.m1164();
      if (integer1 == null) {
         return 1;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", integer1 + "");
         hashtable.put("arch", LogicProgram.f570);
         c_0c.m53(hashtable, "logic_course_uid.arch");
         Object[][] aobject = m817(m808(f457, c_0c, c_r_a, "load_remote"));
         if (!m834(aobject, c_0c)) {
            return 4;
         } else {
            Integer integer = m920(aobject, c_0c);
            if (integer == null) {
               return 4;
            } else {
               if (integer == -1) {
                  C_s_C c_s_c = m825(f457, c_0c, c_r_a, "load_remote", null);
                  if (c_s_c == null) {
                     return 4;
                  }

                  aobject = c_s_c.f1362;
                  integer = c_s_c.f1363;
               }

               if (integer != 0) {
                  m921("no remote data", c_0c);
                  return 4;
               } else {
                  C_j_B c_j_b = new C_j_B();
                  String s6 = m821(aobject, "code version", c_0c);
                  if (s6 == null) {
                     return 1;
                  } else {
                     String s = s6.trim();
                     s6 = m821(aobject, "text version", c_0c);
                     if (s6 == null) {
                        return 1;
                     } else {
                        String s1 = s6.trim();
                        s6 = m821(aobject, "loader version", c_0c);
                        if (s6 == null) {
                           return 1;
                        } else {
                           c_j_b.f1183 = s6.trim();
                           s6 = m821(aobject, "download URL", c_0c);
                           if (s6 == null) {
                              return 1;
                           } else {
                              C_p_ c_p_ = m875(s6.trim(), c_0c);
                              if (c_p_ == null) {
                                 return 1;
                              } else {
                                 s6 = m821(aobject, "download text", c_0c);
                                 if (s6 == null) {
                                    return 1;
                                 } else {
                                    String s2 = s6.trim();
                                    s6 = m821(aobject, "update URL", c_0c);
                                    if (s6 == null) {
                                       return 1;
                                    } else {
                                       C_p_ c_p_1 = m875(s6.trim(), c_0c);
                                       if (c_p_1 == null) {
                                          return 1;
                                       } else {
                                          s6 = m821(aobject, "update text", c_0c);
                                          if (s6 == null) {
                                             return 1;
                                          } else {
                                             String s3 = s6.trim();
                                             s6 = m821(aobject, "loader URL", c_0c);
                                             if (s6 == null) {
                                                return 1;
                                             } else {
                                                c_j_b.f1182 = m875(s6.trim(), c_0c);
                                                if (c_j_b.f1182 == null) {
                                                   return 1;
                                                } else {
                                                   s6 = m821(aobject, "admin version", c_0c);
                                                   if (s6 == null) {
                                                      return 1;
                                                   } else {
                                                      String s4 = s6.trim();
                                                      s6 = m821(aobject, "admin url", c_0c);
                                                      if (s6 == null) {
                                                         return 1;
                                                      } else {
                                                         C_p_ c_p_2 = m875(s6.trim(), c_0c);
                                                         s6 = m821(aobject, "admin text", c_0c);
                                                         if (s6 == null) {
                                                            return 1;
                                                         } else {
                                                            String s5 = s6.trim();
                                                            s6 = m822(aobject, "wave token", c_0c, true);
                                                            f488 = s6 == null ? null : s6.trim();
                                                            c_j_b.f1184 = c_oe;
                                                            int i = 0;
                                                            boolean flag = m865(c_oe);
                                                            if (!f484 && !m867(s, LogicProgram.f569, flag) && !LogicProgram.f580) {
                                                               f484 = true;
                                                               c_j_b.f1180 = c_p_;
                                                               c_j_b.f1181 = s2;
                                                               c_j_b.f1187 = m870(s2);
                                                               if (c_j_b.f1187 == 0 && LogicProgram.f583 && !f486) {
                                                                  c_j_b.f1186 = true;
                                                               }

                                                               c_j_b.f1185 = false;

                                                               do {
                                                                  i = m869(c_j_b, c_j_b.f1186 ? "not046" : "not033");
                                                               } while (c_j_b.f1186 && i == 5);
                                                            } else if (!flag || !m867(s1, f475, true)) {
                                                               c_j_b.f1180 = c_p_1;
                                                               c_j_b.f1181 = s3;
                                                               c_j_b.f1187 = 1;
                                                               c_j_b.f1185 = true;
                                                               i = m869(c_j_b, flag ? "not034" : "not047");
                                                            } else if (!f485 && c_p_2 != null && (f479 != null || m868(c_oe))) {
                                                               f485 = true;
                                                               flag = m866(c_oe);
                                                               if (!flag || !m867(s4, f479, true)) {
                                                                  c_j_b.f1180 = c_p_2;
                                                                  c_j_b.f1181 = s5;
                                                                  c_j_b.f1187 = 1;
                                                                  c_j_b.f1185 = true;
                                                                  i = m869(c_j_b, flag ? "not036" : "not049");
                                                               }
                                                            }

                                                            return i;
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   static boolean m865(C_OE c_oe) {
      if (!f471.equalsIgnoreCase(c_oe.m1156())) {
         return false;
      } else if (!f472.equalsIgnoreCase(c_oe.m1157())) {
         return false;
      } else {
         return !f473.equalsIgnoreCase(c_oe.m1159()) ? false : c_oe.m1163() != null;
      }
   }

   static boolean m866(C_OE c_oe) {
      if (f476 == null || !f476.equalsIgnoreCase(c_oe.m1156())) {
         return false;
      } else if (f477 == null || !f477.equalsIgnoreCase(c_oe.m1157())) {
         return false;
      } else {
         return f478 == null || !f478.equalsIgnoreCase(c_oe.m1159()) ? false : c_oe.m1163() != null;
      }
   }

   static boolean m867(String s, String s1, boolean flag) {
      int i = s.compareTo(s1);
      return flag && !s.endsWith("x") && !s1.endsWith("x") ? i <= 0 : i == 0;
   }

   static boolean m868(C_OE c_oe) {
      if (!c_oe.m1171("instructor")) {
         return false;
      } else {
         C_H c_h = C_H.m411("not060");
         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, null, null, c_b_e);
         return f481 = c_b_e.f1027 == 1;
      }
   }

   static int m869(C_j_B c_j_b, String s) {
      if (c_j_b.f1187 == 2) {
         return m873(c_j_b);
      } else {
         C_H c_h = C_H.m411(s);
         C_SE c_se = new C_SE(c_h.f373, c_j_b);
         Hashtable hashtable = c_j_b.f1184.m1165();
         C_H.m664(hashtable, "text", c_j_b.f1181);
         C_UA.m1329(c_h, hashtable, null, c_se);
         return c_se.f772;
      }
   }

   static int m870(String s) {
      String s1 = s.trim().toUpperCase();
      return !s1.startsWith("HTTP://") && !s1.startsWith("HTTPS://") ? 0 : 2;
   }

   static boolean m871(C_j_B c_j_b, boolean flag) {
      Integer integer = LogicProgram.f587 == null ? null : LogicProgram.f587.m1935();
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = new BufferedWriter(new FileWriter(LogicProgram.f560));
         bufferedwriter.write("source:" + c_j_b.f1180);
         bufferedwriter.newLine();
         bufferedwriter.write("configDir:" + LogicProgram.f550);
         bufferedwriter.newLine();
         bufferedwriter.write("rootDir:" + LogicProgram.f554);
         bufferedwriter.newLine();
         bufferedwriter.write("progDir:" + LogicProgram.f553);
         bufferedwriter.newLine();
         bufferedwriter.write("linkDir:" + LogicProgram.f552);
         bufferedwriter.newLine();
         bufferedwriter.write("arch:" + LogicProgram.f570);
         bufferedwriter.newLine();
         bufferedwriter.write("type:" + (c_j_b.f1185 ? "local" : "core"));
         bufferedwriter.newLine();
         if (integer != null && !LogicProgram.f530.m2082("SoloCheck", "yes").equalsIgnoreCase("no")) {
            bufferedwriter.write("soloPort:" + integer);
            bufferedwriter.newLine();
         }

         if (!LogicProgram.f583) {
            bufferedwriter.write("workDeleted:");
            bufferedwriter.newLine();
         }

         bufferedwriter.write("needBackup:");
         if (LogicProgram.f583 && !f486) {
            bufferedwriter.write("true");
         } else {
            bufferedwriter.write("false");
         }

         bufferedwriter.newLine();
         bufferedwriter.write("institution:" + c_j_b.f1184.m1156());
         bufferedwriter.newLine();
         bufferedwriter.write("term:" + c_j_b.f1184.m1157());
         bufferedwriter.newLine();
         bufferedwriter.write("course:" + c_j_b.f1184.m1159());
         bufferedwriter.newLine();
         bufferedwriter.flush();
         bufferedwriter.close();
      } catch (IOException ioexception1) {
         if (bufferedwriter != null) {
            try {
               bufferedwriter.close();
            } catch (IOException ioexception) {
            }
         }

         if (flag) {
            C_UA.m1329(C_H.m411("not062"), null, null, null);
         }

         return false;
      }

      LogicProgram.f571 = false;
      return true;
   }

   static int m872(C_j_B c_j_b) {
      if (c_j_b.f1187 == 1) {
         return m874(c_j_b);
      } else {
         Object object = null;

         do {
            object = m876(c_j_b.f1182, c_j_b);
         } while (object == null && m922(c_j_b));

         if (object == null) {
            return 1;
         } else if (!m871(c_j_b, true)) {
            return 1;
         } else {
            boolean flag;
            do {
               flag = m878((File)object, c_j_b);
            } while (!flag && m922(c_j_b));

            return flag ? 3 : 1;
         }
      }
   }

   static int m873(C_j_B c_j_b) {
      C_H c_h;
      if (LogicProgram.f567 != null) {
         c_h = C_H.m411("not084");
      } else {
         c_h = C_H.m411("not085");
      }

      C_p_F c_p_f = new C_p_F(c_h.f373, c_j_b.f1181);
      C_UA.m1329(c_h, null, null, c_p_f);
      return c_p_f.f1338;
   }

   static int m874(C_j_B c_j_b) {
      while (true) {
         C_i_B c_i_b = new C_i_B();
         if (!c_i_b.m1856(c_j_b.f1180, c_j_b.f1185 ? LogicProgram.f550 : LogicProgram.f554, null)) {
            C_H c_h = C_H.m411("not086");
            C_b_E c_b_e = new C_b_E(c_h.f373);
            C_UA.m1329(c_h, null, null, c_b_e);
            if (c_b_e.f1027 == 0) {
               continue;
            }

            return 1;
         }

         LogicProgram.f591 = true;
         LogicProgram.f592 = c_j_b.f1184;
         return 2;
      }
   }

   static C_p_ m875(String s, C_v_ c_v_) {
      if (s == null) {
         return null;
      } else {
         try {
            return new C_p_(s);
         } catch (MalformedURLException malformedurlexception) {
            m921("could not interpret " + s + " as a URL.", c_v_);
            return null;
         }
      }
   }

   static File m876(C_p_ c_p_, C_j_B c_j_b) {
      File file1 = new File(LogicProgram.f553, "loader.jar");
      String s = m881(file1, "edu.ucla.phil.logic.LPUpdateLoader", "version");
      if (s == null || c_j_b.f1183 == null || c_j_b.f1183.compareTo(s) > 0) {
         C_i_B c_i_b = new C_i_B();
         if (!c_i_b.m1856(c_p_, file1, m828(10000L))) {
            C_UA.m1329(C_H.m411("not061"), null, null, null);
            return null;
         }
      }

      return file1;
   }

   static File m877(File file1, String s) {
      File file2 = new File(s);
      return file2.isAbsolute() ? file2 : new File(file1, s);
   }

   static boolean m878(File file1, C_v_ c_v_) {
      String[] astring = new String[]{"java", "-Dload.info=" + LogicProgram.f560, "-jar", file1.getPath()};
      if (LogicProgram.f570.equals("windows")) {
         astring[0] = "javaw";
      } else if (LogicProgram.f570.equals("macos")) {
         astring[0] = System.getProperty("java.home") + "/bin/java";
      }

      try {
         Runtime.getRuntime().exec(astring, null, LogicProgram.f550);
         m879();
         return true;
      } catch (IOException ioexception) {
         if (c_v_ == null) {
            C_UA.m1329(C_H.m411("not063"), null, null, null);
         } else {
            c_v_.m4("not063", null);
         }

         return false;
      }
   }

   static void m879() {
      File file1 = LogicProgram.f562;
      File[] afile = file1 == null ? null : file1.listFiles();
      int i = afile == null ? 0 : afile.length;
      if (i != 0) {
         if (!LogicProgram.f563.exists()) {
            LogicProgram.f563.mkdirs();
            if (!LogicProgram.f563.exists()) {
               return;
            }
         }

         for (int j = 0; j < i; j++) {
            File file3 = afile[j];
            File file2 = new File(LogicProgram.f563, file3.getName());
            if (file2.exists()) {
               int k = 1;

               do {
                  file2 = new File(LogicProgram.f563, file3.getName() + "-" + ++k);
               } while (file2.exists());
            }

            file3.renameTo(file2);
         }

         m880();
      }
   }

   static void m880() {
      File[] afile = LogicProgram.f563.listFiles();
      int i = afile == null ? 0 : afile.length;

      for (int j = 0; j < i; j++) {
         File file1 = afile[j];
         file1.delete();
      }
   }

   static String m881(File file1, String s, String s1) {
      if (!file1.exists()) {
         return null;
      } else {
         C_RA c_ra = new C_RA(file1);

         try {
            return (String)c_ra.loadClass(s).getField(s1).get(null);
         } catch (Exception exception) {
            return null;
         }
      }
   }

   static String[] m882(C_OE c_oe, C_r_A c_r_a) {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return null;
      } else {
         String[] astring = m883(c_0c, c_oe, c_r_a);
         m832(c_0c, null);
         return astring;
      }
   }

   static String[] m883(C_0C c_0c, C_OE c_oe, C_r_A c_r_a) {
      if (c_oe.f664 == null) {
         Boolean obool = m906(c_0c, c_oe, null, c_r_a);
         if (obool == null || !obool) {
            return null;
         }
      }

      c_oe.f665 = c_oe.m1164();
      if (c_oe.f665 == null) {
         return null;
      } else {
         String[] astring;
         do {
            astring = m884(c_0c, c_oe, c_r_a);
         } while (astring == null && m922(c_0c));

         return astring;
      }
   }

   static String[] m884(C_0C c_0c, C_OE c_oe, C_r_A c_r_a) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", c_oe.f664 + "");
      hashtable.put("logic_course_uid", c_oe.f665 + "");
      c_0c.m53(hashtable, "logic_user_uid.logic_course_uid");
      Object[][] aobject = m817(m808(f459, c_0c, c_r_a, "getUserCourseRelations"));
      if (!m834(aobject, c_0c)) {
         return null;
      } else {
         Integer integer = m920(aobject, c_0c);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f459, c_0c, c_r_a, "getUserCourseRelations", null);
               if (c_s_c == null) {
                  return null;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("no remote data", c_0c);
               return null;
            } else {
               String s = m821(aobject, "reln count", c_0c);
               if (s == null) {
                  return null;
               } else {
                  Integer integer1 = LogicProgram.m1010(s.trim());
                  if (integer1 == null) {
                     m921("Could not parse \"" + s + "\" as a number.", c_0c);
                     return null;
                  } else {
                     int i = integer1;
                     String[] astring = new String[i];

                     for (int j = 0; j < i; j++) {
                        s = m821(aobject, "reln_" + (j + 1), c_0c);
                        if (s == null) {
                           return null;
                        }

                        astring[j] = s.trim().toLowerCase();
                     }

                     return astring;
                  }
               }
            }
         }
      }
   }

   static boolean m885(C_OE c_oe, String s, C_r_A c_r_a) {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return false;
      } else {
         boolean flag = m886(c_0c, c_oe, s, c_r_a);
         m832(c_0c, null);
         return flag;
      }
   }

   static boolean m886(C_0C c_0c, C_OE c_oe, String s, C_r_A c_r_a) {
      if (f483) {
         return false;
      } else if (c_oe.f666 != null && LogicProgram.m1051(c_oe.f666, s.toLowerCase()) != -1) {
         return true;
      } else {
         if (c_oe.f664 == null) {
            Boolean obool = m906(c_0c, c_oe, null, c_r_a);
            if (obool == null || !obool) {
               return false;
            }
         }

         c_oe.f665 = c_oe.m1164();
         if (c_oe.f665 == null) {
            return false;
         } else {
            boolean flag;
            do {
               flag = m887(c_0c, c_oe, s, c_r_a);
            } while (!flag && m922(c_0c));

            return flag;
         }
      }
   }

   static boolean m887(C_0C c_0c, C_OE c_oe, String s, C_r_A c_r_a) {
      c_oe.f666 = null;
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", c_oe.f664 + "");
      hashtable.put("logic_course_uid", c_oe.f665 + "");
      hashtable.put("relation", s);
      c_0c.m53(hashtable, "logic_user_uid.logic_course_uid.relation");
      Object[][] aobject = m817(m808(f460, c_0c, c_r_a, "addUserCourseRelation"));
      Integer integer = m920(aobject, c_0c);
      if (integer == null) {
         return false;
      } else {
         if (integer == -1) {
            C_s_C c_s_c = m825(f460, c_0c, c_r_a, "addUserCourseRelation", null);
            if (c_s_c == null) {
               return false;
            }

            aobject = c_s_c.f1362;
            integer = c_s_c.f1363;
         }

         if (integer != 0) {
            m921("status = " + integer, c_0c);
            return false;
         } else {
            c_oe.f666 = m883(c_0c, c_oe, c_r_a);
            return true;
         }
      }
   }

   static Boolean m888(C_OE c_oe, String s, C_r_A c_r_a) {
      if (s == null) {
         return null;
      } else {
         C_0C c_0c = m829(null);
         if (c_0c == null) {
            return Boolean.FALSE;
         } else if (!m886(c_0c, c_oe, "student", c_r_a)) {
            m832(c_0c, null);
            return Boolean.FALSE;
         } else {
            C_XA c_xa = new C_XA(c_oe.f664, c_0c, "password");
            c_xa.f868 = s;

            Boolean obool;
            try {
               if (!m889(c_xa, null, c_r_a)) {
                  return Boolean.FALSE;
               }

               if (c_xa.m1463() != 0) {
                  return null;
               }

               if ((c_xa.f869 = m844(LogicProgram.f555)) != null) {
                  return m853(c_xa, c_r_a) ? Boolean.TRUE : Boolean.FALSE;
               }

               obool = Boolean.FALSE;
            } finally {
               m895(c_xa, null);
            }

            return obool;
         }
      }
   }

   static boolean m889(C_XA c_xa, C_x_A c_x_a, C_r_A c_r_a) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m890(c_xa, c_x_a, c_r_a);
         } while (!flag && m922(c_xa));

         return flag;
      }
   }

   static boolean m890(C_XA c_xa, C_x_A c_x_a, C_r_A c_r_a) {
      String s = "userid";
      Hashtable hashtable = new Hashtable();
      hashtable.put("userid", c_xa.f865 + "");
      c_xa.f867.m53(hashtable, s);
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      Object[][] aobject = m817(m808(f453, c_xa.f867, c_r_a, "backup_info"));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f453, c_xa.f867, c_r_a, "backup_info", c_x_a);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_xa);
               return false;
            } else {
               Integer integer2 = m823(aobject, "backup count", c_xa);
               if (integer2 == null) {
                  return false;
               } else {
                  int i = integer2;
                  c_xa.f872 = new C_w_D[i];

                  for (int j = 0; j < i; j++) {
                     int k = m818(aobject, "backup_" + (j + 1));
                     if (k == -1) {
                        m921("Could not find \"BACKUP_" + (j + 1) + "\".", c_xa);
                        return false;
                     }

                     Object[] aobject1 = aobject[k];
                     if (aobject1.length < 5 || aobject1[1] == null || aobject1[2] == null || aobject1[4] == null) {
                        m921("Could not parse \"BACKUP_" + (j + 1) + "\".", c_xa);
                        return false;
                     }

                     String s1 = ((String)aobject1[1]).trim();
                     String s2 = ((String)aobject1[2]).trim();
                     Integer integer1 = LogicProgram.m1010(((String)aobject1[4]).trim());
                     if (integer1 == null) {
                        m921("Could not parse \"BACKUP_" + (j + 1) + "\".", c_xa);
                        return false;
                     }

                     c_xa.f872[j] = new C_w_D(s1, s2, integer1);
                  }

                  return true;
               }
            }
         }
      }
   }

   static boolean m891(C_XA c_xa, C_r_A c_r_a) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m892(c_xa, c_r_a);
         } while (!flag && m922(c_xa));

         if (!flag) {
            m904(LogicProgram.f555);
         }

         return flag;
      }
   }

   static boolean m892(C_XA c_xa, C_r_A c_r_a) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", c_xa.f871 + "");
      c_xa.f867.m53(hashtable, s);
      Object[][] aobject = m817(m808(f454, c_xa.f867, c_r_a, "restore"));
      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f454, c_xa.f867, c_r_a, "restore", null);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_xa);
               return false;
            } else {
               c_xa.f869 = m821(aobject, "data", c_xa);
               return c_xa.f869 != null;
            }
         }
      }
   }

   static boolean m893(C_XA c_xa, C_x_A c_x_a, C_r_A c_r_a) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m894(c_xa, c_x_a, c_r_a);
         } while (!flag && m922(c_xa));

         return flag;
      }
   }

   static boolean m894(C_XA c_xa, C_x_A c_x_a, C_r_A c_r_a) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", c_xa.f871 + "");
      if (c_x_a != null) {
         c_x_a.m2162(true);
      }

      c_xa.f867.m53(hashtable, s);
      Object[][] aobject = m817(m808(f455, c_xa.f867, c_r_a, "delete_backup"));
      if (c_x_a != null) {
         c_x_a.m2162(false);
      }

      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f455, c_xa.f867, c_r_a, "delete_backup", c_x_a);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_xa);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static void m895(C_XA c_xa, C_x_A c_x_a) {
      m832(c_xa.f867, c_x_a);
      c_xa.f867 = null;
   }

   static boolean m896(String s, C_x_A c_x_a) {
      if (s == null) {
         C_UA.m1329(C_H.m411("not041"), null, null, null);
         return false;
      } else if (!C_z_C.m2211()) {
         return false;
      } else {
         C_XA c_xa = m852(LogicProgram.f533, c_x_a);
         if (c_xa == null) {
            return false;
         } else {
            c_xa.f868 = s;
            C_H c_h = C_H.m411("not020");
            C_r_A c_r_a = new C_r_A(c_h.f370, c_h.f372);
            c_xa.f869 = m844(LogicProgram.f555);
            if (c_xa.f869 == null) {
               m895(c_xa, c_x_a);
               C_UA.m1329(C_H.m411("not015"), null, null, null);
               return false;
            } else if (!m853(c_xa, c_r_a)) {
               m895(c_xa, c_x_a);
               return false;
            } else {
               if (m889(c_xa, c_x_a, c_r_a)) {
                  c_xa.m1468(LogicProgram.f566, c_x_a, c_r_a);
               }

               m895(c_xa, c_x_a);
               LogicProgram.f581 = false;
               C_UA.m1329(C_H.m411("not017"), null, null, null);
               return true;
            }
         }
      }
   }

   static Boolean m897(String s, String s1, C_HB c_hb) {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return null;
      } else {
         Boolean obool = m898(s, s1, c_0c, c_hb);
         m832(c_0c, null);
         return obool;
      }
   }

   static Boolean m898(String s, String s1, C_0C c_0c, C_HB c_hb) {
      Boolean obool = m906(c_0c, c_hb, null, (C_r_A)null);
      if (obool != null && obool) {
         C_XA c_xa = new C_XA(c_hb.f664, c_0c, "hasBackup");
         if (!m889(c_xa, null, (C_r_A)null)) {
            return null;
         } else if (s1 != null && c_xa.m1464(s1) != 0) {
            return Boolean.TRUE;
         } else {
            return s != null && c_xa.m1464(s) != 0 ? Boolean.TRUE : Boolean.FALSE;
         }
      } else {
         return obool;
      }
   }

   static boolean m899(String s, String s1, C_HB c_hb) {
      if (s1 == null && s == null) {
         C_UA.m1329(C_H.m411("not040"), null, null, null);
         return false;
      } else if (c_hb == null && (c_hb = C_j_C.m1869()) == null) {
         return false;
      } else {
         C_XA c_xa = m852(c_hb, null);
         if (c_xa == null) {
            return false;
         } else if (!m889(c_xa, null, (C_r_A)null)) {
            m895(c_xa, null);
            return false;
         } else {
            boolean flag = true;
            if (s1 != null) {
               c_xa.f868 = s1;
               if (c_xa.m1463() != 0) {
                  flag = false;
                  if (LogicProgram.m1040("restore") != null) {
                     C_c_B c_c_b = C_u_C.m2103("restore", null, "not095");
                     if (c_c_b != null) {
                        String s2 = c_c_b.m716();
                        if (s2 != null) {
                           C_UA.m1329(C_H.m411(s2), c_c_b.f428, null, null);
                        }

                        flag = true;
                        return false;
                     }
                  }
               }
            }

            if (flag && s != null) {
               c_xa.f868 = s;
            }

            if (!C_u_C.m2116(c_xa)) {
               m895(c_xa, null);
               return false;
            } else {
               C_H c_h = C_H.m411("not021");
               C_r_A c_r_a = new C_r_A(c_h.f370, c_h.f372);
               if (!m891(c_xa, c_r_a)) {
                  m895(c_xa, null);
                  return false;
               } else {
                  boolean flag1 = m847(c_xa.f869, flag, LogicProgram.f550);
                  if (flag1) {
                     LogicProgram.f581 = false;
                  } else {
                     C_UA.m1329(C_H.m411("not016"), null, null, null);
                  }

                  m895(c_xa, null);
                  return true;
               }
            }
         }
      }
   }

   static boolean m900(File file1, File file2) {
      boolean flag = false;
      if (file1 != null && file2 != null) {
         Hashtable hashtable = C_H.m667("source", file1.getParent(), "dest", file2.toString());
         C_H c_h = C_H.m411("not026");
         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, hashtable, null, c_b_e);
         if (c_b_e.f1027 != 0) {
            return false;
         }

         if (file2.equals(LogicProgram.f550)) {
            m903(false);
         } else {
            m904(new File(file2, "work"));
         }

         C_c_B c_c_b = new C_c_B("not025", hashtable);

         while (!(flag = m901(file1, file2)) && m922(c_c_b)) {
         }

         if (flag) {
            LogicProgram.f582 = false;
            C_UA.m1329(C_H.m411("not024"), null, null, null);
         }
      }

      return flag;
   }

   static boolean m901(File file1, File file2) {
      if (!file1.exists()) {
         return false;
      } else {
         boolean flag = false;
         C_H c_h = C_H.m411("not066");
         C_SD c_sd = new C_SD(c_h.f370, c_h.f372, false);
         c_sd.m1284(20, 10);
         String s = m844(file1);
         if (s != null) {
            flag = m847(s, false, file2);
         }

         c_sd.dispose();
         return flag;
      }
   }

   static int m902() {
      return m903(true);
   }

   static int m903(boolean flag) {
      Object object = null;
      if (flag) {
         C_H c_h = C_H.m411("not030");
         C_b_E c_b_e = new C_b_E(c_h.f373);
         C_UA.m1329(c_h, null, null, c_b_e);
         if (c_b_e.f1027 != 0) {
            return 1;
         }
      }

      LogicProgram.f581 = false;
      LogicProgram.f582 = false;
      LogicProgram.m979();
      object = m904(LogicProgram.f555);
      LogicProgram.f583 = false;
      LogicProgram.m978();
      object = LogicProgram.m980((String[])object);
      if (object == null) {
         return 0;
      } else {
         int k = ((Object[])object).length;

         for (int i = 0; i < k; i++) {
            C_k_C.m1905("file not deleted: " + ((Object[])object)[i]);
         }

         if (flag) {
            Dimension dimension = new Dimension(240, 300);
            C_0E c_0e = new C_0E();
            C_LB c_lb = new C_LB();
            c_lb.setLayout(new BorderLayout());
            C_LB c_lb1 = new C_LB();
            c_lb1.setLayout(new C_m_A());

            for (int j = 0; j < k; j++) {
               c_lb1.add(new C_ZE((String)((Object[])object)[j]));
            }

            C_LB c_lb2 = new C_LB();
            c_lb2.setLayout(new C_m_A());
            c_lb2.add(new C_ZE(C_H.m412("not032")));
            c_lb.add(c_lb2, "North");
            JScrollPane jscrollpane = new JScrollPane(c_lb1);
            c_lb.add(jscrollpane, "Center");
            String[] astring = new String[]{"OK"};
            C_UA c_ua = new C_UA(c_0e, "not032", c_lb, astring);
            c_ua.setSize(dimension);
            c_ua.m1323(C_UA.m1321(dimension), true);
            c_0e.dispose();
         }

         return 2;
      }
   }

   static String[] m904(File file1) {
      String[] astring = null;
      Vector vector = new Vector();
      if (file1.isDirectory()) {
         String[] astring1 = file1.list();
         int i = astring1.length;

         for (int j = 0; j < i; j++) {
            File file2 = new File(file1, astring1[j]);
            if (file2.isFile() && !file2.delete()) {
               vector.addElement(astring1[j]);
            }
         }

         if ((i = vector.size()) != 0) {
            astring = vector.toArray(new String[i]);
         }
      }

      return astring;
   }

   static Boolean m905(C_OE c_oe, C_x_A c_x_a, C_r_A c_r_a) {
      C_0C c_0c = m829(c_x_a);
      if (c_0c == null) {
         return null;
      } else {
         Boolean obool = m906(c_0c, c_oe, c_x_a, c_r_a);
         m832(c_0c, c_x_a);
         return obool;
      }
   }

   static Boolean m906(C_0C c_0c, C_OE c_oe, C_x_A c_x_a, C_r_A c_r_a) {
      Boolean obool;
      do {
         obool = m907(c_0c, c_oe, c_x_a, c_r_a);
      } while (obool == null && m922(c_0c));

      return obool;
   }

   static Boolean m907(C_0C c_0c, C_OE c_oe, C_x_A c_x_a, C_r_A c_r_a) {
      String s = c_oe.m1154(true);
      if (s.trim().equals("")) {
         return Boolean.FALSE;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("uid", s);
         hashtable.put("institution", c_oe.m1156());
         c_0c.m53(hashtable, "uid.institution");
         if (c_x_a != null) {
            c_x_a.m2162(true);
         }

         Object[][] aobject = m817(m808(f445, c_0c, c_r_a, "userExists"));
         if (c_x_a != null) {
            c_x_a.m2162(false);
         }

         if (!m834(aobject, c_0c)) {
            return null;
         } else {
            Integer integer = m920(aobject, c_0c);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  C_s_C c_s_c = m825(f445, c_0c, c_r_a, "userExists", null);
                  if (c_s_c == null) {
                     return null;
                  }

                  aobject = c_s_c.f1362;
                  integer = c_s_c.f1363;
               }

               if (integer != 0) {
                  m921("status = " + integer, c_0c);
                  return null;
               } else {
                  Integer integer1 = m823(aobject, "return_value", c_0c);
                  if (integer1 == null) {
                     return null;
                  } else if (integer1 != 1) {
                     return Boolean.FALSE;
                  } else {
                     c_oe.f664 = m823(aobject, "logic_user_uid", c_0c);
                     return c_oe.f664 == null ? null : Boolean.TRUE;
                  }
               }
            }
         }
      }
   }

   static Integer m908(C_0C c_0c, C_OE c_oe, C__F c__f, String s, int i) {
      if (c_oe == null) {
         return null;
      } else {
         Integer integer;
         do {
            integer = m909(c_0c, c_oe, (C_r_A)null, c__f, s, i);
         } while (integer == null && m922(c_0c));

         if (integer == null) {
            return null;
         } else {
            if (integer != 0) {
               if (s == null) {
                  return null;
               }

               if (c__f.f934) {
                  C_UA.m1328("Authorization Failed", s, null, null);
                  return null;
               }

               c__f.f934 = true;
               c_oe.f664 = m910(c_0c, c_oe, c__f, s, i);
            }

            if (c_oe.f664 == null) {
               return null;
            } else {
               if (c_oe instanceof C_HB) {
                  ((C_HB)c_oe).f381 = c__f.f933 == null ? c__f.f932 : c__f.f933;
               }

               return c_oe.f664;
            }
         }
      }
   }

   static Integer m909(C_0C c_0c, C_OE c_oe, C_r_A c_r_a, C__F c__f, String s, int i) {
      Hashtable hashtable = new Hashtable();
      boolean flag = c_oe instanceof C_HB;
      if (!flag) {
         hashtable.put("first_name", c_oe.m1150());
         hashtable.put("middle_name", c_oe.m1151());
         hashtable.put("last_name", c_oe.m1152());
      }

      hashtable.put("uid", c_oe.m1154(true));
      hashtable.put("password", c__f.f932);
      if (!flag) {
         hashtable.put("email", c_oe.m1155());
      }

      hashtable.put("institution", c_oe.m1156());
      hashtable.put("purpose", userPurposes[i]);
      if (flag) {
         c_0c.m53(hashtable, "uid.password.institution.purpose");
      } else {
         c_0c.m53(hashtable, "first_name.middle_name.last_name.uid.password.email.institution.purpose");
      }

      Object[][] aobject = m817(m808(f446, c_0c, c_r_a, "loginUser"));
      if (!m834(aobject, c_0c)) {
         return null;
      } else {
         Integer integer = m920(aobject, c_0c);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f446, c_0c, c_r_a, "loginUser", null);
               if (c_s_c == null) {
                  return null;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer == 0) {
               c_oe.f664 = m823(aobject, "logic_user_uid", c_0c);
               if (c_oe.f664 == null) {
                  return null;
               }
            }

            return integer;
         }
      }
   }

   static Integer m910(C_0C c_0c, C_OE c_oe, C__F c__f, String s, int i) {
      if (C_j_C.m1865(c_0c, c_oe, c__f, s) == null) {
         return null;
      } else if (m908(c_0c, c_oe, c__f, s, i) != null) {
         return c_oe.f664;
      } else if (LogicProgram.m1040("developer") == null) {
         return null;
      } else {
         C_c_B c_c_b = C_u_C.m2102("developer", "Developer");
         if (c_c_b != null) {
            String s1 = c_c_b.m716();
            if (s1 != null) {
               C_UA.m1329(C_H.m411(s1), c_c_b.f428, null, null);
            }

            return null;
         } else {
            Boolean obool = m906(c_0c, c_oe, null, (C_r_A)null);
            return obool != null && obool ? c_oe.f664 : null;
         }
      }
   }

   static Boolean m911(C_0C c_0c, C_OE c_oe) {
      Boolean obool = m906(c_0c, c_oe, null, (C_r_A)null);
      if (obool == null) {
         return Boolean.FALSE;
      } else if (!obool) {
         return null;
      } else {
         C_a_F c_a_f = C_a_F.m1642(c_0c, c_oe, null, 1);
         if (c_a_f == null) {
            return Boolean.FALSE;
         } else {
            if (LogicProgram.f586 == null) {
               f489 = c_a_f;
            }

            return Boolean.TRUE;
         }
      }
   }

   static C_OE m912(String s, String s1) {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return null;
      } else {
         C_OE c_oe = m913(c_0c, s, s1);
         m832(c_0c, null);
         return c_oe;
      }
   }

   static C_OE m913(C_0C c_0c, String s, String s1) {
      if (f447 == null) {
         return null;
      } else {
         C_OE c_oe;
         do {
            c_oe = m914(c_0c, s, s1);
         } while (c_oe == null && m922(c_0c));

         return c_oe;
      }
   }

   static C_OE m914(C_0C c_0c, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("institution", s);
      hashtable.put("studentID", s1);
      c_0c.m53(hashtable, "institution.studentID");
      Object[][] aobject = m817(m808(f447, c_0c, (C_r_A)null, "getUserInfo"));
      if (!m834(aobject, c_0c)) {
         return null;
      } else {
         Integer integer = m920(aobject, c_0c);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f447, c_0c, (C_r_A)null, "getUserInfo", null);
               if (c_s_c == null) {
                  return null;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            return integer != 0 ? null : null;
         }
      }
   }

   static boolean m915(C_0C c_0c, int i, String s, String s1) {
      Boolean obool;
      do {
         obool = m916(c_0c, (C_r_A)null, i, s, s1);
      } while (obool == null && m922(c_0c));

      return obool != null && obool;
   }

   static Boolean m916(C_0C c_0c, C_r_A c_r_a, int i, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", i + "");
      hashtable.put("old_password", s);
      hashtable.put("new_password", s1);
      c_0c.m53(hashtable, "logic_user_uid.old_password.new_password");
      Object[][] aobject = m817(m808(f448, c_0c, c_r_a, "changePassword"));
      if (!m834(aobject, c_0c)) {
         return null;
      } else {
         Integer integer = m920(aobject, c_0c);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f448, c_0c, c_r_a, "changePassword", null);
               if (c_s_c == null) {
                  return null;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            return integer == 0 ? Boolean.TRUE : Boolean.FALSE;
         }
      }
   }

   static boolean m917() {
      C_0C c_0c = m829(null);
      if (c_0c == null) {
         return false;
      } else {
         boolean flag = m918(c_0c);
         m832(c_0c, null);
         return flag;
      }
   }

   static boolean m918(C_0C c_0c) {
      C_H c_h = C_H.m411("not071");

      boolean flag;
      do {
         C_r_A c_r_a = new C_r_A(c_h.f370, c_h.f372);
         flag = m919(c_0c, c_r_a);
      } while (!flag && m922(c_0c));

      return flag;
   }

   static boolean m919(C_0C c_0c, C_r_A c_r_a) {
      Vector vector = new Vector();
      Hashtable hashtable = new Hashtable();
      hashtable.put("arch", LogicProgram.f570);
      c_0c.m53(hashtable, "arch");
      Object[][] aobject = m817(m808(f458, c_0c, c_r_a, "getRemoteCourses"));
      if (!m834(aobject, c_0c)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_0c);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               C_s_C c_s_c = m825(f458, c_0c, c_r_a, "getRemoteCourses", null);
               if (c_s_c == null) {
                  return false;
               }

               aobject = c_s_c.f1362;
               integer = c_s_c.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, c_0c);
               return false;
            } else {
               Integer integer3 = m823(aobject, "course count", c_0c);
               if (integer3 == null) {
                  return false;
               } else {
                  int i = integer3;

                  for (int j = 1; j <= i; j++) {
                     int k = m818(aobject, "course_" + j);
                     if (k == -1) {
                        m921("Could not find \"COURSE" + j + "\".", c_0c);
                        return false;
                     }

                     Integer integer1 = LogicProgram.m1010((String)aobject[k][4]);
                     if (integer1 == null) {
                        m921("Could not parse \"" + (String)aobject[k][4] + "\" as a number.", c_0c);
                        return false;
                     }

                     String s = aobject[k].length > 5 ? (String)aobject[k][5] : null;
                     if (s != null) {
                        s = s.trim();
                     }

                     Integer integer2 = null;
                     if (aobject[k].length > 6 && (integer2 = LogicProgram.m1010((String)aobject[k][6])) == null) {
                        m921("Could not parse \"" + (String)aobject[k][6] + "\" as a number.", c_0c);
                        return false;
                     }

                     String s1 = aobject[k].length > 7 ? (String)aobject[k][7] : null;
                     C_MF c_mf = new C_MF((String)aobject[k][1], (String)aobject[k][2], (String)aobject[k][3], integer1, s, integer2, s1);
                     vector.addElement(c_mf);
                  }

                  C_MF.f628 = new C_MF[vector.size()];
                  vector.copyInto(C_MF.f628);
                  C_WA c_wa = new C_WA(new C_d_B());
                  C_MF.f628 = (C_MF[])c_wa.m1425(C_MF.f628);
                  return true;
               }
            }
         }
      }
   }

   static Integer m920(Object[][] aobject, C_v_ c_v_) {
      return m823(aobject, "error status", c_v_);
   }

   static void m921(String s, C_v_ c_v_) {
      if (c_v_ == null) {
         C_H c_h = C_H.m411("not008");
         C_UA.m1328(c_h.f370, c_h.f372 + "\n\n" + s, null, null);
      } else {
         Hashtable hashtable = new Hashtable();
         if (s != null) {
            hashtable.put("commErrorMsg", s);
         }

         c_v_.m4("not008", hashtable);
      }
   }

   static boolean m922(C_v_ c_v_) {
      C_H c_h1 = C_H.m411("not037");
      C_c_B c_c_b;
      String s;
      C_H c_h;
      if (c_v_ != null && (c_c_b = c_v_.m5()) != null && c_c_b.f427 != null) {
         c_h = C_H.m411(c_c_b.f427);
         s = c_h.f372;
         if (c_c_b.f428 != null) {
            s = C_H.m661(s, c_c_b.f428);
            String s1 = (String)c_c_b.f428.get("commErrorMsg");
            if (s1 != null) {
               s = s + "\n\n" + s1;
            }
         }

         s = s + "\n\n" + c_h1.f372;
      } else {
         c_h = c_h1;
         s = c_h1.f372;
      }

      C_b_E c_b_e = new C_b_E(c_h1.f373);
      C_UA.m1328(c_h.f370, s, null, c_b_e);
      return c_b_e.f1027 == 0;
   }
}
