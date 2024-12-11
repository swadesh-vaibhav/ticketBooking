import React, { useState } from 'react';
import axios from 'axios';

const RegistrationForm = () => {
  const [productId, setProductId] = useState('');
  const [attendeeName, setAttendeeName] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    axios.post('http://localhost:8080/registrations', { productId, attendeeName })
      .then(response => {
        console.log('Registration created:', response.data);
        setProductId('');
        setAttendeeName('');
      })
      .catch(error => console.error('Error creating registration:', error));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Create Registration</h2>
      <div>
        <label>Product ID:</label>
        <input type="text" value={productId} onChange={(e) => setProductId(e.target.value)} required />
      </div>
      <div>
        <label>Attendee Name:</label>
        <input type="text" value={attendeeName} onChange={(e) => setAttendeeName(e.target.value)} required />
      </div>
      <button type="submit">Register</button>
    </form>
  );
};

export default RegistrationForm;