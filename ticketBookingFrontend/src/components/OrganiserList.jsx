import React, { useEffect, useState } from 'react';
import axios from 'axios';

const OrganiserList = () => {
  const [organisers, setOrganisers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/organizers')
      .then(response => setOrganisers(response.data))
      .catch(error => console.error('Error fetching organisers:', error));
  }, []);

  return (
    <div>
      <h2>Organisers</h2>
      <ul>
        {organisers.map(organiser => (
          <li key={organiser.id}>{organiser.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default OrganiserList;