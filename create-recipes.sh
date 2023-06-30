#!/bin/bash

API_URL="http://localhost:8080/recipe"

# Define an array of recipe objects
recipes=(
  '{
    "name": "Test Recipe 1",
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ingredient 1\", \"amount\": \"2\", \"measurement\": \"CUP\"},
                    {\"name\": \"Ingredient 2\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "30 minutes",
    "instructions": "Cook in pan",
    "favoriteCount": "3"
  }'
  # Add more recipes
  #breakfast
  '{
    "name": "Scrambled Eggs",
    "foodType": "Breakfast",
    "ingredients": "[{\"name\": \"large eggs\",\"amount\": \"2\", \"measurement\": \"COUNT\"},
                    {\"name\": \"salt\", \"amount\": \"to taste\"},
                    {\"name\": \"pepper\",\"amount\": \"to taste\"},
                    {\"name\": \"butter\",\"amount\": \"1\",\"measurement\": \"TABLESPOON\"}]",
  "timeToPrepare": "5 minutes",
  "instructions": "Crack the eggs into a bowl and whisk them until well combined. Season with salt and pepper to taste.Heat a non-stick skillet over medium heat and melt the butter.
                   Pour the whisked eggs into the skillet and let them cook for a few seconds. Using a spatula, gently stir the eggs until they form soft curds.
                   Cook for another minute or until the eggs are fully cooked but still moist. Serve hot.",
  "favoriteCount": "3"
  }'


'{
   "name": "Banana Pancakes",
   "foodType": "Breakfast",
   "ingredients": "[{\"name\": \"banana\",\"amount\": \"1\"},
                    {\"name\": \"pancake mix\",\"amount\": \"1\",\"measurement\": \"CUP\"},
                    {\"name\": \"milk\",\"amount\": \"1.5\", \"measurement\": \"CUP\"},
                    { \"name\": \"butter\",\"amount\": \"1\",\"measurement\": \"TABLESPOON\"}]",
   "timeToPrepare": "10 minutes",
   "instructions": "In a mixing bowl, mash the ripe banana until smooth. Add the pancake mix and milk to the bowl and stir until well combined.
                   Heat a griddle or non-stick pan over medium heat and melt the butter. Pour 1/4 cup of the pancake batter onto the griddle for each pancake.
                   Cook for 2-3 minutes on each side or until golden brown.\n6. Serve the pancakes with your favorite toppings, such as syrup or sliced bananas.",
   "favoriteCount": 2
 }'


'{
  "name": "Avocado Toast",
  "foodType": "Breakfast",
  "ingredients": "[{\"name\": \"avocado\",\"amount\": \"1\"},
                {\"name\": \"bread slices,\"amount\": \"2\"},
                {\"name\": \"salt\",\"amount\": \"to taste\"},
                {\"name\": \"peppers\",\"amount\": \"1.5\",\"measurement\": \"CUP\"},
                {\"name\": \"red pepper flakes\",\"amount\": \"optional\"}]",
  "timeToPrepare": "5 minutes",
  "instructions": "Toast the bread slices until golden and crispy. Cut the avocado in half, remove the pit, and scoop the flesh into a bowl.
                  Mash the avocado with a fork and season with salt, pepper, and red pepper flakes (if desired).
                  Spread the mashed avocado evenly onto the toast. Optionally, garnish with additional toppings like sliced tomatoes or a drizzle of olive oil.\n6. Serve immediately.",
  "favoriteCount": 1
}'






)

for recipe in "${recipes[@]}"; do
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done