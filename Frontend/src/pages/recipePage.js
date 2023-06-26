import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RecipeClient from "../api/recipeClient";

class RecipePage extends BaseClass {
  capturedFormValues = {};
  dataStore;
  menu;
  ingredientCount = 1;

  constructor() {
    super();
    this.bindClassMethods(['openPopUp', 'buildRecipeTable', 'addNewRecipe', 'onStateChange', 'handleTabClick', 'continueBtnClicked', 'addIngredientClicked'], this);

    this.dataStore = new DataStore();
    this.WELCOMETAB = "tab-recipes";
    this.SEARCHTAB = "tab-search-recipes";
    this.TYPETAB = "tab-search-by-type";
    this.CREATETAB = "tab-create-recipe";
    this.CREATETABCHILD1 = "createRecipe1";
    this.CREATETABCHILD2 = "createRecipe2";

    this.menu = document.querySelector('.menu');
  };

  async mount() {
    this.client = new RecipeClient();

    this.menu.addEventListener('click', this.handleTabClick);
    this.dataStore.addChangeListener(this.onStateChange);
    this.dataStore.set("parentState", this.WELCOMETAB);
  };

  async onStateChange() {
    const parentState = this.dataStore.get("parentState");
    const childState = this.dataStore.get("childState");

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
    if (parentState === this.CREATETAB) {
      if (childState === this.CREATETABCHILD1) {
        document.getElementById(this.CREATETABCHILD1.toString()).classList.add("active");
        document.getElementById(this.CREATETABCHILD2.toString()).classList.remove("active");

        document.getElementById("createContinueBtn")
            .addEventListener('click', this.continueBtnClicked);
      } else if (childState === this.CREATETABCHILD2) {
        document.getElementById(this.CREATETABCHILD1.toString()).classList.remove("active");
        document.getElementById(this.CREATETABCHILD2.toString()).classList.add("active");

        document.getElementById("addIngredient")
            .addEventListener('click', this.addIngredientClicked);
        document.getElementById("addRecipe")
            .addEventListener('click', this.addNewRecipe);
      }
    }
  };

  async buildRecipeTable() {
    const mainDiv = document.getElementById("welcomeTab");
    const recipes = await this.client.getAllRecipes();
    mainDiv.innerHTML = ``;
    recipes.forEach((recipe) => {
      const id = recipe.id;
      const foodType = recipe.foodType;
      const name = recipe.name;
      const ingredients = JSON.parse(recipe.ingredients); // Parse the ingredients string
      const timeToPrepare = recipe.timeToPrepare;

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
      ingredientElement.innerHTML = `${ingredientName}-${ingredientAmount} ${ingredientMeasurement}`;
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

  async continueBtnClicked(event) {
    event.preventDefault();

    const name = document.getElementById("name-field").value;
    const type = document.getElementById("type-field").value;
    const cookTime = document.getElementById("timeToCook-field").value;
    this.capturedFormValues = { name, type, cookTime };
    console.log(this.capturedFormValues);
    this.dataStore.set("childState", this.CREATETABCHILD2);
  };

  async addIngredientClicked(event) {
    const ingredientForm = document.getElementById("ingredient-field");
    const formId = this.buildIngredientFieldId();

    const ingredientCard = document.createElement("div");
    ingredientCard.classList.add("card");
    ingredientCard.classList.add("small-margin");
    ingredientCard.innerHTML = `
    <h2 class="ingredientTitle">Ingredient</h2>
    <label>Name</label>
    <input type="text" required class="validated-field" id="${formId}-name">
    <label>Measurement</label>
    <select id="${formId}-measurement">
      <option value="TEASPOON">TSP</option>
      <option value="TABLESPOON">TBSP</option>
      <option value="CUP">C</option>
      <option value="COUNT">Count</option>
      <option value="POUND">Pound</option>
    </select>
    <label>Amount</label>
    <input type="text" required class="validated-field" id="${formId}-amount">
  `;

    ingredientForm.appendChild(ingredientCard);
  }

  async addNewRecipe(event) {
    const ingredientForm = document.getElementById("ingredient-field");
    const ingredients = Array.from(ingredientForm.getElementsByClassName("card")).map(card => {
      const name = card.querySelector("input[id$='-name']").value;
      const measurement = card.querySelector("select[id$='-measurement']").value;
      const amount = card.querySelector("input[id$='-amount']").value;

      return {
        name: name,
        measurement: measurement,
        amount: amount
      };
    });
    const name = this.capturedFormValues.name;
    const foodType = this.capturedFormValues.type;
    const jsonIngredients = JSON.stringify(ingredients);
    const cookTime = this.capturedFormValues.cookTime;

    const createdRecipe = await this.client.createRecipe(name, foodType, jsonIngredients, cookTime, this.errorHandler);

    if (createdRecipe) {
      this.showMessage(`Created new Recipe named : ${createdRecipe.name}`)
    } else {
      this.errorHandler("Error creating!  Try again...");
    }
    this.dataStore.set("parentState", this.WELCOMETAB);
  }

  buildIngredientFieldId() {
    const id = `ingredient${this.ingredientCount}`;
    this.ingredientCount++;
    return id;
  }


}

const main = async () => {
  const recipePage = new RecipePage();
  await recipePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
