/**
 * CalendarDay class represents a day in the calendar.
 *
 */
public class CalendarDay implements Comparable<CalendarDay>
{
    private int year;
    private int month;
    private int day;

	/**
	 * Constructor CalendarDay
	 * @param aMonth month
	 * @param aDay day
	 * @param aYear year
	 */
    CalendarDay(int aMonth, int aDay, int aYear)
    {
		this.month = aMonth;
		this.day = aDay;
    	this.year = aYear;
    }

	/**
	 * boolean compareTo() compares two days
	 * @param date CalendarDay object
	 * @return -1 if year/month/day is earlier than date object
	 * 			1 if year/month/day is later than date object
	 * 			0 if days are the same
	 */
	public int compareTo(CalendarDay date)
	{
		if(this.year < date.year) return-1;

		else if(this.year > date.year) return 1;

		else
		{
			if(this.month < date.month) return -1;

			else if(this.month > date.month) return 1;

			else
			{
				if(this.day < date.day) return -1;

				else if(this.day > date.day) return 1;

				else return 0;
			}
		}
	}
    
   
}