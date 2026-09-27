package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

abstract class LogicModule extends CellPanel implements ModuleConstants, ModuleComponentMarker {
   static Dimension moduleSize = new Dimension(64 * LogicProgram.fontSize, 40 * LogicProgram.fontSize);
   ModuleFrame frame = null;
   boolean forPrint;
   int problemIndex = -1;
   Color[] colors;
   static String[] kaplan = LogicProgram.symbols;
   static String[] html = LogicProgram.htmlSymbols;
   ProblemTitlePanel titlePanel;
   static ProblemEditorFrame[] problemEditors = new ProblemEditorFrame[moduleClasses.length];
   static boolean eraseWork = false;

   abstract boolean shutdown(boolean flag);

   abstract boolean save();

   abstract void resize();

   abstract Frame getFrame();

   abstract int getModuleIndex();

   abstract int getProblemState(TaggedRecord taggedrecord);

   LogicModule(boolean flag) {
      this.colors = (this.forPrint = flag) ? LogicProgram.printColors : LogicProgram.moduleColors;
      this.setForeground(this.colors[0]);
      this.setBackground(this.colors[1]);
      this.titlePanel = new ProblemTitlePanel(this.colors);
      staticCheck(this.getModuleIndex());
      this.enableEvents(8L);
   }

   void showInFront() {
      if (this.frame.getState() != 0) {
         this.frame.setState(0);
      }

      this.frame.toFront();
      this.frame.requestFocus();
   }

   static Object getStaticField(int i, String s) {
      return getStaticField(moduleClasses[i], s);
   }

   static void setStaticField(int i, String s, Object object) {
      setStaticField(moduleClasses[i], s, object);
   }

   static Object getStaticField(Class oclass, String s) {
      try {
         return oclass.getDeclaredField(s).get(null);
      } catch (NoSuchFieldException nosuchfieldexception) {
      } catch (IllegalAccessException illegalaccessexception) {
      }

      return null;
   }

   static void setStaticField(Class oclass, String s, Object object) {
      try {
         oclass.getDeclaredField(s).set(null, object);
      } catch (NoSuchFieldException nosuchfieldexception) {
      } catch (IllegalAccessException illegalaccessexception) {
      }
   }

   static ProblemSet getExercises(int i, boolean flag, boolean flag1, boolean flag2) {
      ProblemSet problemset = null;

      try {
         Object[] aobject = new Object[]{new Boolean(flag), new Boolean(flag1), new Boolean(flag2)};
         Class[] aclass = new Class[]{boolean.class, boolean.class, boolean.class};
         Method method = moduleClasses[i].getDeclaredMethod("readExercises", aclass);
         problemset = (ProblemSet)method.invoke(null, aobject);
      } catch (NoSuchMethodException nosuchmethodexception) {
      } catch (IllegalAccessException illegalaccessexception) {
      } catch (InvocationTargetException invocationtargetexception) {
         Throwable throwable = invocationtargetexception.getTargetException();
         if (throwable instanceof Error) {
            throw (Error)throwable;
         }

         if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         }
      }

