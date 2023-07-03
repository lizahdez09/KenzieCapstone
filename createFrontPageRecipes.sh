#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Set the request data
REQUEST_DATA='
{
  "name": "Golden Delight French Toast",
  "foodType": "Lunch",
  "ingredients": "[{\"name\": \"Brioche Sliced Bread\", \"amount\": \"2\", \"measurement\": \"COUNT\"}, {\"name\": \"Eggs\", \"amount\": \"4\", \"measurement\": \"COUNT\"}, {\"name\": \"Vanilla\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Brown Sugar\", \"amount\": \"1/2\", \"measurement\": \"CUP\"}, {\"name\": \"Cinnamon\", \"amount\": \"2\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Milk\", \"amount\": \"1\", \"measurement\": \"CUP\"}]",
  "timeToPrepare": "30 minutes",
  "instructions": "Whisk all wet ingredients together, and then mix in your brown sugar. After completing this step you can go ahead and fully submerse your bread in the egg mixture. Melt the butter on a medium/low heat setting and add un-cooked french toast. Cook for about 2-4 minutes on each side or until golden brown"
}'
# Define an array of recipe objects
recipes=(
  '{
    "name": "Savory Fiesta Beef Tacos"
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ground Beef\", \"amount\": \"1\", \"measurement\": \"COUNT}, {\"name\": \"Onion\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Garlic\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Salt\", \"amount\": \"1\", \"measurement\": \"TEASPOON}, {\"name\": \"Pepper\", \"amount\": \"1\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Water\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Taco Shells or Tortillas\", \"amount\": \"1\", \"measurement\": \"CUP\"}]",
    "timeToPrepare": "30 minutes",
    "instructions": "cook in pan"
  }'
  '{
    "name": "Fresh Delight Fruit Salad",
    "foodType": "Breakfast",
    "ingredients": "[{\"name\": \"Strawberry\", \"amount\": \"5\", \"measurement\": \"COUNT\"}, {\"name\": \"Mango\", \"amount\": \"1\", \"measurement\": \"COUNT\"}, {\"name\": \"Blackberry\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Orange\", \"amount\": \"1\", \"measurement\": \"COUNT}, {\"name\": \"Blueberry\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Grape\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Sugar\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Water\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Lemon Juice\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "15 minutes",
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
