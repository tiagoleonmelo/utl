import java.util.*;

public class Column extends Symbol{

  public ArrayList<Item> items = new ArrayList<>();
  public String name;

  public Column(String s, Type t){
    super(s,t);
  }

  public Column(String s, Type t, String name){
    super(s,t);
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public boolean contains(String s){
    for(Item c : this.items){
      if(c.getValue().equals(s)){
        return true;
      }
    }
    return false;
  }
  public Item get(String s){
    for(Item i : this.items){
      if(i.getValue().equals(s)){
        return i;
      }
    }
    return null;
  }
  public Item get(int idx){
    return this.items.get(idx);
  }

  public int size(){
    return this.items.size();
  }

  public void add(Item i){
    this.items.add(i);
  }

  public boolean delete(String s){
    for(Item i: this.items){
      if(i.getValue().equals(s)){
        this.items.remove(i);
        return true;
      }
    }
    return false;
  }
  public void setName(String name){
    this.name=name;
  }
  public String toString(){
    return  "Coluna-> " + this.name + ":" + this.items.toString();
  }
}
