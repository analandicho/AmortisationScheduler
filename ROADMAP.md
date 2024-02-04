1. Decimal precision calculation when creating schedule:
Example: Amortisation schedule for the one with balloon payment of 10000. Last remaining balance is coming off as 9999.99 instead of 10000.
```
        {
            "period": 12,
            "payment": 930.08,
            "principal": 862.19,
            "interest": 67.89,
            "balance": 9999.99
        }
```

2. Simplify retrieval logic of INDIVIDUAL SCHEDULE endpoint.
3. Security of resources / endpoints - Authentication/Auth
4. Additional logging ?

