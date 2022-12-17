package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoObject{

	@JsonProperty("metaDataProperty")
	private MetaDataProperty metaDataProperty;

	@JsonProperty("boundedBy")
	private BoundedBy boundedBy;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("Point")
	private Point point;

	public void setMetaDataProperty(MetaDataProperty metaDataProperty){
		this.metaDataProperty = metaDataProperty;
	}

	public MetaDataProperty getMetaDataProperty(){
		return metaDataProperty;
	}

	public void setBoundedBy(BoundedBy boundedBy){
		this.boundedBy = boundedBy;
	}

	public BoundedBy getBoundedBy(){
		return boundedBy;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPoint(Point point){
		this.point = point;
	}

	public Point getPoint(){
		return point;
	}

	@Override
 	public String toString(){
		return 
			"GeoObject{" + 
			"metaDataProperty = '" + metaDataProperty + '\'' + 
			",boundedBy = '" + boundedBy + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",point = '" + point + '\'' + 
			"}";
		}
}