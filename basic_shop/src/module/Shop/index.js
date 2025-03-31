import React, { useEffect, useState } from 'react';
import { getAllProducts } from '../../api/apis/ApiProduct';
import { BASE_URL } from '../../config/const_url';

const Shop = () => {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [sortOrder, setSortOrder] = useState('default');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;

  useEffect(() => {
    fetchProducts(page);
  }, [page]);

  useEffect(() => {
    applyFilters();
  }, [searchTerm, sortOrder, products]);

  const fetchProducts = async (page) => {
    try {
      const response = await getAllProducts(page, pageSize);
      const productList = response?.data?.data?.content || [];
      const totalPageCount = response?.data?.data?.totalPages || 1;

      setProducts(productList);
      setTotalPages(totalPageCount);
    } catch (error) {
      console.error('Error fetching products:', error);
      setProducts([]);
    }
  };

  const applyFilters = () => {
    let updatedProducts = [...products];

    // Lọc theo từ khóa tìm kiếm
    if (searchTerm) {
      updatedProducts = updatedProducts.filter((product) =>
        product.productName.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Sắp xếp sản phẩm
    if (sortOrder === 'price-asc') {
      updatedProducts.sort((a, b) => a.price - b.price);
    } else if (sortOrder === 'price-desc') {
      updatedProducts.sort((a, b) => b.price - a.price);
    }

    setFilteredProducts(updatedProducts);
  };

  return (
    <div>
      <div className="untree_co-section product-section before-footer-section">
        <div className="container">
          {/* Thanh tìm kiếm và sắp xếp */}
          <div className="d-flex justify-content-between my-3">
            <input
              type="text"
              className="form-control w-50"
              placeholder="Tìm kiếm sản phẩm..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <select
              className="form-select w-25"
              value={sortOrder}
              onChange={(e) => setSortOrder(e.target.value)}
            >
              <option value="default">Sắp xếp</option>
              <option value="price-asc">Giá: Thấp đến Cao</option>
              <option value="price-desc">Giá: Cao đến Thấp</option>
            </select>
          </div>

          {/* Danh sách sản phẩm */}
          <div className="row">
            {filteredProducts.length > 0 ? (
              filteredProducts.map((product, index) => (
                <div key={index} className="col-12 col-md-4 col-lg-3 mb-5">
                  <div className="product-item">
                    <img
                      src={`${BASE_URL}${product.imgUrl}`}
                      className="img-fluid product-thumbnail"
                      alt={product.productName}
                    />

                    <h3 className="product-title">{product.productName}</h3>
                    <strong className="product-price">${product.price}</strong>
                    <span className="icon-cross">
                      <img
                        src="images/cross.svg"
                        className="img-fluid"
                        alt="Add to cart"
                      />
                    </span>
                  </div>
                </div>
              ))
            ) : (
              <p className="text-center">Không có sản phẩm nào.</p>
            )}
          </div>

          {/* Phân trang */}
          <nav className="mt-4">
            <ul className="pagination justify-content-center">
              <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
                <button
                  className="page-link"
                  onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
                >
                  &laquo;
                </button>
              </li>
              {[...Array(totalPages)].map((_, i) => (
                <li
                  key={i}
                  className={`page-item ${i === page ? 'active' : ''}`}
                >
                  <button className="page-link" onClick={() => setPage(i)}>
                    {i + 1}
                  </button>
                </li>
              ))}
              <li
                className={`page-item ${
                  page >= totalPages - 1 ? 'disabled' : ''
                }`}
              >
                <button
                  className="page-link"
                  onClick={() =>
                    setPage((prev) => Math.min(prev + 1, totalPages - 1))
                  }
                >
                  &raquo;
                </button>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  );
};

export default Shop;
