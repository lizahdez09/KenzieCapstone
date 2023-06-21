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
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getRecipe(id, errorCallback) {
        try {
            const response = await this.client.get(`/recipe/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getRecipe", error, errorCallback)
        }
    }

    async createRecipe(id, recipe, name, errorCallback) {
        try {
            const response = await this.client.post(`recipe`, {
                id: id,
                recipe: recipe,
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("createExample", error, errorCallback);
        }
    }

    async updateRecipe(id, recipe, name, errorCallback) {
        try {
            const response = await this.client.update(`recipe`, {
                id: id,
                recipe: recipe,
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("updateRecipe", error, errorCallback);
        }
    }
}