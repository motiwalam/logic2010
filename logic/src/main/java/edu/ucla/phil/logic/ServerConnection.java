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

class ServerConnection implements LogicConstants {
   static ServerUrl verifyUrl = null;
   static ServerUrl userUrl = null;
   static ServerUrl userInfoUrl = null;
   static ServerUrl passwordUrl = null;
   static ServerUrl siteUrl = null;
   static ServerUrl submissionUrl = null;
   static ServerUrl uploadUrl = null;
   static ServerUrl backupUrl = null;
   static ServerUrl backupInfoUrl = null;
   static ServerUrl restoreUrl = null;
   static ServerUrl deleteBackupUrl = null;
   static ServerUrl nonceUrl = null;
   static ServerUrl loadRemoteUrl = null;
   static ServerUrl courseRemoteUrl = null;
   static ServerUrl getUserRelationUrl = null;
   static ServerUrl addUserRelationUrl = null;
   static ServerUrl insertProblemUrl = null;
   static ServerUrl updateProblemUrl = null;
   static ServerUrl deleteProblemUrl = null;
   static ServerUrl sqlGeneratorUrl = null;
   static ServerUrl websiteUrl = null;
   static File f466 = null;
   static File f467 = null;
   static File f468 = null;
   static File f469 = null;
   static File f470 = null;
   static String institution = null;
   static String term = null;
   static String course = null;
   static String ident = null;
   static String textVersion = null;
   static String f476 = null;
   static String f477 = null;
   static String f478 = null;
   static String f479 = null;
   static boolean f480 = false;
   static boolean f481 = false;
   static boolean f482 = false;
   static boolean demoMode = false;
   static boolean f484 = false;
   static boolean f485 = false;
   static boolean f486 = false;
   static Credentials serverCredentials = null;
   static String f488 = null;
   static C_a_F f489 = null;
   static final String f490 = "work/";
   static final String[] WORK_FILES = new String[]{
      "user.txt", "prefs.txt", "derwork.txt", "invwork.txt", "parwork.txt", "recwork.txt", "symwork.txt", "truwork.txt", "keywork.txt"
   };
   static final String[] DATA_FILES = new String[]{"derdata.txt", "invdata.txt", "pardata.txt", "recdata.txt", "symdata.txt", "trudata.txt"};
   static final String f493 = "work.zip";
   static final String f494 = "loader.jar";

   static boolean readDatabaseLinks() {
      File file1 = LogicProgram.configDir;
      String s = LogicProgram.getLink("editDir");
      f466 = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("localDir");
      f467 = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("textDir");
      f470 = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("adminDir");
      f468 = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("nonetDir");
      f469 = s == null ? null : resolvePath(file1, s);
      Hashtable hashtable;
      if ((hashtable = LogicProgram.readLinks(f470, false)) != null) {
         institution = LogicProgram.getValue(hashtable, "institution", "").trim();
         term = LogicProgram.getValue(hashtable, "term", "").trim();
         course = LogicProgram.getValue(hashtable, "course", "").trim();
         ident = LogicProgram.getValue(hashtable, "ident", "").trim();
         textVersion = LogicProgram.getValue(hashtable, "version", "").trim();
         f476 = LogicProgram.getLink("institution", "").trim();
         f477 = LogicProgram.getLink("term", "").trim();
         f478 = LogicProgram.getLink("course", "").trim();
         f479 = LogicProgram.getLink("version", "").trim();
         f480 = true;
         s = LogicProgram.getValue(hashtable, "nonetDir", null);
         f482 = s == null ? false : LogicProgram.canonicalFile(s).equals(LogicProgram.linkDir);
      } else if ((hashtable = LogicProgram.readLinks(f468, false)) != null && LogicProgram.getValue(hashtable, "textDir", null) != null) {
         institution = LogicProgram.getLink("institution", "").trim();
         term = LogicProgram.getLink("term", "").trim();
         course = LogicProgram.getLink("course", "").trim();
         ident = LogicProgram.getLink("ident", "").trim();
         textVersion = LogicProgram.getLink("version", "").trim();
         f476 = LogicProgram.getValue(hashtable, "institution", "").trim();
         f477 = LogicProgram.getValue(hashtable, "term", "").trim();
         f478 = LogicProgram.getValue(hashtable, "course", "").trim();
         f479 = LogicProgram.getValue(hashtable, "version", "").trim();
         f480 = false;
      } else {
         institution = LogicProgram.getLink("institution", "").trim();
         term = LogicProgram.getLink("term", "").trim();
         course = LogicProgram.getLink("course", "").trim();
         ident = LogicProgram.getLink("ident", "").trim();
         textVersion = LogicProgram.getLink("version", "").trim();
         f476 = null;
         f477 = null;
         f478 = null;
         f479 = null;
         f480 = false;
      }

      demoMode = LogicProgram.m970(institution);
      if (institution.equals("")) {
         return false;
      } else {
         if (!demoMode) {
            if (term.equals("")) {
               return false;
            }

            if (course.equals("")) {
               return false;
            }
         }

         if (textVersion.equals("")) {
            return false;
         } else {
            websiteUrl = getLinkUrl("website");
            nonceUrl = getLinkUrl("logic_nonce");
            verifyUrl = getLinkUrl("logic_verify");
            userUrl = getLinkUrl("logic_user");
            userInfoUrl = getLinkUrl("logic_user_info");
            passwordUrl = getLinkUrl("logic_password");
            siteUrl = getLinkUrl("logic_site");
            submissionUrl = getLinkUrl("logic_submission");
            uploadUrl = getLinkUrl("logic_upload");
            backupUrl = getLinkUrl("logic_backup");
            backupInfoUrl = getLinkUrl("logic_backup_info");
            restoreUrl = getLinkUrl("logic_restore");
            deleteBackupUrl = getLinkUrl("logic_delete_backup");
            loadRemoteUrl = getLinkUrl("logic_load_remote");
            courseRemoteUrl = getLinkUrl("logic_course_remote");
            getUserRelationUrl = getLinkUrl("logic_get_user_reln");
            addUserRelationUrl = getLinkUrl("logic_add_user_reln");
            insertProblemUrl = getLinkUrl("logic_insert_problem");
            updateProblemUrl = getLinkUrl("logic_update_problem");
            deleteProblemUrl = getLinkUrl("logic_delete_problem");
            sqlGeneratorUrl = getLinkUrl("logic_sql_generator");
            serverCredentials = LogicProgram.getCredentials("logic");
            if (nonceUrl == null) {
               return false;
            } else if (verifyUrl == null || userUrl == null || passwordUrl == null || siteUrl == null) {
               return false;
            } else if (submissionUrl == null) {
               return false;
            } else {
               return backupUrl == null || backupInfoUrl == null || restoreUrl == null || deleteBackupUrl == null
                  ? false
                  : courseRemoteUrl != null && loadRemoteUrl != null && getUserRelationUrl != null && addUserRelationUrl != null;
            }
         }
      }
   }

   static ServerUrl getLinkUrl(String s) {
      String s1 = LogicProgram.getLink(s);
      return m875(s1, new ErrorRef(null));
   }

   static String httpGet(URL url) {
      String s = m805(url, null);
      if (DiagnosticsLog.out != null) {
         DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
         DiagnosticsLog.out.println("GET " + url);
         DiagnosticsLog.out.println(s);
      }

      return s;
   }

   static String m805(URL url, HttpDigestAuth httpdigestauth) {
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
         if (LogicProgram.debug) {
            ioexception.printStackTrace(System.err);
         }

         s = null;
      }

      if (urlconnection != null) {
         ((HttpURLConnection)urlconnection).disconnect();
      }

