import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RatInMaze extends JFrame {
    private static final int SIZE = 4; // Size of the maze

    private int[][] maze = {
            {0, 1, 1, 1},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {1, 0, 0, 0}
    };

    private int[][] solution = new int[SIZE][SIZE];
    private JButton solveButton;
    private JPanel mazePanel;

    public RatInMaze() {
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        solveButton = new JButton("Solve Maze");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveMaze();
            }
        });

        mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(SIZE, SIZE));

        add(solveButton, BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);

        initializeMazePanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeMazePanel() {
        mazePanel.removeAll();
        mazePanel.setPreferredSize(new Dimension(300, 300));
        mazePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBackground(maze[i][j] == 1 ? Color.BLACK : Color.WHITE);
                mazePanel.add(cellPanel);
            }
        }

        revalidate();
        repaint();
    }

    private void solveMaze() {
        if (solveMazeRecursive(0, 0)) {
            System.out.println("Maze solution found:");
            printSolution();
            updateMazePanel();
        } else {
            System.out.println("No solution exists for the given maze.");
        }
    }

    private boolean solveMazeRecursive(int row, int col) {
        if (row == SIZE - 1 && col == SIZE - 1) {
            solution[row][col] = 1; // Mark the destination cell as part of the path
            return true;
        }

        if (isValidMove(row, col)) {
            solution[row][col] = 1; // Mark the current cell as part of the path

            // Move down
            if (solveMazeRecursive(row + 1, col)) {
                return true;
            }

            // Move right
            if (solveMazeRecursive(row, col + 1)) {
                return true;
            }

            // If moving down and right is not possible, backtrack
            solution[row][col] = 0; // Mark the current cell as not part of the path
        }

        return false;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && maze[row][col] == 0;
    }

    private void printSolution() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void updateMazePanel() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JPanel cellPanel = (JPanel) mazePanel.getComponent(i * SIZE + j);
                cellPanel.setBackground(solution[i][j] == 1 ? Color.GREEN : (maze[i][j] == 1 ? Color.BLACK : Color.WHITE));
            }
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RatInMaze();
            }
        });
    }
}