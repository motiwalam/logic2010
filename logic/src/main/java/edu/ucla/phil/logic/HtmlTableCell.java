package edu.ucla.phil.logic;

import java.awt.Rectangle;

class HtmlTableCell extends Rectangle {
   HtmlTableCell next;
   Object content;

   HtmlTableCell(int i, int j, int k, Object object) {
      super(j, k);
      this.y = i;
      this.content = object;
      this.next = null;
   }

   HtmlTableCell(int i, Object object) {
      this(i, 1, 1, object);
   }

   HtmlTableCell insertInto(HtmlTableCell htmltablecell1) {
      HtmlTableCell htmltablecell2 = this;

      while (htmltablecell2 != null && htmltablecell2.y + htmltablecell2.height <= htmltablecell1.y) {
         htmltablecell2 = htmltablecell2.next;
      }

      if (htmltablecell2 != null && htmltablecell2.x <= 0) {
         HtmlTableCell htmltablecell3 = htmltablecell2;

         while (true) {
            while (htmltablecell2.next != null && htmltablecell2.next.y + htmltablecell2.next.height <= htmltablecell1.y) {
               htmltablecell2.next = htmltablecell2.next.next;
            }

            if (htmltablecell2.next == null || htmltablecell2.next.x > htmltablecell2.x + htmltablecell2.width) {
               htmltablecell1.next = htmltablecell2.next;
               htmltablecell2.next = htmltablecell1;
               htmltablecell1.x = htmltablecell2.x + htmltablecell2.width;
               return htmltablecell3;
            }

            htmltablecell2 = htmltablecell2.next;
         }
      } else {
         htmltablecell1.next = htmltablecell2;
         htmltablecell1.x = 0;
         return htmltablecell1;
      }
   }

   @Override
   public String toString() {
      return "\r\nHTableCell[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + ",content=" + this.content + "]";
   }
}
