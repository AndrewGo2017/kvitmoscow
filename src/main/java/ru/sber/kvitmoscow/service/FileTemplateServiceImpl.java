package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileTemplate;
import ru.sber.kvitmoscow.repository.FileTemplateRepository;

import java.util.List;

@Service
public class FileTemplateServiceImpl implements FileTemplateService {
    @Autowired
    private FileTemplateRepository fileTemplateRepository;

    @Override
    public FileTemplate save(FileTemplate entity) {
        return fileTemplateRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileTemplateRepository.delete(id) != 0;
    }

    @Override
    public FileTemplate get(int id) {
        return fileTemplateRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileTemplate> getAll() {
        return fileTemplateRepository.findAll();
    }
}
