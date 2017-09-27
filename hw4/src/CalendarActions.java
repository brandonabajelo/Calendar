import java.io.*;
import java.text.ParseException;
import java.util.*;



/**
 * Created by brandonabajelo on 3/4/17.
 * Version 1.0
 * Purpose of CalendarActions is to provide functionality for all the various
 * actions that the user can do with their calendar
 */
public class CalendarActions {

    // inside TreeMap holds all the events. The first tree map sorts the dates
    static public TreeMap<String, Event> listOfEvents = new TreeMap<>();

    //outside TreeMaps holds all the dates. The second tree map sorts the times of multiple events in the same day
    static public TreeMap<String, TreeMap<String, Event>> tm = new TreeMap<>();

    /** printCalendarMonthYear method prints a calendar based on the month and year integers
     * passed. This method calls printCalendar to populate the calendar with events
     * @param month , representing a month in the calendar year
     * @param year , representing a specific year
     */
    public static void printCalendarMonthYear(int month, int year) {
        // create a new GregorianCalendar object
        Calendar cal = new GregorianCalendar();

        // set its date to the first day of the month/year given by user
        cal.clear();
        cal.set(year, month - 1, 1);

        Calendar today = new GregorianCalendar();
        int number = 0;
        if(today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && today.get(Calendar.MONTH) == cal.get(Calendar.MONTH)){
            number = today.get(Calendar.DAY_OF_MONTH);
        }

        // print calendar header
        System.out.println("\n"
                + cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                Locale.US) + " " + cal.get(Calendar.YEAR));

        // obtain the weekday of the first day of month.
        int firstWeekdayOfMonth = cal.get(Calendar.DAY_OF_WEEK);

        // obtain the number of days in month.
        int numberOfMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // for star



