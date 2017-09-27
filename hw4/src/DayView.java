import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * DayView class is a JPanel with strings of times and JTables
 */
public class DayView extends JPanel {

    /**
     * array of time strings
     */
    public static final String[] times = {
        "1 am", "2 am", "3 am", "4 am", "5 am", "6 am", "7 am", "8 am", "9 am", "10 am", "11 am",
        "12 am", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm",
        "11 pm", "12 pm"
    };

   /**
    * DayView constructor with CalendarModel parameter
    * @param: events
    */
    public DayView(CalendarModel events) throws IOException {
        date = new JLabel();
        color = new Color(152, 217, 233);
        p2 = new JPanel(new BorderLayout());
        p = new JScrollPane(p2);
        eventController = new EventController();
        this.calendarModelEvents = events;
        this.setLayout(new BorderLayout());
        today();

    }

    /**
     * Left() method accounts for displaying time's in column of day view
     * @param: ArrayList events
     */
    private void Left(TreeMap<String, Event> events)
    {
        Object[][] rowData2D = new Object[24][1];
        for (int i = 0; i < 24; i++) {
            rowData2D[i][0] = times[i];
        }
        Object[] columnNames = {""};
        
        if (events != null)
        {
            final int[] h = new int[24];

            leftTable = new JTable(rowData2D, columnNames)
            {
                @Override
                public Component prepareRenderer(TableCellRenderer r, int i, int ic) {
                    Component c = super.prepareRenderer(r, i, ic);
                    
                    if (h[i] == 1) {
                        c.setBackground(color);
                    } else {
                        c.setBackground(Color.white);
                    }
                    return c;
                }
            };
        } 
        else 
        {
        	leftTable = new JTable(rowData2D, columnNames);
        }

        leftTable.setTableHeader(null);
        leftTable.setRowHeight(40);
        leftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        leftTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        leftTable.setGridColor(Color.red);
        leftTable.setEnabled(false);
           
    }

   /**
    * right() method creates grid style JTable
    * @param: ArrayList events
    */
    private void right(TreeMap<String, Event> events)
    {
        Object[][] o = new Object[24][1];
        Object[] t = {""};

        if (events != null) {
            final int[] h = new int[24];
            for (Map.Entry<String, Event> a : events.entrySet())
            {
            	int a1 = Event.getHour(a.getValue().getStartTime());
            	int b1 = Event.getHour(a.getValue().getEndTime());
                int start = a1 -1;
                int end = b1 - 1 ;

                o[start][0] = a.getValue().getTitle();

                while (start <= end) {
                    h[start++] = 1;
                }
            }

            rightTable = new JTable(o, t)
            {
                @Override
                public Component prepareRenderer(TableCellRenderer r, int i, int ic) {
                    Component comp = super.prepareRenderer(r, i, ic);
                    
                    if (h[i] == 1) {
                        comp.setBackground(color);
                    } else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } 
        else 
        {
            rightTable = new JTable(o, t);
        }

        rightTable.setTableHeader(null);
        rightTable.setRowHeight(40);
        rightTable.setGridColor(Color.red);
        rightTable.setEnabled(false);
    }

    /**
     * this should show event on the right side
     * show()
     * @param: ArrayList events
     */
    private void show(TreeMap<String, Event> events) {

        this.invalidate();
        p2.removeAll();

        Left(events);
        right(events);
        String a = eventController.currentDay();
        int b = eventController.getMonth();
        int c = eventController.getDay();
        headerDate(a + " " + (b+1) + "/" + c);

        p2.add(leftTable, BorderLayout.WEST);
        p2.add(rightTable, BorderLayout.CENTER);

        this.add(date, BorderLayout.NORTH);
        this.add(p, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    /**
     * headerDate() sets the date panel header
     * @param d
     */
    private void headerDate(String d) {
        this.date.setText(d);
    }

    /**
     * today() method
     * sets current date
     */
    public void today() 
    {
    	CalendarDay a = eventController.date();
    	eventController.currentDate();
        System.out.println("today: "+eventController.date2MMYYDD());
        show(calendarModelEvents.getMyEvents(eventController.date2MMYYDD()));
    }

   /**
    * view() method
    * @param: year, month and day
    */
    public void view(int y, int m, int d) {
    	eventController.setCalendar(y, m, d); //return gc
    	CalendarDay a = eventController.date();
    	System.out.println("view: "+eventController.date2MMYYDD());
        show(calendarModelEvents.getMyEvents(eventController.date2MMYYDD()));
    }

    public CalendarModel getModel(){
        return calendarModelEvents;
    }


    private JLabel date;
    private JScrollPane p;
    private JTable leftTable, rightTable;
    private JPanel p2;
    private EventController eventController;
    private CalendarModel calendarModelEvents;
    private Color color;
}

