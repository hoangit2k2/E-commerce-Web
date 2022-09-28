package rrs.model.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import rrs.model.utils.FileUpload;

@Service
public class FileService implements FileUpload {
	
	@Autowired private ServletContext context;

	public byte[] getByte(String folder, String name) {
		Path path = this.getSysPath(folder, name);
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Server -> get file
	public String getFilePath(String folder, String name) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_FOLDER).toUriString() +"/"+ folder + "/" +name;
	}

	// Server -> getList file
	public List<String> getFilePaths(String folder) {
		String path = ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_FOLDER).toUriString() + "/" + folder;
		List<String> list = new ArrayList<>();
		for (String name : getFileNames(folder))
			list.add(path + "/" + name);
		return list;
	}

	// System -> Array name
	public String[] getFileNames(String folder) {
		File dir = Paths.get(context.getRealPath(DEFAULT_FOLDER), folder).toFile();
		return dir.list();
	}

	// System -> get path
	public Path getSysPath(String folder, String fileName) {
		File dir = Paths.get(context.getRealPath(DEFAULT_FOLDER), folder).toFile();
		if(!dir.exists()) dir.mkdirs();
		return Paths.get(dir.getAbsolutePath(), fileName);
	}

	// System -> create path
	public Path createSysFolder(String folder) {
		File dir = Paths.get(context.getRealPath(DEFAULT_FOLDER), folder).toFile();
		if (!dir.exists()) dir.mkdirs();
		return Paths.get(dir.getAbsolutePath());
	}

}
