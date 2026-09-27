package edu.ucla.phil.logic;

interface ExpressionKinds {
   int KIND_ATOMIC = 0;
   int KIND_QUANTIFIED = 1;
   int KIND_CONNECTIVE = 2;
   int KIND_SIMPLE_TERM = 3;
   int KIND_OPERATION_TERM = 4;
   int KIND_DESCRIPTION_TERM = 5;
   int KIND_IDENTITY = 6;
   int KIND_MEMBERSHIP = 7;
}
