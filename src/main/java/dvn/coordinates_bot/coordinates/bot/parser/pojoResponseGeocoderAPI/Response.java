package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("response")
	private Response response;

	@JsonProperty("GeoObjectCollection")
	private GeoObjectCollection geoObjectCollection;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	public void setGeoObjectCollection(GeoObjectCollection geoObjectCollection){
		this.geoObjectCollection = geoObjectCollection;
	}

	public GeoObjectCollection getGeoObjectCollection(){
		return geoObjectCollection;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"response = '" + response + '\'' + 
			",geoObjectCollection = '" + geoObjectCollection + '\'' + 
			"}";
		}
}