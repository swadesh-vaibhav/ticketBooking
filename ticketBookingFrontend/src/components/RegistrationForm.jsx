import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const RegistrationForm = () => {
  const [productId, setProductId] = useState('');
  const [products, setProducts] = useState([]);
  const [attendeeName, setAttendeeName] = useState('');
  const [error, setError] = useState(false);
  const location = useLocation();
  const event = location.state?.event;

  useEffect(() => {
    if (event) {
      axios.get(`http://localhost:8081/products?eventId=${event.id}`)
        .then(response => {
          setProducts(response.data);
        })
        .catch(error => console.error('Error fetching events:', error));
    }
    else {
      setError(true);
    }
  }, [event]);
  
  useEffect(() => {
    if (error) {
      alert(`Please select a valid event. Given value: ${event} is invalid.`);
    }
  }, [error]);

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

  if (error) {
    return (
    <div className="events-page">
      <Link to="/">Go back to Home</Link>
    </div>);
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Register for {event.name}</h2>
      <div>
        <label>Select ticket variant:</label>
        <select value={productId} onChange={(e) => setProductId(e.target.value)} required>
          {products.map(product => (
            <option key={product.id} value={product.id}>
              {product.name} - {product.description} - ${product.price.toFixed(2)}
            </option>
          ))}
        </select>
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