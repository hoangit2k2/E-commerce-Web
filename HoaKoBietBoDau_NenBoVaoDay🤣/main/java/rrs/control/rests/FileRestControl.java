package rrs.control.rests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rrs.model.utils.FileUpload;

@CrossOrigin("*")
@RestController
@RequestMapping({"/rest/"})
public class FileRestControl {

	// @formatter:off
	@Autowired private FileUpload service;

	// get file
	@GetMapping("file/{folder}/{file}")
	public byte[] readFile(@PathVariable String folder, @PathVariable String file) {
		return service.getByte(folder, file);
	}

	// get file's paths
	@GetMapping("dir/**")
	public ResponseEntity<List<String>> getList(HttpServletRequest req) {
		StringBuffer pathIO = req.getRequestURL(); // cut director path
		String path = pathIO.substring(pathIO.lastIndexOf("dir/")+4);
		return ResponseEntity.ok(service.getFilePaths(path));
	}

	@PostMapping("dir/**") // save file
	public ResponseEntity<List<String>> save(HttpServletRequest req,
			@RequestParam(required = false) MultipartFile[] files) throws Exception{
		StringBuffer pathIO = req.getRequestURL(); // cut director path
		String path = pathIO.substring(pathIO.lastIndexOf("dir/")+4);
		List<String> list = new LinkedList<>();
		
		if(files != null) for (MultipartFile file : files) {
			String name = file.getOriginalFilename(); // Get file name
			Path sysPath = service.getSysPath(path, name);
			try {
				// save file if file doesn't exists
				if(!sysPath.toFile().exists()) file.transferTo(sysPath);
				else System.err.println("File name's "+file.getOriginalFilename()+" already exists, cannot be saved.");
				list.add(service.getFilePath(path, name));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok(list);
	}

	@DeleteMapping("dir/**")
	public ResponseEntity<Void> delete(HttpServletRequest req) {
		StringBuffer pathIO = req.getRequestURL(); // cut director path
		String path = pathIO.substring(pathIO.lastIndexOf("dir/")+4, pathIO.lastIndexOf("/"));
		String fileName = pathIO.substring(pathIO.lastIndexOf("/")+1);
		
		File file = service.getSysPath(path, fileName).toFile();
		return file.delete() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
	 
	// @formatter:on

}
