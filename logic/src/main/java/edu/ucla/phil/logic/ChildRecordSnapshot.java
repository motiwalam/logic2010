package edu.ucla.phil.logic;

class ChildRecordSnapshot implements SymbolizationConstants {
   int[] childTypes;
   String[] childRecords;

   ChildRecordSnapshot() {
      this.childTypes = null;
      this.childRecords = null;
   }

   ChildRecordSnapshot(SymbolizationNode symbolizationnode) {
      this.capture(symbolizationnode);
   }

   void capture(SymbolizationNode symbolizationnode) {
      int i = symbolizationnode.argTypes.length;
      this.childTypes = new int[i];
      this.childRecords = new String[i];

      for (int j = 0; j < i; j++) {
         SymbolizationNode symbolizationnode1 = symbolizationnode.getChildNode(j);
         this.childTypes[j] = symbolizationnode1.outType;
         this.childRecords[j] = symbolizationnode1.toRecord(false);
      }
   }

   void restore(SymbolizationNode symbolizationnode) {
      int i = symbolizationnode.argTypes.length;
      int[] aint = symbolizationnode.argTypes;
      if (this.childTypes.length < i) {
         i = this.childTypes.length;
      }

      for (int j = 0; j < i; j++) {
         if (aint[j] == 2 || this.childTypes[j] == 2 || this.childTypes[j] == aint[j]) {
            symbolizationnode.getChildNode(j).loadRecord(new TaggedRecord(this.childRecords[j]), false);
         }
      }
   }
}
