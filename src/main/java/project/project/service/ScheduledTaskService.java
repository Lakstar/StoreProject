package project.project.service;

public interface ScheduledTaskService {

    void insertMemoriesIfLessThanFive();

    void insertRamsIfLessThanFive();
}
