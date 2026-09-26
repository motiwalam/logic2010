package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.event.WindowEvent;

class C_BA extends C_T {
   C_l_E f235;
   boolean f236;

   C_BA(C_l_E c_l_e) {
      super(c_l_e.f1258.f317.f915.frame, m374(c_l_e), false, c_l_e);
      this.f235 = c_l_e;
      this.f236 = false;
      this.setLayout(new C_m_A());
   }

   static String m374(C_l_E c_l_e) {
      int i = c_l_e.f1258.m30();
      return i == 0 ? "Problem" : "Line " + i;
   }

   @Override
   public void windowLostFocus(WindowEvent windowevent) {
      Object object = windowevent.getSource();
      if (!this.f777 && !LogicProgram.m1033(this, (Component)object)) {
         if (this.f235 == this.f235.f1258.f323) {
            this.f235.f1258.m594();
         } else if (this.f235 == this.f235.f1258.f324) {
            this.f235.f1258.m586();
         }
      }

      super.windowLostFocus(windowevent);
   }

   @Override
   public String m375(String s) {
      C_G c_g = this.f235.f1258;
      boolean flag = c_g.f324 == this.f235;
      if (s == null) {
         return null;
      } else {
         s = LogicProgram.m995(s, new String[]{"\n"}, new String[]{" "});
         if (s.equals("backspace")) {
            s = "\b";
         } else if (s.equals("space")) {
            s = " ";
         } else if (s.equals("tab")) {
            if (c_g.f323 != null && c_g.f324 != null && c_g.m564() == null) {
               c_g.f317.f915.requestAid = true;
               c_g.m22(!flag);
            }

            s = null;
         } else if (s.equals("enter")) {
            if (!flag) {
               c_g.m564();
            }

            if (c_g.f317.f915.commandMode) {
               if (c_g.f317.f915.focus != c_g.f324) {
                  c_g.m586();
               }

               c_g.m563(c_g.f323 != null);
            }

            C_G c_g1 = c_g.m18();
            if (c_g1 != null) {
               c_g.f317.f915.requestAid = true;
               c_g1.m22(flag);
            }

            s = null;
         } else if (s.equals("up")) {
            C_0B c_0b = c_g.m24(true);
            if (c_0b != null) {
               c_g.f317.f915.requestAid = true;
               c_0b.m22(flag);
            }

            s = null;
         } else if (s.equals("down")) {
            C_0B c_0b1 = c_g.m23(true);
            if (c_0b1 != null) {
               c_g.f317.f915.requestAid = true;
               c_0b1.m22(flag);
            }

            s = null;
         } else if (s.equals("in")) {
            this.f235.f1258.m566();
            s = null;
         } else if (s.equals("out")) {
            this.f235.f1258.m567();
            s = null;
         } else if (s.equals("delete line")) {
            if (c_g.f317.f915.problem.f917 != c_g) {
               C_0B c_0b2 = c_g.m23(false);
               if (c_0b2 == null) {
                  c_0b2 = c_g.m24(true);
               }

               c_g.m19(false);
               c_g.f317.f915.requestAid = true;
               c_0b2.m22(flag);
            }

            s = null;
         } else if (s.equals("Show/Unshow")) {
            if (!flag) {
               C_0B c_0b3 = c_g.m570();
               if (c_0b3 != null) {
                  this.f235 = c_0b3.m14();
               }

               s = null;
            }
         } else if (s.equals("Box/Unbox")) {
            if (flag) {
               c_g.m573();
               s = null;
            }
         } else if (s.equals("Show Unneg")) {
            s = s + " ";
         } else if (s.equals("Show Ant")) {
            s = s + " ";
         } else if (s.equals("Show NegCons")) {
            s = s + " ";
         } else if (s.equals("Show NegDisj")) {
            s = s + " ";
         } else if (s.startsWith("Show ")) {
            String s1 = c_g.m7(false);
            if (flag && s1 != null && s1.equals("") && this.f235.getText().equals("")) {
               c_g.m8(s);
               if (c_g.f317.f915.commandMode) {
                  c_g.m563(c_g.f323 != null);
               }

               C_G c_g2 = c_g.m18();
               if (c_g2 != null) {
                  c_g.f317.f915.requestAid = true;
                  c_g2.m22(flag);
               }

               s = null;
            }
         }

         return super.m375(s);
      }
   }

   @Override
   public int m376() {
      return this.f235.f1255;
   }

   @Override
   public int m377() {
      return this.f235.f1256;
   }

   @Override
   public int m378() {
      return this.f235.f1257;
   }

   @Override
   public void m379(int i, int j) {
      this.f235.f1255 = i;
      this.f235.f1256 = j;
   }

   @Override
   public void m380(int i) {
      this.f235.f1257 = i;
   }

   @Override
   public void m381(String s, int i) {
      this.f235.m1796(s, i);
   }
}
