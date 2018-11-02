package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvitmoscow.Authorization;
import ru.sber.kvitmoscow.example.ExampleHandler;
import ru.sber.kvitmoscow.handler.FileHandler;
import ru.sber.kvitmoscow.model.Function;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.FunctionService;
import ru.sber.kvitmoscow.service.UserSettingService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/func")
public class FuncController {
    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    public ExampleHandler exampleHandler;

    @GetMapping
    public String index(Model m) {
        List<UserSetting> userSettings = userSettingService.getAllByUserId(Authorization.id());
        List<Function> functions = functionService.getAll();
        m.addAttribute("noEdit", "");
        m.addAttribute("userSettings", userSettings);
        m.addAttribute("functions", functions);
        m.addAttribute("title", "Формирование квитанций");
        return "func";
    }

    @PostMapping
    public void upload(HttpServletResponse response,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       @RequestParam("userSetting") Integer userSetting,
                       @RequestParam("function") Integer function,
                       Model m) throws Exception {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("Cp1251");
        ByteArrayOutputStream baos = null;
        if (function == 3){
            baos = exampleHandler.handle(userSetting);
            baos.writeTo(response.getOutputStream());
        } else {
//            try {
                if (file == null){
                    throw new Exception("Не выбран файл!");
                }

                baos = fileHandler.handle(file, userSetting, function);

                String fileName = "";
                if (function == 1) {
                    fileName = "kvit.pdf";
                } else {
                    String mask = userSettingService.get(userSetting).getFileMask();
                    if (!mask.isEmpty()) {
                        fileName = mask + "_" + fileHandler.getLastMask() + ".txt";
                    }
                }
                response.setHeader("fileName", fileName);
                baos.writeTo(response.getOutputStream());
                baos.flush();
//            } catch (Exception e) {
//                response.addHeader("err", "err");
//
//                baos = new ByteArrayOutputStream();
//                    String message = "{ \"message\": \"" + e.getMessage() + "\" }";
//                baos.write(message.getBytes());
//                baos.writeTo(response.getOutputStream());
//            }
        }
        baos.flush();
    }



    @GetMapping("/example/{userSettingId}")
    public ResponseEntity createExample(HttpServletResponse response, @PathVariable("userSettingId") Integer userSettingId) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("Cp1251");
        response.setHeader("Content-Disposition", "filename=example.xlsx");

        ByteArrayOutputStream baos = exampleHandler.handle(userSettingId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header("Content-Disposition", "filename=example.xlsx")
                .body(baos.toByteArray());


//        baos.writeTo(response.getOutputStream());
//        baos.flush();
    }


}

