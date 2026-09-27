package edu.ucla.phil.logic.pkgA;

import java.io.IOException;

public class Syntax2TokenManager implements Syntax2Constants {
   static final int[] jjnextStates = new int[]{1, 2, 9, 10, 13, 14, 17, 18};
   public static final String[] jjstrLiteralImages = new String[]{
      "", null, null, null, "\n", null, null, null, null, null, "<->", "->", "&", "|", "=", "<>", "[m]", "~", "@", "!", "(", ")", "%"
   };
   public static final String[] lexStateNames = new String[]{"DEFAULT"};
   static final long[] jjtoToken = new long[]{8388593L};
   static final long[] jjtoSkip = new long[]{14L};
   private static Syntax2CharStream input_stream;
   private static final int[] jjrounds = new int[22];
   private static final int[] jjstateSet = new int[44];
   protected static char curChar;
   static int curLexState = 0;
   static int defaultLexState = 0;
   static int jjnewStateCnt;
   static int jjround;
   static int jjmatchedPos;
   static int jjmatchedKind;

   private static final int jjStopStringLiteralDfa_0(int i, long j) {
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

   private static final int jjStartNfa_0(int i, long j) {
      return jjMoveNfa_0(jjStopStringLiteralDfa_0(i, j), i + 1);
   }

   private static final int jjStopAtPos(int i, int j) {
      jjmatchedKind = j;
      jjmatchedPos = i;
      return i + 1;
   }

   private static final int jjStartNfaWithStates_0(int i, int j, int k) {
      jjmatchedKind = j;
      jjmatchedPos = i;

      try {
         curChar = Syntax2CharStream.readChar();
      } catch (IOException ioexception) {
         return i + 1;
      }

      return jjMoveNfa_0(k, i + 1);
   }

   private static final int jjMoveStringLiteralDfa0_0() {
      switch (curChar) {
         case '\n':
            return jjStopAtPos(0, 4);
         case '!':
            return jjStopAtPos(0, 19);
         case '%':
            return jjStopAtPos(0, 22);
         case '&':
            return jjStopAtPos(0, 12);
         case '(':
            return jjStopAtPos(0, 20);
         case ')':
            return jjStopAtPos(0, 21);
         case '-':
            return jjMoveStringLiteralDfa1_0(2048L);
         case '<':
            return jjMoveStringLiteralDfa1_0(33792L);
         case '=':
            return jjStopAtPos(0, 14);
         case '@':
            return jjStopAtPos(0, 18);
         case '[':
            return jjMoveStringLiteralDfa1_0(65536L);
         case '|':
            return jjStopAtPos(0, 13);
         case '~':
            return jjStopAtPos(0, 17);
         default:
            return jjMoveNfa_0(0, 0);
      }
   }

   private static final int jjMoveStringLiteralDfa1_0(long i) {
      try {
         curChar = Syntax2CharStream.readChar();
      } catch (IOException ioexception) {
         jjStopStringLiteralDfa_0(0, i);
         return 1;
      }

      switch (curChar) {
         case '-':
            return jjMoveStringLiteralDfa2_0(i, 1024L);
         case '>':
            if ((i & 2048L) != 0L) {
               return jjStopAtPos(1, 11);
            } else if ((i & 32768L) != 0L) {
               return jjStopAtPos(1, 15);
            }
         default:
            return jjStartNfa_0(0, i);
         case 'm':
            return jjMoveStringLiteralDfa2_0(i, 65536L);
      }
   }

   private static final int jjMoveStringLiteralDfa2_0(long i, long j) {
      if ((j = j & i) == 0L) {
         return jjStartNfa_0(0, i);
      } else {
         try {
            curChar = Syntax2CharStream.readChar();
         } catch (IOException ioexception) {
            jjStopStringLiteralDfa_0(1, j);
            return 2;
         }

         switch (curChar) {
            case '>':
               if ((j & 1024L) != 0L) {
                  return jjStopAtPos(2, 10);
               }
               break;
            case ']':
               if ((j & 65536L) != 0L) {
                  return jjStopAtPos(2, 16);
               }
         }

         return jjStartNfa_0(1, j);
      }
   }

   private static final void jjCheckNAdd(int i) {
      if (jjrounds[i] != jjround) {
         jjstateSet[jjnewStateCnt++] = i;
         jjrounds[i] = jjround;
      }
   }

   private static final void jjAddStates(int i, int j) {
      do {
         jjstateSet[jjnewStateCnt++] = jjnextStates[i];
      } while (i++ != j);
   }

   private static final void jjCheckNAddTwoStates(int i, int j) {
      jjCheckNAdd(i);
      jjCheckNAdd(j);
   }

   private static final void jjCheckNAddStates(int i, int j) {
      do {
         jjCheckNAdd(jjnextStates[i]);
      } while (i++ != j);
   }

   private static final void jjCheckNAddStates(int i) {
      jjCheckNAdd(jjnextStates[i]);
      jjCheckNAdd(jjnextStates[i + 1]);
   }

   private static final int jjMoveNfa_0(int i, int j) {
      int k = 0;
      jjnewStateCnt = 22;
      int l = 1;
      jjstateSet[0] = i;
      int i1 = 2147483647;

      while (true) {
         if (++jjround == 2147483647) {
            ReInitRounds();
         }

         if (curChar < '@') {
            long i2 = 1L << curChar;

            do {
               l--;
               switch (jjstateSet[l]) {
                  case 0:
                     if (curChar == '?') {
                        i1 = 9;
                        jjstateSet[jjnewStateCnt++] = 21;
                     }
                     break;
                  case 1:
                     if (curChar == '0' && i1 > 5) {
                        i1 = 5;
                     }
                     break;
                  case 2:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        jjCheckNAdd(3);
                     }
                     break;
                  case 3:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        jjCheckNAdd(3);
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
                        jjCheckNAddTwoStates(6, 7);
                     }
                     break;
                  case 6:
                     if ((287948901175001088L & i2) != 0L) {
                        jjCheckNAddTwoStates(6, 7);
                     }
                     break;
                  case 9:
                     if (curChar == '0' && i1 > 6) {
                        i1 = 6;
                     }
                     break;
                  case 10:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        jjCheckNAdd(11);
                     }
                     break;
                  case 11:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        jjCheckNAdd(11);
                     }
                     break;
                  case 13:
                     if (curChar == '0' && i1 > 7) {
                        i1 = 7;
                     }
                     break;
                  case 14:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        jjCheckNAdd(15);
                     }
                     break;
                  case 15:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        jjCheckNAdd(15);
                     }
                     break;
                  case 17:
                     if (curChar == '0' && i1 > 8) {
                        i1 = 8;
                     }
                     break;
                  case 18:
                     if ((287667426198290432L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        jjCheckNAdd(19);
                     }
                     break;
                  case 19:
                     if ((287948901175001088L & i2) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        jjCheckNAdd(19);
                     }
               }
            } while (l != k);
         } else if (curChar < 128) {
            long l1 = 1L << (curChar & '?');

            do {
               l--;
               switch (jjstateSet[l]) {
                  case 0:
                     if ((576458553280167936L & l1) != 0L) {
                        if (i1 > 5) {
                           i1 = 5;
                        }

                        jjAddStates(0, 1);
                     } else if ((65534L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        jjAddStates(2, 3);
                     } else if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        jjAddStates(4, 5);
                     } else if ((2190433320960L & l1) != 0L) {
                        if (i1 > 8) {
                           i1 = 8;
                        }

                        jjAddStates(6, 7);
                     } else if (curChar == '{') {
                        jjstateSet[jjnewStateCnt++] = 5;
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
                     if (curChar == '{') {
                        jjstateSet[jjnewStateCnt++] = 5;
                     }
                     break;
                  case 7:
                     if (curChar == '}') {
                        i1 = 5;
                     }
                     break;
                  case 8:
                     if ((65534L & l1) != 0L) {
                        if (i1 > 6) {
                           i1 = 6;
                        }

                        jjAddStates(2, 3);
                     }
                     break;
                  case 12:
                     if ((134152192L & l1) != 0L) {
                        if (i1 > 7) {
                           i1 = 7;
                        }

                        jjAddStates(4, 5);
                     }
                     break;
                  case 16:
                     if ((2190433320960L & l1) != 0L) {
                        i1 = 8;
                        jjAddStates(6, 7);
                     }
                     break;
                  case 21:
                     if ((134217726L & l1) != 0L) {
                        if (i1 > 9) {
                           i1 = 9;
                        }

                        jjstateSet[jjnewStateCnt++] = 21;
                     }
               }
            } while (l != k);
         } else {
            int j1 = (curChar & 255) >> 6;
            long k1 = 1L << (curChar & '?');

            do {
               l--;
               switch (jjstateSet[l]) {
               }
            } while (l != k);
         }

         if (i1 != 2147483647) {
            jjmatchedKind = i1;
            jjmatchedPos = j;
            i1 = 2147483647;
         }

         j++;
         int j2 = l = jjnewStateCnt;
         jjnewStateCnt = k;
         if (j2 == (k = 22 - k)) {
            return j;
         }

         try {
            curChar = Syntax2CharStream.readChar();
         } catch (IOException ioexception) {
            return j;
         }
      }
   }

   public Syntax2TokenManager(Syntax2CharStream syntax2charstream) {
      if (input_stream != null) {
         throw new Syntax2TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", 1);
      } else {
         input_stream = syntax2charstream;
      }
   }

   public Syntax2TokenManager(Syntax2CharStream syntax2charstream, int i) {
      this(syntax2charstream);
      SwitchTo(i);
   }

   public static void ReInit(Syntax2CharStream syntax2charstream) {
      jjnewStateCnt = 0;
      jjmatchedPos = 0;
      curLexState = defaultLexState;
      input_stream = syntax2charstream;
      ReInitRounds();
   }

   private static final void ReInitRounds() {
      jjround = -2147483647;
      int i = 22;

      while (i-- > 0) {
         jjrounds[i] = -2147483648;
      }
   }

   public static void ReInit(Syntax2CharStream syntax2charstream, int i) {
      ReInit(syntax2charstream);
      SwitchTo(i);
   }

   public static void SwitchTo(int i) {
      if (i < 1 && i >= 0) {
         curLexState = i;
      } else {
         throw new Syntax2TokenMgrError("Error: Ignoring invalid lexical state : " + i + ". State unchanged.", 2);
      }
   }

   private static final Syntax2Token jjFillToken() {
      Syntax2Token syntax2token = Syntax2Token.newToken(jjmatchedKind);
      syntax2token.kind = jjmatchedKind;
      String s = jjstrLiteralImages[jjmatchedKind];
      syntax2token.image = s == null ? Syntax2CharStream.GetImage() : s;
      syntax2token.beginLine = Syntax2CharStream.getBeginLine();
      syntax2token.beginColumn = Syntax2CharStream.getBeginColumn();
      syntax2token.endLine = Syntax2CharStream.getEndLine();
      syntax2token.endColumn = Syntax2CharStream.getEndColumn();
      return syntax2token;
   }

   public static final Syntax2Token getNextToken() {
      Object object = null;
      int i = 0;

      while (true) {
         try {
            curChar = Syntax2CharStream.BeginToken();
         } catch (IOException ioexception) {
            jjmatchedKind = 0;
            return jjFillToken();
         }

         try {
            while (curChar <= ' ' && (4294976000L & 1L << curChar) != 0L) {
               curChar = Syntax2CharStream.BeginToken();
            }
         } catch (IOException ioexception2) {
            continue;
         }

         jjmatchedKind = 2147483647;
         jjmatchedPos = 0;
         i = jjMoveStringLiteralDfa0_0();
         if (jjmatchedKind == 2147483647) {
            int j = Syntax2CharStream.getEndLine();
            int k = Syntax2CharStream.getEndColumn();
            String s = null;
            boolean flag = false;

            try {
               Syntax2CharStream.readChar();
               Syntax2CharStream.backup(1);
            } catch (IOException ioexception1) {
               flag = true;
               s = i <= 1 ? "" : Syntax2CharStream.GetImage();
               if (curChar != '\n' && curChar != '\r') {
                  k++;
               } else {
                  j++;
                  k = 0;
               }
            }

            if (!flag) {
               Syntax2CharStream.backup(1);
               s = i <= 1 ? "" : Syntax2CharStream.GetImage();
            }

            throw new Syntax2TokenMgrError(flag, curLexState, j, k, s, curChar, 0);
         }

         if (jjmatchedPos + 1 < i) {
            Syntax2CharStream.backup(i - jjmatchedPos - 1);
         }

         if ((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 63)) != 0L) {
            return jjFillToken();
         }
      }
   }
}
