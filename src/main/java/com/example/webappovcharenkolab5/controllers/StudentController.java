package com.example.webappovcharenkolab5.controllers;

import com.example.webappovcharenkolab5.models.Student;
import com.example.webappovcharenkolab5.models.StudentDopInfo;
import com.example.webappovcharenkolab5.services.EmailSenderService;
import com.example.webappovcharenkolab5.services.StudentDopInfoService;
import com.example.webappovcharenkolab5.services.StudentService;
import com.example.webappovcharenkolab5.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentDopInfoService studentDopInfoService;
    private final JwtTokenUtils jwtTokenUtils;
    private final EmailSenderService emailSenderService;

    @GetMapping("/u")
    public String getList(@CookieValue("jwt") String jwt, Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("username", jwtTokenUtils.getUserName(jwt));
        Collection<String> roles = jwtTokenUtils.getRoles(jwt);
        if (roles.contains("ROLE_ADMIN")) {
            model.addAttribute("role", "admin");
        }
        else {
            model.addAttribute("role", "user");
        }
        return "studentList";
    }

    @GetMapping("/u/sorted/rank")
    public ModelAndView getSortedListByRank(Model model) {
        ModelAndView modelAndView = new ModelAndView("studentList");
        modelAndView.addObject("students", studentService.getAllStudentsOrderedByRank());
        return modelAndView;
    }

    @GetMapping("/a/add_form")
    public String showAddForm() {
        return "addStudentForm";
    }

    @GetMapping("/a/edit_form/{id}")
    public ModelAndView showEditForm(@CookieValue("jwt") String jwt, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("editStudentForm");
        modelAndView.addObject("student", studentService.getStudent(id));
        modelAndView.addObject("username", jwtTokenUtils.getUserName(jwt));
        return modelAndView;
    }

//    @GetMapping("/dop_form/{id}")
//    public ModelAndView showDopInfoForm(@PathVariable("id") Long id) {
//        ModelAndView modelAndView = new ModelAndView("dopInfoForm");
//        StudentDopInfo studentDopInfo = studentDopInfoService.getStudentDopInfo(id);
//        if (studentDopInfo == null) {
//            studentDopInfo = new StudentDopInfo();
//            studentDopInfo.setContract(false);
//            studentDopInfo.setScholarship(false);
//            studentDopInfo.setEmail("");
//            studentDopInfo.setPhone("");
//            studentDopInfo.setStudent(studentService.getStudent(id));
//        }
//        modelAndView.addObject("studentDopInfo", studentDopInfo);
//        return modelAndView;
//    }

    @GetMapping("/u/dop_form/{id}")
    public String showDopInfoForm(@CookieValue("jwt") String jwt, @PathVariable("id") Long id, Model model) {
        StudentDopInfo studentDopInfo = studentDopInfoService.getStudentDopInfo(id);
        if (studentDopInfo == null) {
            studentDopInfo = new StudentDopInfo();
            studentDopInfo.setContract(false);
            studentDopInfo.setScholarship(false);
            studentDopInfo.setEmail("");
            studentDopInfo.setPhone("");
            studentDopInfo.setStudent(studentService.getStudent(id));
        }
        model.addAttribute("username", jwtTokenUtils.getUserName(jwt));
        Collection<String> roles = jwtTokenUtils.getRoles(jwt);
        if (roles.contains("ROLE_ADMIN")) {
            model.addAttribute("role", "admin");
        }
        else {
            model.addAttribute("role", "user");
        }
        model.addAttribute("student", studentDopInfo);
        return "dopInfoForm";
    }

    @PostMapping("/u/send")
    public String sendDB() throws MessagingException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            objectMapper.writeValue(new File("/info.json"), studentDopInfoService.getAllInfo());
            objectMapper.writeValue(new File("/students.json"), studentService.getAllStudents());
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailSenderService.sendEmail("0966282541zxzxzx@gmail.com", "School", "File about students");
        return "redirect:/students/u";
    }

    @PostMapping("/a/new")
    public String addStudent(Student student) {
        studentService.addStudent(student);
        return "redirect:/students/u";
    }

    @PostMapping("/a/edit")
    public String editStudent(Student student) {
        studentService.editStudent(student);
        return "redirect:/students/u";
    }

    @PostMapping("/a/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students/u";
    }

    @PostMapping("/a/dop_info/{id}")
    public String addDopInfo(@PathVariable("id") Long id, @ModelAttribute StudentDopInfo studentDopInfo, Model model) {
        StudentDopInfo emailStudent = studentDopInfoService.getStudentDopInfoByEmail(studentDopInfo.getEmail());
        StudentDopInfo phoneStudent = studentDopInfoService.getStudentDopInfoByPhone(studentDopInfo.getPhone());
        studentDopInfo.setStudent(studentService.getStudent(id));
        if (emailStudent != null && !emailStudent.getId().equals(studentDopInfo.getId())) {
            model.addAttribute("error", "Запис з такою поштою вже існує");
            model.addAttribute("student", studentDopInfo);
            return "dopInfoForm";
        }
        if (phoneStudent != null && !phoneStudent.getId().equals(studentDopInfo.getId())) {
            model.addAttribute("error", "Запис з таким номером вже існує");
            model.addAttribute("student", studentDopInfo);
            return "dopInfoForm";
        }
        studentDopInfoService.addDopInfo(studentDopInfo);
        return "redirect:/students/u";
    }

    @GetMapping("/u/search")
    public String searchBySurname(@RequestParam(name = "search") String surname, Model model) {
        model.addAttribute("students", studentService.getStudentsBySurname(surname));
        return "studentList";
    }
}
