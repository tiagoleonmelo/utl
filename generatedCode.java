import java.util.*;
import java.io.*;
public class generatedCode{
public static void main(String [] args){
//UsefulVariables
ArrayList<String> nomes=new ArrayList<>();
ArrayList<ArrayList<String>> itens=new ArrayList<>();
ArrayList<String> currentItems=new ArrayList<>();
//----Inicio do Programa
Column c1 = new Column("",Type.column,"");
c1.setName("colunaVaiSerImportada");
c1.add(new Item("",Type.item,"v1"));
c1.add(new Item("",Type.item,"v2"));
c1.add(new Item("",Type.item,"v3"));
c1.add(new Item("",Type.item,"v4"));
Item i1=new Item("i1",Type.item,"souItem");
System.out.println("Item saved successfully");
Table t1 = new Table("t1", Type.table);
nomes.add(0,"col1");
currentItems.add("1");
currentItems.add("2");
currentItems.add("3");
currentItems.add("4");
itens.add(0,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(1,"colVazia");
itens.add(1,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(2,"colunaImportada");
for (Item s:c1.items){currentItems.add(s.getValue());}
itens.add(2,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(3,"col4");
currentItems.add("itemNormal");
currentItems.add(i1.getValue());
itens.add(3,new ArrayList<>(currentItems));
currentItems.clear();
tableDeclare(t1,nomes,itens,4);
nomes.clear();itens.clear();
System.out.println(t1.toString());
Table t3 = new Table("t3", Type.table);
nomes.add(0,"nome");
currentItems.add("mario");
currentItems.add("maria");
currentItems.add("jose");
itens.add(0,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(1,"numero");
currentItems.add("1");
currentItems.add("2");
currentItems.add("3");
itens.add(1,new ArrayList<>(currentItems));
currentItems.clear();
tableDeclare(t3,nomes,itens,2);
nomes.clear();itens.clear();
Table t4 = new Table("t4", Type.table);
nomes.add(0,"indices");
currentItems.add("1");
currentItems.add("2");
currentItems.add("5");
itens.add(0,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(1,"nome");
currentItems.add("ines");
currentItems.add("carla");
currentItems.add("spanishInquisition");
itens.add(1,new ArrayList<>(currentItems));
currentItems.clear();
tableDeclare(t4,nomes,itens,2);
nomes.clear();itens.clear();
Table t5=tableJoin(t3,t4,"t5");
System.out.println(t5.toString());
Column c2=new Column("c2", Type.column,"");
ColDeclareTwo(c2,t4,"nome",-9);
System.out.println(c2.toString());
Column c3=new Column("c3", Type.column,"");
ColDeclareOne2(c3,t1,0,-9,null,0,2);
System.out.println(c3.toString());
Item i2=new Item("i2",Type.item,"");
i2.setValue(t1.get(1).getName());
System.out.println("Item added successfully");
System.out.println(i2.toString());
Item i3=t1.get(0).get(0);
System.out.println("Item saved successfully");
System.out.println(i3.toString());
deleteTable(t1);
nomes.add(0,"nome");
currentItems.add("mario");
currentItems.add("maria");
currentItems.add("jose");
itens.add(0,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(1,"numero");
currentItems.add("1");
currentItems.add("2");
currentItems.add("3");
itens.add(1,new ArrayList<>(currentItems));
currentItems.clear();
tableDeclare(t1,nomes,itens,2);
nomes.clear();itens.clear();
System.out.println(t1.toString());
i3=i2;
System.out.println(i3.toString());
deleteTable(t3);
System.out.println(t3.toString());
deleteColumn_column(t1,"nome");
System.out.println(t1.toString());
deleteItem6(t4,"nome","0-1");
System.out.println(t4.toString());
putColVar(t4,c2);
putColVar(t4,c1);
System.out.println(t4.toString());
putItem3(t4,"nome","eu");
System.out.println(t4.toString());
alterItem5(t4,"nome","eu","tu");
System.out.println(t4.toString());
Table t6 = new Table("t6", Type.table);
nomes.add(0,"nome");
currentItems.add("mario");
currentItems.add("maria");
currentItems.add("jose");
itens.add(0,new ArrayList<>(currentItems));
currentItems.clear();
nomes.add(1,"numero");
currentItems.add("1");
currentItems.add("2");
currentItems.add("3");
itens.add(1,new ArrayList<>(currentItems));
currentItems.clear();
tableDeclare(t6,nomes,itens,2);
nomes.clear();itens.clear();
System.out.println(t6.toString());
exportCsv(t6,"file1");
}
//Funcoes
public static Table tableJoin(Table t1, Table t2,String var){
Table t= new Table(var, Type.table);
for(int c=0; c<t1.size();c++){
for(int ctwo=0; ctwo<t2.size();ctwo++){
if((t1.get(c).getName()).equals(t2.get(ctwo).getName())){
String n=t1.get(c).getName();
t.addColumn(t1.get(c));
for (Item item: t2.get(ctwo).items){t.get(n).add(item);}
break;}}}
if (t.size()==0){
System.out.println("Não havia colunas iguais, a tabela ficou vazia");
}return t;
}
public static void tableDeclare(Table t,ArrayList<String> listaCol,ArrayList<ArrayList<String>> listaItens, int lim){
int i=0;String col;ArrayList<String> itens=new ArrayList<>();Column c;int l=0;
while(i<lim){
col=listaCol.get(i);
itens=listaItens.get(i);
c= new Column("", Type.column,col);
for(l=0; l<itens.size();l++){
c.add(new Item("",Type.item, itens.get(l)));
}
t.addColumn(c);
i++;
}
System.out.println("Table "+ t.getName()+" added/changed successfully");
}
public static void ColDeclareOne1(Column x,Table tb,String coluna,Integer idx,String it,Integer f,Integer l){
Column col=tb.get(coluna);
x.setName(col.getName());
if (idx!=-9){
Item ite=col.get(idx);
x.add(ite);
}
else if (it!=null){
Item oldIte=col.get(it);
x.add(oldIte);
}
else{
for(int j=f;j<=l;j++){
Item item=col.get(j);
x.add(item);
}
System.out.println("New column added successfully");}}
public static void ColDeclareOne2(Column x,Table tb,int index_col,Integer idx,String it,Integer f,Integer l){
Column idx_col=tb.get(index_col);
x.setName(idx_col.getName());
if (idx!=-9){
Item ite=idx_col.get(idx);
x.add(ite);
}
else if (it!=null){
Item oldIte=idx_col.get(it);
x.add(oldIte);
}
else{
for(int j=f;j<=l;j++){
Item item=idx_col.get(j);
x.add(item);
}
System.out.println("New column added successfully");}}
public static void ColDeclareTwo(Column x,Table tb,String col_name,Integer idx){
Column getC;
if (col_name!=null){
getC=tb.get(col_name);
}
else{
getC=tb.get(idx);
}
x.setName(getC.getName());
for(int i=0; i<getC.size();i++){
x.add(getC.get(i));
}
System.out.println("New column added successfully");}
public static void alterColumn(Table t, String column, String newNome){
if(t.contains(column)){
Column toChange=t.get(column);
toChange.setName(newNome);
}
}
public static void alterColumn_index(Table t , int index, String newNome){
Column toChange=t.get(index);
toChange.setName(newNome);
}
public static void deleteTable(Table t){
if(t.size()==0){
System.out.println("Conteúdo da tabela vazio");
}
while(t.size()!=0){
int i=0;
t.colunas.remove(i);
i++;
}
}
public static void exportCsv(Table t, String file){
int p=0;
boolean flag=true;
try(Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + ".csv"),("utf-8")))){
while(flag){
for(int c=0;c<t.colunas.size();c++){
Column col=t.get(c);
if(p>=col.size()){
flag=false;
break;
}
writer.write(col.get(p).getValue());
if(c==t.colunas.size()-1){
writer.write("");
}
else{
writer.write(",");
}
}
writer.write("\n");
p++;
}
}
catch (UnsupportedEncodingException e) {}
catch (FileNotFoundException e){}
catch (IOException e){}
System.out.println("Ficheiro criado com o nome "+ file+".csv");
}
public static void deleteColumn(Table t, int index){
String delc=t.get(index).getName();
t.delete(delc);
}
public static void deleteColumn_column(Table t, String column){
String delc=t.get(column).getName();
t.delete(delc);
}
public static void deleteColumn_indexRange(Table t, String indexRange){
int first=Integer.parseInt(indexRange.split("-")[0]);
int last=Integer.parseInt(indexRange.split("-")[1]);
ArrayList<String> nomes=new ArrayList<>();
for(int i=first;i<=last;i++){
nomes.add(t.get(i).getName());
}
for (String s: nomes){
t.delete(s);
}
}
public static void alterItem1(Table t, int col_index, int it_index, String item){
Column c=t.get(col_index);
Item iten=c.get(it_index);
iten.setValue(item);
}
public static void alterItem2(Table t, int col_index, int it_index, Item itemVar){
Column c=t.get(col_index);
Item it=c.get(it_index);
it.setValue(itemVar.getValue());
}
public static void alterItem3(Table t, String col_name, int it_index, String item){
Column c=t.get(col_name);
Item it= c.get(it_index);
it.setValue(item);
}
public static void alterItem4(Table t, String col_name, int it_index, Item itemVar){
Column c=t.get(col_name);
Item it=c.get(it_index);
it.setValue(itemVar.getValue());
}
public static void alterItem5(Table t, String col_name, String it_name, String item){
Column c=t.get(col_name);
Item it=c.get(it_name);
it.setValue(item);
}
public static void alterItem6(Table t, String col_name, String it_name, Item itemVar){
Column c=t.get(col_name);
Item it=c.get(it_name);
it.setValue(itemVar.getValue());
}
public static void alterItem7(Table t, int col_index, String it_name, String item){
Column c=t.get(col_index);
Item it=c.get(it_name);
it.setValue(item);
}
public static void alterItem8(Table t, int col_index, String it_name, Item itemVar){
Column c=t.get(col_index);
Item it=c.get(it_name);
it.setValue(itemVar.getValue());
}
public static void deleteItem1(Table t, int col_idx, int it_idx){
Column c=t.get(col_idx);
Item ite=c.get(it_idx);
c.delete(ite.getValue());
}
public static void deleteItem2(Table t, int col_idx, String ite_value){
Column c=t.get(col_idx);
c.delete(ite_value);
}
public static void deleteItem3(Table t, int col_idx, String indexRange){
Column c=t.get(col_idx);
int first=Integer.parseInt(indexRange.split("-")[0]);
int last=Integer.parseInt(indexRange.split("-")[1]);
ArrayList<String> items=new ArrayList<>();
for(int p=first; p<=last;p++){
items.add(c.get(p).getValue());
}
for (String s: items){
c.delete(s);
}
}
public static void deleteItem4(Table t, String col_name, int it_idx){
Column c=t.get(col_name);
Item ite=c.get(it_idx);
c.delete(ite.getValue());
}
public static void deleteItem5(Table t, String col_name, String ite_value){
Column c=t.get(col_name);
c.delete(ite_value);
}
public static void deleteItem6(Table t, String col_name, String indexRange){
Column c=t.get(col_name);
int first=Integer.parseInt(indexRange.split("-")[0]);
int last=Integer.parseInt(indexRange.split("-")[1]);
ArrayList<String> items=new ArrayList<>();
for(int p=first; p<=last;p++){
items.add(c.get(p).getValue());
}
for (String s: items){
c.delete(s);
}}
public static void putCol(Table t, String name_col){
t.addColumn(new Column("",Type.column,name_col));
}
public static void putColVar(Table t, Column c){
t.addColumn(c);
}
public static void putItem1(Table t, int col_index, String item){
Column c=t.get(col_index);
c.add(new Item("", Type.item, item));
}
public static void putItem2(Table t, int col_index, Item itemVar){
Column c=t.get(col_index);
c.add(new Item("", Type.item, itemVar.getValue()));
}
public static void putItem3(Table t, String col_name, String item){
Column c=t.get(col_name);
c.add(new Item("", Type.item, item));
}
public static void putItem4(Table t, String col_name, Item itemVar){
Column c=t.get(col_name);
c.add(new Item("", Type.item, itemVar.getValue()));
}
//end
}
