package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

abstract class PrintTask implements Runnable {
   static Properties printProperties = new Properties();
   static final int BASE_FONT_SIZE = 10;
   static double scale = 10.0 / LogicProgram.fontSize;
   static int minMargin = 36;
   PrintQueue queue;
   boolean cancelled = false;

   PrintTask(PrintQueue printqueue) {
      this.queue = printqueue;
   }

   @Override
   public void run() {
      synchronized (this) {
         if (this.cancelled) {
            return;
         }
      }

      if (this.queue != null) {
         synchronized (this.queue) {
            this.queue.addElement(this);
         }
      }

      try {
         PrinterJob printerjob = PrinterJob.getPrinterJob();
         if (printerjob != null && printerjob.printDialog()) {
            try {
               printerjob.setPrintable(this.getPrintable());
               printerjob.print();
            } catch (PrinterException printerexception) {
               System.out.println("Error printing: " + printerexception);
            }
         }
      } finally {
         if (this.queue != null) {
            synchronized (this.queue) {
               this.queue.removeElement(this);
               if (this.queue.isEmpty()) {
                  this.queue.fireQueueChanged();
               }
            }
         }
      }
   }

   public synchronized void cancel() {
      this.cancelled = true;
   }

   public void schedule() {
      SwingUtilities.invokeLater(this);
   }

   static void waitForQueue(PrintQueue printqueue) {
      if (printqueue != null && !printqueue.isEmpty()) {
         LogicLabel logiclabel = new LogicLabel("Please wait for printing to finish.");
         PrintAbortHandler printaborthandler = new PrintAbortHandler("Abort:abort");
         printaborthandler.setProperty("queue", printqueue);
         PrintWaitDialog printwaitdialog = new PrintWaitDialog(new Frame(), printqueue.getTitle(), logiclabel, printaborthandler.getLabels());
         printwaitdialog.addHandler(printaborthandler);
         printqueue.addQueueListener(printwaitdialog);
         printwaitdialog.showAt(null);
         printqueue.removeQueueListener(printwaitdialog);
      }
   }

   static void abortQueue(PrintQueue printqueue) {
      if (printqueue != null) {
         synchronized (printqueue) {
            while (!printqueue.isEmpty()) {
               ((PrintTask)printqueue.elementAt(0)).cancel();
               printqueue.removeElementAt(0);
            }

            printqueue.fireQueueChanged();
         }
      }
   }

   abstract Printable getPrintable();

   public Dimension getPageSize(PageFormat pageformat) {
      Dimension dimension = new Dimension((int)pageformat.getImageableWidth(), (int)pageformat.getImageableHeight());
      dimension.width = (int)(dimension.width - Math.max(minMargin - pageformat.getImageableX(), 0.0));
      dimension.width = (int)(
         dimension.width - Math.max(minMargin - (pageformat.getWidth() - pageformat.getImageableWidth() - pageformat.getImageableX()), 0.0)
      );
      dimension.width = (int)(dimension.width / scale);
      dimension.height = (int)(dimension.height - Math.max(minMargin - pageformat.getImageableY(), 0.0));
      dimension.height = (int)(
         dimension.height - Math.max(minMargin - (pageformat.getHeight() - pageformat.getImageableHeight() - pageformat.getImageableY()), 0.0)
      );
      dimension.height = (int)(dimension.height / scale);
      return dimension;
   }

   class PrintPaginator {
      Dimension pageSize;
      int headerHeight = 0;
      PrintPageHeader header = null;
      int separatorHeight = 0;
      Component separator = null;
      Vector<Boolean> itemEnds = null;
      PrintTask.PrintPaginator.PrintItemSize[] itemSizes = null;
      ArrayList<PrintTask.PrintPaginator.PrintPageSpan> pages = null;
      JFrame frame;
      JPanel contentPanel = null;

