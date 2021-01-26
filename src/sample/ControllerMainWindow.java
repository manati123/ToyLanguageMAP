package sample;

import Controller.Controller;
import Controller.Controller;
import Model.ADT.List;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Types.IType;
import Model.ADT.*;
import Repo.IRepo;
import Repo.Repo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMainWindow {

    private Controller controller;
    private IRepo repository;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private TableView<TableRow> HeapTable;

    @FXML
    private ListView<String> out;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<Integer> programStateIdentifiers;

    @FXML
    private TableView<TableRow> symbolTable;

    @FXML
    private ListView<String> executionStack;

    @FXML
    private Button switchToPrograms;

    @FXML
    private Button runOneStep;


    @FXML
    public void handleButtonSwitchToPrograms(javafx.event.ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) switchToPrograms.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Programs.fxml"));

        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void handleButtonRunOneStep(javafx.event.ActionEvent event) throws IOException, InterruptedException, MyException {
        controller.oneStepExecution();

        ArrayList<ArrayList<String>> heapTableTemp = new ArrayList<>();
        ArrayList<String> outputTemp = new ArrayList<>();
        ArrayList<ArrayList< String >>fileTableTemp = new ArrayList<>();
        ArrayList<String> fileTableTemp2 = new ArrayList<>();
        ArrayList<Integer> programStateIdentifierTemp = new ArrayList<>();
        ArrayList<ArrayList<String>> symbolTableTemp = new ArrayList<>();
        ArrayList<String> executionStackTemp = new ArrayList<>();



        for(var x:repository.getProgramStatesList()) {
            heapTableTemp.addAll(x.getHeap().getElementsStrings());
            outputTemp.addAll(x.getOutput().getElementsStrings());
            fileTableTemp.addAll(x.getFileTable().getElementsStrings());
            programStateIdentifierTemp.add(x.getId());
            symbolTableTemp.addAll(x.getSymbolTable().getElementsStrings());
            executionStackTemp.addAll(x.getExecutionStack().getElementsStrings());
        }

        for(var y:controller.getCompletedPrograms(this.repository.getProgramStatesList())) {
            heapTableTemp.addAll(y.getHeap().getElementsStrings());
            outputTemp.addAll(y.getOutput().getElementsStrings());
            fileTableTemp.addAll(y.getFileTable().getElementsStrings());
           programStateIdentifierTemp.add(y.getId());
            symbolTableTemp.addAll(y.getSymbolTable().getElementsStrings());
            executionStackTemp.addAll(y.getExecutionStack().getElementsStrings());
        }

        ArrayList<TableRow> row = new ArrayList<>();
        for(var zz:heapTableTemp){
            row.add(new TableRow(zz.get(0), zz.get(1)));
        }

        ArrayList<TableRow> row2 = new ArrayList<>();
        for(var zz:symbolTableTemp){
            row2.add(new TableRow(zz.get(0), zz.get(1)));
        }

        for(var z:fileTableTemp){
            fileTableTemp2.add(z.get(0));
        }

        HeapTable.setItems(FXCollections.observableList(row));
        out.setItems(FXCollections.observableList(outputTemp));
        fileTable.setItems(FXCollections.observableList(fileTableTemp2));
        programStateIdentifiers.setItems(FXCollections.observableList(programStateIdentifierTemp));
        symbolTable.setItems(FXCollections.observableList(row2));
        executionStack.setItems(FXCollections.observableList(executionStackTemp));

        numberOfProgramStates.setText(Integer.toString(repository.getProgramStatesList().size()));




    }


    public void initData(IStatement selectedItem) throws MyException {
        try {
            selectedItem.typecheck(new Dict<>());
        }catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        ProgramState program = new ProgramState(new Stack<>(), new Dict<>(), new List<>(),selectedItem, new Dict<>(), new Heap<>(),new LockTable<>());
        repository = new Repo("log113.txt", program);
        controller = new Controller(repository);
        numberOfProgramStates.setText(Integer.toString(repository.getProgramStatesList().size()));

    }




}
