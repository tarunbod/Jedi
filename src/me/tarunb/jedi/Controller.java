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

package me.tarunb.jedi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private List<Conversion> conversions = new ArrayList<>();

    @FXML
    private Hyperlink link;

    @FXML
    private ListView filesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void openLink(ActionEvent e) throws Exception {
        Desktop.getDesktop().browse(new URL("http://github.com/tarunbod/Jedi").toURI());
    }

    @FXML
    public void addFiles() {
        FileChooser chooser = new FileChooser();
        chooser.setSelectedExtensionFilter(new ExtensionFilter("Media Types", Utils.FILE_EXTS));
        List<File> chosen = chooser.showOpenMultipleDialog(null);
        for (File f : chosen) {
            TextInputDialog convert = new TextInputDialog(f.getName());
            convert.setHeaderText("Output file name");
            convert.setContentText("Enter the name of the \noutput file (including file extension) for \"" + f.getName() + "\":\n");
            String outputName = convert.showAndWait().orElse(f.getName());
            conversions.add(new Conversion(f.getAbsolutePath(), outputName));
        }
        System.out.println(conversions);
    }

    @FXML
    public void clearFiles() {
        conversions.clear();
    }

    @FXML
    public void convertFiles() {
        new Alert(Alert.AlertType.CONFIRMATION, "You will be asked to choose an output directory.", ButtonType.OK).showAndWait();
        DirectoryChooser chooser = new DirectoryChooser();
        File outputDir = chooser.showDialog(null);
        for (Conversion c : conversions) {
            ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/ffmpeg", "-i", c.getInputFile(), new File(outputDir, c.getOutputFile()).getAbsolutePath());
            try {
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred converting file " + c.getOutputFile(), ButtonType.OK).showAndWait();
            }
        }
        ButtonType response = new Alert(Alert.AlertType.INFORMATION, "All conversions finished. Check directory " + outputDir.getName() + ".", new ButtonType("Open"), ButtonType.OK).showAndWait().orElse(ButtonType.OK);
        if (response.getText().equals("Open")) {
            try {
                Desktop.getDesktop().browse(new URI(outputDir.getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
