package com.lab1Spring.musiquorum.repositories;

import com.lab1Spring.musiquorum.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Course, UUID> {



}
