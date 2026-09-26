package edu.ucla.phil.logic.pkgB;

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

public class Syntax1Parser extends FormulaParser implements Syntax1Constants {
   static Hashtable f189 = new Hashtable();
   private static boolean f190 = false;
   public static Syntax1TokenManager f191;
   static Syntax1CharStream f192;
   public static Syntax1Token f193;
   public static Syntax1Token f194;
   private static int f195;
   private static Syntax1Token f196;
   private static Syntax1Token f197;
   private static int f198;
   public static boolean f199 = false;
   private static boolean f200;
   private static int f201;
   private static final int[] f202 = new int[0];
   private static final int[] f203 = new int[0];
   private static final Syntax1Parser.C__A[] f204 = new Syntax1Parser.C__A[32];
   private static boolean f205 = false;
   private static int f206 = 0;
   private static Vector f207 = new Vector();
   private static int[] f208;
   private static int f209 = -1;
   private static int[] f210 = new int[100];
   private static int f211;
   private static int f212 = 0;
   private static boolean f213 = true;

   public static final Expression parse() throws Syntax1ParseException {
      try {
         return m278();
      } catch (Syntax1ParseException syntax1parseexception) {
         throw syntax1parseexception;
      }
   }

   public static final Expression m278() throws Syntax1ParseException {
      m367("one_line");

      Object object;
      try {
         if (m286(2147483647)) {
            Formula formula = m279();
            m359(4);
            return formula;
         }

         if (m287(2147483647)) {
            Term term = m285();
            m359(4);
            return term;
         }

         if (!m288(2147483647)) {
            if (!m289(2147483647)) {
               m359(-1);
               throw new Syntax1ParseException();
            }

            m359(0);
            return null;
         }

         m359(4);
         object = null;
      } finally {
         m368("one_line");
      }

      return (Expression)object;
   }

