package edu.ucla.phil.logic;

import java.io.File;
import java.util.Vector;

class PostWorker extends NetworkWorker {
   ServerUrl url;
   Vector body;
   String contentType;
   File saveFile;
   HttpDigestAuth digestAuth;

   PostWorker(ServerUrl serverurl, Vector vector, String s, NetworkTask networktask, File file1, HttpDigestAuth httpdigestauth) {
      super(networktask, null);
      this.url = serverurl;
      this.body = vector;
      this.contentType = s;
      this.saveFile = file1;
      this.digestAuth = httpdigestauth;
   }

   @Override
   Object perform() {
      return ServerConnection.doPost(this.url, this.body, this.contentType, this.task, this.saveFile, this.digestAuth);
   }
}
