package com.kangyonggan.base.util;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author kangyonggan
 */
public class FileUpload {

    public static String upload(String fileDir, String uploadDir, MultipartFile file, String prefix) throws FileUploadException {
        String fileName = "";
        if (file.getSize() != 0) {
            try {
                fileName = uploadDir + extractFilePath(file, prefix);
                File desc = getAbsolutePath(fileDir + fileName);
                file.transferTo(desc);
            } catch (Exception e) {
                throw new FileUploadException("File Upload Exception", e);
            }
        }
        return fileName;
    }

    private static File getAbsolutePath(String filename) throws IOException {
        File desc = new File(filename);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    private static String extractFilePath(MultipartFile file, String prefix) {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        return extractFilePathByExtension(fileExt, prefix);
    }

    private static String extractFilePathByExtension(String extension, String prefix) {
        StringBuilder tempPath = new StringBuilder();
        tempPath.append(prefix).append(UUID.randomUUID().toString().replaceAll("-", ""));
        tempPath.append(".").append(extension);

        return tempPath.toString();
    }
}
