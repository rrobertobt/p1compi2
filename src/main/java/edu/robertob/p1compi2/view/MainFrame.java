package edu.robertob.p1compi2.view;

import edu.robertob.p1compi2.analysis.PLexer;
import edu.robertob.p1compi2.analysis.PParser;
import edu.robertob.p1compi2.data.CurrentSession;
import edu.robertob.p1compi2.data.PFile;
import edu.robertob.p1compi2.engine.base.Statement;
import edu.robertob.p1compi2.engine.statements.*;
import edu.robertob.p1compi2.engine.structs.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

/**
 * @author robertob
 */
public class MainFrame extends JFrame {

    CurrentSession currentSession = new CurrentSession();
    TypesReportFrame typesReportFrame = new TypesReportFrame();
    SymbolsReportFrame symbolsReportFrame = new SymbolsReportFrame();
//    ErrorsReportFrame errorsReportFrame = new ErrorsReportFrame();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        jToolBar2.setFloatable(false);
        jToolBar2.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));

        consoleOutputLabel.setFont(consoleOutputLabel.getFont().deriveFont(Font.BOLD));

        jTextPane1.setFont(new Font("monospaced", Font.PLAIN, 14));
        jSplitPane2.setDividerLocation(0.5);
        jSplitPane2.setResizeWeight(0.5);

        // create a keyboard shortcut listener for the run button (ctrl+R, ctrl+ENTER)
        runCodeBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "runCode");
        runCodeBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), "runCode");

        runCodeBtn.getActionMap().put("runCode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSession.getActiveFileIndex() != -1)
                    runCodeBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for the analyze all button (ctrl+E, ctrl+shift+ENTER)
        analyzeAllCodeBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "analyzeAllCode");
        analyzeAllCodeBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "analyzeAllCode");

        analyzeAllCodeBtn.getActionMap().put("analyzeAllCode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSession.getActiveFileIndex() != -1)
                    analyzeAllCodeBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for the save button (ctrl+S, command+S)
        saveFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "saveFile");
        saveFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK), "saveFile");
        saveFileBtn.getActionMap().put("saveFile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSession.getActiveFileIndex() != -1)
                    saveFileBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for the save as button (ctrl+shift+S, command+shift+S)
        saveAsFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "saveAsFile");
        saveAsFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "saveAsFile");
        saveAsFileBtn.getActionMap().put("saveAsFile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSession.getActiveFileIndex() != -1)
                    saveAsFileBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for the new file button (ctrl+N, command+N)
        newFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "newFile");
        newFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_DOWN_MASK), "newFile");
        newFileBtn.getActionMap().put("newFile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFileBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for the open file button (ctrl+O, command+O)
        openFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "openFile");
        openFileBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_DOWN_MASK), "openFile");
        openFileBtn.getActionMap().put("openFile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.doClick();
            }
        });

        // create a keyboard shortcut listener for closing the current tab (ctrl+W, command+W)
        jTabbedPane1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK), "closeTab");
        jTabbedPane1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_DOWN_MASK), "closeTab");
        jTabbedPane1.getActionMap().put("closeTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var index = jTabbedPane1.getSelectedIndex();
                var test = (ButtonTabComponent)jTabbedPane1.getTabComponentAt(index);
                var test2 = test.getButton();
                test2.doClick();
//                if (index != -1) {
//                    jTabbedPane1.remove(index);
//                    currentSession.removeFile(index);
//                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar2 = new javax.swing.JToolBar();
        newFileBtn = new javax.swing.JButton();
        openFileBtn = new javax.swing.JButton();
        saveFileBtn = new javax.swing.JButton();
        saveAsFileBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        fileStatusLabel = new javax.swing.JLabel();
        runCodeBtn = new javax.swing.JButton();
        analyzeAllCodeBtn = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        consoleOutputLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        reportsMenu = new javax.swing.JMenu();
        symbolsTableMenuItem = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        treeMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(252, 252, 252));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setBorderPainted(false);
        jToolBar2.setEnabled(false);

        newFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/file-plus.png"))); // NOI18N
        newFileBtn.setText("Nuevo archivo");
        newFileBtn.setFocusable(false);
        newFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(newFileBtn);

        openFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/file-import.png"))); // NOI18N
        openFileBtn.setText("Abrir archivo");
        openFileBtn.setFocusable(false);
        openFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(openFileBtn);

        saveFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/download.png"))); // NOI18N
        saveFileBtn.setText("Guardar");
        saveFileBtn.setEnabled(false);
        saveFileBtn.setFocusable(false);
        saveFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(saveFileBtn);

        saveAsFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/download.png"))); // NOI18N
        saveAsFileBtn.setText("Guardar como...");
        saveAsFileBtn.setEnabled(false);
        saveAsFileBtn.setFocusable(false);
        saveAsFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveAsFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveAsFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsFileBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(saveAsFileBtn);

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOpaque(true);
        jSeparator2.setSeparatorSize(new java.awt.Dimension(1, 30));
        jToolBar2.add(jSeparator2);
        jToolBar2.add(fileStatusLabel);

        runCodeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/player-play.png"))); // NOI18N
        runCodeBtn.setText("Ejecutar");
        runCodeBtn.setEnabled(false);
        runCodeBtn.setFocusable(false);
        runCodeBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runCodeBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        runCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runCodeBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(runCodeBtn);

        analyzeAllCodeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/player-next.png"))); // NOI18N
        analyzeAllCodeBtn.setText("Analizar todos");
        analyzeAllCodeBtn.setEnabled(false);
        analyzeAllCodeBtn.setFocusable(false);
        analyzeAllCodeBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        analyzeAllCodeBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        analyzeAllCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analyzeAllCodeBtnActionPerformed(evt);
            }
        });
        jToolBar2.add(analyzeAllCodeBtn);

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel1);

        consoleOutputLabel.setText("Salida de la consola");

        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(consoleOutputLabel)
                                .addContainerGap(675, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(consoleOutputLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(jPanel2);

        jMenuBar1.setBackground(new java.awt.Color(252, 252, 252));
        jMenuBar1.setBorder(null);
        jMenuBar1.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N

        reportsMenu.setText("Reportes");
        reportsMenu.setEnabled(false);

        symbolsTableMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list-letters.png"))); // NOI18N
        symbolsTableMenuItem.setText("Ver tabla de símbolos");
        symbolsTableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                symbolsTableMenuItemActionPerformed(evt);
            }
        });
        reportsMenu.add(symbolsTableMenuItem);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/category.png"))); // NOI18N
        jMenuItem2.setText("Ver tabla de tipos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        reportsMenu.add(jMenuItem2);

        treeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/binary-tree.png"))); // NOI18N
        treeMenuItem.setText("Ver árbol de activaciones");
        treeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treeMenuItemActionPerformed(evt);
            }
        });
        reportsMenu.add(treeMenuItem);

        jMenuBar1.add(reportsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.LEADING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSplitPane2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newFileBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newFileBtnActionPerformed
        var name = JOptionPane.showInputDialog(this, "Nombre del archivo");
        if (name == null || name.isEmpty()) {
            return;
        }
        var content = "";
        var sessionFile = new PFile(name, "", content, false, true, jTabbedPane1.getTabCount());
        currentSession.addFile(sessionFile);

        var textPane = new JTextPane();
        textPane.setText(content);
        var scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        var tln = new TextLineNumber(textPane, 3);
        scrollPane.setRowHeaderView(tln);

//        textPane.setFont(new Font("monospacedd", Font.PLAIN, 15));
        textPane.setFont(new Font("monospaced", Font.PLAIN, 15));

        jTabbedPane1.add(name, scrollPane);
        jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount() - 1, new ButtonTabComponent(jTabbedPane1, currentSession));
        fileStatusLabel.setText("Modificado");
        fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.BOLD));
