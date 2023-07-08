import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderUserInfo'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the recipe list.
     */
    async mount() {
        if (!localStorage.getItem('userInfo')) {
            window.location.href = 'index.html';
        }
        await this.renderUserInfo();
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    async renderUserInfo(){
        const user = localStorage.getItem('userInfo');
        const userInfo = JSON.parse(user);
        const userInfoContainer = document.getElementById("user-info-container");

        const welcomeMessage = document.createElement("p");
        welcomeMessage.innerHTML = `Welcome, ${userInfo.name}!<br><a href="userPage.html">My Account</a>`;


        const logoutButton = document.createElement("button");
        logoutButton.textContent = "Logout";
        logoutButton.addEventListener("click", () => {
            localStorage.removeItem('userInfo');
            localStorage.removeItem('favorites');
            location.reload();
        });

        userInfoContainer.appendChild(welcomeMessage);
        userInfoContainer.appendChild(logoutButton);
    }
    // Event Handlers --------------------------------------------------------------------------------------------------

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userPage = new UserPage();
    await userPage.mount();
};

window.addEventListener('DOMContentLoaded', main);