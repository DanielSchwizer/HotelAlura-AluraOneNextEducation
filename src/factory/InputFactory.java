package factory;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class InputFactory {
    private List<JTextField> instances = new ArrayList<>();

    public void SetInput(JTextField input){
       instances.add(input);
    }

    public List<JTextField> getInstances() {
        return instances;
    }
}
