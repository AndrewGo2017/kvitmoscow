package ru.sber.kvitmoscow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
                       @RequestParam("file") MultipartFile file,
                       @RequestParam("userSetting") Integer userSetting,
                       @RequestParam("function") Integer function,
                       Model m) throws Exception {


            ByteArrayOutputStream baos = fileHandler.handle(file, userSetting, function);

            String fileName = "";
            if (function == 1){
                fileName = "kvit.pdf";
            } else {
                String mask = userSettingService.get(userSetting).getFileMask();
                if (!mask.isEmpty()){
                    fileName = mask + "_" + fileHandler.getLastMask() + ".txt";
                }
            }
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("Cp1251");
            response.setHeader("Content-Disposition", "filename="+ fileName);

            baos.writeTo(response.getOutputStream());
            baos.flush();

//        ClassLoader classLoader = getClass().getClassLoader();
//            String str  = classLoader.getResource(".").getFile();



//        File f = new File(new ClassPathResource("static/content").getFile().getPath() + "/" + Authorization.id() + fileName);
//
//        try(OutputStream outputStream = new FileOutputStream( f)) {
//                baos.writeTo(outputStream);
//            }
//
//            return ResponseEntity.ok(f.getName());

//        return ResponseEntity.ok().build();
    }

    @Autowired
    public ExampleHandler exampleHandler;

    @GetMapping("/example/{userSettingId}")
    public void createExample(HttpServletResponse response, @PathVariable("userSettingId") Integer userSettingId) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("Cp1251");
        response.setHeader("Content-Disposition", "filename=example.xlsx");

        ByteArrayOutputStream baos = exampleHandler.handle(userSettingId);
        baos.writeTo(response.getOutputStream());
        baos.flush();
    }
}

