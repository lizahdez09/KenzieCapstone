#!/bin/bash

API_URL="http://localhost:8080/recipe"

# Define an array of recipe objects
recipes=(
  '{
    "name": "Test Recipe 1",
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "30 minutes",
    "instructions": "Cook in pan",
    "favoriteCount": "3"
  }'
  # Add more recipes
)

for recipe in "${recipes[@]}"; do
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done