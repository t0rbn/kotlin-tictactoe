package xyz.torben.player;

import org.jetbrains.annotations.NotNull;
import xyz.torben.board.Board;
import xyz.torben.board.BoardFieldValue;
import xyz.torben.board.Coordinate;
import xyz.torben.board.Field;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GuiPlayer implements Player {
    private BoardFieldValue myPlayerValue;
    private boolean isWaitingForClick;
    private int nextActionX;
    private int nextActionY;

    private JFrame frame;
    private JPanel panel1;
    private JButton button02;
    private JButton button01;
    private JButton button00;
    private JButton button10;
    private JButton button20;
    private JButton button11;
    private JButton button21;
    private JButton button12;
    private JButton button22;

    public GuiPlayer(BoardFieldValue myPlayerValue) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        this.myPlayerValue = myPlayerValue;
        this.isWaitingForClick = false;
        this.initEventListeners();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void registerClick(int x, int y) {
        this.nextActionX = x;
        this.nextActionY = y;
        this.isWaitingForClick = false;
    }

    private void updateButtonLabels(Board board) {
        button00.setText(board.getField(new Coordinate(0, 0)).getValue().getPrintableString());
        button01.setText(board.getField(new Coordinate(0, 1)).getValue().getPrintableString());
        button02.setText(board.getField(new Coordinate(0, 2)).getValue().getPrintableString());
        button10.setText(board.getField(new Coordinate(1, 0)).getValue().getPrintableString());
        button11.setText(board.getField(new Coordinate(1, 1)).getValue().getPrintableString());
        button12.setText(board.getField(new Coordinate(1, 2)).getValue().getPrintableString());
        button20.setText(board.getField(new Coordinate(2, 0)).getValue().getPrintableString());
        button21.setText(board.getField(new Coordinate(2, 1)).getValue().getPrintableString());
        button22.setText(board.getField(new Coordinate(2, 2)).getValue().getPrintableString());
    }

    private ActionListener generateButtonActionListener(int x, int y) {
        return actionEvent -> registerClick(x, y);
    }

    private void initEventListeners() {
        button00.addActionListener(generateButtonActionListener(0, 0));
        button01.addActionListener(generateButtonActionListener(0, 1));
        button02.addActionListener(generateButtonActionListener(0, 2));
        button10.addActionListener(generateButtonActionListener(1, 0));
        button11.addActionListener(generateButtonActionListener(1, 1));
        button12.addActionListener(generateButtonActionListener(1, 2));
        button20.addActionListener(generateButtonActionListener(2, 0));
        button21.addActionListener(generateButtonActionListener(2, 1));
        button22.addActionListener(generateButtonActionListener(2, 2));
    }

    @Override
    public void makeMove(@NotNull Board board) {
        updateButtonLabels(board);
        this.isWaitingForClick = true;
        while(isWaitingForClick) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        board.setField(new Field(new Coordinate(nextActionX, nextActionY), myPlayerValue));
    }

    @Override
    public void informWinner(@NotNull BoardFieldValue winner) {
        String winnerMessage = winner == BoardFieldValue.NONE
                                   ? "Tie!"
                                   : winner.getPrintableString() + " wins!";
        JOptionPane.showMessageDialog(frame, winnerMessage);
        frame.setVisible(false);
    }
}
