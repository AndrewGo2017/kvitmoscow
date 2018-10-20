package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileType;
import ru.sber.kvitmoscow.repository.FileTypeRepository;

import java.util.List;

@Service
public class FileTypeServiceImpl implements FileTypeService {
    @Autowired
     private FileTypeRepository fileTypeRepository;

    @Override
    public FileType save(FileType entity) {
        return fileTypeRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileTypeRepository.delete(id)!=0;
    }

    @Override
    public FileType get(int id) {
        return fileTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileType> getAll() {
        return fileTypeRepository.findAll();
    }
}
