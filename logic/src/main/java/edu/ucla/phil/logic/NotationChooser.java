package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

class NotationChooser extends CellPanel implements ModuleComponentMarker {
   ParsingProblemPanel problemPanel;
   LogicLabel resultLabel;
   NotationRadioButton[] buttons;
   RadioGroupPanel buttonGroup;
   static final String NOTATION_CODES = "OIN";
   static final String[] NOTATION_LABELS = new String[]{"Official Notation", "Informal Notation", "Not Well Formed"};

   NotationChooser(ParsingProblemPanel parsingproblempanel) {
      this.problemPanel = parsingproblempanel;
      this.setLayout(new VerticalStackLayout());
      this.setBorder(new EmptyBorder(0, 5, 0, 5));
      this.add(this.resultLabel = new LogicLabel(" "));
      this.buttons = new NotationRadioButton[NOTATION_LABELS.length];
      this.buttonGroup = new RadioGroupPanel();

      for (int i = 0; i < NOTATION_LABELS.length; i++) {
         this.buttonGroup.add(this.buttons[i] = new NotationRadioButton(NOTATION_LABELS[i], this), null, i);
      }

      this.add(this.buttonGroup);
   }

   void setResultText(String s) {
      this.resultLabel.setText(s);
   }

   int getSelectedIndex() {
      int i = this.buttons.length;

      for (int j = 0; j < i; j++) {
         if (this.buttons[j].isSelected()) {
            return j;
         }
      }

      return -1;
   }

   static String codeForIndex(int i) {
      return i >= 0 && i < "OIN".length() ? "OIN".substring(i, i + 1) : null;
   }

   String getSelectedCode() {
      return codeForIndex(this.getSelectedIndex());
   }

   static int indexForCode(String s) {
      return s != null && s.length() == 1 ? "OIN".indexOf(s) : -1;
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      if (awtevent.getID() == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
