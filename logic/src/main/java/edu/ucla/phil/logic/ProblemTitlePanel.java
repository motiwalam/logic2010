package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class ProblemTitlePanel extends CellPanel {
   EditableTextPane titleLabel;
   EditableTextPane statementPane;
   EditableTextPane statusPane;
   CommentPanel noteArea;

   ProblemTitlePanel(Color[] acolor) {
      super(false);
      this.setLayout(new GridBagLayout());
      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.anchor = 23;
      this.add(this.titleLabel = new EditableTextPane("Problem: ", false), gridbagconstraints);
      this.titleLabel.setMinimumPreferred(true);
      this.titleLabel.setFocusable(false);
      gridbagconstraints.gridx = 1;
      gridbagconstraints.anchor = 23;
      gridbagconstraints.weightx = 1.0;
      gridbagconstraints.fill = 1;
      this.add(this.statementPane = new EditableTextPane(""), gridbagconstraints);
      this.statementPane.setEditable(false);
      this.statementPane.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.statementPane.setWrapLines(true);
      this.statementPane.setWrapWords(true);
      gridbagconstraints.gridx = 2;
      gridbagconstraints.fill = 0;
      gridbagconstraints.anchor = 24;
      gridbagconstraints.weightx = 0.0;
      this.add(this.statusPane = new EditableTextPane("", false), gridbagconstraints);
      this.statusPane.setMinimumPreferred(true);
      this.statusPane.setFocusable(false);
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridy = 1;
      gridbagconstraints.gridwidth = 3;
      gridbagconstraints.fill = 1;
      gridbagconstraints.weightx = 1.0;
      this.add(this.noteArea = new CommentPanel(true), gridbagconstraints);
      int i = this.statementPane.getHeight();
      this.titleLabel.setSize(this.titleLabel.getWidth(), i);
      this.noteArea.setCommentColors(acolor);
      this.clearFields();
   }

   void setTitleLabel(String s) {
      if (s == null) {
         s = "Problem";
      }

      this.titleLabel.setText(s.trim() + ": ");
      this.validate();
   }

   String getTitleLabel() {
      return this.titleLabel.getName();
   }

   void setStatement(String s) {
      if (s == null) {
         s = "";
      }

      this.statementPane.setText(s.trim());
      this.validate();
   }

   String getStatement() {
      return this.statementPane.getText();
   }

   void setStatus(String s) {
      if (s == null) {
         s = "";
      }

      this.statusPane.setText(s.trim());
      this.validate();
   }

   String getStatus() {
      return this.statusPane.getText();
   }

   void setNote(String s) {
      this.noteArea.setComment(s);
      this.validate();
   }

   String getNote() {
      return this.noteArea.getComment();
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      if (this.titleLabel != null) {
         this.titleLabel.setFont(font);
      }

      if (this.statementPane != null) {
         this.statementPane.setFont(font);
      }

      if (this.statusPane != null) {
         this.statusPane.setFont(font);
      }

      if (this.noteArea != null) {
         this.noteArea.setFont(font);
      }

      this.validate();
   }

   void clearFields() {
      this.setTitleLabel(null);
      this.setStatement(null);
      this.setStatus(null);
      this.setNote(null);
   }
}
