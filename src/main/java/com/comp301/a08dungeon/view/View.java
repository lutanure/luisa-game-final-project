package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.Observer;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class View implements FXComponent, Observer {
  private final Model model;
  private final Controller controller;
  private final Stage stage;
  
  public View(Controller controller, Model model, Stage stage, int windowWidth, int windowHeight) {
    this.model = model;
    this.controller = controller;
    this.stage = stage;
  }


  public Parent render() {
    switch (model.getStatus()) {
      case END_GAME:
        return new TitleScreenView(controller, model).render();
      case IN_PROGRESS:
      default:
        return new GameView(controller, model).render();
    }
  }

  @Override
  public void update() {
    stage.getScene().setRoot(render());
  }
}
