/*
 * @(#)Parser.java                        2.1 2003/10/07
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

import Triangle.ErrorReporter;
import Triangle.AbstractSyntaxTrees.ActualParameter;
import Triangle.AbstractSyntaxTrees.ActualParameterSequence;
import Triangle.AbstractSyntaxTrees.ArrayAggregate;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
/*
 @author: Marco
 Imports de nuevos ASTS
*/
import Triangle.AbstractSyntaxTrees.Case;
import Triangle.AbstractSyntaxTrees.Cases;
import Triangle.AbstractSyntaxTrees.ElseCase;
import Triangle.AbstractSyntaxTrees.CaseLiteral;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.Command;
import Triangle.AbstractSyntaxTrees.CompoundIfCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;

import Triangle.AbstractSyntaxTrees.Expression;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Function;

import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MixCases;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.NextCase;
import Triangle.AbstractSyntaxTrees.NextCaseLiteral;
import Triangle.AbstractSyntaxTrees.NextProcFuncs;
import Triangle.AbstractSyntaxTrees.OneCaseLiteral;
import Triangle.AbstractSyntaxTrees.OneCases;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.OrCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.PrivDeclaration; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcFunc;
import Triangle.AbstractSyntaxTrees.ProcFuncs;
import Triangle.AbstractSyntaxTrees.Procedure;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RecordAggregate;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;  //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatForInCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeDoCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeUntilCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeWhileCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialOrCommand; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;

import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleOrCommand;  //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleOrCommand;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.Var2Declaration; //AGREGADO @Steven
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;

import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;

public class Parser {

  private Scanner lexicalAnalyser;
  private ErrorReporter errorReporter;
  private Token currentToken;
  private SourcePosition previousTokenPosition;

  public Parser(Scanner lexer, ErrorReporter reporter) {
    lexicalAnalyser = lexer;
    errorReporter = reporter;
    previousTokenPosition = new SourcePosition();
  }

// accept checks whether the current token matches tokenExpected.
// If so, fetches the next token.
// If not, reports a syntactic error.

  void accept (int tokenExpected) throws SyntaxError {
      
    if (currentToken.kind == tokenExpected) {
      previousTokenPosition = currentToken.position;
      currentToken = lexicalAnalyser.scan();
    } else {
      syntacticError("\"%\" expected here", Token.spell(tokenExpected));
    }
  }

  void acceptIt() {
    previousTokenPosition = currentToken.position;
    currentToken = lexicalAnalyser.scan();
  }

// start records the position of the start of a phrase.
// This is defined to be the position of the first
// character of the first token of the phrase.

  void start(SourcePosition position) {
    position.start = currentToken.position.start;
  }

// finish records the position of the end of a phrase.
// This is defined to be the position of the last
// character of the last token of the phrase.

  void finish(SourcePosition position) {
    position.finish = previousTokenPosition.finish;
  }

  void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
    SourcePosition pos = currentToken.position;
    errorReporter.reportError(messageTemplate, tokenQuoted, pos);
    throw(new SyntaxError());
  }

