package com.studentcrud.studentcrud.controller;

import java.util.List;
import java.util.Optional;

import com.studentcrud.studentcrud.model.Student;
import com.studentcrud.studentcrud.repository.StudentRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
  @Autowired
  private StudentRepository repository;

  @GetMapping()
  public ResponseEntity<List<Student>> index() {
    try {
      return ResponseEntity.ok().body(repository.findAll());   
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<Student> create(@RequestBody Student student) {
    try {
      Student saved = repository.save(student);
      return ResponseEntity.ok().body(saved);

    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> update(@PathVariable("id") Long id, @RequestBody Student student) {
    try {
      return repository.findById(id).map(studentItem -> {
        studentItem.setFirstName(student.getFirstName());
        studentItem.setLastName(student.getLastName());
        studentItem.setEmail(student.getEmail());
        studentItem.setAge(student.getAge());

        Student updated = repository.save(studentItem);
        return ResponseEntity.ok().body(updated);
      }).orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      return repository.findById(id).map(studentItem -> {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
      }).orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
