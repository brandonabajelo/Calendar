import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

/**
 * EventView class is used when user wants to create a new event.
 * Provides GUI for creating events
 */
class EventView extends JFrame implements ActionListener
{
    public EventView(CalendarModel event, EventController c)
    {
        model = event;
        firstP = new JPanel();
        firstP.setLayout(new BorderLayout());
        eventController = c;

        name = new JTextField();
        name.setPreferredSize( new Dimension(300 , 28 ) );
        name.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	name.setText("");
            }
        });

       JPanel panel = new JPanel(new FlowLayout());
       current = new JLabel((eventController.getMonth()+1) + "/" + eventController.getDay() + "/" + (eventController.getYear()));
       current.setBackground( null );

       to = new JLabel("to: ");
       to.setBackground( null );
       panel.add(current);

       info = new JLabel("Ex format: HH:MM");

       start = new JTextField(10);
       end = new JTextField(10);

       panel.add(start);
       panel.add(to);
       panel.add(end);
       panel.add(info);

       error = new JLabel();
       error.setForeground(Color.GRAY);

       JButton b1 = new JButton("SAVE");
       b1.addActionListener(this);
       panel.add(b1);
        
       firstP.add(name,BorderLayout.NORTH);
       firstP.add(panel,BorderLayout.CENTER);
       firstP.add(error,BorderLayout.SOUTH);
        

       add(firstP);       
       setVisible(true);

    }

    /**
     * checks for error in event creation
     * @param action action
     */
	public void actionPerformed(ActionEvent action)
	{
		error.setText("");
        try {
            boolean flag = CalendarActions.createEvent(name.getText(),current.getText(),start.getText(), end.getText());

            if(flag){
                this.setVisible(false);
            this.dispose();
                eventController.get().view(eventController.getYear(), eventController.getMonth(), eventController.getDay());
                model.update();
            }else{

            error.setText("Error, Please indicate a valid time: ");
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

	}
    private CalendarModel model;
    private JPanel firstP;
    private JTextField name;
    private JLabel to, current, info;
    private JTextField start, end;
    private JLabel error;
    private EventController eventController;
}