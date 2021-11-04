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
 * AST de regla "Case-Literal"
 */
public class CaseLiteral extends AST {
    
    public Terminal terminalAST;
    
    public CaseLiteral(SourcePosition thePosition, Terminal tAST) {
        super(thePosition);
        this.terminalAST = tAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCaseLiteral(this, o);
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
