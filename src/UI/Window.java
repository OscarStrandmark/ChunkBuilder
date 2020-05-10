package UI;

import Logic.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

    private Controller controller;

    private JLabel[][] lblMatrix = new JLabel[10][10];

    private JPanel contentPane;
    private JPanel matrixPane;
    private JPanel controlPane;

    private JButton btnRock;
    private JButton btnAir;
    private JButton btnEntrance;
    private JButton btnExit;
    private JButton btnArrowtrapLeft;
    private JButton btnArrowtrapRight;
    private JButton btnSpikes;

    private JLabel lblSelected;

    private JButton btnSave;
    private JButton btnClear;

    private JCheckBox cboxUp;
    private JCheckBox cboxDown;
    private JCheckBox cboxLeft;
    private JCheckBox cboxRight;
    private JCheckBox cboxEntrance;
    private JCheckBox cboxExit;

    public Window() throws IOException {

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        setSize(950,681);
        setResizable(false);
        setVisible(true);
        setEnabled(true);
        setTitle("Chunkbuilder");
        setIconImage(ImageIO.read(new File("images/Idol.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        matrixPane = new JPanel(new GridLayout(10,10));
        matrixPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        matrixPane.setMinimumSize(new Dimension(640,640));
        matrixPane.setMaximumSize(new Dimension(640,640));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                ImageIcon img = new ImageIcon("images/Air.png");
                JLabel lbl = new JLabel(img);
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                lblMatrix[row][col] = lbl;
                matrixPane.add(lbl);
                lbl.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lbl.setIcon(new ImageIcon(lblSelected.getIcon().toString()));
                    }
                });
            }
        }

        contentPane.add(matrixPane,BorderLayout.WEST);
        contentPane.revalidate();
        contentPane.repaint();

        controlPane = new JPanel(new BorderLayout());

        JPanel btnPanel = new JPanel(new GridLayout(3,3));

        //Buttons for tiles
        btnRock = new JButton(new ImageIcon("images/Rock.png"));
        btnAir = new JButton(new ImageIcon("images/Air.png"));
        btnEntrance = new JButton(new ImageIcon("images/Entrance.png"));
        btnExit = new JButton(new ImageIcon("images/Exit.png"));
        btnArrowtrapLeft = new JButton(new ImageIcon("images/ArrowtrapLeft.png"));
        btnArrowtrapRight = new JButton(new ImageIcon("images/ArrowtrapRight.png"));
        btnSpikes = new JButton(new ImageIcon("images/Spikes.png"));
        btnPanel.add(btnAir);
        btnPanel.add(btnRock);
        btnPanel.add(btnEntrance);
        btnPanel.add(btnExit);
        btnPanel.add(btnArrowtrapLeft);
        btnPanel.add(btnArrowtrapRight);
        btnPanel.add(btnSpikes);

        controlPane.add(btnPanel,BorderLayout.NORTH);
        contentPane.add(controlPane, BorderLayout.CENTER);

        //View for selected tile
        JPanel selectedTilePane = new JPanel();
        selectedTilePane.add(new JLabel("Selected Tile:"));
        lblSelected = new JLabel(new ImageIcon("images/Air.png"));
        selectedTilePane.add(lblSelected);

        //controlPane.add(selectedTilePane,BorderLayout.CENTER);

        //Save and Clear buttons
        btnSave = new JButton("Save to file");
        btnClear = new JButton("Clear placed tiles");
        JPanel southBtnPane = new JPanel();
        southBtnPane.add(btnSave);
        southBtnPane.add(btnClear);
        controlPane.add(southBtnPane,BorderLayout.SOUTH);

        //Checkboxes for entrances
        JPanel cboxPane = new JPanel(new GridLayout(3,3));
        cboxUp = new JCheckBox("Up");
        cboxDown = new JCheckBox("Down");
        cboxLeft = new JCheckBox("Left");
        cboxRight = new JCheckBox("Right");
        cboxEntrance = new JCheckBox("Entrance");
        cboxExit = new JCheckBox("Exit");
        cboxPane.add(cboxEntrance);
        cboxPane.add(cboxUp);
        cboxPane.add(cboxExit);
        cboxPane.add(cboxLeft);
        cboxPane.add(new JLabel(""));
        cboxPane.add(cboxRight);
        cboxPane.add(new JLabel(""));
        cboxPane.add(cboxDown);
        cboxPane.add(new JLabel(""));

        //Control panel being weird
        JPanel centerLeftPane = new JPanel(new BorderLayout());
        centerLeftPane.add(selectedTilePane,BorderLayout.CENTER);
        centerLeftPane.add(cboxPane,BorderLayout.SOUTH);
        controlPane.add(centerLeftPane,BorderLayout.CENTER);

        controlPane.revalidate();

        //Add listeners to buttons
        ButtonListener buttonListener = new ButtonListener();
        btnRock.addActionListener(buttonListener);
        btnAir.addActionListener(buttonListener);
        btnEntrance.addActionListener(buttonListener);
        btnExit.addActionListener(buttonListener);
        btnArrowtrapLeft.addActionListener(buttonListener);
        btnArrowtrapRight.addActionListener(buttonListener);
        btnSpikes.addActionListener(buttonListener);

        btnSave.addActionListener(buttonListener);
        btnClear.addActionListener(buttonListener);

    }

    public void setController(Controller c) {
        controller = c;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnSave || e.getSource() == btnClear){
                if(e.getSource() == btnSave){
                    //Get tile properties and send to controller for translation and saving to file
                    boolean up       = cboxUp.isSelected();
                    boolean down     = cboxDown.isSelected();
                    boolean left     = cboxLeft.isSelected();
                    boolean right    = cboxRight.isSelected();
                    boolean entrance = cboxEntrance.isSelected();
                    boolean exit     = cboxExit.isSelected();
                    try {
                        controller.saveChunk(lblMatrix,up,down,left,right,entrance,exit);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if(e.getSource() == btnClear){
                    for (int row = 0; row < 10; row++) {
                        for (int col = 0; col < 10; col++) {
                            lblMatrix[row][col].setIcon(new ImageIcon("images/Air.png"));
                        }
                    }
                }
            } else {
                //Set icon of clicked square to same as selected sprite
                JButton btnPressed = (JButton) e.getSource();
                String btnImagePath = btnPressed.getIcon().toString();
                lblSelected.setIcon(new ImageIcon(btnPressed.getIcon().toString()));
            }
        }
    }
}
