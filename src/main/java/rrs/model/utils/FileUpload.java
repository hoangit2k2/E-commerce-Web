package rrs.model.utils;

import java.nio.file.Path;
import java.util.List;

import rrs.model.services.FileService;

/**
 * {@link FileService} implements this code ༼ つ ◕_◕ ༽つ
 */
public interface FileUpload {
	public static final String DEFAULT_FOLDER = "/data";
	
	/**
	 * @param folder containing files 
	 * @param name of file to read
	 * 
	 * @return bytes of file
	 */
	public byte[] getByte(String folder, String name);

	/**
	 * @param folder containing files 
	 * @param name of file to read
	 * 
	 * @return {@link String} is href link to file
	 */
	public String getFilePath(String folder, String name);

	/**
	 * @param folder containing files
	 * @return {@link List<@link String>} all paths with any files in parameter folder
	 */
	public List<String> getFilePaths(String folder);

	/**
	 * @param folder containing files
	 * @return all files're name
	 */
	public String[] getFileNames(String folder);

	/**
	 * @param folder containing files
	 * @param fileName is file's name (●'◡'●)
	 * @return {@link Path} absolute link to file of system
	 */
	public Path getSysPath(String folder, String fileName);

	/**
	 * @param folder is name to create new folder in the system
	 * @return {@link Path} has been created
	 */
	public Path createSysFolder(String folder);
}
