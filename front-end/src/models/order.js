export class OrderRequestModel {
  constructor(productId, amount) {
    this.productId = productId;
    this.amount = amount;
  }
}

export class OrderResponseModel {
  constructor(id, amount, product) {
    this.id = id;
    this.amount = amount;
    this.product = product;
  }
}
