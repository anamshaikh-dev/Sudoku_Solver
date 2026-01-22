package com.example.sudokusolver;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText[][] cells = new EditText[9][9];
    GridLayout gridLayout;
    Button solveBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        solveBtn = findViewById(R.id.solveBtn);
        clearBtn = findViewById(R.id.clearBtn);

        createSudokuGrid();

        solveBtn.setOnClickListener(v -> solveSudoku());
        clearBtn.setOnClickListener(v -> clearGrid());
    }

    private void createSudokuGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                EditText cell = new EditText(this);
                cell.setGravity(Gravity.CENTER);
                cell.setTextSize(18);
                cell.setInputType(InputType.TYPE_CLASS_NUMBER);
                cell.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});


                cell.setTextColor(getResources().getColor(android.R.color.black));


                int boxRow = row / 3;
                int boxCol = col / 3;
                if ((boxRow + boxCol) % 2 == 0) {
                    cell.setBackgroundColor(getResources().getColor(R.color.boxLight));
                } else {
                    cell.setBackgroundColor(getResources().getColor(R.color.boxDark));
                }


                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 120;
                params.columnSpec = GridLayout.spec(col, 1f);
                params.rowSpec = GridLayout.spec(row, 1f);

                int left = (col % 3 == 0) ? 4 : 2;
                int top = (row % 3 == 0) ? 4 : 2;
                int right = 2;
                int bottom = 2;

                params.setMargins(left, top, right, bottom);

                cell.setLayoutParams(params);
                gridLayout.addView(cell);
                cells[row][col] = cell;
            }
        }
    }

    private void solveSudoku() {
        int[][] board = new int[9][9];


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText().toString();
                board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }


        if (solve(board)) {

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                }
            }
        } else {
            Toast.makeText(this, "No solution found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {


        for (int x = 0; x < 9; x++)
            if (board[row][x] == num) return false;


        for (int x = 0; x < 9; x++)
            if (board[x][col] == num) return false;


        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                cells[i][j].setText("");
    }
}