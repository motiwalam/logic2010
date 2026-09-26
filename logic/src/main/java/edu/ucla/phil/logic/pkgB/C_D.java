package edu.ucla.phil.logic.pkgB;

import java.io.IOException;

public class C_D implements C_F {
   static final int[] f174 = new int[]{1, 2, 13, 14, 9, 10, 17, 18};
   public static final String[] f175 = new String[]{
      "", null, null, null, "\n", null, null, null, null, null, "<->", "->", "&", "|", "=", "<>", "[m]", "~", "@", "!", "(", ")", "%"
   };
   public static final String[] f176 = new String[]{"DEFAULT"};
   static final long[] f177 = new long[]{8388593L};
   static final long[] f178 = new long[]{14L};
   private static C_B f179;
   private static final int[] f180 = new int[22];
   private static final int[] f181 = new int[44];
   protected static char f182;
   static int f183 = 0;
   static int f184 = 0;
   static int f185;
   static int f186;
   static int f187;
   static int f188;

   private static final int m259(int i, long j) {
      switch (i) {
         case 0:
            if ((j & 65536L) != 0L) {
               return 5;
            }

            return -1;
         default:
            return -1;
      }
   }

   private static final int m260(int i, long j) {
      return m271(m259(i, j), i + 1);
   }

   private static final int m261(int i, int j) {
      f188 = j;
      f187 = i;
      return i + 1;
   }

   private static final int m262(int i, int j, int k) {
      f188 = j;
      f187 = i;

      try {
         f182 = C_B.m241();
      } catch (IOException ioexception) {
         return i + 1;
      }

      return m271(k, i + 1);
   }

   private static final int m263() {
      switch (f182) {
         case '\n':
            return m261(0, 4);
         case '!':
            return m261(0, 19);
         case '%':
            return m261(0, 22);
         case '&':
            return m261(0, 12);
         case '(':
            return m261(0, 20);
         case ')':
            return m261(0, 21);
         case '-':
            return m264(2048L);
         case '<':
            return m264(33792L);
         case '=':
            return m261(0, 14);
         case '@':
            return m261(0, 18);
         case '[':
            return m264(65536L);
         case '|':
            return m261(0, 13);
         case '~':
            return m261(0, 17);
         default:
            return m271(0, 0);
      }
   }

   private static final int m264(long i) {
      try {
         f182 = C_B.m241();
      } catch (IOException ioexception) {
         m259(0, i);
         return 1;
      }

      switch (f182) {
         case '-':
            return m265(i, 1024L);
         case '>':
            if ((i & 2048L) != 0L) {
               return m261(1, 11);
            } else if ((i & 32768L) != 0L) {
               return m261(1, 15);
            }
         default:
            return m260(0, i);
         case 'm':
            return m265(i, 65536L);
      }
   }

   private static final int m265(long i, long j) {
      if ((j = j & i) == 0L) {
         return m260(0, i);
      } else {
         try {
            f182 = C_B.m241();
         } catch (IOException ioexception) {
            m259(1, j);
            return 2;
         }

         switch (f182) {
            case '>':
               if ((j & 1024L) != 0L) {
                  return m261(2, 10);
               }
               break;
            case ']':
               if ((j & 65536L) != 0L) {
                  return m261(2, 16);
               }
         }

         return m260(1, j);
      }
   }

   private static final void m266(int i) {
      if (f180[i] != f186) {
         f181[f185++] = i;
         f180[i] = f186;
      }
   }

   private static final void m267(int i, int j) {
      do {
         f181[f185++] = f174[i];
      } while (i++ != j);
   }

   private static final void m268(int i, int j) {
      m266(i);
      m266(j);
   }

   private static final void m269(int i, int j) {
      do {
         m266(f174[i]);
      } while (i++ != j);
   }

   private static final void m270(int i) {
      m266(f174[i]);
      m266(f174[i + 1]);
   }

