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

class ModuleFrame extends JFrame implements LogicConstants, WindowListener, ComponentListener, Runnable {
   LogicModule module;

   ModuleFrame() {
      this("", LogicProgram.fontSize);
   }

   ModuleFrame(String s) {
      this(s, LogicProgram.fontSize);
   }

   ModuleFrame(String s, int i) {
      super(s);
      this.setIconImages(LogicProgram.f594);
      this.addComponentListener(this);
      this.addWindowListener(this);
      this.m64();
      this.module = null;
      this.setLayout(new BorderLayout(0, 0));
      this.setFont(LogicProgram.getFont(i));
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
      if (this.module == null || this.module.shutdown(flag)) {
         this.dispose();
      }
   }

   protected void m63(KeyEvent keyevent) {
      byte b0 = 15;
      int i = keyevent.getKeyCode();
      int j = keyevent.getModifiers();
      if (i == 81 && (j & b0) == 2) {
         if (LogicProgram.mainMenu != null) {
            LogicProgram.mainMenu.shutdown(false);
         } else {
            LogicProgram.exit();
         }
      }
   }

   void m64() {
      AbstractAction abstractaction = new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent actionevent) {
            if (LogicProgram.f590) {
               boolean flag = !ProblemSet.f1082;
               ProblemSet.f1082 = flag;
               LogicModule.eraseWork = flag;
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
      if (component == this && this.module != null) {
         this.module.resize();
      }
   }

   @Override
   public void componentShown(ComponentEvent componentevent) {
      Component component = componentevent.getComponent();
      if (component == this && this.module != null) {
         this.module.resize();
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
