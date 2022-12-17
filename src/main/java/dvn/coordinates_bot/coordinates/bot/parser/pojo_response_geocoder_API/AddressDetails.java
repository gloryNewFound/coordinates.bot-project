package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDetails{

	@JsonProperty("Country")
	private Country country;

	public void setCountry(Country country){
		this.country = country;
	}

	public Country getCountry(){
		return country;
	}

	@Override
 	public String toString(){
		return 
			"AddressDetails{" + 
			"country = '" + country + '\'' + 
			"}";
		}
}