#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Define the list of recipes
RECIPES='[
  {
    "name": "Recipe 1",
    "foodType": "Lunch",
    "ingredients": [
      {"name": "Ingredient 1", "amount": "2", "measurement": "CUP"},
      {"name": "Ingredient 2", "amount": "1", "measurement": "TABLESPOON"}
    ],
    "timeToPrepare": "30 minutes"
  },
  {
    "name": "Recipe 2",
    "foodType": "Dinner",
    "ingredients": [
      {"name": "Ingredient A", "amount": "3", "measurement": "CUP"},
      {"name": "Ingredient B", "amount": "2", "measurement": "TABLESPOON"}
    ],
    "timeToPrepare": "45 minutes"
  }
]'

# Iterate over each recipe and make a POST request
for recipe in $(echo "$RECIPES" | jq -c '.[]'); do
  # Send POST request to create a new recipe
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done