package edu.drexel.trainsim.order.db;

import com.google.inject.Inject;
import edu.drexel.trainsim.order.models.Traveler;
import org.sql2o.Sql2o;

public class GetOrCreateTravelerImpl implements GetOrCreateTraveler
{
  private final Sql2o db;

  @Inject
  public GetOrCreateTravelerImpl(Sql2o db)
  {
    this.db = db;
  }

  @Override
  public Traveler call(Traveler traveler)
  {
    String sql = "SELECT id, firstName, lastName, email, phone" +
                 "  FROM travelers" +
                 " WHERE firstName = :firstName AND lastName = :lastName AND email = :email AND phone = :phone";
    String insertSql = "INSERT INTO travelers(firstName, lastName, email, phone) VALUES(:firstName, :lastName, :email, :phone)";

    try (var con = this.db.open())
    {
      var res = con.createQuery(sql).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeAndFetch(Traveler.class);

      // There is a race condition here if we have more than one servers talking to the db.
      if (res.isEmpty())
      {
        con.createQuery(insertSql).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeUpdate();
        res = con.createQuery(sql).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeAndFetch(Traveler.class);
      }

      return res.get(0);
    }
  }
}
