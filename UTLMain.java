import java.util.Scanner;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class UTLMain {
   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in);
      String lineText = null;
      int lineNum = 1;
      if (sc.hasNextLine())
         lineText = sc.nextLine();
      UTLParser parser = new UTLParser(null);
      // replace error listener:
      // parser.removeErrorListeners(); // remove ConsoleErrorListener
      // parser.addErrorListener(new ErrorHandlingListener());
      SemanticAnalysis visitor0 = new SemanticAnalysis();
      UTLCompiler visitor1 = new UTLCompiler();
      while (lineText != null) {
         // create a CharStream that reads from standard input:
         CharStream input = CharStreams.fromString(lineText + "\n");
         // create a lexer that feeds off of input CharStream:
         UTLLexer lexer = new UTLLexer(input);
         lexer.setLine(lineNum);
         lexer.setCharPositionInLine(0);
         // create a buffer of tokens pulled from the lexer:
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         // create a parser that feeds off the tokens buffer:
         parser.setInputStream(tokens);
         // begin parsing at r rule:
         ParseTree tree = parser.r();
         if (parser.getNumberOfSyntaxErrors() == 0) {
            // print LISP-style tree:
            // System.out.println(tree.toStringTree(parser));
            visitor0.visit(tree);
            visitor1.visit(tree);
         }
         if (sc.hasNextLine())
            lineText = sc.nextLine();
         else
            lineText = null;
         lineNum++;
      } // iteracao dos visitors acaba aqui
      System.out.println("No errors were found, compiling code in file generatedCode.java");
      String st = visitor1.getST();
      st += "}\n";
      // funçoes
      st += "//Funcoes\n";
      // tableDeclareJoining
      st += "public static Table tableJoin(Table t1, Table t2,String var){\n";
      st += "Table t= new Table(var, Type.table);\n";
      st += "for(int c=0; c<t1.size();c++){\n";
      st += "for(int ctwo=0; ctwo<t2.size();ctwo++){\n";
      st += "if((t1.get(c).getName()).equals(t2.get(ctwo).getName())){\n";
      st += "String n=t1.get(c).getName();\nt.addColumn(t1.get(c));\nfor (Item item: t2.get(ctwo).items){t.get(n).add(item);}\nbreak;}}}\n";
      st += "if (t.size()==0){\n";
      st += "System.out.println(\"Não havia colunas iguais, a tabela ficou vazia\");\n";
      st += "}return t;\n";
      st += "}\n";
      // tableDeclareBase
      st += "public static void tableDeclare(Table t,ArrayList<String> listaCol,ArrayList<ArrayList<String>> listaItens, int lim){\n";
      st += "int i=0;String col;ArrayList<String> itens=new ArrayList<>();Column c;int l=0;\n";
      st += "while(i<lim){\n";
      st += "col=listaCol.get(i);\nitens=listaItens.get(i);\n";
      st += "c= new Column(\"\", Type.column,col);\n";
      st += "for(l=0; l<itens.size();l++){\nc.add(new Item(\"\",Type.item, itens.get(l)));\n}\nt.addColumn(c);\ni++;\n}\n";
      st += "System.out.println(\"Table \"+ t.getName()+\" added/changed successfully\");\n";
      st += "}\n";
      // colDeclareOne1
      st += "public static void ColDeclareOne1(Column x,Table tb,String coluna,Integer idx,String it,Integer f,Integer l){\n";
      st += "Column col=tb.get(coluna);\n";
      st += "x.setName(col.getName());\n";
      st += "if (idx!=-9){\nItem ite=col.get(idx);\nx.add(ite);\n}\n";
      st += "else if (it!=null){\nItem oldIte=col.get(it);\nx.add(oldIte);\n}\n";
      st += "else{\nfor(int j=f;j<=l;j++){\nItem item=col.get(j);\nx.add(item);\n}\n";
      st += "System.out.println(\"New column added successfully\");";
      st += "}}\n";
      // colDeclareOne2
      st += "public static void ColDeclareOne2(Column x,Table tb,int index_col,Integer idx,String it,Integer f,Integer l){\n";
      st += "Column idx_col=tb.get(index_col);\n";
      st += "x.setName(idx_col.getName());\n";
      st += "if (idx!=-9){\nItem ite=idx_col.get(idx);\nx.add(ite);\n}\n";
      st += "else if (it!=null){\nItem oldIte=idx_col.get(it);\nx.add(oldIte);\n}\n";
      st += "else{\nfor(int j=f;j<=l;j++){\nItem item=idx_col.get(j);\nx.add(item);\n}\n";
      st += "System.out.println(\"New column added successfully\");";
      st += "}}\n";
      // colDeclareTwo
      st += "public static void ColDeclareTwo(Column x,Table tb,String col_name,Integer idx){\n";
      st += "Column getC;\n";
      st += "if (col_name!=null){\ngetC=tb.get(col_name);\n}\n";
      st += "else{\ngetC=tb.get(idx);\n}\n";
      st += "x.setName(getC.getName());\n";
      st += "for(int i=0; i<getC.size();i++){\nx.add(getC.get(i));\n}\n";
      st += "System.out.println(\"New column added successfully\");";
      st += "}\n";
      // alterColumn
      st += "public static void alterColumn(Table t, String column, String newNome){\n";
      st += "if(t.contains(column)){\n";
      st += "Column toChange=t.get(column);\n";
      st += "toChange.setName(newNome);\n";
      st += "}\n";
      st += "}\n";
      // alterColumn2
      st += "public static void alterColumn_index(Table t , int index, String newNome){\n";
      st += "Column toChange=t.get(index);\n";
      st += "toChange.setName(newNome);\n";
      st += "}\n";
      // deleteTable
      st += "public static void deleteTable(Table t){\n";
      st += "if(t.size()==0){\n";
      st += "System.out.println(\"Conteúdo da tabela vazio\");\n";
      st += "}\n";
      st += "while(t.size()!=0){\n";
      st += "int i=0;\n";
      st += "t.colunas.remove(i);\n";
      st += "i++;\n";
      st += "}\n";
      st += "}\n";
      // exportCsv
      st += "public static void exportCsv(Table t, String file){\n";
      st += "int p=0;\n";
      st += "boolean flag=true;\n";
      st += "try(Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + \".csv\"),(\"utf-8\")))){\n";
      st += "while(flag){\n";
      st += "for(int c=0;c<t.colunas.size();c++){\n";
      st += "Column col=t.get(c);\n";
      st += "if(p>=col.size()){\n";
      st += "flag=false;\n";
      st += "break;\n";
      st += "}\n";
      st += "writer.write(col.get(p).getValue());\n";
      st += "if(c==t.colunas.size()-1){\n";
      st += "writer.write(\"\");\n";
      st += "}\n";
      st += "else{\n";
      st += "writer.write(\",\");\n";
      st += "}\n";
      st += "}\n";
      st += "writer.write(\"\\n\");\n";
      st += "p++;\n";
      st += "}\n";
      st += "}\n";
      st += "catch (UnsupportedEncodingException e) {}\n";
      st += "catch (FileNotFoundException e){}\n";
      st += "catch (IOException e){}\n";
      st += "System.out.println(\"Ficheiro criado com o nome \"+ file+\".csv\");\n";
      st += "}\n";
      // deleteColumn
      st += "public static void deleteColumn(Table t, int index){\n";
      st += "String delc=t.get(index).getName();\n";
      st += "t.delete(delc);\n";
      st += "}\n";
      // deleteColumn2
      st += "public static void deleteColumn_column(Table t, String column){\n";
      st += "String delc=t.get(column).getName();\n";
      st += "t.delete(delc);\n";
      st += "}\n";
      // deleteColumn3
      st += "public static void deleteColumn_indexRange(Table t, String indexRange){\n";
      st += "int first=Integer.parseInt(indexRange.split(\"-\")[0]);\n";
      st += "int last=Integer.parseInt(indexRange.split(\"-\")[1]);\n";
      st += "ArrayList<String> nomes=new ArrayList<>();\n";
      st += "for(int i=first;i<=last;i++){\n";
      st += "nomes.add(t.get(i).getName());\n";
      st += "}\n";
      st += "for (String s: nomes){\n";
      st += "t.delete(s);\n}\n";
      st += "}\n";

      // AalterItem
      st += "public static void alterItem1(Table t, int col_index, int it_index, String item){\n";
      st += "Column c=t.get(col_index);\n";
      st += "Item iten=c.get(it_index);\n";
      st += "iten.setValue(item);\n";
      st += "}\n";

      st += "public static void alterItem2(Table t, int col_index, int it_index, Item itemVar){\n";
      st += "Column c=t.get(col_index);\n";
      st += "Item it=c.get(it_index);\n";
      st += "it.setValue(itemVar.getValue());\n";
      st += "}\n";
      st += "public static void alterItem3(Table t, String col_name, int it_index, String item){\n";
      st += "Column c=t.get(col_name);\n";
      st += "Item it= c.get(it_index);\n";
      st += "it.setValue(item);\n";
      st += "}\n";

      st += "public static void alterItem4(Table t, String col_name, int it_index, Item itemVar){\n";
      st += "Column c=t.get(col_name);\n";
      st += "Item it=c.get(it_index);\n";
      st += "it.setValue(itemVar.getValue());\n";
      st += "}\n";
      st += "public static void alterItem5(Table t, String col_name, String it_name, String item){\n";
      st += "Column c=t.get(col_name);\n";
      st += "Item it=c.get(it_name);\n";
      st += "it.setValue(item);\n";
      st += "}\n";

      st += "public static void alterItem6(Table t, String col_name, String it_name, Item itemVar){\n";
      st += "Column c=t.get(col_name);\n";
      st += "Item it=c.get(it_name);\n";
      st += "it.setValue(itemVar.getValue());\n";
      st += "}\n";

      st += "public static void alterItem7(Table t, int col_index, String it_name, String item){\n";
      st += "Column c=t.get(col_index);\n";
      st += "Item it=c.get(it_name);\n";
      st += "it.setValue(item);\n";
      st += "}\n";

      st += "public static void alterItem8(Table t, int col_index, String it_name, Item itemVar){\n";
      st += "Column c=t.get(col_index);\n";
      st += "Item it=c.get(it_name);\n";
      st += "it.setValue(itemVar.getValue());\n";
      st += "}\n";

      // deleteItem
      st += "public static void deleteItem1(Table t, int col_idx, int it_idx){\n";
      st += "Column c=t.get(col_idx);\n";
      st += "Item ite=c.get(it_idx);\n";
      st += "c.delete(ite.getValue());\n";
      st += "}\n";

      st += "public static void deleteItem2(Table t, int col_idx, String ite_value){\n";
      st += "Column c=t.get(col_idx);\n";
      st += "c.delete(ite_value);\n";
      st += "}\n";

      st += "public static void deleteItem3(Table t, int col_idx, String indexRange){\n";
      st += "Column c=t.get(col_idx);\n";
      st += "int first=Integer.parseInt(indexRange.split(\"-\")[0]);\n";
      st += "int last=Integer.parseInt(indexRange.split(\"-\")[1]);\n";
      st += "ArrayList<String> items=new ArrayList<>();\n";
      st += "for(int p=first; p<=last;p++){\n";
      st += "items.add(c.get(p).getValue());\n";
      st += "}\n";
      st += "for (String s: items){\n";
      st += "c.delete(s);\n";
      st += "}\n";
      st += "}\n";

      st += "public static void deleteItem4(Table t, String col_name, int it_idx){\n";
      st += "Column c=t.get(col_name);\n";
      st += "Item ite=c.get(it_idx);\n";
      st += "c.delete(ite.getValue());\n";
      st += "}\n";

      st += "public static void deleteItem5(Table t, String col_name, String ite_value){\n";
      st += "Column c=t.get(col_name);\n";
      st += "c.delete(ite_value);\n";
      st += "}\n";

      st += "public static void deleteItem6(Table t, String col_name, String indexRange){\n";
      st += "Column c=t.get(col_name);\n";
      st += "int first=Integer.parseInt(indexRange.split(\"-\")[0]);\n";
      st += "int last=Integer.parseInt(indexRange.split(\"-\")[1]);\n";
      st += "ArrayList<String> items=new ArrayList<>();\n";
      st += "for(int p=first; p<=last;p++){\n";
      st += "items.add(c.get(p).getValue());\n";
      st += "}\n";
      st += "for (String s: items){\n";
      st += "c.delete(s);\n";
      st += "}}\n";

      //putCol
      st+="public static void putCol(Table t, String name_col){\n";
      st+="t.addColumn(new Column(\"\",Type.column,name_col));\n";
      st+="}\n";

      st+="public static void putColVar(Table t, Column c){\n";
      st+="t.addColumn(c);\n";
      st+="}\n";
      //putItem
      st+="public static void putItem1(Table t, int col_index, String item){\n";
      st+="Column c=t.get(col_index);\n";
      st+="c.add(new Item(\"\", Type.item, item));\n";
      st+="}\n";

      st+="public static void putItem2(Table t, int col_index, Item itemVar){\n";
      st+="Column c=t.get(col_index);\n";
      st+="c.add(new Item(\"\", Type.item, itemVar.getValue()));\n";
      st+="}\n";

      st+="public static void putItem3(Table t, String col_name, String item){\n";
      st+="Column c=t.get(col_name);\n";
      st+="c.add(new Item(\"\", Type.item, item));\n";
      st+="}\n";

      st+="public static void putItem4(Table t, String col_name, Item itemVar){\n";
      st+="Column c=t.get(col_name);\n";
      st+="c.add(new Item(\"\", Type.item, itemVar.getValue()));\n";
      st+="}\n";

      // fim
      st += "//end\n}\n";
      try {
         throw new IOException();
      } catch (IOException e) {
         BufferedWriter writer = new BufferedWriter(new FileWriter("generatedCode.java"));
         writer.write(st);
         writer.close();
      }
   }
}
