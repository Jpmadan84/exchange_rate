package mmj.amazonaws.finance.forex;

import com.fasterxml.jackson.annotation.JsonSetter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Rates {
	private double inr;
	public Rates() {
		
	}
	
	public double getInr() {
		return inr;
	}
	@JsonSetter("INR")
	public void setInr(double inr) {
		this.inr = inr;
	}
	
	

}
