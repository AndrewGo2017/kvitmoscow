package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.FileMainField;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FileMainFieldService;
import ru.sber.kvitmoscow.service.UserSettingService;
import ru.sber.kvitmoscow.to.FileMainFieldTo;

import java.util.List;

@Controller
@RequestMapping("/mfield")
public class FileMainFieldController {
    @Autowired
    private FileMainFieldService fileMainFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Основные поля");
        m.addAttribute("hideAddButton", "");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileMainField> fileMainFields = fileMainFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileMainFields", fileMainFields);
        return "fragments/tables :: fileMainFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileMainField> fileMainFields = fileMainFieldService.getAll();
        m.addAttribute("fileMainFields", fileMainFields);
        return "fragments/tables :: fileMainFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileMainField fileMainField = fileMainFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileMainField", fileMainField);
        return "fragments/dialogs :: fileMainFieldDialog";
    }

    @PostMapping
    public String save(FileMainFieldTo entity) {
        fileMainFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileMainFieldService.delete(id);
        return "common";
    }
}