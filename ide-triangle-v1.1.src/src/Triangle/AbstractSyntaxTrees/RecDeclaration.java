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
public class RecDeclaration extends Declaration{
    
  public RecDeclaration (ProcFuncs pfsAST, SourcePosition thePosition) {
    super (thePosition);
    PFs = pfsAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitRecDeclaration(this, o);
  }

  public ProcFuncs PFs;

    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitRecDeclaration(this, o);
    }
}
