package time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p><code>ClockPanel</code> is a simple time-related utility that displays
 * the Julian day and Zulu/UTC times in a panel.  The constructor
 * accepts a <code>ClockTimeZone</code> argument that defines additional
 * time zones and their display aliases.</p>
 * @author jsmith
 * @version $Id: ClockPanel.java $
 */

public class ClockPanel extends JPanel {
  private static final int MAX_TIMEZONES = 10;
  private static final int DELAY = 1000;
  private static final int WIDTH = 200;
  private static final int HEIGHT = 140;
  private JLabel dayLabel, localLabel, zuluLabel;
  private ActionListener clockUpdater;
  private javax.swing.Timer timer;

  /**
   * <p>Implements a simple vertically organized panel that displays the
   * Julian day, Zulu/UTC time, and local time.</p>
   */

  public ClockPanel() {
    this(WIDTH, HEIGHT, DELAY, null);
  }

  /**
   * <p>Implements a simple vertically organized panel that displays the
   * Julian day, Zulu/UTC time, and local time.</p>
   * @param width The preferred width, in pixels.
   * @param height The preferred height, in pixels.
   */

  public ClockPanel(int width, int height) {
    this(width, height, DELAY, null);
  }

  /**
   * <p>Implements a simple vertically organized panel that displays the
   * Julian day, Zulu/UTC time, and local time.</p>
   * @param width The preferred width, in pixels.
   * @param height The preferred height, in pixels.
   * @param updatePeriod The preferred update cycle/period, in milliseconds.
   */

  public ClockPanel(int width, int height, int updatePeriod) {
    this(width, height, updatePeriod, null);
  }

  /**
   * <p>Implements a simple vertically organized panel that displays the
   * Julian day, Zulu/UTC time, and, optionally, the time for additional
   * timezones.</p>
   * @param width The preferred width, in pixels.
   * @param height The preferred height, in pixels.
   * @param updatePeriod The preferred update cycle/period, in milliseconds.
   * @param timeZones The array of <code>ClockTimeZone</code> instances
   * that define additional time zones and their respective display
   * aliases.
   * @see ClockTimeZone
   */

  public ClockPanel(int width, int height, int updatePeriod,
      final ClockTimeZone[] timeZones) {
    setLayout(new BorderLayout(5, 5));
    InsetsPanel outerPanel = new InsetsPanel();
    outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
    add(outerPanel, BorderLayout.CENTER);
    JPanel dayPanel = new JPanel();
    dayPanel.setLayout(new BorderLayout(5, 5));
    outerPanel.add(dayPanel);
    dayPanel.add(new JLabel("Julian Day (Zulu) "), BorderLayout.WEST);
    dayLabel = new JLabel("");
    dayPanel.add(dayLabel, BorderLayout.EAST);

    JPanel zuluPanel = new JPanel();
    zuluPanel.setLayout(new BorderLayout(5, 5));
    outerPanel.add(zuluPanel);
    zuluPanel.add(new JLabel("Zulu "), BorderLayout.WEST);
    zuluLabel = new JLabel("");
    zuluPanel.add(zuluLabel, BorderLayout.EAST);

    JPanel tempPanel;
    final int numZones;
    if (timeZones != null) {
      if (timeZones.length < MAX_TIMEZONES) {
        numZones = timeZones.length;
      }
      else {
        numZones = MAX_TIMEZONES;
      }
    }
    else {
      numZones = 0;
    }
    final JLabel[] timeLabels = new JLabel[numZones];
    for (int i = 0; i < numZones; i++) {
      tempPanel = new JPanel();
      tempPanel.setLayout(new BorderLayout(5, 5));
      outerPanel.add(tempPanel);
      tempPanel.add(new JLabel(timeZones[i].label), BorderLayout.WEST);
      timeLabels[i] = new JLabel("");
      tempPanel.add(timeLabels[i], BorderLayout.EAST);
    }

    final DateFormat df =
      DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.UK);
    df.setTimeZone(TimeZone.getTimeZone("Zulu"));
    final NumberFormat nf = df.getNumberFormat();
    //final Calendar cal = Calendar.getInstance();

    clockUpdater = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        df.setTimeZone(TimeZone.getTimeZone("Zulu"));
        Calendar cal = df.getCalendar();
        Date date = new Date();
        df.setTimeZone(TimeZone.getTimeZone("Zulu"));
        dayLabel.setText("" + cal.get(cal.DAY_OF_YEAR));
        zuluLabel.setText(df.format(date));
        date = new Date();
        for (int i = 0; i < numZones; i++) {
          df.setTimeZone(TimeZone.getTimeZone(timeZones[i].zone));
          timeLabels[i].setText(df.format(date));
        }
      }
    };
    timer = new javax.swing.Timer(updatePeriod, clockUpdater);

    if (width > 0 && height > 0) {
      setSize(width, height);
    }
    else {
      //setSize(WIDTH, HEIGHT);
      setSize(getPreferredSize());
    }
    setVisible(true);
  }

  /**
   * <p>Starts the clock.</p>
   */

  public void start() {
    timer.start();
  }

  /**
   * <p>Stopss the clock.</p>
   */

  public void stop() {
    timer.stop();
  }

  /**
   * <p>Displays a list of valid time zones in a command window.</p>
   */

  public static void listTimeZones() {
    String[] ids = TimeZone.getAvailableIDs();
    for (String id : ids) {
      System.out.println(id);
    }
  }

  class InsetsPanel extends JPanel {
    public Insets getInsets() {
      return new Insets(5, 5, 5, 5);
    }
  }
}
