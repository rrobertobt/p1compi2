package edu.robertob.p1compi2.analysis;

//import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.p1compi2.engine.structs.PError;
import java_cup.runtime.Symbol;
import java.util.LinkedList;

%%

//codigo de usuario
%{
    private LinkedList<PError> errorList = new LinkedList<>();
    public LinkedList<PError> getLexicalErrorList(){
        return errorList;
    }
%}

%init{
    yyline = 1;
    yycolumn = 1;
    errorList = new LinkedList<>();
%init}

%cup
%class PLexer

%eofval{
  return new Symbol(sym.EOF, yyline, yycolumn, "EOF");
%eofval}

%public
%line
%char
%column
%full
//%debug
%ignorecase

OPENPAR="("
CLOSEPAR=")"
PLUS="+"
MINUS="-"
UNDERSCORE="_"
ASTERISK="*"
DOUBLEASTERISK="**"
SLASH="/"
MODULO="%"
EQUALS="="
DOUBLEEQUALS="=="
NEGATION="!"
EXCLAMATIONEQUALS="!="
LTGT="<>"
LESS="<"
LESSEQUALS="<="
GREATER=">"
GREATEREQUALS=">="
ARROW="=>"
OR="||"
AND="&&"
XOR=\^
SEMICOLON=";"
COLON=":"
COMMA=","
OPENBRACE="{"
CLOSEBRACE="}"
OPENBRACKET=[\[]
CLOSEBRACKET=[\]]
BLANKS=[\ \r\t\f\n]+
INTEGER=[0-9]+
DECIMAL=[0-9]+"."[0-9]+
ID=[a-zA-Z0-9_]+

//STRING = [\"]([^\"])*[\"]
STRING = [\']([^\'\\]|\\.)*[\']
//CHAR = [']([^\'])*[']
CHAR = ['][^'][']

// format: { comment }
COMMENT_ONE_LINE = \{[^}]*\}
// format: (* comment *)
COMMENT_MULTIPLE_LINES = \(\*([^*]|\*+[^)*])*\*+\)

DOT="."


// Palabras reservadas
/*
and
array
begin
case
const
div
do
downto
else
end
file
for
function
goto
if
in
label
mod
nil
not
of
or
packed
procedure
program
record
repeat
set
then
to
type
until
var
while
with
*/

RW_AND="and"
RW_ARRAY="array"
RW_BEGIN="begin"
RW_CASE="case"
RW_CONST="const"
RW_DIV="div"
RW_DO="do"
RW_DOWNTO="downto"
RW_ELSE="else"
RW_END="end"
RW_FILE="file"
RW_FOR="for"
RW_FUNCTION="function"
RW_GOTO="goto"
RW_IF="if"
RW_IN="in"
RW_LABEL="label"
RW_MOD="mod"
RW_NIL="nil"
RW_NOT="not"
RW_OF="of"
RW_OR="or"
RW_PACKED="packed"
RW_PROCEDURE="procedure"
RW_PROGRAM="program"
//RW_RECORD="record"
RW_REPEAT="repeat"
RW_SET="set"
RW_THEN="then"
RW_TO="to"
RW_TYPE="type"
RW_UNTIL="until"
RW_VAR="var"
RW_WHILE="while"
RW_WITH="with"

// Otras palabras reservadas
RW_TRUE="true"
RW_FALSE="false"
RW_INTEGER="integer"
RW_REAL="real"
RW_CHAR="char"
RW_STRING="string"
RW_BOOLEAN="boolean"
RW_RECORD="record"

RW_AND_THEN="and then"
RW_OR_ELSE="or else"

RW_WRITELN="writeln"
RW_READLN="readln"

%%

<YYINITIAL> {COMMENT_ONE_LINE} {}
<YYINITIAL> {COMMENT_MULTIPLE_LINES} {}
//<YYINITIAL> {XOR} {
//          return new Symbol(sym.XOR, yyline, yycolumn,yytext());}

