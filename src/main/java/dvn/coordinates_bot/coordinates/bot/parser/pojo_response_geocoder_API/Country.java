package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country{

	@JsonProperty("AdministrativeArea")
	private AdministrativeArea administrativeArea;

	@JsonProperty("CountryName")
	private String countryName;

	@JsonProperty("AddressLine")
	private String addressLine;

	@JsonProperty("CountryNameCode")
	private String countryNameCode;

	public void setAdministrativeArea(AdministrativeArea administrativeArea){
		this.administrativeArea = administrativeArea;
	}

	public AdministrativeArea getAdministrativeArea(){
		return administrativeArea;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setAddressLine(String addressLine){
		this.addressLine = addressLine;
	}

	public String getAddressLine(){
		return addressLine;
	}

	public void setCountryNameCode(String countryNameCode){
		this.countryNameCode = countryNameCode;
	}

	public String getCountryNameCode(){
		return countryNameCode;
	}

	@Override
 	public String toString(){
		return 
			"Country{" + 
			"administrativeArea = '" + administrativeArea + '\'' + 
			",countryName = '" + countryName + '\'' + 
			",addressLine = '" + addressLine + '\'' + 
			",countryNameCode = '" + countryNameCode + '\'' + 
			"}";
		}
}