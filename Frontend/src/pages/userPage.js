import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderUser'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the recipe list.
     */
    async mount() {
        document.getElementById('create-user-form').addEventListener('submit', this.onCreate);
        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderUser())
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderUser() {
        let resultArea = document.getElementById("result-info");

        const user = this.dataStore.get("user");

        if (user) {
            resultArea.innerHTML = `
                <div>Id: ${user.id}</div>
                <div>Name: ${user.name}</div>
                <div>FoodType: ${user.foodType}</div>
                <div>Ingerdients: ${user.ingredients}</div>
                <div>TimeToPrepare: ${user.timeToPrepare}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("user", null);

        let result = await this.client.getRecipe(id, this.errorHandler);
        this.dataStore.set("user", result);

        if (result) {
            this.showMessage(`Got ${result.id}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("user", null);

        let id = document.getElementById("id").value;
        let name = document.getElementById("name").value;
        let foodType = document.getElementById("foodType").value;
        let ingredients = document.getElementById("ingredients").value;
        let timeToPrepare = document.getElementById("timeToPrepare").value;

        const createdRecipe = await this.client.createRecipe(id, name, foodType, ingredients, timeToPrepare, this.errorHandler);
        this.dataStore.set("user", createdRecipe);

        if (createdRecipe) {
            this.showMessage(`Create Successful!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userPage = new UserPage();
    await userPage.mount();
    console.log("page loaded")
};

window.addEventListener('DOMContentLoaded', main);