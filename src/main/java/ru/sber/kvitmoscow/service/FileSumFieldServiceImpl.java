package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileSumField;
import ru.sber.kvitmoscow.repository.FileSumFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileSumFieldTo;

import java.util.List;

@Service
public class FileSumFieldServiceImpl implements FileSumFieldService {
    @Autowired
    private FileSumFieldRepository fileSumFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Override
    public FileSumField save(FileSumFieldTo entity) {
        FileSumField fileSumField = new FileSumField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getName(),
                entity.getValue(),
                entity.getIsBold()
        );
        return fileSumFieldRepository.save(fileSumField);
    }

    @Override
    public FileSumField save(FileSumField entity) {
        return fileSumFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileSumFieldRepository.delete(id) != 0;
    }

    @Override
    public FileSumField get(int id) {
        return fileSumFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileSumField> getAll() {
        return fileSumFieldRepository.findAll();
    }

    @Override
    public List<FileSumField> getAllByUserSettingId(int userSettingId) {
        return fileSumFieldRepository.getAllByUserSettingId(userSettingId);
    }
}
