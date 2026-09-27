package edu.ucla.phil.logic;

class Credentials {
   String user;
   String password;

   Credentials(String s, String s1) {
      this.user = s;
      this.password = s1;
   }

   Credentials(String s) {
      int i = s == null ? -1 : s.indexOf(58);
      if (i == -1) {
         this.user = s;
         this.password = null;
      } else {
         this.user = s.substring(0, i);
         this.password = s.substring(i + 1);
      }
   }

   @Override
   public String toString() {
      return this.password == null ? this.user : this.user + ":" + this.password;
   }
}
