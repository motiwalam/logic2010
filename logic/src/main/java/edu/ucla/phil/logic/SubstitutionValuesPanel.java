package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JPanel;

class SubstitutionValuesPanel extends LinePanel implements SubstitutionListener, LogicConstants {
   static String[] SYMBOLS = LogicProgram.symbols;
   int placeholderCount;
   Vector valueLabels;

   SubstitutionValuesPanel(int i) {
      this(i, 1);
   }

   SubstitutionValuesPanel(int i, int j) {
      super(i);
      this.placeholderCount = j;
      JPanel jpanel = new JPanel();
      this.setLayout(new FlowLayout(0, 0, 0));
      jpanel.setLayout(new FlexGridLayout(2, j, 0, 0, true, false));
      this.add(jpanel);
      this.valueLabels = new Vector();

      for (int k = 0; k < j; k++) {
         LogicLabel logiclabel = new LogicLabel("");
         this.valueLabels.addElement(logiclabel);
         jpanel.add(new LogicLabel(SchematicLetter.placeholder(k) + ": "), new Point(0, k));
         jpanel.add(logiclabel, new Point(1, k));
      }
   }

   @Override
   public void setPlaceholderValue(int i, String s) {
      LogicLabel logiclabel = (LogicLabel)this.valueLabels.elementAt(i);
      logiclabel.setText(s == null ? "" : LogicProgram.translateSymbols(s, maggie, SYMBOLS));
   }

   @Override
   public ErrorRef validatePlaceholderValue(int i, Expression expression) {
      return null;
   }
}
