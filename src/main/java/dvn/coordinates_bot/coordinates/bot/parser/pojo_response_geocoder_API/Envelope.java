package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Envelope{

	@JsonProperty("lowerCorner")
	private String lowerCorner;

	@JsonProperty("upperCorner")
	private String upperCorner;

	public void setLowerCorner(String lowerCorner){
		this.lowerCorner = lowerCorner;
	}

	public String getLowerCorner(){
		return lowerCorner;
	}

	public void setUpperCorner(String upperCorner){
		this.upperCorner = upperCorner;
	}

	public String getUpperCorner(){
		return upperCorner;
	}

	@Override
 	public String toString(){
		return 
			"Envelope{" + 
			"lowerCorner = '" + lowerCorner + '\'' + 
			",upperCorner = '" + upperCorner + '\'' + 
			"}";
		}
}