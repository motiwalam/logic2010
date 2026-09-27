package edu.ucla.phil.logic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// A problem list with a search field above it and a count of completed problems below it.
// The count leaves out worked examples and user-created problems.
class ProblemSearchPanel extends JPanel implements DocumentListener, ActionListener {
   ProblemListView list;
   ProblemSet problems;
   boolean[] counted;
   JTextField searchField;
   JLabel countLabel;

   ProblemSearchPanel(ProblemListView problemlistview, ProblemSet problemset, ProblemSet problemset1) {
      super(new BorderLayout(0, 4));
      this.list = problemlistview;
      this.problems = problemset;
      this.counted = new boolean[problemset.size()];

      for (int i = 0; i < this.counted.length; i++) {
         ProblemEntry problementry = problemset.getEntryAt(i);
         if (problementry != null && problementry.name != null) {
            TaggedRecord taggedrecord = new TaggedRecord(problementry.name);
            this.counted[i] = !ProblemSet.isExample(taggedrecord) && ProblemSet.lookupName(problemset1, taggedrecord.getName()) != null;
         }
      }

      this.searchField = new JTextField();
      this.searchField.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.searchField.setToolTipText("Search problem names and what they prove, e.g. T2 or MC1");
      this.searchField.getDocument().addDocumentListener(this);
      this.searchField.addActionListener(this);
      this.searchField.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent keyevent) {
            int i = keyevent.getKeyCode();
            if (i == KeyEvent.VK_UP || i == KeyEvent.VK_DOWN) {
               ProblemSearchPanel.this.list.moveSelection(i == KeyEvent.VK_UP ? -1 : 1);
               keyevent.consume();
            } else if (i == KeyEvent.VK_PAGE_UP || i == KeyEvent.VK_PAGE_DOWN) {
               ProblemSearchPanel.this.list.moveSelection(i == KeyEvent.VK_PAGE_UP ? -10 : 10);
               keyevent.consume();
            }
         }
      });
      JPanel jpanel = new JPanel(new BorderLayout(4, 0));
      JLabel jlabel = new JLabel("Search:");
      jlabel.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      jpanel.add(jlabel, "West");
      jpanel.add(this.searchField, "Center");
      jpanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
      this.add(jpanel, "North");
      JScrollPane jscrollpane = new JScrollPane();
      jscrollpane.setViewportView(problemlistview);
      this.add(jscrollpane, "Center");
      this.countLabel = new JLabel();
      this.countLabel.setFont(LogicProgram.getFont(LogicProgram.fontSize));
      this.countLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
      this.add(this.countLabel, "South");
      this.updateCounts();
   }

   void updateCounts() {
      int[] aint = this.count(this.list.allRowToProblem);
      String s = "Completed: " + aint[0] + "    Not completed: " + (aint[1] - aint[0]);
      if (!this.searchField.getText().trim().equals("")) {
         int[] aint1 = this.count(this.list.rowToProblem);
         s = s + "    (matches: " + aint1[2] + ", " + aint1[0] + " of " + aint1[1] + " completed)";
      }

      this.countLabel.setText(s);
   }

   // {completed, counted, rows} over the problems in the given rows
   int[] count(int[] aint) {
      int[] aint1 = new int[3];
      if (aint != null) {
         for (int i = 0; i < aint.length; i++) {
            int j = aint[i];
            ProblemEntry problementry;
            if (j >= 0 && (problementry = this.problems.getEntryAt(j)) != null && (this.list.searchTexts == null || this.list.searchTexts[j] != null)) {
               aint1[2]++;
               if (j < this.counted.length && this.counted[j]) {
                  aint1[1]++;
                  if (problementry.state == ProblemEntry.STATE_CORRECT) {
                     aint1[0]++;
                  }
               }
            }
         }
      }

      return aint1;
   }

   void filterChanged() {
      this.list.setFilter(this.searchField.getText());
      this.updateCounts();
   }

   @Override
   public void insertUpdate(DocumentEvent documentevent) {
      this.filterChanged();
   }

   @Override
   public void removeUpdate(DocumentEvent documentevent) {
      this.filterChanged();
   }

   @Override
   public void changedUpdate(DocumentEvent documentevent) {
      this.filterChanged();
   }

   @Override
   public void actionPerformed(ActionEvent actionevent) {
      if (this.list.getSelectedProblem(this.list.rowToProblem) != -1) {
         this.list.accept();
      }
   }
}
