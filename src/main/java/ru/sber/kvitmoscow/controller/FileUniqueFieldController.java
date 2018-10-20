package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.*;
import ru.sber.kvitmoscow.service.*;
import ru.sber.kvitmoscow.to.FileUniqueFieldTo;

import java.util.List;

@Controller
@RequestMapping("/ufield")
public class FileUniqueFieldController {
    @Autowired
    private FileUniqueFieldService fileUniqueFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Уникальные поля");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileUniqueField> fileUniqueFields = fileUniqueFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileUniqueFields", fileUniqueFields);
        return "fragments/tables :: fileUniqueFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileUniqueField> fileUniqueFields = fileUniqueFieldService.getAll();
        m.addAttribute("fileUniqueFields", fileUniqueFields);
        return "fragments/tables :: fileUniqueFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileUniqueField fileUniqueField = fileUniqueFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileUniqueField", fileUniqueField);
        return "fragments/dialogs :: fileUniqueFieldDialog";
    }

    @PostMapping
    public String save(FileUniqueFieldTo entity) {
        fileUniqueFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileUniqueFieldService.delete(id);
        return "common";
    }
}