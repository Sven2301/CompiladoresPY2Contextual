/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTML;

/**
 * @newClass
 * @author Marco
 * 
 * representa los tokens generados por HTML
 */
import java.util.regex.Pattern;

/**
 *
 * @author Marco
 */
public class TokenHTML {
    private String spelling;
    private EnumToken type;
    
    private static String[] reservedWords = new String[] { "array",
    //Eliminada
    //"begin",
    "const",
    "do",
    "else",
    "end",
    // Agregadas*****
    "for",
    "from",
    //**************
    "func",
    "if",
    "in",
    "let",
    // Agregadas*****
    "local",
    //**************
    "of",
    "proc",
    // Agregadas*****
    "range",
    //**************
    "record",
    // Agregadas*****
    "recursive",
    "repeat",
    "skip",
    "then",
    "to",
    "type",
    "until",
    //**************
    "var",
    // Agregadas*****
    "when",
    //**************
    "while"
                                                        };
    private static String[] operators = new String[] {".",":",";",",",":=","~","|","..","~"};
    private static String[] separators = new String[] {"(",")","[","]","{","}","",};
    
    public TokenHTML(String spelling){
        this.spelling = spelling;
        this.type = identifyType(spelling);
    }
    
    public TokenHTML(){
        this.spelling = null;
        this.type = null;
    }

    private EnumToken identifyType(String spelling) {
        if (spelling.contentEquals("\n")){
            return EnumToken.ENTER;
        }
        if (spelling.contentEquals("\t")){
            return EnumToken.TAB;
        }
        else if (spelling.contentEquals(" ")){
            return EnumToken.SPACE;
        }
        
        else if (isReserved(spelling)){
            return EnumToken.RESERVED;
        }
        
        else if (isOperator(spelling)){
            return EnumToken.OPERATOR;
        }
        
        else if (isSeparator(spelling)){
            return EnumToken.SEPARATOR;
        }
        
        else if (isLiteral(spelling)){
            return EnumToken.LITERAL;
        }
        
        else if (spelling.charAt(0) == '!'){
            return EnumToken.COMMENT;
        }
        return EnumToken.IDENTIFIER;
    }

    public boolean isReserved(String spelling) {
        for (String reservedWord : TokenHTML.reservedWords) {
            if (spelling.contentEquals(reservedWord)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isOperator(String spelling) {
        for (String operator : TokenHTML.operators) {
            if (spelling.contentEquals(operator)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isSeparator(String spelling) {
        for (String separator : TokenHTML.separators) {
            if (spelling.contentEquals(separator)) {
                return true;
            }
        }
        return false;
    }
    
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false; 
        }
        return pattern.matcher(strNum).matches();
    }
    
    public boolean isLiteral(String spelling){
        return (isNumeric(spelling) || isChar(spelling));
        
    }
    
    public boolean isChar(String spelling){
        return (spelling.charAt(0) == '\'') && (spelling.charAt(spelling.length()-1) == '\'');
    }
    
    @Override
    public String toString(){
        return this.spelling;
    }

    public EnumToken getType() {
        return type;
    }

    public void setType(EnumToken type) {
        this.type = type;
    }
            
}