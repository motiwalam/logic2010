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

class C_JE extends C_v_A implements Printable {
   C_t_ f436 = null;
   C_u_ f437 = null;
   int f438;

   C_JE(int i) {
      this.f438 = i;
      this.setLayout(new C_g_(i, 0));
   }

   static C_JE m726(Reader reader, int i) {
      return reader == null ? null : m727(new C_XD(reader), i);
   }

   static C_JE m727(C_XD c_xd, int i) {
      C_JE c_je = null;
      C_JE c_je1 = null;
      int j = c_xd.m1482();
      String s = "";

      for (int k = 0; k < j; k++) {
         char c0 = c_xd.m1474(k);
         String s1 = c_xd.m1483(k);
         if (c0 == '$') {
            s = s + s1;
         } else if (c0 == '+' || c0 == '-') {
            C_JE c_je2 = new C_JE(i);
            if (c_je == null) {
               c_je = c_je2;
               c_je2.f437 = new C_u_(c_je2, 1);
            } else {
               c_je1.add(c_je2);
               c_je2.f437 = c_je1.f437;
            }

            c_je1 = c_je2;
            c_je2.m2125(c_je2.f436 = new C_t_(c_je2));
            c_je2.f436.f1371.setName(LogicProgram.m1004(s1));
            c_je2.f436.f1372.setText(LogicProgram.m1004(s));
            c_je2.m2123(c0 == '-');
            s = "";
         } else {
            Container container;
            if (c0 == '=' && c_je1 != null && (container = c_je1.getParent()) instanceof C_JE) {
               c_je1 = (C_JE)container;
            }
         }
      }

      return c_je;
   }

   @Override
   public boolean action(Event event, Object object) {
      boolean flag = super.action(event, object);
      if (this.m2124()) {
         this.invalidate();
         this.validate();
      }

      return flag;
   }

   void m728(int i) {
      this.f437.m2093(0, i);
   }

   Vector m729() {
      Vector vector = new Vector();
      this.m730(vector);
      return vector;
   }

   void m730(Vector vector) {
      vector.addElement(this);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof C_JE) {
            ((C_JE)component).m730(vector);
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
         this.m731();
         this.m728(dimension.width);
         Frame frame = new Frame("This is necessary because AFC is offscreen challenged");
         frame.setFont(LogicProgram.m1029(b0 * 10 / 72));
         frame.setForeground(Color.black);
         frame.setBackground(Color.white);
         frame.setLocation(0, -1024);
         frame.setVisible(true);
         frame.setVisible(false);
         C_t_B c_t_b = new C_t_B();
         c_t_b.setLayout(new C_m_A());
         frame.add(this);
         frame.add(c_t_b);
         c_t_b.add(c_t_b.f1374 = new C_ZE("page 1 of 1"));
         c_t_b.add(new C_ZE(" "));
         this.invalidate();
         this.validate();
         c_t_b.invalidate();
         c_t_b.validate();
         int j = c_t_b.getBounds().height;
         int k = dimension.height - j;
         Vector vector = this.m729();
         Vector vector1 = new Vector();
         int l = vector.size();
         int i1 = 0;
         int j1 = 0;

         for (int k1 = 0; k1 < l; k1++) {
            C_JE c_je1 = (C_JE)vector.elementAt(k1);
            Rectangle rectangle = LogicProgram.m1035(c_je1.f436, this);
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
            if (LogicProgram.f573) {
               c_t_b.f1374.setName("Page " + j2 + " of " + l);
               c_t_b.invalidate();
               graphics2d.setClip(0, 0, dimension.width, j);
               RepaintManager.currentManager((JComponent)this).setDoubleBufferingEnabled(false);
               c_t_b.paint(graphics2d);
               Rectangle rectangle1 = (Rectangle)vector1.elementAt(j2 - 1);
               graphics2d.translate(0, j - rectangle1.y);
               graphics2d.setClip(rectangle1);
               this.paint(graphics2d);
               RepaintManager.currentManager((JComponent)this).setDoubleBufferingEnabled(true);
            } else if (LogicProgram.f572) {
               System.out.println("Page number:     " + j2);
               System.out.println("Page dimensions: " + dimension.width + "x" + dimension.height);
               System.out.println("Page resolution: " + b0 + " dpi");
               System.out.println("Page size:       " + (float)dimension.width / b0 + "x" + (float)dimension.height / b0);
            }
         }

         frame.dispose();
         return LogicProgram.f573 ? 0 : 1;
      }
   }

   void m731() {
      this.m2123(true);
      int i = this.getComponentCount();

      for (int j = 0; j < i; j++) {
         Component component = this.getComponent(j);
         if (component instanceof C_JE) {
            ((C_JE)component).m731();
         }
      }
   }
}
