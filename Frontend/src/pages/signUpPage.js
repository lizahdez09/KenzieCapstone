import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";
import axios from "axios";

class SignUpPage extends BaseClass {
  constructor() {
    super();
    this.bindClassMethods(['sendHome', 'onStateChange', 'handleButtonSelection',
      'loginUser', 'signupUser'], this);
    this.dataStore = new DataStore();

    this.LOGINBTN = "logInButtonSelection";
    this.SIGNUPBTN = "signUpButtonSelection";
  }

  async mount() {

    document.getElementById('signUpButtonSelection').addEventListener('click', this.handleButtonSelection);
    document.getElementById('logInButtonSelection').addEventListener('click', this.handleButtonSelection);
    document.getElementById('loginSubmit').addEventListener('click', this.loginUser);
    document.getElementById('signupSubmit').addEventListener('click', this.signupUser);
    document.getElementById('site-container').addEventListener('click', this.sendHome);
    this.client = new UserClient();


    this.dataStore.addChangeListener(this.onStateChange);
  }

  /* change listener*/
  async onStateChange() {
    const state = this.dataStore.get("state");

    if (state === this.LOGINBTN) {
      document.getElementById("loginFormContainer").classList.add("active");
      document.getElementById("signupFormContainer").classList.remove("active");
    } else if (state === this.SIGNUPBTN) {
      document.getElementById("loginFormContainer").classList.remove("active");
      document.getElementById("signupFormContainer").classList.add("active");
    }
  }


  /* Event Handles*/
  async sendHome(event) {
    window.location.href = "index.html";
  }

  async handleButtonSelection(event) {
    event.preventDefault();
    this.dataStore.set("state", event.target.id);
    console.log(event.target.id);
  }

  async loginUser(event) {

  }

  async signupUser(event) {
    event.preventDefault();

    const firstName = document.getElementById("signUpfirstName").value;
    const lastName = document.getElementById("signUplastName").value;
    const email = document.getElementById("signUpEmail").value;
    const password = document.getElementById("signUpPassword").value;

    const name = `${firstName} ${lastName}`;

    await this.client.signup(name, email, password, this.errorHandler);
  }


}

const main = async () => {
  const signUpPage = new SignUpPage();
  await signUpPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
