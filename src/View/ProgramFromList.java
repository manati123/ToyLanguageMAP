package View;

import Model.Statements.CompStatement;
import Model.Statements.IStatement;

import java.util.Optional;

public interface ProgramFromList {
    static IStatement create(java.util.List<IStatement> statements) {
        Optional<IStatement> result = statements.stream().reduce(CompStatement::new);
        return result.orElse(null);
    }
}
