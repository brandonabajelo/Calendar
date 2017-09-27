import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;


public class CalendarFrame extends JFrame implements ChangeListener {

    private EventController eventController;
    private CalendarModel CalendarModel;
    private DayView DayView;
    final JPanel rightPanel;

    /**
     * CalendarFrame constructor creates a CalendarFrame object
     *
     */
    public CalendarFrame() throws IOException
    {
        CalendarModel = new CalendarModel();

        DayView = new DayView(CalendarModel);
        rightPanel = new JPanel();
        CalendarModel.addChangeListener(this);

        eventController = new EventController(CalendarModel);
        eventController.set(DayView);

        final CalendarView calendar = new CalendarView(eventController, CalendarModel);
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout());
        p.add(calendar);
        calendar.setPreferredSize(new Dimension(300, 300));
   
        left.add(p, BorderLayout.CENTER);

        rightPanel.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        add(left, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        setTitle("CALENDAR");

        rightPanel.add(DayView, BorderLayout.CENTER);
        rightPanel.validate();
        eventController.get().view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
        rightPanel.repaint();
    }

 
    /**
     * stateChanged() repaints dayView
     * @param: CalendarModel
     */
    public void stateChanged(ChangeEvent e) 
    {
    	rightPanel.removeAll();
        rightPanel.add(DayView, BorderLayout.CENTER);
        rightPanel.validate();
        rightPanel.repaint();
        this.repaint();
    }
}
