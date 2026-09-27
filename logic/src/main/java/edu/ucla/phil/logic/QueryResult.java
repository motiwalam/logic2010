package edu.ucla.phil.logic;

class QueryResult {
   Object[][] table;
   Integer status;

   QueryResult(Object[][] aobject, Integer integer) {
      this.table = aobject;
      this.status = integer;
   }
}
