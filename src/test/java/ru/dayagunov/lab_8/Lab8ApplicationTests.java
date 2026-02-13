package ru.dayagunov.lab_8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dayagunov.lab_8.model.Task;
import ru.dayagunov.lab_8.ports.TaskRepositoryPort;
import ru.dayagunov.lab_8.service.TaskCoreService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class Lab8ApplicationTests {
    private TaskRepositoryPort mockRepository;
    private TaskCoreService taskCoreService;

    @BeforeEach
    void setUp() {
        mockRepository = mock(TaskRepositoryPort.class);
        taskCoreService = new TaskCoreService(mockRepository);
    }

    @Test
    void shouldCalculateAverageAmountForIncompleteTasksUsingParallelStream() {
        Task t1 = new Task("Open 1", false, 100);
        Task t2 = new Task("Open 2", false, 200);
        Task t3 = new Task("Closed 3", true, 500);
        Task t4 = new Task("Open 4", false, 300);

        List<Task> allTasks = Arrays.asList(t1, t2, t3, t4);

        // Настройка мока
        when(mockRepository.findAll()).thenReturn(allTasks);

        double average = taskCoreService.calculateAverageTaskAmount();

        assertEquals(275.0, average, 0.001, "Среднее должно быть рассчитано корректно по незавершенным задачам.");

        verify(mockRepository, times(1)).findAll();

        verify(mockRepository, never()).save(any(Task.class));
    }

    @Test
    void shouldReturnZeroIfAllTasksAreCompleted() {
        when(mockRepository.findAll()).thenReturn(Arrays.asList());

        double average = taskCoreService.calculateAverageTaskAmount();

        assertEquals(0.0, average, "Если задач нет, то среднее должно быть 0.0");
    }

}
