import java.io.*;

// Thread to write to a file
class FileWriterThread extends Thread {
    private String filename;

    FileWriterThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Hello from Java multithreading!\n");
            writer.write("This text is written by a thread.");
            System.out.println("File writing done.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

// Thread to read from the file
class FileReaderThread extends Thread {
    private String filename;

    FileReaderThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Reading file contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}

public class MultiThreadFileIO {
    public static void main(String[] args) {
        String filename = "example.txt";

        FileWriterThread writerThread = new FileWriterThread(filename);
        FileReaderThread readerThread = new FileReaderThread(filename);

        writerThread.start();
        
        try {
            writerThread.join();  // Ensure writing completes before reading
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        readerThread.start();
    }
}
