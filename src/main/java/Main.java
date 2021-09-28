import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String PATH = "./banlist.txt";
    private static final String BAN_COMMAND = "/ban";

    public static void main(String[] args) {

        System.out.println("Press any key to start the process. You will have 5 seconds to make sure that the Twitch chat window is selected.");

        Scanner scan = new Scanner(System.in);

        scan.nextLine();
        try {
            for (int i = 5; i >= 1; i--) {
                System.out.println(i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        scan.close();

        try {

            FileInputStream fis = new FileInputStream(PATH);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Robot robot = new Robot();

            String fileLine;

            while ((fileLine = in.readLine()) != null) {
                System.out.println(BAN_COMMAND+ " " + fileLine);
                StringSelection stringSelection = new StringSelection(BAN_COMMAND+ " " + fileLine);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, stringSelection);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                TimeUnit.MILLISECONDS.sleep(100);
            }

            fis.close();
            in.close();

        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file, please make sure the file is called banlist.txt and that it is located in the same directory as the jar. Thank you");
            System.err.println(e);
        } catch (Exception e) {
            System.err.println("Something went wrong while reading the file: " + e);
        }

    }
}
