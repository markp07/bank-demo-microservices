package xyz.markpost.accounts.model;

public enum AccountType {
  CHECKING("CHECKING"),
  SAVING("SAVING");

  private final String text;

  /**
   *
   */
  AccountType(final String text) {
    this.text = text;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
}