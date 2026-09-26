package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

class C_RE extends C_D implements C_v_D {
   static String[] f737 = LogicProgram.f596;

   C_RE(String s) {
      super(s);
   }

   @Override
   boolean m451(C_UA c_ua) {
      String s = this.m450(c_ua);
      if (s == null) {
         return true;
      } else if (!s.equalsIgnoreCase("up")) {
         if (s.equalsIgnoreCase("ok")) {
            C_d_C c_d_c4 = (C_d_C)this.m449("target");
            c_d_c4.f1045.f1438.requestFocus();
            return true;
         } else if (s.equalsIgnoreCase("text")) {
            C_d_C c_d_c3 = (C_d_C)this.m449("target");
            C_d_C c_d_c6 = (C_d_C)this.m449("answer");
            Vector vector3 = (Vector)this.m449("targetBinders");
            Vector vector5 = (Vector)this.m449("answerBinders");
            if (!c_d_c3.f1045.f1438.isEditable()) {
               Toolkit.getDefaultToolkit().beep();
            } else {
               c_d_c3.f1045.f1438.setText(C_f_A.m1807(c_d_c6.f1045.f1438.getText(), vector5, vector3));
            }

            return false;
         } else if (s.equalsIgnoreCase("symb")) {
            C_d_C c_d_c2 = (C_d_C)this.m449("target");
            C_d_C c_d_c5 = (C_d_C)this.m449("answer");
            Vector vector2 = (Vector)this.m449("targetBinders");
            Vector vector4 = (Vector)this.m449("answerBinders");
            String s2 = c_d_c5.f1048 == 11 ? c_d_c5.m1685().f837.getText() : null;
            s2 = C_OA.m1139(C_d_C.m1732(LogicProgram.m995(s2, f737, maggie), vector4, vector2), "\\{");
            c_d_c2.m1691(c_d_c5.f1048, s2, true);
            return false;
         } else {
            return true;
         }
      } else {
         C_d_C c_d_c = (C_d_C)this.m449("target");
         C_d_C c_d_c1 = (C_d_C)this.m449("answer");
         Vector vector = (Vector)this.m449("targetBinders");
         Vector vector1 = (Vector)this.m449("answerBinders");
         c_d_c = c_d_c.m1688();
         c_d_c1 = c_d_c1.m1688();
         if (c_d_c != null && c_d_c1 != null) {
            this.m447("target", c_d_c);
            this.m447("answer", c_d_c1);
            if (c_d_c.m1726()) {
               vector.setSize(vector.size() - 1);
            }

            if (c_d_c1.m1726()) {
               vector1.setSize(vector1.size() - 1);
            }

            C_H c_h = C_h_E.m411("symnot001");
            Hashtable hashtable = C_CD.m425(c_d_c1, vector, vector1);
            String s1 = LogicProgram.m1004(C_H.m661(c_h.f372, hashtable));
            if (c_ua.f792 instanceof JScrollPane) {
               JViewport jviewport = ((JScrollPane)c_ua.f792).getViewport();
               if (jviewport != null) {
                  Component component = jviewport.getView();
                  if (component instanceof C_s_D) {
                     ((C_s_D)component).setText(s1);
                  }
               }
            }

            Rectangle rectangle = LogicProgram.m1035(c_d_c.f1045.f1438, null);
            Dimension dimension = c_ua.getSize();
            Point point = new Point(rectangle.x + rectangle.width / 2 - dimension.width / 2, rectangle.y + rectangle.height);
            c_ua.setLocation(point);
            c_ua.setResizable(true);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }

         return false;
      }
   }
}
