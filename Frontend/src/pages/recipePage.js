import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

class RecipePage extends BaseClass {
  capturedFormValues = {};
  dataStore;
  menu;
  ingredientCount = 1;

  constructor() {
    super();
    this.bindClassMethods(['addNewRecipe', 'onStateChange', 'handleTabClick', 'continueBtnClicked', 'addIngredientClicked'], this);

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
    this.client = new ExampleClient();

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

    const html = `
    <div class="card">
      <h2 class="ingredientTitle">Ingredient</h2>
      <label>Name</label>
      <input type="text" required class="validated-field" id="${formId}-name">
      <label>Measurement</label>
      <select id="${formId}-measurement">
        <option value="TEASPOON">tsp</option>
        <option value="TABLESPOON">tbsp</option>
        <option value="CUP">c</option>
      </select>
      <label>Amount</label>
      <input type="text" required class="validated-field" id="${formId}-amount">
    </div>
  `;

    ingredientForm.innerHTML += html;
  };

  async addNewRecipe(event) {

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
