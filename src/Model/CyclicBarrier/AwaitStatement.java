//package Model.CyclicBarrier;
//
//
//import Model.ADT.Dict;
//import Model.Exceptions.MyException;
//import Model.ProgramState;
//import Model.Statements.IStatement;
//import Model.Types.IType;
//import Model.Value.IValue;
//import Model.Value.IntegerValue;
//
//public class AwaitStatement implements IStatement {
//    private String var;
//
//    public AwaitStatement(String v){
//        this.var = v;
//    }
//
//    @Override
//    public ProgramState execute(ProgramState state) throws MyException {
//        if(state.getSymbolTable().get(this.var) == null)
//            return null;
//
//        IntegerValue index = (IntegerValue)state.getSymbolTable().get(this.var);
//        if(!state.getCyclicBarrierTable().contains(index.getValue()))
//            return null;
//        synchronized (state.getCyclicBarrierTable()){
//            if(state.getCyclicBarrierTable().get(index.getValue()).getFirst().equals(state.getCyclicBarrierTable().get(index.getValue()).getSecond()))
//                return null;
//            else
//            {
//                state.getExecutionStack().push(new AwaitStatement(var));
//                //state.getCyclicBarrierTable().get(index.getValue()).getSecond().add
//
//                return null;
//            }
//        }
//
//
//        //return null;
//    }
//
//    @Override
//    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
//        return null;
//    }
//}
