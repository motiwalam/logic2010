package edu.ucla.phil.logic;

import java.awt.Color;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

class SymbolizationNodeMenu extends JPopupMenu implements ActionListener, SymbolizationConstants {
   SymbolizationTextPanel textPanel;
   static final String[] HINT_LABELS = new String[]{"Hint"};
   static final String[] HINT_HOVER = new String[]{"Ctrl+Shift+?"};
   static final Integer[] HINT_CHAPTERS = new Integer[]{new Integer(1)};
   static final String[] INEQUALITY_LABELS = new String[]{"Inequality"};
   static final Integer[] INEQUALITY_CHAPTERS = new Integer[]{new Integer(5)};
   static final String[] RESTRICTED_LABELS = new String[]{"Rest Univ", "Rest Exist", "Unrest"};
   static final String[] RESTRICTED_HOVER = new String[]{
      "Restricted Universal Generalization", "Restricted Existential Generalization", "Remove Restricted Generalization"
   };
   static final Integer[] RESTRICTED_CHAPTERS = new Integer[]{new Integer(3), new Integer(3), new Integer(3)};

   SymbolizationNodeMenu(SymbolizationTextPanel symbolizationtextpanel) {
      this.textPanel = symbolizationtextpanel;
   }

   void rebuildItems() {
      SymbolizationNode symbolizationnode = (SymbolizationNode)this.textPanel.getParent();
      SymbolizationNode symbolizationnode1 = symbolizationnode.getParentNode();
      Integer integer = symbolizationnode.symbolizer.chapter;
      int j = symbolizationnode1 == null ? -1 : symbolizationnode1.indexOfChildNode(symbolizationnode);
      int k = symbolizationnode1 == null ? 2 : symbolizationnode1.argTypes[j];

      while (this.getComponentCount() != 0) {
         this.remove(0);
      }

      int i = connOutTypes.length;

      for (int l = 0; l < i; l++) {
         int i1 = connOutTypes[l];
         if ((i1 == 2 || k == 2 || i1 == k) && this.isChapterAvailable(integer, connChaps[l])) {
            SmallMenuItem smallmenuitem;
            this.add(smallmenuitem = new SmallMenuItem(connMenu[l]));
            smallmenuitem.addActionListener(this);
            if (connHover[l] != null) {
               smallmenuitem.setHoverText(connHover[l]);
            }
         }
      }

      if ((k == 0 || k == 2) && this.isChapterAvailable(integer, INEQUALITY_CHAPTERS[0])) {
         SmallMenuItem smallmenuitem1;
         this.add(smallmenuitem1 = new SmallMenuItem(INEQUALITY_LABELS[0]));
         smallmenuitem1.addActionListener(this);
      }

      if (!symbolizationnode.symbolizer.hintsDisabled
         && symbolizationnode.symbolizer.problem.countAnswers() != 0
         && this.isChapterAvailable(integer, HINT_CHAPTERS[0])) {
         SmallMenuItem smallmenuitem2;
         this.add(smallmenuitem2 = new SmallMenuItem(HINT_LABELS[0]));
         smallmenuitem2.setHoverText(HINT_HOVER[0]);
         smallmenuitem2.addActionListener(this);
      }

      if (k == 0 || k == 2) {
         if (this.isChapterAvailable(integer, RESTRICTED_CHAPTERS[0])) {
            SmallMenuItem smallmenuitem3;
            this.add(smallmenuitem3 = new SmallMenuItem(RESTRICTED_LABELS[0]));
            smallmenuitem3.setHoverText(RESTRICTED_HOVER[0]);
            smallmenuitem3.addActionListener(this);
         }

         if (this.isChapterAvailable(integer, RESTRICTED_CHAPTERS[1])) {
            SmallMenuItem smallmenuitem4;
            this.add(smallmenuitem4 = new SmallMenuItem(RESTRICTED_LABELS[1]));
            smallmenuitem4.setHoverText(RESTRICTED_HOVER[1]);
            smallmenuitem4.addActionListener(this);
         }
      }

      if ((
            symbolizationnode.connective == 6 && symbolizationnode.getChildNode(0).connective == 2
               || symbolizationnode.connective == 7 && symbolizationnode.getChildNode(0).connective == 3
               || symbolizationnode.connective == 1 && symbolizationnode.getChildNode(0).connective == 9
         )
         && this.isChapterAvailable(integer, RESTRICTED_CHAPTERS[2])) {
         SmallMenuItem smallmenuitem5;
         this.add(smallmenuitem5 = new SmallMenuItem(RESTRICTED_LABELS[2]));
         smallmenuitem5.setHoverText(RESTRICTED_HOVER[2]);
         smallmenuitem5.addActionListener(this);
      }

      this.addSeparator();
      i = editMenu.length;

      for (int j1 = 0; j1 < i; j1++) {
         SmallMenuItem smallmenuitem6;
         this.add(smallmenuitem6 = new SmallMenuItem(editMenu[j1]));
         smallmenuitem6.setHoverText(editHover[j1]);
         smallmenuitem6.addActionListener(this);
      }
   }

