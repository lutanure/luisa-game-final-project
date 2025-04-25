package com.comp301.a08dungeon.controller;

import com.comp301.a08dungeon.model.Model;

public class ControllerImpl implements Controller {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  } 

  public void moveUp(){
    model.moveUp();
  }
  public void moveDown(){
    model.moveDown();
  }
  public void moveRight(){
    model.moveRight();
  }
  public void moveLeft(){
    model.moveLeft();
  }

  public void startGame(){
    model.startGame();
  }
}
