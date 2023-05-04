## technical test for onebox

### Description
This is a technical test for onebox. 

 This is a technical test that consists of:
- The creation of a shopping cart.
- You can delete a shopping cart.
- You can get cart information by cart id.
- You can create products.
- Products can be added to the shopping cart.
- You can remove products from the cart.
- After 10 minutes of inactivity, the cart will be destroyed.

### Requirements
- JDK 17
- Maven

### How to run it
To test it you can clone this repository.
 
Once the project is downloaded, we go to the main folder and run the corresponding maven command to compile it.

To run, simply run the command maven -> mvn spring-boot:run

To deploy we run the maven package command, this will generate a jar which if run will raise the application on localhost:8080.


### Enpoints
Cart endpoints
```
-- Get all carts --
GET /api/cart

-- Get cart by id --
GET /api/cart/{id}

-- Create cart --
POST /api/cart

-- Delete cart --
DELETE /api/cart/{id}

-- Add product to cart --
POST /api/cart/{id}/product/{idProduct}

-- Delete product from cart --
DELETE /api/cart/{id}/product/{idProduct}

```

Product endpoints
```
-- Get all products --
GET /api/product

-- Get product by id --
GET /api/product/{id}

-- Create product --
POST /api/product

-- Delete product --
DELETE /api/product/{id}
```

To facilitate testing, a file "Request - Tecnical test.json" containing requests corresponding to the project is provided.This json can be imported into postman.

It simply needs to be completed with the missing information.