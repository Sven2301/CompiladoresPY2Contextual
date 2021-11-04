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
public class RepeatForInCommand extends Command{
    
  public RepeatForInCommand (Identifier iAST, Expression eAST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    C = cAST;
    E = eAST;
    I = iAST;
  }
  
  public Object visit(Visitor v, Object o) {
    return v.visitRepeatForInCommand(this, o);
  }
  
  public Command C;
  public Expression E;
  public Identifier I;
  
  
  @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitRepeatForInCommand(this, o);
    }
}
