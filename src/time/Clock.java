package time;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.*;
import javax.xml.stream.events.StartDocument;

/**
 * <p><code>Clock</code> is a simple clock application that displays the
 * Julian day and Zulu/UTC time, as well as the time from additional
 * time zones.  The user can provide an optional time zone file in the
 * following format:</p>
 * <pre><code>
 * [time-zone-string],[time-zone-alias]
 * [time-zone-string],[time-zone-alias]
 * ...
 * </code></pre>
 * <p>The entries must occur one pair per line, separated by a comma.
 * There is no whitespace processing and no escape for a comma as the
 * separator--all characters (and spaces) are significant.  Example:</p>
 * <pre><code>
 * America/New_York,Eastern
 * America/Denver,Mountain
 * </code></pre>
 * @author jsmith
 * @version $Id: Clock.java $
 */

public class Clock extends JFrame {
  private static final String APP_TITLE = "Clock";
  private static final int DELAY = 1000;
  private static final int WIDTH = 200;
  private static final int HEIGHT = 275;
  private ClockPanel clockPanel;

  /**
   * <p>Not for public consumption.</p>
   * <p><code>Clock</code> is never instantiated directly.</p>
   */

  public Clock() {
    this(APP_TITLE);
  }

  /**
   * <p>Not for public consumption.</p>
   * <p><code>Clock</code> is never instantiated directly.</p>
   * @param title The title displayed in the title bar.
   */

  public Clock(String title) {
    this(title, WIDTH, HEIGHT, DELAY);
  }

  /**
   * <p>Not for public consumption.</p>
   * <p><code>Clock</code> is never instantiated directly.</p>
   * @param width The width, in pixels.
   * @param height The height, in pixels.
   * @param delay The update period, in milliseconds.
   */

  public Clock(int width, int height, int delay) {
    this(APP_TITLE, width, height, delay);
  }

  /**
   * <p>Not for public consumption.</p>
   * <p><code>Clock</code> is never instantiated directly.</p>
   * @param title The title displayed in the title bar.
   * @param width The width, in pixels.
   * @param height The height, in pixels.
   * @param delay The update period, in milliseconds.
   */

  public Clock(String title, int width, int height, int delay) {
    super(title);
    Container contentPane = getRootPane().getContentPane();
    setLayout(new BorderLayout(5, 5));
    ClockTimeZone[] timeZones = {
      new ClockTimeZone("Asia/Kabul", "Kabul"),
      new ClockTimeZone("Asia/Baghdad", "Baghdad"),
      new ClockTimeZone("Asia/Qatar", "Qatar"),
      new ClockTimeZone("Europe/Berlin", "Berlin"),
      new ClockTimeZone("Europe/London", "London"),
      new ClockTimeZone("America/New_York", "New York"),
      new ClockTimeZone("America/Denver", "Denver"),
      new ClockTimeZone("US/Hawaii", "Hawaii"),
      new ClockTimeZone("Australia/Adelaide", "Adelaide"),
      //new ClockTimeZone("",""),
    };
    contentPane.add(clockPanel = new ClockPanel(0, 0, delay, timeZones));
    addWindowListener(new TheCloser());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    pack();
    if (width > 0 && height > 0) {
      setSize(width, height);
    }
    else {
      setSize(getPreferredSize());
    }
    setVisible(true);
  }

  /**
   * <p>Not for public consumption.</p>
   * <p><code>Clock</code> is never instantiated directly.</p>
   * @param title The title displayed in the title bar.
   * @param width The width, in pixels.
   * @param height The height, in pixels.
   * @param delay The update period, in milliseconds.
   * @param timeZoneFile A file or path specification for an
   * optional timezone file.
   */

  public Clock(String title, int width, int height, int delay,
      String timeZoneFile) {
    super(title);
    Container contentPane = getRootPane().getContentPane();
    setLayout(new BorderLayout(5, 5));
    ClockTimeZone[] timeZones = parseTimeZoneFile(timeZoneFile);
    contentPane.add(clockPanel = new ClockPanel(0, 0, delay, timeZones));
    addWindowListener(new TheCloser());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    pack();
    if (width > 0 && height > 0) {
      setSize(width, height);
    }
    else {
      setSize(getPreferredSize());
    }
    setVisible(true);
  }

  private ClockTimeZone[] parseTimeZoneFile(String timeZoneFile) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(timeZoneFile));
      ArrayList<ClockTimeZone> list = new ArrayList<ClockTimeZone>();
      String line;
      while ((line = br.readLine()) != null) {
        ClockTimeZone ctz = new ClockTimeZone();
        int pos = line.indexOf(",");
        if (pos > 1) {
          ctz.zone = line.substring(0, pos);
          ctz.label = line.substring(pos + 1);
          //System.out.println("'" + ctz.zone + "', '" + ctz.label + "'.");
          list.add(ctz);
        }
      }
      br.close();
      ClockTimeZone[] timeZones = list.toArray(new ClockTimeZone[0]);
      return timeZones;
    }
    catch (Exception e) {
      System.out.println(
        "Unable to read timezone file: '" + timeZoneFile + "'.");
      System.out.println("Exception trace: ");
      e.printStackTrace();
      return null;
    }
  }

  /**
   * <p>Starts the clock.</p>
   */

  public void start() {
    clockPanel.start();
  }

  /**
   * <p>Stops the clock.</p>
   */

  public void stop() {
    clockPanel.stop();
  }

  /**
   * <p>Queries for legitimate timezone values, or runs the Clock
   * application.  Width and height are in pixels; update-period is in
   * milliseconds.</p>
   * <p>Usage:</p>
   * <ul>
   *   <li><code>java time.Clock timezones</code>
   *   <li><code>java time.Clock 200 275 1000</code>
   *   <li><code>java time.Clock 200 275 1000 timezonefile</code>
   * </ul>
   * @param args Provides a list of possible timezones (only), a triplet
   * that specifies start-up parameters, or a quad-tuple that specifies
   * start-up parameters, all of which are required.
   */
   
  public static void main(String[] args) {
    if (args.length == 1 && args[0].equalsIgnoreCase("timezones")) {
      ClockPanel.listTimeZones();
    }
    else if (args.length == 3) {
      int width = Integer.parseInt(args[0]);
      int height = Integer.parseInt(args[1]);
      int delay = Integer.parseInt(args[2]);
      new Clock(width, height, delay).start();
    }
    else if (args.length == 4) {
      int width = Integer.parseInt(args[0]);
      int height = Integer.parseInt(args[1]);
      int delay = Integer.parseInt(args[2]);
      new Clock(APP_TITLE, width, height, delay, args[3]).start();
    }
    else {
      //new Clock(-1, -1, DELAY).start();
      new Clock().start();
    }
  }

  class TheCloser extends WindowAdapter {
    public void windowClosing(WindowEvent event) {
      if (event.getID() == WindowEvent.WINDOW_CLOSING) {
        System.exit(0);
      }
    }
  }
}
