const createItemButton = document.getElementById('createItemButton');

createItemButton.addEventListener('click', function() {
  const itemInput = document.getElementById('itemInput');
  const itemValue = itemInput.value;

  if (itemValue) {
    const itemForm = document.createElement('form');
    itemForm.classList.add('itemForm');

    const itemInput = document.createElement('input');
    itemInput.classList.add('itemInput');
    itemInput.type = 'text';
    itemInput.value = itemValue;

    const itemSubmitButton = document.createElement('button');
    itemSubmitButton.classList.add('itemSubmitButton');
    itemSubmitButton.type = 'submit';
    itemSubmitButton.textContent = 'Submit';

    itemForm.appendChild(itemInput);
    itemForm.appendChild(itemSubmitButton);

    const orderCreationForm = document.getElementById('orderCreationForm');
    orderCreationForm.appendChild(itemForm);

    itemInput.value = '';
  }
});