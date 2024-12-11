import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
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
          <Link
          key={organiser.id} 
          to="/events"
          state={{ organiser: organiser }}>
            <div className="organiser-card">
              <h3>{organiser.name}</h3>
              <p>{organiser.description}</p>
            </div>
            </Link>
        ))}
      </div>
    </div>
  );
};

export default OrganiserList;