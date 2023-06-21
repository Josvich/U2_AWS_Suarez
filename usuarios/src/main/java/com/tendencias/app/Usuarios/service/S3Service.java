package com.tendencias.app.Usuarios.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.tendencias.app.Usuarios.model.Asset;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class S3Service { 
    private final static String BUCKET="demonspringboots3bucket";
     @Autowired
    private AmazonS3Client s3Client;
   
 public String putObject(MultipartFile multipart){
        String extension = StringUtils.getFilenameExtension(multipart.getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extension);

        ObjectMetadata objectmetadata = new ObjectMetadata();
        objectmetadata.setContentType(multipart.getContentType());

        try{
           PutObjectRequest putobjectrequest = new PutObjectRequest(BUCKET, key, multipart.getInputStream(), objectmetadata)
                   .withCannedAcl(CannedAccessControlList.PublicRead);
           
           s3Client.putObject(putobjectrequest);
           return key;
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Asset getObject(String key){
        S3Object s3object = s3Client.getObject(BUCKET, key);
        ObjectMetadata metadata = s3object.getObjectMetadata();
        
        try{
            S3ObjectInputStream inputstream = s3object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputstream);
        
            return new Asset(bytes, metadata.getContentType());
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void deleteObject(String key){
        s3Client.deleteObject(BUCKET, key);
    }

    public String getObjectUrl(String key){
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }
  
  
}
