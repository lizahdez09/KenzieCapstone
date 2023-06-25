#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Set the request data
REQUEST_DATA='
{
  "name": "Recipe Name",
  "foodType": "Lunch",
  "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
  "timeToPrepare": "30 minutes"
}'

# Send POST request to create a new recipe
curl -X POST -H "Content-Type: application/json" -d "$REQUEST_DATA" "$API_URL"
