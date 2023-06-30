import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class UserClient extends BaseClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getFavoriteRecipe', 'addFavoriteRecipe', 'updateFavoriteRecipe','signup'];
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

      async signup(name, email, password, errorCallback) {
        try {
          const response = await this.client.post('/user', {
            name,
            email,
            password
          });
          return response.data;
        } catch (error) {
          this.handleError("signup", error, errorCallback);
        }
      }

    async getFavoriteRecipe(id, errorCallback) {
        try {
            const response = await this.client.get(`/user/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getFavoriteRecipe", error, errorCallback)
        }
    }

    async addFavoriteRecipe(id, name, favoriteRecipe, errorCallback) {
        try {
            const response = await this.client.post(`/user`, {
                id: id,
                name: name,
                favoriteRecipe: favoriteRecipe
            });
            return response.data;
        } catch (error) {
            this.handleError("addFavoriteRecipe", error, errorCallback);
        }
    }

    async updateFavoriteRecipe(id, name, favoriteRecipe, errorCallback) {
        try {
            const response = await this.client.update(`/user/${id}`, {
                id: id,
                name: name,
                favoriteRecipe: favoriteRecipe
            });
            return response.data;
        } catch (error) {
            this.handleError("updateFavoriteRecipe", error, errorCallback);
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