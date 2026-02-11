## ORDERLY- Order Management Service (Spring Boot + Kafka)

This app is a simple **order management** REST service that **produces Kafka events** for each CRUD operation.  
Later you can add Go consumers that subscribe to the topics and do calculations/analytics.

### REST API

Base path: `/api/orders`

- **Create order**
  - **POST** `/api/orders`
  - Body:

```json
{
  "customerId": "cust-123",
  "items": ["item-1", "item-2"],
  "totalAmount": 99.99
}
```

- **Get order by id**
  - **GET** `/api/orders/{id}`

- **List all orders**
  - **GET** `/api/orders`

- **Update order**
  - **PUT** `/api/orders/{id}`
  - Body (all fields optional; only send what you want to change):

```json
{
  "items": ["item-1", "item-3"],
  "totalAmount": 120.50,
  "status": "PAID"
}
```

- **Delete order**
  - **DELETE** `/api/orders/{id}`

### Kafka Topics

- **Produced topic**: `orders.events`
  - Key: `orderId` (string)
  - Value: JSON `OrderEvent`:

```json
{
  "type": "ORDER_CREATED",
  "orderId": "8dd5...",
  "customerId": "cust-123",
  "items": ["item-1", "item-2"],
  "totalAmount": 99.99,
  "status": "NEW",
  "previousStatus": null,
  "occurredAt": "2026-02-10T12:34:56.789Z",
  "notificationMessage": "Order 8dd5... created for customer cust-123"
}
```

`type` can be one of: `ORDER_CREATED`, `ORDER_UPDATED`, `ORDER_DELETED`.

This JSON is friendly for Go consumers (no Java-specific type headers).

### Configuration

- Kafka bootstrap servers are configured for the **multi-node** cluster (3 brokers, EXTERNAL listeners):

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
```

- `localhost:9092` → broker-1, `localhost:9093` → broker-2, `localhost:9094` → broker-3  
- Override in `application-local.yml` (or env) if your cluster uses different addresses.

