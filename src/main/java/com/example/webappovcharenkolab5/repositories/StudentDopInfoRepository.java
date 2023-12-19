package com.example.webappovcharenkolab5.repositories;

import com.example.webappovcharenkolab5.models.StudentDopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentDopInfoRepository extends JpaRepository<StudentDopInfo, Long> {

    @Query("""
            select s from StudentDopInfo s where s.student.id = ?1
            """)
    StudentDopInfo getStudentDopInfoById(Long id);

    StudentDopInfo getStudentDopInfoByEmail(String email);

    StudentDopInfo getStudentDopInfoByPhone(String phone);

    @Query("""
            select s from StudentDopInfo s
            """)
    Collection<StudentDopInfo> findStudents();
}
