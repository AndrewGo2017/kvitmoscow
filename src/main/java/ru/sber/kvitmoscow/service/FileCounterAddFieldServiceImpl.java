package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileCounterAddField;
import ru.sber.kvitmoscow.repository.FileCounterAddFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileCounterAddFieldTo;

import java.util.List;

@Service
public class FileCounterAddFieldServiceImpl implements FileCounterAddFieldService {

    @Autowired
    private FileCounterAddFieldRepository fileCounterAddFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Override
    public FileCounterAddField save(FileCounterAddFieldTo entity) {
        FileCounterAddField fileCounterU = new FileCounterAddField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getName(),
                entity.getValue()
        );

        return fileCounterAddFieldRepository.save(fileCounterU);
    }

    @Override
    public FileCounterAddField save(FileCounterAddField entity) {
        return fileCounterAddFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileCounterAddFieldRepository.delete(id) != 0;
    }

    @Override
    public FileCounterAddField get(int id) {
        return fileCounterAddFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileCounterAddField> getAll() {
        return fileCounterAddFieldRepository.findAll();
    }

    @Override
    public List<FileCounterAddField> getAllByUserSettingId(int userSettingId) {
        return fileCounterAddFieldRepository.getAllByUserSettingId(userSettingId);
    }
}
