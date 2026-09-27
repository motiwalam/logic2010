package edu.ucla.phil.logic;

import javax.swing.JScrollPane;

class SymbolizationScrollPane extends JScrollPane {
   LPSymbolizer module;

   SymbolizationScrollPane(LPSymbolizer lpsymbolizer) {
      super(20, 31);
      this.getViewport().setBackground(lpsymbolizer.colors[1]);
      this.module = lpsymbolizer;
   }
}
