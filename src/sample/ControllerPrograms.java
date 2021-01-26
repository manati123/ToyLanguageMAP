
package sample;


//
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Statements.CloseRFileStatement;
import Model.Statements.OpenRFileStatement;
import Model.Statements.readRFileStatement;
import Model.Statements.HeapAllocationStatement;
import Model.Statements.HeapWriteStatement;
import Model.Types.BooleanType;
import Model.Types.IntegerType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Value.BooleanValue;
import Model.Value.IntegerValue;
import Model.Value.StringValue;
import View.ProgramFromList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;




public class ControllerPrograms {


    @FXML
    private ListView<IStatement> listView;

    @FXML
    private Button switchToMainWindow;


    @FXML
    public void initialize() throws Exception {

        listView.setItems(getCommands());

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().selectIndices(0);

    }

    @FXML
    public void handleButtonSwitchToMainWindow(javafx.event.ActionEvent event) throws IOException, MyException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "MainWindow.fxml"
                )
        );
        Stage stage;
        Parent root;
        stage=(Stage) switchToMainWindow.getScene().getWindow();
        root = loader.load();
        try{
        ControllerMainWindow controller = loader.getController();
        controller.initData(listView.getSelectionModel().getSelectedItem());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText(e.toString());
            alert.showAndWait();
            return;
        }
        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.show();

    }

    static IStatement example1(){
        return new CompStatement(new VariableDeclarationStatement("v",new IntegerType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new PrintStatement(new VariableExpression("v"))));
    }



    static IStatement example2(){
        return new CompStatement(
                new VariableDeclarationStatement("a",new IntegerType()),
                new CompStatement(
                        new VariableDeclarationStatement("b", new IntegerType()),
                        new CompStatement(
                                new AssignStatement(
                                        "a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntegerValue(2)),
                                                new ArithmeticExpression(new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(0)), 4),
                                                1)
                                ),
                                new CompStatement(
                                        new AssignStatement("b", new ArithmeticExpression( new VariableExpression("a"), new ValueExpression(new IntegerValue(1)),1)),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    static IStatement example3(){
        return new CompStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompStatement(
                        new VariableDeclarationStatement("v", new IntegerType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }

    static IStatement example4(){
        return new CompStatement(
                new VariableDeclarationStatement("fileVariable",new StringType()),
                new CompStatement(
                        new AssignStatement("fileVariable", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(
                                new OpenRFileStatement(new VariableExpression("fileVariable")),
                                new CompStatement(
                                        new VariableDeclarationStatement("var", new IntegerType()),
                                        new CompStatement(
                                                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("var")),
                                                        new CompStatement(
                                                                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpression("var")),
                                                                        new CloseRFileStatement(new VariableExpression("fileVariable"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    static IStatement example5(){
        return ProgramFromList.create(Arrays.asList(new VariableDeclarationStatement("fileVariable", new StringType()),
                new AssignStatement("fileVariable", new ValueExpression(new StringValue("test.in"))),
                new OpenRFileStatement(new VariableExpression("fileVariable")),
                new VariableDeclarationStatement("var", new IntegerType()),
                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
                new PrintStatement(new VariableExpression("var")),
                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
                new PrintStatement(new VariableExpression("varc")),
                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
                new PrintStatement(new VariableExpression("var"))
                )
        );
    }

    static IStatement example6(){
        return new CompStatement(new VariableDeclarationStatement( "v",new IntegerType()) ,
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
                        new WhileStatement(new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(0)),">"),
                                new CompStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(1)),
                                                2))
                                ))));}

    static IStatement example7(){
        VariableDeclarationStatement v = new VariableDeclarationStatement("v", new RefType(new IntegerType()));
        HeapAllocationStatement heapAllocStmt = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
        VariableDeclarationStatement rdc = new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType())));
        HeapAllocationStatement rfc = new HeapAllocationStatement("a",new VariableExpression("v"));
        HeapAllocationStatement rfc1 = new HeapAllocationStatement("v",new ValueExpression(new IntegerValue(30)));
        CompStatement cms = new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))));
        return new CompStatement(v ,new CompStatement(heapAllocStmt, new CompStatement(rdc, new CompStatement(rfc, new CompStatement(rfc1, cms)))));

    }

    static IStatement example8(){
        return new CompStatement(
                new VariableDeclarationStatement("v",new RefType(new IntegerType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntegerValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5)), 1))))));
    }

    static IStatement example9(){
        return new CompStatement(
                new VariableDeclarationStatement("v",new IntegerType()),
                new CompStatement(
                        new VariableDeclarationStatement("a",new RefType(new IntegerType())),
                        new CompStatement(
                                new AssignStatement("v",new ValueExpression(new IntegerValue(10))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(
                                                                new HeapWriteStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        ))
        );
    }

    static IStatement examplel15() throws Exception {
        return new CompStatement(new VariableDeclarationStatement("v", new IntegerType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(new ForStatemtnt("v", new ValueExpression(new IntegerValue(0)), new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(3)),"!="), new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 1),
                                new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 1))))),
                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3)))));

    }
    static IStatement forStatement() throws Exception {
        return new CompStatement(new VariableDeclarationStatement("a",new RefType(new IntegerType())),
                new CompStatement( new VariableDeclarationStatement("v",new IntegerType()),
                new CompStatement(new AssignStatement("v",
                        new ValueExpression(new IntegerValue(0))),
                new CompStatement(new HeapAllocationStatement("a",new ValueExpression(new IntegerValue(20))),
                        new CompStatement(new ForStatemtnt("v",new ValueExpression(new IntegerValue(0)),
                                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntegerValue(3)),"<"),
                                new ArithmeticExpression(new VariableExpression("v"),new ValueExpression(new IntegerValue(1)),1),
                                new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ReadHeapExpression(new VariableExpression("a")),3))))),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))));

