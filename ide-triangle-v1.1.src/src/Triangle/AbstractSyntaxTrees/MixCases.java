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
 * Representa un AST de casos compuesto por un AST de caso y un AST de elsecase
 * 
 */
public class MixCases extends Cases {
    
    public Case caseAST;
    public ElseCase elseCaseAST;
    
    public MixCases(Case caseAST, ElseCase elseCaseAST, SourcePosition thePosition) {
        super(thePosition);
        
        this.elseCaseAST = elseCaseAST;
        this.caseAST = caseAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitMixCases(this, o);
    }

    @Override
    public Object visitXML(Visitor v, Object o) {
        return v.visitMixCases(this, o);
    }
    
    
    
}