package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point{

	@JsonProperty("pos")
	private String pos;

	public void setPos(String pos){
		this.pos = pos;
	}

	public String getPos(){
		return pos;
	}

	@Override
 	public String toString(){
		return 
			"Point{" + 
			"pos = '" + pos + '\'' + 
			"}";
		}
}