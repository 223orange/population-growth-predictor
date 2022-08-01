# population-growth-predictor

Web service which provides information about the expected population for a given country and ranks the top 20 countries,
ordered by population.

It uses the official API of the United Nations to fetch data: https://population.un.org/dataportal/about/dataapi#introduction

Basically, the service provides two endpoints and data which is returned to the user is in JSON format:

```
population_predictor/api/v1/expected-population/{locationName}/{startYear}/{endYear}
```

```
population_predictor/api/v1/ranking/{year}
```

Once you launch the application, you can access the API documentation from here:

```
http://localhost:8080/swagger-ui.html
```