///////////////////////////////////////////////////////////////////////////////
//
// PROGRAMS
//
///////////////////////////////////////////////////////////////////////////////

  public Program parseProgram() {

    Program programAST = null;

    previousTokenPosition.start = 0;
    previousTokenPosition.finish = 0;
    currentToken = lexicalAnalyser.scan();

    try {
      Command cAST = parseCommand();
      programAST = new Program(cAST, previousTokenPosition);
      if (currentToken.kind != Token.EOT) {
        syntacticError("\"%\" not expected after end of program",
          currentToken.spelling);
      }
    }
    catch (SyntaxError s) { return null; }
    return programAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// LITERALS
//
///////////////////////////////////////////////////////////////////////////////

// parseIntegerLiteral parses an integer-literal, and constructs
// a leaf AST to represent it.

  IntegerLiteral parseIntegerLiteral() throws SyntaxError {
    IntegerLiteral IL = null;

    if (currentToken.kind == Token.INTLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      IL = new IntegerLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      IL = null;
      syntacticError("integer literal expected here", "");
    }
    return IL;
  }

// parseCharacterLiteral parses a character-literal, and constructs a leaf
// AST to represent it.

  CharacterLiteral parseCharacterLiteral() throws SyntaxError {
    CharacterLiteral CL = null;

    if (currentToken.kind == Token.CHARLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      CL = new CharacterLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      CL = null;
      syntacticError("character literal expected here", "");
    }
    return CL;
  }

// parseIdentifier parses an identifier, and constructs a leaf AST to
// represent it.

  Identifier parseIdentifier() throws SyntaxError {
    Identifier I = null;

    if (currentToken.kind == Token.IDENTIFIER) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      I = new Identifier(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      I = null;
      syntacticError("identifier expected here", "");
    }
    return I;
  }

// parseOperator parses an operator, and constructs a leaf AST to
// represent it.

  Operator parseOperator() throws SyntaxError {
    Operator O = null;

    if (currentToken.kind == Token.OPERATOR) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      O = new Operator(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      O = null;
      syntacticError("operator expected here", "");
    }
    return O;
  }

///////////////////////////////////////////////////////////////////////////////
//
// COMMANDS
//
///////////////////////////////////////////////////////////////////////////////

// parseCommand parses the command, and constructs an AST
// to represent its phrase structure.

  Command parseCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();

    start(commandPos);
    commandAST = parseSingleCommand();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Command c2AST = parseSingleCommand();
      finish(commandPos);
      commandAST = new SequentialCommand(commandAST, c2AST, commandPos);
    }
    return commandAST;
  }

      //CAMBIO REALIZADO
    /*
      @author: Steven
      @descrip: Se se comentaron las alternativas que debian ser eliminadas para
      dejar en evidencia que estaban ahii ero ya no se toman en cuenta.
    Eran las siguientes:
  
    | "begin" Command "end"
    | "let" Declaration "in" single-Command
    | "if" Expression "then" single-Command "else" single-Command
    | "while" Expression "do" single-Command
  
    Y agregar las siguientes:
    "skip"
    | V-name ":=" Expression
    | Identifier "(" Actual-Parameter-Sequence ")"
    | "let" Declaration "in" Command "end"
    | "if" Expression "then" Command ("|" Expression "then" Command)*
    "else" Command "end"
    | "select" Expression "from" Cases "end" ---> Este no se toma en cuenta ya que el grupo no tiene 4 integrantes 
    | "repeat" ("while" | "until") Expression "do" Command "end"
    | "repeat" "do" Command ("while" | "until") Expression "end" 
    | "repeat" "for" Identifier ":=" "range" Expression ".." Expression
    "do" Command "end"
    | "repeat" "for" Identifier ":=" "range" Expression ".." Expression ("while"| "until") 
    Expression "do" Command "end"
    | "repeat" "for" Identifier "in" Expression "do" Command "end"
    */
  Command parseSingleCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();
    start(commandPos);
    
    
    switch (currentToken.kind) {
    
    /*    
    case Token.SKIP:
    {
      acceptIt();
      finish(commandPos);
      commandAST = new EmptyCommand(commandPos);
    }
      break;
        */

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();  
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(commandPos);
          commandAST = new CallCommand(iAST, apsAST, commandPos);

        } else {
          Vname varAST = parseRestOfVname(iAST);
          accept(Token.BECOMES);
          Expression eAST = parseExpression();
          finish(commandPos);
          commandAST = new AssignCommand(varAST, eAST, commandPos);
        }
      }
      break;
    /*ELIMINADO @Steven
    case Token.BEGIN:
      acceptIt();
      commandAST = parseCommand();
      accept(Token.END);
      break;
    */
      
    /* ELIMINADO @Steven
    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
      }
      break;
    */

    /* ELIMINADO @Steven
    case Token.IF:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command c1AST = parseSingleCommand();
        accept(Token.ELSE);
        Command c2AST = parseSingleCommand();
        finish(commandPos);
        commandAST = new IfCommand(eAST, c1AST, c2AST, commandPos);
      }
      break;
    */

    /* ELIMINADO @Steven
    case Token.WHILE:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new WhileCommand(eAST, cAST, commandPos);
      }
      break;
    */
      
    // AGREGADO @Steven
    // "let" Declaration "in" Command "end"
    /* REVISAR POSIBLE RECURSIVIDAD
    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseCommand();
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
      }
      break;
    */
      
      
    /*
    AGREGADO @Steven
    "let" Declaration "in" Command "end"
    */
    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseCommand();
        accept(Token.END);
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
        
      }
      
      break;
    
    /*
    AGREGADO @Steven
    "if" Expression "then" Command ("|" Expression "then" Command)*
    "else" Command "end"
    */  
    case Token.IF:
    {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command cAST = parseCommand();
        OrCommand orAST = null;
        
        if (currentToken.kind == Token.OR) {
            acceptIt();
            Expression e2AST = parseExpression();
            accept(Token.THEN);
            Command c2AST = parseCommand();
            finish(commandPos);
            orAST = new SingleOrCommand(e2AST, c2AST, commandPos);
        }
        
        while(currentToken.kind == Token.OR) {
            
            acceptIt();
            Expression e2AST = parseExpression();
            accept(Token.THEN);
            Command c2AST = parseCommand();
            finish(commandPos);
            SingleOrCommand sOrAST = new SingleOrCommand(e2AST, c2AST, commandPos);
            orAST = new SequentialOrCommand(orAST, sOrAST, commandPos);
        }
        
        accept(Token.ELSE);
        Command c2AST = parseCommand();
        accept(Token.END);
        finish(commandPos);
        
        commandAST = orAST != null ? new CompoundIfCommand(eAST, cAST, orAST, c2AST, commandPos)
                : new IfCommand(eAST, cAST, c2AST, commandPos);
        break;
    }
      
    // AGREGADO @author Steven
    /*
    | "repeat" ("while" | "until") Expression "do" Command "end"
    | "repeat" "do" Command ("while" | "until") Expression "end" 
    | "repeat" "for" Identifier ":=" "range" Expression ".." Expression ("do" Command "end" | 
    "while" Expression "do" Command "end"| "until" Expression "do" Command "end")
    | "repeat" "for" Identifier "in" Expression "do" Command "end"
    */
    case Token.REPEAT:
      {
        acceptIt();
        switch (currentToken.kind)
        {
            // "repeat" ("while" | "until") Expression "do" Command "end"
            case Token.WHILE:
            {
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.DO);
                Command cAST = parseCommand();
                accept(Token.END);
                finish(commandPos);
                commandAST = new RepeatWhileCommand(eAST, cAST, commandPos);             
            }
            break;
            
            case Token.UNTIL:
            {
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.DO);
                Command cAST = parseCommand();
                accept(Token.END);
                finish(commandPos);
                commandAST = new RepeatUntilCommand(eAST, cAST, commandPos);               
            }
            break;
            
            //"repeat" "do" Command ("while" | "until") Expression "end" 
            case Token.DO:
            {
                acceptIt();
                Command cAST = parseCommand();
                switch(currentToken.kind)
                {
                    case Token.WHILE:
                    {
                        acceptIt();
                        Expression eAST = parseExpression();
                        accept(Token.END);
                        finish(commandPos);
                        commandAST = new RepeatDoWhileCommand(cAST, eAST, commandPos);
                    }
                    break;
                    
                    case Token.UNTIL:
                    {
                        acceptIt();
                        Expression eAST = parseExpression();
                        accept(Token.END);
                        finish(commandPos);
                        commandAST = new RepeatDoUntilCommand(cAST, eAST, commandPos);
                    }
                    break;
                    
                    default:
                        syntacticError("\"%\" cannot follow a repeat-do command, expected while or until", 
                                currentToken.spelling);
                    break;
                }
                
            }
            break;
            
            /*
            "repeat" "for" Identifier ":=" "range" Expression ".." Expression ("do" Command "end" | 
            "while" Expression "do" Command "end"| "until" Expression "do" Command "end")
            */
            case Token.FOR:
            {
                acceptIt();
                Identifier iAST = parseIdentifier();
                switch (currentToken.kind) {
                    case Token.BECOMES:
                        {
                            acceptIt();
                            accept(Token.RANGE);
                            Expression eAST = parseExpression();
                            accept(Token.DOT);
                            accept(Token.DOT);
                            Expression eAST2 = parseExpression();
                            switch(currentToken.kind)
                            {
                                case Token.DO:
                                {
                                    acceptIt();
                                    Command cAST = parseCommand();
                                    accept(Token.END);
                                    finish(commandPos);
                                    commandAST = new RepeatForRangeDoCommand(iAST, eAST, eAST2, cAST, commandPos);
                                }
                                break;

                                case Token.WHILE:
                                {
                                    acceptIt();
                                    Expression eAST3 = parseExpression();
                                    accept(Token.DO);
                                    Command cAST = parseCommand();
                                    accept(Token.END);
                                    finish(commandPos);
                                    commandAST = new RepeatForRangeWhileCommand(iAST, eAST, eAST2, eAST3, cAST, commandPos);
                                }
                                break;

                                case Token.UNTIL:
                                {
                                    acceptIt();
                                    Expression eAST3 = parseExpression();
                                    accept(Token.DO);
                                    Command cAST = parseCommand();
                                    accept(Token.END);
                                    finish(commandPos);
                                    commandAST = new RepeatForRangeUntilCommand(iAST, eAST, eAST2, eAST3, cAST, commandPos);
                                }
                                break;
                            }   
                            break;
                        }
                    // "repeat" "for" Identifier "in" Expression "do" Command "end"
                    case Token.IN:
                        {
                                            System.out.println("Hola");

                            acceptIt();
                            Expression eAST = parseExpression();
                            accept(Token.DO);
                            Command cAST = parseCommand();
                            accept(Token.END);
                            finish(commandPos);
                            commandAST = new RepeatForInCommand(iAST, eAST, cAST, commandPos);                    
                            
                        }
                        break;
                        
                    default:
                        syntacticError("\"%\" cannot follow a repeat for command, expected :=, or in",
                                currentToken.spelling);
                        break;
                }
                
            }
            break;
            
            default:
                syntacticError("\"%\" cannot follow a repeat command, expected while, until, do or for",
                    currentToken.spelling);
                break;
        }
      }
      
    case Token.SEMICOLON:
    case Token.END:
    case Token.ELSE:
    case Token.IN:
    case Token.SKIP:  //CAMBIO: Se agrego el skip como alternativa para comando vacio. @Steven
    //case Token.EOT:

      acceptIt();
      finish(commandPos);
      commandAST = new EmptyCommand(commandPos);
      break;

    default:
      syntacticError("\"%\" cannot start a command",
        currentToken.spelling);
      break;

    }

    return commandAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// EXPRESSIONS
