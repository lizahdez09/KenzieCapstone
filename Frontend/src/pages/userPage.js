import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";
import RecipeClient from "../api/recipeClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderUserInfo', 'renderFavoritesList', 'fetchRecipes'], this);
        this.dataStore = new DataStore();
        this.client = new RecipeClient();
        this.userClient = new UserClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the recipe list.
     */
    async mount() {
        if (!localStorage.getItem('userInfo')) {
            window.location.href = 'index.html';
        }
        await this.renderUserInfo();
        await this.renderFavoritesList();
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

    async renderFavoritesList() {
        const mainDiv = document.getElementById("favoritesList");
        const favoritesList = JSON.parse(localStorage.getItem("favorites")) || [];
        const recipes = [];
        await this.fetchRecipes(favoritesList, recipes);

        mainDiv.innerHTML = `<h2>Favorite Recipes</h2>`;

        if (recipes.length > 0) {
            recipes.forEach((recipe) => {
                console.log(recipe);
                const id = recipe.id;
                const foodType = recipe.foodType;
                const name = recipe.name;
                const ingredients = JSON.parse(recipe.ingredients);
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
        } else {
            const recipeCard = document.createElement("div");
            recipeCard.classList.add("recipeCard");
            recipeCard.style.textAlign = "center";
            recipeCard.style.color = "red";

            const nameParagraph = document.createElement("p");
            nameParagraph.style.fontSize = "24px";
            nameParagraph.textContent = "No Recipes found! Try again later...";

            recipeCard.appendChild(nameParagraph);
            mainDiv.appendChild(recipeCard);
        }
    }

    async fetchRecipes(favoritesList, recipes) {
        for (const id of favoritesList) {
            try {
                const recipe = await this.client.getRecipe(id);
                recipes.push(recipe);
            } catch (error) {
                this.errorHandler(error);
            }
        }
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