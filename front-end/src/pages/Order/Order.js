import React, { Component } from "react";
import "./Order.scss";

export class Order extends Component {
  state = {
    orders: [
      {
        id: 1,
        name: "可乐",
        price: 1,
        amount: 1,
        unit: "瓶",
      },
      {
        id: 2,
        name: "可乐2",
        price: 2,
        amount: 2,
        unit: "罐",
      },
    ],
  };

  ordersRender() {
    const { orders } = this.state;

    return orders.map(({ id, name, price, amount, unit }) => (
      <tr key={id}>
        <td>{name}</td>
        <td>{price}</td>
        <td>{amount}</td>
        <td>{unit}</td>
        <td>
          <button className="order-delete-btn">删除</button>
        </td>
      </tr>
    ));
  }

  render() {
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
