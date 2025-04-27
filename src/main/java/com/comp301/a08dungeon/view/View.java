package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View implements FXComponent, Observer {
  private final Controller controller;
  private final Model model;
  private final Stage stage;
  private final int windowWidth;
  private final int windowHeight;
  
  public View(Controller controller, Model model, Stage stage, int windowWidth, int windowHeight) {
    this.controller = controller;
    this.model = model;
    this.stage = stage;
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
  }


  public Parent render() {
    Pane s = new StackPane();
    s.getChildren().add(new Label("Hello, World"));
    return s;
  }

  @Override
  public void update() {
    Scene scene = new Scene(render(), windowWidth, windowHeight);
    scene.getStylesheets().add("dungeon.css");
    stage.setScene(scene);
  }
}
