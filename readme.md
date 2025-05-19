# Prisms CMS Demo
Welcome to my web app demo, below I will explain the work I did and my thought process.

As the job position will require Java, I figured I should get a head start. The demo is written with Java + Springboot.

## Design Assumptions
I made some design assumptions stated below, let me know if any of this
is a no go and I can make adjustments. 

* No user defined schema, data model is defined in code and not modifable by client. Which means that if a model needs an update, we will rebuild our code and release. 
* Although our data models are related and referencing each other by id (game -> scene -> character), I have chosen NOT to nest our response model. This will probably create inconvenience for the client, and if client query is not done right, it can lead to inefficiency and trigger n+1 query. 
* For Read, user can lookup via
  * id of the entity (single)
  * list of id (multiple)
* For Edit, user are required to provide an id, and can only modify one at a time. 
* For Create, user can only create one model at a time
* For simplicity, user cannot delete a model
* Authn nor Authz is outside of the scope of this demo/project
* No Web Client, straightly a REST service

## Project Walk Through
With the assumption out of the way, Let's proceed to look at our project. 
1. I started at the springboot doc and used https://start.spring.io/ to bootstrap my project. 
2. Once the project has been init, I added a home controller and a landing page to ensure my build is working. I used `./gradlew bootRun` to run the web server locally on port 8080. 
3. Following the pattern encouraged by springboot, we will be separating our concerns into:
  * Repository: Data Persistence layer
  * Service: Domain logic for business logic
  * Model: Data Shape
  * Controller: Coordinate between client request and domain logic
4. Based on the 3 model requirement, we generated the appropriate model, controller and repository. 
   1. The model is probably one of the most important part of the CMS since that define what data we can manage/update. 
   2. I skipped service layer in this case since there aren't really any business logic. If we want to be more vigilant about separation of concern, we can add the service class and also create request model + domain model, where one is straight for client and the other is for domain/business logic.  
   3. For the repository, at first I started with creating 3 separate repository for each of the model, however none of them has any unqiue query right now, so I opted for a simpler approach where we have one repository class, and based on the generic class being passed in, it will go to its appropriate location in the hashmap. 
5. now we just need to standup the scripts, which we have defined in `Config/DataLoader.java`. By implmeneting the CommandLineRunner, the defined code will run before the server start receiving requests.
6. 

## Further things / Bonus
1. Currently we are just returning the data model as is without any meta data or standardize structure. As system grow in complexity, it may make sense for us to use standardize our responses with standards (ex: JSONAPI). However the standardize structure does add complexity and for most of the time, it will require clientside to stiches things back together before it can be usable for the frontend. 
2.  

## Misc/learnings/Notes
* packages is kinda similar to C# namespaces
* sdkman is pretty nice, was able to switch to lower version of Java after I ran into build issues https://sdkman.io/. I ran into more issues with my macbook, so I decided to move my project over to github dev containers
* Explicit getter and setter is a bit terse, definitly miss sytnax sugar `{get;set;}`
* I couldn't decide between gradle vs maven, I end up choosing gradle just because it has more functionality
* Springboot DI seems to encourage certain design practice. It does feel off to reference the actual implementation class during ctor injection
* Generic syntax takes some time to get used to, <T> T MethodName looks a confusing at first.