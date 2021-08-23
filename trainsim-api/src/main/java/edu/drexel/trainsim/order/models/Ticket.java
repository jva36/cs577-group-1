package edu.drexel.trainsim.order.models;

import java.util.UUID;

public class Ticket
{
  private int id;
  private int orderID;
  private int travelerID;
  private UUID itineraryID;
  private float price;

  public int getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }

  public int getOrderID()
  {
    return orderID;
  }
  public void setOrderID(int orderID)
  {
    this.orderID = orderID;
  }

  public int getTravelerID()
  {
    return travelerID;
  }
  public void setTravelerID(int travelerID)
  {
    this.travelerID = travelerID;
  }

  public UUID getItineraryID()
  {
    return itineraryID;
  }
  public void setItineraryID(UUID itineraryID)
  {
    this.itineraryID = itineraryID;
  }

  public float getPrice()
  {
    return price;
  }
  public void setPrice(float price)
  {
    this.price = price;
  }
}
