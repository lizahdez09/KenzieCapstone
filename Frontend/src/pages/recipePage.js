import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RecipePage extends BaseClass {
  constructor() {
    super();
    this.bindClassMethods(['onStateChange'], this);
    this.dataStore = new DataStore();

    this.WELCOMETAB = 0;
    this.SEARCHTAB = 1;
    this.TYPETAB = 2;
    this.CREATETAB = 3;

  }

  /**
   * Once the page has loaded, set up the event handlers and fetch the concert list.
   */
  async mount() {
    // document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
    // document.getElementById('create-form').addEventListener('submit', this.onCreate);
    // document.getElementById('search-form').addEventListener('submit', this.onSearch);
    this.client = new ExampleClient();

    this.dataStore.addChangeListener(this.onStateChange)
    this.dataStore.set("parent-state", this.WELCOMETAB);
  }

  // Render Methods --------------------------------------------------------------------------------------------------

  async onStateChange() {
    const parentState = this.dataStore.get("parent-state");
    const childState = this.dataStore.get("child-state");

    const welcomeDiv = document.getElementById("welcomeTab");
    const searchDiv = document.getElementById("searchTab");
    const typeDiv = document.getElementById("typeTab");
    const createDiv = document.getElementById("createTab");

    if (parentState === this.WELCOMETAB) {
      welcomeDiv.classList.add("active");
      searchDiv.classList.remove("active");
      typeDiv.classList.remove("active");
      createDiv.classList.remove("active");
    }


  }

  // Event Handlers --------------------------------------------------------------------------------------------------

  async onGet(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();
  }

  async onCreate(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();
  }

  async onSearch(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();
  }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
  const recipePage = new RecipePage();
  recipePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
