# Technical-Test

Technical test for BCNC GROUP

## Index

- Requeriments
- Database model
- How to run it
- Endpoints
- Events

## Requeriments:

- Java (OpenJDK 19.0.2)

## Database model:

![aa](https://i.gyazo.com/a598dc6c8622967fbbdd39293026d285.png)

## How to run it:

### Run Application:

```
gradlew run
```

### Run unitTests:

```
gradlew test
```

### Run integrationTests:

```
gradlew integrationTest
```

## Endpoints

GET - /{brandCode}/{productCode}/{currency}/pvp

#### Parameters

> | name          | type   | description                                                                        | exemple                              |
> | ------------- | ------ | ---------------------------------------------------------------------------------- | ------------------------------------ |
> | brandCode     | UUID   | Brand code identify                                                                | ec2ae97c-3f2d-44e4-a195-c17ef510bfab |
> | productCode   | UUID   | Product code identify                                                              | 48bd8f04-f983-4718-b918-d788e11dcade |
> | currency      | String | The currency for the product price                                                 | EUR                                  |
> | `currentTime` | `none` | `Internally we get the current time of the interaction, for security is not param` | `none`                               |

#### Responses

> | name            | type  | description                                                            | exemple                              |
> | --------------- | ----- | ---------------------------------------------------------------------- | ------------------------------------ |
> | price           | float | Price of the product with out apply promotion                          | 10.3                                 |
> | offApplied      | int   | Percentage of discount in the product, can be 0 if not found promotion | 12                                   |
> | pvp             | float | Price of the product with applied promotion                            | 12                                   |
> | promotionCode   | UUID  | Promotion code identify                                                | 4500a4c5-4ae3-4482-b672-95cef86dd40a |
> | applicationTime | Date  | Time of request product (WIP to make it the remaining time)            | 2023-02-08T00:28:23.4416667          |

## Events

Currently no event is consumed or produced.
