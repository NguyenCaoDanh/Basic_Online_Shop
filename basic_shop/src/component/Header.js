import React from "react";
import { Link } from "react-router-dom";

const Header = () => {
  const isLoggedIn = !!localStorage.getItem('token');
  const email = localStorage.getItem('email');

  return (
    <>
      {/* Start Header/Navigation */}
      <nav className="custom-navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">
        <div className="container">
          <Link className="navbar-brand" to="/">Furni<span>.</span></Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarsFurni">
            <ul className="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
              <li className="nav-item active">
                <Link className="nav-link" to="/">Home</Link>
              </li>
              <li><Link className="nav-link" to="/shop">Shop</Link></li>
              <li><Link className="nav-link" to="/services">Services</Link></li>
              <li><Link className="nav-link" to="/teachers">Teachers</Link></li>
              <li><Link className="nav-link" to="/about">About us</Link></li>
              <li><Link className="nav-link" to="/contact">Contact us</Link></li>
            </ul>
            <ul className="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
              {isLoggedIn ? (
                <>
                  <li className="nav-item">
                    <span className="nav-link text-light">{email}</span>
                  </li>
                  <li><Link className="nav-link" to="/logout">Logout</Link></li>
                </>
              ) : (
                <li><Link className="nav-link" to="/login"><img src="/images/user.svg" alt="User" /></Link></li>
              )}
              <li><Link className="nav-link" to="/order-list"><img src="/images/cart.svg" alt="Cart" /></Link></li>
            </ul>
          </div>
        </div>
      </nav>
      {/* End Header/Navigation */}
    </>
  );
};

export default Header;