   public static final Formula m279() throws Syntax1ParseException {
      m367("formula");

      Object object1;
      try {
         Object object = m280();

         while (m290(2147483647)) {
            if (m291(2147483647)) {
               m359(10);
            } else {
               if (!m292(2147483647)) {
                  m359(-1);
                  throw new Syntax1ParseException();
               }

               m359(11);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(f193.f232);
            connectiveformula.m2040((Formula)object);
            Formula formula = m280();
            connectiveformula.m2041(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         m368("formula");
      }

      return (Formula)object1;
   }

   public static final Formula m280() throws Syntax1ParseException {
      m367("conjexp");

      Object object1;
      try {
         Object object = m283();

         while (m293(2147483647)) {
            if (m294(2147483647)) {
               m359(12);
            } else {
               if (!m295(2147483647)) {
                  m359(-1);
                  throw new Syntax1ParseException();
               }

               m359(13);
            }

            ConnectiveFormula connectiveformula = new ConnectiveFormula(f193.f232);
            connectiveformula.m2040((Formula)object);
            Formula formula = m283();
            connectiveformula.m2041(formula);
            object = connectiveformula;
         }

         object1 = object;
      } finally {
         m368("conjexp");
      }

      return (Formula)object1;
   }

   public static final Formula m281() throws Syntax1ParseException {
      m367("equation");

      IdentityFormula identityformula1;
      try {
         if (!m296(2147483647)) {
            if (!m297(2147483647)) {
               m359(-1);
               throw new Syntax1ParseException();
            }

            Term term2 = m285();
            m359(15);
            IdentityFormula identityformula2 = new IdentityFormula("=");
            identityformula2.m2176(term2);
            Term term3 = m285();
            identityformula2.m2177(term3);
            return identityformula2.negate().m2045();
         }

         Term term = m285();
         m359(14);
         IdentityFormula identityformula = new IdentityFormula(f193.f232);
         identityformula.m2176(term);
         Term term1 = m285();
         identityformula.m2177(term1);
         identityformula1 = identityformula;
      } finally {
         m368("equation");
      }

      return identityformula1;
   }

   public static final Formula m282() throws Syntax1ParseException {
      m367("member");

      MembershipFormula membershipformula1;
      try {
         Term term = m285();
         m359(16);
         MembershipFormula membershipformula = new MembershipFormula(f193.f232);
         membershipformula.m2087(term);
         Term term1 = m285();
         membershipformula.m2088(term1);
         membershipformula1 = membershipformula;
      } finally {
         m368("member");
      }

      return membershipformula1;
   }

   public static final Formula m283() throws Syntax1ParseException {
      m367("unary");

      Formula formula1;
      try {
         if (m298(2147483647)) {
            m359(17);
            ConnectiveFormula connectiveformula = new ConnectiveFormula(f193.f232);
            Formula formula4 = m283();
            connectiveformula.m2040(formula4);
            return connectiveformula;
         }

         if (m299(2147483647)) {
            m359(18);
            QuantifiedFormula quantifiedformula1 = new QuantifiedFormula(f193.f232);
            m359(5);
            SimpleTerm simpleterm1 = new SimpleTerm(f193.f232);
            quantifiedformula1.m1991(simpleterm1);
            f189.put(simpleterm1.getSymbol(), quantifiedformula1);
            Formula formula3 = m283();
            quantifiedformula1.m1992(formula3);
            f189.remove(simpleterm1.getSymbol());
            return quantifiedformula1;
         }

         if (m300(2147483647)) {
            m359(19);
            QuantifiedFormula quantifiedformula = new QuantifiedFormula(f193.f232);
            m359(5);
            SimpleTerm simpleterm = new SimpleTerm(f193.f232);
            quantifiedformula.m1991(simpleterm);
            f189.put(simpleterm.getSymbol(), quantifiedformula);
            Formula formula2 = m283();
            quantifiedformula.m1992(formula2);
            f189.remove(simpleterm.getSymbol());
            return quantifiedformula;
         }

         if (m301(2147483647)) {
            return m281();
         }

         if (!m302(2147483647)) {
            if (!m303(2147483647)) {
               m359(-1);
               throw new Syntax1ParseException();
            }

            return m284();
         }

         Formula formula = m282();
         formula1 = formula;
      } finally {
         m368("unary");
      }

      return formula1;
   }

   public static final Formula m284() throws Syntax1ParseException {
      m367("primary");

      AtomicFormula atomicformula1;
      try {
         if (m309(2147483647)) {
            m359(20);
            Formula formula = m279();
            m359(21);
            return formula;
         }

         if (m310(2147483647)) {
            if (m304(2147483647)) {
               m359(6);
            } else {
               if (!m305(2147483647)) {
                  m359(-1);
                  throw new Syntax1ParseException();
               }

               m359(7);
            }

            AtomicFormula atomicformula2 = new AtomicFormula(f193.f232);
            m359(20);

            do {
               Term term1 = m285();
               atomicformula2.m2028(term1);
            } while (m306(2147483647));

            m359(21);
            return atomicformula2;
         }

         if (m311(2147483647)) {
            m359(6);
            AtomicFormula atomicformula = new AtomicFormula(f193.f232);
            Term term = m285();
            atomicformula.m2028(term);
            return atomicformula;
         }

         if (!m312(2147483647)) {
            m359(-1);
            throw new Syntax1ParseException();
         }

         if (m307(2147483647)) {
            m359(7);
         } else {
            if (!m308(2147483647)) {
               m359(-1);
               throw new Syntax1ParseException();
            }

            m359(9);
         }

         atomicformula1 = new AtomicFormula(f193.f232);
      } finally {
         m368("primary");
      }

      return atomicformula1;
   }

   public static final Term m285() throws Syntax1ParseException {
      m367("term");

      OperationTerm operationterm1;
      try {
         if (m314(2147483647)) {
            m359(5);
            SimpleTerm simpleterm1 = new SimpleTerm(f193.f232);
            Expression expression = (Expression)f189.get(simpleterm1.getSymbol());
            if (expression != null) {
               simpleterm1.m1848(expression);
            }

            return simpleterm1;
         }

         if (!m315(2147483647)) {
            if (!m316(2147483647)) {
               if (!m317(2147483647)) {
                  m359(-1);
                  throw new Syntax1ParseException();
               }

               m359(22);
               DescriptionTerm descriptionterm = new DescriptionTerm(f193.f232);
               m359(5);
               SimpleTerm simpleterm = new SimpleTerm(f193.f232);
               descriptionterm.m1455(simpleterm);
               f189.put(simpleterm.getSymbol(), descriptionterm);
               Formula formula = m283();
               descriptionterm.m1456(formula);
               f189.remove(simpleterm.getSymbol());
               return descriptionterm;
            }

            m359(8);
            return new OperationTerm(f193.f232);
         }

         m359(8);
         OperationTerm operationterm = new OperationTerm(f193.f232);
         m359(20);

         do {
            Term term = m285();
            operationterm.m1963(term);
         } while (m313(2147483647));

         m359(21);
         operationterm1 = operationterm;
      } finally {
         m368("term");
      }

      return operationterm1;
   }

   private static final boolean m286(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m331();
      m372(0, i);
      return flag;
   }

   private static final boolean m287(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m328();
      m372(1, i);
      return flag;
   }

   private static final boolean m288(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m323();
      m372(2, i);
      return flag;
   }

   private static final boolean m289(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m320();
      m372(3, i);
      return flag;
   }

   private static final boolean m290(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m355();
      m372(4, i);
      return flag;
   }

   private static final boolean m291(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m353();
      m372(5, i);
      return flag;
   }

   private static final boolean m292(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m349();
      m372(6, i);
      return flag;
   }

   private static final boolean m293(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m348();
      m372(7, i);
      return flag;
   }

   private static final boolean m294(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m347();
      m372(8, i);
      return flag;
   }

   private static final boolean m295(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m346();
      m372(9, i);
      return flag;
   }

   private static final boolean m296(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m345();
      m372(10, i);
      return flag;
   }

   private static final boolean m297(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m343();
      m372(11, i);
      return flag;
   }

   private static final boolean m298(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m341();
      m372(12, i);
      return flag;
   }

   private static final boolean m299(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m339();
      m372(13, i);
      return flag;
   }

   private static final boolean m300(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m338();
      m372(14, i);
      return flag;
   }

   private static final boolean m301(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m337();
      m372(15, i);
      return flag;
   }

   private static final boolean m302(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m334();
      m372(16, i);
      return flag;
   }

   private static final boolean m303(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m333();
      m372(17, i);
      return flag;
   }

   private static final boolean m304(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m330();
      m372(18, i);
      return flag;
   }

   private static final boolean m305(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m324();
      m372(19, i);
      return flag;
   }

   private static final boolean m306(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m326();
      m372(20, i);
      return flag;
   }

   private static final boolean m307(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m325();
      m372(21, i);
      return flag;
   }

   private static final boolean m308(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m319();
      m372(22, i);
      return flag;
   }

   private static final boolean m309(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m336();
      m372(23, i);
      return flag;
   }

   private static final boolean m310(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m332();
      m372(24, i);
      return flag;
   }

   private static final boolean m311(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m329();
      m372(25, i);
      return flag;
   }

   private static final boolean m312(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m327();
      m372(26, i);
      return flag;
   }

   private static final boolean m313(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m351();
      m372(27, i);
      return flag;
   }

   private static final boolean m314(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m321();
      m372(28, i);
      return flag;
   }

   private static final boolean m315(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m356();
      m372(29, i);
      return flag;
   }

   private static final boolean m316(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m354();
      m372(30, i);
      return flag;
   }

   private static final boolean m317(int i) {
      f198 = i;
      f197 = f196 = f193;
      boolean flag = !m352();
      m372(31, i);
      return flag;
   }

   private static final boolean m318() {
      if (m350()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else {
         do {
            Syntax1Token syntax1token = f196;
            if (m355()) {
               f196 = syntax1token;
               return false;
            }
         } while (f198 != 0 || f196 != f197);

         return false;
      }
   }

   private static final boolean m319() {
      if (m360(9)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m320() {
      if (m360(0)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m321() {
      if (m360(5)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m322() {
      Syntax1Token syntax1token = f196;
      if (m321()) {
         f196 = syntax1token;
         if (m356()) {
            f196 = syntax1token;
            if (m354()) {
               f196 = syntax1token;
               if (m352()) {
                  return true;
               }

               if (f198 == 0 && f196 == f197) {
                  return false;
               }
            } else if (f198 == 0 && f196 == f197) {
               return false;
            }
         } else if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      return false;
   }

   private static final boolean m323() {
      if (m360(4)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m324() {
      if (m360(7)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m325() {
      if (m360(7)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m326() {
      if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m327() {
      Syntax1Token syntax1token = f196;
      if (m325()) {
         f196 = syntax1token;
         if (m319()) {
            return true;
         }

         if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      return false;
   }

   private static final boolean m328() {
      if (m322()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(4)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m329() {
      if (m360(6)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m330() {
      if (m360(6)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m331() {
      if (m318()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(4)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m332() {
      Syntax1Token syntax1token = f196;
      if (m330()) {
         f196 = syntax1token;
         if (m324()) {
            return true;
         }

         if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      if (m360(20)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m326()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else {
         do {
            syntax1token = f196;
            if (m326()) {
               f196 = syntax1token;
               if (m360(21)) {
                  return true;
               }

               if (f198 == 0 && f196 == f197) {
                  return false;
               }

               return false;
            }
         } while (f198 != 0 || f196 != f197);

         return false;
      }
   }

   private static final boolean m333() {
      if (m335()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m334() {
      if (m342()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m335() {
      Syntax1Token syntax1token = f196;
      if (m336()) {
         f196 = syntax1token;
         if (m332()) {
            f196 = syntax1token;
            if (m329()) {
               f196 = syntax1token;
               if (m327()) {
                  return true;
               }

               if (f198 == 0 && f196 == f197) {
                  return false;
               }
            } else if (f198 == 0 && f196 == f197) {
               return false;
            }
         } else if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      return false;
   }

   private static final boolean m336() {
      if (m360(20)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m318()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(21)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m337() {
      if (m344()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m338() {
      if (m360(19)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(5)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m340()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m339() {
      if (m360(18)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(5)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m340()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m340() {
      Syntax1Token syntax1token = f196;
      if (m341()) {
         f196 = syntax1token;
         if (m339()) {
            f196 = syntax1token;
            if (m338()) {
               f196 = syntax1token;
               if (m337()) {
                  f196 = syntax1token;
                  if (m334()) {
                     f196 = syntax1token;
                     if (m333()) {
                        return true;
                     }

                     if (f198 == 0 && f196 == f197) {
                        return false;
                     }
                  } else if (f198 == 0 && f196 == f197) {
                     return false;
                  }
               } else if (f198 == 0 && f196 == f197) {
                  return false;
               }
            } else if (f198 == 0 && f196 == f197) {
               return false;
            }
         } else if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      return false;
   }

   private static final boolean m341() {
      if (m360(17)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m340()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m342() {
      if (m322()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(16)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m343() {
      if (m322()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(15)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m344() {
      Syntax1Token syntax1token = f196;
      if (m345()) {
         f196 = syntax1token;
         if (m343()) {
            return true;
         }

         if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      return false;
   }

   private static final boolean m345() {
      if (m322()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(14)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m346() {
      if (m360(13)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m347() {
      if (m360(12)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m348() {
      Syntax1Token syntax1token = f196;
      if (m347()) {
         f196 = syntax1token;
         if (m346()) {
            return true;
         }

         if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      if (m340()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m349() {
      if (m360(11)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m350() {
      if (m340()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else {
         do {
            Syntax1Token syntax1token = f196;
            if (m348()) {
               f196 = syntax1token;
               return false;
            }
         } while (f198 != 0 || f196 != f197);

         return false;
      }
   }

   private static final boolean m351() {
      if (m322()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m352() {
      if (m360(22)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(5)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m340()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m353() {
      if (m360(10)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m354() {
      if (m360(8)) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m355() {
      Syntax1Token syntax1token = f196;
      if (m353()) {
         f196 = syntax1token;
         if (m349()) {
            return true;
         }

         if (f198 == 0 && f196 == f197) {
            return false;
         }
      } else if (f198 == 0 && f196 == f197) {
         return false;
      }

      if (m350()) {
         return true;
      } else {
         return f198 == 0 && f196 == f197 ? false : false;
      }
   }

   private static final boolean m356() {
      if (m360(8)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m360(20)) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else if (m351()) {
         return true;
      } else if (f198 == 0 && f196 == f197) {
         return false;
      } else {
         do {
            Syntax1Token syntax1token = f196;
            if (m351()) {
               f196 = syntax1token;
               if (m360(21)) {
                  return true;
               }

               if (f198 == 0 && f196 == f197) {
                  return false;
               }

               return false;
            }
         } while (f198 != 0 || f196 != f197);

         return false;
      }
   }

   public Syntax1Parser(InputStream inputstream) {
      if (f190) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f190 = true;
         f192 = new Syntax1CharStream(inputstream, 1, 1);
         f191 = new Syntax1TokenManager(f192);
         f193 = new Syntax1Token();
         f195 = -1;
         f201 = 0;

         for (int i = 0; i < 0; i++) {
            f202[i] = -1;
         }

         for (int j = 0; j < f204.length; j++) {
            f204[j] = new Syntax1Parser.C__A();
         }
      }
   }

   public static void m357(InputStream inputstream) {
      Syntax1CharStream.m252(inputstream, 1, 1);
      Syntax1TokenManager.m272(f192);
      f193 = new Syntax1Token();
      f195 = -1;
      f201 = 0;

      for (int i = 0; i < 0; i++) {
         f202[i] = -1;
      }

      for (int j = 0; j < f204.length; j++) {
         f204[j] = new Syntax1Parser.C__A();
      }
   }

   public Syntax1Parser(Reader reader) {
      if (f190) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f190 = true;
         f192 = new Syntax1CharStream(reader, 1, 1);
         f191 = new Syntax1TokenManager(f192);
         f193 = new Syntax1Token();
         f195 = -1;
         f201 = 0;

         for (int i = 0; i < 0; i++) {
            f202[i] = -1;
         }

         for (int j = 0; j < f204.length; j++) {
            f204[j] = new Syntax1Parser.C__A();
         }
      }
   }

   public static void reinit(Reader reader) {
      Syntax1CharStream.m250(reader, 1, 1);
      Syntax1TokenManager.m272(f192);
      f193 = new Syntax1Token();
      f195 = -1;
      f201 = 0;

      for (int i = 0; i < 0; i++) {
         f202[i] = -1;
      }

      for (int j = 0; j < f204.length; j++) {
         f204[j] = new Syntax1Parser.C__A();
      }
   }

   public Syntax1Parser(Syntax1TokenManager syntax1tokenmanager) {
      if (f190) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f190 = true;
         f191 = syntax1tokenmanager;
         f193 = new Syntax1Token();
         f195 = -1;
         f201 = 0;

         for (int i = 0; i < 0; i++) {
            f202[i] = -1;
         }

         for (int j = 0; j < f204.length; j++) {
            f204[j] = new Syntax1Parser.C__A();
         }
      }
   }

   public void m358(Syntax1TokenManager syntax1tokenmanager) {
      f191 = syntax1tokenmanager;
      f193 = new Syntax1Token();
      f195 = -1;
      f201 = 0;

      for (int i = 0; i < 0; i++) {
         f202[i] = -1;
      }

      for (int j = 0; j < f204.length; j++) {
         f204[j] = new Syntax1Parser.C__A();
      }
   }

   private static final Syntax1Token m359(int i) throws Syntax1ParseException {
      Syntax1Token syntax1token = f193;
      if (f193.f233 != null) {
         f193 = f193.f233;
      } else {
         f193 = f193.f233 = Syntax1TokenManager.m277();
      }

      f195 = -1;
      if (f193.f227 != i) {
         f193 = syntax1token;
         f209 = i;
         throw m365();
      } else {
         f201++;
         if (++f206 > 100) {
            f206 = 0;

            for (int j = 0; j < f204.length; j++) {
               for (Syntax1Parser.C__A syntax1parser$c__a = f204[j]; syntax1parser$c__a != null; syntax1parser$c__a = syntax1parser$c__a.f217) {
                  if (syntax1parser$c__a.f214 < f201) {
                     syntax1parser$c__a.f215 = null;
                  }
               }
            }
         }

         m369(f193, "");
         return f193;
      }
   }

   private static final boolean m360(int i) {
      if (f196 == f197) {
         f198--;
         if (f196.f233 == null) {
            f197 = f196 = f196.f233 = Syntax1TokenManager.m277();
         } else {
            f197 = f196 = f196.f233;
         }
      } else {
         f196 = f196.f233;
      }

      if (f205) {
         int j = 0;

         Syntax1Token syntax1token;
         for (syntax1token = f193; syntax1token != null && syntax1token != f196; syntax1token = syntax1token.f233) {
            j++;
         }

         if (syntax1token != null) {
            m364(i, j);
         }
      }

      return f196.f227 != i;
   }

   public static final Syntax1Token m361() {
      if (f193.f233 != null) {
         f193 = f193.f233;
      } else {
         f193 = f193.f233 = Syntax1TokenManager.m277();
      }

      f195 = -1;
      f201++;
      m369(f193, " (in getNextToken)");
      return f193;
   }

   public static final Syntax1Token m362(int i) {
      Syntax1Token syntax1token = f199 ? f196 : f193;

      for (int j = 0; j < i; j++) {
         if (syntax1token.f233 != null) {
            syntax1token = syntax1token.f233;
         } else {
            syntax1token = syntax1token.f233 = Syntax1TokenManager.m277();
         }
      }

      return syntax1token;
   }

   private static final int m363() {
      return (f194 = f193.f233) == null ? (f195 = (f193.f233 = Syntax1TokenManager.m277()).f227) : (f195 = f194.f227);
   }

   private static void m364(int i, int j) {
      if (j < 100) {
         if (j == f211 + 1) {
            f210[f211++] = i;
         } else if (f211 != 0) {
            f208 = new int[f211];

            for (int k = 0; k < f211; k++) {
               f208[k] = f210[k];
            }

            boolean flag = false;
            Enumeration enumeration = f207.elements();

            while (enumeration.hasMoreElements()) {
               int[] aint = (int[])enumeration.nextElement();
               if (aint.length == f208.length) {
                  flag = true;

                  for (int l = 0; l < f208.length; l++) {
                     if (aint[l] != f208[l]) {
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
               f207.addElement(f208);
            }

            if (j != 0) {
               int[] aint1 = f210;
               f211 = j;
               aint1[j - 1] = i;
            }
         }
      }
   }

   public static final Syntax1ParseException m365() {
      f207.removeAllElements();
      boolean[] aboolean = new boolean[23];

      for (int i = 0; i < 23; i++) {
         aboolean[i] = false;
      }

      if (f209 >= 0) {
         aboolean[f209] = true;
         f209 = -1;
      }

      for (int k = 0; k < 0; k++) {
         if (f202[k] == f201) {
            for (int j = 0; j < 32; j++) {
               if ((f203[k] & 1 << j) != 0) {
                  aboolean[j] = true;
               }
            }
         }
      }

      for (int l = 0; l < 23; l++) {
         if (aboolean[l]) {
            f208 = new int[1];
            f208[0] = l;
            f207.addElement(f208);
         }
      }

      f211 = 0;
      m371();
      m364(0, 0);
      int[][] aint = new int[f207.size()][];

      for (int i1 = 0; i1 < f207.size(); i1++) {
         aint[i1] = (int[])f207.elementAt(i1);
      }

      return new Syntax1ParseException(f193, aint, f226);
   }

   public static final void m366() {
      f213 = true;
   }

   public static final void disableTracing() {
      f213 = false;
   }

   private static final void m367(String s) {
      if (f213) {
         for (int i = 0; i < f212; i++) {
            System.out.print(" ");
         }

         System.out.println("Call:   " + s);
      }

      f212 += 2;
   }

   private static final void m368(String s) {
      f212 -= 2;
      if (f213) {
         for (int i = 0; i < f212; i++) {
            System.out.print(" ");
         }

         System.out.println("Return: " + s);
      }
   }

   private static final void m369(Syntax1Token syntax1token, String s) {
      if (f213) {
         for (int i = 0; i < f212; i++) {
            System.out.print(" ");
         }

         System.out.print("Consumed token: <" + f226[syntax1token.f227]);
         if (syntax1token.f227 != 0 && !f226[syntax1token.f227].equals("\"" + syntax1token.f232 + "\"")) {
            System.out.print(": \"" + syntax1token.f232 + "\"");
         }

         System.out.println(">" + s);
      }
   }

   private static final void m370(Syntax1Token syntax1token, int i) {
      if (f213) {
         for (int j = 0; j < f212; j++) {
            System.out.print(" ");
         }

         System.out.print("Visited token: <" + f226[syntax1token.f227]);
         if (syntax1token.f227 != 0 && !f226[syntax1token.f227].equals("\"" + syntax1token.f232 + "\"")) {
            System.out.print(": \"" + syntax1token.f232 + "\"");
         }

         System.out.println(">; Expected token: <" + f226[i] + ">");
      }
   }

   private static final void m371() {
      f205 = true;

      for (int i = 0; i < 32; i++) {
         Syntax1Parser.C__A syntax1parser$c__a = f204[i];

         do {
            if (syntax1parser$c__a.f214 > f201) {
               f198 = syntax1parser$c__a.f216;
               f197 = f196 = syntax1parser$c__a.f215;
               switch (i) {
                  case 0:
                     m331();
                     break;
                  case 1:
                     m328();
                     break;
                  case 2:
                     m323();
                     break;
                  case 3:
                     m320();
                     break;
                  case 4:
                     m355();
                     break;
                  case 5:
                     m353();
                     break;
                  case 6:
                     m349();
                     break;
                  case 7:
                     m348();
                     break;
                  case 8:
                     m347();
                     break;
                  case 9:
                     m346();
                     break;
                  case 10:
                     m345();
                     break;
                  case 11:
                     m343();
                     break;
                  case 12:
                     m341();
                     break;
                  case 13:
                     m339();
                     break;
                  case 14:
                     m338();
                     break;
                  case 15:
                     m337();
                     break;
                  case 16:
                     m334();
                     break;
                  case 17:
                     m333();
                     break;
                  case 18:
                     m330();
                     break;
                  case 19:
                     m324();
                     break;
                  case 20:
                     m326();
                     break;
                  case 21:
                     m325();
                     break;
                  case 22:
                     m319();
                     break;
                  case 23:
                     m336();
                     break;
                  case 24:
                     m332();
                     break;
                  case 25:
                     m329();
                     break;
                  case 26:
                     m327();
                     break;
                  case 27:
                     m351();
                     break;
                  case 28:
                     m321();
                     break;
                  case 29:
                     m356();
                     break;
                  case 30:
                     m354();
                     break;
                  case 31:
                     m352();
               }
            }

            syntax1parser$c__a = syntax1parser$c__a.f217;
         } while (syntax1parser$c__a == null);
      }

      f205 = false;
   }

   private static final void m372(int i, int j) {
      Syntax1Parser.C__A syntax1parser$c__a;
      for (syntax1parser$c__a = f204[i]; syntax1parser$c__a.f214 > f201; syntax1parser$c__a = syntax1parser$c__a.f217) {
         if (syntax1parser$c__a.f217 == null) {
            syntax1parser$c__a = syntax1parser$c__a.f217 = new Syntax1Parser.C__A();
            break;
         }
      }

      syntax1parser$c__a.f214 = f201 + j - f198;
      syntax1parser$c__a.f215 = f193;
      syntax1parser$c__a.f216 = j;
   }

   static final class C__A {
      int f214;
      Syntax1Token f215;
      int f216;
      Syntax1Parser.C__A f217;
   }
}
