# Prisms CMS Demo
Welcome to my web app demo. Below I will explain the work I did and my thought process.

As the job position will require Java, I figured I should get a head start. The demo is written with Java + Springboot.

## Design Assumptions
I made some design assumptions stated below, let me know if any of this
is a no go and I can make adjustments. 

* No user defined schema, data model is defined in code and not modifiable by client. Which means that if a model needs an update, we will rebuild our code and release. 
* Although our data models are related and reference each other by id (game -> scene -> character), I have chosen NOT to nest our response model. This will probably create inconvenience for the client, and if client queries are not done right, it can lead to inefficiency and trigger n+1 queries. 
* For Read, users can lookup via:
  * id of the entity (single)
  * list of ids (multiple)
* For Edit, users are required to provide an id, and can only modify one entity at a time. 
* For Create, users can only create one model at a time
* For simplicity, users cannot delete models
* Authn and Authz are outside the scope of this demo/project
* No Web Client, strictly a REST service

## Project Walk Through
With the assumptions out of the way, let's proceed to look at our project. 
1. I started with the Sprinboot documentation and used https://start.spring.io/ to bootstrap my project. 
2. Once the project has been init, I added a home controller and a landing page to ensure my build is working. I used `./gradlew bootRun` to run the web server locally on port 8080. 
3. Following the pattern encouraged by Springboot, we will be separating our concerns into:
   * Repository: Data Persistence layer
   * Service: Domain logic for business logic
   * Model: Data Shape
   * Controller: Coordination between client requests and domain logic
4. Based on the 3 model requirement, we generated the appropriate models, controllers and repositories. 
   1. The model is probably one of the most important parts of the CMS since it defines what data we can manage/update. 
   2. I skipped the service layer in this case since there isn't really any complex business logic. If we want to be more vigilant about separation of concerns, we can add the service class and also create request models + domain models, where one is strictly for client interaction and the other is for domain/business logic.  
   3. For the repository, I started by creating 3 separate repositories for each model. Each repository has its own HashMap for storage. Since Spring Boot by default has repositories scoped as singletons, they will function as in-memory storage until our server shuts down.  
5. Now we just need to set up the initialization scripts, which we have defined in `Config/DataLoader.java`. By implementing the CommandLineRunner interface, the defined code will run before the server starts receiving requests.

## Further things / Bonus
1. Currently we are just returning the data model as is without any metadata or standardized structure. As the system grows in complexity, it may make sense for us to standardize our responses with established standards (ex: JSON:API). However, standardized structures do add complexity and, in most cases, will require client-side code to stitch things back together before it can be usable for the frontend. 
2. Regarding the bonus question, with the setup we have, there is actually quite a bit of work to **ADD** a new model. We will need to add a new controller + model + repository. If reducing code is the goal here, I would try to center the configuration/modification work on our model, and make our controllers/repositories support generics. So instead of a new controller/repository for every model, it would be Controller<T>. If we want to take things a bit further, we can implement some kind of schema to define our models, and all the code will be generated based on that given schema. 
3. I didn't focus too much time on validations. If I have more time, I will add validations to the model. It is very much needed since user can create a character/scene/graph that very much does not exist on its dependency. I will focus on adding the validations on the model, eiter via library or just as part of the setter. 

## Misc/Learnings/Notes
To cap off, I had a good time building out the springboot application, was pleasantly suprise on how easy to get started and shortcut it took on conventions. 

* Java packages are kind of similar to C# namespaces
* SDKMAN is pretty nice; I was able to switch to a lower version of Java after I ran into build issues (https://sdkman.io/). I ran into more issues with my MacBook, so I decided to move my project over to GitHub dev containers
* Explicit getters and setters are a bit terse; I definitely miss the C# syntax sugar of `{get;set;}`
* I couldn't decide between Gradle vs Maven; I ended up choosing Gradle because it has more functionality
* Spring Boot's DI seems to encourage certain design practices. It does feel strange to reference the actual implementation class during constructor injection
* Generic syntax takes some time to get used to; `<T> T methodName()` looks a bit confusing at first
* I added Swagger to make exploring the API a breeze; setting it up was fairly straightforward 