/*
 * IDE-Triangle v1.0
 * TableDetails.java
 */

package Core.Visitors;

import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
/*
 * @author Marco
 * Importación de nuevos AST
*/
import Triangle.AbstractSyntaxTrees.Case; //Agregado
import Triangle.AbstractSyntaxTrees.Cases; //Agregado
import Triangle.AbstractSyntaxTrees.ElseCase; //Agregado
import Triangle.AbstractSyntaxTrees.CaseLiteral; //Agregado

// FIN
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.CompoundIfCommand; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;

import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Function; //Agregado

import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MixCases; //Agregado
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.NextCase; //Agregado
import Triangle.AbstractSyntaxTrees.NextCaseLiteral;
import Triangle.AbstractSyntaxTrees.NextProcFuncs; //Agregado
import Triangle.AbstractSyntaxTrees.OneCaseLiteral; //Agregado
import Triangle.AbstractSyntaxTrees.OneCases; //Agregado
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.PrivDeclaration; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcFuncs; //Agregado
import Triangle.AbstractSyntaxTrees.Procedure; //Agregado
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatForInCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeDoCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeUntilCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatForRangeWhileCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand; // AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialOrCommand; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;

import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleOrCommand; //AGREGADO @author Steven
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.Var2Declaration; //@author Steven
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
//import Triangle.AbstractSyntaxTrees.WhileCommand; ELIMINADO @author Steven
import Triangle.CodeGenerator.Field;
import Triangle.CodeGenerator.KnownAddress;
import Triangle.CodeGenerator.KnownRoutine;
import Triangle.CodeGenerator.KnownValue;
import Triangle.CodeGenerator.TypeRepresentation;
import Triangle.CodeGenerator.UnknownAddress;
import Triangle.CodeGenerator.UnknownRoutine;
import Triangle.CodeGenerator.UnknownValue;
import javax.swing.table.DefaultTableModel;

/**
 * Implements the Triangle Visitor interface, which is used to
 * visit an entire AST. 
 *
 * Generates a DefaultTableModel, used to draw a Jable.
 *
 * @author Luis Leopoldo Pérez <luiperpe@ns.isi.ulatina.ac.cr>
 */
public class TableVisitor implements Visitor {
    
    /** Creates a new instance of TableDetails */
    public TableVisitor() {        
    }

  // <editor-fold defaultstate="collapsed" desc=" Commands ">
  // Commands
  public Object visitAssignCommand(AssignCommand ast, Object o) { 
      ast.V.visit(this, null);
      ast.E.visit(this, null);
      
      return(null);
  }
  
  public Object visitCallCommand(CallCommand ast, Object o) { 
      ast.I.visit(this, null);
      ast.APS.visit(this, null);
      
      return(null);
  }
  
  public Object visitEmptyCommand(EmptyCommand ast, Object o) { 
      return(null);
  }
  
  public Object visitIfCommand(IfCommand ast, Object o) { 
      ast.E.visit(this, null);
      ast.C1.visit(this, null);
      ast.C2.visit(this, null);
      
      return(null);
  }
  
  public Object visitLetCommand(LetCommand ast, Object o) {     
      ast.D.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }
  
  public Object visitSequentialCommand(SequentialCommand ast, Object o) { 
      ast.C1.visit(this, null);
      ast.C2.visit(this, null);
      
      return(null);
  }
  
  //AGREGADO @author Steven
  public Object visitSequentialOrCommand(SequentialOrCommand ast, Object o) {
        ast.SE1.visit(this, null);
        ast.SE2.visit(this, null);
        
      return (null);
   }
  
   // AGREGADO @author Steven
   public Object visitCompoundIfCommand(CompoundIfCommand ast, Object o) {
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.EIC.visit(this, null);
        ast.C2.visit(this, null);
        
        return (null);
   }
  
  /*
  AGREGADO @author Steven
  */
  public Object visitSingleOrCommand(SingleOrCommand ast, Object o) { 
      ast.E.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }
  /*
  AGREGADO @author Steven
  */    
   public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
        ast.C.visit(this, null);
        ast.E.visit(this, null);

