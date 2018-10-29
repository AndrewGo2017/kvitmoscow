package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileSumAddField;
import ru.sber.kvitmoscow.repository.FileSumAddFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileSumAddFieldTo;

import java.util.List;

@Service
public class FileSumAddFieldServiceImpl implements FileSumAddFieldService {

    @Autowired
    private FileSumAddFieldRepository fileSumAddFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Override
    public FileSumAddField save(FileSumAddFieldTo entity) {
        FileSumAddField fileSumU = new FileSumAddField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getName(),
                entity.getValue(),
                entity.getIsBold()
        );

        return fileSumAddFieldRepository.save(fileSumU);
    }

    @Override
    public FileSumAddField save(FileSumAddField entity) {
        return fileSumAddFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileSumAddFieldRepository.delete(id) != 0;
    }

    @Override
    public FileSumAddField get(int id) {
        return fileSumAddFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileSumAddField> getAll() {
        return fileSumAddFieldRepository.findAll();
    }

    @Override
    public List<FileSumAddField> getAllByUserSettingId(int userSettingId) {
        return fileSumAddFieldRepository.getAllByUserSettingId(userSettingId);
    }
}
