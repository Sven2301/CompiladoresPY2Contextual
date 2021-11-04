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
 * Representa un AST de Func
 * 
 */
public class Function extends ProcFunc{
    
    public Identifier idAST;
    public FormalParameterSequence fpsAST;
    public TypeDenoter typeDenAST;
    public Expression expAST;

    public Function(Identifier idAST, FormalParameterSequence fpsAST, TypeDenoter typeDenAST, Expression expAST, SourcePosition thePosition) {
        
        super(thePosition);
        this.idAST = idAST;
        this.fpsAST = fpsAST;
        this.typeDenAST = typeDenAST;
        this.expAST = expAST;
        
        
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunction(this, o);
    }

    @Override
    public Object visitXML(Visitor v, Object o) {
       return v.visitFunction(this, o);
    }
    
}
