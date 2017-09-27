import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * EventController serves as controller of application
 */
public class EventController {

    public final static String[] daysOfMonth = {
        "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

   /**
    * Constructor of EventController
    * @param: CalendarModel
    */
    EventController(CalendarModel CalendarModel)
    {
        this.CalendarModel = CalendarModel;
        gc = new GregorianCalendar();

    }

    /**
     * Constructor with no parameters
     */
   EventController()
    {
        gc = new GregorianCalendar();
        this.CalendarModel = new CalendarModel();
    }

   /**
    * calendar() returns GregorianCalendar object
    */
   
    public GregorianCalendar calendar() 
    {
        return gc;
    }

   /**
    * set() takes DayView parameter
    * @param: a DayView object
    */
    public void set(DayView a)
    {
        this.DayView = a;
    }

   /**
    * get()
    * return: DayView object
    */
    public DayView get()
    {
        return this.DayView;
    }

    /**
     * getMonth()
     * returns current month
     */
    public int getMonth() 
    {
    	int a = (GregorianCalendar.MONTH);
        return gc.get(a);
    }

    /**
     * getYear()
     * returns current year
     */
    public int getYear() 
    {
    	int a = GregorianCalendar.YEAR;
        return gc.get(a);
    }

    /**
     * getDay()
     * returns the current day
     */
    public int getDay() 
    {
    	int a = GregorianCalendar.DAY_OF_MONTH;
        return gc.get(a);
    }

    /**
     * getUpcomingDay()
     * increments day
     */
    public void getUpcomingDay() 
    {
    	int a = GregorianCalendar.DAY_OF_MONTH;
        gc.add(a, 1);
    }

   /**
    * getLastDay()
    * decrements day
    */
    public void getLastDay() 
    {
    	int a = GregorianCalendar.DAY_OF_MONTH;
        gc.add(a, -1);
    }

    /**
     * date()
     * returns current date
     */
    public CalendarDay date()
    {
    	int a = getMonth()+1;
    	int b = getDay();
    	int c = getYear();
        return new CalendarDay( a, b, c);
    }

    /**
     * date2MMYYDD() formats date
     * @return String
     */
    public String date2MMYYDD(){
        return  Event.modifyStringToSort(getMonth()+1+"/"+getDay()+"/"+getYear());
    }

    public String date2MMYYDD(String string){
        return  Event.modifyStringToSort(string);
    }
    
    /**
     * currentDate()
     * sets today's date
     */
    public void currentDate() 
    {
    	int year = GregorianCalendar.YEAR;
    	int month = GregorianCalendar.MONTH;
    	int day = GregorianCalendar.DAY_OF_MONTH;
        gc.set(year, Calendar.getInstance().get(year));
        gc.set(month, Calendar.getInstance().get(month));
        gc.set(day, Calendar.getInstance().get(day));
    }
    
    /**
     * currentDay()
     * returns current day
     */
    public String currentDay() 
    {
    	int a = GregorianCalendar.DAY_OF_WEEK;
        return daysOfMonth[gc.get(a)];
    }

   /**
    * setCalendar() sets date
    * @param : year, month, day
    */
    public void setCalendar(int y, int m, int d)
    {
        gc = new GregorianCalendar(y, m, d);
    }

    private DayView DayView;
    private GregorianCalendar gc;
    private CalendarModel CalendarModel;

}
