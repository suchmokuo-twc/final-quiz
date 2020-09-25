import React from "react";
import { HiOutlineHome } from "react-icons/hi";
import { AiOutlineShoppingCart } from "react-icons/ai";
import { BsPlus } from "react-icons/bs";
import { Header, HeaderItem, Footer } from "./components";
import { AddProduct, Mall, Order } from "./pages";
import "./App.scss";
import {
  Redirect,
  Route,
  Switch,
  BrowserRouter as Router,
} from "react-router-dom";

function App() {
  return (
    <Router>
      <Header>
        <HeaderItem to="/mall">
          <HiOutlineHome />
          商城
        </HeaderItem>
        <HeaderItem to="/orders">
          <AiOutlineShoppingCart />
          订单
        </HeaderItem>
        <HeaderItem to="/add-product">
          <BsPlus />
          添加商品
        </HeaderItem>
      </Header>

      <main>
        <Switch>
          <Route exact path="/mall" component={Mall} />
          <Route exact path="/orders" component={Order} />
          <Route exact path="/add-product" component={AddProduct} />
          <Redirect from="/*" to="/mall" />
        </Switch>
      </main>

      <Footer text="TW Mall &copy;2018 Created by ForCheng" />
    </Router>
  );
}

export default App;
