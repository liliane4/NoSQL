package mds.ilyane.java;

import java.util.UUID;

public class User {

  private final String user1;
  private String log;

  public User() {
    this.user1 = UUID.randomUUID().toString();
  }

  public User(final String user1, final String log) {
    this.user1 = user1;
    this.log = log;
  }

  public String getUser1() {
    return user1;
  }

  public String getLog() {
    return log;
  }

  public void setLogin(String log) {
    this.log = log;
  }
}
