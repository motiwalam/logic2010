package edu.ucla.phil.logic;

public class Base64Codec {
   int f1319;
   byte[] f1320;
   private int f1321;
   private byte f1322;
   static final String f1323 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
   static final String[] f1324 = new String[]{"", "==", "="};

   public Base64Codec() {
      this.m1996();
   }

   public Base64Codec(byte[] abyte) {
      this();
      this.m1998(abyte);
   }

   public Base64Codec(byte[] abyte, int i, int j) {
      this();
      this.m1999(abyte, i, j);
   }

   public Base64Codec(String s) {
      this();
      this.m2003(s);
   }

   public void m1996() {
      this.f1319 = 0;
      this.f1320 = new byte[3];
      this.f1321 = 0;
      this.f1322 = 0;
   }

   private void m1997(int i) {
      int j = this.f1320.length;
      if (i > j) {
         int k = j;

         while (i > k) {
            k *= 2;
         }

         byte[] abyte = new byte[k];
         System.arraycopy(this.f1320, 0, abyte, 0, j);
         this.f1320 = abyte;
      }
   }

   public void m1998(byte[] abyte) {
      if (abyte != null) {
         this.m1999(abyte, 0, abyte.length);
      }
   }

   public void m1999(byte[] abyte, int i, int j) {
      this.m1997(this.f1319 + j);
      System.arraycopy(abyte, i, this.f1320, this.f1319, j);
      this.f1319 += j;
   }

   public byte[] m2000() {
      return this.m2001(false);
   }

   public byte[] m2001(boolean flag) {
      byte[] abyte = new byte[this.f1319];
      System.arraycopy(this.f1320, 0, abyte, 0, this.f1319);
      if (flag) {
         this.f1319 = 0;
      }

      return abyte;
   }

   public void m2002(char c0) {
      int i = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c0);
      if (i != -1) {
         if (this.f1321 != 0) {
            this.m1997(this.f1319 + 1);
         }

         if (this.f1321 == 0) {
            this.f1322 = (byte)(i << 2);
            this.f1321 = 1;
         } else if (this.f1321 == 1) {
            this.f1320[this.f1319++] = (byte)(this.f1322 | i >> 4);
            this.f1322 = (byte)(i << 4);
            this.f1321 = 2;
         } else if (this.f1321 == 2) {
            this.f1320[this.f1319++] = (byte)(this.f1322 | i >> 2);
            this.f1322 = (byte)(i << 6);
            this.f1321 = 3;
         } else if (this.f1321 == 3) {
            this.f1320[this.f1319++] = (byte)(this.f1322 | i);
            this.f1321 = 0;
         }
      }
   }

   public void m2003(String s) {
      if (s != null) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            this.m2002(s.charAt(j));
         }
      }
   }

   @Override
   public String toString() {
      return this.m2006(false, true);
   }

   public String m2004(boolean flag) {
      return this.m2006(false, flag);
   }

   public String m2005(boolean flag) {
      return this.m2006(flag, true);
   }

   public String m2006(boolean flag, boolean flag1) {
      StringBuffer stringbuffer = new StringBuffer();
      byte b0 = 0;
      int j = flag ? this.f1319 / 3 * 3 : this.f1319;
      byte b1 = 0;

      for (int i = 0; i < j; i++) {
         int k = this.f1320[i] & 255;
         if (b0 == 0) {
            stringbuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(k >> 2));
            b1 = (byte)(k << 4 & 63);
            b0 = 1;
         } else if (b0 == 1) {
            stringbuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(b1 | k >> 4));
            b1 = (byte)(k << 2 & 63);
            b0 = 2;
         } else if (b0 == 2) {
            stringbuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(b1 | k >> 6));
            stringbuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(k & 63));
            b0 = 0;
         }
      }

      if (b0 != 0) {
         stringbuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(b1));
         if (flag1) {
            stringbuffer.append(f1324[b0]);
         }
      }

      if (flag) {
         for (int l = j; l < this.f1319; l++) {
            this.f1320[l - j] = this.f1320[l];
         }

         this.f1319 -= j;
      }

      return stringbuffer.toString();
   }
}
