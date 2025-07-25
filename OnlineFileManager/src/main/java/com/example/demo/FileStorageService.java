package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

//UPLOADING
	//location where to save
    private static final String STORAGE_DIRECTORY = "C:\\Users\\vivek.chauhan\\Downloads\\Uploaded_Files_From_SpringBoot";

    public void saveFile(MultipartFile fileToSave) {
    	//to check the file is not null
        if (fileToSave == null) {
            throw new NullPointerException("fileToSave is null");
        }

        //File.separator : this will use required seprator for linux/windows/mac depeding upon OS
        //fileToSave.getOriginalFilename() : save file with original file name.
        var targetFile = new File(STORAGE_DIRECTORY + File.separator + fileToSave.getOriginalFilename());
        // if (!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
        
        //this will check the parent is original folder or not / avoid path/sql injection.
        if (!targetFile.getParentFile().toPath().normalize().equals(new File(STORAGE_DIRECTORY).toPath().normalize())) {
            throw new SecurityException("Unsupported filename!");
        }

        try {
            Files.copy(fileToSave.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file", e);
        } 
    }
    
//DOWNLOADING
    public File getDownloadFile(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            throw new NullPointerException("fileToSave is null");
        }
    	var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + fileName);
        if (!fileToDownload.getParentFile().toPath().normalize().equals(new File(STORAGE_DIRECTORY).toPath().normalize())) {
            throw new SecurityException("Unsupported filename!");
        }
        
        if (!fileToDownload.exists()) {
            throw new FileNotFoundException("No file Named : "+fileName);
        }
        
        return fileToDownload;
}
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
