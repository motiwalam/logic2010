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

abstract class C_l_B implements Runnable {
   static Properties f1230 = new Properties();
   static final int f1231 = 10;
   static double f1232 = 10.0 / LogicProgram.f539;
   static int f1233 = 36;
   C_c_C f1234;
   boolean f1235 = false;

   C_l_B(C_c_C c_c_c) {
      this.f1234 = c_c_c;
   }

   @Override
   public void run() {
      synchronized (this) {
         if (this.f1235) {
            return;
         }
      }

      if (this.f1234 != null) {
         synchronized (this.f1234) {
            this.f1234.addElement(this);
         }
      }

      try {
         PrinterJob printerjob = PrinterJob.getPrinterJob();
         if (printerjob != null && printerjob.printDialog()) {
            try {
               printerjob.setPrintable(this.m413());
               printerjob.print();
            } catch (PrinterException printerexception) {
               System.out.println("Error printing: " + printerexception);
            }
         }
      } finally {
         if (this.f1234 != null) {
            synchronized (this.f1234) {
               this.f1234.removeElement(this);
               if (this.f1234.isEmpty()) {
                  this.f1234.m1670();
               }
            }
         }
      }
   }

   public synchronized void m1922() {
      this.f1235 = true;
   }

   public void m1923() {
      SwingUtilities.invokeLater(this);
   }

   static void m1924(C_c_C c_c_c) {
      if (c_c_c != null && !c_c_c.isEmpty()) {
         C_ZE c_ze = new C_ZE("Please wait for printing to finish.");
         C_ZB c_zb = new C_ZB("Abort:abort");
         c_zb.m447("queue", c_c_c);
         C_AC c_ac = new C_AC(new Frame(), c_c_c.m1667(), c_ze, c_zb.m445());
         c_ac.m1315(c_zb);
         c_c_c.m1668(c_ac);
         c_ac.m1322(null);
         c_c_c.m1669(c_ac);
      }
   }

   static void m1925(C_c_C c_c_c) {
      if (c_c_c != null) {
         synchronized (c_c_c) {
            while (!c_c_c.isEmpty()) {
               ((C_l_B)c_c_c.elementAt(0)).m1922();
               c_c_c.removeElementAt(0);
            }

            c_c_c.m1670();
         }
      }
   }

   abstract Printable m413();

   public Dimension m1926(PageFormat pageformat) {
      Dimension dimension = new Dimension((int)pageformat.getImageableWidth(), (int)pageformat.getImageableHeight());
      dimension.width = (int)(dimension.width - Math.max(f1233 - pageformat.getImageableX(), 0.0));
      dimension.width = (int)(dimension.width - Math.max(f1233 - (pageformat.getWidth() - pageformat.getImageableWidth() - pageformat.getImageableX()), 0.0));
      dimension.width = (int)(dimension.width / f1232);
      dimension.height = (int)(dimension.height - Math.max(f1233 - pageformat.getImageableY(), 0.0));
      dimension.height = (int)(
         dimension.height - Math.max(f1233 - (pageformat.getHeight() - pageformat.getImageableHeight() - pageformat.getImageableY()), 0.0)
      );
      dimension.height = (int)(dimension.height / f1232);
      return dimension;
   }

   class C__A {
      Dimension f1236;
      int f1237 = 0;
      C_t_B f1238 = null;
      int f1239 = 0;
      Component f1240 = null;
      Vector<Boolean> f1241 = null;
      C_l_B.C__A.C2__B[] f1242 = null;
      ArrayList<C_l_B.C__A.C2__A> f1243 = null;
      JFrame f1244;
      JPanel f1245 = null;

