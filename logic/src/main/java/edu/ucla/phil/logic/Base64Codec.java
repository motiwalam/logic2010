package edu.ucla.phil.logic;

public class Base64Codec {
   int length;
   byte[] bytes;
   private int decodeState;
   private byte pendingBits;
   static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
   static final String[] PADDING = new String[]{"", "==", "="};

   public Base64Codec() {
      this.reset();
   }

   public Base64Codec(byte[] abyte) {
      this();
      this.addBytes(abyte);
   }

   public Base64Codec(byte[] abyte, int i, int j) {
      this();
      this.addBytes(abyte, i, j);
   }

   public Base64Codec(String s) {
      this();
      this.addBase64String(s);
   }

   public void reset() {
      this.length = 0;
      this.bytes = new byte[3];
      this.decodeState = 0;
      this.pendingBits = 0;
   }

   private void ensureCapacity(int i) {
      int j = this.bytes.length;
      if (i > j) {
         int k = j;

         while (i > k) {
            k *= 2;
         }

         byte[] abyte = new byte[k];
         System.arraycopy(this.bytes, 0, abyte, 0, j);
         this.bytes = abyte;
      }
   }

   public void addBytes(byte[] abyte) {
      if (abyte != null) {
         this.addBytes(abyte, 0, abyte.length);
      }
   }

   public void addBytes(byte[] abyte, int i, int j) {
      this.ensureCapacity(this.length + j);
      System.arraycopy(abyte, i, this.bytes, this.length, j);
      this.length += j;
   }

   public byte[] getBytes() {
      return this.getBytes(false);
   }

   public byte[] getBytes(boolean flag) {
      byte[] abyte = new byte[this.length];
      System.arraycopy(this.bytes, 0, abyte, 0, this.length);
      if (flag) {
         this.length = 0;
      }

      return abyte;
   }

   public void addBase64Char(char c0) {
      int i = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c0);
      if (i != -1) {
         if (this.decodeState != 0) {
            this.ensureCapacity(this.length + 1);
         }

         if (this.decodeState == 0) {
            this.pendingBits = (byte)(i << 2);
            this.decodeState = 1;
         } else if (this.decodeState == 1) {
            this.bytes[this.length++] = (byte)(this.pendingBits | i >> 4);
            this.pendingBits = (byte)(i << 4);
            this.decodeState = 2;
         } else if (this.decodeState == 2) {
            this.bytes[this.length++] = (byte)(this.pendingBits | i >> 2);
            this.pendingBits = (byte)(i << 6);
            this.decodeState = 3;
         } else if (this.decodeState == 3) {
            this.bytes[this.length++] = (byte)(this.pendingBits | i);
            this.decodeState = 0;
         }
      }
   }

   public void addBase64String(String s) {
      if (s != null) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            this.addBase64Char(s.charAt(j));
         }
      }
   }

   @Override
   public String toString() {
      return this.encode(false, true);
   }

   public String encodeAll(boolean flag) {
      return this.encode(false, flag);
   }

   public String encodeChunk(boolean flag) {
      return this.encode(flag, true);
   }

   public String encode(boolean flag, boolean flag1) {
      StringBuffer stringbuffer = new StringBuffer();
      byte b0 = 0;
      int j = flag ? this.length / 3 * 3 : this.length;
      byte b1 = 0;

      for (int i = 0; i < j; i++) {
         int k = this.bytes[i] & 255;
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
            stringbuffer.append(PADDING[b0]);
         }
      }

      if (flag) {
         for (int l = j; l < this.length; l++) {
            this.bytes[l - j] = this.bytes[l];
         }

         this.length -= j;
      }

      return stringbuffer.toString();
   }
}
