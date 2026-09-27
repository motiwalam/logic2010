package edu.ucla.phil.logic;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Vector;

class GeneralizationTermSelector extends TermOccurrenceSelector {
   GeneralizationTermSelector(Expression expression, int i, Frame frame) {
      super(expression, i, frame, 1);
   }

   @Override
   void reportNotWellFormed(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot014", hashtable);
   }

   @Override
   void reportNotATerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot015", hashtable);
   }

   @Override
   void reportNotStandaloneTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot028", hashtable);
   }

   @Override
   void reportBoundOccurrence(Vector vector, Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot026", hashtable);
   }

   @Override
   void reportDifferentTerm(Hashtable hashtable) {
      DerivationDialogs.showMessage("dernot016", hashtable);
   }
}
