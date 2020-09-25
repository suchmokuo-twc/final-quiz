import React, { Component } from "react";
import "./Mall.scss";
import { Product } from "./Product";
import { apiGetProducts } from "../../api";

export class Mall extends Component {
  state = {
    products: [],
  };

  setProducts = (products) => {
    this.setState({ products });
  };

  componentDidMount() {
    apiGetProducts().then(this.setProducts).catch(console.error);
  }

  render() {
    const { products } = this.state;

    return (
      <div className="mall">
        {products.map((product) => (
          <Product key={product.id} product={product} />
        ))}
      </div>
    );
  }
}
