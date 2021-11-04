/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 * @newClass
 * @author Marco
 * 
 * Crea un AST para un solo AST de caso literal
 * 
 */
public class OneCaseLiteral extends Case {
    
    
    public CaseLiteral caseLiteralAST;
    public Command commandAST;
    
    public OneCaseLiteral(CaseLiteral caseLiteralAST, Command commandAST, SourcePosition thePosition) {
        super(thePosition);
        
        this.caseLiteralAST = caseLiteralAST;
        this.commandAST = commandAST;
    }

 
    public Object visit(Visitor v, Object o) {
         return v.visitOneCaseLiteral(this, o);
    }
    
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitOneCaseLiteral(this, o);
    }
    
    
}
