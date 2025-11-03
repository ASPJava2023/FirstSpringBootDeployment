package com.asp.FirstSpringBootDeployment.controller;

import com.asp.FirstSpringBootDeployment.entity.Tutorial;
import com.asp.FirstSpringBootDeployment.repository.TutorialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    //Get all tutorials listed
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("List found: {}", tutorials);
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                  }
    }
    //Get tutorial by id
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<?> getTutorialById(@PathVariable("id") String idParam) {
        log.info("Request received to get tutorial with id {}", idParam);
        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            log.error("Invalid tutorial id format: {}", idParam);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            log.info("Tutorial found: {}", tutorialData.get());
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            log.error("Tutorial with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("tutorial with id " + id + " not found");

        }
    }
     //Create a new tutorial
    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialRepository
                    .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            log.info("New tutorial created: {}", _tutorial);
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating tutorial: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Update tutorial by id
    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {

        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        log.info("Request to update tutorial with id {}: {}", id, tutorial);
        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            log.info("Tutorial updated: {}", _tutorial);
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            log.warn("Tutorial with id {} not found for update", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Delete tutorial by id
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<?> deleteTutorial(@PathVariable("id") long id) {
        log.info("Request  received to delete tutorial with id {}", id);

        try {
            if(!tutorialRepository.existsById(id)) {
                log.warn("Tutorial with id {} not found for deletion", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tutorial with id " + id + " not found");
            }

            tutorialRepository.deleteById(id);
            log.info("Tutorial with id {} deleted successfully", id);
                return ResponseEntity.status(HttpStatus.OK)
                    .body("Tutorial with id " + id + " deleted successfully");
        }
        catch (Exception e) {
            log.error("Error occurred while deleting tutorial with id {}: {}", id, e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting tutorial with id " + id);
        }
    }
    //Delete all tutorials
    @DeleteMapping("/tutorials")
    public ResponseEntity<?> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            log.info("All tutorials deleted successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("All tutorials deleted successfully");
        } catch (Exception e) {
            log.error("Error occurred while deleting all tutorials: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
   }




