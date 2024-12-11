import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import EventsList from './components/EventsList';
import OrganiserList from './components/OrganiserList';
import RegistrationForm from './components/RegistrationForm';
import './App.css'; // Import the CSS file

const App = () => {
  return (
    <Router>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/registrations">Registrations</Link></li>
          <li><Link to="/organisers">Organisers</Link></li>
          <li><Link to="/register">Register</Link></li>
        </ul>
      </nav>
      <div>
        <Routes>
          <Route path="/" element={<h1>Welcome to Ticket Booking</h1>} />
          <Route path="/registrations" element={<EventsList />} />
          <Route path="/organisers" element={<OrganiserList />} />
          <Route path="/register" element={<RegistrationForm />} />
          <Route path="/events/:organiserId" element={<EventsList />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;