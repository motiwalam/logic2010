package edu.ucla.phil.logic;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;

class DerivationLine extends JPanel implements DerivationNode, MessageParamSource, DerivationConstants, ModuleComponentMarker {
   DerivationBox box;
   LinePanel rowPanel;
   LinePanel formulaRow;
   LinePanel annotationRow;
   LinePanel messageRow;
   LineLabel showLabel;
   DerivationLineEditor formulaEditor;
   DerivationLineEditor annotationEditor;
   StyledTextPane messagePane;
   MessageDetailsButton messageButton;
   LineLabel numberLabel;
   Vector references;
   Vector referrers;
   private int lineNumber;
   int messagePhase;
   Expression formula;
   boolean syntaxOk;
   boolean readyToCancel;
   boolean errorHighlighted;
   Vector justifications;
   Message message;
   boolean unusedFlag;
   String messageText;
   FormulaEntryField commandLog;
   static String[] SYMBOLS = LogicProgram.symbols;

   DerivationLine(DerivationBox derivationbox, boolean flag) {
      this.box = derivationbox;
      this.formulaRow = new LinePanel(this);
      this.annotationRow = new LinePanel(this);
      ModuleFrame moduleframe = derivationbox.module.frame;
      this.enableEvents(8L);
      if (flag) {
         this.showLabel = new LineLabel(derivationbox.parentBox == null ? "Problem: " : "Show ");
         this.formulaRow.add(this.showLabel, "West");
         this.annotationRow.add(this.commandLog = new FormulaEntryField(moduleframe), "Center");
         if (!derivationbox.module.doShowLog) {
            this.commandLog.setVisible(false);
         }

         this.annotationEditor = null;
         this.references = null;
      } else {
         this.showLabel = null;
         this.commandLog = null;
         this.annotationRow.add(this.annotationEditor = new DerivationLineEditor(this), "Center");
         this.references = new Vector();
      }

      this.formulaRow.add(this.formulaEditor = new DerivationLineEditor(this), "Center");
      this.formulaRow.add(new LinePanel(this, derivationbox.module.hSpacer), "East");
      this.annotationRow.add(new LinePanel(this, derivationbox.module.hSpacer), "East");
      this.messageRow = new LinePanel(this);
      this.messagePane = new StyledTextPane("");
      this.messagePane.setWrapLines(true);
      this.messagePane.setWrapWords(true);
      this.messagePane.setEditable(false);
      this.messagePane.setFocusable(false);
      this.messageRow.add(this.messagePane, "Center");
      this.messageButton = new MessageDetailsButton(this);
      this.messageButton.setVisible(false);
      this.rowPanel = new LinePanel(this);
      this.rowPanel.setFont(derivationbox.module.font);
      if (derivationbox.module.problem == null) {
         this.rowPanel.setLayout(new FixedColumnLineLayout(derivationbox, derivationbox.module.lineColumns - 1));
      } else {
         this.rowPanel.setLayout(new FixedColumnLineLayout(derivationbox.module.problem, derivationbox.module.lineColumns - 1));
      }

      this.rowPanel.add(this.formulaRow);
      this.rowPanel.add(this.annotationRow);
      this.rowPanel.add(this.messageRow);
      this.rowPanel.add(this.messageButton);
      this.setLayout(new BorderLayout());
      this.add(this.rowPanel, "Center");
      this.add(new LinePanel(this, derivationbox.module.vSpacer), "South");
      derivationbox.module.numbers.add(this.numberLabel = new LineLabel(this));
      this.referrers = new Vector();
      this.messagePhase = 0;
      this.errorHighlighted = false;
      this.updateColors();
      this.formula = null;
      this.syntaxOk = true;
      this.justifications = null;
      this.message = null;
      this.messageText = null;
      this.unusedFlag = false;
   }

   public boolean isDerivationLine() {
      return true;
   }

   @Override
   public DerivationBox getEnclosingBox() {
      return this.box.showLine == this ? this.box.parentBox : this.box;
   }

   @Override
   public void setFormulaText(String s) {
      if (this.formulaEditor != null) {
         this.formulaEditor.setText(LogicProgram.translateSymbols(s, maggie, SYMBOLS));
      }
   }

   @Override
   public String getFormulaText(boolean flag) {
      if (this.formulaEditor == null) {
         return null;
      } else {
         String s = this.formulaEditor.getText();
         if (flag) {
            s = this.stripComment(s);
         }

         return LogicProgram.translateSymbols(s, SYMBOLS, maggie);
      }
   }

   @Override
   public void setAnnotationText(String s) {
      if (this.annotationEditor != null) {
         this.annotationEditor.setText(s);
      }
   }

   @Override
   public String getAnnotationText(boolean flag) {
      if (this.annotationEditor == null) {
         return null;
      } else {
         String s = this.annotationEditor.getText();
         if (flag) {
            s = this.stripComment(s);
         }

         return s;
      }
   }

   String stripComment(String s) {
      if (s == null) {
         return null;
      } else {
         int i = s.indexOf(35);
         return i == -1 ? s : s.substring(0, i).trim();
      }
   }

