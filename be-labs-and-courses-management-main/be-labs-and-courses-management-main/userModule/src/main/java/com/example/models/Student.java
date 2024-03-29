package com.example.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.util.*;

@Entity
@SQLDelete(sql="UPDATE Student SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(name = "students")
public class Student extends User {
    private Set<String> enrolledCourses = new HashSet<>();
    @Min(value=1)
    @Max(value=3)
    private int year;
    @Min(value=1)
    @Max(value=6)
    private int semester;
    private String registrationNumber;

    // <-------------------------------- FROM CATALOG ----------------------------------> //
    private int maxGradeId = 0;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Grade> grades = new ArrayList<>();

    private boolean deleted = false;
    // <--------------------------------------------------------------------------------> //

    public Student(UUID id,
                   String firstname,
                   String lastname,
                   String email,
                   String username,
                   int year,
                   int semester,
                   String registrationNumber,
                   Set<String> enrolledCourses) {
        super(id, firstname, lastname, email, username, 2);
        this.enrolledCourses = enrolledCourses;
        this.year = year;
        this.semester = semester;
        this.registrationNumber = registrationNumber;
    }


    public Student(String firstname,
                   String lastname,
                   String email,
                   String username,
                   int year,
                   int semester,
                   String registrationNumber,
                   Set<String> enrolledCourses) {
        super(firstname, lastname, email, username, 2);
        this.enrolledCourses = enrolledCourses;
        this.year = year;
        this.semester = semester;
        this.registrationNumber = registrationNumber;
    }

    public Student() {

    }

    public Set<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Set<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void addEnrolledCourse(String course) {
        enrolledCourses.add(course);
    }

    public boolean isDeleted(){
        return deleted;
    }
    public Student setDeleted() {
        deleted = true;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "enrolledCourses=" + enrolledCourses +
                ", year=" + year +
                ", semester=" + semester +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object user) {
        return super.equals(user);
    }

    // <-------------------------------- FROM CATALOG ----------------------------------> //

    // ati putea face add si set comun si in if-else doar sa modificati maxGradeId
    public void addGrade(Grade grade) {
        if (!grades.isEmpty()) {
            maxGradeId++;
            grades.add(grade);
            grade.setId(maxGradeId);
        } else {
            grades.add(grade);
            maxGradeId = 0;
            grade.setId(maxGradeId);
        }
    }

    public List<Grade> getGrades() {
        List<Grade> gradesList = new ArrayList<>();
        for (Grade grade : this.grades) {
            if (!grade.isDeleted()) {
                gradesList.add(grade);
            }
        }
        return gradesList;
    }

    public Grade getGradeById(int id) {
        for (Grade grade : this.getGrades()) {
            if (grade.getId() == id) {
                return grade;
            }
        }
        return null;
    }
    // ** //
    public List<Grade> getGradesBySubject(String subject) {
        List<Grade> gradesList = new ArrayList<>();
        for (Grade grade : this.getGrades()) {
            if (grade.getSubject().getTitle().equals(subject)) {
                gradesList.add(grade);
            }
        }
        return gradesList;
    }
}