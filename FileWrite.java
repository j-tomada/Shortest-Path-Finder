import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Random;

public class FileWrite {
    public static void main(String[] args) throws IOException {
        File file = new File("text.txt");
        PrintStream stream = new PrintStream(file);
        Random rand = new Random();
        int count = 0;

        for (int i = 65; i <= 89; ++i) {
            char newChar = (char) i;
            stream.print(newChar +", ");


            for (int j = 0; j < 25; ++j) {
                if (j == count) {
                    stream.print(0 +", ");
                }
                else if (j == count + 1) {
                    stream.print(1 +", ");
                }
                else {
                    int randValue = rand.nextInt(50) + 1;
                    stream.print(randValue + ", ");
                }
            }
            ++count;
            stream.println();
        }
        stream.close();
    }
}
