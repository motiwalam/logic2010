package edu.ucla.phil.logic;

import javax.swing.JScrollPane;

class C_c_F extends JScrollPane {
   LPSymbolizer f1032;

   C_c_F(LPSymbolizer lpsymbolizer) {
      super(20, 31);
      this.getViewport().setBackground(lpsymbolizer.colors[1]);
      this.f1032 = lpsymbolizer;
   }
}
