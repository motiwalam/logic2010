package edu.ucla.phil.logic;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.border.EmptyBorder;

class C_IF extends C_s_D {
   Frame f424;
   C_UA f425;

   C_IF(Frame frame) {
      this("", -1, false, frame);
   }

   C_IF(String s, Frame frame) {
      this(s, -1, false, frame);
   }

   C_IF(boolean flag) {
      this("", -1, flag, null);
   }

   C_IF(String s, int i, Frame frame) {
      this(s, i, false, frame);
   }

   C_IF(String s, int i, boolean flag, Frame frame) {
      super(s, i, flag);
      this.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.f424 = frame;
      this.f425 = null;
   }

   @Override
   public String getName() {
      String s = super.getName();
      return s == null ? "" : s;
   }

   void m712(Frame frame) {
      this.f424 = frame;
   }

   void m713(C_UA c_ua) {
      this.f425 = c_ua;
   }

   @Override
   void m714() {
      Rectangle rectangle = LogicProgram.m1036(this, null);
      C_a_E c_a_e;
      if (this.f425 == null) {
         c_a_e = new C_a_E(this.f424, this.getName(), false, this);
      } else {
         c_a_e = new C_a_E(this.f425, this.getName(), false, this);
      }

      c_a_e.f978 = this.f425;
      String[] astring = new String[]{"\\l->", "\\l~", "\\l&", "\\l|", "\\l<->", "\\l@", "\\l!", "=", "\\l<>", "\\l%"};
      String[] astring1 = LogicProgram.m1022(LogicProgram.f599);
      String[] astring2 = new String[]{"(", ")", ".", "\\l.:"};
      String[] astring3 = LogicProgram.m1022(LogicProgram.f601);
      String[] astring4 = LogicProgram.m1022(LogicProgram.f600);
      String[] astring5 = LogicProgram.m1022("xyzuvw");
      String[] astring6 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
      String[][] astring7 = new String[][]{astring, astring1, astring2, astring3, astring4, astring5, astring6};
      C_JF c_jf;
      c_a_e.add(c_jf = new C_JF(c_a_e, astring7), "West");
      String[] astring8 = new String[]{
         "Ctrl+Shift+C", "Ctrl+Shift+N", "Ctrl+Shift+A", "Ctrl+Shift+O", "Ctrl+Shift+B", "Ctrl+Shift+U", "Ctrl+Shift+E", null, "Ctrl+Shift+I", "Ctrl+Shift+D"
      };
      String[] astring9 = new String[]{null, null, null, "Ctrl+Shift+T"};
      String[][] astring10 = new String[][]{astring8, null, astring9};
      c_jf.m732(astring10);
      c_jf.setEnabled(true);
      c_jf.requestFocus();
      astring = new String[]{"space", "backspace", "copy", "paste"};
      String[][] astring11 = new String[][]{astring};
      c_a_e.add(c_jf = new C_JF(c_a_e, astring11, true, false), "West");
      astring2 = new String[]{null, null, "Ctrl+C", "Ctrl+V"};
      String[][] astring12 = new String[][]{astring2};
      c_jf.m732(astring12);
      c_jf.setEnabled(true);
      c_jf.requestFocus();
      c_a_e.invalidate();
      c_a_e.setEnabled(true);
      c_a_e.requestFocus();
      c_a_e.m1302(new Point(rectangle.x, rectangle.y + rectangle.height));
      c_a_e.setResizable(false);
      c_a_e.m1301();
   }

   void m715(C_UA c_ua) {
      if (c_ua != null) {
         c_ua.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent windowevent) {
               C_IF.this.getCaret().setVisible(true);
            }

            @Override
            public void windowLostFocus(WindowEvent windowevent) {
            }
         });
      }
   }
}