      PrintPaginator(PageFormat pageformat, PrintPageHeader printpageheader, Vector vector, Component component) {
         this.pageSize = PrintTask.this.getPageSize(pageformat);
         this.frame = new JFrame();
         this.frame.setBackground(LogicProgram.printColors[1]);
         this.header = printpageheader;
         if (this.header != null) {
            this.header.setLimitWidth(this.pageSize.width);
            this.header.setBounds(0, 0, this.pageSize.width, this.pageSize.height);
            this.header.doLayout();
            this.headerHeight = this.header.getPreferredSize().height;
            this.header.setBounds(0, 0, this.pageSize.width, this.headerHeight);
            this.frame.add(this.header);
         }

         this.separator = component;
         if (this.separator != null) {
            this.separator.setBounds(0, 0, this.pageSize.width, this.pageSize.height);
            this.separator.doLayout();
            this.separatorHeight = this.separator.getPreferredSize().height;
            this.separator.setBounds(0, 0, this.pageSize.width, this.separatorHeight);
            this.frame.add(this.separator);
         }

         int i = this.pageSize.height - this.headerHeight;
         this.contentPanel = new JPanel();
         this.contentPanel.setBackground(LogicProgram.printColors[1]);
         this.contentPanel.setBounds(0, 0, this.pageSize.width, 0);
         this.contentPanel.setLayout(new VerticalStackLayout());
         this.frame.add(this.contentPanel);
         if (vector != null && vector.size() != 0) {
            this.itemEnds = new Vector<>(vector.size());

            for (int j = 0; j < vector.size(); j++) {
               Object object = vector.get(j);
               if (object instanceof Vector) {
                  Vector vector1 = (Vector)object;

                  for (int k = 0; k < vector1.size(); k++) {
                     Component component2 = (Component)vector1.get(k);
                     this.contentPanel.add(component2);
                     component2.doLayout();
                     this.itemEnds.add(new Boolean(k == vector1.size() - 1));
                  }
               } else {
                  Component component1 = (Component)object;
                  this.contentPanel.add(component1);
                  component1.doLayout();
                  this.itemEnds.add(new Boolean(true));
               }
            }

            this.frame.pack();
            this.frame.dispose();
            int j1 = this.contentPanel.getComponentCount();
            this.itemSizes = new PrintTask.PrintPaginator.PrintItemSize[j1];
            this.pages = new ArrayList<>(j1 / 10 + 2);
            PrintTask.PrintPaginator.PrintPageSpan printtask$printpaginator$printpagespan = new PrintTask.PrintPaginator.PrintPageSpan(0, -1, 0);
            int k1 = 0;
            int l1 = 0;
            int i2 = -1;
            boolean flag = true;

            while (l1 < j1) {
               if (k1 > 0 && i2 == l1 - 1) {
                  k1 += this.separatorHeight;
               }

               Component component3 = this.contentPanel.getComponent(l1);
               Rectangle rectangle = component3.getBounds();
               double d0 = 1.0;
               if (rectangle.width > this.pageSize.width) {
                  d0 = (double)this.pageSize.width / rectangle.width;
               }

               this.itemSizes[l1] = new PrintTask.PrintPaginator.PrintItemSize(rectangle.height, d0);
               int l = rectangle.height;
               if (!flag && k1 + l * d0 > i) {
                  printtask$printpaginator$printpagespan.lastIndex = i2;
                  this.pages.add(printtask$printpaginator$printpagespan);
                  printtask$printpaginator$printpagespan = new PrintTask.PrintPaginator.PrintPageSpan(i2 + 1, -1, 0);
                  k1 = 0;
                  flag = true;
                  l1 = i2 + 1;
               } else {
                  int i1;
                  for (i1 = 0; k1 + (l - i1) * d0 > i; k1 = 0) {
                     if (k1 > 0) {
                        printtask$printpaginator$printpagespan.lastIndex = l1 - 1;
                        boolean flag1 = false;
                     } else {
                        printtask$printpaginator$printpagespan.lastIndex = l1;
                        i1 = (int)(i1 + (i - k1) / d0);
                     }

                     this.pages.add(printtask$printpaginator$printpagespan);
                     printtask$printpaginator$printpagespan = new PrintTask.PrintPaginator.PrintPageSpan(l1, -1, i1);
                  }

                  k1 = (int)(k1 + (l - i1) * d0);
                  if (this.itemEnds.get(l1)) {
                     if (k1 > 0) {
                        flag = false;
                     }

                     i2 = l1;
                  }

                  l1++;
               }
            }

            printtask$printpaginator$printpagespan.lastIndex = j1 - 1;
            this.pages.add(printtask$printpaginator$printpagespan);
         }
      }

