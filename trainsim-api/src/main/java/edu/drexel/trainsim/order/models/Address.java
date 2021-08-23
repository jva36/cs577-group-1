package edu.drexel.trainsim.order.models;

public class Address
{
  private String street;
  private String apt;
  private String city;
  private String state;
  private String zip;

  public void setStreet(String street)
  {
    this.street = street;
  }

  public void setApt(String apt)
  {
    this.apt = apt;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public void setState(String state)
  {
    this.state = state;
  }
  public void setZip(String zip)
  {
    this.zip = zip;
  }

  @Override
  public String toString()
  {
    return street + (apt == null ? "" : ", " + apt) + ", " + city + ", " + state + ", " + zip;
  }
}
