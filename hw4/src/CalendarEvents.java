/**
 * CalendarEvents class creates an event with three parameters
 */
public class CalendarEvents {
    private String title;
    private String startTime;
    private String endTime;

   /**
    * Constructor
    * @param: title, startHour, endHour
    */
    public CalendarEvents(String t, String st, String et){
        this.title = t;
        this.startTime = st;
        this.endTime = et;

    }

}
