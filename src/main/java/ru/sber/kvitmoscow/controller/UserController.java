package ru.sber.kvitmoscow.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.kvitmoscow.model.Role;
import ru.sber.kvitmoscow.model.User;
import ru.sber.kvitmoscow.service.RoleService;
import ru.sber.kvitmoscow.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(Model m){
        m.addAttribute("title", "Пользователи");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        User user = userService.get(id);
        List<Role> roles = roleService.getAll();
        m.addAttribute("user",user);
        m.addAttribute("roles", roles);
        return "fragments/dialogs :: userDialog";
    }

    @PostMapping
    public String save(User entity) {
        log.info("save {} from {} ",entity, this.getClass().getSimpleName());

        userService.save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        log.info("delete id {} from {}",id, this.getClass().getSimpleName());

        userService.delete(id);
        return "common";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        log.info("getAll from {}", this.getClass().getSimpleName());

        List<User> users = userService.getAll();
        m.addAttribute("users", users);
        return "fragments/tables :: usersList";
    }


    @GetMapping("/file")
    public void getFile(HttpServletResponse response) throws DocumentException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("hello-hello!!!"));
            document.close();
            response.setContentType("application/pdf");
            baos.writeTo(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

}
