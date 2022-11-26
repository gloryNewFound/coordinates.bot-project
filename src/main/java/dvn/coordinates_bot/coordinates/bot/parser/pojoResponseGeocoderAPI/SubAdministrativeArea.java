package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubAdministrativeArea{

	@JsonProperty("Locality")
	private Locality locality;

	@JsonProperty("SubAdministrativeAreaName")
	private String subAdministrativeAreaName;

	public void setLocality(Locality locality){
		this.locality = locality;
	}

	public Locality getLocality(){
		return locality;
	}

	public void setSubAdministrativeAreaName(String subAdministrativeAreaName){
		this.subAdministrativeAreaName = subAdministrativeAreaName;
	}

	public String getSubAdministrativeAreaName(){
		return subAdministrativeAreaName;
	}

	@Override
 	public String toString(){
		return 
			"SubAdministrativeArea{" + 
			"locality = '" + locality + '\'' + 
			",subAdministrativeAreaName = '" + subAdministrativeAreaName + '\'' + 
			"}";
		}
}