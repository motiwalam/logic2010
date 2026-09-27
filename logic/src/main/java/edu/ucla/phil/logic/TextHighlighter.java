package edu.ucla.phil.logic;

import java.awt.Color;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class TextHighlighter implements LogicConstants {
   static final char MARK_CHAR = '̀';
   static final Color[] COLORS = new Color[]{null, dialogRed, dialogGreen, dialogBlue, dialogOrange};
   int[] colorIndexes;
   Vector ranges;

   TextHighlighter(int[] aint, Vector vector) {
      this.colorIndexes = aint;
      this.ranges = vector;
   }

   TextHighlighter copy() {
      int i = this.size();
      TextHighlighter texthighlighter1 = new TextHighlighter(new int[i], new Vector());
      if (i != 0) {
         System.arraycopy(this.colorIndexes, 0, texthighlighter1.colorIndexes, 0, i);
      }

      for (int j = 0; j < i; j++) {
         IntervalSet intervalset = (IntervalSet)this.ranges.elementAt(j);
         texthighlighter1.ranges.addElement(intervalset == null ? null : intervalset.copy(true));
      }

      return texthighlighter1;
   }

   int size() {
      return Math.min(this.colorIndexes == null ? 0 : this.colorIndexes.length, this.ranges == null ? 0 : this.ranges.size());
   }

   static void applyColor(StyledDocument styleddocument, int i, IntervalSet intervalset) {
      SimpleAttributeSet simpleattributeset = new SimpleAttributeSet();
      simpleattributeset.addAttribute(StyleConstants.Foreground, COLORS[i]);
      int j = styleddocument == null ? 0 : styleddocument.getLength();
      if (j != 0 && intervalset != null && !intervalset.isEmpty()) {
         for (int k = 0; k < j; k++) {
            if (intervalset.contains(k)) {
               styleddocument.setCharacterAttributes(k, 1, simpleattributeset, false);
            }
         }
      }
   }

   StyledDocument createDocument(String s) {
      DefaultStyledDocument defaultstyleddocument = new DefaultStyledDocument();

      try {
         defaultstyleddocument.insertString(0, s, SimpleAttributeSet.EMPTY);
      } catch (BadLocationException badlocationexception) {
      }

      int i = this.size();

      for (int j = 0; j < i; j++) {
         applyColor(defaultstyleddocument, this.colorIndexes[j], (IntervalSet)this.ranges.elementAt(j));
      }

      return defaultstyleddocument;
   }

   static StyledDocument createDocument(HighlightedText highlightedtext) {
      TextHighlighter texthighlighter = fromRanges(highlightedtext.layers);
      if (texthighlighter == null) {
         DefaultStyledDocument defaultstyleddocument = new DefaultStyledDocument();

         try {
            defaultstyleddocument.insertString(0, highlightedtext.text, SimpleAttributeSet.EMPTY);
         } catch (BadLocationException badlocationexception) {
         }

         return defaultstyleddocument;
      } else {
         return texthighlighter.createDocument(highlightedtext.text);
      }
   }

   static TextHighlighter fromRanges(Vector vector) {
      if (vector == null) {
         return null;
      } else {
         int i = vector.size();
         int[] aint = new int[i];

         for (int j = 0; j < i; j++) {
            aint[j] = 1;
         }

         return new TextHighlighter(aint, vector);
      }
   }
}
