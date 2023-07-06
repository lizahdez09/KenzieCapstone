#!/bin/bash

API_URL="http://localhost:8080/recipe"
RECIPE_COUNT=10

# Array of possible food types
food_types=("Breakfast" "Lunch" "Dinner" "Dessert")
# Array of possible ingredients
ingredients=("Milk" "Onion" "Garlic" "Chicken" "Tomato" "Lettuce" "Carrot" "Spinach" "Onion" "Potato" "Cucumber" "Celery" "Cabbage"
            "Leek" "Asparagus" "Peas" "Cauliflower" "Avocado" "Pepper" "Broccoli" "Artichoke" "Pumpkin" "Radish" "Mushroom" "Corn"
             "Grapes" "Watermelon" "Orange" "Pear" "Plum" "Pear" "Strawberry" "Blueberry" "Mango" "Papaya" "Apricot" "Banana" "Grapefruit"
             "Lemon" "Lime" "Pineapple" "Melon" "Coconut" "Peach" "Kiwi" "Cherry" "Bread" "Rice" "Quinoa" "Flour" "Barley" "Chickpeas"
             "Buckwheat" "Millet" "Wheat" "Oats" "Rye" "Couscous" "Hominy" "Cornmeal" "Semolina" "Bran" "Muesli" "Panko" "Matzo" "Corn Starch"
             "Beef" "Pork" "Veal" "Shrimp" "Crab" "Turkey" "Tofu" "Soybeans" "Egg" "Yogurt" )
# Array of possible measurements
measurements=("TEASPOON" "TABLESPOON" "CUP" "COUNT" "POUND")
# Function to generate a random recipe
generate_recipe() {
  local name="Recipe $((RANDOM % 1000 + 1))"
  local food_type=${food_types[$((RANDOM % ${#food_types[@]}))]}
  local ingredient_count=$((RANDOM % 12 + 1))
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
