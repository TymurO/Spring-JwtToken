package com.example.webappovcharenkolab5.repositories;

import com.example.webappovcharenkolab5.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
            select s from Student s order by s.surname asc
            """)
    Collection<Student> getAllStudents();

    Student getStudentById(Long id);

    @Query("""
            select s from Student s order by s.rank desc
            """)
    Collection<Student> getSortedStudentsByRank();

    Collection<Student> getStudentsBySurnameStartingWith(String name);
}
