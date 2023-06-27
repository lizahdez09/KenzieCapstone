#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Define an array of recipe objects
recipes=(
  '{
    "name": "Test Recipe 1",
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "30 minutes"
  }'
  '{
    "name": "Test Recipe 2",
    "foodType": "Dinner",
    "ingredients": "[{\"name\": \"Ingredient A\", \"amount\": \"3\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient B\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "45 minutes"
  }'
  # Add more recipe objects as needed
)

# Loop through the recipe objects and send POST requests
for recipe in "${recipes[@]}"; do
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done