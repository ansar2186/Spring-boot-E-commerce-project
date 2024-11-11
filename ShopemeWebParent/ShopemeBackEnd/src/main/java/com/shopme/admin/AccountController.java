package com.shopme.admin;

import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.service.UserService;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.commom.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    private UserService service;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser,
                              Model model) {
        String email = loggedUser.getUsername();
        User user = service.getUserName(email);
        model.addAttribute("user", user);
        return "account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes
            , @RequestParam("image") MultipartFile multipartFile,
                              @AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            user.setPhoto(fileName);
            User savedUser = service.updateAccount(user);
            String uploadDir = "users-photo/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhoto().isEmpty()) {
                user.setPhoto(null);
                service.updateAccount(user);
            }
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Your account details has been updated.");

        return "redirect:/account";
    }

}
