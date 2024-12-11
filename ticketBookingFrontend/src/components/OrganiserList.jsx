import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './OrganiserList.css'; // Import the CSS file for OrganiserList

const OrganiserList = () => {
  const [organisers, setOrganisers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/organizers')
      .then(response => setOrganisers(response.data))
      .catch(error => console.error('Error fetching organisers:', error));
  }, []);

  return (
    <div className="container">
      <h2>Event Organisers</h2>
      <div className="organiser-list">
        {organisers.map(organiser => (
          <div key={organiser.id} className="organiser-card">
            <h3>{organiser.name}</h3>
            <p>{organiser.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default OrganiserList;