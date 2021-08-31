package edu.drexel.trainsim.web;

import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;

import edu.drexel.trainsim.user.commands.GetOrCreateGoogleUser;
import edu.drexel.trainsim.order.models.*;
import edu.drexel.trainsim.order.db.GetOrCreateOrder;
import edu.drexel.trainsim.order.db.GetOrCreateTicket;
import edu.drexel.trainsim.order.db.GetOrCreateTraveler;

public class OrderController implements Controller
{
  private final GetOrCreateOrder cmdCreateOrder;
  private final GetOrCreateTicket cmdCreateTicket;
  private final GetOrCreateTraveler cmdCreateTraveler;
  private final GetOrCreateGoogleUser cmdCreateUser;

  @Inject
  public OrderController(GetOrCreateOrder cmdCreateOrder, GetOrCreateTicket cmdCreateTicket, GetOrCreateTraveler cmdCreateTraveler, GetOrCreateGoogleUser cmdCreateUser)
  {
    this.cmdCreateOrder = cmdCreateOrder;
    this.cmdCreateTicket = cmdCreateTicket;
    this.cmdCreateTraveler = cmdCreateTraveler;
    this.cmdCreateUser = cmdCreateUser;
  }

  public void bindRoutes(Javalin app)
  {
    app.post("/api/order/checkout", ctx -> this.getTravlerByInfo(ctx));
  }

  private void getTravlerByInfo(Context ctx)
  {
    var request = ctx.bodyAsClass(CheckOutRequest.class);
    int userID = request.getUserID();
    if (userID < 1)
      userID = cmdCreateUser.call(request.getTravelers().get(0).getEmail(), "Guest").getId();

    Order order = cmdCreateOrder.create(userID, request.getAddress().toString());
    List<Traveler> travelers = new ArrayList();
    for (Traveler traveler : request.getTravelers())
      travelers.add(cmdCreateTraveler.call(traveler));
    List<Ticket> tickets = cmdCreateTicket.create(order.getId(), travelers, request.getTickets());
    ctx.json(new CheckOutResponse(order.getId(), request.getUserID(), tickets));
  }
}
