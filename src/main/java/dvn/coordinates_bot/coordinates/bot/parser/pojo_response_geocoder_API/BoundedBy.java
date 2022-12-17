package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoundedBy{

	@JsonProperty("Envelope")
	private Envelope envelope;

	public void setEnvelope(Envelope envelope){
		this.envelope = envelope;
	}

	public Envelope getEnvelope(){
		return envelope;
	}

	@Override
 	public String toString(){
		return 
			"BoundedBy{" + 
			"envelope = '" + envelope + '\'' + 
			"}";
		}
}