package edu.ucla.phil.logic;

import java.awt.print.Printable;

class TextFilePrintJob extends PrintTask {
   TaggedRecord record;

   TextFilePrintJob(TaggedRecord taggedrecord, PrintQueue printqueue) {
      super(printqueue);
      this.record = taggedrecord;
   }

   @Override
   Printable getPrintable() {
      return OutlineNode.parseOutline(this.record, 20);
   }

   static void printRecord(TaggedRecord taggedrecord, PrintQueue printqueue) {
      if (taggedrecord != null) {
         new TextFilePrintJob(taggedrecord, printqueue).schedule();
      }
   }
}
