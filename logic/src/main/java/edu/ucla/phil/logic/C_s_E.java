package edu.ucla.phil.logic;

import java.io.File;

class C_s_E extends C_o_ {
   ServerUrl f1368;
   File f1369;
   HttpDownloader f1370;

   C_s_E(ServerUrl serverurl, File file1, HttpDownloader httpdownloader, NetworkTask networktask) {
      super(networktask, new Boolean(false));
      this.f1368 = serverurl;
      this.f1369 = file1;
      this.f1370 = httpdownloader;
   }

   @Override
   Object m687() {
      return new Boolean(this.f1370.m1857(this.f1368, this.f1369, this.f1317));
   }
}
