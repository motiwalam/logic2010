package edu.ucla.phil.logic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.border.EtchedBorder;

class ParseTreeNodePanel extends CellPanel {
   ParseTreeNodePanel parent = null;
   ParseTreeLayout treeLayout;
   FormulaParseNode parseNode = null;
   ParseTreeFormulaText formulaText;
   boolean expanded = false;
   ParseTreePanel treePanel;

   ParseTreeNodePanel(FormulaParseNode formulaparsenode) {
      this((ParseTreePanel)null);
      this.setParseNode(formulaparsenode);
   }

   ParseTreeNodePanel(ParseTreePanel parsetreepanel) {
      this.treePanel = parsetreepanel;
      this.setLayout(this.treeLayout = new ParseTreeLayout());
      this.add(this.formulaText = new ParseTreeFormulaText(""));
      this.treeLayout.setAnchor(this.formulaText);
   }

   ParseTreeNodePanel detach() {
      if (this.parent != null) {
         if (this.treePanel != null && !this.parent.expanded) {
            this.treePanel.unexpandedCount--;
         }

         this.setTreePanel(null);
         this.parent.remove(this);
         this.parent = null;
      }

      return this;
   }

   ParseTreeNodePanel addChildNode(ParseTreeNodePanel parsetreenodepanel1) {
      if (parsetreenodepanel1 != null) {
         this.add(parsetreenodepanel1.detach());
         parsetreenodepanel1.parent = this;
         if (this.treePanel != null && !this.expanded) {
            this.treePanel.unexpandedCount++;
         }

         parsetreenodepanel1.setTreePanel(this.treePanel);
      }

      return parsetreenodepanel1;
   }

   void setTreePanel(ParseTreePanel parsetreepanel) {
      int j = this.getChildNodeCount();
      if (!this.expanded) {
         if (this.treePanel != null) {
            this.treePanel.unexpandedCount -= j;
         }

         if (parsetreepanel != null) {
            parsetreepanel.unexpandedCount += j;
         }
      }

      this.treePanel = parsetreepanel;

      for (int i = 0; i < j; i++) {
         this.getChildNode(i).setTreePanel(parsetreepanel);
      }
   }

   void setFormula(String s) {
      this.setParseNode(new FormulaParseNode(s));
      if (this.parseNode == null) {
         this.add(this.formulaText = new ParseTreeFormulaText(s));
         this.treeLayout.setAnchor(this.formulaText);
      }

      this.setExpanded(false);
      this.setBorder(new EtchedBorder(1));
      if (this.treePanel != null) {
         this.treePanel.rootNode = this;
         this.treePanel.updateStatus();
      }
   }

   void setParseNode(FormulaParseNode formulaparsenode) {
      this.treeLayout.setAnchor(null);
      if (this.formulaText != null) {
         int j = this.getChildNodeCount();
         this.remove(this.formulaText);
         this.formulaText = null;

         for (int i = 0; i < j; i++) {
            ((ParseTreeNodePanel)this.getComponent(0)).detach();
         }
      }

      if (formulaparsenode != null && formulaparsenode.expression != null) {
         this.parseNode = formulaparsenode;
         this.add(this.formulaText = new ParseTreeFormulaText(formulaparsenode));
         this.treeLayout.setAnchor(this.formulaText);
         int l = formulaparsenode.getChildCount();

         for (int k = 0; k < l; k++) {
            this.addChildNode(new ParseTreeNodePanel(formulaparsenode.getChild(k)));
         }

         this.validate();
      } else {
         this.parseNode = null;
      }
   }

   void setExpanded(boolean flag) {
      int i = this.getChildNodeCount();
      if (this.treePanel != null) {
         if (this.expanded && !flag) {
            if (this.treePanel.module.checkNow && this.treePanel.unexpandedCount == 0 && i != 0) {
               this.treePanel.statusLabel.setText("Incomplete");
            }

            this.treePanel.unexpandedCount += i;
         } else if (!this.expanded && flag) {
            this.treePanel.unexpandedCount -= i;
            if (this.treePanel.module.checkNow && this.treePanel.unexpandedCount == 0 && i != 0) {
               this.treePanel.statusLabel.setText("Complete");
            }
         }

         this.treePanel.validate();
      }

      this.expanded = flag;

      for (int j = 0; j < i; j++) {
         ParseTreeNodePanel parsetreenodepanel1 = this.getChildNode(j);
         parsetreenodepanel1.setVisible(flag);
         if (!flag) {
            parsetreenodepanel1.setExpanded(false);
         }
      }
   }

   String restoreExpansion(String s) {
      if (s == null) {
         s = "";
      }

      int i = s.indexOf(",");
      String s1;
      if (i == -1) {
         s1 = s;
         s = "";
      } else {
         s1 = s.substring(0, i);
         s = s.substring(i + 1);
      }

      int j;
      try {
         j = Integer.parseInt(s1.trim());
      } catch (NumberFormatException numberformatexception) {
         j = 0;
      }

      this.setExpanded(j != 0);

      for (int k = 0; k < j; k++) {
         try {
            s = this.getChildNode(k).restoreExpansion(s);
         } catch (ClassCastException classcastexception) {
         }
      }

      return s;
   }

   int getChildNodeCount() {
      return this.formulaText == null ? 0 : this.getComponentCount() - 1;
   }

   ParseTreeNodePanel getChildNode(int i) {
      Component component = this.getComponent(i + 1);
      return component instanceof ParseTreeNodePanel ? (ParseTreeNodePanel)component : null;
   }

   String getExpansionString(boolean flag) {
      if (!flag && !this.expanded) {
         return "0";
      } else {
         int i = this.getChildNodeCount();
         String s = "" + i;

         for (int j = 0; j < i; j++) {
            s = s + "," + this.getChildNode(j).getExpansionString(flag);
         }

         return s;
      }
   }

   @Override
   public void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      if (!this.treePanel.module.noDescent) {
         int i = this.getComponentCount();
         int j = 0;
         if (i != 0 && this.formulaText != null && !this.formulaText.getText().isEmpty()) {
            Rectangle rectangle = this.getComponent(0).getBounds();
            graphics.setColor(this.getForeground());
            int i1;
            int k1;
            int j1 = k1 = i1 = rectangle.x + this.formulaText.getAnchorX();
            int l = rectangle.y + rectangle.height;
            int k = ((ParseTreeLayout)this.getLayout()).getVgap();

            for (int l1 = 1; l1 < i; l1++) {
               Component component = this.getComponent(l1);
               if (component.isVisible()) {
                  j++;
                  rectangle = component.getBounds();
                  int i2 = rectangle.x + rectangle.width / 2;
                  if (i2 < j1) {
                     j1 = i2;
                  }

                  if (i2 > k1) {
                     k1 = i2;
                  }

                  graphics.drawLine(i2, l + k / 2, i2, l + k);
               }
            }

            if (j != 0) {
               graphics.drawLine(i1, l, i1, l + k / 2);
               graphics.drawLine(j1, l + k / 2, k1, l + k / 2);
            }
         }
      }
   }
}
