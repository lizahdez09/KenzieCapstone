import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";
import RecipeClient from "../api/recipeClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class HomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([
            'sendHome',
            'login',
            'signup',
            'renderUserInfo',
            'renderRecipeInformation'], this);
        this.dataStore = new DataStore();
        this.userClient = new UserClient();
        this.client = new RecipeClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        if (!localStorage.getItem('userInfo')) {
            document.getElementById('loginSignupOverlay').style.display = 'block';
        } else {
            document.getElementById('loginSignupOverlay').style.display= 'none';
            await this.renderUserInfo();
        }
        const switchToSignupLink = document.getElementById('switchToSignupLink');
        const switchToLoginLink = document.getElementById('switchToLoginLink');
        const loginForm = document.getElementById('loginForm');
        const signupForm = document.getElementById('signupForm');

        switchToSignupLink.addEventListener('click', function (event) {
            event.preventDefault();
            loginForm.style.display = 'none';
            signupForm.style.display = 'block';
        });
        switchToLoginLink.addEventListener('click', function (event) {
            event.preventDefault();
            loginForm.style.display = 'block';
            signupForm.style.display = 'none';
        });

        document.getElementById('site-container').addEventListener('click', this.sendHome);
        document.getElementById('loginForm').addEventListener('submit', this.login);
        document.getElementById('signupForm').addEventListener('submit', this.signup);

        const images = document.querySelectorAll(".quickPicksLeft img, .quickPicksRight img, .quickPicksFarRight img");
        images.forEach((image) => {
            image.addEventListener("click", (event) => {
                const imageId = image.id;
                this.renderRecipeInformation(event, imageId);
            });
        });
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

    async sendHome(event) {
        window.location.href = "index.html";
    }

    async login(event) {
        event.preventDefault();

        const spinner = document.getElementById("spinner");
        spinner.style.display = "block";

        const userLoginRequest = {
            email: document.getElementById('loginEmail').value,
            password: document.getElementById('loginPassword').value
        }

        try {
            const user = await this.userClient.login(userLoginRequest, this.errorHandler);
            localStorage.setItem('userInfo', JSON.stringify({
                id: user.id,
                email: user.email,
                name: user.name
            }));
            let list = [...user.favoriteRecipes]
            localStorage.setItem('favorites', JSON.stringify(list));
            location.reload();
        } catch (error) {
            console.error(error);
            console.log(error.response.status)
            console.log(error.response.data.message)
            spinner.style.display = "none";
            const errorMessage = document.getElementById("errorMessage");

            if (error.response && error.response.status === 500) {
                errorMessage.textContent = `No user found by ${userLoginRequest.email}`;
            } else if (error.response && error.response.status === 404) {
                errorMessage.textContent = "Incorrect password";
            } else {
                errorMessage.textContent = "An error occurred. Please try again later.";
            }

            errorMessage.style.display = "block";
        }
    }

    async signup(event) {
        event.preventDefault();

        const firstName = document.getElementById("signupFirstName").value;
        const lastName = document.getElementById("signupLastName").value;
        const email = document.getElementById("signupEmail").value;
        const password = document.getElementById("signupPassword").value;
        const confirmPassword = document.getElementById("confirmSignupPassword").value;
        if (password !== confirmPassword) {
            const errorMessage = document.getElementById("errorMessage");
            errorMessage.textContent= "Passwords do not match. Please try again.";
            errorMessage.style.display = "block"
            document.getElementById("signupPassword").classList.add("password-mismatch");
            document.getElementById("confirmSignupPassword").classList.add("password-mismatch");
            return;
        }
        const name = `${firstName} ${lastName}`;

        const spinner = document.getElementById("spinner");
        spinner.style.display = "block";

        try {
            const user = await this.userClient.signup(name, email, password, this.errorHandler);
                localStorage.setItem('userInfo', JSON.stringify({
                    id: user.id,
                    email: user.email,
                    name: user.name
                }));
                localStorage.setItem('favorites', JSON.stringify(user.favoriteRecipes));
            location.reload();
        } catch (error) {
            console.error(error);
            spinner.style.display = "none";
        }
    }

    async renderRecipeInformation(event, recipeId) {
        event.preventDefault();
        const mainDiv = document.getElementById("recipeInformation");
        mainDiv.innerHTML = '';
        const recipe = await this.client.getRecipe(recipeId, this.errorHandler);
        const foodType = recipe.foodType;
        const name = recipe.name;
        const capitalizedName = name.charAt(0).toUpperCase() + name.slice(1);
        const ingredients = JSON.parse(recipe.ingredients);
        const timeToPrepare = recipe.timeToPrepare;
        const instructions = recipe.instructions;

        const recipeTitle = document.createElement("h2");
        recipeTitle.classList.add("recipeTitle");
        recipeTitle.textContent = `${capitalizedName}`;

        const recipeType = document.createElement("p");
        recipeType.classList.add()
        recipeType.innerText = `Best suitable for ${foodType}`;

        const recipeTime = document.createElement("p");
        recipeTime.classList.add()
        recipeTime.innerText = `Time to prepare: ${timeToPrepare} minutes.`;

        const ingredientParagraph = document.createElement("p");
        ingredientParagraph.classList.add()
        const ingredientTitle = document.createElement("h3");
        ingredientTitle.innerText = `Ingredients`
        ingredientParagraph.appendChild(ingredientTitle);
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
            ingredientParagraph.appendChild(ingredientElement);
        });

        const instructionsParagraph = document.createElement("p");
        instructionsParagraph.classList.add();
        const instructionsTitle = document.createElement("h3");
        instructionsTitle.innerText = `Cooking Instructions`;
        instructionsParagraph.appendChild(instructionsTitle);
        const instructionsP = document.createElement("p");
        instructionsP.innerText = `${instructions}`;
        instructionsParagraph.appendChild(instructionsP);


        mainDiv.appendChild(recipeTitle);
        mainDiv.appendChild(recipeType);
        mainDiv.appendChild(recipeTime);
        mainDiv.appendChild(ingredientParagraph);
        mainDiv.appendChild(instructionsParagraph);
        mainDiv.style.display = "flex";
        mainDiv.scrollIntoView({ behavior: "smooth", block: "start", inline: "nearest" });
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
