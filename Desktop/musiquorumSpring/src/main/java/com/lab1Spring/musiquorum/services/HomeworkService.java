package com.lab1Spring.musiquorum.services;

import com.lab1Spring.musiquorum.dtos.HomeworkCorrectionDTO;
import com.lab1Spring.musiquorum.dtos.HomeworkDTO;
import com.lab1Spring.musiquorum.repositories.HomeworkFileRepo;
import com.lab1Spring.musiquorum.exceptions.BadRequestException;
import com.lab1Spring.musiquorum.exceptions.FileStorageException;
import com.lab1Spring.musiquorum.models.*;
import com.lab1Spring.musiquorum.repositories.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private HomeworkFileRepo homeworkFileRepo;


    public Homework getHomework(UUID homeworkId) {
        return homeworkRepository.findById(homeworkId).orElseThrow(() -> new BadRequestException("Homework not found"));
    }

    public Homework createHomework(HomeworkDTO homeworkDTO) {
        Homework homework = homeworkRepository.findByUseridAndAssignmentId(homeworkDTO.getStudentId(), homeworkDTO.getAssignmentId());
        homework.setStatus(HomeworkStatus.SUBMITTED);
        homeworkRepository.save(homework);
        homeworkDTO.getFiles().forEach(file -> saveAssignmentFile(file, homework));
        return homework;
    }

    public Homework rateHomework(HomeworkCorrectionDTO homeworkDTO) {
        Homework homework = homeworkRepository.findByUseridAndAssignmentId(homeworkDTO.getStudentId(), homeworkDTO.getAssignmentId());
        homework.setStatus(HomeworkStatus.CORRECTED);
        homework.setComment(homeworkDTO.getComment());
        homework.setGrade(homeworkDTO.getGrade());
        homeworkRepository.save(homework);
        return homework;
    }

    public void saveAssignmentFile(MultipartFile multipartFile, Homework homework) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            HomeworkFile homeworkFile = new HomeworkFile(multipartFile.getName(), multipartFile.getContentType(), new Date(), homework, multipartFile.getBytes());

            homeworkFileRepo.save(homeworkFile);
        } catch (IOException | FileStorageException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }

    }
}
