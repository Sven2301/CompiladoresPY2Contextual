/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Triangle.AbstractSyntaxTrees.AST;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CaseLiteral;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.CompoundIfCommand;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.ElseCase;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Function;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MixCases;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.NextCase;
import Triangle.AbstractSyntaxTrees.NextCaseLiteral;
import Triangle.AbstractSyntaxTrees.NextProcFuncs;
import Triangle.AbstractSyntaxTrees.OneCaseLiteral;
import Triangle.AbstractSyntaxTrees.OneCases;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.PrivDeclaration;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Procedure;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForInCommand;
import Triangle.AbstractSyntaxTrees.RepeatForRangeDoCommand;
import Triangle.AbstractSyntaxTrees.RepeatForRangeUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatForRangeWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialOrCommand;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleOrCommand;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.Var2Declaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @newclass
 * @author Marco
 * 
 * Clase que implementa la generacion de un XML
 * 
 */
public class XMLGenerator implements Visitor {
    
    // Documento en el que se guardar la información
    private Document d;
    
    // Constructor
    public XMLGenerator(){}
    
   
    //Generador de XML
    
    public Object generadorXML(AST ast, String fileName) {
    
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.d = dBuilder.newDocument();
            
            this.d.appendChild((Node) ast.visitXML(this, null));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.d);
            
