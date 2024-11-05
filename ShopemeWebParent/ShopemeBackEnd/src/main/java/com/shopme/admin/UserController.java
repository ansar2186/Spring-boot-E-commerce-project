package com.shopme.admin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import ch.qos.logback.core.util.StringUtil;
import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.service.impl.UserServiceImpl;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.admin.util.UserCsvExporter;
import com.shopme.admin.util.UserExcelExporter;
import com.shopme.admin.util.UserPdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.service.RoleService;
import com.shopme.admin.service.UserService;
import com.shopme.commom.entity.Role;
import com.shopme.commom.entity.User;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/users")
    public String listFirstPage(Model model) {

        //return listByPage(0, model, "firstName", "asc", null);
        return listByPage(0, model, "firstName", "asc", null);
        //return listByPage(0, model);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyWord") String keyWord) {

        Page<User> page = userService.listByPage(pageNum, sortField, sortDir, keyWord);
        //Page<User> page = userService.listByPage(pageNum);
        List<User> listUsers = page.getContent();

        long startCount = (long) (pageNum - 1) * UserServiceImpl.pageSize + 1;
        long endCount = (long) startCount + UserServiceImpl.pageSize - 1;

        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortFiled", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "user";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        User user = new User();
        List<Role> allRoles = roleService.getAllRoles();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", allRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "user_form";

    }

    @PostMapping("/users/save")
    public String saveUsers(User user, RedirectAttributes redirectAttributes
            , @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            user.setPhoto(fileName);
            User savedUser = userService.saveUsers(user);
            String uploadDir = "users-photo/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String userEdit(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) throws UserNotFoundException {
        try {
            User user = userService.getUserByID(id);
            List<Role> allRoles = roleService.getAllRoles();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User(ID:" + id + ")");
            model.addAttribute("listRoles", allRoles);
            return "user_form";
        } catch (UserNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) throws UserNotFoundException {
        try {
            userService.deleteUse(id);
            redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been successfully");
            //return "user_form";
        } catch (UserNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " hase been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";

    }

    //TIPOS DE EXPORTACION, CSV, EXCEL Y PDF
    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.getAllUser();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.getAllUser();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.getAllUser();
        UserPdfExporter exporter = new UserPdfExporter();
        exporter.export(listUsers, response);
    }

}
