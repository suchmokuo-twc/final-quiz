import React, { Component } from "react";
import { withRouter } from "react-router";
import "./HeaderItem.scss";

export const HeaderItem = withRouter(
  class extends Component {
    get active() {
      const {
        to,
        location: { pathname },
      } = this.props;

      return to === pathname;
    }

    onItemClick = () => {
      const { history, to } = this.props;

      history.push(to);
    };

    render() {
      const { children } = this.props;
      const { onItemClick, active } = this;

      const className = active ? "header-item active" : "header-item";

      return (
        <div className={className} onClick={onItemClick}>
          {children}
        </div>
      );
    }
  }
);
