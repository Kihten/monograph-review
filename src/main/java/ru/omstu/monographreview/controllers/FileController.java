package ru.omstu.monographreview.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.omstu.monographreview.dto.RequestFile;
import ru.omstu.monographreview.services.FileService;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("download/{fileId}")
    public void downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        RequestFile file = fileService.getFile(fileId);

        if (file != null){
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; fileName = " + file.getFileName());
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(file.getData());
            outputStream.close();
        }
    }
}
