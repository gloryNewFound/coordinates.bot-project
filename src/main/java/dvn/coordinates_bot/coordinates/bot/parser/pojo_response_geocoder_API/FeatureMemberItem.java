package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureMemberItem{

	@JsonProperty("GeoObject")
	private GeoObject geoObject;

	public void setGeoObject(GeoObject geoObject){
		this.geoObject = geoObject;
	}

	public GeoObject getGeoObject(){
		return geoObject;
	}

	@Override
 	public String toString(){
		return 
			"FeatureMemberItem{" + 
			"geoObject = '" + geoObject + '\'' + 
			"}";
		}
}