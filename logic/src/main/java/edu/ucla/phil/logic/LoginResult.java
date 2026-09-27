package edu.ucla.phil.logic;

import java.util.Hashtable;

class LoginResult {
   String passwordHash;
   int userUid;

   LoginResult(String s, int i) {
      this.passwordHash = s;
      this.userUid = i;
   }

   static LoginResult login(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, int i) {
      String s = userinfo instanceof NewUserInfo ? "not051" : "not052";
      return login(serversession, userinfo, busyindicator, s, i);
   }

   static LoginResult login(ServerSession serversession, UserInfo userinfo, BusyIndicator busyindicator, String s, int i) {
      PasswordEntry passwordentry = new PasswordEntry(AccountManager.promptForPassword(serversession, userinfo, busyindicator));
      if (passwordentry.password == null) {
         return null;
      } else {
         Hashtable hashtable = Message.params("site", userinfo.getInstitution(), "sid", userinfo.getStudentId());
         if (!(userinfo instanceof NewUserInfo)) {
            Message.putParam(hashtable, "name", userinfo.getFullName());
         }

         String s1 = Message.substitute(Message.getText(s), hashtable);
         if (busyindicator != null) {
            busyindicator.setBusy(true);
         }

         Integer integer = ServerConnection.loginUser(serversession, userinfo, passwordentry, s1, i);
         if (busyindicator != null) {
            busyindicator.setBusy(false);
         }

         if (integer == null) {
            return null;
         } else {
            userinfo.userUid = integer;
            if (userinfo instanceof NewUserInfo) {
               ((NewUserInfo)userinfo).passwordHash = passwordentry.password;
            }

            return new LoginResult(passwordentry.password, integer);
         }
      }
   }
}
