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
        this.bindClassMethods([
            'renderUserInfo',
            'renderFavoritesList',
            'fetchRecipes',
            'handleFavorite',
            'openPopUp'], this);
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
                nameParagraph.textContent = `${name}`;

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
    async openPopUp(event) {
        const recipeId = event.currentTarget.id;
        const overlay = document.getElementById("overlay");
        overlay.style.display = "flex";

        const overlayContentDiv = document.getElementById("smallOverlayContent");
        overlayContentDiv.innerHTML = "";

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
        const ingredients = JSON.parse(recipe.ingredients);
        const timeToPrepare = recipe.timeToPrepare;
        const instructions = recipe.instructions;

        overlayContentDiv.innerHTML = `
    <h2 class="ingredientTitlePopUp">${capitalizedName}</h2>
    <p class="foodType">Best suitable for ${foodType}</p>
    <p>Time to Prepare: ${timeToPrepare} minutes</p>
    <h3>Ingredients</h3>
  `;

        ingredients.forEach((ingredient) => {
            const ingredientName = ingredient.name;
            const ingredientAmount = ingredient.amount;
            const ingredientMeasurement = ingredient.measurement;

            const ingredientElement = document.createElement("p");

            if (ingredientAmount > "") {
                ingredientElement.innerHTML = `${ingredientName}-${ingredientAmount} ${ingredientMeasurement}`;
            } else {
                ingredientElement.innerHTML = `${ingredientName}`;
            }

            overlayContentDiv.appendChild(ingredientElement);
        });

        overlayContentDiv.innerHTML += `
    <h2 class="ingredientTitlePopUp">Cooking Instructions</h2>
    <p>${instructions}</p>
  `;
        const favoriteButton = document.createElement("button");
        favoriteButton.innerHTML = "&#9734;";
        favoriteButton.classList.add("favoriteButton");
        favoriteButton.id = recipeId;

        const favorites = JSON.parse(localStorage.getItem("favorites")) || [];
        if (favorites.includes(recipeId)) {
            favoriteButton.innerHTML = "&#9733;";
            favoriteButton.classList.add("favorite");
        }

        favoriteButton.addEventListener("click", this.handleFavorite);

        overlayContentDiv.prepend(favoriteButton);
    }

    async handleFavorite(event){
        const favoriteButton = event.currentTarget;
        const clickedRecipeId = event.target.id;
        const isFavorite = favoriteButton.classList.contains("favorite");
        const userInfo = JSON.parse(localStorage.getItem("userInfo"));
        const userEmail = userInfo.email;

        if (isFavorite) {
            const favorites = JSON.parse(localStorage.getItem("favorites")) || [];
            const updatedFavorites = favorites.filter((id) => id !== clickedRecipeId);
            localStorage.setItem("favorites", JSON.stringify(updatedFavorites));
            favoriteButton.innerHTML = "&#9734;";
            favoriteButton.classList.remove("favorite");
            await this.client.unFavorite(clickedRecipeId, this.errorHandler);
            await this.userClient.updateUserFavorites(userEmail, updatedFavorites, this.errorHandler);

        } else {
            const favorites = JSON.parse(localStorage.getItem("favorites")) || [];
            favorites.push(clickedRecipeId);
            localStorage.setItem("favorites", JSON.stringify(favorites));
            favoriteButton.innerHTML = "&#9733;";
            favoriteButton.classList.add("favorite");
            await this.client.favorite(clickedRecipeId, this.errorHandler);
            await this.userClient.updateUserFavorites(userEmail, favorites, this.errorHandler);
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userPage = new UserPage();
    await userPage.mount();
};

window.addEventListener('DOMContentLoaded', main);