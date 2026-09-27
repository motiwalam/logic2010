package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class Leibniz12TermSelector extends TermOccurrenceSelector {
   Hashtable extraParams;

   Leibniz12TermSelector(Expression expression, int i, Frame frame, Hashtable hashtable) {
      super(expression, i, frame, 1);
      this.extraParams = hashtable;
   }

   @Override
   void reportNotWellFormed(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot030", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportNotATerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot031", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportNotStandaloneTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot035", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportBoundOccurrence(Vector vector, Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot033", Message.mergeParams(this.extraParams, hashtable));
   }

   @Override
   void reportDifferentTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot032", Message.mergeParams(this.extraParams, hashtable));
   }
}
