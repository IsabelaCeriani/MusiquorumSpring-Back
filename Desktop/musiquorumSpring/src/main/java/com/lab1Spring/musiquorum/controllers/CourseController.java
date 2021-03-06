package com.lab1Spring.musiquorum.controllers;

import com.lab1Spring.musiquorum.dtos.*;
import com.lab1Spring.musiquorum.models.Course;
import com.lab1Spring.musiquorum.models.User;
import com.lab1Spring.musiquorum.services.CourseService;
import com.lab1Spring.musiquorum.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/{profId}")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody @Valid  CreateCourseDTO courseDTO, @PathVariable @Valid UUID profId){

        return ResponseEntity.ok(courseService.addCourse(courseDTO, profId));
    }


    @PutMapping("/editCourse/{courseId}")
    public ResponseEntity<CourseDTO> editCourse(@RequestBody @Valid EditCourseDTO courseDTO, @PathVariable @Valid UUID courseId){
        return ResponseEntity.ok(courseService.editCourse(courseDTO, courseId));
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public ResponseEntity<CourseDTO> deleteCourse(@PathVariable @Valid UUID courseId){
        return ResponseEntity.ok(courseService.deleteCourse(courseId));
    }

    @GetMapping("")
    public  ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public  ResponseEntity<Course> getCourseById(@PathVariable UUID courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping("/teacherCourses/{profId}")
    public  ResponseEntity<List<Course>> getMyProfCourses(@PathVariable @Valid UUID profId){
        return ResponseEntity.ok(courseService.getTeacherCourses(profId));
    }
    @GetMapping("/enrolledIn/{userID}")
    public  ResponseEntity<List<Course>> enrolledInCourses(@PathVariable @Valid UUID userID){
        return ResponseEntity.ok(courseService.getEnrolledInCourses(userID));
    }

    @GetMapping("/byName")
    public  ResponseEntity<List<Course>> getCoursesByTitle(@RequestBody String courseName){
        return ResponseEntity.ok(courseService.getCoursesByTitle(courseName));
    }

    @GetMapping("/byTags")
    public  ResponseEntity<List<Course>> getCoursesByTags(@RequestBody List<String> tags){
        return ResponseEntity.ok(courseService.getCourseByTags(tags));
    }

    @PostMapping("enroll/{courseId}/{userId}")
    public ResponseEntity<Course> enrollToCourse(@PathVariable @Valid UUID courseId, @PathVariable @Valid UUID userId){
        return ResponseEntity.ok(courseService.enrollToCourse(courseId, userId));
    }

}
