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
 * Representa grupos de ASTs  "single- declaration"
 * 
 */
public abstract class SingleDeclaration extends Declaration {

    public SingleDeclaration(SourcePosition thePosition) {
        super(thePosition);
    }
}