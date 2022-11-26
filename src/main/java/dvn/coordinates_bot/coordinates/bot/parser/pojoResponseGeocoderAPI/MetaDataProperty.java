package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaDataProperty{

	@JsonProperty("GeocoderResponseMetaData")
	private GeocoderResponseMetaData geocoderResponseMetaData;

	@JsonProperty("GeocoderMetaData")
	private GeocoderMetaData geocoderMetaData;

	public void setGeocoderResponseMetaData(GeocoderResponseMetaData geocoderResponseMetaData){
		this.geocoderResponseMetaData = geocoderResponseMetaData;
	}

	public GeocoderResponseMetaData getGeocoderResponseMetaData(){
		return geocoderResponseMetaData;
	}

	public void setGeocoderMetaData(GeocoderMetaData geocoderMetaData){
		this.geocoderMetaData = geocoderMetaData;
	}

	public GeocoderMetaData getGeocoderMetaData(){
		return geocoderMetaData;
	}

	@Override
 	public String toString(){
		return 
			"MetaDataProperty{" + 
			"geocoderResponseMetaData = '" + geocoderResponseMetaData + '\'' + 
			",geocoderMetaData = '" + geocoderMetaData + '\'' + 
			"}";
		}
}