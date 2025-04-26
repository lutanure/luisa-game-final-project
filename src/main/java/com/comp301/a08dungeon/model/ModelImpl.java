package com.comp301.a08dungeon.model;

import java.util.ArrayList;
import java.util.List;

import com.comp301.a08dungeon.model.board.Board;
import com.comp301.a08dungeon.model.board.BoardImpl;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.CollisionResult;
import com.comp301.a08dungeon.model.pieces.Piece;

public class ModelImpl implements Model {
  private Board board;
  private int curScore;
  private int highScore;
  private int level;
  private STATUS status;
  private List<Observer> observers;

  public ModelImpl(int width, int height) {
    this.board = new BoardImpl(width, height);
    this.curScore = 0;
    this.highScore = 0;
    this.level = 0;
    this.status = STATUS.END_GAME;
    this.observers = new ArrayList<>();
  }

  public ModelImpl(Board board) {
    this.board = board;
    this.curScore = 0;
    this.highScore = 0;
    this.level = 1;
    this.status = STATUS.IN_PROGRESS;
    this.observers = new ArrayList<>();
  }

  @Override
  public int getWidth() {
    return board.getWidth();
  }

  @Override
  public int getHeight() {
    return board.getHeight();
  }

  @Override
  public Piece get(Posn p) {
    return board.get(p);
  }

  @Override
  public int getCurScore() {
    return curScore;
  }

  @Override
  public int getHighScore() {
    return highScore;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public STATUS getStatus() {
    return status;
  }

  @Override
  public void startGame() {
    this.curScore = 0;
    this.level = 1;
    this.status = STATUS.IN_PROGRESS;

    try {
      board.init(level + 1, 2, 2);
    } catch (IllegalArgumentException e) {
      endGame();
    }

    notifyObservers();
  }

  @Override
  public void endGame() {
    this.status = STATUS.END_GAME;
    if (curScore > highScore) {
      highScore = curScore;
    }
    notifyObservers();
  }

  private void moveHero(int dRow, int dCol) {
    if (status != STATUS.IN_PROGRESS) {
      return;
    }

    CollisionResult result = board.moveHero(dRow, dCol);

    curScore += result.getPoints();

    switch (result.getResults()) {
      case GAME_OVER:
        endGame();
        break;
      case NEXT_LEVEL:
        level++;
        try {
          board.init(level + 1, 2, 2);
        } catch (IllegalArgumentException e) {
          endGame();
        }
        break;
      case CONTINUE:
        break;
    }

    notifyObservers();
  }

  @Override
  public void moveUp() {
    moveHero(-1, 0);
  }

  @Override
  public void moveDown() {
    moveHero(1, 0);
  }

  @Override
  public void moveLeft() {
    moveHero(0, -1);
  }

  @Override
  public void moveRight() {
    moveHero(0, 1);
  }

  @Override
  public void addObserver(Observer o) {
    if (o != null && !observers.contains(o)) {
      observers.add(o);
    }
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}