            File file = new File("XMLs\\"+fileName);
            file.getParentFile().mkdirs();
            
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            
        }catch (Exception e) {
      e.printStackTrace();
   }
    return null;

  }
    
    
    // Metodos de visitadores para general XML
    @Override
    public Object visitAssignCommand(AssignCommand ast, Object o) {
        Element elem = d.createElement("AssignCommand");
        elem.appendChild((Node) ast.E.visitXML(this,null));
        elem.appendChild((Node) ast.V.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitCallCommand(CallCommand ast, Object o) {
        Element elem = d.createElement("CallCommand");
        elem.appendChild((Node) ast.APS.visitXML(this,null));
        elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitCompoundIfCommand(CompoundIfCommand ast, Object o) {
        Element elem = d.createElement("CompoundIfCommand");
        elem.appendChild((Node) ast.C1.visitXML(this,null));
        elem.appendChild((Node) ast.C2.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
        elem.appendChild((Node) ast.EIC.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitEmptyCommand(EmptyCommand ast, Object o) {
       Element elem = d.createElement("EmptyCommand");
       return elem;
    }

    @Override
    public Object visitIfCommand(IfCommand ast, Object o) {
        Element elem = d.createElement("IfCommand");
        elem.appendChild((Node) ast.C1.visitXML(this, null));
        elem.appendChild((Node) ast.C2.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitLetCommand(LetCommand ast, Object o) {
        Element elem = d.createElement("LetCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.D.visitXML(this, null));
        
        
        return elem;
    }

    @Override
    public Object visitSingleOrCommand(SingleOrCommand ast, Object o) {
        Element elem = d.createElement("SingleOrCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        
        elem.appendChild((Node) ast.E.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitSequentialCommand(SequentialCommand ast, Object o) {
        Element elem = d.createElement("SequentialCommand");
        elem.appendChild((Node) ast.C1.visitXML(this, null));
        elem.appendChild((Node) ast.C2.visitXML(this, null));
        
        
        return elem;
    }

    @Override
    public Object visitSequentialOrCommand(SequentialOrCommand ast, Object o) {
        Element elem = d.createElement("SequentialOrCommand");
        elem.appendChild((Node) ast.SE1.visitXML(this, null));
        elem.appendChild((Node) ast.SE2.visitXML(this, null));
       
        return elem;
    }

    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
        Element elem = d.createElement("RepeatDoUntilCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
       
        return elem;
    }

    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        Element elem = d.createElement("RepeatDoWhileCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
       
        return elem;
    }

    @Override
    public Object visitRepeatForInCommand(RepeatForInCommand ast, Object o) {
        Element elem = d.createElement("RepeatForInCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitRepeatForRangeDoCommand(RepeatForRangeDoCommand ast, Object o) {
        Element elem = d.createElement("RepeatForRangeDoCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E1.visitXML(this, null));
        elem.appendChild((Node) ast.E2.visitXML(this, null));
        elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitRepeatForRangeUntilCommand(RepeatForRangeUntilCommand ast, Object o) {
        Element elem = d.createElement("RepeatForRangeUntilCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E1.visitXML(this, null));
        elem.appendChild((Node) ast.E2.visitXML(this, null));
        elem.appendChild((Node) ast.E3.visitXML(this, null));
        elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitRepeatForRangeWhileCommand(RepeatForRangeWhileCommand ast, Object o) {
        Element elem = d.createElement("RepeatForRangeWhileCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E1.visitXML(this, null));
        elem.appendChild((Node) ast.E2.visitXML(this, null));
        elem.appendChild((Node) ast.E3.visitXML(this, null));
        elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) {
        Element elem = d.createElement("RepeatForRangeWhileCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
        Element elem = d.createElement("RepeatUntilCommand");
        elem.appendChild((Node) ast.C.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitArrayExpression(ArrayExpression ast, Object o) {
        Element elem = d.createElement("ArrayExpression");
        elem.appendChild((Node) ast.AA.visitXML(this, null));
        
        
        return elem;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression ast, Object o) {
        Element elem = d.createElement("BinaryExpression");
        elem.appendChild((Node) ast.E1.visitXML(this, null));
        elem.appendChild((Node) ast.E2.visitXML(this, null));
        elem.appendChild((Node) ast.O.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitCallExpression(CallExpression ast, Object o) {
        Element elem = d.createElement("CallExpression");
        elem.appendChild((Node) ast.APS.visitXML(this, null));
        elem.appendChild((Node) ast.I.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitCharacterExpression(CharacterExpression ast, Object o) {
        Element elem = d.createElement("CharacterExpression");
        elem.appendChild((Node) ast.CL.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitEmptyExpression(EmptyExpression ast, Object o) {
        Element elem = d.createElement("EmptyExpression");
        return elem;
    }

    @Override
    public Object visitIfExpression(IfExpression ast, Object o) {
        Element elem = d.createElement("IfExpression");
        elem.appendChild((Node) ast.E1.visitXML(this, null));
        elem.appendChild((Node) ast.E2.visitXML(this, null));
        elem.appendChild((Node) ast.E3.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitIntegerExpression(IntegerExpression ast, Object o) {
        Element elem = d.createElement("IntegerExpression");
        elem.appendChild((Node) ast.IL.visitXML(this, null));
        
        return elem;
    }

    @Override
    public Object visitLetExpression(LetExpression ast, Object o) {
        Element elem = d.createElement("LetExpression");
        elem.appendChild((Node) ast.D.visitXML(this, null));
        elem.appendChild((Node) ast.E.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitRecordExpression(RecordExpression ast, Object o) {
        Element elem = d.createElement("RecordExpression");
        elem.appendChild((Node) ast.RA.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        Element elem = d.createElement("UnaryExpression");
        elem.appendChild((Node) ast.E.visitXML(this, null));
        elem.appendChild((Node) ast.O.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitVnameExpression(VnameExpression ast, Object o) {
        Element elem = d.createElement("VnameExpression");
        elem.appendChild((Node) ast.V.visitXML(this, null));
        return elem;
    }

    @Override
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
       Element elem = d.createElement("BinaryOperatorDeclaration");
       elem.appendChild((Node) ast.ARG1.visitXML(this, null));
       elem.appendChild((Node) ast.ARG2.visitXML(this, null));
       elem.appendChild((Node) ast.O.visitXML(this, null));
       elem.appendChild((Node) ast.RES.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
       Element elem = d.createElement("ConstDeclaration");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
       Element elem = d.createElement("FuncDeclaration");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.FPS.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitPrivDeclaration(PrivDeclaration ast, Object o) {
       Element elem = d.createElement("PrivDeclaration");
       elem.appendChild((Node) ast.D1.visitXML(this, null));
       elem.appendChild((Node) ast.D2.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
        Element elem = d.createElement("ProcDeclaration");
       elem.appendChild((Node) ast.C.visitXML(this, null));
       elem.appendChild((Node) ast.FPS.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
       Element elem = d.createElement("SequentialDeclaration");
       elem.appendChild((Node) ast.D1.visitXML(this, null));
       elem.appendChild((Node) ast.D2.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitRecDeclaration(RecDeclaration ast, Object o) {
        Element elem = d.createElement("RecDeclaration");
       elem.appendChild((Node) ast.PFs.visitXML(this, null));
       
       
       return elem;
    }

    @Override
    public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
       Element elem = d.createElement("TypeDeclaration");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
       Element elem = d.createElement("UnaryOperatorDeclaration");
       elem.appendChild((Node) ast.ARG.visitXML(this, null));
       elem.appendChild((Node) ast.O.visitXML(this, null));
       elem.appendChild((Node) ast.RES.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitVarDeclaration(VarDeclaration ast, Object o) {
       Element elem = d.createElement("VarDeclaration");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       return elem;
    }
    
    @Override
    public Object visitVar2Declaration(Var2Declaration ast, Object o) {
       Element elem = d.createElement("Var2Declaration");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.E.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
        Element elem = d.createElement("MultipleArrayAggregate");
       elem.appendChild((Node) ast.AA.visitXML(this, null));
       elem.appendChild((Node) ast.E.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
        Element elem = d.createElement("SingleArrayAggregate");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
        Element elem = d.createElement("MultipleRecordAggregate");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.RA.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
        Element elem = d.createElement("SingleRecordAggregate");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
       Element elem = d.createElement("ConstFormalParameter");
       elem.appendChild((Node) ast.T.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
       Element elem = d.createElement("FuncFormalParameter");
       elem.appendChild((Node) ast.T.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.FPS.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
       Element elem = d.createElement("ProcFormalParameter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.FPS.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
       Element elem = d.createElement("VarFormalParameter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
       Element elem = d.createElement("EmptyFormalParameterSequence");
       return elem;
    }

    @Override
    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
        Element elem = d.createElement("MultipleFormalParameterSequence");
       elem.appendChild((Node) ast.FP.visitXML(this, null));
       elem.appendChild((Node) ast.FPS.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
        Element elem = d.createElement("SingleFormalParameterSequence");
       elem.appendChild((Node) ast.FP.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
       Element elem = d.createElement("ConstActualParameter");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
       Element elem = d.createElement("FuncActualParameter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
       Element elem = d.createElement("ProcActualParameter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitVarActualParameter(VarActualParameter ast, Object o) {
        Element elem = d.createElement("VarActualParameter");
       elem.appendChild((Node) ast.V.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
        Element elem = d.createElement("EmptyActualParameterSequence");
       return elem;
    }

    @Override
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
        Element elem = d.createElement("MultipleActualParameterSequence");
       elem.appendChild((Node) ast.AP.visitXML(this, null));
       elem.appendChild((Node) ast.APS.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
        Element elem = d.createElement("SingleActualParameterSequence");
       elem.appendChild((Node) ast.AP.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
       Element elem = d.createElement("AnyTypeDenoter");
       return elem;
    }

    @Override
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
       Element elem = d.createElement("ArrayTypeDenoter");
       elem.appendChild((Node) ast.IL.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
       Element elem = d.createElement("BoolTypeDenoter");
       return elem;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
        Element elem = d.createElement("CharTypeDenoter");
       return elem;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
        Element elem = d.createElement("ErrorTypeDenoter");
       return elem;
    }

    @Override
    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
        Element elem = d.createElement("SimpleTypeDenoter");
       return elem;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
       Element elem = d.createElement("IntTypeDenoter");
       return elem;
    }

    @Override
    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
        Element elem = d.createElement("IntTypeDenoter");
       return elem;
    }

    @Override
    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
        Element elem = d.createElement("MultipleFieldTypeDenoter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.FT.visitXML(this, null));
       elem.appendChild((Node) ast.I.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
        Element elem = d.createElement("SingleFieldTypeDenoter");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.T.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
        Element elem = d.createElement("CharacterLiteral");
       
        Attr attrType = d.createAttribute("value");
        attrType.setValue(ast.spelling);
        elem.setAttributeNode(attrType);
        
       return elem;
    }

    @Override
    public Object visitIdentifier(Identifier ast, Object o) {
        Element elem = d.createElement("Identifier");
       
        Attr attrType = d.createAttribute("value");
        attrType.setValue(ast.spelling);
        elem.setAttributeNode(attrType);
        
       return elem;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
        Element elem = d.createElement("IntegerLiteral");
       
        Attr attrType = d.createAttribute("value");
        attrType.setValue(ast.spelling);
        elem.setAttributeNode(attrType);
        
       return elem;
    }

    @Override
    public Object visitOperator(Operator ast, Object o) {
        Element elem = d.createElement("Operator");
       
        Attr attrType = d.createAttribute("value");
        attrType.setValue(ast.spelling);
        elem.setAttributeNode(attrType);
        
       return elem;
    }

    @Override
    public Object visitDotVname(DotVname ast, Object o) {
        Element elem = d.createElement("DotVname");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       elem.appendChild((Node) ast.V.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitSimpleVname(SimpleVname ast, Object o) {
        Element elem = d.createElement("SimpleVname");
       elem.appendChild((Node) ast.I.visitXML(this, null));
       
       
       return elem;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        Element elem = d.createElement("SubscriptVname");
       elem.appendChild((Node) ast.E.visitXML(this, null));
       elem.appendChild((Node) ast.V.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitProgram(Program ast, Object o) {
        Element elem = d.createElement("Program");
       elem.appendChild((Node) ast.C.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitCaseLiteral(CaseLiteral AST, Object o) {
        Element elem = d.createElement("CaseLiteral");
       elem.appendChild((Node) AST.terminalAST.visitXML(this, null));
       
       return elem;
    }

    @Override
    public Object visitNextCaseLiteral(NextCaseLiteral AST, Object o) {
        Element elem = d.createElement("NextCaseLiteral");
       elem.appendChild((Node) AST.Case_1.visitXML(this, null));
       elem.appendChild((Node) AST.Case_2.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitOneCaseLiteral(OneCaseLiteral AST, Object o) {
        Element elem = d.createElement("OneCaseLiteral");
       elem.appendChild((Node) AST.caseLiteralAST.visitXML(this, null));
       elem.appendChild((Node) AST.commandAST.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitElseCase(ElseCase AST, Object o) {
        Element elem = d.createElement("ElseCase");
       elem.appendChild((Node) AST.elseCaseAST.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitNextCase(NextCase AST, Object o) {
        Element elem = d.createElement("NextCase");
       elem.appendChild((Node) AST.Case_1.visitXML(this, null));
       elem.appendChild((Node) AST.Case_2.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitMixCases(MixCases AST, Object o) {
        Element elem = d.createElement("MixCases");
       elem.appendChild((Node) AST.caseAST.visitXML(this, null));
       elem.appendChild((Node) AST.elseCaseAST.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitOneCases(OneCases AST, Object o) {
        Element elem = d.createElement("OneCases");
       elem.appendChild((Node) AST.caseAST.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitProcedure(Procedure AST, Object o) {
        Element elem = d.createElement("Procedure");
       elem.appendChild((Node) AST.COM.visitXML(this, null));
       elem.appendChild((Node) AST.FRMPARSEQ.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitFunction(Function AST, Object o) {
        Element elem = d.createElement("Function");
       elem.appendChild((Node) AST.expAST.visitXML(this, null));
       elem.appendChild((Node) AST.fpsAST.visitXML(this, null));
       elem.appendChild((Node) AST.idAST.visitXML(this, null));
       elem.appendChild((Node) AST.typeDenAST.visitXML(this, null));
       return elem;
    }

    @Override
    public Object visitNextProcFuncs(NextProcFuncs AST, Object o) {
        Element elem = d.createElement("Function");
       elem.appendChild((Node) AST.procFunc1.visitXML(this, null));
       elem.appendChild((Node) AST.procFunc2.visitXML(this, null));
       
       return elem;
    }
    
}
