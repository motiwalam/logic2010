package edu.ucla.phil.logic;

class LineReference {
   int offset;
   int length;
   DerivationNode target;
   DerivationLine source;

   LineReference(int i, int j, DerivationNode derivationnode, DerivationLine derivationline) {
      this.offset = i;
      this.length = j;
      this.target = derivationnode;
      this.source = derivationline;
   }
}
