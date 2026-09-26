package edu.ucla.phil.logic.pkgB;

import edu.ucla.phil.logic.FormulaLexerError;

public class Syntax1TokenMgrError extends FormulaLexerError {
   static final int f169 = 0;
   static final int f170 = 1;
   static final int f171 = 2;
   static final int f172 = 3;
   int f173;

   protected static final String m257(String s) {
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

   private static final String m258(boolean flag, int k, int i, int j, String s, char c0) {
      return "Lexical error at line "
         + i
         + ", column "
         + j
         + ".  Encountered: "
         + (flag ? "<EOF> " : "\"" + m257(String.valueOf(c0)) + "\"" + " (" + c0 + "), ")
         + "after : \""
         + m257(s)
         + "\"";
   }

   @Override
   public String getMessage() {
      return super.getMessage();
   }

   public Syntax1TokenMgrError() {
   }

   public Syntax1TokenMgrError(String s, int i) {
      super(s);
      this.f173 = i;
   }

   public Syntax1TokenMgrError(boolean flag, int i, int j, int k, String s, char c0, int l) {
      this(m258(flag, i, j, k, s, c0), l);
   }
}
