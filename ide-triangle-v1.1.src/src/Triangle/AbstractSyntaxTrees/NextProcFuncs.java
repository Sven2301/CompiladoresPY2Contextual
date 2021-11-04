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
 */
public class NextProcFuncs extends ProcFuncs{
    
    public ProcFuncs procFunc1;
    public ProcFuncs procFunc2;

    public NextProcFuncs(SourcePosition thePosition, ProcFuncs procFunc1, ProcFuncs procFunc2) {
        super(thePosition);
        this.procFunc1 = procFunc1;
        this.procFunc2 = procFunc2;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitNextProcFuncs(this,o);
    }
    
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitNextProcFuncs(this, o);
    }
    
}
