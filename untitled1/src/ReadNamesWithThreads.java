import java.io.*;
import java.util.*;

public class ReadNamesWithThreads {
    static class FileReaderThread implements Runnable {
        private final String fileName;
        private final List<String> sharedList;

        public FileReaderThread(String fileName, List<String> sharedList) {
            this.fileName = fileName;
            this.sharedList = sharedList;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        synchronized (sharedList) {
                            sharedList.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + fileName);
            }
        }
    }

    public static void main(String[] args) {
        List<String> allNames = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String fileName = i + ".txt";
            Thread t = new Thread(new FileReaderThread(fileName, allNames));
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
                Thread.currentThread().interrupt();
            }
        }

        Collections.sort(allNames, String.CASE_INSENSITIVE_ORDER);
        System.out.println("All names sorted:");
        for (String name : allNames) {
            System.out.println(name);
        }
    }
}
