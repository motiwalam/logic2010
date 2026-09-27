package edu.ucla.phil.logic;

import java.util.Hashtable;

interface ResponseHandler {
   void setError(String s, Hashtable hashtable);

   ErrorRef getError();
}
