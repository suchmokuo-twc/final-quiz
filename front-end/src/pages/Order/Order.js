import React, { Component } from "react";
import { apiDeleteOrder, apiGetOrders } from "../../api/order";
import "./Order.scss";

export class Order extends Component {
  state = {
    orderResponses: [],
    currentDeletingOrderIds: [],
  };

  setOrderResponses = (orderResponses) => {
    this.setState({ orderResponses });
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
    const { orderResponses } = this.state;

    this.setOrderResponses(orderResponses.filter((o) => o.id !== id));
  }

  handleDeleteOrderFailed() {
    alert('订单删除失败，请稍后再试');
  }

  ordersRender() {
    const { orderResponses, currentDeletingOrderIds } = this.state;

    return orderResponses.map(
      ({ id, amount, product: { name, price, unit } }) => (
        <tr key={id}>
          <td>{name}</td>
          <td>{price}</td>
          <td>{amount}</td>
          <td>{unit}</td>
          <td>
            <button
              className="order-delete-btn"
              disabled={currentDeletingOrderIds.includes(id)}
              onClick={() => this.deleteOrder(id)}
            >
              删除
            </button>
          </td>
        </tr>
      )
    );
  }

  render() {
    const { orderResponses } = this.state;

    if (orderResponses.length === 0) {
      return (
        <div className="order-no-data">
          <p>暂无订单，返回商城页面继续购买</p>
        </div>
      );
    }

    return (
      <div className="order">
        <table>
          <thead>
            <tr>
              <th>名字</th>
              <th>单价</th>
              <th>数量</th>
              <th>单位</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>{this.ordersRender()}</tbody>
        </table>
      </div>
    );
  }
}
