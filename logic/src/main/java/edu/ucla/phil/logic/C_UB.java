package edu.ucla.phil.logic;

public class C_UB {
   String f797;
   char f798;
   int f799;

   public C_UB() {
      this.m1333();
   }

   public C_UB(String s) {
      this();
      this.f797 = s;
   }

   public C_UB(byte[] abyte) {
      this();
      this.m1335(abyte);
   }

   public C_UB(byte[] abyte, int i, int j) {
      this();
      this.m1336(abyte, i, j);
   }

   void m1333() {
      this.f797 = "";
      this.f798 = 0;
      this.f799 = 0;
   }

   public void m1334(String s) {
      if (this.f799 != 0) {
         throw new IllegalStateException();
      } else {
         this.f797 = this.f797 + s;
      }
   }

   public void m1335(byte[] abyte) {
      this.m1336(abyte, 0, abyte.length);
   }

   public void m1336(byte[] abyte, int i, int j) {
      while (i < j) {
         char c0 = (char)(abyte[i] & 255);
         i++;
         if (this.f799 == 0) {
            if (c0 < 128) {
               this.f797 = this.f797 + c0;
            } else {
               if (c0 < 192) {
                  throw new IllegalArgumentException();
               }

               if (c0 < 224) {
                  this.f798 = (char)((c0 & 31) << 6);
                  this.f799 = 1;
               } else {
                  if (c0 >= 240) {
                     throw new IllegalArgumentException();
                  }

                  this.f798 = (char)((c0 & 15) << 12);
                  this.f799 = 2;
               }
            }
         } else {
            if (c0 < 128 || c0 >= 192) {
               throw new IllegalArgumentException();
            }

            if (this.f799 == 1) {
               this.f797 = this.f797 + (char)(this.f798 | c0 & '?');
               this.f799 = 0;
            } else if (this.f799 == 2) {
               this.f798 = (char)(this.f798 | (c0 & '?') << 6);
               this.f799 = 1;
            }
         }
      }
   }

   public byte[] m1337() {
      return this.m1338(true);
   }

   public byte[] m1338(boolean flag) {
      int j = this.f797.length();
      String s = "";

      for (int i = 0; i < j; i++) {
         char c0 = this.f797.charAt(i);
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
      return this.f797;
   }
}
