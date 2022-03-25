package ru.gb.bulgakov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.bulgakov.service.PictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/picture")
public class PictureController {
    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/{pictureId}")
    public void downloadPicture(@PathVariable("pictureId") long pictureId, HttpServletResponse res) throws IOException {

        Optional<String> opt = pictureService.getPictureContentType(pictureId);
        if (opt.isPresent()) {
            res.setContentType(opt.get());
            res.getOutputStream().write(pictureService.getPictureDataById(pictureId).get());
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}