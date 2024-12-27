import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import './RegistrationDetails.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSpinner, faCheckCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';

const RegistrationDetails = () => {
  const location = useLocation();
  const receivedRegistration = location.state?.registrationDetails;
  const [registrationDetails, setRegistrationDetails] = useState(receivedRegistration || null);
  const [ticketCode, setTicketCode] = useState('');
  const [error, setError] = useState(false);

  useEffect(() => {
    if (receivedRegistration) {
      const pollRegistrationStatus = () => {
        axios.get(`http://localhost:8080/registrations/ticketStatus?id=${receivedRegistration.id}`)
          .then(response => {
            setRegistrationDetails(response.data);
            if (response.data.registrationStatus === 'CONFIRMED' || response.data.registrationStatus === 'FAILED') {
              clearInterval(pollingInterval);
            }
            setError(false);
          })
          .catch(error => {
            console.error('Error fetching registration status:', error);
            setError(true);
          });
      };

      const pollingInterval = setInterval(pollRegistrationStatus, 2000); // Poll every 2 seconds

      return () => clearInterval(pollingInterval); // Cleanup interval on component unmount
    }
  }, [receivedRegistration]);

  const getStatusIcon = (status) => {
    switch (status) {
      case 'PENDING':
        return <FontAwesomeIcon icon={faSpinner} spin />;
      case 'CONFIRMED':
        return <FontAwesomeIcon icon={faCheckCircle} style={{ color: 'green' }} />;
      case 'FAILED':
        return <FontAwesomeIcon icon={faTimesCircle} style={{ color: 'red' }} />;
      default:
        return null;
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.get(`http://localhost:8080/registrations/${ticketCode}`)
      .then(response => {
        setError(false);
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
      {!registrationDetails && (
        <form onSubmit={handleSubmit}>
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
          <p><strong>Price:</strong> ${parseFloat(registrationDetails.amount).toFixed(2)}</p>
          <p><strong>Status:</strong> {registrationDetails.registrationStatus} {getStatusIcon(registrationDetails.registrationStatus)}</p>
          <Link to="/" className="home-button">Go to Home</Link>
        </div>
      )}
    </div>
  );
};

export default RegistrationDetails;