// reserved words
<YYINITIAL> {RW_AND_THEN} {return new Symbol(sym.RW_AND_THEN, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_OR_ELSE} {return new Symbol(sym.RW_OR_ELSE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_AND} {return new Symbol(sym.RW_AND, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_ARRAY} {return new Symbol(sym.RW_ARRAY, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_BEGIN} {return new Symbol(sym.RW_BEGIN, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_CASE} {return new Symbol(sym.RW_CASE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_CONST} {return new Symbol(sym.RW_CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_DIV} {return new Symbol(sym.RW_DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_DO} {return new Symbol(sym.RW_DO, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_DOWNTO} {return new Symbol(sym.RW_DOWNTO, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_ELSE} {return new Symbol(sym.RW_ELSE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_END} {return new Symbol(sym.RW_END, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_FILE} {return new Symbol(sym.RW_FILE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_FOR} {return new Symbol(sym.RW_FOR, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_FUNCTION} {return new Symbol(sym.RW_FUNCTION, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_GOTO} {return new Symbol(sym.RW_GOTO, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_IF} {return new Symbol(sym.RW_IF, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_IN} {return new Symbol(sym.RW_IN, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_LABEL} {return new Symbol(sym.RW_LABEL, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_MOD} {return new Symbol(sym.RW_MOD, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_NIL} {return new Symbol(sym.RW_NIL, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_NOT} {return new Symbol(sym.RW_NOT, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_OF} {return new Symbol(sym.RW_OF, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_OR} {return new Symbol(sym.RW_OR, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_PACKED} {return new Symbol(sym.RW_PACKED, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_PROCEDURE} {return new Symbol(sym.RW_PROCEDURE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_PROGRAM} {return new Symbol(sym.RW_PROGRAM, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_RECORD} {return new Symbol(sym.RW_RECORD, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_REPEAT} {return new Symbol(sym.RW_REPEAT, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_SET} {return new Symbol(sym.RW_SET, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_THEN} {return new Symbol(sym.RW_THEN, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_TO} {return new Symbol(sym.RW_TO, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_TYPE} {return new Symbol(sym.RW_TYPE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_UNTIL} {return new Symbol(sym.RW_UNTIL, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_VAR} {return new Symbol(sym.RW_VAR, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_WHILE} {return new Symbol(sym.RW_WHILE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_WITH} {return new Symbol(sym.RW_WITH, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_TRUE} {return new Symbol(sym.RW_TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_FALSE} {return new Symbol(sym.RW_FALSE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_INTEGER} {return new Symbol(sym.RW_INTEGER, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_REAL} {return new Symbol(sym.RW_REAL, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_CHAR} {return new Symbol(sym.RW_CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_STRING} {return new Symbol(sym.RW_STRING, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_BOOLEAN} {return new Symbol(sym.RW_BOOLEAN, yyline, yycolumn,yytext());}




      <YYINITIAL> {DOT} {return new Symbol(sym.DOT, yyline, yycolumn,yytext());}
      <YYINITIAL> {UNDERSCORE} {return new Symbol(sym.UNDERSCORE, yyline, yycolumn,yytext());}
<YYINITIAL> {ARROW} {return new Symbol(sym.ARROW, yyline, yycolumn,yytext());}
<YYINITIAL> {LTGT} {return new Symbol(sym.NOT_EQUALS, yyline, yycolumn,yytext());}

<YYINITIAL> {OPENBRACKET} {return new Symbol(sym.OPENBRACKET, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEBRACKET} {return new Symbol(sym.CLOSEBRACKET, yyline, yycolumn,yytext());}

<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {INTEGER} {return new Symbol(sym.INTEGER, yyline, yycolumn,yytext());}
<YYINITIAL> {ID} {return new Symbol(sym.ID, yyline, yycolumn,yytext());}

<YYINITIAL> {CHAR} {
    String foundString = yytext();
    String stringObj = foundString.substring(1, foundString.length()-1);
    // Remember to transform the string to a char in CUP
    return new Symbol(sym.CHAR, yyline, yycolumn, stringObj);
}

<YYINITIAL> {STRING} {
    String foundString = yytext();
    String stringObj = foundString.substring(1, foundString.length()-1);
    return new Symbol(sym.STRING, yyline, yycolumn, stringObj);
    }


<YYINITIAL> {SEMICOLON} {return new Symbol(sym.SEMICOLON, yyline, yycolumn,yytext());}
<YYINITIAL> {COLON} {return new Symbol(sym.COLON, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENPAR} {return new Symbol(sym.OPENPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEPAR} {return new Symbol(sym.CLOSEPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENBRACE} {return new Symbol(sym.OPENBRACE, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEBRACE} {return new Symbol(sym.CLOSEBRACE, yyline, yycolumn,yytext());}
<YYINITIAL> {COMMA} {return new Symbol(sym.COMMA, yyline, yycolumn,yytext());}


<YYINITIAL> {PLUS} {return new Symbol(sym.PLUS, yyline, yycolumn,yytext());}
<YYINITIAL> {MINUS} {return new Symbol(sym.MINUS, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLEASTERISK} {return new Symbol(sym.DOUBLEASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {ASTERISK} {return new Symbol(sym.ASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {SLASH} {return new Symbol(sym.SLASH, yyline, yycolumn,yytext());}
<YYINITIAL> {MODULO} {return new Symbol(sym.MODULO, yyline, yycolumn,yytext());}

<YYINITIAL> {DOUBLEEQUALS} {return new Symbol(sym.DOUBLEEQUALS, yyline, yycolumn,yytext());}

<YYINITIAL> {EQUALS} {return new Symbol(sym.EQUALS, yyline, yycolumn,yytext());}
<YYINITIAL> {LESSEQUALS} {return new Symbol(sym.LESSEQUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {GREATEREQUALS} {return new Symbol(sym.GREATEREQUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {LESS} {return new Symbol(sym.LESS, yyline, yycolumn,yytext());}
<YYINITIAL> {GREATER} {return new Symbol(sym.GREATER, yyline, yycolumn,yytext());}
<YYINITIAL> {EXCLAMATIONEQUALS} {return new Symbol(sym.NOT_EQUALS, yyline, yycolumn,yytext());}
<YYINITIAL> {NEGATION} {return new Symbol(sym.NEGATION, yyline, yycolumn,yytext());}
<YYINITIAL> {OR} {return new Symbol(sym.OR, yyline, yycolumn,yytext());}
<YYINITIAL> {AND} {return new Symbol(sym.AND, yyline, yycolumn,yytext());}

<YYINITIAL> {BLANKS} {}


<YYINITIAL> . {
errorList.add(new PError("Lexico", "El caracter " + yytext() + " no pertenece al lenguaje, en: "+yyline+":"+yycolumn, yyline, yycolumn));
System.out.println("Error lexico: El caracter " + yytext() + " no pertenece al lenguaje, en: "+yyline+":"+yycolumn);
}