package Repo;

import Model.Exceptions.MyException;
import Model.ProgramState;

import java.util.ArrayList;
import java.util.List;

public interface IRepo {
    //ProgramState getCurrentProgramState();
    String getLogFilePath();
    void logProgramStateExecute() throws MyException;
    void logProgramStateExecute(ProgramState program) throws MyException;
    List<ProgramState> getProgramStatesList();
    void setProgramStatesList(List<ProgramState> newProgramStatesList);
}