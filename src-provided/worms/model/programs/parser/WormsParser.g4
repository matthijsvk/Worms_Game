// ANTLR v4 Grammar for controling Worms.
// Check the tutorial at http://jnb.ociweb.com/jnb/jnbJun2008.html
// or http://www.antlr.org/ for details.


grammar WormsParser;

@header { package worms.model.programs.parser; }


@members
{

}


// ------------------------------------------------------------------------
// --- Eval and Related Definitions ---------------------------------------
// ------------------------------------------------------------------------
eval:	     (decl SEMICOLON
            | action SEMICOLON
            | assign SEMICOLON
            | PRINT expr SEMICOLON
            | ctrl
            | SEMICOLON) (eval)?
;
decl:         type IDENTIFIER (ASSIGN expr)?;
action:       TURN expr
            | MOVE
            | JUMP
            | TOGGLEWEAP
            | FIRE expr
            | SKIP
;
unop:         GETX expr
            | GETY expr
            | GETDIR expr
            | GETRADIUS expr
            | GETAP expr
            | GETMAXAP expr
            | GETHP expr
            | GETMAXHP expr
            | SAMETEAM expr
            | SEARCHOBJ expr
            | ISWORM expr
            | ISFOOD expr
//            | ISTERRAIN expr
            | SQRT LEFT_PAREN expr RIGHT_PAREN
            | SIN LEFT_PAREN expr RIGHT_PAREN
            | COS LEFT_PAREN expr RIGHT_PAREN
            | NOT expr
;
ctrl:       ifthenelse | whiledo | foreach;
ifthenelse: IF expr (THEN)? LEFT_BRACE (eval)? RIGHT_BRACE
            (ELSE LEFT_BRACE (eval)? RIGHT_BRACE)?;
whiledo:    WHILE expr (DO)? LEFT_BRACE (eval)? RIGHT_BRACE;
foreach:    FOREACH LEFT_PAREN entityspec COMMA IDENTIFIER RIGHT_PAREN
            (DO)? LEFT_BRACE (eval)? RIGHT_BRACE;
assign:     IDENTIFIER ASSIGN expr;
expr:         NUMBER
            | IDENTIFIER
            | LEFT_PAREN expr RIGHT_PAREN
            | namedconst
            | unop
            | expr binop expr
;

// ------------------------------------------------------------------------
// --- Named Constants ----------------------------------------------------
// ------------------------------------------------------------------------

namedconst: SELF | TRUE | FALSE | NULL;
SELF:      'self';
TRUE:      'true';
FALSE:     'false';
NULL:      'null';


// ------------------------------------------------------------------------
// --- Types and Specifiers -----------------------------------------------
// ------------------------------------------------------------------------
PRINT:     'print';

type:   BOOL | DOUBLE | ENTITY;
BOOL:      'bool';
DOUBLE:    'double';
ENTITY:    'entity';

entityspec:   WORM
            | FOOD
//            | TERRAIN
            | ANY;
WORM:      'worm';
FOOD:      'food';
TERRAIN:   'terrain';
ANY:       'any';


// ------------------------------------------------------------------------
// --- Unary Operations ---------------------------------------------------
// ------------------------------------------------------------------------
GETX:      'getx';
GETY:      'gety';
GETDIR:    'getdir';
GETRADIUS: 'getradius';
GETAP:     'getap';
GETMAXAP:  'getmaxap';
GETHP:     'gethp';
GETMAXHP:  'getmaxhp';
SAMETEAM:  'sameteam';
SEARCHOBJ: 'searchobj';
ISWORM:    'isworm';
ISFOOD:    'isfood';
//ISTERRAIN: 'isterrain';
SQRT:      'sqrt';
SIN:       'sin';
COS:       'cos';
NOT:       '!';


// ------------------------------------------------------------------------
// --- Space Entity Actions -----------------------------------------------
// ------------------------------------------------------------------------
TURN:      'turn';
MOVE:      'move';
JUMP:      'jump';
TOGGLEWEAP:'toggleweap';
FIRE:      'fire'; 
SKIP:      'skip';


// ------------------------------------------------------------------------
// --- Control Flow -------------------------------------------------------
// ------------------------------------------------------------------------
IF:        'if';
THEN:      'then';
ELSE:      'else';
WHILE:     'while';
DO:        'do';
FOREACH:   'foreach';


// ------------------------------------------------------------------------
// --- Assignment and Arithmetics -----------------------------------------
// ------------------------------------------------------------------------
ASSIGN: ':=';

binop:  MUL | DIV | ADD | SUB |
        NEQ | EQ | LT | GT | LEQ | GEQ |
        AND | OR;
MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
EQ:  '==';
NEQ: '!=';
LT:  '<';
GT:  '>';
LEQ: '<=';
GEQ: '>=';
AND: '&&';
OR:  '||';


// ------------------------------------------------------------------------
// --- Literals and Variables ---------------------------------------------
// ------------------------------------------------------------------------

NUMBER:  INTEGER | FLOAT;
FLOAT:   INTEGER '.' '0'..'9'+;
INTEGER: (('-'|'+')? '0'..'9' '0'..'9'*);

IDENTIFIER: LETTER (LETTER | DIGIT | '_')*;
fragment LETTER: LOWER | UPPER;
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment DIGIT: '0'..'9';


// ------------------------------------------------------------------------
// --- Syntactical Ballast ------------------------------------------------
// ------------------------------------------------------------------------
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';

SEMICOLON: ';';
COMMA: ',';

// Skip runs of newline, space and tab characters.
WHITESPACE: [ \t\r\n]+ -> skip;
 
// Single-line comments begin with //, are followed by any characters
// other than those in a newline, and are terminated by newline characters.
SINGLE_COMMENT: '//' ~('\r' | '\n')* NEWLINE -> skip;
fragment NEWLINE: ('\r'? '\n')+;