   @Override
   public String getParamValue(String s) {
      if (s.equals("line number")) {
         return this.getLineNumber() + "";
      } else if (s.equals("wff")) {
         return "\\l" + this.getFormulaText(true) + "\\l";
      } else {
         if (s.equals("show")) {
            DerivationBox derivationbox = this.getEnclosingBox();
            if (derivationbox != null) {
               return "\\l" + derivationbox.getFormulaText(true) + "\\l";
            }
         }

         if (s.equals("show line number")) {
            DerivationBox derivationbox1 = this.getEnclosingBox();
            if (derivationbox1 != null) {
               return derivationbox1.showLine.getLineNumber() + "";
            }
         }

         if (s.equals("premises")) {
            Expression[] aexpression1 = this.box.module.premises;
            int j1 = aexpression1 == null ? 0 : aexpression1.length;
            String object = "\\l";

            for (int k = 0; k < j1; k++) {
               if (k != 0) {
                  object = object + "\\n";
               }

               object = object + aexpression1[k];
            }

            return object + "\\l";
         } else {
            if (s.length() >= 7 && s.substring(0, 7).equals("premise")) {
               Expression[] aexpression = this.box.module.premises;
               int j = aexpression == null ? 0 : aexpression.length;

               int i;
               try {
                  i = Integer.parseInt(s.substring(7).trim());
               } catch (NumberFormatException numberformatexception1) {
                  i = 0;
               }

               if (i > 0 && i <= j) {
                  return "\\l" + aexpression[i - 1] + "\\l";
               }
            }

            if (s.equals("branch lines left")) {
               int l = this.countLinesBelow();
               return l + " line" + (l == 1 ? "" : "s");
            } else if (!s.equals("operator") && !s.equals("quantifier") && !s.equals("connective")) {
               if (s.equals("bound variable") || s.equals("antecedent")) {
                  s = "arg 1";
               } else if (s.equals("bound wff") || s.equals("consequent")) {
                  s = "arg 2";
               }

               if (s.length() >= 3 && s.substring(0, 3).equals("arg")) {
                  Expression expression1 = this.getFormula();
                  if (expression1 == null) {
                     return "";
                  }

                  int k1 = expression1.getChildCount();

                  int i1;
                  try {
                     i1 = Integer.parseInt(s.substring(3).trim());
                  } catch (NumberFormatException numberformatexception) {
                     i1 = 0;
                  }

                  if (i1 > 0 && i1 <= k1) {
                     return "\\l" + expression1.getChild(i1 - 1) + "\\l";
                  }
               }

               return null;
            } else {
               Expression expression = this.getFormula();
               return expression == null ? "" : "\\l" + expression.getSymbol() + "\\l";
            }
         }
      }
   }

   int countLinesBelow() {
      Object object = this.box.showLine == this ? this.box : this;
      DerivationBox derivationbox = ((DerivationNode)object).getEnclosingBox();
      int i = 0;
      int j = derivationbox.getContentCount();
      int k = ((DerivationNode)object).getIndexInBox();

      for (int l = k + 1; l < j; l++) {
         i += derivationbox.getNode(l).countLines(false);
      }

      return i;
   }

   @Override
   public void setMessageText(String s, boolean flag) {
      this.messagePhase = 0;
      if (s == null) {
         this.messagePane.setBackground(this.box.module.colors[2]);
         this.messagePane.setTextForeground(this.box.module.colors[0]);
         this.messagePane.setText("");
      } else {
         this.messagePane.setBackground(this.box.module.colors[flag ? 3 : 2]);
         this.messagePane.setTextForeground(this.box.module.colors[flag ? 5 : 0]);
         this.messagePane.setText(s);
      }
   }

   @Override
   public void showMessage(String s) {
      this.showMessage(s, null, this.box.module.phase, null);
   }

   void showMessage(String s, int i) {
      this.showMessage(s, null, i, null);
   }

   void showMessage(String s, DerivationLineChecker derivationlinechecker) {
      this.showMessage(s, derivationlinechecker, this.box.module.phase, null);
   }

   void showMessage(String s, DerivationLineChecker derivationlinechecker, int i) {
      this.showMessage(s, derivationlinechecker, i, null);
   }

   @Override
   public void showMessage(String s, Hashtable hashtable) {
      this.showMessage(s, null, this.box.module.phase, hashtable);
   }

   void showMessage(String s, int i, Hashtable hashtable) {
      this.showMessage(s, null, i, hashtable);
   }

   void showMessage(String s, DerivationLineChecker derivationlinechecker, Hashtable hashtable) {
      this.showMessage(s, derivationlinechecker, this.box.module.phase, hashtable);
   }

   void showMessage(String s, DerivationLineChecker derivationlinechecker, int i, Hashtable hashtable) {
      if (!LPDerivation.restating) {
         if (this.messagePhase == 0 || i < this.messagePhase) {
            this.message = DerivationMessage.get(s);
            if (this.message.isError) {
               this.messagePane.setBackground(this.box.module.colors[3]);
               this.messagePane.setTextForeground(this.box.module.colors[4]);
               if (!this.message.id.equalsIgnoreCase("dererr078") && !this.message.id.equalsIgnoreCase("dererr057")) {
                  this.box.module.errorCount++;
               }
            } else {
               this.messagePane.setBackground(this.box.module.colors[2]);
               this.messagePane.setTextForeground(this.box.module.colors[0]);
            }

            this.messagePhase = i;
            if (derivationlinechecker == null) {
               this.messageText = DerivationMessage.format(this.message.title, hashtable, this);
            } else {
               this.messageText = DerivationMessage.format(this.message.title, hashtable, derivationlinechecker);
            }

            if (this.box.module.errorMessagesDisabled && !this.message.id.equalsIgnoreCase("dererr064")) {
               if (!this.message.id.equalsIgnoreCase("dererr078") && (this.box.parentBox != null || this.box.showLine != this)) {
                  if (this.message.isError) {
                     this.messagePane.setText("Error");
                  }
               } else {
                  this.messagePane.setText(LogicProgram.expandEscapes(this.messageText));
               }
            } else {
               this.messagePane.setText(LogicProgram.expandEscapes(this.messageText));
               this.messageButton.setVisible(true);
               this.messageButton.prepareExplanation(hashtable, derivationlinechecker);
            }

            if (this.message.isError) {
               this.flagEnclosingBox();
            }
         }
      }
   }

   void flagEnclosingBox() {
      if (this.box.module.serialMode && this.box.parentBox != null) {
         DerivationBox derivationbox = this.getEnclosingBox();
         if (derivationbox.parentBox != null) {
            derivationbox.showMessage("dererr078", 4);
         }
      }
   }

   void setMessageButtonParam(String s, Object object) {
      this.messageButton.setHandlerParam(s, object);
   }

   @Override
   public void clearMessage() {
      this.clearMessage(this.box.module.phase);
   }

   void clearMessage(int i) {
      if (!LPDerivation.restating) {
         if (this.messagePhase == 0 || i == this.messagePhase) {
            this.messageButton.setVisible(false);
            this.messageButton.reset();
            this.message = null;
            this.errorHighlighted = false;
            this.messagePane.setBackground(this.box.module.colors[2]);
            this.messagePane.setForeground(this.box.module.colors[0]);
            this.messagePhase = 0;
            this.messageText = null;
            this.messagePane.setText("");
         }
      }
   }

