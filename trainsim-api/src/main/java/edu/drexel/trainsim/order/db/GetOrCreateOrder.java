package edu.drexel.trainsim.order.db;

import edu.drexel.trainsim.order.models.Order;

@FunctionalInterface
public interface GetOrCreateOrder
{
  Order create(int userID, String address);
}
