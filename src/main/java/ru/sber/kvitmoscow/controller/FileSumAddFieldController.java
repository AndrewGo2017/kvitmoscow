package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.FileSumAddField;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FileSumAddFieldService;
import ru.sber.kvitmoscow.service.UserSettingService;

import java.util.List;

@Controller
@RequestMapping("/safield")
public class FileSumAddFieldController {
    @Autowired
    private FileSumAddFieldService fileSumAddFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Дополнительные поля для таблицы сумм");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileSumAddField> fileSumAddFields = fileSumAddFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileSumAddFields", fileSumAddFields);
        return "fragments/tables :: fileSumAddFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileSumAddField> fileSumAddFields = fileSumAddFieldService.getAll();
        m.addAttribute("fileSumAddFields", fileSumAddFields);
        return "fragments/tables :: fileSumAddFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileSumAddField fileSumAddField = fileSumAddFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileSumAddField", fileSumAddField);
        return "fragments/dialogs :: fileSumAddFieldDialog";
    }

    @PostMapping
    public String save(FileSumAddField entity) {
        fileSumAddFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileSumAddFieldService.delete(id);
        return "common";
    }
}
