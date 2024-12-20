import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import './RegistrationForm.css'; // Import the CSS file

const RegistrationForm = () => {
  const [productId, setProductId] = useState('');
  const [products, setProducts] = useState([]);
  const [attendeeName, setAttendeeName] = useState('');
  const [error, setError] = useState(false);
  const location = useLocation();
  const event = location.state?.event;
  const navigate = useNavigate();

  useEffect(() => {
    if (event) {
      axios.get(`http://localhost:8081/products?eventId=${event.id}`)
        .then(response => {
          setProducts(response.data);
          setProductId(response.data[0].id);
        })
        .catch(error => console.error('Error fetching products:', error));
    } else {
      setError(true);
    }
  }, [event]);

  useEffect(() => {
    if (error) {
      alert(`Please select a valid event. Given value: ${event} is invalid.`);
    }
  }, [error]);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:8080/registrations', { productId, attendeeName })
      .then(response => {
        console.log('Registration created:', response.data);
        navigate('/registrations', { state: { registrationDetails: response.data } });
      })
      .catch(error => console.error('Error creating registration:', error));
  };

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  if (error) {
    return (
      <div className="events-page">
        <Link to="/">Go back to Home</Link>
      </div>
    );
  }

  return (
    <div className="registration-page">
      <h2>Register for {event?.name}</h2>
      <div className="event-details">
        <p><strong>Event Name:</strong> {event?.name}</p>
        <p><strong>Location:</strong> {event?.venue.street}, {event?.venue.city}, {event?.venue.country}</p>
        <p><strong>Date:</strong> {formatDate(event?.startDate)} to {formatDate(event?.endDate)}</p>
      </div>
      <form onSubmit={handleSubmit}>
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
    </div>
  );
};

export default RegistrationForm;