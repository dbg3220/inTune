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
| SPA | Single Page |


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._



### Definition of MVP
The MVP will consist of a fully functional estore with a working backend representation of relevant data types.
Any user will be able to login with a username and password or create an accoutn if they have no login. Products
checked out in their cart will be remembered between sessions and any user should be able to see all products through
a search bar.

### MVP Features
1. Create Account
2. Edit Products
3. Get products by category
4. Get product by Subcategory
5. Browse my cart
6. Save my cart
7. Save payment and Address Info
8. Learn about an Instrument
9. Browse Instrument Categories
10. Log in


### Roadmap of Enhancements
1. Renting instruments
2. Participate in the Community Blog
3. Seeing the Community Board
3. Add friends
4. See Bands that are looking for musicians


## Application Domain

This section describes the application domain.

![Domain Model](domain-model-placeholder.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the e-store application.

> _Provide a summary of the application's user interface.  Describe, from
> the user's perspective, the flow of the pages in the web application._


### View Tier
> _Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side 
> to help illustrate the end-to-end flow._


### ViewModel Tier
The ViewModel Tier is the Rest-API of our program and it consists only of controllers
for data types used in the View tier. It acts as the point of contact between our front end
and back end. It interacts with HTTP requests and responds with certain bodies and certin error
codes as responses(i.e. OK, CREATED, INTERNAL_SERVER_ERROR, CONFLICT)


### Model Tier
The model tier has Product, User, Cart, Lesson, Equipment, and Instrument classes which represent
the existence of such objects while the backend Spring Java framework is running.

### Static Code Analysis/Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
