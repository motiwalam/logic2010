package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class Leibniz34TermSelector extends TermOccurrenceSelector {
   Hashtable extraParams;
   SchematicLetter ruleLetter;

   Leibniz34TermSelector(Expression expression, int i, Frame frame, Hashtable hashtable, SchematicLetter schematicletter) {
      super(expression, i, frame, 1);
      this.extraParams = hashtable;
      this.ruleLetter = schematicletter;
   }

   @Override
   void reportNotWellFormed(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot036", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportNotATerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot037", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportNotStandaloneTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot042", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportBoundOccurrence(Vector vector, Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot039", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportDifferentTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot038", Message.mergeParams(this.extraParams, hashtable));
   }
}
