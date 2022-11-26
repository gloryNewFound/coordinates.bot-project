package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentsItem{

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("name")
	private String name;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"ComponentsItem{" + 
			"kind = '" + kind + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}