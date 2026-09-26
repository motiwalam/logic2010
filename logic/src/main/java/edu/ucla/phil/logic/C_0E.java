package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

class C_0E extends JFrame implements C_n_A, WindowListener, ComponentListener, Runnable {
   C_U f27;

   C_0E() {
      this("", LogicProgram.f539);
   }

   C_0E(String s) {
      this(s, LogicProgram.f539);
   }

   C_0E(String s, int i) {
      super(s);
      this.setIconImages(LogicProgram.f594);
      this.addComponentListener(this);
      this.addWindowListener(this);
      this.m64();
      this.f27 = null;
      this.setLayout(new BorderLayout(0, 0));
      this.setFont(LogicProgram.m1029(i));
   }

   void m61(Color[] acolor) {
      this.setForeground(acolor[0]);
      this.setBackground(acolor[1]);
      Graphics graphics = this.getGraphics();
      if (graphics != null) {
         this.paintAll(graphics);
      }
   }

   @Override
   public void windowClosing(WindowEvent windowevent) {
      this.setDefaultCloseOperation(0);
      this.m62(false);
   }

   @Override
   public void windowActivated(WindowEvent windowevent) {
   }

   @Override
   public void windowClosed(WindowEvent windowevent) {
   }

   @Override
   public void windowDeactivated(WindowEvent windowevent) {
   }

   @Override
   public void windowIconified(WindowEvent windowevent) {
   }

   @Override
   public void windowDeiconified(WindowEvent windowevent) {
   }

   @Override
   public void windowOpened(WindowEvent windowevent) {
   }

   void m62(boolean flag) {
      if (this.f27 == null || this.f27.shutdown(flag)) {
         this.dispose();
      }
   }

   protected void m63(KeyEvent keyevent) {
      byte b0 = 15;
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (i == 81 && (j & b0) == 2) {
         if (LogicProgram.f586 != null) {
            LogicProgram.f586.shutdown(false);
         } else {
            LogicProgram.m977();
         }
      }
   }

   void m64() {
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LogicProgram.f590) {
               boolean flag = !C_e_D.f1082;
               C_e_D.f1082 = flag;
               C_U.eraseWork = flag;
            }
         }
      };
      this.getRootPane().getActionMap().put("toggleHide", abstractaction);
      InputMap inputmap = this.getRootPane().getInputMap(1);
      inputmap.put(KeyStroke.getKeyStroke(72, 3), "toggleHide");
   }

   void m65() {
      new Thread(this).start();
   }

   @Override
   public void componentHidden(ComponentEvent componentevent) {
   }

   @Override
   public void componentMoved(ComponentEvent componentevent) {
   }

   @Override
   public void componentResized(ComponentEvent componentevent) {
      Component component = componentevent.getComponent();
      if (component == this && this.f27 != null) {
         this.f27.resize();
      }
   }

   @Override
   public void componentShown(ComponentEvent componentevent) {
      Component component = componentevent.getComponent();
      if (component == this && this.f27 != null) {
         this.f27.resize();
      }
   }

   @Override
   public void run() {
      try {
         Thread.sleep(1000L);
      } catch (InterruptedException interruptedexception) {
      }

      this.show();
   }
}
