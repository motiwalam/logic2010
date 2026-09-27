package edu.ucla.phil.logic;

import java.awt.FlowLayout;
import java.util.Hashtable;
import java.util.Vector;

class RecognitionProblemPanel extends SizedPanel implements LogicConstants {
   static String[] displaySymbols = LogicProgram.symbols;
   LPRecognition module;
   ArgumentParser argument;
   String problemName;
   String statement;
   Vector correctRules;
   Vector nearMissRules;
   String successComment;
   String answer;
   SizedPanel argumentPanel;
   SizedPanel premisesPanel;
   SizedPanel conclusionPanel;
   SizedPanel answerPanel;
   SizedPanel verdictPanel;
   SizedPanel commentPanel;
   SizedPanel resultPanel;
   LogicLabel conclusionLabel;
   LogicLabel verdictLabel;
   MessageTextArea commentText;
   RecognitionEntryPane ruleField;

   RecognitionProblemPanel(LPRecognition lprecognition) {
      this.module = lprecognition;
      this.argumentPanel = new SizedPanel();
      this.argumentPanel.add(this.premisesPanel = new SizedPanel(), "North");
      this.premisesPanel.setLayout(new VerticalStackLayout());
      this.argumentPanel.add(new SizedSeparator(5, 1, false, lprecognition.colors[0]), "Center");
      this.argumentPanel.add(this.conclusionPanel = new SizedPanel(), "South");
      this.conclusionPanel.setLayout(new VerticalStackLayout());
      this.conclusionPanel.add(this.conclusionLabel = new LogicLabel());
      this.answerPanel = new SizedPanel();
      this.answerPanel.setLayout(new VerticalStackLayout(4));
      this.answerPanel.add(new LogicLabel(LogicProgram.expandEscapes(this.getPromptText())));
      this.answerPanel.add(this.ruleField = new RecognitionEntryPane(lprecognition));
      this.ruleField.setForeground(lprecognition.colors[1]);
      this.ruleField.setBackground(lprecognition.colors[0]);
      this.ruleField.setWrapLines(false);
      this.ruleField.setWrapWords(false);
      this.ruleField.minWidth = lprecognition.fontSize * 3;
      SizedPanel sizedpanel = new SizedPanel();
      sizedpanel.setLayout(new FlowLayout(0, 0, 0));
      sizedpanel.add(this.argumentPanel);
      sizedpanel.add(new SizedSeparator(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]));
      sizedpanel.add(this.answerPanel);
      SizedPanel sizedpanel1 = new SizedPanel();
      sizedpanel1.add(new SizedSeparator(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      sizedpanel1.add(new SizedSeparator(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      sizedpanel1.add(sizedpanel, "Center");
      this.add(sizedpanel1, "North");
      this.verdictPanel = new SizedPanel();
      this.verdictPanel.add(this.verdictLabel = new LogicLabel(), "West");
      this.verdictPanel.add(new SizedSeparator(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "Center");
      SizedPanel sizedpanel2 = new SizedPanel();
      sizedpanel2.add(this.verdictPanel, "North");
      this.commentPanel = new SizedPanel();
      this.commentPanel.add(this.commentText = new MessageTextArea());
      SizedPanel sizedpanel3 = new SizedPanel();
      sizedpanel3.add(sizedpanel2, "West");
      sizedpanel3.add(this.commentPanel, "Center");
      SizedPanel sizedpanel4 = new SizedPanel();
      sizedpanel4.add(new SizedSeparator(lprecognition.fontSize * 2, 0, false, lprecognition.colors[0]), "North");
      sizedpanel4.add(new SizedSeparator(lprecognition.fontSize * 2, 0, true, lprecognition.colors[0]), "West");
      sizedpanel4.add(sizedpanel3, "Center");
      this.add(this.resultPanel = sizedpanel4, "Center");
      this.clear();
   }

   String getPromptText() {
      Message message = RecognitionMessage.get("recnot005");
      String s = message == null ? null : message.text;
      if (s == null) {
         s = "Please enter the rule that applies, or enter \"None\".";
      }

      return s;
   }

   void clear() {
      this.argument = null;
      this.problemName = null;
      this.statement = null;
      this.correctRules = null;
      this.nearMissRules = null;
      this.successComment = null;
      this.answer = null;
      this.premisesPanel.removeAll();
      this.conclusionLabel.setText("");
      this.ruleField.setText("");
      this.verdictLabel.setText("");
      this.commentText.setText("");
   }

   void loadProblem(TaggedRecord taggedrecord) {
      this.clear();
      this.module.setProblemTitle(taggedrecord.getName());
      this.statement = taggedrecord.valueAt(taggedrecord.indexOfTag('='));
      this.module.titlePanel.setStatement(LogicProgram.translateSymbols(this.statement, maggie, displaySymbols));
      this.argument = ArgumentParser.parse(this.statement);
      this.answer = taggedrecord.valueAt(taggedrecord.indexOfTag('*'));
      String s = LPRecognition.exercises == null ? null : LPRecognition.exercises.getRecord(this.problemName);
      taggedrecord = new TaggedRecord(s);
      this.correctRules = parseRuleList(taggedrecord.valueAt(taggedrecord.indexOfTag('@')));
      this.nearMissRules = parseRuleList(taggedrecord.valueAt(taggedrecord.indexOfTag('~')));
      this.successComment = taggedrecord.valueAt(taggedrecord.indexOfTag('&'));
      if (this.argument != null) {
         int i = this.argument.premises == null ? 0 : this.argument.premises.length;

         for (int j = 0; j < i; j++) {
            LogicLabel logiclabel = new LogicLabel(LogicProgram.translateSymbols(this.argument.premiseTexts[j], maggie, displaySymbols));
            this.premisesPanel.add(logiclabel);
            logiclabel.setForeground(this.module.colors[0]);
         }

         this.conclusionLabel.setText(LogicProgram.translateSymbols(this.argument.conclusionText, maggie, displaySymbols));
         this.conclusionLabel.setForeground(this.module.colors[0]);
      }

      if (this.answer != null) {
         this.ruleField.setText(this.answer);
      }
   }

   static Vector parseRuleList(String s) {
      if (s == null) {
         return null;
      } else {
         Vector vector = new Vector();
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\.");
         delimitedtokenizer.setInput(s);

         String s1;
         while ((s1 = delimitedtokenizer.nextToken()) != null) {
            if ((s1 = s1.trim()).length() != 0) {
               vector.addElement(s1.toUpperCase());
            }
         }

         if (vector.size() == 0) {
            return null;
         } else {
            vector.trimToSize();
            return vector;
         }
      }
   }

   void readAnswer() {
      this.answer = this.ruleField.getText().trim();
      if (this.answer.length() == 0) {
         this.answer = null;
      }
   }

   String getWorkRecord() {
      this.readAnswer();
      String s = "";
      s = s + TaggedRecord.formatField(this.problemName, '$');
      s = s + TaggedRecord.formatField(this.statement, '=');
      return s + TaggedRecord.formatField(this.answer, '*');
   }

   void clearAnswer() {
      this.answer = null;
      this.ruleField.setText("");
      this.verdictLabel.setText("");
      this.commentText.setText("");
   }

   boolean showVerdict(boolean flag, String s) {
      if (flag) {
         this.verdictLabel.setText("Correct");
         this.verdictLabel.setForeground(dialogGreen);
         if (s == null) {
            this.commentText.setText("Congratulations!");
         } else {
            this.commentText.setText(LogicProgram.expandEscapes(s.trim()));
         }
      } else {
         this.verdictLabel.setText("Incorrect");
         this.verdictLabel.setForeground(dialogRed);
         this.commentText.setText("Try Again");
         if (s == null) {
            this.commentText.setText("Try Again.");
         } else {
            this.commentText.setText(LogicProgram.expandEscapes(s.trim()));
         }
      }

      return flag;
   }

   boolean checkAnswer() {
      this.readAnswer();
      if (this.answer == null) {
         return this.showVerdict(false, RecognitionMessage.getText("recnot001"));
      } else if (this.correctRules != null && this.correctRules.indexOf(this.answer.toUpperCase()) != -1) {
         return this.showVerdict(true, this.successComment);
      } else if (this.nearMissRules != null && this.nearMissRules.indexOf(this.answer.toUpperCase()) != -1) {
         String s2 = RecognitionMessage.getText("recnot006");
         if (s2 != null) {
            String s5 = this.argument.premises != null && this.argument.premises.length == 1 ? "this premise" : "these premises";
            Hashtable hashtable1 = Message.params("ruleName", this.answer, "thisPremise", s5);
            s2 = Message.substitute(s2, hashtable1);
         }

         return this.showVerdict(false, s2);
      } else if (this.answer.toUpperCase().equals("NONE")) {
         return this.correctRules == null && (this.argument == null || this.argument.matchRule(this.module.activeRules()) != 2)
            ? this.showVerdict(true, this.successComment)
            : this.showVerdict(false, RecognitionMessage.getText("recnot001"));
      } else {
         Rule rule = LogicProgram.getRule(this.answer);
         if (rule == null) {
            String s4 = RecognitionMessage.getText("recnot002");
            if (s4 != null) {
               s4 = Message.substitute(s4, Message.params("ruleName", this.answer));
            }

            return this.showVerdict(false, s4);
         } else if (!this.module.ruleActive(rule)) {
            String s3 = RecognitionMessage.getText("recnot007");
            if (s3 != null) {
               s3 = Message.substitute(s3, Message.params("ruleName", rule.name));
            }

            return this.showVerdict(false, s3);
         } else if (this.argument == null) {
            return this.showVerdict(false, RecognitionMessage.getText("recnot004"));
         } else {
            int i = this.argument.matchRule(rule);
            if (i == 2) {
               return this.showVerdict(true, this.successComment);
            } else if (i != 1 && !this.isLenientSinglePremiseRule(rule)) {
               String s6 = RecognitionMessage.getText("recnot003");
               if (s6 != null) {
                  String s7 = this.argument.premises != null && this.argument.premises.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable2 = Message.params("ruleName", rule.name, "thisPremise", s7);
                  s6 = Message.substitute(s6, hashtable2);
               }

               return this.showVerdict(false, s6);
            } else {
               String s = RecognitionMessage.getText("recnot006");
               if (s != null) {
                  String s1 = this.argument.premises != null && this.argument.premises.length == 1 ? "this premise" : "these premises";
                  Hashtable hashtable = Message.params("ruleName", rule.name, "thisPremise", s1);
                  s = Message.substitute(s, hashtable);
               }

               return this.showVerdict(false, s);
            }
         }
      }
   }

   boolean isLenientSinglePremiseRule(Rule rule) {
      if (this.argument.premises != null && this.argument.premises.length == 1) {
         if (rule.name.equalsIgnoreCase("EG")) {
            return true;
         }

         if (rule.name.equalsIgnoreCase("AV")) {
            return true;
         }

         if (rule.name.equalsIgnoreCase("AV3")) {
            return true;
         }
      }

      return false;
   }
}
