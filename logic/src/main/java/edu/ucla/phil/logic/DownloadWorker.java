package edu.ucla.phil.logic;

import java.io.File;

class DownloadWorker extends NetworkWorker {
   ServerUrl url;
   File destination;
   HttpDownloader downloader;

   DownloadWorker(ServerUrl serverurl, File file1, HttpDownloader httpdownloader, NetworkTask networktask) {
      super(networktask, new Boolean(false));
      this.url = serverurl;
      this.destination = file1;
      this.downloader = httpdownloader;
   }

   @Override
   Object perform() {
      return new Boolean(this.downloader.downloadDirect(this.url, this.destination, this.task));
   }
}
