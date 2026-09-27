package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.event.WindowEvent;

class DerivationKeypad extends KeypadDialog {
   DerivationLineEditor editor;
   boolean unusedFlag;

   DerivationKeypad(DerivationLineEditor derivationlineeditor) {
      super(derivationlineeditor.line.box.module.frame, getTitleFor(derivationlineeditor), false, derivationlineeditor);
      this.editor = derivationlineeditor;
      this.unusedFlag = false;
      this.setLayout(new VerticalStackLayout());
   }

   static String getTitleFor(DerivationLineEditor derivationlineeditor) {
      int i = derivationlineeditor.line.getLineNumber();
      return i == 0 ? "Problem" : "Line " + i;
   }

   @Override
   public void windowLostFocus(WindowEvent windowevent) {
      Object object = windowevent.getSource();
      if (!this.disposed && !LogicProgram.isDescendant(this, (Component)object)) {
         if (this.editor == this.editor.line.formulaEditor) {
            this.editor.line.parseFormula();
         } else if (this.editor == this.editor.line.annotationEditor) {
            this.editor.line.parseReferences();
         }
      }

      super.windowLostFocus(windowevent);
   }

   @Override
   public String translateKey(String s) {
      DerivationLine derivationline = this.editor.line;
      boolean flag = derivationline.annotationEditor == this.editor;
      if (s == null) {
         return null;
      } else {
         String s1 = LogicProgram.translateSymbols(s, new String[]{"\n"}, new String[]{" "});
         if (s1.equals("backspace")) {
            s1 = "\b";
         } else if (s1.equals("space")) {
            s1 = " ";
         } else if (s1.equals("tab")) {
            if (derivationline.formulaEditor != null && derivationline.annotationEditor != null && derivationline.applyShowPrefix() == null) {
               derivationline.box.module.requestAid = true;
               derivationline.focusEditor(!flag);
            }

            s1 = null;
         } else if (s1.equals("enter")) {
            if (!flag) {
               derivationline.applyShowPrefix();
            }

            if (derivationline.box.module.commandMode) {
               if (derivationline.box.module.focus != derivationline.annotationEditor) {
                  derivationline.parseReferences();
               }

               derivationline.commitEdit(derivationline.formulaEditor != null);
            }

            DerivationLine derivationline1 = derivationline.insertLineAfter();
            if (derivationline1 != null) {
               derivationline.box.module.requestAid = true;
               derivationline1.focusEditor(flag);
            }

            s1 = null;
         } else if (s1.equals("up")) {
            DerivationNode derivationnode = derivationline.getPreviousNode(true);
            if (derivationnode != null) {
               derivationline.box.module.requestAid = true;
               derivationnode.focusEditor(flag);
            }

            s1 = null;
         } else if (s1.equals("down")) {
            DerivationNode derivationnode1 = derivationline.getNextNode(true);
            if (derivationnode1 != null) {
               derivationline.box.module.requestAid = true;
               derivationnode1.focusEditor(flag);
            }

            s1 = null;
         } else if (s1.equals("in")) {
            this.editor.line.indentIntoOpenBox();
            s1 = null;
         } else if (s1.equals("out")) {
            this.editor.line.outdentFollowingLines();
            s1 = null;
         } else if (s1.equals("delete line")) {
            if (derivationline.box.module.problem.showLine != derivationline) {
               DerivationNode derivationnode2 = derivationline.getNextNode(false);
               if (derivationnode2 == null) {
                  derivationnode2 = derivationline.getPreviousNode(true);
               }

               derivationline.deleteNode(false);
               derivationline.box.module.requestAid = true;
               derivationnode2.focusEditor(flag);
            }

            s1 = null;
         } else if (s1.equals("Show/Unshow")) {
            if (!flag) {
               DerivationNode derivationnode3 = derivationline.toggleShow();
               if (derivationnode3 != null) {
                  this.editor = derivationnode3.getFormulaEditor();
               }

               s1 = null;
            }
         } else if (s1.equals("Box/Unbox")) {
            if (flag) {
               derivationline.toggleBoxAndCancel();
               s1 = null;
            }
         } else if (s1.equals("Show Unneg")) {
            s1 = s1 + " ";
         } else if (s1.equals("Show Ant")) {
            s1 = s1 + " ";
         } else if (s1.equals("Show NegCons")) {
            s1 = s1 + " ";
         } else if (s1.equals("Show NegDisj")) {
            s1 = s1 + " ";
         } else if (s1.startsWith("Show ")) {
            String s2 = derivationline.getFormulaText(false);
            if (flag && s2 != null && s2.equals("") && this.editor.getText().equals("")) {
               derivationline.setAnnotationText(s1);
               if (derivationline.box.module.commandMode) {
                  derivationline.commitEdit(derivationline.formulaEditor != null);
               }

               DerivationLine derivationline2 = derivationline.insertLineAfter();
               if (derivationline2 != null) {
                  derivationline.box.module.requestAid = true;
                  derivationline2.focusEditor(flag);
               }

               s1 = null;
            }
         }

         return super.translateKey(s1);
      }
   }

   @Override
   public int getTargetSelectionStart() {
      return this.editor.savedSelectionStart;
   }

   @Override
   public int getTargetSelectionEnd() {
      return this.editor.savedSelectionEnd;
   }

   @Override
   public int getTargetCaret() {
      return this.editor.savedCaretPosition;
   }

   @Override
   public void selectInTarget(int i, int j) {
      this.editor.savedSelectionStart = i;
      this.editor.savedSelectionEnd = j;
   }

   @Override
   public void setTargetCaret(int i) {
      this.editor.savedCaretPosition = i;
   }

   @Override
   public void insertIntoTarget(String s, int i) {
      this.editor.insertText(s, i);
   }
}
