package edu.ucla.phil.logic;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JCheckBox;

class InterpretationEditor extends SizedPanel {
   SymbolInterpretation symbol;
   int universeSize;
   int columns;
   int rows;
   FlexGridLayout gridLayout;

   InterpretationEditor(SymbolInterpretation symbolinterpretation, int i) {
      this.symbol = symbolinterpretation;
      this.universeSize = i;
      this.gridLayout = null;
      this.columns = symbolinterpretation.arity == 0 ? 1 : i;
      this.rows = 1;

      for (int j = 1; j < symbolinterpretation.arity; j++) {
         this.rows *= i;
      }

      if (this.columns != 0) {
         this.setLayout(this.gridLayout = new FlexGridLayout(this.columns, this.rows));
         this.gridLayout.setHgap(10);
         int[] aint = new int[symbolinterpretation.arity];
         if (symbolinterpretation instanceof PredicateInterpretation) {
            PredicateInterpretation predicateinterpretation = (PredicateInterpretation)symbolinterpretation;
            if (symbolinterpretation.arity == 0) {
               ScaledCheckBox scaledcheckbox;
               this.add(scaledcheckbox = new ScaledCheckBox(symbolinterpretation.name), new Point(0, 0));
               scaledcheckbox.setSelected(predicateinterpretation.extension != null && !predicateinterpretation.extension.isEmpty());
            } else {
               for (int i1 = 0; i1 < this.rows; i1++) {
                  String s = "";
                  int l = i1;

                  for (int k = 1; k < symbolinterpretation.arity; k++) {
                     s = s + "," + (aint[k - 1] = l % i);
                     l /= i;
                  }

                  for (int k1 = 0; k1 < this.columns; k1++) {
                     ScaledCheckBox scaledcheckbox1;
                     this.add(scaledcheckbox1 = new ScaledCheckBox(symbolinterpretation.name + "(" + s + k1 + ")"), new Point(k1, i1));
                     aint[symbolinterpretation.arity - 1] = k1;
                     Boolean obool = (Boolean)symbolinterpretation.getValue(aint);
                     scaledcheckbox1.setSelected(obool != null && obool);
                  }
               }
            }
         } else if (symbolinterpretation instanceof OperationInterpretation) {
            OperationInterpretation operationinterpretation = (OperationInterpretation)symbolinterpretation;
            if (symbolinterpretation.arity == 0) {
               LabeledNumberChoice labelednumberchoice;
               this.add(labelednumberchoice = new LabeledNumberChoice(symbolinterpretation.name, i), new Point(0, 0));
               labelednumberchoice.setSelectedNumber(operationinterpretation.defaultValue == null ? 0 : operationinterpretation.defaultValue);
            } else {
               for (int j1 = 0; j1 < this.rows; j1++) {
                  String s1 = "";
                  int j2 = j1;

                  for (int l1 = 1; l1 < symbolinterpretation.arity; l1++) {
                     s1 = s1 + "," + (aint[l1 - 1] = j2 % i);
                     j2 /= i;
                  }

                  for (int i2 = 0; i2 < this.columns; i2++) {
                     LabeledNumberChoice labelednumberchoice1;
                     this.add(labelednumberchoice1 = new LabeledNumberChoice(symbolinterpretation.name + "(" + s1 + i2 + ")", i), new Point(i2, j1));
                     aint[symbolinterpretation.arity - 1] = i2;
                     Integer integer = (Integer)symbolinterpretation.getValue(aint);
                     labelednumberchoice1.setSelectedNumber(integer == null ? 0 : integer);
                  }
               }
            }
         }
      }
   }

   void applyToSymbol() {
      int[] aint = new int[this.symbol.arity];
      if (this.symbol instanceof PredicateInterpretation) {
         PredicateInterpretation predicateinterpretation = (PredicateInterpretation)this.symbol;
         Vector vector = new Vector();
         if (this.symbol.arity == 0) {
            JCheckBox jcheckbox1 = (JCheckBox)this.gridLayout.getCellComponent(new Point(0, 0));
            if (jcheckbox1.isSelected()) {
               vector.addElement(new ExpressionPath());
            }
         } else {
            for (int i = 0; i < this.rows; i++) {
               int k = i;

               for (int j = 1; j < this.symbol.arity; j++) {
                  aint[j - 1] = k % this.universeSize;
                  k /= this.universeSize;
               }

               for (int i1 = 0; i1 < this.columns; i1++) {
                  JCheckBox jcheckbox = (JCheckBox)this.gridLayout.getCellComponent(new Point(i1, i));
                  aint[this.symbol.arity - 1] = i1;
                  if (jcheckbox.isSelected()) {
                     ExpressionPath expressionpath = new ExpressionPath();
                     expressionpath.append(aint);
                     vector.addElement(expressionpath);
                  }
               }
            }
         }

         predicateinterpretation.extension = vector;
      } else if (this.symbol instanceof OperationInterpretation) {
         OperationInterpretation operationinterpretation = (OperationInterpretation)this.symbol;
         if (this.symbol.arity == 0) {
            LabeledNumberChoice labelednumberchoice = (LabeledNumberChoice)this.gridLayout.getCellComponent(new Point(0, 0));
            operationinterpretation.valueTable = null;
            operationinterpretation.defaultValue = new Integer(labelednumberchoice.getSelectedNumber());
         } else {
            Hashtable hashtable = new Hashtable();

            for (int l = 0; l < this.rows; l++) {
               int l1 = l;

               for (int j1 = 1; j1 < this.symbol.arity; j1++) {
                  aint[j1 - 1] = l1 % this.universeSize;
                  l1 /= this.universeSize;
               }

               for (int k1 = 0; k1 < this.columns; k1++) {
                  LabeledNumberChoice labelednumberchoice1 = (LabeledNumberChoice)this.gridLayout.getCellComponent(new Point(k1, l));
                  aint[this.symbol.arity - 1] = k1;
                  ExpressionPath expressionpath1 = new ExpressionPath();
                  expressionpath1.append(aint);
                  hashtable.put(expressionpath1, new Integer(labelednumberchoice1.getSelectedNumber()));
               }
            }

            operationinterpretation.valueTable = hashtable;
            operationinterpretation.defaultValue = null;
         }
      }
   }
}
