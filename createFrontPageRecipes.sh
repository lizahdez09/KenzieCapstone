#!/bin/bash

# Set the API endpoint URL
API_URL="http://localhost:8080/recipe"

# Set the request data
REQUEST_DATA='
{
  "id": "7cb82e92-669b-498a-8333-57d3859dd901",
  "name": "Golden Delight French Toast",
  "foodType": "Breakfast",
  "ingredients": "[{\"name\": \"Brioche Sliced Bread\", \"amount\": \"2\", \"measurement\": \"COUNT\"}, {\"name\": \"Eggs\", \"amount\": \"4\", \"measurement\": \"COUNT\"}, {\"name\": \"Vanilla\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Brown Sugar\", \"amount\": \"1/2\", \"measurement\": \"CUP\"}, {\"name\": \"Cinnamon\", \"amount\": \"2\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Milk\", \"amount\": \"1\", \"measurement\": \"CUP\"}]",
  "timeToPrepare": "30",
  "instructions": "1.Whisk all wet ingredients together, and then mix in your brown sugar.\n\n2. Fully submerse your bread in the egg mixture.\n\n3. Melt the butter on a medium/low heat setting and add un-cooked french toast.\n\n4. Cook for 2-4 minutes on each side or until golden brown"
}'
# Define an array of recipe objects
recipes=(
  '{
    "id": "287a02c4-1882-45c0-9c10-ca22712824ab",
    "name": "Savory Fiesta Beef Tacos",
    "foodType": "Lunch",
    "ingredients": "[{\"name\": \"Ground Beef\", \"amount\": \"1\", \"measurement\": \"COUNT\"}, {\"name\": \"Onion\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Garlic\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Salt\", \"amount\": \"1\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Pepper\", \"amount\": \"1\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Water\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Taco Shells or Tortillas\", \"amount\": \"1\", \"measurement\": \"CUP\"}]",
    "timeToPrepare": "30",
    "instructions": "1. In a skillet, cook ground beef over medium heat until browned. Drain excess fat.\n\n2. Add diced onion, bell pepper, and minced garlic to the skillet. Cook until vegetables are tender.\n\n3. Sprinkle taco seasoning over the beef mixture. Stir well to combine.\n\n4. Add diced tomato to the skillet and cook for a few more minutes.\n\n5. Warm tortillas in a separate pan or in the oven.\n\n6. Assemble the tacos by placing a portion of the beef mixture onto each warm tortilla.\n\n7. Top with shredded lettuce, cheese, and salsa.\n\n8. Serve and enjoy the savory fiesta beef tacos!"
  }'
  '{
    "id": "96af71f3-bd8f-43c3-b470-4a2b1c2db043",
    "name": "Fresh Delight Fruit Salad",
    "foodType": "Dessert",
    "ingredients": "[{\"name\": \"Strawberries\", \"amount\": \"2\", \"measurement\": \"CUP\"}, {\"name\": \"Blueberries\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Mango\", \"amount\": \"1\", \"measurement\": \"COUNT\"}, {\"name\": \"Kiwi\", \"amount\": \"2\", \"measurement\": \"COUNT\"}, {\"name\": \"Grapes\", \"amount\": \"1\", \"measurement\": \"CUP\"}, {\"name\": \"Honey\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Lime Juice\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "15",
    "instructions": "1. Wash and prepare all the fruits. Cut strawberries into halves or quarters, slice the mango and kiwi, and remove grapes from the stem.\n\n2. In a large mixing bowl, combine all the prepared fruits.\n\n3. Drizzle honey and lime juice over the fruit mixture.\n\n4. Gently toss the fruit salad to evenly coat the fruits with honey and lime juice.\n\n5. Refrigerate for at least 30 minutes to allow the flavors to blend.\n\n6. Serve chilled and enjoy the fresh delight fruit salad!"
  }'
  '{
    "id": "dbaa497e-039d-449c-9205-eeb6321f7cce",
    "name": "Decadent Chocolate Bliss Mug Cake",
    "foodType": "Dessert",
    "ingredients": "[{\"name\": \"All-Purpose Flour\", \"amount\": \"4\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Cocoa Powder\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Granulated Sugar\", \"amount\": \"2\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Baking Powder\", \"amount\": \"1/4\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Salt\", \"amount\": \"1/8\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Milk\", \"amount\": \"3\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Vegetable Oil\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}, {\"name\": \"Vanilla Extract\", \"amount\": \"1/4\", \"measurement\": \"TEASPOON\"}, {\"name\": \"Chocolate Chips\", \"amount\": \"1\", \"measurement\": \"TABLESPOON\"}]",
    "timeToPrepare": "5",
    "instructions": "1. In a microwave-safe mug, whisk together the all-purpose flour, cocoa powder, granulated sugar, baking powder, and salt.\n\n2. Add the milk, vegetable oil, and vanilla extract to the mug. Stir well until the batter is smooth and no lumps remain.\n\n3. Stir in the chocolate chips.\n\n4. Microwave the mug on high power for about 1 minute and 30 seconds, or until the cake has risen and is set in the center.\n\n5. Carefully remove the mug from the microwave (it will be hot) and let it cool for a minute or two.\n\n6. Enjoy the decadent chocolate bliss mug cake straight from the mug or transfer it to a plate. You can also add toppings like whipped cream, ice cream, or chocolate sauce if desired."
  }'
  # Add more recipe objects as needed
)

# Send POST request to create a new recipe
curl -X POST -H "Content-Type: application/json" -d "$REQUEST_DATA" "$API_URL"

# Loop through the recipe objects and send POST requests
for recipe in "${recipes[@]}"; do
  curl -X POST -H "Content-Type: application/json" -d "$recipe" "$API_URL"
done
