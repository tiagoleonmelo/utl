grammar UTL;

r: (declaration | operation | assignment | print)* EOF;

declaration: tableDeclare   |
             columnDeclare  |
             itemDeclare    |
             columnNameDec
             ;

assignment: tableAssign      |
            columnAssign     |
            columnNameAssign |
            itemAssign
            ;

operation: deleteTable  |
					 deleteColumn |
           deleteItem   |
           putCol       |
           putItem      |
    		   alterColumn  |
    		   alterItem    |
           exportCSV
           ;

print: 'print' tableVar; //aqui TableVar representa qualquer tipo de variavel (table,column,item) e não só Tables
// Declarations

tableDeclare    :'Table' tableVar '=' '[' columnDefinition (';' columnDefinition )*  ']'  #TableDeclareBase
                |'Table' tableVar '=' tableVar'.join('tableVar')'                         #TableDeclareJoining
                ;

columnDeclare:   'Column' columnVar '=' tableVar'.col('col_access').item('(item_access|indexRange)')'  #ColDeclareOne
                |'Column' columnVar '=' tableVar'.col('col_access')'                                  #ColDeclareTwo
                |'Column' columnVar '=' columnDefinition                                              #ColDeclareThree
                ;

columnDefinition: column ':' (item|itemVar) (',' (item|itemVar) )*  #ColBase
                | column '->' columnVar                              #ColFetch
                | column                                            #ColEmpty
                ;

columnNameDec   : 'Item' itemVar '=' tableVar '.col(' col_access').name' ;

itemDeclare     : 'Item' itemVar '=' tableVar '.col(' col_access ').item(' item_access ')' #itemDeclTable
                | 'Item' itemVar '=' item #itemDeclStr
                | 'Item' v1=itemVar '=' v2=itemVar #itemDeclVar
                ;


// Assignments

tableAssign     : tableVar '=' '[' columnDefinition (';' columnDefinition )*  ']'  #TableAssignBase
                | tableVar '=' tableVar'.join('tableVar')'                         #TableAssignJoining
                ;

columnAssign    : columnVar '=' tableVar'.col('col_access').item('(item_access|indexRange)')'  #ColAssignOne
                | columnVar '=' tableVar'.col('col_access')'                                  #ColAssignTwo
                | columnVar '=' columnDefinition                                              #ColAssignThree
                ;

columnNameAssign: itemVar '=' tableVar'.col('col_access').name' ;

itemAssign      : itemVar '=' tableVar '.col(' col_access ').item(' item_access ')' #itemAssignTable
                | v1=itemVar '=' v2=itemVar #itemAssignVar
                ;

// Operations

deleteTable : tableVar'.del' ;

deleteColumn: tableVar'.col('(col_access|indexRange)').del' ;

deleteItem  : tableVar'.col('col_access').item('(item_access|indexRange)').del' ;


putCol  : tableVar'.put('(column|columnVar) (',' (column|columnVar))* ')' ;

putItem : tableVar'.col('col_access').put('(item|itemVar) (',' (item|itemVar) )* ')' ;


alterColumn : tableVar'.col('col_access').alter('column')';

alterItem   : tableVar'.col('col_access').item('item_access').alter('(item|itemVar)')' ;

exportCSV   : tableVar'.csv('fileName')' ; //Exports a table to a CSV

// "Terminator" tokens

col_access:	index
          | column
          ;

item_access: index
           | item
           ;

tableVar    :ID;
columnVar   :ID;
itemVar     :ID;
column      :STRING;
item        :STRING;
index       :INT ;
indexRange  :INT'-'INT ;
fileName    :STRING;


STRING      : '"'[a-zA-Z_0-9 .]+'"';
ID          :[a-zA-Z_][a-zA-Z_0-9]*;
INT         :[0-9]+;
COMMENT     : '#' .*? '\n' -> skip;
WHITESPACE  : [ \t\r\n]+ -> skip;
ERROR       : .; // Na pior das hipoteses gera um error
