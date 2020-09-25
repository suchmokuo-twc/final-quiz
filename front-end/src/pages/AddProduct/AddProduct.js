import React, { Component } from "react";
import { apiCreateProduct } from "../../api";
import { ProductModel } from "../../models";
import "./AddProduct.scss";

export class AddProduct extends Component {
  state = {
    formValues: {},
    creating: false,
  };

  get formValuesValid() {
    const { name, price, unit, image } = this.state.formValues;

    return name && price && unit && image;
  }

  get submitBtnDisabled() {
    const { creating } = this.state;
    const { formValuesValid } = this;

    return creating || !formValuesValid;
  }

  setFormValue(key, value) {
    const { formValues } = this.state;

    this.setState({
      formValues: {
        ...formValues,
        [key]: value,
      },
    });
  }

  setCreating(creating) {
    this.setState({ creating });
  }

  onFormFieldChange = (event) => {
    const { name, value } = event.target;

    this.setFormValue(name, value);
  };

  onFormSubmit = (event) => {
    event.preventDefault();

    const { name, price, unit, image } = this.state.formValues;
    const product = new ProductModel(null, name, price, unit, image);

    this.setCreating(true);

    apiCreateProduct(product)
      .then(() => this.handleCreateProductSucceed())
      .catch((error) => this.handleCreateProductFaild(error))
      .finally(() => this.setCreating(false));
  };

  handleCreateProductSucceed() {
    alert("创建成功！");
  }

  handleCreateProductFaild(error) {
    alert(error.message);
  }

  formRender() {
    const { onFormFieldChange, onFormSubmit, submitBtnDisabled } = this;

    return (
      <form>
        <label htmlFor="name" className="required">
          名称：
        </label>
        <input
          type="text"
          id="name"
          placeholder="名称"
          name="name"
          onChange={onFormFieldChange}
        />

        <label htmlFor="price" className="required">
          价格：
        </label>
        <input
          type="number"
          min="0"
          step="1"
          id="price"
          placeholder="价格"
          name="price"
          onChange={onFormFieldChange}
        />

        <label htmlFor="unit" className="required">
          单位：
        </label>
        <input
          type="text"
          id="unit"
          placeholder="单位"
          name="unit"
          onChange={onFormFieldChange}
        />

        <label htmlFor="image" className="required">
          图片：
        </label>
        <input
          type="text"
          id="image"
          placeholder="URL"
          name="image"
          onChange={onFormFieldChange}
        />

        <button
          className="add-product-submit-btn"
          onClick={onFormSubmit}
          disabled={submitBtnDisabled}
        >
          提交
        </button>
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
