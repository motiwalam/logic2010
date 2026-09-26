package edu.ucla.phil.logic;

import java.io.File;

class NetZipLoadThread extends ResultThread {
   AuthURL source;
   File dest;
   NetZipLoader loader;

   NetZipLoadThread(AuthURL source, File dest, NetZipLoader loader, Valve valve) {
      super(valve, new Boolean(false));
      this.source = source;
      this.dest = dest;
      this.loader = loader;
   }

   @Override
   Object spin() {
      return new Boolean(this.loader._load(this.source, this.dest, this.valve));
   }
}
