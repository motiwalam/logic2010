package edu.ucla.phil.logic;

import java.util.Vector;

class C_HA {
   Expression f377;
   ArgumentParser f378;
   Vector f379;
   boolean[] f380;

   C_HA(Expression expression) {
      this.f378 = null;
      this.f379 = new Vector();
      this.m672(this.f377 = expression);
      this.m675();
   }

   C_HA(ArgumentParser argumentparser) {
      this.f377 = null;
      this.f378 = argumentparser;
      this.f379 = new Vector();
      int i = argumentparser.f827.length;

      for (int j = 0; j < i; j++) {
         this.m672(argumentparser.f827[j]);
      }

      this.m672(argumentparser.f828);
      this.m675();
   }

   void m672(Expression expression) {
      if (expression instanceof ConnectiveFormula) {
         int i = expression.getChildCount();

         for (int j = 0; j < i; j++) {
            this.m672(expression.getChild(j));
         }
      } else if (expression != null && this.m678(expression) == -1) {
         this.f379.addElement(expression);
      }
   }

   ErrorRef m673(String s) {
      Vector vector = new Vector();

      while (s != null) {
         int i = s.indexOf(46);
         String s1;
         if (i == -1) {
            s1 = s;
            s = null;
         } else {
            s1 = s.substring(0, i);
            s = s.substring(i + 1);
         }

         if ((s1 = s1.trim()).length() != 0) {
            Expression expression;
            try {
               expression = LogicProgram.m1006(s1);
            } catch (FormulaParseException formulaparseexception) {
               expression = null;
            }

            if (expression == null) {
               vector.addElement(new ErrorRef("truerr016", Message.params("unparsed", s1)));
            } else {
               vector.addElement(expression);
            }
         }
      }

      return this.m674(vector);
   }

   ErrorRef m674(Vector vector) {
      int j = vector.size();
      int[] aint = new int[j];

      for (int i = 0; i < j; i++) {
         Object object = vector.elementAt(i);
         if (object instanceof ErrorRef) {
            return (ErrorRef)object;
         }

         aint[i] = this.m678((Expression)object);
         if (aint[i] == -1) {
            return new ErrorRef("truerr017", Message.params("sentence", object + ""));
         }

         for (int k = 0; k < i; k++) {
            if (aint[i] == aint[k]) {
               return new ErrorRef("truerr018", Message.params("sentence", object + ""));
            }
         }
      }

      if (this.f379.size() != j) {
         return new ErrorRef("truerr015");
      } else {
         Vector vector1 = new Vector();

         for (int l = 0; l < j; l++) {
            vector1.addElement(this.f379.elementAt(aint[l]));
         }

         this.f379 = vector1;
         this.m675();
         return new ErrorRef(null);
      }
   }

   void m675() {
      int i = this.f379.size();
      this.f380 = new boolean[i == 0 ? 0 : 1 << i];
      if (this.f377 != null) {
         this.m676(this.f377, this.f380);
      } else if (this.f378 != null) {
         int j = this.f378.f827.length;
         this.m676(this.f378.f828, this.f380);
         boolean[] aboolean = new boolean[this.f380.length];

         for (int k = 0; k < j; k++) {
            this.m676(this.f378.f827[k], aboolean);

            for (int l = 0; l < this.f380.length; l++) {
               if (!aboolean[l]) {
                  this.f380[l] = true;
               }
            }
         }
      }
   }

   void m676(Expression expression, boolean[] aboolean) {
      int i = aboolean.length;

      for (int j = 0; j < i; j++) {
         aboolean[j] = this.m677(expression, this.m679(j));
      }
   }

   boolean m677(Expression expression, boolean[] aboolean) {
      if (expression instanceof ConnectiveFormula) {
         String s = expression.getSymbol();
         if (s.equals("~")) {
            return !this.m677(expression.getChild(0), aboolean);
         } else if (s.equals("&")) {
            return this.m677(expression.getChild(0), aboolean) & this.m677(expression.getChild(1), aboolean);
         } else if (s.equals("|")) {
            return this.m677(expression.getChild(0), aboolean) | this.m677(expression.getChild(1), aboolean);
         } else if (s.equals("->")) {
            return !this.m677(expression.getChild(0), aboolean) | this.m677(expression.getChild(1), aboolean);
         } else {
            return s.equals("<->") ? this.m677(expression.getChild(0), aboolean) == this.m677(expression.getChild(1), aboolean) : false;
         }
      } else {
         int i = this.m678(expression);
         return i == -1 ? false : aboolean[i];
      }
   }

   int m678(Expression expression) {
      if (expression == null) {
         return -1;
      } else {
         int i = this.f379.size();

         for (int j = 0; j < i; j++) {
            if (expression.m1236((Expression)this.f379.elementAt(j), new C_MB())) {
               return j;
            }
         }

         return -1;
      }
   }

   boolean[] m679(long i) {
      int j = this.f379.size();
      boolean[] aboolean = new boolean[j];

      for (int k = 0; k < j; k++) {
         aboolean[k] = (i & 1L) != 0L;
         i >>= 1;
      }

      return aboolean;
   }

   boolean m680() {
      int i = this.f380.length;

      for (int j = 0; j < i; j++) {
         if (!this.f380[j]) {
            return false;
         }
      }

      return true;
   }

   static boolean m681(Expression expression, Expression expression1) {
      if (expression instanceof Formula && expression1 instanceof Formula) {
         ConnectiveFormula connectiveformula = new ConnectiveFormula("<->");
         connectiveformula.addChild(expression);
         connectiveformula.addChild(expression1);
         return new C_HA(connectiveformula).m680();
      } else {
         return false;
      }
   }
}
