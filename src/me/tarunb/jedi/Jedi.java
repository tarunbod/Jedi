/**
 * Jedi by Tarun Boddupalli
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Tarun Boddupalli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.javaftw.jedi.application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

;import java.io.IOException;

public class Jedi extends Application {

    static {
        Font.loadFont(Jedi.class.getResourceAsStream("res/fonts/Dosis.ttf"), 72);
    }

    private Text openingText;

    private final int WIDTH = 460, HEIGHT = 320;

    @Override
    public void start(Stage stage) throws Exception {
        init(stage);
        StackPane pane = new StackPane(openingText);
        stage.setResizable(false);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("Jedi");
        stage.setScene(new Scene(pane));
        stage.show();
    }


    public void init(final Stage stage) throws Exception {
        openingText = new Text("Jedi");
        openingText.scaleXProperty().bindBidirectional(openingText.scaleYProperty());
        openingText.setFill(Color.DARKGREEN);
        openingText.setFont(new Font("Dosis", 72));
        Timeline anim = new Timeline();
        anim.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(openingText.scaleXProperty(), 0)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(openingText.scaleXProperty(), 0)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(600 + 500), new KeyValue(openingText.scaleXProperty(), 0.3)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(650 + 500), new KeyValue(openingText.scaleXProperty(), 1.1)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(800 + 500), new KeyValue(openingText.scaleXProperty(), 0.9)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(1000 + 500), new KeyValue(openingText.scaleXProperty(), 1.03)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(1100 + 500), new KeyValue(openingText.scaleXProperty(), 0.97)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(1250 + 500), new KeyValue(openingText.scaleXProperty(), 1)));

        anim.getKeyFrames().add(new KeyFrame(Duration.millis(2000 + 500), new KeyValue(openingText.translateYProperty(), 0)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(2250 + 500), new KeyValue(openingText.translateYProperty(), 20)));
        anim.getKeyFrames().add(new KeyFrame(Duration.millis(2325 + 500), new KeyValue(openingText.translateYProperty(), -500)));
        anim.play();

        anim.setOnFinished(e -> {
            Parent jedi = null;
            try {
                jedi = FXMLLoader.load(getClass().getResource("jedi.fxml"));
            } catch (IOException e1) {}
            stage.setScene(new Scene(jedi));
            System.out.println(stage.getWidth() + ", " + stage.getHeight());
            jedi.setOpacity(0);
            new Timeline(new KeyFrame(Duration.millis(500), new KeyValue(jedi.opacityProperty(), 1))).play();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
