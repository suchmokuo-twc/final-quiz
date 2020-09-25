import React, { Component } from "react";
import { VscAdd } from "react-icons/vsc";
import { apiCreateOrder } from "../../api/order";
import { OrderRequestModel } from "../../models";
import "./Product.scss";

export class Product extends Component {
  state = {
    addBtnDisabled: false,
  };

  setAddBtnDisabled = (addBtnDisabled) => {
    this.setState({ addBtnDisabled });
  };

  onOrder = () => {
    const { id } = this.props.product;

    this.setAddBtnDisabled(true);

    const orderRequest = new OrderRequestModel(id, 1);

    apiCreateOrder(orderRequest)
      .then(() => this.setAddBtnDisabled(false))
      .catch(console.error);
  };

  render() {
    const { name, price, unit, image } = this.props.product;
    const { addBtnDisabled } = this.state;
    const { onOrder } = this;

    return (
      <div className="product">
        <img className="product-image" src={image} alt={name} />
        <span className="product-name">{name}</span>
        <span className="product-price">
          单价: {price}元/{unit}
        </span>
        <button
          className="product-add-btn"
          disabled={addBtnDisabled}
          onClick={onOrder}
        >
          <VscAdd />
        </button>
      </div>
    );
  }
}
