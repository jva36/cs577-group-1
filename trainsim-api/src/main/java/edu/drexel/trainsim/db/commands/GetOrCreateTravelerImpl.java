package edu.drexel.trainsim.db.commands;

import com.google.inject.Inject;
import edu.drexel.trainsim.db.models.Traveler;
import edu.drexel.trainsim.db.models.User;
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

    try (var con = this.db.open())
    {
      var res = con.createQuery(sql).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeAndFetch(Traveler.class);

      // There is a race condition here if we have more than one servers talking to the db.
      if (res.isEmpty())
      {
        String sql1 = "INSERT INTO travelers(firstName, lastName, email, phone) VALUES(:firstName, :lastName, :email, :phone) RETURNING *";
//        sql = "INSERT INTO travelers(firstName, lastName, email, phone) VALUES(:firstName, :lastName, :email, :phone) RETURNING id, firstName, lastName, email, phone";

        con.createQuery(sql1).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeUpdate();
        res = con.createQuery(sql).addParameter("firstName", traveler.getFirstName()).addParameter("lastName", traveler.getLastName()).addParameter("email", traveler.getEmail()).addParameter("phone", traveler.getPhone()).executeAndFetch(Traveler.class);
      }

      return res.get(0);
    }
  }
}
