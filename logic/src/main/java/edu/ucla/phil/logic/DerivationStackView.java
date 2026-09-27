package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;
import javax.swing.Timer;

/**
 * The stack view of the derivation module (the Stack button): the formulas on the stack of
 * the justification being edited, after the steps before the cursor. The justification is
 * previewed with a DerivationLineChecker in preview mode; nothing in the derivation changes.
 */
class DerivationStackView extends JPanel implements ActionListener {
   static final String[] IGNORED_AT_END = new String[]{"dererr001", "dererr002", "dererr018"};
   LPDerivation module;
   Timer timer;
   JPanel content;
   String lastKey;
   String lastShown;
   int ticks;

   DerivationStackView(LPDerivation lpderivation) {
      this.module = lpderivation;
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
      JLabel jlabel = new JLabel("Stack at the cursor");
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize, 1));
      jlabel.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
      this.add(jlabel, "North");
      this.content = new JPanel(new GridBagLayout());
      DerivationStackView.WidthTrackingPanel widthtrackingpanel = new DerivationStackView.WidthTrackingPanel();
      widthtrackingpanel.add(this.content, "North");
      JScrollPane jscrollpane = new JScrollPane(widthtrackingpanel, 20, 31);
      jscrollpane.setBorder(null);
      this.add(jscrollpane, "Center");
      this.setPreferredSize(new Dimension(LogicProgram.fontSize * 20, 0));
      this.timer = new Timer(200, this);
   }

   void setOn(boolean flag) {
      this.setVisible(flag);
      this.lastKey = null;
      if (flag) {
         this.timer.start();
         this.refresh();
      } else {
         this.timer.stop();
      }
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (!this.isDisplayable()) {
         this.timer.stop();
      } else if (this.isShowing() && this.module.phase == 0 && !this.module.serialMode) {
         // not while the program is checking (a dialog it opened keeps the timer running)
         this.refresh();
      }
   }

   /** Recomputes when the edited text, the cursor or the line changes, and every second or so. */
   void refresh() {
      DerivationLineEditor derivationlineeditor = this.module.lastFocus;
      if (derivationlineeditor == null || derivationlineeditor.line == null || derivationlineeditor.line.annotationEditor == null) {
         this.display(null, "Put the cursor in a justification to see the stack there.");
      } else {
         DerivationLine derivationline = derivationlineeditor.line;
         String s = derivationline.annotationEditor.getText();
         int i = derivationlineeditor == derivationline.annotationEditor ? derivationlineeditor.getCaretPosition() : s.length();
         String s1 = System.identityHashCode(derivationline) + "\u0000" + i + "\u0000" + s;
         if (!s1.equals(this.lastKey) || ++this.ticks >= 5) {
            this.lastKey = s1;
            this.ticks = 0;
            this.display(derivationline, textBeforeCursor(s, i));
         }
      }
   }

   /**
    * The justification text whose steps are complete at the cursor: the text before it,
    * without a word the cursor is inside, a comment, or a formula in brackets not yet closed
    * (the rule name before it is left out too).
    */
   static String textBeforeCursor(String s, int i) {
      i = Math.max(0, Math.min(i, s.length()));
      if (i > 0 && i < s.length() && !Character.isWhitespace(s.charAt(i)) && !Character.isWhitespace(s.charAt(i - 1))) {
         while (i > 0 && !Character.isWhitespace(s.charAt(i - 1))) {
            i--;
         }
      }

      String s1 = s.substring(0, i);
      int j = s1.indexOf('#');
      if (j != -1) {
         s1 = s1.substring(0, j);
      }

      int k = 0;
      int l = -1;

      for (int m = 0; m < s1.length(); m++) {
         char c0 = s1.charAt(m);
         if (c0 == '[' && k++ == 0) {
            l = m;
         } else if (c0 == ']' && k > 0) {
            k--;
         }
      }

      if (k > 0) {
         int n = l;
         while (n > 0 && Character.isWhitespace(s1.charAt(n - 1))) {
            n--;
         }

         while (n > 0 && DerivationLineChecker.isNameChar(s1.charAt(n - 1))) {
            n--;
         }

         s1 = s1.substring(0, n);
      }

      return s1;
   }

   void display(DerivationLine derivationline, String s) {
      StringBuffer stringbuffer = new StringBuffer();
      Vector vector = new Vector();
      if (derivationline == null) {
         vector.addElement(note(s, Color.gray));
         stringbuffer.append(s);
      } else {
         DerivationStackView.Snapshot snapshot = compute(derivationline, s);
         stringbuffer.append(derivationline.getLineNumber()).append('\u0000').append(s).append('\u0000').append(snapshot.signature());
         String s1 = s.trim().equals("") ? "(no steps yet)" : s.trim();
         vector.addElement(note("Line " + derivationline.getLineNumber() + ", after: " + s1, Color.darkGray));
         if (snapshot.formulas.isEmpty()) {
            vector.addElement(note(snapshot.closed != null ? snapshot.closed + " closes the box; the stack is empty." : "(empty)", Color.gray));
         }

         for (int i = snapshot.formulas.size() - 1; i >= 0; i--) {
            vector.addElement(row(i == snapshot.formulas.size() - 1, snapshot.formulas.elementAt(i).toString(), (String)snapshot.origins.elementAt(i)));
         }

         if (snapshot.error != null) {
            vector.addElement(note(snapshot.error, new Color(170, 0, 0)));
         }
      }

      String s2 = stringbuffer.toString();
      if (!s2.equals(this.lastShown)) {
         this.lastShown = s2;
         this.content.removeAll();
         GridBagConstraints gridbagconstraints = new GridBagConstraints();
         gridbagconstraints.gridx = 0;
         gridbagconstraints.weightx = 1.0;
         gridbagconstraints.fill = 2;
         gridbagconstraints.anchor = 18;
         gridbagconstraints.insets = new Insets(2, 6, 2, 6);

         for (int j = 0; j < vector.size(); j++) {
            this.content.add((java.awt.Component)vector.elementAt(j), gridbagconstraints);
         }

         this.content.revalidate();
         this.content.repaint();
      }
   }

   /** Text that wraps to the width of the view. */
   static JTextArea note(String s, Color color) {
      JTextArea jtextarea = new JTextArea(s);
      jtextarea.setLineWrap(true);
      jtextarea.setWrapStyleWord(true);
      jtextarea.setEditable(false);
      jtextarea.setFocusable(false);
      jtextarea.setOpaque(false);
      jtextarea.setForeground(color);
      jtextarea.setFont(LogicProgram.getFont(LogicProgram.fontSize * 5 / 6, 0));
      return jtextarea;
   }

   static JPanel row(boolean flag, String s, String s1) {
      JPanel jpanel = new JPanel(new BorderLayout(6, 0));
      jpanel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createLineBorder(flag ? Color.darkGray : Color.lightGray), BorderFactory.createEmptyBorder(3, 5, 3, 5)
      ));
      LogicLabel logiclabel = LogicProgram.createFormulaLabel(s);
      logiclabel.setFont(LogicProgram.getFont(LogicProgram.fontSize, flag ? 1 : 0));
      logiclabel.setHorizontalAlignment(2);
      jpanel.add(logiclabel, "Center");
      JLabel jlabel = new JLabel((flag ? "top, " : "") + s1);
      jlabel.setForeground(Color.gray);
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize * 5 / 6, 0));
      jpanel.add(jlabel, "East");
      return jpanel;
   }

   static boolean isBoxRule(String s) {
      return s.equals("CD") || s.equals("ID") || s.equals("DD") || s.equals("UD") || s.equals("BD");
   }

   /**
    * Runs the steps of s on derivationline's justification, as the Check button would but
    * with no dialogs (serial mode) and no messages, and restores what checking changes.
    */
   static DerivationStackView.Snapshot compute(DerivationLine derivationline, String s) {
      LPDerivation lpderivation = derivationline.box.module;
      DerivationBox derivationbox = derivationline.box;
      boolean flag = lpderivation.serialMode;
      boolean flag1 = lpderivation.complete;
      boolean flag2 = lpderivation.proofMissing;
      boolean flag3 = lpderivation.aborted;
      int i = derivationbox.assumptionType;
      int j = derivationbox.assumedSide;
      boolean flag4 = derivationbox.strategyConsistent;
      boolean flag5 = derivationline.readyToCancel;
      Vector vector = derivationline.justifications == null ? null : (Vector)derivationline.justifications.clone();
      DerivationStackView.Snapshot snapshot = new DerivationStackView.Snapshot();
      IdentityHashMap identityhashmap = new IdentityHashMap();
      lpderivation.serialMode = true;

      try {
         DerivationLineChecker derivationlinechecker = new DerivationLineChecker(derivationline, s);

         while (true) {
            boolean flag6 = derivationlinechecker.readNextStep();
            String s1 = derivationlinechecker.getRuleName();
            if (!flag6 || s1 == null) {
               ErrorRef errorref = derivationlinechecker.previewError;
               if (errorref != null && LogicProgram.indexOf(IGNORED_AT_END, errorref.id.toLowerCase()) == -1) {
                  snapshot.error = describe(errorref, derivationlinechecker);
               }
               break;
            }

            if (s1.startsWith("SHOW ")) {
               snapshot.error = s1 + " starts a new Show line.";
               break;
            }

            boolean flag7 = isBoxRule(s1);
            if (!derivationlinechecker.checkStep(false, flag7)) {
               snapshot.error = s1 + ": " + describe(derivationlinechecker.previewError, derivationlinechecker);
               break;
            }

            if (flag7) {
               snapshot.closed = s1;
               break;
            }

            if (derivationlinechecker.result != null && !DerivationLineChecker.isStackOperation(s1)) {
               identityhashmap.put(derivationlinechecker.result, "by " + s1);
            }
         }

         for (int k = 0; k < derivationlinechecker.getStackSize(); k++) {
            Expression expression = derivationlinechecker.getStackFormula(k);
            DerivationNode derivationnode = derivationlinechecker.getCitedNode(k);
            snapshot.formulas.addElement(expression);
            String s2 = derivationnode != null ? "line " + derivationnode.getLineNumber() : (String)identityhashmap.get(expression);
            snapshot.origins.addElement(s2 == null ? "" : s2);
         }
      } catch (RuntimeException runtimeexception) {
         snapshot.error = "The stack could not be computed here (" + runtimeexception + ").";
      } finally {
         lpderivation.serialMode = flag;
         lpderivation.complete = flag1;
         lpderivation.proofMissing = flag2;
         lpderivation.aborted = flag3;
         derivationbox.assumptionType = i;
         derivationbox.assumedSide = j;
         derivationbox.strategyConsistent = flag4;
         derivationline.readyToCancel = flag5;
         derivationline.justifications = vector;
      }

      return snapshot;
   }

   static String describe(ErrorRef errorref, DerivationLineChecker derivationlinechecker) {
      if (errorref == null) {
         return "this step does not apply.";
      } else if (errorref.id.equalsIgnoreCase("dererr064")) {
         return "this step needs a choice the program would ask for in a dialog; a formula in brackets after the rule name makes it.";
      } else {
         Message message = DerivationMessage.get(errorref.id);
         String s = message.title != null ? message.title : message.text;
         return LogicProgram.translateSymbols(DerivationMessage.format(s, errorref.params, derivationlinechecker).replace("\\l", ""));
      }
   }

   /** Lays out its contents at the width of the scroll pane's view, so that text wraps. */
   static class WidthTrackingPanel extends JPanel implements Scrollable {
      WidthTrackingPanel() {
         super(new BorderLayout());
      }

      @Override
      public Dimension getPreferredScrollableViewportSize() {
         return this.getPreferredSize();
      }

      @Override
      public int getScrollableUnitIncrement(Rectangle rectangle, int i, int j) {
         return 16;
      }

      @Override
      public int getScrollableBlockIncrement(Rectangle rectangle, int i, int j) {
         return rectangle.height;
      }

      @Override
      public boolean getScrollableTracksViewportWidth() {
         return true;
      }

      @Override
      public boolean getScrollableTracksViewportHeight() {
         return false;
      }
   }

   static class Snapshot {
      Vector formulas = new Vector();
      Vector origins = new Vector();
      String error;
      String closed;

      String signature() {
         return this.formulas + "\u0000" + this.origins + "\u0000" + this.error + "\u0000" + this.closed;
      }
   }
}
