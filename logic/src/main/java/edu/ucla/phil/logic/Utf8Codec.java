package edu.ucla.phil.logic;

public class Utf8Codec {
   String text;
   char partialChar;
   int pendingBytes;

   public Utf8Codec() {
      this.reset();
   }

   public Utf8Codec(String s) {
      this();
      this.text = s;
   }

   public Utf8Codec(byte[] abyte) {
      this();
      this.decode(abyte);
   }

   public Utf8Codec(byte[] abyte, int i, int j) {
      this();
      this.decode(abyte, i, j);
   }

   void reset() {
      this.text = "";
      this.partialChar = 0;
      this.pendingBytes = 0;
   }

   public void append(String s) {
      if (this.pendingBytes != 0) {
         throw new IllegalStateException();
      } else {
         this.text = this.text + s;
      }
   }

   public void decode(byte[] abyte) {
      this.decode(abyte, 0, abyte.length);
   }

   public void decode(byte[] abyte, int i, int j) {
      while (i < j) {
         char c0 = (char)(abyte[i] & 255);
         i++;
         if (this.pendingBytes == 0) {
            if (c0 < 128) {
               this.text = this.text + c0;
            } else {
               if (c0 < 192) {
                  throw new IllegalArgumentException();
               }

               if (c0 < 224) {
                  this.partialChar = (char)((c0 & 31) << 6);
                  this.pendingBytes = 1;
               } else {
                  if (c0 >= 240) {
                     throw new IllegalArgumentException();
                  }

                  this.partialChar = (char)((c0 & 15) << 12);
                  this.pendingBytes = 2;
               }
            }
         } else {
            if (c0 < 128 || c0 >= 192) {
               throw new IllegalArgumentException();
            }

            if (this.pendingBytes == 1) {
               this.text = this.text + (char)(this.partialChar | c0 & '?');
               this.pendingBytes = 0;
            } else if (this.pendingBytes == 2) {
               this.partialChar = (char)(this.partialChar | (c0 & '?') << 6);
               this.pendingBytes = 1;
            }
         }
      }
   }

   public byte[] encode() {
      return this.encode(true);
   }

   public byte[] encode(boolean flag) {
      int j = this.text.length();
      String s = "";

      for (int i = 0; i < j; i++) {
         char c0 = this.text.charAt(i);
         if (c0 == 0 && flag) {
            s = s + "À\u0080";
         } else if (c0 < 128) {
            s = s + c0;
         } else if (c0 < 2048) {
            s = s + (char)(192 | c0 >> 6 & 31);
            s = s + (char)(128 | c0 & '?');
         } else {
            s = s + (char)(224 | c0 >> '\f' & 15);
            s = s + (char)(128 | c0 >> 6 & 63);
            s = s + (char)(128 | c0 & '?');
         }
      }

      byte[] abyte = new byte[j = s.length()];

      for (int k = 0; k < j; k++) {
         abyte[k] = (byte)s.charAt(k);
      }

      return abyte;
   }

   @Override
   public String toString() {
      return this.text;
   }
}
