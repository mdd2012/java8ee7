package b;

import java.util.Date;

public class Bean2sub {

	private int intField;
	private long longField;
	private Integer integerField;
	private String strField;
	private Date dateField;
	private String only2;


	public String getOnly2() {
		return only2;
	}


	public void setOnly2(String only2) {
		this.only2 = only2;
	}


	public int getIntField() {
		return intField;
	}


	public void setIntField(int intField) {
		this.intField = intField;
	}


	public long getLongField() {
		return longField;
	}


	public void setLongField(long longField) {
		this.longField = longField;
	}


	public Integer getIntegerField() {
		return integerField;
	}


	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}


	public String getStrField() {
		return strField;
	}


	public void setStrField(String strField) {
		this.strField = strField;
	}


	public Date getDateField() {
		return dateField;
	}


	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}


	@Override
	public String toString() {
		return "" + intField + "," + longField + "," + integerField + ", " + strField + "," + dateField + "," + only2;
	}
}
