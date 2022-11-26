package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

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