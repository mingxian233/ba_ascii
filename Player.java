import java.io.*;
import java.util.*;

public class Player {

    public static void main(String[] args) {
        List<String> frames = loadFrames("ascii_frames.txt");
        int fps = 30; // 调整帧率以适应播放环境
        long frameDelay = 1000 / fps;

        try {
            for (String frame : frames) {
                clearScreen();
                System.out.print(frame);
                Thread.sleep(frameDelay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadFrames(String filename) {
        List<String> frames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder frame = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("===FRAME===")) {
                    if (frame.length() > 0) {
                        frames.add(frame.toString());
                        frame.setLength(0);
                    }
                } else {
                    frame.append(line).append("\n");
                }
            }
            if (!frame.isEmpty()) frames.add(frame.toString());
        } catch (IOException e) {
            System.err.println("无法加载帧文件: " + e.getMessage());
        }
        return frames;
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}