package nl.checkin.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Registration {

	private int id;
	private int fk_user_id;
	private long temporaryDate;
	private long checkInDate;
	private long checkOutDate;
	private int dayName;
	private int week;
	private int year;
	private int minutes;
	
	public Registration() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFk_user_id() {
		return fk_user_id;
	}

	public void setFk_user_id(int fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	
	

	public long getTemporaryDate() {
		return temporaryDate;
	}

	public void setTemporaryDate(long temporaryDate) {
		this.temporaryDate = temporaryDate;
	}

	public long getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(long checkInDate) {
		this.checkInDate = checkInDate;
	}

	public long getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(long checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getDayName() {
		return dayName;
	}

	public void setDayName(int dayName) {
		this.dayName = dayName;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	
	

	
	
}
