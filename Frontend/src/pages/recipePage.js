import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RecipeClient from "../api/recipeClient";

class RecipePage extends BaseClass {
  capturedFormValues = {};
  dataStore;
  menu;
  ingredientCount = 1;
  filterTags = [];
  constructor() {
    super();
    this.bindClassMethods(['openPopUp', 'buildRecipeTable', 'addNewRecipe',
      'onStateChange', 'handleTabClick', 'addIngredient',
      'addFilter'], this);

    this.dataStore = new DataStore();
    this.WELCOMETAB = "tab-recipes";
    this.SEARCHTAB = "tab-search-recipes";
    this.TYPETAB = "tab-search-by-type";
    this.CREATETAB = "tab-create-recipe";

    this.menu = document.querySelector('.menu');
  };

  async mount() {
    this.client = new RecipeClient();

    this.menu.addEventListener('click', this.handleTabClick);
    document.getElementById('addIngredientButton').addEventListener('click', this.addIngredient);
    document.getElementById('submitNewRecipe').addEventListener('click', this.addNewRecipe);
    document.getElementById('filterButton').addEventListener('click', this.addFilter);
    this.dataStore.addChangeListener(this.onStateChange);
    this.dataStore.set("parentState", this.WELCOMETAB);
  };

  async onStateChange() {
    const parentState = this.dataStore.get("parentState");

    const tabIds = {
      [this.WELCOMETAB]: "welcomeTab",
      [this.SEARCHTAB]: "searchTab",
      [this.TYPETAB]: "typeTab",
      [this.CREATETAB]: "createTab"
    };

    for (const tabId of Object.values(tabIds)) {
      const tabDiv = document.getElementById(tabId);
      tabDiv.classList.remove("active");
    }

    const activeTabDiv = document.getElementById(tabIds[parentState]);
    activeTabDiv.classList.add("active");
    if (parentState === this.WELCOMETAB) {
      await this.buildRecipeTable();
    }
  };

  async buildRecipeTable() {
    const mainDiv = document.getElementById("welcomeTab");
    const recipes = await this.client.getAllRecipes();
    mainDiv.innerHTML = ``;
    recipes.forEach((recipe) => {
      console.log(recipe);
      const id = recipe.id;
      const foodType = recipe.foodType;
      const name = recipe.name;
      const ingredients = JSON.parse(recipe.ingredients); // Parse the ingredients string
      const timeToPrepare = recipe.timeToPrepare;
      const instructions = recipe.instructions;

      const recipeCard = document.createElement("div");
      recipeCard.classList.add("recipeCard");
      recipeCard.id = id;
      const nameParagraph = document.createElement("p");
      nameParagraph.classList.add("info");
      nameParagraph.textContent = `Name: ${name}`;

      const typeParagraph = document.createElement("p");
      typeParagraph.classList.add("info");
      typeParagraph.textContent = `Type: ${foodType}`;

      const timeParagraph = document.createElement("p");
      timeParagraph.classList.add("info");
      timeParagraph.textContent = `Time to cook: ${timeToPrepare}`;

      recipeCard.appendChild(nameParagraph);
      recipeCard.appendChild(typeParagraph);
      recipeCard.appendChild(timeParagraph);

      mainDiv.appendChild(recipeCard);
      recipeCard.addEventListener('click', this.openPopUp);
    });
  }

  async openPopUp(event) {
    const recipeId = event.currentTarget.id;
    const overlay = document.getElementById("overlay");
    overlay.style.display = "flex";

    const overlayContentDiv = document.getElementById("smallOverlayContent");
    overlayContentDiv.innerHTML = ""; // Clear previous content

    const closeOverlayButton = document.getElementById("closeOverlayButton");

    const closeOverlay = () => {
      overlay.style.display = "none";
      closeOverlayButton.removeEventListener("click", closeOverlay);
    };

    closeOverlayButton.addEventListener("click", closeOverlay);

    const recipe = await this.client.getRecipe(recipeId, this.errorHandler);
    console.log(recipe);
    const foodType = recipe.foodType;
    const name = recipe.name;
    const capitalizedName = name.charAt(0).toUpperCase() + name.slice(1);
    const ingredients = JSON.parse(recipe.ingredients); // Parse the ingredients string
    const timeToPrepare = recipe.timeToPrepare;

    overlayContentDiv.innerHTML = `
    <h2 class="ingredientTitlePopUp">${capitalizedName}</h2>
    <p class="foodType">Best suitable for ${foodType}</p>
    <p>Time to Prepare : ${timeToPrepare} minutes</p>
    <h3>Ingredients</h3>
  `;

    // Iterate over the ingredients array and create <p> elements
    ingredients.forEach((ingredient) => {
      const ingredientName = ingredient.name;
      const ingredientAmount = ingredient.amount;
      const ingredientMeasurement = ingredient.measurement;

      const ingredientElement = document.createElement("p");

      if (ingredientAmount > "") {
        ingredientElement.innerHTML = `${ingredientName}-${ingredientAmount} ${ingredientMeasurement}`;
      } else {
        ingredientElement.innerHTML = `${ingredientName}`;
      }

      overlayContentDiv.appendChild(ingredientElement);
    });
  }


