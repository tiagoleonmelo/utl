import java.util.*;

public class SymTable{

    // public SymTable parent;
    protected HashMap<String, Symbol> table;

    public SymTable(){
      this.table = new HashMap<>();
    }

    public boolean exists(String name){
      assert name != null;
      return table.containsKey(name);
    }

    public Symbol get(String name) {
      assert exists(name);
      return table.get(name);
    }

    public void add(Symbol s){
      assert s != null;
      assert !table.containsKey(s.name());

      table.put(s.name(), s);
    }
    public void delete(Symbol s){
      assert s!=null;
      assert table.containsKey(s.name());

      table.remove(s.name(),s);
    }

}
