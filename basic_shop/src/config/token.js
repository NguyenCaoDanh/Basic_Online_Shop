export const getToken = () => {
  try {
    return localStorage.getItem('token');
  } catch (error) {
    console.log(error);
  }
};
