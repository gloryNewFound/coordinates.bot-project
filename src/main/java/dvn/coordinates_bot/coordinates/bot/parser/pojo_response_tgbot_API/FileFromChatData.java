package dvn.coordinates_bot.coordinates.bot.parser.pojo_response_tgbot_API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileFromChatData {

	@JsonProperty("file_path")
	private String filePath;

	@JsonProperty("file_unique_id")
	private String fileUniqueId;

	@JsonProperty("file_id")
	private String fileId;

	@JsonProperty("file_size")
	private int fileSize;

	public String getFilePath(){
		return filePath;
	}

	public String getFileUniqueId(){
		return fileUniqueId;
	}

	public String getFileId(){
		return fileId;
	}

	public int getFileSize(){
		return fileSize;
	}
}