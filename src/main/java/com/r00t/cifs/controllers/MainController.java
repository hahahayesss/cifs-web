package com.r00t.cifs.controllers;

import com.r00t.cifs.models.DataModel;
import com.r00t.cifs.models.UserModel;
import com.r00t.cifs.services.DataService;
import com.r00t.cifs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView openHomePage(Authentication authentication,
                                     ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        modelAndView.addObject("AUTH", checkAuth(authentication));
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView openLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/gallery")
    public ModelAndView openGalleryPage(Authentication authentication,
                                        ModelAndView modelAndView) {
        modelAndView.setViewName("gallery");
        modelAndView.addObject("dataList", dataService.getAllImageAndData());
        modelAndView.addObject("AUTH", checkAuth(authentication));
        return modelAndView;
    }

    @RequestMapping("/add-image")
    public ModelAndView openUploadPage(ModelAndView modelAndView) {
        modelAndView.setViewName("add_image");
        return modelAndView;
    }

    @RequestMapping("/create-user")
    public ModelAndView openCreateUserPage(ModelAndView modelAndView) {
        modelAndView.setViewName("create-user");
        return modelAndView;
    }

    //------------------------------------------------------------------------------------------------------------------

    private int checkAuth(Authentication authentication) {
        if (authentication == null)
            return -1;
        else if (authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return 1;
        else
            return 0;
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

    @PostMapping("/new_user")
    public ModelAndView createNewUser(@ModelAttribute UserModel userModel,
                                      ModelAndView modelAndView,
                                      RedirectAttributes redirectAttributes) {
        UserModel temp = userService.createUser(userModel);

        modelAndView.setViewName("redirect:/create-user");
        redirectAttributes.addFlashAttribute("isSuccess", temp != null);
        return modelAndView;
    }
}
