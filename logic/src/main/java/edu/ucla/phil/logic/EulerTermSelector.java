package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class EulerTermSelector extends TermOccurrenceSelector {
   EulerTermSelector(Expression expression, int i, Frame frame, Hashtable hashtable) {
      super(expression, i, frame, 1, hashtable);
   }

   @Override
   void reportNotWellFormed(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot048", hashtable);
   }

   @Override
   void reportNotATerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot049", hashtable);
   }

   @Override
   void reportNotStandaloneTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot050", hashtable);
   }

   @Override
   void reportBoundOccurrence(Vector vector, Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot051", hashtable);
   }

   @Override
   void reportDifferentTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot052", hashtable);
   }
}
