package edu.ucla.phil.logic;

import java.util.Vector;

class C_DB extends SchematicRule {
   String f274;

   C_DB(String s) {
      this(s, s);
   }

   C_DB(String s, String s1) {
      super(s1);
      this.m453(s);
   }

   Vector m452() {
      if (this.f274 == null) {
         return null;
      } else {
         Vector vector = new Vector();
         vector.addElement(this.f274);
         return vector;
      }
   }

   void m453(String s) {
      TaggedRecord taggedrecord = new TaggedRecord(LPDerivation.problems.m1780(s));
      this.f274 = taggedrecord.getName();
      this.m948(LPDerivation.getProblemStatement(taggedrecord));
   }
}
