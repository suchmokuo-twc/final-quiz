import React, { Component } from "react";
import { VscAdd } from "react-icons/vsc";
import "./Product.scss";

export class Product extends Component {
  render() {
    const { name, price, unit, image } = this.props.product;

    return (
      <div className="product">
        <img className="product-image" src={image} alt={name} />
        <span className="product-name">{name}</span>
        <span className="product-price">
          单价: {price}元/{unit}
        </span>
        <button className="product-add-btn">
          <VscAdd />
        </button>
      </div>
    );
  }
}
