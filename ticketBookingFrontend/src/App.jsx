import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import EventsList from './components/EventsList';
import OrganiserList from './components/OrganiserList';
import RegistrationForm from './components/RegistrationForm';
import RegistrationDetails from './components/RegistrationDetails';
import './App.css'; // Import the CSS file

const App = () => {
  return (
    <Router>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/registrations">Registration Details</Link></li>
          <li><Link to="/organisers">Organisers</Link></li>
        </ul>
      </nav>
      <div>
        <Routes>
          <Route path="/" element={<div>
            <h1>Welcome to Ticket Booking</h1>
            <br/>
            <Link to='organisers'>Browse Organisers</Link>
            </div>} />
          <Route path="/registrations" element={<RegistrationDetails />} />
          <Route path="/organisers" element={<OrganiserList />} />
          <Route path="/register" element={<RegistrationForm />} />
          <Route path="/events" element={<EventsList />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;