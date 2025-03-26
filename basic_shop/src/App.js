import React from 'react';
import { useLocation } from 'react-router-dom';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux-setup/store';

// Import pages
import Header from './component/Header';
import Home from './module/Home/index';
import Footer from './component/Footer';
import Checkout from './module/Checkout/index';
import About from './module/About/index';
import Blog from './module/Blog/index';
import Cart from './module/Cart/index';
import Contact from './module/Contact/index';
import Services from './module/Services/index';
import Shop from './module/Shop/index';
import Login from './module/Auth/Login';
import Register from './module/Auth/Regis'
import Logout from './module/Auth/Logout';
const App = () => {
  const location = useLocation();
  const noHeaderFooter = [
    '/signin',
    '/signup',
    '/forgetpassword',
    '/emailverification',
    '/verificationstep2',
  ];

  return (
    <div>
      {!noHeaderFooter.includes(location.pathname) && <Header />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/about" element={<About />} />
        <Route path="/blog" element={<Blog />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/services" element={<Services />} />
        <Route path="/shop" element={<Shop />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/logout" element={<Logout />} />
        {/* <Route path="*" element={<NotFound/>}/> */}
      </Routes>
      {!noHeaderFooter.includes(location.pathname) && <Footer />}
    </div>
  );
};

function AppWrapper() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <App />
      </Provider>
    </BrowserRouter>
  );
}

export default AppWrapper;
