package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DependentLocality{

	@JsonProperty("DependentLocalityName")
	private String dependentLocalityName;

	@JsonProperty("Premise")
	private Premise premise;

	public void setDependentLocalityName(String dependentLocalityName){
		this.dependentLocalityName = dependentLocalityName;
	}

	public String getDependentLocalityName(){
		return dependentLocalityName;
	}

	public void setPremise(Premise premise){
		this.premise = premise;
	}

	public Premise getPremise(){
		return premise;
	}

	@Override
 	public String toString(){
		return 
			"DependentLocality{" + 
			"dependentLocalityName = '" + dependentLocalityName + '\'' + 
			",premise = '" + premise + '\'' + 
			"}";
		}
}