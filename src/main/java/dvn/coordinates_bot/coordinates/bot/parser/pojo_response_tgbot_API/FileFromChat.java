package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_tgbot_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileFromChat{

	@JsonProperty("result")
	private FileFromChatData fileFromChatData;

	@JsonProperty("ok")
	private boolean ok;

	public FileFromChatData getResult(){
		return fileFromChatData;
	}

	public boolean isOk(){
		return ok;
	}
}