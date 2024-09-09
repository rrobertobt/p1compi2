package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;

import java.util.LinkedList;

public class Program {
    private String name;
    private LinkedList<Statement> headerStatements;
    private LinkedList<Statement> methodsStatements;
    private LinkedList<Statement> bodyStatements;

    public Program(String name) {
        this.name = name;
        this.headerStatements = new LinkedList<>();
        this.methodsStatements = new LinkedList<>();
        this.bodyStatements = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public LinkedList<Statement> getHeaderStatements() {
        return headerStatements;
    }

    public LinkedList<Statement> getMethodsStatements() {
        return methodsStatements;
    }

    public LinkedList<Statement> getBodyStatements() {
        return bodyStatements;
    }

    public static class ProgramHeader {
        private int line, column;
        private String name;
        private LinkedList<Statement> statements;

        public ProgramHeader(int line, int column, String name) {
            this.name = name;
            this.line = line;
            this.column = column;
            this.statements = new LinkedList<>();
        }

        public String getName() {
            return name;
        }

        public LinkedList<Statement> getStatements() {
            return statements;
        }
    }
}