      C__A(PageFormat pageformat, C_t_B c_t_b, Vector vector, Component component) {
         this.f1236 = C_l_B.this.m1926(pageformat);
         this.f1244 = new JFrame();
         this.f1244.setBackground(LogicProgram.f605[1]);
         this.f1238 = c_t_b;
         if (this.f1238 != null) {
            this.f1238.m934(this.f1236.width);
            this.f1238.setBounds(0, 0, this.f1236.width, this.f1236.height);
            this.f1238.doLayout();
            this.f1237 = this.f1238.getPreferredSize().height;
            this.f1238.setBounds(0, 0, this.f1236.width, this.f1237);
            this.f1244.add(this.f1238);
         }

         this.f1240 = component;
         if (this.f1240 != null) {
            this.f1240.setBounds(0, 0, this.f1236.width, this.f1236.height);
            this.f1240.doLayout();
            this.f1239 = this.f1240.getPreferredSize().height;
            this.f1240.setBounds(0, 0, this.f1236.width, this.f1239);
            this.f1244.add(this.f1240);
         }

         int i = this.f1236.height - this.f1237;
         this.f1245 = new JPanel();
         this.f1245.setBackground(LogicProgram.f605[1]);
         this.f1245.setBounds(0, 0, this.f1236.width, 0);
         this.f1245.setLayout(new C_m_A());
         this.f1244.add(this.f1245);
         if (vector != null && vector.size() != 0) {
            this.f1241 = new Vector<>(vector.size());

            for (int j = 0; j < vector.size(); j++) {
               Object object = vector.get(j);
               if (object instanceof Vector) {
                  Vector vector1 = (Vector)object;

                  for (int k = 0; k < vector1.size(); k++) {
                     Component component2 = (Component)vector1.get(k);
                     this.f1245.add(component2);
                     component2.doLayout();
                     this.f1241.add(new Boolean(k == vector1.size() - 1));
                  }
               } else {
                  Component component1 = (Component)object;
                  this.f1245.add(component1);
                  component1.doLayout();
                  this.f1241.add(new Boolean(true));
               }
            }

            this.f1244.pack();
            this.f1244.dispose();
            int j1 = this.f1245.getComponentCount();
            this.f1242 = new C_l_B.C__A.C2__B[j1];
            this.f1243 = new ArrayList<>(j1 / 10 + 2);
            C_l_B.C__A.C2__A c_l_b$c__a$c2__a = new C_l_B.C__A.C2__A(0, -1, 0);
            int k1 = 0;
            int l1 = 0;
            int i2 = -1;
            boolean flag = true;

            while (l1 < j1) {
               if (k1 > 0 && i2 == l1 - 1) {
                  k1 += this.f1239;
               }

               Component component3 = this.f1245.getComponent(l1);
               Rectangle rectangle = component3.getBounds();
               double d0 = 1.0;
               if (rectangle.width > this.f1236.width) {
                  d0 = (double)this.f1236.width / rectangle.width;
               }

               this.f1242[l1] = new C_l_B.C__A.C2__B(rectangle.height, d0);
               int l = rectangle.height;
               if (!flag && k1 + l * d0 > i) {
                  c_l_b$c__a$c2__a.f1248 = i2;
                  this.f1243.add(c_l_b$c__a$c2__a);
                  c_l_b$c__a$c2__a = new C_l_B.C__A.C2__A(i2 + 1, -1, 0);
                  k1 = 0;
                  flag = true;
                  l1 = i2 + 1;
               } else {
                  int i1;
                  for (i1 = 0; k1 + (l - i1) * d0 > i; k1 = 0) {
                     if (k1 > 0) {
                        c_l_b$c__a$c2__a.f1248 = l1 - 1;
                        boolean flag1 = false;
                     } else {
                        c_l_b$c__a$c2__a.f1248 = l1;
                        i1 = (int)(i1 + (i - k1) / d0);
                     }

                     this.f1243.add(c_l_b$c__a$c2__a);
                     c_l_b$c__a$c2__a = new C_l_B.C__A.C2__A(l1, -1, i1);
                  }

                  k1 = (int)(k1 + (l - i1) * d0);
                  if (this.f1241.get(l1)) {
                     if (k1 > 0) {
                        flag = false;
                     }

                     i2 = l1;
                  }

                  l1++;
               }
            }

            c_l_b$c__a$c2__a.f1248 = j1 - 1;
            this.f1243.add(c_l_b$c__a$c2__a);
         }
      }

