package edu.northwestu.intc3283.datasourcestarter.tasks.repository;

import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long>,
        PagingAndSortingRepository<Task, Long> {
}
