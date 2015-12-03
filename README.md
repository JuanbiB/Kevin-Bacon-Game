# Kevin-Bacon-Game

To put it shortly:
Six Degrees of Kevin Bacon is a parlour game based on the "six degrees of separation" concept, which posits that any two people on Earth 
are six or fewer acquaintance links apart.

Our particular implementation used the entire MDB database in order to find how many degrees of connection (the connection being
a movie) are between Kevin Bacon and a chosen actor. You can also change the "center", finding connections (if any) between any two
actors.

This uses one of the most famous graph traversal algorithms, Dijkstra's algorithm.
Implemented for a CS course.
