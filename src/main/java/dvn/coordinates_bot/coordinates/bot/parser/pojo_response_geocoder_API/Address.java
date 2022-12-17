package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Address{

	@JsonProperty("Components")
	private List<ComponentsItem> components;

	@JsonProperty("country_code")
	private String countryCode;

	@JsonProperty("formatted")
	private String formatted;

	public void setComponents(List<ComponentsItem> components){
		this.components = components;
	}

	public List<ComponentsItem> getComponents(){
		return components;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setFormatted(String formatted){
		this.formatted = formatted;
	}

	public String getFormatted(){
		return formatted;
	}

	@Override
 	public String toString(){
		return 
			"Address{" + 
			"components = '" + components + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",formatted = '" + formatted + '\'' + 
			"}";
		}
}