import React, { Component } from "react";
import "./HeaderItem.scss";

export class HeaderItem extends Component {
  render() {
    const { children, onClick } = this.props;

    return (
      <div className="header-item" onClick={onClick}>
        {children}
      </div>
    );
  }
}