        return (null);
  }
  /*
  AGREGADO @author Steven
  */     
   public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        return (null);
  }

  /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatForInCommand(RepeatForInCommand ast, Object o) {
      ast.I.visit(this, null);
      ast.E.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }
  
 /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatForRangeUntilCommand(RepeatForRangeUntilCommand ast, Object o) {
      ast.I.visit(this, null);
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.E3.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }  

 /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatForRangeWhileCommand(RepeatForRangeWhileCommand ast, Object o) {
      ast.I.visit(this, null);
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.E3.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }  

  /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatForRangeDoCommand(RepeatForRangeDoCommand ast, Object o) {
      ast.I.visit(this, null);
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }  
  /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) { 
      ast.E.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }
  
  /*
  AGREGADO @author Steven
  */  
  public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) { 
      ast.E.visit(this, null);
      ast.C.visit(this, null);
      
      return(null);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Expressions ">
  // Expressions
  public Object visitArrayExpression(ArrayExpression ast, Object o) { 
      ast.AA.visit(this, null);
      
      return(null);
  }
  
  public Object visitBinaryExpression(BinaryExpression ast, Object o) { 
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.O.visit(this, null);
      
      return(null);
  }
  
  public Object visitCallExpression(CallExpression ast, Object o) { 
      ast.I.visit(this, null);
      ast.APS.visit(this, null);
      
      return(null);
  }
  
  public Object visitCharacterExpression(CharacterExpression ast, Object o) { 
      ast.CL.visit(this, null);
      
      return(null);
  }
  
  public Object visitEmptyExpression(EmptyExpression ast, Object o) {       
      return(null);
  }
  
  public Object visitIfExpression(IfExpression ast, Object o) {       
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.E3.visit(this, null);
      
      return(null);
  }
  
  public Object visitIntegerExpression(IntegerExpression ast, Object o) { 
      return(null);
  }
  
  public Object visitLetExpression(LetExpression ast, Object o) { 
      ast.D.visit(this, null);
      ast.E.visit(this, null);

      return(null);
  }
  
  public Object visitRecordExpression(RecordExpression ast, Object o) {   
      ast.RA.visit(this, null);
      
      return(null);
  }
  
  public Object visitUnaryExpression(UnaryExpression ast, Object o) {    
      ast.E.visit(this, null);
      ast.O.visit(this, null);
      
      return(null);
  }
  
  public Object visitVnameExpression(VnameExpression ast, Object o) { 
      ast.V.visit(this, null);
      
      return(null);
  }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc=" Declarations ">
  // Declarations
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {        
      return(null);
  }
  
  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {   
      String name = ast.I.spelling;
      String type = "N/A";
      try {
        int size = (ast.entity!=null?ast.entity.size:0);
        int level = -1;
        int displacement = -1;
        int value = -1;
      
        if (ast.entity instanceof KnownValue) {
              type = "KnownValue";
              value = ((KnownValue)ast.entity).value;
          }
          else if (ast.entity instanceof UnknownValue) {
              type = "UnknownValue";
              level = ((UnknownValue)ast.entity).address.level;
              displacement = ((UnknownValue)ast.entity).address.displacement;
          }
          addIdentifier(name, type, size, level, displacement, value);
      } catch (NullPointerException e) { }
      
      ast.E.visit(this, null);
      ast.I.visit(this, null);

      return(null);
  }
  
  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {    
      try {
      addIdentifier(ast.I.spelling, 
              "KnownRoutine", 
              (ast.entity!=null?ast.entity.size:0), 
              ((KnownRoutine)ast.entity).address.level, 
              ((KnownRoutine)ast.entity).address.displacement, 
              -1);      
      } catch (NullPointerException e) { }
      ast.T.visit(this, null);            
      ast.FPS.visit(this, null);
      ast.E.visit(this, null);
            
      return(null);
  }
  
  public Object visitProcDeclaration(ProcDeclaration ast, Object o) { 
      try {
      addIdentifier(ast.I.spelling, "KnownRoutine", 
              (ast.entity!=null?ast.entity.size:0), 
              ((KnownRoutine)ast.entity).address.level, 
              ((KnownRoutine)ast.entity).address.displacement, 
              -1);
      } catch (NullPointerException e) { }
      
      ast.FPS.visit(this, null);
      ast.C.visit(this, null);
            
      return(null);
  }
  
  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {   
      ast.D1.visit(this, null);
      ast.D2.visit(this, null);
      
      return(null);
  }
  //AGREGADO @author Steven
    public Object visitRecDeclaration(RecDeclaration ast, Object o) {
        ast.PFs.visit(this, null);
        return (null);
    }
   //AGREGADO @author Steven 
    public Object visitPrivDeclaration(PrivDeclaration ast, Object o) {
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        return (null);
    }
  
  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) { 
      ast.T.visit(this, null);
      
      return(null);
  }
  
  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {        
      return(null);
  }
  
  
   public Object visitVar2Declaration(Var2Declaration ast, Object o) {
      ast.I.visit(this, null);
      ast.E.visit(this, null);
      return(null);
  }
  
  public Object visitVarDeclaration(VarDeclaration ast, Object o) {      
      try {
      addIdentifier(ast.I.spelling, 
              "KnownAddress", 
              (ast.entity!=null?ast.entity.size:0), 
              ((KnownAddress)ast.entity).address.level, 
              ((KnownAddress)ast.entity).address.displacement, 
              -1);
      } catch (NullPointerException e) { }
      
      ast.T.visit(this, null);
      return(null);
  }
  
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Aggregates ">
  // Array Aggregates
  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) { 
      ast.AA.visit(this, null);
      ast.E.visit(this, null);
      
      return(null);
  }
  
  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) { 
      ast.E.visit(this, null);
      
      return(null);
  }

  // Record Aggregates
  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) { 
      ast.E.visit(this, null);
      ast.I.visit(this, null);
      ast.RA.visit(this, null);
      
      return(null);
  }
  
  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) { 
      ast.E.visit(this, null);
      ast.I.visit(this, null);
      
      return(null);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Parameters ">
  // Formal Parameters
  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {       
      try {
      addIdentifier(ast.I.spelling, 
              "UnknownValue", 
              (ast.entity!=null?ast.entity.size:0), 
              ((UnknownValue)ast.entity).address.level, 
              ((UnknownValue)ast.entity).address.displacement, 
              -1);
      } catch (NullPointerException e) { }
      
      ast.I.visit(this, null);
      ast.T.visit(this, null);
      
      return(null);
  }
  
  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {       
      try {
      addIdentifier(ast.I.spelling, 
              "UnknownRoutine",
              (ast.entity!=null?ast.entity.size:0), 
              ((UnknownRoutine)ast.entity).address.level, 
              ((UnknownRoutine)ast.entity).address.displacement,
              -1);
      } catch (NullPointerException e) { }
      ast.FPS.visit(this, null);      
      ast.T.visit(this, null);     

      return(null);
  }
  
  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {       
      try {
      addIdentifier(ast.I.spelling, 
              "UnknownRoutine",
              (ast.entity!=null?ast.entity.size:0), 
              ((UnknownRoutine)ast.entity).address.level, 
              ((UnknownRoutine)ast.entity).address.displacement,
              -1);      
      } catch (NullPointerException e) { }
      ast.FPS.visit(this, null);      
      
      return(null);
  }
  
  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {       
      try {
      addIdentifier(ast.I.spelling, 
              "UnknownAddress",
              ast.T.entity.size,
              ((UnknownAddress)ast.entity).address.level, 
              ((UnknownAddress)ast.entity).address.displacement,
              -1);
      } catch (NullPointerException e) { }
      ast.T.visit(this, null);
      
      return(null);
  }

  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) { 
      return(null);
  }
  
  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) { 
      ast.FP.visit(this, null);
      ast.FPS.visit(this, null);
      
      return(null);
  }
  
  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) { 
      ast.FP.visit(this, null);
      
      return(null);
  }

  // Actual Parameters
  public Object visitConstActualParameter(ConstActualParameter ast, Object o) { 
      ast.E.visit(this, null);
      
      return(null);
  }
  
  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) { 
      ast.I.visit(this, null);
      
      return(null);
  }
  
  public Object visitProcActualParameter(ProcActualParameter ast, Object o) { 
      ast.I.visit(this, null);
      
      return(null);
  }
  
  public Object visitVarActualParameter(VarActualParameter ast, Object o) { 
      ast.V.visit(this, null);
      
      return(null);
  }

  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {       
      return(null);
  }
  
  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) { 
      ast.AP.visit(this, null);
      ast.APS.visit(this, null);
      
      return(null);
  }
  
  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {   
      ast.AP.visit(this, null);
      
      return(null);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Type Denoters ">
  // Type Denoters
  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {      
      return(null);
  }
  
  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) { 
      ast.IL.visit(this, null);
      ast.T.visit(this, null);
      
      return(null);
  }
  
  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {       
      return(null);
  }
  
  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) { 
      return(null);
  }
  
  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) { 
      return(null);
  }
  
  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) { 
      ast.I.visit(this, null);
      
      return(null);
  }
  
  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) { 
      return(null);
  }
  
  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {   
      ast.FT.visit(this, null);
      return(null);
  }

  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) { 
      try {
      addIdentifier(ast.I.spelling, 
              "Field", 
              (ast.entity!=null?ast.entity.size:0),
              -1, ((Field)ast.entity).fieldOffset, -1);      
    } catch (NullPointerException e) { }
      ast.FT.visit(this, null);
      ast.I.visit(this, null);
      ast.T.visit(this, null);


      return(null);
  }
  
  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) { 
      try {
      addIdentifier(ast.I.spelling, 
              "Field", 
              (ast.entity!=null?ast.entity.size:0),
              -1, ((Field)ast.entity).fieldOffset, -1);
      } catch (NullPointerException e) { }
      ast.I.visit(this, null);
      ast.T.visit(this, null);
  
      return(null);
  }
  
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Literals, Identifiers and Operators ">
  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {   
      return(null);
  }
  
  public Object visitIdentifier(Identifier ast, Object o) {             
      return(null);
  }
  
  public Object visitIntegerLiteral(IntegerLiteral ast, Object o) { 
      return(null);
  }
  
  public Object visitOperator(Operator ast, Object o) { 
      ast.decl.visit(this, null);
  
      return(null);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Values or Variable Names ">
  // Value-or-variable names
  public Object visitDotVname(DotVname ast, Object o) { 
      ast.I.visit(this, null);
      ast.V.visit(this, null);
  
      return(null);
  }
  
  public Object visitSimpleVname(SimpleVname ast, Object o) { 
      ast.I.visit(this, null);
  
      return(null);
  }
  
  public Object visitSubscriptVname(SubscriptVname ast, Object o) { 
      ast.E.visit(this, null);
      ast.V.visit(this, null);
  
      return(null);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc=" Table Creation Methods ">
  // Programs
  public Object visitProgram(Program ast, Object o) { 
      ast.C.visit(this, null);
      
      return(null);
  }
  
    /**
     * Adds an identifier to the table.
     */
    private void addIdentifier(String name, String type, int size, int level, int displacement, int value) {
        boolean exists = false;
        
        for (int i=0;(i<model.getRowCount() && !exists);i++)
            if (((String)model.getValueAt(i, 0)).compareTo(name) == 0)
                exists = true;
        
        if (!exists) {
            model.addRow(new String[] {name, 
                    type, 
                    String.valueOf(size), 
                    (level<0?" ":String.valueOf(level)), 
                    (displacement<0?" ":String.valueOf(displacement)), 
                    (value<0?" ":String.valueOf(value))});
        }
    }
    
    
    /**
     * Returns the filled table model.
     */
    public DefaultTableModel getTable(Program ast) {
        model = new DefaultTableModel((new String[] {"Name", "Type", "Size", "Level", "Displacement", "Value"}), 0);
        visitProgram(ast, null);
        
        return(model);
    }
    
    // </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc=" Attributes ">
    private DefaultTableModel model;
    // </editor-fold>
    /*
     @author: Marco
    */
    //Agregado
    public Object visitProcedure (Procedure ast, Object o) {
        ast.ID.visit(this, null);
        ast.FRMPARSEQ.visit(this, null);
        ast.COM.visit(this, null);
        return (null);
    }
    //Agregado
   public Object visitFunction (Function ast, Object o) {
        ast.expAST.visit(this, null);
        ast.fpsAST.visit(this, null);
        ast.idAST.visit(this, null);
        ast.typeDenAST.visit(this, null);
        return (null);
   }
   
   //Agregado
    public Object visitCaseLiteral (CaseLiteral ast, Object o) {
        ast.terminalAST.visit(this, null);
        return (null);
   }
    
    //Agregado
    public Object visitElseCase (ElseCase ast, Object o) {
        ast.elseCaseAST.visit(this, null);
        return (null);
   }
    //Agregado
    public Object visitMixCases (MixCases ast, Object o) {
        ast.caseAST.visit(this, null);
        ast.elseCaseAST.visit(this, null);
        return (null);
   }
    //Agregado
    public Object visitNextCase (NextCase ast, Object o) {
        ast.Case_1.visit(this, null);
        ast.Case_2.visit(this, null);
        return (null);
   }
    //Agregado
    public Object visitNextCaseLiteral (NextCaseLiteral ast, Object o) {
        ast.Case_1.visit(this, null);
        ast.Case_2.visit(this, null);
        ast.commandAST.visit(this, null);
        return (null);
   }
    //Agregado
    public Object visitNextProcFuncs (NextProcFuncs ast, Object o) {
        ast.procFunc1.visit(this, null);
        ast.procFunc2.visit(this, null);
       
        return (null);
   }
    
    //Agregado
    public Object visitOneCaseLiteral (OneCaseLiteral ast, Object o) {
        ast.caseLiteralAST.visit(this, null);
        ast.commandAST.visit(this, null);
        return (null);
   }
    //Agregado
    public Object visitOneCases (OneCases ast, Object o) {
        ast.caseAST.visit(this, null);
        return (null);
   }

    
   
    
    
}
