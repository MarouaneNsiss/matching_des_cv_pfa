package com.example.matching_des_cv_pfa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.Locale;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path imageStorageLocation;
    private final Path cvStorageLocation;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        // Create separate paths for images and CVs
        this.imageStorageLocation = Paths.get(uploadDir, "images").toAbsolutePath().normalize();
        this.cvStorageLocation = Paths.get(uploadDir, "cvs").toAbsolutePath().normalize();

        try {
            // Ensure directories exist
            Files.createDirectories(this.imageStorageLocation);
            Files.createDirectories(this.cvStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directories for uploaded files.", ex);
        }
    }

    // Method to create a safe filename like fullname_id.extension
    private String createSafeFileName(String fullName, Long id) {
        // Normalize the full name (remove accents, convert to lowercase)
        String normalizedName = normalizeFileName(fullName);

        // Replace spaces with underscores
        normalizedName = normalizedName.replace(" ", "_");

        // Create filename format: firstname_lastname_id
        return String.format("%s_%d", normalizedName, id);
    }

    // Utility method to normalize filename
    private String normalizeFileName(String input) {
        // Remove accents
        String normalized = Normalizer
                .normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase(Locale.ENGLISH);

        // Remove any non-alphanumeric characters except underscores
        return normalized.replaceAll("[^a-z0-9_]", "");
    }
    @Override
    public String storeImage(MultipartFile file, String fullName, Long Id) {
        return storeFile(file, fullName, Id, "image", imageStorageLocation);
    }
    @Override
    public String storeCv(MultipartFile file, String fullName, Long Id) {
        return storeFile(file, fullName, Id, "cv", cvStorageLocation);
    }
    private String storeFile(MultipartFile file, String fullName, Long Id, String fileType, Path storageLocation) {
        // Normalize file name
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null) {
            fileExtension = originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";
        }

        // Generate a unique filename
        String fileName = createSafeFileName(fullName, Id) + "_" + fileType + fileExtension;

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = storageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    @Override
    public void deleteFile(String fileName, boolean isImage) {
        try {
            Path storageLocation = isImage ? imageStorageLocation : cvStorageLocation;
            Path filePath = storageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileName, ex);
        }
    }
    @Override
    public Path getFilePath(String fileName, boolean isImage) {
        Path storageLocation = isImage ? imageStorageLocation : cvStorageLocation;
        return storageLocation.resolve(fileName).normalize();
    }
    @Override
    public Resource loadFileAsResource(String fileName, Path storageLocation) {
        try {
            Path filePath = storageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}
