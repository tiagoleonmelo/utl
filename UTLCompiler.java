// Generated from UTL.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import java.util.*;
import java.io.*;
import org.stringtemplate.v4.*;

public class UTLCompiler extends UTLBaseVisitor<Symbol>{
	private String st="import java.util.*;\nimport java.io.*;\npublic class generatedCode{\npublic static void main(String [] args){\n//UsefulVariables\nArrayList<String> nomes=new ArrayList<>();\nArrayList<ArrayList<String>> itens=new ArrayList<>();\nArrayList<String> currentItems=new ArrayList<>();\n//----Inicio do Programa\n";
	public String getST(){
		return this.st;
	}
	@Override public Symbol visitR(UTLParser.RContext ctx) {
		return visitChildren(ctx);
	}

	@Override public Symbol visitDeclaration(UTLParser.DeclarationContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitAssignment(UTLParser.AssignmentContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitOperation(UTLParser.OperationContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitPrint(UTLParser.PrintContext ctx) {
		String key =ctx.tableVar().getText();
		st+="System.out.println("+key+".toString());\n";

		return visitChildren(ctx); }

	@Override public Symbol visitTableDeclareBase(UTLParser.TableDeclareBaseContext ctx) {
		String var = ctx.tableVar().getText();
		st+="Table "+var+" = new Table(\""+var+"\", Type.table);\n";

		int c=0;
		ArrayList<Column> cols=new ArrayList<>();
		while (ctx.columnDefinition(c)!=null){
			Column res=(Column) visit(ctx.columnDefinition(c));
			cols.add(res);
			c++;
		}
		int nCol=0;
		for (Column co: cols){
			if (co.type()==Type.table){ //colBase
				st+="nomes.add("+Integer.toString(nCol)+","+co.getName()+");\n";
				for (Item i: co.items){
					if (i.type==Type.item){
						st+="currentItems.add("+i.getValue()+");\n";
					}
					else{
						String varname=i.getValue();
						st+="currentItems.add("+varname+".getValue());\n";

					}
				}
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";

			}
			else if (co.type==Type.column){ //col fetch
				String ret=co.getName();
				String[] both=ret.split(".-k27x-.");
				String colname=both[0];
				String varname=both[1];
				st+="nomes.add("+Integer.toString(nCol)+","+colname+");\n";
				st+="for (Item s:"+varname+".items){currentItems.add(s.getValue());}\n";
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";
			}
			else{ //col empty
				st+="nomes.add("+Integer.toString(nCol)+","+co.getName()+");\n";
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";
			}
			nCol++;
		}
		st+="tableDeclare("+var+",nomes,itens,"+Integer.toString(nCol)+");\n";
		st+="nomes.clear();itens.clear();\n";

		return visitChildren(ctx); }

	@Override public Symbol visitTableDeclareJoining(UTLParser.TableDeclareJoiningContext ctx) {
		String var = ctx.tableVar(0).getText();
 		String t1 = ctx.tableVar(1).getText();
		String t2 = ctx.tableVar(2).getText();

		st+="Table "+var+"=tableJoin("+t1+","+t2+",\""+var+"\");\n";

		return visitChildren(ctx); }

	@Override public Symbol visitColDeclareOne(UTLParser.ColDeclareOneContext ctx) {
		String var_name = ctx.columnVar().getText();
		String table_var_name = ctx.tableVar().getText();

		st+="Column "+var_name+"=new Column(\""+var_name+"\", Type.column,\"\");\n";

 		if(ctx.col_access().column() != null){
			int idx=-9;
			String it=null;
			int f=-9;
			int l=-9;
			String coluna=ctx.col_access().column().getText();

			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					idx=Integer.parseInt(ctx.item_access().index().getText());
				}
				else if(ctx.item_access().item()!=null){
					it=ctx.item_access().item().getText();
				}

			}
			else if(ctx.indexRange()!=null){
				f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				l=Integer.parseInt(ctx.indexRange().INT(1).getText());
			}
		st+="ColDeclareOne1("+var_name+","+table_var_name+","+coluna+","+idx+","+it+","+f+","+l+");\n";
		}
		else if(ctx.col_access().index()!=null){
			int idx=-9;
			String it=null;
			int f=-9;
			int l=-9;
			int index_col=Integer.parseInt(ctx.col_access().index().getText());

			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					idx=Integer.parseInt(ctx.item_access().index().getText());
				}
				else if(ctx.item_access().item()!=null){
					it=ctx.item_access().item().getText();
				}
			}
			else if(ctx.indexRange()!=null){
				f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				l=Integer.parseInt(ctx.indexRange().INT(1).getText());
			}
		st+="ColDeclareOne2("+var_name+","+table_var_name+","+index_col+","+idx+","+it+","+f+","+l+");\n";
		}
		return visitChildren(ctx); }

	@Override public Symbol visitColDeclareTwo(UTLParser.ColDeclareTwoContext ctx) {
		String var_name = ctx.columnVar().getText();
		String table_var_name = ctx.tableVar().getText();
		String col_name=null;
		int idx=-9;

		st+="Column "+var_name+"=new Column(\""+var_name+"\", Type.column,\"\");\n";
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				col_name=ctx.col_access().column().getText();
			}
			else if(ctx.col_access().index()!=null){
				idx=Integer.parseInt(ctx.col_access().index().getText());
			}
		st+="ColDeclareTwo("+var_name+","+table_var_name+","+col_name+","+idx+");\n";
		}

		return visitChildren(ctx); }

	@Override public Symbol visitColDeclareThree(UTLParser.ColDeclareThreeContext ctx) {
		String var = ctx.columnVar().getText();
		st+="Column "+var+" = new Column(\"\",Type.column,\"\");\n";

		Column res=(Column) visit(ctx.columnDefinition());
		if (res.type()==Type.table){ //colBase
			String colname=res.getName();
			st+=var+".setName("+colname+");\n";
			for (Item i: res.items){
				if (i.type==Type.item){
					st+=var+".add(new Item(\"\",Type.item,"+i.getValue()+"));\n";
				}
				else{
					String varname=i.getValue();
					st+=var+".add("+varname+");\n";

					}
				}

			}
			else if (res.type==Type.column){ //col fetch
				String ret=res.getName();
				String[] both=ret.split(".-k27x-.");
				String colname=both[0];
				String varname=both[1];
				st+=var+".setName("+colname+");\n";
				st+="for (Item s:"+varname+".items){"+var+".add(s);}\n";
			}
			else{ //col empty
				st+=var+".setName("+res.getName()+");\n";
			}

		return visitChildren(ctx); }

	@Override public Symbol visitColBase(UTLParser.ColBaseContext ctx) {
		String nomecol=ctx.column().getText();
		Column res=new Column(nomecol,Type.table,nomecol);

		int j=0;
		int k=0;
		while(true){
			if(ctx.item(j)!=null){
				res.add(new Item("",Type.item,ctx.item(j).getText()));
				j++;
			}
			else if(ctx.itemVar(k)!=null){
				res.add(new Item("",Type.column,ctx.itemVar(k).getText()));
				k++;
			}
			else{
				break;
			}
		}

		return res; }

	@Override public Symbol visitColFetch(UTLParser.ColFetchContext ctx) {
		String nomecol=ctx.column().getText();
		String var=ctx.columnVar().getText();
		String ret=nomecol+".-k27x-."+var;

		Column res=new Column(nomecol,Type.column,ret);

		return res; }

	@Override public Symbol visitColEmpty(UTLParser.ColEmptyContext ctx) {
		String nomecol=ctx.column().getText();
		Column res=new Column(nomecol,Type.item,nomecol);

		return res; }

	@Override public Symbol visitColumnNameDec(UTLParser.ColumnNameDecContext ctx) {
		String item_var = ctx.itemVar().getText();

 		String table_var = ctx.tableVar().getText();
		st+="Item "+item_var+"=new Item(\""+item_var+"\",Type.item,\"\");\n";

		String s;
		int t;
		String v;
		if(ctx.col_access().column()!=null){
			s=ctx.col_access().column().getText();
			st+=item_var+".setValue("+ table_var+".get("+s+").getName());\n";
		}else{
			t=Integer.parseInt(ctx.col_access().index().getText());
			st+=item_var+".setValue("+ table_var+".get("+t+").getName());\n";
		}

 		st+="System.out.println(\"Item added successfully\");\n";

		return visitChildren(ctx); }

	@Override public Symbol visitItemDeclTable(UTLParser.ItemDeclTableContext ctx) {
		String item_var = ctx.itemVar().getText();
		String table_var = ctx.tableVar().getText(); // Fetching name of the table var and then checking if it exists

		 if(ctx.col_access().column() != null){ // acesso por coluna
			String col_name = ctx.col_access().column().getText();
			if(ctx.item_access().item() != null){ //acesso coluna->item
				String item_name = ctx.item_access().item().getText();
				st+="Item "+item_var+"="+table_var+".get("+col_name+").get("+item_name+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}else{ // acesso coluna->index
				int col_idx = Integer.parseInt(ctx.item_access().index().getText());
				st+="Item "+item_var+"="+table_var+".get("+col_name+").get("+col_idx+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
		}
		else{ //acesso por index
			int indx =Integer.parseInt(ctx.col_access().index().getText());
			//get column -> table.getcolumn
			if(ctx.item_access().item() != null){ //acesso index->item
				String item_name = ctx.item_access().item().getText();
				st+="Item "+item_var+"="+table_var+".get("+indx+").get("+item_name+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
			else{ //acesso index->index
				int col_idx = Integer.parseInt(ctx.item_access().index().getText());
				st+="Item "+item_var+"="+table_var+".get("+indx+").get("+col_idx+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
		}
	}
	@Override public Symbol visitItemDeclStr(UTLParser.ItemDeclStrContext ctx) {
		String item_var = ctx.itemVar().getText();
		String stringv=ctx.item().getText();
		st+="Item "+item_var+"=new Item(\""+item_var+"\",Type.item,"+stringv+");\n";
 		st+="System.out.println(\"Item saved successfully\");\n";
 		return visitChildren(ctx);


	}
	@Override public Symbol visitItemDeclVar(UTLParser.ItemDeclVarContext ctx) {
		String item_var = ctx.v1.getText();
		String item_other=ctx.v2.getText();

		st+="Item "+item_var+"="+item_other+";\n";
 		return visitChildren(ctx);

	}

	@Override public Symbol visitTableAssignBase(UTLParser.TableAssignBaseContext ctx) {
		String var = ctx.tableVar().getText();
		st+="deleteTable("+var+");\n";

		int c=0;
		ArrayList<Column> cols=new ArrayList<>();
		while (ctx.columnDefinition(c)!=null){
			Column res=(Column) visit(ctx.columnDefinition(c));
			cols.add(res);
			c++;
		}
		int nCol=0;
		for (Column co: cols){
			if (co.type()==Type.table){ //colBase
				st+="nomes.add("+Integer.toString(nCol)+","+co.getName()+");\n";
				for (Item i: co.items){
					if (i.type==Type.item){
						st+="currentItems.add("+i.getValue()+");\n";
					}
					else{
						String varname=i.getValue();
						st+="currentItems.add("+varname+".getValue());\n";

					}
				}
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";

			}
			else if (co.type==Type.column){ //col fetch
				String ret=co.getName();
				String[] both=ret.split(".-k27x-.");
				String colname=both[0];
				String varname=both[1];
				st+="nomes.add("+Integer.toString(nCol)+","+colname+");\n";
				st+="for (Item s:"+varname+".items){currentItems.add(s.getValue());}\n";
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";
			}
			else{ //col empty
				st+="nomes.add("+Integer.toString(nCol)+","+co.getName()+");\n";
				st+="itens.add("+Integer.toString(nCol)+",new ArrayList<>(currentItems));\n";
				st+="currentItems.clear();\n";
			}
			nCol++;
		}
		st+="tableDeclare("+var+",nomes,itens,"+Integer.toString(nCol)+");\n";
		st+="nomes.clear();itens.clear();\n";

		return visitChildren(ctx); }
		@Override public Symbol visitTableAssignJoining(UTLParser.TableAssignJoiningContext ctx) {
			String var = ctx.tableVar(0).getText();
 		String t1 = ctx.tableVar(1).getText();
		String t2 = ctx.tableVar(2).getText();

		st+=var+"=tableJoin("+t1+","+t2+",\""+var+"\");\n";

		return visitChildren(ctx); }

	@Override public Symbol visitColAssignOne(UTLParser.ColAssignOneContext ctx) { 
		String var_name = ctx.columnVar().getText();
		String table_var_name = ctx.tableVar().getText();
		st+="deleteColumn("+var_name+");\n";

 		if(ctx.col_access().column() != null){
			int idx=-9;
			String it=null;
			int f=-9;
			int l=-9;
			String coluna=ctx.col_access().column().getText();

			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					idx=Integer.parseInt(ctx.item_access().index().getText());
				}
				else if(ctx.item_access().item()!=null){
					it=ctx.item_access().item().getText();
				}

			}
			else if(ctx.indexRange()!=null){
				f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				l=Integer.parseInt(ctx.indexRange().INT(1).getText());
			}
		st+="ColDeclareOne1("+var_name+","+table_var_name+","+coluna+","+idx+","+it+","+f+","+l+");\n";
		}
		else if(ctx.col_access().index()!=null){
			int idx=-9;
			String it=null;
			int f=-9;
			int l=-9;
			int index_col=Integer.parseInt(ctx.col_access().index().getText());

			if(ctx.item_access()!=null){
				if(ctx.item_access().index()!=null){
					idx=Integer.parseInt(ctx.item_access().index().getText());
				}
				else if(ctx.item_access().item()!=null){
					it=ctx.item_access().item().getText();
				}
			}
			else if(ctx.indexRange()!=null){
				f=Integer.parseInt(ctx.indexRange().INT(0).getText());
				l=Integer.parseInt(ctx.indexRange().INT(1).getText());
			}
		st+="ColDeclareOne2("+var_name+","+table_var_name+","+index_col+","+idx+","+it+","+f+","+l+");\n";
		}
		return visitChildren(ctx); }
	@Override public Symbol visitColAssignTwo(UTLParser.ColAssignTwoContext ctx) { 
		String var_name = ctx.columnVar().getText();
		String table_var_name = ctx.tableVar().getText();
		String col_name=null;
		int idx=-9;

		st+="deleteColumn("+var_name+");\n";
		if(ctx.col_access()!=null){
			if(ctx.col_access().column()!=null){
				col_name=ctx.col_access().column().getText();
			}
			else if(ctx.col_access().index()!=null){
				idx=Integer.parseInt(ctx.col_access().index().getText());
			}
		st+="ColDeclareTwo("+var_name+","+table_var_name+","+col_name+","+idx+");\n";
		}

		return visitChildren(ctx); }
	@Override public Symbol visitColAssignThree(UTLParser.ColAssignThreeContext ctx) { 
		String var = ctx.columnVar().getText();
		st+="deleteColumn("+var+");\n";

		Column res=(Column) visit(ctx.columnDefinition());
		if (res.type()==Type.table){ //colBase
			String colname=res.getName();
			st+=var+".setName("+colname+");\n";
			for (Item i: res.items){
				if (i.type==Type.item){
					st+=var+".add(new Item(\"\",Type.item,"+i.getValue()+"));\n";
				}
				else{
					String varname=i.getValue();
					st+=var+".add("+varname+");\n";

					}
				}

			}
			else if (res.type==Type.column){ //col fetch
				String ret=res.getName();
				String[] both=ret.split(".-k27x-.");
				String colname=both[0];
				String varname=both[1];
				st+=var+".setName("+colname+");\n";
				st+="for (Item s:"+varname+".items){"+var+".add(s);}\n";
			}
			else{ //col empty
				st+=var+".setName("+res.getName()+");\n";
			}

		return visitChildren(ctx); }
		

	@Override public Symbol visitColumnNameAssign(UTLParser.ColumnNameAssignContext ctx) {
		String item_var = ctx.itemVar().getText();

 		String table_var = ctx.tableVar().getText();

		String s;
		int t;
		String v;
		if(ctx.col_access().column()!=null){
			s=ctx.col_access().column().getText();
			st+=item_var+".setValue("+ table_var+".get("+s+").getName());\n";
		}else{
			t=Integer.parseInt(ctx.col_access().index().getText());
			st+=item_var+".setValue("+ table_var+".get("+t+").getName());\n";
		}

 		st+="System.out.println(\"Item changed successfully\");\n";
		return visitChildren(ctx); }

		@Override public Symbol visitItemAssignTable(UTLParser.ItemAssignTableContext ctx) {
			String item_var = ctx.itemVar().getText();
		String table_var = ctx.tableVar().getText(); // Fetching name of the table var and then checking if it exists

		 if(ctx.col_access().column() != null){ // acesso por coluna
			String col_name = ctx.col_access().column().getText();
			if(ctx.item_access().item() != null){ //acesso coluna->item
				String item_name = ctx.item_access().item().getText();
				st+=item_var+"="+table_var+".get("+col_name+").get("+item_name+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}else{ // acesso coluna->index
				int col_idx = Integer.parseInt(ctx.item_access().index().getText());
				st+=item_var+"="+table_var+".get("+col_name+").get("+col_idx+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
		}
		else{ //acesso por index
			int indx =Integer.parseInt(ctx.col_access().index().getText());
			//get column -> table.getcolumn
			if(ctx.item_access().item() != null){ //acesso index->item
				String item_name = ctx.item_access().item().getText();
				st+=item_var+"="+table_var+".get("+indx+").get("+item_name+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
			else{ //acesso index->index
				int col_idx = Integer.parseInt(ctx.item_access().index().getText());
				st+=item_var+"="+table_var+".get("+indx+").get("+col_idx+");\n";
				st+="System.out.println(\"Item saved successfully\");\n";
				return visitChildren(ctx);
			}
		}
	}
	   @Override public Symbol visitItemAssignVar(UTLParser.ItemAssignVarContext ctx) {
		String item_var = ctx.v1.getText();
		String item_other=ctx.v2.getText();

		st+=item_var+"="+item_other+";\n";
 		return visitChildren(ctx);
	   }

	@Override public Symbol visitDeleteTable(UTLParser.DeleteTableContext ctx) {
		String table_var=ctx.tableVar().getText();
		st+="deleteTable("+table_var+");\n";
		return visitChildren(ctx); }

		@Override public Symbol visitDeleteColumn(UTLParser.DeleteColumnContext ctx) {
			String tableVar=ctx.tableVar().getText();
			//String col=ctx.col_access().getText();
			if(ctx.col_access()!=null){
				if(ctx.col_access().index()!=null){
					st+="deleteColumn("+tableVar+","+Integer.parseInt(ctx.col_access().index().getText()) + ");\n";
				}
				else if(ctx.col_access().column()!=null){
					st+="deleteColumn_column("+tableVar+","+ctx.col_access().column().getText()+ ");\n";
				}
			}
			if(ctx.indexRange()!=null){
				st+="deleteColumn_indexRange("+tableVar+",\""+ctx.indexRange().getText()+"\");\n";
			}

			return visitChildren(ctx); }

	@Override public Symbol visitDeleteItem(UTLParser.DeleteItemContext ctx) {
		String tableVar=ctx.tableVar().getText();
		if(ctx.col_access().index()!=null){
			int col_idx=Integer.parseInt(ctx.col_access().index().getText());
			if(ctx.indexRange()!=null){
				String indexRange=ctx.indexRange().getText();
				st+="deleteItem3("+tableVar+","+col_idx+",\""+indexRange+"\");\n";
			}
			else if(ctx.item_access().index()!=null){
				int it_idx=Integer.parseInt(ctx.item_access().index().getText());
				st+="deleteItem1("+tableVar+","+col_idx+","+it_idx+");\n";
			}
			else if(ctx.item_access().item()!=null){
				String item_name=ctx.item_access().item().getText();
				st+="deleteItem2("+tableVar+","+col_idx+","+item_name+");\n";
			}
		}
		else if(ctx.col_access().column()!=null){
			String nome_col=ctx.col_access().column().getText();
			if(ctx.indexRange()!=null){
				String indexRan=ctx.indexRange().getText();
				st+="deleteItem6("+tableVar+","+nome_col+",\""+indexRan+"\");\n";
			}
			else if(ctx.item_access().index()!=null){
				int item_idx=Integer.parseInt(ctx.item_access().index().getText());
				st+="deleteItem4("+tableVar+","+nome_col+","+item_idx+");\n";
			}
			else if(ctx.item_access().item()!=null){
				String item_nome=ctx.item_access().item().getText();
				st+="deleteItem5("+tableVar+","+nome_col+","+item_nome+");\n";
			}

		}

		 return visitChildren(ctx); }

	@Override public Symbol visitPutCol(UTLParser.PutColContext ctx) {
		String tableVar=ctx.tableVar().getText();
			int j=0;
			int k=0;
			while(true){
				if(ctx.column(j)!=null){
						st+="putCol("+tableVar+","+ctx.column(j).getText()+");\n";
						j++;
				}
				else if(ctx.columnVar(k)!=null){
					st+="putColVar("+tableVar+","+ctx.columnVar(k).getText()+");\n";
					k++;
				}
		else{
			break;
		}
		}

		 return visitChildren(ctx); }

	@Override public Symbol visitPutItem(UTLParser.PutItemContext ctx) {
		String tableVar=ctx.tableVar().getText();
		int j=0;
		int k=0;
		if(ctx.col_access().index()!=null){
			int idx_col=Integer.parseInt(ctx.col_access().index().getText());
			while(true){
				if(ctx.item(j)!=null){
					st+="putItem1("+tableVar+","+idx_col+","+ctx.item(j).getText()+");\n";
					j++;
				}
				else if(ctx.itemVar(k)!=null){
					st+="putItem2("+tableVar+","+idx_col+","+ctx.itemVar(k).getText()+");\n";
					k++;
				}
				else{
					break;
				}
			}
		}
		else if(ctx.col_access().column()!=null){
			String name_col=ctx.col_access().column().getText();
			while(true){
				if(ctx.item(j)!=null){
					st+="putItem3("+tableVar+","+name_col+","+ctx.item(j).getText()+");\n";
					j++;
				}
				else if(ctx.itemVar(k)!=null){
					st+="putItem4("+tableVar+","+name_col+","+ctx.itemVar(k).getText()+");\n";
					k++;
				}
				else{
					break;
				}
			}
		}

		 return visitChildren(ctx); }

	@Override public Symbol visitAlterColumn(UTLParser.AlterColumnContext ctx) {
		String tableVar=ctx.tableVar().getText();
		if(ctx.col_access().column()!=null){
			st+="alterColumn("+tableVar+","+ctx.col_access().getText()+","+ctx.column().getText()+");\n";
		}
		else if(ctx.col_access().index()!=null){
			st+="alterColumn_index("+tableVar+","+Integer.parseInt(ctx.col_access().getText())+","+ctx.column().getText()+");\n";
		}
		return visitChildren(ctx); }

	@Override public Symbol visitAlterItem(UTLParser.AlterItemContext ctx) {

		String tableVar=ctx.tableVar().getText();
		if(ctx.col_access().index()!=null){
			int idx=Integer.parseInt(ctx.col_access().getText());
			if(ctx.item_access().index()!=null){
				int idx_item=Integer.parseInt(ctx.item_access().index().getText());
				if(ctx.item()!=null){
					st+="alterItem1("+tableVar+","+idx+","+idx_item+","+ctx.item().getText()+");\n";
				}
				else if(ctx.itemVar()!=null){
					st+="alterItem2("+tableVar+","+idx+","+idx_item+","+ctx.itemVar().getText()+");\n";
				}
			}
			else if(ctx.item_access().item()!=null){
				String it_value=ctx.item_access().item().getText();
				if(ctx.item()!=null){
					st+="alterItem7("+tableVar+","+idx+","+it_value+","+ctx.item().getText()+");\n";
				}
				else if(ctx.itemVar()!=null){
					st+="alterItem8("+tableVar+","+idx+","+it_value+","+ctx.itemVar().getText()+");\n";
				}
			}
		}
		else if(ctx.col_access().column()!=null){
			String name_column=ctx.col_access().column().getText();
			if(ctx.item_access().index()!=null){
				int idx_it=Integer.parseInt(ctx.item_access().index().getText());
				if(ctx.item()!=null){
					st+="alterItem3("+tableVar+","+name_column+","+idx_it+","+ctx.item().getText()+");\n";
				}
				else if(ctx.itemVar()!=null){
					st+="alterItem4("+tableVar+","+name_column+","+idx_it+","+ctx.itemVar().getText()+");\n";
				}
			}
			else if(ctx.item_access().item()!=null){
				String name_item=ctx.item_access().item().getText();
				if(ctx.item()!=null){
					st+="alterItem5("+tableVar+","+name_column+","+name_item+","+ctx.item().getText()+");\n";
				}
				else if(ctx.itemVar()!=null){
						st+="alterItem6("+tableVar+","+name_column+","+name_item+","+ctx.itemVar().getText()+");\n";
				}
			}
		}
		return visitChildren(ctx); }

	@Override public Symbol visitExportCSV(UTLParser.ExportCSVContext ctx) {
		String filename=ctx.fileName().getText();
		String tableVar=ctx.tableVar().getText();
		st+="exportCsv("+tableVar+","+filename+");\n";
		return visitChildren(ctx);
	  }

	@Override public Symbol visitCol_access(UTLParser.Col_accessContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitItem_access(UTLParser.Item_accessContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitTableVar(UTLParser.TableVarContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitColumnVar(UTLParser.ColumnVarContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitItemVar(UTLParser.ItemVarContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitColumn(UTLParser.ColumnContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitItem(UTLParser.ItemContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitIndex(UTLParser.IndexContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitIndexRange(UTLParser.IndexRangeContext ctx) { return visitChildren(ctx); }

	@Override public Symbol visitFileName(UTLParser.FileNameContext ctx) { return visitChildren(ctx); }
}
