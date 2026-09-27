package edu.ucla.phil.logic;

import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

class PrintPageHeader extends SizedPanel {
   LogicLabel pageLabel;

   static PrintPageHeader createForCurrentUser() {
      return create(LogicProgram.user, ServerConnection.getConfiguredCourse());
   }

   static PrintPageHeader create(UserInfo userinfo, CourseInfo courseinfo) {
      PrintPageHeader printpageheader = new PrintPageHeader();
      JPanel jpanel = new JPanel();
      jpanel.setBackground(LogicProgram.printColors[1]);
      jpanel.setLayout(new VerticalStackLayout(0, LogicProgram.fontSize / 5));
      printpageheader.add(jpanel, "East");
      LogicLabel logiclabel;
      jpanel.add(logiclabel = printpageheader.pageLabel = new LogicLabel("Page ? of ?"));
      logiclabel.setBackground(LogicProgram.printColors[1]);
      SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE, M/d/yy K:mm a");
      jpanel.add(logiclabel = new LogicLabel("Date: " + simpledateformat.format(new Date())));
      logiclabel.setBackground(LogicProgram.printColors[1]);
      JPanel jpanel1 = new JPanel();
      jpanel1.setBackground(LogicProgram.printColors[1]);
      printpageheader.add(jpanel1, "Center");
      JPanel jpanel2 = new JPanel();
      jpanel2.setBackground(LogicProgram.printColors[1]);
      jpanel2.setLayout(new VerticalStackLayout(0, LogicProgram.fontSize / 5));
      printpageheader.add(jpanel2, "West");
      if (userinfo != null) {
         jpanel2.add(logiclabel = new LogicLabel("Class: " + userinfo.getField("className", "")));
         logiclabel.setBackground(LogicProgram.printColors[1]);
         jpanel.add(logiclabel = new LogicLabel("Name: " + userinfo.getFullName()));
         logiclabel.setBackground(LogicProgram.printColors[1]);
      }

      if (courseinfo != null) {
         SizedPanel sizedpanel = new SizedPanel();
         sizedpanel.setLayout(new GridBagLayout());
         jpanel2.add(sizedpanel);
         CourseChooserPanel.fillStaffPanel(sizedpanel, courseinfo);
      }

      SizedPanel sizedpanel1 = new SizedPanel(-1, LogicProgram.fontSize * 2);
      sizedpanel1.setBackground(LogicProgram.printColors[1]);
      printpageheader.add(sizedpanel1, "South");
      return printpageheader;
   }
}
