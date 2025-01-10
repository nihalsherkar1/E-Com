package com.ecommerce.project.serviceImpl;

import com.ecommerce.project.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // Get File Names of current /Original File
        String originalFileName= file.getOriginalFilename();
        //  Geneate Unique file Name
        String radomId= UUID.randomUUID().toString();

        // mat.jpg ==>  1234  ==> 1234.jpg
        String fileName=radomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath=path + File.separator + fileName;

        // Check path exist and create
        File folder= new File(path);
        if (!folder.exists()){
            folder.mkdirs();
        }

        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        // return file

        return  fileName;


    }
}