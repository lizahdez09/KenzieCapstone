class WelcomePage extends BaseClass {

 document.getElementById("popup-form").addEventListener("submit", function(e) {
   e.preventDefault(); // Prevent form submission for this example

   // Get the entered email and name values
   var name = document.getElementById("name-input").value;
   var email = document.getElementById("email-input").value;

   // Perform any desired actions with the submitted data
   console.log("Name: " + name);
   console.log("Email: " + email);

   // Close the pop-up after submission (optional)
   document.getElementById("popup-container").style.display = "none";
 });

 document.getElementById("exit-button").addEventListener("click", function() {
   document.getElementById("popup-container").style.display = "none";
 });

}


const main = async () => {
    const examplePage = new ExamplePage();
    examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);