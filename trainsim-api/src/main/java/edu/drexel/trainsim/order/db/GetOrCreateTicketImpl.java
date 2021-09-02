package edu.drexel.trainsim.order.db;

import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import edu.drexel.trainsim.order.models.Traveler;
import org.sql2o.Sql2o;

import edu.drexel.trainsim.order.models.Ticket;

public class GetOrCreateTicketImpl implements GetOrCreateTicket
{
  private final Sql2o db;

  @Inject
  public GetOrCreateTicketImpl(Sql2o db)
  {
    this.db = db;
  }

  @Override
  public List<Ticket> create(int orderID, List<Traveler> travelers, List<Ticket> trips)
  {
    List<Ticket> tickets = new ArrayList();
    try (var con = this.db.open())
    {
      String insertSql = "INSERT INTO tickets(orderID, travelerID, itineraryID, price) VALUES(:orderID, :travelerID, :itineraryID, :price)";
      for (Ticket ticket : trips)
        for (Traveler traveler : travelers)
          con.createQuery(insertSql)
            .addParameter("orderID", orderID)
            .addParameter("travelerID", traveler.getId())
            .addParameter("itineraryID", ticket.getItineraryID())
            .addParameter("price", ticket.getPrice()).executeUpdate();
      String sql = "SELECT id, orderID, travelerID, itineraryID, price" +
                   "  FROM tickets" +
                   " WHERE orderID = :orderID";
      tickets = con.createQuery(sql).addParameter("orderID", orderID).executeAndFetch(Ticket.class);

    }
    return tickets;
  }
}