//        switchTabAndSetDocumentListener(sessionFile, textPane);
        switchTabAndSetDocumentListener(sessionFile, textPane);
    }//GEN-LAST:event_newFileBtnActionPerformed

    private void switchTabAndSetDocumentListener(PFile sessionFile, JTextPane textArea) {
        jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
        final String UNDO_ACTION = "Undo";
        final String REDO_ACTION = "Redo";

        final var undoMgr = new UndoManager();

        // Add listener for undoable events
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent pEvt) {
                undoMgr.addEdit(pEvt.getEdit());
            }
        });

        // Add undo/redo actions
        textArea.getActionMap().put(UNDO_ACTION, new AbstractAction(UNDO_ACTION) {
            public void actionPerformed(ActionEvent pEvt) {
                try {
                    if (undoMgr.canUndo()) {
                        undoMgr.undo();
                    }
                } catch (CannotUndoException e) {
                    e.printStackTrace();
                }
            }
        });
        textArea.getActionMap().put(REDO_ACTION, new AbstractAction(REDO_ACTION) {
            public void actionPerformed(ActionEvent pEvt) {
                try {
                    if (undoMgr.canRedo()) {
                        undoMgr.redo();
                    }
                } catch (CannotRedoException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create keyboard accelerators for undo/redo actions (Ctrl+Z/Ctrl+Y)
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),
                UNDO_ACTION);
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK),
                REDO_ACTION);

        // using 'meta' for mac
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_DOWN_MASK),
                UNDO_ACTION);
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.META_DOWN_MASK),
                REDO_ACTION);


        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sessionFile.setContent(textArea.getText());

                if (sessionFile.isSaved()) {
                    sessionFile.setSaved(false);
                    MainFrame.this.jTabbedPane1.setTitleAt(sessionFile.getIndex(), "*" + sessionFile.getName());

                    MainFrame.this.fileStatusLabel.setText("Modificado");
                    MainFrame.this.fileStatusLabel.setFont(MainFrame.this.fileStatusLabel.getFont().deriveFont(Font.BOLD));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sessionFile.setContent(textArea.getText());

                if (sessionFile.isSaved()) {
                    sessionFile.setSaved(false);
                    MainFrame.this.jTabbedPane1.setTitleAt(sessionFile.getIndex(), "*" + sessionFile.getName());

                    MainFrame.this.fileStatusLabel.setText("Modificado");
                    MainFrame.this.fileStatusLabel.setFont(MainFrame.this.fileStatusLabel.getFont().deriveFont(Font.BOLD));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void openFileBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openFileBtnActionPerformed
        var fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        var result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
//            var file = fileChooser.getSelectedFile();
            var files = fileChooser.getSelectedFiles();

            for (var file : files) {
                var path = file.getAbsolutePath();
                var name = file.getName();
                var content = "";
                var sessionFile = new PFile(name, path, content, true, false, jTabbedPane1.getTabCount());

                for (int i = 0; i < currentSession.getFiles().size(); i++) {
                    if (currentSession.getFiles().get(i) != null && currentSession.getFiles().get(i).getSystemPath().equals(path)) {
                        JOptionPane.showMessageDialog(this, "El archivo ya está abierto", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                currentSession.addFile(sessionFile);

                try {
                    var reader = new BufferedReader(new FileReader(path));
                    var line = reader.readLine();
                    while (line != null) {
                        content += line + "\n";
                        line = reader.readLine();
                    }
                    if (content.length() > 0) content = content.substring(0, content.length() - 1);
                    sessionFile.setContent(content);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                var undoManager = new UndoManager();
                var textPane = new JTextPane();
                textPane.setText(content);
                var scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                var tln = new TextLineNumber(textPane, 3);
                scrollPane.setRowHeaderView(tln);

                textPane.setFont(new Font("monospaced", Font.PLAIN, 15));

                jTabbedPane1.add(name, scrollPane);
                jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount() - 1, new ButtonTabComponent(jTabbedPane1, currentSession));
                currentSession.setActiveFile(jTabbedPane1.getTabCount() - 1);
                switchTabAndSetDocumentListener(sessionFile, textPane);
            }
        }


    }//GEN-LAST:event_openFileBtnActionPerformed

    private void jTabbedPane1StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // change the active file
        var index = jTabbedPane1.getSelectedIndex();
        if (index == -1) {
            fileStatusLabel.setText("");
            jTextPane1.setText("");
            saveFileBtn.setEnabled(false);
            saveAsFileBtn.setEnabled(false);
            runCodeBtn.setEnabled(false);
            analyzeAllCodeBtn.setEnabled(false);
            reportsMenu.setEnabled(false);
            return;
        }
        currentSession.setActiveFile(jTabbedPane1.getSelectedIndex());
        jTextPane1.setText(currentSession.getActiveFile().getConsoleOutput());
        if (currentSession.getActiveFile().isSaved()) {
            fileStatusLabel.setText("Guardado");
            fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.PLAIN));
            jTabbedPane1.setTitleAt(currentSession.getActiveFileIndex(), currentSession.getActiveFile().getName());
        } else {
            fileStatusLabel.setText("Modificado");
            fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.BOLD));
            jTabbedPane1.setTitleAt(currentSession.getActiveFileIndex(), "*" + currentSession.getActiveFile().getName());
        }
        saveFileBtn.setEnabled(true);
        saveAsFileBtn.setEnabled(true);
        runCodeBtn.setEnabled(true);
        analyzeAllCodeBtn.setEnabled(true);

        System.out.println(currentSession.getActiveFile().getErrors().isEmpty() + " " + currentSession.getActiveFile().getName());
        if (currentSession.getActiveFile().getErrors().isEmpty()) {
            reportsMenu.setEnabled(true);
        } else {
            reportsMenu.setEnabled(false);
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void saveFileBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveFileBtnActionPerformed
        var activeFile = currentSession.getActiveFile();
        if (activeFile != null) {
            if (!activeFile.isNew()) {
                activeFile.setSaved(true);
                activeFile.saveFile(activeFile.getContent(), false);
                fileStatusLabel.setText("Guardado");
                jTabbedPane1.setTitleAt(currentSession.getActiveFileIndex(), activeFile.getName());
                fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.PLAIN));
            } else {
                var fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(activeFile.getName() + ".p"));
                var result = fileChooser.showSaveDialog(this);
                // also set the default file name to the current file name
                if (result == JFileChooser.APPROVE_OPTION) {
                    var file = fileChooser.getSelectedFile();
                    var path = file.getAbsolutePath();
                    activeFile.setSystemPath(path);
                    activeFile.setIsNew(false);
                    activeFile.setSaved(true);
                    boolean created = activeFile.saveFile(activeFile.getContent(), true);
                    if (!created) return;
                    fileStatusLabel.setText("Guardado");
                    fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.PLAIN));
                    jTabbedPane1.setTitleAt(currentSession.getActiveFileIndex(), file.getName());
                }
            }
        }
    }//GEN-LAST:event_saveFileBtnActionPerformed

    private void runCodeBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_runCodeBtnActionPerformed
        var code = currentSession.getActiveFile().getContent();
        code = code.trim();
        PLexer lexer = new PLexer(new StringReader(code));
        PParser parser = new PParser(lexer);
        currentSession.getActiveFile().getErrors().clear();
        LinkedList<PError> allErrors = new LinkedList<>();

        // Create the tables (types, symbols)

        try {
            var parserResult = parser.parse();
            TypesTable typesTable = new TypesTable("Global");
            typesTable.fillDefaultTypes();
            var tree = new Tree(((Program) parserResult.value).getAllStatements());
            allErrors = tree.getErrors();
            var globalTable = new SymbolTable("Global");
            tree.setGlobalTable(globalTable);
            currentSession.getActiveFile().setCurrentTree(tree);
            currentSession.getActiveFile().setGlobalTable(globalTable);
            currentSession.getActiveFile().setTypesTable(typesTable);

            //todo: 3 passes through the tree (when methods are implemented):
            // 1. get all the functions and their parameters, also structs
            // 2. get all the variables and their types
            // 3. execute the body of the main block

            for (Statement statement : tree.getStatements()) {
                if (statement == null) continue;

                if (statement instanceof TypeDeclaration || statement instanceof ConstantDeclaration || statement instanceof VariableDeclaration) {
//                    System.out.println("in run"+((TypeDeclaration)statement).getNames());
                    var result = statement.execute(tree, globalTable, typesTable);
//                    if (result instanceof PError) {
//                        allErrors.add((PError) result);
//                    }
                }
            }

            System.out.println("Tree");
            for (Statement statement : tree.getStatements()) {
                System.out.println(statement);
                if (statement == null) continue;

                if (statement instanceof FunctionDeclaration method) {
                    System.out.println("in run"+method.getId());
                    if (method.isReservedMethod()) {
                        currentSession.getActiveFile().getErrors().add(new PError("Semantica", "No se puede declarar un método con el nombre de una función reservada: " + method.getId(), method.getLine(), method.getColumn()));
                        allErrors.add(new PError("Semantica", "No se puede declarar un método con el nombre de una función reservada: " + method.getId(), method.getLine(), method.getColumn()));
                        continue;
                    }
                    tree.addFunction(method);
                    globalTable.addFunction(method);
                }
            }

            for (Statement statement : tree.getStatements()) {
                if (statement == null) continue;
                if (statement instanceof ProcedureDeclaration procedure) {
                    if (procedure.isReservedMethod()) {
                        currentSession.getActiveFile().getErrors().add(new PError("Semantica", "No se puede declarar un procedimiento con el nombre de una función reservada: " + procedure.getId(), procedure.getLine(), procedure.getColumn()));
                        allErrors.add(new PError("Semantica", "No se puede declarar un procedimiento con el nombre de una función reservada: " + procedure.getId(), procedure.getLine(), procedure.getColumn()));
                        continue;
                    }
                    tree.addProcedure(procedure);
                    globalTable.addProcedure(procedure);
                }
            }

            //
            for (Statement statement : tree.getStatements()) {
                if (statement == null) continue;
                if (!(statement instanceof TypeDeclaration || statement instanceof ConstantDeclaration || statement instanceof VariableDeclaration) && !(statement instanceof FunctionDeclaration) && !(statement instanceof ProcedureDeclaration)) {
                    var result = statement.execute(tree, globalTable, typesTable);
//                    if (result instanceof PError) {
//                        allErrors.add((PError) result);
//                    }
                }
            }

//            var alltypes = typesTable.collectAllEntries();
//            for (var type : alltypes) {
//                System.out.println(type.toString());
//            }

        } catch (Exception e) {
            allErrors.add(new PError("Sintáctico", "Error al analizar el código", 0, 0));
            e.printStackTrace();
        }
        jTextPane1.setText("");
        allErrors.addAll(lexer.getLexicalErrorList());