      int printPage(Graphics graphics, PageFormat pageformat, int i) {
         if (this.pages != null && i < this.pages.size()) {
            PrintTask.PrintPaginator.PrintPageSpan printtask$printpaginator$printpagespan = this.pages.get(i);
            Graphics2D graphics2d = (Graphics2D)graphics;
            graphics2d.translate(
               Math.max(pageformat.getImageableX(), (double)PrintTask.minMargin), Math.max(pageformat.getImageableY(), (double)PrintTask.minMargin)
            );
            graphics2d.scale(PrintTask.scale, PrintTask.scale);
            int j = this.pageSize.height;
            if (this.header != null) {
               this.header.pageLabel.setText("Page " + (i + 1) + " of " + this.pages.size());
               this.header.pageLabel.doLayout();
               graphics2d.setClip(0, 0, this.pageSize.width, this.headerHeight);
               RepaintManager.currentManager((JComponent)this.header).setDoubleBufferingEnabled(false);
               this.header.print(graphics2d);
               RepaintManager.currentManager((JComponent)this.header).setDoubleBufferingEnabled(true);
               graphics2d.translate(0, this.headerHeight);
               j -= this.headerHeight;
            }

            for (int k = printtask$printpaginator$printpagespan.firstIndex; k <= printtask$printpaginator$printpagespan.lastIndex; k++) {
               AffineTransform affinetransform = graphics2d.getTransform();
               PrintTask.PrintPaginator.PrintItemSize printtask$printpaginator$printitemsize = this.itemSizes[k];
               Component component = this.contentPanel.getComponent(k);
               graphics2d.scale(printtask$printpaginator$printitemsize.scale, printtask$printpaginator$printitemsize.scale);
               int l = (int)(j / printtask$printpaginator$printitemsize.scale);
               if (k == printtask$printpaginator$printpagespan.firstIndex) {
                  graphics2d.translate(0, -printtask$printpaginator$printpagespan.firstOffset);
               }

               graphics2d.setClip(
                  0,
                  k == printtask$printpaginator$printpagespan.firstIndex ? printtask$printpaginator$printpagespan.firstOffset : 0,
                  (int)(this.pageSize.width / printtask$printpaginator$printitemsize.scale),
                  Math.min(
                     printtask$printpaginator$printitemsize.height
                        - (k == printtask$printpaginator$printpagespan.firstIndex ? printtask$printpaginator$printpagespan.firstOffset : 0),
                     l
                  )
               );
               RepaintManager.currentManager(component).setDoubleBufferingEnabled(false);
               component.print(graphics2d);
               RepaintManager.currentManager(component).setDoubleBufferingEnabled(true);
               graphics2d.setTransform(affinetransform);
               graphics2d.translate(
                  0.0,
                  printtask$printpaginator$printitemsize.scale
                     * (
                        printtask$printpaginator$printitemsize.height
                           - (k == printtask$printpaginator$printpagespan.firstIndex ? printtask$printpaginator$printpagespan.firstOffset : 0)
                     )
               );
               j = (int)(
                  j
                     - printtask$printpaginator$printitemsize.scale
                        * (
                           printtask$printpaginator$printitemsize.height
                              - (k == printtask$printpaginator$printpagespan.firstIndex ? printtask$printpaginator$printpagespan.firstOffset : 0)
                        )
               );
               if (this.separator != null && k != printtask$printpaginator$printpagespan.lastIndex && this.itemEnds.get(k)) {
                  graphics2d.setClip(0, 0, this.pageSize.width, this.separatorHeight);
                  RepaintManager.currentManager(this.separator).setDoubleBufferingEnabled(false);
                  this.separator.print(graphics2d);
                  RepaintManager.currentManager(this.separator).setDoubleBufferingEnabled(true);
                  graphics2d.translate(0, this.separatorHeight);
                  j -= this.separatorHeight;
               }
            }

            return 0;
         } else {
            return 1;
         }
      }

      class PrintItemSize {
         int height;
         double scale;

         PrintItemSize(int i, double d0) {
            this.height = i;
            this.scale = d0;
         }
      }

      class PrintPageSpan {
         int firstIndex;
         int lastIndex;
         int firstOffset;

         PrintPageSpan(int i, int j, int k) {
            this.firstIndex = i;
            this.lastIndex = j;
            this.firstOffset = k;
         }
      }
   }
}
