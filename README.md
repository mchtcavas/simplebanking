# simplebanking

A service that provides banking operations, including account management and transaction handling.

## `application.yml` Configuration File

This file contains various configuration settings for your application. Below are the explanations for each configuration section.

### `application`

Contains the basic configuration settings for the application.

#### `bill-payment`

This section contains configuration settings for bill payment operations.

##### `operators`

- **operators**  
  A list of supported mobile operators for bill payments. Current configuration includes:
  - `turkcell`
  - `vodafone`
  - `turktelekom`

### `springdoc`

This section contains configuration settings for Springdoc OpenAPI, which is used for generating API documentation.

#### `api-docs`

- **path**  
  The path at which the OpenAPI documentation will be available.  
  `/api-docs`

#### `swagger-ui`

- **path**  
  The path at which the Swagger UI will be available. This UI provides a graphical interface to interact with the API documentation.  
  `/swagger-ui.html`

