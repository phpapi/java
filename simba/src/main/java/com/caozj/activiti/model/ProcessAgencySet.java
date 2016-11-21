package com.caozj.activiti.model;

/**
 * 流程代办设置
 * 
 * @author caozj
 *
 */
public class ProcessAgencySet {

  private int id;

  /**
   * 开始时间
   */
  private String startTime;

  /**
   * 结束时间
   */
  private String endTime;

  /**
   * 设置人
   */
  private String account;

  /**
   * 代理人
   */
  private String agencyAccount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getAgencyAccount() {
    return agencyAccount;
  }

  public void setAgencyAccount(String agencyAccount) {
    this.agencyAccount = agencyAccount;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProcessAgency [id=");
    builder.append(id);
    builder.append(", startTime=");
    builder.append(startTime);
    builder.append(", endTime=");
    builder.append(endTime);
    builder.append(", account=");
    builder.append(account);
    builder.append(", agencyAccount=");
    builder.append(agencyAccount);
    builder.append("]");
    return builder.toString();
  }

}
