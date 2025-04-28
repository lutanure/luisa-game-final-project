package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.ModelImpl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("val the valkyrie");
    
    Model model = new ModelImpl(10, 10);
    ControllerImpl controller = new ControllerImpl(model);
    View view = new View(controller, model, stage, 600, 600);
    Scene scene = new Scene(view.render(), 600, 600);

    scene.getStylesheets().add("dungeon.css");
    stage.setScene(scene);
    stage.show();
    model.addObserver(view);

  }
}
