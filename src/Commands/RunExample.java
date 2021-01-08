package Commands;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunExample extends Command{
    private final Controller controller;
    public RunExample(String key, String description, Controller controller){
        super(key,description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            this.controller.allStep();
        }catch(MyException | InterruptedException exception){
            System.out.println("Run -> " + this.getKey() + " --->> has thrown an exception: " + exception.toString());
        }
    }
}
