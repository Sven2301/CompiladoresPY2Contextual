/*
 * IDE-Triangle v1.0
 * Compiler.java 
 */

package Triangle;

import Triangle.CodeGenerator.Frame;
import java.awt.event.ActionListener;
import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.SyntacticAnalyzer.Scanner;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.SyntacticAnalyzer.Parser;
import Triangle.ContextualAnalyzer.Checker;
import Triangle.CodeGenerator.Encoder;

import HTML.GenaratorHTML; //Agregado
import XML.XMLGenerator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/** 
 * This is merely a reimplementation of the Triangle.Compiler class. We need
 * to get to the ASTs in order to draw them in the IDE without modifying the
 * original Triangle code.
 *
 * @author Luis Leopoldo Pérez <luiperpe@ns.isi.ulatina.ac.cr>
 */
public class IDECompiler {

    // <editor-fold defaultstate="collapsed" desc=" Methods ">
    /**
     * Creates a new instance of IDECompiler.
     *
     */
    public IDECompiler() {
    }
    
    /**
     * Particularly the same compileProgram method from the Triangle.Compiler
     * class.
     * @param sourceName Path to the source file.
     * @return True if compilation was succesful.
     */
    public boolean compileProgram(String sourceName) {
        System.out.println("********** " +
                           "Triangle Compiler (IDE-Triangle 1.0)" +
                           " **********");
        
        System.out.println("Syntactic Analysis ...");
        SourceFile source = new SourceFile(sourceName);
        SourceFile sourceHTML = new SourceFile(sourceName);
        Scanner scanner = new Scanner(source);
        
        //Agregado
        /*
        @Author Marco
        - Escaner del html
        */
        Scanner scannerHTML = new Scanner(sourceHTML);
        GenaratorHTML genHTML = new GenaratorHTML();
        genHTML.setCode(sourceName);
        boolean conditionalHTML = true;
        boolean conditionalXML = false;
        
        //Agregado
        /*
        @Author Marco
        - Visitor del xml
        */
        
        XMLGenerator xmlGen = new XMLGenerator();
        
        
        report = new IDEReporter();
        Parser parser = new Parser(scanner, report);
        boolean success = false;
        
        rootAST = parser.parseProgram();
        if (report.numErrors == 0) {

            
            
            System.out.println("Contextual Analysis ...");
            Checker checker = new Checker(report);
            checker.check(rootAST);
            
            if (report.numErrors == 0) {
                /*
                System.out.println("Code Generation ...");
                Encoder encoder = new Encoder(report);
                encoder.encodeRun(rootAST, false);
                */
                
                if (report.numErrors == 0) {
                    //encoder.saveObjectProgram(sourceName.replace(".tri", ".tam"));
                    success = true;
                    conditionalXML = true;
                }
            }
            
            success = true;
            conditionalXML = true;
        }
        
        if (conditionalXML)
            xmlGen.generadorXML(rootAST,sourceName.substring(sourceName.lastIndexOf("\\")+1).replaceAll(".tri",".xml"));
        if (conditionalHTML)
            try {
                genHTML.generateHTML();
            }catch(IOException ex){
                Logger.getLogger(IDECompiler.class.getName()).log(Level.SEVERE, null, ex);
            }

        if (success)
            System.out.println("Compilation was successful.");
        else
            System.out.println("Compilation was unsuccessful.");
        
        return(success);
    }
      
    /**
     * Returns the line number where the first error is.
     * @return Line number.
     */
    public int getErrorPosition() {
        return(report.getFirstErrorPosition());
    }
        
    /**
     * Returns the root Abstract Syntax Tree.
     * @return Program AST (root).
     */
    public Program getAST() {
        return(rootAST);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Attributes ">
    private Program rootAST;        // The Root Abstract Syntax Tree.    
    private IDEReporter report;     // Our ErrorReporter class.
    // </editor-fold>

    private Scanner GeneratorHTML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
