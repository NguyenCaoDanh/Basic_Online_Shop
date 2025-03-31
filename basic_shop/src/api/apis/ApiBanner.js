import Http from '../Http'; // Import đối tượng Http để gọi API

export const createBanner = (file, status) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('status', status);

  return Http.post('api/banners/create', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const updateBanner = (id, file, status) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('status', status);

  return Http.put(`api/banners/update/${id}`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const deleteBanner = (id) => {
  return Http.delete(`api/banners/delete/${id}`);
};

export const getAllBanners = (page = 0, size = 10) => {
  return Http.get(`api/banners?page=${page}&size=${size}`);
};

export const getBannerById = (id) => {
  return Http.get(`api/banners/${id}`);
};
