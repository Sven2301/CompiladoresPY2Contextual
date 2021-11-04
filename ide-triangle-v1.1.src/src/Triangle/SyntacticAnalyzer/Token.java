/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


final class Token extends Object {

  protected int kind;
  protected String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,
 /* CAMBIO
    @author: Marco
    @descrp: Se elimino begin y se agregaron mas palabras reservadas
 */
    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    //BEGIN		= 5,
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR = 9,// Agregada
    FROM = 10,// Agregada
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    LOCAL = 15,// Agregada
    OF			= 16,
    PROC		= 17,
    RANGE = 18,// Agregada
    RECORD		= 19,
    RECURSIVE = 20,// Agregada
    REPEAT = 21,// Agregada
          
    SKIP = 22,// Agregada
    THEN		= 23,
    TO = 24,// Agregada
    TYPE		= 25,
    UNTIL = 26,// Agregada
    VAR			= 27,
    WHEN  = 28,// Agregada
    WHILE		= 29,

    // punctuation...
    DOT			= 30,
    DOTDOT = 31, //Agregada
    OR = 32, //Agregada
    COLON		= 33,
    SEMICOLON           = 34,
    COMMA		= 35,
    BECOMES		= 36,
    IS			= 37,
    

    // brackets...
    LPAREN		= 38,
    RPAREN		= 39,
    LBRACKET	= 40,
    RBRACKET	= 41,
    LCURLY		= 42,
    RCURLY		= 43,

    // special tokens...
    EOT			= 44,
    ERROR		= 45;

  /* CAMBIO
   @author Marco
   @descrp: Se agregaron nuevas palabras reservadas y simbolos lexicos (for, from, local, range, recursive, repeat, select, skip, 
    to, until, when), y se elimino begin
  */
  private static String[] tokenTable = new String[] {
    "<int>", //0
    "<char>", //1
    "<identifier>", //2 
    "<operator>", //3
    "array",//4
    //Eliminada
    //"begin",
    "const", //5
    "do", //6
    "else", //7
    "end", //8
    // Agregadas*****
    "for", //9
    "from", //10
    //**************
    "func", //11
    "if", //12
    "in", //13
    "let", //14
    // Agregadas*****
    "local",//15
    //**************
    "of",//16
    "proc",//17
    // Agregadas*****
    "range",//18
    //**************
    "record",//19
    // Agregadas*****
    "recursive",//20
    "repeat",//21
    "skip",//22
    "then",//23
    "to",//24
    "type",//25
    "until",//26
    //**************
    "var",//27
    // Agregadas*****
    "when",//28
    //**************
    "while",//29
    ".",//30
    "..",//31 AGREGADA
     "|",//32 AGREGADA
    ":",//33
    ";",//34
    ",",//35
    ":=",//36
    "~",//37
    "(",//38
    ")",//39
    "[",//40
    "]",//41
    "{",//42
    "}",//43
    "",//44
    "<error>"//45
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;

}

