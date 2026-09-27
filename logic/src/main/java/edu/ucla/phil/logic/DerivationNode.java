package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Hashtable;

interface DerivationNode {
   void setFormulaText(String s);

   String getFormulaText(boolean flag);

   void setAnnotationText(String s);

   String getAnnotationText(boolean flag);

   void setMessageText(String s, boolean flag);

   void showMessage(String s);

   void showMessage(String s, Hashtable hashtable);

   void clearMessage();

   DerivationLineEditor getFormulaEditor();

   DerivationLineEditor getAnnotationEditor();

   int getIndexInBox();

   DerivationBox getEnclosingBox();

   DerivationLine insertLineAfter();

   void deleteNode(boolean flag);

   void moveIntoPreviousBox();

   void moveOutOfBox();

   void focusEditor(boolean flag);

   DerivationNode getNextNode(boolean flag);

   DerivationNode getPreviousNode(boolean flag);

   DerivationNode getHeadNode();

   boolean isShowLine();

   boolean isCancelLine();

   boolean areEnclosingBoxesExpanded();

   void expandEnclosingBoxes();

   int getLineNumber();

   Rectangle getBoundsInProblemPanel();

   DerivationNode findLine(int i);

   int renumberLines(int i);

   void setFont(Font font);

   void layoutColumns();

   void applyColors(Color[] acolor);

   int getMaxBoxDepth(boolean flag);

   int getBoxDepth();

   int countLines(boolean flag);

   void addReferrer(LineReference linereference);

   void removeReferrer(LineReference linereference);

   void detachReferrers();

   void retargetReferrers();

   boolean checkSyntax();

   Expression getFormula();

   void refreshReferenceNumbers();

   boolean verify();

   String encodeWork();

   String encodeMessages();
}
