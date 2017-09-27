import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

/**
 * CalendarView class displays the GUI calendar and provides
 * functional buttons related to Calendar
 */
public class CalendarView extends JPanel {

    public final static String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    /**
     * CalendarView constructor with controller and model parameters
     * @param: controller, events
     */
    CalendarView(EventController controller, final CalendarModel events)
    {
        eventController = controller;
        month = new JPanel();
        month.setLayout(new GridLayout(0, 7));
        day = new ArrayList<JLabel>();
        Heading = new JLabel();
     
        previousDayButton = new JButton("<");
        nextDayButton = new JButton(">");



        createButton = new JButton("CREATE EVENT");
        createButton.setBackground(Color.BLUE);
        createButton.setForeground(Color.WHITE);
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);

        quitButton = new JButton("Quit Calendar");
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);
        quitButton.setOpaque(true);
        quitButton.setBorderPainted(false);

        deleteButton = new JButton("DELETE EVENT");
        deleteButton.setBackground(Color.DARK_GRAY);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);


        /**
         * actionListener for quit button
         */
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CalendarActions.saveEventsToTxtFile();
            	System.exit(0);	
            }
        });

        /**
         * actionListener for create button
         */
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventView m = new EventView(events, eventController);
                m.setSize(550, 200);
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                int a = d.width;
                int b = d.height;
                m.setLocation(a/2-m.getSize().width/2, b/2-m.getSize().height/2); 
            }
        });

        addButtonActionListener(previousDayButton);
        addButtonActionListener(nextDayButton);

        JPanel changePanel = new JPanel(new FlowLayout());
        changePanel.add(previousDayButton);
        changePanel.add(nextDayButton);

        weeks = new ArrayList<JLabel>();

        weeks.add(new JLabel("Su", JLabel.CENTER));
        weeks.add(new JLabel("M", JLabel.CENTER));
        weeks.add(new JLabel("T", JLabel.CENTER));
        weeks.add(new JLabel("W", JLabel.CENTER));
        weeks.add(new JLabel("T", JLabel.CENTER));
        weeks.add(new JLabel("F", JLabel.CENTER));
        weeks.add(new JLabel("S", JLabel.CENTER));

        setLayout(new BorderLayout());
        JPanel topP = new JPanel(new BorderLayout());
        JPanel tempP = new JPanel(new FlowLayout());
        tempP.add(createButton);
        tempP.add(deleteButton);
        topP.add(tempP, BorderLayout.NORTH);
        topP.add(Heading, BorderLayout.WEST);
        topP.add(changePanel, BorderLayout.EAST);
        topP.add(quitButton, BorderLayout.SOUTH);
        add(topP, BorderLayout.NORTH);
        add(month, BorderLayout.CENTER);
        month();
    }

   /**
    * month() creates the GUI month view
    */
    public void month() {
        ArrayList<String> d = calendar();
        day.clear();
        month.removeAll();

        for (String s : d) {
            if (s.contains("*")) {
                JLabel tempL = new JLabel(s.substring(1), JLabel.CENTER);
                day.add(tempL);
            } else {

                if (Integer.parseInt(s) == eventController.calendar().get(Calendar.DATE))
                {
                	int a = JLabel.CENTER;
                    JLabel tempL = new JLabel(s, a);
                    tempL.setBorder(BorderFactory.createLineBorder(Color.gray));
                    day.add(tempL);

                } else {
                	
                    JLabel tempL = new JLabel(s, JLabel.CENTER);
                    Font f = tempL.getFont();
                    day.add(tempL);
                }
            }
        }
        for (JLabel a : weeks) {
            month.add(a);
        }

        for (JLabel b : day) {
            b.setOpaque(true);
            b.setBackground(Color.white);
            month.add(b);
        }

        Heading.setText(months[eventController.getMonth()] + " " + eventController.getYear());
        day();
        month.validate();
        month.repaint();
    }

   /**
    * calendar() accounts for empty days of a particular month
    * @return array list
    */
    private ArrayList<String> calendar() {

        ArrayList<String> array = new ArrayList();
        int c = eventController.getDay();
        GregorianCalendar calendar = eventController.calendar();

        GregorianCalendar k = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        int blanks = k.get(Calendar.DAY_OF_WEEK)-1;
        for (int i = 0; i < blanks; i++) {
            array.add("*");
        }

        int num = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        for (int i = 1; i <= num; i++) {
            array.add(String.valueOf(i));
        }

        int b = ((7-( array.size() % 7)) == 7) ? 0 : 7-( array.size() % 7);
    	for(int i =0; i< b; i++)
        {
    		array.add("*");
        }
        calendar.set(GregorianCalendar.DAY_OF_MONTH, c);
        return array;
    }

    /**
     * actionListener for incrementing/decrementing the day view
     * @param button
     */
    private void addButtonActionListener(final JButton button) {
        button.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if (button.getText().equals("<"))
                {
                    eventController.getLastDay();
                    eventController.get().view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
                }
                else 
                {
                    eventController.getUpcomingDay();
                    eventController.get().view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
                }
                month();
            }
        });
    }

    /**
     * day() helps month populate calendar as well as highlights days which have an event
     */
    public void day() {
    	
        int month = eventController.getMonth();
        int year = eventController.getYear();
        
        for (final JLabel a : day) 
        {
            final int m = month;
            final int y = year;


            if(a.getText().matches("\\d+"))
        	{

                TreeMap<String, Event> events =  eventController.get().getModel().getMyEvents(eventController.date2MMYYDD((m+1)+"/"+a.getText()+"/"+y));
                if(events!=null)
                    a.setBackground(Color.yellow);

            a.addMouseListener(
                    new MouseAdapter() {
               
                @Override
                public void mouseClicked(MouseEvent e) {
                	eventController.setCalendar(y, m, Integer.parseInt(a.getText()));
                	month();
                	eventController.get().view(y, m, Integer.parseInt(a.getText()));
                }

                
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        }
    }
    private EventController eventController;
    private JPanel month;
    private ArrayList<JLabel> day;
    private JLabel Heading;
    private ArrayList<JLabel> weeks;
    private JButton previousDayButton;
    private JButton nextDayButton;
    private JButton createButton;
    private JButton quitButton;
    private JButton deleteButton;
}

