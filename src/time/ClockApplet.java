package time;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p><code>ClockApplet</code> is a saimple clock applet that displays
 * the Julian date and Zulu/UTC time, plus optional times from other
 * zones.</p>
 * @author jsmith
 * @version $Id: ClockApplet.java $
 */

public class ClockApplet extends JApplet {
  private static final int DELAY = 1000;
  private static final int WIDTH = 200;
  private static final int HEIGHT = 275;
  private ClockPanel clockPanel;

  /**
   * <p>Not for public consumption.</p>
   */

  public void init() {
    Container contentPane = getContentPane();
    String updatePeriod = getParameter("updatePeriod");
    int delay = DELAY;
    if (updatePeriod != null && updatePeriod.length() > 0) {
      delay = Integer.parseInt(updatePeriod);
      if (delay < DELAY / 2 || delay > DELAY * 60) {
        delay = DELAY;
      }
    }
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
    contentPane.add(clockPanel = new ClockPanel(-1, -1, delay, timeZones));
    setVisible(true);
  }

  /**
   * <p>Not for public consumption.</p>
   */

  public void start() {
    clockPanel.start();
  }

  /**
   * <p>Not for public consumption.</p>
   */

  public void stop() {
    clockPanel.stop();
  }
}
