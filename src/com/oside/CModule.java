package com.oside;

import java.io.*;

public class CModule {

    public static String compile(String sourceFile, String outputFile) {
        return runCompiler("gcc", sourceFile, "-o", outputFile);
    }

    private static String runCompiler(String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString();
        } catch (Exception e) {
            return "Erro ao compilar C: " + e.getMessage();
        }
    }
}
