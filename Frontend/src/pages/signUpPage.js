import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";
import axios from "axios";

class SignUpPage extends BaseClass {
  constructor() {
    super();
    this.bindClassMethods(['handleSubmit', 'handleSignup'], this);
    this.dataStore = new DataStore();
  }

  async mount() {
    document.getElementById('signup-form').addEventListener('submit', this.handleSubmit);
    this.client = new UserClient();
    this.dataStore.addChangeListener(this.renderUser);
  }

  handleSubmit(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    this.handleSignup(name, email, password);
  }

  async handleSignup(name, email, password) {
    try {
      const response = await axios.post('/user', { name, email, password });

      console.log('Signup successful!', response.data);
    } catch (error) {
      console.error('Signup error:', error);
      alert('Signup failed. Please try again later.');
    }
  }
}

const main = async () => {
  const signUpPage = new SignUpPage();
  await signUpPage.mount();
  console.log("page loaded");
};

window.addEventListener('DOMContentLoaded', main);