  async handleTabClick(event) {
    event.preventDefault();

    if (event.target.matches('li')) {
      const tabs = document.querySelectorAll('.menu li');
      tabs.forEach(tab => tab.classList.remove('selectedTab'));

      event.target.classList.add('selectedTab');
      this.dataStore.set("parentState", event.target.id);
      if (event.target.id === this.CREATETAB) {
        this.dataStore.set("childState", this.CREATETABCHILD1);
      }
    }
  };

  async addIngredient(event) {
    const ingredientContainer = document.getElementById('ingredients');
    const ingredientInputs = ingredientContainer.getElementsByClassName('ingredient');

    // Check if all previous ingredient fields are filled
    let allFilled = true;
    for (let i = 0; i < ingredientInputs.length; i++) {
      const nameInput = ingredientInputs[i].querySelector('input[name="ingredientName[]"]');
      const amountInput = ingredientInputs[i].querySelector('input[name="ingredientAmount[]"]');

      if (nameInput.value === '' || amountInput.value === '') {
        allFilled = false;
        // Show tooltip
        const tooltip = ingredientInputs[i].querySelector('.tooltip');
        tooltip.style.display = 'block';
      } else {
        // Hide tooltip if it was previously shown
        const tooltip = ingredientInputs[i].querySelector('.tooltip');
        tooltip.style.display = 'none';
      }
    }

    if (allFilled) {
      const newIngredient = document.createElement('div');
      newIngredient.className = 'ingredient';

      newIngredient.innerHTML = `
      <input type="text" name="ingredientName[]" placeholder="Name" required>
      <input type="text" name="ingredientAmount[]" placeholder="Amount" required>
      <select name="ingredientMeasurement[]" required>
          <option value="TEASPOON">Teaspoon</option>
          <option value="TABLESPOON">Tablespoon</option>
          <option value="CUP">Cup</option>
          <option value="COUNT">Count</option>
          <option value="POUND">Pound</option>
      </select>
      <span class="tooltip">Please fill out the previous ingredient fields.</span> <!-- Add this line -->
    `;

      ingredientContainer.appendChild(newIngredient);
    }
  }

  async addNewRecipe(event) {

    const recipeNameInput = document.getElementById('recipeName');
    const foodTypeInput = document.getElementById('foodType');
    const cookTimeInput = document.getElementById('cookTime');
    const cookingDirectionsInput = document.getElementById('cookingDirections');

    const ingredientInputs = document.querySelectorAll('.ingredient');
    const ingredients = Array.from(ingredientInputs).map((ingredient) => {
      const nameInput = ingredient.querySelector('input[name="ingredientName[]"]');
      const amountInput = ingredient.querySelector('input[name="ingredientAmount[]"]');
      const measurementInput = ingredient.querySelector('select[name="ingredientMeasurement[]"]');

      return {
        name: nameInput.value,
        amount: amountInput.value,
        measurement: measurementInput.value
      };
    });

    const recipe = {
      name: recipeNameInput.value,
      foodType: foodTypeInput.value,
      cookTime: cookTimeInput.value,
      cookingDirections: cookingDirectionsInput.value,
      ingredients: JSON.stringify(ingredients)
    };

    console.log(recipe);
    const createdRecipe = await this.client.createRecipe(recipe, this.errorHandler);

    if (createdRecipe) {
      this.showMessage(`Created new Recipe named : ${createdRecipe.name}`)
    } else {
      this.errorHandler("Error creating!  Try again...");
    }
    this.dataStore.set("parentState", this.WELCOMETAB);
  }

  async addFilter(event) {
    event.preventDefault(); // Prevent form submission

    const searchInput = document.getElementById('searchInput');
    const searchTerm = searchInput.value.trim(); // Get the entered search term

    if (searchTerm !== '') {
      const filterTagsContainer = document.getElementById('filterTags');
      const filterTag = document.createElement('span');
      filterTag.className = 'tag';
      filterTag.textContent = searchTerm;

      // Create a button element to remove the filter tag
      const removeButton = document.createElement('button');
      removeButton.className = 'removeButton';
      removeButton.textContent = 'x';

      // Add event listener to the remove button
      removeButton.addEventListener('click', () => {
        filterTag.remove(); // Remove the filter tag when clicked
      });

      // Append the remove button to the filter tag
      filterTag.appendChild(removeButton);

      // Append the filter tag to the container
      filterTagsContainer.appendChild(filterTag);

      searchInput.value = ''; // Clear the search input
    }
  }


}

const main = async () => {
  const recipePage = new RecipePage();
  await recipePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
