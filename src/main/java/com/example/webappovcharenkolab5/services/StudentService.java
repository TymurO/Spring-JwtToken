package com.example.webappovcharenkolab5.services;

import com.example.webappovcharenkolab5.models.Student;
import com.example.webappovcharenkolab5.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Collection<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public Collection<Student> getAllStudentsOrderedByRank() {
     return studentRepository.getSortedStudentsByRank();
    }

    public Student getStudent(Long id) {
        return studentRepository.getStudentById(id);
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public void editStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsBySurname(String surname) {
        return studentRepository.getStudentsBySurnameStartingWith(surname);
    }
}
