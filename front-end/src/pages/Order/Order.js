import React, { Component } from "react";
import { apiGetOrders } from "../../api/order";
import "./Order.scss";

export class Order extends Component {
  state = {
    orderResponses: [],
  };

  setOrderResponses = (orderResponses) => {
    this.setState({ orderResponses });
  };

  componentDidMount() {
    apiGetOrders().then(this.setOrderResponses).catch(console.error);
  }

  ordersRender() {
    const { orderResponses } = this.state;

    return orderResponses.map(
      ({ id, amount, product: { name, price, unit } }) => (
        <tr key={id}>
          <td>{name}</td>
          <td>{price}</td>
          <td>{amount}</td>
          <td>{unit}</td>
          <td>
            <button className="order-delete-btn">删除</button>
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
