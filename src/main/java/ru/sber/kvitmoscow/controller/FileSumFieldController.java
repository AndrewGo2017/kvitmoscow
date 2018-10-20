package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.FileSumField;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FileSumFieldService;
import ru.sber.kvitmoscow.service.UserSettingService;
import ru.sber.kvitmoscow.to.FileSumFieldTo;

import java.util.List;

@Controller
@RequestMapping("/sfield")
public class FileSumFieldController {
    @Autowired
    private FileSumFieldService fileSumFieldService;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Поля суммы");
        if (idParam != null){
            UserSetting userSetting = userSettingService.get(idParam);
            m.addAttribute("contractName", userSetting.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<FileSumField> fileSumFields = fileSumFieldService.getAllByUserSettingId(id);
        m.addAttribute("fileSumFields", fileSumFields);
        return "fragments/tables :: fileSumFieldList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<FileSumField> fileSumFields = fileSumFieldService.getAll();
        m.addAttribute("fileSumFields", fileSumFields);
        return "fragments/tables :: fileSumFieldList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        FileSumField fileSumField = fileSumFieldService.get(id);
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("fileSumField", fileSumField);
        return "fragments/dialogs :: fileSumFieldDialog";
    }

    @PostMapping
    public String save(FileSumFieldTo entity) {
        fileSumFieldService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        fileSumFieldService.delete(id);
        return "common";
    }
}
