package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Premise{

	@JsonProperty("PremiseNumber")
	private String premiseNumber;

	public void setPremiseNumber(String premiseNumber){
		this.premiseNumber = premiseNumber;
	}

	public String getPremiseNumber(){
		return premiseNumber;
	}

	@Override
 	public String toString(){
		return 
			"Premise{" + 
			"premiseNumber = '" + premiseNumber + '\'' + 
			"}";
		}
}