//
///////////////////////////////////////////////////////////////////////////////

  Expression parseExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();

    start (expressionPos);

    switch (currentToken.kind) {

    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Expression eAST = parseExpression();
        finish(expressionPos);
        expressionAST = new LetExpression(dAST, eAST, expressionPos);
      }
      break;

    case Token.IF:
      {
        acceptIt();
        Expression e1AST = parseExpression();
        accept(Token.THEN);
        Expression e2AST = parseExpression();
        accept(Token.ELSE);
        Expression e3AST = parseExpression();
        finish(expressionPos);
        expressionAST = new IfExpression(e1AST, e2AST, e3AST, expressionPos);
      }
      break;

    default:
      expressionAST = parseSecondaryExpression();
      break;
    }
    return expressionAST;
  }

  Expression parseSecondaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    expressionAST = parsePrimaryExpression();
    while (currentToken.kind == Token.OPERATOR) {
      Operator opAST = parseOperator();
      Expression e2AST = parsePrimaryExpression();
      expressionAST = new BinaryExpression (expressionAST, opAST, e2AST,
        expressionPos);
    }
    return expressionAST;
  }

  Expression parsePrimaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    switch (currentToken.kind) {

    case Token.INTLITERAL:
      {
        IntegerLiteral ilAST = parseIntegerLiteral();
        finish(expressionPos);
        expressionAST = new IntegerExpression(ilAST, expressionPos);
      }
      break;

    case Token.CHARLITERAL:
      {
        CharacterLiteral clAST= parseCharacterLiteral();
        finish(expressionPos);
        expressionAST = new CharacterExpression(clAST, expressionPos);
      }
      break;

    case Token.LBRACKET:
      {
        acceptIt();
        ArrayAggregate aaAST = parseArrayAggregate();
        accept(Token.RBRACKET);
        finish(expressionPos);
        expressionAST = new ArrayExpression(aaAST, expressionPos);
      }
      break;

    case Token.LCURLY:
      {
        acceptIt();
        RecordAggregate raAST = parseRecordAggregate();
        accept(Token.RCURLY);
        finish(expressionPos);
        expressionAST = new RecordExpression(raAST, expressionPos);
      }
      break;

    case Token.IDENTIFIER:
      {
        Identifier iAST= parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(expressionPos);
          expressionAST = new CallExpression(iAST, apsAST, expressionPos);

        } else {
          Vname vAST = parseRestOfVname(iAST);
          finish(expressionPos);
          expressionAST = new VnameExpression(vAST, expressionPos);
        }
      }
      break;

    case Token.OPERATOR:
      {
        Operator opAST = parseOperator();
        Expression eAST = parsePrimaryExpression();
        finish(expressionPos);
        expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
      }
      break;

    case Token.LPAREN:
      acceptIt();
      expressionAST = parseExpression();
      accept(Token.RPAREN);
      break;

    default:
      syntacticError("\"%\" cannot start an expression",
        currentToken.spelling);
      break;

    }
    return expressionAST;
  }

  RecordAggregate parseRecordAggregate() throws SyntaxError {
    RecordAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Identifier iAST = parseIdentifier();
    accept(Token.IS);
    Expression eAST = parseExpression();

    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      RecordAggregate aAST = parseRecordAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleRecordAggregate(iAST, eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleRecordAggregate(iAST, eAST, aggregatePos);
    }
    return aggregateAST;
  }

  ArrayAggregate parseArrayAggregate() throws SyntaxError {
    ArrayAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Expression eAST = parseExpression();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ArrayAggregate aAST = parseArrayAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleArrayAggregate(eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleArrayAggregate(eAST, aggregatePos);
    }
    return aggregateAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// VALUE-OR-VARIABLE NAMES
//
///////////////////////////////////////////////////////////////////////////////

   Vname parseVname () throws SyntaxError {
    Vname vnameAST = null; // in case there's a syntactic error
    Identifier iAST = parseIdentifier();
    vnameAST = parseRestOfVname(iAST);
    return vnameAST;
  }

  Vname parseRestOfVname(Identifier identifierAST) throws SyntaxError {
    SourcePosition vnamePos = new SourcePosition();
    vnamePos = identifierAST.position;
    Vname vAST = new SimpleVname(identifierAST, vnamePos);

    while (currentToken.kind == Token.DOT ||
           currentToken.kind == Token.LBRACKET) {

      if (currentToken.kind == Token.DOT) {
        acceptIt();
        Identifier iAST = parseIdentifier();
        vAST = new DotVname(vAST, iAST, vnamePos);
      } else {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.RBRACKET);
        finish(vnamePos);
        vAST = new SubscriptVname(vAST, eAST, vnamePos);
      }
    }
    return vAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// DECLARATIONS
//
///////////////////////////////////////////////////////////////////////////////

  
  // MODIFICADO @author Steven
  /*
    Modificar Declaration para que se lea
    Declaration
    ::= compound-Declaration
    | Declaration ";" compound-Declaration
  */
  Declaration parseDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    declarationAST = parseCompoundDeclaration();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Declaration d2AST = parseCompoundDeclaration();
      finish(declarationPos);
      declarationAST = new SequentialDeclaration(declarationAST, d2AST,
        declarationPos);
    }
    return declarationAST;
    
    /* VERSION ANTES DE LA MODIFICACION
    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    declarationAST = parseCompoundDeclaration();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Declaration d2AST = parseCompoundDeclaration();
      finish(declarationPos);
      declarationAST = new SequentialDeclaration(declarationAST, d2AST,
        declarationPos);
    }
    return declarationAST;
    */
  }

  Declaration parseSingleDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);

    switch (currentToken.kind) {

    case Token.CONST:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new ConstDeclaration(iAST, eAST, declarationPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        switch (currentToken.kind)
        {
        
            case Token.COLON:
            {
                acceptIt();
                TypeDenoter tAST = parseTypeDenoter();
                finish(declarationPos);
                declarationAST = new VarDeclaration(iAST, tAST, declarationPos);            
            }
            break;
            
            
            case Token.BECOMES:
            {
            
                acceptIt();
                Expression eAST = parseExpression();
                finish(declarationPos);
                declarationAST = new Var2Declaration(iAST, eAST, declarationPos);        
            }
            break;
            
        }

      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.IS);
        Command cAST = parseSingleCommand();
        finish(declarationPos);
        declarationAST = new ProcDeclaration(iAST, fpsAST, cAST, declarationPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new FuncDeclaration(iAST, fpsAST, tAST, eAST,
          declarationPos);
      }
      break;

    case Token.TYPE:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        TypeDenoter tAST = parseTypeDenoter();
        finish(declarationPos);
        declarationAST = new TypeDeclaration(iAST, tAST, declarationPos);
      }
      break;
      
    default:
      syntacticError("\"%\" cannot start a declaration",
        currentToken.spelling);
      break;

    }
    return declarationAST;
  }
 
  // AGREGADO
  /* @author: Steven
  */
  
  Declaration parseCompoundDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    
    switch (currentToken.kind) {
        
        case Token.RECURSIVE:
            {
                acceptIt();
                ProcFuncs pfAST =  parseProcFuncs();
                accept(Token.END);
                finish(declarationPos);
                declarationAST = new RecDeclaration(pfAST, declarationPos);
            }
            break;
            
        case Token.LOCAL:
            {
                acceptIt();
                Declaration d1AST =  parseDeclaration();
                accept(Token.IN);
                Declaration d2AST =  parseDeclaration();
                accept(Token.END);
                finish(declarationPos);
                declarationAST = new PrivDeclaration(d1AST, d2AST, declarationPos);
                
            }
            break;
            
        default:
            declarationAST = parseSingleDeclaration();
    }
    
      return declarationAST;
      
  }
  
