package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import java.nio.file.Files;
import org.springframework.core.io.Resource;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileManagerController {

//UPLOADING
//localhost:8080/upload-file
    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());
    
    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileStorageService.saveFile(file);
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception during upload", e);
            return false;
        } finally {
            System.out.print("finally!!!");
        }
    }
    
//DOWNLOADING
//localhost:8080/download?fileName=Vivek_Chauhan_Resume.pdf
    @GetMapping("/download")
     public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String filename){
    	log.log(Level.INFO, "[SLOW] Download with /download");
    	try {
			var fileToDownload = fileStorageService.getDownloadFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+filename+"\"")
					.contentLength(fileToDownload.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(Files.newInputStream(fileToDownload.toPath())));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}    
}

//DOWNLOADING: MULTIPART APPROCH (MAX 32 PARTS)
//localhost:8080/download-faster?fileName=CursorUserSetup-x64-0.50.5.exe
//can we visulisied using INTERNET DOWNLOAD MANAGER(IDM)
    @GetMapping("/download-faster")
    public ResponseEntity<Resource> downloadFileFaster(@RequestParam("fileName") String filename){
    	log.log(Level.INFO, "[FASTER] Download with /download-faster");
   	try {
			var fileToDownload = fileStorageService.getDownloadFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+filename+"\"")
					.contentLength(fileToDownload.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(fileToDownload));//only this line changes
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}    
    }
    
    
    
    
    
}







































