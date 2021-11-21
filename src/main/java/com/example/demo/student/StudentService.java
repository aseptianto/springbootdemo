package com.example.demo.student;

import com.example.demo.exception.StudentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentException.NotFoundException("student with id " + studentId + " does not exist!")
                );
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new StudentException.EmailExistsException(student.getEmail() + " already exists!");
        }
        studentRepository.saveAndFlush(student);
        return student;
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentException.NotFoundException("student with id " + studentId + " does not exist!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, Student student) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentException.NotFoundException("student with id " + studentId + " does not exist!")
                );

        String name = student.getName();
        if (name != null && name.length() > 0 && !Objects.equals(existingStudent.getName(), name)) {
            existingStudent.setName(name);
        }
        String email = student.getEmail();
        if (email != null && email.length() > 0 && !Objects.equals(existingStudent.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new StudentException.EmailExistsException(email + " is already exist");
            }
            existingStudent.setEmail(email);
        }

        LocalDate dob = student.getDob();
        if (dob != null && !Objects.equals(existingStudent.getDob(), dob)) {
            existingStudent.setDob(dob);
        }
    }
}
