CREATE SCHEMA otp;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    type TEXT NOT NULL
);

CREATE TABLE travelers (
    id SERIAL PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    CONSTRAINT unq_travelers UNIQUE(firstName, lastName, email, phone)
);

CREATE TABLE otp.stops (
    id SERIAL PRIMARY KEY,
    otp_id VARCHAR(64) UNIQUE NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE otp.routes (
    id SERIAL PRIMARY KEY,
    otp_id VARCHAR(64) UNIQUE NOT NULL,
    name TEXT NOT NULL,
    short_name TEXT NOT NULL,
    mode VARCHAR(64) NOT NULL,
    color CHAR(6)
);

CREATE TABLE otp.itineraries (
    id UUID PRIMARY KEY
);

CREATE TABLE otp.legs (
    id UUID PRIMARY KEY,
    itinerary_id UUID REFERENCES otp.itineraries (id) NOT NULL,
    -- route_id will be null if this is a walking leg.
    route_id INT REFERENCES otp.routes (id),
    sort INT NOT NULL,
    distance DOUBLE PRECISION NOT NULL
);

CREATE TABLE otp.places (
    id UUID PRIMARY KEY,
    leg_id UUID REFERENCES otp.places (id) NOT NULL,
    stop_id INT REFERENCES otp.stops (id) NOT NULL,
    sort INT NOT NULL,
    arrive_at TIMESTAMP WITH TIME ZONE NOT NULL,
    depart_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    userID int REFERENCES users (id) NOT NULL,
    status TEXT NOT NULL DEFAULT 'Paid',
    createdTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updatedTime TIMESTAMP,
    address TEXT NOT NULL
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    orderID INT REFERENCES orders (id) NOT NULL,
    travelerID INT REFERENCES travelers (id) NOT NULL,
    itineraryID UUID NOT NULL,
--    itineraryID UUID REFERENCES otp.itineraries (id) NOT NULL,
    price REAL NOT NULL
);

CREATE TYPE otp.api_stop AS (id TEXT, name TEXT);

CREATE PROCEDURE otp.load_stops(otp_data json)
LANGUAGE SQL
AS $$
INSERT INTO otp.stops (otp_id, name)
SELECT id AS otp_id, name
FROM json_populate_recordset(null::otp.api_stop, otp_data);
$$;

CREATE TYPE otp.api_route_short AS (
    id TEXT,
    "longName" TEXT,
    "shortName" TEXT,
    mode TEXT,
    color TEXT
);

CREATE PROCEDURE otp.load_routes(otp_data json)
LANGUAGE SQL
AS $$
INSERT INTO otp.routes (otp_id, name, short_name, mode, color)
SELECT
    id AS otp_id,
    "longName" AS name,
    "shortName" AS short_name,
    mode,
    color
FROM json_populate_recordset(null::otp.api_route_short, otp_data);
$$;