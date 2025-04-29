package com.comp301.a08dungeon.model.board;

import com.comp301.a08dungeon.model.pieces.CollisionResult;
import com.comp301.a08dungeon.model.pieces.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardImpl implements Board {
  private final int width;
  private final int height;
  private final Piece[][] board;
  private Hero hero;

  public BoardImpl(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = new Piece[height][width];
  }

  public BoardImpl(Piece[][] board) {
    this.height = board.length;
    this.width = board[0].length;
    this.board = new Piece[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Piece p = board[r][c];
        this.board[r][c] = p;
        if (p != null) {
          p.setPosn(new Posn(r, c));
          if (p instanceof Hero) {
            hero = (Hero) p;
          }
        }
      }
    }
  }

  @Override
  public void init(int enemies, int treasures, int walls) {
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        board[r][c] = null;
      }
    }
    int totalPieces = 1 + 1 + enemies + treasures + walls;
    int totalSpots = width * height;
    if (totalPieces > totalSpots) {
      throw new IllegalArgumentException();
    }
    List<Posn> available = new ArrayList<>();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        available.add(new Posn(r, c));
      }
    }
    Random rand = new Random();
    Posn heroPos = available.remove(rand.nextInt(available.size()));
    hero = new Hero();
    set(hero, heroPos);
    Posn exitPos = available.remove(rand.nextInt(available.size()));
    set(new Exit(), exitPos);
    for (int i = 0; i < enemies; i++) {
      Posn pos = available.remove(rand.nextInt(available.size()));
      set(new Enemy(), pos);
    }
    for (int i = 0; i < treasures; i++) {
      Posn pos = available.remove(rand.nextInt(available.size()));
      set(new Treasure(), pos);
    }
    for (int i = 0; i < walls; i++) {
      Posn pos = available.remove(rand.nextInt(available.size()));
      set(new Wall(), pos);
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public Piece get(Posn posn) {
    return board[posn.getRow()][posn.getCol()];
  }

  @Override
  public void set(Piece p, Posn newPos) {
    if (p == null || newPos == null) {
      throw new IllegalArgumentException();
    }
    int row = newPos.getRow();
    int col = newPos.getCol();
    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IllegalArgumentException();
    }
    Posn oldPos = p.getPosn();
    if (oldPos != null) {
      board[oldPos.getRow()][oldPos.getCol()] = null;
    }
    board[row][col] = p;
    p.setPosn(newPos);
  }

  @Override
  public CollisionResult moveHero(int drow, int dcol) {
    Posn current = hero.getPosn();
    int newRow = current.getRow() + drow;
    int newCol = current.getCol() + dcol;
    if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }
    Piece destination = board[newRow][newCol];
    if (destination instanceof Wall) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }
    CollisionResult heroResult = hero.collide(destination);
    int totalPoints = heroResult.getPoints();
    if (heroResult.getResults() == CollisionResult.Result.GAME_OVER) {
      return heroResult;
    }
    if (destination instanceof Treasure) {
      board[newRow][newCol] = null;
    }
    if (destination instanceof Exit) {
      return new CollisionResult(0, CollisionResult.Result.NEXT_LEVEL);
    }
    set(hero, new Posn(newRow, newCol));
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Piece p = board[i][j];
        if (p instanceof Enemy) {
          Enemy enemy = (Enemy) p;
          Posn newPos = randomMove(enemy);
          if (newPos != null && !newPos.equals(enemy.getPosn())) {
            Piece target = board[newPos.getRow()][newPos.getCol()];
            if (target instanceof Hero) {
              return new CollisionResult(totalPoints, CollisionResult.Result.GAME_OVER);
            }
            if (target instanceof Treasure) {
              board[newPos.getRow()][newPos.getCol()] = null;
            }
            set(enemy, newPos);
          }
        }
      }
    }
    return new CollisionResult(totalPoints, CollisionResult.Result.CONTINUE);
  }

  private Posn randomMove(Enemy enemy) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    List<Posn> validMoves = new ArrayList<>();
    Posn pos = enemy.getPosn();
    int r = pos.getRow();
    int c = pos.getCol();
    for (int[] dir : directions) {
      int nr = r + dir[0];
      int nc = c + dir[1];
      if (nr < 0 || nr >= height || nc < 0 || nc >= width) continue;
      Piece target = board[nr][nc];
      if (target instanceof Wall || target instanceof Exit || target instanceof Enemy) continue;
      validMoves.add(new Posn(nr, nc));
    }
    if (validMoves.isEmpty()) {
      return null;
    }
    Random rand = new Random();
    return validMoves.get(rand.nextInt(validMoves.size()));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(board[i][j] == null ? "." : board[i][j].getName().charAt(0)).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
