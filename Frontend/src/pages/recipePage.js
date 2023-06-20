import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {
  constructor() {
    super();
    this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
    this.dataStore = new DataStore();
  }

  /**
   * Once the page has loaded, set up the event handlers and fetch the concert list.
   */
  async mount() {
    document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
    document.getElementById('create-form').addEventListener('submit', this.onCreate);
    document.getElementById('search-form').addEventListener('submit', this.onSearch);
    this.client = new ExampleClient();

    this.dataStore.addChangeListener(this.renderExample)
  }

  // Render Methods --------------------------------------------------------------------------------------------------

  async renderExample() {
    let resultArea = document.getElementById("result-info");

    const example = this.dataStore.get("example");

    if (recipes) {
      resultArea.innerHTML = `
        <div>ID: ${recipes.id}</div>
        <div>Name: ${recipes.name}</div>
      `;
    } else {
      resultArea.innerHTML = "No Item";
    }
  }

  // Event Handlers --------------------------------------------------------------------------------------------------

  async onGet(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();

    let id = document.getElementById("id-field").value;
    this.dataStore.set("recipe", null);

    let result = await this.client.getExample(id, this.errorHandler);
    this.dataStore.set("recipe", result);
    if (result) {
      this.showMessage(`Got ${result.name}!`);
    } else {
      this.errorHandler("Error doing GET! Try again...");
    }
  }

  async onCreate(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();
    this.dataStore.set("recipe", null);

    let name = document.getElementById("create-name-field").value;

    const createdExample = await this.client.createExample(name, this.errorHandler);
    this.dataStore.set("recipe", createdExample);

    if (createdExample) {
      this.showMessage(`Created ${createdExample.name}!`);
    } else {
      this.errorHandler("Error creating! Try again...");
    }
  }

  async onSearch(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();

    let searchQuery = document.getElementById("search-field").value;

    // Create an instance of the RecipeSearch class
    const recipeSearch = new RecipeSearch();

    // Call the searchByName method and pass the search query
    recipeSearch.searchByName(searchQuery);
  }
}

/**
 * Class for searching recipes by name.
 */
class RecipeSearch {
  searchByName(query) {
    // Perform the search logic here
    // You can use the query parameter to search for recipes by name
    // You can make API requests, filter data, or perform any other search operations based on your requirements

    // Example search logic:
    console.log(`Searching recipes by name: ${query}`);

    // You can add your search logic here based on the query parameter
    // For example, make an API request to retrieve recipes with a matching name

    // Once you have the search results, you can update the UI or perform further actions
    // For this example, we are simply logging the search query

    // Update the UI or perform further actions based on the search results
    // ...
  }
}