      return s;
   }

   static String m806(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask) {
      return postForm(serverurl, serversession.encodeForm(), networktask);
   }

   static String postForm(ServerUrl serverurl, String s, NetworkTask networktask) {
      Vector vector = new Vector();
      vector.addElement(s.getBytes());
      String s1 = m810(serverurl, vector, "application/x-www-form-urlencoded", networktask, null, null);
      if (DiagnosticsLog.out != null) {
         DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
         DiagnosticsLog.out.println("POST " + serverurl + ": " + s);
         DiagnosticsLog.out.println(s1);
      }

      return s1;
   }

   static String postMultipart(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask, String s) {
      return m809(serverurl, serversession.encodeMultipart(s), networktask, s);
   }

   static String m809(ServerUrl serverurl, Vector vector, NetworkTask networktask, String s) {
      String s1 = m810(serverurl, vector, "multipart/form-data; boundary=" + s, networktask, null, null);
      if (DiagnosticsLog.out != null) {
         DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
         DiagnosticsLog.out.println("POST " + serverurl + ": ");
         int i = vector.size();

         for (int j = 0; j < i; j++) {
            Object object = vector.elementAt(j);
            if (object instanceof String) {
               DiagnosticsLog.out.println((String)object);
            } else if (object instanceof File) {
               DiagnosticsLog.out.println("File: " + ((File)object).getAbsolutePath());
            } else {
               DiagnosticsLog.out.println("Unexpected type: " + object.getClass());
            }
         }

         DiagnosticsLog.out.println(s1);
      }

      return s1;
   }

   static String m810(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      if (networktask == null) {
         networktask = m828(10000L);
      }

      return m811(serverurl, vector, s, networktask, file1, httpdigestauth);
   }

   static String m811(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      URLConnection urlconnection = null;
      Md5OutputStream md5outputstream = new Md5OutputStream();
      m813(md5outputstream, vector);

      String s1;
      try {
         urlconnection = serverurl.m2016();
         if (!(urlconnection instanceof HttpURLConnection)) {
            return null;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("Content-type", s);
         httpurlconnection.setRequestProperty("Content-length", md5outputstream.m924() + "");
         httpurlconnection.setRequestProperty("Content-MD5", new Base64Codec(md5outputstream.digest()).toString());
         httpurlconnection.setConnectTimeout(10000);
         httpurlconnection.setReadTimeout(10000);
         httpurlconnection.setDoOutput(true);
         OutputStream outputstream = httpurlconnection.getOutputStream();
         networktask.m2059(outputstream);
         m813(outputstream, vector);
         networktask.m2059(null);
         httpurlconnection.getHeaderField(0);
         int i = httpurlconnection.getResponseCode();
         if (i < 200 || i >= 300) {
            httpurlconnection.disconnect();
            return null;
         }

         InputStream inputstream = httpurlconnection.getInputStream();
         networktask.m2057(inputstream);
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

         networktask.m2057(null);
      } catch (IOException ioexception) {
         if (LogicProgram.debug) {
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
      Base64Codec base64codec = new Base64Codec();

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
               base64codec.m2003(new String(achar, 0, i));
               bufferedoutputstream.write(base64codec.m2001(true));
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
         bufferedoutputstream.write(base64codec.m2001(false));
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
            Base64Codec base64codec = new Base64Codec();

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
                     base64codec.m1999(abyte, 0, k);
                     outputstream.write(base64codec.m2005(true).getBytes());
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
               outputstream.write((base64codec.m2005(false) + "\r\n").getBytes());
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

   static Object[][] parseResponseTable(String s) {
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

   static String m821(Object[][] aobject, String s, ResponseHandler responsehandler) {
      return m822(aobject, s, responsehandler, false);
   }

   static String m822(Object[][] aobject, String s, ResponseHandler responsehandler, boolean flag) {
      if (aobject == null) {
         m921("no response from server", responsehandler);
         return null;
      } else {
         int i = m818(aobject, s);
         if (i == -1) {
            if (!flag) {
               m921("Could not find \"" + s.toUpperCase() + "\".", responsehandler);
            }

            return null;
         } else if (aobject[i].length < 2) {
            if (!flag) {
               m921("Could not parse \"" + s.toUpperCase() + "\".", responsehandler);
            }

            return null;
         } else {
            String s1 = (String)aobject[i][1];
            if (s1 == null) {
               if (!flag) {
                  m921("Could not parse \"" + s.toUpperCase() + "\".", responsehandler);
               }

               return null;
            } else {
               return s1;
            }
         }
      }
   }

   static Integer m823(Object[][] aobject, String s, ResponseHandler responsehandler) {
      return m824(aobject, s, responsehandler, false);
   }

   static Integer m824(Object[][] aobject, String s, ResponseHandler responsehandler, boolean flag) {
      String s1 = m822(aobject, s, responsehandler, flag);
      if (s1 == null) {
         return null;
      } else {
         Integer integer = LogicProgram.parseInteger(s1.trim());
         if (integer == null) {
            if (!flag) {
               m921("Could not parse \"" + s1 + "\" as a number.", responsehandler);
            }

            return null;
         } else {
            return integer;
         }
      }
   }

   static QueryResult m825(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask, String s, BusyIndicator busyindicator) {
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      m831(serversession, networktask);
      Object[][] aobject = parseResponseTable(postMultipart(serverurl, serversession, networktask, s));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      if (!m834(aobject, serversession)) {
         return null;
      } else {
         Integer integer = m920(aobject, serversession);
         return !m833(integer, serversession) ? null : new QueryResult(aobject, integer);
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

   static NetworkTask m828(long i) {
      String s = "Process Watcher";
      String s1 = "This process has taken over " + i / 1000L + " seconds.\nIf you think it is hung, press Abort.";
      return new NetworkTask(s, s1, i);
   }

   static ServerSession openSession(BusyIndicator busyindicator) {
      ResponseHandler responsehandler;
      do {
         responsehandler = requestNonce(busyindicator, (NetworkTask)null);
      } while (responsehandler instanceof ErrorRef && m922(responsehandler));

      return responsehandler instanceof ServerSession ? (ServerSession)responsehandler : null;
   }

   static ResponseHandler requestNonce(BusyIndicator busyindicator, NetworkTask networktask) {
      Object object = null;

      try {
         object = "user=" + URLEncoder.encode(serverCredentials.user, "UTF-8");
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, (String)object, networktask));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      ErrorRef errorref = new ErrorRef(null);
      Integer integer = m920(aobject, errorref);
      if (integer == null) {
         return errorref;
      } else if (integer != 0) {
         m921("status = " + integer, errorref);
         return errorref;
      } else {
         String s = m821(aobject, "nonce", errorref);
         return (ResponseHandler)(s == null ? errorref : new ServerSession(serverCredentials, s));
      }
   }

   static boolean m831(ServerSession serversession, NetworkTask networktask) {
      Object object = null;

      try {
         object = "user=" + URLEncoder.encode(serverCredentials.user, "UTF-8");
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, (String)object, networktask));
      Integer integer = m920(aobject, serversession);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         m921("status = " + integer, serversession);
         return false;
      } else {
         String s = m821(aobject, "nonce", serversession);
         if (s == null) {
            return false;
         } else {
            serversession.m49(s);
            return true;
         }
      }
   }

   static boolean m832(ServerSession serversession, BusyIndicator busyindicator) {
      String s = "";

      try {
         s = s + "user=" + URLEncoder.encode(serversession.credentials.user, "UTF-8") + "&";
         s = s + "nonce=" + URLEncoder.encode(serversession.nonce, "UTF-8") + "&";
         s = s + "auth=" + Scrambler.md5Hex(serversession.credentials.user + ":" + serversession.nonce + ":" + serversession.credentials.password);
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, s, (NetworkTask)null));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      Integer integer = m920(aobject, serversession);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         m921("status: " + integer, serversession);
         return false;
      } else {
         return true;
      }
   }

   static boolean m833(Integer integer, ResponseHandler responsehandler) {
      if (integer == null) {
         return false;
      } else if (integer < 0) {
         if (integer == -1) {
            m921("repeated nonce failure", responsehandler);
         } else if (integer == -2) {
            m921("internal authentication failure", responsehandler);
         } else {
            m921("unknown authentication error: " + integer, responsehandler);
         }

         return false;
      } else {
         return true;
      }
   }

   static boolean m834(Object[][] aobject, ServerSession serversession) {
      String s = m821(aobject, "dgst", serversession);
      if (s == null) {
         return false;
      } else {
         String s1 = m821(aobject, "cnonce", serversession);
         if (s1 == null) {
            return false;
         } else {
            String s2 = m821(aobject, "auth", serversession);
            if (s2 == null) {
               return false;
            } else if (serversession.cnonce != null && serversession.cnonce.equals(s1)) {
               String[] astring = ServerSession.m54(s);
               String s3 = "";
               int i = astring == null ? 0 : astring.length;

               for (int j = 0; j < i; j++) {
                  int k = m818(aobject, astring[j]);
                  if (k == -1) {
                     m921("Could not find \"" + astring[j] + "\".", serversession);
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

               String s5 = Scrambler.md5Hex(serversession.cnonce + ":" + serversession.credentials.password + ":" + Scrambler.md5Hex(s3));
               if (!s5.equals(s2)) {
                  m921("signature mismatch", serversession);
                  return false;
               } else {
                  return true;
               }
            } else {
               m921("client nonce mismatch", serversession);
               return false;
            }
         }
      }
   }

   static Submission prepareSubmission(BusyIndicator busyindicator) {
      if (!demoMode && !LogicProgram.noNetwork) {
         ServerSession serversession = openSession(busyindicator);
         if (serversession == null) {
            return null;
         } else {
            UserInfo userinfo = LogicProgram.user;
            C_a_F c_a_f = C_a_F.m1642(serversession, userinfo, busyindicator, 1);
            if (c_a_f != null && AccountManager.m1863(userinfo, "not048", false, false) != null) {
               if (userinfo.dirty) {
                  userinfo.save();
               }

               int i = userinfo.f665 == null ? 0 : userinfo.f665;
               return new Submission(i, c_a_f.f980, serversession, c_a_f.f979);
            } else {
               m832(serversession, busyindicator);
               return null;
            }
         }
      } else {
         MessageDialog.showMessage(Message.get("not039"), null, null, null);
         return null;
      }
   }

   static boolean submit(Submission submission, BusyIndicator busyindicator) {
      if (submission.m2() && !demoMode) {
         Message message = Message.get("not070");
         NetworkTask networktask = new NetworkTask(message.id, message.text);

         boolean flag;
         do {
            flag = submitOnce(submission, busyindicator, networktask);
         } while (!flag && m922(submission));

         return flag;
      } else {
         return false;
      }
   }

   static boolean submitOnce(Submission submission, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "logic_user_uid.logic_course_uid.evaluation.tproblem_md5.twork.problem_name.module.help_count.duration";
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", submission.f3 + "");
      hashtable.put("logic_course_uid", submission.f2 + "");
      hashtable.put("evaluation", submission.evaluation);
      hashtable.put("tproblem_md5", submission.problemMd5);
      hashtable.put("twork", submission.work);
      hashtable.put("problem_name", submission.problemName);
      hashtable.put("module", submission.module);
      hashtable.put("help_count", submission.helpCount + "");
      hashtable.put("duration", submission.duration + "");
      if (f488 != null) {
         s = s + ".wave_token";
         hashtable.put("wave_token", f488);
      }

      if (submission.f1 != null) {
         s = s + ".ip";
         hashtable.put("ip", submission.f1);
      }

      submission.session.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(submissionUrl, submission.session, networktask, "submit"));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      if (!m834(aobject, submission.session)) {
         return false;
      } else {
         Integer integer = m920(aobject, submission);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(submissionUrl, submission.session, networktask, "submit", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, submission);
               return false;
            } else {
               submission.timestamp = m822(aobject, "dtTimestamp", submission, true);
               submission.submissionUid = m822(aobject, "logic_submission_uid", submission, true);
               return true;
            }
         }
      }
   }

   static void m838(Submission submission, BusyIndicator busyindicator) {
      m832(submission.session, busyindicator);
      submission.session = null;
   }

   static boolean m839(File file1) {
      File file2 = new File(LogicProgram.configDir, "work.zip");

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

   static C_LD m840(BusyIndicator busyindicator) {
      if (!demoMode && f480 && !LogicProgram.noNetwork && uploadUrl != null) {
         ServerSession serversession = openSession(busyindicator);
         if (serversession == null) {
            return null;
         } else {
            UserInfo userinfo = LogicProgram.user;
            if (userinfo.f664 == null) {
               Boolean obool = m906(serversession, userinfo, null, null);
               if (obool == null || !obool) {
                  m832(serversession, null);
                  return null;
               }
            }

            ErrorRef errorref = UserSetup.m2102(null, "instructor");
            if (errorref != null) {
               String s = errorref.m716();
               if (s != null) {
                  MessageDialog.showMessage(Message.get(s), errorref.f428, null, null);
               }

               m832(serversession, null);
               return null;
            } else {
               CourseInfo courseinfo = m861();
               if (courseinfo.f623 == null) {
                  m832(serversession, null);
                  return null;
               } else {
                  return new C_LD(serversession, userinfo.f664, courseinfo.f623, FormulaParser.getSyntax());
               }
            }
         }
      } else {
         MessageDialog.showMessage(Message.get("not039"), null, null, null);
         return null;
      }
   }

   static boolean m841(C_LD c_ld, BusyIndicator busyindicator) {
      if (c_ld == null) {
         return false;
      } else {
         Message message = Message.get("not070");
         NetworkTask networktask = new NetworkTask(message.id, message.text);

         boolean flag;
         do {
            flag = m842(c_ld, busyindicator, networktask);
         } while (!flag && m922(c_ld));

         return flag;
      }
   }

   static boolean m842(C_LD c_ld, BusyIndicator busyindicator, NetworkTask networktask) {
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

      c_ld.f506.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(uploadUrl, c_ld.f506, networktask, "upload"));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      if (!m834(aobject, c_ld.f506)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_ld);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(uploadUrl, c_ld.f506, networktask, "upload", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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

   static void m843(C_LD c_ld, BusyIndicator busyindicator) {
      m832(c_ld.f506, busyindicator);
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
            int j = WORK_FILES.length;

            for (int k = 0; k < j; k++) {
               File file2 = new File(file1, WORK_FILES[k]);
               if (file2.exists() && file2.isFile()) {
                  zipoutputstream.putNextEntry(new ZipEntry("work/" + WORK_FILES[k]));
                  fileinputstream = new FileInputStream(file2);

                  int i;
                  while ((i = fileinputstream.read(abyte)) != -1) {
                     zipoutputstream.write(abyte, 0, i);
                  }

                  fileinputstream.close();
                  fileinputstream = null;
               }
            }

            j = DATA_FILES.length;

            for (int i1 = 0; i1 < j; i1++) {
               File file3 = new File(file1, DATA_FILES[i1]);
               if (file3.exists() && file3.isFile()) {
                  zipoutputstream.putNextEntry(new ZipEntry("work/" + DATA_FILES[i1]));
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
      File file2 = new File(LogicProgram.configDir, "work.zip");

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
               int j = LogicProgram.m1051(WORK_FILES, s1.substring("work/".length()));
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
      for (int i = 2; i < WORK_FILES.length; i++) {
         if (new File(LogicProgram.workDir, WORK_FILES[i]).exists()) {
            return true;
         }
      }

      return false;
   }

   static Hashtable m850() {
      CourseInfo courseinfo = m861();
      Institution institutionx = Institution.m1293(courseinfo.f620);
      return Message.params("dbsite", courseinfo.f620, "dbterm", institutionx.m1299(courseinfo.f621), "dbcourse", courseinfo.f622);
   }

   static String m851(String s) {
      String s1 = "logic/";
      return s.toLowerCase().startsWith(s1) ? "work/" + s.substring(s1.length()) : s;
   }

   static C_XA m852(UserInfo userinfo, BusyIndicator busyindicator) {
      ServerSession serversession = openSession(busyindicator);
      if (serversession == null) {
         return null;
      } else {
         C_a_F c_a_f = f489 != null ? f489 : C_a_F.m1642(serversession, userinfo, busyindicator, 1);
         if (c_a_f == null) {
            m832(serversession, busyindicator);
            return null;
         } else {
            return new C_XA(c_a_f.f980, serversession, c_a_f.f979);
         }
      }
   }

   static boolean m853(C_XA c_xa, NetworkTask networktask) {
      if (c_xa.m1462() && c_xa.f868 != null) {
         boolean flag;
         do {
            flag = m854(c_xa, networktask);
         } while (!flag && m922(c_xa));

         return flag;
      } else {
         return false;
      }
   }

   static boolean m854(C_XA c_xa, NetworkTask networktask) {
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

      c_xa.f867.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(backupUrl, c_xa.f867, networktask, "backup"));
      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(backupUrl, c_xa.f867, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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

   static boolean m855(C_FC c_fc, NetworkTask networktask, boolean flag) {
      if (insertProblemUrl != null && updateProblemUrl != null) {
         c_fc.f305 = openSession(null);
         if (c_fc.f305 == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = m856(c_fc, networktask, flag);
            } while (obool == null && m922(c_fc.f305));

            m832(c_fc.f305, null);
            return obool != null && obool;
         }
      } else {
         return false;
      }
   }

   static Boolean m856(C_FC c_fc, NetworkTask networktask, boolean flag) {
      String s = "problem_name.tProblem.tProblem_md5.tWeb_form_problem.common_name.comment.version.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", m815(c_fc.f306));
      hashtable.put("tProblem", m815(c_fc.f307));
      hashtable.put("tProblem_md5", m815(c_fc.m538()));
      hashtable.put("tWeb_form_problem", m815(c_fc.m539()));
      hashtable.put("common_name", m815(c_fc.f308));
      hashtable.put("comment", m815(c_fc.m540()));
      hashtable.put("version", m816(c_fc.f310));
      hashtable.put("syntax", m815(FormulaParser.getSyntax() + ""));
      c_fc.f305.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(
         postMultipart(flag ? updateProblemUrl : insertProblemUrl, c_fc.f305, networktask, flag ? "update_problem" : "insert_problem")
      );
      if (!m834(aobject, c_fc.f305)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_fc);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(insertProblemUrl, c_fc.f305, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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

   static boolean m857(C_FC c_fc, NetworkTask networktask) {
      if (deleteProblemUrl == null) {
         return false;
      } else {
         c_fc.f305 = openSession(null);
         if (c_fc.f305 == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = m858(c_fc, networktask);
            } while (obool == null && m922(c_fc.f305));

            m832(c_fc.f305, null);
            return obool != null && obool;
         }
      }
   }

   static Boolean m858(C_FC c_fc, NetworkTask networktask) {
      String s = "problem_name.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", c_fc.f306);
      hashtable.put("syntax", FormulaParser.getSyntax());
      c_fc.f305.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(deleteProblemUrl, c_fc.f305, networktask, "delete_problem"));
      if (!m834(aobject, c_fc.f305)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_fc);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(insertProblemUrl, c_fc.f305, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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
      if (LogicProgram.getCredentials("exam") == null) {
         return false;
      } else {
         ServerSession serversession = openSession(null);
         if (serversession == null) {
            return true;
         } else {
            Boolean obool;
            do {
               obool = m860(serversession);
            } while (obool == null && m922(serversession));

            m832(serversession, null);
            return obool == null || obool;
         }
      }
   }

   static Boolean m860(ServerSession serversession) {
      CourseInfo courseinfo = m861();
      if (courseinfo == null) {
         return null;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", courseinfo.f623 + "");
         hashtable.put("arch", LogicProgram.arch);
         serversession.setParams(hashtable, "logic_course_uid");
         Object[][] aobject = parseResponseTable(postMultipart(loadRemoteUrl, serversession, (NetworkTask)null, "load_remote"));
         if (!m834(aobject, serversession)) {
            return null;
         } else {
            Integer integer = m920(aobject, serversession);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = m825(loadRemoteUrl, serversession, (NetworkTask)null, "load_remote", null);
                  if (queryresult == null) {
                     return null;
                  }

                  aobject = queryresult.f1362;
                  integer = queryresult.f1363;
               }

               if (integer != 0) {
                  m921("no remote data", serversession);
                  return null;
               } else {
                  String s1 = f480 ? f479 : textVersion;
                  String s = m821(aobject, f480 ? "admin version" : "text version", serversession);
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

   static CourseInfo m861() {
      CourseInfo[] acourseinfo = CourseInfo.m1120(institution, term, course);
      return acourseinfo != null && acourseinfo.length != 0 ? acourseinfo[0] : null;
   }

   static int m862(UserInfo userinfo) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return 1;
      } else {
         int i = m863(serversession, userinfo);
         m832(serversession, null);
         return i;
      }
   }

   static int m863(ServerSession serversession, UserInfo userinfo) {
      int i;
      do {
         i = m864(serversession, userinfo);
      } while (i == 4 && m922(serversession));

      return i;
   }

   static int m864(ServerSession serversession, UserInfo userinfo) {
      NetworkTask networktask = new NetworkTask(LPInfo.programName, "Checking for updates...");
      Integer integer1 = userinfo.m1164();
      if (integer1 == null) {
         return 1;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", integer1 + "");
         hashtable.put("arch", LogicProgram.arch);
         serversession.setParams(hashtable, "logic_course_uid.arch");
         Object[][] aobject = parseResponseTable(postMultipart(loadRemoteUrl, serversession, networktask, "load_remote"));
         if (!m834(aobject, serversession)) {
            return 4;
         } else {
            Integer integer = m920(aobject, serversession);
            if (integer == null) {
               return 4;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = m825(loadRemoteUrl, serversession, networktask, "load_remote", null);
                  if (queryresult == null) {
                     return 4;
                  }

                  aobject = queryresult.f1362;
                  integer = queryresult.f1363;
               }

               if (integer != 0) {
                  m921("no remote data", serversession);
                  return 4;
               } else {
                  C_j_B c_j_b = new C_j_B();
                  String s6 = m821(aobject, "code version", serversession);
                  if (s6 == null) {
                     return 1;
                  } else {
                     String s = s6.trim();
                     s6 = m821(aobject, "text version", serversession);
                     if (s6 == null) {
                        return 1;
                     } else {
                        String s1 = s6.trim();
                        s6 = m821(aobject, "loader version", serversession);
                        if (s6 == null) {
                           return 1;
                        } else {
                           c_j_b.f1183 = s6.trim();
                           s6 = m821(aobject, "download URL", serversession);
                           if (s6 == null) {
                              return 1;
                           } else {
                              ServerUrl serverurl = m875(s6.trim(), serversession);
                              if (serverurl == null) {
                                 return 1;
                              } else {
                                 s6 = m821(aobject, "download text", serversession);
                                 if (s6 == null) {
                                    return 1;
                                 } else {
                                    String s2 = s6.trim();
                                    s6 = m821(aobject, "update URL", serversession);
                                    if (s6 == null) {
                                       return 1;
                                    } else {
                                       ServerUrl serverurl1 = m875(s6.trim(), serversession);
                                       if (serverurl1 == null) {
                                          return 1;
                                       } else {
                                          s6 = m821(aobject, "update text", serversession);
                                          if (s6 == null) {
                                             return 1;
                                          } else {
                                             String s3 = s6.trim();
                                             s6 = m821(aobject, "loader URL", serversession);
                                             if (s6 == null) {
                                                return 1;
                                             } else {
                                                c_j_b.f1182 = m875(s6.trim(), serversession);
                                                if (c_j_b.f1182 == null) {
                                                   return 1;
                                                } else {
                                                   s6 = m821(aobject, "admin version", serversession);
                                                   if (s6 == null) {
                                                      return 1;
                                                   } else {
                                                      String s4 = s6.trim();
                                                      s6 = m821(aobject, "admin url", serversession);
                                                      if (s6 == null) {
                                                         return 1;
                                                      } else {
                                                         ServerUrl serverurl2 = m875(s6.trim(), serversession);
                                                         s6 = m821(aobject, "admin text", serversession);
                                                         if (s6 == null) {
                                                            return 1;
                                                         } else {
                                                            String s5 = s6.trim();
                                                            s6 = m822(aobject, "wave token", serversession, true);
                                                            f488 = s6 == null ? null : s6.trim();
                                                            c_j_b.f1184 = userinfo;
                                                            int i = 0;
                                                            boolean flag = m865(userinfo);
                                                            if (!f484 && !m867(s, LogicProgram.codeVersion, flag) && !LogicProgram.f580) {
                                                               f484 = true;
                                                               c_j_b.f1180 = serverurl;
                                                               c_j_b.f1181 = s2;
                                                               c_j_b.f1187 = m870(s2);
                                                               if (c_j_b.f1187 == 0 && LogicProgram.f583 && !f486) {
                                                                  c_j_b.f1186 = true;
                                                               }

                                                               c_j_b.f1185 = false;

                                                               do {
                                                                  i = m869(c_j_b, c_j_b.f1186 ? "not046" : "not033");
                                                               } while (c_j_b.f1186 && i == 5);
                                                            } else if (!flag || !m867(s1, textVersion, true)) {
                                                               c_j_b.f1180 = serverurl1;
                                                               c_j_b.f1181 = s3;
                                                               c_j_b.f1187 = 1;
                                                               c_j_b.f1185 = true;
                                                               i = m869(c_j_b, flag ? "not034" : "not047");
                                                            } else if (!f485 && serverurl2 != null && (f479 != null || m868(userinfo))) {
                                                               f485 = true;
                                                               flag = m866(userinfo);
                                                               if (!flag || !m867(s4, f479, true)) {
                                                                  c_j_b.f1180 = serverurl2;
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

   static boolean m865(UserInfo userinfo) {
      if (!institution.equalsIgnoreCase(userinfo.getInstitution())) {
         return false;
      } else if (!term.equalsIgnoreCase(userinfo.getTerm())) {
         return false;
      } else {
         return !course.equalsIgnoreCase(userinfo.getClassName()) ? false : userinfo.m1163() != null;
      }
   }

   static boolean m866(UserInfo userinfo) {
      if (f476 == null || !f476.equalsIgnoreCase(userinfo.getInstitution())) {
         return false;
      } else if (f477 == null || !f477.equalsIgnoreCase(userinfo.getTerm())) {
         return false;
      } else {
         return f478 == null || !f478.equalsIgnoreCase(userinfo.getClassName()) ? false : userinfo.m1163() != null;
      }
   }

   static boolean m867(String s, String s1, boolean flag) {
      int i = s.compareTo(s1);
      return flag && !s.endsWith("x") && !s1.endsWith("x") ? i <= 0 : i == 0;
   }

   static boolean m868(UserInfo userinfo) {
      if (!userinfo.m1171("instructor")) {
         return false;
      } else {
         Message message = Message.get("not060");
         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, null, null, c_b_e);
         return f481 = c_b_e.f1027 == 1;
      }
   }

   static int m869(C_j_B c_j_b, String s) {
      if (c_j_b.f1187 == 2) {
         return m873(c_j_b);
      } else {
         Message message = Message.get(s);
         C_SE c_se = new C_SE(message.buttons, c_j_b);
         Hashtable hashtable = c_j_b.f1184.m1165();
         Message.putParam(hashtable, "text", c_j_b.f1181);
         MessageDialog.showMessage(message, hashtable, null, c_se);
         return c_se.f772;
      }
   }

   static int m870(String s) {
      String s1 = s.trim().toUpperCase();
      return !s1.startsWith("HTTP://") && !s1.startsWith("HTTPS://") ? 0 : 2;
   }

   static boolean m871(C_j_B c_j_b, boolean flag) {
      Integer integer = LogicProgram.instanceGuard == null ? null : LogicProgram.instanceGuard.m1935();
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = new BufferedWriter(new FileWriter(LogicProgram.f560));
         bufferedwriter.write("source:" + c_j_b.f1180);
         bufferedwriter.newLine();
         bufferedwriter.write("configDir:" + LogicProgram.configDir);
         bufferedwriter.newLine();
         bufferedwriter.write("rootDir:" + LogicProgram.f554);
         bufferedwriter.newLine();
         bufferedwriter.write("progDir:" + LogicProgram.progDir);
         bufferedwriter.newLine();
         bufferedwriter.write("linkDir:" + LogicProgram.linkDir);
         bufferedwriter.newLine();
         bufferedwriter.write("arch:" + LogicProgram.arch);
         bufferedwriter.newLine();
         bufferedwriter.write("type:" + (c_j_b.f1185 ? "local" : "core"));
         bufferedwriter.newLine();
         if (integer != null && !LogicProgram.overrides.m2082("SoloCheck", "yes").equalsIgnoreCase("no")) {
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
         bufferedwriter.write("institution:" + c_j_b.f1184.getInstitution());
         bufferedwriter.newLine();
         bufferedwriter.write("term:" + c_j_b.f1184.getTerm());
         bufferedwriter.newLine();
         bufferedwriter.write("course:" + c_j_b.f1184.getClassName());
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
            MessageDialog.showMessage(Message.get("not062"), null, null, null);
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
      Message message;
      if (LogicProgram.f567 != null) {
         message = Message.get("not084");
      } else {
         message = Message.get("not085");
      }

      C_p_F c_p_f = new C_p_F(message.buttons, c_j_b.f1181);
      MessageDialog.showMessage(message, null, null, c_p_f);
      return c_p_f.f1338;
   }

   static int m874(C_j_B c_j_b) {
      while (true) {
         HttpDownloader httpdownloader = new HttpDownloader();
         if (!httpdownloader.m1856(c_j_b.f1180, c_j_b.f1185 ? LogicProgram.configDir : LogicProgram.f554, null)) {
            Message message = Message.get("not086");
            C_b_E c_b_e = new C_b_E(message.buttons);
            MessageDialog.showMessage(message, null, null, c_b_e);
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

   static ServerUrl m875(String s, ResponseHandler responsehandler) {
      if (s == null) {
         return null;
      } else {
         try {
            return new ServerUrl(s);
         } catch (MalformedURLException malformedurlexception) {
            m921("could not interpret " + s + " as a URL.", responsehandler);
            return null;
         }
      }
   }

   static File m876(ServerUrl serverurl, C_j_B c_j_b) {
      File file1 = new File(LogicProgram.progDir, "loader.jar");
      String s = m881(file1, "edu.ucla.phil.logic.LPUpdateLoader", "version");
      if (s == null || c_j_b.f1183 == null || c_j_b.f1183.compareTo(s) > 0) {
         HttpDownloader httpdownloader = new HttpDownloader();
         if (!httpdownloader.m1856(serverurl, file1, m828(10000L))) {
            MessageDialog.showMessage(Message.get("not061"), null, null, null);
            return null;
         }
      }

      return file1;
   }

   static File resolvePath(File file1, String s) {
      File file2 = new File(s);
      return file2.isAbsolute() ? file2 : new File(file1, s);
   }

   static boolean m878(File file1, ResponseHandler responsehandler) {
      String[] astring = new String[]{"java", "-Dload.info=" + LogicProgram.f560, "-jar", file1.getPath()};
      if (LogicProgram.arch.equals("windows")) {
         astring[0] = "javaw";
      } else if (LogicProgram.arch.equals("macos")) {
         astring[0] = System.getProperty("java.home") + "/bin/java";
      }

      try {
         Runtime.getRuntime().exec(astring, null, LogicProgram.configDir);
         m879();
         return true;
      } catch (IOException ioexception) {
         if (responsehandler == null) {
            MessageDialog.showMessage(Message.get("not063"), null, null, null);
         } else {
            responsehandler.m4("not063", null);
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

   static String[] m882(UserInfo userinfo, NetworkTask networktask) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         String[] astring = m883(serversession, userinfo, networktask);
         m832(serversession, null);
         return astring;
      }
   }

   static String[] m883(ServerSession serversession, UserInfo userinfo, NetworkTask networktask) {
      if (userinfo.f664 == null) {
         Boolean obool = m906(serversession, userinfo, null, networktask);
         if (obool == null || !obool) {
            return null;
         }
      }

      userinfo.f665 = userinfo.m1164();
      if (userinfo.f665 == null) {
         return null;
      } else {
         String[] astring;
         do {
            astring = m884(serversession, userinfo, networktask);
         } while (astring == null && m922(serversession));

         return astring;
      }
   }

   static String[] m884(ServerSession serversession, UserInfo userinfo, NetworkTask networktask) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", userinfo.f664 + "");
      hashtable.put("logic_course_uid", userinfo.f665 + "");
      serversession.setParams(hashtable, "logic_user_uid.logic_course_uid");
      Object[][] aobject = parseResponseTable(postMultipart(getUserRelationUrl, serversession, networktask, "getUserCourseRelations"));
      if (!m834(aobject, serversession)) {
         return null;
      } else {
         Integer integer = m920(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(getUserRelationUrl, serversession, networktask, "getUserCourseRelations", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            if (integer != 0) {
               m921("no remote data", serversession);
               return null;
            } else {
               String s = m821(aobject, "reln count", serversession);
               if (s == null) {
                  return null;
               } else {
                  Integer integer1 = LogicProgram.parseInteger(s.trim());
                  if (integer1 == null) {
                     m921("Could not parse \"" + s + "\" as a number.", serversession);
                     return null;
                  } else {
                     int i = integer1;
                     String[] astring = new String[i];

                     for (int j = 0; j < i; j++) {
                        s = m821(aobject, "reln_" + (j + 1), serversession);
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

   static boolean m885(UserInfo userinfo, String s, NetworkTask networktask) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return false;
      } else {
         boolean flag = m886(serversession, userinfo, s, networktask);
         m832(serversession, null);
         return flag;
      }
   }

   static boolean m886(ServerSession serversession, UserInfo userinfo, String s, NetworkTask networktask) {
      if (demoMode) {
         return false;
      } else if (userinfo.f666 != null && LogicProgram.m1051(userinfo.f666, s.toLowerCase()) != -1) {
         return true;
      } else {
         if (userinfo.f664 == null) {
            Boolean obool = m906(serversession, userinfo, null, networktask);
            if (obool == null || !obool) {
               return false;
            }
         }

         userinfo.f665 = userinfo.m1164();
         if (userinfo.f665 == null) {
            return false;
         } else {
            boolean flag;
            do {
               flag = m887(serversession, userinfo, s, networktask);
            } while (!flag && m922(serversession));

            return flag;
         }
      }
   }

   static boolean m887(ServerSession serversession, UserInfo userinfo, String s, NetworkTask networktask) {
      userinfo.f666 = null;
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", userinfo.f664 + "");
      hashtable.put("logic_course_uid", userinfo.f665 + "");
      hashtable.put("relation", s);
      serversession.setParams(hashtable, "logic_user_uid.logic_course_uid.relation");
      Object[][] aobject = parseResponseTable(postMultipart(addUserRelationUrl, serversession, networktask, "addUserCourseRelation"));
      Integer integer = m920(aobject, serversession);
      if (integer == null) {
         return false;
      } else {
         if (integer == -1) {
            QueryResult queryresult = m825(addUserRelationUrl, serversession, networktask, "addUserCourseRelation", null);
            if (queryresult == null) {
               return false;
            }

            aobject = queryresult.f1362;
            integer = queryresult.f1363;
         }

         if (integer != 0) {
            m921("status = " + integer, serversession);
            return false;
         } else {
            userinfo.f666 = m883(serversession, userinfo, networktask);
            return true;
         }
      }
   }

   static Boolean m888(UserInfo userinfo, String s, NetworkTask networktask) {
      if (s == null) {
         return null;
      } else {
         ServerSession serversession = openSession(null);
         if (serversession == null) {
            return Boolean.FALSE;
         } else if (!m886(serversession, userinfo, "student", networktask)) {
            m832(serversession, null);
            return Boolean.FALSE;
         } else {
            C_XA c_xa = new C_XA(userinfo.f664, serversession, "password");
            c_xa.f868 = s;

            Boolean obool;
            try {
               if (!m889(c_xa, null, networktask)) {
                  return Boolean.FALSE;
               }

               if (c_xa.m1463() != 0) {
                  return null;
               }

               if ((c_xa.f869 = m844(LogicProgram.workDir)) != null) {
                  return m853(c_xa, networktask) ? Boolean.TRUE : Boolean.FALSE;
               }

               obool = Boolean.FALSE;
            } finally {
               m895(c_xa, null);
            }

            return obool;
         }
      }
   }

   static boolean m889(C_XA c_xa, BusyIndicator busyindicator, NetworkTask networktask) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m890(c_xa, busyindicator, networktask);
         } while (!flag && m922(c_xa));

         return flag;
      }
   }

   static boolean m890(C_XA c_xa, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "userid";
      Hashtable hashtable = new Hashtable();
      hashtable.put("userid", c_xa.f865 + "");
      c_xa.f867.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(backupInfoUrl, c_xa.f867, networktask, "backup_info"));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(backupInfoUrl, c_xa.f867, networktask, "backup_info", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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
                     Integer integer1 = LogicProgram.parseInteger(((String)aobject1[4]).trim());
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

   static boolean m891(C_XA c_xa, NetworkTask networktask) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m892(c_xa, networktask);
         } while (!flag && m922(c_xa));

         if (!flag) {
            m904(LogicProgram.workDir);
         }

         return flag;
      }
   }

   static boolean m892(C_XA c_xa, NetworkTask networktask) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", c_xa.f871 + "");
      c_xa.f867.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(restoreUrl, c_xa.f867, networktask, "restore"));
      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(restoreUrl, c_xa.f867, networktask, "restore", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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

   static boolean m893(C_XA c_xa, BusyIndicator busyindicator, NetworkTask networktask) {
      if (!c_xa.m1462()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = m894(c_xa, busyindicator, networktask);
         } while (!flag && m922(c_xa));

         return flag;
      }
   }

   static boolean m894(C_XA c_xa, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", c_xa.f871 + "");
      if (busyindicator != null) {
         busyindicator.m2162(true);
      }

      c_xa.f867.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(deleteBackupUrl, c_xa.f867, networktask, "delete_backup"));
      if (busyindicator != null) {
         busyindicator.m2162(false);
      }

      if (!m834(aobject, c_xa.f867)) {
         return false;
      } else {
         Integer integer = m920(aobject, c_xa);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(deleteBackupUrl, c_xa.f867, networktask, "delete_backup", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
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

   static void m895(C_XA c_xa, BusyIndicator busyindicator) {
      m832(c_xa.f867, busyindicator);
      c_xa.f867 = null;
   }

   static boolean m896(String s, BusyIndicator busyindicator) {
      if (s == null) {
         MessageDialog.showMessage(Message.get("not041"), null, null, null);
         return false;
      } else if (!MainMenu.m2211()) {
         return false;
      } else {
         C_XA c_xa = m852(LogicProgram.user, busyindicator);
         if (c_xa == null) {
            return false;
         } else {
            c_xa.f868 = s;
            Message message = Message.get("not020");
            NetworkTask networktask = new NetworkTask(message.id, message.text);
            c_xa.f869 = m844(LogicProgram.workDir);
            if (c_xa.f869 == null) {
               m895(c_xa, busyindicator);
               MessageDialog.showMessage(Message.get("not015"), null, null, null);
               return false;
            } else if (!m853(c_xa, networktask)) {
               m895(c_xa, busyindicator);
               return false;
            } else {
               if (m889(c_xa, busyindicator, networktask)) {
                  c_xa.m1468(LogicProgram.f566, busyindicator, networktask);
               }

               m895(c_xa, busyindicator);
               LogicProgram.f581 = false;
               MessageDialog.showMessage(Message.get("not017"), null, null, null);
               return true;
            }
         }
      }
   }

   static Boolean m897(String s, String s1, NewUserInfo newuserinfo) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         Boolean obool = m898(s, s1, serversession, newuserinfo);
         m832(serversession, null);
         return obool;
      }
   }

   static Boolean m898(String s, String s1, ServerSession serversession, NewUserInfo newuserinfo) {
      Boolean obool = m906(serversession, newuserinfo, null, (NetworkTask)null);
      if (obool != null && obool) {
         C_XA c_xa = new C_XA(newuserinfo.f664, serversession, "hasBackup");
         if (!m889(c_xa, null, (NetworkTask)null)) {
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

   static boolean m899(String s, String s1, NewUserInfo newuserinfo) {
      if (s1 == null && s == null) {
         MessageDialog.showMessage(Message.get("not040"), null, null, null);
         return false;
      } else if (newuserinfo == null && (newuserinfo = AccountManager.m1869()) == null) {
         return false;
      } else {
         C_XA c_xa = m852(newuserinfo, null);
         if (c_xa == null) {
            return false;
         } else if (!m889(c_xa, null, (NetworkTask)null)) {
            m895(c_xa, null);
            return false;
         } else {
            boolean flag = true;
            if (s1 != null) {
               c_xa.f868 = s1;
               if (c_xa.m1463() != 0) {
                  flag = false;
                  if (LogicProgram.getCredentials("restore") != null) {
                     ErrorRef errorref = UserSetup.m2103("restore", null, "not095");
                     if (errorref != null) {
                        String s2 = errorref.m716();
                        if (s2 != null) {
                           MessageDialog.showMessage(Message.get(s2), errorref.f428, null, null);
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

            if (!UserSetup.m2116(c_xa)) {
               m895(c_xa, null);
               return false;
            } else {
               Message message = Message.get("not021");
               NetworkTask networktask = new NetworkTask(message.id, message.text);
               if (!m891(c_xa, networktask)) {
                  m895(c_xa, null);
                  return false;
               } else {
                  boolean flag1 = m847(c_xa.f869, flag, LogicProgram.configDir);
                  if (flag1) {
                     LogicProgram.f581 = false;
                  } else {
                     MessageDialog.showMessage(Message.get("not016"), null, null, null);
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
         Hashtable hashtable = Message.params("source", file1.getParent(), "dest", file2.toString());
         Message message = Message.get("not026");
         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, hashtable, null, c_b_e);
         if (c_b_e.f1027 != 0) {
            return false;
         }

         if (file2.equals(LogicProgram.configDir)) {
            m903(false);
         } else {
            m904(new File(file2, "work"));
         }

         ErrorRef errorref = new ErrorRef("not025", hashtable);

         while (!(flag = m901(file1, file2)) && m922(errorref)) {
         }

         if (flag) {
            LogicProgram.f582 = false;
            MessageDialog.showMessage(Message.get("not024"), null, null, null);
         }
      }

      return flag;
   }

   static boolean m901(File file1, File file2) {
      if (!file1.exists()) {
         return false;
      } else {
         boolean flag = false;
         Message message = Message.get("not066");
         ProgressDialog progressdialog = new ProgressDialog(message.id, message.text, false);
         progressdialog.m1284(20, 10);
         String s = m844(file1);
         if (s != null) {
            flag = m847(s, false, file2);
         }

         progressdialog.dispose();
         return flag;
      }
   }

   static int m902() {
      return m903(true);
   }

   static int m903(boolean flag) {
      Object object = null;
      if (flag) {
         Message message = Message.get("not030");
         C_b_E c_b_e = new C_b_E(message.buttons);
         MessageDialog.showMessage(message, null, null, c_b_e);
         if (c_b_e.f1027 != 0) {
            return 1;
         }
      }

      LogicProgram.f581 = false;
      LogicProgram.f582 = false;
      LogicProgram.m979();
      object = m904(LogicProgram.workDir);
      LogicProgram.f583 = false;
      LogicProgram.m978();
      object = LogicProgram.m980((String[])object);
      if (object == null) {
         return 0;
      } else {
         int k = ((Object[])object).length;

         for (int i = 0; i < k; i++) {
            DiagnosticsLog.m1905("file not deleted: " + ((Object[])object)[i]);
         }

         if (flag) {
            Dimension dimension = new Dimension(240, 300);
            ModuleFrame moduleframe = new ModuleFrame();
            SizedPanel sizedpanel = new SizedPanel();
            sizedpanel.setLayout(new BorderLayout());
            SizedPanel sizedpanel1 = new SizedPanel();
            sizedpanel1.setLayout(new C_m_A());

            for (int j = 0; j < k; j++) {
               sizedpanel1.add(new C_ZE((String)((Object[])object)[j]));
            }

            SizedPanel sizedpanel2 = new SizedPanel();
            sizedpanel2.setLayout(new C_m_A());
            sizedpanel2.add(new C_ZE(Message.getText("not032")));
            sizedpanel.add(sizedpanel2, "North");
            JScrollPane jscrollpane = new JScrollPane(sizedpanel1);
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"OK"};
            MessageDialog messagedialog = new MessageDialog(moduleframe, "not032", sizedpanel, astring);
            messagedialog.setSize(dimension);
            messagedialog.m1323(MessageDialog.m1321(dimension), true);
            moduleframe.dispose();
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

   static Boolean m905(UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      ServerSession serversession = openSession(busyindicator);
      if (serversession == null) {
         return null;
      } else {
         Boolean obool = m906(serversession, userinfo, busyindicator, networktask);
         m832(serversession, busyindicator);
         return obool;
      }
   }

   static Boolean m906(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      Boolean obool;
      do {
         obool = m907(serversession, userinfo, busyindicator, networktask);
      } while (obool == null && m922(serversession));

      return obool;
   }

   static Boolean m907(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = userinfo.m1154(true);
      if (s.trim().equals("")) {
         return Boolean.FALSE;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("uid", s);
         hashtable.put("institution", userinfo.getInstitution());
         serversession.setParams(hashtable, "uid.institution");
         if (busyindicator != null) {
            busyindicator.m2162(true);
         }

         Object[][] aobject = parseResponseTable(postMultipart(verifyUrl, serversession, networktask, "userExists"));
         if (busyindicator != null) {
            busyindicator.m2162(false);
         }

         if (!m834(aobject, serversession)) {
            return null;
         } else {
            Integer integer = m920(aobject, serversession);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = m825(verifyUrl, serversession, networktask, "userExists", null);
                  if (queryresult == null) {
                     return null;
                  }

                  aobject = queryresult.f1362;
                  integer = queryresult.f1363;
               }

               if (integer != 0) {
                  m921("status = " + integer, serversession);
                  return null;
               } else {
                  Integer integer1 = m823(aobject, "return_value", serversession);
                  if (integer1 == null) {
                     return null;
                  } else if (integer1 != 1) {
                     return Boolean.FALSE;
                  } else {
                     userinfo.f664 = m823(aobject, "logic_user_uid", serversession);
                     return userinfo.f664 == null ? null : Boolean.TRUE;
                  }
               }
            }
         }
      }
   }

   static Integer m908(ServerSession serversession, UserInfo userinfo, C__F c__f, String s, int i) {
      if (userinfo == null) {
         return null;
      } else {
         Integer integer;
         do {
            integer = m909(serversession, userinfo, (NetworkTask)null, c__f, s, i);
         } while (integer == null && m922(serversession));

         if (integer == null) {
            return null;
         } else {
            if (integer != 0) {
               if (s == null) {
                  return null;
               }

               if (c__f.f934) {
                  MessageDialog.showMessage("Authorization Failed", s, null, null);
                  return null;
               }

               c__f.f934 = true;
               userinfo.f664 = m910(serversession, userinfo, c__f, s, i);
            }

            if (userinfo.f664 == null) {
               return null;
            } else {
               if (userinfo instanceof NewUserInfo) {
                  ((NewUserInfo)userinfo).f381 = c__f.f933 == null ? c__f.f932 : c__f.f933;
               }

               return userinfo.f664;
            }
         }
      }
   }

   static Integer m909(ServerSession serversession, UserInfo userinfo, NetworkTask networktask, C__F c__f, String s, int i) {
      Hashtable hashtable = new Hashtable();
      boolean flag = userinfo instanceof NewUserInfo;
      if (!flag) {
         hashtable.put("first_name", userinfo.getFirstName());
         hashtable.put("middle_name", userinfo.getMiddleName());
         hashtable.put("last_name", userinfo.getLastName());
      }

      hashtable.put("uid", userinfo.m1154(true));
      hashtable.put("password", c__f.f932);
      if (!flag) {
         hashtable.put("email", userinfo.getEmail());
      }

      hashtable.put("institution", userinfo.getInstitution());
      hashtable.put("purpose", userPurposes[i]);
      if (flag) {
         serversession.setParams(hashtable, "uid.password.institution.purpose");
      } else {
         serversession.setParams(hashtable, "first_name.middle_name.last_name.uid.password.email.institution.purpose");
      }

      Object[][] aobject = parseResponseTable(postMultipart(userUrl, serversession, networktask, "loginUser"));
      if (!m834(aobject, serversession)) {
         return null;
      } else {
         Integer integer = m920(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(userUrl, serversession, networktask, "loginUser", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            if (integer == 0) {
               userinfo.f664 = m823(aobject, "logic_user_uid", serversession);
               if (userinfo.f664 == null) {
                  return null;
               }
            }

            return integer;
         }
      }
   }

   static Integer m910(ServerSession serversession, UserInfo userinfo, C__F c__f, String s, int i) {
      if (AccountManager.m1865(serversession, userinfo, c__f, s) == null) {
         return null;
      } else if (m908(serversession, userinfo, c__f, s, i) != null) {
         return userinfo.f664;
      } else if (LogicProgram.getCredentials("developer") == null) {
         return null;
      } else {
         ErrorRef errorref = UserSetup.m2102("developer", "Developer");
         if (errorref != null) {
            String s1 = errorref.m716();
            if (s1 != null) {
               MessageDialog.showMessage(Message.get(s1), errorref.f428, null, null);
            }

            return null;
         } else {
            Boolean obool = m906(serversession, userinfo, null, (NetworkTask)null);
            return obool != null && obool ? userinfo.f664 : null;
         }
      }
   }

   static Boolean m911(ServerSession serversession, UserInfo userinfo) {
      Boolean obool = m906(serversession, userinfo, null, (NetworkTask)null);
      if (obool == null) {
         return Boolean.FALSE;
      } else if (!obool) {
         return null;
      } else {
         C_a_F c_a_f = C_a_F.m1642(serversession, userinfo, null, 1);
         if (c_a_f == null) {
            return Boolean.FALSE;
         } else {
            if (LogicProgram.mainMenu == null) {
               f489 = c_a_f;
            }

            return Boolean.TRUE;
         }
      }
   }

   static UserInfo m912(String s, String s1) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         UserInfo userinfo = m913(serversession, s, s1);
         m832(serversession, null);
         return userinfo;
      }
   }

   static UserInfo m913(ServerSession serversession, String s, String s1) {
      if (userInfoUrl == null) {
         return null;
      } else {
         UserInfo userinfo;
         do {
            userinfo = m914(serversession, s, s1);
         } while (userinfo == null && m922(serversession));

         return userinfo;
      }
   }

   static UserInfo m914(ServerSession serversession, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("institution", s);
      hashtable.put("studentID", s1);
      serversession.setParams(hashtable, "institution.studentID");
      Object[][] aobject = parseResponseTable(postMultipart(userInfoUrl, serversession, (NetworkTask)null, "getUserInfo"));
      if (!m834(aobject, serversession)) {
         return null;
      } else {
         Integer integer = m920(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(userInfoUrl, serversession, (NetworkTask)null, "getUserInfo", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            return integer != 0 ? null : null;
         }
      }
   }

   static boolean m915(ServerSession serversession, int i, String s, String s1) {
      Boolean obool;
      do {
         obool = m916(serversession, (NetworkTask)null, i, s, s1);
      } while (obool == null && m922(serversession));

      return obool != null && obool;
   }

   static Boolean m916(ServerSession serversession, NetworkTask networktask, int i, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", i + "");
      hashtable.put("old_password", s);
      hashtable.put("new_password", s1);
      serversession.setParams(hashtable, "logic_user_uid.old_password.new_password");
      Object[][] aobject = parseResponseTable(postMultipart(passwordUrl, serversession, networktask, "changePassword"));
      if (!m834(aobject, serversession)) {
         return null;
      } else {
         Integer integer = m920(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(passwordUrl, serversession, networktask, "changePassword", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            return integer == 0 ? Boolean.TRUE : Boolean.FALSE;
         }
      }
   }

   static boolean m917() {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return false;
      } else {
         boolean flag = m918(serversession);
         m832(serversession, null);
         return flag;
      }
   }

   static boolean m918(ServerSession serversession) {
      Message message = Message.get("not071");

      boolean flag;
      do {
         NetworkTask networktask = new NetworkTask(message.id, message.text);
         flag = m919(serversession, networktask);
      } while (!flag && m922(serversession));

      return flag;
   }

   static boolean m919(ServerSession serversession, NetworkTask networktask) {
      Vector vector = new Vector();
      Hashtable hashtable = new Hashtable();
      hashtable.put("arch", LogicProgram.arch);
      serversession.setParams(hashtable, "arch");
      Object[][] aobject = parseResponseTable(postMultipart(courseRemoteUrl, serversession, networktask, "getRemoteCourses"));
      if (!m834(aobject, serversession)) {
         return false;
      } else {
         Integer integer = m920(aobject, serversession);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = m825(courseRemoteUrl, serversession, networktask, "getRemoteCourses", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.f1362;
               integer = queryresult.f1363;
            }

            if (integer != 0) {
               m921("status = " + integer, serversession);
               return false;
            } else {
               Integer integer3 = m823(aobject, "course count", serversession);
               if (integer3 == null) {
                  return false;
               } else {
                  int i = integer3;

                  for (int j = 1; j <= i; j++) {
                     int k = m818(aobject, "course_" + j);
                     if (k == -1) {
                        m921("Could not find \"COURSE" + j + "\".", serversession);
                        return false;
                     }

                     Integer integer1 = LogicProgram.parseInteger((String)aobject[k][4]);
                     if (integer1 == null) {
                        m921("Could not parse \"" + (String)aobject[k][4] + "\" as a number.", serversession);
                        return false;
                     }

                     String s = aobject[k].length > 5 ? (String)aobject[k][5] : null;
                     if (s != null) {
                        s = s.trim();
                     }

                     Integer integer2 = null;
                     if (aobject[k].length > 6 && (integer2 = LogicProgram.parseInteger((String)aobject[k][6])) == null) {
                        m921("Could not parse \"" + (String)aobject[k][6] + "\" as a number.", serversession);
                        return false;
                     }

                     String s1 = aobject[k].length > 7 ? (String)aobject[k][7] : null;
                     CourseInfo courseinfo = new CourseInfo((String)aobject[k][1], (String)aobject[k][2], (String)aobject[k][3], integer1, s, integer2, s1);
                     vector.addElement(courseinfo);
                  }

                  CourseInfo.f628 = new CourseInfo[vector.size()];
                  vector.copyInto(CourseInfo.f628);
                  C_WA c_wa = new C_WA(new C_d_B());
                  CourseInfo.f628 = (CourseInfo[])c_wa.m1425(CourseInfo.f628);
                  return true;
               }
            }
         }
      }
   }

   static Integer m920(Object[][] aobject, ResponseHandler responsehandler) {
      return m823(aobject, "error status", responsehandler);
   }

   static void m921(String s, ResponseHandler responsehandler) {
      if (responsehandler == null) {
         Message message = Message.get("not008");
         MessageDialog.showMessage(message.id, message.text + "\n\n" + s, null, null);
      } else {
         Hashtable hashtable = new Hashtable();
         if (s != null) {
            hashtable.put("commErrorMsg", s);
         }

         responsehandler.m4("not008", hashtable);
      }
   }

   static boolean m922(ResponseHandler responsehandler) {
      Message message1 = Message.get("not037");
      ErrorRef errorref;
      String s;
      Message message;
      if (responsehandler != null && (errorref = responsehandler.m5()) != null && errorref.f427 != null) {
         message = Message.get(errorref.f427);
         s = message.text;
         if (errorref.f428 != null) {
            s = Message.substitute(s, errorref.f428);
            String s1 = (String)errorref.f428.get("commErrorMsg");
            if (s1 != null) {
               s = s + "\n\n" + s1;
            }
         }

         s = s + "\n\n" + message1.text;
      } else {
         message = message1;
         s = message1.text;
      }

      C_b_E c_b_e = new C_b_E(message1.buttons);
      MessageDialog.showMessage(message.id, s, null, c_b_e);
      return c_b_e.f1027 == 0;
   }
}
