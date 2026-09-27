package edu.ucla.phil.logic.syntax2;

public class Syntax2Token {
   public int kind;
   public int beginLine;
   public int beginColumn;
   public int endLine;
   public int endColumn;
   public String image;
   public Syntax2Token next;
   public Syntax2Token specialToken;

   @Override
   public final String toString() {
      return this.image;
   }

   public static final Syntax2Token newToken(int i) {
      switch (i) {
         default:
            return new Syntax2Token();
      }
   }
}
