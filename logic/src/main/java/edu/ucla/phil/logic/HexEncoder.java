package edu.ucla.phil.logic;

public class HexEncoder {
   int length;
   byte[] bytes;
   private int nibbleState;
   private byte pendingNibble;
   static final String HEX_DIGITS = "0123456789ABCDEF";

   public HexEncoder() {
      this.reset();
   }

   public HexEncoder(byte[] abyte) {
      this();
      this.addBytes(abyte);
   }

   public HexEncoder(byte[] abyte, int i, int j) {
      this();
      this.addBytes(abyte, i, j);
   }

   public HexEncoder(String s) {
      this();
      this.addHexString(s);
   }

   public void reset() {
      this.length = 0;
      this.bytes = new byte[3];
      this.nibbleState = 0;
      this.pendingNibble = 0;
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

   public void addHexChar(char c0) {
      int i = "0123456789ABCDEF".indexOf(Character.toUpperCase(c0));
      if (i != -1) {
         if (this.nibbleState != 0) {
            this.ensureCapacity(this.length + 1);
         }

         if (this.nibbleState == 0) {
            this.pendingNibble = (byte)(i << 4);
            this.nibbleState = 1;
         } else if (this.nibbleState == 1) {
            this.bytes[this.length++] = (byte)(this.pendingNibble | i);
            this.nibbleState = 0;
         }
      }
   }

   public void addHexString(String s) {
      if (s != null) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            this.addHexChar(s.charAt(j));
         }
      }
   }

   @Override
   public String toString() {
      return this.toHexString(false, false);
   }

   public String toHexString(boolean flag) {
      return this.toHexString(false, flag);
   }

   public String toHexStringAndReset(boolean flag) {
      return this.toHexString(flag, false);
   }

   public String toHexString(boolean flag, boolean flag1) {
      String s = "";

      for (int i = 0; i < this.length; i++) {
         int j = this.bytes[i] & 255;
         s = s + "" + "0123456789ABCDEF".charAt(j >> 4) + "0123456789ABCDEF".charAt(j & 15);
      }

      if (flag) {
         this.length = 0;
      }

      return flag1 ? s.toLowerCase() : s;
   }
}
