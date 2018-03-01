CREATE TABLE IF NOT EXISTS AccessLog
( id INTEGER AUTO_INCREMENT PRIMARY KEY,
  eventTime TIMESTAMP(3),
  originIp VARCHAR(20),
  httpMethod VARCHAR(10),
  httpResponse SMALLINT,
  userAgentRequestHeader VARCHAR(1024)
)

