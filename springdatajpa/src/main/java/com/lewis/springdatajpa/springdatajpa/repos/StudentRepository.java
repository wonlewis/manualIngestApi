package com.lewis.springdatajpa.springdatajpa.repos;
import com.lewis.springdatajpa.springdatajpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Long> {

    }

