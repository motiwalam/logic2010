package edu.ucla.phil.logic.pkgA;

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
   static Hashtable f70 = new Hashtable();
   private static boolean f71 = false;
   public static Syntax2TokenManager f72;
   static Syntax2CharStream f73;
   public static Syntax2Token f74;
   public static Syntax2Token f75;
   private static int f76;
   private static Syntax2Token f77;
   private static Syntax2Token f78;
   private static int f79;
   public static boolean f80 = false;
   private static boolean f81;
   private static int f82;
   private static final int[] f83 = new int[0];
   private static final int[] f84 = new int[0];
   private static final Syntax2Parser.C__A[] f85 = new Syntax2Parser.C__A[32];
   private static boolean f86 = false;
   private static int f87 = 0;
   private static Vector f88 = new Vector();
   private static int[] f89;
   private static int f90 = -1;
   private static int[] f91 = new int[100];
   private static int f92;
   private static int f93 = 0;
   private static boolean f94 = true;

   public static final Expression parse() throws Syntax2ParseException {
      try {
         return m115();
      } catch (Syntax2ParseException syntax2parseexception) {
         throw syntax2parseexception;
      }
   }

   public static final Expression m115() throws Syntax2ParseException {
      m206("one_line");

      Object object;
      try {
         if (m123(2147483647)) {
            Formula formula = m116();
            m197(4);
            return formula;
         }

         if (m124(2147483647)) {
            Term term = m122();
            m197(4);
            return term;
         }

         if (!m125(2147483647)) {
            if (!m126(2147483647)) {
               m197(-1);
               throw new Syntax2ParseException();
            }

            m197(0);
            return null;
         }

         m197(4);
         object = null;
      } finally {
         m207("one_line");
      }

      return (Expression)object;
   }

   public static final Formula m116() throws Syntax2ParseException {
      m206("formula");

      Object object1;
      try {
         Object object = m117();

         while (m127(2147483647)) {
            if (m128(2147483647)) {
               m197(10);
            } else {
               if (!m129(2147483647)) {
                  m197(-1);
                  throw new Syntax2ParseException();
               }

               m197(11);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(f74.f113);
            connectiveformula.m2040((Formula)object);
            Formula formula = m117();
            connectiveformula.m2041(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         m207("formula");
      }

      return (Formula)object1;
   }

   public static final Formula m117() throws Syntax2ParseException {
      m206("conjexp");

      Object object1;
      try {
         Object object = m120();

         while (m130(2147483647)) {
            if (m131(2147483647)) {
               m197(12);
            } else {
               if (!m132(2147483647)) {
                  m197(-1);
                  throw new Syntax2ParseException();
               }

               m197(13);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(f74.f113);
            connectiveformula.m2040((Formula)object);
            Formula formula = m120();
            connectiveformula.m2041(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         m207("conjexp");
      }

      return (Formula)object1;
   }

   public static final Formula m118() throws Syntax2ParseException {
      m206("equation");

      IdentityFormula identityformula1;
      try {
         if (!m133(2147483647)) {
            if (!m134(2147483647)) {
               m197(-1);
               throw new Syntax2ParseException();
            }

            Term term2 = m122();
            m197(15);
            IdentityFormula identityformula2 = new IdentityFormula("=");
            identityformula2.m2176(term2);
            Term term3 = m122();
            identityformula2.m2177(term3);
            return identityformula2.negate().m2045();
         }

         Term term = m122();
         m197(14);
         IdentityFormula identityformula = new IdentityFormula(f74.f113);
         identityformula.m2176(term);
         Term term1 = m122();
         identityformula.m2177(term1);
         identityformula1 = identityformula;
      } finally {
         m207("equation");
      }

      return identityformula1;
   }

   public static final Formula m119() throws Syntax2ParseException {
      m206("member");

      MembershipFormula membershipformula1;
      try {
         Term term = m122();
         m197(16);
         MembershipFormula membershipformula = new MembershipFormula(f74.f113);
         membershipformula.m2087(term);
         Term term1 = m122();
         membershipformula.m2088(term1);
         membershipformula1 = membershipformula;
      } finally {
         m207("member");
      }

      return membershipformula1;
   }

   public static final Formula m120() throws Syntax2ParseException {
      m206("unary");

      Formula formula1;
      try {
         if (m135(2147483647)) {
            m197(17);
            ConnectiveFormula connectiveformula = new ConnectiveFormula(f74.f113);
            Formula formula4 = m120();
            connectiveformula.m2040(formula4);
            return connectiveformula;
         }

         if (m136(2147483647)) {
            m197(18);
            QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(f74.f113);
            m197(5);
            SimpleTerm simpleterm1 = new SimpleTerm(f74.f113);
            quantifiedformula1.m1991(simpleterm1);
            f70.put(simpleterm1.getSymbol(), quantifiedformula1);
            Formula formula3 = m120();
            quantifiedformula1.m1992(formula3);
            f70.remove(simpleterm1.getSymbol());
            return quantifiedformula1;
         }

         if (m137(2147483647)) {
            m197(19);
            QuantifiedFormula quantifiedformula = new QuantifiedFormula(f74.f113);
            m197(5);
            SimpleTerm simpleterm = new SimpleTerm(f74.f113);
            quantifiedformula.m1991(simpleterm);
            f70.put(simpleterm.getSymbol(), quantifiedformula);
            Formula formula2 = m120();
            quantifiedformula.m1992(formula2);
            f70.remove(simpleterm.getSymbol());
            return quantifiedformula;
         }

         if (m138(2147483647)) {
            return m118();
         }

         if (!m139(2147483647)) {
            if (!m140(2147483647)) {
               m197(-1);
               throw new Syntax2ParseException();
            }

            return m121();
         }

         Formula formula = m119();
         formula1 = formula;
      } finally {
         m207("unary");
      }

      return formula1;
   }

   public static final Formula m121() throws Syntax2ParseException {
      m206("primary");

      AtomicFormula atomicformula1;
      try {
         if (m146(2147483647)) {
            m197(20);
            Formula formula = m116();
            m197(21);
            return formula;
         }

         if (m147(2147483647)) {
            if (m141(2147483647)) {
               m197(6);
            } else {
               if (!m142(2147483647)) {
                  m197(-1);
                  throw new Syntax2ParseException();
               }

               m197(7);
            }

            AtomicFormula atomicformula2 = new AtomicFormula(f74.f113);
            m197(20);

            do {
               Term term1 = m122();
               atomicformula2.m2028(term1);
            } while (m143(2147483647));

            m197(21);
            return atomicformula2;
         }

         if (m148(2147483647)) {
            m197(6);
            AtomicFormula atomicformula = new AtomicFormula(f74.f113);
            Term term = m122();
            atomicformula.m2028(term);
            return atomicformula;
         }

         if (!m149(2147483647)) {
            m197(-1);
            throw new Syntax2ParseException();
         }

         if (m144(2147483647)) {
            m197(7);
         } else {
            if (!m145(2147483647)) {
               m197(-1);
               throw new Syntax2ParseException();
            }

            m197(9);
         }

         atomicformula1 = new AtomicFormula(f74.f113);
      } finally {
         m207("primary");
      }

      return atomicformula1;
   }

   public static final Term m122() throws Syntax2ParseException {
      m206("term");

      OperationTerm operationterm1;
      try {
         if (m151(2147483647)) {
            m197(5);
            SimpleTerm simpleterm1 = new SimpleTerm(f74.f113);
            Expression expression = (Expression)f70.get(simpleterm1.getSymbol());
            if (expression != null) {
               simpleterm1.m1848(expression);
            }

            return simpleterm1;
         }

         if (!m152(2147483647)) {
            if (!m153(2147483647)) {
               if (!m154(2147483647)) {
                  m197(-1);
                  throw new Syntax2ParseException();
               }

               m197(22);
               DescriptionTerm descriptionterm = new DescriptionTerm(f74.f113);
               m197(5);
               SimpleTerm simpleterm = new SimpleTerm(f74.f113);
               descriptionterm.m1455(simpleterm);
               f70.put(simpleterm.getSymbol(), descriptionterm);
               Formula formula = m120();
               descriptionterm.m1456(formula);
               f70.remove(simpleterm.getSymbol());
               return descriptionterm;
            }

            m197(8);
            return new OperationTerm(f74.f113);
         }

         m197(8);
         OperationTerm operationterm = new OperationTerm(f74.f113);
         m197(20);

         do {
            Term term = m122();
            operationterm.m1963(term);
         } while (m150(2147483647));

         m197(21);
         operationterm1 = operationterm;
      } finally {
         m207("term");
      }

      return operationterm1;
   }

   private static final boolean m123(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m168();
      m211(0, i);
      return flag;
   }

   private static final boolean m124(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m165();
      m211(1, i);
      return flag;
   }

   private static final boolean m125(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m160();
      m211(2, i);
      return flag;
   }

   private static final boolean m126(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m157();
      m211(3, i);
      return flag;
   }

   private static final boolean m127(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m192();
      m211(4, i);
      return flag;
   }

   private static final boolean m128(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m190();
      m211(5, i);
      return flag;
   }

   private static final boolean m129(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m186();
      m211(6, i);
      return flag;
   }

   private static final boolean m130(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m185();
      m211(7, i);
      return flag;
   }

   private static final boolean m131(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m184();
      m211(8, i);
      return flag;
   }

   private static final boolean m132(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m183();
      m211(9, i);
      return flag;
   }

   private static final boolean m133(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m182();
      m211(10, i);
      return flag;
   }

   private static final boolean m134(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m180();
      m211(11, i);
      return flag;
   }

   private static final boolean m135(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m178();
      m211(12, i);
      return flag;
   }

   private static final boolean m136(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m176();
      m211(13, i);
      return flag;
   }

   private static final boolean m137(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m175();
      m211(14, i);
      return flag;
   }

   private static final boolean m138(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m174();
      m211(15, i);
      return flag;
   }

   private static final boolean m139(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m171();
      m211(16, i);
      return flag;
   }

   private static final boolean m140(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m170();
      m211(17, i);
      return flag;
   }

   private static final boolean m141(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m167();
      m211(18, i);
      return flag;
   }

   private static final boolean m142(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m161();
      m211(19, i);
      return flag;
   }

   private static final boolean m143(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m163();
      m211(20, i);
      return flag;
   }

   private static final boolean m144(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m162();
      m211(21, i);
      return flag;
   }

   private static final boolean m145(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m156();
      m211(22, i);
      return flag;
   }

   private static final boolean m146(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m173();
      m211(23, i);
      return flag;
   }

   private static final boolean m147(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m169();
      m211(24, i);
      return flag;
   }

   private static final boolean m148(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m166();
      m211(25, i);
      return flag;
   }

   private static final boolean m149(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m164();
      m211(26, i);
      return flag;
   }

   private static final boolean m150(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m188();
      m211(27, i);
      return flag;
   }

   private static final boolean m151(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m158();
      m211(28, i);
      return flag;
   }

   private static final boolean m152(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m193();
      m211(29, i);
      return flag;
   }

   private static final boolean m153(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m191();
      m211(30, i);
      return flag;
   }

   private static final boolean m154(int i) {
      f79 = i;
      f78 = f77 = f74;
      boolean flag = !m189();
      m211(31, i);
      return flag;
   }

   private static final boolean m155() {
      if (m187()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = f77;
            if (m192()) {
               f77 = syntax2token;
               return false;
            }
         } while (f79 != 0 || f77 != f78);

         return false;
      }
   }

   private static final boolean m156() {
      if (m198(9)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m157() {
      if (m198(0)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m158() {
      if (m198(5)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m159() {
      Syntax2Token syntax2token = f77;
      if (m158()) {
         f77 = syntax2token;
         if (m193()) {
            f77 = syntax2token;
            if (m191()) {
               f77 = syntax2token;
               if (m189()) {
                  return true;
               }

               if (f79 == 0 && f77 == f78) {
                  return false;
               }
            } else if (f79 == 0 && f77 == f78) {
               return false;
            }
         } else if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      return false;
   }

   private static final boolean m160() {
      if (m198(4)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m161() {
      if (m198(7)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m162() {
      if (m198(7)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m163() {
      if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m164() {
      Syntax2Token syntax2token = f77;
      if (m162()) {
         f77 = syntax2token;
         if (m156()) {
            return true;
         }

         if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      return false;
   }

   private static final boolean m165() {
      if (m159()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(4)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m166() {
      if (m198(6)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m167() {
      if (m198(6)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m168() {
      if (m155()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(4)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m169() {
      Syntax2Token syntax2token = f77;
      if (m167()) {
         f77 = syntax2token;
         if (m161()) {
            return true;
         }

         if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      if (m198(20)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m163()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else {
         do {
            syntax2token = f77;
            if (m163()) {
               f77 = syntax2token;
               if (m198(21)) {
                  return true;
               }

               if (f79 == 0 && f77 == f78) {
                  return false;
               }

               return false;
            }
         } while (f79 != 0 || f77 != f78);

         return false;
      }
   }

   private static final boolean m170() {
      if (m172()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m171() {
      if (m179()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m172() {
      Syntax2Token syntax2token = f77;
      if (m173()) {
         f77 = syntax2token;
         if (m169()) {
            f77 = syntax2token;
            if (m166()) {
               f77 = syntax2token;
               if (m164()) {
                  return true;
               }

               if (f79 == 0 && f77 == f78) {
                  return false;
               }
            } else if (f79 == 0 && f77 == f78) {
               return false;
            }
         } else if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      return false;
   }

   private static final boolean m173() {
      if (m198(20)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m155()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(21)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m174() {
      if (m181()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m175() {
      if (m198(19)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(5)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m177()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m176() {
      if (m198(18)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(5)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m177()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m177() {
      Syntax2Token syntax2token = f77;
      if (m178()) {
         f77 = syntax2token;
         if (m176()) {
            f77 = syntax2token;
            if (m175()) {
               f77 = syntax2token;
               if (m174()) {
                  f77 = syntax2token;
                  if (m171()) {
                     f77 = syntax2token;
                     if (m170()) {
                        return true;
                     }

                     if (f79 == 0 && f77 == f78) {
                        return false;
                     }
                  } else if (f79 == 0 && f77 == f78) {
                     return false;
                  }
               } else if (f79 == 0 && f77 == f78) {
                  return false;
               }
            } else if (f79 == 0 && f77 == f78) {
               return false;
            }
         } else if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      return false;
   }

   private static final boolean m178() {
      if (m198(17)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m177()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m179() {
      if (m159()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(16)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m180() {
      if (m159()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(15)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m181() {
      Syntax2Token syntax2token = f77;
      if (m182()) {
         f77 = syntax2token;
         if (m180()) {
            return true;
         }

         if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      return false;
   }

   private static final boolean m182() {
      if (m159()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(14)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m183() {
      if (m198(13)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m184() {
      if (m198(12)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m185() {
      Syntax2Token syntax2token = f77;
      if (m184()) {
         f77 = syntax2token;
         if (m183()) {
            return true;
         }

         if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      if (m177()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m186() {
      if (m198(11)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m187() {
      if (m177()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = f77;
            if (m185()) {
               f77 = syntax2token;
               return false;
            }
         } while (f79 != 0 || f77 != f78);

         return false;
      }
   }

   private static final boolean m188() {
      if (m159()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m189() {
      if (m198(22)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(5)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m177()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m190() {
      if (m198(10)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m191() {
      if (m198(8)) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m192() {
      Syntax2Token syntax2token = f77;
      if (m190()) {
         f77 = syntax2token;
         if (m186()) {
            return true;
         }

         if (f79 == 0 && f77 == f78) {
            return false;
         }
      } else if (f79 == 0 && f77 == f78) {
         return false;
      }

      if (m187()) {
         return true;
      } else {
         return f79 == 0 && f77 == f78 ? false : false;
      }
   }

   private static final boolean m193() {
      if (m198(8)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m198(20)) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else if (m188()) {
         return true;
      } else if (f79 == 0 && f77 == f78) {
         return false;
      } else {
         do {
            Syntax2Token syntax2token = f77;
            if (m188()) {
               f77 = syntax2token;
               if (m198(21)) {
                  return true;
               }

               if (f79 == 0 && f77 == f78) {
                  return false;
               }

               return false;
            }
         } while (f79 != 0 || f77 != f78);

         return false;
      }
   }

   public Syntax2Parser(InputStream inputstream) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f73 = new Syntax2CharStream(inputstream, 1, 1);
         f72 = new Syntax2TokenManager(f73);
         f74 = new Syntax2Token();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new Syntax2Parser.C__A();
         }
      }
   }

   public static void m194(InputStream inputstream) {
      Syntax2CharStream.m88(inputstream, 1, 1);
      Syntax2TokenManager.m108(f73);
      f74 = new Syntax2Token();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new Syntax2Parser.C__A();
      }
   }

   public Syntax2Parser(Reader reader) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f73 = new Syntax2CharStream(reader, 1, 1);
         f72 = new Syntax2TokenManager(f73);
         f74 = new Syntax2Token();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new Syntax2Parser.C__A();
         }
      }
   }

   public static void reinit(Reader reader) {
      Syntax2CharStream.m86(reader, 1, 1);
      Syntax2TokenManager.m108(f73);
      f74 = new Syntax2Token();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new Syntax2Parser.C__A();
      }
   }

   public Syntax2Parser(Syntax2TokenManager syntax2tokenmanager) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f72 = syntax2tokenmanager;
         f74 = new Syntax2Token();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new Syntax2Parser.C__A();
         }
      }
   }

   public void m196(Syntax2TokenManager syntax2tokenmanager) {
      f72 = syntax2tokenmanager;
      f74 = new Syntax2Token();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new Syntax2Parser.C__A();
      }
   }

   private static final Syntax2Token m197(int i) throws Syntax2ParseException {
      Syntax2Token syntax2token = f74;
      if (f74.f114 != null) {
         f74 = f74.f114;
      } else {
         f74 = f74.f114 = Syntax2TokenManager.m113();
      }

      f76 = -1;
      if (f74.f108 != i) {
         f74 = syntax2token;
         f90 = i;
         throw m203();
      } else {
         f82++;
         if (++f87 > 100) {
            f87 = 0;

            for (int j = 0; j < f85.length; j++) {
               for (Syntax2Parser.C__A syntax2parser$c__a = f85[j]; syntax2parser$c__a != null; syntax2parser$c__a = syntax2parser$c__a.f98) {
                  if (syntax2parser$c__a.f95 < f82) {
                     syntax2parser$c__a.f96 = null;
                  }
               }
            }
         }

         m208(f74, "");
         return f74;
      }
   }

   private static final boolean m198(int i) {
      if (f77 == f78) {
         f79--;
         if (f77.f114 == null) {
            f78 = f77 = f77.f114 = Syntax2TokenManager.m113();
         } else {
            f78 = f77 = f77.f114;
         }
      } else {
         f77 = f77.f114;
      }

      if (f86) {
         int j = 0;

         Syntax2Token syntax2token;
         for (syntax2token = f74; syntax2token != null && syntax2token != f77; syntax2token = syntax2token.f114) {
            j++;
         }

         if (syntax2token != null) {
            m202(i, j);
         }
      }

      return f77.f108 != i;
   }

   public static final Syntax2Token m199() {
      if (f74.f114 != null) {
         f74 = f74.f114;
      } else {
         f74 = f74.f114 = Syntax2TokenManager.m113();
      }

      f76 = -1;
      f82++;
      m208(f74, " (in getNextToken)");
      return f74;
   }

   public static final Syntax2Token m200(int i) {
      Syntax2Token syntax2token = f80 ? f77 : f74;

      for (int j = 0; j < i; j++) {
         if (syntax2token.f114 != null) {
            syntax2token = syntax2token.f114;
         } else {
            syntax2token = syntax2token.f114 = Syntax2TokenManager.m113();
         }
      }

      return syntax2token;
   }

   private static final int m201() {
      return (f75 = f74.f114) == null ? (f76 = (f74.f114 = Syntax2TokenManager.m113()).f108) : (f76 = f75.f108);
   }

   private static void m202(int i, int j) {
      if (j < 100) {
         if (j == f92 + 1) {
            f91[f92++] = i;
         } else if (f92 != 0) {
            f89 = new int[f92];

            for (int k = 0; k < f92; k++) {
               f89[k] = f91[k];
            }

            boolean flag = false;
            Enumeration enumeration = f88.elements();

            while (enumeration.hasMoreElements()) {
               int[] aint = (int[])enumeration.nextElement();
               if (aint.length == f89.length) {
                  flag = true;

                  for (int l = 0; l < f89.length; l++) {
                     if (aint[l] != f89[l]) {
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
               f88.addElement(f89);
            }

            if (j != 0) {
               int[] aint1 = f91;
               f92 = j;
               aint1[j - 1] = i;
            }
         }
      }
   }

   public static final Syntax2ParseException m203() {
      f88.removeAllElements();
      boolean[] aboolean = new boolean[23];

      for (int i = 0; i < 23; i++) {
         aboolean[i] = false;
      }

      if (f90 >= 0) {
         aboolean[f90] = true;
         f90 = -1;
      }

      for (int k = 0; k < 0; k++) {
         if (f83[k] == f82) {
            for (int j = 0; j < 32; j++) {
               if ((f84[k] & 1 << j) != 0) {
                  aboolean[j] = true;
               }
            }
         }
      }

      for (int l = 0; l < 23; l++) {
         if (aboolean[l]) {
            f89 = new int[1];
            f89[0] = l;
            f88.addElement(f89);
         }
      }

      f92 = 0;
      m210();
      m202(0, 0);
      int[][] aint = new int[f88.size()][];

      for (int i1 = 0; i1 < f88.size(); i1++) {
         aint[i1] = (int[])f88.elementAt(i1);
      }

      return new Syntax2ParseException(f74, aint, f107);
   }

   public static final void m204() {
      f94 = true;
   }

   public static final void disableTracing() {
      f94 = false;
   }

   private static final void m206(String s) {
      if (f94) {
         for (int i = 0; i < f93; i++) {
            System.out.print(" ");
         }

         System.out.println("Call:   " + s);
      }

      f93 += 2;
   }

   private static final void m207(String s) {
      f93 -= 2;
      if (f94) {
         for (int i = 0; i < f93; i++) {
            System.out.print(" ");
         }

         System.out.println("Return: " + s);
      }
   }

   private static final void m208(Syntax2Token syntax2token, String s) {
      if (f94) {
         for (int i = 0; i < f93; i++) {
            System.out.print(" ");
         }

         System.out.print("Consumed token: <" + f107[syntax2token.f108]);
         if (syntax2token.f108 != 0 && !f107[syntax2token.f108].equals("\"" + syntax2token.f113 + "\"")) {
            System.out.print(": \"" + syntax2token.f113 + "\"");
         }

         System.out.println(">" + s);
      }
   }

   private static final void m209(Syntax2Token syntax2token, int i) {
      if (f94) {
         for (int j = 0; j < f93; j++) {
            System.out.print(" ");
         }

         System.out.print("Visited token: <" + f107[syntax2token.f108]);
         if (syntax2token.f108 != 0 && !f107[syntax2token.f108].equals("\"" + syntax2token.f113 + "\"")) {
            System.out.print(": \"" + syntax2token.f113 + "\"");
         }

         System.out.println(">; Expected token: <" + f107[i] + ">");
      }
   }

   private static final void m210() {
      f86 = true;

      for (int i = 0; i < 32; i++) {
         Syntax2Parser.C__A syntax2parser$c__a = f85[i];

         do {
            if (syntax2parser$c__a.f95 > f82) {
               f79 = syntax2parser$c__a.f97;
               f78 = f77 = syntax2parser$c__a.f96;
               switch (i) {
                  case 0:
                     m168();
                     break;
                  case 1:
                     m165();
                     break;
                  case 2:
                     m160();
                     break;
                  case 3:
                     m157();
                     break;
                  case 4:
                     m192();
                     break;
                  case 5:
                     m190();
                     break;
                  case 6:
                     m186();
                     break;
                  case 7:
                     m185();
                     break;
                  case 8:
                     m184();
                     break;
                  case 9:
                     m183();
                     break;
                  case 10:
                     m182();
                     break;
                  case 11:
                     m180();
                     break;
                  case 12:
                     m178();
                     break;
                  case 13:
                     m176();
                     break;
                  case 14:
                     m175();
                     break;
                  case 15:
                     m174();
                     break;
                  case 16:
                     m171();
                     break;
                  case 17:
                     m170();
                     break;
                  case 18:
                     m167();
                     break;
                  case 19:
                     m161();
                     break;
                  case 20:
                     m163();
                     break;
                  case 21:
                     m162();
                     break;
                  case 22:
                     m156();
                     break;
                  case 23:
                     m173();
                     break;
                  case 24:
                     m169();
                     break;
                  case 25:
                     m166();
                     break;
                  case 26:
                     m164();
                     break;
                  case 27:
                     m188();
                     break;
                  case 28:
                     m158();
                     break;
                  case 29:
                     m193();
                     break;
                  case 30:
                     m191();
                     break;
                  case 31:
                     m189();
               }
            }

            syntax2parser$c__a = syntax2parser$c__a.f98;
         } while (syntax2parser$c__a == null);
      }

      f86 = false;
   }

   private static final void m211(int i, int j) {
      Syntax2Parser.C__A syntax2parser$c__a;
      for (syntax2parser$c__a = f85[i]; syntax2parser$c__a.f95 > f82; syntax2parser$c__a = syntax2parser$c__a.f98) {
         if (syntax2parser$c__a.f98 == null) {
            syntax2parser$c__a = syntax2parser$c__a.f98 = new Syntax2Parser.C__A();
            break;
         }
      }

      syntax2parser$c__a.f95 = f82 + j - f79;
      syntax2parser$c__a.f96 = f74;
      syntax2parser$c__a.f97 = j;
   }

   static final class C__A {
      int f95;
      Syntax2Token f96;
      int f97;
      Syntax2Parser.C__A f98;
   }
}
