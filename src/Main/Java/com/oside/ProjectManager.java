package com.oside;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectManager {

    private Path projectPath;

    public ProjectManager() {
        projectPath = null;
    }

    // Cria novo projeto em um diretório especificado
    public void newProject(String folderPath) throws IOException {
        projectPath = Paths.get(folderPath);
        if (!Files.exists(projectPath)) {
            Files.createDirectories(projectPath);
        }
        // Cria pastas básicas
        Files.createDirectories(projectPath.resolve("src"));
        Files.createDirectories(projectPath.resolve("bin"));
        Files.createDirectories(projectPath.resolve("assets"));
        System.out.println("Novo projeto criado em: " + projectPath.toString());
    }

    // Salva conteúdo de um arquivo dentro do projeto
    public void saveFile(String fileName, String content) throws IOException {
        if (projectPath == null) throw new IOException("Nenhum projeto aberto!");
        Path filePath = projectPath.resolve("src").resolve(fileName);
        Files.write(filePath, content.getBytes());
        System.out.println("Arquivo salvo: " + filePath.toString());
    }

    // Abre arquivo dentro do projeto
    public String openFile(String fileName) throws IOException {
        if (projectPath == null) throw new IOException("Nenhum projeto aberto!");
        Path filePath = projectPath.resolve("src").resolve(fileName);
        if (!Files.exists(filePath)) throw new FileNotFoundException("Arquivo não encontrado: " + fileName);
        return new String(Files.readAllBytes(filePath));
    }

    // Gera arquivo .zip do projeto completo
    public void generateZip(String outputFileName) throws IOException {
        if (projectPath == null) throw new IOException("Nenhum projeto aberto!");
        Path zipPath = projectPath.resolve(outputFileName);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
            Files.walk(projectPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry entry = new ZipEntry(projectPath.relativize(path).toString());
                        try {
                            zos.putNextEntry(entry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        System.out.println("Arquivo zip gerado: " + zipPath.toString());
    }

    // Configura o caminho do projeto já existente
    public void openProject(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) throw new FileNotFoundException("Projeto não encontrado!");
        projectPath = path;
        System.out.println("Projeto aberto: " + projectPath.toString());
    }

    public Path getProjectPath() {
        return projectPath;
    }
          }
