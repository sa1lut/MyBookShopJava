package ru.saltanov.MySpringBoot2Dbase.dao;

import org.springframework.stereotype.Repository;
import ru.saltanov.MySpringBoot2Dbase.entity.Discipline;
import ru.saltanov.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Repository
public interface DisciplineDAO {
    List<Discipline> getAllDisciplines();

    Discipline saveDiscipline(Discipline discipline);

    Discipline getDiscipline(int id);

    int deleteDiscipline(int id);
}
