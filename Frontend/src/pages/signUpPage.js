import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


class SignUpPage extends BaseClass{
  constructor() {
     super();
     this.bindClassMethods(['handleSubmit', 'handleSignup'], this);
     this.dataStore = new DataStore();


  }
     async mount() {
          document.getElementById('signup-form').addEventListener('submit', this.handleSubmit());
          this.client = new UserClient();
          this.dataStore.addChangeListener(this.renderUser())
      }

  handleSubmit(event) {
    event.preventDefault();

    // Retrieve the form field values
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Perform form validation if needed

    // Call a method to handle signup logic and API calls
    this.handleSignup(name, email, password);
  }

 async handleSignup(name, email, password) {
   try {
     const response = await axios.post('/user', { name, email, password });
     // Handle successful signup response
     console.log('Signup successful!', response.data);
     // Optionally, redirect to a success page or perform other actions
   } catch (error) {
     // Handle signup error
     console.error('Signup error:', error);
     // Display an error message to the user
     alert('Signup failed. Please try again later.');
   }
 }

  }
}
const main = async () => {
    const signUpPage = new SignUpPage();
    await signUpPage.mount();
    console.log("page loaded")
};

window.addEventListener('DOMContentLoaded', main);
// Create an instance of the SignUpPage class
const signUpPage = new SignUpPage();
