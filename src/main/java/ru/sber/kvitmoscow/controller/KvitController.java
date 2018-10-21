package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvitmoscow.handler.FileHandler;
import ru.sber.kvitmoscow.model.Function;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FunctionService;
import ru.sber.kvitmoscow.service.UserSettingService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/kvit")
public class KvitController {
    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private FunctionService functionService;

    @GetMapping
    public String index(Model m) {
        List<UserSetting> userSettings = userSettingService.getAll();
        List<Function> functions = functionService.getAll();
        m.addAttribute("noEdit", "");
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("functions", functions);
        m.addAttribute("title", "Формирование квитанций");
        return "kvit";
    }

    @PostMapping
    public void upload(HttpServletResponse response,
                       @RequestParam("file") MultipartFile file,
                       @RequestParam("userSetting") Integer userSetting,
                       @RequestParam("function") Integer function,
                       Model m) throws Exception {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("Cp1251");
        String fileName = "";
        if (function == 1){
            fileName = "kvit.pdf";
        } else {
            fileName = "kvit.txt";
        }
        response.setHeader("Content-Disposition", "filename="+ fileName);
        ByteArrayOutputStream baos = fileHandler.handle(file, userSetting, function);
        baos.writeTo(response.getOutputStream());
    }

    public ResponseEntity f() throws IOException {
        List<String> lines = Arrays.asList("The first line", "The second line");
        Path file = Paths.get("the-file-name.txt");
        Files.write(file, lines, Charset.forName("UTF-8"));

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"").body(file);
    }
}