      int m1927(Graphics graphics, PageFormat pageformat, int i) {
         if (this.f1243 != null && i < this.f1243.size()) {
            C_l_B.C__A.C2__A c_l_b$c__a$c2__a = this.f1243.get(i);
            Graphics2D graphics2d = (Graphics2D)graphics;
            graphics2d.translate(Math.max(pageformat.getImageableX(), (double)C_l_B.f1233), Math.max(pageformat.getImageableY(), (double)C_l_B.f1233));
            graphics2d.scale(C_l_B.f1232, C_l_B.f1232);
            int j = this.f1236.height;
            if (this.f1238 != null) {
               this.f1238.f1374.setText("Page " + (i + 1) + " of " + this.f1243.size());
               this.f1238.f1374.doLayout();
               graphics2d.setClip(0, 0, this.f1236.width, this.f1237);
               RepaintManager.currentManager((JComponent)this.f1238).setDoubleBufferingEnabled(false);
               this.f1238.print(graphics2d);
               RepaintManager.currentManager((JComponent)this.f1238).setDoubleBufferingEnabled(true);
               graphics2d.translate(0, this.f1237);
               j -= this.f1237;
            }

            for (int k = c_l_b$c__a$c2__a.f1247; k <= c_l_b$c__a$c2__a.f1248; k++) {
               AffineTransform affinetransform = graphics2d.getTransform();
               C_l_B.C__A.C2__B c_l_b$c__a$c2__b = this.f1242[k];
               Component component = this.f1245.getComponent(k);
               graphics2d.scale(c_l_b$c__a$c2__b.f1252, c_l_b$c__a$c2__b.f1252);
               int l = (int)(j / c_l_b$c__a$c2__b.f1252);
               if (k == c_l_b$c__a$c2__a.f1247) {
                  graphics2d.translate(0, -c_l_b$c__a$c2__a.f1249);
               }

               graphics2d.setClip(
                  0,
                  k == c_l_b$c__a$c2__a.f1247 ? c_l_b$c__a$c2__a.f1249 : 0,
                  (int)(this.f1236.width / c_l_b$c__a$c2__b.f1252),
                  Math.min(c_l_b$c__a$c2__b.f1251 - (k == c_l_b$c__a$c2__a.f1247 ? c_l_b$c__a$c2__a.f1249 : 0), l)
               );
               RepaintManager.currentManager(component).setDoubleBufferingEnabled(false);
               component.print(graphics2d);
               RepaintManager.currentManager(component).setDoubleBufferingEnabled(true);
               graphics2d.setTransform(affinetransform);
               graphics2d.translate(0.0, c_l_b$c__a$c2__b.f1252 * (c_l_b$c__a$c2__b.f1251 - (k == c_l_b$c__a$c2__a.f1247 ? c_l_b$c__a$c2__a.f1249 : 0)));
               j = (int)(j - c_l_b$c__a$c2__b.f1252 * (c_l_b$c__a$c2__b.f1251 - (k == c_l_b$c__a$c2__a.f1247 ? c_l_b$c__a$c2__a.f1249 : 0)));
               if (this.f1240 != null && k != c_l_b$c__a$c2__a.f1248 && this.f1241.get(k)) {
                  graphics2d.setClip(0, 0, this.f1236.width, this.f1239);
                  RepaintManager.currentManager(this.f1240).setDoubleBufferingEnabled(false);
                  this.f1240.print(graphics2d);
                  RepaintManager.currentManager(this.f1240).setDoubleBufferingEnabled(true);
                  graphics2d.translate(0, this.f1239);
                  j -= this.f1239;
               }
            }

            return 0;
         } else {
            return 1;
         }
      }

      class C2__A {
         int f1247;
         int f1248;
         int f1249;

         C2__A(int i, int j, int k) {
            this.f1247 = i;
            this.f1248 = j;
            this.f1249 = k;
         }
      }

      class C2__B {
         int f1251;
         double f1252;

         C2__B(int i, double d0) {
            this.f1251 = i;
            this.f1252 = d0;
         }
      }
   }
}
