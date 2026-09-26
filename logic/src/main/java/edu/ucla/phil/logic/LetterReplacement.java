package edu.ucla.phil.logic;

class LetterReplacement {
   Expression f367;
   Expression f368;
   ErrorRef f369;

   LetterReplacement(Expression expression, Expression expression1) {
      if ((this.f369 = SchemeInstantiation.m1884(expression, expression1)) == null) {
         this.f367 = expression;
         this.f368 = expression1.copy();
         this.f368.m1264(expression);
      }
   }

   LetterReplacement(ErrorRef errorref) {
      this.f369 = errorref;
   }

   boolean m656(LetterReplacement letterreplacement1) {
      if (this.f369 != null || letterreplacement1.f369 != null) {
         return false;
      } else {
         return this.f367.symbol.equals(letterreplacement1.f367.symbol) && this.f367.childCount == letterreplacement1.f367.childCount
            ? this.f368.m1236(letterreplacement1.f368, null)
            : false;
      }
   }

   @Override
   public String toString() {
      return this.f367 + ":" + this.f368;
   }
}
