package edu.ucla.phil.logic.pkgA;

import java.io.IOException;

public class C_D implements C_F {
   static final int[] f55 = new int[]{1, 2, 9, 10, 13, 14, 17, 18};
   public static final String[] f56 = new String[]{
      "", null, null, null, "\n", null, null, null, null, null, "<->", "->", "&", "|", "=", "<>", "[m]", "~", "@", "!", "(", ")", "%"
   };
   public static final String[] f57 = new String[]{"DEFAULT"};
   static final long[] f58 = new long[]{8388593L};
   static final long[] f59 = new long[]{14L};
   private static C_B f60;
   private static final int[] f61 = new int[22];
   private static final int[] f62 = new int[44];
   protected static char f63;
   static int f64 = 0;
   static int f65 = 0;
   static int f66;
   static int f67;
   static int f68;
   static int f69;

   private static final int m95(int i, long j) {
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

   private static final int m96(int i, long j) {
      return m107(m95(i, j), i + 1);
   }

   private static final int m97(int i, int j) {
      f69 = j;
      f68 = i;
      return i + 1;
   }

   private static final int m98(int i, int j, int k) {
      f69 = j;
      f68 = i;

      try {
         f63 = C_B.m77();
      } catch (IOException ioexception) {
         return i + 1;
      }

      return m107(k, i + 1);
   }

   private static final int m99() {
      switch (f63) {
         case '\n':
            return m97(0, 4);
         case '!':
            return m97(0, 19);
         case '%':
            return m97(0, 22);
         case '&':
            return m97(0, 12);
         case '(':
            return m97(0, 20);
         case ')':
            return m97(0, 21);
         case '-':
            return m100(2048L);
         case '<':
            return m100(33792L);
         case '=':
            return m97(0, 14);
         case '@':
            return m97(0, 18);
         case '[':
            return m100(65536L);
         case '|':
            return m97(0, 13);
         case '~':
            return m97(0, 17);
         default:
            return m107(0, 0);
      }
   }

   private static final int m100(long i) {
      try {
         f63 = C_B.m77();
      } catch (IOException ioexception) {
         m95(0, i);
         return 1;
      }

      switch (f63) {
         case '-':
            return m101(i, 1024L);
         case '>':
            if ((i & 2048L) != 0L) {
               return m97(1, 11);
            } else if ((i & 32768L) != 0L) {
               return m97(1, 15);
            }
         default:
            return m96(0, i);
         case 'm':
            return m101(i, 65536L);
      }
   }

   private static final int m101(long i, long j) {
      if ((j = j & i) == 0L) {
         return m96(0, i);
      } else {
         try {
            f63 = C_B.m77();
         } catch (IOException ioexception) {
            m95(1, j);
            return 2;
         }

         switch (f63) {
            case '>':
               if ((j & 1024L) != 0L) {
                  return m97(2, 10);
               }
               break;
            case ']':
               if ((j & 65536L) != 0L) {
                  return m97(2, 16);
               }
         }

         return m96(1, j);
      }
   }

   private static final void m102(int i) {
      if (f61[i] != f67) {
         f62[f66++] = i;
         f61[i] = f67;
      }
   }

   private static final void m103(int i, int j) {
      do {
         f62[f66++] = f55[i];
      } while (i++ != j);
   }

   private static final void m104(int i, int j) {
      m102(i);
      m102(j);
   }

   private static final void m105(int i, int j) {
      do {
         m102(f55[i]);
      } while (i++ != j);
   }

   private static final void m106(int i) {
      m102(f55[i]);
      m102(f55[i + 1]);
   }

   private static final int m107(int i, int j) {
      int k = 0;
      f66 = 22;
      int l = 1;
      f62[0] = i;
      int i1 = 2147483647;

      while (true) {
         if (++f67 == 2147483647) {
            m109();
         }

         if (f63 < '@') {
            long i2 = 1L << f63;

            do {
               l--;
               switch (f62[l]) {
                  case 0:
                     if (f63 == '?') {
                        i1 = 9;
                        f62[f66++] = 21;
                     }
                     break;
                  case 1:
                     if (f63 == '0' && i1 > 5) {
                        i1 = 5;
                     }
                     break;
                  case 2:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m102(3);
                     }
                     break;
                  case 3:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m102(3);
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
                        m104(6, 7);
                     }
                     break;
                  case 6:
                     if ((287948901175001088L & i2) != 0L) {
                        m104(6, 7);
                     }
                     break;
                  case 9:
                     if (f63 == '0' && i1 > 6) {
                        i1 = 6;
                     }
                     break;
                  case 10:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m102(11);
                     }
                     break;
                  case 11:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m102(11);
                     }
                     break;
                  case 13:
                     if (f63 == '0' && i1 > 7) {
                        i1 = 7;
                     }
                     break;
                  case 14:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m102(15);
                     }
                     break;
                  case 15:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m102(15);
                     }
                     break;
                  case 17:
                     if (f63 == '0' && i1 > 8) {
                        i1 = 8;
                     }
                     break;
                  case 18:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m102(19);
                     }
                     break;
                  case 19:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m102(19);
                     }
               }
            } while (l != k);
         } else if (f63 < 128) {
            long l1 = 1L << (f63 & '?');

            do {
               l--;
               switch (f62[l]) {
                  case 0:
                     if ((576458553280167936L & l1) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        m103(0, 1);
                     } else if ((65534L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m103(2, 3);
                     } else if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m103(4, 5);
                     } else if ((2190433320960L & l1) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        m103(6, 7);
                     } else if (f63 == '{') {
                        f62[f66++] = 5;
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
                     if (f63 == '{') {
                        f62[f66++] = 5;
                     }
                     break;
                  case 7:
                     if (f63 == '}') {
                        i1 = 5;
                     }
                     break;
                  case 8:
                     if ((65534L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        m103(2, 3);
                     }
                     break;
                  case 12:
                     if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        m103(4, 5);
                     }
                     break;
                  case 16:
                     if ((2190433320960L & l1) != 0L) {
                        i1 = 8;
                        m103(6, 7);
                     }
                     break;
                  case 21:
                     if ((134217726L & l1) != 0L) {
                        if (i1 > 9) {
                           i1 = 9;
                        }

                        f62[f66++] = 21;
                     }
               }
            } while (l != k);
         } else {
            int j1 = (f63 & 255) >> 6;
            long k1 = 1L << (f63 & '?');

            do {
               l--;
               switch (f62[l]) {
               }
            } while (l != k);
         }

         if (i1 != 2147483647) {
            f69 = i1;
            f68 = j;
            i1 = 2147483647;
         }

         j++;
         int j2 = l = f66;
         f66 = k;
         if (j2 == (k = 22 - k)) {
            return j;
         }

         try {
            f63 = C_B.m77();
         } catch (IOException ioexception) {
            return j;
         }
      }
   }

   public C_D(C_B c_b) {
      if (f60 != null) {
         throw new C_C("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", 1);
      } else {
         f60 = c_b;
      }
   }

   public C_D(C_B c_b, int i) {
      this(c_b);
      m111(i);
   }

   public static void m108(C_B c_b) {
      f66 = 0;
      f68 = 0;
      f64 = f65;
      f60 = c_b;
      m109();
   }

   private static final void m109() {
      f67 = -2147483647;
      int i = 22;

      while (i-- > 0) {
         f61[i] = -2147483648;
      }
   }

   public static void m110(C_B c_b, int i) {
      m108(c_b);
      m111(i);
   }

   public static void m111(int i) {
      if (i < 1 && i >= 0) {
         f64 = i;
      } else {
         throw new C_C("Error: Ignoring invalid lexical state : " + i + ". State unchanged.", 2);
      }
   }

   private static final C_G m112() {
      C_G c_g = C_G.m212(f69);
      c_g.f108 = f69;
      String s = f56[f69];
      c_g.f113 = s == null ? C_B.m89() : s;
      c_g.f109 = C_B.m83();
      c_g.f110 = C_B.m82();
      c_g.f111 = C_B.m81();
      c_g.f112 = C_B.m80();
      return c_g;
   }

   public static final C_G m113() {
      Object object = null;
      int i = 0;

      while (true) {
         try {
            f63 = C_B.m75();
         } catch (IOException ioexception) {
            f69 = 0;
            return m112();
         }

         try {
            while (f63 <= ' ' && (4294976000L & 1L << f63) != 0L) {
               f63 = C_B.m75();
            }
         } catch (IOException ioexception2) {
            continue;
         }

         f69 = 2147483647;
         f68 = 0;
         i = m99();
         if (f69 == 2147483647) {
            int j = C_B.m81();
            int k = C_B.m80();
            String s = null;
            boolean flag = false;

            try {
               C_B.m77();
               C_B.m84(1);
            } catch (IOException ioexception1) {
               flag = true;
               s = i <= 1 ? "" : C_B.m89();
               if (f63 != '\n' && f63 != '\r') {
                  k++;
               } else {
                  j++;
                  k = 0;
               }
            }

            if (!flag) {
               C_B.m84(1);
               s = i <= 1 ? "" : C_B.m89();
            }

            throw new C_C(flag, f64, j, k, s, f63, 0);
         }

         if (f68 + 1 < i) {
            C_B.m84(i - f68 - 1);
         }

         if ((f58[f69 >> 6] & 1L << (f69 & 63)) != 0L) {
            return m112();
         }
      }
   }
}
