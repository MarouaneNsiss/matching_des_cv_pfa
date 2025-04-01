package com.example.matching_des_cv_pfa.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {

    String storeImage(MultipartFile file, String fullName, Long Id);

    String storeCv(MultipartFile file, String fullName, Long Id);

    void deleteFile(String fileName, boolean isImage);
    Path getFilePath(String fileName, boolean isImage);


    Resource loadFileAsResource(String fileName, Path storageLocation);
}
