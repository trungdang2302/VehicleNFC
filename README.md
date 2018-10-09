# VehicleNFC Rest Api
# Usage
### Order
---
- To add new Order into Server queue

`localhost:8080/order/create`

Method used: `POST`

Return: True (Success), False (Failed)

Sample JSON:
```
{
  "checkInDate": 159321874,
  "userId": 
    {
      "id": 1,
      "phone": 094291429993
    },
  "locationId":
    {
      "location": "Quang Trung"
    },
}
```
- get order by id

`localhost:8080/order/get-order/{id}`
		
```

- get all order 

`localhost:8080/order/get-order`

```
