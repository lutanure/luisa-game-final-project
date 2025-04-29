package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.Piece;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    int cellSize = 600 / cols;

    Pane board = new Pane();
    board.setPrefSize(600, 600);
    board.setPadding(new Insets(10));


    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Posn p = new Posn(r, c);
        Piece piece = model.get(p);
        Node cell;  
        if (piece == null) {
          cell = new Label();
        } else {
          String resPath = piece.getResourcePath();
          URL url = getClass().getResource(resPath);
          if (url == null) {
            throw new IllegalStateException();
          }
          Image img = new Image(url.toExternalForm());
          ImageView iv = new ImageView(img);
          iv.setPreserveRatio(true);
          iv.setFitWidth(cellSize);
          iv.setFitHeight(cellSize);
          cell = iv;
        }
        cell.setLayoutX(c * cellSize);
        cell.setLayoutY(r * cellSize);
        board.getChildren().add(cell);
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

    Label scoreLabel = new Label("score: " + model.getCurScore());
    scoreLabel.getStyleClass().add("score-label");

    Label levLabel = new Label("level: " + model.getLevel());
    levLabel.getStyleClass().add("level-label");

    GridPane setup = new GridPane();
    setup.setHgap(5);
    setup.setVgap(5);
    setup.add(up,    1, 0);
    setup.add(left,  0, 1);
    setup.add(down,  1, 2);
    setup.add(right, 2, 1);
    setup.setPadding(new Insets(10));
    setup.getStyleClass().add("controls");


    HBox bottom = new HBox(20, scoreLabel, levLabel, setup);
    bottom.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setCenter(board);
    root.setBottom(bottom);
    root.setPrefSize(600, 600 + 60);
    root.getStyleClass().add("game-view");
    return root;
  }
}
