import React, { Component } from "react";
import "./Header.scss";

export class Header extends Component {
  render() {
    const { children } = this.props;

    return (
      <header className="header">
        <div className="header-container">{children}</div>
      </header>
    );
  }
}
