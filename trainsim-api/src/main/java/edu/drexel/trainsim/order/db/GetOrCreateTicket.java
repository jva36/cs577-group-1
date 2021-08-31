package edu.drexel.trainsim.order.db;

import java.util.List;
import java.util.UUID;

import edu.drexel.trainsim.order.models.Ticket;
import edu.drexel.trainsim.order.models.Traveler;

@FunctionalInterface
public interface GetOrCreateTicket
{
  List<Ticket> create(int orderID, List<Traveler> travelers, UUID itineraryID, float price);
}
