#!/bin/bash

API_URL="http://localhost:8080/recipe"
RECIPE_COUNT=20

# Array of possible food types
food_types=("Breakfast" "Lunch" "Dinner" "Dessert")
# Array of possible ingredients
ingredients=("Salt" "Pepper" "Sugar" "Flour" "Butter" "Milk" "Eggs" "Chicken" "Beef" "Fish" "Rice" "Tomatoes" "Onions" "Garlic")
# Array of possible measurements
measurements=("TEASPOON" "TABLESPOON" "CUP" "COUNT" "POUND")
# Function to generate a random recipe
generate_recipe() {
  local name="Recipe $((RANDOM % 1000 + 1))"
  local food_type=${food_types[$((RANDOM % ${#food_types[@]}))]}
  local ingredient_count=$((RANDOM % 10 + 1))
  local ingredient_list="["

  for ((i=0; i<$ingredient_count; i++)); do
    local ingredient=${ingredients[$((RANDOM % ${#ingredients[@]}))]}
    local amount=$((RANDOM % 4 + 1))
    local measurement=${measurements[$((RANDOM % ${#measurements[@]}))]}

    ingredient_list+="{\\\"name\\\": \\\"$ingredient\\\", \\\"amount\\\": \\\"$amount\\\", \\\"measurement\\\": \\\"$measurement\\\"}"

    if ((i != ingredient_count - 1)); then
      ingredient_list+=", "
    fi
  done

  ingredient_list+="]"

  local time_to_prepare="$((RANDOM % 60 + 15)) minutes"
  local instructions="Cook according to instructions"
  local favorite_count=$((RANDOM % 10))

  local recipe='{
    "name": "'$name'",
    "foodType": "'$food_type'",
    "ingredients": "'$ingredient_list'",
    "timeToPrepare": "'$time_to_prepare'",
    "instructions": "'$instructions'",
    "favoriteCount": "'$favorite_count'"
  }'

  echo "$recipe"
}

# Generate and post random recipes
for ((i=0; i<RECIPE_COUNT; i++)); do
  recipe=$(generate_recipe)
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
  echo ""
done
