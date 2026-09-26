package edu.ucla.phil.logic;

interface DerivationConstants extends ModuleConstants {
   int EXPR_PARSE = 1;
   int JUST_PARSE = 2;
   int JUST_CHECK = 3;
   int COMP_CHECK = 4;
   int ASS_DD = 0;
   int ASS_ID = 1;
   int ASS_CD = 2;
   int ASS_BD = 3;
   String[] ASS_STR = new String[]{"D", "I", "C", "B"};
   String[] BD_ASS = new String[]{"ASS BDL", "ASS BDR", "ASS BD"};
   int SCHEME_FAILED = 1;
   int SCHEME_INCOMP = 2;
   int VAR_VIOLATION = 3;
   int IMPROPER_SUB = 4;
   int SUB_ERROR = 5;
}
