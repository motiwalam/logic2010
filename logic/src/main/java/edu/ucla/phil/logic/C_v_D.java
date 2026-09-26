package edu.ucla.phil.logic;

public interface C_v_D extends C_XC {
   int NONE = 0;
   int NEGATION = 1;
   int IMPLICATION = 2;
   int CONJUNCTION = 3;
   int DISJUNCTION = 4;
   int EQUIVALENCE = 5;
   int UNIVERSAL = 6;
   int EXISTENTIAL = 7;
   int DESCRIPTIVE = 8;
   int EQUATION = 9;
   int MEMBER = 10;
   int SYMBOLIC = 11;
   boolean noSymbolics = true;
   int[] connOutTypes = new int[]{2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2};
   int[][] connArgTypes = new int[][]{new int[0], {0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0}, {0}, {0}, {1, 1}, {1, 1}, new int[0]};
   String[] connSymbol = new String[]{"?", "~", "->", "&", "|", "<->", "@", "!", "%", "=", "[m]", "*"};
   String[] connMenu = new String[]{
      "Truncate",
      "Negation",
      "Conditional",
      "Conjunction",
      "Disjunction",
      "Biconditional",
      "Univ Gen",
      "Exist Gen",
      "Descriptive",
      "Equality",
      "Member",
      "Atomic"
   };
   String[] connHover = new String[]{
      "Ctrl+Shift+T",
      "Ctrl+Shift+N",
      "Ctrl+Shift+C",
      "Ctrl+Shift+A",
      "Ctrl+Shift+O",
      "Ctrl+Shift+B",
      "Ctrl+Shift+U",
      "Ctrl+Shift+E",
      "Ctrl+Shift+D",
      "Ctrl+Shift+=",
      "Ctrl+Shift+M",
      "Ctrl+Shift+@"
   };
   String[] editMenu = new String[]{"Cut", "Copy", "Paste", "Select All", "Clear"};
   String[] editHover = new String[]{"Ctrl+X", "Ctrl+C", "Ctrl+V", "Ctrl+A", null};
   String[] connWords = new String[]{
      "an unparsed statement",
      "a negation",
      "a conditional",
      "a conjunction",
      "a disjunction",
      "a biconditional",
      "a universal generalization",
      "an existential generalization",
      "a descriptive statement",
      "an equality",
      "a member relation",
      "an atomic expression"
   };
   Integer[] connChaps = new Integer[]{
      new Integer(1),
      new Integer(1),
      new Integer(1),
      new Integer(2),
      new Integer(2),
      new Integer(2),
      new Integer(3),
      new Integer(3),
      new Integer(6),
      new Integer(5),
      new Integer(5),
      new Integer(1)
   };
   String[] expTypes = new String[]{"Formula", "Term"};
}
