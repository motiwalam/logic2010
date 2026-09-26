package edu.ucla.phil.logic;

public class C_BE {
   boolean f242;
   int f243;
   private int f244;
   C_0D[] f245;
   String f246;

   public C_BE() {
      this.m399();
   }

   public C_BE(String s) {
      this();
      int i = s.indexOf(123);
      if (i != -1) {
         this.f246 = s.substring(0, i);
         this.f242 = this.f246.indexOf(126) != -1;
         this.f246 = m409(this.f246, "~", true);
         C_OA c_oa = new C_OA("\\\"");
         c_oa.m1132(s.substring(i + 1));
         C_BE c_be1 = new C_BE();

         while (true) {
            String s1 = c_oa.m1135();
            if (c_oa.m1134() == '\\' || s1.indexOf(125) != -1) {
               break;
            }

            boolean flag = s1.indexOf(126) == -1;
            s1 = c_oa.m1135();
            if (c_oa.m1134() == '\\') {
               break;
            }

            c_be1.m406(s1, flag);
         }

         if ((this.f243 = c_be1.f243) == 0) {
            this.f245 = null;
         } else {
            this.f245 = new C_0D[this.f243];
            System.arraycopy(c_be1.f245, 0, this.f245, 0, this.f243);
         }
      }
   }

   public static C_BE m391(String s) {
      return new C_BE().m406(s, true).m406(s, false);
   }

   public static C_BE m392(String s) {
      return new C_BE().m406(s, true);
   }

   public static C_BE m393(String s) {
      return new C_BE().m406(s, false);
   }

   public C_BE m394(boolean flag) {
      C_BE c_be1 = new C_BE();
      c_be1.f242 = this.f242;
      c_be1.f243 = this.f243;
      c_be1.f246 = this.f246;
      c_be1.f245 = this.f243 == 0 ? null : (flag ? new C_0D[this.f243] : this.f245);
      if (this.f243 != 0 && flag) {
         System.arraycopy(this.f245, 0, c_be1.f245, 0, this.f243);
      }

      return c_be1;
   }

   public C_BE m395(C_BE c_be1) {
      return this.m401().m397(c_be1.m394(false).m401()).m401();
   }

   public C_BE m396(C_BE c_be1) {
      return this.m397(c_be1.m394(false).m401());
   }

   public C_BE m397(C_BE c_be1) {
      if (c_be1.f242) {
         this.f246 = this.f242 ? m409(this.f246 + c_be1.f246, "", true) : m409(this.f246, c_be1.f246, true);
      } else {
         this.f246 = this.f242 ? m409(c_be1.f246, this.f246, true) : m409(this.f246, c_be1.f246, false);
      }

      if (c_be1.f243 == 0) {
         return c_be1.f242 ? this : this.m400(false);
      } else {
         C_BE c_be2 = new C_BE();
         C_BE c_be3 = this.m394(false);

         C_BE c_be4;
         for (c_be4 = c_be1.m394(false); c_be3.f244 < c_be3.f243; c_be3.m398()) {
            C_0D c_0d = c_be3.f245[c_be3.f244];
            C_0D c_0d1 = c_be4.f245[c_be4.f244];
            int i = c_0d.m60(c_0d1);
            if (i > 0 || i == 0 && !c_be3.f242 && c_be4.f242) {
               C_BE c_be5 = c_be3;
               c_be3 = c_be4;
               c_be4 = c_be5;
               c_0d = c_0d1;
            }

            if (c_be4.f242) {
               c_be2.m407(c_0d);
            }
         }

         if (c_be3.f242) {
            for (int j = c_be4.f244; j < c_be4.f243; j++) {
               c_be2.m407(c_be4.f245[j]);
            }
         }

         this.f242 = this.f242 & c_be1.f242;
         this.f243 = c_be2.f243;
         if (this.f243 == 0) {
            this.f245 = null;
         } else {
            this.f245 = new C_0D[this.f243];
            System.arraycopy(c_be2.f245, 0, this.f245, 0, this.f243);
         }

         return this;
      }
   }

