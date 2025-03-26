import axios from 'axios';
import { BASE_API } from '../config/const_url';

const Http = axios.create({
  baseURL: BASE_API,
});

export default Http;
