CREATE TABLE users (
  id         INTEGER PRIMARY KEY,
  username VARCHAR(32),
  password  VARCHAR(32),
  question VARCHAR(32),
  answer VARCHAR(32),
  habit VARCHAR(64),
  isUser INTEGER,
  isAdmin INTEGER
);