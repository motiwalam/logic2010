package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.Vector;
import javax.swing.Box;

abstract class PrintPage extends PrintTask implements Printable {
   int[] problemIndices;
   PrintTask.PrintPaginator paginator = null;
   Component separator = null;

   PrintPage(PrintQueue printqueue, int[] aint) {
      super(printqueue);
      this.problemIndices = aint;
      this.separator = Box.createVerticalStrut(LogicProgram.fontSize);
   }

   @Override
   Printable getPrintable() {
      return this;
   }

   @Override
   public int print(Graphics graphics, PageFormat pageformat, int i) {
      if (this.paginator == null) {
         Vector vector = this.getPrintComponents(this.getPageSize(pageformat));
         if (vector == null) {
            return 1;
         }

         PrintPageHeader printpageheader = null;
         if (LogicProgram.user != null) {
            printpageheader = PrintPageHeader.createForCurrentUser();
         }

         this.paginator = new PrintTask.PrintPaginator(pageformat, printpageheader, vector, this.separator);
      }

      return this.paginator.printPage(graphics, pageformat, i);
   }

   abstract Vector getPrintComponents(Dimension dimension);

   void setSeparator(Component component) {
      this.separator = component;
   }
}