   @Override
   public DerivationLineEditor getFormulaEditor() {
      return this.formulaEditor;
   }

   int getEditorSelectionStart(boolean flag) {
      DerivationLineEditor derivationlineeditor = flag ? this.annotationEditor : this.formulaEditor;
      if (derivationlineeditor == null) {
         return 0;
      } else {
         return derivationlineeditor == this.box.module.focus ? derivationlineeditor.getSelectionStart() : derivationlineeditor.savedSelectionStart;
      }
   }

   int getEditorSelectionEnd(boolean flag) {
      DerivationLineEditor derivationlineeditor = flag ? this.annotationEditor : this.formulaEditor;
      if (derivationlineeditor == null) {
         return 0;
      } else {
         return derivationlineeditor == this.box.module.focus ? derivationlineeditor.getSelectionEnd() : derivationlineeditor.savedSelectionEnd;
      }
   }

   int getEditorCaretPosition(boolean flag) {
      DerivationLineEditor derivationlineeditor = flag ? this.annotationEditor : this.formulaEditor;
      if (derivationlineeditor == null) {
         return 0;
      } else {
         return derivationlineeditor == this.box.module.focus ? derivationlineeditor.getCaretPosition() : derivationlineeditor.savedCaretPosition;
      }
   }

   void setEditorSelection(int i, int j, boolean flag) {
      DerivationLineEditor derivationlineeditor = flag ? this.annotationEditor : this.formulaEditor;
      if (derivationlineeditor != null) {
         derivationlineeditor.select(derivationlineeditor.savedSelectionStart = i, derivationlineeditor.savedSelectionEnd = j);
      }
   }

   void setEditorCaretPosition(int i, boolean flag) {
      DerivationLineEditor derivationlineeditor = flag ? this.annotationEditor : this.formulaEditor;
      if (derivationlineeditor != null) {
         derivationlineeditor.setCaretPosition(derivationlineeditor.savedCaretPosition = i);
      }
   }

   @Override
   public DerivationLineEditor getAnnotationEditor() {
      return this.annotationEditor;
   }

   @Override
   public int getIndexInBox() {
      return this.box == null ? -1 : this.box.toContentIndex(this.box.indexOfChild(this));
   }

   @Override
   public void focusEditor(boolean flag) {
      this.expandEnclosingBoxes();
      if ((flag || this.formulaEditor == null) && this.annotationEditor != null) {
         this.annotationEditor.requestFocus();
      } else if (this.formulaEditor != null) {
         this.formulaEditor.requestFocus();
      }
   }

   @Override
   public void requestFocus() {
   }

   void commitEdit(boolean flag) {
      if (this.annotationEditor != null && (this.formulaEditor == null ? flag : this.formulaEditor.getText().equals(""))) {
         this.box.module.abort(false);
         this.box.module.resetVarNames();
         if (flag) {
            this.justifications = null;
         }

         if (this.box.module.focus == this.annotationEditor) {
            this.parseReferences();
         } else if (this.box.module.focus == this.formulaEditor) {
            this.parseFormula();
         }

         if (this.checkLine(true) && this.readyToCancel) {
            this.toggleBoxAndCancel();
         }
      }
   }

   DerivationBox applyShowPrefix() {
      if (this.formulaEditor != null && this.annotationEditor != null) {
         String s = this.formulaEditor.getText().trim();
         if (s.length() < 4 || !s.substring(0, 4).equalsIgnoreCase("Show")) {
            return null;
         } else if (s.length() > 4 && !Character.isWhitespace(s.charAt(4))) {
            return null;
         } else {
            this.formulaEditor.setText(s.substring(4).trim());
            return this.makeShowLine();
         }
      } else {
         return null;
      }
   }

   @Override
   public DerivationLine insertLineAfter() {
      DerivationBox derivationbox = this.box;
      DerivationLineEditor derivationlineeditor = this.box.module.focus;
      boolean flag = derivationlineeditor != null && derivationlineeditor == derivationlineeditor.line.annotationEditor;
      int i = this.getIndexInBox();
      if (this.box.cancelLine == this || !this.box.isExpanded()) {
         derivationbox = derivationbox.parentBox;
         if (derivationbox == null) {
            return null;
         }

         i = this.box.getIndexInBox();
      }

      if (flag) {
         derivationlineeditor.line.parseReferences();
      }

      DerivationLine derivationline1 = derivationbox.insertLine(derivationbox.toComponentIndex(i + 1));
      this.box.module.problem.renumberAll();
      if (flag) {
         derivationlineeditor.line.refreshReferenceNumbers();
         derivationlineeditor.line.clearReferences();
      }

      return derivationline1;
   }

   @Override
   public void deleteNode(boolean flag1) {
      if (this.box.showLine == this) {
         if (this.box.parentBox != null) {
            this.makePlainLine().deleteNode(false);
         }
      } else {
         if (this.box.cancelLine == this) {
            this.uncancel();
         }

         DerivationLineEditor derivationlineeditor = this.box.module.focus;
         boolean flag = derivationlineeditor != null && derivationlineeditor == derivationlineeditor.line.annotationEditor && derivationlineeditor.line != this;
         if (flag) {
            derivationlineeditor.line.parseReferences();
         }

         this.clearReferences();
         this.detachReferrers();
         this.box.module.numbers.remove(this.numberLabel);
         this.suppressEditorFocusLoss();
         this.box.remove(this);
         this.box.module.setWidths(false);
         this.box.module.problem.renumberAll();
         if (flag) {
            derivationlineeditor.line.refreshReferenceNumbers();
            derivationlineeditor.line.clearReferences();
         }
      }
   }

   @Override
   public DerivationNode getNextNode(boolean flag) {
      int i = this.getIndexInBox() + 1;

      DerivationBox derivationbox;
      for (derivationbox = this.box;
         derivationbox != null && i >= (flag && !derivationbox.isExpanded() ? 1 : derivationbox.getContentCount());
         derivationbox = derivationbox.parentBox
      ) {
         i = derivationbox.getIndexInBox() + 1;
      }

      return derivationbox == null ? null : derivationbox.getNode(i);
   }

