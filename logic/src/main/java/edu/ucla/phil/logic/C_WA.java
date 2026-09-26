package edu.ucla.phil.logic;

import java.lang.reflect.Array;

public class C_WA {
   C_EA f853;

   public C_WA(C_EA c_ea) {
      this.f853 = c_ea;
   }

   public int[] m1424(Object[] aobject) {
      int j = aobject.length;
      byte b0 = 1;
      int[] aint = new int[j];
      int[] aint1 = new int[j];
      int i = 0;

      while (i < j) {
         aint[i] = i++;
      }

      while (b0 < j) {
         int k = 0;

         while (k < j) {
            int l = k + b0;
            if (l > j) {
               l = j;
            }

            this.m1426(aobject, aint, k, l, aint1);
            k = l + b0;
         }

         int[] aint2 = aint1;
         aint1 = aint;
         aint = aint2;
         b0 *= 2;
      }

      return aint;
   }

   public Object[] m1425(Object[] aobject) {
      int j = aobject.length;
      int[] aint = this.m1424(aobject);
      Object[] aobject1 = (Object[])Array.newInstance(aobject.getClass().getComponentType(), j);

      for (int i = 0; i < j; i++) {
         aobject1[i] = aobject[aint[i]];
      }

      return aobject1;
   }

   private void m1426(Object[] aobject, int[] aint, int i, int j, int[] aint1) {
      int k = i;
      int l = j - 1;
      int i1 = -1;
      int j1 = aobject.length;
      int k1 = j - i;
      int l1 = k1 < j1 - j ? k1 : j1 - j;
      if (l1 == 0) {
         System.arraycopy(aint, i, aint1, i, k1);
      } else {
         while (k1 > 0) {
            if (i1 != -1 && i >= i1 || !this.f853.m493(aobject[aint[i]], aobject[aint[j]])) {
               j1 = j;
               j = i;
               i = j1;
               int i2 = l1;
               l1 = k1;
               k1 = i2;
               l = j1 + i2 - 1;
               i1 = -1;
            }

            aint1[k] = aint[i];
            k++;
            i++;
            k1--;
            if (l >= i) {
               if (this.f853.m493(aobject[aint[l]], aobject[aint[j]])) {
                  j1 = l - i + 1;
                  System.arraycopy(aint, i, aint1, k, j1);
                  k += j1;
                  i += j1;
                  k1 -= j1;
                  if (k1 == 0) {
                     break;
                  }

                  l = i1;
               } else {
                  i1 = l;
               }

               l = (i + l) / 2;
            }
         }

         System.arraycopy(aint, j, aint1, k, l1);
      }
   }
}
