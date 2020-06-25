package Q2;

import javax.swing.*;

public class Main {

    private static int WIDTH=800,HEIGHT=800;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar");
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CalendarPanel());
        frame.setVisible(true);
    }
}
