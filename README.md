#Alien Game Application

#Requirements: 
Java SDK 9

Lombok Plug in

#UserController

####getUsers()

Getting all the users in the database.

####getUser(Integer)

Getting the user with specific id.

####addUser(User)

Add the user.

####updateUser(User,id)

Replace the user with given id with given user.

####addScore(Integer,Integer)

Add the given score to the user with given id.


####getUsersScore()

Getting the score of user with given id.

####getScores()

Getting all the scores in the database.

####getWeeklyScores()

Getting all the scores in a specific week.

####deleteUser(Integer)

Delete the user with given id.


#ScoreRepository

####getLeaderBoard()

Getting leader board according to the scores all time.

####getLeaderBoardWeekly()

Getting leader board according to the scores for weekly.

####generateResponse(String)

Generating a response with a given query whose type is String.

#UserRepository

####findByEmail(String)

Getting the user with specific email.

#UserService

####getAllUsers()

Getting all the users in the database.

####getUser(Integer)

Getting the user with specific id from repository.

####addUser(User)

Add user to the repository.

####updateUser(User,id)

Replace the user with given id with given user.

####addScore(Integer,Integer)

Add the given score to the user with given id.


####getUsersScore()

Getting the score of user with given id.

####getScores()

Getting all the scores in the database.

####getWeeklyScores()

Getting all the scores in a specific week.

####deleteUser(Integer)

Delete the user with given id.