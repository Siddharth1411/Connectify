// without using local storage
// document.getElementById('theme_change_button').addEventListener('click', function() {
//     const htmlElement = document.documentElement;
//     const themeTextSpan = this.querySelector('span');

//     if (htmlElement.classList.contains('light')) {
//       htmlElement.classList.remove('light');
//       htmlElement.classList.add('dark');
//       themeTextSpan.textContent = 'light';
//     } else {
//       htmlElement.classList.remove('dark');
//       htmlElement.classList.add('light');
//       themeTextSpan.textContent = 'dark';
//     }
//   });



// using local storage is better because: user preference is remembered, local storage persists across multiple sessions thus user doesn't have to manually change everytime he opens the app 

//   using local storage
// Check local storage for theme preference
const storedTheme = localStorage.getItem('theme');
const htmlElement = document.documentElement;
const themeButton = document.getElementById('theme_change_button');
const themeTextSpan = themeButton.querySelector('span');

if (storedTheme) {
  htmlElement.classList.remove('light', 'dark');
  htmlElement.classList.add(storedTheme);
  themeTextSpan.textContent = storedTheme === 'light' ? 'dark' : 'light';
}

themeButton.addEventListener('click', function() {
  const currentTheme = htmlElement.classList.contains('light') ? 'light' : 'dark';
  const newTheme = currentTheme === 'light' ? 'dark' : 'light';

  // Toggle theme class
  htmlElement.classList.remove('light', 'dark');
  htmlElement.classList.add(newTheme);

  // Update button text
  themeTextSpan.textContent = newTheme === 'light' ? 'dark' : 'light';

  // Store the new theme in local storage
  localStorage.setItem('theme', newTheme);
});