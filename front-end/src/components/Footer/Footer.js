import React from "react";
import "./Footer.scss";

export const Footer = (props) => {
  const { text } = props;

  return <footer className="footer">{text}</footer>;
};
