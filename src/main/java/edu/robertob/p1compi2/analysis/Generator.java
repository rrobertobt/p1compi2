package edu.robertob.p1compi2.analysis;

public class Generator {
    public static void main(String[] args) {
        try {
            // Generate the Lexer with JFlex
            String path = "src/main/java/edu/robertob/p1compi2/analysis/";
            String Flex[] = {path + "Lexer.flex", "-d", path};
            jflex.Main.generate(Flex);

            // Generate the Parser with Cup
            String Cup[] = {"-destdir", path, "-parser", "PParser", path + "Parser.cup"};
            java_cup.Main.main(Cup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
