package edu.drexel.trainsim.order.db;

import com.google.inject.Inject;
import edu.drexel.trainsim.order.models.Traveler;
import org.sql2o.Sql2o;

import edu.drexel.trainsim.order.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetOrCreateTicketImpl implements GetOrCreateTicket
{
  private final Sql2o db;

  @Inject
  public GetOrCreateTicketImpl(Sql2o db)
  {
    this.db = db;
  }

  @Override
  public List<Ticket> create(int orderID, List<Traveler> travelers, UUID itineraryID, float price)
  {
    List<Ticket> tickets = new ArrayList();
    try (var con = this.db.open())
    {
      String insertSql = "INSERT INTO tickets(orderID, travelerID, itineraryID, price) VALUES(:orderID, :travelerID, :itineraryID, :price)";
      for (Traveler traveler : travelers)
        con.createQuery(insertSql)
         .addParameter("orderID", orderID)
         .addParameter("travelerID", traveler.getId())
         .addParameter("itineraryID", itineraryID)
         .addParameter("price", price).executeUpdate();
      String sql = "SELECT id, orderID, travelerID, itineraryID, price" +
                   "  FROM tickets" +
                   " WHERE orderID = :orderID";
      tickets = con.createQuery(sql).addParameter("orderID", orderID).executeAndFetch(Ticket.class);

    }
    return tickets;
  }
}