      return problemset;
   }

   static ProblemSet getWork(int i) {
      ProblemSet problemset = null;

      try {
         Object[] aobject = new Object[0];
         Class[] aclass = new Class[0];
         Method method = moduleClasses[i].getDeclaredMethod("readWork", aclass);
         problemset = (ProblemSet)method.invoke(null, aobject);
      } catch (NoSuchMethodException nosuchmethodexception) {
      } catch (IllegalAccessException illegalaccessexception) {
      } catch (InvocationTargetException invocationtargetexception) {
         Throwable throwable = invocationtargetexception.getTargetException();
         if (throwable instanceof Error) {
            throw (Error)throwable;
         }

         if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         }
      }

      return problemset;
   }

   static boolean readProblems(Reader reader, ProblemSet problemset, boolean flag, boolean flag1) {
      ScrambledReader scrambledreader;
      if (reader instanceof ScrambledReader) {
         scrambledreader = (ScrambledReader)reader;
      } else {
         scrambledreader = new ScrambledReader(reader, LogicProgram.scrambleKey);
      }

      if (flag && problemset.headingsByName == null) {
         problemset.headingsByName = new Hashtable();
      }

      try {
         Vector vector = null;

         String s;
         while ((s = scrambledreader.readLine()) != null) {
            if (TaggedRecord.isBlankOrComment(s)) {
               if (flag && s.indexOf("#-") == 0) {
                  if (vector == null) {
                     vector = new Vector();
                  }

                  vector.addElement(s.substring(2));
               } else if (s.indexOf(35) == 0) {
                  problemset.storedDigest = s.substring(1).trim();
               }
            } else {
               if (problemset.addProblem(s, vector, flag1) != -1) {
                  vector = null;
               }

               if (LogicProgram.debug && !problemset.skipArgumentCheck && flag) {
                  ArgumentParser argumentparser = new ArgumentParser(problemset.getStatement(s), true);
                  String s1 = argumentparser.describeError(true, true);
                  if (s1 != null) {
                     String s2 = TaggedRecord.nameOf(s);
                     System.out.println(s2 + ": " + s1);
                  }
               }
            }
         }

         scrambledreader.close();
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   /** Saves a module's work: workFileName is the internal work file name, e.g. "derwork.txt". */
   static void writeProblems(ProblemSet problemset, String workFileName) throws IOException {
      String s = problemset.getDigestVersKey();
      if (!LogicProgram.user.getField(s, "").equals("1")) {
         LogicProgram.user.put(s, "1");
         LogicProgram.user.save();
      }

      synchronized (problemset) {
         Vector vector = new Vector();
         int i = problemset.size();

         for (int j = 0; j < i; j++) {
            vector.addElement(problemset.getRecordAt(j));
         }

         // the digest covers the records as they will be read back from the saved file
         vector = DataFiles.canonicalRecords(vector, DataFiles.schemaForKey(workFileName));
         String s1 = LogicProgram.user.computeDigest(vector.elements(), (String)LogicProgram.user.get(s));
         DataFiles.writeWork(LogicProgram.workDir, workFileName, vector, s1);
      }

      LogicProgram.backupNeeded = true;
      LogicProgram.copyNeeded = true;
   }

   static void writeExercises(ProblemSet problemset, Writer writer) throws IOException {
      if (problemset != null) {
         BufferedWriter bufferedwriter;
         if (writer instanceof BufferedWriter) {
            bufferedwriter = (BufferedWriter)writer;
         } else {
            bufferedwriter = new BufferedWriter(writer);
         }

         Enumeration enumeration = problemset.elements();

         while (enumeration.hasMoreElements()) {
            ProblemEntry problementry = (ProblemEntry)enumeration.nextElement();
            String s = TaggedRecord.nameOf(problementry.name);
            Vector vector;
            if (problemset.headingsByName != null && s != null) {
               vector = (Vector)problemset.headingsByName.get(s);
            } else {
               vector = null;
            }

            if (vector != null) {
               Enumeration enumeration1 = vector.elements();

               while (enumeration1.hasMoreElements()) {
                  bufferedwriter.write("#-" + enumeration1.nextElement());
                  bufferedwriter.newLine();
               }
            }

            bufferedwriter.write(problementry.name);
            bufferedwriter.newLine();
         }
      }
   }

   static boolean validateUserProblem(String s) {
      boolean flag = false;
      if (!s.contains("\r") && !s.contains("\n")) {
         flag = true;
      }

      if (!flag) {
         Message message = Message.get("not093");
         MessageDialog.showMessage(message, null, null, null);
      }

      return flag;
   }

   static boolean staticCheck(int i) {
      if (i == -1) {
         return true;
      } else {
         Class oclass = moduleClasses[i];
         String[] astring = new String[]{"instances", "exercises", "digestVersKey", "monoProbs", "messageClass"};
         String[] astring1 = new String[]{"startup", "checkQuit", "readExercises", "readWork"};
         Class[][] aclass = new Class[][]{
            {Rectangle.class, BusyIndicator.class, String.class},
            {Hashtable.class, Hashtable.class},
            {boolean.class, boolean.class, boolean.class},
            new Class[0]
         };
         boolean flag = true;
         int j = astring.length;

         while (--j >= 0) {
            if (!hasStaticField(oclass, astring[j])) {
               System.out.println("missing static field: " + astring[j]);
               flag = false;
            }
         }

         j = Math.min(astring1.length, aclass.length);

         while (--j >= 0) {
            if (!hasStaticMethod(oclass, astring1[j], aclass[j])) {
               System.out.println("missing static method: " + astring1[j]);
               flag = false;
            }
         }

         return flag;
      }
   }

   static boolean hasStaticField(Class oclass, String s) {
      try {
         return (oclass.getDeclaredField(s).getModifiers() & 8) != 0;
      } catch (NoSuchFieldException nosuchfieldexception) {
         return false;
      }
   }

   static boolean hasStaticMethod(Class oclass, String s, Class[] aclass) {
      try {
         return (oclass.getDeclaredMethod(s, aclass).getModifiers() & 8) != 0;
      } catch (NoSuchMethodException nosuchmethodexception) {
         return false;
      }
   }

   void invalrepaint() {
      this.revalidate();
      this.repaint();
   }

   abstract static class ModuleStartupTask implements Runnable {
      BusyIndicator busyIndicator;
      Rectangle bounds;
      String problemName;

      ModuleStartupTask(BusyIndicator busyindicator, Rectangle rectangle, String s) {
         this.busyIndicator = busyindicator;
         this.bounds = rectangle;
         this.problemName = s;
      }

      void stopBusyIndicator() {
         if (this.busyIndicator != null) {
            this.busyIndicator.setBusy(false);
            this.busyIndicator = null;
         }
      }

      public abstract void continueStartup();
   }
}
