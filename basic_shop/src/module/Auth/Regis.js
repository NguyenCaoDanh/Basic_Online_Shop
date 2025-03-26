import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { RegisUser }  from '../../api/apis/ApiAuth';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');
  const [error, setError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    setPasswordError('');

    // Validate that passwords match
    if (password !== repeatPassword) {
      setPasswordError('Passwords do not match.');
      return;
    }

    try {
      const response = await RegisUser(username, password);

      // Handle successful registration
      navigate('/login');
    } catch (error) {
      setError('Registration failed. Please try again.');
    }
  };

  return (
    <div className="container mt-5" style={{ fontFamily: '"Poppins", sans-serif' }}>
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="card shadow-sm mb-4" style={{ borderRadius: '10px' }}>
            <div className="card-body p-5">
              <h2 className="text-center mb-4">Register</h2>
              {error && <div className="alert alert-danger">{error}</div>}
              <form onSubmit={handleRegister}>
                <div className="mb-3">
                  <label htmlFor="username" className="form-label">Username</label>
                  <input
                    type="username"
                    className="form-control"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="password" className="form-label">Password</label>
                  <input
                    type="password"
                    className="form-control"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="repeatPassword" className="form-label">Repeat Password</label>
                  <input
                    type="password"
                    className="form-control"
                    id="repeatPassword"
                    value={repeatPassword}
                    onChange={(e) => setRepeatPassword(e.target.value)}
                    required
                  />
                </div>
                {passwordError && <div className="alert alert-danger">{passwordError}</div>}
                <button type="submit" className="btn btn-primary w-100">Register</button>
              </form>
              <div className="text-center mt-3">
                <a href="/login">Already have an account? Login</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;
    