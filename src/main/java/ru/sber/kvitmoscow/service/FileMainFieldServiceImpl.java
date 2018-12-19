package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.FileMainField;
import ru.sber.kvitmoscow.repository.FileMainFieldRepository;
import ru.sber.kvitmoscow.repository.UserSettingRepository;
import ru.sber.kvitmoscow.to.FileMainFieldTo;

import java.util.List;

@Service
public class FileMainFieldServiceImpl implements FileMainFieldService {
    @Autowired
    private FileMainFieldRepository fileMainFieldRepository;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Override
    public FileMainField save(FileMainFieldTo entity) {
        FileMainField fileMainField = new FileMainField(
                entity.getId(),
                userSettingRepository.getOne(entity.getUserSetting()),
                entity.getLs(),
                entity.getAdr(),
                entity.getFio(),
                entity.getPeriod(),
                entity.getSum(),
                entity.getKbk(),
                entity.getOktmo(),
                entity.getContract(),
                entity.getPurpose(),
                entity.getLsName(),
                entity.getAdrName(),
                entity.getFioName(),
                entity.getPeriodName(),
                entity.getSumName(),
                entity.getKbkName(),
                entity.getOktmoName(),
                entity.getContractName(),
                entity.getPurposeName()
        );

        return fileMainFieldRepository.save(fileMainField);
    }

    @Override
    public FileMainField save(FileMainField entity) {
        return fileMainFieldRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return fileMainFieldRepository.delete(id) != 0;
    }

    @Override
    public FileMainField get(int id) {
        return fileMainFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileMainField> getAll() {
        return fileMainFieldRepository.findAll();
    }

    @Override
    public List<FileMainField> getAllByUserSettingId(int userSettingId) {
        return fileMainFieldRepository.getAllByUserSettingId(userSettingId);
    }

    @Override
    public void deleteAllBySettingId(int settingId) {
        fileMainFieldRepository.deleteAllBySettingId(settingId);
    }
}
