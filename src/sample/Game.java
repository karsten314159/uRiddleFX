package sample;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import uriddle.logic.*;

import static uriddle.logic.Direction.*;
import static uriddle.logic.Level.State.*;

import java.io.*;
import java.net.URL;
import java.util.*;

import uriddle.logic.Level.*;

class Game {
  public static void drawToBitmap(Level level, WritableImage bitmap) {
    String string = level.toString();
    int y = -1;
    List<String> split = new ArrayList<String>(
            Arrays.asList(string.split("\n")));
    split.remove(LevelWriter.HEADER_LINE);

    int w = 10;
    int h = Math.max(split.size(), 10);
    for (String line : split) {
      w = Math.max(w, line.length());
    }
        /*if (bmp.getWidth() != w * SCALE || bmp.getHeight() != h * SCALE) {
            bmp = Bitmap.createBitmap(w * SCALE, h * SCALE, Config.ARGB_4444);
        }*/
    int SCALE = 10;
    // Random random = new Random();

    PixelWriter pixelWriter = bitmap.getPixelWriter();
    String line0 = split.get(0);
    for (String line : split) {
      y++;
      if (level.pixelate && y % 2 == 0) {
        line = line0;
      }
      line0 = line;
      char c = line.charAt(0);
      for (int x = 0; x < line.length(); x++) {
        c = line.charAt(x);
        if (level.pixelate && x % 2 == 0) {
          c = line.charAt(Math.max(0, x - (y % 2)));
        }
        // @formatter:off
        int color =
                c == ' ' ? 0xFF000000
                        : c == '+' ? 0xFF808080
                        : c == '#' ? 0xFFFF0000
                        : c == '.' ? 0xFF333333
                        : c == 'P' ? 0xFF330000
                        : c == 'X' ? 0xFF330033
                        : c == '1' ? 0xFF885500
                        : c == '2' ? 0xFF1111AA
                        : c == 'q' ? 0xFF00FFFF
                        : c == 'o' ? 0xFFFFFF00
                        : 0xFFFFFF00;
        // @formatter:on
        int sc = level.pixelate ? SCALE : SCALE - 1;
        for (int dx = 0; dx < sc; dx++) {
          for (int dy = 0; dy < sc; dy++) {
            int jitter = 0; //random.nextInt(5) + y % 5;
            // + (dx / 2)
            int color2 = color | (jitter << 16) | (jitter << 8) | (jitter << 4);
            pixelWriter.setArgb(x * SCALE + dx, y * SCALE + dy, color2);
          }
        }
      }
    }
  }

  public void main(String[] args) throws Exception {
    int index = 0;
    List<String> levels = getLevels();
    Level level = get(levels, index);

    System.out.println("Welcome to uRiddle!");
    System.out.println(
            "Goal: Move the thick (#) boxes into each other to open the exit (X) and leave the level.");
    System.out.println("Controls: Enter w, a, s or d end press Enter to move.");
    System.out.println("Good luck and have fun!");
    System.out.println(level.toString());
    Scanner s = new Scanner(System.in);
    String read;
    while ((read = s.nextLine()) != null) {
      Direction d = getDir(read);
      if (d != null) {
        State go = level.go(d);
        System.out.println(level.toString());
        if (go == REACHED_EXIT) {
          System.out.println("You reached the exit! Well done!");
          index++;
          level = get(levels, index);
        }
      }
    }
    s.close();
  }

  public static Level get(List<String> levels, int index) {
    if (index >= levels.size() || index < 0) {
      index = 0;
    }
    String entry = levels.get(index);
    Level level = LevelReader.instance.fromString(entry);
    return level;
  }

  static Direction getDir(String read) {
    if (read.length() == 0)
      return null;

    switch (read.charAt(0)) {
      case 'w':
        return TOP;
      case 's':
        return BOTTOM;
      case 'a':
        return LEFT;
      case 'd':
        return RIGHT;
      default:
        return null;
    }
  }

  public static List<String> getLevels(BufferedReader rd) throws Exception {
    List<String> res = new ArrayList<String>();
    String line;
    String lastName = null;
    String name = null;
    StringBuilder entry = new StringBuilder();
    try {
      while ((line = rd.readLine()) != null) {
        if (line.startsWith(" ")) {
          entry.append(line).append("\n");
        } else {
          if (name != null && name != lastName) {
            if (entry.length() != 0) {
              entry.delete(entry.length() - 1, entry.length());
            }
            entry.insert(0, name + "\n");
            res.add(entry.toString());
            entry.delete(0, entry.length());
            lastName = name;
          }
          name = line;
        }
      }
    } finally {
      if (rd != null) {
        rd.close();
      }
    }
    return res;
  }

  public static List<String> getLevels() throws Exception {

    InputStream in = Game.class.getResourceAsStream("/uriddle/logic/levels.txt");
    BufferedReader rd = new BufferedReader(new InputStreamReader(in));
    return getLevels(rd);
  }
}