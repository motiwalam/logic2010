package edu.ucla.phil.logic;

import java.awt.Color;

interface C_n_A {
   Color bruinGold = new Color(16776960);
   Color bruinBlue = new Color(255);
   Color bruinBluf = new Color(8421631);
   Color bruinRed = new Color(16744576);
   Color bruinNavy = new Color(128);
   Color bruinWhite = new Color(16777215);
   Color bruinBlack = new Color(0);
   Color bruinGray = new Color(14737632);
   Color bruinAsh = new Color(15263976);
   Color bruinMaize = new Color(16777185);
   Color dialogRed = new Color(12386304);
   Color dialogGreen = new Color(43008);
   Color dialogBlue = new Color(255);
   Color dialogOrange = new Color(16747520);
   Color dialogBlack = new Color(0);
   Color dialogWhite = new Color(16777215);
   String[] maggie = new String[]{"@", "!", "%", "~", "&", "|", "->", "<->", "<>", "[m]", ".:", "\\"};
   String[] rob = new String[]{"\\u", "\\e", "\\d", "\\n", "\\a", "\\o", "\\c", "\\b", "\\i", "\\m", "\\t", "\\\\"};
   String[] kaplan1 = new String[]{"⋀", "⋁", "℩", "∼", "∧", "∨", "→", "↔", "≠", "∊", "∴", "\\"};
   String[] kaplan2 = new String[]{"∀", "∃", "℩", "∼", "∧", "∨", "→", "↔", "≠", "∊", "∴", "\\"};
   String[] kaplan3 = new String[]{"⋀", "⋁", "℩", "∼", "∧", "∨", "⟶", "⟷", "≠", "∊", "∴", "\\"};
   String[] kaplan4 = new String[]{"∀", "∃", "℩", "∼", "∧", "∨", "⟶", "⟷", "≠", "∊", "∴", "\\"};
   String[] kaplan5 = new String[]{"ȯɜ", "ɜȯ", "ũ", "ž", "Ǚ", "ǚ", "Ʈ", "ƫ", "ƹ", "ǎ", "Ŝ", "\\"};
   String[] kaplan6 = new String[]{"Ģ", "Ĥ", "ũ", "ž", "Ǚ", "ǚ", "Ʈ", "ƫ", "ƹ", "ǎ", "Ŝ", "\\"};
   String[] kaplan7 = new String[]{"Ģ", "Ĥ", "ũ", "ž", "Ħ", "Ů", "ĭľ", "ļĭľ", "ģ", "ť", "Ŝ", "\\"};
   String[] html1 = new String[]{
      "&#x22C0;", "&#x22C1;", "&#x2129;", "&#x223C;", "&#x2227;", "&#x2228;", "&#x2192;", "&#x2194;", "&#x2260;", "&#x220A;", "&#x2234;", "\\"
   };
   String[] html2 = new String[]{
      "&#x2200;", "&#x2203;", "&#x2129;", "&#x223C;", "&#x2227;", "&#x2228;", "&#x2192;", "&#x2194;", "&#x2260;", "&#x220A;", "&#x2234;", "\\"
   };
   String[] html3 = new String[]{
      "<font face=\"Symbol\">&#34;</font>",
      "<font face=\"Symbol\">&#36;</font>",
      "<font face=\"Symbol\">&#105;</font>",
      "<font face=\"Symbol\">&#126;</font>",
      "<font face=\"Symbol\">&#217;</font>",
      "<font face=\"Symbol\">&#218;</font>",
      "<font face=\"Symbol\">&#174;</font>",
      "<font face=\"Symbol\">&#171;</font>",
      "<font face=\"Symbol\">&#185;</font>",
      "<font face=\"Symbol\">&#206;</font>",
      "<font face=\"Symbol\">&#92;</font>",
      "\\"
   };
   String sentenceLetters1 = "PQRSTUVWXYZ";
   String sentenceLetters2 = "PQRSTUVWXYZ";
   String monadicLetters1 = "FGHIJKLMNO";
   String monadicLetters2 = "FGHIJKLMNOABCDE";
   String operationLetters1 = "ABCDE";
   String operationLetters2 = "abcdefgh";
   String variableLetters1 = "abcdefghijklmnopqrstuvwxyz";
   String variableLetters2 = "ijklmnopqrstuvwxyz";
   String topVariables = "xyzuvw";
   String autoVariables = "xyzuvwlmnopqrst";
   int ABBREV_INEQ = -1;
   int RETAIN_INEQ = 0;
   int EXPAND_INEQ = 1;
   int unit_scroll_inc = 22;
   int CREATE_USER = 0;
   int VERIFY_USER = 1;
   int UPDATE_USER = 2;
   String[] userPurposes = new String[]{"create", "verify", "update"};
}
