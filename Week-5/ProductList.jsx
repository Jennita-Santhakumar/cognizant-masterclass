import React from 'react';
import './ProductList.css';

/**
 * Product List Component - Display products in grid format
 */
function ProductList({ products }) {
  return (
    <div className="product-list-container">
      <h2>Products</h2>
      {products.length === 0 ? (
        <p>No products found</p>
      ) : (
        <div className="product-grid">
          {products.map((product) => (
            <div key={product.id} className="product-card">
              <div className="product-header">
                <h3>{product.name}</h3>
              </div>
              <div className="product-body">
                <p className="price">₹{product.price}</p>
                <p className="quantity">Stock: {product.quantity}</p>
              </div>
              <div className="product-footer">
                <button className="add-to-cart">Add to Cart</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default ProductList;
