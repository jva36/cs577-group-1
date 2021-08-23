package edu.drexel.trainsim.order.models;

import java.util.Date;

public class Order
{
  private int id;
  private int userID;
  private String status;
  private Date createdTime;
  private String address;

  public int getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }

  public int getUserID()
  {
    return userID;
  }
  public void setUserID(int userID)
  {
    this.userID = userID;
  }

  public String getStatus()
  {
    return status;
  }
  public void setStatus(String status)
  {
    this.status = status;
  }

  public Date getCreatedTime()
  {
    return createdTime;
  }
  public void setCreatedTime(Date createdTime)
  {
    this.createdTime = createdTime;
  }

  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
}