package edu.drexel.trainsim.order.db;

import edu.drexel.trainsim.order.models.Traveler;

@FunctionalInterface
public interface GetOrCreateTraveler
{
  Traveler call(Traveler traveler);
}
