package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileUniqueField;
import ru.sber.kvitmoscow.repository.FileUniqueFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileUniqueFieldTo;

import java.util.List;

@Service
public class FileUniqueFieldServiceImpl implements FileUniqueFieldService {
    @Autowired
    private FileUniqueFieldRepository fileUniqueFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Override
    public FileUniqueField save(FileUniqueFieldTo entity) {
        FileUniqueField fileUniqueField = new FileUniqueField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getName(),
                entity.getValue()
        );

        return fileUniqueFieldRepository.save(fileUniqueField);
    }

    @Override
    public FileUniqueField save(FileUniqueField entity) {
        return fileUniqueFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileUniqueFieldRepository.delete(id) != 0;
    }

    @Override
    public FileUniqueField get(int id) {
        return fileUniqueFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileUniqueField> getAll() {
        return fileUniqueFieldRepository.findAll();
    }

    @Override
    public List<FileUniqueField> getAllByUserSettingId(int userSettingId) {
        return fileUniqueFieldRepository.getAllByUserSettingId(userSettingId);
    }
}
