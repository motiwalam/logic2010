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

abstract class C_U extends C_TA implements C_XC, C_LC {
   static Dimension moduleSize = new Dimension(64 * LogicProgram.f539, 40 * LogicProgram.f539);
   C_0E frame = null;
   boolean forPrint;
   int problemIndex = -1;
   Color[] colors;
   static String[] kaplan = LogicProgram.f596;
   static String[] html = LogicProgram.f598;
   C_g_D titlePanel;
   static C_d_A[] problemEditors = new C_d_A[moduleClasses.length];
   static boolean eraseWork = false;

   abstract boolean shutdown(boolean flag);

   abstract boolean save();

   abstract void resize();

   abstract Frame getFrame();

   abstract int getModuleIndex();

   abstract int getProblemState(C_XD c_xd);

   C_U(boolean flag) {
      this.colors = (this.forPrint = flag) ? LogicProgram.f605 : LogicProgram.f607;
      this.setForeground(this.colors[0]);
      this.setBackground(this.colors[1]);
      this.titlePanel = new C_g_D(this.colors);
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

   static C_e_D getExercises(int i, boolean flag, boolean flag1, boolean flag2) {
      C_e_D c_e_d = null;

      try {
         Object[] aobject = new Object[]{new Boolean(flag), new Boolean(flag1), new Boolean(flag2)};
         Class[] aclass = new Class[]{boolean.class, boolean.class, boolean.class};
         Method method = moduleClasses[i].getDeclaredMethod("readExercises", aclass);
         c_e_d = (C_e_D)method.invoke(null, aobject);
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

      return c_e_d;
   }

   static C_e_D getWork(int i) {
      C_e_D c_e_d = null;

      try {
         Object[] aobject = new Object[0];
         Class[] aclass = new Class[0];
         Method method = moduleClasses[i].getDeclaredMethod("readWork", aclass);
         c_e_d = (C_e_D)method.invoke(null, aobject);
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

      return c_e_d;
   }

   static boolean readProblems(Reader reader, C_e_D c_e_d, boolean flag, boolean flag1) {
      C_XB c_xb;
      if (reader instanceof C_XB) {
         c_xb = (C_XB)reader;
      } else {
         c_xb = new C_XB(reader, LogicProgram.f537);
      }

      if (flag && c_e_d.f1075 == null) {
         c_e_d.f1075 = new Hashtable();
      }

      try {
         Vector vector = null;

         String s;
         while ((s = c_xb.readLine()) != null) {
            if (C_XD.m1511(s)) {
               if (flag && s.indexOf("#-") == 0) {
                  if (vector == null) {
                     vector = new Vector();
                  }

                  vector.addElement(s.substring(2));
               } else if (s.indexOf(35) == 0) {
                  c_e_d.f1076 = s.substring(1).trim();
               }
            } else {
               if (c_e_d.m1098(s, vector, flag1) != -1) {
                  vector = null;
               }

               if (LogicProgram.f572 && !c_e_d.f1080 && flag) {
                  C_VC c_vc = new C_VC(c_e_d.m1768(s), true);
                  String s1 = c_vc.m1388(true, true);
                  if (s1 != null) {
                     String s2 = C_XD.m1493(s);
                     System.out.println(s2 + ": " + s1);
                  }
               }
            }
         }

         c_xb.close();
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   static void writeProblems(C_e_D c_e_d, Writer writer) throws IOException {
      String s = c_e_d.m1786();
      if (!LogicProgram.f533.m1149(s, "").equals("1")) {
         LogicProgram.f533.put(s, "1");
         LogicProgram.f533.m684();
      }

      synchronized (c_e_d) {
         if (c_e_d == null) {
            return;
         }

         BufferedWriter bufferedwriter;
         if (writer instanceof BufferedWriter) {
            bufferedwriter = (BufferedWriter)writer;
         } else {
            bufferedwriter = new BufferedWriter(writer);
         }

         int i = c_e_d.size();

         for (int j = 0; j < i; j++) {
            String s1 = c_e_d.m1778(j);
            bufferedwriter.write(s1, 0, s1.length());
            bufferedwriter.newLine();
         }

         String s2 = "# " + c_e_d.m1777(LogicProgram.f533);
         bufferedwriter.write(s2, 0, s2.length());
         bufferedwriter.newLine();
         bufferedwriter.close();
      }

      LogicProgram.f581 = true;
      LogicProgram.f582 = true;
   }

   static void writeExercises(C_e_D c_e_d, Writer writer) throws IOException {
      if (c_e_d != null) {
         BufferedWriter bufferedwriter;
         if (writer instanceof BufferedWriter) {
            bufferedwriter = (BufferedWriter)writer;
         } else {
            bufferedwriter = new BufferedWriter(writer);
         }

         Enumeration enumeration = c_e_d.elements();

         while (enumeration.hasMoreElements()) {
            C_f_F c_f_f = (C_f_F)enumeration.nextElement();
            String s = C_XD.m1493(c_f_f.f1119);
            Vector vector;
            if (c_e_d.f1075 != null && s != null) {
               vector = (Vector)c_e_d.f1075.get(s);
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

            bufferedwriter.write(c_f_f.f1119);
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
         C_H c_h = C_H.m411("not093");
         C_UA.m1329(c_h, null, null, null);
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
            {Rectangle.class, C_x_A.class, String.class}, {Hashtable.class, Hashtable.class}, {boolean.class, boolean.class, boolean.class}, new Class[0]
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

   abstract static class C__A implements Runnable {
      C_x_A f782;
      Rectangle f783;
      String f784;

      C__A(C_x_A c_x_a, Rectangle rectangle, String s) {
         this.f782 = c_x_a;
         this.f783 = rectangle;
         this.f784 = s;
      }

      void m1310() {
         if (this.f782 != null) {
            this.f782.m2162(false);
            this.f782 = null;
         }
      }

      public abstract void m959();
   }
}
