package com.studentcrud.studentcrud.repository;

import com.studentcrud.studentcrud.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{
  
}
