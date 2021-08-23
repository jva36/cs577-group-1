package edu.drexel.trainsim.order;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.drexel.trainsim.itinerary.db.GetAllStops;
import edu.drexel.trainsim.itinerary.db.GetAllStopsImpl;
import edu.drexel.trainsim.order.db.*;
import org.sql2o.Sql2o;

public class OrderModule extends AbstractModule {
    private final Sql2o db;
    private Gson gson;
    
    public OrderModule(HikariConfig config) {
        this.db = new Sql2o(new HikariDataSource(config));
        this.gson = new Gson();
    }

    @Override
    protected void configure() {
        bind(GetAllStops.class).to(GetAllStopsImpl.class);
        bind(GetOrCreateOrder.class).to(GetOrCreateOrderImpl.class);
        bind(GetOrCreateTicket.class).to(GetOrCreateTicketImpl.class);
        bind(GetOrCreateTraveler.class).to(GetOrCreateTravelerImpl.class);
    }

    @Provides
    public Sql2o getDb() {
        return this.db;
    }

    @Provides
    public Gson getGson() {
        return this.gson; 
    }
}
