package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.FileCounterAddField;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FileCounterAddFieldService;
import ru.sber.kvitmoscow.service.UserSettingService;

import java.util.List;

@Controller
@RequestMapping("/cafield")
public class FileCounterAddFieldController {
    @Autowired
    private FileCounterAddFieldService fileCounterAddFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Дополнительные поля для таблицы счетчиков");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileCounterAddField> fileCounterAddFields = fileCounterAddFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileCounterAddFields", fileCounterAddFields);
        return "fragments/tables :: fileCounterAddFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileCounterAddField> fileCounterAddFields = fileCounterAddFieldService.getAll();
        m.addAttribute("fileCounterAddFields", fileCounterAddFields);
        return "fragments/tables :: fileCounterAddFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileCounterAddField fileCounterAddField = fileCounterAddFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileCounterAddField", fileCounterAddField);
        return "fragments/dialogs :: fileCounterAddFieldDialog";
    }

    @PostMapping
    public String save(FileCounterAddField entity) {
        fileCounterAddFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileCounterAddFieldService.delete(id);
        return "common";
    }
}
