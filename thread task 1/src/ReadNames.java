import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReadNames {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            pool.execute(new FileTask(i + ".txt", names));
        }

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Pool error");
        }

        Collections.sort(names);
        for (String n : names) {
            System.out.println(n);
        }
    }
}