package edu.ucla.phil.logic;

import java.util.Hashtable;

class C_a_F {
   String f979;
   int f980;

   C_a_F(String s, int i) {
      this.f979 = s;
      this.f980 = i;
   }

   static C_a_F m1642(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, int i) {
      String s = userinfo instanceof NewUserInfo ? "not051" : "not052";
      return m1643(serversession, userinfo, busyindicator, s, i);
   }

   static C_a_F m1643(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, String s, int i) {
      C__F c__f = new C__F(AccountManager.m1864(serversession, userinfo, busyindicator));
      if (c__f.f932 == null) {
         return null;
      } else {
         Hashtable hashtable = Message.params("site", userinfo.getInstitution(), "sid", userinfo.getStudentId());
         if (!(userinfo instanceof NewUserInfo)) {
            Message.putParam(hashtable, "name", userinfo.getFullName());
         }

         String s1 = Message.substitute(Message.getText(s), hashtable);
         if (busyindicator != null) {
            busyindicator.m2162(true);
         }

         Integer integer = ServerConnection.m908(serversession, userinfo, c__f, s1, i);
         if (busyindicator != null) {
            busyindicator.m2162(false);
         }

         if (integer == null) {
            return null;
         } else {
            userinfo.f664 = integer;
            if (userinfo instanceof NewUserInfo) {
               ((NewUserInfo)userinfo).f381 = c__f.f932;
            }

            return new C_a_F(c__f.f932, integer);
         }
      }
   }
}
