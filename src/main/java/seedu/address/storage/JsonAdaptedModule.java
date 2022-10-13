package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * This class represents a Jackson friendly version of {@link Module}.
 */
public class JsonAdaptedModule {
    public static final String MISSING_MODULE_MESSAGE_FORMAT =
            "Module's code field is not present";
    private final String moduleCode;


    public JsonAdaptedModule(@JsonProperty("modCode") String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public JsonAdaptedModule(Module mod) {
        moduleCode = mod.getModuleCode().moduleCode;
    }

    /**
     * Converts this Jackson-friendly module object into a Module Object.
     *
     * @return The Module Object which is created.
     * @throws IllegalValueException If moduleCode is null.
     */
    public Module toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(MISSING_MODULE_MESSAGE_FORMAT);
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MODULE_CODE_CONSTRAINTS);
        }
        final ModuleCode modCode = new ModuleCode(moduleCode);
        return new Module(modCode);
    }
}