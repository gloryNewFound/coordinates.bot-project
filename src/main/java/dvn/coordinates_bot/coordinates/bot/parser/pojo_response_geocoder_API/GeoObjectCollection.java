package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeoObjectCollection{

	@JsonProperty("metaDataProperty")
	private MetaDataProperty metaDataProperty;

	@JsonProperty("featureMember")
	private List<FeatureMemberItem> featureMember;

	public void setMetaDataProperty(MetaDataProperty metaDataProperty){
		this.metaDataProperty = metaDataProperty;
	}

	public MetaDataProperty getMetaDataProperty(){
		return metaDataProperty;
	}

	public void setFeatureMember(List<FeatureMemberItem> featureMember){
		this.featureMember = featureMember;
	}

	public List<FeatureMemberItem> getFeatureMember(){
		return featureMember;
	}

	@Override
 	public String toString(){
		return 
			"GeoObjectCollection{" + 
			"metaDataProperty = '" + metaDataProperty + '\'' + 
			",featureMember = '" + featureMember + '\'' + 
			"}";
		}
}