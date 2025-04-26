package com.comp301.a08dungeon.model.pieces;

public class Hero extends APiece implements MovablePiece {
  public Hero() {
    super("Hero", "resources/hero.png");
  }

  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }

    if (other instanceof Treasure) {
      Treasure treasure = (Treasure) other;
      return new CollisionResult(treasure.getValue(), CollisionResult.Result.CONTINUE);
    }

    if (other instanceof Enemy) {
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    }

    if (other instanceof Exit) {
      return new CollisionResult(0, CollisionResult.Result.NEXT_LEVEL);
    }

    throw new IllegalArgumentException();
  }
}
