package seedu.address.testutil;

import static seedu.address.testutil.TypicalModules.getTypicalModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.module.Module;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task TASK_A = new TaskBuilder().withModule("cs2030")
        .withDescription("Task A")
        .withStatus("incomplete")
        .build();
    public static final Task TASK_B = new TaskBuilder().withModule("cs2030")
        .withDescription("Task B")
        .withStatus("incomplete")
        .build();
    public static final Task TASK_C = new TaskBuilder().withModule("cs2040")
        .withDescription("Task C")
        .withStatus("incomplete")
        .build();
    public static final Task TASK_D = new TaskBuilder().withModule("cs2040")
        .withDescription("Task D")
        .withStatus("complete")
        .build();
    public static final Task TASK_E = new TaskBuilder().withModule("cs2100")
        .withDescription("Task E")
        .withStatus("complete")
        .build();
    public static final Task TASK_F = new TaskBuilder().withModule("cs2100")
        .withDescription("Task F")
        .withStatus("complete")
        .build();

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks and typical modules.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Module module: getTypicalModules()) {
            ab.addModule(module);
        }
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_A, TASK_B, TASK_C, TASK_D, TASK_E, TASK_F));
    }
}