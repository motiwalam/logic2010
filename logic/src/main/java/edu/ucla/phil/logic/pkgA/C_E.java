package edu.ucla.phil.logic.pkgA;

import edu.ucla.phil.logic.C_FB;
import edu.ucla.phil.logic.C_RF;
import edu.ucla.phil.logic.C_WE;
import edu.ucla.phil.logic.C_X;
import edu.ucla.phil.logic.C_i_;
import edu.ucla.phil.logic.C_n_C;
import edu.ucla.phil.logic.C_o_A;
import edu.ucla.phil.logic.C_q_A;
import edu.ucla.phil.logic.C_q_F;
import edu.ucla.phil.logic.C_t_D;
import edu.ucla.phil.logic.C_x_D;
import edu.ucla.phil.logic.C_y_A;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class C_E extends C_FB implements C_F {
   static Hashtable f70 = new Hashtable();
   private static boolean f71 = false;
   public static C_D f72;
   static C_B f73;
   public static C_G f74;
   public static C_G f75;
   private static int f76;
   private static C_G f77;
   private static C_G f78;
   private static int f79;
   public static boolean f80 = false;
   private static boolean f81;
   private static int f82;
   private static final int[] f83 = new int[0];
   private static final int[] f84 = new int[0];
   private static final C_E.C__A[] f85 = new C_E.C__A[32];
   private static boolean f86 = false;
   private static int f87 = 0;
   private static Vector f88 = new Vector();
   private static int[] f89;
   private static int f90 = -1;
   private static int[] f91 = new int[100];
   private static int f92;
   private static int f93 = 0;
   private static boolean f94 = true;

   public static final C_RF m114() throws C_A {
      try {
         return m115();
      } catch (C_A c_a) {
         throw c_a;
      }
   }

   public static final C_RF m115() throws C_A {
      m206("one_line");

      Object object;
      try {
         if (m123(2147483647)) {
            C_y_A c_y_a = m116();
            m197(4);
            return c_y_a;
         }

         if (m124(2147483647)) {
            C_X c_x = m122();
            m197(4);
            return c_x;
         }

         if (!m125(2147483647)) {
            if (!m126(2147483647)) {
               m197(-1);
               throw new C_A();
            }

            m197(0);
            return null;
         }

         m197(4);
         object = null;
      } finally {
         m207("one_line");
      }

      return (C_RF)object;
   }

   public static final C_y_A m116() throws C_A {
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
                  throw new C_A();
               }

               m197(11);
            }

            C_q_F c_q_f = new C_q_F(f74.f113);
            c_q_f.m2040((C_y_A)object);
            C_y_A c_y_a = m117();
            c_q_f.m2041(c_y_a);
            object = c_q_f;
         }

         object1 = object;
      } finally {
         m207("formula");
      }

      return (C_y_A)object1;
   }

   public static final C_y_A m117() throws C_A {
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
                  throw new C_A();
               }

               m197(13);
            }

            C_q_F c_q_f = new C_q_F(f74.f113);
            c_q_f.m2040((C_y_A)object);
            C_y_A c_y_a = m120();
            c_q_f.m2041(c_y_a);
            object = c_q_f;
         }

         object1 = object;
      } finally {
         m207("conjexp");
      }

      return (C_y_A)object1;
   }

   public static final C_y_A m118() throws C_A {
      m206("equation");

      C_x_D c_x_d1;
      try {
         if (!m133(2147483647)) {
            if (!m134(2147483647)) {
               m197(-1);
               throw new C_A();
            }

            C_X c_x2 = m122();
            m197(15);
            C_x_D c_x_d2 = new C_x_D("=");
            c_x_d2.m2176(c_x2);
            C_X c_x3 = m122();
            c_x_d2.m2177(c_x3);
            return c_x_d2.m1256().m2045();
         }

         C_X c_x = m122();
         m197(14);
         C_x_D c_x_d = new C_x_D(f74.f113);
         c_x_d.m2176(c_x);
         C_X c_x1 = m122();
         c_x_d.m2177(c_x1);
         c_x_d1 = c_x_d;
      } finally {
         m207("equation");
      }

      return c_x_d1;
   }

   public static final C_y_A m119() throws C_A {
      m206("member");

      C_t_D c_t_d1;
      try {
         C_X c_x = m122();
         m197(16);
         C_t_D c_t_d = new C_t_D(f74.f113);
         c_t_d.m2087(c_x);
         C_X c_x1 = m122();
         c_t_d.m2088(c_x1);
         c_t_d1 = c_t_d;
      } finally {
         m207("member");
      }

      return c_t_d1;
   }

   public static final C_y_A m120() throws C_A {
      m206("unary");

      C_y_A c_y_a1;
      try {
         if (m135(2147483647)) {
            m197(17);
            C_q_F c_q_f = new C_q_F(f74.f113);
            C_y_A c_y_a4 = m120();
            c_q_f.m2040(c_y_a4);
            return c_q_f;
         }

         if (m136(2147483647)) {
            m197(18);
            C_o_A c_o_a1 = new C_o_A(f74.f113);
            m197(5);
            C_i_ c_i_1 = new C_i_(f74.f113);
            c_o_a1.m1991(c_i_1);
            f70.put(c_i_1.m1214(), c_o_a1);
            C_y_A c_y_a3 = m120();
            c_o_a1.m1992(c_y_a3);
            f70.remove(c_i_1.m1214());
            return c_o_a1;
         }

         if (m137(2147483647)) {
            m197(19);
            C_o_A c_o_a = new C_o_A(f74.f113);
            m197(5);
            C_i_ c_i_ = new C_i_(f74.f113);
            c_o_a.m1991(c_i_);
            f70.put(c_i_.m1214(), c_o_a);
            C_y_A c_y_a2 = m120();
            c_o_a.m1992(c_y_a2);
            f70.remove(c_i_.m1214());
            return c_o_a;
         }

         if (m138(2147483647)) {
            return m118();
         }

         if (!m139(2147483647)) {
            if (!m140(2147483647)) {
               m197(-1);
               throw new C_A();
            }

            return m121();
         }

         C_y_A c_y_a = m119();
         c_y_a1 = c_y_a;
      } finally {
         m207("unary");
      }

      return c_y_a1;
   }

   public static final C_y_A m121() throws C_A {
      m206("primary");

      C_q_A c_q_a1;
      try {
         if (m146(2147483647)) {
            m197(20);
            C_y_A c_y_a = m116();
            m197(21);
            return c_y_a;
         }

         if (m147(2147483647)) {
            if (m141(2147483647)) {
               m197(6);
            } else {
               if (!m142(2147483647)) {
                  m197(-1);
                  throw new C_A();
               }

               m197(7);
            }

            C_q_A c_q_a2 = new C_q_A(f74.f113);
            m197(20);

            do {
               C_X c_x1 = m122();
               c_q_a2.m2028(c_x1);
            } while (m143(2147483647));

            m197(21);
            return c_q_a2;
         }

         if (m148(2147483647)) {
            m197(6);
            C_q_A c_q_a = new C_q_A(f74.f113);
            C_X c_x = m122();
            c_q_a.m2028(c_x);
            return c_q_a;
         }

         if (!m149(2147483647)) {
            m197(-1);
            throw new C_A();
         }

         if (m144(2147483647)) {
            m197(7);
         } else {
            if (!m145(2147483647)) {
               m197(-1);
               throw new C_A();
            }

            m197(9);
         }

         c_q_a1 = new C_q_A(f74.f113);
      } finally {
         m207("primary");
      }

      return c_q_a1;
   }

   public static final C_X m122() throws C_A {
      m206("term");

      C_n_C c_n_c1;
      try {
         if (m151(2147483647)) {
            m197(5);
            C_i_ c_i_1 = new C_i_(f74.f113);
            C_RF c_rf = (C_RF)f70.get(c_i_1.m1214());
            if (c_rf != null) {
               c_i_1.m1848(c_rf);
            }

            return c_i_1;
         }

         if (!m152(2147483647)) {
            if (!m153(2147483647)) {
               if (!m154(2147483647)) {
                  m197(-1);
                  throw new C_A();
               }

               m197(22);
               C_WE c_we = new C_WE(f74.f113);
               m197(5);
               C_i_ c_i_ = new C_i_(f74.f113);
               c_we.m1455(c_i_);
               f70.put(c_i_.m1214(), c_we);
               C_y_A c_y_a = m120();
               c_we.m1456(c_y_a);
               f70.remove(c_i_.m1214());
               return c_we;
            }

            m197(8);
            return new C_n_C(f74.f113);
         }

         m197(8);
         C_n_C c_n_c = new C_n_C(f74.f113);
         m197(20);

         do {
            C_X c_x = m122();
            c_n_c.m1963(c_x);
         } while (m150(2147483647));

         m197(21);
         c_n_c1 = c_n_c;
      } finally {
         m207("term");
      }

      return c_n_c1;
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
            C_G c_g = f77;
            if (m192()) {
               f77 = c_g;
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
      C_G c_g = f77;
      if (m158()) {
         f77 = c_g;
         if (m193()) {
            f77 = c_g;
            if (m191()) {
               f77 = c_g;
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
      C_G c_g = f77;
      if (m162()) {
         f77 = c_g;
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
      C_G c_g = f77;
      if (m167()) {
         f77 = c_g;
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
            c_g = f77;
            if (m163()) {
               f77 = c_g;
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
      C_G c_g = f77;
      if (m173()) {
         f77 = c_g;
         if (m169()) {
            f77 = c_g;
            if (m166()) {
               f77 = c_g;
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
      C_G c_g = f77;
      if (m178()) {
         f77 = c_g;
         if (m176()) {
            f77 = c_g;
            if (m175()) {
               f77 = c_g;
               if (m174()) {
                  f77 = c_g;
                  if (m171()) {
                     f77 = c_g;
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
      C_G c_g = f77;
      if (m182()) {
         f77 = c_g;
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
      C_G c_g = f77;
      if (m184()) {
         f77 = c_g;
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
            C_G c_g = f77;
            if (m185()) {
               f77 = c_g;
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
      C_G c_g = f77;
      if (m190()) {
         f77 = c_g;
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
            C_G c_g = f77;
            if (m188()) {
               f77 = c_g;
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

   public C_E(InputStream inputstream) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f73 = new C_B(inputstream, 1, 1);
         f72 = new C_D(f73);
         f74 = new C_G();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new C_E.C__A();
         }
      }
   }

   public static void m194(InputStream inputstream) {
      C_B.m88(inputstream, 1, 1);
      C_D.m108(f73);
      f74 = new C_G();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new C_E.C__A();
      }
   }

   public C_E(Reader reader) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f73 = new C_B(reader, 1, 1);
         f72 = new C_D(f73);
         f74 = new C_G();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new C_E.C__A();
         }
      }
   }

   public static void m195(Reader reader) {
      C_B.m86(reader, 1, 1);
      C_D.m108(f73);
      f74 = new C_G();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new C_E.C__A();
      }
   }

   public C_E(C_D c_d) {
      if (f71) {
         System.out.println("ERROR: Second call to constructor of static parser.  You must");
         System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
         System.out.println("       during parser generation.");
         throw new Error();
      } else {
         f71 = true;
         f72 = c_d;
         f74 = new C_G();
         f76 = -1;
         f82 = 0;

         for (int i = 0; i < 0; i++) {
            f83[i] = -1;
         }

         for (int j = 0; j < f85.length; j++) {
            f85[j] = new C_E.C__A();
         }
      }
   }

   public void m196(C_D c_d) {
      f72 = c_d;
      f74 = new C_G();
      f76 = -1;
      f82 = 0;

      for (int i = 0; i < 0; i++) {
         f83[i] = -1;
      }

      for (int j = 0; j < f85.length; j++) {
         f85[j] = new C_E.C__A();
      }
   }

   private static final C_G m197(int i) throws C_A {
      C_G c_g = f74;
      if (f74.f114 != null) {
         f74 = f74.f114;
      } else {
         f74 = f74.f114 = C_D.m113();
      }

      f76 = -1;
      if (f74.f108 != i) {
         f74 = c_g;
         f90 = i;
         throw m203();
      } else {
         f82++;
         if (++f87 > 100) {
            f87 = 0;

            for (int j = 0; j < f85.length; j++) {
               for (C_E.C__A c_e$c__a = f85[j]; c_e$c__a != null; c_e$c__a = c_e$c__a.f98) {
                  if (c_e$c__a.f95 < f82) {
                     c_e$c__a.f96 = null;
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
            f78 = f77 = f77.f114 = C_D.m113();
         } else {
            f78 = f77 = f77.f114;
         }
      } else {
         f77 = f77.f114;
      }

      if (f86) {
         int j = 0;

         C_G c_g;
         for (c_g = f74; c_g != null && c_g != f77; c_g = c_g.f114) {
            j++;
         }

         if (c_g != null) {
            m202(i, j);
         }
      }

      return f77.f108 != i;
   }

   public static final C_G m199() {
      if (f74.f114 != null) {
         f74 = f74.f114;
      } else {
         f74 = f74.f114 = C_D.m113();
      }

      f76 = -1;
      f82++;
      m208(f74, " (in getNextToken)");
      return f74;
   }

   public static final C_G m200(int i) {
      C_G c_g = f80 ? f77 : f74;

      for (int j = 0; j < i; j++) {
         if (c_g.f114 != null) {
            c_g = c_g.f114;
         } else {
            c_g = c_g.f114 = C_D.m113();
         }
      }

      return c_g;
   }

   private static final int m201() {
      return (f75 = f74.f114) == null ? (f76 = (f74.f114 = C_D.m113()).f108) : (f76 = f75.f108);
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

   public static final C_A m203() {
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

      return new C_A(f74, aint, f107);
   }

   public static final void m204() {
      f94 = true;
   }

   public static final void m205() {
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

   private static final void m208(C_G c_g, String s) {
      if (f94) {
         for (int i = 0; i < f93; i++) {
            System.out.print(" ");
         }

         System.out.print("Consumed token: <" + f107[c_g.f108]);
         if (c_g.f108 != 0 && !f107[c_g.f108].equals("\"" + c_g.f113 + "\"")) {
            System.out.print(": \"" + c_g.f113 + "\"");
         }

         System.out.println(">" + s);
      }
   }

   private static final void m209(C_G c_g, int i) {
      if (f94) {
         for (int j = 0; j < f93; j++) {
            System.out.print(" ");
         }

         System.out.print("Visited token: <" + f107[c_g.f108]);
         if (c_g.f108 != 0 && !f107[c_g.f108].equals("\"" + c_g.f113 + "\"")) {
            System.out.print(": \"" + c_g.f113 + "\"");
         }

         System.out.println(">; Expected token: <" + f107[i] + ">");
      }
   }

   private static final void m210() {
      f86 = true;

      for (int i = 0; i < 32; i++) {
         C_E.C__A c_e$c__a = f85[i];

         do {
            if (c_e$c__a.f95 > f82) {
               f79 = c_e$c__a.f97;
               f78 = f77 = c_e$c__a.f96;
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

            c_e$c__a = c_e$c__a.f98;
         } while (c_e$c__a == null);
      }

      f86 = false;
   }

   private static final void m211(int i, int j) {
      C_E.C__A c_e$c__a;
      for (c_e$c__a = f85[i]; c_e$c__a.f95 > f82; c_e$c__a = c_e$c__a.f98) {
         if (c_e$c__a.f98 == null) {
            c_e$c__a = c_e$c__a.f98 = new C_E.C__A();
            break;
         }
      }

      c_e$c__a.f95 = f82 + j - f79;
      c_e$c__a.f96 = f74;
      c_e$c__a.f97 = j;
   }

   static final class C__A {
      int f95;
      C_G f96;
      int f97;
      C_E.C__A f98;
   }
}
