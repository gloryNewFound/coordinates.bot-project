package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseFromGeocoderAPI {

	@JsonProperty("response")
	private ResponseFromGeocoderAPI responseFromGeocoderAPI;

	@JsonProperty("GeoObjectCollection")
	private GeoObjectCollection geoObjectCollection;

	public void setResponse(ResponseFromGeocoderAPI responseFromGeocoderAPI){
		this.responseFromGeocoderAPI = responseFromGeocoderAPI;
	}

	public ResponseFromGeocoderAPI getResponse(){
		return responseFromGeocoderAPI;
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
			"response = '" + responseFromGeocoderAPI + '\'' +
			",geoObjectCollection = '" + geoObjectCollection + '\'' + 
			"}";
		}
}