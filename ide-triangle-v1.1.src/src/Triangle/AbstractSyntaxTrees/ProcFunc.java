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
 * Representa un AST de la regla "Proc-Func"
 * 
 */
public abstract class ProcFunc extends ProcFuncs {

  public ProcFunc (SourcePosition thePosition) {
    super (thePosition);
  }
}
