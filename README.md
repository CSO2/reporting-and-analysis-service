# 📊 Reporting and Analysis Service

> Admin dashboard, sales analytics, and inventory reports for the CS02 E-Commerce Platform

## 📋 Overview

The Reporting and Analysis Service provides comprehensive analytics and reporting capabilities for the admin dashboard. It aggregates data from various services to provide insights into sales performance, inventory levels, and customer behavior.

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| Language | Java | 17 |
| Framework | Spring Boot | 4.0.0 |
| Database | PostgreSQL | 15 |
| Batch Processing | Spring Batch | Latest |
| Message Queue | RabbitMQ | Latest |
| Build Tool | Maven | 3.x |
| ORM | Spring Data JPA | Latest |

## 🚀 API Endpoints

### Dashboard

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/analytics/dashboard` | Admin | Get dashboard metrics |
| `GET` | `/api/analytics/dashboard?start={date}&end={date}` | Admin | Get metrics for date range |

### Sales Analytics

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/analytics/sales` | Admin | Get sales summary |
| `GET` | `/api/analytics/sales/daily` | Admin | Get daily sales data |
| `GET` | `/api/analytics/sales/monthly` | Admin | Get monthly sales data |
| `GET` | `/api/analytics/top-products` | Admin | Get top selling products |
| `GET` | `/api/analytics/top-categories` | Admin | Get top performing categories |

### Inventory Reports

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/analytics/inventory` | Admin | Get inventory overview |
| `GET` | `/api/analytics/inventory/low-stock` | Admin | Get low stock items |
| `GET` | `/api/analytics/inventory/turnover` | Admin | Get inventory turnover |

### Customer Analytics

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/analytics/customers` | Admin | Get customer metrics |
| `GET` | `/api/analytics/customers/new` | Admin | Get new customer count |
| `GET` | `/api/analytics/customers/retention` | Admin | Get retention rate |

## 📊 Data Models

### DailySales

```java
{
  "id": "uuid",
  "date": "2024-01-15",
  "totalOrders": 125,
  "totalRevenue": 45678.90,
  "totalItems": 312,
  "averageOrderValue": 365.43,
  "newCustomers": 23,
  "returningCustomers": 102
}
```

### ProductPerformance

```java
{
  "id": "uuid",
  "productId": "string",
  "productName": "NVIDIA RTX 4090",
  "category": "graphics-cards",
  "totalSold": 156,
  "totalRevenue": 249243.44,
  "averageRating": 4.8,
  "period": "2024-01"
}
```

### DashboardMetrics

```java
{
  "totalRevenue": 156789.00,
  "totalOrders": 423,
  "averageOrderValue": 370.65,
  "newCustomers": 89,
  "pendingOrders": 12,
  "lowStockItems": 5,
  "topProducts": [ProductPerformance],
  "revenueByDay": [DailySales],
  "revenueGrowth": 12.5,
  "orderGrowth": 8.3
}
```

### InventoryReport

```java
{
  "totalProducts": 1256,
  "totalValue": 2345678.90,
  "lowStockCount": 23,
  "outOfStockCount": 5,
  "categorySummary": [
    {
      "category": "graphics-cards",
      "productCount": 45,
      "totalStock": 320,
      "stockValue": 567890.00
    }
  ]
}
```

## 🔧 Configuration

### Application Properties

```yaml
server:
  port: 8088

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/CSO2_reporting_and_analysis_service
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  batch:
    job:
      enabled: false

analytics:
  aggregation:
    cron: "0 0 1 * * ?"  # Daily at 1 AM
```

### Environment Variables

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | No | `jdbc:postgresql://localhost:5432/CSO2_reporting_and_analysis_service` | Database URL |
| `SPRING_DATASOURCE_USERNAME` | No | `postgres` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | No | `postgres` | Database password |
| `SPRING_RABBITMQ_HOST` | No | `localhost` | RabbitMQ host |
| `SERVER_PORT` | No | `8088` | Service port |

## 📦 Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-batch</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## 🔄 Data Aggregation

### RabbitMQ Event Consumption

The service consumes events from other services to aggregate analytics data:

| Queue | Event | Action |
|-------|-------|--------|
| `order-completed` | Order completed | Update daily sales |
| `product-sold` | Product sold | Update product performance |
| `user-registered` | New user | Update customer metrics |
| `inventory-updated` | Stock change | Update inventory reports |

### Spring Batch Jobs

| Job | Schedule | Description |
|-----|----------|-------------|
| `dailySalesAggregation` | 1:00 AM | Aggregate daily sales |
| `monthlyReportGeneration` | 1st of month | Generate monthly reports |
| `inventorySnapshot` | 6:00 AM | Snapshot inventory levels |

