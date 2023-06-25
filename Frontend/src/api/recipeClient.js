import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class RecipeClient extends BaseClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getRecipe', 'createRecipe', 'updateRecipe'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async getAllRecipes(errorCallback) {
        try {
            const response = await this.client.get('/recipe');
            return response.data;
        } catch (error) {
            this.handleError("getAllRecipes", error, errorCallback);
        }
    }

    async getRecipe(id, errorCallback) {
        try {
            console.log("I get here.")
            const response = await this.client.get(`/recipe/${id}`);
            console.log(response);
            return response.data;
        } catch (error) {
            this.handleError("getRecipe", error, errorCallback)
        }
    }

    async createRecipe(name, foodType, ingredients, timeToPrepare, errorCallback) {
        try {
            const response = await this.client.post(`/recipe`, {
                name: name,
                foodType: foodType,
                ingredients: ingredients,
                timeToPrepare: timeToPrepare
            });
            console.log(response.data);
            return response.data;
        } catch (error) {
            this.handleError("createRecipe", error, errorCallback);
        }
    }

    async updateRecipe(id, name, foodType, ingredients, timeToPrepare, errorCallback) {
        try {
            const response = await this.client.update(`/recipe`, {
                id: id,
                name: name,
                foodType: foodType,
                ingredients: ingredients,
                timeToPrepare: timeToPrepare
            });
            return response.data;
        } catch (error) {
            this.handleError("updateRecipe", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}