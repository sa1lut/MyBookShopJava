package ru.saltanov.MySpringBoot2Dbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saltanov.MySpringBoot2Dbase.entity.Discipline;
import ru.saltanov.MySpringBoot2Dbase.service.DisciplineService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping("/disciplines")
    public ResponseEntity<List<Discipline>> allDisciplines() {
        try {
            return new ResponseEntity<>(disciplineService.getAllDisciplines(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disciplines/{id}")
    public ResponseEntity<Discipline> getDiscipline(@PathVariable("id") int id) {
        Discipline discipline;
        try {
            discipline = disciplineService.getDiscipline(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (discipline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(discipline, HttpStatus.OK);
    }

    @PostMapping("/disciplines")
    public ResponseEntity<Discipline> saveDiscipline(@RequestBody Discipline discipline) {
        try {
            return new ResponseEntity<>(disciplineService.saveDiscipline(discipline), HttpStatus.OK);
        } catch (Exception e) {
//            log.error("post /disciplines exception:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/disciplines")
    public ResponseEntity<Discipline> updateDiscipline(@RequestBody Discipline discipline) {
        try {
            return new ResponseEntity<>(disciplineService.saveDiscipline(discipline), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<Discipline> deleteDiscipline(@PathVariable("id") int id) {
        int deletedRows;

        try {
            deletedRows = disciplineService.deleteDiscipline(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (deletedRows == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