   @Override
   public DerivationNode getPreviousNode(boolean flag) {
      int i = this.getIndexInBox();
      DerivationBox derivationbox = this.box;
      if (i == 0) {
         i = derivationbox.getIndexInBox();
         derivationbox = derivationbox.parentBox;
      }

      if (derivationbox == null) {
         return null;
      } else {
         DerivationNode derivationnode = derivationbox.getNode(i - 1);

         while (derivationnode instanceof DerivationBox) {
            derivationbox = (DerivationBox)derivationnode;
            if (flag && !derivationbox.isExpanded() || derivationbox.getContentCount() == 1) {
               break;
            }

            derivationnode = derivationbox.getNode(derivationbox.getContentCount() - 1);
         }

         return derivationnode;
      }
   }

   @Override
   public DerivationNode getHeadNode() {
      return (DerivationNode)(this.box.showLine == this ? this.box : this);
   }

   @Override
   public boolean isShowLine() {
      return this.box.showLine == this;
   }

   @Override
   public boolean isCancelLine() {
      return this.box.cancelLine == this;
   }

   @Override
   public boolean areEnclosingBoxesExpanded() {
      for (DerivationBox derivationbox = this.box.showLine == this ? this.box.parentBox : this.box;
         derivationbox != null;
         derivationbox = derivationbox.parentBox
      ) {
         if (!derivationbox.isExpanded()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void expandEnclosingBoxes() {
      for (DerivationBox derivationbox = this.box.showLine == this ? this.box.parentBox : this.box;
         derivationbox != null;
         derivationbox = derivationbox.parentBox
      ) {
         if (!derivationbox.isExpanded()) {
            derivationbox.setExpanded(true);
         }
      }
   }

   @Override
   public Rectangle getBoundsInProblemPanel() {
      return LogicProgram.boundsRelativeTo(this, this.box.module.problemPanel);
   }

   boolean canUse(DerivationNode derivationnode) {
      ErrorRef errorref = this.usageError(derivationnode);
      if (errorref != null) {
         this.showMessage(errorref.id, errorref.params);
      }

      return errorref == null;
   }

   /** Why this line may not cite derivationnode, or null if it may. */
   ErrorRef usageError(DerivationNode derivationnode) {
      DerivationBox derivationbox = this.getEnclosingBox();
      Object object = this.getHeadNode();
      DerivationBox derivationbox1 = derivationnode.getEnclosingBox();
      DerivationNode derivationnode1 = derivationnode.getHeadNode();
      if (derivationbox1 != null && this.box.showLine != this) {
         while (derivationbox != derivationbox1 && derivationbox != null) {
            object = derivationbox;
            derivationbox = derivationbox.parentBox;
         }

         if (derivationbox == derivationbox1 && derivationnode1.getIndexInBox() < ((DerivationNode)object).getIndexInBox()) {
            return null;
         } else {
            int i = derivationnode1.getLineNumber();
            if (i >= this.getLineNumber()) {
               return new ErrorRef("dererr044", Message.params("remote line number", i + ""));
            } else if (derivationbox == derivationbox1) {
               return new ErrorRef("dererr045", Message.params("remote line number", i + ""));
            } else {
               return new ErrorRef("dererr046", Message.params("remote line number", i + ""));
            }
         }
      } else {
         return new ErrorRef("dererrtxt", Message.params("text", "unexpected error; contact instructor: LPDerLine.canUse(ILPDerLine)"));
      }
   }

   @Override
   public void moveIntoPreviousBox() {
      if (this.box.cancelLine == this) {
         this.uncancel();
      }

      if (this.box.showLine == this) {
         this.box.moveIntoPreviousBox();
      } else {
         int i = this.getIndexInBox();
         if (i > 0) {
            DerivationNode derivationnode = this.box.getNode(i - 1);
            if (derivationnode instanceof DerivationBox) {
               DerivationLineEditor derivationlineeditor = this.box.module.focus;
               if (derivationlineeditor != null && derivationlineeditor.line == this) {
                  this.box.highlightShowLabel(false);
               }

               this.box.remove(this);
               ((DerivationBox)derivationnode).add(this, -1);
               this.box = (DerivationBox)derivationnode;
               if (derivationlineeditor != null && derivationlineeditor.line == this) {
                  this.box.highlightShowLabel(true);
               }

               derivationlineeditor.requestFocus();
            }
         }
      }
   }

   void indentIntoOpenBox() {
      int i = this.getIndexInBox();
      DerivationBox derivationbox = this.box;
      if (i == 0) {
         i = derivationbox.getIndexInBox();
         derivationbox = derivationbox.parentBox;
      }

      if (derivationbox != null) {
         DerivationNode derivationnode = null;
         int j = i;

         while (--j > 0) {
            derivationnode = derivationbox.getNode(j);
            if (derivationnode instanceof DerivationBox && ((DerivationBox)derivationnode).cancelLine == null && ((DerivationBox)derivationnode).isExpanded()) {
               break;
            }
         }

         if (j > 0) {
            while (--i >= j) {
               derivationbox.getNode(j + 1).moveIntoPreviousBox();
            }

            derivationnode.layoutColumns();
            derivationbox.module.setWidths(false);
         }
      }

      this.box.revalidate();
   }

   @Override
   public void moveOutOfBox() {
      if (this.box.cancelLine == this) {
         this.uncancel();
      }

      if (this.box.showLine == this) {
         this.box.moveOutOfBox();
      } else {
         int i = this.getIndexInBox();
         if (i == this.box.getContentCount() - 1) {
            DerivationBox derivationbox = this.box.parentBox;
            if (derivationbox != null) {
               DerivationLineEditor derivationlineeditor = this.box.module.focus;
               if (derivationlineeditor != null && derivationlineeditor.line == this) {
                  this.box.highlightShowLabel(false);
               }

               this.box.remove(this);
               derivationbox.add(this, derivationbox.toComponentIndex(this.box.getIndexInBox() + 1));
               this.box = derivationbox;
               if (derivationlineeditor != null && derivationlineeditor.line == this) {
                  this.box.highlightShowLabel(true);
               }

               derivationlineeditor.requestFocus();
            }
         }
      }
   }

   void outdentFollowingLines() {
      int i = this.getIndexInBox();
      DerivationBox derivationbox = this.box;
      if (i == 0) {
         i = derivationbox.getIndexInBox();
         derivationbox = derivationbox.parentBox;
      }

      if (derivationbox != null && derivationbox.parentBox != null) {
         int j = derivationbox.getContentCount();

         while (--j >= i) {
            derivationbox.getNode(j).moveOutOfBox();
         }

         derivationbox.parentBox.layoutColumns();
         derivationbox.module.setWidths(false);
      }

      this.box.revalidate();
   }

   void suppressEditorFocusLoss() {
      if (this.formulaEditor != null) {
         this.formulaEditor.ignoreFocusLoss = true;
      }

      if (this.annotationEditor != null) {
         this.annotationEditor.ignoreFocusLoss = true;
      }
   }

   void restoreEditorFocusLoss() {
      if (this.formulaEditor != null) {
         this.formulaEditor.ignoreFocusLoss = false;
      }

      if (this.annotationEditor != null) {
         this.annotationEditor.ignoreFocusLoss = false;
      }
   }

   DerivationNode toggleShow() {
      Object object;
      if (this.box.showLine == this) {
         object = this.makePlainLine();
      } else {
         object = this.makeShowLine();
      }

      return (DerivationNode)object;
   }

   DerivationBox makeShowLine() {
      if (this.box.showLine == this) {
         return this.box;
      } else if (this.box.cancelLine == this) {
         return null;
      } else {
         this.formulaRow.add(this.showLabel = new LineLabel("Show "), "West");
         this.annotationRow.remove(this.annotationEditor);
         this.annotationRow.add(this.commandLog = new FormulaEntryField(this.box.module.frame), "Center");
         this.commandLog.setForeground(this.box.module.colors[0]);
         this.commandLog.setBackground(this.box.module.colors[1]);
         if (!this.box.module.doShowLog) {
            this.commandLog.setVisible(false);
         }

         this.annotationEditor = null;
         this.clearReferences();
         this.references = null;
         DerivationBox derivationbox = this.box;
         int i = derivationbox.indexOfChild(this);
         this.suppressEditorFocusLoss();
         derivationbox.remove(this);
         derivationbox.add(this.box = new DerivationBox(this), i);
         this.restoreEditorFocusLoss();
         this.box.revalidate();
         return this.box;
      }
   }

   DerivationLine makePlainLine() {
      DerivationBox derivationbox = this.box.parentBox;
      if (this.box.showLine != this) {
         return this;
      } else if (derivationbox == null) {
         return null;
      } else {
         int i = this.box.getContentCount();

         while (--i > 0) {
            this.box.getNode(i).moveOutOfBox();
         }

         this.formulaRow.remove(this.showLabel);
         this.showLabel = null;
         this.annotationRow.remove(this.commandLog);
         this.commandLog = null;
         this.annotationRow.add(this.annotationEditor = new DerivationLineEditor(this), "Center");
         this.references = new Vector();
         int j = derivationbox.indexOfChild(this.box);
         this.suppressEditorFocusLoss();
         derivationbox.remove(this.box);
         derivationbox.add(this, j);
         this.box = derivationbox;
         this.restoreEditorFocusLoss();
         this.box.revalidate();
         return this;
      }
   }

   void toggleBoxAndCancel() {
      if (this.box.cancelLine == this) {
         this.uncancel();
      } else {
         this.boxAndCancel();
      }

      this.box.module.invalrepaint();
   }

   void boxAndCancel() {
      if (this.box.parentBox == null) {
         DerivationDialogs.showMessage("dernot009", null, this);
      } else if (this.box.showLine == this) {
         DerivationDialogs.showMessage("dernot010", null, this);
      } else {
         if (this.box.cancelLine == null && this.isLastInBox()) {
            this.deleteFollowingLines();
         }

         if (this.box.getContentCount() - 1 != this.getIndexInBox()) {
            DerivationDialogs.showMessage("dernot011", null, this);
         } else if (this.box.cancelLine == null) {
            this.formulaEditor.ignoreFocusLoss = true;
            this.formulaRow.remove(this.formulaEditor);
            this.formulaEditor = null;
            this.formula = null;
            this.syntaxOk = true;
            this.clearMessage(1);
            this.box.cancelLine = this;
            this.box.showLine.showLabel.setCanceled(true);
            this.box.revalidate();
         }
      }
   }

   void uncancel() {
      if (this.box.cancelLine == this) {
         this.formulaRow.add(this.formulaEditor = new DerivationLineEditor(this), "Center");
         this.formula = null;
         this.syntaxOk = true;
         this.box.cancelLine = null;
         this.box.showLine.showLabel.setCanceled(false);
         this.box.revalidate();
      }
   }

   boolean isLastInBox() {
      int i = this.box.getContentCount();

      for (int j = this.getIndexInBox() + 1; j < i; j++) {
         DerivationNode derivationnode = this.box.getNode(j);
         if (derivationnode instanceof DerivationBox) {
            return false;
         }

         String s = ((DerivationLine)derivationnode).getFormulaText(true);
         String s1 = ((DerivationLine)derivationnode).getAnnotationText(true);
         if (s != null && !s.trim().equals("") || s1 != null && !s1.trim().equals("")) {
            return false;
         }
      }

      return true;
   }

   void deleteFollowingLines() {
      int i = this.getIndexInBox() + 1;

      while (this.box.getContentCount() > i) {
         this.box.getNode(i).deleteNode(true);
      }
   }

   @Override
   public int renumberLines(int i) {
      this.setLineNumber(i);
      return i + 1;
   }

   void updateFont() {
      ModuleFrame moduleframe = this.box.module.frame;
      if (moduleframe != null) {
         this.setFont(LogicProgram.getFont(this.box.module.fontSize));
      }
   }

   @Override
   public void setFont(Font font) {
      super.setFont(font);
      if (this.showLabel != null) {
         this.showLabel.setFont(font);
      }

      if (this.formulaEditor != null) {
         this.formulaEditor.setFont(font);
      }

      if (this.annotationEditor != null) {
         this.annotationEditor.setFont(font);
      }

      if (this.commandLog != null) {
         this.commandLog.setFont(font);
      }

      if (this.messagePane != null) {
         this.messagePane.setFont(font);
      }

      if (this.messageButton != null) {
         this.messageButton.setFont(font);
      }
   }

   void updateColors() {
      ModuleFrame moduleframe = this.box.module.frame;
      if (moduleframe != null) {
         this.applyColors(this.box.module.colors);
      }
   }

   @Override
   public void applyColors(Color[] acolor) {
      DerivationLineEditor derivationlineeditor = this.box.module.focus;
      this.setForeground(acolor[0]);
      this.setBackground(acolor[1]);
      this.numberLabel.setForeground(acolor[0]);
      this.numberLabel.setBackground(acolor[1]);
      if (this.showLabel != null) {
         if (this.isShowLineOfFocusedBox()) {
            this.showLabel.setForeground(acolor[1]);
            this.showLabel.setBackground(acolor[0]);
         } else {
            this.showLabel.setForeground(acolor[0]);
            this.showLabel.setBackground(acolor[2]);
         }
      }

      if (this.formulaEditor != null) {
         this.formulaEditor.setForeground(derivationlineeditor == this.formulaEditor ? acolor[1] : acolor[0]);
         this.formulaEditor.setBackground(derivationlineeditor == this.formulaEditor ? acolor[0] : acolor[2]);
      }

      if (this.annotationEditor != null) {
         this.annotationEditor.setForeground(derivationlineeditor == this.annotationEditor ? acolor[1] : acolor[0]);
         this.annotationEditor.setBackground(derivationlineeditor == this.annotationEditor ? acolor[0] : acolor[2]);
      }

      if (this.commandLog != null) {
         this.commandLog.setForeground(acolor[0]);
         this.commandLog.setBackground(acolor[1]);
      }

      this.messagePane.setForeground(this.errorHighlighted ? acolor[4] : acolor[0]);
      this.messageRow.setBackground(this.errorHighlighted ? acolor[3] : acolor[2]);
      this.messageButton.setForeground(acolor[0]);
      this.messageButton.setBackground(acolor[1]);
   }

   boolean isShowLineOfFocusedBox() {
      if (this.showLabel == null) {
         return false;
      } else {
         DerivationLineEditor derivationlineeditor = this.box.module.focus;
         if (derivationlineeditor == null) {
            return false;
         } else {
            DerivationBox derivationbox = derivationlineeditor.line.getEnclosingBox();
            return derivationbox == null ? false : derivationbox.showLine == this;
         }
      }
   }

   @Override
   public void layoutColumns() {
      Point point = this.getLocation();
      point.x = this.box.showLine == this ? 0 : this.box.module.indent;
      this.setLocation(point);
      int[] aint;
      if (this.box.parentBox == null && this.box.showLine == this) {
         aint = this.box.module.problemWidths;
      } else {
         aint = this.box.module.proofWidths;
      }

      ((FixedColumnLineLayout)this.rowPanel.getLayout()).setColumnWidths(aint);
      this.rowPanel.doLayout();
      this.revalidate();
   }

   @Override
   public int getMaxBoxDepth(boolean flag) {
      return 0;
   }

   @Override
   public int getBoxDepth() {
      return this.box.showLine == this ? this.box.getBoxDepth() : this.box.getBoxDepth() + 1;
   }

   @Override
   public int countLines(boolean flag) {
      return 1;
   }

   @Override
   public synchronized int getLineNumber() {
      return this.lineNumber;
   }

   public synchronized void setLineNumber(int i) {
      if (this.lineNumber != i) {
         if (this.numberLabel != null) {
            this.numberLabel.setText(i == 0 ? " " : Integer.toString(i));
         }

         this.lineNumber = i;
      }
   }

   @Override
   public DerivationNode findLine(int i) {
      return null;
   }

   DerivationNode getRelativeNode(Integer integer) {
      return integer == null ? null : this.getRelativeNode(integer.intValue());
   }

   DerivationNode getRelativeNode(int i) {
      if (i >= 0) {
         return null;
      } else {
         int j = this.getIndexInBox();
         DerivationBox derivationbox = this.box;
         if (j == 0) {
            j = derivationbox.getIndexInBox();
            derivationbox = derivationbox.parentBox;
         }

         if (derivationbox == null) {
            return null;
         } else {
            j += i;
            return j > 0 ? derivationbox.getNode(j) : derivationbox.showLine.getRelativeNode(j - 1);
         }
      }
   }

   static boolean isOperatorChar(char c0) {
      return !Character.isLetter(c0) && c0 < 256 ? "~!@#$%^&*()_+-=<>|".indexOf(c0) != -1 : true;
   }

   void resolveRelativeReferences() {
      String s = this.getAnnotationText(true);
      int i = s.length();
      int j = 0;
      int i1 = 0;
      char c0 = 0;
      char c1 = 0;

      while (true) {
         if (j < i) {
            c0 = c1;
            c1 = s.charAt(j);
            if (c1 != '-') {
               j++;
               continue;
            }
         }

         if (j >= i) {
            this.refreshReferenceNumbers();
            this.clearReferences();
            return;
         }

         int k;
         for (k = j++; j < i; j++) {
            c1 = s.charAt(j);
            if (!Character.isDigit(c1)) {
               break;
            }
         }

         int l = j++;
         if ((k == 0 || !isOperatorChar(c0))
            && (l == i || !isOperatorChar(c1))
            && this.addReference(k - i1, l - k, this.getRelativeNode(LogicProgram.parseInteger(s.substring(k, l)))) != null) {
            i1 = l;
         }
      }
   }

   void parseReferences() {
      this.parseReferences(true);
   }

   void parseReferences(boolean flag) {
      int i = this.box.module.setPhase(2);

      try {
         if (this.annotationEditor != null) {
            this.clearMessage();
            this.clearReferences();
            if (flag) {
               this.resolveRelativeReferences();
            }

            String s = this.getAnnotationText(true);
            int j = s.length();
            int k = 0;
            int j1 = 0;
            char c0 = 0;
            char c1 = 0;

            while (true) {
               while (k < j) {
                  c0 = c1;
                  c1 = s.charAt(k);
                  if (Character.isDigit(c1)) {
                     break;
                  }

                  k++;
               }

               if (k >= j) {
                  return;
               }

               int l;
               for (l = k++; k < j; k++) {
                  c1 = s.charAt(k);
                  if (!Character.isDigit(c1)) {
                     break;
                  }
               }

               int i1 = k++;
               if (l != 0 && isOperatorChar(c0) || i1 != j && isOperatorChar(c1)) {
                  if ((l == 0 || !isOperatorChar(c0)) && i1 != j && isOperatorChar(c1)) {
                     this.showMessage("dererr047");
                  }
               } else if (this.addReference(l - j1, i1 - l, LogicProgram.parseInteger(s.substring(l, i1))) != null) {
                  j1 = i1;
               }
            }
         }
      } finally {
         this.box.module.setPhase(i);
      }
   }

   @Override
   public void refreshReferenceNumbers() {
      String s = this.getAnnotationText(false);
      if (s != null) {
         String s1 = "";
         int i = this.references.size();
         int j = 0;
         if (this.box.module.focus == this.annotationEditor) {
            this.annotationEditor.saveSelection(false);
         }

         for (int k = 0; k < i; k++) {
            LineReference linereference = (LineReference)this.references.elementAt(k);
            String s2 = Integer.toString(linereference.target.getLineNumber());
            this.annotationEditor.adjustSavedSelection(s1.length() + linereference.offset, linereference.length, s2.length());
            s1 = s1 + s.substring(j, j + linereference.offset) + s2;
            j += linereference.offset + linereference.length;
            linereference.length = s2.length();
         }

         s1 = s1 + s.substring(j);
         this.setAnnotationText(s1);
         if (this.box.module.focus == this.annotationEditor) {
            this.annotationEditor.restoreSelection();
         }
      }
   }

   LineReference addReference(int i, int j, DerivationNode derivationnode) {
      if (derivationnode == null) {
         return null;
      } else {
         LineReference linereference = new LineReference(i, j, derivationnode, this);
         this.references.addElement(linereference);
         derivationnode.addReferrer(linereference);
         return linereference;
      }
   }

   LineReference addReference(int i, int j, Integer integer) {
      return integer == null ? null : this.addReference(i, j, integer.intValue());
   }

   LineReference addReference(int i, int j, int k) {
      return this.addReference(i, j, this.box.module.problem.findLine(k));
   }

   void removeReference(LineReference linereference) {
      int i = this.references.size();
      int j = 0;
      linereference.target.removeReferrer(linereference);
      if (this.box.module.focus == this.annotationEditor) {
         this.annotationEditor.saveSelection(false);
      }

      for (int k = 0; k < i; k++) {
         LineReference linereference1 = (LineReference)this.references.elementAt(k);
         if (linereference1 == linereference) {
            String s = this.getAnnotationText(false);
            String s1 = "<deleted>";
            this.annotationEditor.adjustSavedSelection(j + linereference1.offset, linereference1.length, s1.length());
            this.setAnnotationText(s.substring(0, j + linereference1.offset) + s1 + s.substring(j + linereference1.offset + linereference1.length));
            this.references.removeElementAt(k);
            i--;
            j = linereference1.offset + s1.length();
            if (k < i) {
               linereference1 = (LineReference)this.references.elementAt(k);
               linereference1.offset += j;
            }
            break;
         }

         j += linereference1.offset + linereference1.length;
      }

      if (this.box.module.focus == this.annotationEditor) {
         this.annotationEditor.restoreSelection();
      }
   }

   void clearReferences() {
      int i = this.references.size();

      while (--i >= 0) {
         LineReference linereference = (LineReference)this.references.elementAt(i);
         linereference.target.removeReferrer(linereference);
         this.references.removeElementAt(i);
      }
   }

   @Override
   public void addReferrer(LineReference linereference) {
      this.referrers.addElement(linereference);
   }

   @Override
   public void removeReferrer(LineReference linereference) {
      this.referrers.removeElement(linereference);
   }

   @Override
   public void detachReferrers() {
      while (!this.referrers.isEmpty()) {
         LineReference linereference = (LineReference)this.referrers.firstElement();
         linereference.source.removeReference(linereference);
      }
   }

   @Override
   public void retargetReferrers() {
      Object object = this.box.showLine == this ? this.box : this;
      Enumeration enumeration = this.referrers.elements();

      while (enumeration.hasMoreElements()) {
         LineReference linereference = (LineReference)enumeration.nextElement();
         linereference.target = (DerivationNode)object;
      }
   }

   void checkRedundantShow() {
      if (this.syntaxOk && this.formula != null && this == this.box.showLine && this.box.parentBox != null) {
         int i = this.box.getIndexInBox();

         for (DerivationBox derivationbox = this.box.parentBox; derivationbox != null; derivationbox = derivationbox.parentBox) {
            while (--i != 0) {
               DerivationNode derivationnode = derivationbox.getNode(i);
               if (this.formula.isIdentical(derivationnode.getFormula())) {
                  Hashtable hashtable = Message.params("previous", "line " + derivationnode.getLineNumber());
                  this.showMessage("dernot053", hashtable);
                  return;
               }
            }

            i = derivationbox.getIndexInBox();
         }

         Expression[] aexpression = this.box.module.premises;
         int j = aexpression == null ? 0 : aexpression.length;

         for (int k = 0; k < j; k++) {
            if (this.formula.isIdentical(aexpression[k])) {
               Hashtable hashtable1 = Message.params("previous", "premise " + (k + 1));
               this.showMessage("dernot053", hashtable1);
               return;
            }
         }
      }
   }

   @Override
   public boolean checkSyntax() {
      if (this.box.module.focus == this.formulaEditor) {
         this.parseFormula();
      }

      return this.syntaxOk;
   }

   void parseFormula() {
      int i = this.box.module.setPhase(1);

      try {
         this.syntaxOk = true;
         if (this.formulaEditor == null) {
            return;
         }

         this.clearMessage();
         if (this.box.showLine == this && this.box.parentBox == null) {
            this.box.module.parseProblem();
            return;
         }

         this.applyShowPrefix();

         try {
            this.formula = LogicProgram.parseFormula(this.getFormulaText(true), false, false, this.box.showLine == this);
         } catch (FormulaParseException formulaparseexception) {
            this.formula = null;
            String s = formulaparseexception.getMessage();
            if (s == null) {
               this.showMessage("dererr059", Message.params("parser error", this.getFormulaText(true)));
            } else {
               this.showMessage("dererrtxt", Message.params("text", s));
            }

            this.syntaxOk = false;
         }
      } finally {
         this.box.module.setPhase(i);
      }
   }

   @Override
   public Expression getFormula() {
      return this.formula;
   }

   @Override
   public boolean verify() {
      return this.checkLine(false);
   }

   boolean checkLine(boolean flag) {
      int i = this.box.module.setPhase(3);

      try {
         if (!this.syntaxOk) {
            this.box.strategyConsistent = false;
            return false;
         } else {
            this.readyToCancel = false;
            if (this.box.showLine != this) {
               this.clearMessage(4);
            }

            this.clearMessage();
            if (this.annotationEditor != null) {
               DerivationLineChecker derivationlinechecker = new DerivationLineChecker(this, flag);
               if (this.formulaEditor != null && this.formula == null) {
                  if (this.getAnnotationText(true).trim().equals("")) {
                     if (!flag) {
                        this.showMessage("derinf003");
                        return true;
                     }

                     return true;
                  }

                  if (!flag) {
                     derivationlinechecker.getClass();
                     this.showMessage("derinf004");
                     return true;
                  }
               }

               while (derivationlinechecker.readNextStep()) {
                  if (derivationlinechecker.getRuleName() == null) {
                     this.showMessage("dererr049");
                     this.box.strategyConsistent = false;
                     return false;
                  }

                  if (!derivationlinechecker.skipToNextStep()) {
                     if (this.box.module.serialMode) {
                        return derivationlinechecker.checkStep(true) && this.checkNotAtTopLevel();
                     }

                     if (flag) {
                        return derivationlinechecker.checkStep(true);
                     }

                     if (!derivationlinechecker.checkStep(false, true)) {
                        return false;
                     }

                     if (derivationlinechecker.lineFormula == null || derivationlinechecker.lineFormula.isIdentical(derivationlinechecker.result)) {
                        return true;
                     }

                     Hashtable hashtable = null;
                     if (derivationlinechecker.consumedFormulas != null) {
                        String s = "";
                        int j = derivationlinechecker.consumedFormulas.size();

                        for (int k = 0; k < j; k++) {
                           s = s + (k == 0 ? "" : "\\n") + derivationlinechecker.consumedFormulas.elementAt(k);
                        }

                        hashtable = Message.putParam(hashtable, "rule form premises", "\\l" + s + "\\l");
                     }

                     Hashtable hashtable1 = this.box.module.commandMode ? new Hashtable() : null;
                     if (hashtable1 != null) {
                        if (this.justifications != null) {
                           hashtable1.put("caches", this.justifications);
                        }

                        hashtable1.put("just", derivationlinechecker);
                        if (derivationlinechecker.result != null) {
                           hashtable1.put("sum", derivationlinechecker.result);
                           hashtable = Message.putParam(hashtable, "rule form conclusion", "\\l" + derivationlinechecker.result + "\\l");
                        }
                     }

                     String s1 = this.box.module.commandMode ? "dernot100" : "dernot101";
                     DerivationDialogs.showMessage(s1, hashtable, hashtable1, derivationlinechecker);
                     return false;
                  }

                  if (!this.box.module.queuedMode) {
                     this.showMessage("dererr050");
                     this.box.strategyConsistent = false;
                     return false;
                  }

                  if (!derivationlinechecker.checkStep(false)) {
                     return false;
                  }

                  if (this.box.module.aborted()) {
                     return false;
                  }

                  if (derivationlinechecker.matchLine) {
                     return true;
                  }

                  this.collectVariables(derivationlinechecker.result);
                  this.readyToCancel = false;
               }

               return false;
            } else if (this.formula != null) {
               return true;
            } else {
               this.showMessage("dererr048");
               this.box.strategyConsistent = false;
               return false;
            }
         }
      } finally {
         if (this.box.showLine != this) {
            this.collectVariables(this.formula);
         }

         this.box.module.setPhase(i);
      }
   }

   void collectVariables(Expression expression) {
      if (this.box.boxVariables == null) {
         this.box.boxVariables = new Vector();
      }

      if (this.box.module.varNames == null) {
         this.box.module.varNames = new Vector();
      }

      if (expression != null) {
         expression.collectTermSymbols(this.box.module.varNames, this.box.boxVariables);
      }
   }

   boolean checkNotAtTopLevel() {
      if (this.annotationEditor != null && this.box.parentBox == null) {
         this.showMessage("dererr051", 4);
         this.box.module.complete = false;
         return false;
      } else {
         return true;
      }
   }

   String encodeJustifications() {
      String s = "";
      if (this.justifications != null) {
         int i = this.justifications.size();
         boolean flag = true;

         for (int j = 0; j < i; j++) {
            Justification justification = (Justification)this.justifications.elementAt(j);
            if (justification != null) {
               flag = false;
            }

            s = s + TaggedRecord.formatField(justification == null ? "" : justification.encode(), ':');
         }

         if (flag) {
            s = "";
         }
      }

      return s;
   }

   @Override
   public String encodeWork() {
      if (this.box.showLine == this) {
         String s2 = TaggedRecord.formatField(this.getFormulaText(false), (char)(this.box.isExpanded() ? '-' : '+'));
         String s1 = this.commandLog == null ? null : LogicProgram.translateSymbols(this.commandLog.getText(), SYMBOLS, rob);
         if (s1 != null && s1.length() != 0) {
            s2 = s2 + TaggedRecord.formatField(s1, 's');
         }

         return s2 + this.encodeJustifications();
      } else {
         String s = LogicProgram.translateSymbols(this.getAnnotationText(false), SYMBOLS, rob);
         return this.box.cancelLine == this
            ? TaggedRecord.formatField(s, '#') + this.encodeJustifications()
            : TaggedRecord.formatField(this.getFormulaText(false), '<') + TaggedRecord.formatField(s, '>') + this.encodeJustifications();
      }
   }

   @Override
   public String encodeMessages() {
      return this.message != null && this.message.isError ? TaggedRecord.formatField(this.getLineNumber() + ":" + this.messageText, 'm') : "";
   }

   @Override
   public void processEvent(AWTEvent awtevent) {
      int i = awtevent.getID();
      if (i == 400) {
         LogicProgram.forwardKeyEvent(this, (KeyEvent)awtevent);
      } else {
         super.processEvent(awtevent);
      }
   }
}
