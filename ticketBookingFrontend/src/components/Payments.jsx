import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import './Payments.css';

const Payments = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const registrationDetails = location.state?.registrationDetails;
  const [transaction, setTransaction] = useState(null);
  const [processingTime, setProcessingTime] = useState(0);

  useEffect(() => {
    if (registrationDetails) {
      axios
        .get(`http://localhost:8082/transactions/create?registrationId=${registrationDetails.id}&amount=${registrationDetails.amount}`)
        .then((resp) => setTransaction(resp.data))
        .catch((err) => console.error('Error creating transaction:', err));
    }
  }, [registrationDetails]);

  const handleFail = () => {
    if (!transaction?.id) return;
    axios
      .get(`http://localhost:8082/transactions/fail/${transaction.id}?processingTime=${processingTime}`)
      .then(() => {
        navigate('/registrations', {
          state: {
            registrationDetails: {
              ...registrationDetails,
              transactionId: transaction.id,
            },
          },
        });
      })
      .catch((err) => console.error('Error failing transaction:', err));
  };

  const handleSucceed = () => {
    if (!transaction?.id) return;
    axios
      .get(`http://localhost:8082/transactions/succeed/${transaction.id}?processingTime=${processingTime}`)
      .then(() => {
        navigate('/registrations', {
          state: {
            registrationDetails: {
            ...registrationDetails,
            transactionId: transaction.id,
          },
        },
      });
    })
    .catch((err) => console.error('Error scheduling success:', err));
  };

  if (!registrationDetails) {
    return (
      <div>
        <h2>No registration found</h2>
        <Link to="/">Go to Home</Link>
      </div>
    );
  }

  return (
    <div className="container">
      <h2>Payment Page</h2>
      <p><strong>Attendee:</strong> {registrationDetails.attendeeName}</p>
      <p><strong>Amount:</strong> ${registrationDetails.amount?.toFixed(2) || 'N/A'}</p>
      <p><strong>Transaction ID:</strong> {transaction?.id || 'Creating...'}</p>

      <div>
        <label htmlFor="processingTime">Processing Time (seconds): </label>
        <input
          type="number"
          id="processingTime"
          value={processingTime}
          onChange={(e) => setProcessingTime(Number(e.target.value))}
          min="0"
          max="100"
        />
      </div>

      <button onClick={handleFail}>Fail</button>
      <button onClick={handleSucceed}>Succeed</button>
    </div>
  );
};

export default Payments;