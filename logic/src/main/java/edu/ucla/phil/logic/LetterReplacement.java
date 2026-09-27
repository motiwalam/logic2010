package edu.ucla.phil.logic;

class LetterReplacement {
   Expression pattern;
   Expression replacement;
   ErrorRef error;

   LetterReplacement(Expression expression, Expression expression1) {
      if ((this.error = SchemeInstantiation.validateReplacement(expression, expression1)) == null) {
         this.pattern = expression;
         this.replacement = expression1.copy();
         this.replacement.linkArgumentPlaceholders(expression);
      }
   }

   LetterReplacement(ErrorRef errorref) {
      this.error = errorref;
   }

   boolean sameReplacementAs(LetterReplacement letterreplacement1) {
      if (this.error != null || letterreplacement1.error != null) {
         return false;
      } else {
         return this.pattern.symbol.equals(letterreplacement1.pattern.symbol) && this.pattern.childCount == letterreplacement1.pattern.childCount
            ? this.replacement.isAlphaEquivalent(letterreplacement1.replacement, null)
            : false;
      }
   }

   @Override
   public String toString() {
      return this.pattern + ":" + this.replacement;
   }
}
