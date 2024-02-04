# Amortisation Scheduler (Technical Challenge)
[Submission by Ana Landicho, Feb 2024]

## DESCRIPTION
REST API implementation for creating an amortisation schedule for an asset being financed with the following requirements:
1. Create the amortisation schedule for a provided set of loan details (both with and without a balloon payment)
2. List previously created schedules, returning:
   - The details that were used to generate the schedule
   - The monthly repayment amount that was calculated
   - The total interest due
   - The total payments due
3. Retrieve the full details of an individual schedule, returning:
   - The contents of the list API for the schedule
   - The amortisation schedule that was prepared

### API

| Resource | Input                                                                                       | Output                                                                                                                                                                                                                              | Desc  |
|----------|---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------|
| `PUT /schedules/create`  | `costAmount`, `depositAmount`, `yearInterestRAte`,`numberOfMonthlyPayments`,`balloonAmount` | Creation confirmation obj                                                                                                                                                                                                           | desc1 |
| `GET /schedules/view/all` | n/a                                                                                         | List of PreviousSchedulesDto with following fields: `loanAssetId`, `costAmount`, `depositAmount` `yearInterestRate`, `numberOfMonthlyPayments`, `balloonAmount`, `calculatedRepaymentAmount`, `totalInterestDue`, `totalPaymentDue` | desc2 |
| `GET /schedules/view/{assetId}` | `assetId` as query string param                                                             | RetrieveIndividualScheduleDto object containing fields:    ` details` `amortisationSchedule`                                                                                                                                        | desc3 |


## RUNNING THE APP
- Run in an IDE from main file:
`src/main/java/com/analandicho/AmortisationScheduler/AmortisationSchedulerApplication.java`

- Run on terminal using maven (from root project directory)
```shell
./mvnw spring-boot:run
```

## TESTS

Tests can be found in: `src/test`





## Miscellaneous

### Project Details

Project initialised with https://start.spring.io/ using following configurations:
- Project: Maven
- Language: Java 17 
- Spring Boot version: 3.2.2 
- Dependencies:
  - Spring Web 
  - Spring JPA 
  - H2 DB


### Development Platforms
IDE: IntelliJ IDEA 2023.2 (Community Edition) Runtime version: 17.0.7+7-b1000.6 aarch64 VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
OS: macOS 14.1.1

  