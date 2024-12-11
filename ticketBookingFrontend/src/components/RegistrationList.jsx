import React, { useEffect, useState } from 'react';
import axios from 'axios';

const RegistrationList = () => {
  const [registrations, setRegistrations] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/registrations')
      .then(response => setRegistrations(response.data))
      .catch(error => console.error('Error fetching registrations:', error));
  }, []);

  return (
    <div>
      <h2>Registrations</h2>
      <ul>
        {registrations.map(registration => (
          <li key={registration.id}>{registration.attendeeName} - {registration.eventName}</li>
        ))}
      </ul>
    </div>
  );
};

export default RegistrationList;