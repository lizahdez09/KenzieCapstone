#!/bin/bash

API_URL="http://localhost:8080/ingredient"

# Set the list of ingredients to add
INGREDIENTS=("Milk" "Onion" "Garlic" "Chicken" "Tomato" "Lettuce" "Carrot" "Spinach" "Onion" "Potato" "Cucumber" "Celery" "Cabbage"
"Leek" "Asparagus" "Peas" "Cauliflower" "Avocado" "Pepper" "Broccoli" "Artichoke" "Pumpkin" "Radish" "Mushroom" "Corn"
 "Grapes" "Watermelon" "Orange" "Pear" "Plum" "Pear" "Strawberry" "Blueberry" "Mango" "Papaya" "Apricot" "Banana" "Grapefruit"
 "Lemon" "Lime" "Pineapple" "Melon" "Coconut" "Peach" "Kiwi" "Cherry" "Bread" "Rice" "Quinoa" "Flour" "Barley" "Chickpeas"
 "Buckwheat" "Millet" "Wheat" "Oats" "Rye" "Couscous" "Hominy" "Cornmeal" "Semolina" "Bran" "Muesli" "Panko" "Matzo" "Corn Starch"
 "Beef" "Pork" "Veal" "Shrimp" "Crab" "Turkey" "Tofu" "Soybeans" "Egg" "Yogurt" )

for INGREDIENT_NAME in "${INGREDIENTS[@]}"; do
  JSON_PAYLOAD="{\"name\":\"$INGREDIENT_NAME\"}"
  curl -X POST -H "Content-Type: application/json" -d "$JSON_PAYLOAD" "$API_URL"
  echo "$RESPONSE"
done