import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { LoginUser } from '../../api/apis/ApiAuth';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');

    if (!username || !password) {
      setError('Please enter both username and password.');
      return;
    }

    try {
      const response = await LoginUser(username, password);
      console.log('Login Response:', response); // Log chi tiết

      // Kiểm tra nơi chứa token
      const token = response?.data?.data?.token;

      if (token) {
        localStorage.setItem('token', token);
        navigate('/');
      } else {
        throw new Error('Invalid credentials or server error.');
      }
    } catch (error) {
      console.error('Login error:', error.response?.data || error.message);
      setError(
        error.response?.data?.message ||
          'Failed to log in. Please check your credentials.'
      );
    }
  };

  return (
    <div
      className="container mt-5"
      style={{ fontFamily: '"Poppins", sans-serif' }}
    >
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="card shadow-sm mb-4" style={{ borderRadius: '10px' }}>
            <div className="card-body p-5">
              <h2 className="text-center mb-4">Login</h2>
              {error && <div className="alert alert-danger">{error}</div>}
              <form onSubmit={handleLogin}>
                <div className="mb-3">
                  <label htmlFor="username" className="form-label">
                    Username{' '}
                  </label>
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
                  <label htmlFor="password" className="form-label">
                    Password
                  </label>
                  <div className="input-group">
                    <input
                      type={showPassword ? 'text' : 'password'}
                      className="form-control"
                      id="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      required
                    />
                    <button
                      type="button"
                      className="input-group-text"
                      onClick={() => setShowPassword(!showPassword)}
                      style={{ cursor: 'pointer' }}
                    >
                      {showPassword ? 'Hide' : 'Show'}
                    </button>
                  </div>
                </div>
                <button type="submit" className="btn btn-primary w-100">
                  Login
                </button>
              </form>
              <div className="text-center mt-3">
                <a href="/register">Don't have an account? Sign up</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
