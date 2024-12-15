import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;

public class Main {

    public static void main(String[] args) {
        File selectedFile = null;

        try {
            if (args.length > 0) {
                Path filePath = Paths.get(args[0]);

                if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                    selectedFile = filePath.toFile();
                } else {
                    System.out.println("File not found or invalid path: " + args[0]);
                    return;
                }
            } else {
                // No command-line argument; use JFileChooser
                JFileChooser chooser = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                    return;
                }
            }

            // Process the selected file
            if (selectedFile != null) {
                processFile(selectedFile);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processFile(File selectedFile) {
        int line = 0;
        int wordCnt = 0;
        int charCht = 0;

        try (InputStream in = new BufferedInputStream(Files.newInputStream(selectedFile.toPath(), CREATE));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String rec;
            while ((rec = reader.readLine()) != null) {
                line++;
                charCht += rec.length();
                String[] words = rec.split("\\s+");
                wordCnt += words.length;
                System.out.printf("\nLine %4d %-60s ", line, rec);
            }

            System.out.println("\n\nData file read!");
            System.out.println("File stats:");
            System.out.println("File name: " + selectedFile.getName());
            System.out.println("Total lines: " + line);
            System.out.println("Total words: " + wordCnt);
            System.out.println("Total characters: " + charCht);

        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}