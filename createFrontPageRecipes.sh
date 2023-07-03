#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Set the request data
REQUEST_DATA='
{
  "name": "Golden Delight French Toast",
  "foodType": "Lunch",
  "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
  "timeToPrepare": "30 minutes",
  "instructions": "cook in pan"
}'
# Define an array of recipe objects
recipes=(
  '{
    "name": "Savory Fiesta Beef Tacos",
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "30 minutes",
    "instructions": "cook in pan"
  }'
  '{
    "name": "Fresh Delight Fruit Salad",
    "foodType": "Dinner",
    "ingredients": "[{\"name\": \"Ingredient A\", \"amount\": \"3\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient B\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "45 minutes",
    "instructions": "cook in pan"
  }'
  '{
    "name": "Decadent Chocolate Bliss Mug Cake",
    "foodType": "Dinner",
    "ingredients": "[{\"name\": \"Ingredient A\", \"amount\": \"3\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient B\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "45 minutes",
    "instructions": "cook in pan"
  }'
  # Add more recipe objects as needed
)

# Send POST request to create a new recipe
curl -X POST -H "Content-Type: application/json" -d "$REQUEST_DATA" "$API_URL"

# Loop through the recipe objects and send POST requests
for recipe in "${recipes[@]}"; do
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done
