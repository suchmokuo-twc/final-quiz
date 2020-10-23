import React, { Component } from "react";
import { apiDeleteOrder, apiGetOrders } from "../../api/order";
import "./Order.scss";

export class Order extends Component {
  state = {
    ordersResponses: [],
    currentDeletingOrderIds: [],
  };

  setOrderResponses = (ordersResponses) => {
    this.setState({ ordersResponses });
  };

  addCurrentDeletingOrderId(id) {
    const { currentDeletingOrderIds } = this.state;
    currentDeletingOrderIds.push(id);

    this.setState({ currentDeletingOrderIds });
  }

  removeCurrentDeletingId(id) {
    const { currentDeletingOrderIds } = this.state;

    this.setState({
      currentDeletingOrderIds: currentDeletingOrderIds.filter(
        (orderId) => orderId !== id
      ),
    });
  }

  componentDidMount() {
    apiGetOrders().then(this.setOrderResponses).catch(console.error);
  }

  deleteOrder(id) {
    this.addCurrentDeletingOrderId(id);

    apiDeleteOrder(id)
      .then(() => this.handleDeleteOrderSucceed(id))
      .catch((error) => this.handleDeleteOrderFailed(error))
      .finally(() => this.removeCurrentDeletingId(id));
  }

  handleDeleteOrderSucceed(id) {
    const { ordersResponses } = this.state;

    this.setOrderResponses(ordersResponses.filter((o) => o.id !== id));
  }

  handleDeleteOrderFailed() {
    alert("订单删除失败，请稍后再试");
  }

  getOrderTotalPrice(order) {
    return order.products.reduce(
      (totalPrice, product) =>
        totalPrice + product.amount * product.product.price,
      0
    );
  }

  orderRender = (order) => {
    const { currentDeletingOrderIds } = this.state;
    const { id, products } = order;

    return (
      <div className="order-info" key={id}>
        <div className="order-info-title">
          <span>订单号：{id}</span>
          <button
            disabled={currentDeletingOrderIds.includes(id)}
            onClick={() => this.deleteOrder(id)}
          >
            删除
          </button>
        </div>

        <table>
          <thead>
            <tr>
              <th>#</th>
              <th>名称</th>
              <th>单价</th>
              <th>数量</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product, index) => (
              <tr key={id + product.product.id}>
                <td className="order-table-bold-cell">{index + 1}</td>
                <td>{product.product.name}</td>
                <td>{product.product.price.toFixed(2)}</td>
                <td>
                  {product.amount}
                  {product.product.unit}
                </td>
              </tr>
            ))}
            <tr className="order-total-price">
              <td className="order-table-bold-cell">总价</td>
              <td></td>
              <td className="order-table-bold-cell">{this.getOrderTotalPrice(order).toFixed(2)}</td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  };

  render() {
    const { ordersResponses } = this.state;

    if (ordersResponses.length === 0) {
      return (
        <div className="order-no-data">
          <p>暂无订单，返回商城页面继续购买</p>
        </div>
      );
    }

    return <div className="order">{ordersResponses.map(this.orderRender)}</div>;
  }
}
