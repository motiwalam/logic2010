package edu.ucla.phil.logic;

import java.awt.Rectangle;
import java.util.Vector;

class HtmlTableParser extends Vector {
   HtmlTableCell bounds = null;

   void addCell(HtmlTableCell htmltablecell) {
      this.addElement(htmltablecell);
      this.bounds = this.bounds == null ? htmltablecell : this.bounds.insertInto(htmltablecell);
   }

   int parseTable(String s, int i) {
      int j = s.length();
      String s1 = System.getProperty("line.separator");
      this.setSize(0);
      this.bounds = null;
      int k = 0;
      int l = HtmlTag.findTagEnd(s, HtmlTag.findTag("table", s, i));
      if (l == -1) {
         return -1;
      } else {
         int i1 = HtmlTag.findTag("/table", s, l);
         if (i1 == -1) {
            return -1;
         } else {
            for (int j1 = HtmlTag.findTagEnd(s, HtmlTag.findTag("tr", s, l, i1)); j1 != -1; k++) {
               int k1 = HtmlTag.findTag("/tr", s, j1, i1);
               if (k1 == -1) {
                  return -1;
               }

               int l1 = HtmlTag.findTag("td", s, j1, k1);

               while (l1 != -1) {
                  HtmlTag htmltag = HtmlTag.parseTagAt(s, l1);
                  int i2 = htmltag.getIntAttribute("colspan", 1);
                  int j2 = htmltag.getIntAttribute("rowspan", 1);
                  int i3 = HtmlTag.findTagEnd(s, l1);
                  int k2 = HtmlTag.findTag("/td", s, i3, k1);
                  if (k2 == -1) {
                     return -1;
                  }

                  String s2 = "";

                  for (int l2 = HtmlTag.findTag(null, s, i3, k2); l2 != -1; l2 = HtmlTag.findTag(null, s, i3, k2)) {
                     s2 = s2 + s.substring(i3, l2);
                     htmltag = HtmlTag.parseTagAt(s, l2);
                     i3 = HtmlTag.findTagEnd(s, l2);
                  }

                  s2 = s2 + s.substring(i3, k2);
                  this.addCell(new HtmlTableCell(k, i2, j2, HtmlTag.decodeEntities(s2)));
                  l1 = HtmlTag.findTag("td", s, HtmlTag.findTagEnd(s, k2), k1);
               }

               j1 = HtmlTag.findTagEnd(s, HtmlTag.findTag("tr", s, HtmlTag.findTagEnd(s, k1), i1));
            }

            return HtmlTag.findTagEnd(s, i1);
         }
      }
   }

   HtmlTableCell getCellAt(int i, int j) {
      int k = this.size();

      for (int l = 0; l < k; l++) {
         HtmlTableCell htmltablecell = (HtmlTableCell)this.elementAt(l);
         if (i >= htmltablecell.x && i < htmltablecell.x + htmltablecell.width && j >= htmltablecell.y && j < htmltablecell.y + htmltablecell.height) {
            return htmltablecell;
         }
      }

      return null;
   }

   Rectangle getBounds() {
      Rectangle rectangle = new Rectangle();
      int i = this.size();

      for (int j = 0; j < i; j++) {
         rectangle.add((Rectangle)this.elementAt(j));
      }

      return rectangle;
   }

   Object[][] toGrid() {
      Rectangle rectangle = this.getBounds();
      Object[][] aobject = new Object[rectangle.height][rectangle.width];
      int i = this.size();

      for (int j = 0; j < i; j++) {
         HtmlTableCell htmltablecell = (HtmlTableCell)this.elementAt(j);
         aobject[htmltablecell.y][htmltablecell.x] = htmltablecell.content;
      }

      return aobject;
   }
}
