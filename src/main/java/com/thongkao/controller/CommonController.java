package com.thongkao.controller;

import com.thongkao.model.FileResponse;
import com.thongkao.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @GetMapping("/file/{fileName}")
    public void getFile(@PathVariable(name = "fileName") String fileName, HttpServletResponse response) throws ServletException, IOException {
        FileResponse file = fileService.getFile(fileName);
        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getFileName());
        response.getOutputStream().write(file.getData());
    }
}
