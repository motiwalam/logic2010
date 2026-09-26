package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.event.WindowEvent;

class C_BA extends KeypadDialog {
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
      DerivationLine derivationline = this.f235.f1258;
      boolean flag = derivationline.f324 == this.f235;
      if (s == null) {
         return null;
      } else {
         s = LogicProgram.m995(s, new String[]{"\n"}, new String[]{" "});
         if (s.equals("backspace")) {
            s = "\b";
         } else if (s.equals("space")) {
            s = " ";
         } else if (s.equals("tab")) {
            if (derivationline.f323 != null && derivationline.f324 != null && derivationline.m564() == null) {
               derivationline.f317.f915.requestAid = true;
               derivationline.m22(!flag);
            }

            s = null;
         } else if (s.equals("enter")) {
            if (!flag) {
               derivationline.m564();
            }

            if (derivationline.f317.f915.commandMode) {
               if (derivationline.f317.f915.focus != derivationline.f324) {
                  derivationline.m586();
               }

               derivationline.m563(derivationline.f323 != null);
            }

            DerivationLine derivationline1 = derivationline.m18();
            if (derivationline1 != null) {
               derivationline.f317.f915.requestAid = true;
               derivationline1.m22(flag);
            }

            s = null;
         } else if (s.equals("up")) {
            DerivationNode derivationnode = derivationline.m24(true);
            if (derivationnode != null) {
               derivationline.f317.f915.requestAid = true;
               derivationnode.m22(flag);
            }

            s = null;
         } else if (s.equals("down")) {
            DerivationNode derivationnode1 = derivationline.m23(true);
            if (derivationnode1 != null) {
               derivationline.f317.f915.requestAid = true;
               derivationnode1.m22(flag);
            }

            s = null;
         } else if (s.equals("in")) {
            this.f235.f1258.m566();
            s = null;
         } else if (s.equals("out")) {
            this.f235.f1258.m567();
            s = null;
         } else if (s.equals("delete line")) {
            if (derivationline.f317.f915.problem.f917 != derivationline) {
               DerivationNode derivationnode2 = derivationline.m23(false);
               if (derivationnode2 == null) {
                  derivationnode2 = derivationline.m24(true);
               }

               derivationline.m19(false);
               derivationline.f317.f915.requestAid = true;
               derivationnode2.m22(flag);
            }

            s = null;
         } else if (s.equals("Show/Unshow")) {
            if (!flag) {
               DerivationNode derivationnode3 = derivationline.m570();
               if (derivationnode3 != null) {
                  this.f235 = derivationnode3.m14();
               }

               s = null;
            }
         } else if (s.equals("Box/Unbox")) {
            if (flag) {
               derivationline.m573();
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
            String s1 = derivationline.m7(false);
            if (flag && s1 != null && s1.equals("") && this.f235.getText().equals("")) {
               derivationline.m8(s);
               if (derivationline.f317.f915.commandMode) {
                  derivationline.m563(derivationline.f323 != null);
               }

               DerivationLine derivationline2 = derivationline.m18();
               if (derivationline2 != null) {
                  derivationline.f317.f915.requestAid = true;
                  derivationline2.m22(flag);
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
