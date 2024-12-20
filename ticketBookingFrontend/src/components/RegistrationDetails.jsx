import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import './RegistrationDetails.css'; // Import the CSS file

const RegistrationDetails = () => {
  const [ticketCode, setTicketCode] = useState('');
  const [registrationDetails, setRegistrationDetails] = useState(null);
  const [error, setError] = useState(false);
  const location = useLocation();
  const receivedRegistration = location.state?.registrationDetails;

  useEffect(() => {
    if (!registrationDetails) {
      setRegistrationDetails(receivedRegistration);
  }}, [receivedRegistration]);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.get(`http://localhost:8080/registrations/${ticketCode}`)
      .then(response => {
        setRegistrationDetails(response.data);
      })
      .catch(error => {
        console.error('Error fetching registration details:', error);
        setError(true);
        setRegistrationDetails(null);
      });
  };

  return (
    <div>
      <h2>Registration Details</h2>
      {!registrationDetails && (<form onSubmit={handleSubmit}>
        <div>
          <label>Enter Ticket Code (Voucher Code):</label>
          <input
            type="text"
            value={ticketCode}
            onChange={(e) => setTicketCode(e.target.value)}
            required
          />
        </div>
        <button type="submit">Get Details</button>
      </form>
      )}
      {error && <p className="error">Error fetching registration details. Please check the Ticket Code and try again.</p>}
      {registrationDetails && (
        <div className="registration-details">
          <p><strong>Ticket Code (This is your voucher code):</strong> {registrationDetails.ticketCode}</p>
          <p><strong>Attendee Name:</strong> {registrationDetails.attendeeName}</p>
          <p><strong>Event Name:</strong> {registrationDetails.eventName}</p>
          <p><strong>Price:</strong> ${registrationDetails.amount.toFixed(2)}</p>
          <Link to="/" className="home-button">Go to Home</Link>
        </div>
      )}
    </div>
  );
};

export default RegistrationDetails;