//package View;
//
//import Commands.ExitCommand;
//import Commands.RunExample;
//import Controller.Controller;
//import Model.ADT.Dict;
//import Model.ADT.Heap;
//import Model.ADT.List;
//import Model.ADT.Stack;
//import Model.Exceptions.MyException;
//import Model.Expressions.*;
//import Model.ProgramState;
//import Model.Statements.*;
//import Model.Types.BooleanType;
//import Model.Types.IntegerType;
//import Model.Types.RefType;
//import Model.Types.StringType;
//import Model.Value.BooleanValue;
//import Model.Value.IValue;
//import Model.Value.IntegerValue;
//import Model.Value.StringValue;
//import Repo.Repo;
//import com.sun.jdi.Value;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//
//public class main {
//    Controller controller;
//
//    public main(Controller c) {this.controller = c;}
//
//    static IStatement example1(){
//        return new CompStatement(new VariableDeclarationStatement("v",new IntegerType()),
//                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new PrintStatement(new VariableExpression("v"))));
//    }
//    static IStatement example2(){
//        return new CompStatement(
//                new VariableDeclarationStatement("a",new IntegerType()),
//                new CompStatement(
//                        new VariableDeclarationStatement("b", new IntegerType()),
//                        new CompStatement(
//                                new AssignStatement(
//                                        "a",
//                                        new ArithmeticExpression(
//                                                new ValueExpression(new IntegerValue(2)),
//                                                new ArithmeticExpression(new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(0)), 4),
//                                                1)
//                                ),
//                                new CompStatement(
//                                        new AssignStatement("b", new ArithmeticExpression( new VariableExpression("a"), new ValueExpression(new IntegerValue(1)),1)),
//                                        new PrintStatement(new VariableExpression("b"))
//                                )
//                        )
//                )
//        );
//    }
//
//    static IStatement example3(){
//        return new CompStatement(
//                new VariableDeclarationStatement("a", new BooleanType()),
//                new CompStatement(
//                        new VariableDeclarationStatement("v", new IntegerType()),
//                        new CompStatement(
//                                new AssignStatement("a", new ValueExpression(new BooleanValue(true))),
//                                new CompStatement(
//                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))),
//                                        new PrintStatement(new VariableExpression("v"))
//                                )
//                        )
//                )
//        );
//    }
//
//    static IStatement example4(){
//        return new CompStatement(
//                new VariableDeclarationStatement("fileVariable",new StringType()),
//                new CompStatement(
//                        new AssignStatement("fileVariable", new ValueExpression(new StringValue("test.in"))),
//                        new CompStatement(
//                                new OpenRFileStatement(new VariableExpression("fileVariable")),
//                                new CompStatement(
//                                        new VariableDeclarationStatement("var", new IntegerType()),
//                                        new CompStatement(
//                                                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
//                                                new CompStatement(
//                                                        new PrintStatement(new VariableExpression("var")),
//                                                        new CompStatement(
//                                                                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
//                                                                new CompStatement(
//                                                                        new PrintStatement(new VariableExpression("var")),
//                                                                        new CloseRFileStatement(new VariableExpression("fileVariable"))
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//    }
//
//    static IStatement example5(){
//        return ProgramFromList.create(Arrays.asList(new VariableDeclarationStatement("fileVariable", new StringType()),
//                new AssignStatement("fileVariable", new ValueExpression(new StringValue("test.in"))),
//                new OpenRFileStatement(new VariableExpression("fileVariable")),
//                new VariableDeclarationStatement("var", new IntegerType()),
//                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
//                new PrintStatement(new VariableExpression("var")),
//                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
//                new PrintStatement(new VariableExpression("varc")),
//                new readRFileStatement(new VariableExpression("fileVariable"), "var"),
//                new PrintStatement(new VariableExpression("var"))
//                )
//        );
//    }
//
//    static IStatement example6(){
//        return new CompStatement(new VariableDeclarationStatement( "v",new IntegerType()) ,
//                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
//                        new WhileStatement(new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(0)),">"),
//                                new CompStatement(
//                                        new PrintStatement(new VariableExpression("v")),
//                                        new AssignStatement("v", new ArithmeticExpression(
//                                                new VariableExpression("v"),
//                                                new ValueExpression(new IntegerValue(1)),
//                                                2))
//                                ))));
//    }
//
//    static IStatement example7(){
//        VariableDeclarationStatement v = new VariableDeclarationStatement("v", new RefType(new IntegerType()));
//        HeapAllocationStatement heapAllocStmt = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
//        VariableDeclarationStatement rdc = new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType())));
//        HeapAllocationStatement rfc = new HeapAllocationStatement("a",new VariableExpression("v"));
//        HeapAllocationStatement rfc1 = new HeapAllocationStatement("v",new ValueExpression(new IntegerValue(30)));
//        CompStatement cms = new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))));
//        return new CompStatement(v ,new CompStatement(heapAllocStmt, new CompStatement(rdc, new CompStatement(rfc, new CompStatement(rfc1, cms)))));
//
//    }
//
//    static IStatement example8(){
//        return new CompStatement(
//                new VariableDeclarationStatement("v",new RefType(new IntegerType())),
//                new CompStatement(
//                                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
//                                new CompStatement(
//                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
//                                        new CompStatement(
//                                                new HeapWriteStatement("v", new ValueExpression(new IntegerValue(30))),
//                                                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5)), 1))))));
//    }
//
//    static IStatement example9(){
//        return new CompStatement(
//                new VariableDeclarationStatement("v",new IntegerType()),
//                new CompStatement(
//                        new VariableDeclarationStatement("a",new RefType(new IntegerType())),
//                        new CompStatement(
//                                new AssignStatement("v",new ValueExpression(new IntegerValue(10))),
//                                new CompStatement(
//                                        new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
//                                        new CompStatement(
//                                                new ForkStatement(
//                                                new CompStatement(
//                                                        new HeapWriteStatement("a", new ValueExpression(new IntegerValue(30))),
//                                                        new CompStatement(
//                                                                new AssignStatement("v", new ValueExpression(new IntegerValue(32))),
//                                                                new CompStatement(
//                                                                        new PrintStatement(new VariableExpression("v")),
//new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
//                                                                )
//                                                        )
//                                                )
//                                                ),
//                                                new CompStatement(
//                                                        new PrintStatement(new VariableExpression("v")),
//                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
//                                                )
//                                        )
//                                )
//                        ))
//        );
//    }
//
//
//
//    public static void main(String[] args) throws Exception {
//        Heap<Integer, IValue> bigBoyHeap = new Heap<Integer,IValue>();
//        IStatement ex1 = example1();
//        ProgramState program1 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex1, new Dict<>(), bigBoyHeap);
//        Repo repo1 = new Repo("log1.txt", program1);
//        Controller controller1 = new Controller(repo1, true);
//        IStatement ex2 = example2();
//        ProgramState program2 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex2, new Dict<>(), bigBoyHeap);
//        Repo repo2 = new Repo("log2.txt", program2);
//        Controller controller2 = new Controller(repo2, true);
//        IStatement ex3 = example3();
//        ProgramState program3 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex3, new Dict<>(), bigBoyHeap);
//        Repo repo3 = new Repo("log3.txt", program3);
//        Controller controller3 = new Controller(repo3, true);
//        IStatement ex4 = example4();
//        ProgramState program4 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex4, new Dict<>(), bigBoyHeap);
//        Repo repo4 = new Repo("log4.txt", program4);
//        Controller controller4 = new Controller(repo4, true);
//        IStatement ex5 = example5();
//        ProgramState program5 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex5, new Dict<>(), bigBoyHeap);
//        Repo repo5 = new Repo("log5.txt", program5);
//        Controller controller5 = new Controller(repo5, true);
//        IStatement ex6 = example6();
//        ProgramState program6 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex6, new Dict<>(), bigBoyHeap);
//        Repo repo6 = new Repo("log6.txt", program6);
//        Controller controller6 = new Controller(repo6, true);
//        IStatement ex7 = example7();
//        ProgramState program7 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex7, new Dict<>(), bigBoyHeap);
//        Repo repo7 = new Repo("log7.txt", program7);
//        Controller controller7 = new Controller(repo7, true);
//        IStatement ex8 = example8();
//        ProgramState program8 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex8, new Dict<>(), bigBoyHeap);
//        Repo repo8 = new Repo("log8.txt", program8);
//        Controller controller8 = new Controller(repo8, true);
//        IStatement ex9 = example9();
//        ProgramState program9 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex9, new Dict<>(), bigBoyHeap);
//        Repo repo9= new Repo("log9.txt", program9);
//        Controller controller9 = new Controller(repo9, true);
//
//        IStatement ex15 = new CompStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(20))),
//                        new CompStatement(new ForStatemtnt("v", new ValueExpression(new IntegerValue(0)), new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(3)),"!="), new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 1),
//                                new ForkStatement(new CompStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), 1))))),
//                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3)))));
//
//        ProgramState program15 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex15, new Dict<>(), bigBoyHeap);
//        Repo repo15= new Repo("log15.txt", program15);
//
//
//        Controller controller15 = new Controller(repo15, true);
//        IStatement ex16 = new CompStatement(new VariableDeclarationStatement("v",new IntegerType()),
//                new CompStatement((new AssignStatement("v",new ValueExpression((new IntegerValue(5))))),
//                        new RepeatUntilStatement(new AssignStatement("v", new ArithmeticExpression(
//                                new VariableExpression("v"),
//                                new ValueExpression(new IntegerValue(1)),
//                                2)),new RelationalExpression( new VariableExpression("v"), new ValueExpression(new IntegerValue(0)),"!="))));
//
//
//
//        ProgramState program16 = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), ex16, new Dict<>(), bigBoyHeap);
//        Repo repo16= new Repo("log16.txt", program16);
//
//
//        Controller controller16 = new Controller(repo16, true);
//        try{
//
//            ex6.typecheck(new Dict<>());
//            ex7.typecheck(new Dict<>());
//            ex8.typecheck(new Dict<>());
//            ex9.typecheck(new Dict<>());
//        }catch(MyException e){
//            System.out.println(e.getMessage());
//        }
//
//
//        TextMenu menu =  new TextMenu();
//        menu.addCommand(new ExitCommand("0","exit"));
//        menu.addCommand(new RunExample("1",ex1.toString(),controller1));
//        menu.addCommand(new RunExample("2",ex2.toString(),controller2));
//        menu.addCommand(new RunExample("3",ex3.toString(),controller3));
//        menu.addCommand(new RunExample("4",ex4.toString(),controller4));
//        menu.addCommand(new RunExample("5",ex5.toString(),controller5));
//        menu.addCommand(new RunExample("6",ex6.toString(),controller6));
//        menu.addCommand(new RunExample("7",ex7.toString(),controller7));
//        menu.addCommand(new RunExample("8",ex8.toString(),controller8));
//        menu.addCommand(new RunExample("9",ex9.toString(),controller9));
//        menu.addCommand(new RunExample("15",ex15.toString(),controller15));
//        menu.addCommand(new RunExample("16",ex16.toString(),controller16));
//        menu.show();//comment for creation <3
//        /*try{
//            controller.allStep();
//        }
//        catch (MyException e){
//            System.out.println(e.toString());
//        }
//        System.out.println(controller.getMainProgramState().originalProgramState.toString());*/
//    }
//}