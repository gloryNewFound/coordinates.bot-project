package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Locality{

	@JsonProperty("LocalityName")
	private String localityName;

	@JsonProperty("DependentLocality")
	private DependentLocality dependentLocality;

	public void setLocalityName(String localityName){
		this.localityName = localityName;
	}

	public String getLocalityName(){
		return localityName;
	}

	public void setDependentLocality(DependentLocality dependentLocality){
		this.dependentLocality = dependentLocality;
	}

	public DependentLocality getDependentLocality(){
		return dependentLocality;
	}

	@Override
 	public String toString(){
		return 
			"Locality{" + 
			"localityName = '" + localityName + '\'' + 
			",dependentLocality = '" + dependentLocality + '\'' + 
			"}";
		}
}