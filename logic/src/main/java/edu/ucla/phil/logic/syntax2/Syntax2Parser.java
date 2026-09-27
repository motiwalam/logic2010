package edu.ucla.phil.logic.syntax2;

import edu.ucla.phil.logic.AtomicFormula;
import edu.ucla.phil.logic.ConnectiveFormula;
import edu.ucla.phil.logic.DescriptionTerm;
import edu.ucla.phil.logic.Expression;
import edu.ucla.phil.logic.Formula;
import edu.ucla.phil.logic.FormulaParser;
import edu.ucla.phil.logic.IdentityFormula;
import edu.ucla.phil.logic.MembershipFormula;
import edu.ucla.phil.logic.OperationTerm;
import edu.ucla.phil.logic.QuantifiedFormula;
import edu.ucla.phil.logic.SimpleTerm;
import edu.ucla.phil.logic.Term;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Syntax2Parser extends FormulaParser implements Syntax2Constants {
   static Hashtable binders = new Hashtable();
   private static boolean jj_initialized_once = false;
   public static Syntax2TokenManager token_source;
   static Syntax2CharStream jj_input_stream;
   public static Syntax2Token token;
   public static Syntax2Token jj_nt;
   private static int jj_ntk;
   private static Syntax2Token jj_scanpos;
   private static Syntax2Token jj_lastpos;
   private static int jj_la;
   public static boolean lookingAhead = false;
   private static boolean jj_semLA;
   private static int jj_gen;
   private static final int[] jj_la1 = new int[0];
   private static final int[] jj_la1_0 = new int[0];
   private static final Syntax2Parser.JJCalls[] jj_2_rtns = new Syntax2Parser.JJCalls[32];
   private static boolean jj_rescan = false;
   private static int jj_gc = 0;
   private static Vector jj_expentries = new Vector();
   private static int[] jj_expentry;
   private static int jj_kind = -1;
   private static int[] jj_lasttokens = new int[100];
   private static int jj_endpos;
   private static int trace_indent = 0;
   private static boolean trace_enabled = true;

   public static final Expression parse() throws Syntax2ParseException {
      try {
         return one_line();
      } catch (Syntax2ParseException syntax2parseexception) {
         throw syntax2parseexception;
      }
   }

   public static final Expression one_line() throws Syntax2ParseException {
      trace_call("one_line");

      Object object;
      try {
         if (jj_2_1(2147483647)) {
            Formula formula = formula();
            jj_consume_token(4);
            return formula;
         }

         if (jj_2_2(2147483647)) {
            Term term = term();
            jj_consume_token(4);
            return term;
         }

         if (!jj_2_3(2147483647)) {
            if (!jj_2_4(2147483647)) {
               jj_consume_token(-1);
               throw new Syntax2ParseException();
            }

            jj_consume_token(0);
            return null;
         }

         jj_consume_token(4);
         object = null;
      } finally {
         trace_return("one_line");
      }

      return (Expression)object;
   }

   public static final Formula formula() throws Syntax2ParseException {
      trace_call("formula");

      Object object1;
      try {
         Object object = conjexp();

         while (jj_2_5(2147483647)) {
            if (jj_2_6(2147483647)) {
               jj_consume_token(10);
            } else {
               if (!jj_2_7(2147483647)) {
                  jj_consume_token(-1);
                  throw new Syntax2ParseException();
               }

               jj_consume_token(11);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(token.image);
            connectiveformula.setLeft((Formula)object);
            Formula formula = conjexp();
            connectiveformula.setRight(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         trace_return("formula");
      }

      return (Formula)object1;
   }

   public static final Formula conjexp() throws Syntax2ParseException {
      trace_call("conjexp");

      Object object1;
      try {
         Object object = unary();

         while (jj_2_8(2147483647)) {
            if (jj_2_9(2147483647)) {
               jj_consume_token(12);
            } else {
               if (!jj_2_10(2147483647)) {
                  jj_consume_token(-1);
                  throw new Syntax2ParseException();
               }

               jj_consume_token(13);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(token.image);
            connectiveformula.setLeft((Formula)object);
            Formula formula = unary();
            connectiveformula.setRight(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         trace_return("conjexp");
      }

      return (Formula)object1;
   }

   public static final Formula equation() throws Syntax2ParseException {
      trace_call("equation");

      IdentityFormula identityformula1;
      try {
         if (!jj_2_11(2147483647)) {
            if (!jj_2_12(2147483647)) {
               jj_consume_token(-1);
               throw new Syntax2ParseException();
            }

            Term term2 = term();
            jj_consume_token(15);
            IdentityFormula identityformula2 = new IdentityFormula("=");
            identityformula2.setLeft(term2);
            Term term3 = term();
            identityformula2.setRight(term3);
            return identityformula2.negate().markAsInequality();
         }

         Term term = term();
         jj_consume_token(14);
         IdentityFormula identityformula = new IdentityFormula(token.image);
         identityformula.setLeft(term);
         Term term1 = term();
         identityformula.setRight(term1);
         identityformula1 = identityformula;
      } finally {
         trace_return("equation");
      }

      return identityformula1;
   }

   public static final Formula member() throws Syntax2ParseException {
      trace_call("member");

      MembershipFormula membershipformula1;
      try {
         Term term = term();
         jj_consume_token(16);
         MembershipFormula membershipformula = new MembershipFormula(token.image);
         membershipformula.setElement(term);
         Term term1 = term();
         membershipformula.setSet(term1);
         membershipformula1 = membershipformula;
      } finally {
         trace_return("member");
      }

      return membershipformula1;
   }

   public static final Formula unary() throws Syntax2ParseException {
      trace_call("unary");

      Formula formula1;
      try {
         if (jj_2_13(2147483647)) {
            jj_consume_token(17);
            ConnectiveFormula connectiveformula = new ConnectiveFormula(token.image);
            Formula formula4 = unary();
            connectiveformula.setLeft(formula4);
            return connectiveformula;
         }

         if (jj_2_14(2147483647)) {
            jj_consume_token(18);
            QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(token.image);
            jj_consume_token(5);
            SimpleTerm simpleterm1 = new SimpleTerm(token.image);
            quantifiedformula1.setVariable(simpleterm1);
            binders.put(simpleterm1.getSymbol(), quantifiedformula1);
            Formula formula3 = unary();
            quantifiedformula1.setBody(formula3);
            binders.remove(simpleterm1.getSymbol());
            return quantifiedformula1;
         }

         if (jj_2_15(2147483647)) {
            jj_consume_token(19);
            QuantifiedFormula quantifiedformula = new QuantifiedFormula(token.image);
            jj_consume_token(5);
            SimpleTerm simpleterm = new SimpleTerm(token.image);
            quantifiedformula.setVariable(simpleterm);
            binders.put(simpleterm.getSymbol(), quantifiedformula);
            Formula formula2 = unary();
            quantifiedformula.setBody(formula2);
            binders.remove(simpleterm.getSymbol());
            return quantifiedformula;
         }

         if (jj_2_16(2147483647)) {
            return equation();
         }

         if (!jj_2_17(2147483647)) {
            if (!jj_2_18(2147483647)) {
               jj_consume_token(-1);
               throw new Syntax2ParseException();
            }

            return primary();
         }

         Formula formula = member();
         formula1 = formula;
      } finally {
         trace_return("unary");
      }

      return formula1;
   }

   public static final Formula primary() throws Syntax2ParseException {
      trace_call("primary");

      AtomicFormula atomicformula1;
      try {
         if (jj_2_24(2147483647)) {
            jj_consume_token(20);
            Formula formula = formula();
            jj_consume_token(21);
            return formula;
         }

         if (jj_2_25(2147483647)) {
            if (jj_2_19(2147483647)) {
               jj_consume_token(6);
            } else {
               if (!jj_2_20(2147483647)) {
                  jj_consume_token(-1);
                  throw new Syntax2ParseException();
               }

               jj_consume_token(7);
            }

            AtomicFormula atomicformula2 = new AtomicFormula(token.image);
            jj_consume_token(20);

            do {
               Term term1 = term();
               atomicformula2.addArgument(term1);
            } while (jj_2_21(2147483647));

            jj_consume_token(21);
            return atomicformula2;
         }

         if (jj_2_26(2147483647)) {
            jj_consume_token(6);
            AtomicFormula atomicformula = new AtomicFormula(token.image);
            Term term = term();
            atomicformula.addArgument(term);
            return atomicformula;
         }

         if (!jj_2_27(2147483647)) {
            jj_consume_token(-1);
            throw new Syntax2ParseException();
         }

         if (jj_2_22(2147483647)) {
            jj_consume_token(7);
         } else {
            if (!jj_2_23(2147483647)) {
               jj_consume_token(-1);
               throw new Syntax2ParseException();
            }

            jj_consume_token(9);
         }

         atomicformula1 = new AtomicFormula(token.image);
      } finally {
         trace_return("primary");
      }

      return atomicformula1;
   }

   public static final Term term() throws Syntax2ParseException {
      trace_call("term");

      OperationTerm operationterm1;
      try {
         if (jj_2_29(2147483647)) {
            jj_consume_token(5);
            SimpleTerm simpleterm1 = new SimpleTerm(token.image);
            Expression expression = (Expression)binders.get(simpleterm1.getSymbol());
            if (expression != null) {
               simpleterm1.setBinder(expression);
            }

            return simpleterm1;
         }

         if (!jj_2_30(2147483647)) {
            if (!jj_2_31(2147483647)) {
               if (!jj_2_32(2147483647)) {
                  jj_consume_token(-1);
                  throw new Syntax2ParseException();
               }

               jj_consume_token(22);
               DescriptionTerm descriptionterm = new DescriptionTerm(token.image);
               jj_consume_token(5);
               SimpleTerm simpleterm = new SimpleTerm(token.image);
               descriptionterm.setVariable(simpleterm);
               binders.put(simpleterm.getSymbol(), descriptionterm);
               Formula formula = unary();
               descriptionterm.setBody(formula);
               binders.remove(simpleterm.getSymbol());
               return descriptionterm;
            }

            jj_consume_token(8);
            return new OperationTerm(token.image);
         }

         jj_consume_token(8);
         OperationTerm operationterm = new OperationTerm(token.image);
         jj_consume_token(20);

         do {
            Term term = term();
            operationterm.addArgument(term);
         } while (jj_2_28(2147483647));

         jj_consume_token(21);
         operationterm1 = operationterm;
      } finally {
         trace_return("term");
      }

      return operationterm1;
   }

   private static final boolean jj_2_1(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_1();
      jj_save(0, i);
      return flag;
   }

   private static final boolean jj_2_2(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_2();
      jj_save(1, i);
      return flag;
   }

   private static final boolean jj_2_3(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_3();
      jj_save(2, i);
      return flag;
   }

   private static final boolean jj_2_4(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_4();
      jj_save(3, i);
      return flag;
   }

   private static final boolean jj_2_5(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_5();
      jj_save(4, i);
      return flag;
   }

   private static final boolean jj_2_6(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_6();
      jj_save(5, i);
      return flag;
   }

   private static final boolean jj_2_7(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_7();
      jj_save(6, i);
      return flag;
   }

   private static final boolean jj_2_8(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_8();
      jj_save(7, i);
      return flag;
   }

   private static final boolean jj_2_9(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_9();
      jj_save(8, i);
      return flag;
   }

   private static final boolean jj_2_10(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_10();
      jj_save(9, i);
      return flag;
   }

   private static final boolean jj_2_11(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_11();
      jj_save(10, i);
      return flag;
   }

   private static final boolean jj_2_12(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_12();
      jj_save(11, i);
      return flag;
   }

   private static final boolean jj_2_13(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_13();
      jj_save(12, i);
      return flag;
   }

   private static final boolean jj_2_14(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_14();
      jj_save(13, i);
      return flag;
   }

   private static final boolean jj_2_15(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_15();
      jj_save(14, i);
      return flag;
   }

   private static final boolean jj_2_16(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_16();
      jj_save(15, i);
      return flag;
   }

   private static final boolean jj_2_17(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_17();
      jj_save(16, i);
      return flag;
   }

   private static final boolean jj_2_18(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_18();
      jj_save(17, i);
      return flag;
   }

   private static final boolean jj_2_19(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_19();
      jj_save(18, i);
      return flag;
   }

   private static final boolean jj_2_20(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_20();
      jj_save(19, i);
      return flag;
   }

   private static final boolean jj_2_21(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_21();
      jj_save(20, i);
      return flag;
   }

   private static final boolean jj_2_22(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_22();
      jj_save(21, i);
      return flag;
   }

   private static final boolean jj_2_23(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_23();
      jj_save(22, i);
      return flag;
   }

   private static final boolean jj_2_24(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_24();
      jj_save(23, i);
      return flag;
   }

   private static final boolean jj_2_25(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_25();
      jj_save(24, i);
      return flag;
   }

   private static final boolean jj_2_26(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_26();
      jj_save(25, i);
      return flag;
   }

   private static final boolean jj_2_27(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_27();
      jj_save(26, i);
      return flag;
   }

   private static final boolean jj_2_28(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_28();
      jj_save(27, i);
      return flag;
   }

   private static final boolean jj_2_29(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_29();
      jj_save(28, i);
      return flag;
   }

   private static final boolean jj_2_30(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_30();
      jj_save(29, i);
      return flag;
   }

   private static final boolean jj_2_31(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_31();
      jj_save(30, i);
      return flag;
   }

   private static final boolean jj_2_32(int i) {
      jj_la = i;
      jj_lastpos = jj_scanpos = token;
      boolean flag = !jj_3_32();
      jj_save(31, i);
      return flag;
   }

   private static final boolean jj_3R_1() {
      if (jj_3R_7()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = jj_scanpos;
            if (jj_3_5()) {
               jj_scanpos = syntax2token;
               return false;
            }
         } while (jj_la != 0 || jj_scanpos != jj_lastpos);

         return false;
      }
   }

   private static final boolean jj_3_23() {
      if (jj_scan_token(9)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_4() {
      if (jj_scan_token(0)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_29() {
      if (jj_scan_token(5)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_2() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_29()) {
         jj_scanpos = syntax2token;
         if (jj_3_30()) {
            jj_scanpos = syntax2token;
            if (jj_3_31()) {
               jj_scanpos = syntax2token;
               if (jj_3_32()) {
                  return true;
               }

               if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                  return false;
               }
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
               return false;
            }
         } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      return false;
   }

   private static final boolean jj_3_3() {
      if (jj_scan_token(4)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_20() {
      if (jj_scan_token(7)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_22() {
      if (jj_scan_token(7)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_21() {
      if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_27() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_22()) {
         jj_scanpos = syntax2token;
         if (jj_3_23()) {
            return true;
         }

         if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      return false;
   }

   private static final boolean jj_3_2() {
      if (jj_3R_2()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(4)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_26() {
      if (jj_scan_token(6)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_19() {
      if (jj_scan_token(6)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_1() {
      if (jj_3R_1()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(4)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_25() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_19()) {
         jj_scanpos = syntax2token;
         if (jj_3_20()) {
            return true;
         }

         if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      if (jj_scan_token(20)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3_21()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else {
         do {
            syntax2token = jj_scanpos;
            if (jj_3_21()) {
               jj_scanpos = syntax2token;
               if (jj_scan_token(21)) {
                  return true;
               }

               if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                  return false;
               }

               return false;
            }
         } while (jj_la != 0 || jj_scanpos != jj_lastpos);

         return false;
      }
   }

   private static final boolean jj_3_18() {
      if (jj_3R_3()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_17() {
      if (jj_3R_5()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_3() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_24()) {
         jj_scanpos = syntax2token;
         if (jj_3_25()) {
            jj_scanpos = syntax2token;
            if (jj_3_26()) {
               jj_scanpos = syntax2token;
               if (jj_3_27()) {
                  return true;
               }

               if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                  return false;
               }
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
               return false;
            }
         } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      return false;
   }

   private static final boolean jj_3_24() {
      if (jj_scan_token(20)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_1()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(21)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_16() {
      if (jj_3R_6()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_15() {
      if (jj_scan_token(19)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(5)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_4()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_14() {
      if (jj_scan_token(18)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(5)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_4()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_4() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_13()) {
         jj_scanpos = syntax2token;
         if (jj_3_14()) {
            jj_scanpos = syntax2token;
            if (jj_3_15()) {
               jj_scanpos = syntax2token;
               if (jj_3_16()) {
                  jj_scanpos = syntax2token;
                  if (jj_3_17()) {
                     jj_scanpos = syntax2token;
                     if (jj_3_18()) {
                        return true;
                     }

                     if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                        return false;
                     }
                  } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                     return false;
                  }
               } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                  return false;
               }
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
               return false;
            }
         } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      return false;
   }

   private static final boolean jj_3_13() {
      if (jj_scan_token(17)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_4()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_5() {
      if (jj_3R_2()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(16)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_12() {
      if (jj_3R_2()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(15)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_6() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_11()) {
         jj_scanpos = syntax2token;
         if (jj_3_12()) {
            return true;
         }

         if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      return false;
   }

   private static final boolean jj_3_11() {
      if (jj_3R_2()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(14)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_10() {
      if (jj_scan_token(13)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_9() {
      if (jj_scan_token(12)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_8() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_9()) {
         jj_scanpos = syntax2token;
         if (jj_3_10()) {
            return true;
         }

         if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      if (jj_3R_4()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_7() {
      if (jj_scan_token(11)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3R_7() {
      if (jj_3R_4()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = jj_scanpos;
            if (jj_3_8()) {
               jj_scanpos = syntax2token;
               return false;
            }
         } while (jj_la != 0 || jj_scanpos != jj_lastpos);

         return false;
      }
   }

   private static final boolean jj_3_28() {
      if (jj_3R_2()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_32() {
      if (jj_scan_token(22)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(5)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3R_4()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_6() {
      if (jj_scan_token(10)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_31() {
      if (jj_scan_token(8)) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_5() {
      Syntax2Token syntax2token = jj_scanpos;
      if (jj_3_6()) {
         jj_scanpos = syntax2token;
         if (jj_3_7()) {
            return true;
         }

         if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            return false;
         }
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      }

      if (jj_3R_7()) {
         return true;
      } else {
         return jj_la == 0 && jj_scanpos == jj_lastpos ? false : false;
      }
   }

   private static final boolean jj_3_30() {
      if (jj_scan_token(8)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_scan_token(20)) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else if (jj_3_28()) {
         return true;
      } else if (jj_la == 0 && jj_scanpos == jj_lastpos) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = jj_scanpos;
            if (jj_3_28()) {
               jj_scanpos = syntax2token;
               if (jj_scan_token(21)) {
                  return true;
               }

               if (jj_la == 0 && jj_scanpos == jj_lastpos) {
                  return false;
               }

               return false;
            }
         } while (jj_la != 0 || jj_scanpos != jj_lastpos);

         return false;
      }
   }

   public Syntax2Parser(InputStream inputstream) {
      if (jj_initialized_once) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         jj_initialized_once = true;
         jj_input_stream = new Syntax2CharStream(inputstream, 1, 1);
         token_source = new Syntax2TokenManager(jj_input_stream);
         token = new Syntax2Token();
         jj_ntk = -1;
         jj_gen = 0;

         for (int i = 0; i < 0; i++) {
            jj_la1[i] = -1;
         }

         for (int j = 0; j < jj_2_rtns.length; j++) {
            jj_2_rtns[j] = new Syntax2Parser.JJCalls();
         }
      }
   }

   public static void ReInit(InputStream inputstream) {
      Syntax2CharStream.ReInit(inputstream, 1, 1);
      Syntax2TokenManager.ReInit(jj_input_stream);
      token = new Syntax2Token();
      jj_ntk = -1;
      jj_gen = 0;

      for (int i = 0; i < 0; i++) {
         jj_la1[i] = -1;
      }

      for (int j = 0; j < jj_2_rtns.length; j++) {
         jj_2_rtns[j] = new Syntax2Parser.JJCalls();
      }
   }

   public Syntax2Parser(Reader reader) {
      if (jj_initialized_once) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         jj_initialized_once = true;
         jj_input_stream = new Syntax2CharStream(reader, 1, 1);
         token_source = new Syntax2TokenManager(jj_input_stream);
         token = new Syntax2Token();
         jj_ntk = -1;
         jj_gen = 0;

         for (int i = 0; i < 0; i++) {
            jj_la1[i] = -1;
         }

         for (int j = 0; j < jj_2_rtns.length; j++) {
            jj_2_rtns[j] = new Syntax2Parser.JJCalls();
         }
      }
   }

   public static void reinit(Reader reader) {
      Syntax2CharStream.ReInit(reader, 1, 1);
      Syntax2TokenManager.ReInit(jj_input_stream);
      token = new Syntax2Token();
      jj_ntk = -1;
      jj_gen = 0;

      for (int i = 0; i < 0; i++) {
         jj_la1[i] = -1;
      }

      for (int j = 0; j < jj_2_rtns.length; j++) {
         jj_2_rtns[j] = new Syntax2Parser.JJCalls();
      }
   }

   public Syntax2Parser(Syntax2TokenManager syntax2tokenmanager) {
      if (jj_initialized_once) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         jj_initialized_once = true;
         token_source = syntax2tokenmanager;
         token = new Syntax2Token();
         jj_ntk = -1;
         jj_gen = 0;

         for (int i = 0; i < 0; i++) {
            jj_la1[i] = -1;
         }

         for (int j = 0; j < jj_2_rtns.length; j++) {
            jj_2_rtns[j] = new Syntax2Parser.JJCalls();
         }
      }
   }

   public void ReInit(Syntax2TokenManager syntax2tokenmanager) {
      token_source = syntax2tokenmanager;
      token = new Syntax2Token();
      jj_ntk = -1;
      jj_gen = 0;

      for (int i = 0; i < 0; i++) {
         jj_la1[i] = -1;
      }

      for (int j = 0; j < jj_2_rtns.length; j++) {
         jj_2_rtns[j] = new Syntax2Parser.JJCalls();
      }
   }

   private static final Syntax2Token jj_consume_token(int i) throws Syntax2ParseException {
      Syntax2Token syntax2token;
      if ((syntax2token = token).next != null) {
         token = token.next;
      } else {
         token = token.next = Syntax2TokenManager.getNextToken();
      }

      jj_ntk = -1;
      if (token.kind != i) {
         token = syntax2token;
         jj_kind = i;
         throw generateParseException();
      } else {
         jj_gen++;
         if (++jj_gc > 100) {
            jj_gc = 0;

            for (int j = 0; j < jj_2_rtns.length; j++) {
               for (Syntax2Parser.JJCalls syntax2parser$jjcalls = jj_2_rtns[j];
                  syntax2parser$jjcalls != null;
                  syntax2parser$jjcalls = syntax2parser$jjcalls.next
               ) {
                  if (syntax2parser$jjcalls.gen < jj_gen) {
                     syntax2parser$jjcalls.first = null;
                  }
               }
            }
         }

         trace_token(token, "");
         return token;
      }
   }

   private static final boolean jj_scan_token(int i) {
      if (jj_scanpos == jj_lastpos) {
         jj_la--;
         if (jj_scanpos.next == null) {
            jj_lastpos = jj_scanpos = jj_scanpos.next = Syntax2TokenManager.getNextToken();
         } else {
            jj_lastpos = jj_scanpos = jj_scanpos.next;
         }
      } else {
         jj_scanpos = jj_scanpos.next;
      }

      if (jj_rescan) {
         int j = 0;

         Syntax2Token syntax2token;
         for (syntax2token = token; syntax2token != null && syntax2token != jj_scanpos; syntax2token = syntax2token.next) {
            j++;
         }

         if (syntax2token != null) {
            jj_add_error_token(i, j);
         }
      }

      return jj_scanpos.kind != i;
   }

   public static final Syntax2Token getNextToken() {
      if (token.next != null) {
         token = token.next;
      } else {
         token = token.next = Syntax2TokenManager.getNextToken();
      }

      jj_ntk = -1;
      jj_gen++;
      trace_token(token, " (in getNextToken)");
      return token;
   }

   public static final Syntax2Token getToken(int i) {
      Syntax2Token syntax2token = lookingAhead ? jj_scanpos : token;

      for (int j = 0; j < i; j++) {
         if (syntax2token.next != null) {
            syntax2token = syntax2token.next;
         } else {
            syntax2token = syntax2token.next = Syntax2TokenManager.getNextToken();
         }
      }

      return syntax2token;
   }

   private static final int jj_ntk() {
      return (jj_nt = token.next) == null ? (jj_ntk = (token.next = Syntax2TokenManager.getNextToken()).kind) : (jj_ntk = jj_nt.kind);
   }

   private static void jj_add_error_token(int i, int j) {
      if (j < 100) {
         if (j == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = i;
         } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];

            for (int k = 0; k < jj_endpos; k++) {
               jj_expentry[k] = jj_lasttokens[k];
            }

            boolean flag = false;
            Enumeration enumeration = jj_expentries.elements();

            while (enumeration.hasMoreElements()) {
               int[] aint = (int[])enumeration.nextElement();
               if (aint.length == jj_expentry.length) {
                  flag = true;

                  for (int l = 0; l < jj_expentry.length; l++) {
                     if (aint[l] != jj_expentry[l]) {
                        flag = false;
                        break;
                     }
                  }

                  if (flag) {
                     break;
                  }
               }
            }

            if (!flag) {
               jj_expentries.addElement(jj_expentry);
            }

            if (j != 0) {
               int[] aint1 = jj_lasttokens;
               jj_endpos = j;
               aint1[j - 1] = i;
            }
         }
      }
   }

   public static final Syntax2ParseException generateParseException() {
      jj_expentries.removeAllElements();
      boolean[] aboolean = new boolean[23];

      for (int i = 0; i < 23; i++) {
         aboolean[i] = false;
      }

      if (jj_kind >= 0) {
         aboolean[jj_kind] = true;
         jj_kind = -1;
      }

      for (int k = 0; k < 0; k++) {
         if (jj_la1[k] == jj_gen) {
            for (int j = 0; j < 32; j++) {
               if ((jj_la1_0[k] & 1 << j) != 0) {
                  aboolean[j] = true;
               }
            }
         }
      }

      for (int l = 0; l < 23; l++) {
         if (aboolean[l]) {
            jj_expentry = new int[1];
            jj_expentry[0] = l;
            jj_expentries.addElement(jj_expentry);
         }
      }

      jj_endpos = 0;
      jj_rescan_token();
      jj_add_error_token(0, 0);
      int[][] aint = new int[jj_expentries.size()][];

      for (int i1 = 0; i1 < jj_expentries.size(); i1++) {
         aint[i1] = (int[])jj_expentries.elementAt(i1);
      }

      return new Syntax2ParseException(token, aint, tokenImage);
   }

   public static final void enable_tracing() {
      trace_enabled = true;
   }

   public static final void disableTracing() {
      trace_enabled = false;
   }

   private static final void trace_call(String s) {
      if (trace_enabled) {
         for (int i = 0; i < trace_indent; i++) {
            System.out.print(" ");
         }

         System.out.println("Call:   " + s);
      }

      trace_indent += 2;
   }

   private static final void trace_return(String s) {
      trace_indent -= 2;
      if (trace_enabled) {
         for (int i = 0; i < trace_indent; i++) {
            System.out.print(" ");
         }

         System.out.println("Return: " + s);
      }
   }

   private static final void trace_token(Syntax2Token syntax2token, String s) {
      if (trace_enabled) {
         for (int i = 0; i < trace_indent; i++) {
            System.out.print(" ");
         }

         System.out.print("Consumed token: <" + tokenImage[syntax2token.kind]);
         if (syntax2token.kind != 0 && !tokenImage[syntax2token.kind].equals("\"" + syntax2token.image + "\"")) {
            System.out.print(": \"" + syntax2token.image + "\"");
         }

         System.out.println(">" + s);
      }
   }

   private static final void trace_scan(Syntax2Token syntax2token, int i) {
      if (trace_enabled) {
         for (int j = 0; j < trace_indent; j++) {
            System.out.print(" ");
         }

         System.out.print("Visited token: <" + tokenImage[syntax2token.kind]);
         if (syntax2token.kind != 0 && !tokenImage[syntax2token.kind].equals("\"" + syntax2token.image + "\"")) {
            System.out.print(": \"" + syntax2token.image + "\"");
         }

         System.out.println(">; Expected token: <" + tokenImage[i] + ">");
      }
   }

   private static final void jj_rescan_token() {
      jj_rescan = true;

      for (int i = 0; i < 32; i++) {
         Syntax2Parser.JJCalls syntax2parser$jjcalls = jj_2_rtns[i];

         do {
            if (syntax2parser$jjcalls.gen > jj_gen) {
               jj_la = syntax2parser$jjcalls.arg;
               jj_lastpos = jj_scanpos = syntax2parser$jjcalls.first;
               switch (i) {
                  case 0:
                     jj_3_1();
                     break;
                  case 1:
                     jj_3_2();
                     break;
                  case 2:
                     jj_3_3();
                     break;
                  case 3:
                     jj_3_4();
                     break;
                  case 4:
                     jj_3_5();
                     break;
                  case 5:
                     jj_3_6();
                     break;
                  case 6:
                     jj_3_7();
                     break;
                  case 7:
                     jj_3_8();
                     break;
                  case 8:
                     jj_3_9();
                     break;
                  case 9:
                     jj_3_10();
                     break;
                  case 10:
                     jj_3_11();
                     break;
                  case 11:
                     jj_3_12();
                     break;
                  case 12:
                     jj_3_13();
                     break;
                  case 13:
                     jj_3_14();
                     break;
                  case 14:
                     jj_3_15();
                     break;
                  case 15:
                     jj_3_16();
                     break;
                  case 16:
                     jj_3_17();
                     break;
                  case 17:
                     jj_3_18();
                     break;
                  case 18:
                     jj_3_19();
                     break;
                  case 19:
                     jj_3_20();
                     break;
                  case 20:
                     jj_3_21();
                     break;
                  case 21:
                     jj_3_22();
                     break;
                  case 22:
                     jj_3_23();
                     break;
                  case 23:
                     jj_3_24();
                     break;
                  case 24:
                     jj_3_25();
                     break;
                  case 25:
                     jj_3_26();
                     break;
                  case 26:
                     jj_3_27();
                     break;
                  case 27:
                     jj_3_28();
                     break;
                  case 28:
                     jj_3_29();
                     break;
                  case 29:
                     jj_3_30();
                     break;
                  case 30:
                     jj_3_31();
                     break;
                  case 31:
                     jj_3_32();
               }
            }

            syntax2parser$jjcalls = syntax2parser$jjcalls.next;
         } while (syntax2parser$jjcalls != null);
      }

      jj_rescan = false;
   }

   private static final void jj_save(int i, int j) {
      Syntax2Parser.JJCalls syntax2parser$jjcalls;
      for (syntax2parser$jjcalls = jj_2_rtns[i]; syntax2parser$jjcalls.gen > jj_gen; syntax2parser$jjcalls = syntax2parser$jjcalls.next) {
         if (syntax2parser$jjcalls.next == null) {
            syntax2parser$jjcalls = syntax2parser$jjcalls.next = new Syntax2Parser.JJCalls();
            break;
         }
      }

      syntax2parser$jjcalls.gen = jj_gen + j - jj_la;
      syntax2parser$jjcalls.first = token;
      syntax2parser$jjcalls.arg = j;
   }

   static final class JJCalls {
      int gen;
      Syntax2Token first;
      int arg;
      Syntax2Parser.JJCalls next;
   }
}
