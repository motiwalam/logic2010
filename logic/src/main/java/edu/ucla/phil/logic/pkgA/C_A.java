package edu.ucla.phil.logic.pkgA;

import edu.ucla.phil.logic.C_k_B;

public class C_A extends C_k_B {
   protected boolean f30;
   public C_G f31;
   public int[][] f32;
   public String[] f33;
   protected String f34 = System.getProperty("line.separator", "\n");

   public C_A(C_G c_g, int[][] aint, String[] astring) {
      super("");
      this.f30 = true;
      this.f31 = c_g;
      this.f32 = aint;
      this.f33 = astring;
   }

   public C_A() {
      this.f30 = false;
   }

   public C_A(String s) {
      super(s);
      this.f30 = false;
   }

   @Override
   public String getMessage() {
      if (!this.f30) {
         return super.getMessage();
      } else {
         String s = "";
         int i = 0;

         for (int j = 0; j < this.f32.length; j++) {
            if (i < this.f32[j].length) {
               i = this.f32[j].length;
            }

            for (int k = 0; k < this.f32[j].length; k++) {
               s = s + this.f33[this.f32[j][k]] + " ";
            }

            if (this.f32[j][this.f32[j].length - 1] != 0) {
               s = s + "...";
            }

            s = s + this.f34 + "    ";
         }

         String s1 = "Encountered \"";
         C_G c_g = this.f31.f114;

         for (int l = 0; l < i; l++) {
            if (l != 0) {
               s1 = s1 + " ";
            }

            if (c_g.f108 == 0) {
               s1 = s1 + this.f33[0];
               break;
            }

            s1 = s1 + this.m72(c_g.f113);
            c_g = c_g.f114;
         }

         s1 = s1 + "\" at line " + this.f31.f114.f109 + ", column " + this.f31.f114.f110 + "." + this.f34;
         if (this.f32.length == 1) {
            s1 = s1 + "Was expecting:" + this.f34 + "    ";
         } else {
            s1 = s1 + "Was expecting one of:" + this.f34 + "    ";
         }

         return s1 + s;
      }
   }

   protected String m72(String s) {
      StringBuffer stringbuffer = new StringBuffer();

      for (int i = 0; i < s.length(); i++) {
         switch (s.charAt(i)) {
            case '\u0000':
               break;
            case '\b':
               stringbuffer.append("\\b");
               break;
            case '\t':
               stringbuffer.append("\\t");
               break;
            case '\n':
               stringbuffer.append("\\n");
               break;
            case '\f':
               stringbuffer.append("\\f");
               break;
            case '\r':
               stringbuffer.append("\\r");
               break;
            case '"':
               stringbuffer.append("\\\"");
               break;
            case '\'':
               stringbuffer.append("\\'");
               break;
            case '\\':
               stringbuffer.append("\\\\");
               break;
            default:
               char c0;
               if ((c0 = s.charAt(i)) >= ' ' && c0 <= '~') {
                  stringbuffer.append(c0);
               } else {
                  String s1 = "0000" + Integer.toString(c0, 16);
                  stringbuffer.append("\\u" + s1.substring(s1.length() - 4, s1.length()));
               }
         }
      }

      return stringbuffer.toString();
   }
}
