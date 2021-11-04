/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import static jdk.nashorn.internal.objects.NativeError.getFileName;

/**
 * @newClass
 * @author Marco
 * 
 * Se encarga de generar el HTML
 * 
 */
public class GenaratorHTML {
    
    private String data;
    private ArrayList<TokenHTML> tokens;
    private String fileName;
    
    public GenaratorHTML(){
        this.data = "";
        this.tokens = new ArrayList<>();
        this.fileName = "";
    }
    
    public String getData() {
        return data;
    }
    
    public void setCode(String path) {
        this.data = ReadFile(path);
        this.fileName = path.substring(path.lastIndexOf("\\")+1).replaceAll(".tri", ".html");        
    }
    
    public ArrayList<TokenHTML> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<TokenHTML> tokens) {
        this.tokens = tokens;
    }
    
    public String  ReadFile(String path) {
        String text = "";
        try {
          File myObj = new File(path);
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            text = text.concat(data+"\n");
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return text;
    }
    
    public ArrayList<String> getCodeSections(){
        ArrayList<String> words = new ArrayList<>();
        String word = "";
        
        for (int i = 0 ; i < this.data.length(); i++){
            if(isSpecial(this.data.charAt(i))){
                words.add(word);
                words.add(this.data.charAt(i)+"");
                word = "";
            }
            
            else{
                word = word.concat(this.data.charAt(i)+"");
            }
            
        }
        words.add(word);
        return words; 
    }
    
    public ArrayList<TokenHTML> getTokens(ArrayList<String> sections){
        ArrayList<TokenHTML> codeTokens = new ArrayList<>();
        
        for (String preToken : sections){
            TokenHTML token = new TokenHTML(preToken);
            codeTokens.add(token);
        }
        
        codeTokens = fixComments(codeTokens);
        return codeTokens;
        
    }
    
    private boolean isSpecial(char charAt) {
        TokenHTML tokenIdentifier = new TokenHTML();
        return (charAt == '\n') || (charAt == ' ') || (charAt == '\t') || (tokenIdentifier.isOperator(charAt+"")) || (tokenIdentifier.isSeparator(charAt+""));
    }
    
    private ArrayList<TokenHTML> fixComments(ArrayList<TokenHTML> codeTokens) {
        boolean commentZone = false;
        

        for (TokenHTML token : codeTokens){
            if(token.getType() == EnumToken.COMMENT)
                commentZone = true;
            if (token.getType() == EnumToken.ENTER)
                commentZone = false;
            if(commentZone)
                token.setType(EnumToken.COMMENT);
        }
        

        codeTokens = unifyComments(codeTokens);
        return codeTokens;
        
        
    }
    
    private ArrayList<TokenHTML> unifyComments(ArrayList<TokenHTML> codeTokens) {
        

        ArrayList<TokenHTML> unifiedTokens = new ArrayList<>();
        String comment = "";
        TokenHTML tempToken = new TokenHTML(comment);
        
        for(TokenHTML token : codeTokens){
            if(token.getType() == EnumToken.COMMENT){
                comment = comment.concat(token.toString());
            }
            else if(token.getType() != EnumToken.COMMENT && !comment.isEmpty()){
                
                tempToken = new TokenHTML(comment);
                unifiedTokens.add(tempToken);
                comment = "";
                unifiedTokens.add(token);
            }
            else
                unifiedTokens.add(token);
            
        }
        if(!comment.isEmpty() && codeTokens.get(codeTokens.size()-1).getType() == EnumToken.COMMENT ){
            tempToken = new TokenHTML(comment);
            unifiedTokens.add(tempToken);
        }
        return unifiedTokens;
    }
    
    public void generateHTML() throws IOException{
        String html = "";
        ArrayList<String> preTokens = getCodeSections();

        ArrayList<ArrayList<TokenHTML>> tokensByLines = getLines(getTokens(preTokens));
        
        
        for(ArrayList<TokenHTML> line : tokensByLines){
            html = html.concat(lineToString(line));
        }
        
        File file = new File("HTMLs\\"+this.fileName);
        file.getParentFile().mkdirs();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file)); 
            bw.write(html);
            bw.close();
        } catch(IOException e) {
        }
    }
    
    public ArrayList<ArrayList<TokenHTML>> getLines(ArrayList<TokenHTML> tokens){
        ArrayList<ArrayList<TokenHTML>> lines = new ArrayList<>();
        ArrayList<TokenHTML> temp = new ArrayList<>();
        
        for(TokenHTML token : tokens){
            temp.add(token);
            if(token.getType() == EnumToken.ENTER){
                lines.add(temp);
                temp = new ArrayList<>();
            }
                    
        }
        lines.add(temp);
        return lines;
    
    }
    
    private String writeComment(TokenHTML comment){
        return "<span style=\"color: #99cc00;\">"+comment.toString()+"</span>"; 
    }
    
    private String writeLiteral(TokenHTML literal){
        return "<span style=\"color: #3366ff;\">"+literal.toString()+"</span> "; 
    }
    
    private String writeReserved(TokenHTML reserved){
        return "<strong>"+reserved+"</strong>" ; 
    }
    
    private String writeFormat(int tabs){
        return "<p style=\"padding-left: "+tabs+"rem; font-family: 'DejaVu Sans', monospace; \">"; 
    }
    
    private String writeEOL(){
        return "</p>"; 
    }
    
    private int getTabs(ArrayList<TokenHTML> tokens){
        if(tokens.isEmpty())
            return 0;
        else if (tokens.get(0).getType() == EnumToken.TAB){
            tokens.remove(0);
            return 1+getTabs(tokens);
        }
        else{
            tokens.remove(0);
            return 0+getTabs(tokens);
        }
        
    }

    private String lineToString(ArrayList<TokenHTML> line) {
        
        String lineString = "";
        
        lineString = lineString.concat(writeFormat(getTabs((ArrayList<TokenHTML>) line.clone())));
        
        for(TokenHTML token : line){
            
            switch (token.getType()){
                case COMMENT:
                    lineString = lineString.concat(writeComment(token));
                    break;
                case LITERAL:
                    lineString = lineString.concat(writeLiteral(token));
                    break;
                case RESERVED:
                    lineString = lineString.concat(writeReserved(token));
                    break;
                    
                default:
                    
                    lineString = lineString.concat(token.toString());
                    break;
            }
        }
        lineString = lineString.concat(writeEOL());
        return lineString;
    }
    
    
}
