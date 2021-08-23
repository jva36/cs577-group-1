package edu.drexel.trainsim.web;

import com.google.inject.Inject;
import edu.drexel.trainsim.order.db.GetOrCreateTraveler;
import edu.drexel.trainsim.order.models.Traveler;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class TravelerController implements Controller
{
  private final GetOrCreateTraveler getOrCreateTraveler;

  @Inject
  public TravelerController(GetOrCreateTraveler cmd)
  {
    this.getOrCreateTraveler = cmd;
  }

  public void bindRoutes(Javalin app)
  {
    app.post("/api/traveler", ctx -> this.getTravlerByInfo(ctx));
  }

  private void getTravlerByInfo(Context ctx)
  {
    // Notice that there is absolutely no server-side validation that this is real signed-in Google user.
    // We have to make a call to a Google API to verify this.
    ctx.json(getOrCreateTraveler.call(ctx.bodyAsClass(Traveler.class)));
  }
}
