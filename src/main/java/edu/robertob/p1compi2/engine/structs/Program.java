package edu.robertob.p1compi2.engine.structs;

import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.statements.TypeDeclaration;

import java.util.LinkedList;

public class Program {
    private String name;
    private LinkedList<Statement> headerStatements;
    private LinkedList<Statement> methodsStatements;
    private LinkedList<Statement> bodyStatements;
    private LinkedList<Statement> allStatements;

    public Program(ProgramHeader header, LinkedList<Statement> methodsStatements, LinkedList<Statement> bodyStatements) {
        this.name = header.getName();
        this.headerStatements = new LinkedList<>();
        this.methodsStatements = new LinkedList<>();
        if (methodsStatements != null)  this.methodsStatements.addAll(methodsStatements);
        else this.methodsStatements = new LinkedList<>();
        this.bodyStatements = bodyStatements;

        this.headerStatements.addAll(header.getStatements());
        // invert order of header statements

        this.allStatements = new LinkedList<>();
//        var newList = reverseLinkedList(this.headerStatements);
        if (this.headerStatements != null) this.allStatements.addAll(this.headerStatements);
        if (this.methodsStatements != null) this.allStatements.addAll(this.methodsStatements);
        if (this.bodyStatements != null) this.allStatements.addAll(this.bodyStatements);
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

    public LinkedList<Statement> getAllStatements() {
        return allStatements;
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

    public static LinkedList<Statement> reverseLinkedList(LinkedList<TypeDeclaration> llist)
    {
        LinkedList<Statement> revLinkedList = new LinkedList<Statement>();
        for (int i = llist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revLinkedList.add(llist.get(i));
        }
        // Return the reversed arraylist
        return revLinkedList;
    }


}