//        currentSession.getActiveFile().getErrors().addAll(lexer.getLexicalErrorList());
        allErrors.addAll(parser.getSyntaxErrorList());
//        currentSession.getActiveFile().getErrors().addAll(parser.getSyntaxErrorList());
        currentSession.getActiveFile().getErrors().addAll(allErrors);
        if (allErrors.isEmpty()) {
            jTextPane1.setText(jTextPane1.getText() + "\n✅ -> Analisis completado sin errores\n");
            reportsMenu.setEnabled(true);
        } else {
            jTextPane1.setText(jTextPane1.getText() + "\n❌ -> Análisis no fue exitoso - Errores encontrados:\n");
            reportsMenu.setEnabled(false);
            for (PError error : allErrors) {
                jTextPane1.setText(jTextPane1.getText() + error.toString() + "\n");
            }
        }
        currentSession.getActiveFile().setConsoleOutput(jTextPane1.getText());
    }//GEN-LAST:event_runCodeBtnActionPerformed

    private void symbolsTableMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_symbolsTableMenuItemActionPerformed
        var activeFile = currentSession.getActiveFile();
        if (activeFile != null) {
            symbolsReportFrame.setSymbolTableAndShow(activeFile.getGlobalTable());
        }
    }//GEN-LAST:event_symbolsTableMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        var activeFile = currentSession.getActiveFile();
        if (activeFile != null) {
//            activeFile.getGlobalTable().addMethods(currentSession.getActiveFile().getCurrentTree());
            typesReportFrame.setTypesTableAndShow(activeFile.getTypesTable());
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void saveAsFileBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveAsFileBtnActionPerformed
        var activeFile = currentSession.getActiveFile();
        if (activeFile != null) {
            if (!activeFile.isNew()) {
                var fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(activeFile.getName()));
                var result = fileChooser.showSaveDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    var file = fileChooser.getSelectedFile();
                    var path = file.getAbsolutePath();
                    var newSessionFile = new PFile(file.getName(), path, activeFile.getContent(), true, false, jTabbedPane1.getTabCount());
                    boolean saved = newSessionFile.saveFile(newSessionFile.getContent(), true);
                    if (!saved) return;
                    currentSession.addFile(newSessionFile);

                    try {
                        var reader = new BufferedReader(new FileReader(path));
                        var line = reader.readLine();
                        var content = "";
                        while (line != null) {
                            content += line + "\n";
                            line = reader.readLine();
                        }
                        if (content.length() > 0) content = content.substring(0, content.length() - 1);
                        newSessionFile.setContent(content);
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    var textPane = new JTextPane();
                    textPane.setText(newSessionFile.getContent());
                    var scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    var tln = new TextLineNumber(textPane, 3);
                    scrollPane.setRowHeaderView(tln);

                    textPane.setFont(new Font("monospaced", Font.PLAIN, 15));

                    jTabbedPane1.add(newSessionFile.getName(), scrollPane);
                    jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount() - 1, new ButtonTabComponent(jTabbedPane1, currentSession));
                    currentSession.setActiveFile(jTabbedPane1.getTabCount() - 1);
                    switchTabAndSetDocumentListener(newSessionFile, textPane);
                }
            } else {
                var fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(activeFile.getName() + ".p"));
                var result = fileChooser.showSaveDialog(this);
                // also set the default file name to the current file name
                if (result == JFileChooser.APPROVE_OPTION) {
                    var file = fileChooser.getSelectedFile();
                    var path = file.getAbsolutePath();
                    activeFile.setSystemPath(path);
                    activeFile.setIsNew(false);
                    activeFile.setSaved(true);
                    boolean created = activeFile.saveFile(activeFile.getContent(), true);
                    if (!created) return;
                    fileStatusLabel.setText("Guardado");
                    fileStatusLabel.setFont(fileStatusLabel.getFont().deriveFont(Font.PLAIN));
                    jTabbedPane1.setTitleAt(currentSession.getActiveFileIndex(), file.getName());
                }
            }
        }
    }//GEN-LAST:event_saveAsFileBtnActionPerformed

    private void analyzeAllCodeBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_analyzeAllCodeBtnActionPerformed
        var allFiles = currentSession.getFiles();

        boolean success = true;
        var failedFileNames = new LinkedList<String>();
        for (var file : allFiles) {
            if (file == null) continue;
            var code = file.getContent();
            code = code.trim();
            PLexer lexer = new PLexer(new StringReader(code));
            PParser parser = new PParser(lexer);
            file.getErrors().clear();
            LinkedList<PError> allErrors = new LinkedList<>();

            // Create the tables (types, symbols)

            try {
                var parserResult = parser.parse();
                TypesTable typesTable = new TypesTable("Global");
                typesTable.fillDefaultTypes();
                var tree = new Tree(((Program) parserResult.value).getAllStatements());
                allErrors = tree.getErrors();
                var globalTable = new SymbolTable("Global");
                tree.setGlobalTable(globalTable);
                file.setCurrentTree(tree);
                file.setGlobalTable(globalTable);
                file.setTypesTable(typesTable);

                for (Statement statement : tree.getStatements()) {
                    if (statement == null) continue;

                    if (statement instanceof TypeDeclaration || statement instanceof ConstantDeclaration || statement instanceof VariableDeclaration) {
//                    System.out.println("in run"+((TypeDeclaration)statement).getNames());
                        var result = statement.execute(tree, globalTable, typesTable);
//                    if (result instanceof PError) {
//                        allErrors.add((PError) result);
//                    }
                    }
                }

                System.out.println("Tree");
                for (Statement statement : tree.getStatements()) {
                    System.out.println(statement);
                    if (statement == null) continue;

                    if (statement instanceof FunctionDeclaration method) {
                        System.out.println("in run"+method.getId());
                        if (method.isReservedMethod()) {
                            currentSession.getActiveFile().getErrors().add(new PError("Semantica", "No se puede declarar un método con el nombre de una función reservada: " + method.getId(), method.getLine(), method.getColumn()));
                            allErrors.add(new PError("Semantica", "No se puede declarar un método con el nombre de una función reservada: " + method.getId(), method.getLine(), method.getColumn()));
                            continue;
                        }
                        tree.addFunction(method);
                        globalTable.addFunction(method);
                    }
                }

                for (Statement statement : tree.getStatements()) {
                    if (statement == null) continue;
                    if (statement instanceof ProcedureDeclaration procedure) {
                        if (procedure.isReservedMethod()) {
                            currentSession.getActiveFile().getErrors().add(new PError("Semantica", "No se puede declarar un procedimiento con el nombre de una función reservada: " + procedure.getId(), procedure.getLine(), procedure.getColumn()));
                            allErrors.add(new PError("Semantica", "No se puede declarar un procedimiento con el nombre de una función reservada: " + procedure.getId(), procedure.getLine(), procedure.getColumn()));
                            continue;
                        }
                        tree.addProcedure(procedure);
                        globalTable.addProcedure(procedure);
                    }
                }

                //
                for (Statement statement : tree.getStatements()) {
                    if (statement == null) continue;
                    if (!(statement instanceof TypeDeclaration || statement instanceof ConstantDeclaration || statement instanceof VariableDeclaration) && !(statement instanceof FunctionDeclaration) && !(statement instanceof ProcedureDeclaration)) {
                        var result = statement.execute(tree, globalTable, typesTable);
//                    if (result instanceof PError) {
//                        allErrors.add((PError) result);
//                    }
                    }
                }

            } catch (Exception e) {
                allErrors.add(new PError("Sintáctico", "Error al analizar el código", 0, 0));
                e.printStackTrace();
            }

            allErrors.addAll(lexer.getLexicalErrorList());
            file.getErrors().addAll(lexer.getLexicalErrorList());
            allErrors.addAll(parser.getSyntaxErrorList());
            file.getErrors().addAll(parser.getSyntaxErrorList());
            String consoleResult = "";
            if (allErrors.isEmpty()) {
                // check if the current tab is the one being analyzed
                    System.out.println("active file index: " + currentSession.getActiveFileIndex() + " file index: " + file.getIndex());
                if (currentSession.getActiveFileIndex() == file.getIndex()) {
                    jTextPane1.setText("\n✅ -> Analisis completado sin errores\n");
                }
                consoleResult = "\n✅ -> Analisis completado sin errores\n";
            } else {
                // check if the current tab is the one being analyzed
                System.out.println("active file index: " + currentSession.getActiveFileIndex() + " file index: " + file.getIndex());
                if (currentSession.getActiveFileIndex() == file.getIndex()) {
                    jTextPane1.setText("\n❌ -> Análisis no fue exitoso - Errores encontrados:\n");
                    for (PError error : allErrors) {
                        jTextPane1.setText(jTextPane1.getText() + error.toString() + "\n");
                    }
                }
                consoleResult = "\n❌ -> Análisis no fue exitoso - Errores encontrados:\n";
                for (PError error : allErrors) {
                    consoleResult += error.toString() + "\n";
                }
                success = false;
                failedFileNames.add(file.getName());
            }
            file.setConsoleOutput(consoleResult);
        }
        if (success) {
            JOptionPane.showMessageDialog(this, "Todos los archivos fueron analizados con éxito", "Análisis completado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Algunos archivos no pudieron ser analizados, revisar consolas individuales para ver errores: \n"+failedFileNames.toString(), "Análisis no completado", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_analyzeAllCodeBtnActionPerformed

    private void treeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treeMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_treeMenuItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton analyzeAllCodeBtn;
    private javax.swing.JLabel consoleOutputLabel;
    private javax.swing.JLabel fileStatusLabel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton newFileBtn;
    private javax.swing.JButton openFileBtn;
    private javax.swing.JMenu reportsMenu;
    private javax.swing.JButton runCodeBtn;
    private javax.swing.JButton saveAsFileBtn;
    private javax.swing.JButton saveFileBtn;
    private javax.swing.JMenuItem symbolsTableMenuItem;
    private javax.swing.JMenuItem treeMenuItem;
    // End of variables declaration//GEN-END:variables
}
