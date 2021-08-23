package edu.drexel.trainsim.order.models;

import java.util.List;

public class CheckOutResponse
{
  private int orderID;
  private int userID;
  private List<Ticket> tickets;

  public CheckOutResponse(int orderID, int userID, List<Ticket> tickets)
  {
    this.orderID = orderID;
    this.userID = userID;
    this.tickets = tickets;
  }

  public int getOrderID()
  {
    return orderID;
  }
  public int getUserID()
  {
    return userID;
  }
  public List<Ticket> getTravelers()
  {
    return tickets;
  }
}
