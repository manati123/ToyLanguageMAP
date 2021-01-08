package Model.Exceptions;

public class MyException extends Exception {
    String exceptionMessage;
    public MyException(String _m) {this.exceptionMessage = _m;}

    @Override
    public String toString(){
        return "Exception is -> " + exceptionMessage + "...";
    }
}
