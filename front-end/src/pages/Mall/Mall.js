import React, { Component } from "react";
import "./Mall.scss";
import { Product } from "./Product";
import { ProductModel } from "../../models";
import { apiGetProducts } from "../../api";

export class Mall extends Component {
  state = {
    products: [],
  };

  setProducts = (products) => {
    products = products.map(
      ({ id, name, price, unit, image }) =>
        new ProductModel(id, name, price, unit, image)
    );

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
          <Product product={product} />
        ))}
      </div>
    );
  }
}
