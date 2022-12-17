package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdministrativeArea{

	@JsonProperty("AdministrativeAreaName")
	private String administrativeAreaName;

	@JsonProperty("SubAdministrativeArea")
	private SubAdministrativeArea subAdministrativeArea;

	public void setAdministrativeAreaName(String administrativeAreaName){
		this.administrativeAreaName = administrativeAreaName;
	}

	public String getAdministrativeAreaName(){
		return administrativeAreaName;
	}

	public void setSubAdministrativeArea(SubAdministrativeArea subAdministrativeArea){
		this.subAdministrativeArea = subAdministrativeArea;
	}

	public SubAdministrativeArea getSubAdministrativeArea(){
		return subAdministrativeArea;
	}

	@Override
 	public String toString(){
		return 
			"AdministrativeArea{" + 
			"administrativeAreaName = '" + administrativeAreaName + '\'' + 
			",subAdministrativeArea = '" + subAdministrativeArea + '\'' + 
			"}";
		}
}