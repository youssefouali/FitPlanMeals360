package com.example.mealplanner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    public void uploadFile(MultipartFile file, String prefix);



}
