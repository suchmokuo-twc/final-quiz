import { OrderResponseModel } from "../models";
import { post, URL } from "./base";

const ENDPOINT = URL + "/orders";

export async function apiCreateOrder(orderRequest) {
  const response = await post(ENDPOINT, orderRequest);

  if (response.status !== 201) {
    const data = await response.json();
    throw new Error(data.error);
  }

  return;
}

export async function apiGetOrders() {
  const response = await fetch(ENDPOINT);
  const data = await response.json();

  if (response.status !== 200) {
    throw new Error(data.error);
  }

  return data.map(
    ({ id, amount, product }) => new OrderResponseModel(id, amount, product)
  );
}
