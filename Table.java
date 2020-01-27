import java.util.*;

public class Table extends Symbol{

  public ArrayList<Column> colunas = new ArrayList<>();

  public Table(String s, Type t){
    super(s,t);
  }

  public void addColumn(Column c){
    assert c != null;

    colunas.add(c);
  }

  public boolean contains(String s){
    for(Column c : this.colunas){
      if(c.getName().equals(s)){
        return true;
      }
    }
    return false;
  }

  public Column get(String s){
    for(Column c : this.colunas){
      if(c.getName().equals(s)){
        return c;
      }
    }
    return null;
  }

  public Column get(int s){
    return this.colunas.get(s);
  }

  public int size(){
    return this.colunas.size();
  }
  public boolean delete(String s){
    for(Column c: this.colunas){
      if(c.getName().equals(s)){
        colunas.remove(c);
        return true;
      }
    }
    return false;
  }
  public String getName(){
    return name();
  }
  public String toString(){
    return name() + ":" + this.colunas.toString();
  }
}
