package edu.ucla.phil.logic.pkgA;

import edu.ucla.phil.logic.FormulaLexerError;

public class Syntax2TokenMgrError extends FormulaLexerError {
   static final int LEXICAL_ERROR = 0;
   static final int STATIC_LEXER_ERROR = 1;
   static final int INVALID_LEXICAL_STATE = 2;
   static final int LOOP_DETECTED = 3;
   int errorCode;

   protected static final String addEscapes(String s) {
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

   private static final String LexicalError(boolean flag, int k, int i, int j, String s, char c0) {
      return "Lexical error at line "
         + i
         + ", column "
         + j
         + ".  Encountered: "
         + (flag ? "<EOF> " : "\"" + addEscapes(String.valueOf(c0)) + "\"" + " (" + c0 + "), ")
         + "after : \""
         + addEscapes(s)
         + "\"";
   }

   @Override
   public String getMessage() {
      return super.getMessage();
   }

   public Syntax2TokenMgrError() {
   }

   public Syntax2TokenMgrError(String s, int i) {
      super(s);
      this.errorCode = i;
   }

   public Syntax2TokenMgrError(boolean flag, int i, int j, int k, String s, char c0, int l) {
      this(LexicalError(flag, i, j, k, s, c0), l);
   }
}
