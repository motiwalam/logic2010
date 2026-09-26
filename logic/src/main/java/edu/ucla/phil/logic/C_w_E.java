package edu.ucla.phil.logic;

import java.util.Vector;

interface C_w_E {
   boolean hasProperty(C_VB c_vb, String s);

   boolean hasProperty(Integer integer, String s);

   Vector getProofs(C_LF c_lf);

   Vector getProofs(Integer integer);

   String excludedProof();

   boolean checkProof(String s);
}
