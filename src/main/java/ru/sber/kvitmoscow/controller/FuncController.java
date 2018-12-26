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

import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/func")
public class FuncController {
    private static MediaType STREAM_MEDIA_TYPE = MediaType.parseMediaType("application/octet-stream");


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
    public ResponseEntity upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("userSetting") Integer userSetting,
            @RequestParam("function") Integer function) throws Exception {
        ByteArrayOutputStream baos = null;

        try {
            if (file == null) {
                return createResponse(createBaosWithMessage("Не выбран файл!"), STREAM_MEDIA_TYPE, "err", "err");
            }

            baos = fileHandler.handle(file, userSetting, function);

            String fileName = "";
            if (function == 1) {
                fileName = "kvit.pdf";
            } else {
                String mask = userSettingService.get(userSetting).getFileMask();
                mask = mask.isEmpty() ? "_" : mask;
                if (!mask.isEmpty()) {
                    fileName = mask + "_" + fileHandler.getLastMask() + ".txt";
                }
            }
            return createResponse(baos, STREAM_MEDIA_TYPE, "filename", fileName);
        } catch (Exception e) {
            return createResponse(createBaosWithMessage(e.getMessage()), STREAM_MEDIA_TYPE, "err", "err");
        }
    }

    @GetMapping("/example/{userSettingId}")
    public ResponseEntity createExample(@PathVariable("userSettingId") Integer userSettingId) throws IOException {
        ByteArrayOutputStream baos = exampleHandler.handle(userSettingId);
        return createResponse(baos, STREAM_MEDIA_TYPE, "Content-Disposition", "filename=example.xlsx");
    }

    private ResponseEntity createResponse(ByteArrayOutputStream baos, MediaType contentType, String headerName, String headerValue ){
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(headerName, headerValue)
                .body(baos.toByteArray());
    }

    private ByteArrayOutputStream createBaosWithMessage(String str) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String message = "{ \"message\": \"" + str + "\" }";
        baos.write(message.getBytes());
        return baos;
    }
}