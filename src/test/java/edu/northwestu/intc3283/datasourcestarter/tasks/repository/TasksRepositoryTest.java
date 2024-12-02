package edu.northwestu.intc3283.datasourcestarter.tasks.repository;

import edu.northwestu.intc3283.datasourcestarter.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.CustomJdbcConfiguration;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseTestContextConfiguration.class)
@Import(CustomJdbcConfiguration.class)
class TasksRepositoryTest {

    @Autowired
    private TasksRepository tasksRepository;

    @Test
    public void canSaveTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("This is a description");
        task.setStatus("PENDING");
        this.tasksRepository.save(task);
        assertNotNull(task.getId());
    }


}