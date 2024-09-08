package edu.robertob.p1compi2;

//import edu.robertob.p1compi2.analysis.PLexer;
//import edu.robertob.p1compi2.analysis.PParser;
import edu.robertob.p1compi2.view.MainFrame;

import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
//        StringReader sr = new StringReader(
//                "program myProgram" +
//                        " " +
//                "type" +
//                        " " +
//                "days, myvar, other = integer;"
//        );
//        PLexer lexer = new PLexer(sr);
//        PParser parser = new PParser(lexer);
//
//        try {
//            parser.parse();
//            System.out.println("Parseado correctamente");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        var frame = new MainFrame();
        frame.setVisible(true);
    }
}