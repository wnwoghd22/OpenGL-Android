package com.eternal.rolly_roll.game.control;

import com.eternal.rolly_roll.game.model.PlayerObject;
import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;

public class Level extends GameObject {
    private int boardSize;

    private Tile[][] board;

    public Tile[][] GenerateBoard(int size) {
        board = new Tile[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                board[i][j] = new Tile(new Vector3D(
                        -(float)(size - 1) / 2.0f + j,
                        0f,
                        -(float)(size - 1) / 2.0f + i
                ));

        // bind tiles
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(i > 0) // if up exists
                    board[i][j].bindUp(board[i - 1][j]);
                if(i < size - 1) // if down exists
                    board[i][j].bindDown(board[i + 1][j]);
                if(j > 0) // if left exists
                    board[i][j].bindLeft(board[i][j - 1]);
                if(j < size - 1) // if right exists
                    board[i][j].bindRight(board[i][j + 1]);
            }
        }

        return board;
    }

    @Override
    public void Update() {

    }
}
