import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class UserClient extends BaseClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['signup', 'login', 'updateUserFavorites'];
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

    async signup(name, email, password, errorCallBack) {
        try {
            const userRequest = {
                id: "",
                name: name,
                email: email,
                password: password,
                favoriteRecipes: ""
            };
            const jsonString = JSON.stringify(userRequest);

            const response = await this.client.post('/user', jsonString, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError("signup", error, errorCallBack);
        }
    }

    async updateUserFavorites(userUpdateRequest, errorCallBack) {
        try {
            const response = await this.client.post('/user/update', jsonString, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError("updateUserFavorites", error, errorCallBack);
        }
    }

    async login(userLoginRequest, errorCallBack) {
            const response = await this.client.post(`/user/login`, {
                email: userLoginRequest.email,
                password: userLoginRequest.password
            });
            return response.data;
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