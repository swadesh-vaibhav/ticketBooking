import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import './EventsList.css'; // Import the CSS file for EventsList

const EventsList = () => {
  const [events, setEvents] = useState([]);
  const [error, setError] = useState(false);
  const location = useLocation();
  const organiser = location.state?.organiser;

  useEffect(() => {
    if (organiser) {
      axios.get(`http://localhost:8081/events?organizerId=${organiser.id}`)
        .then(response => setEvents(response.data))
        .catch(error => console.error('Error fetching events:', error));
    } else {
      setError(true);
    }
  }, []);

  useEffect(() => {
    if (error) {
      alert(`Please select a valid organiser. Given value: ${organiser} is invalid.`);
    }
  }, [error]);

  if (error) {
    return (
    <div className="events-page">
      <Link to="/">Go back to Home</Link>
    </div>);
  }

  return (
      <div className="container">
        <h2>Events by {organiser?.name}</h2>
        <div className="event-list">
          {events.map(event => (
            <Link
              key={event.id} 
              to="/register"
              state={{ event: event }}>
              <div key={event.id} className="event-card">
                <h3>{event.name}</h3>
                <p>{event.street}</p>
                <p>{event.city}</p>
                <p>{event.country}</p>
                <p>{event.startDate} to {event.endDate}</p>
              </div>
            </Link>
          ))}
        </div>
      </div>
  );
};

export default EventsList;