///////////////////////////////////////////////////////////////////////////////
//
// PARAMETERS
//
///////////////////////////////////////////////////////////////////////////////

  FormalParameterSequence parseFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST;

    SourcePosition formalsPos = new SourcePosition();

    start(formalsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(formalsPos);
      formalsAST = new EmptyFormalParameterSequence(formalsPos);

    } else {
      formalsAST = parseProperFormalParameterSequence();
    }
    return formalsAST;
  }

  FormalParameterSequence parseProperFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST = null; // in case there's a syntactic error;

    SourcePosition formalsPos = new SourcePosition();
    start(formalsPos);
    FormalParameter fpAST = parseFormalParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FormalParameterSequence fpsAST = parseProperFormalParameterSequence();
      finish(formalsPos);
      formalsAST = new MultipleFormalParameterSequence(fpAST, fpsAST,
        formalsPos);

    } else {
      finish(formalsPos);
      formalsAST = new SingleFormalParameterSequence(fpAST, formalsPos);
    }
    return formalsAST;
  }

  FormalParameter parseFormalParameter() throws SyntaxError {
    FormalParameter formalAST = null; // in case there's a syntactic error;

    SourcePosition formalPos = new SourcePosition();
    start(formalPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new ConstFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new VarFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        finish(formalPos);
        formalAST = new ProcFormalParameter(iAST, fpsAST, formalPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new FuncFormalParameter(iAST, fpsAST, tAST, formalPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a formal parameter",
        currentToken.spelling);
      break;

    }
    return formalAST;
  }


  ActualParameterSequence parseActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST;

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(actualsPos);
      actualsAST = new EmptyActualParameterSequence(actualsPos);

    } else {
      actualsAST = parseProperActualParameterSequence();
    }
    return actualsAST;
  }

  ActualParameterSequence parseProperActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST = null; // in case there's a syntactic error

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    ActualParameter apAST = parseActualParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ActualParameterSequence apsAST = parseProperActualParameterSequence();
      finish(actualsPos);
      actualsAST = new MultipleActualParameterSequence(apAST, apsAST,
        actualsPos);
    } else {
      finish(actualsPos);
      actualsAST = new SingleActualParameterSequence(apAST, actualsPos);
    }
    return actualsAST;
  }

  ActualParameter parseActualParameter() throws SyntaxError {
    ActualParameter actualAST = null; // in case there's a syntactic error

    SourcePosition actualPos = new SourcePosition();

    start(actualPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
    case Token.INTLITERAL:
    case Token.CHARLITERAL:
    case Token.OPERATOR:
    case Token.LET:
    case Token.IF:
    case Token.LPAREN:
    case Token.LBRACKET:
    case Token.LCURLY:
      {
        Expression eAST = parseExpression();
        finish(actualPos);
        actualAST = new ConstActualParameter(eAST, actualPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Vname vAST = parseVname();
        finish(actualPos);
        actualAST = new VarActualParameter(vAST, actualPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new ProcActualParameter(iAST, actualPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new FuncActualParameter(iAST, actualPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start an actual parameter",
        currentToken.spelling);
      break;

    }
    return actualAST;
  }

  //CAMBIO SE AGREGARON LOS PARSERS DE LOS PROC-FUNCS
  /* @author Marco
  */
  
///////////////////////////////////////////////////////////////////////////////
//
// PROCFUNCS
//
///////////////////////////////////////////////////////////////////////////////
  
  ProcFunc parseProcFunc() throws SyntaxError {
       ProcFunc procfuncAST = null;
       SourcePosition procFuncPos = new SourcePosition();
       start(procFuncPos);
       
       switch(currentToken.kind){
           case Token.PROC:
            {
                acceptIt();
                Identifier idAST = parseIdentifier();
                accept(Token.LPAREN);
                FormalParameterSequence formalParameterSqAST = parseFormalParameterSequence();
                accept(Token.RPAREN);
                accept(Token.IS);
                Command commandAST = parseCommand();
                accept(Token.END);
                finish(procFuncPos);
                procfuncAST = new Procedure(idAST,formalParameterSqAST,commandAST, procFuncPos);
                
            }
            break;
           case Token.FUNC:
           {
               acceptIt();
               Identifier idAST = parseIdentifier();
               accept(Token.LPAREN);
               FormalParameterSequence formalParameterSqAST = parseFormalParameterSequence();
               accept(Token.RPAREN);
               accept(Token.COLON);
               TypeDenoter typeDenoterAST = parseTypeDenoter();
               accept(Token.IS);
               Expression expressionAST = parseExpression();
               finish(procFuncPos);
               procfuncAST = new Function(idAST, formalParameterSqAST, typeDenoterAST, expressionAST, procFuncPos);
           }
           break;
           
           default:
               syntacticError("\"%\" cannot start a procedure or a function",
               currentToken.spelling);
               break;
       }
       
       return procfuncAST;
       
       
  }
  
  
  ProcFuncs parseProcFuncs() throws SyntaxError {
       ProcFuncs procfuncAST = null;
       SourcePosition procFuncPos = new SourcePosition();
       start(procFuncPos);
       
       ProcFunc procFuncASTAux = parseProcFunc();
       
       accept(Token.OR);
       
       ProcFunc procFuncASTAux2 = parseProcFunc();
       finish(procFuncPos);
       
       while (currentToken.kind == Token.OR) {
            acceptIt();
            procFuncASTAux2 = parseProcFunc();
            finish(procFuncPos);
            procfuncAST = new NextProcFuncs(procFuncPos, procFuncASTAux,procFuncASTAux2);
      
    }

       return procfuncAST;
  }
  
  
///////////////////////////////////////////////////////////////////////////////
//
// TYPE-DENOTERS
//
///////////////////////////////////////////////////////////////////////////////

  TypeDenoter parseTypeDenoter() throws SyntaxError {
    TypeDenoter typeAST = null; // in case there's a syntactic error
    SourcePosition typePos = new SourcePosition();

    start(typePos);
    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        finish(typePos);
        typeAST = new SimpleTypeDenoter(iAST, typePos);
      }
      break;

    case Token.ARRAY:
      {
        acceptIt();
        IntegerLiteral ilAST = parseIntegerLiteral();
        accept(Token.OF);
        TypeDenoter tAST = parseTypeDenoter();
        finish(typePos);
        typeAST = new ArrayTypeDenoter(ilAST, tAST, typePos);
      }
      break;

    case Token.RECORD:
      {
        acceptIt();
        FieldTypeDenoter fAST = parseFieldTypeDenoter();
        accept(Token.END);
        finish(typePos);
        typeAST = new RecordTypeDenoter(fAST, typePos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a type denoter",
        currentToken.spelling);
      break;

    }
    return typeAST;
  }

  FieldTypeDenoter parseFieldTypeDenoter() throws SyntaxError {
    FieldTypeDenoter fieldAST = null; // in case there's a syntactic error

    SourcePosition fieldPos = new SourcePosition();

    start(fieldPos);
    Identifier iAST = parseIdentifier();
    accept(Token.COLON);
    TypeDenoter tAST = parseTypeDenoter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FieldTypeDenoter fAST = parseFieldTypeDenoter();
      finish(fieldPos);
      fieldAST = new MultipleFieldTypeDenoter(iAST, tAST, fAST, fieldPos);
    } else {
      finish(fieldPos);
      fieldAST = new SingleFieldTypeDenoter(iAST, tAST, fieldPos);
    }
    return fieldAST;
  }
  

  
    ///////////////////////////////////////////////////////////////////////////////
  //
  // CASES
  //
  ///////////////////////////////////////////////////////////////////////////////


    /* CAMBIO
   * @author Marco
   * @descrip: Este método se encarga de parsear el "Cases"
   */
  Cases parseCases() throws SyntaxError {
      ElseCase elseCaseAST = null;
      Cases casesAST = null;
      SourcePosition position = new SourcePosition();
      start(position);
      
     Case caseAST = parseCase();
     
     while(currentToken.kind == Token.WHEN) {
            Case caseAST2 = parseCase();
            finish(position);
            caseAST2 = new NextCase(position,caseAST,caseAST2);
     }
     
     if (currentToken.kind == Token.ELSE) {
            elseCaseAST = parseElseCase();
        }
     
    finish(position);
    
    if (elseCaseAST == null){
        casesAST = new OneCases(caseAST,position);
    } else{
        casesAST = new MixCases(caseAST,elseCaseAST,position);
    }
     return casesAST;
      
  }
 
  /* CAMBIO
   * @author Marco
   * @descrip: Este método se encarga de parsear el "Case"
   */
  Case parseCase() throws SyntaxError {
      
      Case caseAST = null;
      SourcePosition position = new SourcePosition();
      start(position);
      
     accept(Token.WHEN);
     
     
     if (currentToken.kind == Token.RANGE){
         
         acceptIt();
         CaseLiteral caseLitAST = parseCaseLiteral();
         accept(Token.DOTDOT);
         CaseLiteral caseLitAST2 = parseCaseLiteral();
         accept(Token.THEN);
         Command caseCommand = parseCommand();
         finish(position);
         caseAST = new NextCaseLiteral(position,caseCommand,caseLitAST,caseLitAST2);
         
         return caseAST;
         
     } else {
         acceptIt();
         CaseLiteral caseLitAST = parseCaseLiteral();
         accept(Token.THEN);
         Command caseCommand = parseCommand();
         finish(position);
         caseAST = new OneCaseLiteral(caseLitAST,caseCommand,position);
         return caseAST;
     }
     
  }
  /* CAMBIO
   * @author Marco
   * @descrip: Este método se encarga de parsear el "ElseCase"
   */
  ElseCase parseElseCase() throws SyntaxError {
      ElseCase elseCaseAST = null;
      SourcePosition fieldPos = new SourcePosition();
      start(fieldPos);
        
      accept(Token.ELSE);
      Command elseCaseCommand = parseCommand();
      finish(fieldPos);
      elseCaseAST = new ElseCase(fieldPos, elseCaseCommand);
         
      finish(fieldPos);
         
      return elseCaseAST;
         
    }
  
  /* CAMBIO
   * @author Marco
   * @descrip: Este método se encarga de parsear el "CaseLiteral"
   */
    CaseLiteral parseCaseLiteral() throws SyntaxError {
        CaseLiteral caseLitAST = null;
        SourcePosition fieldPos = new SourcePosition();
        start(fieldPos);

        switch(currentToken.kind) {

            case Token.INTLITERAL:
            {
                IntegerLiteral intLitAST = parseIntegerLiteral();
                finish(fieldPos);
                caseLitAST = new CaseLiteral(fieldPos,intLitAST);
            }
            break;

            case Token.CHARLITERAL:
            {
                CharacterLiteral charLitAST = parseCharacterLiteral();
                finish(fieldPos);
                caseLitAST = new CaseLiteral (fieldPos, charLitAST);
            }
            break;
            default:
                syntacticError("\"%\" cannot start a case literal", currentToken.spelling);
                break;

        }

        return caseLitAST;

    }
    
}

