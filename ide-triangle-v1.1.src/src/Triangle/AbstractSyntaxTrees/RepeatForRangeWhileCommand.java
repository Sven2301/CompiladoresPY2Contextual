/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 * Nueva clase
 * @author Steven
 */
public class RepeatForRangeWhileCommand extends Command{
    
  public RepeatForRangeWhileCommand (Identifier iAST, Expression e1AST, Expression e2AST, Expression e3AST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    
    I = iAST;
    E1 = e1AST;
    E2 = e2AST;
    E3 = e3AST;
    C = cAST;
    
  }
  
  public Object visit(Visitor v, Object o) {
    return v.visitRepeatForRangeWhileCommand(this, o);
  }
  
  
  public Identifier I;  
  public Expression E1;
  public Expression E2;
  public Expression E3;
  public Command C;
  
  @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitRepeatForRangeWhileCommand(this, o);
    }
}
