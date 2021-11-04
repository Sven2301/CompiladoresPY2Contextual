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
 * AST de un caso compuesto de dos AST case literals y un AST command
 * 
 */
public class NextCaseLiteral extends Case{
    
    public CaseLiteral Case_1;
    public CaseLiteral Case_2;
    public Command commandAST;

    public NextCaseLiteral(SourcePosition thePosition, Command commandAST, CaseLiteral caseLitAST, CaseLiteral caseLitAST2) {
        super(thePosition);
        this.Case_1 = caseLitAST;
        this.Case_2 = caseLitAST2;
        this.commandAST = commandAST;
    }


    public Object visit(Visitor v, Object o) {
        return v.visitNextCaseLiteral(this, o);
    }
    
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitNextCaseLiteral(this, o);
    }
    
    
}
