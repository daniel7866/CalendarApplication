package Q2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class CalendarPanel extends JPanel {
    private static final int ROWS = 6,COLS = 7,MAX_DAYS = 31,VGAP=5,HGAP=5;
    private JLabel lblTitle;
    private JLabel[] lblDay;
    private JButton[] btnDays;
    private JButton btnNext,btnPrev;
    private Events events;
    private JPanel gridPanel,titlePanel;


    public CalendarPanel(){
        super();

        this.setLayout(new BorderLayout());
        Listener listener= new Listener();

        lblDay = new JLabel[]{new JLabel("Sun"),new JLabel("Mon"),new JLabel("Tue"),new JLabel("Wed"),
                new JLabel("Thu"),new JLabel("Fri"),new JLabel("Sat")};
        btnDays = new JButton[MAX_DAYS];
        for (int i = 0; i < MAX_DAYS; i++) {
            btnDays[i] = new JButton((i+1)+"");
            btnDays[i].addActionListener(listener);
        }
        events = new Events();
        btnNext = new JButton("Next");
        btnPrev = new JButton("Prev");

        btnNext.addActionListener(listener);
        btnPrev.addActionListener(listener);

        refreshCalendarPanel();
    }

    private void refreshCalendarPanel(){//this will set all the buttons and texts on the panel
        //top panel:
        Calendar calendar = events.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH,1);//set the current date to the first day of this month
        lblTitle = new JLabel((int)(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));

        titlePanel = new JPanel();
        titlePanel.add(lblTitle);
        titlePanel.add(btnPrev);
        titlePanel.add(btnNext);
        this.add(titlePanel,BorderLayout.NORTH);
        //grid panel:
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0,COLS,HGAP,VGAP));
        this.add(gridPanel,BorderLayout.CENTER);

        for (int i = 0; i < COLS; i++) {
            gridPanel.add(lblDay[i]);//The days of the week
        }

        int dayOffset = calendar.get(Calendar.DAY_OF_WEEK);//get the first day of the week of current month
        while(dayOffset >1){
            gridPanel.add(new JLabel(""));//add empty cell to the grid to shift the days to the right place in the calendar
            dayOffset--;
        }
        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            if(events.showEvent(new Date(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),i)) != null)
                btnDays[i].setBackground(Color.GREEN);//if there are events on this day - make it green
            else
                btnDays[i].setBackground(Color.lightGray);
            gridPanel.add(btnDays[i]);
        }
    }

    private void refresh(){
        removeAll();
        refreshCalendarPanel();
        validate();
    }

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnPrev) {
                events.prevMonth();
                refresh();
            } else if (e.getSource() == btnNext) {
                events.nextMonth();
                refresh();
            } else {
                for (int i = 0; i < MAX_DAYS; i++) {
                    if (e.getSource() == btnDays[i]) {
                        Calendar c = events.getCalendar();
                        Object[] options = {"Add a new event", "Show events"};
                        final int ADD = 0, SHOW = 1;
                        int opt = JOptionPane.showOptionDialog(null,
                                "",
                                "",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (opt == ADD) {
                            String eventDesc = JOptionPane.showInputDialog(null, "Type event description:");
                            events.addEvent(new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i), eventDesc);
                            refresh();
                        } else {
                            String event = events.showEvent(new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i));
                            if (event == null)
                                event = "There are no events on this day!";
                            JOptionPane.showConfirmDialog(null, event, "", JOptionPane.DEFAULT_OPTION);
                        }
                    }
                }
            }
        }
    }
}