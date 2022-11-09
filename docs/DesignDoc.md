---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: In-Tune
* Team members
  * Hayden Cieniawski
  * Clayton Acheson
  * Jonathan Zhu
  * Donovan Cataldo
  * Damon Gonzalez

## Executive Summary

To create a web store that allows users to rent and purchase instruments, as well as partake in lessons. Users will be able to log into the website with an email and password, and then have payment and shipping information saved.

### Purpose
To allow customers to interact with the full inventory and capability of the music store from
a personal computer.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA  | Single Page|

## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

Any user who is not the admin will be able to log in using their username and be presented with a home page where they
can browse products in multiple ways(search bar, top products, by category). They will be able to add any product in
the store to their cart with a requested quantity that is equal to or less than the amount the store has in inventory.
If a user logs out than the products in their cart will be retained. The admin will have the ability to login with the
username 'admin' and be presented with a home page similar to that of a regular user but will include the ability to edit
the inventory of the store.


### Definition of MVP
The MVP will consist of a fully functional estore with a working backend representation of relevant data types.
Any user will be able to login with a username and password or create an account if they have no login. Products
checked out in their cart will be remembered between sessions and any user should be able to see all products through
a search bar.

### MVP Features
1. Create Account
2. Edit Products
3. Get products by category
4. Browse my cart
5. Save my cart
6. Save payment and Address Info
7. Learn about an Instrument
8. Browse Instrument Categories
9. Log in
10. Search for an Instrument
11. Add an Instrument to my cart
12. Checkout my cart
13. Add a review
14. View all reviews
15. Look at all Lessons
16. Schedule a Lesson


### Roadmap of Enhancements
1. Add a review
2. View all reviews
3. Look at all Lessons
4. Schedule a Lesson
5. admin privileges for Lesson



## Application Domain

This section describes the application domain.

![Domain Model](domain-model-placeholder.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._


## Architecture and Design

The application architecture will be separated into 2 distinct components, an angular frontend implementation
and a Spring API backend implementation. 
Frontend:
Backend: The Spring API will implement a Model, View, View_Model structure that will use a local database
in the form of json files, 1 json file for each type of object being handled by the backend.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistence. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. 
The ViewModel provides RESTful APIs to the client (View) as well as any logic required to 
manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface
On landing, the user will be displayed with a selection of the most popular products by name. This will herein be referred to as the dashboard. From here, they will also be able to see, at the header, there will be four options. These include the Dashboard, Products, Login, and Cart. Products will present the user with a list of all current products available along with their photo, name, and price. There is a search bar on this page that allows users to look up products by first name. From here, a user can click on any product to be taken to that products detail page. Here, the user will be able to add the product to their unique cart (if logged in). If they do not have an account/are not logged in, they will be informed that they need to log in. Otherwise, the product can be added to their cart. The login page is a simple page prompting for users to enter their username. A success page will be followed once the user is signed in. Finally, there is a cart page, it is here that users can see what products they have in their cart and how many of each.


### View Tier
> _Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP requests from the client-side to the server-side 
> to help illustrate the end-to-end flow._


### ViewModel Tier
The ViewModel Tier is the Rest-API of our program and it consists only of controllers
for data types used in the View tier. It acts as the point of contact between our front end
and back end. It interacts with HTTP requests and responds with certain bodies and certain error
codes as responses(i.e. OK, CREATED, INTERNAL_SERVER_ERROR, CONFLICT)


### Model Tier
The Model Tier has Product, User, Cart classes which represent the data being stored in the
local database. They represent little functionality of the application besides what is needed
to maintain their internal state.

### Static Code Analysis/Design Improvements

## Testing
Currently only 85% of backend tests pass.

### Acceptance Testing
Almost all acceptance tests pass. The only issues that we have run into is that users are unable to
save their cart between sessions. This is because we are using a local database while cart is still being
worked on by the frontend team. Besides that shortcoming, all other acceptance tests pass.

### Unit Testing and Code Coverage

