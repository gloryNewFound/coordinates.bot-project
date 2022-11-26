package dvn.coordinates_bot.coordinates.bot.parser.pojoResponseTGBotAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileFromChat{

	@JsonProperty("result")
	private Result result;

	@JsonProperty("ok")
	private boolean ok;

	public Result getResult(){
		return result;
	}

	public boolean isOk(){
		return ok;
	}


}