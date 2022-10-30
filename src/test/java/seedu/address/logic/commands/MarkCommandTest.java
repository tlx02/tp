package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_MARKED_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test // valid index for unfiltered list [1...size of list]
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.replaceTask(taskToMark, taskToMark.mark(), true);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test // invalid index for unfiltered list [size of list + 1...INT_MAX]
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);
        String expectedMessage = String.format(
            Messages.MESSAGE_INVALID_TASK_INDEX_TOO_LARGE, model.getFilteredTaskList().size() + 1);

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test // invalid task for unfiltered list [marked task]
    public void execute_invalidTaskUnfilteredList_throwsCommandException() {

        MarkCommand markCommand = new MarkCommand(INDEX_MARKED_TASK);

        String expectedMessage = MarkCommand.MESSAGE_TASK_ALREADY_MARKED;

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test // valid index for filtered list [1...size of list]
    public void execute_validIndexFilteredList_success() {

        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.replaceTask(model.getFilteredTaskList().get(0), taskToMark.mark(), true);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test // invalid index for filtered list [size of list + 1...INT_MAX]
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);
        String expectedMessage = String.format(
            Messages.MESSAGE_INVALID_TASK_INDEX_TOO_LARGE, model.getFilteredTaskList().size() + 1);

        assertCommandFailure(markCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkCommand(INDEX_FIRST_TASK);
        MarkCommand markSecondCommand = new MarkCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(INDEX_FIRST_TASK);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

}
