package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeocoderResponseMetaData{

	@JsonProperty("request")
	private String request;

	@JsonProperty("found")
	private String found;

	@JsonProperty("boundedBy")
	private BoundedBy boundedBy;

	@JsonProperty("results")
	private String results;

	public void setRequest(String request){
		this.request = request;
	}

	public String getRequest(){
		return request;
	}

	public void setFound(String found){
		this.found = found;
	}

	public String getFound(){
		return found;
	}

	public void setBoundedBy(BoundedBy boundedBy){
		this.boundedBy = boundedBy;
	}

	public BoundedBy getBoundedBy(){
		return boundedBy;
	}

	public void setResults(String results){
		this.results = results;
	}

	public String getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"GeocoderResponseMetaData{" + 
			"request = '" + request + '\'' + 
			",found = '" + found + '\'' + 
			",boundedBy = '" + boundedBy + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}