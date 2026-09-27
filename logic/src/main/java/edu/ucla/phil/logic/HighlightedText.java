package edu.ucla.phil.logic;

import java.util.Vector;

class HighlightedText {
   String text;
   Vector layers;

   HighlightedText(String s, Vector vector) {
      this.text = s;
      this.layers = vector;
   }

   HighlightedText(String s) {
      this(s, null);
   }

   HighlightedText() {
      this(null, null);
   }

   int length() {
      return this.text == null ? 0 : this.text.length();
   }

   int layerCount() {
      return this.layers == null ? 0 : this.layers.size();
   }

   HighlightedText copy() {
      if (this.layers == null) {
         return new HighlightedText(this.text, null);
      } else {
         HighlightedText highlightedtext1 = new HighlightedText(this.text, new Vector());
         int i = this.layerCount();

         for (int j = 0; j < i; j++) {
            IntervalSet intervalset = (IntervalSet)this.layers.elementAt(j);
            highlightedtext1.layers.addElement(intervalset.copy(true));
         }

         return highlightedtext1;
      }
   }

   HighlightedText append(HighlightedText highlightedtext1) {
      int i = this.length();
      int j = this.layerCount();
      int k = highlightedtext1.layerCount();
      if (k > j) {
         if (this.layers == null) {
            this.layers = new Vector();
         }

         this.layers.setSize(k);
      }

      for (int l = 0; l < k; l++) {
         IntervalSet intervalset = (IntervalSet)this.layers.elementAt(l);
         IntervalSet intervalset1 = (IntervalSet)highlightedtext1.layers.elementAt(l);
         if (intervalset1 != null) {
            IntervalSet intervalset2 = intervalset1.copy(true).shift(i);
            if (intervalset == null) {
               this.layers.setElementAt(intervalset2, l);
            } else {
               intervalset.union(intervalset2);
            }
         }
      }

      return this.append(highlightedtext1.text);
   }

   HighlightedText append(String s) {
      if (this.text == null) {
         this.text = s;
      } else if (s != null) {
         this.text = this.text + s;
      }

      return this;
   }

   HighlightedText substring(int i, int j) {
      int k = this.length();
      if (i > k) {
         i = k;
      }

      if (j > k) {
         j = k;
      }

      HighlightedText highlightedtext1 = new HighlightedText();
      if (this.text != null) {
         highlightedtext1.text = this.text.substring(i, j);
      }

      if (this.layers != null) {
         IntervalSet intervalset = IntervalSet.range(i, j - i);
         int l = this.layerCount();
         highlightedtext1.layers = new Vector();

         for (int i1 = 0; i1 < l; i1++) {
            IntervalSet intervalset1 = (IntervalSet)this.layers.elementAt(i1);
            highlightedtext1.layers.addElement(intervalset1 == null ? null : intervalset1.intersect(intervalset).shift(-i));
         }
      }

      return highlightedtext1;
   }

   HighlightedText substring(int i) {
      return this.substring(i, this.length());
   }

   boolean hasHighlights() {
      int i = this.layerCount();

      for (int j = 0; j < i; j++) {
         IntervalSet intervalset = (IntervalSet)this.layers.elementAt(j);
         if (intervalset != null && !intervalset.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   boolean sharesHighlightLayer(HighlightedText highlightedtext1) {
      int i = Math.min(this.layerCount(), highlightedtext1.layerCount());

      for (int j = 0; j < i; j++) {
         IntervalSet intervalset = (IntervalSet)this.layers.elementAt(j);
         IntervalSet intervalset1 = (IntervalSet)highlightedtext1.layers.elementAt(j);
         if (intervalset != null && !intervalset.isEmpty() && intervalset1 != null && !intervalset1.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof HighlightedText)) {
         return false;
      } else {
         HighlightedText highlightedtext3 = (HighlightedText)object;
         if (this.length() == 0 ? highlightedtext3.length() == 0 : this.text.equals(highlightedtext3.text)) {
            HighlightedText highlightedtext1;
            HighlightedText highlightedtext2;
            if (this.layerCount() < highlightedtext3.layerCount()) {
               highlightedtext1 = this;
               highlightedtext2 = highlightedtext3;
            } else {
               highlightedtext1 = highlightedtext3;
               highlightedtext2 = this;
            }

            int j = highlightedtext1.layerCount();
            int k = highlightedtext2.layerCount();

            for (int i = 0; i < j; i++) {
               IntervalSet intervalset = (IntervalSet)highlightedtext1.layers.elementAt(i);
               IntervalSet intervalset1 = (IntervalSet)highlightedtext2.layers.elementAt(i);
               boolean flag = intervalset == null || intervalset.isEmpty();
               boolean flag1 = intervalset1 == null || intervalset1.isEmpty();
               if ((!flag || !flag1) && (flag || flag1 || !intervalset.equals(intervalset1))) {
                  return false;
               }
            }

            for (int l = j; l < k; l++) {
               IntervalSet intervalset2 = (IntervalSet)highlightedtext2.layers.elementAt(l);
               if (intervalset2 != null && !intervalset2.isEmpty()) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.length() == 0 ? 0 : this.text.hashCode();
      int j = this.layerCount();

      for (int k = 0; k < j; k++) {
         i *= 40701;
         IntervalSet intervalset = (IntervalSet)this.layers.elementAt(k);
         if (intervalset != null && !intervalset.isEmpty()) {
            i += intervalset.hashCode();
         }
      }

      return i;
   }
}
