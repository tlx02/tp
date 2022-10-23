package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_COMPLETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_LINKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.FilterPredicate;
import seedu.address.model.task.LinkStatus;
import seedu.address.model.task.TaskStatus;

/**
 * Parses input arguments and creates a new FilterTasksCommand object
 */
public class FilterTasksCommandParser implements Parser<FilterTasksCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterTasksCommand
     * and returns a FilterTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTasksCommand parse(String args) throws ParseException {
        Optional<Module> module = Optional.empty();
        Optional<TaskStatus> taskStatus = Optional.empty();
        Optional<LinkStatus> linkStatus = Optional.empty();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_IS_COMPLETE, PREFIX_IS_LINKED);

        if (!isPrefixPresent(argMultimap, PREFIX_MODULE, PREFIX_IS_COMPLETE, PREFIX_IS_LINKED)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTasksCommand.MESSAGE_USAGE));
        }

        if (hasPrefix(argMultimap, PREFIX_MODULE)) {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            module = Optional.of(new Module(moduleCode));
        }

        if (hasPrefix(argMultimap, PREFIX_IS_COMPLETE)) {
            taskStatus = Optional.of(ParserUtil.parseIsComplete(argMultimap.getValue(PREFIX_IS_COMPLETE).get()));
        }

        if (hasPrefix(argMultimap, PREFIX_IS_LINKED)) {
            linkStatus = Optional.of(ParserUtil.parseIsLinked(argMultimap.getValue(PREFIX_IS_LINKED).get()));
        }

        return new FilterTasksCommand(new FilterPredicate(module, taskStatus, linkStatus));
    }

    /**
     * Returns true if at least one prefix is not empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the prefix is not empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean hasPrefix(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
