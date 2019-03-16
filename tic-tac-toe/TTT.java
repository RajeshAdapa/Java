package ex;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TTT {

  JFrame frame;
  JTable board;
  TableModel model;
  JLabel title;

  int user = 0;
  int n = 3;
  int total = 0;

  String[] values = { "X", "O" };

  public TTT() {

    frame = new JFrame();
    frame.setLayout(new FlowLayout());
    frame.setSize(500, 500);
    frame.setVisible(true);

    title = new JLabel("TIC TAC TOE");

    model = new DefaultTableModel(n, n) {

      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    board = new JTable(model);
    board.setRowHeight(70);
    board.setRowSelectionAllowed(false);

    Font font = new Font("comic sans ms", Font.BOLD, 40);
    board.setFont(font);

    font = font.deriveFont(40);
    title.setFont(font);
    title.setForeground(new Color(255, 99, 61));

    board.addMouseListener(new MouseAdapter() {

      public void mouseClicked(MouseEvent me) {

        if (me.getClickCount() == 2) {

          int rowIndex = board.getSelectedRow();
          int columnIndex = board.getSelectedColumn();

          markPosition(rowIndex, columnIndex);
        }
      }

    });

    frame.add(title);
    frame.add(board);
  }

  public void markPosition(int rowIndex, int columnIndex) {

    Object value = board.getValueAt(rowIndex, columnIndex);

    if (value == null) {

      board.setValueAt(" " + values[user], rowIndex, columnIndex);
      if (checkBoard(rowIndex, columnIndex)) {
        user++;
        JOptionPane.showMessageDialog(null, "User " + user + " wins");
        frame.dispose();
        return;
      }

      user = (user == 0) ? 1 : 0;

      if (total == (n * n) - 1) {

        int input = JOptionPane.showConfirmDialog(null, "Game Draw! Want to play again?");
        if (input == 0) {

          total = 0;
          user = 0;

          for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
              board.setValueAt("", i, j);
        }

        else if (input == 1 || input == 2) {
          frame.dispose();
        }
      }

      total++;

    } else {

      JOptionPane.showMessageDialog(null, "Wrong Position");
    }

  }

  private boolean checkBoard(int rIndex, int cIndex) {

    int flag = 0;

    // Row

    for (int i = 0; i < n; i++) {

      Object val = board.getValueAt(rIndex, i);

      if (val == null || !values[user].equalsIgnoreCase(((String) val).trim())) {
        flag = 1;
        break;
      }
    }

    if (flag == 0)
      return true;
    else
      flag = 0;

    // Column

    for (int i = 0; i < n; i++) {

      Object val = board.getValueAt(i, cIndex);

      if (val == null || !values[user].equalsIgnoreCase(((String) val).trim())) {
        flag = 1;
        break;
      }
    }

    if (flag == 0)
      return true;
    else
      flag = 0;

    // LtoR

    for (int i = 0; i < n; i++) {

      Object val = board.getValueAt(i, i);

      if (val == null || !values[user].equalsIgnoreCase(((String) val).trim())) {
        flag = 1;
        break;
      }
    }

    if (flag == 0)
      return true;
    else
      flag = 0;

    // RtoL

    for (int i = 0; i < n; i++) {

      for (int j = 0; j < n; j++) {

        if (i + j == n - 1) {
          Object val = board.getValueAt(i, j);

          if (val == null || !values[user].equalsIgnoreCase(((String) val).trim())) {
            flag = 1;
            break;
          }
        }
      }
    }

    if (flag == 0)
      return true;
    else
      flag = 0;

    return false;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        new TTT();
      }
    });

  }

}
