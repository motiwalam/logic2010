package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;

class MessageDetailsButton extends JButton implements LogicConstants, ActionListener {
   String explanation;
   DerivationLine line;
   Rectangle dialogBounds;
   DerivationQueryHandler handler;

   MessageDetailsButton(DerivationLine derivationline) {
      super("?");
      this.line = derivationline;
      this.reset();
      Color[] acolor = derivationline.box.module.colors;
      this.setMargin(new Insets(0, 0, 0, 0));
      super.setForeground(acolor[6]);
      super.setBackground(acolor[5]);
      this.addActionListener(this);
      this.setFont(LogicProgram.getFont(derivationline.box.module.fontSize - 4));
   }

   @Override
   public void setBackground(Color color) {
   }

   @Override
   public void setForeground(Color color) {
   }

   void prepareExplanation(Hashtable hashtable, DerivationLineChecker derivationlinechecker) {
      if (derivationlinechecker == null) {
         this.explanation = DerivationMessage.format(this.line.message.text, hashtable, this.line);
      } else {
         this.explanation = DerivationMessage.format(this.line.message.text, hashtable, derivationlinechecker);
      }

      short short1 = 300;
      short short2 = 200;
      Point point = MessageDialog.centeredLocation(new Dimension(short1, short2));
      Rectangle rectangle = LogicProgram.boundsRelativeTo(this.line.messagePane, null);
      Point point1 = new Point(rectangle.x, rectangle.y);
      int i = point1.x - short1 - 10;
      int j = point.y;
      new Rectangle(i, j, short1, short2);
      this.handler = new DerivationQueryHandler(this.line, this.line.message.buttons);
   }

   void setHandlerParam(String s, Object object) {
      if (this.handler != null) {
         this.handler.setProperty(s, object);
      }
   }

   void reset() {
      this.explanation = "";
      this.dialogBounds = null;
      this.handler = null;
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      String s = this.line.message.id;
      this.line.focusEditor(true);
      MessageDialog.showScrollingMessage(s, this.explanation, this.dialogBounds, this.handler);
   }

   @Override
   public Dimension getPreferredSize() {
      Dimension dimension = super.getPreferredSize();
      dimension.width = 100;
      return dimension;
   }
}