   private static final int m271(int i, int j) {
      int k = 0;
      f185 = 22;
      int l = 1;
      f181[0] = i;
      int i1 = 2147483647;

      while (true) {
         if (++f186 == 2147483647) {
            m273();
         }

         if (f182 < '@') {
            long i2 = 1L << f182;

            do {
               l--;
               switch (f181[l]) {
                  case 0:
                     if (f182 == '?') {
                        i1 = 9;
                        f181[f185++] = 21;
                     }
                     break;
                  case 1:
                     if (f182 == '0' && i1 > 5) {
                        i1 = 5;
                     }
                     break;
                  case 2:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m266(3);
                     }
                     break;
                  case 3:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m266(3);
                     }
                  case 4:
                  case 7:
                  case 8:
                  case 12:
                  case 16:
                  default:
                     break;
                  case 5:
                     if ((287667426198290432L & i2) != 0L) {
                        m268(6, 7);
                     }
                     break;
                  case 6:
                     if ((287948901175001088L & i2) != 0L) {
                        m268(6, 7);
                     }
                     break;
                  case 9:
                     if (f182 == '0' && i1 > 6) {
                        i1 = 6;
                     }
                     break;
                  case 10:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m266(11);
                     }
                     break;
                  case 11:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m266(11);
                     }
                     break;
                  case 13:
                     if (f182 == '0' && i1 > 7) {
                        i1 = 7;
                     }
                     break;
                  case 14:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m266(15);
                     }
                     break;
                  case 15:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m266(15);
                     }
                     break;
                  case 17:
                     if (f182 == '0' && i1 > 8) {
                        i1 = 8;
                     }
                     break;
                  case 18:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m266(19);
                     }
                     break;
                  case 19:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m266(19);
                     }
               }
            } while (l != k);
         } else if (f182 < 128) {
            long l1 = 1L << (f182 & '?');

            do {
               l--;
               switch (f181[l]) {
                  case 0:
                     if ((576460743713488896L & l1) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m267(0, 1);
                     } else if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m267(2, 3);
                     } else if ((65472L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m267(4, 5);
                     } else if ((62L & l1) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m267(6, 7);
                     } else if (f182 == '{') {
                        f181[f185++] = 5;
                     }
                  case 1:
                  case 2:
                  case 3:
                  case 5:
                  case 6:
                  case 9:
                  case 10:
                  case 11:
                  case 13:
                  case 14:
                  case 15:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  default:
                     break;
                  case 4:
                     if (f182 == '{') {
                        f181[f185++] = 5;
                     }
                     break;
                  case 7:
                     if (f182 == '}') {
                        i1 = 5;
                     }
                     break;
                  case 8:
                     if ((65472L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m267(4, 5);
                     }
                     break;
                  case 12:
                     if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m267(2, 3);
                     }
                     break;
                  case 16:
                     if ((62L & l1) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m267(6, 7);
                     }
                     break;
                  case 21:
                     if ((134217726L & l1) != 0L) {
                        if (i1 > 9) {
                           i1 = 9;
                        }

                        f181[f185++] = 21;
                     }
               }
            } while (l != k);
         } else {
            int j1 = (f182 & 255) >> 6;
            long k1 = 1L << (f182 & '?');

            do {
               l--;
               switch (f181[l]) {
               }
            } while (l != k);
         }

         if (i1 != 2147483647) {
            f188 = i1;
            f187 = j;
            i1 = 2147483647;
         }

         j++;
         int j2 = l = f185;
         f185 = k;
         if (j2 == (k = 22 - k)) {
            return j;
         }

         try {
            f182 = C_B.m241();
         } catch (IOException ioexception) {
            return j;
         }
      }
   }

   public C_D(C_B c_b) {
      if (f179 != null) {
         throw new C_C("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", 1);
      } else {
         f179 = c_b;
      }
   }

   public C_D(C_B c_b, int i) {
      this(c_b);
      m275(i);
   }

   public static void m272(C_B c_b) {
      f185 = 0;
      f187 = 0;
      f183 = f184;
      f179 = c_b;
      m273();
   }

   private static final void m273() {
      f186 = -2147483647;
      int i = 22;

      while (i-- > 0) {
         f180[i] = -2147483648;
      }
   }

   public static void m274(C_B c_b, int i) {
      m272(c_b);
      m275(i);
   }

   public static void m275(int i) {
      if (i < 1 && i >= 0) {
         f183 = i;
      } else {
         throw new C_C("Error: Ignoring invalid lexical state : " + i + ". State unchanged.", 2);
      }
   }

   private static final C_G m276() {
      C_G c_g = C_G.m373(f188);
      c_g.f227 = f188;
      String s = f175[f188];
      c_g.f232 = s == null ? C_B.m253() : s;
      c_g.f228 = C_B.m247();
      c_g.f229 = C_B.m246();
      c_g.f230 = C_B.m245();
      c_g.f231 = C_B.m244();
      return c_g;
   }

   public static final C_G m277() {
      Object object = null;
      int i = 0;

      while (true) {
         try {
            f182 = C_B.m239();
         } catch (IOException ioexception) {
            f188 = 0;
            return m276();
         }

         try {
            while (f182 <= ' ' && (4294976000L & 1L << f182) != 0L) {
               f182 = C_B.m239();
            }
         } catch (IOException ioexception2) {
            continue;
         }

         f188 = 2147483647;
         f187 = 0;
         i = m263();
         if (f188 == 2147483647) {
            int j = C_B.m245();
            int k = C_B.m244();
            String s = null;
            boolean flag = false;

            try {
               C_B.m241();
               C_B.m248(1);
            } catch (IOException ioexception1) {
               flag = true;
               s = i <= 1 ? "" : C_B.m253();
               if (f182 != '\n' && f182 != '\r') {
                  k++;
               } else {
                  j++;
                  k = 0;
               }
            }

            if (!flag) {
               C_B.m248(1);
               s = i <= 1 ? "" : C_B.m253();
            }

            throw new C_C(flag, f183, j, k, s, f182, 0);
         }

         if (f187 + 1 < i) {
            C_B.m248(i - f187 - 1);
         }

         if ((f177[f188 >> 6] & 1L << (f188 & 63)) != 0L) {
            return m276();
         }
      }
   }
}
