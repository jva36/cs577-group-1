package edu.drexel.trainsim.order.db;

import com.google.inject.Inject;
import org.sql2o.Sql2o;

import edu.drexel.trainsim.order.models.Order;

public class GetOrCreateOrderImpl implements GetOrCreateOrder
{
  private final Sql2o db;

  @Inject
  public GetOrCreateOrderImpl(Sql2o db)
  {
    this.db = db;
  }

  @Override
  public Order create(int userID, String address)
  {
    try (var con = this.db.open())
    {
      con.createQuery("INSERT INTO orders(userID, address) VALUES(:userID, :address)").addParameter("userID", userID).addParameter("address", address).executeUpdate();
      String sql = "SELECT id, userID, status, createdTime" +
                   "  FROM orders" +
                   " WHERE userID = :userID AND address = :address" +
                   " ORDER BY createdTime DESC";
      var res = con.createQuery(sql).addParameter("userID", userID).addParameter("address", address).executeAndFetch(Order.class);

      return res.get(0);
    }
  }
}
