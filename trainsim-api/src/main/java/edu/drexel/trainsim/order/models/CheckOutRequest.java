package edu.drexel.trainsim.order.models;

import java.util.List;

public class CheckOutRequest
{
  private String paymentConfirmation;
  private int userID;
  private Ticket ticket;
  private List<Traveler> travelers;
  private Address address;

  public String getPaymentConfirmation()
  {
    return paymentConfirmation;
  }
  public void setPaymentConfirmation(String paymentConfirmation)
  {
    this.paymentConfirmation = paymentConfirmation;
  }

  public List<Traveler> getTravelers()
  {
    return travelers;
  }
  public void setTraveler(List<Traveler> travelers)
  {
    this.travelers = travelers;
  }

  public int getUserID()
  {
    return userID;
  }
  public void setUserID(int userID)
  {
    this.userID = userID;
  }

  public Ticket getTicket()
  {
    return ticket;
  }
  public void setTicket(Ticket ticket)
  {
    this.ticket = ticket;
  }

  public Address getAddress()
  {
    return address;
  }
  public void setAddress(Address address)
  {
    this.address = address;
  }
}
