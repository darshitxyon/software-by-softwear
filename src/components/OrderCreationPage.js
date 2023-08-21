import React, { useState } from 'react';
import ItemCreationButton from './ItemCreationButton';

const OrderCreationPage = () => {
  const [order, setOrder] = useState({ orderId: '', items: [], totalCost: 0 });

  const addItemToOrder = (item) => {
    setOrder(prevOrder => ({
      ...prevOrder,
      items: [...prevOrder.items, item],
      totalCost: prevOrder.totalCost + item.cost
    }));
  };

  return (
    <div id="order-creation-page">
      <h1>Create Order</h1>
      <ItemCreationButton addItemToOrder={addItemToOrder} />
      <h2>Order Details</h2>
      <p>Order ID: {order.orderId}</p>
      <p>Total Cost: {order.totalCost}</p>
      <ul>
        {order.items.map((item, index) => (
          <li key={index}>{item.name} - {item.cost}</li>
        ))}
      </ul>
    </div>
  );
};

export default OrderCreationPage;