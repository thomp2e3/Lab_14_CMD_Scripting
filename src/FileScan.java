import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
public class FileScan
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        int line = 0;
        int wordCnt = 0;
        int charCnt = 0;
        String[] words;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (args.length > 0) {
                selectedFile = new File(args[0]);
                if (selectedFile.exists()) {
                    System.out.println("File not found: " + args[0]);
                    return;
                }
            } else {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("No file selected! ... exiting.\nRun the program again and select a file.");
                    return;
                }
            }

            Path file = selectedFile.toPath();
            InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (reader.ready()) {
                rec = reader.readLine();
                line++;
                charCnt = rec.length();
                words = rec.split(" ");
                wordCnt += words.length;
                System.out.printf("\nLine %4d %-60s ", line, rec);
            }
            reader.close();
            System.out.println("\n\nData file read!");
            System.out.println("File: " + selectedFile.getName());
            System.out.println("File Stats: ");
            System.out.println("Total Lines: " + line);
            System.out.println("Total Words: " + wordCnt);
            System.out.println("Total Characters: " + charCnt);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}