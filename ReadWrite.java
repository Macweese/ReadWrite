/*
 * REDACTED
 */

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program that reads/writes to/from console/files
 */
public class ReadWrite {

    // This path is specific to my machine
    static final Path PATH = Paths.get("REDACTED");
    // This path is specific to my machine
    static final String STRING_PATH = "REDACTED";

    static DecimalFormat df = new DecimalFormat("#00.00");
    static Set<Integer> integerSet = new HashSet<>() {};
    static StringBuilder content = new StringBuilder();
    static String input;
    static String[] numbers;

    /**
     * Main
     */
    public static void main(String[] args) throws IOException
    {
        // Grab numbers from a text, format with two decimal points
        ScannerIO.run();

        // Read/Write to/from console
        BufferedIO.run();

        // Read/Write to/from a text file
        FileHandler.run();
    }

    /**
     * Operations to perform using Scanner class
     * Takes all digits from a String input and enters values
     * into a String array, and Integer Set
     *
     * {@link Scanner}
     */
    public static class ScannerIO
    {
        public static void in() throws InputMismatchException
        {
            System.out.println("Enter numbers...");
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            numbers = input.split(" ");

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find())
            {
                integerSet.add(Integer.valueOf(matcher.group()));
            }

        }

        public static void out()
        {
            System.out.println("Input:\n" + input);
            System.out.println(integerSet);

            integerSet.stream().mapToInt(i -> i).mapToObj(i -> df.format(i)).forEach(System.out::println);
        }

        public static void run()
        {
            in();
            out();
        }
    }

    /**
     * Operations to perform using Buffered classes
     * Takes a string and prints out same value
     * {@link BufferedReader}, {@link BufferedWriter}
     */
    public static class BufferedIO
    {

        /**
         * Reads the contents of console and returns String value
         *
         * @return The contents in console as a String
         * @throws IOException Exception
         */
        public static String inputReader() throws IOException
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            input = reader.readLine();
            if (input.isEmpty() || input.isBlank() || input.equals(""))
                return "";

            return input;
        }

        /**
         * Prints the input from reader() to the console
         *
         * @throws IOException Exception
         */
        public static void printConsole() throws IOException
        {
            System.out.println(input);
        }

        public static void run()  throws IOException
        {
            System.out.println("Type something...");
            inputReader();
            printConsole();
        }
    }

    /**
     * Operations to perform with files
     */
    public static class FileHandler
    {
        /**
         * Writes over or appends an existing file, or creates a new one if none exists
         *
         * @param filename The file to write or overwrite
         * @param text The contents to write
         * @param append Boolean value whether or not to append file contents
         * @throws FileNotFoundException File does not exist
         * @throws IOException Exception
         */
        static public void fileWriter(String filename, String text, boolean append) throws FileNotFoundException, IOException
        {
            filename = filename + ".txt";
            File f = new File(PATH + filename);
            FileWriter out = new FileWriter(filename, append);

            try {
                out.append(text + System.getProperty("line.separator"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }

        /**
         * Read from a text file
         *
         * @param file The file to read from
         * @return Returns contents of the file as String
         * @throws IOException Exception
         */
        static public String fileReader(File file) throws IOException
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            input = "";
            try {
                while ((input = in.readLine()) != null)
                {
                    content.append(input);
                }
            }
            finally {
                in.close();
            }
            return content.toString();
        }

        public static void run() throws IOException
        {
            // Reads inputs from console, creates or appends to a text file
            fileWriter(BufferedIO.inputReader(), BufferedIO.inputReader(), true);

            // Reads contents from existing text file, prints to console
            System.out.println(fileReader(fileFromPath()));
        }

        /**
         * Fetches the file from specified directory
         * NOT COMPATIBLE ON ALL MACHINES
         *
         * @return Returns the the specified file
         * @throws IOException Exception
         */
        public static File fileFromPath() throws FileNotFoundException, IOException
        {
            Path path = Paths.get(STRING_PATH + File.separator + BufferedIO.inputReader() + ".txt");
            return path.toFile();
        }
    }
}

