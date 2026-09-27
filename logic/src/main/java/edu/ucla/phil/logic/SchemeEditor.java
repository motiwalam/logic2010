package edu.ucla.phil.logic;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Scrollable;

class SchemeEditor extends JPanel implements SymbolizationConstants, Scrollable, MouseListener {
   Vector symbolFields = new Vector();
   Vector englishFields = new Vector();
   MouseListener mouseListener = null;
   boolean editable = false;
   GridBagConstraints constraints = new GridBagConstraints();
   static String[] displaySymbols = LogicProgram.symbols;

   SchemeEditor() {
      this.setLayout(new GridBagLayout());
   }

   SchemeEditor(boolean flag) {
      this();
      if (this.editable = flag) {
         this.mouseListener = this;
      }
   }

   @Override
   public boolean getScrollableTracksViewportWidth() {
      return true;
   }

   @Override
   public boolean getScrollableTracksViewportHeight() {
      return false;
   }

   @Override
   public Dimension getPreferredScrollableViewportSize() {
      return this.getPreferredSize();
   }

   @Override
   public int getScrollableUnitIncrement(Rectangle rectangle, int i, int j) {
      return this.getRowCount() > 0 ? ((EditableTextPane)this.symbolFields.elementAt(0)).getHeight() : 1;
   }

   @Override
   public int getScrollableBlockIncrement(Rectangle rectangle, int i, int j) {
      return this.getRowCount() > 0 ? ((EditableTextPane)this.symbolFields.elementAt(0)).getHeight() : 1;
   }

   void clearRows() {
      this.removeAll();
      this.symbolFields = new Vector();
      this.englishFields = new Vector();
      this.constraints.gridy = 0;
   }

   void loadScheme(TaggedRecord taggedrecord) {
      if (taggedrecord == null) {
         this.clearRows();
      } else {
         this.setScheme(taggedrecord.valueAt(taggedrecord.indexOfTag('=')));
      }
   }

   void setScheme(String s) {
      this.clearRows();
      if (s != null) {
         DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
         DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\.");
         delimitedtokenizer.setInput(s);
         this.constraints.weighty = 0.0;
         this.constraints.gridwidth = 1;
         this.constraints.gridx = 0;
         this.constraints.fill = 0;
         this.constraints.insets = new Insets(0, 0, 0, 0);
         this.constraints.weightx = 0.0;
         this.constraints.anchor = 23;
         FontLabel fontlabel = new FontLabel("symbol");
         fontlabel.setForeground(this.getForeground());
         this.add(fontlabel, this.constraints);
         this.constraints.gridx = 1;
         this.constraints.insets = new Insets(0, 5, 0, 5);
         fontlabel = new FontLabel(":");
         fontlabel.setForeground(this.getForeground());
         this.add(fontlabel, this.constraints);
         this.constraints.gridx = 2;
         this.constraints.insets = new Insets(0, 0, 0, 0);
         this.constraints.weightx = 1.0;
         this.constraints.fill = 2;
         fontlabel = new FontLabel("English");
         fontlabel.setForeground(this.getForeground());
         this.add(fontlabel, this.constraints);
         this.constraints.gridy++;

         while (true) {
            String s1 = delimitedtokenizer.nextToken();
            if (s1 == null) {
               break;
            }

            delimitedtokenizer1.setInput(delimitedtokenizer.getRemaining());
            String s2 = delimitedtokenizer1.nextToken();
            if (s2 == null) {
               break;
            }

            delimitedtokenizer.setInput(delimitedtokenizer1.getRemaining());
            this.addRow(s1, s2);
         }

         this.revalidate();
      }
   }

   int getRowCount() {
      return this.symbolFields.size();
   }

   String getScheme() {
      int i = this.getRowCount();
      String s = "";
      DelimitedTokenizer delimitedtokenizer = new DelimitedTokenizer("\\:");
      DelimitedTokenizer delimitedtokenizer1 = new DelimitedTokenizer("\\.");

      for (int j = 0; j < i; j++) {
         EditableTextPane editabletextpane = (EditableTextPane)this.symbolFields.elementAt(j);
         EditableTextPane editabletextpane1 = (EditableTextPane)this.englishFields.elementAt(j);
         if (editabletextpane != null && editabletextpane1 != null) {
            String s1 = LogicProgram.translateSymbols(editabletextpane.getText().trim(), displaySymbols, maggie);
            String s2 = editabletextpane1.getText().trim();
            if (s1.length() != 0 && s2.length() != 0) {
               if (s.length() != 0) {
                  s = s + ".";
               }

               s = s + delimitedtokenizer.escape(s1) + ":" + delimitedtokenizer1.escape(s2);
            }
         }
      }

      return s;
   }

   void addRow(String s, String s1) {
      SchemeCellPane schemecellpane = new SchemeCellPane(LogicProgram.translateSymbols(s.trim(), maggie, displaySymbols), this);
      schemecellpane.setForeground(this.getForeground());
      schemecellpane.setEditable(this.editable);
      schemecellpane.setMinimumPreferred(true);
      if (this.mouseListener != null) {
         schemecellpane.addMouseListener(this.mouseListener);
      }

      this.constraints.weighty = 0.0;
      this.constraints.gridwidth = 1;
      this.constraints.gridx = 0;
      this.constraints.fill = 2;
      this.constraints.insets = new Insets(0, 0, 0, 0);
      this.constraints.weightx = 0.0;
      this.constraints.anchor = 23;
      this.add(schemecellpane, this.constraints);
      this.symbolFields.addElement(schemecellpane);
      this.constraints.gridx = 1;
      this.constraints.insets = new Insets(0, 5, 0, 5);
      FontLabel fontlabel = new FontLabel(":");
      fontlabel.setForeground(this.getForeground());
      this.add(fontlabel, this.constraints);
      SchemeCellPane schemecellpane1 = new SchemeCellPane(s1.trim(), this);
      schemecellpane1.setForeground(this.getForeground());
      schemecellpane1.setEditable(this.editable);
      schemecellpane1.setWrapLines(true);
      schemecellpane1.setWrapWords(true);
      if (this.mouseListener != null) {
         schemecellpane1.addMouseListener(this.mouseListener);
      }

      this.constraints.gridx = 2;
      this.constraints.insets = new Insets(0, 0, 0, 0);
      this.constraints.weightx = 1.0;
      this.constraints.fill = 2;
      this.add(schemecellpane1, this.constraints);
      this.englishFields.addElement(schemecellpane1);
      this.constraints.gridy++;
      this.validate();
   }

   int indexOfSymbolField(EditableTextPane editabletextpane) {
      return this.symbolFields.indexOf(editabletextpane);
   }

   int indexOfEnglishField(EditableTextPane editabletextpane) {
      return this.englishFields.indexOf(editabletextpane);
   }

   @Override
   public void mouseClicked(MouseEvent mouseevent) {
   }

   @Override
   public void mouseEntered(MouseEvent mouseevent) {
   }

   @Override
   public void mouseExited(MouseEvent mouseevent) {
   }

   @Override
   public void mousePressed(MouseEvent mouseevent) {
   }

   @Override
   public void mouseReleased(MouseEvent mouseevent) {
   }
}
