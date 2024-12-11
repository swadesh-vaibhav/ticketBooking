import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom'; 
import axios from 'axios';

const EventsList = () => {
  const [events, setEvents] = useState([]);
  const [error, setError] = useState(false);
  const { organiserId } = useParams();

  

  useEffect(() => {
    if (organiserId) {
      axios.get(`http://localhost:8081/events?organizerId=${organiserId}`)
        .then(response => setEvents(response.data))
        .catch(error => console.error('Error fetching events:', error));
    } else {
      setError(true);
    }
  }, [organiserId]);

  useEffect(() => {
    if (error) {
      alert(`Please select a valid organiser. Given value: ${organiserId} is invalid.`);
    }
  }, [error]);

  if(error) {
    return <Link to="/">Go back to Home</Link>
  }

  return (
    <div className="container">
      <h2>Events</h2>
      <div className="organiser-list">
        {events.map(event => (
          <div key={event.id} className="organiser-card">
            <h3>{event.name}</h3>
            <p>{event.street}</p>
            <p>{event.city}</p>
            <p>{event.country}</p>
            <p>{event.startDate} to {event.endDate}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default EventsList;