        // print anonymous calendar month based on the weekday of the first
        // day of the month and the number of days in month:
        printCalendar(tm, month, year, numberOfMonthDays, firstWeekdayOfMonth, number);
    }


    /** printCalendar formats the calendar to be printed as well as places brackets around the current day and * near
     * days that have an event scheduled
     * @param outsideMap , tree map of events
     * @param month , month in a specific calendar year
     * @param year , year
     * @param numberOfMonthDays , how many days are in the specific month
     * @param firstWeekdayOfMonth , identifies which day is the first day of the month
     * @param bracket , used to find the current day so a bracket can be placed around it
     */
    public static void printCalendar(TreeMap<String, TreeMap<String, Event>> outsideMap, int month, int year,
                                     int numberOfMonthDays, int firstWeekdayOfMonth, int bracket) {

        Calendar newCal = new GregorianCalendar();

        String starKey = "";

        // reset index of weekday
        int weekdayIndex = 0;

        // print calendar weekday header
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        // leave/skip weekdays before the first day of month
        for (int day = 1; day < firstWeekdayOfMonth; day++) {
            System.out.print("    ");
            weekdayIndex++;
        }

        // print the days of month in tabular format.
        for (int day = 1; day <= numberOfMonthDays; day++) {
           starKey = Event.modifyStringToSort(month  + "/" + day + "/" + year);
            // if there's an event on the day use *
            if(outsideMap.get(starKey) != null && month == newCal.get(Calendar.MONTH)+1 && year == newCal.get(Calendar.YEAR))
                System.out.print("*");
            // print day
            if(bracket == day)
            System.out.printf("[%1$2d]", day);
            else
            System.out.printf("%1$2d", day);

            // next weekday
            weekdayIndex++;
            // if it is the last weekday
            if (weekdayIndex == 7) {
                // reset it
                weekdayIndex = 0;
                // and go to next line
                System.out.println();
            } else { // otherwise
                // print space
                System.out.print("  ");
            }
        }

        // print a final new-line.
        System.out.println();
    }

    static Scanner scanner = new Scanner(System.in);

    /** creatEvent method prompts user for all information needed to create a
     * new event and add it to the tree map
     *
     * @throws ParseException
     */
    public static boolean createEvent(String title, String eventDate, String startingTime, String endingTime) throws ParseException {
        //listOfEvents = new TreeMap<>();

        boolean flag = false;

            Event createdEvent = new Event(title, Event.modifyStringToSort(eventDate), startingTime, endingTime);

            // if the outer tree map is empty for that specific date, reserve spot for that day. if the day is already made, it will skip this
            // if statement
            if (tm.get(createdEvent.getEventDate()) == null) {
                tm.put(createdEvent.getEventDate(), new TreeMap<String, Event>());
            }

            listOfEvents = tm.get(createdEvent.getEventDate());

            if(listOfEvents.size()==0)
                listOfEvents.put(startingTime, createdEvent);

            for(Map.Entry<String, Event> e : listOfEvents.entrySet()) {


                if (!e.getValue().isConflict(startingTime)) {
                    listOfEvents.put(startingTime, createdEvent);

                    tm.put(createdEvent.getEventDate(), listOfEvents);

                    flag = true;
                }
            }
                return flag;
    }

    /** loadCalendar fills our tree map of events with events
     * listed in a text file: events.txt
     * @throws IOException
     */
    public static void loadCalendar() throws IOException {
        FileReader file = new FileReader("events.txt");
        BufferedReader br = new BufferedReader(file);

        String title, eventDate, startingTime, endingTime;

        String line = "";
        //set line equal to line read from createdEvent.txt
        while ((line = br.readLine()) != null) {
            eventDate = line.split("\\|")[0];

            // this time variable is going to catch the entire time frame
            String time = line.split("\\|")[1];

            startingTime = time.split("-")[0];

            if (time.split("-").length == 1) {
                endingTime = null;
            } else {
                endingTime = time.split("-")[1];
            }
            title = line.split("\\|")[2];


            Event createdEvent = new Event(title, eventDate, startingTime, endingTime);

            if (tm.get(createdEvent.getEventDate()) == null) {
                tm.put(createdEvent.getEventDate(), new TreeMap<String, Event>());
            }

            listOfEvents = tm.get(createdEvent.getEventDate());

            listOfEvents.put(startingTime, createdEvent);

            tm.put(createdEvent.getEventDate(), listOfEvents);
        }
    }

    /** viewCalendarBy allows the user to pick how they wish to view the calendar.
     * View by day will display all the
     * events for the current day. View by month will display a month view with *'s
     * next to the days with events
     */
    public static void viewCalendarBy() {
        System.out.println("[D]ay view or [M]onth view ? (Enter the corresponding letter)");
        String option = scanner.nextLine().toUpperCase();
        //create a variable with the current day
        Calendar currentDay = new GregorianCalendar();
        String choice;
        switch (option) {
            case "D":
                do {
                    String currentDay2String = (currentDay.get(Calendar.MONTH) + 1 + "/" + currentDay.get(Calendar.DAY_OF_MONTH) +
                            "/" + currentDay.get(Calendar.YEAR));
                    currentDay2String = Event.modifyStringToSort(currentDay2String);
                    if (tm.get(currentDay2String) != null) {
                        for (Map.Entry<String, Event> events : tm.get(currentDay2String).entrySet()) {
                            System.out.println(events.getValue().toString());
                        }
                    } else {
                        System.out.println("There is no event on this day");
                    }
                    System.out.println("[P]revious or [N]ext or [M]ain menu");
                    choice = scanner.nextLine().toUpperCase();
                    if (choice.equals("P")) {
                        currentDay = new GregorianCalendar(currentDay.get(Calendar.YEAR),
                                currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH) - 1);

                    }
                    else if (choice.equals("N")) {
                        currentDay = new GregorianCalendar(currentDay.get(Calendar.YEAR),
                                currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH) + 1);

                    }
                }while (choice.equals("P") || choice.equals("N"));
                break;
            case "M":
                printCalendarMonthYear(currentDay.get(Calendar.MONTH) + 1, currentDay.get(Calendar.YEAR));
                do {
                    System.out.println("[P]revious or [N]ext or [M]ain menu");
                     choice = scanner.nextLine().toUpperCase();

                    if (choice.equals("P")) {
                        currentDay = new GregorianCalendar(currentDay.get(Calendar.YEAR),
                                currentDay.get(Calendar.MONTH) - 1, currentDay.get(Calendar.DAY_OF_MONTH));

                        printCalendarMonthYear(currentDay.get(Calendar.MONTH) + 1, currentDay.get(Calendar.YEAR));
                    }
                    else if (choice.equals("N")) {
                        currentDay = new GregorianCalendar(currentDay.get(Calendar.YEAR),
                                currentDay.get(Calendar.MONTH) + 1, currentDay.get(Calendar.DAY_OF_MONTH));

                        printCalendarMonthYear(currentDay.get(Calendar.MONTH) + 1, currentDay.get(Calendar.YEAR));
                    }
                } while (choice.equals("P") || choice.equals("N"));
                break;
        }
    }

    /** viewEventByDate allows the user to enter a specific date and view events on that
     * date
     */
    public static void viewEventByDate() {
        System.out.println("Enter the date of the event (MM/DD/YYYY)");
        String option = scanner.nextLine();
        if (tm.get(option) != null) {
            for (Map.Entry<String, Event> events : tm.get(option).entrySet()) {
                System.out.println(events.getValue().toString());
            }
        } else{
            System.out.println("No event has been scheduled on this day\n");
        }

    }
    public static TreeMap<String, Event> viewEventByDate(String option) {
        if (tm.get(option) != null) {
            return tm.get(option);
        }
        return null;
    }

    /** displayAllEvents will iterate through the tree map of events and print them all out in order of
     * event date
     *
     * @param outsideMap, list of all events held in this tree map
     */
    public static void displayAllEvents(TreeMap<String, TreeMap<String, Event>> outsideMap) {
        for (Map.Entry<String, TreeMap<String, Event>> outTM : outsideMap.entrySet()) {

            for (Map.Entry<String, Event> events : outTM.getValue().entrySet()) {

                System.out.println(events.getValue().toString());
            }
        }
    }

    /** deleteEvent allows the user two options in deleting events
     * First option is delete an event by specific date.
     * Second option is deleting all events
     */
    public static void deleteEvent() {
        System.out.println("Delete options: [S]elect (To select a date) or [A]ll (To delete all events)");
       String option = scanner.nextLine().toUpperCase();

        switch (option) {
            case "S":
                System.out.println("Enter the date of the event you wish to delete (MM/DD/YYYY)");
                String input = scanner.nextLine();
                if (tm.get(input) != null) {
                    tm.get(input).clear();
                    System.out.println("All events on this date have been deleted \n");
                } else {
                    System.out.println("Event not found.\n");
                }
                break;
            case "A":
                tm.clear();
                System.out.println("All events have been deleted\n");
                break;
        }
    }

    /** saveEventsToTxtFile saves all events created to a txt file events.txt
     */
    public static void saveEventsToTxtFile(){
       try {
           PrintWriter filewriter = new PrintWriter(new FileWriter(new File("events.txt"), false));

           for (Map.Entry<String, TreeMap<String, Event>> outTM : tm.entrySet()) {

               for (Map.Entry<String, Event> events : outTM.getValue().entrySet()) {

                   filewriter.println(events.getValue().toStringOutput());
               }
           }filewriter.close();
       } catch(IOException e){
           System.err.println("error writing to file");
        }
    }

}
