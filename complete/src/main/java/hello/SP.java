package hello;

import java.util.List;

public class SP {

    private String name;
    private List<SPColumn> listOfColumns;

    public SP(String name){
        this.name = name;
    }

    public SP(String name, List<SPColumn> listOfColumns){
        this.name = name;
        this.listOfColumns = listOfColumns;
    }

    public void setName (String n){
        this.name = n;
    }

    public String getName (){
        return this.name;
    }

    public void setListOfColumns(List<SPColumn> listOfColumns){
        this.listOfColumns = listOfColumns;
    }

    public List<SPColumn> getListOfColumns(){
        return listOfColumns;
    }
}