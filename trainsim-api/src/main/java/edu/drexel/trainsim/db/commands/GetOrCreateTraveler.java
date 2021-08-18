package edu.drexel.trainsim.db.commands;

import edu.drexel.trainsim.db.models.Traveler;

@FunctionalInterface
public interface GetOrCreateTraveler
{
//  Traveler call(String firstName, String lastName, String email, int phone);
  Traveler call(Traveler traveler);
}
