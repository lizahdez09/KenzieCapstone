import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class HomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['sendHome'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('site-container').addEventListener('click', this.sendHome);

    }

    // Render Methods --------------------------------------------------------------------------------------------------



    // Event Handlers --------------------------------------------------------------------------------------------------

    async sendHome(event) {
        window.location.href = "index.html";
    }

}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    await homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
