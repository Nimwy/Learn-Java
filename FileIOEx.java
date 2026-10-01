
import java.io.FileWriter;
import java.io.IOException;

public class FileIOEx {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("students.txt")) {
            writer.write("An\nBinh\nNam");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}