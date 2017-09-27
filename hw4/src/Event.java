import java.util.Date;

/**
 * Created by brandonabajelo on 3/4/17.
 * Version 1.0
 * Purpose of event class is to define set paramaters for new events to be created
 */
public class Event {

    private Date currentDate;
    private String title;
    private String eventDate;
    private String startTime;
    private String endTime;

    /** Event constructor with no ending time
     * @param theTitle , title of event
     * @param theEventDate , date of event
     * @param theStartTime , starting time of event
     */
    public Event(String theTitle, String theEventDate, String theStartTime){
        this.title = theTitle;
        this.eventDate = modifyStringToSort(theEventDate);
        this.startTime = modifyTimeToString(theStartTime);
    }

    /** Event constructor with ending time
     * @param theTitle , title of event
     * @param theEventDate , date of event
     * @param theStartTime , starting time of event
     * @param theEndTime , ending time of event
     */
    public Event(String theTitle, String theEventDate, String theStartTime, String theEndTime){
        this.title = theTitle;
        this.eventDate = modifyStringToSort(theEventDate);
        this.startTime = modifyTimeToString(theStartTime);
        this.endTime = modifyTimeToString(theEndTime);
    }

    /** modifyStringToSort ensures that the date string is formatted correctly so that sorting and searching
     * will be successful
     * @param date , string representation of date
     * @return , string formatted correctly
     */
    public static String modifyStringToSort(String date){
       String month = date.split("/")[0];
        String day = date.split("/")[1];
        if(month.length() ==1){
            month="0"+month;
        }
        if(day.length() ==1){
            day="0"+day;
        }
        return month+"/"+day+"/"+date.split("/")[2];
    }

    /** modifyTimeToString ensures that the time string is formatted correctly so that sorting and searching will be
     * succesfull
     * @param time , string representation of time of event
     * @return , string formatted correctly
     */
    public static String modifyTimeToString(String time){
        String hour = time.split(":")[0];
        String minute = time.split(":")[1];
        if(hour.length() ==1){
            hour="0"+hour;
        } if(minute.length() ==1){
            minute="0"+minute;
        }
        return hour+":"+minute;
    }

    /**
     * modifyTimeToInt() converts a string representation of time and
     * converts to int so checking for conflicting events is easier
     * @param time String
     * @return int
     */
    public static int modifyTimeToInt(String time){
        String hour = time.split(":")[0];
        String minute = time.split(":")[1];
        if(hour.length() ==1){
            hour="0"+hour;
        } if(minute.length() ==1){
            minute="0"+minute;
        }
        return Integer.parseInt(hour+minute);
    }


    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /** override toString method so event date is correct in our tree map
     * @return , correctly formatted event date
     */
    @Override
    public String toString(){
        return getEventDate() +" " +getStartTime() +"-" + getEndTime() +" " + getTitle() + "\n";
    }

    public String toStringOutput(){
        return getEventDate() +"|" +getStartTime() +"-" + getEndTime() +"|" + getTitle();
    }

    /**
     * isConflict() checks if there is a conflict in scheduling events
     * @param time
     * @return
     */
    public boolean isConflict(String time){
        boolean flag = false;
        int st = modifyTimeToInt(startTime);
        int et = modifyTimeToInt(endTime);
        int eventtime = modifyTimeToInt(time);

        if (st < eventtime && eventtime < et)
            flag = true;
        return flag;
    }

    /**
     * getHour() gets the hour of event
     * @param time String
     * @return int
     */
    public static int getHour(String time){
        return Integer.parseInt(time.split(":")[0]);
    }
}
