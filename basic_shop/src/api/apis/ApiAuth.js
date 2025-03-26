import Http from '../Http';

export const LoginUser = (username, password) => {
  return Http.post('api/account/login', { username, password });
};
export const RegisUser = (username, password) => {
  return Http.post('api/account/register', { username, password });
};
export const LogoutUser = () => {
  return Http.post('auth/logout');
};
