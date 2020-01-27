import java.util.*;

public class Item extends Symbol{

  private String value;

  public Item(String s, Type t){
    super(s,t);
  }

  public Item(String var_name, Type t, String value){
    super(var_name,t);
    this.value=value;
  }
  public void setValue(String value){
    this.value=value;
  }
  public String getValue(){
    return this.value;
  }
  public String toString(){
    return value;
  }





}
