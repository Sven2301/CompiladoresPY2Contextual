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
 *  AST de un Squential Or Command
 */
public class SequentialOrCommand extends OrCommand{
    
    public OrCommand SE1;
    public OrCommand SE2;
    
    public SequentialOrCommand(OrCommand c1AST, 
            OrCommand c2AST, SourcePosition thePosition) {
        super(thePosition);
        SE1 = c1AST;
        SE2 = c2AST;
        

    }
        public Object visit(Visitor v, Object o) {
        return v.visitSequentialOrCommand(this, o);
    }
        
    @Override
    public Object visitXML(Visitor v, Object o) {
         return v.visitSequentialOrCommand(this, o);
    }
}
