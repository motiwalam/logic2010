package edu.ucla.phil.logic.pkgB;

import edu.ucla.phil.logic.C_k_B;

public class C_A extends C_k_B {
   protected boolean f149;
   public C_G f150;
   public int[][] f151;
   public String[] f152;
   protected String f153 = System.getProperty("line.separator", "\n");

   public C_A(C_G c_g, int[][] aint, String[] astring) {
      super("");
      this.f149 = true;
      this.f150 = c_g;
      this.f151 = aint;
      this.f152 = astring;
   }

   public C_A() {
      this.f149 = false;
   }

   public C_A(String s) {
      super(s);
      this.f149 = false;
   }

   @Override
   public String getMessage() {
      if (!this.f149) {
         return super.getMessage();
      } else {
         String s = "";
         int i = 0;

         for (int j = 0; j < this.f151.length; j++) {
            if (i < this.f151[j].length) {
               i = this.f151[j].length;
            }

            for (int k = 0; k < this.f151[j].length; k++) {
               s = s + this.f152[this.f151[j][k]] + " ";
            }

            if (this.f151[j][this.f151[j].length - 1] != 0) {
               s = s + "...";
            }

            s = s + this.f153 + "    ";
         }

         String s1 = "Encountered \"";
         C_G c_g = this.f150.f233;

         for (int l = 0; l < i; l++) {
            if (l != 0) {
               s1 = s1 + " ";
            }

            if (c_g.f227 == 0) {
               s1 = s1 + this.f152[0];
               break;
            }

            s1 = s1 + this.m236(c_g.f232);
            c_g = c_g.f233;
         }

         s1 = s1 + "\" at line " + this.f150.f233.f228 + ", column " + this.f150.f233.f229 + "." + this.f153;
         if (this.f151.length == 1) {
            s1 = s1 + "Was expecting:" + this.f153 + "    ";
         } else {
            s1 = s1 + "Was expecting one of:" + this.f153 + "    ";
         }

         return s1 + s;
      }
   }

   protected String m236(String s) {
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
