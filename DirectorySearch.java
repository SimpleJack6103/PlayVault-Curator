//package PlayVaultCurator.UI;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.io.IOException;


public class DirectorySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a directory path:");
        String directoryPath = scanner.nextLine();

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The provided path is not a valid directory.");
            return;
        }

        System.out.println("Searching in directory: " + directory);
        searchFiles(directory);
    }

    //search for files given a directory
    public static void searchFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    typeLookup(file);
                } else if (file.isDirectory()) {
                    searchFiles(file);
                }
            }
        }
    }

    public static String getExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    //checks type of file
    public static void typeLookup(File f_type){
        try {
            Path filePath = f_type.toPath();  // fetch path
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class); //read in attrs
            String fileName = f_type.getName();
            String fileType = getExtension(filePath);
            long fileSize = f_type.length();
            String lastAccessed = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(attrs.lastAccessTime().toMillis());
            if (fileType.equals("exe")) {

                System.out.printf("Name: %s, Type: %s, Size: %.2f MB, Last Accessed: %s%n",
                        fileName, fileType, fileSize / (1024.0 * 1024.0), lastAccessed);
            } else {
                System.out.println("---");
            }
        }
        catch(Exception e){
            System.out.println("Error retrieving file attributes for " + f_type.getAbsolutePath());
        }
    }

      //checks steam file
      public static String steamLookup(File f_type){
        try {
            Path filePath = f_type.toPath();  // fetch path
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class); //read in attrs
            String fileName = f_type.getName();
            String fileType = getExtension(filePath);
           //  long fileSize = f_type.length();
           // String lastAccessed = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(attrs.lastAccessTime().toMillis());
            if (fileType.equals("vdf") && fileName.equals("libraryfolders.vdf")) {

                // Read file contents as a string
                String content = Files.readString(filePath);
                return content;
            } else {
                return " "; //file not the target
            }
        }
        catch(Exception e){
            System.out.println("Error retrieving file attributes for " + f_type.getAbsolutePath());
            e.printStackTrace();
            return " ";
        }
    }
}
