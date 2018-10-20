package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.FileCounterField;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FileCounterFieldService;
import ru.sber.kvitmoscow.service.UserSettingService;
import ru.sber.kvitmoscow.to.FileCounterFieldTo;

import java.util.List;

@Controller
@RequestMapping("/cfield")
public class FileCounterFieldController {
    @Autowired
    private FileCounterFieldService fileCounterFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Поля счетчики");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileCounterField> fileCounterFields = fileCounterFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileCounterFields", fileCounterFields);
        return "fragments/tables :: fileCounterFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileCounterField> fileCounterFields = fileCounterFieldService.getAll();
        m.addAttribute("fileCounterFields", fileCounterFields);
        return "fragments/tables :: fileCounterFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileCounterField fileCounterField = fileCounterFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileCounterField", fileCounterField);
        return "fragments/dialogs :: fileCounterFieldDialog";
    }

    @PostMapping
    public String save(FileCounterFieldTo entity) {
        fileCounterFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileCounterFieldService.delete(id);
        return "common";
    }
}
