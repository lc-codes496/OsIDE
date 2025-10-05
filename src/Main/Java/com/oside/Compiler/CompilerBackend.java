public class CompilerBackend {

    public static String compileC(String sourceFile, String outputFile) {
        return runCompiler("gcc", sourceFile, "-o", outputFile);
    }

    public static String compileCpp(String sourceFile, String outputFile) {
        return runCompiler("g++", sourceFile, "-o", outputFile);
    }

    public static String compileCSharp(String sourceFile, String outputFile) {
        return runCompiler("csc", sourceFile, "/out:" + outputFile);
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
            return "Erro ao compilar: " + e.getMessage();
        }
    }
}
