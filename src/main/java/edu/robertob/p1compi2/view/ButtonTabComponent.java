package edu.robertob.p1compi2.view;

import edu.robertob.p1compi2.data.CurrentSession;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;
    private final CurrentSession currentSession;
    // we'll save the label in this variable so we can update it when the tab save status changes
    private final JLabel label;

    private final JButton button;

    public ButtonTabComponent(final JTabbedPane pane, CurrentSession currentSession) {
        //unset default FlowLayout' gaps

        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        this.currentSession = currentSession;
        setOpaque(false);

        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        this.label = label;

        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        this.button = button;
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    public JButton getButton() {
        return button;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if (label != null) {
            // Calculate the preferred width based on the label's preferred width
            size.width = label.getPreferredSize().width + 20; // Add some padding
        }
        return size;
    }

    public void repaintTabButtonLabel(int index) {
        var title = pane.getTitleAt(index);
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                // if the file isnt saved, we ask the user if they want to save it
                var currentFile = ButtonTabComponent.this.currentSession.getFiles().get(i);
                if (!currentFile.isSaved()) {
                    int result = JOptionPane.showConfirmDialog(null, "Este archivo tiene cambios no guardados, estas seguro que deseas cerrarlo?", "Save file", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result != JOptionPane.YES_OPTION) return;
                }
                ButtonTabComponent.this.currentSession.removeFile(i);
                pane.remove(i);
            }
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
