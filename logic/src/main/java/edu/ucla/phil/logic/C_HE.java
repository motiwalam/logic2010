package edu.ucla.phil.logic;

import java.io.File;
import java.util.Vector;

class C_HE extends C_o_ {
   ServerUrl f385;
   Vector f386;
   String f387;
   File f388;
   HttpDigestAuth f389;

   C_HE(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      super(networktask, null);
      this.f385 = serverurl;
      this.f386 = vector;
      this.f387 = s;
      this.f388 = file1;
      this.f389 = httpdigestauth;
   }

   @Override
   Object m687() {
      return ServerConnection.m811(this.f385, this.f386, this.f387, this.f1317, this.f388, this.f389);
   }
}