   private void m398() {
      this.f244++;
      this.f242 = !this.f242;
   }

   public C_BE m399() {
      return this.m400(true);
   }

   private C_BE m400(boolean flag) {
      this.f242 = false;
      this.f243 = 0;
      this.f244 = 0;
      this.f245 = null;
      if (flag) {
         this.f246 = "";
      }

      return this;
   }

   public C_BE m401() {
      this.f242 = !this.f242;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof C_BE)) {
         return false;
      } else {
         C_BE c_be1 = (C_BE)object;
         if (this.f242 == c_be1.f242 && this.f243 == c_be1.f243) {
            if (this.f246.length() != c_be1.f246.length()) {
               return false;
            } else if (m409(this.f246, c_be1.f246, true).length() != 0) {
               return false;
            } else {
               for (int i = 0; i < this.f243; i++) {
                  if (!this.f245[i].equals(c_be1.f245[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int i = this.f242 ? 1 : 0;

      for (int j = 0; j < this.f243; j++) {
         C_0D c_0d = this.f245[j];
         i = i * 40503 + (c_0d.f26 ? 1 : 0) + c_0d.f25.hashCode();
      }

      return i;
   }

   public boolean m402() {
      return this.f243 == 0 && !this.f242 && this.f246.length() == 0;
   }

   public C_BE m403(String s) {
      if (s != null && !s.equals("")) {
         for (int i = 0; i < this.f243; i++) {
            this.f245[i].f25 = s + this.f245[i].f25;
         }
      }

      return this;
   }

   public boolean m404(String s) {
      return s == null ? false : !m391(s).m397(this).m402();
   }

   public boolean m405(char c0) {
      return this.f246.indexOf(c0) == -1 ? this.f242 : !this.f242;
   }

   @Override
   public String toString() {
      String s = "";

      for (int i = 0; i < this.f243; i++) {
         s = s + (i == 0 ? "" : ",") + this.f245[i];
      }

      return (this.f242 ? "~" : "") + this.f246 + "{" + s + "}";
   }

   C_BE m406(String s, boolean flag) {
      return this.m407(new C_0D(s, flag));
   }

   C_BE m407(C_0D c_0d) {
      if (c_0d != null) {
         for (int i = this.f243; i >= 0; i--) {
            int j;
            if (i == 0 || (j = this.f245[i - 1].m60(c_0d)) < 0) {
               this.m408(this.f243 + 1);
               if (i < this.f243) {
                  System.arraycopy(this.f245, i, this.f245, i + 1, this.f243 - i);
               }

               this.f245[i] = c_0d;
               this.f243++;
               break;
            }

            if (j == 0) {
               if (i < this.f243) {
                  System.arraycopy(this.f245, i, this.f245, i - 1, this.f243 - i);
               }

               this.f243--;
               break;
            }
         }
      }

      return this;
   }

   void m408(int i) {
      int j = this.f245 == null ? 0 : this.f245.length;
      if (i > j) {
         if (j == 0) {
            j = 1;
         }

         while (i > j) {
            j *= 2;
         }

         C_0D[] ac_0d = new C_0D[j];
         if (this.f245 != null) {
            System.arraycopy(this.f245, 0, ac_0d, 0, this.f243);
         }

         this.f245 = ac_0d;
      }
   }

   static String m409(String s, String s1, boolean flag) {
      String s2 = "";
      if (flag || s1.length() != 0) {
         int i = s.length();

         for (int j = 0; j < i; j++) {
            char c0 = s.charAt(j);
            if (s2.indexOf(c0) == -1 && (s1.indexOf(c0) == -1 ? flag : !flag)) {
               s2 = s2 + c0;
            }
         }
      }

      return s2;
   }
}
