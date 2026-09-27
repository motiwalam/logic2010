package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.Reader;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

class OutlineNode extends CollapsibleNode implements Printable {
   OutlineEntry entry = null;
   FixedColumnLayout columnLayout = null;
   int indent;

   OutlineNode(int i) {
      this.indent = i;
      this.setLayout(new OutlineLayout(i, 0));
   }

   static OutlineNode readOutline(Reader reader, int i) {
      return reader == null ? null : parseOutline(new TaggedRecord(reader), i);
   }

   static OutlineNode parseOutline(TaggedRecord taggedrecord, int i) {
      OutlineNode outlinenode = null;
      OutlineNode outlinenode1 = null;
      int j = taggedrecord.getFieldCount();
      String s = "";

      for (int k = 0; k < j; k++) {
         char c0 = taggedrecord.tagAt(k);
         String s1 = taggedrecord.valueAt(k);
         if (c0 == '$') {
            s = s + s1;
         } else if (c0 == '+' || c0 == '-') {
            OutlineNode outlinenode2 = new OutlineNode(i);
            if (outlinenode == null) {
               outlinenode = outlinenode2;
               outlinenode2.columnLayout = new FixedColumnLayout(outlinenode2, 1);
            } else {
               outlinenode1.add(outlinenode2);
               outlinenode2.columnLayout = outlinenode1.columnLayout;
            }

            outlinenode1 = outlinenode2;
            outlinenode2.setHeader(outlinenode2.entry = new OutlineEntry(outlinenode2));
            outlinenode2.entry.titleLabel.setName(LogicProgram.expandEscapes(s1));
            outlinenode2.entry.textArea.setText(LogicProgram.expandEscapes(s));
            outlinenode2.setExpanded(c0 == '-');
            s = "";
         } else {
            Container container;
            if (c0 == '=' && outlinenode1 != null && (container = outlinenode1.getParent()) instanceof OutlineNode) {
               outlinenode1 = (OutlineNode)container;
            }
         }
      }

      return outlinenode;
   }

   @Override
   public boolean action(Event event, Object object) {
      boolean flag = super.action(event, object);
      if (this.isExpanded()) {
         this.invalidate();
         this.validate();
      }

      return flag;
   }

   void setWidth(int i) {
      this.columnLayout.setColumnWidth(0, i);
   }

   Vector getAllNodes() {
      Vector vector = new Vector();
      this.collectNodes(vector);
      return vector;
   }

   void collectNodes(Vector vector) {
      vector.addElement(this);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof OutlineNode) {
            ((OutlineNode)component).collectNodes(vector);
         }
      }
   }

   @Override
   public int print(Graphics graphics, PageFormat pageformat, int i) {
      if (i > 1) {
         return 1;
      } else {
         Graphics2D graphics2d = (Graphics2D)graphics;
         graphics2d.translate(pageformat.getImageableX(), pageformat.getImageableY());
         byte b0 = 72;
         Dimension dimension = new Dimension((int)pageformat.getImageableWidth(), (int)pageformat.getImageableHeight());
         this.expandAll();
         this.setWidth(dimension.width);
         Frame frame = new Frame("This is necessary because AFC is offscreen challenged");
         frame.setFont(LogicProgram.getFont(b0 * 10 / 72));
         frame.setForeground(Color.black);
         frame.setBackground(Color.white);
         frame.setLocation(0, -1024);
         frame.setVisible(true);
         frame.setVisible(false);
         PrintPageHeader printpageheader = new PrintPageHeader();
         printpageheader.setLayout(new VerticalStackLayout());
         frame.add(this);
         frame.add(printpageheader);
         printpageheader.add(printpageheader.pageLabel = new LogicLabel("page 1 of 1"));
         printpageheader.add(new LogicLabel(" "));
         this.invalidate();
         this.validate();
         printpageheader.invalidate();
         printpageheader.validate();
         int j = printpageheader.getBounds().height;
         int k = dimension.height - j;
         Vector vector = this.getAllNodes();
         Vector vector1 = new Vector();
         int l = vector.size();
         int i1 = 0;
         int j1 = 0;

         for (int k1 = 0; k1 < l; k1++) {
            OutlineNode outlinenode1 = (OutlineNode)vector.elementAt(k1);
            Rectangle rectangle = LogicProgram.boundsRelativeTo(outlinenode1.entry, this);
            int l1 = rectangle.y + rectangle.height;

            while (l1 > i1 + k) {
               int i2 = j1 > i1 ? j1 - i1 : k;
               vector1.addElement(new Rectangle(0, i1, dimension.width, i2));
               i1 += i2;
            }

            j1 = l1;
         }

         if (j1 > i1) {
            vector1.addElement(new Rectangle(0, i1, dimension.width, j1 - i1));
         }

         l = vector1.size();

         for (int j2 = 1; j2 <= l; j2++) {
            if (LogicProgram.printingEnabled) {
               printpageheader.pageLabel.setName("Page " + j2 + " of " + l);
               printpageheader.invalidate();
               graphics2d.setClip(0, 0, dimension.width, j);
               RepaintManager.currentManager((JComponent)this).setDoubleBufferingEnabled(false);
               printpageheader.paint(graphics2d);
               Rectangle rectangle1 = (Rectangle)vector1.elementAt(j2 - 1);
               graphics2d.translate(0, j - rectangle1.y);
               graphics2d.setClip(rectangle1);
               this.paint(graphics2d);
               RepaintManager.currentManager((JComponent)this).setDoubleBufferingEnabled(true);
            } else if (LogicProgram.debug) {
               System.out.println("Page number:     " + j2);
               System.out.println("Page dimensions: " + dimension.width + "x" + dimension.height);
               System.out.println("Page resolution: " + b0 + " dpi");
               System.out.println("Page size:       " + (float)dimension.width / b0 + "x" + (float)dimension.height / b0);
            }
         }

         frame.dispose();
         return LogicProgram.printingEnabled ? 0 : 1;
      }
   }

   void expandAll() {
      this.setExpanded(true);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof OutlineNode) {
            ((OutlineNode)component).expandAll();
         }
      }
   }
}
