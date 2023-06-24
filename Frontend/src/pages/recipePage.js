import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

class RecipePage extends BaseClass {
  capturedFormValues = {};
  dataStore;
  menu;

  constructor() {
    super();
    this.bindClassMethods(['onStateChange', 'handleTabClick', 'continueBtnClicked'], this);

    this.dataStore = new DataStore();
    this.WELCOMETAB = "tab-recipes";
    this.SEARCHTAB = "tab-search-recipes";
    this.TYPETAB = "tab-search-by-type";
    this.CREATETAB = "tab-create-recipe";
    this.CREATETABCHILD1 = 0;
    this.CREATETABCHILD2 = 0;

    this.menu = document.querySelector('.menu');
  }

  async mount() {
    this.client = new ExampleClient();

    this.menu.addEventListener('click', this.handleTabClick);
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

    if (parentState === this.CREATETAB) {
      const continueBtn = document.getElementById("createContinueBtn");
      continueBtn.addEventListener('click', this.continueBtnClicked);
    }
  };

  async handleTabClick(event) {
    event.preventDefault();

    if (event.target.matches('li')) {
      const tabs = document.querySelectorAll('.menu li');
      tabs.forEach(tab => tab.classList.remove('selectedTab'));

      event.target.classList.add('selectedTab');
      this.dataStore.set("parentState", event.target.id);
    }
  };

  async continueBtnClicked(event) {
    event.preventDefault();

    const name = document.getElementById("name-field").value;
    const type = document.getElementById("type-field").value;
    const cookTime = document.getElementById("timeToCook-field").value;
    this.capturedFormValues = { name, type, cookTime };
    console.log(this.capturedFormValues);
  };

  // Additional event handler methods and utility methods can be added here
}

const main = async () => {
  const recipePage = new RecipePage();
  await recipePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
