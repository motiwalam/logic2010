package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class CommentPanel extends SizedPanel {
   SizedSeparator leftSpacer;
   SizedSeparator rightSpacer;
   SizedPanel textHolder;
   StyledTextPane textPane;
   FixedColumnLayout columnLayout;

   CommentPanel(boolean flag) {
      if (flag) {
         this.columnLayout = null;
         this.setLayout(new BorderLayout());
         this.add(this.leftSpacer = new SizedSeparator(true), "West");
         this.add(this.textHolder = new SizedPanel(), "Center");
         this.add(this.rightSpacer = new SizedSeparator(true), "East");
      } else {
         this.setLayout(this.columnLayout = new FixedColumnLayout(3));
         this.add(this.leftSpacer = new SizedSeparator(true));
         this.add(this.textHolder = new SizedPanel());
         this.add(this.rightSpacer = new SizedSeparator(true));
      }

      this.textPane = new StyledTextPane();
      StyledDocument styleddocument = this.textPane.getStyledDocument();
      SimpleAttributeSet simpleattributeset = new SimpleAttributeSet();
      StyleConstants.setAlignment(simpleattributeset, 1);
      StyleConstants.setForeground(simpleattributeset, LogicConstants.bruinBlack);
      StyleConstants.setBackground(simpleattributeset, LogicConstants.bruinWhite);
      StyleConstants.setFontFamily(simpleattributeset, this.textPane.getFont().getFamily());
      StyleConstants.setFontSize(simpleattributeset, this.textPane.getFont().getSize());
      StyleConstants.setBold(simpleattributeset, this.textPane.getFont().isBold());
      this.textPane.setParagraphAttributes(simpleattributeset, true);
      this.textPane.setWrapLines(true);
      this.textPane.setWrapWords(true);
      this.textHolder.add(this.textPane);
      this.textHolder.setBackground(LogicConstants.bruinWhite);
      this.textHolder.setForeground(LogicConstants.bruinBlack);
      this.textPane.setVisible(false);
      this.setEnabled(false);
   }

   void setComment(String s) {
      if (s == null) {
         this.textPane.setText("");
         this.textPane.setVisible(false);
      } else {
         this.textPane.setText(LogicProgram.expandEscapes(s.trim()));
         this.textPane.setVisible(true);
      }
   }

   String getComment() {
      return this.textPane.isVisible() ? this.textPane.getText() : null;
   }

   @Override
   public void setFont(Font font) {
      if (this.textPane == null) {
         super.setFont(font);
      } else {
         this.textPane.setFont(font);
      }
   }

   void setTextAlignmentY(float f) {
      this.textPane.setAlignmentY(f);
   }

   void setCommentColors(Color[] acolor) {
      this.textHolder.setForeground(acolor[5]);
      this.textHolder.setBackground(acolor[6]);
   }

   void setColumnWidths(int i, int j) {
      this.setColumnWidths(i, j, 0);
   }

   void setColumnWidths(int i, int j, int k) {
      if (this.columnLayout == null) {
         this.leftSpacer.setLength(i);
         this.textHolder.setLimitWidth(j);
         this.rightSpacer.setLength(k);
      } else {
         this.columnLayout.setColumnWidths(new int[]{i, j, k});
      }
   }
}
