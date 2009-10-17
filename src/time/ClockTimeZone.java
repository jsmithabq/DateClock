package time;

/**
 * <p><code>ClockTimeZone</code> represents a formal time zone designation,
 * plus a display label.  For convenience, the fields are public.</p>
 * @author jsmith
 * @version $Id: ClockTimeZone.java $
 */

public class ClockTimeZone {
  /**
   * The formal time zone specification string.
   */
  public String zone;
  /**
   * The display label, or alias.
   */
  public String label;

  /**
   * <p>Provides a no-arg constructor.</p>
   */

  public ClockTimeZone() {
  }

  /**
   * <p>Sets the fomral time zone and its alias, or display label.</p>
   * @param zone The formal (international) zone designation.
   * @param label The display label (alias).
   */

  public ClockTimeZone(String zone, String label) {
    this.zone = zone;
    this.label = label;
  }
}
