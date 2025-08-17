import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class FileTask implements Runnable {
    private String file;
    private List<String> names;

    public FileTask(String file, List<String> names) {
        this.file = file;
        this.names = names;
    }

    @Override
    public void run() {
        try {
            File f = new File(file);
            System.out.println(f.getAbsolutePath());
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                synchronized (names) {
                    names.add(line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error in " + file);
        }
    }
}
