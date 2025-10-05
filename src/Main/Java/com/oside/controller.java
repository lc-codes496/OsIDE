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
    private ProjectManager projectManager;
    private String currentFileName;
    private String currentLanguage; // "C", "CPP", "CS"

    public void setStage(Stage stage) {
        this.stage = stage;
        projectManager = new ProjectManager();
    }

    @FXML
    private void newProject() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Escolha a pasta do novo projeto");
            chooser.setInitialFileName("");
            File folder = chooser.showSaveDialog(stage);
            if(folder != null) {
                projectManager.newProject(folder.getAbsolutePath());
                editor.clear();
                console.appendText("Novo projeto criado em: " + folder.getAbsolutePath() + "\n");
            }
        } catch (IOException e) {
            console.appendText("Erro ao criar novo projeto: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void openProject() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Abrir projeto");
            File file = chooser.showOpenDialog(stage);
            if(file != null) {
                projectManager.openProject(file.getParent());
                editor.setText(projectManager.openFile(file.getName()));
                currentFileName = file.getName();
                console.appendText("Projeto aberto: " + file.getParent() + "\n");
            }
        } catch (IOException e) {
            console.appendText("Erro ao abrir projeto: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void saveProject() {
        try {
            if(currentFileName == null) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Salvar arquivo como");
                File file = chooser.showSaveDialog(stage);
                if(file != null) currentFileName = file.getName();
            }
            if(currentFileName != null) {
                projectManager.saveFile(currentFileName, editor.getText());
                console.appendText("Arquivo salvo: " + currentFileName + "\n");
            }
        } catch (IOException e) {
            console.appendText("Erro ao salvar arquivo: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void compileC() {
        compile("C");
    }

    @FXML
    private void compileCpp() {
        compile("CPP");
    }

    @FXML
    private void compileCSharp() {
        compile("CS");
    }

    private void compile(String language) {
        try {
            if(currentFileName == null) {
                console.appendText("Nenhum arquivo selecionado.\n");
                return;
            }

            String sourcePath = projectManager.getProjectPath().resolve("src").resolve(currentFileName).toString();
            String outputPath = projectManager.getProjectPath().resolve("bin").resolve("output.exe").toString();
            String result;

            switch(language) {
                case "C":
                    result = CModule.compile(sourcePath, outputPath);
                    break;
                case "CPP":
                    result = CppModule.compile(sourcePath, outputPath);
                    break;
                case "CS":
                    result = CSharpModule.compile(sourcePath, outputPath);
                    break;
                default:
                    result = "Linguagem desconhecida!";
            }

            console.appendText(result + "\n");
        } catch(Exception e) {
            console.appendText("Erro ao compilar: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void generateOutput() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Salvar arquivo final (.zip)");
            chooser.setInitialFileName("output.zip");
            File file = chooser.showSaveDialog(stage);
            if(file != null) {
                projectManager.generateZip(file.getName());
                console.appendText("Arquivo final gerado: " + file.getAbsolutePath() + "\n");
            }
        } catch(IOException e) {
            console.appendText("Erro ao gerar arquivo: " + e.getMessage() + "\n");
        }
    }
                                        }
