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
 * Representa un AST de un caso compuesto de dos casos
 * 
 */
public class NextCase extends Case{
    
    public Case Case_1;
    public Case Case_2;
    

    public NextCase(SourcePosition thePosition, Case caseAST, Case caseAST2) {
        super(thePosition);
        this.Case_1 = caseAST;
        this.Case_2 = caseAST2;
        
    }

   
    public Object visit(Visitor v, Object o) {
        return v.visitNextCase(this, o);
    }
    
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitNextCase(this, o);
    }
    
}
