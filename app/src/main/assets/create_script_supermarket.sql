CREATE TABLE IF NOT EXISTS products (
    localId             INTEGER PRIMARY KEY AUTOINCREMENT,
    id                  INTEGER,
    filename            TEXT,
    price               DOUBLE,
    rating              INT,
    description         TEXT,
    title               TEXT,
    type                TEXT
);
