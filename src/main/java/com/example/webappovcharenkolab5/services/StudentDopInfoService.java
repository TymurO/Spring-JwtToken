package com.example.webappovcharenkolab5.services;

import com.example.webappovcharenkolab5.models.StudentDopInfo;
import com.example.webappovcharenkolab5.repositories.StudentDopInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StudentDopInfoService {

    private final StudentDopInfoRepository studentDopInfoRepository;

    public StudentDopInfo getStudentDopInfo(Long id) {
        return studentDopInfoRepository.getStudentDopInfoById(id);
    }


    public void addDopInfo(StudentDopInfo studentDopInfo) {
        studentDopInfoRepository.save(studentDopInfo);
    }

    public StudentDopInfo getStudentDopInfoByEmail(String email) {
        return studentDopInfoRepository.getStudentDopInfoByEmail(email);
    }

    public StudentDopInfo getStudentDopInfoByPhone(String phone) {
        return studentDopInfoRepository.getStudentDopInfoByPhone(phone);
    }

    public Collection<StudentDopInfo> getAllInfo() {
        return studentDopInfoRepository.findStudents();
    }
}
