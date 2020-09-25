export class OrderRequest {
  constructor(productId, amount) {
    this.productId = productId;
    this.amount = amount;
  }
}

export class OrderResponse {
  constructor(id, amount, product) {
    this.id = id;
    this.amount = amount;
    this.product = product;
  }
}
