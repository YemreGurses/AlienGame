#Allien Game Application

#Requirements: 
Java SDK 9

Lombok Plug in

#Security Configuration

####configure(AuthenticationManagerBuilder)

Allowing authentication with Spring security to move that to JDBC, defining a data source within the application. 

####configure(HttpSecurity)

Spring security generates a login page automatically, based on the features that are enabled and using standard values for the URL. Configuring some simple authorization on each URL using roles.

####configure(WebSecurity)



#WebMvcConfig

passwordEncoder()

Defining the simple BCryptPasswordEncoder as a bean in our configuration.

#LoginController

####@GET login()

Login function to let registered users to log in to our application.

####@GET registration()

Registration function for register new users. 

####@POST createNewUser(User, BindingResult)

If the user wanted to be registered is already exists in the database, exception throws.
Else save the user.

####@GET home()

Home page function for registered users. Handles authentication also.

#UserController

####getUsers()

Getting all the users in the database.

####getUsers(Integer)

Getting the user with specific id.

####getScores()

Getting all the scores in the database.

####getWeeklyScores()

Getting all the scores in a specific week.

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

####saveUser(User)

Handles the saving the the user to the repository.

