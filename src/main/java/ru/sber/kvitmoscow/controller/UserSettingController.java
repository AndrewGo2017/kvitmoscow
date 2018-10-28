package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.Authorization;
import ru.sber.kvitmoscow.model.*;
import ru.sber.kvitmoscow.service.*;
import ru.sber.kvitmoscow.to.UserSettingTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/setting")
public class UserSettingController {
    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileTypeService fileTypeService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private FileTemplateService fileTemplateService;

    @Autowired
    private SheetPositionService sheetPositionService;

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        m.addAttribute("title", "Настройки пользователя");
        if (idParam != null){
            User user = userService.get(idParam);
            m.addAttribute("contractName", user.getName());
            m.addAttribute("tableCondition", "/user/" + idParam);
        }
        return "common";
    }

    @GetMapping("/all/user/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        List<UserSetting> registerIns = userSettingService.getAllByUserId(id);
        m.addAttribute("userSettings", registerIns);
        return "fragments/tables :: userSettingList";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        List<UserSetting> userSettings = userSettingService.getAllByUserId(Authorization.id());
        m.addAttribute("userSettings", userSettings);
        return "fragments/tables :: userSettingList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        UserSetting userSetting = userSettingService.get(id);
        List<FileType> fileTypes = fileTypeService.getAll();
        List<Template> templates = templateService.getAll();
        List<SheetPosition> sheetPositions = sheetPositionService.getAll();
        List<FileTemplate> fileTemplates = fileTemplateService.getAll();
        List<User> users = userService.getAll();
        List<Integer> fontSizes = new ArrayList<>(Arrays.asList(-4,-3,-2,-1,0,1,2,3,4));
        m.addAttribute("users",users);
        m.addAttribute("fileTypes", fileTypes);
        m.addAttribute("templates", templates);
        m.addAttribute("userSetting", userSetting);
        m.addAttribute("sheetPositions", sheetPositions);
        m.addAttribute("fileTemplates", fileTemplates);
        m.addAttribute("fontSizes", fontSizes);

        return "fragments/dialogs :: userSettingDialog";
    }

    @PostMapping
    public String save(UserSettingTo entity) {
        entity.setUser(Authorization.id());
        userSettingService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userSettingService.delete(id);
        return "common";
    }

    @PostMapping("/setting/{sid}/template/{tid}")
    public String fillDictionariesWithDefaultData(@PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid){
        userSettingService.fillDictionariesWithDefaultData(sid, tid);

        return "common";
    }
}
