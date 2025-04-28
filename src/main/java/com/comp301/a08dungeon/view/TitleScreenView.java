package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TitleScreenView implements FXComponent {
  private final Controller controller;
  private final Model model;

  public TitleScreenView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    Label title = new Label("Val the Valkyrie");
    title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

    Label highScore = new Label("high score: " + model.getHighScore());
    Label lastScore = new Label("last score:  " + model.getCurScore());

    Button startBtn = new Button("start game");
    startBtn.setOnAction(e -> controller.startGame());

    Label credit = new Label("by lu");

    VBox layout = new VBox(15, title, highScore, lastScore, startBtn, credit);
    layout.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(layout);
    root.getStyleClass().add("title-screen");
    return root;
  }
}

