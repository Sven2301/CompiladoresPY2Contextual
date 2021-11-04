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
 * Representa un AST del caso 1 de cases normal
 * 
 */
public class OneCases extends Cases {
    
    
    public Case caseAST;
    
    
    public OneCases(Case caseAST, SourcePosition thePosition) {
        super(thePosition);
        
        this.caseAST = caseAST;
        
    }


    public Object visit(Visitor v, Object o) {
        return v.visitOneCases(this, o);
    }
    
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitOneCases(this, o);
    }
    
    
    
}