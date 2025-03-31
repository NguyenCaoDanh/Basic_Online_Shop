import Http from '../Http'; // Import đối tượng Http để gọi API

export const createProduct = (product, file) => {
  const formData = new FormData();
  formData.append('product', JSON.stringify(product));
  if (file) {
    formData.append('file', file);
  }

  return Http.post('api/products/create', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const updateProduct = (id, product, file) => {
  const formData = new FormData();
  formData.append('product', JSON.stringify(product));
  if (file) {
    formData.append('file', file);
  }

  return Http.put(`api/products/update/${id}`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const deleteProduct = (id) => {
  return Http.delete(`api/products/delete/${id}`);
};

export const getAllProducts = (page = 0, size = 10) => {
  return Http.get(`api/products?page=${page}&size=${size}`);
};

export const getProductById = (id) => {
  return Http.get(`api/products/${id}`);
};
