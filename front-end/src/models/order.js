import { ProductModel } from "./product";

export class OrderRequestModel {
  constructor(productId, amount) {
    this.productId = productId;
    this.amount = amount;
  }
}

export class OrdersResponseModel {
  constructor(id, products) {
    this.id = id;
    this.products = products.map(({ product, amount }) => ({
      amount,
      product: new ProductModel(
        product.id,
        product.name,
        product.price,
        product.unit,
        product.image
      ),
    }));
  }
}
