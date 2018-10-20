package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvitmoscow.handler.file.FileHandler;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.UserSettingService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/kvit")
public class KvitController {
    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    public String index(Model m) {
        List<UserSetting> userSettings = userSettingService.getAll();
        m.addAttribute("noEdit", "");
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("title", "Формирование квитанций");
        return "kvit";
    }

    @PostMapping
    public void upload(HttpServletResponse response, @RequestParam("file") MultipartFile file, @RequestParam("userSetting") Integer userSetting, Model m) throws Exception {
        ByteArrayOutputStream baos = fileHandler.handle(file, userSetting);
        response.setContentType("application/pdf");
        baos.writeTo(response.getOutputStream());
        response.flushBuffer();
    }


}

