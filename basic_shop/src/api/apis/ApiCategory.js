import Http from '../Http'; // Import đối tượng Http để gọi API

export const createCategory = (category) => {
  return Http.post('api/category/create', category);
};

export const getCategory = (id) => {
  return Http.get(`api/category/${id}`);
};

export const updateCategory = (id, updatedCategory) => {
  return Http.put(`api/category/update/${id}`, updatedCategory);
};

export const deleteCategory = (id) => {
  return Http.delete(`api/category/delete/${id}`);
};

export const getAllCategories = (page = 0, size = 10) => {
  return Http.get(`api/category?page=${page}&size=${size}`);
};
