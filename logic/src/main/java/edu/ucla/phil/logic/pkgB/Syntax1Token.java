package edu.ucla.phil.logic.pkgB;

public class Syntax1Token {
   public int kind;
   public int beginLine;
   public int beginColumn;
   public int endLine;
   public int endColumn;
   public String image;
   public Syntax1Token next;
   public Syntax1Token specialToken;

   @Override
   public final String toString() {
      return this.image;
   }

   public static final Syntax1Token newToken(int i) {
      switch (i) {
         default:
            return new Syntax1Token();
      }
   }
}
