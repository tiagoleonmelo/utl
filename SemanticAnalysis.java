// Generated from UTL.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import java.util.*;
import java.io.*;
/**
 * This class provides an empty implementation of {@link UTLVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class SemanticAnalysis<Boolean> extends  UTLBaseVisitor<Boolean> {
	public SymTable sym_t = new SymTable();
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Boolean visitR(UTLParser.RContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Boolean visitDeclaration(UTLParser.DeclarationContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Boolean visitAssignment(UTLParser.AssignmentContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Boolean visitOperation(UTLParser.OperationContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Boolean visitPrint(UTLParser.PrintContext ctx) {
	String key =ctx.tableVar().getText();
	if(!sym_t.exists(key)){
		System.out.println("Variable doesn't exist, shutting down");
		System.exit(1);
	}

	return visitChildren(ctx); }

	 @Override public Boolean visitTableDeclareBase(UTLParser.TableDeclareBaseContext ctx) {
 		String var = ctx.tableVar().getText();

 		if(sym_t.exists(var)){
			 System.out.println("Error: Variable " + var + " already exists, shutting down");
			 System.exit(1);
 		}

 		// Create a new table and add every column to it

 		Table t = new Table(var, Type.table);

 		sym_t.add(t);
		int i=0;
		while(true){
			if(ctx.columnDefinition(i)!=null){
				if (ctx.columnDefinition(i).getText().contains("->")){//colfetch
					String res[]=ctx.columnDefinition(i).getText().split("->");
					String var1=res[1];
					String name1=res[0];

					if(!sym_t.exists(var1)){
						System.out.println("Error: Variable "+var1+" doesnt exist, shutting down");
					   System.exit(1);
					}else if(sym_t.get(var1).type() != Type.column){
						System.out.println("Error: Variable " + var1 + " is of Incompatible type, shutting down");
					   System.exit(1);
					}
					Column col1 = new Column("",Type.column,name1);
					if(sym_t.exists(var1)){
						Column c1 =(Column)sym_t.get(var1);
						for(int i2=0; i2<c1.size();i2++){
							col1.add(c1.get(i2));
						}
					}
					t.addColumn(col1);

				}
				else if (!ctx.columnDefinition(i).getText().contains(":")){ //colempty alt
					String col2=ctx.columnDefinition(i).getText();
					Column c2=new Column("",Type.column,col2);
					t.addColumn(c2);
				}
				else{ //colBase
					String col_item[]=ctx.columnDefinition(i).getText().split(":");
					String col=col_item[0];
					String itens[]=col_item[1].split(",");
					Column c= new Column("", Type.column,col);
					for(int l=0; l<itens.length;l++){
						c.add(new Item("",Type.item, (String)itens[l]));
					}
					t.addColumn(c);
				}
				
				i++;
			}
			else{
				break;
			}

		}

 		return visitChildren(ctx);

 	}

 	@Override public Boolean visitTableDeclareJoining(UTLParser.TableDeclareJoiningContext ctx) {
 		String var = ctx.tableVar(0).getText();
 		String t1 = ctx.tableVar(1).getText();
 		String t2 = ctx.tableVar(2).getText();

 		if(sym_t.exists(var)){
			 System.out.println("Error: Variable "+var+" already exists, shutting down");
			 System.exit(1);
 		}

 		if(sym_t.exists(t1) && sym_t.exists(t2)){
 			if(sym_t.get(t1).type() == Type.table && sym_t.get(t2).type() == Type.table){
 				sym_t.add(new Table(var, Type.table));
 				//System.out.println("Var "+var+" added successfully");
 			}else{
				 System.out.println("Error: Incompatible types of operands for join(), shutting down");
				 System.exit(1);
 			}
 		}else{
 			if(sym_t.exists(t1)){
 				System.out.println("Error: table " + t2 + " hasn't been declared, shutting down");
				System.exit(1);
 			}else{
 				System.out.println("Error: table " + t1 + " hasn't been declared, shutting down");
				System.exit(1);
 			}
 		}
		Table table1=(Table)sym_t.get(t1);
		Table table2=(Table)sym_t.get(t2);
		Table newTable=(Table)sym_t.get(var);

		for(int c=0; c<table1.size();c++){
			for(int ctwo=0; ctwo<table2.size();ctwo++){
				if((table1.get(c).getName()).equals(table2.get(ctwo).getName())){
					newTable.addColumn(table1.get(c));
					newTable.addColumn(table2.get(ctwo));
					break;
				}
			}
		}
		if (newTable.size()==0){
			//System.out.println("Não havia colunas iguais, a tabela nao foi criada");
			sym_t.delete(newTable);
		}
 		return visitChildren(ctx);

 	}

 	@Override public Boolean visitColDeclareOne(UTLParser.ColDeclareOneContext ctx) {

 		String var_name = ctx.columnVar().getText();
 		if(sym_t.exists(var_name)){
			 System.out.println("Error: Variable " + var_name + " already exists, shutting down");
			 System.exit(1);
 		}

 		String table_var_name = ctx.tableVar().getText();
 		if(!sym_t.exists(table_var_name)){
 			System.out.println("Error: Variable " + table_var_name + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(table_var_name).type() != Type.table){
 			System.out.println("Error: Variable " + table_var_name + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Table tb= (Table) sym_t.get(table_var_name);
		Column newCol =new Column(var_name, Type.column,"");
 		if(ctx.col_access().column() != null){

			String coluna=ctx.col_access().column().getText();
			Column col=tb.get(coluna);

			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					int idx=Integer.parseInt(ctx.item_access().index().getText());
					Item ite=col.get(idx);
					newCol.add(ite);
				}
				else if(ctx.item_access().item()!=null){
					String it=ctx.item_access().item().getText();
					Item oldIte=col.get(it);
					newCol.add(oldIte);
				}
			}
			else if(ctx.indexRange()!=null){
				int f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				int l=Integer.parseInt(ctx.indexRange().INT(1).getText());
				for(int j=f;j<l;j++){
					Item item=col.get(j);
					newCol.add(item);
				}
			}

 		}
		else if(ctx.col_access().index()!=null){
			int index_col=Integer.parseInt(ctx.col_access().index().getText());
			Column idx_col=tb.get(index_col);
			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					int idx=Integer.parseInt(ctx.item_access().index().getText());
					Item ite=idx_col.get(idx);
					newCol.add(ite);
				}
				else if(ctx.item_access().item()!=null){
					String it=ctx.item_access().item().getText();
					Item oldIte=idx_col.get(it);
					newCol.add(oldIte);
				}
			}
			else if(ctx.indexRange()!=null){
				int f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				int l=Integer.parseInt(ctx.indexRange().INT(1).getText());
				for(int j=f;j<l;j++){
					Item item=idx_col.get(j);
					newCol.add(item);
				}
			}

		}

		sym_t.add(newCol);
		//System.out.println(newCol);
 		//System.out.println("Var "+var_name+" added successfully");

 		return visitChildren(ctx);

 	}

 	@Override public Boolean visitColDeclareTwo(UTLParser.ColDeclareTwoContext ctx) {

 		String var_name = ctx.columnVar().getText();
 		if(sym_t.exists(var_name)){
 			System.out.println("Error: Variable " + var_name + " already exists, shutting down");
			System.exit(1);
 		}

 		String table_var_name = ctx.tableVar().getText();
 		if(!sym_t.exists(table_var_name)){
 			System.out.println("Error: Variable " + table_var_name + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(table_var_name).type() != Type.table){
 			System.out.println("Error: Variable " + table_var_name + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Table t=(Table)sym_t.get(table_var_name);
		Column newCol=new Column(var_name, Type.column,"");
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				Column getC=t.get(ctx.col_access().column().getText());
				for(int i=0; i<getC.size();i++){
					newCol.add(getC.get(i));
				}
			}
			else if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column getCol=t.get(idx);
				for(int j=0; j<getCol.size();j++){
					newCol.add(getCol.get(j));
				}
			}
		}
 		sym_t.add(newCol);

 		return visitChildren(ctx);

 	}

 	@Override public Boolean visitColDeclareThree(UTLParser.ColDeclareThreeContext ctx) {
		 String var = ctx.columnVar().getText();

		if(sym_t.exists(var)){
			System.out.println("Error: Variable " + var + " already exists, shutting down");
			System.exit(1);
		}


		Column t = new Column(var,Type.column);

		sym_t.add(t);

			   if (ctx.columnDefinition().getText().contains("->")){//colfetch
				   String res[]=ctx.columnDefinition().getText().split("->");
				   String var1=res[1];
				   String name1=res[0];

				   if(!sym_t.exists(var1)){
					   System.out.println("Error: Variable "+var1+" doesnt exist, shutting down");
					  System.exit(1);
				   }else if(sym_t.get(var1).type() != Type.column){
					   System.out.println("Error: Variable " + var1 + " is of Incompatible type, shutting down");
					  System.exit(1);
				   }
				   Column col1 = new Column("",Type.column,name1);
				   if(sym_t.exists(var1)){
					   Column c1 =(Column)sym_t.get(var1);
					   for(int i2=0; i2<c1.size();i2++){
						   col1.add(c1.get(i2));
					   }
				   }

			   }
			   else if (!ctx.columnDefinition().getText().contains(":")){ //colempty alt
				   String col2=ctx.columnDefinition().getText();
				   Column c2=new Column("",Type.column,col2);
			   }
			   else{ //colBase
				   String col_item[]=ctx.columnDefinition().getText().split(":");
				   String col=col_item[0];
				   String itens[]=col_item[1].split(",");
				   Column c= new Column("", Type.column,col);
				   for(int l=0; l<itens.length;l++){
					   c.add(new Item("",Type.item, (String)itens[l]));
				   }
			   }

		return visitChildren(ctx);

	}

 	@Override public Boolean visitColBase(UTLParser.ColBaseContext ctx) {

 		// We need to verify the existence of every itemVar that might be referenced
 		int i = 0;
 		while(true){
 			if(ctx.itemVar(i) != null){
 				String var = ctx.itemVar(i).getText();
 				if(!sym_t.exists(var)){
 					System.out.println("Error: Variable "+ var + " doesnt exist, shutting down");
					System.exit(1);
 				}else if(sym_t.get(var).type() != Type.item){
 					System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
					System.exit(1);
 				}
 			}

 			i = i + 1;
 			if(ctx.item(i) == null && ctx.itemVar(i) == null){
 				break;
 			}
 		}
		Column c= new Column("",Type.column,ctx.column().getText());
		int j=0;
		int l=0;
		//get only value
		while(true){
			if(ctx.item(j)!=null){
				Item item = new Item("",Type.item,(String)ctx.item(j).getText());
				c.add(item);
				//System.out.println(item.getValue() + " adicionado a "+ c.name());
				j++;
			}
			//get a item already defined
			else if(ctx.itemVar(l)!=null){
				if(sym_t.exists(ctx.itemVar(l).getText()) && sym_t.get(ctx.itemVar(l).getText()).type()==Type.item){
					Item old= (Item)sym_t.get(ctx.itemVar(l).getText());
					Item it= new Item(ctx.itemVar(l).getText(), Type.item, old.getValue());
					//System.out.println(it.toString());
					c.add(it);
					//System.out.println(it.name() + " com o valor " + it.getValue() + " adicionado a " + ctx.column().getText());
					l++;
			}

			}

			else{
				break;
			}

			}
			//System.out.println(c.name() + c.size());

 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitColFetch(UTLParser.ColFetchContext ctx) {

 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitColEmpty(UTLParser.ColEmptyContext ctx) {
 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitColumnNameDec(UTLParser.ColumnNameDecContext ctx) {
 		String item_var = ctx.itemVar().getText();
 		if(sym_t.exists(item_var)){
 			System.out.println("Error: Variable "+ item_var + " already exists, shutting down");
			System.exit(1);
 		}

 		String table_var = ctx.tableVar().getText();
 		if(!sym_t.exists(table_var)){
 			System.out.println("Error: Variable "+ table_var + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(table_var).type()!= Type.table){
 			System.out.println("Error: Variable " + table_var + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Item x=new Item(item_var, Type.item,"");
		Table tb= (Table) sym_t.get(table_var);
		String s;
		int t;
		String v;
		if(ctx.col_access().column()!=null){
			s=ctx.col_access().column().getText();
			v=tb.get(s).getName();
		}else{
			t=Integer.parseInt(ctx.col_access().index().getText());
			v=tb.get(t).getName();
		}
		x.setValue(v);
 		sym_t.add(x);
 		//System.out.println("Var "+item_var+" added successfully");

 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitItemDeclTable(UTLParser.ItemDeclTableContext ctx) {
 		String item_var = ctx.itemVar().getText();

 		if(sym_t.exists(item_var)){
 			System.out.println("ERROR: Variable "+item_var+" already exists, shutting down");
			System.exit(1);
 		}

 		if(ctx.tableVar() != null){ // Avoiding null ptr exception
 			String var = ctx.tableVar().getText(); // Fetching name of the table var and then checking if it exists
 			if(sym_t.exists(var)){

 				// check if this table var can support this col_access
 				Table t = (Table) sym_t.get(var); // Casting since polymorphism is great but only some times

 				if(ctx.col_access().column() != null){ // Avoiding null ptr exception

 					if(t.contains(ctx.col_access().column().getText())){ // If table contains a column with that name

 						// check if this col_access can support this item_access
 						String col_name = ctx.col_access().column().getText();
 						Column c = (Column) t.get(col_name); // Applying same logic as above but this time with a column

 						if(ctx.item_access().item() != null){

 									if(c.contains(ctx.item_access().item().getText())){

 										String value = ctx.item_access().item().getText(); // Fetching the value
 										sym_t.add(new Item(item_var, Type.item, value)); // Adding this item to the symbol table
 										//System.out.println(item_var + " was saved successfully");
 										return visitChildren(ctx); // We can return now, this functions goal has been completed

 									}else{
 										System.out.println("Error: That item doesnt exist, shutting down");
										System.exit(1);
 									}

 						}else{

 									int col_idx = Integer.parseInt(ctx.item_access().index().getText());
 									if(c.size() > col_idx){

 										String value = c.get(col_idx).getValue(); // Fetching the value with overloading
 										sym_t.add(new Item(item_var, Type.item, value)); // Adding this item to the symbol table
 										//System.out.println(item_var + " was saved successfully");
 										return visitChildren(ctx); // We can return now, this functions goal has been completed

 									}else{
 										System.out.println("Error: That item doesnt exist, shutting down");
										System.exit(1);
 									}

 						}

 					}else{
 						System.out.println("Error: This table doesnt contain any column with that name, shutting down");
						System.exit(1);
 					}

 				}else{
 					String indx = ctx.col_access().index().getText();
 					int index = Integer.parseInt(indx);
 					if(t.size() > index){ // If index is within the table range

 						Column c = (Column) t.get(index); // Getting the column with overloading

 						if(ctx.item_access().item() != null){

 									if(c.contains(ctx.item_access().item().getText())){

 										String value = ctx.item_access().item().getText(); // Fetching the value
 										sym_t.add(new Item(item_var, Type.item, value)); // Adding this item to the symbol table
 										//System.out.println(item_var + " was saved successfully");
 										return visitChildren(ctx); // We can return now, this functions goal has been completed

 									}else{
 										System.out.println("Error: That item doesnt exist, shutting down");
										System.exit(1);
 									}

 						}else{

 									int col_idx = Integer.parseInt(ctx.item_access().index().getText());
 									if(c.size() > col_idx){

 										String value = c.get(col_idx).getValue(); // Fetching the value with overloading
 										sym_t.add(new Item(item_var, Type.item, value)); // Adding this item to the symbol table
 										//System.out.println(item_var + " was saved successfully");
 										return visitChildren(ctx); // We can return now, this functions goal has been completed

 									}else{
 										System.out.println("Error: That item doesnt exist, shutting down");
										System.exit(1);
 									}

 						}

 					}else{
 						System.out.println("Error: This table doesnt contain any column with that name, shutting down");
						System.exit(1);
 					}
 				}

 		}else{
 			System.out.println("Error: That table doesnt exist, shutting down");
			System.exit(1);
 		}
 	}
 		return visitChildren(ctx);
	 }
	 @Override public Boolean visitItemDeclStr(UTLParser.ItemDeclStrContext ctx) {
		String item_var = ctx.itemVar().getText();
		String s=ctx.item().getText();

		if(sym_t.exists(item_var)){
			System.out.println("ERROR: Variable "+item_var+" already exists, shutting down");
			System.exit(1);
		}

		if(ctx.item() != null){
			sym_t.add(new Item(item_var, Type.item, ctx.item().getText())); // Adding this item to the symbol table
			//System.out.println(item_var + " was saved successfully with the value of " + ctx.item().getText());
			return visitChildren(ctx); // We can return now, this functions goal has been completed
		}
		Item x=new Item(item_var, Type.item,s);
		sym_t.add(x);
		return visitChildren(ctx);
	}
	@Override public Boolean visitItemDeclVar(UTLParser.ItemDeclVarContext ctx) {
		String item_var = ctx.v1.getText();
		String item_other=ctx.v2.getText();

		if(sym_t.exists(item_var)){
			System.out.println("ERROR: Variable "+item_var+" already exists, shutting down");
			System.exit(1);
		}
		if(!sym_t.exists(item_other)){
			System.out.println("ERROR: Variable "+item_other+" doesn't exist, shutting down");
			System.exit(1);
		}
		return visitChildren(ctx);
		}

 	@Override public Boolean visitTableAssignBase(UTLParser.TableAssignBaseContext ctx) {
 		String var = ctx.tableVar().getText();
		if(!sym_t.exists(var)){
			System.out.println("ERROR: Variable "+var+" doesnt exist, shutting down");
			System.exit(1);
		}
 		if(sym_t.get(var).type()!= Type.table){
 			System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
			System.exit(1);
		 }
		Table x = (Table) sym_t.get(var);
		sym_t.delete(x);
		Table t = new Table(var, Type.table);
		int i=0;
		while(true){
			if(ctx.columnDefinition(i)!=null){
				if (ctx.columnDefinition(i).getText().contains("->")){//colfetch
					String res[]=ctx.columnDefinition(i).getText().split("->");
					String var1=res[1];
					String name1=res[0];

					if(!sym_t.exists(var1)){
						System.out.println("Error: Variable "+var1+" doesnt exist, shutting down");
					   System.exit(1);
					}else if(sym_t.get(var1).type() != Type.column){
						System.out.println("Error: Variable " + var1 + " is of Incompatible type, shutting down");
					   System.exit(1);
					}
					Column col1 = new Column("",Type.column,name1);
					if(sym_t.exists(var1)){
						Column c1 =(Column)sym_t.get(var1);
						for(int i2=0; i2<c1.size();i2++){
							col1.add(c1.get(i2));
						}
					}
					t.addColumn(col1);

				}
				else if (!ctx.columnDefinition(i).getText().contains(":")){ //colempty alt
					String col2=ctx.columnDefinition(i).getText();
					Column c2=new Column("",Type.column,col2);
					t.addColumn(c2);
				}
				else{ //colBase
					String col_item[]=ctx.columnDefinition(i).getText().split(":");
					String col=col_item[0];
					String itens[]=col_item[1].split(",");
					Column c= new Column("", Type.column,col);
					for(int l=0; l<itens.length;l++){
						c.add(new Item("",Type.item, (String)itens[l]));
					}
					t.addColumn(c);
				}
				
				i++;
			}
			else{
				break;
			}

		}
		sym_t.add(t);
 		return visitChildren(ctx);
	 }
	 @Override public Boolean visitTableAssignJoining(UTLParser.TableAssignJoiningContext ctx) {
		String var = ctx.tableVar(0).getText();
 		String t1 = ctx.tableVar(1).getText();
 		String t2 = ctx.tableVar(2).getText();

 		if(!sym_t.exists(var)){
			 System.out.println("Error: Variable "+var+" doesn't exist, shutting down");
			 System.exit(1);
 		}

 		if(sym_t.exists(t1) && sym_t.exists(t2)){
 			if(sym_t.get(t1).type() == Type.table && sym_t.get(t2).type() == Type.table){
 			}else{
				 System.out.println("Error: Incompatible types of operands for join(), shutting down");
				 System.exit(1);
 			}
 		}else{
 			if(sym_t.exists(t1)){
 				System.out.println("Error: table " + t2 + " hasn't been declared, shutting down");
				System.exit(1);
 			}else{
 				System.out.println("Error: table " + t1 + " hasn't been declared, shutting down");
				System.exit(1);
 			}
 		}
		Table table1=(Table)sym_t.get(t1);
		Table table2=(Table)sym_t.get(t2);
		Table newTable=(Table)sym_t.get(var);

		for(int c=0; c<table1.size();c++){
			for(int ctwo=0; ctwo<table2.size();ctwo++){
				if((table1.get(c).getName()).equals(table2.get(ctwo).getName())){
					newTable.addColumn(table1.get(c));
					newTable.addColumn(table2.get(ctwo));
					break;
				}
			}
		}
		if (newTable.size()==0){
			//System.out.println("Não havia colunas iguais, a tabela nao foi criada");
			sym_t.delete(newTable);
		}
 		return visitChildren(ctx);
	}

 	@Override public Boolean visitColAssignOne(UTLParser.ColAssignOneContext ctx) {

 		String var = ctx.columnVar().getText();
		 if(!sym_t.exists(var)){
			System.out.println("ERROR: Variable "+var+" doesnt exist, shutting down");
			System.exit(1);
		}
 		if(sym_t.get(var).type()!= Type.column){
 			System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		String table_var_name = ctx.tableVar().getText();

 		if(!sym_t.exists(table_var_name)){
 			System.out.println("Error: Variable " + table_var_name + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(table_var_name).type()!= Type.table){
 			System.out.println("Error: Variable " + table_var_name + " is of Incompatible type, shutting down");
			System.exit(1);
		 }
		 Table t=(Table)sym_t.get(table_var_name);
		 sym_t.delete(sym_t.get(var));
		Column newCol=new Column(var, Type.column,"");
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				Column getC=t.get(ctx.col_access().column().getText());
				for(int i=0; i<getC.size();i++){
					newCol.add(getC.get(i));
				}
			}
			else if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column getCol=t.get(idx);
				for(int j=0; j<getCol.size();j++){
					newCol.add(getCol.get(j));
				}
			}
		}
 		sym_t.add(newCol);

 		return visitChildren(ctx);
	 }
	 @Override public Boolean visitColAssignTwo(UTLParser.ColAssignTwoContext ctx) {

		String var_name = ctx.columnVar().getText();
		if(!sym_t.exists(var_name)){
			System.out.println("ERROR: Variable "+var_name+" doesnt exist, shutting down");
			System.exit(1);
		}
 		if(sym_t.get(var_name).type()!= Type.column){
 			System.out.println("Error: Variable " + var_name + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		String table_var_name = ctx.tableVar().getText();
 		if(!sym_t.exists(table_var_name)){
 			System.out.println("Error: Variable " + table_var_name + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(table_var_name).type() != Type.table){
 			System.out.println("Error: Variable " + table_var_name + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Table t=(Table)sym_t.get(table_var_name);
		sym_t.delete(sym_t.get(var_name));
		Column newCol=new Column(var_name, Type.column,"");
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				Column getC=t.get(ctx.col_access().column().getText());
				for(int i=0; i<getC.size();i++){
					newCol.add(getC.get(i));
				}
			}
			else if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column getCol=t.get(idx);
				for(int j=0; j<getCol.size();j++){
					newCol.add(getCol.get(j));
				}
			}
		}
 		sym_t.add(newCol);

 		return visitChildren(ctx);
	}
	@Override public Boolean visitColAssignThree(UTLParser.ColAssignThreeContext ctx) {

		String var_name = ctx.columnVar().getText();

		if(!sym_t.exists(var_name)){
			System.out.println("ERROR: Variable "+var_name+" doesnt exist, shutting down");
			System.exit(1);
		}
 		if(sym_t.get(var_name).type()!= Type.column){
 			System.out.println("Error: Variable " + var_name + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		 sym_t.delete(sym_t.get(var_name));
		Column t = new Column(var_name,Type.column);

			   if (ctx.columnDefinition().getText().contains("->")){//colfetch
				   String res[]=ctx.columnDefinition().getText().split("->");
				   String var1=res[1];
				   String name1=res[0];

				   if(!sym_t.exists(var1)){
					   System.out.println("Error: Variable "+var1+" doesnt exist, shutting down");
					  System.exit(1);
				   }else if(sym_t.get(var1).type() != Type.column){
					   System.out.println("Error: Variable " + var1 + " is of Incompatible type, shutting down");
					  System.exit(1);
				   }
				   Column col1 = new Column("",Type.column,name1);
				   if(sym_t.exists(var1)){
					   Column c1 =(Column)sym_t.get(var1);
					   for(int i2=0; i2<c1.size();i2++){
						   col1.add(c1.get(i2));
					   }
				   }

			   }
			   else if (!ctx.columnDefinition().getText().contains(":")){ //colempty alt
				   String col2=ctx.columnDefinition().getText();
				   Column c2=new Column("",Type.column,col2);
			   }
			   else{ //colBase
				   String col_item[]=ctx.columnDefinition().getText().split(":");
				   String col=col_item[0];
				   String itens[]=col_item[1].split(",");
				   Column c= new Column("", Type.column,col);
				   for(int l=0; l<itens.length;l++){
					   c.add(new Item("",Type.item, (String)itens[l]));
				   }
			   }
		sym_t.add(t);

		return visitChildren(ctx);
	}

 	@Override public Boolean visitColumnNameAssign(UTLParser.ColumnNameAssignContext ctx) {
 		String var = ctx.itemVar().getText();

 		if(sym_t.get(var).type()!= Type.column){
 			System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Table tb=(Table)sym_t.get(tblvar);
		Column c_old=(Column)sym_t.get(var);
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				String col=ctx.col_access().column().getText();
				//Column c=tb.get(col);
				c_old.setName(col);
			}
			else if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column c=tb.get(idx);
				c_old.setName(c.getName());
			}
		}

 		//System.out.println("Column Name assigned to "+var+" successfully");

 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitItemAssignTable(UTLParser.ItemAssignTableContext ctx) {
 		String var = ctx.itemVar().getText();

 		if(sym_t.get(var).type()!= Type.item){
 			System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		Table tb=(Table)sym_t.get(tblvar);
		Item toChange=(Item)sym_t.get(var);
 		//System.out.println("Item assigned to "+var+" successfully");
		if(ctx.col_access()!=null){
			if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column c=tb.get(idx);
				if(ctx.item_access()!=null){
					if(ctx.item_access().index()!=null){
						int i=Integer.parseInt(ctx.item_access().index().getText());
						Item it=c.get(i);

						toChange.setValue(it.getValue());
					}
					else if(ctx.item_access().item()!=null){
						String ite=ctx.item_access().item().getText();
						Item Ite=c.get(ite);
						toChange.setValue(ite);
					}
				}
			}
			if(ctx.col_access().column()!=null){
				String col=ctx.col_access().column().getText();
				Column r=tb.get(col);
				if(ctx.item_access()!=null){
					if(ctx.item_access().index()!=null){
						int i=Integer.parseInt(ctx.item_access().index().getText());
						Item it=r.get(i);

						toChange.setValue(it.getValue());
					}
					else if(ctx.item_access().item()!=null){
						String ite=ctx.item_access().item().getText();
						Item Ite=r.get(ite);
						toChange.setValue(ite);
					}
				}

			}
		}
 		return visitChildren(ctx);
	 }
	@Override public Boolean visitItemAssignVar(UTLParser.ItemAssignVarContext ctx) {
		String item_var = ctx.v1.getText();
		String item_other=ctx.v2.getText();

		if(!sym_t.exists(item_var)){
			System.out.println("ERROR: Variable "+item_var+" doesnt exist, shutting down");
			System.exit(1);
		}
		if(!sym_t.exists(item_other)){
			System.out.println("ERROR: Variable "+item_other+" doesn't exist, shutting down");
			System.exit(1);
		}
		return visitChildren(ctx);
		}

 	@Override public Boolean visitDeleteTable(UTLParser.DeleteTableContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

		sym_t.delete((Table)sym_t.get(tblvar));
		sym_t.add(new Table(tblvar,Type.table));
		//System.out.println(tblvar + " eliminada");
 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitDeleteColumn(UTLParser.DeleteColumnContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
			String col=(String)ctx.col_access().getText();
			//System.out.println("coluna a eliminar " + col);
			//System.out.println("tamanho tabela "+ ((Table)sym_t.get(tblvar)).size());
			if(((Table)sym_t.get(tblvar)).contains(col)){
				((Table)sym_t.get(tblvar)).delete(col);

			}
			else{
				System.out.println("Tabela "+ tblvar + " não tem essa coluna, shutting down");
				System.exit(1);
			}
		}
			if(ctx.col_access().index()!=null){
				int ind=Integer.parseInt(ctx.col_access().index().getText());
				if(((Table)sym_t.get(tblvar)).size()>ind){
					Column coluna=((Table)sym_t.get(tblvar)).get(ind);
					((Table)sym_t.get(tblvar)).delete(coluna.name());
				}
				else{
					System.out.println("Indice maior que tamanho da tabela, shutting down");
					System.exit(1);
				}
			}

		//	System.out.println(((Table)sym_t.get(tblvar)).get(col));
		//	System.out.println("Primeira coluna"+((Table)sym_t.get(tblvar)).get(0));
		}

		else if(ctx.indexRange()!=null){
			int first=Integer.parseInt(ctx.indexRange().INT(0).getText());
			int last=Integer.parseInt(ctx.indexRange().INT(1).getText());

			for(int c=first;c<last;c++){
				Column col=((Table)sym_t.get(tblvar)).get(c);
				((Table)sym_t.get(tblvar)).delete(col.name());
			}
		}


 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitDeleteItem(UTLParser.DeleteItemContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

		Table t=(Table)sym_t.get(tblvar);
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				String col=ctx.col_access().column().getText();
				Column c=t.get(col);
				if(ctx.item_access()!=null){
					c.delete(ctx.item_access().getText());
				}
				else if(ctx.indexRange()!=null){
					int first=Integer.parseInt(ctx.indexRange().INT(0).getText());
					int last=Integer.parseInt(ctx.indexRange().INT(1).getText());
					for(int l=first;l<last;l++){
						String coluna=c.get(l).getValue();
						c.delete(coluna);
					}
				}

			}
			else if(ctx.col_access().index()!=null){
				int idx=Integer.parseInt(ctx.col_access().index().getText());
				Column r=t.get(idx);
				if(ctx.item_access()!=null){
					r.delete(ctx.item_access().getText());
				}
				else if(ctx.indexRange()!=null){
					int first=Integer.parseInt(ctx.indexRange().INT(0).getText());
					int last=Integer.parseInt(ctx.indexRange().INT(1).getText());
					for(int l=first;l<last;l++){
						String coluna=r.get(l).getValue();
						r.delete(coluna);
					}
				}
			}
		}
 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitPutCol(UTLParser.PutColContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		int i = 0;
 		while(true){
 			if(ctx.columnVar(i) != null){
 				String var = ctx.columnVar(i).getText();
 				if(!sym_t.exists(var)){
 					System.out.println("Error: Variable "+ var + " doesnt exist, shutting down");
					System.exit(1);
 				}else if(sym_t.get(var).type()!= Type.column){
 					System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
					System.exit(1);
 				}
 			}

 			i = i + 1;
 			if(ctx.columnVar(i) == null){
 				break;
 			}
 		}
		int c=0;
		int l=0;
		Table tb=(Table) sym_t.get(tblvar);
		while (true){
			if(ctx.column(c)!=null){

				tb.addColumn(new Column("",Type.column,ctx.column(c).getText()));
				c++;
			}
			else if(ctx.columnVar(l)!=null){
				tb.addColumn((Column)sym_t.get(ctx.columnVar(l).getText()));
				//System.out.println("var column " + ctx.columnVar(l).getText()+"com o noem" + ((Column)sym_t.get(ctx.columnVar(l).getText())).getName());
				l++;
			}


			else{
				break;
			}
	}

 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitPutItem(UTLParser.PutItemContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		int i = 0;
 		while(true){
 			if(ctx.itemVar(i) != null){
 				String var = ctx.itemVar(i).getText();
 				if(!sym_t.exists(var)){
 					System.out.println("Error: Variable "+ var + " doesnt exist, shutting down");
					System.exit(1);
 
 				}else if(sym_t.get(var).type()!= Type.item){
 					System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
					System.exit(1);
 				}
 			}

 			i = i + 1;
 			if(ctx.item(i) == null && ctx.itemVar(i) == null){
 				break;
 			}
 		}
		Table ta=(Table)sym_t.get(tblvar);
		int c=0;
		int l=0;
		if(ctx.col_access().index()!=null){
			int ind=Integer.parseInt(ctx.col_access().index().getText());
			Column col=ta.get(ind);
		//	System.out.println("nomde coluna"+col.name());
			while(true){
				if(ctx.item(c)!=null){
					col.add(new Item("",Type.item,ctx.item(c).getText()));

					c++;
				}
				else if(ctx.itemVar(l)!=null){
					//System.out.println("item adicionado " + ctx.itemVar(l).getText());
					col.add((Item)sym_t.get(ctx.itemVar(l).getText()));
					l++;
				}
				else{
					break;
				}

			}
		}
		if(ctx.col_access().column()!=null){
			String row=ctx.col_access().column().getText();
			Column r=ta.get(row);
			while(true){
				if(ctx.item(c)!=null){
					r.add(new Item("",Type.item,ctx.item(c).getText()));
					c++;
				}
				else if(ctx.itemVar(l)!=null){
					r.add((Item)sym_t.get(ctx.itemVar(l).getText()));
					l++;
				}
				else{
					break;
				}

			}

		}


 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitAlterColumn(UTLParser.AlterColumnContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
	//	System.out.println("o col_acces_index" + ctx.col_access().index().getText());
	//	System.out.println(" valor para alterar" + ctx.column().getText());

		if(ctx.col_access().index()!=null){
			int index=Integer.parseInt(ctx.col_access().index().getText());
			Table tpois= (Table) sym_t.get(tblvar);
			String pa=tpois.toString();
			Column c = (Column)tpois.get(index);
			String newNome=(String)ctx.column().getText();
			c.setName(newNome);
		}
		if(ctx.col_access().column()!=null){
			String row=(String)ctx.col_access().column().getText();
			Table t= (Table) sym_t.get(tblvar);
			Column c = (Column)t.get(row);
			c.setName(ctx.column().getText());
			//System.out.println("Novo nome: "+c.getName());

		}


 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitAlterItem(UTLParser.AlterItemContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}

 		if(ctx.itemVar() != null){
 			String var = ctx.itemVar().getText();
 			if(!sym_t.exists(var)){
 				System.out.println("Error: Variable "+ var + " doesnt exist, shutting down");
				System.exit(1);
 			}else if(sym_t.get(var).type()!= Type.item){
 				System.out.println("Error: Variable " + var + " is of Incompatible type, shutting down");
				System.exit(1);
 			}
 		}
		Table tb=(Table)sym_t.get(tblvar);
		if(ctx.col_access().index()!=null){
			int idx=Integer.parseInt(ctx.col_access().index().getText());
			Column c=tb.get(idx);
			if(ctx.item_access().index()!=null){
				int in=Integer.parseInt(ctx.item_access().index().getText());
				Item old=c.get(in);
				if(ctx.item()!=null){
					old.setValue(ctx.item().getText());
				}
				else if(ctx.itemVar()!=null){
					old.setValue(((Item)sym_t.get(ctx.itemVar().getText())).getValue());
				}
			}
			if(ctx.item_access().item()!=null){
				String it=ctx.item_access().item().getText();
				if(c.contains(it)){
					if(ctx.item()!=null){
						c.get(it).setValue(ctx.item().getText());
					}
					else if(ctx.itemVar()!=null){
						c.get(it).setValue(((Item)sym_t.get(ctx.itemVar().getText())).getValue());
					}
				}
			}
		}
		else if(ctx.col_access().column()!=null){
			String GetCol=ctx.col_access().column().getText();
			Column r=tb.get(GetCol);
			if(ctx.item_access().index()!=null){
				int in=Integer.parseInt(ctx.item_access().index().getText());
				Item old=r.get(in);
				if(ctx.item()!=null){
					old.setValue(ctx.item().getText());
				}
				else if(ctx.itemVar()!=null){
					old.setValue(((Item)sym_t.get(ctx.itemVar().getText())).getValue());
				}
			}
			if(ctx.item_access().item()!=null){
				String it=ctx.item_access().item().getText();
				if(r.contains(it)){
					if(ctx.item()!=null){
						r.get(it).setValue(ctx.item().getText());
					}
					else if(ctx.itemVar()!=null){
						r.get(it).setValue(((Item)sym_t.get(ctx.itemVar().getText())).getValue());
					}
				}
			}
		}
 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitExportCSV(UTLParser.ExportCSVContext ctx) {
 		String tblvar = ctx.tableVar().getText();

 		if(!sym_t.exists(tblvar)){
 			System.out.println("Error: Variable " + tblvar + " doesnt exist, shutting down");
			System.exit(1);
 		}else if(sym_t.get(tblvar).type()!= Type.table){
 			System.out.println("Error: Variable " + tblvar + " is of Incompatible type, shutting down");
			System.exit(1);
 		}
		//valor 0 para cada Column
		//coluna++
		/*String FileName=ctx.fileName().getText();
		int i=0;
		boolean flag=true;
		try(Writer writer= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName+".csv"),("utf-8")))){
			while(flag){
			for(int c=0;c<((Table)sym_t.get(tblvar)).colunas.size();c++){
				Table tb=(Table)sym_t.get(tblvar);
				Column col=tb.get(c);
				if(i>=col.size()){
					flag=false;
					break;
				}
				writer.write(col.get(i).getValue());

				if(c==((Table)sym_t.get(tblvar)).colunas.size()-1){
					writer.write("");
				}
				else{
					writer.write(",");
				}

			}
			writer.write("\n");
			i++;

		}
	}
		catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}*/
 		return visitChildren(ctx);
 	}

 	@Override public Boolean visitCol_access(UTLParser.Col_accessContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitItem_access(UTLParser.Item_accessContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitTableVar(UTLParser.TableVarContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitColumnVar(UTLParser.ColumnVarContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitItemVar(UTLParser.ItemVarContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitColumn(UTLParser.ColumnContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitItem(UTLParser.ItemContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitIndex(UTLParser.IndexContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitIndexRange(UTLParser.IndexRangeContext ctx) { return visitChildren(ctx); }

 	@Override public Boolean visitFileName(UTLParser.FileNameContext ctx) { return visitChildren(ctx); }
 }
