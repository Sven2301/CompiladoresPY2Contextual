/*
 * @(#)CallExpression.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CallExpression extends Expression {

  public CallExpression (Identifier iAST, ActualParameterSequence apsAST,
               SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    APS = apsAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCallExpression(this, o);
  }

  public Identifier I;
  public ActualParameterSequence APS;
/* Cambio
  * @Autor: Marco
  * XML VISITOR para generador de documento XML
   */
    @Override
    public Object visitXML(Visitor v, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
