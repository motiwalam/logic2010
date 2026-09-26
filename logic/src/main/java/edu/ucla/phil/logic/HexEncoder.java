package edu.ucla.phil.logic;

public class HexEncoder {
   int f1280;
   byte[] f1281;
   private int f1282;
   private byte f1283;
   static final String f1284 = "0123456789ABCDEF";

   public HexEncoder() {
      this.m1939();
   }

   public HexEncoder(byte[] abyte) {
      this();
      this.m1941(abyte);
   }

   public HexEncoder(byte[] abyte, int i, int j) {
      this();
      this.m1942(abyte, i, j);
   }

   public HexEncoder(String s) {
      this();
      this.m1946(s);
   }

   public void m1939() {
      this.f1280 = 0;
      this.f1281 = new byte[3];
      this.f1282 = 0;
      this.f1283 = 0;
   }

   private void m1940(int i) {
      int j = this.f1281.length;
      if (i > j) {
         int k = j;

         while (i > k) {
            k *= 2;
         }

         byte[] abyte = new byte[k];
         System.arraycopy(this.f1281, 0, abyte, 0, j);
         this.f1281 = abyte;
      }
   }

   public void m1941(byte[] abyte) {
      if (abyte != null) {
         this.m1942(abyte, 0, abyte.length);
      }
   }

   public void m1942(byte[] abyte, int i, int j) {
      this.m1940(this.f1280 + j);
      System.arraycopy(abyte, i, this.f1281, this.f1280, j);
      this.f1280 += j;
   }

   public byte[] m1943() {
      return this.m1944(false);
   }

   public byte[] m1944(boolean flag) {
      byte[] abyte = new byte[this.f1280];
      System.arraycopy(this.f1281, 0, abyte, 0, this.f1280);
      if (flag) {
         this.f1280 = 0;
      }

      return abyte;
   }

   public void m1945(char c0) {
      int i = "0123456789ABCDEF".indexOf(Character.toUpperCase(c0));
      if (i != -1) {
         if (this.f1282 != 0) {
            this.m1940(this.f1280 + 1);
         }

         if (this.f1282 == 0) {
            this.f1283 = (byte)(i << 4);
            this.f1282 = 1;
         } else if (this.f1282 == 1) {
            this.f1281[this.f1280++] = (byte)(this.f1283 | i);
            this.f1282 = 0;
         }
      }
   }

   public void m1946(String s) {
      if (s != null) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            this.m1945(s.charAt(j));
         }
      }
   }

   @Override
   public String toString() {
      return this.m1949(false, false);
   }

   public String m1947(boolean flag) {
      return this.m1949(false, flag);
   }

   public String m1948(boolean flag) {
      return this.m1949(flag, false);
   }

   public String m1949(boolean flag, boolean flag1) {
      String s = "";

      for (int i = 0; i < this.f1280; i++) {
         int j = this.f1281[i] & 255;
         s = s + "" + "0123456789ABCDEF".charAt(j >> 4) + "0123456789ABCDEF".charAt(j & 15);
      }

      if (flag) {
         this.f1280 = 0;
      }

      return flag1 ? s.toLowerCase() : s;
   }
}