## 🏃 Running the Service

### Local Development

```bash
cd backend/reporting-and-analysis-service

# Using Maven Wrapper
./mvnw spring-boot:run

# Or with Maven
mvn spring-boot:run
```

### Docker

```bash
cd backend/reporting-and-analysis-service

# Build JAR
./mvnw clean package -DskipTests

# Build Docker image
docker build -t cs02/reporting-analytics-service .

# Run container
docker run -p 8088:8088 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/CSO2_reporting_and_analysis_service \
  -e SPRING_RABBITMQ_HOST=rabbitmq \
  cs02/reporting-analytics-service
```

## 🗄️ Database Requirements

- **PostgreSQL** running on port `5432`
- Database: `CSO2_reporting_and_analysis_service`
- Tables auto-created via JPA

### Database Schema

```sql
-- daily_sales table
CREATE TABLE daily_sales (
    id UUID PRIMARY KEY,
    date DATE UNIQUE NOT NULL,
    total_orders INTEGER,
    total_revenue DECIMAL(12,2),
    total_items INTEGER,
    average_order_value DECIMAL(10,2),
    new_customers INTEGER,
    returning_customers INTEGER,
    created_at TIMESTAMP
);

-- product_performance table
CREATE TABLE product_performance (
    id UUID PRIMARY KEY,
    product_id VARCHAR(100),
    product_name VARCHAR(255),
    category VARCHAR(100),
    total_sold INTEGER,
    total_revenue DECIMAL(12,2),
    average_rating DECIMAL(3,2),
    period VARCHAR(10),
    UNIQUE(product_id, period)
);
```

## ✅ Features - Completion Status

| Feature | Status | Notes |
|---------|--------|-------|
| Dashboard metrics | ✅ Complete | KPI overview |
| Daily sales aggregation | ✅ Complete | Daily totals |
| Top selling products | ✅ Complete | Revenue-based ranking |
| Revenue tracking | ✅ Complete | Total and by period |
| Data ingestion service | ✅ Complete | Event consumption |
| Date range filtering | ✅ Complete | Custom date queries |
| Revenue growth calculation | ✅ Complete | Period comparison |

### **Overall Completion: 70%** ✅

## ❌ Not Implemented / Future Enhancements

| Feature | Priority | Notes |
|---------|----------|-------|
| Real-time data aggregation | Medium | Currently batch-based |
| Inventory reports (full) | Medium | Placeholder data |
| Customer analytics | Medium | Basic implementation |
| Export to CSV/Excel | Medium | Report downloads |
| Email scheduled reports | Low | Automated reporting |
| Custom report builder | Low | User-defined reports |
| Trend analysis | Low | Historical comparisons |
| Forecasting | Low | Predictive analytics |
| Grafana integration | Low | Visual dashboards |

## 📁 Project Structure

```
reporting-and-analysis-service/
├── src/
│   ├── main/
│   │   ├── java/com/cs02/reporting/
│   │   │   ├── ReportingServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── SalesController.java
│   │   │   │   └── InventoryController.java
│   │   │   ├── model/
│   │   │   │   ├── DailySales.java
│   │   │   │   └── ProductPerformance.java
│   │   │   ├── repository/
│   │   │   │   ├── DailySalesRepository.java
│   │   │   │   └── ProductPerformanceRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AnalyticsService.java
│   │   │   │   └── DataIngestionService.java
│   │   │   ├── batch/
│   │   │   │   └── SalesAggregationJob.java
│   │   │   └── listener/
│   │   │       └── EventListener.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
├── Dockerfile
└── README.md
```

## 🧪 Testing

```bash
# Run unit tests
./mvnw test

# Test endpoints
curl -H "X-User-Id: admin" http://localhost:8088/api/analytics/dashboard
curl -H "X-User-Id: admin" "http://localhost:8088/api/analytics/dashboard?start=2024-01-01&end=2024-01-31"
curl -H "X-User-Id: admin" http://localhost:8088/api/analytics/top-products
curl -H "X-User-Id: admin" http://localhost:8088/api/analytics/inventory
```

## 🔗 Related Services

- [API Gateway](../api-gateway/README.md) - Routes `/api/analytics/*`
- [Order Service](../order-service/README.md) - Order data source
- [Product Catalogue Service](../product-catalogue-service/README.md) - Inventory data
- [User Identity Service](../user-identity-service/README.md) - Customer data

## 📝 Notes

- Service runs on port **8088**
- Requires **PostgreSQL** for analytical data storage
- **RabbitMQ** is optional for real-time event ingestion
- **Spring Batch** jobs run on configurable schedules
- Admin authentication required for all endpoints
- Some metrics may use mock/calculated data until full integration
- Consider Redis caching for frequently accessed metrics
