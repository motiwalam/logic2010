package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ParseTreeFormulaText extends LogicTextArea implements LogicConstants, AnchorProvider, MouseListener {
   static String[] SYMBOLS = LogicProgram.symbols;
   FormulaParseNode parseNode;
   int[] selectedRange = null;
   Point highlightOrigin = null;
   char[] highlightChars = null;
   boolean selectionCorrect = false;

   ParseTreeFormulaText(FormulaParseNode formulaparsenode) {
      super(LogicProgram.translateSymbols(formulaparsenode.toString(), maggie, SYMBOLS));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize * 7 / 6));
      this.parseNode = formulaparsenode;
      this.setEditable(false);
      this.addMouseListener(this);
   }

   ParseTreeFormulaText(String s, boolean flag) {
      super(LogicProgram.translateSymbols(s, maggie, SYMBOLS));
      this.setFont(LogicProgram.getFont(LogicProgram.fontSize * 7 / 6));
      this.parseNode = flag ? new FormulaParseNode(s) : null;
      if (this.parseNode != null && this.parseNode.expression == null) {
         this.parseNode = null;
      }

      this.setEditable(false);
      this.addMouseListener(this);
   }

   ParseTreeFormulaText(String s) {
      this(s, false);
   }

   IntervalSet getOperatorRanges() {
      if (this.parseNode == null) {
         return new IntervalSet();
      } else {
         IntervalSet intervalset = this.parseNode.getOperatorRanges();
         FormulaParseNode formulaparsenode = this.parseNode.getRoot(true);
         FormulaParseNode.convertRanges(formulaparsenode.strippedIndex, formulaparsenode.parenDepth, intervalset, false);
         int i = this.parseNode.getTextRange()[0];

         for (int j = 0; j < intervalset.count; j++) {
            intervalset.boundaries[j] -= i;
         }

         LogicProgram.translateSymbols(this.parseNode.toString(), maggie, SYMBOLS, intervalset.boundaries);
         return intervalset;
      }
   }

   IntervalSet getOperatorPixelRanges() {
      IntervalSet intervalset = this.getOperatorRanges();
      int[] aint = intervalset.boundaries;
      int i = intervalset.count;

      for (int j = 0; j < i; j++) {
         aint[j] = this.getCharLocation(aint[j]).x;
      }

      return intervalset;
   }

   @Override
   public int getAnchorX() {
      IntervalSet intervalset = this.getOperatorPixelRanges();
      return intervalset.count < 2
         ? (this.getCharLocation(0).x + this.getCharLocation(this.getDocument().getLength()).x) / 2
         : (intervalset.boundaries[0] + intervalset.boundaries[1]) / 2;
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
      int i = mouseevent.getX();
      Container container = this.getParent();
      if (container instanceof ParseTreeNodePanel) {
         ParseTreeNodePanel parsetreenodepanel = (ParseTreeNodePanel)container;
         if ("N".equals(parsetreenodepanel.treePanel.module.problem.notationChooser.getSelectedCode())) {
            return;
         }

         if (parsetreenodepanel.treePanel.module.noDescent) {
            this.selectionCorrect = this.parseNode != null && this.parseNode.getChildCount() != 0 && this.getOperatorPixelRanges().contains(i);
            this.setHighlight(this.selectedRange = this.getSymbolRangeAt(i));
            this.repaint();
         } else if (!parsetreenodepanel.expanded && this.parseNode != null && this.parseNode.getChildCount() != 0 && this.getOperatorPixelRanges().contains(i)) {
            parsetreenodepanel.setExpanded(!parsetreenodepanel.expanded);
            this.flashSymbolAt(i, dialogGreen, false);
         } else {
            this.flashSymbolAt(i, dialogRed, true);
            parsetreenodepanel.treePanel.module.errorCount++;
         }
      }
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }

   int[] getSymbolRangeAt(int i) {
      String s = this.getText();
      int j = 0;
      int k = s.length();
      if (i >= this.getCharLocation(0).x && i < this.getCharLocation(k).x) {
         while (j < k && i >= this.getCharLocation(j + 1).x) {
            j++;
         }

         return s.charAt(j) == ' ' ? null : LogicProgram.symbolBoundsAt(s, j, SYMBOLS);
      } else {
         return null;
      }
   }

   boolean setHighlight(int[] aint) {
      if (aint == null) {
         this.highlightChars = null;
         this.highlightOrigin = null;
         return false;
      } else {
         String s = this.getText();
         this.highlightChars = new char[aint[1] - aint[0]];
         s.getChars(aint[0], aint[1], this.highlightChars, 0);
         Graphics graphics = this.getGraphics();
         this.highlightOrigin = this.getCharLocation(aint[0]);
         this.highlightOrigin.y += graphics.getFontMetrics().getAscent();
         return true;
      }
   }

   void clearHighlight() {
      this.setHighlight(this.selectedRange = null);
      this.selectionCorrect = false;
      this.repaint();
   }

   void flashSymbolAt(int i, Color color, boolean flag) {
      if (this.setHighlight(this.selectedRange = this.getSymbolRangeAt(i))) {
         if (flag) {
            Toolkit.getDefaultToolkit().beep();
         }

         Graphics graphics = this.getGraphics();
         Color color1 = this.getForeground();
         graphics.setColor(color);
         graphics.drawChars(this.highlightChars, 0, this.highlightChars.length, this.highlightOrigin.x, this.highlightOrigin.y);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException interruptedexception) {
         }

         graphics.setColor(color1);
         graphics.drawChars(this.highlightChars, 0, this.highlightChars.length, this.highlightOrigin.x, this.highlightOrigin.y);
      }
   }

   public void emptyHook() {
   }

   @Override
   public void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      Container container = this.getParent();
      if (container instanceof ParseTreeNodePanel) {
         ParseTreeNodePanel parsetreenodepanel = (ParseTreeNodePanel)container;
         boolean flag = parsetreenodepanel.treePanel.module.noDescent;
         boolean flag1 = parsetreenodepanel.treePanel.module.checkDisabled;
         if (flag && this.highlightChars != null && this.highlightOrigin != null) {
            Color color = graphics.getColor();
            Color color1 = flag ? dialogOrange : (this.selectionCorrect ? dialogGreen : dialogRed);
            graphics.setColor(color1);
            graphics.drawChars(this.highlightChars, 0, this.highlightChars.length, this.highlightOrigin.x, this.highlightOrigin.y);
            graphics.setColor(color);
         }
      }
   }
}
