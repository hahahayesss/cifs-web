package com.r00t.cifs.controllers;

import com.r00t.cifs.models.DataModel;
import com.r00t.cifs.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @Autowired
    private DataService dataService;

    @RequestMapping("/")
    public ModelAndView openHomePage(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView openLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/gallery")
    public ModelAndView openGalleryPage(ModelAndView modelAndView) {
        modelAndView.setViewName("gallery");
        modelAndView.addObject("dataList", dataService.getAllImageAndData());
        return modelAndView;
    }

    @RequestMapping("/add-image")
    public ModelAndView openUploadPage(ModelAndView modelAndView) {
        modelAndView.setViewName("add_image");
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView uploadImage(@RequestParam("recordDate") String recordDate,
                                    @RequestParam("recordTime") String recordTime,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes) {
        String fileName = dataService.uploadFile(imageFile);

        DataModel dataModel = new DataModel();
        dataModel.setRecordDate(recordDate);
        dataModel.setRecordTime(recordTime);
        dataModel.setImageDataName(fileName);
        dataService.insert(dataModel);

        modelAndView.setViewName("redirect:/add-image");
        redirectAttributes.addFlashAttribute("isSuccess", "yes");
        return modelAndView;
    }
}