//        return new CompStatement(new VariableDeclarationStatement("a", new RefType(new IntegerType())),
//                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),new CompStatement(
//                        new ForStatemtnt("v",new ValueExpression(new IntegerValue(0)),new ValueExpression(new IntegerValue(3)),
//                                new ArithmeticExpression(new VariableExpression("v"),new ValueExpression(new IntegerValue(1)),1),
//                                new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")),
//                                        new AssignStatement("v",new ArithmeticExpression(new VariableExpression("v"),
//                                                new ReadHeapExpression(new VariableExpression("a")),3))))),
//                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
//                )));
    }
    static IStatement bcd(){
        return new CompStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntegerType())),
                new CompStatement(
                        new VariableDeclarationStatement("v2",new RefType(new IntegerType())),
                        new CompStatement(
                                new VariableDeclarationStatement("x",new IntegerType()),
                                new CompStatement(
                                        new VariableDeclarationStatement("q",new IntegerType()),
                                        new CompStatement(
                                                new HeapAllocationStatement(
                                                        "v1",new ValueExpression(new IntegerValue(20))),
                                                new CompStatement(
                                                        new HeapAllocationStatement("v2",new ValueExpression(new IntegerValue(30))),
                                                        new CompStatement(
                                                                new NewLockStatement("x"),
                                                                new CompStatement(
                                                                        new ForkStatement(
                                                                                new CompStatement(
                                                                                        new LockStatement("x"),
                                                                                        new CompStatement(
                                                                                                new HeapWriteStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntegerValue(20)),2)),
                                                                                                new UnlockStatement("x")
                                                                                        )
                                                                                )

                                                                        ),
                                                                        new CompStatement(
                                                                                new LockStatement("x"),
                                                                                new CompStatement(
                                                                                        new HeapWriteStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntegerValue(10)),3)),
                                                                                        new UnlockStatement("x")
                                                                                )
                                                                        )
                                                                )

                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

