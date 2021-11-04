/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 * @newclass
 * @author Marco
 * 
 * AST de regla "ElseCase"
 * 
 */
public class ElseCase extends AST {
    
    public Command elseCaseAST;
    
    public ElseCase(SourcePosition thePosition, Command elseCaseAST) {
        super(thePosition);
        
        this.elseCaseAST = elseCaseAST;
    }

   
    public Object visit(Visitor v, Object o) {
        return v.visitElseCase(this, o);
    }
/* Cambio
  * @Autor: Marco
  * XML VISITOR para generador de documento XML
   */
    @Override
    public Object visitXML(Visitor v, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
