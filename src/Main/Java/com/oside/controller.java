package com.oside;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {
    @FXML
    private TextArea editor;
    @FXML
    private TextArea console;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void newProject() {
        editor.clear();
        console.appendText("Novo projeto criado.\n");
    }

    @FXML
    private void openProject() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de código", "*.c", "*.cpp", "*.cs", "*.asm", "*.txt"));
        File file = chooser.showOpenDialog(stage);
        if(file != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                editor.clear();
                String line;
                while((line = reader.readLine()) != null) {
                    editor.appendText(line + "\n");
                }
                reader.close();
                console.appendText("Arquivo aberto: " + file.getName() + "\n");
            } catch(IOException e) {
                console.appendText("Erro ao abrir arquivo.\n");
            }
        }
    }

    @FXML
    private void saveProject() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(stage);
        if(file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(editor.getText());
                writer.close();
                console.appendText("Projeto salvo: " + file.getName() + "\n");
            } catch(IOException e) {
                console.appendText("Erro ao salvar arquivo.\n");
            }
        }
    }

    @FXML
    private void generateOutput() {
        // Aqui vai chamar o backend em C/C++/C# futuramente
        console.appendText("Gerando arquivo final (.zip ou .iso)...\n");
    }
            }
