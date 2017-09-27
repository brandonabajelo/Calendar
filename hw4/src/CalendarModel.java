import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/**
 * CalendarModel class reads events from text file and uploads calendar with events
 */
public class CalendarModel {

    public CalendarModel()
    {
        try {
            CalendarActions.loadCalendar();
        }catch(Exception e) {
        }
        list = new HashMap();
        dates = new ArrayList<CalendarDay>();
        l = new ArrayList();
    }


    /**
     * addChangeListener() adds listener to array list
     * @param: l
     */
    public void addChangeListener(ChangeListener l) {
        this.l.add(l);
    }

   /**
    * update()
    * updates listeners
    */
    public void update() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener listener : l) {
            listener.stateChanged(e);
        }
    }

    /**
     * getMyEvents() method returns tree map of events
     * @param date String object
     * @return tree map
     */
    public TreeMap<String, Event> getMyEvents(String date){

        return CalendarActions.viewEventByDate(date);
    }

    private HashMap<CalendarDay, ArrayList<CalendarEvents>> list;
    private ArrayList<CalendarDay> dates;
    private ArrayList<ChangeListener> l;

}
