package edu.ucla.phil.logic;

interface KeypadTarget {
   String translateKey(String s);

   String getTargetText();

   int getTargetSelectionStart();

   int getTargetSelectionEnd();

   int getTargetCaret();

   void selectInTarget(int i, int j);

   void setTargetCaret(int i);

   void insertIntoTarget(String s, int i);

   void deleteFromTarget(int i, int j);

   void invalidate();

   boolean isTargetReadOnly();
}
