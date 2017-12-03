package cm.gasmyr.it.app.music.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cm.gasmyr.it.app.music.common.AttributeName;
import cm.gasmyr.it.app.music.common.StorageFileNotFoundException;
import cm.gasmyr.it.app.music.service.StorageService;

public class FileUploadController extends ControllerUtils {

	private static final String FILE_UPLOAD = "fileUpload";
	private static final String FILES_FILENAME = "/files/{filename:.+}";
	private static final String REDIRECT_FILES = "redirect:/files";
	private static final String YOU_SUCCESSFULLY_UPLOADED = "You successfully uploaded ";
	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping(PATH_FILES)
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute(AttributeName.FILES,
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return FILE_UPLOAD;
	}

	@GetMapping(FILES_FILENAME)
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
		
	}

	@PostMapping(PATH_FILE)
	public String handleFileUpload(@RequestParam(AttributeName.FILE) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		storageService.store(file);
		redirectAttributes.addFlashAttribute(AttributeName.MESSAGE,
				YOU_SUCCESSFULLY_UPLOADED + file.getOriginalFilename() + "!");

		return REDIRECT_FILES;
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
