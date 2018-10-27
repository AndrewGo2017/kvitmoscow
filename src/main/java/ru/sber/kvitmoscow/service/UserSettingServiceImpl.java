package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.repository.*;
import ru.sber.kvitmoscow.to.FileMainFieldTo;
import ru.sber.kvitmoscow.to.UserSettingTo;

import java.util.List;

@Service
public class UserSettingServiceImpl implements UserSettingService {

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private FileMainFieldService fileMainFieldService;

    @Autowired
    private FileTemplateRepository fileTemplateRepository;

    @Autowired
    private SheetPositionRepository sheetPositionRepository;

    @Override
    public UserSetting save(UserSettingTo entity) {
        UserSetting userSetting = new UserSetting(
                entity.getId(),
                userRepository.getOne(entity.getUser()),
                fileTypeRepository.getOne(entity.getFileType()),
                templateRepository.getOne(entity.getTemplate()),
                fileTemplateRepository.getOne(entity.getFileTemplate()),
                sheetPositionRepository.getOne(entity.getSheetPosition()),
                entity.getName(),
                entity.getFileMask(),
                entity.getQrAddInfo(),
                entity.getBillQuantity(),
                entity.getOrgName(),
                entity.getOrgInn(),
                entity.getOrgKpp(),
                entity.getOrgPayAcc(),
                entity.getOrgBank(),
                entity.getOrgBic(),
                entity.getOrgCorAcc(),
                entity.getOrgAddInfo()
        );

        UserSetting newUserSetting = userSettingRepository.save(userSetting);

        if (entity.isNew()){
            FileMainFieldTo f = FileMainFieldTo.getDefaultMainFieldTo(newUserSetting.getId());
            fileMainFieldService.save(f);
        }

        return userSettingRepository.save(userSetting);
    }

    @Override
    public UserSetting save(UserSetting entity) {
        UserSetting userSetting = userSettingRepository.save(entity);
        return userSetting;
    }

    @Override
    public boolean delete(int id) {
        return userSettingRepository.delete(id) != 0;
    }

    @Override
    public UserSetting get(int id) {
        return userSettingRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserSetting> getAll() {
        return userSettingRepository.findAll();
    }

    @Override
    public List<UserSetting> getAllByUserId(int userId) {
        return userSettingRepository.getAllByUserId(userId);
    }

    @Override
    public void fillDictionariesWithDefaultData(int userSettingId, int templateId) {
        fileMainFieldService.deleteAllBySettingId(userSettingId);
        fileMainFieldService.save(FileMainFieldTo.getDefaultMainFieldToBySettingId(userSettingId, templateId));
    }
}