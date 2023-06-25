#!/bin/bash

API_URL="http://localhost:8080/ingredient"

# Set the list of ingredients to add
INGREDIENTS=("Milk" "Onion" "Garlic")

for INGREDIENT_NAME in "${INGREDIENTS[@]}"; do
  JSON_PAYLOAD="{\"name\":\"$INGREDIENT_NAME\"}"
  curl -X POST -H "Content-Type: application/json" -d "$JSON_PAYLOAD" "$API_URL"
done