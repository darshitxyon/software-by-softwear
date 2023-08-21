import React, { useState } from 'react';
import './ItemCreationButton.css';

const ItemCreationButton = ({ addItemToOrder }) => {
  const [item, setItem] = useState({});

  const createItem = () => {
    // Logic to create a new item
    // This is a placeholder and should be replaced with actual item creation logic
    const newItem = {
      itemId: Math.random().toString(36).substring(7),
      name: 'New Item',
      cost: Math.floor(Math.random() * 100)
    };

    setItem(newItem);
    addItemToOrder(newItem.itemId);
  };

  return (
    <button id="item-creation-button" className="item-creation-button" onClick={createItem}>
      Create Item
    </button>
  );
};

export default ItemCreationButton;