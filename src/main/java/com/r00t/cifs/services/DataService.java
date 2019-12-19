package com.r00t.cifs.services;

import com.r00t.cifs.configs.FileStorageProperties;
import com.r00t.cifs.models.DataModel;
import com.r00t.cifs.repositories.DataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private DataModelRepository repository;
    private final Path fileStorageLocation;

    public DataService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DataModel> getAllImageAndData() {
//        List<DataModel> dataList = new ArrayList<>();
//        for (int x = 0; x < 9; x++) {
//            DataModel temp = new DataModel();
//            temp.setRecordDate("D" + x);
//            temp.setRecordTime("T" + x);
//            temp.setImageDataName("chef_gallery_3-300x300");
//            temp.setRawDataName("D" + x);
//            dataList.add(temp);
//        }
//        return dataList;
        return repository.findAll();
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "";

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DataModel insert(DataModel dataModel) {
        return repository.insert(dataModel);
    }
}
