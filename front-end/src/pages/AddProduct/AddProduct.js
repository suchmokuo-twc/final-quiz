import React, { Component } from "react";
import "./AddProduct.scss";

export class AddProduct extends Component {
  formRender() {
    return (
      <form>
        <label htmlFor="name" className="required">
          名称：
        </label>
        <input type="text" id="name" placeholder="名称" />

        <label htmlFor="price" className="required">
          价格：
        </label>
        <input type="number" min="0" step="1" id="price" placeholder="价格" />

        <label htmlFor="unit" className="required">
          单位：
        </label>
        <input type="text" id="unit" placeholder="单位" />

        <label htmlFor="image" className="required">
          图片：
        </label>
        <input type="text" id="image" placeholder="URL" />

        <button className="add-product-submit-btn">提交</button>
      </form>
    );
  }

  render() {
    return (
      <div className="add-product">
        <h1>添加商品</h1>
        {this.formRender()}
      </div>
    );
  }
}
