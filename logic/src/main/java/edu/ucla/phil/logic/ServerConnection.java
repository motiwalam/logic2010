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
   static File editDir = null;
   static File localDir = null;
   static File adminDir = null;
   static File nonetDir = null;
   static File textDir = null;
   static String institution = null;
   static String term = null;
   static String course = null;
   static String ident = null;
   static String textVersion = null;
   static String adminInstitution = null;
   static String adminTerm = null;
   static String adminCourse = null;
   static String adminVersion = null;
   static boolean adminInstall = false;
   static boolean installAdminNow = false;
   static boolean linkedFromNonetDir = false;
   static boolean demoMode = false;
   static boolean coreUpdateOffered = false;
   static boolean adminUpdateChecked = false;
   static boolean backedUpForUpdate = false;
   static Credentials serverCredentials = null;
   static String waveToken = null;
   static LoginResult cachedLogin = null;
   static final String WORK_DIR_PREFIX = "work/";
   static final String[] WORK_FILES = new String[]{
      "user.txt", "prefs.txt", "derwork.txt", "invwork.txt", "parwork.txt", "recwork.txt", "symwork.txt", "truwork.txt", "keywork.txt"
   };
   static final String[] DATA_FILES = new String[]{"derdata.txt", "invdata.txt", "pardata.txt", "recdata.txt", "symdata.txt", "trudata.txt"};
   static final String WORK_ZIP_NAME = "work.zip";
   static final String LOADER_JAR_NAME = "loader.jar";

   static boolean readDatabaseLinks() {
      File file1 = LogicProgram.configDir;
      String s = LogicProgram.getLink("editDir");
      editDir = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("localDir");
      localDir = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("textDir");
      textDir = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("adminDir");
      adminDir = s == null ? null : resolvePath(file1, s);
      s = LogicProgram.getLink("nonetDir");
      nonetDir = s == null ? null : resolvePath(file1, s);
      Hashtable hashtable;
      if ((hashtable = LogicProgram.readLinks(textDir, false)) != null) {
         institution = LogicProgram.getValue(hashtable, "institution", "").trim();
         term = LogicProgram.getValue(hashtable, "term", "").trim();
         course = LogicProgram.getValue(hashtable, "course", "").trim();
         ident = LogicProgram.getValue(hashtable, "ident", "").trim();
         textVersion = LogicProgram.getValue(hashtable, "version", "").trim();
         adminInstitution = LogicProgram.getLink("institution", "").trim();
         adminTerm = LogicProgram.getLink("term", "").trim();
         adminCourse = LogicProgram.getLink("course", "").trim();
         adminVersion = LogicProgram.getLink("version", "").trim();
         adminInstall = true;
         s = LogicProgram.getValue(hashtable, "nonetDir", null);
         linkedFromNonetDir = s == null ? false : LogicProgram.canonicalFile(s).equals(LogicProgram.linkDir);
      } else if ((hashtable = LogicProgram.readLinks(adminDir, false)) != null && LogicProgram.getValue(hashtable, "textDir", null) != null) {
         institution = LogicProgram.getLink("institution", "").trim();
         term = LogicProgram.getLink("term", "").trim();
         course = LogicProgram.getLink("course", "").trim();
         ident = LogicProgram.getLink("ident", "").trim();
         textVersion = LogicProgram.getLink("version", "").trim();
         adminInstitution = LogicProgram.getValue(hashtable, "institution", "").trim();
         adminTerm = LogicProgram.getValue(hashtable, "term", "").trim();
         adminCourse = LogicProgram.getValue(hashtable, "course", "").trim();
         adminVersion = LogicProgram.getValue(hashtable, "version", "").trim();
         adminInstall = false;
      } else {
         institution = LogicProgram.getLink("institution", "").trim();
         term = LogicProgram.getLink("term", "").trim();
         course = LogicProgram.getLink("course", "").trim();
         ident = LogicProgram.getLink("ident", "").trim();
         textVersion = LogicProgram.getLink("version", "").trim();
         adminInstitution = null;
         adminTerm = null;
         adminCourse = null;
         adminVersion = null;
         adminInstall = false;
      }

      demoMode = LogicProgram.localMode || LogicProgram.isDemoName(institution);
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
      return parseUrl(s1, new ErrorRef(null));
   }

   static String httpGet(URL url) {
      String s = httpGet(url, null);
      if (DiagnosticsLog.out != null) {
         DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
         DiagnosticsLog.out.println("GET " + url);
         DiagnosticsLog.out.println(s);
      }

      return s;
   }

   /** Safety net for local mode: the course server must never be contacted. */
   static void checkNetworkAllowed(Object target) throws IOException {
      if (LogicProgram.localMode) {
         IOException e = new IOException("network access disabled in local mode: " + target);
         e.printStackTrace();
         throw e;
      }
   }

   static String httpGet(URL url, HttpDigestAuth httpdigestauth) {
      URLConnection urlconnection = null;

      String s;
      try {
         checkNetworkAllowed(url);
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

   static String postSessionForm(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask) {
      return postForm(serverurl, serversession.encodeForm(), networktask);
   }

   static String postForm(ServerUrl serverurl, String s, NetworkTask networktask) {
      Vector vector = new Vector();
      vector.addElement(s.getBytes());
      String s1 = post(serverurl, vector, "application/x-www-form-urlencoded", networktask, null, null);
      if (DiagnosticsLog.out != null) {
         DiagnosticsLog.out.println(LogicProgram.utcTimestamp());
         DiagnosticsLog.out.println("POST " + serverurl + ": " + s);
         DiagnosticsLog.out.println(s1);
      }

      return s1;
   }

   static String postMultipart(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask, String s) {
      return postMultipartBody(serverurl, serversession.encodeMultipart(s), networktask, s);
   }

   static String postMultipartBody(ServerUrl serverurl, Vector vector, NetworkTask networktask, String s) {
      String s1 = post(serverurl, vector, "multipart/form-data; boundary=" + s, networktask, null, null);
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

   static String post(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      if (networktask == null) {
         networktask = createWatchdogTask(10000L);
      }

      return doPost(serverurl, vector, s, networktask, file1, httpdigestauth);
   }

   static String doPost(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      URLConnection urlconnection = null;
      Md5OutputStream md5outputstream = new Md5OutputStream();
      writeRequestBody(md5outputstream, vector);

      String s1;
      try {
         urlconnection = serverurl.openConnection();
         if (!(urlconnection instanceof HttpURLConnection)) {
            return null;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)urlconnection;
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("Content-type", s);
         httpurlconnection.setRequestProperty("Content-length", md5outputstream.getByteCount() + "");
         httpurlconnection.setRequestProperty("Content-MD5", new Base64Codec(md5outputstream.digest()).toString());
         httpurlconnection.setConnectTimeout(10000);
         httpurlconnection.setReadTimeout(10000);
         httpurlconnection.setDoOutput(true);
         OutputStream outputstream = httpurlconnection.getOutputStream();
         networktask.setOutputStream(outputstream);
         writeRequestBody(outputstream, vector);
         networktask.setOutputStream(null);
         httpurlconnection.getHeaderField(0);
         int i = httpurlconnection.getResponseCode();
         if (i < 200 || i >= 300) {
            httpurlconnection.disconnect();
            return null;
         }

         InputStream inputstream = httpurlconnection.getInputStream();
         networktask.setInputStream(inputstream);
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
            decodeBase64ToFile(bufferedreader, file1);
         }

         networktask.setInputStream(null);
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

   static IOException decodeBase64ToFile(Reader reader, File file1) {
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
               base64codec.addBase64String(new String(achar, 0, i));
               bufferedoutputstream.write(base64codec.getBytes(true));
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
         bufferedoutputstream.write(base64codec.getBytes(false));
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

   static IOException writeRequestBody(OutputStream outputstream, Vector vector) {
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
                     base64codec.addBytes(abyte, 0, k);
                     outputstream.write(base64codec.encodeChunk(true).getBytes());
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
               outputstream.write((base64codec.encodeChunk(false) + "\r\n").getBytes());
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

   static String stubReturnsNull(String s) {
      return null;
   }

   static String sqlQuote(String s) {
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

   static String sqlQuoteInteger(Integer integer) {
      return integer == null ? "NULL" : "'" + integer + "'";
   }

   static Object[][] parseResponseTable(String s) {
      if (s == null) {
         return (Object[][])null;
      } else {
         HtmlTableParser htmltableparser = new HtmlTableParser();
         htmltableparser.parseTable(s, 0);
         return htmltableparser.toGrid();
      }
   }

   static int findRow(Object[][] aobject, String s) {
      return findRow(aobject, 0, 0, s);
   }

   static int findRow(Object[][] aobject, int i, String s) {
      return findRow(aobject, 0, i, s);
   }

   static int findRow(Object[][] aobject, int i, int j, String s) {
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

   static String getResponseValue(Object[][] aobject, String s, ResponseHandler responsehandler) {
      return getResponseValue(aobject, s, responsehandler, false);
   }

   static String getResponseValue(Object[][] aobject, String s, ResponseHandler responsehandler, boolean flag) {
      if (aobject == null) {
         reportError("no response from server", responsehandler);
         return null;
      } else {
         int i = findRow(aobject, s);
         if (i == -1) {
            if (!flag) {
               reportError("Could not find \"" + s.toUpperCase() + "\".", responsehandler);
            }

            return null;
         } else if (aobject[i].length < 2) {
            if (!flag) {
               reportError("Could not parse \"" + s.toUpperCase() + "\".", responsehandler);
            }

            return null;
         } else {
            String s1 = (String)aobject[i][1];
            if (s1 == null) {
               if (!flag) {
                  reportError("Could not parse \"" + s.toUpperCase() + "\".", responsehandler);
               }

               return null;
            } else {
               return s1;
            }
         }
      }
   }

   static Integer getResponseInteger(Object[][] aobject, String s, ResponseHandler responsehandler) {
      return getResponseInteger(aobject, s, responsehandler, false);
   }

   static Integer getResponseInteger(Object[][] aobject, String s, ResponseHandler responsehandler, boolean flag) {
      String s1 = getResponseValue(aobject, s, responsehandler, flag);
      if (s1 == null) {
         return null;
      } else {
         Integer integer = LogicProgram.parseInteger(s1.trim());
         if (integer == null) {
            if (!flag) {
               reportError("Could not parse \"" + s1 + "\" as a number.", responsehandler);
            }

            return null;
         } else {
            return integer;
         }
      }
   }

   static QueryResult repostWithNewNonce(ServerUrl serverurl, ServerSession serversession, NetworkTask networktask, String s, BusyIndicator busyindicator) {
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      refreshNonce(serversession, networktask);
      Object[][] aobject = parseResponseTable(postMultipart(serverurl, serversession, networktask, s));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      if (!verifyResponseSignature(aobject, serversession)) {
         return null;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         return !checkAuthStatus(integer, serversession) ? null : new QueryResult(aobject, integer);
      }
   }

   static String urlDecode(String s) {
      String s2 = replaceString(s, "+", " ");
      String s1 = "";
      int j = 0;
      int k = s2.length();

      int i;
      while ((i = s2.indexOf("%", j)) != -1 && i + 3 <= k) {
         s1 = s1 + s2.substring(j, i);

         try {
            s1 = s1 + (char)Integer.parseInt(s2.substring(i + 1, i + 3), 16);
            j = i + 3;
         } catch (NumberFormatException numberformatexception) {
            s1 = s1 + "%";
            j = i + 1;
         }
      }

      return s1 + s2.substring(j);
   }

   static String replaceString(String s, String s1, String s2) {
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

   static NetworkTask createWatchdogTask(long i) {
      String s = "Process Watcher";
      String s1 = "This process has taken over " + i / 1000L + " seconds.\nIf you think it is hung, press Abort.";
      return new NetworkTask(s, s1, i);
   }

   static ServerSession openSession(BusyIndicator busyindicator) {
      ResponseHandler responsehandler;
      do {
         responsehandler = requestNonce(busyindicator, (NetworkTask)null);
      } while (responsehandler instanceof ErrorRef && askRetry(responsehandler));

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
         busyindicator.setBusy(true);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, (String)object, networktask));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      ErrorRef errorref = new ErrorRef(null);
      Integer integer = getErrorStatus(aobject, errorref);
      if (integer == null) {
         return errorref;
      } else if (integer != 0) {
         reportError("status = " + integer, errorref);
         return errorref;
      } else {
         String s = getResponseValue(aobject, "nonce", errorref);
         return (ResponseHandler)(s == null ? errorref : new ServerSession(serverCredentials, s));
      }
   }

   static boolean refreshNonce(ServerSession serversession, NetworkTask networktask) {
      Object object = null;

      try {
         object = "user=" + URLEncoder.encode(serverCredentials.user, "UTF-8");
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, (String)object, networktask));
      Integer integer = getErrorStatus(aobject, serversession);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         reportError("status = " + integer, serversession);
         return false;
      } else {
         String s = getResponseValue(aobject, "nonce", serversession);
         if (s == null) {
            return false;
         } else {
            serversession.setNonce(s);
            return true;
         }
      }
   }

   static boolean closeSession(ServerSession serversession, BusyIndicator busyindicator) {
      String s = "";

      try {
         s = s + "user=" + URLEncoder.encode(serversession.credentials.user, "UTF-8") + "&";
         s = s + "nonce=" + URLEncoder.encode(serversession.nonce, "UTF-8") + "&";
         s = s + "auth=" + Scrambler.md5Hex(serversession.credentials.user + ":" + serversession.nonce + ":" + serversession.credentials.password);
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         throw new RuntimeException("unable to encode UTF-8", unsupportedencodingexception);
      }

      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      Object[][] aobject = parseResponseTable(postForm(nonceUrl, s, (NetworkTask)null));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      Integer integer = getErrorStatus(aobject, serversession);
      if (integer == null) {
         return false;
      } else if (integer != 0) {
         reportError("status: " + integer, serversession);
         return false;
      } else {
         return true;
      }
   }

   static boolean checkAuthStatus(Integer integer, ResponseHandler responsehandler) {
      if (integer == null) {
         return false;
      } else if (integer < 0) {
         if (integer == -1) {
            reportError("repeated nonce failure", responsehandler);
         } else if (integer == -2) {
            reportError("internal authentication failure", responsehandler);
         } else {
            reportError("unknown authentication error: " + integer, responsehandler);
         }

         return false;
      } else {
         return true;
      }
   }

   static boolean verifyResponseSignature(Object[][] aobject, ServerSession serversession) {
      String s = getResponseValue(aobject, "dgst", serversession);
      if (s == null) {
         return false;
      } else {
         String s1 = getResponseValue(aobject, "cnonce", serversession);
         if (s1 == null) {
            return false;
         } else {
            String s2 = getResponseValue(aobject, "auth", serversession);
            if (s2 == null) {
               return false;
            } else if (serversession.cnonce != null && serversession.cnonce.equals(s1)) {
               String[] astring = ServerSession.splitFieldList(s);
               String s3 = "";
               int i = astring == null ? 0 : astring.length;

               for (int j = 0; j < i; j++) {
                  int k = findRow(aobject, astring[j]);
                  if (k == -1) {
                     reportError("Could not find \"" + astring[j] + "\".", serversession);
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
                  reportError("signature mismatch", serversession);
                  return false;
               } else {
                  return true;
               }
            } else {
               reportError("client nonce mismatch", serversession);
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
            LoginResult loginresult = LoginResult.login(serversession, userinfo, busyindicator, 1);
            if (loginresult != null && AccountManager.chooseCourse(userinfo, "not048", false, false) != null) {
               if (userinfo.dirty) {
                  userinfo.save();
               }

               int i = userinfo.courseUid == null ? 0 : userinfo.courseUid;
               return new Submission(i, loginresult.userUid, serversession, loginresult.passwordHash);
            } else {
               closeSession(serversession, busyindicator);
               return null;
            }
         }
      } else {
         MessageDialog.showMessage(Message.get("not039"), null, null, null);
         return null;
      }
   }

   static boolean submit(Submission submission, BusyIndicator busyindicator) {
      if (submission.isComplete() && !demoMode) {
         Message message = Message.get("not070");
         NetworkTask networktask = new NetworkTask(message.id, message.text);

         boolean flag;
         do {
            flag = submitOnce(submission, busyindicator, networktask);
         } while (!flag && askRetry(submission));

         return flag;
      } else {
         return false;
      }
   }

   static boolean submitOnce(Submission submission, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "logic_user_uid.logic_course_uid.evaluation.tproblem_md5.twork.problem_name.module.help_count.duration";
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", submission.userUid + "");
      hashtable.put("logic_course_uid", submission.courseUid + "");
      hashtable.put("evaluation", submission.evaluation);
      hashtable.put("tproblem_md5", submission.problemMd5);
      hashtable.put("twork", submission.work);
      hashtable.put("problem_name", submission.problemName);
      hashtable.put("module", submission.module);
      hashtable.put("help_count", submission.helpCount + "");
      hashtable.put("duration", submission.duration + "");
      if (waveToken != null) {
         s = s + ".wave_token";
         hashtable.put("wave_token", waveToken);
      }

      if (submission.ipAddress != null) {
         s = s + ".ip";
         hashtable.put("ip", submission.ipAddress);
      }

      submission.session.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(submissionUrl, submission.session, networktask, "submit"));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      if (!verifyResponseSignature(aobject, submission.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, submission);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(submissionUrl, submission.session, networktask, "submit", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, submission);
               return false;
            } else {
               submission.timestamp = getResponseValue(aobject, "dtTimestamp", submission, true);
               submission.submissionUid = getResponseValue(aobject, "logic_submission_uid", submission, true);
               return true;
            }
         }
      }
   }

   static void finishSubmission(Submission submission, BusyIndicator busyindicator) {
      closeSession(submission.session, busyindicator);
      submission.session = null;
   }

   static boolean saveWorkZip(File file1) {
      File file2 = new File(LogicProgram.configDir, "work.zip");

      FileOutputStream fileoutputstream;
      try {
         fileoutputstream = new FileOutputStream(file2);
      } catch (IOException ioexception1) {
         return false;
      }

      boolean flag = zipWork(file1, fileoutputstream);

      try {
         fileoutputstream.close();
      } catch (IOException ioexception) {
      }

      if (!flag && file2.exists()) {
         file2.delete();
      }

      return flag;
   }

   static ProblemUpload prepareUpload(BusyIndicator busyindicator) {
      if (!demoMode && adminInstall && !LogicProgram.noNetwork && uploadUrl != null) {
         ServerSession serversession = openSession(busyindicator);
         if (serversession == null) {
            return null;
         } else {
            UserInfo userinfo = LogicProgram.user;
            if (userinfo.userUid == null) {
               Boolean obool = verifyUser(serversession, userinfo, null, null);
               if (obool == null || !obool) {
                  closeSession(serversession, null);
                  return null;
               }
            }

            ErrorRef errorref = UserSetup.checkAccess(null, "instructor");
            if (errorref != null) {
               String s = errorref.getId();
               if (s != null) {
                  MessageDialog.showMessage(Message.get(s), errorref.params, null, null);
               }

               closeSession(serversession, null);
               return null;
            } else {
               CourseInfo courseinfo = getConfiguredCourse();
               if (courseinfo.courseUid == null) {
                  closeSession(serversession, null);
                  return null;
               } else {
                  return new ProblemUpload(serversession, userinfo.userUid, courseinfo.courseUid, FormulaParser.getSyntax());
               }
            }
         }
      } else {
         MessageDialog.showMessage(Message.get("not039"), null, null, null);
         return null;
      }
   }

   static boolean uploadProblem(ProblemUpload problemupload, BusyIndicator busyindicator) {
      if (problemupload == null) {
         return false;
      } else {
         Message message = Message.get("not070");
         NetworkTask networktask = new NetworkTask(message.id, message.text);

         boolean flag;
         do {
            flag = uploadProblemOnce(problemupload, busyindicator, networktask);
         } while (!flag && askRetry(problemupload));

         return flag;
      }
   }

   static boolean uploadProblemOnce(ProblemUpload problemupload, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "logic_user_uid.logic_course_uid.problem_name.text.webtext.syntax.type.aux.num_answers";
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", problemupload.userUid + "");
      hashtable.put("logic_course_uid", problemupload.courseUid + "");
      hashtable.put("problem_name", problemupload.problemName);
      hashtable.put("text", problemupload.text);
      hashtable.put("webtext", problemupload.webText);
      hashtable.put("syntax", problemupload.syntax + "");
      hashtable.put("type", problemupload.type);
      hashtable.put("aux", problemupload.aux == null ? "" : problemupload.aux);
      if (problemupload.answers == null) {
         hashtable.put("num_answers", "0");
      } else {
         hashtable.put("num_answers", problemupload.answers.length + "");
         int i = problemupload.answers.length;

         for (int j = 0; j < i; j++) {
            s = s + ".answer_" + (j + 1);
            hashtable.put("answer_" + (j + 1), problemupload.answers[j]);
         }
      }

      problemupload.session.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(uploadUrl, problemupload.session, networktask, "upload"));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      if (!verifyResponseSignature(aobject, problemupload.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, problemupload);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(uploadUrl, problemupload.session, networktask, "upload", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, problemupload);
               return false;
            } else {
               problemupload.timestamp = getResponseValue(aobject, "dtTimestamp", problemupload, true);
               problemupload.problemUid = getResponseValue(aobject, "logic_inst_problem_uid", problemupload, true);
               return true;
            }
         }
      }
   }

   static void finishUpload(ProblemUpload problemupload, BusyIndicator busyindicator) {
      closeSession(problemupload.session, busyindicator);
      problemupload.session = null;
   }

   static String zipWorkToBase64(File file1) {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
      Base64OutputStream base64outputstream = new Base64OutputStream(bytearrayoutputstream);
      boolean flag = zipWork(file1, base64outputstream);

      try {
         base64outputstream.close();
      } catch (IOException ioexception) {
      }

      return flag ? bytearrayoutputstream.toString() : null;
   }

   static boolean zipWork(File file1, OutputStream outputstream) {
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
               // backups carry the work files in the older format, under their older names
               byte[] abyte1 = DataFiles.legacyWorkBytes(file1, WORK_FILES[k]);
               if (abyte1 != null) {
                  zipoutputstream.putNextEntry(new ZipEntry("work/" + WORK_FILES[k]));
                  zipoutputstream.write(abyte1);
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

   static boolean restoreFromWorkZip(boolean flag, File file1) {
      File file2 = new File(LogicProgram.configDir, "work.zip");

      try {
         return unzipWork(new FileInputStream(file2), flag, file1);
      } catch (FileNotFoundException filenotfoundexception) {
         return false;
      }
   }

   static boolean unzipBase64Work(String s, boolean flag, File file1) {
      return unzipWork(new Base64InputStream(new ByteArrayInputStream(s.getBytes())), flag, file1);
   }

   static boolean unzipWork(InputStream inputstream, boolean flag, File file1) {
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
            String s2 = normalizeZipEntryName(s);
            if (zipentry.isDirectory()) {
               if (s2.equalsIgnoreCase("work/")) {
                  file2 = new File(file1, s2);
                  if (!file2.exists()) {
                     file2.mkdirs();
                  }
               }
            } else if (file2 != null) {
               String s1 = s2.toLowerCase();
               int j = LogicProgram.indexOf(WORK_FILES, s1.substring("work/".length()));
               if (s1.startsWith("work/") && (flag ? j == 0 || j == 1 : j != -1)) {
                  fileoutputstream = new FileOutputStream(new File(file1, s2));

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

   static boolean hasModuleWorkFiles() {
      for (int i = 2; i < WORK_FILES.length; i++) {
         if (DataFiles.hasWork(LogicProgram.workDir, WORK_FILES[i])) {
            return true;
         }
      }

      return false;
   }

   static Hashtable getCourseMessageParams() {
      CourseInfo courseinfo = getConfiguredCourse();
      Institution institutionx = Institution.forName(courseinfo.institution);
      return Message.params("dbsite", courseinfo.institution, "dbterm", institutionx.displayTerm(courseinfo.term), "dbcourse", courseinfo.course);
   }

   static String normalizeZipEntryName(String s) {
      String s1 = "logic/";
      return s.toLowerCase().startsWith(s1) ? "work/" + s.substring(s1.length()) : s;
   }

   static BackupRequest prepareBackup(UserInfo userinfo, BusyIndicator busyindicator) {
      ServerSession serversession = openSession(busyindicator);
      if (serversession == null) {
         return null;
      } else {
         LoginResult loginresult = cachedLogin != null ? cachedLogin : LoginResult.login(serversession, userinfo, busyindicator, 1);
         if (loginresult == null) {
            closeSession(serversession, busyindicator);
            return null;
         } else {
            return new BackupRequest(loginresult.userUid, serversession, loginresult.passwordHash);
         }
      }
   }

   static boolean uploadBackup(BackupRequest backuprequest, NetworkTask networktask) {
      if (backuprequest.isReady() && backuprequest.backupKey != null) {
         boolean flag;
         do {
            flag = uploadBackupOnce(backuprequest, networktask);
         } while (!flag && askRetry(backuprequest));

         return flag;
      } else {
         return false;
      }
   }

   static boolean uploadBackupOnce(BackupRequest backuprequest, NetworkTask networktask) {
      String s = "userid.sectionid.data.key";
      Hashtable hashtable = new Hashtable();
      hashtable.put("userid", backuprequest.userUid + "");
      hashtable.put("sectionid", "0");
      hashtable.put("data", backuprequest.data);
      hashtable.put("key", backuprequest.backupKey);
      if (backuprequest.ipAddress != null) {
         s = s + ".ip";
         hashtable.put("ip", backuprequest.ipAddress);
      }

      backuprequest.session.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(backupUrl, backuprequest.session, networktask, "backup"));
      if (!verifyResponseSignature(aobject, backuprequest.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, backuprequest);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(backupUrl, backuprequest.session, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, backuprequest);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean saveProblemRecord(ProblemDbRecord problemdbrecord, NetworkTask networktask, boolean flag) {
      if (insertProblemUrl != null && updateProblemUrl != null) {
         problemdbrecord.session = openSession(null);
         if (problemdbrecord.session == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = saveProblemRecordOnce(problemdbrecord, networktask, flag);
            } while (obool == null && askRetry(problemdbrecord.session));

            closeSession(problemdbrecord.session, null);
            return obool != null && obool;
         }
      } else {
         return false;
      }
   }

   static Boolean saveProblemRecordOnce(ProblemDbRecord problemdbrecord, NetworkTask networktask, boolean flag) {
      String s = "problem_name.tProblem.tProblem_md5.tWeb_form_problem.common_name.comment.version.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", sqlQuote(problemdbrecord.problemName));
      hashtable.put("tProblem", sqlQuote(problemdbrecord.problemText));
      hashtable.put("tProblem_md5", sqlQuote(problemdbrecord.getProblemMd5()));
      hashtable.put("tWeb_form_problem", sqlQuote(problemdbrecord.getWebFormProblem()));
      hashtable.put("common_name", sqlQuote(problemdbrecord.commonName));
      hashtable.put("comment", sqlQuote(problemdbrecord.getTruncatedComment()));
      hashtable.put("version", sqlQuoteInteger(problemdbrecord.version));
      hashtable.put("syntax", sqlQuote(FormulaParser.getSyntax() + ""));
      problemdbrecord.session.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(
         postMultipart(flag ? updateProblemUrl : insertProblemUrl, problemdbrecord.session, networktask, flag ? "update_problem" : "insert_problem")
      );
      if (!verifyResponseSignature(aobject, problemdbrecord.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, problemdbrecord);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(insertProblemUrl, problemdbrecord.session, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, problemdbrecord);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean deleteProblemRecord(ProblemDbRecord problemdbrecord, NetworkTask networktask) {
      if (deleteProblemUrl == null) {
         return false;
      } else {
         problemdbrecord.session = openSession(null);
         if (problemdbrecord.session == null) {
            return false;
         } else {
            Boolean obool;
            do {
               obool = deleteProblemRecordOnce(problemdbrecord, networktask);
            } while (obool == null && askRetry(problemdbrecord.session));

            closeSession(problemdbrecord.session, null);
            return obool != null && obool;
         }
      }
   }

   static Boolean deleteProblemRecordOnce(ProblemDbRecord problemdbrecord, NetworkTask networktask) {
      String s = "problem_name.syntax";
      Hashtable hashtable = new Hashtable();
      hashtable.put("problem_name", problemdbrecord.problemName);
      hashtable.put("syntax", FormulaParser.getSyntax());
      problemdbrecord.session.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(deleteProblemUrl, problemdbrecord.session, networktask, "delete_problem"));
      if (!verifyResponseSignature(aobject, problemdbrecord.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, problemdbrecord);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(insertProblemUrl, problemdbrecord.session, networktask, "backup", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, problemdbrecord);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static boolean isExamVersionCurrent() {
      if (LogicProgram.getCredentials("exam") == null) {
         return false;
      } else {
         ServerSession serversession = openSession(null);
         if (serversession == null) {
            return true;
         } else {
            Boolean obool;
            do {
               obool = isExamVersionCurrentOnce(serversession);
            } while (obool == null && askRetry(serversession));

            closeSession(serversession, null);
            return obool == null || obool;
         }
      }
   }

   static Boolean isExamVersionCurrentOnce(ServerSession serversession) {
      CourseInfo courseinfo = getConfiguredCourse();
      if (courseinfo == null) {
         return null;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", courseinfo.courseUid + "");
         hashtable.put("arch", LogicProgram.arch);
         serversession.setParams(hashtable, "logic_course_uid");
         Object[][] aobject = parseResponseTable(postMultipart(loadRemoteUrl, serversession, (NetworkTask)null, "load_remote"));
         if (!verifyResponseSignature(aobject, serversession)) {
            return null;
         } else {
            Integer integer = getErrorStatus(aobject, serversession);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = repostWithNewNonce(loadRemoteUrl, serversession, (NetworkTask)null, "load_remote", null);
                  if (queryresult == null) {
                     return null;
                  }

                  aobject = queryresult.table;
                  integer = queryresult.status;
               }

               if (integer != 0) {
                  reportError("no remote data", serversession);
                  return null;
               } else {
                  String s1 = adminInstall ? adminVersion : textVersion;
                  String s = getResponseValue(aobject, adminInstall ? "admin version" : "text version", serversession);
                  if (s == null) {
                     return null;
                  } else {
                     return isUpToDate(s.trim(), s1, true) ? Boolean.TRUE : Boolean.FALSE;
                  }
               }
            }
         }
      }
   }

   static CourseInfo getConfiguredCourse() {
      CourseInfo[] acourseinfo = CourseInfo.findCourses(institution, term, course);
      return acourseinfo != null && acourseinfo.length != 0 ? acourseinfo[0] : null;
   }

   static int checkForUpdates(UserInfo userinfo) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return 1;
      } else {
         int i = checkForUpdates(serversession, userinfo);
         closeSession(serversession, null);
         return i;
      }
   }

   static int checkForUpdates(ServerSession serversession, UserInfo userinfo) {
      int i;
      do {
         i = checkForUpdatesOnce(serversession, userinfo);
      } while (i == 4 && askRetry(serversession));

      return i;
   }

   static int checkForUpdatesOnce(ServerSession serversession, UserInfo userinfo) {
      NetworkTask networktask = new NetworkTask(LPInfo.programName, "Checking for updates...");
      Integer integer1 = userinfo.getCourseUid();
      if (integer1 == null) {
         return 1;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("logic_course_uid", integer1 + "");
         hashtable.put("arch", LogicProgram.arch);
         serversession.setParams(hashtable, "logic_course_uid.arch");
         Object[][] aobject = parseResponseTable(postMultipart(loadRemoteUrl, serversession, networktask, "load_remote"));
         if (!verifyResponseSignature(aobject, serversession)) {
            return 4;
         } else {
            Integer integer = getErrorStatus(aobject, serversession);
            if (integer == null) {
               return 4;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = repostWithNewNonce(loadRemoteUrl, serversession, networktask, "load_remote", null);
                  if (queryresult == null) {
                     return 4;
                  }

                  aobject = queryresult.table;
                  integer = queryresult.status;
               }

               if (integer != 0) {
                  reportError("no remote data", serversession);
                  return 4;
               } else {
                  UpdateInfo updateinfo = new UpdateInfo();
                  String s6 = getResponseValue(aobject, "code version", serversession);
                  if (s6 == null) {
                     return 1;
                  } else {
                     String s = s6.trim();
                     s6 = getResponseValue(aobject, "text version", serversession);
                     if (s6 == null) {
                        return 1;
                     } else {
                        String s1 = s6.trim();
                        s6 = getResponseValue(aobject, "loader version", serversession);
                        if (s6 == null) {
                           return 1;
                        } else {
                           updateinfo.loaderVersion = s6.trim();
                           s6 = getResponseValue(aobject, "download URL", serversession);
                           if (s6 == null) {
                              return 1;
                           } else {
                              ServerUrl serverurl = parseUrl(s6.trim(), serversession);
                              if (serverurl == null) {
                                 return 1;
                              } else {
                                 s6 = getResponseValue(aobject, "download text", serversession);
                                 if (s6 == null) {
                                    return 1;
                                 } else {
                                    String s2 = s6.trim();
                                    s6 = getResponseValue(aobject, "update URL", serversession);
                                    if (s6 == null) {
                                       return 1;
                                    } else {
                                       ServerUrl serverurl1 = parseUrl(s6.trim(), serversession);
                                       if (serverurl1 == null) {
                                          return 1;
                                       } else {
                                          s6 = getResponseValue(aobject, "update text", serversession);
                                          if (s6 == null) {
                                             return 1;
                                          } else {
                                             String s3 = s6.trim();
                                             s6 = getResponseValue(aobject, "loader URL", serversession);
                                             if (s6 == null) {
                                                return 1;
                                             } else {
                                                updateinfo.loaderUrl = parseUrl(s6.trim(), serversession);
                                                if (updateinfo.loaderUrl == null) {
                                                   return 1;
                                                } else {
                                                   s6 = getResponseValue(aobject, "admin version", serversession);
                                                   if (s6 == null) {
                                                      return 1;
                                                   } else {
                                                      String s4 = s6.trim();
                                                      s6 = getResponseValue(aobject, "admin url", serversession);
                                                      if (s6 == null) {
                                                         return 1;
                                                      } else {
                                                         ServerUrl serverurl2 = parseUrl(s6.trim(), serversession);
                                                         s6 = getResponseValue(aobject, "admin text", serversession);
                                                         if (s6 == null) {
                                                            return 1;
                                                         } else {
                                                            String s5 = s6.trim();
                                                            s6 = getResponseValue(aobject, "wave token", serversession, true);
                                                            waveToken = s6 == null ? null : s6.trim();
                                                            updateinfo.user = userinfo;
                                                            int i = 0;
                                                            boolean flag = matchesConfiguredCourse(userinfo);
                                                            if (!coreUpdateOffered && !isUpToDate(s, LogicProgram.codeVersion, flag) && !LogicProgram.fromIde) {
                                                               coreUpdateOffered = true;
                                                               updateinfo.downloadUrl = serverurl;
                                                               updateinfo.downloadText = s2;
                                                               updateinfo.method = getUpdateMethod(s2);
                                                               if (updateinfo.method == 0 && LogicProgram.workPresent && !backedUpForUpdate) {
                                                                  updateinfo.backupRequired = true;
                                                               }

                                                               updateinfo.localUpdate = false;

                                                               do {
                                                                  i = promptForUpdate(updateinfo, updateinfo.backupRequired ? "not046" : "not033");
                                                               } while (updateinfo.backupRequired && i == 5);
                                                            } else if (!flag || !isUpToDate(s1, textVersion, true)) {
                                                               updateinfo.downloadUrl = serverurl1;
                                                               updateinfo.downloadText = s3;
                                                               updateinfo.method = 1;
                                                               updateinfo.localUpdate = true;
                                                               i = promptForUpdate(updateinfo, flag ? "not034" : "not047");
                                                            } else if (!adminUpdateChecked
                                                               && serverurl2 != null
                                                               && (adminVersion != null || askInstallAdminVersion(userinfo))) {
                                                               adminUpdateChecked = true;
                                                               flag = matchesAdminCourse(userinfo);
                                                               if (!flag || !isUpToDate(s4, adminVersion, true)) {
                                                                  updateinfo.downloadUrl = serverurl2;
                                                                  updateinfo.downloadText = s5;
                                                                  updateinfo.method = 1;
                                                                  updateinfo.localUpdate = true;
                                                                  i = promptForUpdate(updateinfo, flag ? "not036" : "not049");
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

   static boolean matchesConfiguredCourse(UserInfo userinfo) {
      if (!institution.equalsIgnoreCase(userinfo.getInstitution())) {
         return false;
      } else if (!term.equalsIgnoreCase(userinfo.getTerm())) {
         return false;
      } else {
         return !course.equalsIgnoreCase(userinfo.getClassName()) ? false : userinfo.getCourse() != null;
      }
   }

   static boolean matchesAdminCourse(UserInfo userinfo) {
      if (adminInstitution == null || !adminInstitution.equalsIgnoreCase(userinfo.getInstitution())) {
         return false;
      } else if (adminTerm == null || !adminTerm.equalsIgnoreCase(userinfo.getTerm())) {
         return false;
      } else {
         return adminCourse == null || !adminCourse.equalsIgnoreCase(userinfo.getClassName()) ? false : userinfo.getCourse() != null;
      }
   }

   static boolean isUpToDate(String s, String s1, boolean flag) {
      int i = s.compareTo(s1);
      return flag && !s.endsWith("x") && !s1.endsWith("x") ? i <= 0 : i == 0;
   }

   static boolean askInstallAdminVersion(UserInfo userinfo) {
      if (!userinfo.hasRelation("instructor")) {
         return false;
      } else {
         Message message = Message.get("not060");
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, null, null, buttonchoicehandler);
         return installAdminNow = buttonchoicehandler.choice == 1;
      }
   }

   static int promptForUpdate(UpdateInfo updateinfo, String s) {
      if (updateinfo.method == 2) {
         return showUpdateInstructions(updateinfo);
      } else {
         Message message = Message.get(s);
         UpdatePromptHandler updateprompthandler = new UpdatePromptHandler(message.buttons, updateinfo);
         Hashtable hashtable = updateinfo.user.getMessageParams();
         Message.putParam(hashtable, "text", updateinfo.downloadText);
         MessageDialog.showMessage(message, hashtable, null, updateprompthandler);
         return updateprompthandler.result;
      }
   }

   static int getUpdateMethod(String s) {
      String s1 = s.trim().toUpperCase();
      return !s1.startsWith("HTTP://") && !s1.startsWith("HTTPS://") ? 0 : 2;
   }

   static boolean writeLoadInfo(UpdateInfo updateinfo, boolean flag) {
      Integer integer = LogicProgram.instanceGuard == null ? null : LogicProgram.instanceGuard.getPort();
      BufferedWriter bufferedwriter = null;

      try {
         bufferedwriter = new BufferedWriter(new FileWriter(LogicProgram.loadInfoFile));
         bufferedwriter.write("source:" + updateinfo.downloadUrl);
         bufferedwriter.newLine();
         bufferedwriter.write("configDir:" + LogicProgram.configDir);
         bufferedwriter.newLine();
         bufferedwriter.write("rootDir:" + LogicProgram.rootDir);
         bufferedwriter.newLine();
         bufferedwriter.write("progDir:" + LogicProgram.progDir);
         bufferedwriter.newLine();
         bufferedwriter.write("linkDir:" + LogicProgram.linkDir);
         bufferedwriter.newLine();
         bufferedwriter.write("arch:" + LogicProgram.arch);
         bufferedwriter.newLine();
         bufferedwriter.write("type:" + (updateinfo.localUpdate ? "local" : "core"));
         bufferedwriter.newLine();
         if (integer != null && !LogicProgram.overrides.lookup("SoloCheck", "yes").equalsIgnoreCase("no")) {
            bufferedwriter.write("soloPort:" + integer);
            bufferedwriter.newLine();
         }

         if (!LogicProgram.workPresent) {
            bufferedwriter.write("workDeleted:");
            bufferedwriter.newLine();
         }

         bufferedwriter.write("needBackup:");
         if (LogicProgram.workPresent && !backedUpForUpdate) {
            bufferedwriter.write("true");
         } else {
            bufferedwriter.write("false");
         }

         bufferedwriter.newLine();
         bufferedwriter.write("institution:" + updateinfo.user.getInstitution());
         bufferedwriter.newLine();
         bufferedwriter.write("term:" + updateinfo.user.getTerm());
         bufferedwriter.newLine();
         bufferedwriter.write("course:" + updateinfo.user.getClassName());
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

      LogicProgram.deleteLoadInfo = false;
      return true;
   }

   static int performUpdate(UpdateInfo updateinfo) {
      if (updateinfo.method == 1) {
         return downloadDirectUpdate(updateinfo);
      } else {
         Object object = null;

         do {
            object = fetchLoaderJar(updateinfo.loaderUrl, updateinfo);
         } while (object == null && askRetry(updateinfo));

         if (object == null) {
            return 1;
         } else if (!writeLoadInfo(updateinfo, true)) {
            return 1;
         } else {
            boolean flag;
            do {
               flag = launchLoader((File)object, updateinfo);
            } while (!flag && askRetry(updateinfo));

            return flag ? 3 : 1;
         }
      }
   }

   static int showUpdateInstructions(UpdateInfo updateinfo) {
      Message message;
      if (LogicProgram.backupName != null) {
         message = Message.get("not084");
      } else {
         message = Message.get("not085");
      }

      UpdateInstructionsHandler updateinstructionshandler = new UpdateInstructionsHandler(message.buttons, updateinfo.downloadText);
      MessageDialog.showMessage(message, null, null, updateinstructionshandler);
      return updateinstructionshandler.result;
   }

   static int downloadDirectUpdate(UpdateInfo updateinfo) {
      while (true) {
         HttpDownloader httpdownloader = new HttpDownloader();
         if (!httpdownloader.download(updateinfo.downloadUrl, updateinfo.localUpdate ? LogicProgram.configDir : LogicProgram.rootDir, null)) {
            Message message = Message.get("not086");
            ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
            MessageDialog.showMessage(message, null, null, buttonchoicehandler);
            if (buttonchoicehandler.choice == 0) {
               continue;
            }

            return 1;
         }

         LogicProgram.reinitializing = true;
         LogicProgram.reinitUser = updateinfo.user;
         return 2;
      }
   }

   static ServerUrl parseUrl(String s, ResponseHandler responsehandler) {
      if (s == null) {
         return null;
      } else {
         try {
            return new ServerUrl(s);
         } catch (MalformedURLException malformedurlexception) {
            reportError("could not interpret " + s + " as a URL.", responsehandler);
            return null;
         }
      }
   }

   static File fetchLoaderJar(ServerUrl serverurl, UpdateInfo updateinfo) {
      File file1 = new File(LogicProgram.progDir, "loader.jar");
      String s = readJarStaticField(file1, "edu.ucla.phil.logic.LPUpdateLoader", "version");
      if (s == null || updateinfo.loaderVersion == null || updateinfo.loaderVersion.compareTo(s) > 0) {
         HttpDownloader httpdownloader = new HttpDownloader();
         if (!httpdownloader.download(serverurl, file1, createWatchdogTask(10000L))) {
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

   static boolean launchLoader(File file1, ResponseHandler responsehandler) {
      String[] astring = new String[]{"java", "-Dload.info=" + LogicProgram.loadInfoFile, "-jar", file1.getPath()};
      if (LogicProgram.arch.equals("windows")) {
         astring[0] = "javaw";
      } else if (LogicProgram.arch.equals("macos")) {
         astring[0] = System.getProperty("java.home") + "/bin/java";
      }

      try {
         Runtime.getRuntime().exec(astring, null, LogicProgram.configDir);
         moveTextFilesToTrash();
         return true;
      } catch (IOException ioexception) {
         if (responsehandler == null) {
            MessageDialog.showMessage(Message.get("not063"), null, null, null);
         } else {
            responsehandler.setError("not063", null);
         }

         return false;
      }
   }

   static void moveTextFilesToTrash() {
      File file1 = LogicProgram.textDir;
      File[] afile = file1 == null ? null : file1.listFiles();
      int i = afile == null ? 0 : afile.length;
      if (i != 0) {
         if (!LogicProgram.trashDir.exists()) {
            LogicProgram.trashDir.mkdirs();
            if (!LogicProgram.trashDir.exists()) {
               return;
            }
         }

         for (int j = 0; j < i; j++) {
            File file3 = afile[j];
            File file2 = new File(LogicProgram.trashDir, file3.getName());
            if (file2.exists()) {
               int k = 1;

               do {
                  file2 = new File(LogicProgram.trashDir, file3.getName() + "-" + ++k);
               } while (file2.exists());
            }

            file3.renameTo(file2);
         }

         emptyTrash();
      }
   }

   static void emptyTrash() {
      File[] afile = LogicProgram.trashDir.listFiles();
      int i = afile == null ? 0 : afile.length;

      for (int j = 0; j < i; j++) {
         File file1 = afile[j];
         file1.delete();
      }
   }

   static String readJarStaticField(File file1, String s, String s1) {
      if (!file1.exists()) {
         return null;
      } else {
         JarClassLoader jarclassloader = new JarClassLoader(file1);

         try {
            return (String)jarclassloader.loadClass(s).getField(s1).get(null);
         } catch (Exception exception) {
            return null;
         }
      }
   }

   static String[] getUserRelations(UserInfo userinfo, NetworkTask networktask) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         String[] astring = getUserRelations(serversession, userinfo, networktask);
         closeSession(serversession, null);
         return astring;
      }
   }

   static String[] getUserRelations(ServerSession serversession, UserInfo userinfo, NetworkTask networktask) {
      if (userinfo.userUid == null) {
         Boolean obool = verifyUser(serversession, userinfo, null, networktask);
         if (obool == null || !obool) {
            return null;
         }
      }

      userinfo.courseUid = userinfo.getCourseUid();
      if (userinfo.courseUid == null) {
         return null;
      } else {
         String[] astring;
         do {
            astring = getUserRelationsOnce(serversession, userinfo, networktask);
         } while (astring == null && askRetry(serversession));

         return astring;
      }
   }

   static String[] getUserRelationsOnce(ServerSession serversession, UserInfo userinfo, NetworkTask networktask) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", userinfo.userUid + "");
      hashtable.put("logic_course_uid", userinfo.courseUid + "");
      serversession.setParams(hashtable, "logic_user_uid.logic_course_uid");
      Object[][] aobject = parseResponseTable(postMultipart(getUserRelationUrl, serversession, networktask, "getUserCourseRelations"));
      if (!verifyResponseSignature(aobject, serversession)) {
         return null;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(getUserRelationUrl, serversession, networktask, "getUserCourseRelations", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("no remote data", serversession);
               return null;
            } else {
               String s = getResponseValue(aobject, "reln count", serversession);
               if (s == null) {
                  return null;
               } else {
                  Integer integer1 = LogicProgram.parseInteger(s.trim());
                  if (integer1 == null) {
                     reportError("Could not parse \"" + s + "\" as a number.", serversession);
                     return null;
                  } else {
                     int i = integer1;
                     String[] astring = new String[i];

                     for (int j = 0; j < i; j++) {
                        s = getResponseValue(aobject, "reln_" + (j + 1), serversession);
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

   static boolean addUserRelation(UserInfo userinfo, String s, NetworkTask networktask) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return false;
      } else {
         boolean flag = addUserRelation(serversession, userinfo, s, networktask);
         closeSession(serversession, null);
         return flag;
      }
   }

   static boolean addUserRelation(ServerSession serversession, UserInfo userinfo, String s, NetworkTask networktask) {
      if (demoMode) {
         return false;
      } else if (userinfo.relations != null && LogicProgram.indexOf(userinfo.relations, s.toLowerCase()) != -1) {
         return true;
      } else {
         if (userinfo.userUid == null) {
            Boolean obool = verifyUser(serversession, userinfo, null, networktask);
            if (obool == null || !obool) {
               return false;
            }
         }

         userinfo.courseUid = userinfo.getCourseUid();
         if (userinfo.courseUid == null) {
            return false;
         } else {
            boolean flag;
            do {
               flag = addUserRelationOnce(serversession, userinfo, s, networktask);
            } while (!flag && askRetry(serversession));

            return flag;
         }
      }
   }

   static boolean addUserRelationOnce(ServerSession serversession, UserInfo userinfo, String s, NetworkTask networktask) {
      userinfo.relations = null;
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", userinfo.userUid + "");
      hashtable.put("logic_course_uid", userinfo.courseUid + "");
      hashtable.put("relation", s);
      serversession.setParams(hashtable, "logic_user_uid.logic_course_uid.relation");
      Object[][] aobject = parseResponseTable(postMultipart(addUserRelationUrl, serversession, networktask, "addUserCourseRelation"));
      Integer integer = getErrorStatus(aobject, serversession);
      if (integer == null) {
         return false;
      } else {
         if (integer == -1) {
            QueryResult queryresult = repostWithNewNonce(addUserRelationUrl, serversession, networktask, "addUserCourseRelation", null);
            if (queryresult == null) {
               return false;
            }

            aobject = queryresult.table;
            integer = queryresult.status;
         }

         if (integer != 0) {
            reportError("status = " + integer, serversession);
            return false;
         } else {
            userinfo.relations = getUserRelations(serversession, userinfo, networktask);
            return true;
         }
      }
   }

   static Boolean ensureInitialBackup(UserInfo userinfo, String s, NetworkTask networktask) {
      if (s == null) {
         return null;
      } else {
         ServerSession serversession = openSession(null);
         if (serversession == null) {
            return Boolean.FALSE;
         } else if (!addUserRelation(serversession, userinfo, "student", networktask)) {
            closeSession(serversession, null);
            return Boolean.FALSE;
         } else {
            BackupRequest backuprequest = new BackupRequest(userinfo.userUid, serversession, "password");
            backuprequest.backupKey = s;

            Boolean obool;
            try {
               if (!fetchBackupInfo(backuprequest, null, networktask)) {
                  return Boolean.FALSE;
               }

               if (backuprequest.countBackupsForKey() != 0) {
                  return null;
               }

               if ((backuprequest.data = zipWorkToBase64(LogicProgram.workDir)) != null) {
                  return uploadBackup(backuprequest, networktask) ? Boolean.TRUE : Boolean.FALSE;
               }

               obool = Boolean.FALSE;
            } finally {
               finishBackupRequest(backuprequest, null);
            }

            return obool;
         }
      }
   }

   static boolean fetchBackupInfo(BackupRequest backuprequest, BusyIndicator busyindicator, NetworkTask networktask) {
      if (!backuprequest.isReady()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = fetchBackupInfoOnce(backuprequest, busyindicator, networktask);
         } while (!flag && askRetry(backuprequest));

         return flag;
      }
   }

   static boolean fetchBackupInfoOnce(BackupRequest backuprequest, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "userid";
      Hashtable hashtable = new Hashtable();
      hashtable.put("userid", backuprequest.userUid + "");
      backuprequest.session.setParams(hashtable, s);
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      Object[][] aobject = parseResponseTable(postMultipart(backupInfoUrl, backuprequest.session, networktask, "backup_info"));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      if (!verifyResponseSignature(aobject, backuprequest.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, backuprequest);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(backupInfoUrl, backuprequest.session, networktask, "backup_info", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, backuprequest);
               return false;
            } else {
               Integer integer2 = getResponseInteger(aobject, "backup count", backuprequest);
               if (integer2 == null) {
                  return false;
               } else {
                  int i = integer2;
                  backuprequest.backups = new BackupEntry[i];

                  for (int j = 0; j < i; j++) {
                     int k = findRow(aobject, "backup_" + (j + 1));
                     if (k == -1) {
                        reportError("Could not find \"BACKUP_" + (j + 1) + "\".", backuprequest);
                        return false;
                     }

                     Object[] aobject1 = aobject[k];
                     if (aobject1.length < 5 || aobject1[1] == null || aobject1[2] == null || aobject1[4] == null) {
                        reportError("Could not parse \"BACKUP_" + (j + 1) + "\".", backuprequest);
                        return false;
                     }

                     String s1 = ((String)aobject1[1]).trim();
                     String s2 = ((String)aobject1[2]).trim();
                     Integer integer1 = LogicProgram.parseInteger(((String)aobject1[4]).trim());
                     if (integer1 == null) {
                        reportError("Could not parse \"BACKUP_" + (j + 1) + "\".", backuprequest);
                        return false;
                     }

                     backuprequest.backups[j] = new BackupEntry(s1, s2, integer1);
                  }

                  return true;
               }
            }
         }
      }
   }

   static boolean restoreBackup(BackupRequest backuprequest, NetworkTask networktask) {
      if (!backuprequest.isReady()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = restoreBackupOnce(backuprequest, networktask);
         } while (!flag && askRetry(backuprequest));

         if (!flag) {
            deleteFilesIn(LogicProgram.workDir);
         }

         return flag;
      }
   }

   static boolean restoreBackupOnce(BackupRequest backuprequest, NetworkTask networktask) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", backuprequest.selectedBackupId + "");
      backuprequest.session.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(restoreUrl, backuprequest.session, networktask, "restore"));
      if (!verifyResponseSignature(aobject, backuprequest.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, backuprequest);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(restoreUrl, backuprequest.session, networktask, "restore", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, backuprequest);
               return false;
            } else {
               backuprequest.data = getResponseValue(aobject, "data", backuprequest);
               return backuprequest.data != null;
            }
         }
      }
   }

   static boolean deleteBackup(BackupRequest backuprequest, BusyIndicator busyindicator, NetworkTask networktask) {
      if (!backuprequest.isReady()) {
         return false;
      } else {
         boolean flag;
         do {
            flag = deleteBackupOnce(backuprequest, busyindicator, networktask);
         } while (!flag && askRetry(backuprequest));

         return flag;
      }
   }

   static boolean deleteBackupOnce(BackupRequest backuprequest, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = "backup";
      Hashtable hashtable = new Hashtable();
      hashtable.put("backup", backuprequest.selectedBackupId + "");
      if (busyindicator != null) {
         busyindicator.setBusy(true);
      }

      backuprequest.session.setParams(hashtable, s);
      Object[][] aobject = parseResponseTable(postMultipart(deleteBackupUrl, backuprequest.session, networktask, "delete_backup"));
      if (busyindicator != null) {
         busyindicator.setBusy(false);
      }

      if (!verifyResponseSignature(aobject, backuprequest.session)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, backuprequest);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(deleteBackupUrl, backuprequest.session, networktask, "delete_backup", busyindicator);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, backuprequest);
               return false;
            } else {
               return true;
            }
         }
      }
   }

   static void finishBackupRequest(BackupRequest backuprequest, BusyIndicator busyindicator) {
      closeSession(backuprequest.session, busyindicator);
      backuprequest.session = null;
   }

   static boolean backupWork(String s, BusyIndicator busyindicator) {
      if (s == null) {
         MessageDialog.showMessage(Message.get("not041"), null, null, null);
         return false;
      } else if (!MainMenu.saveAll()) {
         return false;
      } else {
         BackupRequest backuprequest = prepareBackup(LogicProgram.user, busyindicator);
         if (backuprequest == null) {
            return false;
         } else {
            backuprequest.backupKey = s;
            Message message = Message.get("not020");
            NetworkTask networktask = new NetworkTask(message.id, message.text);
            backuprequest.data = zipWorkToBase64(LogicProgram.workDir);
            if (backuprequest.data == null) {
               finishBackupRequest(backuprequest, busyindicator);
               MessageDialog.showMessage(Message.get("not015"), null, null, null);
               return false;
            } else if (!uploadBackup(backuprequest, networktask)) {
               finishBackupRequest(backuprequest, busyindicator);
               return false;
            } else {
               if (fetchBackupInfo(backuprequest, busyindicator, networktask)) {
                  backuprequest.pruneBackups(LogicProgram.maxBackups, busyindicator, networktask);
               }

               finishBackupRequest(backuprequest, busyindicator);
               LogicProgram.backupNeeded = false;
               MessageDialog.showMessage(Message.get("not017"), null, null, null);
               return true;
            }
         }
      }
   }

   static Boolean hasBackup(String s, String s1, NewUserInfo newuserinfo) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         Boolean obool = hasBackup(s, s1, serversession, newuserinfo);
         closeSession(serversession, null);
         return obool;
      }
   }

   static Boolean hasBackup(String s, String s1, ServerSession serversession, NewUserInfo newuserinfo) {
      Boolean obool = verifyUser(serversession, newuserinfo, null, (NetworkTask)null);
      if (obool != null && obool) {
         BackupRequest backuprequest = new BackupRequest(newuserinfo.userUid, serversession, "hasBackup");
         if (!fetchBackupInfo(backuprequest, null, (NetworkTask)null)) {
            return null;
         } else if (s1 != null && backuprequest.countBackups(s1) != 0) {
            return Boolean.TRUE;
         } else {
            return s != null && backuprequest.countBackups(s) != 0 ? Boolean.TRUE : Boolean.FALSE;
         }
      } else {
         return obool;
      }
   }

   static boolean restoreWork(String s, String s1, NewUserInfo newuserinfo) {
      if (s1 == null && s == null) {
         MessageDialog.showMessage(Message.get("not040"), null, null, null);
         return false;
      } else if (newuserinfo == null && (newuserinfo = AccountManager.createNewUser()) == null) {
         return false;
      } else {
         BackupRequest backuprequest = prepareBackup(newuserinfo, null);
         if (backuprequest == null) {
            return false;
         } else if (!fetchBackupInfo(backuprequest, null, (NetworkTask)null)) {
            finishBackupRequest(backuprequest, null);
            return false;
         } else {
            boolean flag = true;
            if (s1 != null) {
               backuprequest.backupKey = s1;
               if (backuprequest.countBackupsForKey() != 0) {
                  flag = false;
                  if (LogicProgram.getCredentials("restore") != null) {
                     ErrorRef errorref = UserSetup.checkAccess("restore", null, "not095");
                     if (errorref != null) {
                        String s2 = errorref.getId();
                        if (s2 != null) {
                           MessageDialog.showMessage(Message.get(s2), errorref.params, null, null);
                        }

                        flag = true;
                        return false;
                     }
                  }
               }
            }

            if (flag && s != null) {
               backuprequest.backupKey = s;
            }

            if (!UserSetup.chooseBackup(backuprequest)) {
               finishBackupRequest(backuprequest, null);
               return false;
            } else {
               Message message = Message.get("not021");
               NetworkTask networktask = new NetworkTask(message.id, message.text);
               if (!restoreBackup(backuprequest, networktask)) {
                  finishBackupRequest(backuprequest, null);
                  return false;
               } else {
                  boolean flag1 = unzipBase64Work(backuprequest.data, flag, LogicProgram.configDir);
                  if (flag1) {
                     LogicProgram.backupNeeded = false;
                  } else {
                     MessageDialog.showMessage(Message.get("not016"), null, null, null);
                  }

                  finishBackupRequest(backuprequest, null);
                  return true;
               }
            }
         }
      }
   }

   static boolean copyWork(File file1, File file2) {
      boolean flag = false;
      if (file1 != null && file2 != null) {
         Hashtable hashtable = Message.params("source", file1.getParent(), "dest", file2.toString());
         Message message = Message.get("not026");
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, hashtable, null, buttonchoicehandler);
         if (buttonchoicehandler.choice != 0) {
            return false;
         }

         if (file2.equals(LogicProgram.configDir)) {
            deleteWork(false);
         } else {
            deleteFilesIn(new File(file2, "work"));
         }

         ErrorRef errorref = new ErrorRef("not025", hashtable);

         while (!(flag = copyWorkFiles(file1, file2)) && askRetry(errorref)) {
         }

         if (flag) {
            LogicProgram.copyNeeded = false;
            MessageDialog.showMessage(Message.get("not024"), null, null, null);
         }
      }

      return flag;
   }

   static boolean copyWorkFiles(File file1, File file2) {
      if (!file1.exists()) {
         return false;
      } else {
         boolean flag = false;
         Message message = Message.get("not066");
         ProgressDialog progressdialog = new ProgressDialog(message.id, message.text, false);
         progressdialog.showWithMargins(20, 10);
         String s = zipWorkToBase64(file1);
         if (s != null) {
            flag = unzipBase64Work(s, false, file2);
         }

         progressdialog.dispose();
         return flag;
      }
   }

   static int deleteWork() {
      return deleteWork(true);
   }

   static int deleteWork(boolean flag) {
      Object object = null;
      if (flag) {
         Message message = Message.get("not030");
         ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message.buttons);
         MessageDialog.showMessage(message, null, null, buttonchoicehandler);
         if (buttonchoicehandler.choice != 0) {
            return 1;
         }
      }

      LogicProgram.backupNeeded = false;
      LogicProgram.copyNeeded = false;
      LogicProgram.closeDebugLogs();
      object = deleteFilesIn(LogicProgram.workDir);
      LogicProgram.workPresent = false;
      LogicProgram.openDebugLogs();
      String[] astring1 = LogicProgram.excludeLogFiles((String[])object);
      if (astring1 == null) {
         return 0;
      } else {
         int k = astring1.length;

         for (int i = 0; i < k; i++) {
            DiagnosticsLog.log("file not deleted: " + astring1[i]);
         }

         if (flag) {
            Dimension dimension = new Dimension(240, 300);
            ModuleFrame moduleframe = new ModuleFrame();
            SizedPanel sizedpanel = new SizedPanel();
            sizedpanel.setLayout(new BorderLayout());
            SizedPanel sizedpanel1 = new SizedPanel();
            sizedpanel1.setLayout(new VerticalStackLayout());

            for (int j = 0; j < k; j++) {
               sizedpanel1.add(new LogicLabel(astring1[j]));
            }

            SizedPanel sizedpanel2 = new SizedPanel();
            sizedpanel2.setLayout(new VerticalStackLayout());
            sizedpanel2.add(new LogicLabel(Message.getText("not032")));
            sizedpanel.add(sizedpanel2, "North");
            JScrollPane jscrollpane = new JScrollPane(sizedpanel1);
            sizedpanel.add(jscrollpane, "Center");
            String[] astring = new String[]{"OK"};
            MessageDialog messagedialog = new MessageDialog(moduleframe, "not032", sizedpanel, astring);
            messagedialog.setSize(dimension);
            messagedialog.showAt(MessageDialog.centeredLocation(dimension), true);
            moduleframe.dispose();
         }

         return 2;
      }
   }

   static String[] deleteFilesIn(File file1) {
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
            astring = (String[])vector.toArray(new String[i]);
         }
      }

      return astring;
   }

   static Boolean verifyUser(UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      ServerSession serversession = openSession(busyindicator);
      if (serversession == null) {
         return null;
      } else {
         Boolean obool = verifyUser(serversession, userinfo, busyindicator, networktask);
         closeSession(serversession, busyindicator);
         return obool;
      }
   }

   static Boolean verifyUser(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      Boolean obool;
      do {
         obool = verifyUserOnce(serversession, userinfo, busyindicator, networktask);
      } while (obool == null && askRetry(serversession));

      return obool;
   }

   static Boolean verifyUserOnce(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, NetworkTask networktask) {
      String s = userinfo.getStudentId(true);
      if (s.trim().equals("")) {
         return Boolean.FALSE;
      } else {
         Hashtable hashtable = new Hashtable();
         hashtable.put("uid", s);
         hashtable.put("institution", userinfo.getInstitution());
         serversession.setParams(hashtable, "uid.institution");
         if (busyindicator != null) {
            busyindicator.setBusy(true);
         }

         Object[][] aobject = parseResponseTable(postMultipart(verifyUrl, serversession, networktask, "userExists"));
         if (busyindicator != null) {
            busyindicator.setBusy(false);
         }

         if (!verifyResponseSignature(aobject, serversession)) {
            return null;
         } else {
            Integer integer = getErrorStatus(aobject, serversession);
            if (integer == null) {
               return null;
            } else {
               if (integer == -1) {
                  QueryResult queryresult = repostWithNewNonce(verifyUrl, serversession, networktask, "userExists", null);
                  if (queryresult == null) {
                     return null;
                  }

                  aobject = queryresult.table;
                  integer = queryresult.status;
               }

               if (integer != 0) {
                  reportError("status = " + integer, serversession);
                  return null;
               } else {
                  Integer integer1 = getResponseInteger(aobject, "return_value", serversession);
                  if (integer1 == null) {
                     return null;
                  } else if (integer1 != 1) {
                     return Boolean.FALSE;
                  } else {
                     userinfo.userUid = getResponseInteger(aobject, "logic_user_uid", serversession);
                     return userinfo.userUid == null ? null : Boolean.TRUE;
                  }
               }
            }
         }
      }
   }

   static Integer loginUser(ServerSession serversession, UserInfo userinfo, PasswordEntry passwordentry, String s, int i) {
      if (userinfo == null) {
         return null;
      } else {
         Integer integer;
         do {
            integer = loginUserOnce(serversession, userinfo, (NetworkTask)null, passwordentry, s, i);
         } while (integer == null && askRetry(serversession));

         if (integer == null) {
            return null;
         } else {
            if (integer != 0) {
               if (s == null) {
                  return null;
               }

               if (passwordentry.retried) {
                  MessageDialog.showMessage("Authorization Failed", s, null, null);
                  return null;
               }

               passwordentry.retried = true;
               userinfo.userUid = retryLogin(serversession, userinfo, passwordentry, s, i);
            }

            if (userinfo.userUid == null) {
               return null;
            } else {
               if (userinfo instanceof NewUserInfo) {
                  ((NewUserInfo)userinfo).passwordHash = passwordentry.newPassword == null ? passwordentry.password : passwordentry.newPassword;
               }

               return userinfo.userUid;
            }
         }
      }
   }

   static Integer loginUserOnce(ServerSession serversession, UserInfo userinfo, NetworkTask networktask, PasswordEntry passwordentry, String s, int i) {
      Hashtable hashtable = new Hashtable();
      boolean flag = userinfo instanceof NewUserInfo;
      if (!flag) {
         hashtable.put("first_name", userinfo.getFirstName());
         hashtable.put("middle_name", userinfo.getMiddleName());
         hashtable.put("last_name", userinfo.getLastName());
      }

      hashtable.put("uid", userinfo.getStudentId(true));
      hashtable.put("password", passwordentry.password);
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
      if (!verifyResponseSignature(aobject, serversession)) {
         return null;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(userUrl, serversession, networktask, "loginUser", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer == 0) {
               userinfo.userUid = getResponseInteger(aobject, "logic_user_uid", serversession);
               if (userinfo.userUid == null) {
                  return null;
               }
            }

            return integer;
         }
      }
   }

   static Integer retryLogin(ServerSession serversession, UserInfo userinfo, PasswordEntry passwordentry, String s, int i) {
      if (AccountManager.reenterPassword(serversession, userinfo, passwordentry, s) == null) {
         return null;
      } else if (loginUser(serversession, userinfo, passwordentry, s, i) != null) {
         return userinfo.userUid;
      } else if (LogicProgram.getCredentials("developer") == null) {
         return null;
      } else {
         ErrorRef errorref = UserSetup.checkAccess("developer", "Developer");
         if (errorref != null) {
            String s1 = errorref.getId();
            if (s1 != null) {
               MessageDialog.showMessage(Message.get(s1), errorref.params, null, null);
            }

            return null;
         } else {
            Boolean obool = verifyUser(serversession, userinfo, null, (NetworkTask)null);
            return obool != null && obool ? userinfo.userUid : null;
         }
      }
   }

   static Boolean checkRegistration(ServerSession serversession, UserInfo userinfo) {
      Boolean obool = verifyUser(serversession, userinfo, null, (NetworkTask)null);
      if (obool == null) {
         return Boolean.FALSE;
      } else if (!obool) {
         return null;
      } else {
         LoginResult loginresult = LoginResult.login(serversession, userinfo, null, 1);
         if (loginresult == null) {
            return Boolean.FALSE;
         } else {
            if (LogicProgram.mainMenu == null) {
               cachedLogin = loginresult;
            }

            return Boolean.TRUE;
         }
      }
   }

   static UserInfo fetchUserInfo(String s, String s1) {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return null;
      } else {
         UserInfo userinfo = fetchUserInfo(serversession, s, s1);
         closeSession(serversession, null);
         return userinfo;
      }
   }

   static UserInfo fetchUserInfo(ServerSession serversession, String s, String s1) {
      if (userInfoUrl == null) {
         return null;
      } else {
         UserInfo userinfo;
         do {
            userinfo = fetchUserInfoOnce(serversession, s, s1);
         } while (userinfo == null && askRetry(serversession));

         return userinfo;
      }
   }

   static UserInfo fetchUserInfoOnce(ServerSession serversession, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("institution", s);
      hashtable.put("studentID", s1);
      serversession.setParams(hashtable, "institution.studentID");
      Object[][] aobject = parseResponseTable(postMultipart(userInfoUrl, serversession, (NetworkTask)null, "getUserInfo"));
      if (!verifyResponseSignature(aobject, serversession)) {
         return null;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(userInfoUrl, serversession, (NetworkTask)null, "getUserInfo", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            return integer != 0 ? null : null;
         }
      }
   }

   static boolean changePassword(ServerSession serversession, int i, String s, String s1) {
      Boolean obool;
      do {
         obool = changePasswordOnce(serversession, (NetworkTask)null, i, s, s1);
      } while (obool == null && askRetry(serversession));

      return obool != null && obool;
   }

   static Boolean changePasswordOnce(ServerSession serversession, NetworkTask networktask, int i, String s, String s1) {
      Hashtable hashtable = new Hashtable();
      hashtable.put("logic_user_uid", i + "");
      hashtable.put("old_password", s);
      hashtable.put("new_password", s1);
      serversession.setParams(hashtable, "logic_user_uid.old_password.new_password");
      Object[][] aobject = parseResponseTable(postMultipart(passwordUrl, serversession, networktask, "changePassword"));
      if (!verifyResponseSignature(aobject, serversession)) {
         return null;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         if (integer == null) {
            return null;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(passwordUrl, serversession, networktask, "changePassword", null);
               if (queryresult == null) {
                  return null;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            return integer == 0 ? Boolean.TRUE : Boolean.FALSE;
         }
      }
   }

   static boolean fetchCourseList() {
      ServerSession serversession = openSession(null);
      if (serversession == null) {
         return false;
      } else {
         boolean flag = fetchCourseList(serversession);
         closeSession(serversession, null);
         return flag;
      }
   }

   static boolean fetchCourseList(ServerSession serversession) {
      Message message = Message.get("not071");

      boolean flag;
      do {
         NetworkTask networktask = new NetworkTask(message.id, message.text);
         flag = fetchCourseListOnce(serversession, networktask);
      } while (!flag && askRetry(serversession));

      return flag;
   }

   static boolean fetchCourseListOnce(ServerSession serversession, NetworkTask networktask) {
      Vector vector = new Vector();
      Hashtable hashtable = new Hashtable();
      hashtable.put("arch", LogicProgram.arch);
      serversession.setParams(hashtable, "arch");
      Object[][] aobject = parseResponseTable(postMultipart(courseRemoteUrl, serversession, networktask, "getRemoteCourses"));
      if (!verifyResponseSignature(aobject, serversession)) {
         return false;
      } else {
         Integer integer = getErrorStatus(aobject, serversession);
         if (integer == null) {
            return false;
         } else {
            if (integer == -1) {
               QueryResult queryresult = repostWithNewNonce(courseRemoteUrl, serversession, networktask, "getRemoteCourses", null);
               if (queryresult == null) {
                  return false;
               }

               aobject = queryresult.table;
               integer = queryresult.status;
            }

            if (integer != 0) {
               reportError("status = " + integer, serversession);
               return false;
            } else {
               Integer integer3 = getResponseInteger(aobject, "course count", serversession);
               if (integer3 == null) {
                  return false;
               } else {
                  int i = integer3;

                  for (int j = 1; j <= i; j++) {
                     int k = findRow(aobject, "course_" + j);
                     if (k == -1) {
                        reportError("Could not find \"COURSE" + j + "\".", serversession);
                        return false;
                     }

                     Integer integer1 = LogicProgram.parseInteger((String)aobject[k][4]);
                     if (integer1 == null) {
                        reportError("Could not parse \"" + (String)aobject[k][4] + "\" as a number.", serversession);
                        return false;
                     }

                     String s = aobject[k].length > 5 ? (String)aobject[k][5] : null;
                     if (s != null) {
                        s = s.trim();
                     }

                     Integer integer2 = null;
                     if (aobject[k].length > 6 && (integer2 = LogicProgram.parseInteger((String)aobject[k][6])) == null) {
                        reportError("Could not parse \"" + (String)aobject[k][6] + "\" as a number.", serversession);
                        return false;
                     }

                     String s1 = aobject[k].length > 7 ? (String)aobject[k][7] : null;
                     CourseInfo courseinfo = new CourseInfo((String)aobject[k][1], (String)aobject[k][2], (String)aobject[k][3], integer1, s, integer2, s1);
                     vector.addElement(courseinfo);
                  }

                  CourseInfo.allCourses = new CourseInfo[vector.size()];
                  vector.copyInto(CourseInfo.allCourses);
                  MergeSorter mergesorter = new MergeSorter(new CourseOrder());
                  CourseInfo.allCourses = (CourseInfo[])mergesorter.sort(CourseInfo.allCourses);
                  return true;
               }
            }
         }
      }
   }

   static Integer getErrorStatus(Object[][] aobject, ResponseHandler responsehandler) {
      return getResponseInteger(aobject, "error status", responsehandler);
   }

   static void reportError(String s, ResponseHandler responsehandler) {
      if (responsehandler == null) {
         Message message = Message.get("not008");
         MessageDialog.showMessage(message.id, message.text + "\n\n" + s, null, null);
      } else {
         Hashtable hashtable = new Hashtable();
         if (s != null) {
            hashtable.put("commErrorMsg", s);
         }

         responsehandler.setError("not008", hashtable);
      }
   }

   static boolean askRetry(ResponseHandler responsehandler) {
      Message message1 = Message.get("not037");
      ErrorRef errorref;
      String s;
      Message message;
      if (responsehandler != null && (errorref = responsehandler.getError()) != null && errorref.id != null) {
         message = Message.get(errorref.id);
         String s2 = message.text;
         if (errorref.params != null) {
            s2 = Message.substitute(s2, errorref.params);
            String s1 = (String)errorref.params.get("commErrorMsg");
            if (s1 != null) {
               s2 = s2 + "\n\n" + s1;
            }
         }

         s = s2 + "\n\n" + message1.text;
      } else {
         message = message1;
         s = message1.text;
      }

      ButtonChoiceHandler buttonchoicehandler = new ButtonChoiceHandler(message1.buttons);
      MessageDialog.showMessage(message.id, s, null, buttonchoicehandler);
      return buttonchoicehandler.choice == 0;
   }
}
