import React from 'react';
import { useNavigate } from 'react-router-dom';
import { LogoutUser } from '../../api/apis/ApiAuth';

function Logout() {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      // Clear local storage
      localStorage.removeItem('token');
      localStorage.removeItem('username');

      // Call the backend logout endpoint
      await LogoutUser();

      // Redirect to login page
      navigate('/login');
    } catch (error) {
      console.error('Logout error:', error);
      // Redirect to login page even if logout fails
      navigate('/login');
    }
  };

  return (
    <div className="logout-container">
      <div className="logout-card">
        <h2>Are you sure you want to logout?</h2>
        <button onClick={handleLogout} className="logout-button">
          Logout
        </button>
      </div>
    </div>
  );
}

export default Logout;
