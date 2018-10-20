package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileCounterField;
import ru.sber.kvitmoscow.repository.FileCounterFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileCounterFieldTo;

import java.util.List;

@Service
public class FileCounterFieldServiceImpl implements FileCounterFieldService {
    @Autowired
    private FileCounterFieldRepository fileCounterFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;


    @Override
    public FileCounterField save(FileCounterFieldTo entity) {
        FileCounterField fileCounterField = new FileCounterField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getName(),
                entity.getMesure(),
                entity.getTariff(),
                entity.getCurrent(),
                entity.getPrevious(),
                entity.getConsumption()
        );
        return fileCounterFieldRepository.save(fileCounterField);
    }

    @Override
    public FileCounterField save(FileCounterField entity) {
        return fileCounterFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileCounterFieldRepository.delete(id) != 0;
    }

    @Override
    public FileCounterField get(int id) {
        return fileCounterFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileCounterField> getAll() {
        return fileCounterFieldRepository.findAll();
    }

    @Override
    public List<FileCounterField> getAllByUserSettingId(int userSettingId) {
        return fileCounterFieldRepository.getAllByUserSettingId(userSettingId);
    }
}
