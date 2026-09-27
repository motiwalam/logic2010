package edu.ucla.phil.logic.pkgA;

import edu.ucla.phil.logic.FormulaParseException;

public class Syntax2ParseException extends FormulaParseException {
   protected boolean specialConstructor;
   public Syntax2Token currentToken;
   public int[][] expectedTokenSequences;
   public String[] tokenImage;
   protected String eol = System.getProperty("line.separator", "\n");

   public Syntax2ParseException(Syntax2Token syntax2token, int[][] aint, String[] astring) {
      super("");
      this.specialConstructor = true;
      this.currentToken = syntax2token;
      this.expectedTokenSequences = aint;
      this.tokenImage = astring;
   }

   public Syntax2ParseException() {
      this.specialConstructor = false;
   }

   public Syntax2ParseException(String s) {
      super(s);
      this.specialConstructor = false;
   }

   @Override
   public String getMessage() {
      if (!this.specialConstructor) {
         return super.getMessage();
      } else {
         String s = "";
         int i = 0;

         for (int j = 0; j < this.expectedTokenSequences.length; j++) {
            if (i < this.expectedTokenSequences[j].length) {
               i = this.expectedTokenSequences[j].length;
            }

            for (int k = 0; k < this.expectedTokenSequences[j].length; k++) {
               s = s + this.tokenImage[this.expectedTokenSequences[j][k]] + " ";
            }

            if (this.expectedTokenSequences[j][this.expectedTokenSequences[j].length - 1] != 0) {
               s = s + "...";
            }

            s = s + this.eol + "    ";
         }

         String s1 = "Encountered \"";
         Syntax2Token syntax2token = this.currentToken.next;

         for (int l = 0; l < i; l++) {
            if (l != 0) {
               s1 = s1 + " ";
            }

            if (syntax2token.kind == 0) {
               s1 = s1 + this.tokenImage[0];
               break;
            }

            s1 = s1 + this.add_escapes(syntax2token.image);
            syntax2token = syntax2token.next;
         }

         String s2 = s1 + "\" at line " + this.currentToken.next.beginLine + ", column " + this.currentToken.next.beginColumn + "." + this.eol;
         if (this.expectedTokenSequences.length == 1) {
            s1 = s2 + "Was expecting:" + this.eol + "    ";
         } else {
            s1 = s2 + "Was expecting one of:" + this.eol + "    ";
         }

         return s1 + s;
      }
   }

   protected String add_escapes(String s) {
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
