package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.Piece;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;  

public class GameView implements FXComponent{
  private final Controller controller;
  private final Model model;

  public GameView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    int rows = model.getHeight();
    int cols = model.getWidth();

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10));
    grid.setHgap(0);
    grid.setVgap(0);

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Posn p = new Posn(r, c);
        Piece piece = model.get(p);
        Node cell;  
        if (piece == null) {
          cell = new Label(" ");
        } else {
          String resPath = piece.getResourcePath();
          URL url = getClass().getResource(resPath);
          if (url == null) {
            throw new IllegalStateException();
          }
          Image img = new Image(url.toExternalForm());
          ImageView iv = new ImageView(img);
          iv.setFitWidth(40);
          iv.setFitHeight(40);
          cell = iv;
          iv.setFitWidth(40);
          iv.setFitHeight(40);
          cell = iv;
        }
        grid.add(cell, c, r);
        GridPane.setHalignment(cell, HPos.CENTER);
        GridPane.setValignment(cell, VPos.CENTER);
      }
    }

    Button up = new Button("↑");
    Button down = new Button("↓");
    Button left = new Button("←");
    Button right = new Button("→");
    up.setOnAction(e -> controller.moveUp());
    down.setOnAction(e -> controller.moveDown());
    left.setOnAction(e -> controller.moveLeft());
    right.setOnAction(e -> controller.moveRight());

    HBox controls = new HBox(10, left, up, down, right);
    controls.setPadding(new Insets(10));
    controls.getStyleClass().add("controls");

    Label scoreLabel = new Label("score: " + model.getCurScore());
    scoreLabel.getStyleClass().add("score-label");

    Label levLabel = new Label("level: " + model.getLevel());
    levLabel.getStyleClass().add("level-label");


    HBox bottom = new HBox(20, scoreLabel, controls);
    bottom.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setCenter(grid);
    root.setBottom(bottom);
    root.getStyleClass().add("game-view");
    return root;
  }
}
