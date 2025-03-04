# Cianit

Cianit is a simplified web application that replicates core functionalities of the real estate platform Cian. This project includes two RESTful web services and a frontend module:

1. **Object Collection Service** - Manages a collection of `Flat` objects, supporting CRUD operations, filtering, sorting, pagination, and additional operations.
2. **Agency Service** - Provides advanced operations using data from the Object Collection Service.
3. **Frontend** - User interface for interacting with the backend services.

## Features

### Housing Service
- Add, update, retrieve, and delete flats.
- Fetch a list of flats with support for filtering, sorting, and pagination.
- Additional operations:
    - Delete all flats matching a specific `house` value.
    - Delete one flat with a specific `new` value.
    - Retrieve a flat with the maximum `numberOfRooms`.

### Agency Service
- Compare two flats by ID and retrieve the cheapest one.
- Retrieve a sorted list of flats based on travel time to the metro.

### Frontend
- Interactive interface for viewing, adding, and managing flats.
- Visual representation of filtering, sorting, and comparisons.

## Technologies
- **Java**: Core language for backend logic.
- **Spring Boot**: Framework for developing the RESTful APIs.
- **React** (or similar): Framework for building the frontend.
- **OpenAPI**: Specification for documenting the API.
- **XML**: Data format for API communication.

## Setup

### Prerequisites
- JDK 17+
- Maven 3+
- Node.js (for the frontend)
- Docker (optional, for containerized deployment)

### Install dependencies

```sh
npm install axios xml-js
```

### Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

## Project Structure (planned)

```
Cianit/
├── docs/                   # API documentation and diagrams
│   ├── api/                # OpenAPI specifications (YAML/JSON files)
│   └── diagrams/           # System and flow diagrams
├── backend/                # Backend modules
│   ├── common/             # Shared libraries or utilities for all backend services
│   │   ├── src/            # Java source files
│   │   └── pom.xml         # Maven configuration
│   ├── housing/            # Object Collection Service
│   │   ├── src/            # Java source files
│   │   ├── resources/      # Configurations, XML schemas, etc.
│   │   ├── test/           # Unit and integration tests
│   │   └── pom.xml         # Maven configuration
│   ├── agency/             # Agency Service
│   │   ├── src/            # Java source files
│   │   ├── resources/      # Configurations, XML schemas, etc.
│   │   ├── test/           # Unit and integration tests
│   │   └── pom.xml         # Maven configuration
│   └── ???
├── frontend/               # Frontend module
│   ├── ???
├── config/                 # Shared configuration files (e.g., application.yml)
├── scripts/                # Deployment and utility scripts
├── tests/                  # End-to-end and integration tests
│   ├── e2e/                # End-to-end tests
│   └── mocks/              # Mock services and data
├── Dockerfile              # Containerization setup
├── docker-compose.yml      # Multi-service Docker configuration
└── README.md               # Project overview
```