//    static IStatement lockTableStuff(){
//        return new CompStatement(
//                new VariableDeclarationStatement("v1",new RefType(new IntegerType())),
//                new CompStatement(new VariableDeclarationStatement("v2",new RefType(new IntegerType())),
//                        new CompStatement(
//                                new VariableDeclarationStatement("x",new IntegerType()),
//                                new CompStatement(
//                                        new VariableDeclarationStatement("q",new IntegerType()),
//                                        new CompStatement(
//                                                new HeapAllocationStatement(
//                                                        "v1",new ValueExpression(new IntegerValue(20))),
//                                                new CompStatement(
//                                                        new HeapAllocationStatement("v2",new ValueExpression(new IntegerValue(30))),
//                                                        new CompStatement(
//                                                                new NewLockStatement("x"),
//                                                                new CompStatement(
//                                                                        new ForkStatement(
//                                                                                new CompStatement(
//                                                                                        new LockStatement("x"),
//                                                                                        new CompStatement(
//                                                                                                new HeapWriteStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression("1"),2)),
//                                                                                                new UnlockStatement("x")
//                                                                                        )
//                                                                                ),
//                                                                                new CompStatement(
//                                                                                        new LockStatement("x"),
//                                                                                        new CompStatement(
//                                                                                                new HeapWriteStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntegerValue(10)),3)),
//                                                                                                new UnlockStatement("x")
//                                                                                        )
//                                                                                )
//                                                                        ),
//                                                                        new CompStatement(
//                                                                                new NewLockStatement("q"),
//                                                                                new CompStatement(
//                                                                                        new ForkStatement(
//                                                                                                new CompStatement(
//                                                                                                new CompStatement(
//                                                                                                new LockStatement("q"),
//                                                                                                new CompStatement(
//                                                                                                        new HeapWriteStatement("v2",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntegerValue(5)),1)),
//                                                                                                        new UnlockStatement("q")
//                                                                                                )),
//                                                                                                new CompStatement(
//                                                                                                        new LockStatement("q"),
//                                                                                                        new CompStatement(
//                                                                                                                new HeapWriteStatement("v2",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntegerValue(10)),3)),
//                                                                                                                new UnlockStatement("q")
//                                                                                                        )
//                                                                                                )),
//                                                                                                new CompStatement(
//                                                                                                        new NopStatement(),
//                                                                                                        new CompStatement(
//                                                                                                                new NopStatement(),
//                                                                                                                new CompStatement(
//                                                                                                                        new NopStatement(),
//                                                                                                                        new CompStatement(
//                                                                                                                                new NopStatement(),
//                                                                                                                                new CompStatement(
//                                                                                                                                        new LockStatement("x"),
//                                                                                                                                        new CompStatement(
//                                                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
//                                                                                                                                                new CompStatement(
//                                                                                                                                                        new UnlockStatement("x"),
//                                                                                                                                                        new CompStatement(
//                                                                                                                                                                new LockStatement("q"),
//                                                                                                                                                                new CompStatement(
//                                                                                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
//                                                                                                                                                                        new UnlockStatement("q")
//                                                                                                                                                                )
//                                                                                                                                                        )
//                                                                                                                                                )
//                                                                                                                                        )
//                                                                                                                                )
//                                                                                                                        )
//                                                                                                                )
//                                                                                                        )
//                                                                                                )
//
//                                                                                        )
//
//                                                                                )
//                                                                        )
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        ))
//
//        )
//    }


    private ObservableList<IStatement> getCommands() throws Exception {
        IStatement ex1 = new CompStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new PrintStatement(new VariableExpression("v"))));


        IStatement ex3 = new CompStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompStatement(
                        new VariableDeclarationStatement("v", new IntegerType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        IStatement ex4 = new CompStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("input\\test.in"))),
                        new CompStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompStatement(
                                        new VariableDeclarationStatement("varc", new IntegerType()),
                                        new CompStatement(
                                                new readRFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(
                                                                new readRFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )

                )
        );

        IStatement ex5 = new CompStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
                new CompStatement(
                        new AssignStatement("a", new ValueExpression(new IntegerValue(8))),
                        new CompStatement(
                                new VariableDeclarationStatement("b", new IntegerType()),
                                new CompStatement(
                                        new AssignStatement("b", new ValueExpression(new IntegerValue(10))),
                                        new CompStatement(
                                                new VariableDeclarationStatement("min", new IntegerType()),
                                                new CompStatement(
                                                        new IfStatement(new RelationalExpression( new VariableExpression("a"), new VariableExpression("b"),"<"), new AssignStatement("min", new VariableExpression("a")), new AssignStatement("min", new VariableExpression("b"))),
                                                        new PrintStatement(new VariableExpression("min"))
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement ex6 = new CompStatement(
                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );

        IStatement ex7 = new CompStatement(
                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression( new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5)),1))
                                        )
                                )
                        )
                )
        );

        IStatement ex8 = new CompStatement(
                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntegerValue(30))),
                                        new PrintStatement(new ArithmeticExpression( new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5)),1))

                                )
                        )
                )
        );

        IStatement ex9 = new CompStatement(
                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

        IStatement ex10 = new CompStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(0)),">"),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(1)),2))
                                        )),
                                new PrintStatement(new VariableExpression("v"))

                        )
                )
        );

        IStatement ex11 = new CompStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntegerType())),
                        new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(
                                                                new HeapWriteStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )

                                                        )
                                                ),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        ObservableList<IStatement> commands = FXCollections.observableArrayList(example1(), example2(), example3(), example4(),
                example5(), example6(), example7(), example8(),example9() ,ex10, ex11,examplel15(),bcd());
        return commands;
    }



}

