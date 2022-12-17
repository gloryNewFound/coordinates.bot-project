package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeocoderMetaData{

	@JsonProperty("Address")
	private Address address;

	@JsonProperty("AddressDetails")
	private AddressDetails addressDetails;

	@JsonProperty("kind")
	private String kind;

	@JsonProperty("precision")
	private String precision;

	@JsonProperty("text")
	private String text;

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setAddressDetails(AddressDetails addressDetails){
		this.addressDetails = addressDetails;
	}

	public AddressDetails getAddressDetails(){
		return addressDetails;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setPrecision(String precision){
		this.precision = precision;
	}

	public String getPrecision(){
		return precision;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"GeocoderMetaData{" + 
			"address = '" + address + '\'' + 
			",addressDetails = '" + addressDetails + '\'' + 
			",kind = '" + kind + '\'' + 
			",precision = '" + precision + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}