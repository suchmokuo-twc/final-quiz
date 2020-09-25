import React from "react";
import "./App.css";
import { Header, HeaderItem } from "./components";
import { AddProduct, Mall, Order } from "./pages";
import { HiOutlineHome } from "react-icons/hi";
import { AiOutlineShoppingCart } from "react-icons/ai";
import { BsPlus } from "react-icons/bs";

function App() {
  return (
    <Header>
      <HeaderItem>
        <HiOutlineHome />
        商城
      </HeaderItem>
      <HeaderItem>
        <AiOutlineShoppingCart />
        订单
      </HeaderItem>
      <HeaderItem>
        <BsPlus />
        添加商品
      </HeaderItem>
    </Header>
  );
}

export default App;
