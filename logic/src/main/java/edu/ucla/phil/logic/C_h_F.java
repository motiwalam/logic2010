package edu.ucla.phil.logic;

import java.awt.Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

abstract class C_h_F extends FormulaEntryField {
   int f1164;
   Vector f1165;
   Term[] f1166;
   int[] f1167;
   Vector f1168;
   Hashtable f1169;
   static String[] f1170 = LogicProgram.symbols;

   C_h_F(Expression expression, int i, Frame frame, int j) {
      this(expression, i, frame, j, new Hashtable());
   }

   C_h_F(Expression expression, int i, Frame frame, int j, Hashtable hashtable) {
      super(LogicProgram.m995(expression.m1205(true, 1), maggie, f1170), i, frame);
      this.f1164 = j;
      this.f1165 = new Vector();
      this.f1166 = new Term[j];
      this.f1167 = new int[j];
      this.f1168 = new Vector();
      this.f1169 = hashtable;
      this.addKeyListener(this);
      this.setEditable(false);
      this.getCaret().setVisible(true);
      this.setFocusable(true);
      this.addFocusListener(new FocusAdapter() {
         public void m1847(FocusEvent focusevent) {
            C_h_F.this.getCaret().setVisible(true);
         }
      });
   }

   @Override
   void m1842(String s) {
      if (!this.isEditable()) {
         int i = 0;

         while (i < this.f1164 && !SchematicLetter.m1853(i).equals(s)) {
            i++;
         }

         if (i == this.f1164) {
            return;
         }

         String s1 = this.getText();
         Hashtable hashtable = Message.m665(this.f1169, null);
         Message.putParam(hashtable, "full text", LogicProgram.m1005(s1));
         String s2 = this.getSelectedText();
         if (s2.length() == 0) {
            this.m1843(hashtable);
            this.requestFocus();
            return;
         }

         Message.putParam(hashtable, "selection", LogicProgram.m1005(s2));
         int[] aint = new int[]{this.getSelectionStart(), this.getSelectionEnd()};
         String s3 = LogicProgram.m996(s1, f1170, maggie, aint);
         String s4 = s3.substring(0, aint[0]) + s + s3.substring(aint[1]);
         Message.putParam(hashtable, "subbed text", "\\l" + s4 + "\\l");

         Expression expression;
         try {
            expression = LogicProgram.m1008(LogicProgram.m995(s2, f1170, maggie), true, false);
         } catch (FormulaParseException formulaparseexception2) {
            this.m927(hashtable);
            this.requestFocus();
            return;
         }

         if (!(expression instanceof Term)) {
            this.m928(hashtable);
            this.requestFocus();
            return;
         }

         try {
            LogicProgram.m1008(s4, true, true);
         } catch (FormulaParseException formulaparseexception1) {
            this.m929(hashtable);
            this.requestFocus();
            return;
         }

         Expression expression1;
         try {
            expression1 = LogicProgram.m1008(s3, true, true);
         } catch (FormulaParseException formulaparseexception) {
            expression1 = null;
         }

         C_DD c_dd = new C_DD(expression1);
         c_dd.f284 = s3;
         Expression expression2 = c_dd.m480(aint[0], aint[1]).m456();
         Vector vector = expression2.m1259();
         if (vector != null) {
            this.m930(vector, hashtable);
            this.requestFocus();
            return;
         }

         if (this.f1167[i] != 0) {
            if (!expression.m1235(this.f1166[i])) {
               this.m931(Message.putParam(hashtable, "old term", "\\l" + this.f1166[i] + "\\l"));
               this.requestFocus();
               return;
            }
         } else {
            Enumeration enumeration = this.f1168.elements();

            while (enumeration.hasMoreElements()) {
               C_s_ c_s_ = (C_s_)enumeration.nextElement();
               ErrorRef errorref = c_s_.m1965(i, expression);
               if (errorref != null) {
                  C_KB.m761(errorref.f427, Message.m665(hashtable, errorref.f428));
                  this.requestFocus();
                  return;
               }
            }

            enumeration = this.f1168.elements();

            while (enumeration.hasMoreElements()) {
               C_s_ c_s_1 = (C_s_)enumeration.nextElement();
               c_s_1.m1964(i, expression.toString());
            }

            this.f1166[i] = (Term)expression;
         }

         this.f1167[i]++;
         this.f1165.addElement(new C_OC(i, this.getSelectionStart(), s2));
      }

      this.m2077(s, true);
   }

   void m1843(Hashtable hashtable) {
      C_KB.m761("dernot013", hashtable);
   }

   abstract void m927(Hashtable hashtable);

   abstract void m928(Hashtable hashtable);

   abstract void m929(Hashtable hashtable);

   abstract void m930(Vector vector, Hashtable hashtable);

   abstract void m931(Hashtable hashtable);

   @Override
   public boolean m1844() {
      if (this.isEditable()) {
         super.m1844();
      }

      int i = this.f1165.size();
      if (i == 0) {
         return false;
      } else {
         C_OC c_oc = (C_OC)this.f1165.elementAt(i - 1);
         this.f1165.setSize(i - 1);
         int j = c_oc.f654;
         int k = c_oc.f655;
         String s = c_oc.f656;
         if (--this.f1167[j] == 0) {
            Enumeration enumeration = this.f1168.elements();

            while (enumeration.hasMoreElements()) {
               C_s_ c_s_ = (C_s_)enumeration.nextElement();
               c_s_.m1964(j, null);
            }

            this.f1166[j] = null;
         }

         this.m1795(null, k, k + SchematicLetter.m1853(j).length());
         this.m1796(s, k);
         this.select(k, k + s.length());
         return true;
      }
   }

   @Override
   public void keyTyped(KeyEvent keyevent) {
      if ((keyevent.getModifiers() & 1) == 0 && keyevent.getKeyChar() == 26) {
         keyevent.consume();
         if (!this.m1844()) {
            C_KB.m759("dernot012");
            this.requestFocus();
         }
      }

      super.keyTyped(keyevent);
   }

   @Override
   void m714() {
   }

   void m1845(C_s_ c_s_) {
      this.f1168.addElement(c_s_);
   }

   void m1846(C_s_ c_s_) {
      this.f1168.removeElement(c_s_);
   }
}
