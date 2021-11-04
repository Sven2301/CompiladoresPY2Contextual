/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 * Clase Agregada
 * @author Steven
 * Para representar un AST de un OR ---> |
 */
public abstract class OrCommand extends Command{
    
    public OrCommand(SourcePosition thePosition){
    
        super(thePosition);
    }
}
