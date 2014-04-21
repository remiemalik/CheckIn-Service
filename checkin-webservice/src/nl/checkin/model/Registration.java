package nl.checkin.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Registration {

	private int id;
	private Calendar checkInDate;
	private Calendar checkOutDate;
    private String checkOutDateString;
	private int dayName;
	private long minutes;
	DateFormat dateFormat;
    Calendar calendar;

	
	public Registration() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar = Calendar.getInstance();
	}
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	public void setCheckInDate() {
		this.checkInDate = calendar;
	}
	
	public void setCheckInDate(String calendarString) throws ParseException{
		this.checkInDate = calendar;
		this.checkInDate.setTime(dateFormat.parse(calendarString));
	}

	public String getCheckInDate() {
		return dateFormat.format(this.checkInDate.getTime());
	}
	
	public Calendar getCheckInDateInstance(){
		return checkInDate;
	}

	public void setCheckOutDate() {
		setCalendar();
		this.checkOutDate = calendar;
		setCheckOutDateString(dateFormat.format(this.checkOutDate.getTime()));
	}
	

	public String getCheckOutDateString() {
		return checkOutDateString;
	}


	public void setCheckOutDateString(String checkOutDateString) {
		this.checkOutDateString = checkOutDateString;
	}


	public void setCalendar() {
		this.calendar =  Calendar.getInstance();;
	}


	public String getCheckOutDate() {
		return dateFormat.format(this.checkOutDate.getTime());
	}
	
	public Calendar getCheckOutDateInstance(){
		return checkOutDate;
	}

	
	public int getDayName() {
		return dayName;
	}

	public void setDayName() {
		this.dayName = calendar.DAY_OF_WEEK;
	}

	public long getMinutes() {
		return minutes;
	}

	public void setMinutes(long minutes) {
		this.minutes = minutes;
	}
	
	
}
