package edu.ucla.phil.logic;

interface SubstitutionListener {
   ErrorRef validatePlaceholderValue(int i, Expression expression);

   void setPlaceholderValue(int i, String s);
}
