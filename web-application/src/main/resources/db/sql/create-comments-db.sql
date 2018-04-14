CREATE TABLE comments (
  id         INTEGER PRIMARY KEY,
  postid INTEGER,
  theComment VARCHAR(512),
  author VARCHAR(32)
  );