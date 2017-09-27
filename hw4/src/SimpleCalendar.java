import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * main method implementation of GUI calendar
 */
public class SimpleCalendar
{
    public static void main(String[] args) throws IOException 
    {
        CalendarFrame calendarFrame = new CalendarFrame();
        calendarFrame.setSize(new Dimension(1000, 600));
        calendarFrame.setResizable(true);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setVisible(true);
    }
}
