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

    const menu = document.querySelector('.menu');
  }

  /**
   * Once the page has loaded, set up the event handlers and fetch the concert list.
   */
  async mount() {
    document.querySelector('.menu').addEventListener('click', this.handleTabClick)

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
    } else if (parentState === this.SEARCHTAB) {
      welcomeDiv.classList.remove("active");
      searchDiv.classList.add("active");
      typeDiv.classList.remove("active");
      createDiv.classList.remove("active");
    } else if (parentState === this.TYPETAB) {
      welcomeDiv.classList.remove("active");
      searchDiv.classList.remove("active");
      typeDiv.classList.add("active");
      createDiv.classList.remove("active");
    } else if (parentState === this.CREATETAB) {
      welcomeDiv.classList.remove("active");
      searchDiv.classList.remove("active");
      typeDiv.classList.remove("active");
      createDiv.classList.add("active");
    }
  }

  // Event Handlers --------------------------------------------------------------------------------------------------
  async handleTabClick(event) {
    // Prevent the page from refreshing on form submit
    event.preventDefault();

    if (event.target.matches('li')) {
      const tabs = document.querySelectorAll('.menu li');
      tabs.forEach(tab => {
        tab.classList.remove('selectedTab');
      });

      const clickedTab = event.target;
      clickedTab.classList.add('selectedTab');

      // Perform any additional actions based on the clicked tab
      // For example, you can show/hide content or trigger other functionality
      console.log('Clicked tab:', clickedTab.id);

    }
  }

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