   boolean isChapterAvailable(Integer integer, Integer integer1) {
      return integer == null || integer1 == null || integer >= integer1;
   }

   @Override
   public boolean keyDown(Event event, int i) {
      if (i == 27) {
         this.textPanel.popupWasOpen = true;
      }

      return super.keyDown(event, i);
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      JMenuItem jmenuitem = (JMenuItem)actionevent.getSource();
      String s = jmenuitem.getText();
      SymbolizationNode symbolizationnode = (SymbolizationNode)this.textPanel.getParent();

      for (int i = 0; i < connMenu.length; i++) {
         if (s.equals(connMenu[i])) {
            SymbolizationNode symbolizationnode1 = symbolizationnode.setConnective(i, true);
            if (symbolizationnode1 != null) {
               symbolizationnode1.textPanel.textPane.requestFocus();
            }

            return;
         }
      }

      if (s.equals(INEQUALITY_LABELS[0])) {
         ChildRecordSnapshot childrecordsnapshot3 = new ChildRecordSnapshot(symbolizationnode);
         symbolizationnode.setConnective(0, false);
         symbolizationnode.setConnective(1, true);
         SymbolizationNode symbolizationnode6;
         (symbolizationnode6 = symbolizationnode.getChildNode(0)).setConnective(9, false);
         childrecordsnapshot3.restore(symbolizationnode6);
         symbolizationnode6.textPanel.textPane.requestFocus();
      } else if (s.equals(HINT_LABELS[0])) {
         SymbolizationNode symbolizationnode5 = symbolizationnode.symbolizer.problem;
         SymbolizationNode symbolizationnode7 = symbolizationnode5.findClosestAnswer();
         if (symbolizationnode7 != null) {
            symbolizationnode5.clearErrors();
            HintCollector hintcollector = new HintCollector(symbolizationnode);
            symbolizationnode5.matchTree(symbolizationnode7, new Vector(), new Vector(), hintcollector);
            SymbolizationHint symbolizationhint = hintcollector.getHint();
            if (symbolizationhint == null) {
               SymbolizationErrorButton symbolizationerrorbutton = null;
               Vector vector = hintcollector.getErrors();
               int k = vector.size();
               SymbolizationNode symbolizationnode2 = symbolizationnode;

               while (symbolizationerrorbutton == null && (symbolizationnode2 = symbolizationnode2.getParentNode()) != null) {
                  for (int l = 0; l < k; l++) {
                     SymbolizationErrorButton symbolizationerrorbutton1 = (SymbolizationErrorButton)vector.elementAt(l);
                     if (symbolizationerrorbutton1.target == symbolizationnode2) {
                        symbolizationerrorbutton = symbolizationerrorbutton1;
                        break;
                     }
                  }
               }

               if (symbolizationerrorbutton != null) {
                  SymbolizationConnectivePanel symbolizationconnectivepanel = symbolizationerrorbutton.target.getConnectivePanel();
                  if (symbolizationconnectivepanel != null) {
                     symbolizationconnectivepanel.add(symbolizationerrorbutton);
                     symbolizationnode.symbolizer.errorCount++;
                     symbolizationconnectivepanel.enableTarget.setEnabled(true);
                     symbolizationconnectivepanel.revalidate();
                  }
               }
            } else {
               symbolizationhint.show();
               symbolizationnode.symbolizer.hintCount++;
            }
         }
      } else if (s.equals(RESTRICTED_LABELS[0])) {
         ChildRecordSnapshot childrecordsnapshot2 = new ChildRecordSnapshot(symbolizationnode);
         symbolizationnode.setConnective(0, false);
         symbolizationnode.setConnective(6, true);
         SymbolizationNode symbolizationnode4;
         (symbolizationnode4 = symbolizationnode.getChildNode(0)).setConnective(2, false);
         childrecordsnapshot2.restore(symbolizationnode4);
         symbolizationnode4.textPanel.textPane.requestFocus();
      } else if (s.equals(RESTRICTED_LABELS[1])) {
         ChildRecordSnapshot childrecordsnapshot1 = new ChildRecordSnapshot(symbolizationnode);
         symbolizationnode.setConnective(0, false);
         symbolizationnode.setConnective(7, true);
         SymbolizationNode symbolizationnode3;
         (symbolizationnode3 = symbolizationnode.getChildNode(0)).setConnective(3, false);
         childrecordsnapshot1.restore(symbolizationnode3);
         symbolizationnode3.textPanel.textPane.requestFocus();
      } else if (s.equals(RESTRICTED_LABELS[2])) {
         ChildRecordSnapshot childrecordsnapshot = new ChildRecordSnapshot(symbolizationnode.getChildNode(0));
         int j1 = symbolizationnode.getChildNode(0).connective;
         symbolizationnode.setConnective(0, false);
         symbolizationnode.setConnective(j1, true);
         childrecordsnapshot.restore(symbolizationnode);
         symbolizationnode.textPanel.textPane.requestFocus();
      } else if (s.equals(editMenu[0]) || s.equals(editMenu[4])) {
         SymbolizationTextPane symbolizationtextpane1 = this.textPanel.textPane;
         int i1 = symbolizationtextpane1.getSelectionStart();
         int j = symbolizationtextpane1.getSelectionEnd();
         if (j > i1) {
            if (s.equals(editMenu[0])) {
               EditableTextPane.copyToClipboard(symbolizationtextpane1.getSelectedText());
            }

            String s1 = symbolizationtextpane1.getText();
            symbolizationtextpane1.setText(s1.substring(0, i1) + s1.substring(j));
            symbolizationtextpane1.select(i1, i1);
            this.textPanel.normalizeText(true);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (s.equals(editMenu[1])) {
         String s2 = this.textPanel.textPane.getSelectedText();
         if (s2 != null && s2.length() > 0) {
            EditableTextPane.copyToClipboard(s2);
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (s.equals(editMenu[2])) {
         SymbolizationTextPane symbolizationtextpane = this.textPanel.textPane;
         String s3 = EditableTextPane.getClipboardText();
         if (s3 == null) {
            s3 = "";
         }

         symbolizationtextpane.replaceSelection(s3);
         this.textPanel.normalizeText(true);
      } else if (s.equals(editMenu[3])) {
         this.textPanel.textPane.select(0, 2147483647);
      }
   }

   JMenuItem[] getMenuItems() {
      int i = this.getComponentCount();
      JMenuItem[] ajmenuitem = new JMenuItem[i];

      for (int j = 0; j < i; j++) {
         ajmenuitem[j] = (JMenuItem)this.getComponent(j);
      }

      return ajmenuitem;
   }

   static {
      UIManager.put("MenuItem.selectionBackground", new Color(184, 207, 229));
   }
}
