package edu.ucla.phil.logic;

class RuleApplicationDisplay implements LogicConstants {
   RuleApplication application;
   SchemeInstantiation displayInstantiation;
   FormulaParseNode[] premiseDisplays;
   FormulaParseNode conclusionDisplay;

   RuleApplicationDisplay(RuleApplication ruleapplication, DerivationLineChecker derivationlinechecker) {
      this.application = (RuleApplication)ruleapplication.clone();
      this.displayInstantiation = this.application.instantiation.assignFreshLetters(null);
      int i = this.application.getPremiseCount();
      this.premiseDisplays = new FormulaParseNode[i];

      for (int j = 0; j < i; j++) {
         this.premiseDisplays[j] = this.createDisplay(this.application.getPremise(j, derivationlinechecker));
      }

      this.conclusionDisplay = this.createDisplay(this.application.getConclusion(derivationlinechecker));
   }

   FormulaParseNode createDisplay(Expression expression) {
      FormulaParseNode formulaparsenode = new FormulaParseNode(expression, true, -1);
      formulaparsenode.addLetterRanges(this.displayInstantiation.pendingLetters);
      formulaparsenode.addTermRanges(this.application.boundVariables);
      return formulaparsenode;
   }

   HighlightedText toHighlightedText() {
      return this.toHighlightedText(true);
   }

   HighlightedText toHighlightedText(boolean flag) {
      HighlightedText highlightedtext = new HighlightedText();
      int i = this.application.getPremiseCount();

      for (int j = 0; j < i; j++) {
         if (j != 0) {
            highlightedtext.append(".");
         }

         highlightedtext.append(this.premiseDisplays[j].toStyledText());
      }

      HighlightedText highlightedtext1 = this.conclusionDisplay.toStyledText();
      if (flag && !highlightedtext.sharesHighlightLayer(highlightedtext1)) {
         return highlightedtext1;
      } else {
         highlightedtext.append(".:");
         highlightedtext.append(highlightedtext1);
         return highlightedtext;
      }
   }
}
