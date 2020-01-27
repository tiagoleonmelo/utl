import java.util.Scanner;

public class Symbol {
  protected String name;
  protected Type type;

  public Symbol(String name, Type type){

    //assert name != null && !name.isEmpty();
    assert type != null;

    this.name = name;
    this.type = type;
  }

  public String name(){
    return name;
  }

  public Type type(){
    return type;
  }

  public String toString(){
    return name;
  }
  public void setName(String name){
    this.name=name;
  }

  public String getName(){
    return this.getName() ;
  }

}
